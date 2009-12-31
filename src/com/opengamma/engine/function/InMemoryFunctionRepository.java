/**
 * Copyright (C) 2009 - 2009 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.engine.function;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.opengamma.engine.position.Position;
import com.opengamma.engine.security.Security;
import com.opengamma.engine.value.AnalyticValueDefinition;
import com.opengamma.engine.value.AnalyticValueDefinitionComparator;

/**
 * An in-memory implementation of {@link FunctionRepository}.
 * This can either be used as-is through a factory which scans available functions,
 * or it can be used as a cache on top of a more costly function repository. 
 *
 * @author kirk
 */
public class InMemoryFunctionRepository implements FunctionRepository {
  private final Map<String, FunctionInvoker> _invokersByUniqueIdentifier =
    new HashMap<String, FunctionInvoker>();
  private final Set<FunctionDefinition> _functions = new HashSet<FunctionDefinition>();
  
  public InMemoryFunctionRepository() {
  }
  
  public synchronized void addFunction(AbstractFunction function, FunctionInvoker invoker) {
    validateFunction(function, invoker);
    _functions.add(function);
    function.setUniqueIdentifier(Integer.toString(_functions.size()));
    _invokersByUniqueIdentifier.put(function.getUniqueIdentifier(), invoker);
  }

  /**
   * @param function
   * @param invoker
   */
  private void validateFunction(AbstractFunction function,
      FunctionInvoker invoker) {
    // REVIEW kirk 2009-12-31 -- After the rewrite is done this is meaningless.
    if(function == null) {
      throw new NullPointerException("Must provide a function.");
    }
    if(invoker == null) {
      throw new NullPointerException("Must provide an invoker.");
    }
    if(function instanceof PrimitiveFunctionDefinition) {
      if(!(invoker instanceof PrimitiveFunctionInvoker)) {
        throw new IllegalArgumentException("Must provide primitive invoker for primitive definition.");
      }
    } else if(function instanceof SecurityFunctionDefinition) {
      if(!(invoker instanceof SecurityFunctionInvoker)) {
        throw new IllegalArgumentException("Must provide security invoker for security definition.");
      }
    } else if(function instanceof PositionFunctionDefinition) {
      if(!(invoker instanceof PositionFunctionInvoker)) {
        throw new IllegalArgumentException("Must provide position invoker for position definition.");
      }
    } else if(function instanceof AggregatePositionFunctionDefinition) {
      if(!(invoker instanceof AggregatePositionFunctionInvoker)) {
        throw new IllegalArgumentException("Must provide aggregate position invoker for aggregate position definition.");
      }
    } else if(function instanceof NewFunctionDefinition) {
      if(!(invoker instanceof NewFunctionInvoker)) {
        throw new IllegalArgumentException("Must provide new style invoker for aggregate position definition.");
      }
    } else {
      throw new IllegalArgumentException("Unexpected analytic function definition " + function.getClass());
    }
  }

  @Override
  public Collection<FunctionDefinition> getAllFunctions() {
    return Collections.unmodifiableCollection(_functions);
  }

  @Override
  public Collection<FunctionDefinition> getFunctionsProducing(
      Collection<AnalyticValueDefinition<?>> outputs) {
    Set<FunctionDefinition> result = new HashSet<FunctionDefinition>();
    for(FunctionDefinition function : _functions) {
      Collection<AnalyticValueDefinition<?>> possibleResults = null;
      if(!(function instanceof PrimitiveFunctionDefinition)) {
        // Can't work with it because it requires execution inside of a
        // security or position context.
        continue;
      }
      PrimitiveFunctionDefinition primitiveFunction = (PrimitiveFunctionDefinition) function;
      // Might be applicable even if we're in a position-specific context.
      possibleResults = primitiveFunction.getPossibleResults();
      if(allValuesSatisfied(outputs, possibleResults)) {
        result.add(function);
      }
    }
    return result;
  }
  
  @Override
  public Collection<FunctionDefinition> getFunctionsProducing(
      Collection<AnalyticValueDefinition<?>> outputs, Security security) {
    Set<FunctionDefinition> result = new HashSet<FunctionDefinition>();
    for(FunctionDefinition function : _functions) {
      Collection<AnalyticValueDefinition<?>> possibleResults = null;
      boolean applicable = false;
      if(function instanceof PrimitiveFunctionDefinition) {
        PrimitiveFunctionDefinition primitiveFunction = (PrimitiveFunctionDefinition) function;
        // Might be applicable even if we're in a security-specific context.
        applicable = true;
        possibleResults = primitiveFunction.getPossibleResults();
      } else if(function instanceof SecurityFunctionDefinition) {
        SecurityFunctionDefinition secDefinition = (SecurityFunctionDefinition) function;
        if(security != null) {
          applicable = secDefinition.isApplicableTo(security.getSecurityType());
        } else {
          applicable = secDefinition.isApplicableTo((String)null);
        }
        if(applicable) {
          possibleResults = secDefinition.getPossibleResults(security);
        }
      }
      // Neither Position or Aggregate are ever applicable to just a Security.
      if(applicable && allValuesSatisfied(outputs, possibleResults)) {
        result.add(function);
      }
    }
    return result;
  }
  
  @Override
  public Collection<FunctionDefinition> getFunctionsProducing(
      Collection<AnalyticValueDefinition<?>> outputs, Position position) {
    Set<FunctionDefinition> result = new HashSet<FunctionDefinition>();
    for(FunctionDefinition function : _functions) {
      Collection<AnalyticValueDefinition<?>> possibleResults = null;
      boolean applicable = false;
      if(function instanceof PrimitiveFunctionDefinition) {
        PrimitiveFunctionDefinition primitiveFunction = (PrimitiveFunctionDefinition) function;
        // Might be applicable even if we're in a position-specific context.
        applicable = true;
        possibleResults = primitiveFunction.getPossibleResults();
      } else if(function instanceof SecurityFunctionDefinition) {
        // Might be applicable as we've got a security resolved.
        SecurityFunctionDefinition secDefinition = (SecurityFunctionDefinition) function;
        Security security = position.getSecurity();
        if(security != null) {
          applicable = secDefinition.isApplicableTo(security.getSecurityType());
        } else {
          applicable = secDefinition.isApplicableTo((String)null);
        }
        if(applicable) {
          possibleResults = secDefinition.getPossibleResults(security);
        }
      } else if(function instanceof PositionFunctionDefinition) {
        PositionFunctionDefinition posDefinition = (PositionFunctionDefinition) function;
        applicable = posDefinition.isApplicableTo(position);
        if(applicable) {
          possibleResults = posDefinition.getPossibleResults(position);
        }
      }
      // Aggregates are never applicable.
      if(applicable && allValuesSatisfied(outputs, possibleResults)) {
        result.add(function);
      }
    }
    return result;
  }
  
  @Override
  public Collection<FunctionDefinition> getFunctionsProducing(
      Collection<AnalyticValueDefinition<?>> outputs, Collection<Position> positions) {
    Set<FunctionDefinition> result = new HashSet<FunctionDefinition>();
    for(FunctionDefinition function : _functions) {
      Collection<AnalyticValueDefinition<?>> possibleResults = null;
      boolean applicable = false;
      if(function instanceof PrimitiveFunctionDefinition) {
        PrimitiveFunctionDefinition primitiveFunction = (PrimitiveFunctionDefinition) function;
        // Might be applicable even if we're in an aggregate context.
        applicable = true;
        possibleResults = primitiveFunction.getPossibleResults();
      } else if(function instanceof AggregatePositionFunctionDefinition) {
        AggregatePositionFunctionDefinition aggDefinition = (AggregatePositionFunctionDefinition) function;
        applicable = aggDefinition.isApplicableTo(positions);
        if(applicable) {
          possibleResults = aggDefinition.getPossibleResults(positions);
        }
      }
      // Security and position functions are never applicable to an aggregate
      if(applicable && allValuesSatisfied(outputs, possibleResults)) {
        result.add(function);
      }
    }
    return result;
  }

  /**
   * @param outputs
   * @param possibleResults
   * @return
   */
  private boolean allValuesSatisfied(
      Collection<AnalyticValueDefinition<?>> outputs,
      Collection<AnalyticValueDefinition<?>> possibleResults) {
    assert possibleResults != null;
    for(AnalyticValueDefinition<?> output : outputs) {
      boolean foundForOutput = false;
      for(AnalyticValueDefinition<?> possibleResult : possibleResults) {
        if(AnalyticValueDefinitionComparator.matches(output, possibleResult)) {
          foundForOutput = true;
          break;
        }
      }
      if(!foundForOutput) {
        return false;
      }
    }
    return true;
  }

  @Override
  public FunctionInvoker getInvoker(String uniqueIdentifier) {
    return _invokersByUniqueIdentifier.get(uniqueIdentifier);
  }

}
