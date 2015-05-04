package org.optimizationBenchmarking.utils.math.units;

import java.math.BigDecimal;
import java.util.HashMap;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.math.Rational;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;

/**
 * A factory class for building conversion matrixes.
 *
 * @param <E>
 *          the enum type
 */
public class ConversionMatrixBuilder<E extends Enum<E>> extends FSM {
  /** the matrix is currently being compiled */
  private static final int STATE_COMPILING = 1;

  /** the matrix has been compiled */
  private static final int STATE_COMPILED = (ConversionMatrixBuilder.STATE_COMPILING + 1);

  /** the conversion factors */
  private Number[][] m_factors;

  /**
   * Create the conversion matrix builder
   *
   * @param count
   *          the number of elements
   */
  public ConversionMatrixBuilder(final int count) {
    super();
    this.m_factors = new Number[count][count];
  }

  /**
   * Set a conversion factor between two elements. Setting the factor for
   * <code>from={@link org.optimizationBenchmarking.utils.math.units.ELength#METER}</code>
   * and
   * <code>to={@link org.optimizationBenchmarking.utils.math.units.ELength#MILLIMETER}</code>
   * to <code>1000</code> means that
   * <code>1 {@link org.optimizationBenchmarking.utils.math.units.ELength#M}</code>
   * equals
   * <code>1000 {@link org.optimizationBenchmarking.utils.math.units.ELength#MM}</code>
   * .
   *
   * @param from
   *          the source unit
   * @param to
   *          the target unit
   * @param factor
   *          the factor
   * @param checkOther
   *          check the other side
   * @return {@code true} if something has changed, {@code false} otherwise
   */
  private final int __setFactor(final int from, final int to,
      final Number factor, final boolean checkOther) {
    Number use, old;
    BigDecimal b;
    double d;
    long l;
    Rational r;
    int ret;

    if (from == to) {
      throw new IllegalArgumentException();
    }
    if (factor == null) {
      throw new IllegalArgumentException();
    }

    this.fsmStateAssert(EComparison.LESS_OR_EQUAL,
        ConversionMatrixBuilder.STATE_COMPILING);

    use = factor;
    old = this.m_factors[from][to];
    ret = 0;

    // <double>
    testDouble: if (factor instanceof Double) {

      d = use.doubleValue();
      if (Double.isNaN(d) || Double.isInfinite(d) || (d == 0d)) {
        return 0;
      }

      if ((d >= Long.MIN_VALUE) && (d <= Long.MAX_VALUE)) {
        l = ((long) d);
        if (l == d) {
          use = Long.valueOf(l);
          break testDouble;
        }
      }

      r = Rational.valueOf(d);
      if (r.isReal() && (r.doubleValue() == d)) {
        use = r;
        break testDouble;
      }

      if ((old == null)
          || ((old instanceof Double) && (old.toString().length() > use
              .toString().length()))) {
        this.m_factors[from][to] = use;
        ret = 1;
      }
      if (checkOther) {
        if (this.__setFactor(to, from, Double.valueOf(1d / d), false) != 0) {
          ret |= 2;
        }
      }

      return ret;
    }
    // </double>

    // <BigDecimal>
    testBD: if (use instanceof BigDecimal) {
      b = ((BigDecimal) use);

      if (b.equals(BigDecimal.ZERO)) {
        return 0;
      }

      try {
        l = b.longValueExact();
        use = Long.valueOf(l);
        break testBD;
      } catch (final ArithmeticException ae) {
        // ignore
      }

      b = b.stripTrailingZeros();
      if ((old == null)
          || (old instanceof Double)
          || ((old instanceof BigDecimal) && (b.toEngineeringString()
              .length() < ((BigDecimal) old).toEngineeringString()
              .length()))) {
        this.m_factors[from][to] = b;
        ret = 1;
      }
      if (checkOther) {
        try {
          b = BigDecimal.ONE.divide(b);
          if (this.__setFactor(to, from, b, false) != 0) {
            ret |= 2;
          }
        } catch (final ArithmeticException ae) {
          // ignore
        }
      }

      return ret;
    }
    // </BigDecimal>

    // <Rational>
    checkR: if (use instanceof Rational) {
      r = ((Rational) use);

      if (!(r.isReal())) {
        return 0;
      }

      if (r.isInteger()) {
        use = Long.valueOf(r.longValue());
        break checkR;
      }

      if (checkOther) {
        if (this.__setFactor(to, from, r.inverse(), false) != 0) {
          ret = 2;
        }
      }

      if (old instanceof Long) {
        return ret;
      }
      if (old instanceof Rational) {
        // if (r.toString().length() >= old.toString().length()) {
        return ret;
        // }
      }

      this.m_factors[from][to] = r;
      return (ret | 1);
    }
    // </Rational>

    // <Long>
    if (use instanceof Long) {

      l = use.longValue();
      if (l == 0L) {
        return 0;
      }

      if (checkOther) {
        if (this.__setFactor(to, from, Rational.valueOf(1L, l), false) != 0) {
          ret = 2;
        }
      }

      if ((old == null) || (!(old instanceof Long))) {
        this.m_factors[from][to] = use;
        ret |= 1;
      }
      return ret;
    }
    // </Long>

    return 0;
  }

  /**
   * Set a conversion factor between two elements. Setting the factor for
   * <code>from={@link org.optimizationBenchmarking.utils.math.units.ELength#METER}</code>
   * and
   * <code>to={@link org.optimizationBenchmarking.utils.math.units.ELength#MILLIMETER}</code>
   * to <code>1000</code> means that
   * <code>1 {@link org.optimizationBenchmarking.utils.math.units.ELength#M}</code>
   * equals
   * <code>1000 {@link org.optimizationBenchmarking.utils.math.units.ELength#MM}</code>
   * .
   *
   * @param from
   *          the source unit
   * @param to
   *          the target unit
   * @param factor
   *          the factor
   */
  public final void setFactor(final E from, final E to, final long factor) {
    this.fsmStateAssert(FSM.STATE_NOTHING);
    this.__setFactor(from.ordinal(), to.ordinal(), Long.valueOf(factor),
        true);
  }

  /**
   * Set a conversion factor between two elements. Setting the factor for
   * <code>from={@link org.optimizationBenchmarking.utils.math.units.ELength#METER}</code>
   * and
   * <code>to={@link org.optimizationBenchmarking.utils.math.units.ELength#MILLIMETER}</code>
   * to <code>1000</code> means that
   * <code>1 {@link org.optimizationBenchmarking.utils.math.units.ELength#M}</code>
   * equals
   * <code>1000 {@link org.optimizationBenchmarking.utils.math.units.ELength#MM}</code>
   * .
   *
   * @param from
   *          the source unit
   * @param to
   *          the target unit
   * @param factor
   *          the factor
   */
  public final void setFactor(final E from, final E to,
      final Rational factor) {
    this.fsmStateAssert(FSM.STATE_NOTHING);
    this.__setFactor(from.ordinal(), to.ordinal(), factor, true);
  }

  /**
   * Set a conversion factor between two elements. Setting the factor for
   * <code>from={@link org.optimizationBenchmarking.utils.math.units.ELength#METER}</code>
   * and
   * <code>to={@link org.optimizationBenchmarking.utils.math.units.ELength#MILLIMETER}</code>
   * to <code>1000</code> means that
   * <code>1 {@link org.optimizationBenchmarking.utils.math.units.ELength#M}</code>
   * equals
   * <code>1000 {@link org.optimizationBenchmarking.utils.math.units.ELength#MM}</code>
   * .
   *
   * @param from
   *          the source unit
   * @param to
   *          the target unit
   * @param factor
   *          the factor
   */
  public final void setFactor(final E from, final E to,
      final BigDecimal factor) {
    this.fsmStateAssert(FSM.STATE_NOTHING);
    this.__setFactor(from.ordinal(), to.ordinal(), factor, true);
  }

  /**
   * Set a conversion factor between two elements. Setting the factor for
   * <code>from={@link org.optimizationBenchmarking.utils.math.units.ELength#METER}</code>
   * and
   * <code>to={@link org.optimizationBenchmarking.utils.math.units.ELength#MILLIMETER}</code>
   * to <code>1000</code> means that
   * <code>1 {@link org.optimizationBenchmarking.utils.math.units.ELength#M}</code>
   * equals
   * <code>1000 {@link org.optimizationBenchmarking.utils.math.units.ELength#MM}</code>
   * .
   *
   * @param from
   *          the source unit
   * @param to
   *          the target unit
   * @param factor
   *          the factor
   */
  public final void setFactor(final E from, final E to, final double factor) {
    this.fsmStateAssert(FSM.STATE_NOTHING);
    this.__setFactor(from.ordinal(), to.ordinal(), Double.valueOf(factor),
        true);
  }

  /**
   * Multiply two numbers
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @return the result
   */
  private static final Number __mul(final Number a, final Number b) {
    final long l;
    Rational r, rb, ra;
    BigDecimal bda, bdb;

    if (a instanceof Long) {
      l = a.longValue();

      if (b instanceof Long) {
        return ConversionMatrixBuilder.__mul(l, b.longValue());
      }

      if (b instanceof Rational) {
        rb = ((Rational) b);
        r = rb.multiply(l);
        if (r.isReal()) {
          return r;
        }

        try {
          return rb.multiply(BigDecimal.valueOf(l));
        } catch (final ArithmeticException ae) {
          //
        }

        return Double.valueOf(rb.multiply((double) l));
      }

      if (b instanceof BigDecimal) {
        return ((BigDecimal) b).multiply(BigDecimal.valueOf(l));
      }

      return Double.valueOf(l * b.doubleValue());
    }

    if (b instanceof Long) {
      return ConversionMatrixBuilder.__mul(b, a);
    }

    if (a instanceof Rational) {
      ra = ((Rational) a);

      if (b instanceof Rational) {
        rb = ((Rational) b);
        r = ra.multiply(rb);
        if (r.isReal()) {
          return r;
        }
        return Double.valueOf(ra.doubleValue() * rb.doubleValue());
      }

      return Double.valueOf(ra.multiply(b.doubleValue()));
    }

    if (b instanceof Rational) {
      return ConversionMatrixBuilder.__mul(b, a);
    }

    if (a instanceof BigDecimal) {
      bda = ((BigDecimal) a);
      if (b instanceof BigDecimal) {
        bdb = ((BigDecimal) b);
        return bda.multiply(bdb);
      }

      return Double.valueOf(a.doubleValue() * b.doubleValue());
    }

    if (b instanceof BigDecimal) {
      return ConversionMatrixBuilder.__mul(b, a);
    }

    return Double.valueOf(a.doubleValue() * b.doubleValue());
  }

  /**
   * Multiply two longs
   *
   * @param a
   *          the first long
   * @param b
   *          the second long
   * @return the result
   */
  private static final Number __mul(final long a, final long b) {
    if (a > b) {
      return ConversionMatrixBuilder.__mul(b, a);
    }
    if (a < 0L) {
      if (b < 0L) {
        if (a >= (Long.MAX_VALUE / b)) {
          return Long.valueOf(a * b);
        }
        return new BigDecimal(a).multiply(new BigDecimal(b));
      }

      if (b > 0) {
        if ((Long.MIN_VALUE / b) <= a) {
          return Long.valueOf(a * b);
        }
        return new BigDecimal(a).multiply(new BigDecimal(b));
      }
      return Long.valueOf(0);
    }

    if (a > 0) {
      if (a <= (Long.MAX_VALUE / b)) {
        return Long.valueOf(a * b);
      }
      return new BigDecimal(a).multiply(new BigDecimal(b));
    }
    return Long.valueOf(0);
  }

  /**
   * Compile this conversion matrix
   *
   * @return the compiled matrix
   */
  public final UnaryFunction[][] compile() {
    final UnaryFunction[][] res;
    final Number[][] factors;
    final int[][] changed;
    final int len;
    final HashMap<Number, UnaryFunction> map;
    int iteration;
    UnaryFunction f, g;
    int i, j, k, change, firstChange, secondChange, changeSum, maxChange, limitChange;
    boolean improved;
    Number a, b;

    this.fsmStateAssertAndSet(FSM.STATE_NOTHING,
        ConversionMatrixBuilder.STATE_COMPILING);

    len = this.m_factors.length;
    changed = new int[len][len];
    iteration = 0;
    i = 0;
    for (final Number[] nums : this.m_factors) {
      j = 0;
      for (final Number num : nums) {
        if (num != null) {
          changed[i][j] = 1;
        }
        j++;
      }
      i++;
    }

    do {
      improved = false;
      iteration++;

      limitChange = (iteration << 1);
      findSmallestStep: for (maxChange = iteration; (++maxChange) <= limitChange;) {

        for (i = 0; i < len; i++) {
          for (j = 0; j < len; j++) {
            if (i == j) {
              continue;
            }

            a = this.m_factors[i][j];
            if (a == null) {
              continue;
            }
            firstChange = changed[i][j];

            for (k = 0; k < len; k++) {
              if ((k == j) || (k == i)) {
                continue;
              }
              b = this.m_factors[j][k];
              if (b == null) {
                continue;
              }
              secondChange = changed[j][k];
              changeSum = (firstChange + secondChange);
              if ((changeSum > iteration) && (changeSum <= maxChange)) {

                change = this.__setFactor(i, k,
                    ConversionMatrixBuilder.__mul(a, b), true);

                if (change != 0) {
                  improved = true;
                  if ((change & 1) != 0) {
                    changed[i][k] = (iteration + 1);
                  }
                  if ((change & 2) != 0) {
                    changed[k][i] = (iteration + 1);
                  }
                }
              }
            }
          }
        }
        if (improved) {
          break findSmallestStep;
        }
      }

    } while (improved);

    this.fsmStateAssertAndSet(ConversionMatrixBuilder.STATE_COMPILING,
        ConversionMatrixBuilder.STATE_COMPILED);
    factors = this.m_factors;
    this.m_factors = null;
    res = new UnaryFunction[factors.length][factors.length];
    map = new HashMap<>();

    for (i = factors.length; (--i) >= 0;) {
      for (j = factors.length; (--j) >= 0;) {
        if (i == j) {
          res[i][j] = Identity.INSTANCE;
        } else {
          a = factors[i][j];
          f = map.get(a);
          if (f == null) {
            f = _ConversionFunction._get(a);
            if (f instanceof _ConvertDoubleMul) {
              b = Double.valueOf(((_ConvertDoubleMul) f).m_multiplier);
              if (!(b.equals(a))) {
                g = map.get(b);
                if (g != null) {
                  f = g;
                } else {
                  map.put(b, f);
                }
              }
            }
            map.put(a, f);
          }
          res[i][j] = f;
        }
      }
    }

    for (i = factors.length; (--i) > 0;) {
      for (j = i; (--j) >= 0;) {
        f = res[i][j];
        g = res[j][i];
        if (f instanceof _ConversionFunction) {
          ((_ConversionFunction) f).m_inverse = g;
        }
        if (g instanceof _ConversionFunction) {
          ((_ConversionFunction) g).m_inverse = f;
        }
      }
    }

    return res;
  }
}
