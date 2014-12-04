package org.optimizationBenchmarking.utils.math.mathEngine.impl.R;

import org.optimizationBenchmarking.utils.math.mathEngine.spec.IExpression;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IFunction;

/** A class for creating functions in R */
abstract class _RFunctionBase extends _RExpressionScope implements
    IFunction {

  /** the number of children, to be utilized by sub-classes */
  int m_childCount;

  /**
   * create the R expression
   * 
   * @param owner
   *          the owner
   * @param engine
   *          the engine
   */
  _RFunctionBase(final _RScope owner, final _REngine engine) {
    super(owner, engine);
  }

  /** {@inheritDoc} */
  @Override
  public final IExpression parameter() {
    return this.parameter(null);
  }

  /** {@inheritDoc} */
  @Override
  public IExpression parameter(final String name) {
    return new _RExpression(this, this.m_engine);
  }

}
