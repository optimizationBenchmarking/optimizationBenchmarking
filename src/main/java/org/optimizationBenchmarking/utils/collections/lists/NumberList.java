package org.optimizationBenchmarking.utils.collections.lists;

/**
 * A list containing {@link java.lang.Number number}s. Implementations of
 * this abstract base class may actually wrap around arrays of primitive
 * numerical types and generate instances of {@link java.lang.Number} on
 * the fly when accesses. They may thus violate some basic invariants of
 * the {@link java.util.List list interface}, such as that two subsequent
 * calls to {@link java.util.List#get(int) get} with the same index
 * parameter will return the same object.
 */
public abstract class NumberList extends BasicList<Number> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  protected NumberList() {
    super();
  }

  /**
   * Get the value at index {@code index}, as {@code double}.
   *
   * @param index
   *          the index
   * @return the value at that index, as {@code double}
   */
  public abstract double getDouble(final int index);

  /**
   * Get the value at index {@code index}, as {@code float}.
   *
   * @param index
   *          the index
   * @return the value at that index, as {@code float}
   */
  public float getFloat(final int index) {
    return ((float) (this.getDouble(index)));
  }

  /**
   * Get the value at index {@code index}, as {@code long}.
   *
   * @param index
   *          the index
   * @return the value at that index, as {@code long}
   */
  public long getLong(final int index) {
    return ((long) (this.getDouble(index)));
  }

  /**
   * Get the value at index {@code index}, as {@code int}.
   *
   * @param index
   *          the index
   * @return the value at that index, as {@code int}
   */
  public int getInt(final int index) {
    return ((int) (this.getLong(index)));
  }

  /**
   * Get the value at index {@code index}, as {@code short}.
   *
   * @param index
   *          the index
   * @return the value at that index, as {@code short}
   */
  public short getShort(final int index) {
    return ((short) (this.getLong(index)));
  }

  /**
   * Get the value at index {@code index}, as {@code byte}.
   *
   * @param index
   *          the index
   * @return the value at that index, as {@code byte}
   */
  public byte getByte(final int index) {
    return ((byte) (this.getLong(index)));
  }

  /**
   * Get the value at the given {@code dimension}
   *
   * @param dimension
   *          the dimension
   * @return the number
   */
  @Override
  public Number get(final int dimension) {
    return Double.valueOf(this.getDouble(dimension));
  }

  /**
   * store the data of this point into the destination {@code double} array
   *
   * @param dest
   *          the destination {@code double} array
   * @param destStart
   *          the starting index where the copying should begin
   */
  public void toArray(final double[] dest, final int destStart) {
    int i, e;

    i = this.size();
    e = (destStart + i);
    for (; (--i) >= 0;) {
      dest[--e] = this.getDouble(i);
    }
  }

  /**
   * store the data of this point into the destination {@code long} array
   *
   * @param dest
   *          the destination {@code long} array
   * @param destStart
   *          the starting index where the copying should begin
   */
  public void toArray(final long[] dest, final int destStart) {
    int i, e;

    i = this.size();
    e = (destStart + i);
    for (; (--i) >= 0;) {
      dest[--e] = this.getLong(i);
    }
  }

  /**
   * store the data of this point into the destination {@code int} array
   *
   * @param dest
   *          the destination {@code int} array
   * @param destStart
   *          the starting index where the copying should begin
   */
  public void toArray(final int[] dest, final int destStart) {
    int i, e;

    i = this.size();
    e = (destStart + i);
    for (; (--i) >= 0;) {
      dest[--e] = this.getInt(i);
    }
  }

}
