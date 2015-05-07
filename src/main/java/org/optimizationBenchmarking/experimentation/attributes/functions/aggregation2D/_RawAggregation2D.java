package org.optimizationBenchmarking.experimentation.attributes.functions.aggregation2D;

import org.optimizationBenchmarking.experimentation.attributes.statistics.parameters.StatisticalParameter;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.processing.iterator2D.MatrixIterator2D;

/** the raw version of the 2D statistical parameter curve over experiments */
public final class _RawAggregation2D extends
    _Aggregation2DBase<IExperiment> {

  /** the raw parameter */
  final _RawInstanceRunsAggregation2D m_raw;

  /** the secondary parameter */
  final StatisticalParameter m_second;

  /**
   * create the raw statistics parameter
   *
   * @param xDim
   *          the {@code x}-dimension
   * @param yDim
   *          the {@code y}-dimension
   * @param yTransformation
   *          the transformation along the {@code y}-dimension
   * @param param
   *          the parameter to be computed after applying the
   *          {@code yTransformation} (if any {@code yTransformation} is
   *          specified, otherwise it is computed directly)
   * @param secondary
   *          the secondary parameter
   */
  public _RawAggregation2D(final IDimension xDim, final IDimension yDim,
      final UnaryFunction yTransformation,
      final StatisticalParameter param,
      final StatisticalParameter secondary) {
    super(EAttributeType.TEMPORARILY_STORED);

    if (secondary == null) {
      throw new IllegalArgumentException(//
          "The secondary statistical parameter cannot be null."); //$NON-NLS-1$
    }

    this.m_raw = new _RawInstanceRunsAggregation2D(xDim, yDim,
        yTransformation, param);
    this.m_second = secondary;
  }

  /** {@inheritDoc} */
  @Override
  public final IDimension getXDimension() {
    return this.m_raw.m_xDim;
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction getXTransformation() {
    return null;
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
  protected final IMatrix compute(final IExperiment data) {
    final IMatrix[] matrices;
    final ArrayListView<? extends IInstanceRuns> instanceRuns;
    int i;

    instanceRuns = data.getData();
    i = instanceRuns.size();
    matrices = new IMatrix[i];
    for (; (--i) >= 0;) {
      matrices[i] = this.m_raw.compute(instanceRuns.get(i));
    }

    return _Aggregation2DBase._aggregate(
        MatrixIterator2D.iterate(0, 1, matrices), this.m_second);
  }

  /** {@inheritDoc} */
  @Override
  public final StatisticalParameter getSecondaryStatisticalParameter() {
    return this.m_second;
  }
}
