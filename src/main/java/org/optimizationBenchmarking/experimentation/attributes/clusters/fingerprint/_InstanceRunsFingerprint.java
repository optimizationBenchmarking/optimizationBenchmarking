package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IDataPoint;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.fitting.impl.ls.LSFitter;
import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix1D;
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

  /** the globally shared instance */
  static final _InstanceRunsFingerprint INSTANCE = new _InstanceRunsFingerprint();

  /** create */
  private _InstanceRunsFingerprint() {
    super(EAttributeType.TEMPORARILY_STORED);
  }

  /**
   * Append the parameters of a curve fitted to the given data
   *
   * @param dimensions
   *          the dimensions
   * @param data
   *          the data
   * @param builder
   *          the builder
   */
  private static final void __appendCurves(final IInstanceRuns data,
      final ArrayListView<? extends IDimension> dimensions,
      final MatrixBuilder builder) {
    final int size;
    final ParametricUnaryFunction poly, decay;
    final double[] rawPoints;
    final DoubleMatrix1D rawMatrix;
    final QuantileAggregate startA, startB, endA, endB;
    IDimension dimension;
    ArrayListView<? extends IRun> runs;
    ArrayListView<? extends IDataPoint> points;
    double[] fittingResult;
    int dimA, dimB, useDimA, useDimB, totalPoints, index;
    boolean isTimeA, isTimeB, useIsTimeA, isIncreasingA, useIsIncreasingA, isIncreasingB;
    ParametricUnaryFunction func;
    double minA, minB, maxA, maxB, scaleA, scaleB, temp;
    IDataPoint dpoint;

    poly = new PolynomialModel();
    decay = new DecayModel();

    startA = new QuantileAggregate(0.5d);
    startB = new QuantileAggregate(0.5d);
    endA = new QuantileAggregate(0.5d);
    endB = new QuantileAggregate(0.5d);

    // We allocate a re-usable data store.
    totalPoints = 0;
    for (final IRun run : data.getData()) {
      totalPoints += run.getData().size();
    }
    rawPoints = new double[totalPoints << 1];
    rawMatrix = new DoubleMatrix1D(rawPoints, totalPoints, 2);

    size = dimensions.size();
    minA = Double.NaN;
    for (dimA = 0; dimA < size; dimA++) {
      dimension = dimensions.get(dimA);
      isTimeA = dimension.getDimensionType().isTimeMeasure();
      isIncreasingA = dimension.getDirection().isIncreasing();

      for (dimB = dimA; (++dimB) < size;) {
        dimension = dimensions.get(dimB);
        isTimeB = dimension.getDimensionType().isTimeMeasure();
        isIncreasingB = dimension.getDirection().isIncreasing();

        // we prefer time-quality over quality-time
        if (isTimeB && (!isTimeA)) {
          useIsTimeA = true;
          isTimeB = false;
          useDimA = dimB;
          useDimB = dimA;
          useIsIncreasingA = isIncreasingB;
          isIncreasingB = isIncreasingA;
        } else {
          useIsTimeA = isTimeA;
          useDimA = dimA;
          useDimB = dimB;
          useIsIncreasingA = isIncreasingA;
        }

        runs = data.getData();

        startA.reset();
        startB.reset();
        endA.reset();
        endB.reset();

        for (final IRun run : runs) {
          points = run.getData();
          dpoint = points.get(0);
          startA.append(dpoint.getDouble(useDimA));
          startB.append(dpoint.getDouble(useDimB));
          dpoint = points.get(points.size() - 1);
          endA.append(dpoint.getDouble(useDimA));
          endB.append(dpoint.getDouble(useDimB));
        }

        minA = startA.doubleValue();
        maxA = endA.doubleValue();
        if (!useIsIncreasingA) {
          temp = minA;
          minA = maxA;
          maxA = temp;
        }
        scaleA = (maxA - minA);

        minB = startB.doubleValue();
        maxB = endB.doubleValue();
        if (!useIsIncreasingA) {
          temp = minB;
          minB = maxB;
          maxB = temp;
        }
        scaleB = (maxB - minB);

        index = 0;
        for (final IRun run : runs) {
          for (final IDataPoint point : run.getData()) {

            temp = point.getDouble(useDimA);
            rawPoints[index++] = isIncreasingA //
            ? (((temp - minA) / scaleA))//
                : (((maxA - temp) / scaleA));//

            temp = point.getDouble(useDimB);
            rawPoints[index++] = isIncreasingB //
            ? (((maxB - temp) / scaleB))//
                : (((temp - minB) / scaleB));//
          }
        }

        if ((useIsTimeA && (!(isTimeB)))) {
          // We model the relationship between a time and an objective
          // value
          // dimension as exponential decay model.
          func = decay;
        } else {
          // We model the relationship between two time dimensions or two
          // objective value dimensions as polynomial of degree 3.
          func = poly;
        }

        // get all the points
        fittingResult = LSFitter.getInstance().use()
            .setFunctionToFit(func).setPoints(rawMatrix)
            .setMinCriticalPoints(3 + (3 * runs.size())).create().call()
            .getFittedParameters();

        builder.append(fittingResult);
        builder.append(minA);
        builder.append(maxA);
        builder.append(minB);
        builder.append(maxB);
      }
    }
  }

  /** Compute the attribute */
  @Override
  protected final IMatrix compute(final IInstanceRuns data) {
    final MatrixBuilder builder;
    final ArrayListView<? extends IDimension> dimensions;

    builder = new MatrixBuilder();
    builder.setM(1);

    dimensions = data.getOwner().getOwner().getDimensions().getData();
    _InstanceRunsFingerprint.__appendCurves(data, dimensions, builder);
    return builder.make();
  }
}