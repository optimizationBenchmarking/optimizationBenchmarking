package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.BasicNumber;

/**
 * A class to compute a (numerical stable) sum of elements. This class
 * applies a set of measures to ensure numeric stability:
 * <ol>
 * <li>If possible, it uses {@code long}-based arithmetic, i.e., 100% exact
 * integer computations based on 64bit long integers.</li>
 * <li>If integer calculations are not possible, it will switch to <a
 * href="http://en.wikipedia.org/wiki/Kahan_summation_algorithm">Kahan's
 * summation algorithm</a> with {@code double}-precision floating point
 * calculations.</li>
 * <li>If, at some point, during the {@code double}-precision floating
 * point summation, switching back to integer arithmetic becomes possible
 * (maybe the sum adds up to an integer without compensation), the
 * summation algorithm tries to switch back to integer arithmetics.</li>
 * <li>We distinguish overflows and infinity:
 * <ol>
 * <li>Adding {@link java.lang.Double#POSITIVE_INFINITY} to a real number
 * results to a transition of the sum to positive infinity. Unless you
 * later add {@link java.lang.Double#NEGATIVE_INFINITY} or
 * {@link java.lang.Double#NaN}, in which case the sum becomes
 * {@link java.lang.Double#NaN}, the sum stays positive infinite.</li>
 * <li>Adding {@link java.lang.Double#NEGATIVE_INFINITY} to a real number
 * results to a transition of the sum to negative infinity. Unless you
 * later add {@link java.lang.Double#POSITIVE_INFINITY} or
 * {@link java.lang.Double#NaN}, in which case the sum becomes
 * {@link java.lang.Double#NaN}, the sum stays negative infinite.</li>
 * <li>Traditionally, adding several large positive numbers resulting in a
 * {@code double} arithmetic overflow is indistinguishable from an infinite
 * sum. Although {@link #doubleValue()} would still return
 * {@link java.lang.Double#POSITIVE_INFINITY} in this case, the treatment
 * of overflows is different from infinity for our stable sum: An overflow
 * is not a real infinity, because you can add, e.g.,
 * {@link java.lang.Double#NEGATIVE_INFINITY} and get
 * {@link java.lang.Double#NEGATIVE_INFINITY} instead of
 * {@link java.lang.Double#NaN}. Instead, the sum can become
 * {@link java.lang.Double#NaN} if you add several <em>negative</em>
 * numbers of large magnitude. We therefore try to maintain an
 * approximation how often the sum has overflown via a Kahan sum and a
 * counter. This way, it is, at least approximately possible to determine
 * whether a sum stays overflown by constantly growing or whether it would
 * drop back to the "normal" range or even underflow. Since the latter two
 * cases are dodgy, they will result in the sum becoming
 * {@link java.lang.Double#NaN}.</li>
 * <li>Analogously, the case of underflow, i.e., the sum becoming too large
 * negative to be held in a non-infinite {@code double}, is handled.</li>
 * </ol>
 */
public final class StableSum extends ScalarAggregate {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the overflow threshold */
  private static final double OVERFLOW = Double.MAX_VALUE;
  /** the underflow threshold */
  private static final double UNDERFLOW = (-StableSum.OVERFLOW);

  /** the compensation */
  private double m_compensation;

  /** instantiate */
  public StableSum() {
    super();
  }

  /**
   * add a value while updating the compensation
   * 
   * @param value
   *          the value to add
   */
  private final void __addWithCompensation(final double value) {
    final double y, t;

    y = (value - this.m_compensation);
    t = (this.m_double + y);
    this.m_compensation = ((t - this.m_double) - y);
    this.m_double = t;
  }

  /** switch from long to double arithmetic */
  private final void __switchFromLongToDoubleArithmetic() {
    this.m_double = this.m_long;
    this.m_compensation = (((long) (this.m_double)) - this.m_long);
    this.m_state = BasicNumber.STATE_DOUBLE;
  }

  /**
   * perform a plain double arithmetic addition.
   * 
   * @param value
   *          the value to add
   */
  @SuppressWarnings("incomplete-switch")
  private final void __plainDoubleAdd(final double value) {
    final double oldSum, oldCompensation, newSum, correctedSum;
    final long lsum;

    // Double arithmetic: regular numbers, overflow, or underflow.
    oldSum = this.m_double;
    oldCompensation = this.m_compensation;
    this.__addWithCompensation(value);
    newSum = this.m_double;

    // We need to check if the new sum has overflown (towards positive
    // infinity) or underflown (towards negative infinity). In either case,
    // we try to count the number of times we have overflown or underflown
    // in m_long
    if (newSum <= StableSum.UNDERFLOW) {
      // OK, underflow
      this.m_double = oldSum;
      this.m_compensation = oldCompensation;
      this.__addWithCompensation(StableSum.OVERFLOW); // shift positive
      this.__addWithCompensation(value); // this now should not underflow
      correctedSum = this.m_double;

      if ((correctedSum > StableSum.UNDERFLOW)
          && (correctedSum < StableSum.OVERFLOW)) {
        // OK, correction worked, now count underflow
        switch (this.m_state) {
          case STATE_DOUBLE: {
            this.m_state = BasicNumber.STATE_NEGATIVE_OVERFLOW;
            this.m_long = 1l;
            return;
          }
          case STATE_POSITIVE_OVERFLOW: {
            if ((--this.m_long) > 0) {
              return;
            }
            break;
            // Otherwise: We have overflown and now returned into the
            // normal range. This seems to be very dodgy, we better switch
            // to / fall through to NaN.
          }
          case STATE_NEGATIVE_OVERFLOW: {
            this.m_long++;
            return;
          }
        } // state error: fall through to NaN
      }// Arithmetic error / it did not work: fall through to NaN
    } else {
      // No underflow, but maybe an overflow?
      if (newSum >= StableSum.OVERFLOW) {// Yes, overflow
        this.m_double = oldSum;
        this.m_compensation = oldCompensation;
        this.__addWithCompensation(StableSum.UNDERFLOW);// shift negative
        this.__addWithCompensation(value);// this now should work
        correctedSum = this.m_double;

        if ((correctedSum > StableSum.UNDERFLOW)
            && (correctedSum < StableSum.OVERFLOW)) {
          // OK, correction worked, no overflow anymore: now count overflow

          switch (this.m_state) {
            case STATE_DOUBLE: {
              this.m_state = BasicNumber.STATE_POSITIVE_OVERFLOW;
              this.m_long = 1L;
              return;
            }
            case STATE_POSITIVE_OVERFLOW: {
              this.m_long++;
              return;
            }
            case STATE_NEGATIVE_OVERFLOW: {
              if ((--this.m_long) > 0L) {
                return;
              }
              break;
              // Otherwise: We have underflown and returned towards the
              // normal range. This is too dodgy, better jump to / fall
              // through to NaN.
            }
          }// state error: fall through to NaN
        }// Arithmetic error: fall through to NaN
      } else {
        if (newSum == newSum) {
          // No new overflow or underflow happened - we may stay in the
          // original state, the sum is OK.

          // Let's check if we can even switch back to long arithmetic
          if ((this.m_state == BasicNumber.STATE_DOUBLE)
              && (this.m_compensation == 0d) && (newSum >= Long.MIN_VALUE)
              && (newSum <= Long.MAX_VALUE)) {
            // We are in the range of long, with no pending compensation...
            lsum = ((long) newSum);
            if (lsum == newSum) {// ...and the new sum is an integer!
              this.m_long = lsum;// so we switch back to
              this.m_state = BasicNumber.STATE_INTEGER; // long arithmetic
            }
          }
          return;
        }
      }
    }

    // If we get here, something went wrong, better set NaN
    this.m_state = BasicNumber.STATE_NAN;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final long value) {
    final int state;
    long s;

    // if the sum is empty, we can take the long value directly
    state = this.m_state;
    if (state == BasicNumber.STATE_EMPTY) {
      this.m_long = value;
      this.m_state = BasicNumber.STATE_INTEGER;
      return;
    }

    // there is nothing to do if we are nan, infinite, or if the new value
    // is 0
    if ((value == 0L) || (state == BasicNumber.STATE_NAN)
        || (state == BasicNumber.STATE_POSITIVE_INFINITY)
        || (state == BasicNumber.STATE_NEGATIVE_INFINITY)) {
      return;
    }

    // we currently use long arithmetic, which means we can try to do
    // absolutely exact calculations
    if (state == BasicNumber.STATE_INTEGER) {
      s = (this.m_long + value);
      if (value < 0L) {
        if (s < this.m_long) {
          this.m_long = s;
          return;
        }
      } else {
        if (s > this.m_long) {
          this.m_long = s;
          return;
        }
      }

      // Hm, our long arithmetic has overflown, either towards positive or
      // negative infinity.
      // Thus, we now need to switch to double arithmetic.
      this.__switchFromLongToDoubleArithmetic();
      // Fall through to double arithmetic
    }

    this.__plainDoubleAdd(value);
  }

  /**
   * Visit a given {@code double}, i.e., add it to the stable sum.
   * 
   * @param value
   *          the value to add
   */
  @Override
  public final void append(final double value) {
    final long lvalue;
    final int state;

    state = this.m_state;

    if (value < Long.MIN_VALUE) {
      // We are outside the long range towards negative.
      if (value <= Double.NEGATIVE_INFINITY) {
        // Set state to negative infinity, unless ...
        switch (state) {
          case STATE_POSITIVE_INFINITY: {
            this.m_state = BasicNumber.STATE_NAN; // ...it was positive
                                                  // infinity
            return;
          }
          case STATE_NAN: {
            return; // ...or nan
          }
          default: {
            // STATE_POSITIVE_OVERFLOW, STATE_NEGATIVE_OVERFLOW,
            // STATE_DOUBLE, STATE_INTEGER,
            // STATE_NEGATIVE_INFINITY
            this.m_state = BasicNumber.STATE_NEGATIVE_INFINITY;
          }
        }
        return; // nothing else to do
      }
    } else {
      if (value > Long.MAX_VALUE) {
        // We are outside the long range towards positive.
        if (value >= Double.POSITIVE_INFINITY) {
          // Set state to positive infinity, unless...
          switch (state) {
            case STATE_NEGATIVE_INFINITY: {
              this.m_state = BasicNumber.STATE_NAN; // ...it was negative
                                                    // infinity
              return;
            }
            case STATE_NAN: {
              return; // ...or nan
            }
            default: {
              // STATE_POSITIVE_OVERFLOW, STATE_NEGATIVE_OVERFLOW,
              // STATE_DOUBLE, STATE_INTEGER,
              // STATE_POSITIVE_INFINITY
              this.m_state = BasicNumber.STATE_POSITIVE_INFINITY;
            }
          }
          return; // nothing else to do
        }
      } else {
        // OK, we are either in the range of long, or NaN
        if (value != value) {
          this.m_state = BasicNumber.STATE_NAN;
          return;// NaN? exit.
        }

        lvalue = ((long) value);// try to do long arithmetic
        if (lvalue == value) {// ok, the double value is actually a long
          if (lvalue != 0L) {
            this.append(lvalue);
          }
          return;// and we are done
        }
      }
    }

    if (state == BasicNumber.STATE_EMPTY) {// it's the first number
      this.m_double = value;
      this.m_compensation = 0d;
      this.m_state = BasicNumber.STATE_DOUBLE;
      return;
    }

    // If we arrive here, then value is neither NaN, nor infinite, nor a
    // long, so we can do double arithmetic as usual.
    if ((state == BasicNumber.STATE_POSITIVE_INFINITY)
        || (state == BasicNumber.STATE_NEGATIVE_INFINITY)
        || (state == BasicNumber.STATE_NAN)) {
      return; // in this case, nothing needs to be done
    }

    // So our number is finite and the stored number is finite too (or
    // overflow/underflow).
    if (state == BasicNumber.STATE_INTEGER) {
      // If we are doing long arithmetic, we now need to switch over to
      // double arithmetic.
      this.__switchFromLongToDoubleArithmetic();
    }

    // Now we are doing double arithmetic, and are either in finite mode,
    // or underflow, or overflow.
    this.__plainDoubleAdd(value);
  }
}
