package org.optimizationBenchmarking.utils.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.functions.combinatoric.GCD;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** a fractional number */
public class Rational extends Number implements ITextable {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** +inf */
  private static final String PI = Double
      .toString(Double.POSITIVE_INFINITY);
  /** -inf */
  private static final String NI = Double
      .toString(Double.NEGATIVE_INFINITY);
  /** nan */
  private static final String NN = Double.toString(Double.NaN);

  /** positive infinity */
  private static final Rational POSITIVE_INFINITY = new Rational(
      Long.MAX_VALUE, 0L);
  /** negative infinity */
  private static final Rational NEGATIVE_INFINITY = new Rational(
      Long.MIN_VALUE, 0L);
  /** NAN */
  private static final Rational NaN = new Rational(0L, 0L);
  /** zero */
  private static final Rational ZERO = new Rational(0L, 1L);
  /** one */
  private static final Rational ONE = new Rational(1L, 1L);

  /** the up */
  private final long m_up;

  /** the down */
  private final long m_down;

  /**
   * create
   * 
   * @param up
   *          the up
   * @param down
   *          the down
   */
  private Rational(final long up, final long down) {
    super();
    this.m_up = up;
    this.m_down = down;
  }

  /**
   * Create a long fraction
   * 
   * @param up
   *          the up
   * @param down
   *          the down
   * @return the fraction
   */
  public static final Rational valueOf(final long up, final long down) {
    return Rational.valueOf(up, down, true);
  }

  /**
   * Create a long fraction
   * 
   * @param number
   *          the number
   * @return the fraction, or {@link #NaN} if the number cannot be
   *         represented as fraction
   */
  public static final Rational valueOf(final double number) {
    final long integer;
    long a_up, a_down, b_up, b_down, mid_up, mid_down, temp1, temp2, gcd;
    double a_dbl, b_dbl, frac, inv, check;

    if (number != number) {
      return Rational.NaN;
    }
    if (number < Long.MIN_VALUE) {
      return Rational.NEGATIVE_INFINITY;
    }
    if (number > Long.MAX_VALUE) {
      return Rational.POSITIVE_INFINITY;
    }
    if (number == 0d) {
      return Rational.ZERO;
    }
    if (number == 1d) {
      return Rational.ONE;
    }
    integer = ((long) number);
    frac = (number - integer);
    inv = (1d / frac);
    if ((integer == number) || (frac == 0d) || (inv < Long.MIN_VALUE)
        || (inv > Long.MAX_VALUE)) {
      return Rational.valueOf(integer, 1L);
    }

    // let's try binary search

    if (frac > 0d) {
      a_up = 0L;
      a_down = 1L;
      b_up = 1L;
      b_down = 1L;
    } else {
      a_up = 0L;
      a_down = 0L;
      b_up = (-1L);
      b_down = 1L;
    }
    a_dbl = (a_up / ((double) a_down));
    b_dbl = (b_up / ((double) b_down));
    outer: for (;;) {

      inner: for (;;) {
        inv = (frac - a_dbl);
        check = (1d / inv);
        if (check < Long.MAX_VALUE) {
          temp1 = ((long) check);
          if ((1d / temp1) != inv) {
            temp1++;
          }

          if (temp1 > 1L) {
            temp2 = Rational.__mulNonZero(a_up, temp1);
            if ((temp2 > 0L) || (a_up == 0L)) {
              temp2 += a_down;
              if (temp2 > 0L) {
                temp1 = Rational.__mulNonZero(a_down, temp1);
                if (temp1 > 0L) {
                  gcd = GCD.INSTANCE.compute(temp2, temp1);
                  if (gcd > 0) {
                    a_up = (temp2 / gcd);
                    a_down = (temp1 / gcd);
                    a_dbl = (a_up / ((double) a_down));
                    if (/* Math.abs(frac - a_dbl) <= 1e-13d */a_dbl == frac) {
                      return Rational.__combine(a_up, a_down, integer);
                    }
                    continue inner;
                  }
                }
              }
            }
          }
        }
        break inner;
      }

      inner: for (;;) {
        inv = (b_dbl - frac);
        check = (1d / inv);
        if (check < Long.MAX_VALUE) {
          temp1 = ((long) check);
          if ((1d / temp1) != inv) {
            temp1++;
          }

          if (temp1 > 1L) {
            temp2 = Rational.__mulNonZero(b_up, temp1);
            if ((temp2 > 0L) || (b_up == 0L)) {
              temp2 -= b_down;
              if (temp2 > 0L) {
                temp1 = Rational.__mulNonZero(b_down, temp1);
                if (temp1 > 0L) {
                  gcd = GCD.INSTANCE.compute(temp2, temp1);
                  if (gcd > 0) {
                    b_up = (temp2 / gcd);
                    b_down = (temp1 / gcd);
                    b_dbl = (b_up / ((double) b_down));
                    if (/* Math.abs(b_dbl - frac) <= 1e-13d */b_dbl == frac) {
                      return Rational.__combine(b_up, b_down, integer);
                    }
                    continue inner;
                  }
                }
              }
            }
          }
        }
        break inner;
      }

      temp2 = Rational.__mulNonZero(a_up, b_down);
      if (temp2 <= 0L) {
        break outer;
      }
      mid_up = Rational.__mulNonZero(b_up, a_down);
      if (mid_up <= 0L) {
        break outer;
      }
      mid_down = Rational.__mulNonZero(b_down, a_down);
      if (mid_down <= 0L) {
        break outer;
      }
      if (((temp2 & 1L) == 0L) && ((mid_up & 1L) == 0L)) {
        mid_up = ((temp2 >>> 1L) + (mid_up >> 1L));
      } else {
        mid_up += temp2;
        mid_down <<= 1L;
      }
      gcd = GCD.INSTANCE.compute(mid_up, mid_down);
      if (gcd <= 0L) {
        break outer;
      }
      mid_up /= gcd;
      mid_down /= gcd;

      check = (mid_up / ((double) mid_down));
      if (/** Math.abs(check - frac) <= 1e-13d */
      check == frac) {
        return Rational.__combine(mid_up, mid_down, integer);
      }
      if (check < frac) {
        if ((a_up >= mid_up) && (a_down <= mid_down)) {
          break outer;
        }
        a_up = mid_up;
        a_down = mid_down;
        a_dbl = check;
      } else {
        if ((b_up <= mid_up) && (b_down >= mid_down)) {
          break outer;
        }
        b_up = mid_up;
        b_down = mid_down;
        b_dbl = check;
      }
    }

    return Rational.NaN;
  }

  /**
   * make the result
   * 
   * @param up
   *          the up
   * @param down
   *          the down
   * @param integer
   *          the int
   * @return the result
   */
  private static final Rational __combine(final long up, final long down,
      final long integer) {
    long temp2;

    temp2 = Rational.__mulNonZero(integer, down);
    if ((temp2 == 0L) && (integer != 0L)) {
      return Rational.NaN;
    }
    temp2 += up;
    if ((integer < 0L) ^ (temp2 < 0L)) {
      return Rational.NaN;
    }
    return Rational.valueOf(temp2, down);
  }

  /**
   * Create a long fraction
   * 
   * @param up
   *          the up
   * @param down
   *          the down
   * @param doGcd
   *          do the doGcd?
   * @return the fraction
   */
  private static final Rational valueOf(final long up, final long down,
      final boolean doGcd) {
    long u, d;
    final long gcd;

    u = up;
    d = down;

    if (d == 0L) {
      if (u > 0L) {
        return Rational.POSITIVE_INFINITY;
      }
      if (u < 0L) {
        return Rational.NEGATIVE_INFINITY;
      }
      return Rational.NaN;
    }
    if (u == 0L) {
      return Rational.ZERO;
    }
    if (d < 0L) {
      u = (-u);
      d = (-d);
    }

    if (doGcd) {
      gcd = GCD.INSTANCE.compute(u, d);
      if (gcd > 1L) {// > 0: if min_value its min_value
        u /= gcd;
        d /= gcd;
      }
    }

    if (u == d) {
      return Rational.ONE;
    }
    return new Rational(u, d);
  }

  /**
   * Does this fraction represent a true number?
   * 
   * @return {@code true} if the fraction stands for a number,
   *         {@code false} otherwise
   */
  public final boolean isReal() {
    return (this.m_down != 0L);
  }

  /**
   * Get the sign of this fraction
   * 
   * @return the sign of this fraction
   */
  public final int sign() {
    return (((this.m_up > 0L) ? 1 : ((this.m_up < 0L) ? (-1) : 0)));
  }

  /**
   * Is the fraction infinite or overflowing?
   * 
   * @return {@code true} if the fraction is infinite or overflowing
   */
  public final boolean isInfinite() {
    return ((this.m_down == 0L) && (this.m_up != 0L));
  }

  /**
   * Is the fraction neither infinite nor an actual number?
   * 
   * @return {@code true} if the fraction is not real
   */
  public final boolean isNaN() {
    return ((this.m_down == 0L) && (this.m_up == 0L));
  }

  /** {@inheritDoc} */
  @Override
  public final double doubleValue() {
    long full, frac;

    if (this.m_down == 0L) {
      if (this.m_up < 0L) {
        return Double.NEGATIVE_INFINITY;
      }
      if (this.m_up > 0L) {
        return Double.POSITIVE_INFINITY;
      }
      return Double.NaN;
    }

    full = (this.m_up / this.m_down);
    frac = (this.m_up % this.m_down);

    return (full + (frac / ((double) (this.m_down))));
  }

  /**
   * l + 1
   * 
   * @param l
   *          the number
   * @return the bounded increment
   */
  private static final long __up(final long l) {
    return ((l < Long.MAX_VALUE) ? (l + 1L) : l);
  }

  /**
   * l - 1
   * 
   * @param l
   *          the number
   * @return the bounded decrement
   */
  private static final long __down(final long l) {
    return ((l > Long.MIN_VALUE) ? (l - 1L) : l);
  }

  /**
   * Obtain a long value representing this number
   * 
   * @param round
   *          the rounding mode
   * @return the long value
   */
  public final long longValue(final RoundingMode round) {
    final long down, integer;
    long fraction;

    down = this.m_down;
    if (down == 0L) {
      if (this.m_up < 0L) {
        return Long.MIN_VALUE;
      }
      if (this.m_up > 0L) {
        return Long.MAX_VALUE;
      }
      throw new IllegalStateException();
    }

    integer = (this.m_up / this.m_down);

    if ((round == null) || (round == RoundingMode.DOWN)) {
      return integer;
    }

    fraction = (this.m_up % this.m_down);
    if (fraction == 0L) {
      return integer;
    }
    if (fraction < 0L) {
      fraction = (-fraction);
    }

    switch (round) {
      case CEILING: {
        return ((integer > 0L) ? Rational.__up(integer) : integer);
      }
      case FLOOR: {
        return ((integer < 0L) ? Rational.__down(integer) : integer);
      }
      case HALF_DOWN: {
        return (((fraction << 1) > down) ? ((integer >= 0) ? (Rational
            .__up(integer)) : (Rational.__down(integer))) : integer);
      }
      case HALF_EVEN: {
        if ((integer & 1L) != 0L) {
          return (((fraction << 1) >= down) ? ((integer >= 0) ? (Rational
              .__up(integer)) : (Rational.__down(integer))) : integer);
        }
        return (((fraction << 1) > down) ? ((integer >= 0) ? (Rational
            .__up(integer)) : (Rational.__down(integer))) : integer);
      }
      case HALF_UP: {
        return (((fraction << 1) >= down) ? ((integer >= 0) ? (Rational
            .__up(integer)) : (Rational.__down(integer))) : integer);
      }

      // case UNNECESSARY:
      default: {
        if (fraction != 0L) {
          throw new ArithmeticException();
        }
        return integer;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final long longValue() {
    return this.longValue(RoundingMode.DOWN);
  }

  /**
   * Does this fraction actually represent an integer number?
   * 
   * @return {@code true} if it is an integer, {@code false} otherwise
   */
  public final boolean isInteger() {
    return (this.m_down == 1L);
  }

  /**
   * multiply with a double
   * 
   * @param d
   *          the double value
   * @return the return value
   */
  public final double multiply(final double d) {
    Rational r;

    r = this.multiply(Rational.valueOf(d));
    if (r.m_down == 0L) {
      return (this.doubleValue() * d);
    }

    return r.doubleValue();
  }

  /**
   * multiply with a long
   * 
   * @param in
   *          the input value
   * @return the return value
   */
  public final Rational multiply(final long in) {
    return this.multiply(new Rational(in, 1L));
  }

  /**
   * invert a fraction
   * 
   * @return the inverse
   */
  public final Rational inverse() {
    return Rational.valueOf(this.m_down, this.m_up, false);
  }

  /**
   * Multiply two long integers, checking for overflow.
   * 
   * @param a
   *          first value
   * @param b
   *          second value
   * @return the product <code>a * b</code>, or {@code 0} on overflow
   */
  private static final long __mulNonZero(final long a, final long b) {

    if (a > b) {
      return Rational.__mulNonZero(b, a);
    }
    if (a < 0) {
      if (b < 0) {
        if (a >= (Long.MAX_VALUE / b)) {
          return (a * b);
        }
        return 0L;
      }

      if ((Long.MIN_VALUE / b) <= a) {
        return a * b;
      }
      return 0L;

    }
    if (a <= (Long.MAX_VALUE / b)) {
      return (a * b);
    }
    return 0L;
  }

  /**
   * multiply
   * 
   * @param b
   *          the other fraction
   * @return the result
   */
  public final Rational multiply(final Rational b) {
    final long a_up, a_down, b_up, b_down, new_up, new_down, up1, up2, down1, down2, gcd_a_up_b_down, gcd_a_down_b_up, gcd;

    a_down = this.m_down;
    a_up = this.m_up;
    b_down = b.m_down;
    b_up = b.m_up;

    if (a_down <= 0L) {
      if ((a_up == 0L) || (b_up == 0L)) {
        return Rational.NaN;
      }
      return (((a_up < 0L) ^ (b_up < 0L)) ? Rational.NEGATIVE_INFINITY
          : Rational.POSITIVE_INFINITY);
    }
    if (b_down <= 0L) {
      if ((b_up == 0L) || (a_up == 0L)) {
        return Rational.NaN;
      }
      return (((a_up < 0L) ^ (b_up < 0L)) ? Rational.NEGATIVE_INFINITY
          : Rational.POSITIVE_INFINITY);
    }

    if ((a_up == 0L) || (b_up == 0L)) {
      return Rational.ZERO;
    }

    if ((a_up == b_down) && (b_up == a_down)) {
      return Rational.ONE;
    }

    gcd_a_up_b_down = GCD.INSTANCE.compute(a_up, b_down);
    if (gcd_a_up_b_down < 0L) {
      up1 = a_up;
      down1 = b_down;
    } else {
      up1 = (a_up / gcd_a_up_b_down);
      down1 = (b_down / gcd_a_up_b_down);
    }

    gcd_a_down_b_up = GCD.INSTANCE.compute(a_down, b_up);
    if (gcd_a_down_b_up < 0L) {
      up2 = b_up;
      down2 = a_down;
    } else {
      up2 = (b_up / gcd_a_down_b_up);
      down2 = (a_down / gcd_a_down_b_up);
    }

    new_up = Rational.__mulNonZero(up1, up2);
    new_down = Rational.__mulNonZero(down1, down2);

    if (new_up == 0L) {
      if (new_down == 0L) {
        return Rational.NaN;
      }
      return (((a_up < 0L) ^ (b_up < 0L)) ? Rational.NEGATIVE_INFINITY
          : Rational.POSITIVE_INFINITY);
    }
    if (new_down == 0L) {
      return Rational.NaN;
    }

    gcd = GCD.INSTANCE.compute(new_up, new_down);
    if (gcd > 1L) {
      return new Rational((new_up / gcd), (new_down / gcd));
    }
    return new Rational(new_up, new_down);
  }

  /**
   * divide two fractions
   * 
   * @param b
   *          the second fraction
   * @return the division result
   */
  public final Rational divide(final Rational b) {
    return this.multiply(b.inverse());
  }

  /** {@inheritDoc} */
  @Override
  public final int intValue() {
    return ((int) (Math.max(Integer.MIN_VALUE,
        Math.min(Integer.MAX_VALUE, this.longValue()))));
  }

  /** {@inheritDoc} */
  @Override
  public final float floatValue() {
    return ((float) (this.doubleValue()));
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    final MemoryTextOutput sb;
    sb = new MemoryTextOutput(32);
    this.toText(sb);
    return sb.toString();
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    final long down;

    down = this.m_down;
    if (down == 0L) {
      textOut.append((this.m_up < 0L) ? Rational.NI
          : ((this.m_up > 0L) ? Rational.PI : Rational.NN));
    } else {
      textOut.append(this.m_up);
      if (down != 1L) {
        textOut.append('/');
        textOut.append(this.m_down);
      }
    }
  }

  /**
   * Try to multiply with a big decimal value
   * 
   * @param b
   *          the value
   * @return the result
   * @throws ArithmeticException
   *           if the procedure fails
   */
  public final BigDecimal multiply(final BigDecimal b) {
    if (this.isReal()) {
      return b.multiply(BigDecimal.valueOf(this.m_up)).divide(
          BigDecimal.valueOf(this.m_down));
    }
    throw new ArithmeticException();
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(HashUtils.hashCode(this.m_up),
        HashUtils.hashCode(this.m_down));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final Rational r;
    if (o instanceof Rational) {
      r = ((Rational) o);
      if (r.m_down == this.m_down) {
        if (r.m_up == this.m_up) {
          return true;
        }
        if (this.m_down == 0L) {
          if (this.m_up < 0L) {
            return (r.m_up < 0L);
          }
          if (this.m_up > 0L) {
            return (r.m_up > 0L);
          }
        }
      }
    }

    return false;
  }
}
