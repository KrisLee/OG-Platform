/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.model.volatility.surface;

import org.apache.commons.lang.Validate;

import com.opengamma.financial.model.interestrate.curve.ForwardCurve;
import com.opengamma.financial.model.volatility.local.LocalVolatilitySurface;
import com.opengamma.math.surface.Surface;

/**
 *  A local volatility surface parameterised by time and moneyness m = strike/forward
 */
public class LocalVolatilitySurfaceMoneyness extends LocalVolatilitySurface<Moneyness> {

  private final ForwardCurve _fc;

  public LocalVolatilitySurfaceMoneyness(final LocalVolatilitySurfaceMoneyness other) {
    super(other.getSurface());
    _fc = other.getForwardCurve();
  }

  /**
   * @param surface A local volatility surface parameterised by time and moneyness m = strike/forward
   * @param forwardCurve the forward curve
   */
  public LocalVolatilitySurfaceMoneyness(final Surface<Double, Double, Double> surface, final ForwardCurve forwardCurve) {
    super(surface);
    Validate.notNull(forwardCurve, "null forward curve");
    _fc = forwardCurve;
  }

  @Override
  public double getVolatility(final double t, final double k) {
    final double f = _fc.getForward(t);
    final Moneyness x = new Moneyness(k, f);
    return getVolatility(t, x);
  }

  /**
   * Depending on the application the same local volatility surface can be seem either as either a function of calendar
   * time and value moneyness, or as a function of expiry and moneyness. Value moneyness is the current value of the forward
   * f(t,T) divided by the initial value of the forward f(0,T) for same expiry T. (strike) moneyness is the strike divided by
   * the forward f(0,T) at the required expity T.
   * Return a volatility for the time - moneyness pair provided.
   * Interpolation/extrapolation behaviour depends on underlying surface
   * @param t time
   * @param m the moneyness. value moneyness  mv = F(t,T)/F(0,T), and strike moneyness mk = k/F(0,T) where k is the strike
   *  and F(t, T) is the value of the forward for expiry at time T at time t.
   * @return The local volatility
   */
  public double getVolatilityForMoneyness(final double t, final double m) {
    final Moneyness s = new Moneyness(m);
    return getVolatility(t, s);
  }

  public ForwardCurve getForwardCurve() {
    return _fc;
  }
}
