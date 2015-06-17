package org.optimizationBenchmarking.experimentation.attributes.functions;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;
import org.optimizationBenchmarking.utils.math.text.IMathRenderable;

/**
 * A transformation to be applied to values from a given source.
 */
public final class NamedSourceTransformation extends
    _Transformation<IMathRenderable> {

  /**
   * Create the data transformation
   * 
   * @param function
   *          the function to be applied
   * @param constants
   *          the constants
   * @param source
   *          the named source
   */
  NamedSourceTransformation(final UnaryFunction function,
      final _PropertyConstant[] constants, final IMathRenderable source) {
    super(function, constants, source);
  }

  /**
   * Create the data transformation
   * 
   * @param source
   *          the named source
   */
  public NamedSourceTransformation(final IMathRenderable source) {
    this(Identity.INSTANCE, null, source);
  }
}
