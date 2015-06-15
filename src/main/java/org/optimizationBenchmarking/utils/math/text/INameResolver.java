package org.optimizationBenchmarking.utils.math.text;

import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.compound.FunctionBuilder;

/**
 * A resolver for names.
 */
public interface INameResolver {

  /**
   * Resolve the given {@code name} and return a
   * {@link org.optimizationBenchmarking.utils.math.functions.MathematicalFunction
   * mathematical function} fitting to it
   *
   * @param name
   *          the name to be resolved
   * @param builder
   *          the function builder
   * @return the mathematical function fitting to {@code name}
   * @throws IllegalArgumentException
   *           if the name cannot be resolved
   */
  public abstract MathematicalFunction resolve(final String name,
      final FunctionBuilder<?> builder);

}
