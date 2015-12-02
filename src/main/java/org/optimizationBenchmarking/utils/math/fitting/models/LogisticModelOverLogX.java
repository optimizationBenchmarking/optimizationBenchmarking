package org.optimizationBenchmarking.utils.math.fitting.models;

import java.util.Random;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.fitting.impl.SamplePermutationBasedParameterGuesser;
import org.optimizationBenchmarking.utils.math.fitting.spec.IParameterGuesser;
import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.AddN;
import org.optimizationBenchmarking.utils.math.functions.power.Exp;
import org.optimizationBenchmarking.utils.math.functions.power.Ln;
import org.optimizationBenchmarking.utils.math.functions.power.Pow;
import org.optimizationBenchmarking.utils.math.functions.power.Sqrt;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.text.IMathRenderable;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * <p>
 * A model function which may be suitable to model how time-objective value
 * relationships of optimization processes behave. Qi Qi has discovered
 * this model: {@code a/(1+b*x^c)}.
 * </p>
 * <p>
 * It is somewhat similar to a logistic model and over a log-scaled
 * {@code x}-axis: Originally, we fitted {@code a/(1+exp(b+c*log(x)))},
 * which corresponds to {@code a/(1+exp(b)*(x^c))}, which, in turn, can be
 * simplified to {@code a/(1+b*x^c)} in the fitting procedure, since
 * {@code exp(b)} is constant with respect to {@code x} and then we can try
 * to find its value directly as well.
 * </p>
 * <p>
 * This model is also somewhat similar to my previous exponential decay
 * model {@code -(exp(a*(x^b))-1)} but seems to fit better and does not
 * require data normalization.
 * </p>
 * <h2>Derivatives</h2>
 * <p>
 * The derivatives have been obtained with http://www.numberempire.com/.
 * </p>
 * <ol>
 * <li>Original function: {@code a/(1+b*x^c)}</li>
 * <li>{@code d/da}: {@code 1/(1+b*x^c)}</li>
 * <li>{@code d/db}: {@code -(a*x^c)/         (1 + 2*b*x^c + b^2*x^(2*c))}
 * </li>
 * <li>{@code d/dc}: {@code -(a*b*x^c*log(x))/(1 + 2*b*x^c + b^2*x^(2*c))}
 * </li>
 * </ol>
 * <h2>Resolution</h2> The resolutions have been obtained with
 * http://www.numberempire.com/ and http://wolframalpha.com/.
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
public class LogisticModelOverLogX extends ParametricUnaryFunction {

  /** create */
  public LogisticModelOverLogX() {
    super();
  }

  /**
   * compute the square root
   *
   * @param a
   *          the number
   * @return the result
   */
  static final double _sqrt(final double a) {
    return Sqrt.INSTANCE.computeAsDouble(a);
  }

  /**
   * compute the exponent
   *
   * @param a
   *          the number
   * @return the exponent
   */
  static final double _exp(final double a) {
    return Exp.INSTANCE.computeAsDouble(a);
  }

  /**
   * compute the square
   *
   * @param a
   *          the number
   * @return the result
   */
  static final double _sqr(final double a) {
    return a * a;
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
   * sum up some numbers
   *
   * @param summands
   *          the numbers to sum up
   * @return the sum
   */
  static final double _add(final double... summands) {
    return AddN.destructiveSum(summands);
  }

  /** {@inheritDoc} */
  @Override
  public double value(final double x, final double[] parameters) {
    double res;

    res = (1d + (parameters[1] * //
        LogisticModelOverLogX._pow(x, parameters[2])));
    if ((Math.abs(res) > 0d) && MathUtils.isFinite(res) && //
        MathUtils.isFinite(res = (parameters[0] / res))) {
      return res;
    }
    return 0d;
  }

  /** {@inheritDoc} */
  @Override
  public void gradient(final double x, final double[] parameters,
      final double[] gradient) {
    final double b, xc, bxc, axc, div;
    double g0;

    xc = LogisticModelOverLogX._pow(x, parameters[2]);

    if (Math.abs(xc) <= 0d) {
      gradient[0] = 1d;
      gradient[1] = gradient[2] = 0d;
      return;
    }

    b = parameters[1];
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

    div = LogisticModelOverLogX._add(1d, 2d * bxc, xc * xc * b * b);
    if ((Math.abs(div) > 0d) && MathUtils.isFinite(div)) {
      g0 = ((-axc) / div);
      if (MathUtils.isFinite(g0)) {
        gradient[1] = g0;
      } else {
        gradient[1] = 0d;
      }
      g0 = ((-(b * axc * LogisticModelOverLogX._log(x))) / div);
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
  public int getParameterCount() {
    return 3;
  }

  /** {@inheritDoc} */
  @Override
  public void mathRender(final ITextOutput out,
      final IParameterRenderer renderer, final IMathRenderable x) {
    renderer.renderParameter(0, out);
    out.append('/');
    out.append('(');
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
  public void mathRender(final IMath out,
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

  /** {@inheritDoc} */
  @Override
  public IParameterGuesser createParameterGuesser(final IMatrix data) {
    return new _LogisticModelOverLogXParameterGuesser(data);
  }

  /**
   * check an {@code a} value for the logistic model over log-scaled
   * {@code x}, i.e., {@code a/(1+b*x^c)}.
   *
   * @param a
   *          the {@code a} value
   * @return {@code true} if the {@code a} value is OK, {@code false}
   *         otherwise
   */
  static final boolean _checkA(final double a) {
    final double abs;
    if (MathUtils.isFinite(a)) {
      abs = Math.abs(a);
      return ((abs > 1e-12d) && (abs < 1e100));
    }
    return false;
  }

  /**
   * _check an {@code b} value for the logistic model over log-scaled
   * {@code x}, i.e., {@code a/(1+b*x^c)}.
   *
   * @param b
   *          the {@code b} value
   * @return {@code true} if the {@code b} value is OK, {@code false}
   *         otherwise
   */
  static final boolean _checkB(final double b) {
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
   * _check an {@code c} value for the logistic model over log-scaled
   * {@code x}, i.e., {@code a/(1+b*x^c)}.
   *
   * @param c
   *          the {@code c} value
   * @return {@code true} if the {@code b} value is OK, {@code false}
   *         otherwise
   */
  static final boolean _checkC(final double c) {
    return (MathUtils.isFinite(c) && (c > 1e-4d) && (c < 1e4d));
  }

  /**
   * choose amongst two numbers
   *
   * @param a1
   *          the first number
   * @param a2
   *          the second number
   * @return their mean, if finite, or any of the two numbers which is
   *         finite, or mean
   */
  static final double _choose(final double a1, final double a2) {
    final double med;
    final int cmp;

    if (a1 == a2) {
      if (a1 == 0d) {
        return 0d;
      }
      return a1;
    }

    med = ((0.5d * a1) + (0.5d * a2));
    if (MathUtils.isFinite(med)) {
      return med;
    }
    if (MathUtils.isFinite(a1)) {
      if (MathUtils.isFinite(a2)) {
        cmp = Integer.compare(Math.abs(Math.getExponent(a1)),
            Math.abs(Math.getExponent(a2)));
        if (cmp < 0) {
          return a1;
        }
        if (cmp == 0) {
          if (Math.abs(a1) < Math.abs(a2)) {
            return a1;
          }
        }
        return a2;
      }

      return a1;
    }
    if (MathUtils.isFinite(a2)) {
      return a2;
    }
    return med;
  }

  /**
   * Compute {@code a} from one point {@code (x,y)} and known {@code b} and
   * {@code c} values.
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
  static final double _a_xybc(final double x, final double y,
      final double b, final double c) {
    return ((x <= 0d) ? y
        : (((b * LogisticModelOverLogX._pow(x, c)) + 1d) * y));
  }

  /**
   * Compute {@code a} from two points {@code (x1,y1)} and {@code (x2,y2)}
   * and a known {@code c} value.
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
  static final double _a_x1y1x2y2c(final double x1, final double y1,
      final double x2, final double y2, final double c) {
    final double x2c, x1c;

    x1c = LogisticModelOverLogX._pow(x1, c);
    x2c = LogisticModelOverLogX._pow(x2, c);
    return (((x2c - x1c) * y1 * y2) / ((x2c * y2) - (x1c * y1)));
  }

  /**
   * Compute {@code b} from two points {@code (x1,y1)} and {@code (x2,y2)}
   * and a known {@code c} value.
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
  static final double _b_x1y1x2y2c(final double x1, final double y1,
      final double x2, final double y2, final double c) {
    return ((y1 - y2) / ((LogisticModelOverLogX._pow(x2, c) * y2)
        - (LogisticModelOverLogX._pow(x1, c) * y1)));
  }

  /**
   * Compute {@code b} from two points {@code (x1,y1)} and {@code (x2,y2)}
   * and a known {@code a} value.
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
  static final double _b_x1y1x2y2a(final double x1, final double y1,
      final double x2, final double y2, final double a) {
    final double lx1, lx2;

    lx1 = LogisticModelOverLogX._log(x1);
    lx2 = LogisticModelOverLogX._log(x2);
    return LogisticModelOverLogX._exp(LogisticModelOverLogX._add(//
        (lx1 * LogisticModelOverLogX._log(a - y2)), //
        -(lx2 * LogisticModelOverLogX._log(a - y1)), //
        -(lx1 * LogisticModelOverLogX._log(y2)), //
        (lx2 * LogisticModelOverLogX._log(y1))) / (lx1 - lx2));
  }

  /**
   * Compute {@code b} from one point {@code (x,y)} and known {@code a} and
   * {@code c} values.
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
  static final double _b_xyac(final double x, final double y,
      final double a, final double c) {
    final double xc, xcy;

    xc = LogisticModelOverLogX._pow(x, c);
    xcy = (xc * y);

    return LogisticModelOverLogX._choose(//
        ((a - y) / xcy), //
        ((a / xcy) - (1d / xc)));
  }

  /**
   * Compute {@code c} from one point {@code (x,y)} and known {@code a} and
   * {@code b} values.
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
  static final double _c_xyab(final double x, final double y,
      final double a, final double b) {
    final double lx, by;

    lx = LogisticModelOverLogX._log(x);
    if ((b <= (-1d)) && (0d < x) && (x < 1d) && (Math.abs(a) <= 0d)
        && (Math.abs(y) <= 0d)) {
      return Math.nextUp(Math.nextUp(//
          LogisticModelOverLogX._log(-1d / b) / lx));
    }

    by = b * y;
    return LogisticModelOverLogX._choose(//
        LogisticModelOverLogX._log((a / by) - (1 / b)) / lx, //
        LogisticModelOverLogX._log((a - y) / by) / lx);
  }

  /** the parameter guesser */
  class _LogisticModelOverLogXParameterGuesser
      extends SamplePermutationBasedParameterGuesser {

    /**
     * Create the parameter guesser
     *
     * @param data
     *          the data
     */
    _LogisticModelOverLogXParameterGuesser(final IMatrix data) {
      super(data, LogisticModelOverLogX.this.getParameterCount(), //
          (LogisticModelOverLogX.this.getParameterCount() - 1));
    }

    /**
     * compute a fallback point of everything else fails.
     *
     * @param points
     *          the points
     * @param dest
     *          the destination
     * @param random
     *          the random number generator
     * @param offset
     *          the offset
     */
    final void _fallback(final double[] points, final double[] dest,
        final Random random, final double offset) {
      double maxY;
      int i;

      findMaxY: {
        if (random.nextInt(10) <= 0) {
          maxY = this.m_maxY;
          if (MathUtils.isFinite(maxY)) {
            break findMaxY;
          }
        }
        maxY = Double.NEGATIVE_INFINITY;
        for (i = (points.length - 1); i > 0; i -= 2) {
          maxY = Math.max(maxY, points[i]);
        }
      }

      maxY = Math.abs(maxY - offset);
      if (maxY < 1e-6d) {
        maxY = 1e-6d;
      }

      dest[0] = maxY = (maxY * Math.abs((1d + //
          Math.abs(0.05d * random.nextGaussian()))));

      dest[1] = (-Math.abs(//
          maxY * (1d / (17d + (3d * random.nextGaussian())))));

      do {
        dest[2] = (1d + random.nextGaussian());
      } while (dest[2] <= 1e-7d);
    }

    /** {@inheritDoc} */
    @Override
    protected boolean fallback(final double[] points, final double[] dest,
        final Random random) {
      this._fallback(points, dest, random, 0d);
      return true;
    }

    /** {@inheritDoc} */
    @Override
    protected final double value(final double x,
        final double[] parameters) {
      return LogisticModelOverLogX.this.value(x, parameters);
    }

    /** {@inheritDoc} */
    @Override
    protected void guessBasedOnPermutation(final double[] points,
        final double[] bestGuess, final double[] destGuess) {
      final double x0, y0, x1, y1/* , x2, y2 */, oldA, oldB, oldC;
      double newA, newB, newC;
      boolean hasA, hasB, hasC, changed;

      hasA = hasB = hasC = false;
      x0 = points[0];
      y0 = points[1];
      x1 = points[2];
      y1 = points[3];
      // x2 = points[4];
      // y2 = points[5];
      newA = oldA = bestGuess[0];
      newB = oldB = bestGuess[1];
      newC = oldC = bestGuess[2];

      changed = true;
      while (changed) {
        changed = false;

        if (!hasB) {
          // find B based on the existing or new A and C values
          newB = LogisticModelOverLogX._b_x1y1x2y2a(x0, y0, x1, y1,
              (hasA ? newA : oldA));
          if (LogisticModelOverLogX._checkB(newB)) {
            changed = hasB = true;
          } else {
            newB = LogisticModelOverLogX._b_xyac(x0, y0,
                (hasA ? newA : bestGuess[0]), (hasC ? newC : oldC));
            if (LogisticModelOverLogX._checkB(newB)) {
              changed = hasB = true;
            } else {
              newB = LogisticModelOverLogX._b_x1y1x2y2c(x0, y0, x1, y1,
                  (hasC ? newC : oldC));
              if (LogisticModelOverLogX._checkB(newB)) {
                changed = hasB = true;
              }
            }
          }
        }

        if (!hasC) {
          // find C based on the existing or new A and B values
          newC = LogisticModelOverLogX._c_xyab(x0, y0,
              (hasA ? newA : oldA), (hasB ? newB : oldB));
          if (LogisticModelOverLogX._checkC(newC)) {
            changed = hasC = true;
          }
        }

        if (!hasA) {
          findA: {
            // find A based on the existing or new B and C values
            if (hasB) {
              newA = LogisticModelOverLogX._a_xybc(x0, y0, newB,
                  (hasC ? newC : oldC));
              if (LogisticModelOverLogX._checkA(newA)) {
                changed = hasA = true;
                break findA;
              }
            }

            newA = LogisticModelOverLogX._a_x1y1x2y2c(x0, y0, x1, y1,
                (hasC ? newC : oldC));
            if (LogisticModelOverLogX._checkA(newA)) {
              changed = hasA = true;
              break findA;
            }

            if (!hasB) {
              newA = LogisticModelOverLogX._a_xybc(x0, y0, oldB,
                  (hasC ? newC : oldC));
              if (LogisticModelOverLogX._checkA(newA)) {
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
              // if (Math.abs(x2) <= 0d) {
              // newA = y2;
              // changed = hasA = true;
              // break findA;
              // }
            }
          }
        }
      }

      destGuess[0] = newA;
      destGuess[1] = newB;
      destGuess[2] = newC;
    }
  }
}