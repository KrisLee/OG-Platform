/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.model.futureoption;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opengamma.core.security.Security;
import com.opengamma.engine.ComputationTarget;
import com.opengamma.engine.function.FunctionCompilationContext;
import com.opengamma.engine.value.ValuePropertyNames;
import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.engine.value.ValueRequirementNames;
import com.opengamma.financial.analytics.OpenGammaFunctionExclusions;
import com.opengamma.financial.analytics.model.curve.forward.ForwardCurveValuePropertyNames;
import com.opengamma.financial.analytics.model.equity.option.EquityOptionFunction;
import com.opengamma.financial.analytics.model.volatility.surface.black.BlackVolatilitySurfacePropertyNamesAndValues;
import com.opengamma.financial.property.DefaultPropertyFunction;
import com.opengamma.financial.security.FinancialSecurityTypes;
import com.opengamma.financial.security.FinancialSecurityUtils;
import com.opengamma.financial.security.option.CommodityFutureOptionSecurity;
import com.opengamma.util.ArgumentChecker;

/**
 *
 */
public class CommodityFutureOptionDefaults extends DefaultPropertyFunction {
  private static final Logger s_logger = LoggerFactory.getLogger(CommodityFutureOptionDefaults.class);
  private static final String[] VALUE_REQUIREMENTS = new String[] {
    ValueRequirementNames.PRESENT_VALUE,
    ValueRequirementNames.VALUE_DELTA,
    ValueRequirementNames.VALUE_GAMMA,
    ValueRequirementNames.VALUE_THETA,
    ValueRequirementNames.VALUE_VEGA,
    ValueRequirementNames.FORWARD_DELTA,
    ValueRequirementNames.FORWARD_GAMMA,
    ValueRequirementNames.DELTA,
    ValueRequirementNames.GAMMA,
    ValueRequirementNames.VEGA,
    ValueRequirementNames.THETA
  };
  private final PriorityClass _priority;
  private final Map<String, String> _currencyToCurveName;
  private final Map<String, String> _currencyToCurveCalculationConfigName;
  private final Map<String, String> _currencyToSurfaceName;
  private final Map<String, String> _currencyToSurfaceCalculationMethod;
  private final Map<String, String> _currencyToInterpolationMethod;
  private final Map<String, String> _currencyToForwardCurveName;
  private final Map<String, String> _currencyToForwardCurveCalculationMethod;

  public CommodityFutureOptionDefaults(final String priority, final String... defaultsPerCurrency) {
    super(FinancialSecurityTypes.COMMODITY_FUTURE_OPTION_SECURITY, true);
    ArgumentChecker.notNull(priority, "priority");
    ArgumentChecker.notNull(defaultsPerCurrency, "defaults per currency");
    final int n = defaultsPerCurrency.length;
    ArgumentChecker.isTrue(n % 8 == 0, "Need one discounting curve name, discounting curve calculation config, surface name, surface calculation method, surface interpolation method," +
        "forward curve name and forward curve calculation method per currency");
    _priority = PriorityClass.valueOf(priority);
    _currencyToCurveName = new LinkedHashMap<>();
    _currencyToCurveCalculationConfigName = new LinkedHashMap<>();
    _currencyToSurfaceName = new LinkedHashMap<>();
    _currencyToSurfaceCalculationMethod = new LinkedHashMap<>();
    _currencyToInterpolationMethod = new LinkedHashMap<>();
    _currencyToForwardCurveName = new LinkedHashMap<>();
    _currencyToForwardCurveCalculationMethod = new LinkedHashMap<>();
    for (int i = 0; i < n; i += 8) {
      final String currency = defaultsPerCurrency[i];
      _currencyToCurveName.put(currency, defaultsPerCurrency[i + 1]);
      _currencyToCurveCalculationConfigName.put(currency, defaultsPerCurrency[i + 2]);
      _currencyToSurfaceName.put(currency, defaultsPerCurrency[i + 3]);
      _currencyToSurfaceCalculationMethod.put(currency, defaultsPerCurrency[i + 4]);
      _currencyToInterpolationMethod.put(currency, defaultsPerCurrency[i + 5]);
      _currencyToForwardCurveName.put(currency, defaultsPerCurrency[i + 6]);
      _currencyToForwardCurveCalculationMethod.put(currency, defaultsPerCurrency[i + 7]);
    }
  }

  @Override
  public boolean canApplyTo(final FunctionCompilationContext context, final ComputationTarget target) {
    final Security security = target.getSecurity();
    final String currency = ((CommodityFutureOptionSecurity) security).getCurrency().getCode();
    return _currencyToCurveCalculationConfigName.containsKey(currency);
  }

  @Override
  protected void getDefaults(final PropertyDefaults defaults) {
    for (final String valueRequirement : VALUE_REQUIREMENTS) {
      defaults.addValuePropertyName(valueRequirement, ValuePropertyNames.CURVE);
      defaults.addValuePropertyName(valueRequirement, ValuePropertyNames.CURVE_CALCULATION_CONFIG);
      defaults.addValuePropertyName(valueRequirement, ValuePropertyNames.SURFACE);
      defaults.addValuePropertyName(valueRequirement, ValuePropertyNames.SURFACE_CALCULATION_METHOD);
      defaults.addValuePropertyName(valueRequirement, BlackVolatilitySurfacePropertyNamesAndValues.PROPERTY_SMILE_INTERPOLATOR);
      defaults.addValuePropertyName(valueRequirement, ForwardCurveValuePropertyNames.PROPERTY_FORWARD_CURVE_CALCULATION_METHOD);
      defaults.addValuePropertyName(valueRequirement, EquityOptionFunction.PROPERTY_FORWARD_CURVE_NAME);
    }
  }

  @Override
  protected Set<String> getDefaultValue(final FunctionCompilationContext context, final ComputationTarget target, final ValueRequirement desiredValue, final String propertyName) {
    final String currency = FinancialSecurityUtils.getCurrency(target.getSecurity()).getCode();
    final String curveName = _currencyToCurveName.get(currency);
    if (curveName == null) {
      s_logger.error("Could not get curve name for {}; should never happen", target.getValue());
      return null;
    }
    if (ValuePropertyNames.CURVE.equals(propertyName)) {
      return Collections.singleton(curveName);
    }
    if (ValuePropertyNames.CURVE_CALCULATION_CONFIG.equals(propertyName)) {
      return Collections.singleton(_currencyToCurveCalculationConfigName.get(currency));
    }
    if (ValuePropertyNames.SURFACE.equals(propertyName)) {
      return Collections.singleton(_currencyToSurfaceName.get(currency));
    }
    if (ValuePropertyNames.SURFACE_CALCULATION_METHOD.equals(propertyName)) {
      return Collections.singleton(_currencyToSurfaceCalculationMethod.get(currency));
    }
    if (EquityOptionFunction.PROPERTY_FORWARD_CURVE_NAME.equals(propertyName)) {
      return Collections.singleton(_currencyToForwardCurveName.get(currency));
    }
    if (ForwardCurveValuePropertyNames.PROPERTY_FORWARD_CURVE_CALCULATION_METHOD.equals(propertyName)) {
      return Collections.singleton(_currencyToForwardCurveCalculationMethod.get(currency));
    }
    if (BlackVolatilitySurfacePropertyNamesAndValues.PROPERTY_SMILE_INTERPOLATOR.equals(propertyName)) {
      return Collections.singleton(_currencyToInterpolationMethod.get(currency));
    }
    s_logger.error("Could not find default value for {} in this function", propertyName);
    return null;
  }

  @Override
  public PriorityClass getPriority() {
    return _priority;
  }

  @Override
  public String getMutualExclusionGroup() {
    return OpenGammaFunctionExclusions.COMMODITY_BLACK_VOLATILITY_SURFACE_DEFAULTS;
  }

}