/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.provider.calculator.blackstirfutures;

import com.opengamma.analytics.financial.interestrate.InstrumentDerivativeVisitorAdapter;
import com.opengamma.analytics.financial.interestrate.future.derivative.InterestRateFutureOptionMarginSecurity;
import com.opengamma.analytics.financial.interestrate.future.derivative.InterestRateFutureOptionMarginTransaction;
import com.opengamma.analytics.financial.interestrate.future.provider.InterestRateFutureOptionMarginSecurityBlackSmileMethod;
import com.opengamma.analytics.financial.provider.description.interestrate.BlackSTIRFuturesSmileProviderInterface;

/**
 * Calculates the gamma (second derivative of the price with respect to the underlying future price) for interest
 * rate future options.
 */
public final class GammaSTIRFutureOptionCalculator extends InstrumentDerivativeVisitorAdapter<BlackSTIRFuturesSmileProviderInterface, Double> {
  /**
   * The unique instance of the calculator.
   */
  private static final GammaSTIRFutureOptionCalculator INSTANCE = new GammaSTIRFutureOptionCalculator();

  /**
   * Gets the calculator instance.
   * @return The calculator.
   */
  public static GammaSTIRFutureOptionCalculator getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor.
   */
  private GammaSTIRFutureOptionCalculator() {
  }

  /**
   * Pricing methods.
   */
  private static final InterestRateFutureOptionMarginSecurityBlackSmileMethod METHOD_STIR_MARGIN = InterestRateFutureOptionMarginSecurityBlackSmileMethod.getInstance();

  @Override
  public Double visitInterestRateFutureOptionMarginTransaction(final InterestRateFutureOptionMarginTransaction futures, final BlackSTIRFuturesSmileProviderInterface black) {
    return METHOD_STIR_MARGIN.priceGamma(futures.getUnderlyingOption(), black);
  }

  @Override
  public Double visitInterestRateFutureOptionMarginSecurity(final InterestRateFutureOptionMarginSecurity futures, final BlackSTIRFuturesSmileProviderInterface black) {
    return METHOD_STIR_MARGIN.priceGamma(futures, black);
  }
}
