package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.BasicNumberWrapper;

/**
 * A base class for aggregates which are related to the arithmetic mean
 */
public abstract class MeanBasedAggregate extends _StatefulNumber {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  public MeanBasedAggregate() {
    super();
  }

  /**
   * Get a facade which allows accessing the sum of all encountered
   * elements
   *
   * @return a facade to access the sum
   */
  public abstract BasicNumberWrapper getSum();

  /**
   * Get a facade which allows accessing the minimum of all encountered
   * elements
   *
   * @return a facade to access the minimum
   */
  public abstract BasicNumberWrapper getMinimum();

  /**
   * Get a facade which allows accessing the maximum of all encountered
   * elements
   *
   * @return a facade to access the maximum
   */
  public abstract BasicNumberWrapper getMaximum();

  /**
   * Get a facade which allows accessing the arithmetic mean elements
   *
   * @return a facade to access the arithmetic mean
   */
  public abstract BasicNumberWrapper getArithmeticMean();

  /**
   * Get the count
   *
   * @return the count
   */
  public abstract long getCountValue();

  /**
   * Get the basic number representing the count
   *
   * @return the number representing the number of registered elements
   */
  public abstract BasicNumber getCount();
}
