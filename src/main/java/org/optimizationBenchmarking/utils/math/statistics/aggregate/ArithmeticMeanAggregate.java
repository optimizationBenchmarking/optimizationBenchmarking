package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Div;

/**
 * An aggregate for the arithmetic mean.
 */
public final class ArithmeticMeanAggregate extends _StatefulNumber {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the number of elements collected */
  long m_count;

  /** the minimum */
  private final MinimumAggregate m_minimum;
  /** the maximum */
  private final MaximumAggregate m_maximum;
  /** the sum */
  private final StableSum m_sum;

  /** create */
  public ArithmeticMeanAggregate() {
    super();
    this.m_minimum = new MinimumAggregate();
    this.m_maximum = new MaximumAggregate();
    this.m_sum = new StableSum();
  }

  /** {@inheritDoc} */
  @Override
  public final void reset() {
    super.reset();
    this.m_minimum.reset();
    this.m_maximum.reset();
    this.m_sum.reset();
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final long value) {
    this.m_minimum.append(value);
    this.m_maximum.append(value);
    this.m_sum.append(value);
    this.m_count++;
    this.m_state = BasicNumber.STATE_EMPTY;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final double value) {
    this.m_minimum.append(value);
    this.m_maximum.append(value);
    this.m_sum.append(value);
    this.m_count++;
    this.m_state = BasicNumber.STATE_EMPTY;
  }

  /**
   * set a long value
   *
   * @param lres
   *          the long value
   */
  private final void __setLong(final long lres) {
    long use, ltest;
    double dtest;

    use = lres;
    switch (this.m_minimum.getState()) {

      case STATE_INTEGER: {
        ltest = this.m_minimum.longValue();
        if (ltest > use) {
          use = ltest;
        }
        break;
      }

      case STATE_DOUBLE: {
        dtest = this.m_minimum.doubleValue();
        if (dtest > use) {
          this.__setDouble(dtest);
          return;
        }
        break;
      }

      case STATE_NEGATIVE_INFINITY:
      case STATE_NEGATIVE_OVERFLOW: {
        break;
      }

      default: {
        // STATE_EMPTY, STATE_NAN, STATE_POSITIVE_INFINITY,
        // STATE_POSITIVE_OVERFLOW
        this._setNaN();
        return;
      }
    }

    switch (this.m_maximum.getState()) {

      case STATE_INTEGER: {
        ltest = this.m_maximum.longValue();
        if (ltest < use) {
          if (use != lres) {
            this._setNaN();
            return;
          }
          use = ltest;
        }
        break;
      }

      case STATE_DOUBLE: {
        dtest = this.m_maximum.doubleValue();
        if (dtest < use) {
          if (use != lres) {
            this._setNaN();
            return;
          }
          this.__setDouble(dtest);
          return;
        }
        break;
      }

      case STATE_POSITIVE_INFINITY:
      case STATE_POSITIVE_OVERFLOW: {
        break;
      }

      default: {
        // STATE_EMPTY, STATE_NAN, STATE_NEGATIVE_INFINITY,
        // STATE_NEGATIVE_OVERFLOW
        this._setNaN();
        return;
      }
    }

    this.m_state = BasicNumber.STATE_INTEGER;
    this.m_long = use;
    this.m_double = use;
  }

  /**
   * set a double value
   *
   * @param dres
   *          the long value
   */
  private final void __setDouble(final double dres) {
    double use, dtest;
    long ltest;

    use = dres;

    switch (this.m_minimum.getState()) {

      case STATE_INTEGER: {
        ltest = this.m_minimum.longValue();
        if (ltest > use) {
          this.__setLong(ltest);
          return;
        }
        break;
      }

      case STATE_DOUBLE: {
        dtest = this.m_minimum.doubleValue();
        if (dtest > use) {
          use = dtest;
        }
        break;
      }

      case STATE_NEGATIVE_INFINITY:
      case STATE_NEGATIVE_OVERFLOW: {
        break;
      }
      default: {
        // STATE_EMPTY, STATE_NAN, STATE_POSITIVE_INFINITY,
        // STATE_POSITIVE_OVERFLOW
        this._setNaN();
        return;
      }
    }

    switch (this.m_maximum.getState()) {

      case STATE_INTEGER: {
        ltest = this.m_maximum.longValue();
        if (ltest < use) {
          if (use != dres) {
            this._setNaN();
            return;
          }
          this.__setLong(ltest);
          return;
        }
        break;
      }

      case STATE_DOUBLE: {
        dtest = this.m_maximum.doubleValue();
        if (dtest < use) {
          if (use != dres) {
            this._setNaN();
            return;
          }
          use = dtest;
        }
        break;
      }

      case STATE_POSITIVE_INFINITY:
      case STATE_POSITIVE_OVERFLOW: {
        break;
      }

      default: {
        // STATE_EMPTY, STATE_NAN, STATE_NEGATIVE_INFINITY,
        // STATE_NEGATIVE_OVERFLOW
        this._setNaN();
        return;
      }
    }

    this.m_state = BasicNumber.STATE_DOUBLE;
    this.m_double = use;
    this.m_long = ((long) use);
  }

  /** compute the internal value */
  @SuppressWarnings("incomplete-switch")
  final void _compute() {
    final long count;
    long lsum, lres;
    double dres, dsum;

    count = this.m_count;
    if (count > 0L) {

      switch (this.m_sum.getState()) {

        case STATE_INTEGER: {
          lsum = this.m_sum.longValue();
          lres = (lsum / count);
          if ((lres * count) == lsum) {
            this.__setLong(lres);
            return;
          }
          dres = Div.INSTANCE.computeAsDouble(lsum, count);
          if (NumericalTypes.isLong(dres)) {
            this.__setLong((long) dres);
            return;
          }
          this.__setDouble(dres);
          return;
        }

        case STATE_DOUBLE: {
          dsum = this.m_sum.doubleValue();
          dres = Div.INSTANCE.computeAsDouble(dsum, count);
          if (NumericalTypes.isLong(dres)) {
            this.__setLong((long) dres);
            return;
          }
          this.__setDouble(dres);
          return;
        }

        case STATE_NEGATIVE_INFINITY: {
          if (this.m_minimum.getState() == BasicNumber.STATE_NEGATIVE_INFINITY) {
            switch (this.m_maximum.getState()) {
              case STATE_NEGATIVE_INFINITY:
              case STATE_NEGATIVE_OVERFLOW:
              case STATE_INTEGER:
              case STATE_DOUBLE: {
                this._setNegativeInfinity();
                return;
              }
            }
          }
          this._setNaN();
          return;
        }

        case STATE_POSITIVE_INFINITY: {
          if (this.m_maximum.getState() == BasicNumber.STATE_POSITIVE_INFINITY) {
            switch (this.m_minimum.getState()) {
              case STATE_POSITIVE_INFINITY:
              case STATE_POSITIVE_OVERFLOW:
              case STATE_INTEGER:
              case STATE_DOUBLE: {
                this._setPositiveInfinity();
                return;
              }
            }
          }
          this._setNaN();
          return;
        }

        case STATE_NEGATIVE_OVERFLOW: {
          switch (this.m_minimum.getState()) {
            case STATE_NEGATIVE_INFINITY:
            case STATE_NEGATIVE_OVERFLOW:
            case STATE_INTEGER:
            case STATE_DOUBLE: {
              switch (this.m_maximum.getState()) {
                case STATE_NEGATIVE_OVERFLOW:
                case STATE_INTEGER:
                case STATE_DOUBLE: {
                  this._setNegativeOverflow();
                  return;
                }
              }
            }
          }
          this._setNaN();
          return;
        }

        case STATE_POSITIVE_OVERFLOW: {
          switch (this.m_maximum.getState()) {
            case STATE_POSITIVE_INFINITY:
            case STATE_POSITIVE_OVERFLOW:
            case STATE_INTEGER:
            case STATE_DOUBLE: {
              switch (this.m_minimum.getState()) {
                case STATE_POSITIVE_OVERFLOW:
                case STATE_INTEGER:
                case STATE_DOUBLE: {
                  this._setPositiveOverflow();
                  return;
                }
              }
            }
          }
          this._setNaN();
          return;
        }
      }

      this._setNaN();
    } else {
      this._setEmpty();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int getState() {
    if (this.m_state == BasicNumber.STATE_EMPTY) {
      this._compute();
    }
    return this.m_state;
  }

  /** {@inheritDoc} */
  @Override
  public final long longValue() {
    if (this.m_state == BasicNumber.STATE_EMPTY) {
      this._compute();
    }
    return this.m_long;
  }

  /** {@inheritDoc} */
  @Override
  public final double doubleValue() {
    if (this.m_state == BasicNumber.STATE_EMPTY) {
      this._compute();
    }
    return this.m_double;
  }
}
