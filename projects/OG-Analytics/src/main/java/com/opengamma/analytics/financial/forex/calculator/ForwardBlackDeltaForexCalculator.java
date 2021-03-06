/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.forex.calculator;

import com.opengamma.analytics.financial.forex.derivative.ForexOptionDigital;
import com.opengamma.analytics.financial.forex.derivative.ForexOptionSingleBarrier;
import com.opengamma.analytics.financial.forex.derivative.ForexOptionVanilla;
import com.opengamma.analytics.financial.forex.method.ForexOptionDigitalBlackMethod;
import com.opengamma.analytics.financial.forex.method.ForexOptionSingleBarrierBlackMethod;
import com.opengamma.analytics.financial.forex.method.ForexOptionVanillaBlackSmileMethod;
import com.opengamma.analytics.financial.interestrate.InstrumentDerivativeVisitorAdapter;
import com.opengamma.analytics.financial.interestrate.YieldCurveBundle;
import com.opengamma.analytics.financial.model.option.definition.SmileDeltaTermStructureDataBundle;
import com.opengamma.util.ArgumentChecker;

/**
 * Calculator of the gamma (second order derivative with respect to the spot rate) for Forex derivatives in the Black (Garman-Kohlhagen) world.
 * @deprecated Curve builders that use and populate {@link YieldCurveBundle}s are deprecated.
 */
@Deprecated
public class ForwardBlackDeltaForexCalculator extends InstrumentDerivativeVisitorAdapter<YieldCurveBundle, Double> {

  /**
   * The unique instance of the calculator.
   */
  private static final ForwardBlackDeltaForexCalculator INSTANCE = new ForwardBlackDeltaForexCalculator();

  /**
   * Gets the calculator instance.
   * @return The calculator.
   */
  public static ForwardBlackDeltaForexCalculator getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor.
   */
  ForwardBlackDeltaForexCalculator() {
  }

  /** Vanilla option calculator */
  private static final ForexOptionVanillaBlackSmileMethod METHOD_FXOPTIONVANILLA = ForexOptionVanillaBlackSmileMethod.getInstance();
  /** Digital option calculator */
  private static final ForexOptionDigitalBlackMethod METHOD_FXDIGITAL = ForexOptionDigitalBlackMethod.getInstance();
  /** Single barrier option calculator */
  private static final ForexOptionSingleBarrierBlackMethod METHOD_FXBARRIER = ForexOptionSingleBarrierBlackMethod.getInstance();

  @Override
  public Double visitForexOptionVanilla(final ForexOptionVanilla derivative, final YieldCurveBundle data) {
    ArgumentChecker.isTrue(data instanceof SmileDeltaTermStructureDataBundle, "Must have data bundle with volatility data");
    return METHOD_FXOPTIONVANILLA.forwardDeltaTheoretical(derivative, data);
  }

  @Override
  public Double visitForexOptionDigital(final ForexOptionDigital derivative, final YieldCurveBundle data) {
    ArgumentChecker.isTrue(data instanceof SmileDeltaTermStructureDataBundle, "Must have data bundle with volatility data");
    return METHOD_FXDIGITAL.forwardDeltaTheoretical(derivative, data);
  }

  @Override
  public Double visitForexOptionSingleBarrier(final ForexOptionSingleBarrier derivative, final YieldCurveBundle data) {
    ArgumentChecker.isTrue(data instanceof SmileDeltaTermStructureDataBundle, "Must have data bundle with volatility data");
    return METHOD_FXBARRIER.forwardDeltaTheoretical(derivative, data);
  }
}
