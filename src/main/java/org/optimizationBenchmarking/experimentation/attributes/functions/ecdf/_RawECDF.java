package org.optimizationBenchmarking.experimentation.attributes.functions.ecdf;

import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Div;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.MatrixBuilder;
import org.optimizationBenchmarking.utils.math.matrix.processing.iterator2D.MatrixIterator2D;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StableSum;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * The raw data of the Estimated Cumulative Distribution Function returns,
 * for an experiment or instance runs set, the fraction of runs which have
 * reached a specified goal.
 */
final class _RawECDF extends Attribute<IElementSet, IMatrix> {

  /** the time dimension */
  final IDimension m_timeDim;

  /** the goal dimension */
  final IDimension m_goalDim;

  /** the goal value as {@code double} */
  final double m_goalValueDouble;

  /** the goal value as {@code long} */
  final long m_goalValueLong;

  /**
   * should we use {@link #m_goalValueDouble} as goal ({@code true}) or
   * {@link #m_goalValueLong} ({@code false}?
   */
  final boolean m_useLongGoal;

  /** the time index */
  final int m_timeIndex;
  /** the goal index */
  final int m_goalIndex;

  /**
   * Create the ECDF attribute
   *
   * @param timeDim
   *          the time dimension
   * @param goalDim
   *          the goal dimension
   * @param goalValue
   *          the goal value
   */
  _RawECDF(final IDimension timeDim, final IDimension goalDim,
      final Number goalValue) {
    super(EAttributeType.TEMPORARILY_STORED);

    if ((timeDim == null) || (goalDim == null) || (goalValue == null)) {
      throw new IllegalArgumentException(//
          "Cannot compute ECDF for time dimension "//$NON-NLS-1$
          + timeDim + //
          ", goal dimension" //$NON-NLS-1$
          + goalDim + //
          ", and goal value " + goalValue);//$NON-NLS-1$
    }

    this.m_timeDim = timeDim;
    this.m_timeIndex = timeDim.getIndex();

    this.m_goalDim = goalDim;
    this.m_goalIndex = goalDim.getIndex();

    switch (goalDim.getDataType()) {
      case BYTE:
      case SHORT:
      case INT:
      case LONG: {
        this.m_useLongGoal = true;

        if ((NumericalTypes.getTypes(goalValue) & NumericalTypes.IS_LONG) != 0) {
          this.m_goalValueLong = goalValue.longValue();
        } else {
          this.m_goalValueLong = _RawECDF.__doubleToLong(
              goalValue.doubleValue(), goalDim);
        }

        this.m_goalValueDouble = this.m_goalValueLong;
        break;
      }
      default: {
        this.m_goalValueDouble = goalValue.doubleValue();
        this.m_useLongGoal = false;
        this.m_goalValueLong = _RawECDF.__doubleToLong(
            this.m_goalValueDouble, goalDim);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_timeIndex),//
            HashUtils.hashCode(this.m_goalIndex)),//
            (this.m_useLongGoal//
                ? HashUtils.hashCode(this.m_goalValueLong)//
                    : HashUtils.hashCode(this.m_goalValueDouble)));//
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final _RawECDF other;

    if (o == this) {
      return true;
    }

    if (o instanceof _RawECDF) {
      other = ((_RawECDF) o);
      if ((this.m_timeIndex == other.m_timeIndex) && //
          (this.m_goalIndex == other.m_goalIndex)) {
        if (this.m_useLongGoal) {
          if (other.m_useLongGoal) {
            return (this.m_goalValueLong == other.m_goalValueLong);
          }
        } else {
          if (!(other.m_useLongGoal)) {
            return (EComparison.compareDoubles(this.m_goalValueDouble,
                other.m_goalValueDouble) == 0);
          }
        }
      }
    }

    return false;
  }

  /**
   * convert a {@code double} goal value to a {@code long}
   *
   * @param d
   *          the goal value
   * @param goalDim
   *          the goal dimension
   * @return the converted value
   */
  private static final long __doubleToLong(final double d,
      final IDimension goalDim) {
    if (d <= Long.MIN_VALUE) {
      return Long.MIN_VALUE;
    }
    if (d >= Long.MAX_VALUE) {
      return Long.MAX_VALUE;
    }

    if (goalDim.getDirection().isIncreasing()) {
      if (d != d) {
        return Long.MAX_VALUE;
      }
      return ((long) (0.5d + Math.ceil(d)));
    }

    if (d != d) {
      return Long.MIN_VALUE;
    }
    return ((long) (0.5d + Math.floor(d)));
  }

  /**
   * Compute the raw matrix for an instance run set
   *
   * @param data
   *          the data the instance runs
   * @return the raw matrix
   */
  private final IMatrix __compute(final IInstanceRuns data) {
    final _List list;

    if (this.m_goalDim.getDataType().isFloat()) {
      if (this.m_timeDim.getDataType().isInteger()) {
        list = new _LongTimeDoubleGoal(this.m_timeDim, this.m_goalIndex,
            this.m_goalValueDouble);
      } else {
        list = new _DoubleTimeDoubleGoal(this.m_timeDim, this.m_goalIndex,
            this.m_goalValueDouble);
      }
    } else {
      if (this.m_timeDim.getDataType().isInteger()) {
        list = new _LongTimeLongGoal(this.m_timeDim, this.m_goalIndex,
            this.m_goalValueLong);
      } else {
        list = new _DoubleTimeLongGoal(this.m_timeDim, this.m_goalIndex,
            this.m_goalValueLong);
      }
    }

    for (final IRun run : data.getData()) {
      list._addRun(run);
    }
    return list._toMatrix();
  }

  /**
   * Join a set of matrices
   *
   * @param matrices
   *          the set of matrices
   * @return the joined matrix
   */
  private static final IMatrix __join(final IMatrix[] matrices) {
    final MatrixBuilder builder;
    final MatrixIterator2D iterator;
    final StableSum sum;
    BasicNumber number;
    int size;

    size = 0;
    for (final IMatrix matrix : matrices) {
      size += matrix.m();
    }

    builder = new MatrixBuilder(size << 1);
    builder.setN(2);

    iterator = MatrixIterator2D.iterate(0, 1, matrices);
    sum = new StableSum();
    while (iterator.hasNext()) {
      number = iterator.next();
      if (number.isInteger()) {
        builder.append(number.longValue());
      } else {
        builder.append(number.doubleValue());
      }
      sum.reset();
      iterator.aggregateRow(0, sum);
      builder.append(Div.INSTANCE.computeAsDouble(sum.doubleValue(),
          matrices.length));
    }

    return builder.make();
  }

  /**
   * Compute the raw matrix for a given experiment
   *
   * @param data
   *          the experiment data
   * @return the raw matrix
   */
  private final IMatrix __compute(final IExperiment data) {
    final IMatrix[] raw;
    final ArrayListView<? extends IInstanceRuns> instanceRuns;
    final int size;
    int i;

    instanceRuns = data.getData();
    size = instanceRuns.size();
    raw = new IMatrix[size];
    for (i = size; (--i) >= 0;) {
      raw[i] = this.get(instanceRuns.get(i));
    }

    return _RawECDF.__join(raw);
  }

  /**
   * Compute the raw matrix for a given experiment set
   *
   * @param data
   *          the experiment set data
   * @return the raw matrix
   */
  private final IMatrix __compute(final IExperimentSet data) {
    final IMatrix[] raw;
    final ArrayListView<? extends IExperiment> experiments;
    final int size;
    int i;

    experiments = data.getData();
    size = experiments.size();
    raw = new IMatrix[size];
    for (i = size; (--i) >= 0;) {
      raw[i] = this.get(experiments.get(i));
    }

    return _RawECDF.__join(raw);
  }

  /** {@inheritDoc} */
  @Override
  protected final IMatrix compute(final IElementSet data) {
    if (data instanceof IInstanceRuns) {
      return this.__compute((IInstanceRuns) data);
    }
    if (data instanceof IExperiment) {
      return this.__compute((IExperiment) data);
    }
    if (data instanceof IExperimentSet) {
      return this.__compute((IExperimentSet) data);
    }

    throw new IllegalArgumentException(//
        "ECDF can only be computed over an IExperimentSet, IExperiment, or IInstanceRuns, but you provided " //$NON-NLS-1$
        + ((data != null)//
            ? (TextUtils.className(data.getClass()) + '.')//
                : "null."));//$NON-NLS-1$

  }
}
