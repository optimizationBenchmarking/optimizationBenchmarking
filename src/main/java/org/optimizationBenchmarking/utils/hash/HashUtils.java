package org.optimizationBenchmarking.utils.hash;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.EmptyUtils;

/**
 * A utilities class for computing hash codes
 */
public final class HashUtils {

  /**
   * Combine two hash codes. This operator returns a result which depends
   * on both hash codes {@code a} and {@code b}. This result depends on the
   * order of the parameters, i.e., it (usually) is different for
   * {@code (a, b)} and {@code (b, a)}.
   *
   * @param a
   *          the first hash code
   * @param b
   *          the second hash code
   * @return the combined hash code
   */
  public static final int combineHashes(final int a, final int b) {
    return ((31 * a) + b);
  }

  /**
   * Combine two hash codes. This operator returns a result which depends
   * on both hash codes {@code a} and {@code b}. This result does not
   * depend on the order of the parameters, i.e., it is the same for
   * {@code (a, b)} and {@code (b, a)}.
   *
   * @param a
   *          the first hash code
   * @param b
   *          the second hash code
   * @return the combined hash code
   */
  public static final int combineHashesUnsorted(final int a, final int b) {
    return (a + b);
  }

  /**
   * Compute the hash code of a byte.
   *
   * @param b
   *          the data
   * @return the hash
   */
  public static final int hashCode(final byte b) {
    return HashUtils.hashCode((int) b);
  }

  /**
   * Compute the hash code of a {@code short}
   *
   * @param b
   *          the data
   * @return the hash
   */
  public static final int hashCode(final short b) {
    return HashUtils.hashCode((int) b);
  }

  /**
   * Compute the hash code of an integer.
   *
   * @param b
   *          the data
   * @return the hash
   */
  public static final int hashCode(final int b) {
    return b;
  }

  /**
   * Compute the hash code of a float.
   *
   * @param b
   *          the data
   * @return the hash
   */
  public static final int hashCode(final float b) {
    return HashUtils.hashCode(Float.floatToIntBits(b));
  }

  /**
   * Compute the hash code of a long.
   *
   * @param b
   *          the data
   * @return the hash
   */
  public static final int hashCode(final long b) {
    return (int) (b ^ (b >>> 32));
  }

  /**
   * Compute the hash code of a double.
   *
   * @param b
   *          the data
   * @return the hash
   */
  public static final int hashCode(final double b) {
    return HashUtils.hashCode(Double.doubleToLongBits(b));
  }

  /**
   * Compute the hash code of an object.
   *
   * @param b
   *          the data
   * @return the hash
   */
  public static final int hashCode(final Object b) {
    if (b == null) {
      return 0;
    }

    if (b instanceof byte[]) {
      return Arrays.hashCode((byte[]) b);
    }
    if (b instanceof short[]) {
      return Arrays.hashCode((short[]) b);
    }
    if (b instanceof int[]) {
      return Arrays.hashCode((int[]) b);
    }
    if (b instanceof long[]) {
      return Arrays.hashCode((long[]) b);
    }
    if (b instanceof boolean[]) {
      return Arrays.hashCode((boolean[]) b);
    }
    if (b instanceof char[]) {
      return Arrays.hashCode((char[]) b);
    }
    if (b instanceof float[]) {
      return Arrays.hashCode((float[]) b);
    }
    if (b instanceof double[]) {
      return Arrays.hashCode((double[]) b);
    }

    return b.hashCode();
  }

  /**
   * Compute a &quot;deep&quot; hash code of an object. If the object is an
   * array, the hash code of the array will be computed. Different from
   * {@link java.util.Arrays#deepHashCode(Object[])}, this method allows
   * for objects to be object arrays containing themselves.
   *
   * @param o
   *          the object
   * @return the hash code of the object
   */
  public static final int deepHashCode(final Object o) {
    return HashUtils.__deepHashCode(o, EmptyUtils.EMPTY_OBJECTS, 0);
  }

  /**
   * The internal method to compute a &quot;deep&quot; hash code of an
   * object. If the object is an array, the hash code of the array will be
   * computed.
   *
   * @param o
   *          the object
   * @param done
   *          the objects already seen
   * @param len
   *          the {@code done} array length
   * @return the hash code of the object
   */
  private static final int __deepHashCode(final Object o,
      final Object[] done, final int len) {
    int i, ul;
    Object[] newDone;

    if (o == null) {
      return 0;
    }

    if (o instanceof Object[]) {

      for (i = len; (--i) >= 0;) {
        if (done[i] == o) {
          return (((i + 1847) * 3559) ^ (i - 2851));
        }
      }

      if (len >= done.length) {
        newDone = new Object[(len + 1) << 1];
        System.arraycopy(done, 0, newDone, 0, len);
      } else {
        newDone = done;
      }
      newDone[len] = o;
      ul = (len + 1);

      i = 0;
      for (final Object z : ((Object[]) o)) {
        i = HashUtils.combineHashes(i,
            HashUtils.__deepHashCode(z, newDone, ul));
      }

      return i;
    }

    return HashUtils.hashCode(o);
  }

  /**
   * Compute the hash code of a character.
   *
   * @param b
   *          the data
   * @return the hash
   */
  public static final int hashCode(final char b) {
    return HashUtils.hashCode((int) b);
  }

  /**
   * Compute the hash code of a boolean value.
   *
   * @param b
   *          the data
   * @return the hash
   */
  public static final int hashCode(final boolean b) {
    return (b ? 1231 : 1237);
  }

  /**
   * Get a key that can uniquely identify the given object in a set or hash
   * map. This key is guaranteed to not be {@code null}. If {@code o} is an
   * array, the returned key will identify this array <em>and</em> any
   * equal array.
   *
   * @param o
   *          the object
   * @return the key
   */
  public static final Object hashKey(final Object o) {
    if (o == null) {
      return _NullKey.INSTANCE;
    }
    if (o.getClass().isArray()) {
      return new _ArrayKey(o);
    }
    return o;
  }
}
