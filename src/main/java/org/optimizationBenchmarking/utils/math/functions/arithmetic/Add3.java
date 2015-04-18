package org.optimizationBenchmarking.utils.math.functions.arithmetic;

import org.optimizationBenchmarking.utils.math.functions.TernaryFunction;

/**
 * The {@code "+"} function for three arguments, with the goal to compute
 * without overflow
 */
public final class Add3 extends TernaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the ternary adder */
  public static final Add3 INSTANCE = new Add3();

  /** instantiate */
  private Add3() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0, final byte x1,
      final byte x2) {
    return ((byte) (x0 + x1 + x2));
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0, final short x1,
      final short x2) {
    return ((short) (x0 + x1 + x2));
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0, final int x1, final int x2) {
    return (x0 + x1 + x2);
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0, final long x1,
      final long x2) {
    return (x0 + x1 + x2);
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x0, final float x1,
      final float x2) {
    return ((float) (this.computeAsDouble(x0, x1, x2)));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0, final double x1,
      final double x2) {
    long a, b, c;

    if ((x0 >= Long.MIN_VALUE) && (x0 <= Long.MAX_VALUE)) {
      a = ((long) x0);
      if (a == x0) {

        if ((x1 >= Long.MIN_VALUE) && (x1 <= Long.MAX_VALUE)) {
          b = ((long) x1);
          if (b == x1) {

            if ((x2 >= Long.MIN_VALUE) && (x2 <= Long.MAX_VALUE)) {
              c = ((long) x2);
              if (c == x2) {

                return this.computeAsDouble(a, b, c);

              }
            }

          }
        }

      }
    }

    return Add3.__computeAsDouble(x0, x1, x2);
  }

  /**
   * Perform the {@code double}-valued computation
   * 
   * @param x0
   *          the first parameter
   * @param x1
   *          the second parameter
   * @param x2
   *          the third parameter
   * @return the result
   */
  private static final double __computeAsDouble(final double x0,
      final double x1, final double x2) {
    double s1, s2, s3, c1, c2, c3, y, t;
    boolean n, p, us1, us2, us3;

    us1 = us2 = us3 = n = p = false;

    // (x0 + x1) + x2
    c1 = 0d;
    s1 = x0;
    y = (x1 - c1);
    t = (s1 + y);
    c1 = ((t - s1) - y);
    s1 = t;
    y = (x2 - c1);
    t = (s1 + y);
    c1 = ((t - s1) - y);
    s1 = t;

    if (s1 <= Double.NEGATIVE_INFINITY) {
      n = true;
    } else {
      if (s1 >= Double.POSITIVE_INFINITY) {
        p = true;
      } else {
        if (s1 == s1) {
          us1 = true;
          c1 = Math.abs(c1);
        }
      }
    }

    // (x0 + x2) + x1
    c2 = 0d;
    s2 = x0;
    y = (x2 - c2);
    t = (s2 + y);
    c2 = ((t - s2) - y);
    s2 = t;
    y = (x1 - c2);
    t = (s2 + y);
    c2 = ((t - s2) - y);
    s2 = t;

    if (s2 <= Double.NEGATIVE_INFINITY) {
      n = true;
    } else {
      if (s2 >= Double.POSITIVE_INFINITY) {
        p = true;
      } else {
        if (s2 == s2) {
          us2 = true;
          c2 = Math.abs(c2);
        }
      }
    }

    // (x1 + x2) + x0
    c3 = 0d;
    s3 = x1;
    y = (x2 - c3);
    t = (s3 + y);
    c3 = ((t - s3) - y);
    s3 = t;
    y = (x0 - c2);
    t = (s3 + y);
    c3 = ((t - s3) - y);
    s3 = t;

    if (s3 <= Double.NEGATIVE_INFINITY) {
      n = true;
    } else {
      if (s3 >= Double.POSITIVE_INFINITY) {
        p = true;
      } else {
        if (s3 == s3) {
          us3 = true;
          c3 = Math.abs(c3);
        }
      }
    }

    if (us1) {
      if (us2 && (c2 < c1)) {
        s1 = s2;
        c1 = c2;
      }
      if (us3 && (c3 < c1)) {
        return s3;
      }
      return s1;
    }
    if (us2) {
      if (us3 && (c3 < c2)) {
        return s3;
      }
      return s2;
    }
    if (us3) {
      return s3;
    }

    if (n && (!p)) {
      return Double.NEGATIVE_INFINITY;
    }
    if (p && (!n)) {
      return Double.POSITIVE_INFINITY;
    }

    n = ((x0 <= Double.NEGATIVE_INFINITY)
        || (x1 <= Double.NEGATIVE_INFINITY) || (x2 <= Double.NEGATIVE_INFINITY));
    p = ((x0 >= Double.POSITIVE_INFINITY)
        || (x1 >= Double.POSITIVE_INFINITY) || (x2 >= Double.POSITIVE_INFINITY));
    if (n) {
      if (p) {
        return Double.NaN;
      }
      return Double.NEGATIVE_INFINITY;
    }
    if (p) {
      return Double.POSITIVE_INFINITY;
    }

    n = ((x0 < 0d) || (x1 < 0d) || (x2 < 0d));
    p = ((x0 > 0d) || (x1 > 0d) || (x2 > 0d));
    if (n) {
      if (p) {
        return Double.NaN;
      }
      return Double.NEGATIVE_INFINITY;
    }
    if (p) {
      return Double.POSITIVE_INFINITY;
    }

    return Double.NaN;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0, final long x1,
      final long x2) {
    long sum01, sum02, sum12, longApprox;
    boolean can01, can02, can12, hasAbove, hasBelow;
    double approx01, approx02, approx12, doubleApprox, sort1, sort2, sort3;
    int sortLen;

    // first, we try to find a non-overflowing sum of two elements

    // can we add x0 and x1 without overflow?
    can01 = (SaturatingAdd.getOverflowType(x0, x1) == 0);
    if (can01) {
      sum01 = (x0 + x1);
      // let's try to add x2 to that sum and see whether it can be done
      // without overflow
      if (SaturatingAdd.getOverflowType(sum01, x2) == 0) {
        return (sum01 + x2);
      }
      approx01 = (((double) sum01) + ((double) x2));
    } else {
      approx01 = Double.NaN;
    }

    // can we add x0 and x2 without overflow?
    can02 = (SaturatingAdd.getOverflowType(x0, x2) == 0);
    if (can02) {
      sum02 = (x0 + x2);
      // let's try to add x1 to that sum and see whether it can be done
      // without overflow
      if (SaturatingAdd.getOverflowType(sum02, x1) == 0) {
        return (sum02 + x1);
      }
      approx02 = (((double) sum02) + ((double) x1));
    } else {
      approx02 = Double.NaN;
    }

    // can we add x1 and x2 without overflow?
    can12 = (SaturatingAdd.getOverflowType(x1, x2) == 0);
    if (can12) {
      sum12 = (x1 + x2);
      // let's try to add x0 to that sum and see whether it can be done
      // without overflow
      if (SaturatingAdd.getOverflowType(sum12, x0) == 0) {
        return (sum12 + x0);
      }
      approx12 = (((double) sum12) + ((double) x0));
    } else {
      approx12 = Double.NaN;
    }

    // ok, there was no pure long-arithmetic sum of all three numbers
    // without overflow so let us try to sum with long arithmetic anyway
    // _and_ with double arithmetic
    longApprox = (x0 + x1 + x2);
    doubleApprox = Add3.__computeAsDouble(x0, x1, x2);

    if (doubleApprox == longApprox) {
      // if both are the same, then it went well anyway (maybe we had two
      // overflows canceling each other)
      return longApprox;
    }

    // if not, we have the following choices:

    // a) we had non-overflowing sums of two elements. if we extend them to
    // sums of three with double arithmetic and end up with at least one
    // sum which is bigger and one which is smaller than the pure
    // long-arithmetic calculation, then we can still accept the result
    // from long arithmetic

    // b) if one of these sums of three equals the result from the long
    // arithmetic, then we can accept that result too

    // c) if we had several such sums and some of them are different, we
    // take the median result

    hasAbove = hasBelow = false;
    if (doubleApprox > longApprox) {
      hasAbove = true;
    } else {
      hasBelow = true;
    }
    sort1 = sort2 = sort3 = doubleApprox;
    sortLen = 1;

    // ok, first sum was possible
    if (can01) {
      if (approx01 > longApprox) {
        hasAbove = true;
      } else {
        if (approx01 < longApprox) {
          hasBelow = true;
        } else {
          return longApprox;
        }
      }

      // sort the sums so we can later compute median
      if (approx01 < sort1) {
        sort2 = sort1;
      } else {
        sort2 = approx01;
      }
      sortLen = 2;

    }

    // ok, second sum was possible
    if (can02) {
      if (approx02 > longApprox) {
        hasAbove = true;
      } else {
        if (approx02 < longApprox) {
          hasBelow = true;
        } else {
          return longApprox;
        }
      }

      // sort the sums so we can later compute median
      switch (sortLen) {
        case 1: {
          if (approx02 < sort1) {
            sort2 = sort1;
          } else {
            sort2 = approx02;
          }
          sortLen = 2;
          break;
        }

        default: {// case 2:{
          if (approx02 < sort1) {
            sort3 = sort2;
            sort2 = sort1;
          } else {
            if (approx02 < sort2) {
              sort3 = sort2;
              sort2 = approx02;
            } else {
              sort3 = approx02;
            }
          }
          sortLen = 3;
          break;
        }
      }

    }

    if (can12) {
      if (approx12 > longApprox) {
        hasAbove = true;
      } else {
        if (approx12 < longApprox) {
          hasBelow = true;
        } else {
          return longApprox;
        }
      }

      // sort the sums so we can later compute median
      switch (sortLen) {
        case 1: {
          if (approx12 < sort1) {
            sort2 = sort1;
          } else {
            sort2 = approx12;
          }
          sortLen = 2;
          break;
        }

        case 2: {
          if (approx12 < sort1) {
            sort3 = sort2;
            sort2 = sort1;
          } else {
            if (approx12 < sort2) {
              sort3 = sort2;
              sort2 = approx12;
            } else {
              sort3 = approx12;
            }
          }
          sortLen = 3;
          break;
        }

        default: {
          if (approx12 < sort1) {
            sort3 = sort2;
            sort2 = sort1;
          } else {
            if (approx12 < sort2) {
              sort3 = sort2;
              sort2 = approx12;
            } else {
              if (approx12 < sort3) {
                sort3 = approx12;
              }
            }
          }
          sortLen = 4;
          break;
        }
      }
    }

    // ok, we had some bigger and some smaller sum in comparison to the
    // long-arithmetic result, so we can accept it
    if (hasAbove && hasBelow) {
      return longApprox;
    }

    // return a the median, the value in the middle, as best guess
    switch (sortLen) {
      case 1: {
        return sort1;
      }
      case 2: {
        approx12 = ((0.5d * sort1) + (0.5d * sort2));
        if ((sort1 <= approx12) && (approx12 <= sort2)) {
          return approx12;
        }
        return sort2;
      }
      case 3: {
        return sort2;
      }
      default: {
        approx12 = ((0.5d * sort2) + (0.5d * sort3));
        if ((sort2 <= approx12) && (approx12 <= sort3)) {
          return approx12;
        }
        return sort2;
      }
    }

  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final int x0, final int x1,
      final int x2) {
    return (((long) x0) + ((long) x1) + (x2));
  }

  /**
   * Write replace: the instance this method is invoked on will be replaced
   * with the singleton instance {@link #INSTANCE} for serialization, i.e.,
   * when the instance is written with
   * {@link java.io.ObjectOutputStream#writeObject(Object)}.
   * 
   * @return the replacement instance (always {@link #INSTANCE})
   */
  private final Object writeReplace() {
    return Add3.INSTANCE;
  }

  /**
   * Read resolve: The instance this method is invoked on will be replaced
   * with the singleton instance {@link #INSTANCE} after serialization,
   * i.e., when the instance is read with
   * {@link java.io.ObjectInputStream#readObject()}.
   * 
   * @return the replacement instance (always {@link #INSTANCE})
   */
  private final Object readResolve() {
    return Add3.INSTANCE;
  }
}
