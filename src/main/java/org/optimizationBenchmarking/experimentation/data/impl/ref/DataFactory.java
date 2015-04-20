package org.optimizationBenchmarking.experimentation.data.impl.ref;

import java.util.Collection;

import org.optimizationBenchmarking.utils.parsers.Parser;

/**
 * A parser for data points and factory for higher-level data structures.
 */
public abstract class DataFactory extends Parser<DataPoint> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  protected DataFactory() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public abstract DataPoint parseString(final String s);

  /** {@inheritDoc} */
  @Override
  public abstract DataPoint parseObject(final Object o);

  /**
   * Create a data point from an array of number instances
   * 
   * @param numbers
   *          the numbers
   * @return the data point
   */
  public abstract DataPoint parseNumbers(final Number... numbers);

  /**
   * Create a run from a set of data points
   * 
   * @param instance
   *          the benchmark instance to which the run should belong
   * @param points
   *          the points
   * @return the run
   */
  public abstract Run createRun(final Instance instance,
      final Collection<DataPoint> points);
}
