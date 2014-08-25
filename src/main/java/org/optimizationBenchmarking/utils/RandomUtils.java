package org.optimizationBenchmarking.utils;

import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * This class provides utility functions which translate 64bit {@code long}
 * values into objects. These utilities are mainly designed to provide
 * pseudo-random objects. No guarantee is given regarding how random these
 * objects really are.
 */
public final class RandomUtils {

  /** the default charset */
  public static final String DEFAULT_CHARSET = "abcdefghijklmnopqrstuvwxyz"; //$NON-NLS-1$
  /** the unique choices */
  private static final int[] UNIQUE = { 1, 7, 8 };

  /**
   * Create a string which corresponds to a given long in an approximate
   * 1:1 mapping. The purpose of this method is to allow you to create a
   * sequence of unique strings. See method
   * {@link #longToObject(long, boolean)} to create a sequence of unique
   * objects.
   *
   * @param value
   *          the long value
   * @param charset
   *          the characters to use
   * @return the string
   */
  public static final String longToString(final String charset,
      final long value) {
    long val;

    val = (value + 1L);
    do {
      val *= 8223372036854775893L;
    } while (val < 0L);
    return RandomUtils.__longToStringRaw(//
        (((charset != null) && (charset.length() > 0)) ? charset
            : RandomUtils.DEFAULT_CHARSET), val);
  }

  /**
   * create a string which exactly corresponds to a given long
   *
   * @param value
   *          the long value
   * @param charset
   *          the characters to use
   * @return the string
   */
  private static final String __longToStringRaw(final String charset,
      final long value) {
    final MemoryTextOutput sb;
    final int len;
    long val;

    len = charset.length();
    sb = new MemoryTextOutput();
    val = value;

    do {
      sb.append(charset.charAt((int) (val % len)));
      val /= len;
    } while (val > 0L);

    return sb.toString();
  }

  /**
   * <p>
   * Transform a long value to a basic java object. The returned object is
   * is an instance of either {@link java.lang.String},
   * {@link java.lang.Character}, {@link java.lang.Boolean},
   * {@link java.lang.Byte}, {@link java.lang.Short},
   * {@link java.lang.Integer}, {@link java.lang.Long},
   * {@link java.lang.Float}, or {@link java.lang.Double}. There is an
   * (approximate) n:1 pseudo-random mapping between the contents of the
   * object and {@code value}.
   * </p>
   * <p>
   * If {@code maxUnique==true}, then there is an approximately 1:1
   * pseudo-random mapping between the contents of the object and
   * {@code value} . For this purpose, only instances of
   * {@link java.lang.String}, {@link java.lang.Long}, and
   * {@link java.lang.Double} are returned. Such objects may be considered
   * to be sufficiently unique for testing purposes and can, e.g., serve as
   * keys in a map. The objects generated with {@code maxUnique==false}, on
   * the other hand, are not necessarily unique but from a wider type
   * spectrum. They make good values of a map.
   * </p>
   * <p>
   * All of the objects returned are {@link java.io.Serializable
   * serializable} but not {@link java.lang.Cloneable}.
   * </p>
   *
   * @param value
   *          the value
   * @param maxUnique
   *          try to create unique objects: only objects are created that
   *          can use at least 40 bits of data
   * @return the object
   */
  @SuppressWarnings("fallthrough")
  public static final Object longToObject(final long value,
      final boolean maxUnique) {
    long use;
    int type;
    float f;
    double d;
    char ch;

    use = (value + 1L);
    do {
      use *= 7777777777777777793L;
    } while (use < 0L);

    if (maxUnique) {
      type = RandomUtils.UNIQUE[((int) (use % RandomUtils.UNIQUE.length))];
      use /= RandomUtils.UNIQUE.length;
    } else {
      type = ((int) (use % 9L));
      use /= 9L;
    }
    use *= 5555555555555555621L;

    switch (type) {
      case 0: {
        f = Float.intBitsToFloat((int) (use & 0xffffffffl));
        if (!(Float.isInfinite(f) || Float.isNaN(f))) {
          return Float.valueOf(f);
        }
      }
      case 1: {
        d = Double.longBitsToDouble(use);
        if (!(Double.isInfinite(d) || Double.isNaN(d))) {
          return Double.valueOf(d);
        }
      }
      case 2: {
        ch = ((char) (use & 0xffffl));
        if (Character.isDefined(ch)
            && Character.isBmpCodePoint(ch)
            && (!(Character.isSupplementaryCodePoint(ch) || Character
                .isSurrogate(ch)))) {
          return Character.valueOf(ch);
        }
      }
      case 3: {
        return Boolean.valueOf((use & 1L) != 0L);
      }
      case 4: {
        return Byte.valueOf((byte) (use & 0xffl));
      }
      case 5: {
        return Short.valueOf((short) (use & 0xffffl));
      }
      case 6: {
        return Integer.valueOf((int) (use & 0xffffffffl));
      }
      case 7: {
        return Long.valueOf(use);
      }
      default: {
        while (use < 0L) {
          use *= 5555555555555555621L;
        }
        return RandomUtils.__longToStringRaw(RandomUtils.DEFAULT_CHARSET,
            use);
      }
    }
  }

}
