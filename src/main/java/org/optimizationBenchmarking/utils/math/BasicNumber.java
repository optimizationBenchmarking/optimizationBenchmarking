package org.optimizationBenchmarking.utils.math;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * An abstract base class for numbers. This class allows for a more
 * explicit distinction between states such as &quot;infinite&quot;,
 * &quot;integer number&quot;, &quot;finite real number&quot;, and
 * &quot;overflow&quot;. It presents an extensible set of constants that
 * may be used by subclasses to express their numerical state.
 */
public abstract class BasicNumber extends Number implements
    Comparable<Number>, ITextable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * The empty state: the number has no value.
   *
   * @see #getState()
   */
  public static final int STATE_EMPTY = 0;

  /**
   * The {@code long} state: the number is a valid integer ({@code long}).
   * Only in this state, {@link #isInteger()} will return {@code true}.
   * {@link #isReal()} will return {@code true} as well.
   *
   * @see #getState()
   */
  public static final int STATE_INTEGER = (BasicNumber.STATE_EMPTY + 1);
  /**
   * The {@code double} state: the number is a finite and well-defined
   * {@code double}. {@link #isReal()} will return {@code true} only in
   * this state and in state {@link #STATE_INTEGER}.
   *
   * @see #getState()
   */
  public static final int STATE_DOUBLE = (BasicNumber.STATE_INTEGER + 1);

  /**
   * The number has overflown, i.e., left the valid range of {@code double}
   * towards positive infinity.
   *
   * @see #getState()
   */
  public static final int STATE_POSITIVE_OVERFLOW = (BasicNumber.STATE_DOUBLE + 1);
  /**
   * The positive infinity state: the number corresponds to
   * {@link java.lang.Double#POSITIVE_INFINITY}.
   *
   * @see #getState()
   */
  public static final int STATE_POSITIVE_INFINITY = (BasicNumber.STATE_POSITIVE_OVERFLOW + 1);

  /**
   * The number has underflown, i.e., left the valid range of
   * {@code double} towards negative infinity.
   *
   * @see #getState()
   */
  public static final int STATE_NEGATIVE_OVERFLOW = (BasicNumber.STATE_POSITIVE_INFINITY + 1);
  /**
   * The negative infinity state: the number corresponds to
   * {@link java.lang.Double#NEGATIVE_INFINITY}.
   *
   * @see #getState()
   */
  public static final int STATE_NEGATIVE_INFINITY = (BasicNumber.STATE_NEGATIVE_OVERFLOW + 1);
  /**
   * The not-a-number state: the number corresponds to
   * {@link java.lang.Double#NaN}.
   *
   * @see #getState()
   */
  public static final int STATE_NAN = (BasicNumber.STATE_NEGATIVE_INFINITY + 1);

  /** the state names */
  static final String[] STATE_NAMES = {
      "empty", //$NON-NLS-1$
      null, null,
      "overflow",//$NON-NLS-1$
      Double.toString(Double.POSITIVE_INFINITY),
      "underflow",//$NON-NLS-1$
      Double.toString(Double.NEGATIVE_INFINITY),
      Double.toString(Double.NaN), };

  /** create */
  protected BasicNumber() {
    super();
  }

  /**
   * Obtain the number state. A number implementation may support any of
   * the states {@link #STATE_EMPTY}, {@link #STATE_INTEGER},
   * {@link #STATE_DOUBLE}, {@link #STATE_POSITIVE_OVERFLOW},
   * {@link #STATE_POSITIVE_INFINITY}, {@link #STATE_NEGATIVE_OVERFLOW},
   * {@link #STATE_NEGATIVE_INFINITY}, and {@link #STATE_NAN}. It may even
   * introduce own, new states. In a {@link #compareTo(Number) comparison},
   * new states must be considered to be between
   * {@link #STATE_POSITIVE_INFINITY} and {@link #STATE_EMPTY}.
   *
   * @return the number state
   * @see #STATE_EMPTY
   * @see #STATE_INTEGER
   * @see #STATE_DOUBLE
   * @see #STATE_POSITIVE_OVERFLOW
   * @see #STATE_POSITIVE_INFINITY
   * @see #STATE_NEGATIVE_OVERFLOW
   * @see #STATE_NEGATIVE_INFINITY
   * @see #STATE_NAN
   */
  public abstract int getState();

  /**
   * Get the sign of this number
   *
   * @return the sign of this number
   */
  public int sign() {
    final double d;
    switch (this.getState()) {
      case STATE_INTEGER: {
        return Long.signum(this.longValue());
      }
      case STATE_DOUBLE: {
        d = this.doubleValue();
        if (d < 0d) {
          return (-1);
        }
        if (d > 0d) {
          return 1;
        }
        return 0;
      }
      case STATE_NEGATIVE_OVERFLOW:
      case STATE_NEGATIVE_INFINITY: {
        return (-1);
      }
      case STATE_POSITIVE_OVERFLOW:
      case STATE_POSITIVE_INFINITY: {
        return 1;
      }
      default: {
        return 0;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public int intValue() {
    final long l;

    l = this.longValue();
    if (l <= Integer.MIN_VALUE) {
      return Integer.MIN_VALUE;
    }
    if (l >= Integer.MAX_VALUE) {
      return Integer.MAX_VALUE;
    }
    return ((int) l);
  }

  /** {@inheritDoc} */
  @Override
  public short shortValue() {
    final long l;

    l = this.longValue();
    if (l <= Short.MIN_VALUE) {
      return Short.MIN_VALUE;
    }
    if (l >= Short.MAX_VALUE) {
      return Short.MAX_VALUE;
    }
    return ((short) l);
  }

  /** {@inheritDoc} */
  @Override
  public byte byteValue() {
    final long l;

    l = this.longValue();
    if (l <= Byte.MIN_VALUE) {
      return Byte.MIN_VALUE;
    }
    if (l >= Byte.MAX_VALUE) {
      return Byte.MAX_VALUE;
    }
    return ((byte) l);
  }

  /** {@inheritDoc} */
  @Override
  public float floatValue() {
    return ((float) (this.doubleValue()));
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    int state;

    state = this.getState();
    if (state == BasicNumber.STATE_INTEGER) {
      return HashUtils.hashCode(this.longValue());
    }
    if (state == BasicNumber.STATE_DOUBLE) {
      return HashUtils.hashCode(this.doubleValue());
    }
    if (state > 2) {
      state -= 2;
    }
    return (Integer.MIN_VALUE + state);
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    final int stateA, stateB;
    final BasicNumber b;

    if (o == this) {
      return true;
    }

    if (o instanceof Number) {
      stateA = this.getState();

      if (o instanceof BasicNumber) {
        b = ((BasicNumber) o);

        stateB = b.getState();

        if (stateA == BasicNumber.STATE_INTEGER) {
          if (stateB == BasicNumber.STATE_INTEGER) {
            return (this.longValue() == b.longValue());
          }
          if (stateB == BasicNumber.STATE_DOUBLE) {
            return (this.longValue() == b.doubleValue());
          }
          return false;
        }

        if (stateA == BasicNumber.STATE_DOUBLE) {
          if (stateB == BasicNumber.STATE_INTEGER) {
            return (this.doubleValue() == b.longValue());
          }
          if (stateB == BasicNumber.STATE_DOUBLE) {
            return (this.doubleValue() == b.doubleValue());
          }
          return false;
        }

        return (stateA == stateB);
      }

      if (stateA == BasicNumber.STATE_INTEGER) {
        return (this.longValue() == ((Number) o).longValue());
      }
      return (EComparison.EQUAL.compare(this.doubleValue(),
          ((Number) o).doubleValue()));
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    final int state;

    state = this.getState();
    if (state == BasicNumber.STATE_INTEGER) {
      textOut.append(this.longValue());
    } else {
      if (state == BasicNumber.STATE_DOUBLE) {
        textOut.append(this.doubleValue());
      } else {
        textOut.append(BasicNumber.STATE_NAMES[state]);
      }
    }
  }

  /**
   * Compare this number to another one. This function may potentially
   * require lots of resources: Since there is no general to compare the
   * full range of possible {@code double} and {@code long} values. If we
   * encounter such a case, we convert both values to
   * {@link java.math.BigDecimal}s
   *
   * @param number
   *          the other number to compare with
   */
  @Override
  public int compareTo(final Number number) {
    final int stateA, stateB;
    final BasicNumber b;
    final double doubleA, doubleB;

    if (number == this) {
      return 0;
    }
    if (number == null) {
      return (-1);
    }

    stateA = this.getState();

    if (number instanceof BasicNumber) {
      b = ((BasicNumber) number);

      stateB = b.getState();

      switch (stateA) {

        case STATE_EMPTY: {
          if (stateB == BasicNumber.STATE_EMPTY) {
            return 0;
          }

          if (stateB == BasicNumber.STATE_NAN) {
            return (-1);
          }
          return 1;
        }

        case STATE_INTEGER: {
          switch (stateB) {
            case STATE_INTEGER: {
              return Long.compare(this.longValue(), b.longValue());
            }

            case STATE_DOUBLE: {
              return EComparison.compareLongToDouble(this.longValue(),
                  b.doubleValue());
            }

            case STATE_NEGATIVE_OVERFLOW:
            case STATE_NEGATIVE_INFINITY: {
              return 1;
            }

            default: {
              return (-1);
            }
          }
        }

        case STATE_DOUBLE: {
          switch (stateB) {

            case STATE_INTEGER: {
              return (-(EComparison.compareLongToDouble(b.longValue(),
                  this.doubleValue())));
            }

            case STATE_DOUBLE: {
              return EComparison.compareDoubles(this.doubleValue(),
                  b.doubleValue());
            }

            case STATE_NEGATIVE_OVERFLOW:
            case STATE_NEGATIVE_INFINITY: {
              return 1;
            }
            default: {
              return (-1);
            }
          }
        }

        case STATE_NEGATIVE_OVERFLOW: {
          switch (stateB) {
            case STATE_NEGATIVE_OVERFLOW: {
              return 0;
            }
            case STATE_NEGATIVE_INFINITY: {
              return 1;
            }
            default: {
              return (-1);
            }
          }
        }

        case STATE_NEGATIVE_INFINITY: {
          return ((stateB == BasicNumber.STATE_NEGATIVE_INFINITY) ? 0
              : (-1));
        }

        case STATE_POSITIVE_OVERFLOW: {
          switch (stateB) {
            case STATE_INTEGER:
            case STATE_DOUBLE:
            case STATE_NEGATIVE_OVERFLOW:
            case STATE_NEGATIVE_INFINITY: {
              return 1;
            }
            case STATE_POSITIVE_OVERFLOW: {
              return 0;
            }
            default: {
              return (-1);
            }
          }
        }

        case STATE_POSITIVE_INFINITY: {
          switch (stateB) {
            case STATE_INTEGER:
            case STATE_DOUBLE:
            case STATE_NEGATIVE_OVERFLOW:
            case STATE_NEGATIVE_INFINITY:
            case STATE_POSITIVE_OVERFLOW: {
              return 1;
            }
            case STATE_POSITIVE_INFINITY: {
              return 0;
            }
            default: {
              return (-1);
            }
          }
        }

        case STATE_NAN: {
          return 1;
        }

        default: {
          if ((stateB == BasicNumber.STATE_NAN)
              || (stateB == BasicNumber.STATE_EMPTY)) {
            return (-1);
          }
          return ((stateB == stateA) ? 0 : 1);
        }
      }
    }

    // a basic number and a long
    if ((NumericalTypes.getTypes(number) & NumericalTypes.IS_LONG) != 0) {
      switch (stateA) {

        case STATE_EMPTY: {
          return 1;
        }

        case STATE_INTEGER: {
          return Long.compare(this.longValue(), number.longValue());
        }

        case STATE_DOUBLE: {
          return (-(EComparison.compareLongToDouble(number.longValue(),
              this.doubleValue())));
        }

        case STATE_NEGATIVE_OVERFLOW:
        case STATE_NEGATIVE_INFINITY: {
          return (-1);
        }

        default: {
          return 1;
        }
      }
    }

    // a basic number and a double
    doubleB = number.doubleValue();
    switch (stateA) {
      case STATE_EMPTY: {
        return ((doubleB != doubleB) ? (-1) : 1);
      }
      case STATE_INTEGER: {
        return EComparison.compareLongToDouble(this.longValue(), doubleB);
      }
      case STATE_DOUBLE: {
        doubleA = this.doubleValue();
        if (doubleA < doubleB) {
          return (-1);
        }
        if (doubleA > doubleB) {
          return 1;
        }
        if (doubleA == doubleB) {
          return 0;
        }
        if (doubleA != doubleA) {
          return ((doubleB != doubleB) ? 0 : (1));
        }
        return (-1);
      }
      case STATE_NEGATIVE_OVERFLOW: {
        if (doubleB <= Double.NEGATIVE_INFINITY) {
          return 1;
        }
        return (-1);
      }
      case STATE_NEGATIVE_INFINITY: {
        if (doubleB <= Double.NEGATIVE_INFINITY) {
          return 0;
        }
        return (-1);
      }
      case STATE_POSITIVE_OVERFLOW: {
        if (doubleB >= Double.POSITIVE_INFINITY) {
          return 1;
        }
        return (-1);
      }
      case STATE_POSITIVE_INFINITY: {
        if (doubleB >= Double.POSITIVE_INFINITY) {
          return 0;
        }
        return (-1);
      }
      case STATE_NAN: {
        return ((doubleB != doubleB) ? 0 : 1);
      }
      default: {
        return ((doubleB != doubleB) ? (-1) : 1);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    final int state;

    state = this.getState();
    if (state == BasicNumber.STATE_INTEGER) {
      return Long.toString(this.longValue());
    }
    if (state == BasicNumber.STATE_DOUBLE) {
      return Double.toString(this.doubleValue());
    }
    return (BasicNumber.STATE_NAMES[state]);
  }

  /**
   * Does the number represent a valid, finite real number?
   *
   * @return {@code true} if <code>state=={@link #STATE_INTEGER}</code> or
   *         <code>state=={@link #STATE_DOUBLE}</code>
   */
  public boolean isReal() {
    final int state;
    state = this.getState();
    return ((state == BasicNumber.STATE_INTEGER) || (state == BasicNumber.STATE_DOUBLE));
  }

  /**
   * Does the number represent a valid, finite integer number?
   *
   * @return {@code true} if <code>state=={@link #STATE_INTEGER}</code>
   */
  public boolean isInteger() {
    return (this.getState() == BasicNumber.STATE_INTEGER);
  }

  /**
   * Transform this value into a constant {@link java.lang.Number}
   *
   * @return the {@link java.lang.Number} representing the value of this
   *         number
   */
  public Number toNumber() {
    final int type;
    final long lval;
    final double dval;

    switch (this.getState()) {
      case STATE_INTEGER: {
        lval = this.longValue();
        type = NumericalTypes.getTypes(lval);
        if ((type & NumericalTypes.IS_BYTE) != 0) {
          return Byte.valueOf((byte) lval);
        }
        if ((type & NumericalTypes.IS_SHORT) != 0) {
          return Short.valueOf((short) lval);
        }
        if ((type & NumericalTypes.IS_INT) != 0) {
          return Integer.valueOf((int) lval);
        }
        return Long.valueOf(lval);
      }
      case STATE_DOUBLE: {
        dval = this.doubleValue();
        type = NumericalTypes.getTypes(dval);
        if ((type & NumericalTypes.IS_BYTE) != 0) {
          return Byte.valueOf((byte) dval);
        }
        if ((type & NumericalTypes.IS_SHORT) != 0) {
          return Short.valueOf((short) dval);
        }
        if ((type & NumericalTypes.IS_INT) != 0) {
          return Integer.valueOf((int) dval);
        }
        if ((type & NumericalTypes.IS_LONG) != 0) {
          return Long.valueOf((long) dval);
        }
        if ((type & NumericalTypes.IS_FLOAT) != 0) {
          return Float.valueOf((float) dval);
        }
        return Double.valueOf(dval);
      }
      case STATE_NEGATIVE_OVERFLOW:
      case STATE_NEGATIVE_INFINITY: {
        return Float.valueOf(Float.NEGATIVE_INFINITY);
      }
      case STATE_POSITIVE_OVERFLOW:
      case STATE_POSITIVE_INFINITY: {
        return Float.valueOf(Float.POSITIVE_INFINITY);
      }
      default: {
        return Float.valueOf(Float.NaN);
      }
    }
  }
}
