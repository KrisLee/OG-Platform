/**
 * Copyright (C) 2009 - 2009 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.math.integration;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opengamma.math.function.DoubleFunction1D;
import com.opengamma.math.function.special.OrthonormalHermitePolynomialFunction;
import com.opengamma.math.rootfinding.NewtonRaphsonSingleRootFinder;
import com.opengamma.util.tuple.Pair;

/**
 * 
 */
public class GaussHermiteOrthogonalPolynomialGeneratingFunction implements QuadratureWeightAndAbscissaFunction {
  private static final Logger s_logger = LoggerFactory.getLogger(GaussHermiteOrthogonalPolynomialGeneratingFunction.class);
  private static final OrthonormalHermitePolynomialFunction HERMITE = new OrthonormalHermitePolynomialFunction();
  private static final NewtonRaphsonSingleRootFinder ROOT_FINDER = new NewtonRaphsonSingleRootFinder(1e-12);

  @Override
  public GaussianQuadratureFunction generate(final int n, final Double... parameters) {
    if (parameters != null) {
      s_logger.info("Limits for this integration method are +/-infinity; ignoring bounds");
    }
    return generate(n);
  }

  private GaussianQuadratureFunction generate(final int n) {
    Validate.isTrue(n > 0);
    final double[] x = new double[n];
    final double[] w = new double[n];
    final int m = (n + 1) / 2;
    final Pair<DoubleFunction1D, DoubleFunction1D>[] polynomials = HERMITE.getPolynomialsAndFirstDerivative(n);
    final Pair<DoubleFunction1D, DoubleFunction1D> pair = polynomials[n];
    final DoubleFunction1D function = pair.getFirst();
    final DoubleFunction1D derivative = pair.getSecond();
    double root = 0;
    for (int i = 0; i < m; i++) {
      root = getInitialRootGuess(root, i, n, x);
      root = ROOT_FINDER.getRoot(function, derivative, root);
      final double dp = derivative.evaluate(root);
      x[i] = -root;
      x[n - 1 - i] = root;
      w[i] = 2. / (dp * dp);
      w[n - 1 - i] = w[i];
    }
    return new GaussianQuadratureFunction(x, w);
  }

  private double getInitialRootGuess(final double previousRoot, final int i, final int n, final double[] x) {
    if (i == 0) {
      return Math.sqrt(2 * n + 1) - 1.85575 * Math.pow(2 * n + 1, -1. / 6);
    }
    if (i == 1) {
      return previousRoot - 1.14 * Math.pow(n, 0.426) / previousRoot;
    }
    if (i == 2) {
      return 1.86 * previousRoot - 0.86 * x[0];
    }
    if (i == 3) {
      return 1.91 * previousRoot - 0.91 * x[1];
    }
    return 2 * previousRoot - x[i - 2];
  }
}
