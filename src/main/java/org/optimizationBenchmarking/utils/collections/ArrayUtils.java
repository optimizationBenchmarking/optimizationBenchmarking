package org.optimizationBenchmarking.utils.collections;

/**
 * Some utilities for arrays.
 */
public final class ArrayUtils {

  /**
   * convert an array of {@code long} values to an array of {@code int}.
   *
   * @param array
   *          the array of {@code long}
   * @return the array of {@code int}
   */
  public static final int[] longsToInts(final long[] array) {
    final int[] res;
    int i;

    res = new int[array.length];
    i = 0;
    for (final long x : array) {
      res[i++] = ((int) x);
    }

    return res;
  }

  /**
   * convert an array of {@code long} values to an array of {@code short}.
   *
   * @param array
   *          the array of {@code long}
   * @return the array of {@code short}
   */
  public static final short[] longsToShorts(final long[] array) {
    final short[] res;
    int i;

    res = new short[array.length];
    i = 0;
    for (final long x : array) {
      res[i++] = ((short) x);
    }

    return res;
  }

  /**
   * convert an array of {@code long} values to an array of {@code byte}.
   *
   * @param array
   *          the array of {@code long}
   * @return the array of {@code byte}
   */
  public static final byte[] longsToBytes(final long[] array) {
    final byte[] res;
    int i;

    res = new byte[array.length];
    i = 0;
    for (final long x : array) {
      res[i++] = ((byte) x);
    }

    return res;
  }

  /**
   * convert an array of {@code long} values to an array of {@code double}.
   *
   * @param array
   *          the array of {@code long}
   * @return the array of {@code double}
   */
  public static final double[] longsToDoubles(final long[] array) {
    final double[] res;
    int i;

    res = new double[array.length];
    i = 0;
    for (final long x : array) {
      res[i++] = x;
    }

    return res;
  }

  /**
   * convert an array of {@code double} values to an array of {@code long}.
   *
   * @param array
   *          the array of {@code double}
   * @return the array of {@code long}
   */
  public static final long[] doublesToLongs(final double[] array) {
    final long[] res;
    int i;

    res = new long[array.length];
    i = 0;
    for (final double x : array) {
      res[i++] = ((long) x);
    }

    return res;
  }

  /**
   * convert an array of {@code double} values to an array of {@code float}
   * .
   *
   * @param array
   *          the array of {@code double}
   * @return the array of {@code float}
   */
  public static final float[] doublesToFloats(final double[] array) {
    final float[] res;
    int i;

    res = new float[array.length];
    i = 0;
    for (final double x : array) {
      res[i++] = ((float) x);
    }

    return res;
  }

  /**
   * convert an array of {@code double} values to an array of {@code int}.
   *
   * @param array
   *          the array of {@code double}
   * @return the array of {@code int}
   */
  public static final int[] doublesToInts(final double[] array) {
    final int[] res;
    int i;

    res = new int[array.length];
    i = 0;
    for (final double x : array) {
      res[i++] = ((int) x);
    }

    return res;
  }

  /**
   * convert an array of {@code double} values to an array of {@code short}
   * .
   *
   * @param array
   *          the array of {@code double}
   * @return the array of {@code short}
   */
  public static final short[] doublesToShorts(final double[] array) {
    final short[] res;
    int i;

    res = new short[array.length];
    i = 0;
    for (final double x : array) {
      res[i++] = ((short) x);
    }

    return res;
  }

  /**
   * convert an array of {@code double} values to an array of {@code byte}.
   *
   * @param array
   *          the array of {@code double}
   * @return the array of {@code byte}
   */
  public static final byte[] doublesToBytes(final double[] array) {
    final byte[] res;
    int i;

    res = new byte[array.length];
    i = 0;
    for (final double x : array) {
      res[i++] = ((byte) x);
    }

    return res;
  }

  /** create not allowed */
  private ArrayUtils() {
    throw new RuntimeException();
  }
}
