package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Div;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.SaturatingSub;

/**
 * An aggregate for the variance, implemented from
 * http://en.wikipedia.org/wiki/Algorithms_for_calculating_variance with
 * extensions to use our stable sums and mean aggregate for maximum
 * precision.
 */
public final class VarianceAggregate extends _StatefulNumber {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the internal aggregate for computing the mean */
  private final ArithmeticMeanAggregate m_mean;

  /** the stable sum for the second moment */
  private final StableSum m_M2;

  /** create */
  public VarianceAggregate() {
    super();
    this.m_mean = new ArithmeticMeanAggregate();
    this.m_M2 = new StableSum();
  }

  /** {@inheritDoc} */
  @Override
  public final void reset() {
    super.reset();
    this.m_mean.reset();
    this.m_M2.reset();
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final long value) {
    boolean integerOldDelta, integerNewDelta;
    long lOldDelta, lNewDelta, lTemp;
    double dOldDelta, dNewDelta;

    if ((this.m_state == BasicNumber.STATE_POSITIVE_OVERFLOW)
        || (this.m_state == BasicNumber.STATE_POSITIVE_INFINITY)
        || (this.m_state == BasicNumber.STATE_NAN)) {
      return;
    }

    lOldDelta = lNewDelta = Long.MAX_VALUE;
    dOldDelta = dNewDelta = Double.NaN;

    if (this.m_mean.m_count <= 0L) {
      lOldDelta = value;
      integerOldDelta = true;
    } else {
      // compute the delta = (x-old mean)
      switch (this.m_mean.m_state) {

        case STATE_INTEGER: {
          lTemp = this.m_mean.m_long;
          if (SaturatingSub.getOverflowType(value, lTemp) == 0) {
            lOldDelta = (value - lTemp);
            integerOldDelta = true;
          } else {
            dOldDelta = (((double) value) - ((double) (lTemp)));
            if (NumericalTypes.isLong(dOldDelta)) {
              lOldDelta = ((long) dOldDelta);
              integerOldDelta = true;
            } else {
              integerOldDelta = false;
            }
          }
          break;
        }

        case STATE_DOUBLE: {
          dOldDelta = (value - this.m_mean.m_double);
          if (NumericalTypes.isLong(dOldDelta)) {
            lOldDelta = ((long) dOldDelta);
            integerOldDelta = true;
          } else {
            integerOldDelta = false;
          }
          break;
        }

        case STATE_POSITIVE_INFINITY:
        case STATE_NEGATIVE_INFINITY: {
          this._setPositiveOverflow();
          return;
        }
        case STATE_POSITIVE_OVERFLOW:
        case STATE_NEGATIVE_OVERFLOW: {
          this._setPositiveInfinity();
          return;
        }

        default: {
          this.m_M2._setNaN();
          this._setNaN();
          return;
        }
      }
    }

    // update the mean
    this.m_mean.append(value);
    this.m_mean._compute();

    // compute the delta = (x-new mean)
    switch (this.m_mean.m_state) {

      case STATE_INTEGER: {
        lTemp = this.m_mean.m_long;
        if (SaturatingSub.getOverflowType(value, lTemp) == 0) {
          lNewDelta = (value - lTemp);
          integerNewDelta = true;
        } else {
          dNewDelta = (((double) value) - ((double) (lTemp)));
          if (NumericalTypes.isLong(dNewDelta)) {
            lNewDelta = ((long) dNewDelta);
            integerNewDelta = true;
          } else {
            integerNewDelta = false;
          }
        }
        break;
      }

      case STATE_DOUBLE: {
        dNewDelta = (value - this.m_mean.m_double);
        if (NumericalTypes.isLong(dNewDelta)) {
          lNewDelta = ((long) dNewDelta);
          integerNewDelta = true;
        } else {
          integerNewDelta = false;
        }
        break;
      }

      case STATE_POSITIVE_INFINITY:
      case STATE_NEGATIVE_INFINITY: {
        this._setPositiveOverflow();
        return;
      }
      case STATE_POSITIVE_OVERFLOW:
      case STATE_NEGATIVE_OVERFLOW: {
        this._setPositiveInfinity();
        return;
      }

      default: {
        this.m_M2._setNaN();
        this._setNaN();
        return;
      }
    }

    compute: {
      if (integerOldDelta) {
        if (integerNewDelta) {
          lTemp = (lNewDelta * lOldDelta);
          if ((lTemp / lOldDelta) == lNewDelta) {
            this.m_M2.append(lTemp);
            break compute;
          }
          dNewDelta = lNewDelta;
        }
        dOldDelta = lOldDelta;
      } else {
        if (integerNewDelta) {
          dNewDelta = lNewDelta;
        }
      }

      this.m_M2.append(dOldDelta * dNewDelta);
    }

    this.m_state = BasicNumber.STATE_EMPTY;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final double value) {
    boolean integerOldDelta, integerNewDelta;
    long lOldDelta, lNewDelta, lTemp;
    double dOldDelta, dNewDelta;

    if ((this.m_state == BasicNumber.STATE_POSITIVE_OVERFLOW)
        || (this.m_state == BasicNumber.STATE_POSITIVE_INFINITY)
        || (this.m_state == BasicNumber.STATE_NAN)) {
      return;
    }

    lOldDelta = lNewDelta = Long.MAX_VALUE;
    dOldDelta = dNewDelta = Double.NaN;

    if (this.m_mean.m_count <= 0L) {
      if (NumericalTypes.isLong(value)) {
        lOldDelta = ((long) value);
        integerOldDelta = true;
      } else {
        dOldDelta = value;
        integerOldDelta = false;
      }
    } else {

      // compute the delta = (x-old mean)
      switch (this.m_mean.m_state) {
        case STATE_INTEGER: {
          lTemp = this.m_mean.m_long;
          dOldDelta = (value - lTemp);
          if (NumericalTypes.isLong(dOldDelta)) {
            lOldDelta = ((long) dOldDelta);
            integerOldDelta = true;
          } else {
            integerOldDelta = false;
          }
          break;
        }

        case STATE_DOUBLE: {
          dOldDelta = (value - this.m_mean.m_double);
          if (NumericalTypes.isLong(dOldDelta)) {
            lOldDelta = ((long) dOldDelta);
            integerOldDelta = true;
          } else {
            integerOldDelta = false;
          }
          break;
        }

        case STATE_POSITIVE_INFINITY:
        case STATE_NEGATIVE_INFINITY: {
          this._setPositiveOverflow();
          return;
        }
        case STATE_POSITIVE_OVERFLOW:
        case STATE_NEGATIVE_OVERFLOW: {
          this._setPositiveInfinity();
          return;
        }

        default: {
          this.m_M2._setNaN();
          this._setNaN();
          return;
        }
      }
    }

    // update the mean
    this.m_mean.append(value);
    this.m_mean._compute();

    // compute the delta = (x-new mean)
    switch (this.m_mean.m_state) {
      case STATE_INTEGER: {
        lTemp = this.m_mean.m_long;
        dNewDelta = (value - lTemp);
        if (NumericalTypes.isLong(dNewDelta)) {
          lNewDelta = ((long) dNewDelta);
          integerNewDelta = true;
        } else {
          integerNewDelta = false;
        }
        break;
      }

      case STATE_DOUBLE: {
        dNewDelta = (value - this.m_mean.m_double);
        if (NumericalTypes.isLong(dNewDelta)) {
          lNewDelta = ((long) dNewDelta);
          integerNewDelta = true;
        } else {
          integerNewDelta = false;
        }
        break;
      }

      case STATE_POSITIVE_INFINITY:
      case STATE_NEGATIVE_INFINITY: {
        this._setPositiveOverflow();
        return;
      }
      case STATE_POSITIVE_OVERFLOW:
      case STATE_NEGATIVE_OVERFLOW: {
        this._setPositiveInfinity();
        return;
      }

      default: {
        this.m_M2._setNaN();
        this._setNaN();
        return;
      }
    }

    compute: {
      if (integerOldDelta) {
        if (integerNewDelta) {
          lTemp = (lNewDelta * lOldDelta);
          if ((lTemp / lOldDelta) == lNewDelta) {
            this.m_M2.append(lTemp);
            break compute;
          }
          dNewDelta = lNewDelta;
        }
        dOldDelta = lOldDelta;
      } else {
        if (integerNewDelta) {
          dNewDelta = lNewDelta;
        }
      }

      this.m_M2.append(dOldDelta * dNewDelta);
    }

    this.m_state = BasicNumber.STATE_EMPTY;
  }

  /** compute the value */
  private final void __compute() {
    long count;
    long lM2;
    double dTemp;

    count = this.m_mean.m_count;
    if (count > 0L) {
      if (count > 1L) {
        count--;

        switch (this.m_M2.getState()) {
          case STATE_INTEGER: {
            lM2 = this.m_M2.longValue();
            if ((lM2 % count) == 0) {
              this.m_double = this.m_long = Math.max(0L, (lM2 / count));
              this.m_state = BasicNumber.STATE_INTEGER;
              return;
            }

            dTemp = Div.INSTANCE.computeAsDouble(lM2, count);
            this.m_double = dTemp;
            this.m_long = ((long) dTemp);
            if (NumericalTypes.isLong(dTemp)) {
              this.m_state = BasicNumber.STATE_INTEGER;
            } else {
              this.m_state = BasicNumber.STATE_DOUBLE;
            }
            return;
          }

          case STATE_DOUBLE: {

            dTemp = Math.max(0d, Div.INSTANCE.computeAsDouble(
                this.m_M2.doubleValue(), count));
            this.m_double = dTemp;
            this.m_long = ((long) dTemp);
            if (NumericalTypes.isLong(dTemp)) {
              this.m_state = BasicNumber.STATE_INTEGER;
            } else {
              this.m_state = BasicNumber.STATE_DOUBLE;
            }
            return;
          }

          default: {
            this._setNaN();
          }
        }
      } else {
        this.m_state = BasicNumber.STATE_INTEGER;
        this.m_long = 0L;
        this.m_double = 0d;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int getState() {
    if (this.m_state == BasicNumber.STATE_EMPTY) {
      this.__compute();
    }
    return this.m_state;
  }

  /** {@inheritDoc} */
  @Override
  public final long longValue() {
    if (this.m_state == BasicNumber.STATE_EMPTY) {
      this.__compute();
    }
    return this.m_long;
  }

  /** {@inheritDoc} */
  @Override
  public final double doubleValue() {
    if (this.m_state == BasicNumber.STATE_EMPTY) {
      this.__compute();
    }
    return this.m_double;
  }
}
