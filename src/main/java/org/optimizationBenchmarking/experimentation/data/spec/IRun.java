package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/**
 * A run is, basically, a list of data points and a matrix.
 */
public interface IRun extends IElementSet, IMatrix {

  /**
   * Get the owning instance runs set.
   * 
   * @return the owning instance runs set.
   */
  @Override
  public abstract IInstanceRuns getOwner();

  /**
   * Get the data points of this run
   * 
   * @return the data points of this run
   */
  @Override
  public abstract ArrayListView<? extends IDataPoint> getData();

  /**
   * Find the the data point in this run whose column/dimension
   * {@code column} fulfills a given condition:
   * <ul>
   * <li>If dimension {@code column} represents a
   * {@link org.optimizationBenchmarking.experimentation.data.spec.EDimensionType#isSolutionQualityMeasure()
   * solution quality} measure, find the earliest data point in this run
   * whose value in dimension {@code column} is at least as good (i.e., the
   * same or better) than {@code value}. If the run does not contain a
   * point which has a quality (in dimension {@code column}) which is as
   * same as good or better than {@code value}, return {@code null}.</li>
   * <li>If dimension {@code column} represents a
   * {@link org.optimizationBenchmarking.experimentation.data.spec.EDimensionType#isTimeMeasure()
   * time measure}, then find either the (earliest) data point in this run
   * exactly at time {@code value} or the last data point in this run which
   * is earlier than {@code value} (all according to time dimension
   * {@code column} ). If no such point exists, i.e., if all points in this
   * run happen after time {@code value}, return {@code null}.</li>
   * </ul>
   * If either case, if there are any data points with value {@code value}
   * in dimension/column {@code column} in this run, then the
   * first/earliest one of them will be returned.
   * 
   * @param column
   *          the column
   * @param value
   *          the value
   * @return the point
   */
  public abstract IDataPoint find(final int column, final double value);

  /**
   * Find the the data point in this run whose column/dimension
   * {@code column} fulfills a given condition:
   * <ul>
   * <li>If dimension {@code column} represents a
   * {@link org.optimizationBenchmarking.experimentation.data.spec.EDimensionType#isSolutionQualityMeasure()
   * solution quality} measure, find the earliest data point in this run
   * whose value in dimension {@code column} is at least as good (i.e., the
   * same or better) than {@code value}. If the run does not contain a
   * point which has a quality (in dimension {@code column}) which is as
   * same as good or better than {@code value}, return {@code null}.</li>
   * <li>If dimension {@code column} represents a
   * {@link org.optimizationBenchmarking.experimentation.data.spec.EDimensionType#isTimeMeasure()
   * time measure}, then find either the (earliest) data point in this run
   * exactly at time {@code value} or the last data point in this run which
   * is earlier than {@code value} (all according to time dimension
   * {@code column} ). If no such point exists, i.e., if all points in this
   * run happen after time {@code value}, return {@code null}.</li>
   * </ul>
   * 
   * @param column
   *          the column
   * @param value
   *          the value
   * @return the point
   */
  public abstract IDataPoint find(final int column, final long value);

}
