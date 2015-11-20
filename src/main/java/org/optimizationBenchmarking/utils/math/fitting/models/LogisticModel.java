package org.optimizationBenchmarking.utils.math.fitting.models;

import java.util.Random;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.fitting.impl.SampleBasedParameterGuesser;
import org.optimizationBenchmarking.utils.math.fitting.spec.IParameterGuesser;
import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add3;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add4;
import org.optimizationBenchmarking.utils.math.functions.power.Exp;
import org.optimizationBenchmarking.utils.math.functions.power.Ln;
import org.optimizationBenchmarking.utils.math.functions.power.Pow;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.text.IMathRenderable;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * <p>
 * A model function which may be suitable to model how time-objective value
 * relationships of optimization processes behave. My student Qi Qi has
 * discovered this model: {@code a/(1+b*x^c)}. It is somewhat similar to a
 * logistic model and, in our tests, fits quite well. It is somewhat
 * similar to my previous exponential decay model {@code -(exp(a*(x^b))-1)}
 * but seems to fit better and does not require data normalization.
 * </p>
 * <h2>Derivatives</h2>
 * <p>
 * The derivatives have been obtained with
 * http://www.numberempire.com/derivativecalculator.php.
 * </p>
 * <ol>
 * <li>Original function: {@code a/(1+b*x^c)}</li>
 * <li>{@code d/da}: {@code 1/(1+b*x^c)}</li>
 * <li>{@code d/db}: {@code -(a*x^c)/         (1 + 2*b*x^c + b^2*x^(2*c))}
 * </li>
 * <li>{@code d/dc}: {@code -(a*b*x^c*log(x))/(1 + 2*b*x^c + b^2*x^(2*c))}
 * </li>
 * </ol>
 * <h2>Resolution</h2>
 * <h3>One Known Point</h3>
 * <ol>
 * <li>{@code a}: {@code a=(b*x^c+1)*y}</li>
 * <li>{@code b}: {@code b=-(y-a)/(x^c*y)}</li>
 * <li>{@code c}: {@code c=log(a/(b*y)-1/b)/log(x)},
 * {@code c = (log((a-y)/(b*y)))/(log(x))}</li>
 * </ol>
 * <h3>Two Known Points</h3>
 * <ol>
 * <li>{@code a}: {@code a=((x2^c-x1^c)*y1*y2)/(x2^c*y2-x1^c*y1)}</li>
 * <li>{@code b}: {@code b=-(y2-y1)/(x2^c*y2-x1^c*y1)}</li>
 * <li>{@code b}:
 * {@code b=exp((log(x1)*log(a-y2)-log(x2)*log(a-y1)-log(x1)*log(y2)+log(x2)*log(y1))/(log(x1)-log(x2)))}
 * </li>
 * </ol>
 */
public final class LogisticModel extends ParametricUnaryFunction {

  /** create */
  public LogisticModel() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double value(final double x, final double[] parameters) {
    double res;

    res = (1d + (parameters[1] + LogisticModel._pow(x, parameters[2])));
    if ((Math.abs(res) > 0d) && MathUtils.isFinite(res) && //
        MathUtils.isFinite(res = (parameters[0] / res))) {
      return res;
    }
    return 0d;
  }

  /**
   * Compute the value of the logistic model for a coordinate {@code x} and
   * model parameters {@code a}, {@code b}, and {@code c}.
   *
   * @param x
   *          the {@code x}-coordinate
   * @param a
   *          the first model parameter
   * @param b
   *          the second model parameter
   * @param c
   *          the third model parameter
   * @return the computed {@code y} value.
   */
  static final double _compute(final double x, final double a,
      final double b, final double c) {
    double res;

    res = (1d + (b + LogisticModel._pow(x, c)));
    if ((Math.abs(res) > 0d) && MathUtils.isFinite(res) && //
        MathUtils.isFinite(res = (a / res))) {
      return res;
    }
    return 0d;
  }

  /**
   * compute the power
   *
   * @param a
   *          the base
   * @param b
   *          the power
   * @return the result
   */
  static final double _pow(final double a, final double b) {
    return Pow.INSTANCE.computeAsDouble(a, b);
  }

  /**
   * compute the natural logarithm
   *
   * @param a
   *          the number
   * @return the logarithm
   */
  static final double _log(final double a) {
    return Ln.INSTANCE.computeAsDouble(a);
  }

  /**
   * add three numbers
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @param c
   *          the third number
   * @return the sum
   */
  static final double _add3(final double a, final double b,
      final double c) {
    return Add3.INSTANCE.computeAsDouble(a, b, c);
  }

  /** {@inheritDoc} */
  @Override
  public final void gradient(final double x, final double[] parameters,
      final double[] gradient) {
    final double b, c, xc, bxc, axc, div;
    double g0;

    b = parameters[1];
    c = parameters[2];

    xc = LogisticModel._pow(x, c);

    if (Math.abs(xc) <= 0d) {
      gradient[0] = 1d;
      gradient[1] = gradient[2] = 0d;
      return;
    }

    bxc = (b * xc);
    if (Math.abs(bxc) <= 0) {
      gradient[0] = 1d;
      gradient[1] = gradient[2] = 0d;
      return;
    }

    g0 = (1d + bxc);
    if ((Math.abs(g0) > 0d) && MathUtils.isFinite(g0)
        && MathUtils.isFinite(g0 = (1d / g0))) {
      gradient[0] = g0;
    } else {
      gradient[0] = 0d;
    }

    axc = (parameters[0] * xc);
    if (Math.abs(axc) <= 0d) {
      gradient[1] = gradient[2] = 0d;
      return;
    }

    div = LogisticModel._add3(1d, 2d * bxc, xc * xc * b * b);
    if ((Math.abs(div) > 0d) && MathUtils.isFinite(div)) {
      g0 = ((-axc) / div);
      if (MathUtils.isFinite(g0)) {
        gradient[1] = g0;
      } else {
        gradient[1] = 0d;
      }
      g0 = ((-(b * axc * LogisticModel._log(x))) / div);
      if (MathUtils.isFinite(g0)) {
        gradient[2] = g0;
      } else {
        gradient[2] = 0d;
      }
      return;
    }

    gradient[1] = gradient[2] = 0d;
  }

  /** {@inheritDoc} */
  @Override
  public final int getParameterCount() {
    return 3;
  }

  /** {@inheritDoc} */
  @Override
  public final IParameterGuesser createParameterGuesser(
      final IMatrix data) {
    return new __LogisticGuesser(data);
  }

  /** {@inheritDoc} */
  @Override
  public final void canonicalizeParameters(final double[] parameters) {
    parameters[2] = Math.abs(parameters[2]);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer, final IMathRenderable x) {
    renderer.renderParameter(0, out);
    out.append('/');
    out.append('1');
    out.append('+');
    renderer.renderParameter(1, out);
    out.append('*');
    x.mathRender(out, renderer);
    out.append('^');
    renderer.renderParameter(2, out);
    out.append(')');
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer, final IMathRenderable x) {
    try (final IMath div = out.div()) {
      renderer.renderParameter(0, div);
      try (final IMath add = div.add()) {
        try (final IText num = add.number()) {
          num.append('1');
        }
        try (final IMath mul = add.mul()) {
          renderer.renderParameter(1, mul);
          try (final IMath pow = mul.pow()) {
            x.mathRender(pow, renderer);
            renderer.renderParameter(2, pow);
          }
        }
      }
    }
  }

  /** A parameter guesser for the logistic models. */
  private static final class __LogisticGuesser
      extends SampleBasedParameterGuesser {

    /**
     * create the guesser
     *
     * @param data
     *          the data
     */
    __LogisticGuesser(final IMatrix data) {
      super(data, 3);
    }

    /**
     * compute the exponent
     *
     * @param a
     *          the number
     * @return the exponent
     */
    private static final double __exp(final double a) {
      return Exp.INSTANCE.computeAsDouble(a);
    }

    /**
     * add four numbers
     *
     * @param a
     *          the first number
     * @param b
     *          the second number
     * @param c
     *          the third number
     * @param d
     *          the fourth number
     * @return the sum
     */
    private static final double __add4(final double a, final double b,
        final double c, final double d) {
      return Add4.INSTANCE.computeAsDouble(a, b, c, d);
    }

    /**
     * compute the error of a given fitting
     *
     * @param x0
     *          the first x-coordinate
     * @param y0
     *          the first y-coordinate
     * @param x1
     *          the second x-coordinate
     * @param y1
     *          the second y-coordinate
     * @param x2
     *          the third x-coordinate
     * @param y2
     *          the third y-coordinate
     * @param a
     *          the first fitting parameter
     * @param b
     *          the second fitting parameter
     * @param c
     *          the first fitting parameter
     * @return the fitting error
     */
    private static final double __error(final double x0, final double y0,
        final double x1, final double y1, final double x2, final double y2,
        final double a, final double b, final double c) {
      return LogisticModel._add3(//
          __LogisticGuesser.__error(x0, y0, a, b, c), //
          __LogisticGuesser.__error(x1, y1, a, b, c), //
          __LogisticGuesser.__error(x2, y2, a, b, c));
    }

    /**
     * Compute the error of the fitting for a single point
     *
     * @param x
     *          the {@code x}-coordinate of the point
     * @param y
     *          the {@code y}-coordinate of the point
     * @param a
     *          the {@code a} value
     * @param b
     *          the {@code b} value
     * @param c
     *          the {@code c} value
     * @return the error
     */
    private static final double __error(final double x, final double y,
        final double a, final double b, final double c) {
      return Math.abs(y - LogisticModel._compute(x, a, b, c));
    }

    /**
     * the median of three numbers
     *
     * @param a
     *          the first number
     * @param b
     *          the second number
     * @param c
     *          the third number
     * @return the median
     */
    private static final double __med3(final double a, final double b,
        final double c) {
      double min, med, max;

      if (a > b) {
        if (a > c) {
          max = a;
          if (b > c) {
            med = b;
            min = c;
          } else {
            med = c;
            min = b;
          }
        } else {
          med = a;
          if (b > c) {
            max = b;
            min = c;
          } else {
            max = c;
            min = b;
          }
        }
      } else {
        if (b > c) {
          max = b;
          if (a > c) {
            med = a;
            min = c;
          } else {
            med = c;
            min = a;
          }
        } else {
          med = b;
          max = c;
          min = a;
        }
      }

      if (MathUtils.isFinite(med)) {
        return med;
      }
      if (MathUtils.isFinite(min)) {
        if (MathUtils.isFinite(max)) {
          med = (0.5d * (min + max));
          if (MathUtils.isFinite(med) && (min <= med) && (med <= max)) {
            return med;
          }
        }
        return min;
      }
      if (MathUtils.isFinite(max)) {
        return max;
      }
      return Double.NaN;
    }

    /**
     * Compute {@code a} from one point {@code (x,y)} and known {@code b}
     * and {@code c} values.
     *
     * @param x
     *          the {@code x}-coordinate of the point
     * @param y
     *          the {@code y}-coordinate of the point
     * @param b
     *          the {@code b} value
     * @param c
     *          the {@code c} value
     * @return the {@code a} value
     */
    private static final double __a_xybc(final double x, final double y,
        final double b, final double c) {
      return ((x <= 0d) ? y : (((b * LogisticModel._pow(x, c)) + 1d) * y));
    }

    /**
     * Compute {@code b} from one point {@code (x,y)} and known {@code a}
     * and {@code c} values.
     *
     * @param x
     *          the {@code x}-coordinate of the point
     * @param y
     *          the {@code y}-coordinate of the point
     * @param a
     *          the {@code a} value
     * @param c
     *          the {@code c} value
     * @return the {@code b} value
     */
    private static final double __b_xyac(final double x, final double y,
        final double a, final double c) {
      final double b1, b2, b3, xc, xcy, e1, e2, e3;

      xc = LogisticModel._pow(x, c);
      xcy = (xc * y);

      b1 = ((a - y) / xcy);
      b2 = ((a / xcy) - (1d / xc));

      if (b1 == b2) {
        return b1;
      }

      e1 = __LogisticGuesser.__error(x, y, a, b1, c);
      e2 = __LogisticGuesser.__error(x, y, a, b2, c);
      if (MathUtils.isFinite(e1)) {
        if (MathUtils.isFinite(e2)) {
          if (e1 == e2) {
            b3 = (0.5d * (b1 + b2));
            if ((b3 != b2) && (b3 != b1) && ((b1 < b3) || (b2 < b3))
                && ((b3 < b1) || (b3 < b2))) {
              e3 = __LogisticGuesser.__error(x, y, a, b3, c);
              if (MathUtils.isFinite(e3) && (e3 <= e1) && (e3 <= e2)) {
                return b3;
              }
            }
          }
          return ((e1 < e2) ? b1 : b2);
        }
        return b1;
      }
      if (MathUtils.isFinite(e2)) {
        return b2;
      }
      return ((e1 < e2) ? b1 : b2);
    }

    /**
     * Compute {@code c} from one point {@code (x,y)} and known {@code a}
     * and {@code b} values.
     *
     * @param x
     *          the {@code x}-coordinate of the point
     * @param y
     *          the {@code y}-coordinate of the point
     * @param a
     *          the {@code a} value
     * @param b
     *          the {@code b} value
     * @return the {@code c} value
     */
    private static final double __c_xyab(final double x, final double y,
        final double a, final double b) {
      final double c1, c2, c3, lx, by, e1, e2, e3;

      lx = LogisticModel._log(x);
      if ((b <= (-1d)) && (0d < x) && (x < 1d) && (Math.abs(a) <= 0d)
          && (Math.abs(y) <= 0d)) {
        return Math.nextUp(Math.nextUp(//
            LogisticModel._log(-1d / b) / lx));
      }

      by = b * y;
      c1 = LogisticModel._log((a / by) - (1 / b)) / lx;
      c2 = LogisticModel._log((a - y) / by) / lx;

      if (c1 == c2) {
        return c1;
      }

      e1 = __LogisticGuesser.__error(x, y, a, b, c1);
      e2 = __LogisticGuesser.__error(x, y, a, b, c2);
      if (MathUtils.isFinite(e1)) {
        if (MathUtils.isFinite(e2)) {
          if (e1 == e2) {
            c3 = (0.5d * (c1 + c2));
            if ((c3 != c2) && (c3 != c1) && ((c1 < c3) || (c2 < c3))
                && ((c3 < c1) || (c3 < c2))) {
              e3 = __LogisticGuesser.__error(x, y, a, b, c3);
              if (MathUtils.isFinite(e3) && (e3 <= e1) && (e3 <= e2)) {
                return c3;
              }
            }
          }
          return ((e1 < e2) ? c1 : c2);
        }
        return c1;
      }
      if (MathUtils.isFinite(e2)) {
        return c2;
      }
      return ((e1 < e2) ? c1 : c2);
    }

    /**
     * Compute {@code a} from two points {@code (x1,y1)} and
     * {@code (x2,y2)} and a known {@code c} value.
     *
     * @param x1
     *          the {@code x}-coordinate of the first point
     * @param y1
     *          the {@code y}-coordinate of the first point
     * @param x2
     *          the {@code x}-coordinate of the second point
     * @param y2
     *          the {@code y}-coordinate of the second point
     * @param c
     *          the {@code c} value
     * @return the {@code a} value
     */
    private static final double __a_x1y1x2y2c(final double x1,
        final double y1, final double x2, final double y2,
        final double c) {
      final double x2c, x1c;

      x1c = LogisticModel._pow(x1, c);
      x2c = LogisticModel._pow(x2, c);
      return (((x2c - x1c) * y1 * y2) / ((x2c * y2) - (x1c * y1)));
    }

    /**
     * Compute {@code b} from two points {@code (x1,y1)} and
     * {@code (x2,y2)} and a known {@code c} value.
     *
     * @param x1
     *          the {@code x}-coordinate of the first point
     * @param y1
     *          the {@code y}-coordinate of the first point
     * @param x2
     *          the {@code x}-coordinate of the second point
     * @param y2
     *          the {@code y}-coordinate of the second point
     * @param c
     *          the {@code c} value
     * @return the {@code b} value
     */
    private static final double __b_x1y1x2y2c(final double x1,
        final double y1, final double x2, final double y2,
        final double c) {
      return ((y1 - y2) / ((LogisticModel._pow(x2, c) * y2)
          - (LogisticModel._pow(x1, c) * y1)));
    }

    /**
     * Compute {@code b} from two points {@code (x1,y1)} and
     * {@code (x2,y2)} and a known {@code a} value.
     *
     * @param x1
     *          the {@code x}-coordinate of the first point
     * @param y1
     *          the {@code y}-coordinate of the first point
     * @param x2
     *          the {@code x}-coordinate of the second point
     * @param y2
     *          the {@code y}-coordinate of the second point
     * @param a
     *          the {@code a} value
     * @return the {@code b} value
     */
    private static final double __b_x1y1x2y2a(final double x1,
        final double y1, final double x2, final double y2,
        final double a) {
      final double lx1, lx2;

      lx1 = LogisticModel._log(x1);
      lx2 = LogisticModel._log(x2);
      return __LogisticGuesser.__exp(__LogisticGuesser.__add4(//
          (lx1 * LogisticModel._log(a - y2)), //
          -(lx2 * LogisticModel._log(a - y1)), //
          -(lx1 * LogisticModel._log(y2)), //
          (lx2 * LogisticModel._log(y1))) / (lx1 - lx2));
    }

    /**
     * Check an {@code a} value
     *
     * @param a
     *          the {@code a} value
     * @param maxY
     *          the maximum {@code y} coordinate
     * @return {@code true} if the {@code a} value is OK, {@code false}
     *         otherwise
     */
    private static final boolean __checkA(final double a,
        final double maxY) {
      final double abs;
      if (MathUtils.isFinite(a)) {
        abs = Math.abs(a);
        if (abs > 1e-14d) {
          if (abs < (1e7d * maxY)) {
            return true;
          }
        }
      }
      return false;
    }

    /**
     * Check an {@code b} value
     *
     * @param b
     *          the {@code b} value
     * @return {@code true} if the {@code b} value is OK, {@code false}
     *         otherwise
     */
    private static final boolean __checkB(final double b) {
      final double abs;
      if (MathUtils.isFinite(b)) {
        abs = Math.abs(b);
        if (abs > 1e-10d) {
          if (abs < 1e10d) {
            return true;
          }
        }
      }
      return false;
    }

    /**
     * Check an {@code c} value
     *
     * @param c
     *          the {@code c} value
     * @return {@code true} if the {@code b} value is OK, {@code false}
     *         otherwise
     */
    private static final boolean __checkC(final double c) {
      return (MathUtils.isFinite(c) && (c > 1e-4d) && (c < 1e4d));
    }

    /**
     * Update a guess for {@code a}, {@code b}, and {@code c} by using
     * median results from all formulas
     *
     * @param x0
     *          the {@code x}-coordinate of the first point
     * @param y0
     *          the {@code y}-coordinate of the first point
     * @param x1
     *          the {@code x}-coordinate of the second point
     * @param y1
     *          the {@code y}-coordinate of the second point
     * @param x2
     *          the {@code x}-coordinate of the third point
     * @param y2
     *          the {@code y}-coordinate of the third point
     * @param maxY
     *          the maximum {@code y} coorrdinate
     * @param dest
     *          the destination array
     * @param bestError
     *          the best error so far
     * @return the new (or old) best error
     */
    private static final double __updateMed(final double x0,
        final double y0, final double x1, final double y1, final double x2,
        final double y2, final double maxY, final double[] dest,
        final double bestError) {
      double newA, newB, newC, error;
      boolean hasA, hasB, hasC, changed;

      newA = newB = newC = Double.NaN;
      hasA = hasB = hasC = false;

      changed = true;
      while (changed) {
        changed = false;

        if (!hasB) {
          // find B based on the existing or new A and C values
          newB = __LogisticGuesser.__med3(//
              __LogisticGuesser.__b_x1y1x2y2a(x0, y0, x1, y1,
                  (hasA ? newA : dest[0])), //
              __LogisticGuesser.__b_x1y1x2y2a(x1, y1, x2, y2,
                  (hasA ? newA : dest[0])), //
              __LogisticGuesser.__b_x1y1x2y2a(x2, y2, x0, y0,
                  (hasA ? newA : dest[0])));

          if (__LogisticGuesser.__checkB(newB)) {
            changed = hasB = true;
          } else {
            newB = __LogisticGuesser.__med3(//
                __LogisticGuesser.__b_xyac(x0, y0, (hasA ? newA : dest[0]),
                    (hasC ? newC : dest[2])), //
                __LogisticGuesser.__b_xyac(x1, y1, (hasA ? newA : dest[0]),
                    (hasC ? newC : dest[2])), //
                __LogisticGuesser.__b_xyac(x2, y2, (hasA ? newA : dest[0]),
                    (hasC ? newC : dest[2])));

            if (__LogisticGuesser.__checkB(newB)) {
              changed = hasB = true;
            } else {
              newB = __LogisticGuesser.__med3(//
                  __LogisticGuesser.__b_x1y1x2y2c(x0, y0, x1, y1,
                      (hasC ? newC : dest[2])), //
                  __LogisticGuesser.__b_x1y1x2y2c(x1, y1, x2, y2,
                      (hasC ? newC : dest[2])), //
                  __LogisticGuesser.__b_x1y1x2y2c(x2, y2, x0, y0,
                      (hasC ? newC : dest[2])));

              if (__LogisticGuesser.__checkB(newB)) {
                changed = hasB = true;
              }
            }
          }
        }

        if (!hasC) {
          // find C based on the existing or new A and B values
          newC = __LogisticGuesser.__med3(//
              __LogisticGuesser.__c_xyab(x0, y0, (hasA ? newA : dest[0]),
                  (hasB ? newB : dest[1])), //
              __LogisticGuesser.__c_xyab(x1, y1, (hasA ? newA : dest[0]),
                  (hasB ? newB : dest[1])), //
              __LogisticGuesser.__c_xyab(x2, y2, (hasA ? newA : dest[0]),
                  (hasB ? newB : dest[1])));

          if (__LogisticGuesser.__checkC(newC)) {
            changed = hasC = true;
          }
        }

        if (!hasA) {
          findA: {
            // find A based on the existing or new B and C values
            if (hasB) {
              newA = __LogisticGuesser.__med3(//
                  __LogisticGuesser.__a_xybc(x0, y0, newB,
                      (hasC ? newC : dest[2])), //
                  __LogisticGuesser.__a_xybc(x1, y1, newB,
                      (hasC ? newC : dest[2])), //
                  __LogisticGuesser.__a_xybc(x2, y2, newB,
                      (hasC ? newC : dest[2])));

              if (__LogisticGuesser.__checkA(newA, maxY)) {
                changed = hasA = true;
                break findA;
              }
            }

            newA = __LogisticGuesser.__med3(//
                __LogisticGuesser.__a_x1y1x2y2c(x0, y0, x1, y1,
                    (hasC ? newC : dest[2])), //
                __LogisticGuesser.__a_x1y1x2y2c(x1, y1, x2, y2,
                    (hasC ? newC : dest[2])), //
                __LogisticGuesser.__a_x1y1x2y2c(x2, y2, x0, y0,
                    (hasC ? newC : dest[2])));//

            if (__LogisticGuesser.__checkA(newA, maxY)) {
              changed = hasA = true;
              break findA;
            }

            if (!hasB) {
              newA = __LogisticGuesser.__med3(//
                  __LogisticGuesser.__a_xybc(x0, y0, dest[1],
                      (hasC ? newC : dest[2])), //
                  __LogisticGuesser.__a_xybc(x1, y1, dest[1],
                      (hasC ? newC : dest[2])), //
                  __LogisticGuesser.__a_xybc(x2, y2, dest[1],
                      (hasC ? newC : dest[2])));

              if (__LogisticGuesser.__checkA(newA, maxY)) {
                changed = hasA = true;
                break findA;
              }
            }

            if (!changed) {
              if (Math.abs(x0) <= 0d) {
                newA = y0;
                changed = hasA = true;
                break findA;
              }
              if (Math.abs(x1) <= 0d) {
                newA = y1;
                changed = hasA = true;
                break findA;
              }
              if (Math.abs(x2) <= 0d) {
                newA = y2;
                changed = hasA = true;
                break findA;
              }
            }
          }
        }

        if (hasA && hasB && hasC) {
          error = __LogisticGuesser.__error(x0, y0, x1, y1, x2, y2, newA,
              newB, newC);
          if (MathUtils.isFinite(error) && (error < bestError)) {
            dest[0] = newA;
            dest[1] = newB;
            dest[2] = newC;
            return error;
          }

          return bestError;
        }
      }

      return bestError;
    }

    /**
     * Update a guess for {@code a}, {@code b}, and {@code c} by using the
     * first two points for calculating the new values (and the last one
     * only in the error computation)
     *
     * @param x0
     *          the {@code x}-coordinate of the first point
     * @param y0
     *          the {@code y}-coordinate of the first point
     * @param x1
     *          the {@code x}-coordinate of the second point
     * @param y1
     *          the {@code y}-coordinate of the second point
     * @param x2
     *          the {@code x}-coordinate of the third point
     * @param y2
     *          the {@code y}-coordinate of the third point
     * @param maxY
     *          the maximum {@code y} coordinate
     * @param dest
     *          the destination array
     * @param bestError
     *          the best error so far
     * @return the new (or old) best error
     */
    private static final double __update(final double x0, final double y0,
        final double x1, final double y1, final double x2, final double y2,
        final double maxY, final double[] dest, final double bestError) {
      double newA, newB, newC, error;
      boolean hasA, hasB, hasC, changed;

      newA = newB = newC = Double.NaN;
      hasA = hasB = hasC = false;

      changed = true;
      while (changed) {
        changed = false;

        if (!hasB) {
          // find B based on the existing or new A and C values
          newB = __LogisticGuesser.__b_x1y1x2y2a(x0, y0, x1, y1,
              (hasA ? newA : dest[0]));
          if (__LogisticGuesser.__checkB(newB)) {
            changed = hasB = true;
          } else {
            newB = __LogisticGuesser.__b_xyac(x0, y0,
                (hasA ? newA : dest[0]), (hasC ? newC : dest[2]));
            if (__LogisticGuesser.__checkB(newB)) {
              changed = hasB = true;
            } else {
              newB = __LogisticGuesser.__b_x1y1x2y2c(x0, y0, x1, y1,
                  (hasC ? newC : dest[2]));
              if (__LogisticGuesser.__checkB(newB)) {
                changed = hasB = true;
              }
            }
          }
        }

        if (!hasC) {
          // find C based on the existing or new A and B values
          newC = __LogisticGuesser.__c_xyab(x0, y0,
              (hasA ? newA : dest[0]), (hasB ? newB : dest[1]));
          if (__LogisticGuesser.__checkC(newC)) {
            changed = hasC = true;
          }
        }

        if (!hasA) {
          findA: {
            // find A based on the existing or new B and C values
            if (hasB) {
              newA = __LogisticGuesser.__a_xybc(x0, y0, newB,
                  (hasC ? newC : dest[2]));
              if (__LogisticGuesser.__checkA(newA, maxY)) {
                changed = hasA = true;
                break findA;
              }
            }

            newA = __LogisticGuesser.__a_x1y1x2y2c(x0, y0, x1, y1,
                (hasC ? newC : dest[2]));
            if (__LogisticGuesser.__checkA(newA, maxY)) {
              changed = hasA = true;
              break findA;
            }

            if (!hasB) {
              newA = __LogisticGuesser.__a_xybc(x0, y0, dest[1],
                  (hasC ? newC : dest[2]));
              if (__LogisticGuesser.__checkA(newA, maxY)) {
                changed = hasA = true;
                break findA;
              }
            }

            if (!changed) {
              if (Math.abs(x0) <= 0d) {
                newA = y0;
                changed = hasA = true;
                break findA;
              }
              if (Math.abs(x1) <= 0d) {
                newA = y1;
                changed = hasA = true;
                break findA;
              }
              if (Math.abs(x2) <= 0d) {
                newA = y2;
                changed = hasA = true;
                break findA;
              }
            }
          }
        }

        if (hasA && hasB && hasC) {
          error = __LogisticGuesser.__error(x0, y0, x1, y1, x2, y2, newA,
              newB, newC);
          if (MathUtils.isFinite(error) && (error < bestError)) {
            dest[0] = newA;
            dest[1] = newB;
            dest[2] = newC;
            return error;
          }

          return bestError;
        }
      }

      return bestError;
    }

    /** {@inheritDoc} */
    @Override
    protected final boolean guess(final double[] points,
        final double[] dest, final Random random) {
      final double maxY, x0, y0, x1, y1, x2, y2;
      double oldError, newError;
      int steps;

      x0 = points[0];
      y0 = points[1];
      x1 = points[2];
      y1 = points[3];
      x2 = points[4];
      y2 = points[5];

      maxY = Math.max(y0, Math.max(y1, y2));
      steps = 100;
      newError = Double.POSITIVE_INFINITY;

      while ((--steps) > 0) {
        __LogisticGuesser.__fallback(maxY, random, dest);
        for (;;) {
          oldError = newError;

          newError = __LogisticGuesser.__update(x0, y0, x1, y1, x2, y2,
              maxY, dest, oldError);
          newError = __LogisticGuesser.__update(x0, y0, x2, y2, x1, y1,
              maxY, dest, newError);
          newError = __LogisticGuesser.__update(x1, y1, x0, y0, x2, y2,
              maxY, dest, newError);
          newError = __LogisticGuesser.__update(x1, y1, x2, y2, x0, y0,
              maxY, dest, newError);
          newError = __LogisticGuesser.__update(x2, y2, x0, y0, x1, y1,
              maxY, dest, newError);
          newError = __LogisticGuesser.__update(x2, y2, x1, y1, x0, y0,
              maxY, dest, newError);

          newError = __LogisticGuesser.__updateMed(x0, y0, x1, y1, x2, y2,
              maxY, dest, newError);

          if ((--steps) <= 0) {
            return MathUtils.isFinite(newError);
          }
          if (newError >= oldError) {
            if (MathUtils.isFinite(newError)) {
              return true;
            }
          }
        }
      }

      return false;
    }

    /** {@inheritDoc} */
    @Override
    protected final boolean fallback(final double[] points,
        final double[] dest, final Random random) {
      double maxY;
      int i;

      maxY = Double.NEGATIVE_INFINITY;
      for (i = (points.length - 1); i > 0; i -= 2) {
        maxY = Math.max(maxY, points[i]);
      }

      __LogisticGuesser.__fallback(maxY, random, dest);
      return true;
    }

    /** {@inheritDoc} */
    @Override
    protected final void fallback(final double[] dest,
        final Random random) {
      __LogisticGuesser.__fallback(this.m_maxY, random, dest);
    }

    /**
     * A fallback if all conventional guessing failed
     *
     * @param maxY
     *          the maximum {@code y}-coordinate encountered
     * @param random
     *          the random number generator
     * @param parameters
     *          the parameters
     */
    private static final void __fallback(final double maxY,
        final Random random, final double[] parameters) {
      double v;

      v = Math.abs(maxY);
      if (v <= 0d) {
        v = 1e-6d;
      }

      parameters[0] = v = Math.abs(v * (1d + //
          Math.abs(0.01d * random.nextGaussian())));
      parameters[1] = (-Math.abs(//
          v * (1d / (17d + (3d * random.nextGaussian())))));
      do {
        parameters[2] = (1d + random.nextGaussian());
      } while (parameters[2] <= 1e-7d);
    }
  }

}
