package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.BasicNumber;

/**
 * An aggregate with a single, scale result.
 */
public abstract class ScalarAggregate extends _StatefulNumber implements
    IAggregate {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** instantiate */
  protected ScalarAggregate() {
    super();
  }

  /**
   * Visit a given {@code long}. By default, this method forwards to
   * {@link #append(double)}, but it may be overridden to do some special
   * computation for {@code long} values.
   * 
   * @param value
   *          the value to visit
   */
  @Override
  public abstract void append(final long value);

  /**
   * Visit a given {@code int}. By default, this method forwards to
   * {@link #append(long)}, but it may be overridden to do some special
   * computation for {@code int} values.
   * 
   * @param value
   *          the value to visit
   */
  @Override
  public final void append(final int value) {
    this.append((long) value);
  }

  /**
   * Visit a given {@code short}. By default, this method forwards to
   * {@link #append(long)}, but it may be overridden to do some special
   * computation for {@code short} values.
   * 
   * @param value
   *          the value to visit
   */
  @Override
  public final void append(final short value) {
    this.append((long) value);
  }

  /**
   * Visit a given {@code byte}. By default, this method forwards to
   * {@link #append(long)}, but it may be overridden to do some special
   * computation for {@code byte} values.
   * 
   * @param value
   *          the value to visit
   */
  @Override
  public final void append(final byte value) {
    this.append((long) value);
  }

  /** reset all internal state information */
  @Override
  public void reset() {
    this.m_state = BasicNumber.STATE_EMPTY;
  }

  /** {@inheritDoc} */
  @Override
  public abstract void append(double value);

  /** {@inheritDoc} */
  @Override
  public final void append(final float value) {
    this.append((double) value);
  }
}
