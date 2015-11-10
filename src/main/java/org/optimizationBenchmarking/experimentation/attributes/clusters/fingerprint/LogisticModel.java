package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.fitting.spec.IParameterGuesser;
import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add3;
import org.optimizationBenchmarking.utils.math.functions.power.Pow;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

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

    res = (1d + (parameters[1]
        + Pow.INSTANCE.computeAsDouble(x, parameters[2])));
    if ((Math.abs(res) > 0d) && MathUtils.isFinite(res) && //
        MathUtils.isFinite(res = (parameters[0] / res))) {
      return res;
    }
    return 0d;
  }

  /** {@inheritDoc} */
  @Override
  public final void gradient(final double x, final double[] parameters,
      final double[] gradient) {
    final double b, c, xc, bxc, axc, div;
    double g0;

    b = parameters[1];
    c = parameters[2];

    xc = Pow.INSTANCE.computeAsDouble(x, c);

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

    div = Add3.INSTANCE.computeAsDouble(1d, 2d * bxc, xc * xc * b * b);
    if ((Math.abs(div) > 0d) && MathUtils.isFinite(div)) {
      g0 = ((-axc) / div);
      if (MathUtils.isFinite(g0)) {
        gradient[1] = g0;
      } else {
        gradient[1] = 0d;
      }
      g0 = ((-(b * axc * Math.log(x))) / div);
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
    return new _LogisticGuesser(data);
  }

  /** {@inheritDoc} */
  @Override
  public final void canonicalizeParameters(final double[] parameters) {
    parameters[2] = Math.abs(parameters[2]);
  }
}
