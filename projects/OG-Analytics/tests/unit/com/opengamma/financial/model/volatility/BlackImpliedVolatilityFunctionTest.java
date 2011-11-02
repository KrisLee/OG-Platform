/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.model.volatility;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;

import com.opengamma.financial.model.option.pricing.analytic.formula.BlackFunctionData;
import com.opengamma.financial.model.option.pricing.analytic.formula.BlackPriceFunction;
import com.opengamma.financial.model.option.pricing.analytic.formula.EuropeanVanillaOption;

/**
 * 
 */
public class BlackImpliedVolatilityFunctionTest {
  private static final double FORWARD = 134.5;
  private static final double DF = 0.87;
  private static final double T = 4.5;
  private static final double SIGMA = 0.2;
  private static final BlackFunctionData[] DATA;
  private static final EuropeanVanillaOption[] OPTIONS;
  private static final double[] PRICES;
  private static final double[] STRIKES;
  private static final BlackPriceFunction FORMULA = new BlackPriceFunction();
  private static final int N = 10;

  //  private static final double[] ERF_COF = new double[] {-1.3026537197817094, 6.4196979235649026e-1,
  //      1.9476473204185836e-2, -9.561514786808631e-3, -9.46595344482036e-4,
  //      3.66839497852761e-4, 4.2523324806907e-5, -2.0278578112534e-5,
  //      -1.624290004647e-6, 1.303655835580e-6, 1.5626441722e-8, -8.5238095915e-8,
  //      6.529054439e-9, 5.059343495e-9, -9.91364156e-10, -2.27365122e-10,
  //      9.6467911e-11, 2.394038e-12, -6.886027e-12, 8.94487e-13, 3.13092e-13,
  //      -1.12708e-13, 3.81e-16, 7.106e-15, -1.523e-15, -9.4e-17, 1.21e-16, -2.8e-17 };

  static {
    PRICES = new double[N];
    STRIKES = new double[N];
    DATA = new BlackFunctionData[N];
    OPTIONS = new EuropeanVanillaOption[N];
    for (int i = 0; i < 10; i++) {
      STRIKES[i] = 50 + 2 * i;
      DATA[i] = new BlackFunctionData(FORWARD, DF, SIGMA);
      OPTIONS[i] = new EuropeanVanillaOption(STRIKES[i], T, true);
      PRICES[i] = FORMULA.getPriceFunction(OPTIONS[i]).evaluate(DATA[i]);
    }
  }

  @Test
  public void test() {
    final BlackImpliedVolatilityFormula formula = new BlackImpliedVolatilityFormula();
    for (int i = 0; i < N; i++) {
      final double vol = formula.getImpliedVolatility(DATA[i], OPTIONS[i], PRICES[i]);
      assertEquals(SIGMA, vol, 1e-6);
    }
  }

  @Test(enabled = false)
  public void test2() {
    double vol = BlackFormulaRepository.impliedVolatility(291.975, 405, 115, 0.231222322, true);
    System.out.println(vol);
  }

}
