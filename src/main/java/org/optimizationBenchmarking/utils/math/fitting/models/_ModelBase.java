package org.optimizationBenchmarking.utils.math.fitting.models;

import java.util.Random;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add3;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add4;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.AddN;
import org.optimizationBenchmarking.utils.math.functions.power.Exp;
import org.optimizationBenchmarking.utils.math.functions.power.Ln;
import org.optimizationBenchmarking.utils.math.functions.power.Pow;
import org.optimizationBenchmarking.utils.math.functions.power.Sqrt;
import org.optimizationBenchmarking.utils.math.text.IMathRenderable;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** An internal base class with shared functionality for models. */
abstract class _ModelBase extends ParametricUnaryFunction {

  /** create */
  _ModelBase() {
    super();
  }

  /**
   * Compute the value of the logistic model over log-scaled {@code x},
   * i.e., {@code a/(1+b*x^c)} for a coordinate {@code x} and model
   * parameters {@code a}, {@code b}, and {@code c}.
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
  static final double _logisticModelOverLogXCompute(final double x,
      final double a, final double b, final double c) {
    double res;

    res = (1d + (b + _ModelBase._pow(x, c)));
    if ((Math.abs(res) > 0d) && MathUtils.isFinite(res) && //
        MathUtils.isFinite(res = (a / res))) {
      return res;
    }
    return 0d;
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
   * compute the sum of a given set of numbers
   *
   * @param summands
   *          the summand array
   * @return the sum
   */
  static final double _sum(final double... summands) {
    return AddN.destructiveSum(summands);
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
  static final double _add4(final double a, final double b, final double c,
      final double d) {
    return Add4.INSTANCE.computeAsDouble(a, b, c, d);
  }

  /**
   * Compute the gradient of the logistic model over log-scaled {@code x},
   * i.e., {@code a/(1+b*x^c)}, for a coordinate {@code x} and a given set
   * of model {@code parameters}.
   *
   * @param x
   *          the {@code x}-coordinate
   * @param a
   *          the first model parameter
   * @param b
   *          the second model parameter
   * @param c
   *          the third model parameter
   * @param gradient
   *          the destination array
   */
  static final void _logisticModelOverLogXGradient(final double x,
      final double a, final double b, final double c,
      final double[] gradient) {
    final double xc, bxc, axc, div;
    double g0;

    xc = _ModelBase._pow(x, c);

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

    axc = (a * xc);
    if (Math.abs(axc) <= 0d) {
      gradient[1] = gradient[2] = 0d;
      return;
    }

    div = _ModelBase._add3(1d, 2d * bxc, xc * xc * b * b);
    if ((Math.abs(div) > 0d) && MathUtils.isFinite(div)) {
      g0 = ((-axc) / div);
      if (MathUtils.isFinite(g0)) {
        gradient[1] = g0;
      } else {
        gradient[1] = 0d;
      }
      g0 = ((-(b * axc * _ModelBase._log(x))) / div);
      if (MathUtils.isFinite(g0)) {
        gradient[2] = g0;
      } else {
        gradient[2] = 0d;
      }
      return;
    }

    gradient[1] = gradient[2] = 0d;
  }

  /**
   * Canonicalize the parameters of the logistic model over log-scaled
   * {@code x}, i.e., {@code a/(1+b*x^c)}.
   *
   * @param parameters
   *          the parameters
   */
  static final void _logisticModelOverLogXCanonicalizeParameters(
      final double[] parameters) {
    parameters[2] = Math.abs(parameters[2]);
  }

  /**
   * Render the logistic model over log-scaled {@code x}, i.e.,
   * {@code a/(1+b*x^c)}.
   *
   * @param out
   *          the text output device
   * @param renderer
   *          the parameter renderer
   * @param x
   *          the renderer for the {@code x} parameter
   */
  static final void _logisticModelOverLogXMathRender(final ITextOutput out,
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

  /**
   * Render the logistic model over log-scaled {@code x}, i.e.,
   * {@code a/(1+b*x^c)}.
   *
   * @param out
   *          the text output device
   * @param renderer
   *          the parameter renderer
   * @param x
   *          the renderer for the {@code x} parameter
   */
  static final void _logisticModelOverLogXMathRender(final IMath out,
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
  static final double _med3(final double a, final double b,
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
   * the median of four numbers
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @param c
   *          the third number
   * @param d
   *          the fourth number
   * @return the median
   */
  static final double _med4(final double a, final double b, final double c,
      final double d) {
    double n1, n2, n3, n4, t;

    n1 = a;
    n2 = b;
    n3 = c;
    n4 = d;

    if (n1 > n3) {
      t = n1;
      n1 = n3;
      n3 = t;
    }

    if (n2 > n4) {
      t = n2;
      n2 = n4;
      n4 = t;
    }

    if (n1 > n2) {
      t = n1;
      n1 = n2;
      n2 = t;
    }

    if (n3 > n4) {
      t = n3;
      n3 = n4;
      n4 = t;
    }

    if (n2 > n3) {
      t = n2;
      n2 = n3;
      n3 = t;
    }

    t = (0.5d * (n2 + n3));
    if (MathUtils.isFinite(t) && (t >= n2) && (t <= n3)) {
      return t;
    }

    t = ((0.5d * n2) + (0.5d * n3));
    if (MathUtils.isFinite(t) && (t >= n2) && (t <= n3)) {
      return t;
    }

    if (MathUtils.isFinite(n2)) {
      return n2;
    }
    if (MathUtils.isFinite(n3)) {
      return n3;
    }
    if (MathUtils.isFinite(n1)) {
      return n1;
    }
    if (MathUtils.isFinite(n4)) {
      return n4;
    }
    return Double.NaN;
  }

  /**
   * Check an {@code a} value for the logistic model over log-scaled
   * {@code x}, i.e., {@code a/(1+b*x^c)}.
   *
   * @param a
   *          the {@code a} value
   * @param maxY
   *          the maximum {@code y} coordinate
   * @return {@code true} if the {@code a} value is OK, {@code false}
   *         otherwise
   */
  static final boolean _logisticModelOverLogXCheckA(final double a,
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
   * Check an {@code b} value for the logistic model over log-scaled
   * {@code x}, i.e., {@code a/(1+b*x^c)}.
   *
   * @param b
   *          the {@code b} value
   * @return {@code true} if the {@code b} value is OK, {@code false}
   *         otherwise
   */
  static final boolean _logisticModelOverLogXCheckB(final double b) {
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
   * Check an {@code c} value for the logistic model over log-scaled
   * {@code x}, i.e., {@code a/(1+b*x^c)}.
   *
   * @param c
   *          the {@code c} value
   * @return {@code true} if the {@code b} value is OK, {@code false}
   *         otherwise
   */
  static final boolean _logisticModelOverLogXCheckC(final double c) {
    return (MathUtils.isFinite(c) && (c > 1e-4d) && (c < 1e4d));
  }

  /**
   * A fallback if all conventional guessing failed for the logistic model
   * over log-scaled {@code x}, i.e., {@code a/(1+b*x^c)}.
   *
   * @param maxY
   *          the maximum {@code y}-coordinate encountered
   * @param random
   *          the random number generator
   * @param parameters
   *          the parameters
   */
  static final void _logisticModelOverLogXFallback(final double maxY,
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
  static final double _logisticModelOverLogX_a_xybc(final double x,
      final double y, final double b, final double c) {
    return ((x <= 0d) ? y : (((b * _ModelBase._pow(x, c)) + 1d) * y));
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
  static final double _logisticModelOverLogX_a_x1y1x2y2c(final double x1,
      final double y1, final double x2, final double y2, final double c) {
    final double x2c, x1c;

    x1c = _ModelBase._pow(x1, c);
    x2c = _ModelBase._pow(x2, c);
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
  static final double _logisticModelOverLogX_b_x1y1x2y2c(final double x1,
      final double y1, final double x2, final double y2, final double c) {
    return ((y1 - y2)
        / ((_ModelBase._pow(x2, c) * y2) - (_ModelBase._pow(x1, c) * y1)));
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
  static final double _logisticModelOverLogX_b_x1y1x2y2a(final double x1,
      final double y1, final double x2, final double y2, final double a) {
    final double lx1, lx2;

    lx1 = _ModelBase._log(x1);
    lx2 = _ModelBase._log(x2);
    return _ModelBase._exp(_ModelBase._add4(//
        (lx1 * _ModelBase._log(a - y2)), //
        -(lx2 * _ModelBase._log(a - y1)), //
        -(lx1 * _ModelBase._log(y2)), //
        (lx2 * _ModelBase._log(y1))) / (lx1 - lx2));
  }
}
