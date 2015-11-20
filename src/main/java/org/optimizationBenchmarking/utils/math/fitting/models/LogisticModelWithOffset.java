package org.optimizationBenchmarking.utils.math.fitting.models;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.fitting.impl.SampleBasedParameterGuesser;
import org.optimizationBenchmarking.utils.math.fitting.spec.IParameterGuesser;
import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add3;
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
 * discovered this model and we refined it together: {@code a+b/(1+c*x^d)}.
 * It is somewhat similar to a logistic model and, in our tests, fits quite
 * well.
 * </p>
 * <h2>Derivatives</h2>
 * <p>
 * The derivatives have been obtained with
 * http://www.numberempire.com/derivativecalculator.php.
 * </p>
 * <ol>
 * <li>Original function: {@code a+b/(1+c*x^d)}</li>
 * <li>{@code d/da}: {@code 1}</li>
 * <li>{@code d/db}: {@code 1/(1+c*x^d)}</li>
 * <li>{@code d/dc}: {@code -(         b*x^d) / (1+2*c*x^d + c^2*x^(2*d))}
 * </li>
 * <li>{@code d/dd}: {@code -(log(x)*c*b*x^d) / (1+2*c*x^d + c^2*x^(2*d))}
 * </li>
 * </ol>
 * <h2>Resolution</h2>
 * <h3>One Known Point</h3>
 * <ol>
 * <li>{@code a}: {@code a=((1+c*x^d)*y-b)/(1+c*x^d)},
 * {@code a=y-b/(1+c*x^d)}</li>
 * <li>{@code b}: {@code b=(1+c*x^d)*y-a*c*x^d-a}</li>
 * <li>{@code c}: {@code c=-(y-b-a)/(x^d*y-a*x^d)},
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
public final class LogisticModelWithOffset
    extends ParametricUnaryFunction {

  /** create */
  public LogisticModelWithOffset() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double value(final double x, final double[] parameters) {
    double res;

    res = (1d + (LogisticModelWithOffset.__pow(x, parameters[3])
        + parameters[2]));
    if ((Math.abs(res) > 0d) && MathUtils.isFinite(res) && //
        MathUtils.isFinite(res = (parameters[1] / res))) {
      return (parameters[0] + res);
    }
    return parameters[0];
  }

  // /** compute the value of the model for a given coordinate {@code x}
  // and model parameters
  // * {@code a}, {@code b}, {@code c}
  // * @param x
  // * @param a
  // * @param b
  // * @param c
  // * @param d
  // * @return
  // */
  // static final double _compute(final double x, final double a, final
  // double b, final double c, final double d){
  //
  // }

  /** {@inheritDoc} */
  @Override
  public final void gradient(final double x, final double[] parameters,
      final double[] gradient) {
    final double c, d, xd, cxd, bxd, div;
    double g1;

    gradient[0] = 1;

    c = parameters[2];
    d = parameters[3];

    xd = LogisticModelWithOffset.__pow(x, d);

    if (Math.abs(xd) <= 0d) {
      gradient[1] = 1d;
      gradient[2] = gradient[3] = 0d;
      return;
    }

    cxd = (c * xd);
    if (Math.abs(cxd) <= 0) {
      gradient[1] = 1d;
      gradient[2] = gradient[3] = 0d;
      return;
    }

    g1 = (1d + cxd);
    if ((Math.abs(g1) > 0d) && MathUtils.isFinite(g1)
        && MathUtils.isFinite(g1 = (1d / g1))) {
      gradient[1] = g1;
    } else {
      gradient[1] = 0d;
    }

    bxd = (parameters[1] * xd);
    if (Math.abs(bxd) <= 0d) {
      gradient[2] = gradient[3] = 0d;
      return;
    }

    div = LogisticModelWithOffset.__add3(1d, (2d * cxd),
        (xd * xd * c * c));
    if ((Math.abs(div) > 0d) && MathUtils.isFinite(div)) {
      g1 = ((-bxd) / div);
      if (MathUtils.isFinite(g1)) {
        gradient[2] = g1;
      } else {
        gradient[2] = 0d;
      }
      g1 = ((-(c * bxd * LogisticModelWithOffset.__log(x))) / div);
      if (MathUtils.isFinite(g1)) {
        gradient[3] = g1;
      } else {
        gradient[3] = 0d;
      }
      return;
    }

    gradient[2] = gradient[3] = 0d;
  }

  /** {@inheritDoc} */
  @Override
  public final int getParameterCount() {
    return 4;
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
    parameters[3] = Math.abs(parameters[3]);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer, final IMathRenderable x) {
    renderer.renderParameter(0, out);
    out.append('+');
    renderer.renderParameter(1, out);
    out.append('/');
    out.append('1');
    out.append('+');
    renderer.renderParameter(2, out);
    out.append('*');
    x.mathRender(out, renderer);
    out.append('^');
    renderer.renderParameter(3, out);
    out.append(')');
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer, final IMathRenderable x) {
    try (final IMath sum = out.add()) {
      renderer.renderParameter(0, sum);
      try (final IMath div = sum.add()) {
        renderer.renderParameter(1, div);
        try (final IMath add = div.add()) {
          try (final IText num = add.number()) {
            num.append('1');
          }
          try (final IMath mul = add.mul()) {
            renderer.renderParameter(2, mul);
            try (final IMath pow = mul.pow()) {
              x.mathRender(pow, renderer);
              renderer.renderParameter(3, pow);
            }
          }
        }
      }
    }
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
  private static final double __pow(final double a, final double b) {
    return Pow.INSTANCE.computeAsDouble(a, b);
  }

  /**
   * compute the natural logarithm
   *
   * @param a
   *          the number
   * @return the logarithm
   */
  private static final double __log(final double a) {
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
  private static final double __add3(final double a, final double b,
      final double c) {
    return Add3.INSTANCE.computeAsDouble(a, b, c);
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
      super(data, 4);
    }
    //
    // /**
    // * compute the exponent
    // *
    // * @param a
    // * the number
    // * @return the exponent
    // */
    // private static final double __exp(final double a) {
    // return Exp.INSTANCE.computeAsDouble(a);
    // }
    //
    // /**
    // * add four numbers
    // *
    // * @param a
    // * the first number
    // * @param b
    // * the second number
    // * @param c
    // * the third number
    // * @param d
    // * the fourth number
    // * @return the sum
    // */
    // private static final double __add4(final double a, final double b,
    // final double c, final double d) {
    // return Add4.INSTANCE.computeAsDouble(a, b, c, d);
    // }

  }

}
