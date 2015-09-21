package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IDataPoint;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.MatrixBuilder;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.QuantileAggregate;

/**
 * <p>
 * An attribute to compute fingerprints from instance run sets. A
 * fingerprint is a row matrix which contains non-normalized, raw
 * statistics about the runs of an experiment for a given benchmark
 * instance. Such data does not need to be humanly interpreted or
 * presented, it is solely intended to provide machine learning and/or
 * clustering approaches information with which different behaviors can be
 * distinguished. For this, it should have the following features:
 * </p>
 * <ul>
 * <li>If two optimization algorithms deliver final results of noticable
 * different quality on the same instance, they should also noticeable
 * differ in at least one value of the fingerprint.</li>
 * <li>If two optimization algorithms run at noticeable different speed on
 * the same instance, they should also noticeable differ in at least one
 * value of the fingerprint.</li>
 * <li>If two optimization algorithms have different knee points on the
 * instance, that should be noticeable.</li>
 * <li>If two instances lead to different knee points under the same
 * algorithm, this should be noticeable.</li>
 * </ul>
 * <p>
 * In order to achieve this, the finger print holds the following values:
 * </p>
 * <ol>
 * <li>The lower quartile (25% quantile),</li>
 * <li>the median (50% quantile), and</li>
 * <li>the upper quartile (75% quantile)</li>
 * </ol>
 * <p>
 * over each dimension for
 * </p>
 * <ol>
 * <li>the first recorded points of the runs (let's call the corresponding
 * median {@code median.first}),</li>
 * <li>the last recorded points of the runs (let's call the corresponding
 * median {@code median.last}),</li>
 * <li>for {@code (median.last-median.first)/4+median.first},
 * {@code (median.last-median.first)/3+median.first},
 * {@code 3(median.last-median.first)/4+median.first}, find the point
 * closest to that value, then aggregate the values of that point for each
 * other dimension</li>
 * </ol>
 * Currently, the computation is very inefficient, as I use three different
 * aggregates for the three quantiles, each of which will collect the data.
 * Once I have better implementations, I will improve on that. </p>
 */
final class _InstanceRunsFingerprint extends
    Attribute<IInstanceRuns, IMatrix> {

  /** the number of divisions */
  private static final int DIVISIONS = 3;

  /** the globally shared instance */
  static final _InstanceRunsFingerprint INSTANCE = new _InstanceRunsFingerprint();

  /** create */
  private _InstanceRunsFingerprint() {
    super(EAttributeType.TEMPORARILY_STORED);
  }

  /**
   * Append a data point to the aggregates
   *
   * @param point
   *          the data point
   * @param q25
   *          the 25% quantile aggregate
   * @param q50
   *          the 50% quantile aggregate (median)
   * @param q75
   *          the 75% quantile aggregate
   * @param dimIndex
   *          the dimension index
   * @param dimIsInteger
   *          is the dimension integer?
   */
  private static final void __append(final IDataPoint point,
      final QuantileAggregate q25, final QuantileAggregate q50,
      final QuantileAggregate q75, final int dimIndex,
      final boolean dimIsInteger) {
    final long longVal;
    final double doubleVal;

    if (dimIsInteger) {
      longVal = point.getLong(dimIndex);
      q25.append(longVal);
      q50.append(longVal);
      q75.append(longVal);
    } else {
      doubleVal = point.getDouble(dimIndex);
      q25.append(doubleVal);
      q50.append(doubleVal);
      q75.append(doubleVal);
    }
  }

  /**
   * Aggregate over data points with the specified value of the given
   * dimension and append it to the aggregates to the aggregates.
   *
   * @param runs
   *          the instance run set
   * @param dimensions
   *          the dimensions
   * @param value
   *          the target value
   * @param q25
   *          the 25% quantile aggregate
   * @param q50
   *          the 50% quantile aggregate (median)
   * @param q75
   *          the 75% quantile aggregate
   * @param dimIndex
   *          the index of the selector dimension
   * @param builder
   *          the matrix builder
   */
  private static final void __append(
      final ArrayListView<? extends IRun> runs,
      final ArrayListView<? extends IDimension> dimensions,
      final double value, final QuantileAggregate q25,
      final QuantileAggregate q50, final QuantileAggregate q75,
      final int dimIndex, final MatrixBuilder builder) {
    ArrayListView<? extends IDataPoint> raw;
    IDataPoint dp, dp0, dpe;
    int curIndex;
    boolean curIsInteger;

    for (final IDimension dim : dimensions) {
      curIndex = dim.getIndex();
      if (curIndex == dimIndex) {
        continue;
      }
      curIsInteger = dim.getDataType().isInteger();

      q25.reset();
      q50.reset();
      q75.reset();

      for (final IRun run : runs) {
        dp = run.find(dimIndex, value);
        if (dp != null) {
          _InstanceRunsFingerprint.__append(dp, q25, q50, q75, curIndex,
              curIsInteger);
          continue;
        }

        raw = run.getData();
        dp0 = raw.get(0);
        dpe = raw.get(raw.size() - 1);

        if (Math.abs(value - dp0.getDouble(dimIndex)) <= //
        Math.abs(dpe.getDouble(dimIndex) - value)) {
          _InstanceRunsFingerprint.__append(dp0, q25, q50, q75, curIndex,
              curIsInteger);
          continue;
        }

        _InstanceRunsFingerprint.__append(dpe, q25, q50, q75, curIndex,
            curIsInteger);
      }

      builder.append(q25);
      builder.append(q50);
      builder.append(q75);
    }
  }

  /**
   * Add the statistics of a particular dimension
   *
   * @param dim
   *          the dimension
   * @param dimensions
   *          the set of all dimensions
   * @param data
   *          the data
   * @param q25
   *          the aggregate to be used for the 25% quantile
   * @param q50
   *          the aggregate to be used for the 50% quantile (median)
   * @param q75
   *          the aggregate to be used for the 75% quantile
   * @param builder
   *          the builder
   */
  private static final void __dimension(final IDimension dim,
      final ArrayListView<? extends IDimension> dimensions,
      final IInstanceRuns data, final QuantileAggregate q25,
      final QuantileAggregate q50, final QuantileAggregate q75,
      final MatrixBuilder builder) {
    final int dimIndex;
    final boolean dimIsInteger;
    final double start, end, div;
    final ArrayListView<? extends IRun> runs;
    ArrayListView<? extends IDataPoint> runData;
    int slot;
    double value;

    dimIndex = dim.getIndex();
    dimIsInteger = dim.getDataType().isInteger();
    runs = data.getData();

    // determine the statistics about the starting point
    q25.reset();
    q50.reset();
    q75.reset();
    for (final IRun run : runs) {
      _InstanceRunsFingerprint.__append(run.getData().get(0), q25, q50,
          q75, dimIndex, dimIsInteger);
    }

    builder.append(q25);
    builder.append(q50);
    builder.append(q75);

    // remember median of start
    start = q50.doubleValue();

    // determine the statistics about the end point
    q25.reset();
    q50.reset();
    q75.reset();
    for (final IRun run : runs) {
      runData = run.getData();
      _InstanceRunsFingerprint.__append(runData.get(runData.size() - 1),
          q25, q50, q75, dimIndex, dimIsInteger);
    }

    builder.append(q25);
    builder.append(q50);
    builder.append(q75);

    // remember median of end points
    end = q50.doubleValue();

    div = (_InstanceRunsFingerprint.DIVISIONS + 1);
    for (slot = 1; slot <= _InstanceRunsFingerprint.DIVISIONS; slot++) {
      value = ((((end - start) * slot) / div) + start);
      _InstanceRunsFingerprint.__append(runs, dimensions, value, q25, q50,
          q75, dimIndex, builder);
    }
  }

  /** Compute the attribute */
  @Override
  protected final IMatrix compute(final IInstanceRuns data) {
    final MatrixBuilder builder;
    final QuantileAggregate q25, q50, q75;
    final ArrayListView<? extends IDimension> dimensions;

    builder = new MatrixBuilder();
    builder.setM(1);

    q25 = new QuantileAggregate(0.25d);
    q50 = new QuantileAggregate(0.5d);
    q75 = new QuantileAggregate(0.75d);
    dimensions = data.getOwner().getOwner().getDimensions().getData();
    for (final IDimension dim : dimensions) {
      _InstanceRunsFingerprint.__dimension(dim, dimensions, data, q25,
          q50, q75, builder);
    }

    return builder.make();
  }

}
