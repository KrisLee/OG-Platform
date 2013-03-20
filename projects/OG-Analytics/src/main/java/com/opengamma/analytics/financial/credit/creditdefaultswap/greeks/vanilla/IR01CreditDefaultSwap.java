/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.credit.creditdefaultswap.greeks.vanilla;

import org.threeten.bp.ZonedDateTime;

import com.opengamma.analytics.financial.credit.PriceType;
import com.opengamma.analytics.financial.credit.bumpers.SpreadBumpType;
import com.opengamma.analytics.financial.credit.cds.ISDACurve;
import com.opengamma.analytics.financial.credit.creditdefaultswap.definition.legacy.LegacyVanillaCreditDefaultSwapDefinition;
import com.opengamma.analytics.financial.credit.creditdefaultswap.pricing.legacy.PresentValueLegacyCreditDefaultSwap;
import com.opengamma.analytics.financial.credit.creditdefaultswap.pricing.vanilla.PresentValueCreditDefaultSwap;
import com.opengamma.analytics.financial.credit.isdayieldcurve.ISDADateCurve;
import com.opengamma.analytics.financial.credit.isdayieldcurve.InterestRateBumpType;
import com.opengamma.analytics.financial.credit.marketdatachecker.SpreadTermStructureDataChecker;
import com.opengamma.financial.convention.daycount.ActualThreeSixtyFive;
import com.opengamma.financial.convention.daycount.DayCount;
import com.opengamma.util.ArgumentChecker;

/**
 * Class containing methods for the computation of IR01 for a vanilla Legacy CDS (parallel and bucketed bumps)
 */
public class IR01CreditDefaultSwap {

  //------------------------------------------------------------------------------------------------------------------------------------------

  private final double _tolerance = 1e-15;

  private static final DayCount ACT365 = new ActualThreeSixtyFive();

  //-------------------------------------------------------------------------------------------------

  // TODO : Lots of ongoing work to do in this class - Work In Progress

  // TODO : Further checks on efficacy of input arguments
  // TODO : Need to get the times[] calculation correct
  // TODO : Need to consider more sophisticated sensitivity calculations e.g. algorithmic differentiation

  // NOTE : We enforce rateBump > 0, therefore if the marketSpreads > 0 (an exception is thrown if this is not the case) then bumpedMarketSpreads > 0 by construction

  // ------------------------------------------------------------------------------------------------------------------------------------------

  // Compute the IR01 by a parallel bump of each point on the yield curve

  public double getIR01ParallelShiftCreditDefaultSwap(
      final ZonedDateTime valuationDate,
      final LegacyVanillaCreditDefaultSwapDefinition cds,
      final ISDADateCurve yieldCurve,
      final ZonedDateTime[] marketTenors,
      final double[] marketSpreads,
      final double interestRateBump,
      final InterestRateBumpType interestRateBumpType,
      final PriceType priceType) {

    // ----------------------------------------------------------------------------------------------------------------------------------------

    // Check input objects are not null

    ArgumentChecker.notNull(valuationDate, "Valuation date");
    ArgumentChecker.notNull(cds, "LegacyCreditDefaultSwapDefinition");
    ArgumentChecker.notNull(yieldCurve, "YieldCurve");
    ArgumentChecker.notNull(marketTenors, "Market tenors");
    ArgumentChecker.notNull(marketSpreads, "Market spreads");
    ArgumentChecker.notNull(interestRateBumpType, "Interest rate bump type");
    ArgumentChecker.notNull(priceType, "price type");

    ArgumentChecker.notNegative(interestRateBump, "Interest rate bump");

    // Construct a market data checker object
    final SpreadTermStructureDataChecker checkMarketData = new SpreadTermStructureDataChecker();

    // Check the efficacy of the input market data
    checkMarketData.checkSpreadData(valuationDate, /*cds, */marketTenors, marketSpreads);

    // ----------------------------------------------------------------------------------------------------------------------------------------

    // Vector to hold the bumped market spreads
    final double[] bumpedInterestRates = new double[yieldCurve.getNumberOfCurvePoints()];

    // ----------------------------------------------------------------------------------------------------------------------------------------

    final double bumpInBp = interestRateBump / 10000;
    // Calculate the bumped spreads
    for (int m = 0; m < yieldCurve.getNumberOfCurvePoints(); m++) {

      if (interestRateBumpType == InterestRateBumpType.ADDITIVE_PARALLEL) {
        bumpedInterestRates[m] = yieldCurve.getInterestRate(m) + bumpInBp;
      }

      if (interestRateBumpType == InterestRateBumpType.MULTIPLICATIVE_PARALLEL) {
        bumpedInterestRates[m] = yieldCurve.getInterestRate(m) * (1 + bumpInBp);
      }
    }

    final ISDADateCurve bumpedYieldCurve = new ISDADateCurve("Bumped", marketTenors, yieldCurve.getTimePoints(), bumpedInterestRates, yieldCurve.getOffset());
    /*
    for (int m = 0; m < yieldCurve.getNumberOfCurvePoints(); m++) {
      System.out.println(yieldCurve.getTimenode(m) + "\t" + yieldCurve.getTimenode(m));
    }
     */

    // ----------------------------------------------------------------------------------------------------------------------------------------

    // Create a CDS PV calculator
    final PresentValueCreditDefaultSwap creditDefaultSwap = new PresentValueCreditDefaultSwap();

    // Calculate the unbumped CDS PV
    final double presentValue = creditDefaultSwap.calibrateAndGetPresentValue(valuationDate, cds, marketTenors, marketSpreads, yieldCurve, priceType);

    // Calculate the bumped (up) CDS PV
    final double bumpedPresentValue = creditDefaultSwap.calibrateAndGetPresentValue(valuationDate, cds, marketTenors, marketSpreads, bumpedYieldCurve, priceType);

    // ----------------------------------------------------------------------------------------------------------------------------------------

    // Calculate the parallel CS01
    final double parallelIR01 = (bumpedPresentValue - presentValue) / interestRateBump;

    return parallelIR01;
  }

  // ------------------------------------------------------------------------------------------------------------------------------------------

  // Compute the IR01 by bumping each point on the yield curve individually by interestRateBump (bump is same for all tenors)

  public double[] getIR01BucketedCreditDefaultSwap(
      final ZonedDateTime valuationDate,
      final LegacyVanillaCreditDefaultSwapDefinition cds,
      final ISDACurve yieldCurve,
      final ZonedDateTime[] marketTenors,
      final double[] marketSpreads,
      final double spreadBump,
      final SpreadBumpType spreadBumpType,
      final PriceType priceType) {

    // ----------------------------------------------------------------------------------------------------------------------------------------

    // Check input objects are not null

    ArgumentChecker.notNull(valuationDate, "Valuation date");
    ArgumentChecker.notNull(cds, "LegacyCreditDefaultSwapDefinition");
    ArgumentChecker.notNull(yieldCurve, "YieldCurve");
    ArgumentChecker.notNull(marketTenors, "Market tenors");
    ArgumentChecker.notNull(marketSpreads, "Market spreads");
    ArgumentChecker.notNull(spreadBumpType, "Spread bump type");

    ArgumentChecker.notNegative(spreadBump, "Spread bump");

    // Construct a market data checker object
    final SpreadTermStructureDataChecker checkMarketData = new SpreadTermStructureDataChecker();

    // Check the efficacy of the input market data
    checkMarketData.checkSpreadData(valuationDate, /*cds, */marketTenors, marketSpreads);

    // ----------------------------------------------------------------------------------------------------------------------------------------

    // Vector of bucketed CS01 sensitivities (per tenor)
    final double[] bucketedIR01 = new double[marketSpreads.length];

    // Vector to hold the bumped market spreads
    final double[] bumpedMarketSpreads = new double[marketSpreads.length];

    // ----------------------------------------------------------------------------------------------------------------------------------------

    // Create a CDS PV calculator
    final PresentValueLegacyCreditDefaultSwap creditDefaultSwap = new PresentValueLegacyCreditDefaultSwap();

    // Calculate the unbumped CDS PV
    final double presentValue = 0; //creditDefaultSwap.calibrateAndGetPresentValue(valuationDate, cds, marketTenors, marketSpreads, yieldCurve, priceType);

    // ----------------------------------------------------------------------------------------------------------------------------------------

    // Loop through and bump each of the spreads at each tenor
    for (int m = 0; m < marketSpreads.length; m++) {

      // TODO : Add the calculation for bumping the interest rate curve

      /*
      // Reset the bumpedMarketSpreads vector to the original marketSpreads
      for (int n = 0; n < marketTenors.length; n++) {
        bumpedMarketSpreads[n] = marketSpreads[n];
      }

      // Bump the spread at tenor m
      if (spreadBumpType == SpreadBumpType.ADDITIVE_BUCKETED) {
        bumpedMarketSpreads[m] = marketSpreads[m] + spreadBump;
      }

      if (spreadBumpType == SpreadBumpType.MULTIPLICATIVE_BUCKETED) {
        bumpedMarketSpreads[m] = marketSpreads[m] * (1 + spreadBump);
      }
       */

      // Calculate the bumped CDS PV
      final double bumpedPresentValue = 0; //creditDefaultSwap.calibrateAndGetPresentValue(valuationDate, cds, marketTenors, bumpedMarketSpreads, yieldCurve, priceType);

      bucketedIR01[m] = (bumpedPresentValue - presentValue) / spreadBump;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------

    return bucketedIR01;
  }

  // ------------------------------------------------------------------------------------------------------------------------------------------

}
