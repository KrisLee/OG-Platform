/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.engine.view.calcnode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

import com.google.common.collect.Sets;
import com.opengamma.engine.ComputationTarget;
import com.opengamma.engine.ComputationTargetResolver;
import com.opengamma.engine.ComputationTargetType;
import com.opengamma.engine.DefaultComputationTargetResolver;
import com.opengamma.engine.function.FunctionExecutionContext;
import com.opengamma.engine.function.FunctionRepository;
import com.opengamma.engine.function.InMemoryFunctionRepository;
import com.opengamma.engine.function.MockFunction;
import com.opengamma.engine.position.MockPositionSource;
import com.opengamma.engine.security.MockSecuritySource;
import com.opengamma.engine.value.ComputedValue;
import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.engine.view.cache.MapViewComputationCache;
import com.opengamma.engine.view.cache.MapViewComputationCacheSource;
import com.opengamma.engine.view.cache.ViewComputationCacheSource;
import com.opengamma.util.InetAddressUtils;

/**
 * 
 */
public class AbstractCalculationNodeTest {
  
  private static class TestCalculationNode extends AbstractCalculationNode {

    protected TestCalculationNode(ViewComputationCacheSource cacheSource, FunctionRepository functionRepository,
        FunctionExecutionContext functionExecutionContext, ComputationTargetResolver targetResolver,
        ViewProcessorQuerySender calcNodeQuerySender) {
      super(cacheSource, 
          functionRepository, 
          functionExecutionContext, 
          targetResolver, 
          calcNodeQuerySender, 
          InetAddressUtils.getLocalHostName());
    }
  }

  @Test
  public void mockFunctionInvocationOneInputMissing() {
    ComputationTarget target = new ComputationTarget(ComputationTargetType.PRIMITIVE, "USD");
    ValueRequirement inputReq = new ValueRequirement("INPUT", target.toSpecification());
    ValueSpecification inputSpec = new ValueSpecification(inputReq);
    //ComputedValue inputValue = new ComputedValue(inputSpec, "Just an input object");
    ValueRequirement outputReq = new ValueRequirement("OUTPUT", target.toSpecification());
    ValueSpecification outputSpec = new ValueSpecification(outputReq);
    ComputedValue outputValue = new ComputedValue(outputSpec, "Nothing we care about");
    MockFunction fn = new MockFunction(
        target,
        Sets.newHashSet(inputReq),
        Sets.newHashSet(outputValue));
    
    InMemoryFunctionRepository functionRepo = new InMemoryFunctionRepository();
    functionRepo.addFunction(fn, fn);
    
    long iterationTimestamp = System.currentTimeMillis();
    CalculationJobSpecification jobSpec = new CalculationJobSpecification("view", "config", iterationTimestamp, 1L);
    MapViewComputationCacheSource cacheSource = new MapViewComputationCacheSource();
    //cache.putValue(inputValue);
    FunctionExecutionContext execContext = new FunctionExecutionContext();
    ViewProcessorQuerySender viewProcessorQuerySender = new ViewProcessorQuerySender(null);
    ComputationTargetResolver targetResolver = new DefaultComputationTargetResolver(new MockSecuritySource(), new MockPositionSource());
    
    TestCalculationNode calcNode = new TestCalculationNode(
        cacheSource,
        functionRepo,
        execContext,
        targetResolver,
        viewProcessorQuerySender);

    CalculationJobItem calculationJobItem = new CalculationJobItem(fn.getUniqueIdentifier(), 
        target.toSpecification(), 
        Sets.newHashSet(inputSpec), 
        Sets.newHashSet(outputReq));
    CalculationJob calcJob = new CalculationJob(jobSpec, Collections.singletonList(calculationJobItem), new DummyResultWriter());
    
    long startTime = System.nanoTime();
    CalculationJobResult jobResult = calcNode.executeJob(calcJob);
    long endTime = System.nanoTime();
    assertNotNull(jobResult);
    assertTrue(jobResult.getDuration() >= 0);
    assertTrue(endTime - startTime >= jobResult.getDuration());
    assertEquals(1, jobResult.getResultItems().size());
    CalculationJobResultItem resultItem = jobResult.getResultItems().get(0);
    assertEquals(calculationJobItem, resultItem.getItem());
    assertEquals(InvocationResult.ERROR, resultItem.getResult());
  }

  @Test
  public void mockFunctionInvocationOneInputOneOutput() {
    ComputationTarget target = new ComputationTarget(ComputationTargetType.PRIMITIVE, "USD");
    ValueRequirement inputReq = new ValueRequirement("INPUT", target.toSpecification());
    ValueSpecification inputSpec = new ValueSpecification(inputReq);
    ComputedValue inputValue = new ComputedValue(inputSpec, "Just an input object");
    ValueRequirement outputReq = new ValueRequirement("OUTPUT", target.toSpecification());
    ValueSpecification outputSpec = new ValueSpecification(outputReq);
    ComputedValue outputValue = new ComputedValue(outputSpec, "Nothing we care about");
    MockFunction fn = new MockFunction(
        target,
        Sets.newHashSet(inputReq),
        Sets.newHashSet(outputValue));
    
    InMemoryFunctionRepository functionRepo = new InMemoryFunctionRepository();
    functionRepo.addFunction(fn, fn);
    
    long iterationTimestamp = System.currentTimeMillis();
    CalculationJobSpecification jobSpec = new CalculationJobSpecification("view", "config", iterationTimestamp, 1L);
    MapViewComputationCacheSource cacheSource = new MapViewComputationCacheSource();
    MapViewComputationCache cache = cacheSource.getCache(jobSpec.getViewName(), jobSpec.getCalcConfigName(), iterationTimestamp);
    cache.putValue(inputValue);
    
    FunctionExecutionContext execContext = new FunctionExecutionContext();
    ViewProcessorQuerySender viewProcessorQuerySender = new ViewProcessorQuerySender(null);
    ComputationTargetResolver targetResolver = new DefaultComputationTargetResolver(new MockSecuritySource(), new MockPositionSource());
    
    TestCalculationNode calcNode = new TestCalculationNode(
        cacheSource,
        functionRepo,
        execContext,
        targetResolver,
        viewProcessorQuerySender);
    
    CalculationJobItem calculationJobItem = new CalculationJobItem(fn.getUniqueIdentifier(), 
        target.toSpecification(), 
        Sets.newHashSet(inputSpec), 
        Sets.newHashSet(outputReq));

    CalculationJob calcJob = new CalculationJob(jobSpec, Collections.singletonList(calculationJobItem), new DummyResultWriter());
    
    CalculationJobResult jobResult = calcNode.executeJob(calcJob);
    assertNotNull(jobResult);
    assertEquals(1, jobResult.getResultItems().size());
    CalculationJobResultItem resultItem = jobResult.getResultItems().get(0);
    assertEquals(calculationJobItem, resultItem.getItem());
    assertEquals(InvocationResult.SUCCESS, resultItem.getResult());
    assertEquals(2, cache.size());
    assertEquals("Nothing we care about", cache.getValue(outputSpec));
  }

}
