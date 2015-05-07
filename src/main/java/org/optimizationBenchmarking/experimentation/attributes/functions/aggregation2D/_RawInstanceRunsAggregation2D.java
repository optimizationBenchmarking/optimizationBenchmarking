package org.optimizationBenchmarking.experimentation.attributes.functions.aggregation2D;

import org.optimizationBenchmarking.experimentation.attributes.statistics.parameters.StatisticalParameter;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.processing.ColumnTransformedMatrix;
import org.optimizationBenchmarking.utils.math.matrix.processing.iterator2D.MatrixIterator2D;

/** the raw version of the 2D statistical parameter curve */
public final class _RawInstanceRunsAggregation2D extends
    _Aggregation2DBase<IInstanceRuns> {

  /** the x-dimension dimension */
  final IDimension m_xDim;
  /** the y-dimension dimension */
  final IDimension m_yDim;
  /** the transformation along the y-axis */
  final UnaryFunction m_yTransformation;
  /**
   * the parameter to be computed after applying the
   * {@link #m_yTransformation} if an {@link #m_yTransformation} is
   * specified, otherwise it is computed directly
   */
  final StatisticalParameter m_param;
  /** the x-dimension index */
  private final int m_xIndex;
  /** the y-dimension index */
  private final int m_yIndex;

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
   */
  public _RawInstanceRunsAggregation2D(final IDimension xDim,
      final IDimension yDim, final UnaryFunction yTransformation,
      final StatisticalParameter param) {
    super(EAttributeType.TEMPORARILY_STORED);

    if (xDim == null) {
      throw new IllegalArgumentException("x-dimension cannot be null."); //$NON-NLS-1$
    }
    if (yDim == null) {
      throw new IllegalArgumentException("y-dimension cannot be null."); //$NON-NLS-1$
    }
    if (param == null) {
      throw new IllegalArgumentException(//
          "Statistical parameter cannot be null."); //$NON-NLS-1$
    }

    this.m_xDim = xDim;
    this.m_xIndex = xDim.getIndex();
    this.m_yDim = yDim;
    this.m_yIndex = yDim.getIndex();
    this.m_yTransformation = yTransformation;
    this.m_param = param;
  }

  /** {@inheritDoc} */
  @Override
  public final IDimension getXDimension() {
    return this.m_xDim;
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction getXTransformation() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final IDimension getYDimension() {
    return this.m_yDim;
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction getYTransformation() {
    return this.m_yTransformation;
  }

  /** {@inheritDoc} */
  @Override
  public final StatisticalParameter getStatisticalParameter() {
    return this.m_param;
  }

  /** {@inheritDoc} */
  @Override
  protected final IMatrix compute(final IInstanceRuns data) {
    final IMatrix[] matrices;
    final ArrayListView<? extends IRun> runs;
    int i;

    if (this.m_yTransformation != null) {
      runs = data.getData();
      i = runs.size();
      matrices = new IMatrix[i];
      for (; (--i) >= 0;) {
        matrices[i] = new ColumnTransformedMatrix(//
            runs.get(i).selectColumns(this.m_xIndex, this.m_yIndex),//
            Identity.INSTANCE,//
            this.m_yTransformation);
      }

      return _Aggregation2DBase._aggregate(
          MatrixIterator2D.iterate(0, 1, matrices), this.m_param);
    }
    return _Aggregation2DBase._aggregate(
        MatrixIterator2D.iterate(this.m_xIndex, this.m_yIndex,
            data.getData()), this.m_param);
  }

  /** {@inheritDoc} */
  @Override
  public final StatisticalParameter getSecondaryStatisticalParameter() {
    return null;
  }
}
