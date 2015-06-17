package org.optimizationBenchmarking.experimentation.attributes.functions;

import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;

/**
 * A transformation to be applied to values from a given dimension.
 */
public final class DimensionTransformation extends
    _Transformation<IDimension> {

  /**
   * Create the data transformation
   * 
   * @param function
   *          the function to be applied
   * @param constants
   *          the constants
   * @param dimension
   *          the dimension
   */
  DimensionTransformation(final UnaryFunction function,
      final _PropertyConstant[] constants, final IDimension dimension) {
    super(function, constants, dimension);
  }

  /**
   * Create the data transformation
   * 
   * @param dimension
   *          the dimension
   */
  public DimensionTransformation(final IDimension dimension) {
    this(Identity.INSTANCE, null, dimension);
  }

  /**
   * The dimension to be transformed by this transformer
   * 
   * @return dimension to be transformed by this transformer
   */
  public final IDimension getDimension() {
    return this.m_renderable;
  }
}
