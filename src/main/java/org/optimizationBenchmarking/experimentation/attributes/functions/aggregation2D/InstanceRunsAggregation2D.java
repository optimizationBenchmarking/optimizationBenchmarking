package org.optimizationBenchmarking.experimentation.attributes.functions.aggregation2D;

import org.optimizationBenchmarking.experimentation.attributes.statistics.parameters.StatisticalParameter;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.processing.ColumnTransformedMatrix;

/** The 2D statistical parameter curve over an instance runs. */
public final class InstanceRunsAggregation2D extends
    _Aggregation2DBase<IInstanceRuns> {

  /** the raw parameter */
  final _RawInstanceRunsAggregation2D m_raw;

  /** the transformation of the x-axis */
  final UnaryFunction m_xTransform;

  /**
   * create the raw statistics parameter
   *
   * @param xDim
   *          the {@code x}-dimension
   * @param xTransformation
   *          the transformation along the {@code x}-dimension
   * @param yDim
   *          the {@code y}-dimension
   * @param yTransformation
   *          the transformation along the {@code y}-dimension
   * @param param
   *          the parameter to be computed after applying the
   *          {@code yTransformation} (if any {@code yTransformation} is
   *          specified, otherwise it is computed directly)
   */
  public InstanceRunsAggregation2D(final IDimension xDim,
      final UnaryFunction xTransformation, final IDimension yDim,
      final UnaryFunction yTransformation, final StatisticalParameter param) {
    super(EAttributeType.NEVER_STORED);

    this.m_raw = new _RawInstanceRunsAggregation2D(xDim, yDim,
        yTransformation, param);
    this.m_xTransform = xTransformation;
  }

  /** {@inheritDoc} */
  @Override
  public final IDimension getXDimension() {
    return this.m_raw.m_xDim;
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction getXTransformation() {
    return this.m_xTransform;
  }

  /** {@inheritDoc} */
  @Override
  public final IDimension getYDimension() {
    return this.m_raw.m_yDim;
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction getYTransformation() {
    return this.m_raw.m_yTransformation;
  }

  /** {@inheritDoc} */
  @Override
  public final StatisticalParameter getStatisticalParameter() {
    return this.m_raw.m_param;
  }

  /** {@inheritDoc} */
  @Override
  protected final IMatrix compute(final IInstanceRuns data) {
    final IMatrix raw;

    raw = this.m_raw.compute(data);
    if (this.m_xTransform != null) {
      return new ColumnTransformedMatrix(raw, this.m_xTransform,
          Identity.INSTANCE);
    }
    return raw;
  }

  /** {@inheritDoc} */
  @Override
  public final StatisticalParameter getSecondaryStatisticalParameter() {
    return null;
  }
}
