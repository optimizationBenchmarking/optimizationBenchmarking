package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.NumericalTypes;

/**
 * An aggregate with a single, scale result.
 */
public abstract class ScalarAggregate extends BasicNumber implements
    IAggregate {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** instantiate */
  public ScalarAggregate() {
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

  /**
   * Reset all internal state information. The {@link #getState()} becomes
   * {@link org.optimizationBenchmarking.utils.math.BasicNumber#STATE_EMPTY
   * empty}.
   */
  public abstract void reset();

  /** {@inheritDoc} */
  @Override
  public abstract void append(double value);

  /** {@inheritDoc} */
  @Override
  public final void append(final float value) {
    this.append((double) value);
  }

  /**
   * Append a number to this aggregate
   *
   * @param v
   *          the number
   */
  public final void append(final Number v) {
    final int type;
    if (v == null) {
      throw new IllegalArgumentException(//
          "Cannot add null number to aggregate " //$NON-NLS-1$
              + this.getClass().getSimpleName());
    }
    type = NumericalTypes.getTypes(v);
    if ((type & NumericalTypes.IS_BYTE) != 0) {
      this.append(v.byteValue());
    } else {
      if ((type & NumericalTypes.IS_SHORT) != 0) {
        this.append(v.shortValue());
      } else {
        if ((type & NumericalTypes.IS_INT) != 0) {
          this.append(v.intValue());
        } else {
          if ((type & NumericalTypes.IS_LONG) != 0) {
            this.append(v.longValue());
          } else {
            if ((type & NumericalTypes.IS_FLOAT) != 0) {
              this.append(v.floatValue());
            } else {
              this.append(v.doubleValue());
            }
          }
        }
      }
    }
  }
}
