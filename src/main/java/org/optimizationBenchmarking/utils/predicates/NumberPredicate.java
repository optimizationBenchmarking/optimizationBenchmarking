package org.optimizationBenchmarking.utils.predicates;

import org.optimizationBenchmarking.utils.hash.HashObject;

/**
 * A predicate defined on numbers.
 */
public abstract class NumberPredicate extends HashObject implements
    IPredicate<Number> {

  /** create the number predicate */
  protected NumberPredicate() {
    super();
  }

  /**
   * Check a byte
   * 
   * @param v
   *          the byte value
   * @return {@code true} if the predicate matches, {@code false} otherwise
   */
  public boolean check(final byte v) {
    return this.check((int) v);
  }

  /**
   * Check a short
   * 
   * @param v
   *          the short value
   * @return {@code true} if the predicate matches, {@code false} otherwise
   */
  public boolean check(final short v) {
    return this.check((int) v);
  }

  /**
   * Check an int
   * 
   * @param v
   *          the int value
   * @return {@code true} if the predicate matches, {@code false} otherwise
   */
  public boolean check(final int v) {
    return this.check((long) v);
  }

  /**
   * Check a long
   * 
   * @param v
   *          the long value
   * @return {@code true} if the predicate matches, {@code false} otherwise
   */
  public boolean check(final long v) {
    return this.check((double) v);
  }

  /**
   * Check a float
   * 
   * @param v
   *          the float value
   * @return {@code true} if the predicate matches, {@code false} otherwise
   */
  public boolean check(final float v) {
    return this.check((double) v);
  }

  /**
   * Check a double
   * 
   * @param v
   *          the double value
   * @return {@code true} if the predicate matches, {@code false} otherwise
   */
  public abstract boolean check(final double v);

  /** {@inheritDoc} */
  @Override
  public boolean check(final Number object) {
    if (object instanceof Long) {
      return this.check(object.longValue());
    }
    if (object instanceof Integer) {
      return this.check(object.intValue());
    }
    if (object instanceof Double) {
      return this.check(object.doubleValue());
    }
    if (object instanceof Float) {
      return this.check(object.floatValue());
    }
    if (object instanceof Short) {
      return this.check(object.shortValue());
    }
    if (object instanceof Byte) {
      return this.check(object.byteValue());
    }
    return this.check(object.doubleValue());
  }
}
