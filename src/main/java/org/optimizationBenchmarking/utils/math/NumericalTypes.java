package org.optimizationBenchmarking.utils.math;

import org.optimizationBenchmarking.utils.error.ErrorUtils;

/**
 * This utility class allows us for checking the compatibility of numerical
 * types. Its methods surely leave much room for improvement, especially in
 * terms of speed, but for now they will do.
 */
public final class NumericalTypes {

  /**
   * the number can be represented as {@code byte} without loss of
   * precision
   */
  public static final int IS_BYTE = 1;
  /**
   * the number can be represented as {@code short} without loss of
   * precision
   */
  public static final int IS_SHORT = (NumericalTypes.IS_BYTE << 1);
  /**
   * the number can be represented as {@code int} without loss of precision
   */
  public static final int IS_INT = (NumericalTypes.IS_SHORT << 1);
  /**
   * the number can be represented as {@code long} without loss of
   * precision
   */
  public static final int IS_LONG = (NumericalTypes.IS_INT << 1);
  /**
   * the number can be represented as {@code float} without loss of
   * precision
   */
  public static final int IS_FLOAT = (NumericalTypes.IS_LONG << 1);
  /**
   * the number can be represented as {@code double} without loss of
   * precision
   */
  public static final int IS_DOUBLE = (NumericalTypes.IS_FLOAT << 1);

  /**
   * the smallest {@code int}eger value which fits into a {@code float}
   * without loss of precision: {@value}
   */
  public static final int MIN_FLOAT_INT = -16777216;
  /**
   * the largest {@code int}eger value which fits into a {@code float}
   * without loss of precision: {@value}
   */
  public static final int MAX_FLOAT_INT = 16777216;

  /**
   * the smallest {@code long} value which fits into a {@code double}
   * without loss of precision: {@value}
   */
  public static final long MIN_DOUBLE_LONG = -9007199254740992L;
  /**
   * the largest {@code long} value which fits into a {@code double}
   * without loss of precision: {@value}
   */
  public static final long MAX_DOUBLE_LONG = 9007199254740992L;

  /**
   * Get the possible types of a given number
   * 
   * @param number
   *          the number
   * @return the types
   */
  public static final int getTypes(final Number number) {
    BasicNumber base;
    int res;

    if (number instanceof Byte) {
      return NumericalTypes.getTypes((Byte) number);
    }
    if (number instanceof Short) {
      return NumericalTypes.getTypes((Short) number);
    }
    if (number instanceof Integer) {
      return NumericalTypes.getTypes((Integer) number);
    }
    if (number instanceof Long) {
      return NumericalTypes.getTypes((Long) number);
    }
    if (number instanceof Float) {
      return NumericalTypes.getTypes((Float) number);
    }
    if (number instanceof Double) {
      return NumericalTypes.getTypes((Double) number);
    }

    if (number instanceof BasicNumber) {
      res = 0;
      base = ((BasicNumber) number);
      if (base.isInteger()) {
        res |= NumericalTypes.IS_LONG;
      }
      if (base.isReal()) {
        res |= NumericalTypes.IS_DOUBLE;
      }
    } else {
      res = (NumericalTypes.getTypes(number.longValue()) & NumericalTypes
          .getTypes(number.doubleValue()));
    }
    return ((res != 0) ? res : NumericalTypes.IS_DOUBLE);
  }

  /**
   * Get the possible types of a given {@link java.lang.Byte byte} number
   * 
   * @param number
   *          the {@link java.lang.Byte byte} number
   * @return the types
   */
  public static final int getTypes(final Byte number) {
    return (NumericalTypes.IS_BYTE | NumericalTypes.IS_SHORT
        | NumericalTypes.IS_INT | NumericalTypes.IS_LONG
        | NumericalTypes.IS_FLOAT | NumericalTypes.IS_DOUBLE);
  }

  /**
   * Get the possible types of a given {@code byte} number
   * 
   * @param number
   *          the {@code byte} number
   * @return the types
   */
  public static final int getTypes(final byte number) {
    return (NumericalTypes.IS_BYTE | NumericalTypes.IS_SHORT
        | NumericalTypes.IS_INT | NumericalTypes.IS_LONG
        | NumericalTypes.IS_FLOAT | NumericalTypes.IS_DOUBLE);
  }

  /**
   * Get the possible types of a given {@link java.lang.Short short} number
   * 
   * @param number
   *          the {@link java.lang.Short short} number
   * @return the types
   */
  public static final int getTypes(final Short number) {
    return NumericalTypes.getTypes(number.shortValue());
  }

  /**
   * Get the possible types of a given {@code short} number
   * 
   * @param number
   *          the {@code short} number
   * @return the types
   */
  public static final int getTypes(final short number) {
    if ((number < Byte.MIN_VALUE) || (number > Byte.MAX_VALUE)) {
      return (NumericalTypes.IS_SHORT | NumericalTypes.IS_INT
          | NumericalTypes.IS_LONG | NumericalTypes.IS_FLOAT | NumericalTypes.IS_DOUBLE);
    }
    return (NumericalTypes.IS_BYTE | NumericalTypes.IS_SHORT
        | NumericalTypes.IS_INT | NumericalTypes.IS_LONG
        | NumericalTypes.IS_FLOAT | NumericalTypes.IS_DOUBLE);
  }

  /**
   * Get the possible types of a given {@link java.lang.Integer int} number
   * 
   * @param number
   *          the {@link java.lang.Integer int} number
   * @return the types
   */
  public static final int getTypes(final Integer number) {
    return NumericalTypes.getTypes(number.intValue());
  }

  /**
   * Get the possible types of a given {@code int} number
   * 
   * @param number
   *          the {@code int} number
   * @return the types
   */
  public static final int getTypes(final int number) {
    int res;

    res = (NumericalTypes.IS_INT | NumericalTypes.IS_LONG | NumericalTypes.IS_DOUBLE);
    if ((number >= NumericalTypes.MIN_FLOAT_INT)
        && (number <= NumericalTypes.MAX_FLOAT_INT)) {
      res |= NumericalTypes.IS_FLOAT;
      if ((number >= Short.MIN_VALUE) && (number <= Short.MAX_VALUE)) {
        res |= NumericalTypes.IS_SHORT | NumericalTypes.IS_FLOAT;
        if ((number >= Byte.MIN_VALUE) && (number <= Byte.MAX_VALUE)) {
          res |= NumericalTypes.IS_BYTE;
        }
      }
    }

    return res;
  }

  /**
   * Get the possible types of a given {@link java.lang.Long long} number
   * 
   * @param number
   *          the {@link java.lang.Long long} number
   * @return the types
   */
  public static final int getTypes(final Long number) {
    return NumericalTypes.getTypes(number.longValue());
  }

  /**
   * Get the possible types of a given {@code long} number
   * 
   * @param number
   *          the {@code long} number
   * @return the types
   */
  public static final int getTypes(final long number) {
    int res;

    res = NumericalTypes.IS_LONG;
    if ((number >= NumericalTypes.MIN_DOUBLE_LONG)
        && (number <= NumericalTypes.MAX_DOUBLE_LONG)) {
      res |= NumericalTypes.IS_DOUBLE;
      if ((number >= Integer.MIN_VALUE) && (number <= Integer.MAX_VALUE)) {
        res |= NumericalTypes.IS_INT;
        if ((number >= NumericalTypes.MIN_FLOAT_INT)
            && (number <= NumericalTypes.MAX_FLOAT_INT)) {
          res |= NumericalTypes.IS_FLOAT;
          if ((number >= Short.MIN_VALUE) && (number <= Short.MAX_VALUE)) {
            res |= NumericalTypes.IS_SHORT | NumericalTypes.IS_FLOAT;
            if ((number >= Byte.MIN_VALUE) && (number <= Byte.MAX_VALUE)) {
              res |= NumericalTypes.IS_BYTE;
            }
          }
        }
      }
    }

    return res;
  }

  /**
   * Get the possible types of a given {@link java.lang.Float float} number
   * 
   * @param number
   *          the {@link java.lang.Float float} number
   * @return the types
   */
  public static final int getTypes(final Float number) {
    return NumericalTypes.getTypes(number.floatValue());
  }

  /**
   * Get the possible types of a given {@code float} number
   * 
   * @param number
   *          the {@code float} number
   * @return the types
   */
  public static final int getTypes(final float number) {
    final double d;
    final long l;
    int res;

    res = NumericalTypes.IS_FLOAT;

    if ((number >= Long.MIN_VALUE) && (number <= Long.MAX_VALUE)) {
      l = ((long) number);
      if (l == number) {
        res |= NumericalTypes.IS_LONG;
        if ((l >= Integer.MIN_VALUE) && (l <= Integer.MAX_VALUE)) {
          res |= NumericalTypes.IS_INT;
          if ((l >= Short.MIN_VALUE) && (l <= Short.MAX_VALUE)) {
            res |= NumericalTypes.IS_SHORT | NumericalTypes.IS_FLOAT;
            if ((l >= Byte.MIN_VALUE) && (l <= Byte.MAX_VALUE)) {
              res |= NumericalTypes.IS_BYTE;
            }
          }
        }
      }
    }

    if (number != number) {
      res |= NumericalTypes.IS_DOUBLE;
    } else {
      d = number;
      if (((float) d) == number) {// should always be true, but let's check
        res |= NumericalTypes.IS_DOUBLE;
      }
    }

    return res;
  }

  /**
   * Get the possible types of a given {@link java.lang.Double double}
   * number
   * 
   * @param number
   *          the {@link java.lang.Double double} number
   * @return the types
   */
  public static final int getTypes(final Double number) {
    return NumericalTypes.getTypes(number.doubleValue());
  }

  /**
   * Get the possible types of a given {@code short} number
   * 
   * @param number
   *          the {@code short} number
   * @return the types
   */
  public static final int getTypes(final double number) {
    int res;
    final long l;
    final float f;

    res = NumericalTypes.IS_DOUBLE;

    checkFloat: {
      if ((number >= Long.MIN_VALUE) && (number <= Long.MAX_VALUE)) {
        l = ((long) number);
        if (l == number) {
          if ((l >= Integer.MIN_VALUE) && (l <= Integer.MAX_VALUE)) {
            res |= NumericalTypes.IS_INT;
            if ((l >= NumericalTypes.MIN_FLOAT_INT)
                && (l <= NumericalTypes.MAX_FLOAT_INT)) {
              res |= NumericalTypes.IS_FLOAT;
              if ((l >= Short.MIN_VALUE) && (l <= Short.MAX_VALUE)) {
                res |= NumericalTypes.IS_SHORT | NumericalTypes.IS_FLOAT;
                if ((l >= Byte.MIN_VALUE) && (l <= Byte.MAX_VALUE)) {
                  res |= NumericalTypes.IS_BYTE;
                }
              }
              break checkFloat;
            }
          }
        }
      }

      if (number != number) {
        res |= NumericalTypes.IS_DOUBLE;
      } else {
        f = ((float) number);
        if (f == number) {
          res |= NumericalTypes.IS_FLOAT;
        }
      }
    }

    return res;
  }

  /** the forbidden constructor */
  private NumericalTypes() {
    ErrorUtils.doNotCall();
  }

}
