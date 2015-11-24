package org.optimizationBenchmarking.utils.math.fitting.models;

import java.util.Random;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.fitting.impl.SampleBasedParameterGuesser;
import org.optimizationBenchmarking.utils.math.fitting.spec.IParameterGuesser;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.text.IMathRenderable;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * <p>
 * A model function which may be suitable to model how time-objective value
 * relationships of optimization processes behave if the finally obtained
 * objective value is not {@code 0}: {@code a/(1+b*x^c) + d}.
 * </p>
 * <h2>Alternate Forms</h2>
 * <ol>
 * <li>{@code a/(1+b*x^c) + d}</li>
 * <li>{@code (d*b*x^c+d+a)/(b*x^c+1)}</li>
 * <li>{@code (d*(b*x^c+1)+a)/(b*x^c+1)}</li>
 * </ol>
 * <h2>Derivatives</h2>
 * <p>
 * The derivatives have been obtained with http://www.numberempire.com/.
 * </p>
 * <ol>
 * <li>Original function: {@code a/(1+b*x^c) + d}</li>
 * <li>{@code d/da}: {@code 1/(1+b*x^c)}</li>
 * <li>{@code d/db}: {@code -(         a*x^c)/(1+2*b*x^c+b^2*x^(2*c))}</li>
 * <li>{@code d/dc}: {@code -(b*log(x)*a*x^c)/(1+2*b*x^c+b^2*x^(2*c))}</li>
 * <li>{@code d/dd}: {@code 1}</li>
 * </ol>
 * <h2>Resolution</h2> The resolutions have been obtained with
 * http://www.numberempire.com/ and http://wolframalpha.com/.
 * <h3>One Known Point</h3>
 * <dl>
 * <dt>a.1.1</dt>
 * <dd>{@code a=(y-d)*(b*x^c+1)}</dd>
 * <dt>a.1.2 [=a.1.1]</dt>
 * <dd>{@code -d*b*x^c-d+y*b*x^c+y}</dd>
 * <dt>b.1.1</dt>
 * <dd>{@code b=(d+a-y)/(x^c*(y-d))}</dd>
 * <dt>b.1.2 [=b.1.1]</dt>
 * <dd>{@code b=(x^(-c)*(d+a-y))/(y-d)}</dd>
 * <dt>c.1.1</dt>
 * <dd>{@code c=log((y-d-a)/(b*(d-y)))/log(x)}</code>
 * <dt>d.1.1</dt>
 * <dd>{@code d=((1+b*x^c)*y-a)/(1+b*x^c)}</dd>
 * </dl>
 * <h3>Two Known Points</h3>
 * <dl>
 * <dt>a.2.1 [from d.1.1]</dt>
 * <dd>
 * {@code a=-((1+b*x1^c+(b^2*x1^c+b)*x2^c)*y2+((-1)-b*x1^c+((-b^2*x1^c)-b)*x2^c)*y1)/(b*x2^c-b*x1^c)}
 * </dd>
 * <dt>a.2.2 [=a.2.1; from d.1.1]</dt>
 * <dd>{@code a=((y1-y2)*(b*x1^c+1)*(b*x2^c+1))/(b*(x2^c-x1^c))}</dd>
 * <dt>a.2.3 [from b.1.1]</dt>
 * <dd>
 * {@code a=((y1-d)*(d-y2)*(x1^c-x2^c))/(d*(x1^c-x2^c)-y1*x1^c+y2*x2^c)}
 * </dd>
 * <dt>a.2.4 [=a.2.3; from b.1.1]</dt>
 * <dd>
 * {@code a=((d*x1^c-d*x2^c+(x2^c-x1^c)*y1)*y2+(d*x1^c-d*x2^c)*y1+d^2*x2^c-d^2*x1^c)/(d*x1^c-d*x2^c-x1^c*y1+x2^c*y2)}
 * </dd>
 * <dt>b.2.1 [from d.1.1]</dt>
 * <dd>
 * {@code b=(x1^(-c)*x2^(-c)*(-sqrt((a*x1^c-a*x2^c+y1*x1^c-y2*x1^c+y1*x2^c-y2*x2^c)^2-4*(y1-y2)*(y1*x1^c*x2^c-y2*x1^c*x2^c))-a*x1^c+a*x2^c-y1*x1^c+y2*x1^c-y1*x2^c+y2*x2^c))/(2*(y1-y2))}
 * </dd>
 * <dt>b.2.2 [from d.1.1]</dt>
 * <dd>
 * {@code b=(x1^(-c)*x2^(-c)*(sqrt((a*x1^c-a*x2^c+y1*x1^c-y2*x1^c+y1*x2^c-y2*x2^c)^2-4*(y1-y2)*(y1*x1^c*x2^c-y2*x1^c*x2^c))-a*x1^c+a*x2^c-y1*x1^c+y2*x1^c-y1*x2^c+y2*x2^c))/(2*(y1-y2))}
 * </dd>
 * <dt>b.2.3 [from d.1.1; probably=b.2.1]</dt>
 * <dd>
 * {@code b=-(sqrt((x1^(2*c)-2*x1^c*x2^c+x2^(2*c))*y2^2+((-2*a*x1^(2*c))+2*a*x2^(2*c)+((-2*x2^(2*c))+4*x1^c*x2^c-2*x1^(2*c))*y1)*y2+(x1^(2*c)-2*x1^c*x2^c+x2^(2*c))*y1^2+(2*a*x1^(2*c)-2*a*x2^(2*c))*y1+a^2*x2^(2*c)-2*a^2*x1^c*x2^c+a^2*x1^(2*c))+(x1^c+x2^c)*y2+((-x1^c)-x2^c)*y1+a*x2^c-a*x1^c)/(2*x1^c*x2^c*y2-2*x1^c*x2^c*y1)}
 * </dd>
 * <dt>b.2.4 [from d.1.1; probably=b.2.2]</dt>
 * <dd>
 * {@code b=(sqrt((x1^(2*c)-2*x1^c*x2^c+x2^(2*c))*y2^2+((-2*a*x1^(2*c))+2*a*x2^(2*c)+((-2*x2^(2*c))+4*x1^c*x2^c-2*x1^(2*c))*y1)*y2+(x1^(2*c)-2*x1^c*x2^c+x2^(2*c))*y1^2+(2*a*x1^(2*c)-2*a*x2^(2*c))*y1+a^2*x2^(2*c)-2*a^2*x1^c*x2^c+a^2*x1^(2*c))+((-x1^c)-x2^c)*y2+(x1^c+x2^c)*y1-a*x2^c+a*x1^c)/(2*x1^c*x2^c*y2-2*x1^c*x2^c*y1)}
 * </dd>
 * <dt>b.2.5 [from a.1.1]</dt>
 * <dd>{@code b=(y1-y2)/(d*x1^c-d*x2^c-x1^c*y1+x2^c*y2)}</dd>
 * <dd>c.2.1 [from b.1.1]</dt>
 * <dd>
 * {@code c=(log(((d-y2)*(d+a-y1))/((d-y1)*(d+a-y2))))/(log(x1)-log(x2))}
 * </dd>
 * <dt>d.2.1 [from a.1.1]</dt>
 * <dd>{@code d=((1+b*x2^c)*y2-(1+b*x1^c)*y1)/(b*(x2^c-x1^c))}</dd>
 * <dt>d.2.2 [= d.2.1; from a.1.1]</dt>
 * <dd>{@code d=(-b*y1*x1^c+b*y2*x2^c-y1+y2)/(b*(x2^c-x1^c))}</dd>
 * <dt>d.2.3 [from b.1.1]</dt>
 * <dd>
 * {@code d=(-sqrt((-a*x1^c+a*x2^c+y1*x1^c+y2*x1^c-y1*x2^c-y2*x2^c)^2-4*(x2^c-x1^c)*(a*y1*x1^c-a*y2*x2^c-y1*y2*x1^c+y1*y2*x2^c))+a*x1^c-a*x2^c-y1*x1^c-y2*x1^c+y1*x2^c+y2*x2^c)/(2*(x2^c-x1^c))}
 * </dd>
 * <dt>d.2.4 [from b.1.1]</dt>
 * <dd>
 * {@code d=(sqrt((-a*x1^c+a*x2^c+y1*x1^c+y2*x1^c-y1*x2^c-y2*x2^c)^2-4*(x2^c-x1^c)*(a*y1*x1^c-a*y2*x2^c-y1*y2*x1^c+y1*y2*x2^c))+a*x1^c-a*x2^c-y1*x1^c-y2*x1^c+y1*x2^c+y2*x2^c)/(2*(x2^c-x1^c))}
 * </dd>
 * <dt>d.2.5 [from b.1.1; probably=d.2.4]</dt>
 * <dd>
 * {@code d=(sqrt((x1^(2*c)-2*x1^c*x2^c+x2^(2*c))*y2^2+((-2*a*x1^(2*c))+2*a*x2^(2*c)+((-2*x2^(2*c))+4*x1^c*x2^c-2*x1^(2*c))*y1)*y2+(x1^(2*c)-2*x1^c*x2^c+x2^(2*c))*y1^2+(2*a*x1^(2*c)-2*a*x2^(2*c))*y1+a^2*x2^(2*c)-2*a^2*x1^c*x2^c+a^2*x1^(2*c))+(x2^c-x1^c)*y2+(x2^c-x1^c)*y1-a*x2^c+a*x1^c)/(2*x2^c-2*x1^c)}
 * </dd>
 * <dt>d.2.6 [from b.1.1; probably=d.2.3]</dt>
 * <dd>
 * {@code d=-(sqrt((x1^(2*c)-2*x1^c*x2^c+x2^(2*c))*y2^2+((-2*a*x1^(2*c))+2*a*x2^(2*c)+((-2*x2^(2*c))+4*x1^c*x2^c-2*x1^(2*c))*y1)*y2+(x1^(2*c)-2*x1^c*x2^c+x2^(2*c))*y1^2+(2*a*x1^(2*c)-2*a*x2^(2*c))*y1+a^2*x2^(2*c)-2*a^2*x1^c*x2^c+a^2*x1^(2*c))+(x1^c-x2^c)*y2+(x1^c-x2^c)*y1+a*x2^c-a*x1^c)/(2*x2^c-2*x1^c)}
 * </dd>
 * </dl>
 * <h3>Three Known Points</h3>
 * <dl>
 * <dt>b.3.1 [from d.2.1 or a.2.2]</dt>
 * <dd>
 * {@code b=-((x2^c-x1^c)*y3+(x1^c-x3^c)*y2+(x3^c-x2^c)*y1)/((x1^c*x3^c-x1^c*x2^c)*y1+(x1^c*x2^c-x2^c*x3^c)*y2+(x2^c-x1^c)*x3^c*y3)}
 * </dd>
 * <dt>d.3.1 [from a.2.3 or b.2.5]</dt>
 * <dd>
 * {@code d=-(((x1^c-x3^c)*y1+(x3^c-x2^c)*y2)*y3+(x2^c-x1^c)*y1*y2)/((x3^c-x2^c)*y1+(x1^c-x3^c)*y2+(x2^c-x1^c)*y3)}
 * </dd>
 * <dt>d.3.2 [from a.2.3 or b.2.5; probably=d.3.1]</dt>
 * <dd>
 * {@code d=(y1*y2*x1^c-y1*y3*x1^c-y1*y2*x2^c+y2*y3*x2^c+y1*y3*x3^c-y2*y3*x3^c)/(y2*x1^c-y3*x1^c-y1*x2^c+y3*x2^c+y1*x3^c-y2*x3^c)}
 * </dd>
 * </dl>
 * <h3>Two Known Points Direct</h3>
 * <dl>
 * <dt>a.d2.1</dt>
 * <dd>
 * {@code a=-((d-y2)*(d*x1^((log(x2^c))/(log(x2)))-d*x2^c-y1*x1^((log(x2^c))/(log(x2)))+y1*x2^c))/(d*x1^((log(x2^c))/(log(x2)))-d*x2^c-y1*x1^((log(x2^c))/(log(x2)))+y2*x2^c)}
 * </dd>
 * <dt>a.d2.2</dt>
 * <dd>
 * {@code a=-((y1-y2)*(b*x2^c+1)*(b*x1^((log(x2^c))/(log(x2)))+1))/(b*(x1^((log(x2^c))/(log(x2)))-x2^c))}
 * </dd>
 * <dt>b.d2.1</dt>
 * <dd>
 * {@code b=(y2-y1)/(-d*x1^((log(x2^c))/(log(x2)))+d*x2^c+y1*x1^((log(x2^c))/(log(x2)))-y2*x2^c)}
 * </dd>
 * <dt>b.d2.2</dt>
 * <dd>
 * {@code b=(x2^(-c)*(-sqrt((-a*x1^c+a*x2^c+y1*x1^c+y2*x1^c-y1*x2^c-y2*x2^c)^2-4*(x2^c-x1^c)*(a*y1*x1^c-a*y2*x2^c-y1*y2*x1^c+y1*y2*x2^c))-a*x1^c+a*x2^c-y1*x1^c+y2*x1^c+y1*x2^c-y2*x2^c))/(sqrt((-a*x1^c+a*x2^c+y1*x1^c+y2*x1^c-y1*x2^c-y2*x2^c)^2-4*(x2^c-x1^c)*(a*y1*x1^c-a*y2*x2^c-y1*y2*x1^c+y1*y2*x2^c))-a*x1^c+a*x2^c+y1*x1^c-y2*x1^c-y1*x2^c+y2*x2^c)}
 * </dd>
 * <dt>b.d2.3</dt>
 * <dd>
 * {@code b=(x2^(-c)*(sqrt((-a*x1^c+a*x2^c+y1*x1^c+y2*x1^c-y1*x2^c-y2*x2^c)^2-4*(x2^c-x1^c)*(a*y1*x1^c-a*y2*x2^c-y1*y2*x1^c+y1*y2*x2^c))-a*x1^c+a*x2^c-y1*x1^c+y2*x1^c+y1*x2^c-y2*x2^c))/(-sqrt((-a*x1^c+a*x2^c+y1*x1^c+y2*x1^c-y1*x2^c-y2*x2^c)^2-4*(x2^c-x1^c)*(a*y1*x1^c-a*y2*x2^c-y1*y2*x1^c+y1*y2*x2^c))-a*x1^c+a*x2^c+y1*x1^c-y2*x1^c-y1*x2^c+y2*x2^c)}
 * </dd>
 * <dt>d.d2.1</dt>
 * <dd>
 * {@code d=(b*y1*x1^((log(x2^c))/(log(x2)))-b*y2*x2^c+y1-y2)/(b*(x1^((log(x2^c))/(log(x2)))-x2^c))}
 * </dd>
 * <dt>d.d2.2</dt>
 * <dd>
 * {@code d=(-sqrt((-a*x1^c+a*x2^c+y1*x1^c+y2*x1^c-y1*x2^c-y2*x2^c)^2-4*(x2^c-x1^c)*(a*y1*x1^c-a*y2*x2^c-y1*y2*x1^c+y1*y2*x2^c))+a*x1^c-a*x2^c-y1*x1^c-y2*x1^c+y1*x2^c+y2*x2^c)/(2*(x2^c-x1^c))}
 * </dd>
 * <dt>d.d2.3</dt>
 * <dd>
 * {@code d=(sqrt((-a*x1^c+a*x2^c+y1*x1^c+y2*x1^c-y1*x2^c-y2*x2^c)^2-4*(x2^c-x1^c)*(a*y1*x1^c-a*y2*x2^c-y1*y2*x1^c+y1*y2*x2^c))+a*x1^c-a*x2^c-y1*x1^c-y2*x1^c+y1*x2^c+y2*x2^c)/(2*(x2^c-x1^c))}
 * </dd>
 * </dl>
 * <h3>Three Known Points Direct</h3>
 * <dl>
 * <dt>a.d3.1</dt>
 * <dd>
 * {@code a=((((x1^c-x2^c)*x3^(2*c)+(x2^(2*c)-x1^(2*c))*x3^c-x1^c*x2^(2*c)+x1^(2*c)*x2^c)*y1+((x2^c-x1^c)*x3^(2*c)+(x1^(2*c)-x2^(2*c))*x3^c+x1^c*x2^(2*c)-x1^(2*c)*x2^c)*y2)*y3^2+(((x2^c-x1^c)*x3^(2*c)+(x1^(2*c)-x2^(2*c))*x3^c+x1^c*x2^(2*c)-x1^(2*c)*x2^c)*y1^2+((x1^c-x2^c)*x3^(2*c)+(x2^(2*c)-x1^(2*c))*x3^c-x1^c*x2^(2*c)+x1^(2*c)*x2^c)*y2^2)*y3+((-x1^(2*c)*x2^c)+x1^c*x2^(2*c)+(x1^(2*c)-x2^(2*c))*x3^c+(x2^c-x1^c)*x3^(2*c))*y1*y2^2+(x1^(2*c)*x2^c-x1^c*x2^(2*c)+(x2^(2*c)-x1^(2*c))*x3^c+(x1^c-x2^c)*x3^(2*c))*y1^2*y2)/((x1^c*x3^(2*c)-2*x1^c*x2^c*x3^c+x1^c*x2^(2*c))*y1^2+(((-x1^c)-x2^c)*x3^(2*c)+(x1^(2*c)+2*x1^c*x2^c+x2^(2*c))*x3^c-x1^c*x2^(2*c)-x1^(2*c)*x2^c)*y1*y2+(x2^c*x3^(2*c)-2*x1^c*x2^c*x3^c+x1^(2*c)*x2^c)*y2^2+(((-x1^(2*c)*x2^c)+x1^c*x2^(2*c)+((-x2^(2*c))+2*x1^c*x2^c-x1^(2*c))*x3^c+(x1^c-x2^c)*x3^(2*c))*y2+(x1^(2*c)*x2^c-x1^c*x2^(2*c)+((-x2^(2*c))+2*x1^c*x2^c-x1^(2*c))*x3^c+(x2^c-x1^c)*x3^(2*c))*y1)*y3+(x2^(2*c)-2*x1^c*x2^c+x1^(2*c))*x3^c*y3^2)}
 * </dd>
 * <dt>a.d3.2</dt>
 * <dd>
 * {@code a=(-y1*y2*x1^c+y1*y3*x1^c+y2*y3*x1^c-y3^2*x1^c+y1*y2*x2^c-y1*y3*x2^c-y2*y3*x2^c+y3^2*x2^c)/(y2*x1^c-y3*x1^c-y1*x2^c+y3*x2^c)}
 * </dd>
 * <dt>b.d3.1</dt>
 * <dd>
 * {@code b=-((x2^c-x1^c)*y3+(x1^c-x3^c)*y2+(x3^c-x2^c)*y1)/((x1^c*x3^c-x1^c*x2^c)*y1+(x1^c*x2^c-x2^c*x3^c)*y2+(x2^c-x1^c)*x3^c*y3)}
 * </dd>
 * <dt>b.d3.2</dt>
 * <dd>
 * {@code b=(x1^(-c)*x2^(-c)*(-y2*x1^c+y3*x1^c+y1*x2^c-y3*x2^c))/(y2-y1)}
 * </dd>
 * <dt>d.d3.1</dt>
 * <dd>
 * {@code d=-(((x1^c-x3^c)*y1+(x3^c-x2^c)*y2)*y3+(x2^c-x1^c)*y1*y2)/((x3^c-x2^c)*y1+(x1^c-x3^c)*y2+(x2^c-x1^c)*y3)}
 * </dd>
 * <dt>d.d3.1</dt>
 * <dd>
 * {@code d=(-y1*y2*x1^c+y1*y3*x1^c+y1*y2*x2^c-y2*y3*x2^c)/(-y2*x1^c+y3*x1^c+y1*x2^c-y3*x2^c)}
 * </dd>
 * </dl>
 */
public final class LogisticModelWithOffsetOverLogX extends _ModelBase {

  /** create */
  public LogisticModelWithOffsetOverLogX() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double value(final double x, final double[] parameters) {
    return LogisticModelWithOffsetOverLogX._compute(x, parameters[0],
        parameters[1], parameters[2], parameters[3]);
  }

  /** {@inheritDoc} */
  @Override
  public final void gradient(final double x, final double[] parameters,
      final double[] gradient) {
    _ModelBase._logisticModelOverLogXGradient(x, parameters[0],
        parameters[1], parameters[2], gradient);
    gradient[3] = 1d;
  }

  /**
   * Compute the value of the logistic model with offset over log-scaled
   * {@code x}, i.e., {@code a/(1+b*x^c)+d} for a coordinate {@code x} and
   * model parameters {@code a}, {@code b}, {@code c}, and {@code d}.
   *
   * @param x
   *          the {@code x}-coordinate
   * @param a
   *          the first model parameter
   * @param b
   *          the second model parameter
   * @param c
   *          the third model parameter
   * @param d
   *          the fourth model parameter
   * @return the computed {@code y} value.
   */
  static final double _compute(final double x, final double a,
      final double b, final double c, final double d) {
    return (d + _ModelBase._logisticModelOverLogXCompute(x, a, b, c));
  }

  /** {@inheritDoc} */
  @Override
  public final int getParameterCount() {
    return 4;
  }

  /** {@inheritDoc} */
  @Override
  public final void canonicalizeParameters(final double[] parameters) {
    _ModelBase._logisticModelOverLogXCanonicalizeParameters(parameters);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer, final IMathRenderable x) {
    renderer.renderParameter(3, out);
    out.append('+');
    _ModelBase._logisticModelOverLogXMathRender(out, renderer, x);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer, final IMathRenderable x) {
    try (final IMath add = out.add()) {
      renderer.renderParameter(3, add);
      _ModelBase._logisticModelOverLogXMathRender(add, renderer, x);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final IParameterGuesser createParameterGuesser(
      final IMatrix data) {
    return new __LogisticWithOfffsetGuesser(data);
  }

  /** A parameter guesser for the logistic models with offset. */
  private static final class __LogisticWithOfffsetGuesser
      extends SampleBasedParameterGuesser {

    /**
     * create the guesser
     *
     * @param data
     *          the data
     */
    __LogisticWithOfffsetGuesser(final IMatrix data) {
      super(data, 4);
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
     * @param x3
     *          the fourth x-coordinate
     * @param y3
     *          the fourth y-coordinate
     * @param a
     *          the first fitting parameter
     * @param b
     *          the second fitting parameter
     * @param c
     *          the third fitting parameter
     * @param d
     *          the fourth fitting parameter
     * @return the fitting error
     */
    private static final double __error(final double x0, final double y0,
        final double x1, final double y1, final double x2, final double y2,
        final double x3, final double y3, final double a, final double b,
        final double c, final double d) {
      return _ModelBase._add4(//
          __LogisticWithOfffsetGuesser.__error(x0, y0, a, b, c, d), //
          __LogisticWithOfffsetGuesser.__error(x1, y1, a, b, c, d), //
          __LogisticWithOfffsetGuesser.__error(x2, y2, a, b, c, d), //
          __LogisticWithOfffsetGuesser.__error(x3, y3, a, b, c, d));
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
     * @param d
     *          the {@code d} value
     * @return the error
     */
    private static final double __error(final double x, final double y,
        final double a, final double b, final double c, final double d) {
      return Math.abs(
          y - LogisticModelWithOffsetOverLogX._compute(x, a, b, c, d));
    }

    /**
     * Compute {@code a} from one point {@code (x,y)} and known {@code b},
     * {@code c}, and {@coded} values.
     *
     * @param x
     *          the {@code x}-coordinate of the point
     * @param y
     *          the {@code y}-coordinate of the point
     * @param b
     *          the {@code b} value
     * @param c
     *          the {@code c} value
     * @param d
     *          the {@code d} value
     * @return the {@code a} value
     */
    private static final double __a_xybcd(final double x, final double y,
        final double b, final double c, final double d) {
      return _ModelBase._logisticModelOverLogX_a_xybc(x, (y - d), b, c);
    }

    /**
     * Compute {@code a} from two points {@code (x1,y1)} and
     * {@code (x2,y2)} and known {@code c} and {@code d} values.
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
     * @param d
     *          the {@code d} value
     * @return the {@code a} value
     */
    private static final double __a_x1y1x2y2cd(final double x1,
        final double y1, final double x2, final double y2, final double c,
        final double d) {
      final double a1, a2, x2c, px, am;

      a1 = _ModelBase._logisticModelOverLogX_a_x1y1x2y2c(x1, (y1 - d), x2,
          (y2 - d), c);
      x2c = _ModelBase._pow(x2, c);
      px = _ModelBase._pow(x1,
          (_ModelBase._log(x2c) / _ModelBase._log(x2)));
      a2 = -((d - y2)
          * (_ModelBase._add4(d * px, -d * x2c, -y1 * px, y1 * x2c)))
          / (_ModelBase._add4(d * px, -d * x2c, -y1 * px, y2 * x2c));

      if (a1 == a2) {
        return a1;
      }

      if (MathUtils.isFinite(a1)) {
        if (MathUtils.isFinite(a2)) {
          am = (0.5d * (a1 + a2));
          if (MathUtils.isFinite(am) && (((am >= a1) && (am <= a2))
              || ((am >= a2) && (am <= a1)))) {
            return am;
          }
        }
        return a1;
      }

      if (MathUtils.isFinite(a2)) {
        return a2;
      }
      return a1;
    }

    /**
     * Compute {@code b} from two points {@code (x1,y1)} and
     * {@code (x2,y2)} and known {@code c} and {@code d} values.
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
     * @param d
     *          the {@code d} value
     * @return the {@code b} value
     */
    private static final double __b_x1y1x2y2cd(final double x1,
        final double y1, final double x2, final double y2, final double c,
        final double d) {
      return _ModelBase._logisticModelOverLogX_b_x1y1x2y2c(x1, (y1 - d),
          x2, (y2 - d), c);
    }

    /**
     * Compute {@code b} from two points {@code (x1,y1)} and
     * {@code (x2,y2)} and known {@code a} and {@code d} values.
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
     * @param d
     *          the {@code d} value
     * @return the {@code b} value
     */
    private static final double __b_x1y1x2y2ad(final double x1,
        final double y1, final double x2, final double y2, final double a,
        final double d) {
      return _ModelBase._logisticModelOverLogX_b_x1y1x2y2a(x1, (y1 - d),
          x2, (y2 - d), a);
    }

    /**
     * Compute {@code b} from one point {@code (x,y)} and known {@code a},
     * {@code c}, and {@code d} values.
     *
     * @param x
     *          the {@code x}-coordinate of the point
     * @param y
     *          the {@code y}-coordinate of the point
     * @param a
     *          the {@code a} value
     * @param c
     *          the {@code c} value
     * @param d
     *          the {@code d} value
     * @return the {@code b} value
     */
    private static final double __b_xyacd(final double x, final double y,
        final double a, final double c, final double d) {
      final double b1, b2, b3, xc, xcy, e1, e2, e3, useY;

      useY = (y - d);

      xc = _ModelBase._pow(x, c);
      xcy = (xc * useY);

      b1 = ((a - useY) / xcy);
      b2 = ((a / xcy) - (1d / xc));

      if (b1 == b2) {
        return b1;
      }

      e1 = __LogisticWithOfffsetGuesser.__error(x, y, a, b1, c, d);
      e2 = __LogisticWithOfffsetGuesser.__error(x, y, a, b2, c, d);
      if (MathUtils.isFinite(e1)) {
        if (MathUtils.isFinite(e2)) {
          if (e1 == e2) {
            b3 = (0.5d * (b1 + b2));
            if ((b3 != b2) && (b3 != b1) && ((b1 < b3) || (b2 < b3))
                && ((b3 < b1) || (b3 < b2))) {
              e3 = __LogisticWithOfffsetGuesser.__error(x, y, a, b3, c, d);
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
     * Compute {@code c} from one point {@code (x,y)} and known {@code a},
     * {@code b}, and {@code d} values.
     *
     * @param x
     *          the {@code x}-coordinate of the point
     * @param y
     *          the {@code y}-coordinate of the point
     * @param a
     *          the {@code a} value
     * @param b
     *          the {@code b} value
     * @param d
     *          the {@code d} value
     * @return the {@code c} value
     */
    private static final double __c_xyabd(final double x, final double y,
        final double a, final double b, final double d) {
      final double c1, c2, c3, lx, by, e1, e2, e3, useY;

      useY = (y - d);

      lx = _ModelBase._log(x);
      if ((b <= (-1d)) && (0d < x) && (x < 1d) && (Math.abs(a) <= 0d)
          && (Math.abs(useY) <= 0d)) {
        return Math.nextUp(Math.nextUp(//
            _ModelBase._log(-1d / b) / lx));
      }

      by = b * useY;
      c1 = _ModelBase._log((a / by) - (1 / b)) / lx;
      c2 = _ModelBase._log((a - useY) / by) / lx;

      if (c1 == c2) {
        return c1;
      }

      e1 = __LogisticWithOfffsetGuesser.__error(x, y, a, b, c1, d);
      e2 = __LogisticWithOfffsetGuesser.__error(x, y, a, b, c2, d);
      if (MathUtils.isFinite(e1)) {
        if (MathUtils.isFinite(e2)) {
          if (e1 == e2) {
            c3 = (0.5d * (c1 + c2));
            if ((c3 != c2) && (c3 != c1) && ((c1 < c3) || (c2 < c3))
                && ((c3 < c1) || (c3 < c2))) {
              e3 = __LogisticWithOfffsetGuesser.__error(x, y, a, b, c3, d);
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
     * Compute {@code d} from three points {@code (x1,y1)}, {@code (x2,y2)}
     * , {@code (x3,y3)} and a known {@code c} value.
     *
     * @param x1
     *          the {@code x}-coordinate of the first point
     * @param y1
     *          the {@code y}-coordinate of the first point
     * @param x2
     *          the {@code x}-coordinate of the second point
     * @param y2
     *          the {@code y}-coordinate of the second point
     * @param x3
     *          the {@code x}-coordinate of the third point
     * @param y3
     *          the {@code y}-coordinate of the third point
     * @param c
     *          the {@code c} value
     * @return the {@code d} value
     */
    private static final double __d_x1y1x2y2x3y3c(final double x1,
        final double y1, final double x2, final double y2, final double x3,
        final double y3, final double c) {
      final double d1, d2, powx1c, powx2c, powx3c, dm;

      powx1c = _ModelBase._pow(x1, c);
      powx2c = _ModelBase._pow(x2, c);
      powx3c = _ModelBase._pow(x3, c);

      d1 = _ModelBase._add4(-y1 * y2 * powx1c, y1 * y3 * powx1c,
          y1 * y2 * powx2c, -y2 * y3 * powx2c)
          / _ModelBase._add4(-y2 * powx1c, y3 * powx1c, y1 * powx2c,
              -y3 * powx2c);
      d2 = -(((((powx1c - powx3c) * y1) + ((powx3c - powx2c) * y2)) * y3)
          + ((powx2c - powx1c) * y1 * y2))
          / (_ModelBase._add3((powx3c - powx2c) * y1,
              +(powx1c - powx3c) * y2, +(powx2c - powx1c) * y3));

      if (d1 == d2) {
        return d1;
      }

      if (MathUtils.isFinite(d1)) {
        if (MathUtils.isFinite(d2)) {
          dm = (0.5d * (d1 + d2));
          if (MathUtils.isFinite(dm) && (((dm >= d1) && (dm <= d2))
              || ((dm >= d2) && (dm <= d1)))) {
            return dm;
          }
        }
        return d1;
      }

      if (MathUtils.isFinite(d2)) {
        return d2;
      }
      return d1;
    }

    /**
     * Compute {@code b} from three points {@code (x1,y1)}, {@code (x2,y2)}
     * , {@code (x3,y3)} and a known {@code c} value.
     *
     * @param x1
     *          the {@code x}-coordinate of the first point
     * @param y1
     *          the {@code y}-coordinate of the first point
     * @param x2
     *          the {@code x}-coordinate of the second point
     * @param y2
     *          the {@code y}-coordinate of the second point
     * @param x3
     *          the {@code x}-coordinate of the third point
     * @param y3
     *          the {@code y}-coordinate of the third point
     * @param c
     *          the {@code c} value
     * @return the {@code b} value
     */
    private static final double __b_x1y1x2y2x3y3c(final double x1,
        final double y1, final double x2, final double y2, final double x3,
        final double y3, final double c) {
      final double b1, b2, powx1c, powx2c, powx3c, bm;

      powx1c = _ModelBase._pow(x1, c);
      powx2c = _ModelBase._pow(x2, c);
      powx3c = _ModelBase._pow(x3, c);

      b1 = -(_ModelBase._add3((powx2c - powx1c) * y3,
          (powx1c - powx3c) * y2, (powx3c - powx2c) * y1))
          / (_ModelBase._add3(((powx1c * powx3c) - (powx1c * powx2c)) * y1,
              ((powx1c * powx2c) - (powx2c * powx3c)) * y2,
              (powx2c - powx1c) * powx3c * y3));
      b2 = (_ModelBase._pow(x1, (-c)) * _ModelBase._pow(x2, (-c))
          * (_ModelBase._add4(-y2 * powx1c, y3 * powx1c, y1 * powx2c,
              -y3 * powx2c)))
          / (y2 - y1);

      if (b1 == b2) {
        return b1;
      }

      if (MathUtils.isFinite(b1)) {
        if (MathUtils.isFinite(b2)) {
          bm = (0.5d * (b1 + b2));
          if (MathUtils.isFinite(bm) && (((bm >= b1) && (bm <= b2))
              || ((bm >= b2) && (bm <= b1)))) {
            return bm;
          }
        }
        return b1;
      }

      if (MathUtils.isFinite(b2)) {
        return b2;
      }
      return b1;
    }

    /**
     * Compute {@code a} from three points {@code (x1,y1)}, {@code (x2,y2)}
     * , {@code (x3,y3)} and a known {@code c} value.
     *
     * @param x1
     *          the {@code x}-coordinate of the first point
     * @param y1
     *          the {@code y}-coordinate of the first point
     * @param x2
     *          the {@code x}-coordinate of the second point
     * @param y2
     *          the {@code y}-coordinate of the second point
     * @param x3
     *          the {@code x}-coordinate of the third point
     * @param y3
     *          the {@code y}-coordinate of the third point
     * @param c
     *          the {@code c} value
     * @return the {@code a} value
     */
    private static final double __a_x1y1x2y2x3y3c(final double x1,
        final double y1, final double x2, final double y2, final double x3,
        final double y3, final double c) {
      final double a1, a2, powx1c, powx2c, powx3c, powx12c, powx22c,
          powx32c, am, sy1, sy2, sy3, powx1cpowx2c, powx1cmpowx2c;

      powx1c = _ModelBase._pow(x1, c);
      powx2c = _ModelBase._pow(x2, c);
      powx3c = _ModelBase._pow(x3, c);
      powx12c = _ModelBase._pow(x1, 2d * c);
      powx22c = _ModelBase._pow(x2, 2d * c);
      powx32c = _ModelBase._pow(x3, 2d * c);
      sy1 = (y1 * y1);
      sy2 = y2 * y2;
      sy3 = (y3 * y3);
      powx1cpowx2c = powx1c * powx2c;
      powx1cmpowx2c = powx1c - powx2c;

      a1 = (((((_ModelBase._add4((powx1cmpowx2c) * powx32c,
          (powx22c - powx12c) * powx3c, -powx1c * powx22c,
          powx12c * powx2c)) * y1)
          + ((_ModelBase._add4((powx2c - powx1c) * powx32c,
              (powx12c - powx22c) * powx3c, powx1c * powx22c,
              -powx12c * powx2c)) * y2))
          * sy3)
          + ((((_ModelBase._add4((powx2c - powx1c) * powx32c,
              (powx12c - powx22c) * powx3c, powx1c * powx22c,
              -powx12c * powx2c)) * (sy1))
              + ((_ModelBase._add4((powx1cmpowx2c) * powx32c,
                  (powx22c - powx12c) * powx3c, -powx1c * powx22c,
                  powx12c * powx2c)) * (sy2)))
              * y3)
          + ((_ModelBase._add4((-powx12c * powx2c), powx1c * powx22c,
              (powx12c - powx22c) * powx3c, (powx2c - powx1c) * powx32c))
              * y1 * (sy2))
          + ((_ModelBase._add4(powx12c * powx2c, -powx1c * powx22c,
              (powx22c - powx12c) * powx3c, (powx1cmpowx2c) * powx32c))
              * (sy1) * y2))
          / (((_ModelBase._add3(powx1c * powx32c,
              -2 * powx1cpowx2c * powx3c, powx1c * powx22c)) * (sy1))
              + ((_ModelBase._add4(((-powx1c) - powx2c) * powx32c,
                  (_ModelBase._add3(powx12c, 2 * powx1cpowx2c, powx22c))
                      * powx3c,
                  -powx1c * powx22c, -powx12c * powx2c)) * y1 * y2)
              + ((_ModelBase._add3(powx2c * powx32c,
                  -2 * powx1cpowx2c * powx3c, powx12c * powx2c)) * (sy2))
              + ((((_ModelBase._add4((-powx12c * powx2c), powx1c * powx22c,
                  (_ModelBase._add3((-powx22c), 2 * powx1cpowx2c,
                      -powx12c)) * powx3c,
                  (powx1cmpowx2c) * powx32c)) * y2)
                  + ((_ModelBase._add4(powx12c * powx2c, -powx1c * powx22c,
                      +(_ModelBase._add3((-powx22c), 2 * powx1cpowx2c,
                          -powx12c)) * powx3c,
                      +(powx2c - powx1c) * powx32c)) * y1))
                  * y3)
              + ((_ModelBase._add3(powx22c, -2 * powx1cpowx2c, powx12c))
                  * powx3c * (sy3)));

      a2 = (_ModelBase._add4(-y1 * y2 * powx1c, y1 * y3 * powx1c,
          y2 * y3 * powx1c, -sy3 * powx1c)
          + _ModelBase._add4(y1 * y2 * powx2c, -y1 * y3 * powx2c,
              -y2 * y3 * powx2c, sy3 * powx2c))
          / (_ModelBase._add4(y2 * powx1c, -y3 * powx1c, -y1 * powx2c,
              y3 * powx2c));

      if (a1 == a2) {
        return a1;
      }

      if (MathUtils.isFinite(a1)) {
        if (MathUtils.isFinite(a2)) {
          am = ((0.9d * a1) + (0.1d * a2));// a1 seems to be more reliable
          if (MathUtils.isFinite(am) && (((am >= a1) && (am <= a2))
              || ((am >= a2) && (am <= a1)))) {
            return am;
          }
        }
        return a1;
      }

      if (MathUtils.isFinite(a2)) {
        return a2;
      }
      return a1;
    }

    /**
     * Check an {@code a} value for the logistic model over log-scaled
     * {@code x}, i.e., {@code a/(1+b*x^c)}.
     *
     * @param a
     *          the {@code a} value
     * @param minY
     *          the minimum {@code y} coordinate
     * @param maxY
     *          the maximum {@code y} coordinate
     * @return {@code true} if the {@code a} value is OK, {@code false}
     *         otherwise
     */
    private static final boolean __checkA(final double a,
        final double minY, final double maxY) {
      return _ModelBase._logisticModelOverLogXCheckA(a, (maxY - minY));
    }

    /**
     * Check an {@code d} value for the logistic model over log-scaled
     * {@code x}, i.e., {@code a/(1+b*x^c)+d}.
     *
     * @param d
     *          the {@code d} value
     * @param minY
     *          the minimum {@code y} coordinate
     * @return {@code true} if the {@code a} value is OK, {@code false}
     *         otherwise
     */
    private static final boolean __checkD(final double d,
        final double minY) {
      final double v;
      if ((-1e20d < d) && (d < 1e20d)) {
        if (minY == 0d) {
          return true;
        }
        v = (Math.abs(d) / Math.abs(minY));
        return ((1e-3d < v) && (v < 1e3d));
      }
      return false;
    }

    /**
     * Update a guess for {@code a}, {@code b}, {@code c}, and {@coded} by
     * using the first three points for calculating the new values (and the
     * last one only in the error computation)
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
     * @param x3
     *          the {@code x}-coordinate of the fourth point
     * @param y3
     *          the {@code y}-coordinate of the fourth point
     * @param minY
     *          the minimum {@code y} coordinate
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
        final double x3, final double y3, final double minY,
        final double maxY, final double[] dest, final double bestError) {
      double newA, newB, newC, newD, error;
      boolean hasA, hasB, hasC, hasD, changed, f1, f2, f3, f4;

      newA = newB = newC = newD = Double.NaN;
      hasA = hasB = hasC = hasD = false;

      changed = true;
      while (changed) {
        changed = false;

        if (!hasB) {
          // find B based on the existing or new A, C, and D values
          findB: {

            // Try based on stuff we already calculated only
            f1 = hasC;
            if (f1) {
              newB = __LogisticWithOfffsetGuesser.__b_x1y1x2y2x3y3c(x0, y0,
                  x1, y1, x2, y2, newC);
              if (_ModelBase._logisticModelOverLogXCheckB(newB)) {
                changed = hasB = true;
                break findB;
              }
            }

            f2 = f3 = f4 = false;
            if (hasD) {
              f2 = hasA;
              if (f2) {
                newB = __LogisticWithOfffsetGuesser.__b_x1y1x2y2ad(x0, y0,
                    x1, y1, newA, newD);
                if (_ModelBase._logisticModelOverLogXCheckB(newB)) {
                  changed = hasB = true;
                  break findB;
                }
              }

              f3 = hasC;
              if (f3) {
                newB = __LogisticWithOfffsetGuesser.__b_x1y1x2y2cd(x0, y0,
                    x1, y1, newC, newD);
                if (_ModelBase._logisticModelOverLogXCheckB(newB)) {
                  changed = hasB = true;
                  break findB;
                }

                f4 = hasA;
                if (f4) {

                  newB = __LogisticWithOfffsetGuesser.__b_xyacd(x0, y0,
                      newA, newC, newD);
                  if (_ModelBase._logisticModelOverLogXCheckB(newB)) {
                    changed = hasB = true;
                    break findB;
                  }
                }
              }
            }

            // OK, not enough data, let's try all formulas regardless of
            // what we have.
            if (!f2) {
              newB = __LogisticWithOfffsetGuesser.__b_x1y1x2y2ad(x0, y0,
                  x1, y1, (hasA ? newA : dest[0]),
                  (hasD ? newD : dest[3]));
              if (_ModelBase._logisticModelOverLogXCheckB(newB)) {
                changed = hasB = true;
                break findB;
              }
            }

            if (!f4) {
              newB = __LogisticWithOfffsetGuesser.__b_xyacd(x0, y0,
                  (hasA ? newA : dest[0]), (hasC ? newC : dest[2]),
                  (hasD ? newD : dest[3]));
              if (_ModelBase._logisticModelOverLogXCheckB(newB)) {
                changed = hasB = true;
                break findB;
              }
            }

            if (!f3) {
              newB = __LogisticWithOfffsetGuesser.__b_x1y1x2y2cd(x0, y0,
                  x1, y1, (hasC ? newC : dest[2]),
                  (hasD ? newD : dest[3]));
              if (_ModelBase._logisticModelOverLogXCheckB(newB)) {
                changed = hasB = true;
                break findB;
              }
            }

            if (!f1) {
              newB = __LogisticWithOfffsetGuesser.__b_x1y1x2y2x3y3c(x0, y0,
                  x1, y1, x2, y2, hasC ? newC : dest[2]);
              if (_ModelBase._logisticModelOverLogXCheckB(newB)) {
                changed = hasB = true;
                break findB;
              }
            }
          }
        }

        // Finished with our attempt to compute B, let's try to compute C

        if (!hasC) {
          // find C based on the existing or new A, B, and D values

          // first let's use formulas for which we already have data
          f1 = (hasA && hasB && hasC);
          if (f1) {
            newC = __LogisticWithOfffsetGuesser.__c_xyabd(x0, y0, newA,
                newB, newD);
            if (_ModelBase._logisticModelOverLogXCheckC(newC)) {
              changed = hasC = true;
            }
          }

          // now let's try all formulas
          if (!f1) {
            newC = __LogisticWithOfffsetGuesser.__c_xyabd(x0, y0,
                (hasA ? newA : dest[0]), (hasB ? newB : dest[1]),
                (hasD ? newD : dest[3]));
            if (_ModelBase._logisticModelOverLogXCheckC(newC)) {
              changed = hasC = true;
            }
          }
        }

        // Finished with our attempt to compute C, let's try to compute A

        if (!hasA) {
          findA: {
            // find A based on the existing or new B, C, and D values

            f1 = hasC;
            if (f1) {
              newA = __LogisticWithOfffsetGuesser.__a_x1y1x2y2x3y3c(x0, y0,
                  x1, y1, x2, y2, newC);
              if (__LogisticWithOfffsetGuesser.__checkA(newA, minY,
                  maxY)) {
                hasA = changed = true;
                break findA;
              }
            }

            f2 = hasB && hasC && hasD;
            if (f2) {
              newA = __LogisticWithOfffsetGuesser.__a_xybcd(x0, y0, newB,
                  newC, newD);
              if (__LogisticWithOfffsetGuesser.__checkA(newA, minY,
                  maxY)) {
                changed = hasA = true;
                break findA;
              }
            }

            f3 = hasC && hasD;
            if (f3) {
              newA = __LogisticWithOfffsetGuesser.__a_x1y1x2y2cd(x0, y0,
                  x1, y1, newC, newD);
              if (__LogisticWithOfffsetGuesser.__checkA(newA, minY,
                  maxY)) {
                changed = hasA = true;
                break findA;
              }
            }

            // ok, let's try all formulas anyway

            if (!f2) {
              f2 = hasB;
              if (f2) {
                newA = __LogisticWithOfffsetGuesser.__a_xybcd(x0, y0, newB,
                    (hasC ? newC : dest[2]), (hasD ? newD : dest[3]));
                if (__LogisticWithOfffsetGuesser.__checkA(newA, minY,
                    maxY)) {
                  changed = hasA = true;
                  break findA;
                }
              }
            }

            if (!f3) {
              newA = __LogisticWithOfffsetGuesser.__a_x1y1x2y2cd(x0, y0,
                  x1, y1, (hasC ? newC : dest[2]),
                  (hasD ? newD : dest[3]));
              if (__LogisticWithOfffsetGuesser.__checkA(newA, minY,
                  maxY)) {
                changed = hasA = true;
                break findA;
              }
            }

            if (!f2) {
              newA = __LogisticWithOfffsetGuesser.__a_xybcd(x0, y0,
                  dest[1], (hasC ? newC : dest[2]),
                  (hasD ? newD : dest[3]));
              if (__LogisticWithOfffsetGuesser.__checkA(newA, minY,
                  maxY)) {
                changed = hasA = true;
                break findA;
              }
            }
          }
        }

        // OK, we are done with A, now let's try to find D

        if (!hasD) {
          // find D based on existing or computed A, B, and C values

          findD: {
            // let's try using existing data
            f1 = hasC;
            if (f1) {
              newD = __LogisticWithOfffsetGuesser.__d_x1y1x2y2x3y3c(x0, y0,
                  x1, y1, x2, y2, newC);
              if (__LogisticWithOfffsetGuesser.__checkD(newD, minY)) {
                hasD = changed = true;
                break findD;
              }
            }

            // let's try using all formulas anyway
            if (!f1) {
              newD = __LogisticWithOfffsetGuesser.__d_x1y1x2y2x3y3c(x0, y0,
                  x1, y1, x2, y2, hasC ? newC : dest[2]);
              if (__LogisticWithOfffsetGuesser.__checkD(newD, minY)) {
                hasD = changed = true;
                break findD;
              }
            }
          }
        }

        // Last straw: nothing has changed and we do not have A

        if (!changed) {
          lastStraw: {

            if (!hasD) {
              // try to obtain d:
              // simply use the y value associated with the largest x as d

              if (x0 > x1) {
                if (x2 > x3) {
                  if (x0 > x2) {
                    newD = y0;
                  } else {
                    newD = y2;
                  }
                } else {
                  if (x0 > x3) {
                    newD = y0;
                  } else {
                    newD = y3;
                  }
                }
              } else {
                if (x2 > x3) {
                  if (x1 > x2) {
                    newD = y1;
                  } else {
                    newD = y2;
                  }
                } else {
                  if (x1 > x3) {
                    newD = y1;
                  } else {
                    newD = y3;
                  }
                }
              }

              changed = hasD = true;
              break lastStraw;
            }

            if (!hasA) {
              // try to obtain d:
              // simply use the y value associated with the smallest x as a

              if (x0 < x1) {
                if (x2 < x3) {
                  if (x0 < x2) {
                    newA = y0;
                  } else {
                    newA = y2;
                  }
                } else {
                  if (x0 < x3) {
                    newA = y0;
                  } else {
                    newA = y3;
                  }
                }
              } else {
                if (x2 < x3) {
                  if (x1 < x2) {
                    newA = y1;
                  } else {
                    newA = y2;
                  }
                } else {
                  if (x1 < x3) {
                    newA = y1;
                  } else {
                    newA = y3;
                  }
                }
              }

              if (hasD) {
                newA -= newD;
              }

              changed = hasA = true;
              break lastStraw;
            }
          }

        }

        if (hasA && hasB && hasC && hasD) {
          error = __LogisticWithOfffsetGuesser.__error(x0, y0, x1, y1, x2,
              y2, x2, y3, newA, newB, newC, newD);
          if (MathUtils.isFinite(error) && (error < bestError)) {
            dest[0] = newA;
            dest[1] = newB;
            dest[2] = newC;
            dest[3] = newD;
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
      final double minY, maxY, x0, y0, x1, y1, x2, y2, x3, y3;
      double oldError, newError;
      int steps;

      x0 = points[0];
      y0 = points[1];
      x1 = points[2];
      y1 = points[3];
      x2 = points[4];
      y2 = points[5];
      x3 = points[6];
      y3 = points[7];

      minY = ((random.nextInt(3) > 0)//
          ? Math.min(Math.min(y0, y1), Math.min(y2, y3)) : this.m_minY);
      maxY = ((random.nextInt(3) > 0)//
          ? Math.max(Math.max(y0, y1), Math.max(y2, y3)) : this.m_maxY);
      steps = 100;
      newError = Double.POSITIVE_INFINITY;

      while ((--steps) > 0) {
        __LogisticWithOfffsetGuesser.__fallback(minY, maxY, random, dest);
        for (;;) {
          oldError = newError;

          newError = __LogisticWithOfffsetGuesser.__update(x0, y0, x1, y1,
              x2, y2, x3, y3, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x0, y0, x1, y1,
              x3, y3, x2, y2, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x0, y0, x3, y3,
              x1, y1, x2, y2, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x3, y3, x0, y0,
              x1, y1, x2, y2, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x3, y3, x0, y0,
              x2, y2, x1, y1, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x0, y0, x3, y3,
              x2, y2, x1, y1, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x0, y0, x2, y2,
              x3, y3, x1, y1, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x0, y0, x2, y2,
              x1, y1, x3, y3, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x2, y2, x0, y0,
              x1, y1, x3, y3, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x2, y2, x0, y0,
              x3, y3, x1, y1, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x2, y2, x3, y3,
              x0, y0, x1, y1, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x3, y3, x2, y2,
              x0, y0, x1, y1, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x3, y3, x2, y2,
              x1, y1, x0, y0, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x2, y2, x3, y3,
              x1, y1, x0, y0, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x2, y2, x1, y1,
              x3, y3, x0, y0, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x2, y2, x1, y1,
              x0, y0, x3, y3, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x1, y1, x2, y2,
              x0, y0, x3, y3, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x1, y1, x2, y2,
              x3, y3, x0, y0, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x1, y1, x3, y3,
              x2, y2, x0, y0, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x3, y3, x1, y1,
              x2, y2, x0, y0, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x3, y3, x1, y1,
              x0, y0, x2, y2, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x1, y1, x3, y3,
              x0, y0, x2, y2, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x1, y1, x0, y0,
              x3, y3, x2, y2, minY, maxY, dest, oldError);
          newError = __LogisticWithOfffsetGuesser.__update(x1, y1, x0, y0,
              x2, y2, x3, y3, minY, maxY, dest, oldError);

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
      double maxY, minY;
      int i;

      maxY = ((random.nextInt(3) <= 0) ? this.m_maxY
          : Double.NEGATIVE_INFINITY);
      minY = ((random.nextInt(3) <= 0) ? this.m_minY
          : Double.POSITIVE_INFINITY);
      for (i = (points.length - 1); i > 0; i -= 2) {
        maxY = Math.max(maxY, points[i]);
        minY = Math.min(minY, points[i]);
      }

      __LogisticWithOfffsetGuesser.__fallback(minY, maxY, random, dest);
      return true;
    }

    /**
     * A fallback if all conventional guessing failed for the logistic
     * model with offset over log-scaled {@code x}, i.e.,
     * {@code a/(1+b*x^c)+d}.
     *
     * @param minY
     *          minimum {@code y}-coordinate encountered
     * @param maxY
     *          the maximum {@code y}-coordinate encountered
     * @param random
     *          the random number generator
     * @param parameters
     *          the parameters
     */
    private static final void __fallback(final double minY,
        final double maxY, final Random random,
        final double[] parameters) {
      final double d;

      parameters[3] = d = (minY + ((Math.max(Math.abs(minY), 1e-16d))
          * Math.abs(0.01d * random.nextGaussian())));
      _ModelBase._logisticModelOverLogXFallback((maxY - d), random,
          parameters);
    }
  }
}
