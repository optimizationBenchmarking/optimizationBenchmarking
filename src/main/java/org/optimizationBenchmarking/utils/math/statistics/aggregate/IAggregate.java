package org.optimizationBenchmarking.utils.math.statistics.aggregate;

/** An aggregate collects numeric data */
public interface IAggregate {

  /**
   * append a byte
   *
   * @param v
   *          the byte
   */
  public abstract void append(final byte v);

  /**
   * append a short
   *
   * @param v
   *          the short
   */
  public abstract void append(final short v);

  /**
   * append an integer
   *
   * @param v
   *          the int
   */
  public abstract void append(final int v);

  /**
   * append a long
   *
   * @param v
   *          the long
   */
  public abstract void append(final long v);

  /**
   * append a float
   *
   * @param v
   *          the float
   */
  public abstract void append(final float v);

  /**
   * append a double
   *
   * @param v
   *          the double
   */
  public abstract void append(final double v);
}
