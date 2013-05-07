/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.model.equity.option;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.management.openmbean.OpenDataException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.analytics.financial.equity.EquityOptionBlackPresentValueCalculator;
import com.opengamma.analytics.financial.equity.EqyOptBaroneAdesiWhaleyPresentValueCalculator;
import com.opengamma.analytics.financial.equity.StaticReplicationDataBundle;
import com.opengamma.analytics.financial.equity.option.EquityOption;
import com.opengamma.analytics.financial.interestrate.InstrumentDerivative;
import com.opengamma.analytics.financial.model.interestrate.curve.ForwardCurve;
import com.opengamma.analytics.financial.model.volatility.surface.BlackVolatilitySurface;
import com.opengamma.engine.ComputationTarget;
import com.opengamma.engine.ComputationTargetSpecification;
import com.opengamma.engine.function.FunctionCompilationContext;
import com.opengamma.engine.function.FunctionInputs;
import com.opengamma.engine.value.ComputedValue;
import com.opengamma.engine.value.ValueProperties;
import com.opengamma.engine.value.ValueProperties.Builder;
import com.opengamma.engine.value.ValuePropertyNames;
import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.engine.value.ValueRequirementNames;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.financial.analytics.model.equity.EquitySecurityScenarioPnLFunction;
import com.opengamma.financial.analytics.model.equity.ScenarioPnLPropertyNamesAndValues;

/**
 * Simple scenario Function returns the difference in PresentValue between defined Scenario and current market conditions.
 * @author casey
 */
public class EquityOptionBAWScenarioPnLFunction extends EquityOptionBAWFunction {
  
  private static final EqyOptBaroneAdesiWhaleyPresentValueCalculator s_pvCalculator = EqyOptBaroneAdesiWhaleyPresentValueCalculator.getInstance();

  /** Default constructor */
  public EquityOptionBAWScenarioPnLFunction() {
    super(ValueRequirementNames.PNL);
  }
  
  private static final String s_priceShift = ScenarioPnLPropertyNamesAndValues.PROPERTY_PRICE_SHIFT;
  private static final String s_volShift = ScenarioPnLPropertyNamesAndValues.PROPERTY_VOL_SHIFT;
  private static final String s_priceShiftType = ScenarioPnLPropertyNamesAndValues.PROPERTY_PRICE_SHIFT_TYPE;
  private static final String s_volShiftType = ScenarioPnLPropertyNamesAndValues.PROPERTY_VOL_SHIFT_TYPE;
  
  private static final Logger s_logger = LoggerFactory.getLogger(EquityOptionBAWScenarioPnLFunction.class);
  
  private String getValueRequirementName() {
    return ValueRequirementNames.PNL;
  }
  
  @Override
  protected Set<ComputedValue> computeValues(InstrumentDerivative derivative, StaticReplicationDataBundle market, FunctionInputs inputs, Set<ValueRequirement> desiredValues,
      ComputationTargetSpecification targetSpec, ValueProperties resultProperties) {

    // Form market scenario
    ValueProperties constraints = desiredValues.iterator().next().getConstraints();
    
    // Apply shift to forward price curve
    final ForwardCurve fwdCurveScen;
    String priceShiftTypeConstraint = constraints.getValues(s_priceShiftType).iterator().next();
    String stockConstraint = constraints.getValues(s_priceShift).iterator().next();
    
    if (stockConstraint.equals("")) { 
      fwdCurveScen = market.getForwardCurve(); // use base market prices
    } else {
      final Double fractionalShift;
      if (priceShiftTypeConstraint.equalsIgnoreCase("Additive")) {
        final Double absShift = Double.valueOf(stockConstraint);
        final double spotPrice = market.getForwardCurve().getSpot();
        fractionalShift = absShift / spotPrice;
      } else if (priceShiftTypeConstraint.equalsIgnoreCase("Multiplicative")) {
        fractionalShift = Double.valueOf(stockConstraint);
      } else {
        fractionalShift = Double.valueOf(stockConstraint);
        s_logger.debug("Valid PriceShiftType's: Additive and Multiplicative. Found: " + priceShiftTypeConstraint + " Defaulting to Multiplicative.");
      }
      fwdCurveScen = market.getForwardCurve().withFractionalShift(fractionalShift);
    }
    
    // Apply shift to vol surface curve
    final BlackVolatilitySurface<?> volSurfScen;
    String volConstraint = constraints.getValues(s_volShift).iterator().next();
    if (volConstraint.equals("")) { // use base market vols
      volSurfScen = market.getVolatilitySurface(); 
    } else { // bump vol surface
      final Double shiftVol = Double.valueOf(volConstraint);
      String volShiftTypeConstraint = constraints.getValues(s_volShiftType).iterator().next();
      final boolean additiveShift;
      if (volShiftTypeConstraint.equalsIgnoreCase("Additive")) {
        additiveShift = true;
      } else if (volShiftTypeConstraint.equalsIgnoreCase("Multiplicative")) {
        additiveShift = false;
      } else {
        s_logger.debug("In ScenarioPnLFunctions, VolShiftType's are Additive and Multiplicative. Found: " + priceShiftTypeConstraint + " Defaulting to Multiplicative.");
        additiveShift = false;
      }
      volSurfScen = market.getVolatilitySurface().withShift(shiftVol, additiveShift);
    }
    
    final StaticReplicationDataBundle marketScen = new StaticReplicationDataBundle(volSurfScen, market.getDiscountCurve(), fwdCurveScen);
    
    // Compute present value under scenario
    final double pvScen = derivative.accept(s_pvCalculator, marketScen);
    
    // present value under current market 
    final double pvBase = (double) inputs.getValue(ValueRequirementNames.PRESENT_VALUE);
    
    // Return with spec
    final ValueSpecification resultSpec = new ValueSpecification(getValueRequirementNames()[0], targetSpec, resultProperties);
    return Collections.singleton(new ComputedValue(resultSpec, pvScen - pvBase));
  }
  
  public Set<ValueRequirement> getRequirements(final FunctionCompilationContext context, final ComputationTarget target, final ValueRequirement desiredValue) {
    // The primary set of requirements are taken from parent function
    Set<ValueRequirement> superReqs = super.getRequirements(context, target, desiredValue);
    if (superReqs == null) {
      return null;
    }
   
    final ValueProperties constraints = desiredValue.getConstraints();
    // Add requirement for present value in base scenario - this will be shared across all scenarios
    ValueProperties.Builder baseConstraints = constraints.copy()
        .withoutAny(s_priceShift).withoutAny(s_priceShiftType)
        .withoutAny(s_volShift).withoutAny(s_volShiftType);
    ValueRequirement basePvReq = new ValueRequirement(ValueRequirementNames.PRESENT_VALUE, target.toSpecification(), baseConstraints.get());    
    
    // Handle scenario constraints    
    ValueProperties.Builder scenarioDefaults = null;
    final Set<String> priceShiftSet = constraints.getValues(s_priceShift);
    if (priceShiftSet == null || priceShiftSet.isEmpty()) { 
      scenarioDefaults = constraints.copy().withoutAny(s_priceShift).with(s_priceShift, ""); 
    }
    final Set<String> priceShiftTypeSet = constraints.getValues(s_priceShiftType);
    if (priceShiftTypeSet == null || priceShiftTypeSet.isEmpty()) {
      if (scenarioDefaults == null) {
        scenarioDefaults = constraints.copy().withoutAny(s_priceShiftType).with(s_priceShiftType, "Multiplicative");
      } else {
        scenarioDefaults = scenarioDefaults.withoutAny(s_priceShiftType).with(s_priceShiftType, "Multiplicative");
      }
    }
    final Set<String> volShiftSet = constraints.getValues(s_volShift);
    if (volShiftSet == null || volShiftSet.isEmpty()) {
      if (scenarioDefaults == null) {
        scenarioDefaults = constraints.copy().withoutAny(s_volShift).with(s_volShift, "");
      } else {
        scenarioDefaults = scenarioDefaults.withoutAny(s_volShift).with(s_volShift, "");
      }
    }
    final Set<String> volShiftSetType = constraints.getValues(s_volShiftType);
    if (volShiftSetType == null || volShiftSetType.isEmpty()) {
      if (scenarioDefaults == null) {
        scenarioDefaults = constraints.copy().withoutAny(s_volShiftType).with(s_volShiftType, "Multiplicative");
      } else {
        scenarioDefaults = scenarioDefaults.withoutAny(s_volShiftType).with(s_volShiftType, "Multiplicative");
      }
    }
    
    // If defaults have been added, this adds additional copy of the Function into dep graph with the adjusted constraints
    if (scenarioDefaults != null) {
      ValueRequirement reqWithScenarioConstraints = new ValueRequirement(getValueRequirementName(), target.toSpecification(), scenarioDefaults.get());
      return Sets.newHashSet(reqWithScenarioConstraints);
    } else {  // Scenarios are defined, so we're satisfied
      superReqs.add(basePvReq);
      return superReqs;
    }
  }

  @Override
  public Set<ValueSpecification> getResults(final FunctionCompilationContext context, final ComputationTarget target, final Map<ValueSpecification, ValueRequirement> inputs) {
    if (inputs.size() == 1) {
      ValueSpecification input = inputs.keySet().iterator().next();
      if (getValueRequirementName().equals(input.getValueName())) {
        return inputs.keySet();
      }
    }
    ValueSpecification superSpec = super.getResults(context, target, inputs).iterator().next();
    Builder properties = superSpec.getProperties().copy()
        .withAny(s_priceShift)
        .withAny(s_volShift)
        .withAny(s_priceShiftType)
        .withAny(s_volShiftType);
        
    return Collections.singleton(new ValueSpecification(getValueRequirementName(), target.toSpecification(), properties.get()));    
  }
}
