package org.optimizationBenchmarking.utils.ml.fitting.models;

import java.util.Random;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.text.IMathRenderable;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.ml.fitting.impl.guessers.ParameterValueChecker;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IParameterGuesser;
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
public final class LogisticModelWithOffsetOverLogX
    extends LogisticModelOverLogX {
  /** the checker for parameter {@code d} */
  static final __CheckerD D = new __CheckerD();

  /** create */
  public LogisticModelWithOffsetOverLogX() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double value(final double x, final double[] parameters) {
    return (super.value(x, parameters) + parameters[3]);
  }

  /** {@inheritDoc} */
  @Override
  public final void gradient(final double x, final double[] parameters,
      final double[] gradient) {
    super.gradient(x, parameters, gradient);
    gradient[3] = 1d;
  }

  /** {@inheritDoc} */
  @Override
  public final int getParameterCount() {
    return 4;
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer, final IMathRenderable x) {
    renderer.renderParameter(3, out);
    out.append('+');
    super.mathRender(out, renderer, x);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer, final IMathRenderable x) {
    try (final IMath add = out.add()) {
      renderer.renderParameter(3, add);
      super.mathRender(add, renderer, x);
    }
  }

  /** {@inheritDoc} */
  @Override
  public IParameterGuesser createParameterGuesser(final IMatrix data) {
    return new __LogisticModelWithOffsetOverLogXParameterGuesser(data);
  }

  /**
   * Compute {@code a} from one point {@code (x,y)} and known {@code b},
   * {@code c}, and {@code d} values.
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
  static final double _a_xybcd(final double x, final double y,
      final double b, final double c, final double d) {
    return LogisticModelOverLogX._a_xybc(x, Math.max(0d, (y - d)), b, c);
  }

  /**
   * Compute {@code a} from two points {@code (x1,y1)} and {@code (x2,y2)}
   * and known {@code c} and {@code d} values.
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
  static final double _a_x1y1x2y2cd(final double x1, final double y1,
      final double x2, final double y2, final double c, final double d) {
    return LogisticModelOverLogX._a_x1y1x2y2c(x1, Math.max(0d, (y1 - d)),
        x2, Math.max(0d, (y2 - d)), c);
  }

  /**
   * Compute {@code b} from two points {@code (x1,y1)} and {@code (x2,y2)}
   * and known {@code c} and {@code d} values.
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
  static final double _b_x1y1x2y2cd(final double x1, final double y1,
      final double x2, final double y2, final double c, final double d) {
    return LogisticModelOverLogX._b_x1y1x2y2c(x1, Math.max(0d, y1 - d), x2,
        Math.max(0d, y2 - d), c);
  }

  /**
   * Compute {@code b} from two points {@code (x1,y1)} and {@code (x2,y2)}
   * and known {@code a} and {@code d} values.
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
  static final double _b_x1y1x2y2ad(final double x1, final double y1,
      final double x2, final double y2, final double a, final double d) {
    return LogisticModelOverLogX._b_x1y1x2y2a(x1, Math.max(0d, y1 - d), x2,
        Math.max(0d, y2 - d), a);
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
  static final double _b_xyacd(final double x, final double y,
      final double a, final double c, final double d) {
    return LogisticModelOverLogX._b_xyac(x, Math.max(0d, y - d), a, c);
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
  static final double _c_xyabd(final double x, final double y,
      final double a, final double b, final double d) {
    return LogisticModelOverLogX._c_xyab(x, Math.max(0d, y - d), a, b);
  }

  /**
   * Compute {@code d} from two points {@code (x1,y1)} and {@code (x2,y2)}
   * , {@code (x3,y3)} and known {@code b} and {@code c} values.
   *
   * @param x1
   *          the {@code x}-coordinate of the first point
   * @param y1
   *          the {@code y}-coordinate of the first point
   * @param x2
   *          the {@code x}-coordinate of the second point
   * @param y2
   *          the {@code y}-coordinate of the second point
   * @param b
   *          the {@code b} value
   * @param c
   *          the {@code c} value
   * @return the {@code d} value
   */
  static final double _d_x1y1x2y2bc(final double x1, final double y1,
      final double x2, final double y2, final double b, final double c) {
    final double x2c, px;

    x2c = _ModelBase._pow(x2, c);
    px = _ModelBase._pow(x1,
        ((_ModelBase._log(x2c)) / (_ModelBase._log(x2))));
    return (_ModelBase._add(b * y1 * px, -b * y2 * x2c, y1, -y2))
        / (b * (px - x2c));
  }

  /**
   * Compute {@code c} from two points {@code (x1,y1)} and {@code (x2,y2)}
   * , {@code (x3,y3)} and known {@code a} and {@code d} values.
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
   * @return the {@code c} value
   */
  static final double _c_x1y1x2y2ad(final double x1, final double y1,
      final double x2, final double y2, final double a, final double d) {
    return (_ModelBase
        ._log(((d - y2) * ((d + a) - y1)) / ((d - y1) * ((d + a) - y2))))
        / (_ModelBase._log(x1) - _ModelBase._log(x2));
  }

  /**
   * Compute {@code b} from three points {@code (x1,y1)}, {@code (x2,y2)} ,
   * {@code (x3,y3)} and a known {@code c} value.
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
  static final double _b_x1y1x2y2x3y3c(final double x1, final double y1,
      final double x2, final double y2, final double x3, final double y3,
      final double c) {
    final double powx1c, powx2c, powx3c;

    powx1c = _ModelBase._pow(x1, c);
    powx2c = _ModelBase._pow(x2, c);
    powx3c = _ModelBase._pow(x3, c);

    return ParameterValueChecker.choose(//
        -(_ModelBase._add((powx2c - powx1c) * y3, (powx1c - powx3c) * y2,
            (powx3c - powx2c) * y1))
            / (_ModelBase._add(
                ((powx1c * powx3c) - (powx1c * powx2c)) * y1,
                ((powx1c * powx2c) - (powx2c * powx3c)) * y2,
                (powx2c - powx1c) * powx3c * y3)), //
        (_ModelBase._pow(x1, (-c)) * _ModelBase._pow(x2, (-c))
            * (_ModelBase._add(-y2 * powx1c, y3 * powx1c, y1 * powx2c,
                -y3 * powx2c)))
            / (y2 - y1), //
        LogisticModelOverLogX.B);
  }

  /**
   * Compute {@code a} from three points {@code (x1,y1)}, {@code (x2,y2)} ,
   * {@code (x3,y3)} and a known {@code c} value.
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
  static final double _a_x1y1x2y2x3y3c(final double x1, final double y1,
      final double x2, final double y2, final double x3, final double y3,
      final double c) {
    final double powx1c, powx2c, powx3c, powx12c, powx22c, powx32c, sy1,
        sy2, sy3, powx1cpowx2c, powx1cmpowx2c;

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

    return ParameterValueChecker.choose(//
        ((_ModelBase._add(
            ((((_ModelBase._add((powx1cmpowx2c) * powx32c,
                (powx22c - powx12c) * powx3c, -powx1c * powx22c,
                powx12c * powx2c)) * y1)
            + ((_ModelBase._add((powx2c - powx1c) * powx32c,
                (powx12c - powx22c) * powx3c, powx1c * powx22c,
                -powx12c * powx2c)) * y2)) * sy3),
            ((((_ModelBase._add((powx2c - powx1c) * powx32c,
                (powx12c - powx22c) * powx3c, powx1c * powx22c,
                -powx12c * powx2c)) * (sy1))
                + ((_ModelBase._add((powx1cmpowx2c) * powx32c,
                    (powx22c - powx12c) * powx3c, -powx1c * powx22c,
                    powx12c * powx2c)) * (sy2)))
                * y3),
            ((_ModelBase._add((-powx12c * powx2c), powx1c * powx22c,
                (powx12c - powx22c) * powx3c, (powx2c - powx1c) * powx32c))
                * y1 * (sy2)),
            ((_ModelBase._add(powx12c * powx2c, -powx1c * powx22c,
                (powx22c - powx12c) * powx3c, (powx1cmpowx2c) * powx32c))
                * (sy1) * y2)))
            / (_ModelBase._add(
                ((_ModelBase._add(powx1c * powx32c,
                    -2 * powx1cpowx2c * powx3c, powx1c * powx22c))
                * (sy1)),
                ((_ModelBase._add(((-powx1c) - powx2c) * powx32c,
                    (_ModelBase._add(powx12c, 2 * powx1cpowx2c, powx22c))
                        * powx3c,
                    -powx1c * powx22c, -powx12c * powx2c)) * y1 * y2),
                ((_ModelBase._add(powx2c * powx32c,
                    -2 * powx1cpowx2c * powx3c, powx12c * powx2c))
                    * (sy2)),
                ((((_ModelBase._add((-powx12c * powx2c), powx1c * powx22c,
                    (_ModelBase._add((-powx22c), 2 * powx1cpowx2c,
                        -powx12c)) * powx3c,
                    (powx1cmpowx2c) * powx32c)) * y2)
                    + ((_ModelBase._add(powx12c * powx2c,
                        -powx1c * powx22c,
                        +(_ModelBase._add((-powx22c), 2 * powx1cpowx2c,
                            -powx12c)) * powx3c,
                        +(powx2c - powx1c) * powx32c)) * y1))
                    * y3),
                ((_ModelBase._add(powx22c, -2 * powx1cpowx2c, powx12c))
                    * powx3c * (sy3))))), //
        ((_ModelBase._add(-y1 * y2 * powx1c, y1 * y3 * powx1c,
            y2 * y3 * powx1c, -sy3 * powx1c)
            + _ModelBase._add(y1 * y2 * powx2c, -y1 * y3 * powx2c,
                -y2 * y3 * powx2c, sy3 * powx2c))
            / (_ModelBase._add(y2 * powx1c, -y3 * powx1c, -y1 * powx2c,
                y3 * powx2c))), //
        LogisticModelOverLogX.A);
  }

  /**
   * Compute {@code d} from three points {@code (x1,y1)}, {@code (x2,y2)} ,
   * {@code (x3,y3)} and a known {@code c} value.
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
  static final double _d_x1y1x2y2x3y3c(final double x1, final double y1,
      final double x2, final double y2, final double x3, final double y3,
      final double c) {
    final double powx1c, powx2c, powx3c;

    powx1c = _ModelBase._pow(x1, c);
    powx2c = _ModelBase._pow(x2, c);
    powx3c = _ModelBase._pow(x3, c);

    return ParameterValueChecker.choose(//
        _ModelBase._add(-y1 * y2 * powx1c, y1 * y3 * powx1c,
            y1 * y2 * powx2c, -y2 * y3 * powx2c)
            / _ModelBase._add(-y2 * powx1c, y3 * powx1c, y1 * powx2c,
                -y3 * powx2c), //
        -(((((powx1c - powx3c) * y1) + ((powx3c - powx2c) * y2)) * y3)
            + ((powx2c - powx1c) * y1 * y2))
            / (_ModelBase._add((powx3c - powx2c) * y1,
                +(powx1c - powx3c) * y2, +(powx2c - powx1c) * y3)), //
        LogisticModelWithOffsetOverLogX.D);
  }

  /**
   * Compute {@code d} from two points {@code (x1,y1)} and {@code (x2,y2)}
   * , {@code (x3,y3)} and known {@code c} and {@code a} values.
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
   * @param c
   *          the {@code c} value
   * @return the {@code d} value
   */
  static final double _d_x1y1x2y2ac(final double x1, final double y1,
      final double x2, final double y2, final double a, final double c) {
    final double d1, x1c, x2c;

    x1c = _ModelBase._pow(x1, c);
    x2c = _ModelBase._pow(x2, c);

    d1 = (_ModelBase
        ._sqrt(_ModelBase
            ._sqr(_ModelBase._add(-a * x1c, a * x2c, y1 * x1c, y2 * x1c,
                -y1 * x2c, -y2 * x2c))
        - (4d * (x2c - x1c)
            * (_ModelBase._add(a * y1 * x1c, -a * y2 * x2c, -y1 * y2 * x1c,
                y1 * y2 * x2c))))
        + _ModelBase._add(a * x1c, -a * x2c, -y1 * x1c, -y2 * x1c,
            y1 * x2c, y2 * x2c))
        / (2d * (x2c - x1c));

    return ParameterValueChecker.choose(d1, -d1,
        LogisticModelWithOffsetOverLogX.D);
  }

  /** the parameter guesser */
  private final class __LogisticModelWithOffsetOverLogXParameterGuesser
      extends _LogisticModelOverLogXParameterGuesser {
    /**
     * Create the parameter guesser
     *
     * @param data
     *          the data
     */
    __LogisticModelWithOffsetOverLogXParameterGuesser(final IMatrix data) {
      super(data);
    }

    /** {@inheritDoc} */
    @Override
    protected final boolean fallback(final double[] points,
        final double[] dest, final Random random) {
      double minY;
      int i;

      findMinY: {
        if (random.nextInt(10) <= 0) {
          minY = this.m_minY;
          if (MathUtils.isFinite(minY)) {
            break findMinY;
          }
        }
        minY = Double.POSITIVE_INFINITY;
        for (i = (points.length - 1); i > 0; i -= 2) {
          minY = Math.min(minY, points[i]);
        }
      }

      minY = (minY * ((1d + //
          Math.abs(0.05d * random.nextGaussian()))));
      dest[3] = minY;
      this._fallback(points, dest, random, minY);
      return true;
    }

    /** {@inheritDoc} */
    @Override
    protected void guessBasedOnPermutation(final double[] points,
        final double[] bestGuess, final double[] destGuess) {

      final double x0, y0, x1, y1, x2, y2, /* x3, y3, */ oldA, oldB, oldC,
          oldD;
      double newA, newB, newC, newD;
      boolean hasA, hasB, hasC, hasD, changed;

      hasA = hasB = hasC = hasD = false;
      x0 = points[0];
      y0 = points[1];
      x1 = points[2];
      y1 = points[3];
      x2 = points[4];
      y2 = points[5];
      // x3 = points[6];
      // y3 = points[7];
      newA = oldA = bestGuess[0];
      newB = oldB = bestGuess[1];
      newC = oldC = bestGuess[2];
      newD = oldD = bestGuess[3];

      changed = true;
      while (changed) {
        changed = false;

        if (!hasB) {
          findB: {
            // find B based on the existing or new A, C and D values
            newB = LogisticModelWithOffsetOverLogX._b_x1y1x2y2ad(x0, y0,
                x1, y1, (hasA ? newA : oldA), (hasD ? newD : oldD));
            if (LogisticModelOverLogX.B.check(newB)) {
              changed = hasB = true;
              break findB;
            }

            newB = LogisticModelWithOffsetOverLogX._b_xyacd(x0, y0,
                (hasA ? newA : bestGuess[0]), (hasC ? newC : oldC),
                (hasD ? newD : oldD));
            if (LogisticModelOverLogX.B.check(newB)) {
              changed = hasB = true;
              break findB;
            }

            newB = LogisticModelWithOffsetOverLogX._b_x1y1x2y2cd(x0, y0,
                x1, y1, (hasC ? newC : oldC), (hasD ? newD : oldD));
            if (LogisticModelOverLogX.B.check(newB)) {
              changed = hasB = true;
              break findB;
            }

            newB = LogisticModelWithOffsetOverLogX._b_x1y1x2y2x3y3c(x0, y0,
                x1, y1, x2, y2, (hasC ? newC : oldC));
            if (LogisticModelOverLogX.B.check(newB)) {
              changed = hasB = true;
              break findB;
            }

          }
        }

        if (!hasC) {
          findC: {
            // find C based on the existing or new A and B values
            newC = LogisticModelWithOffsetOverLogX._c_x1y1x2y2ad(x0, y0,
                x1, y1, (hasA ? newA : oldA), (hasD ? newD : oldD));
            if (LogisticModelOverLogX.C.check(newC)) {
              changed = hasC = true;
              break findC;
            }

            newC = LogisticModelWithOffsetOverLogX._c_xyabd(x0, y0,
                (hasA ? newA : oldA), (hasB ? newB : oldB),
                (hasD ? newD : oldD));
            if (LogisticModelOverLogX.C.check(newC)) {
              changed = hasC = true;
              break findC;
            }
          }
        }

        if (!hasA) {
          findA: {
            // find A based on the existing or new B and C values

            if (hasB) {
              newA = LogisticModelWithOffsetOverLogX._a_xybcd(x0, y0, newB,
                  (hasC ? newC : oldC), (hasD ? newD : oldD));
              if (LogisticModelOverLogX.A.check(newA)) {
                changed = hasA = true;
                break findA;
              }
            }

            newA = LogisticModelWithOffsetOverLogX._a_x1y1x2y2cd(x0, y0,
                x1, y1, (hasC ? newC : oldC), (hasD ? newD : oldD));
            if (LogisticModelOverLogX.A.check(newA)) {
              changed = hasA = true;
              break findA;
            }

            newA = LogisticModelWithOffsetOverLogX._a_x1y1x2y2x3y3c(x0, y0,
                x1, y1, x2, y2, (hasC ? newC : oldC));
            if (LogisticModelOverLogX.A.check(newA)) {
              changed = hasA = true;
              break findA;
            }

            if (!hasB) {
              newA = LogisticModelWithOffsetOverLogX._a_xybcd(x0, y0, oldB,
                  (hasC ? newC : oldC), (hasD ? newD : oldD));
              if (LogisticModelOverLogX.A.check(newA)) {
                changed = hasA = true;
                break findA;
              }
            }
          }
        }

        if (!(hasD)) {
          findD: {
            newD = LogisticModelWithOffsetOverLogX._d_x1y1x2y2ac(x0, y0,
                x1, y1, (hasA ? newA : oldA), (hasC ? newC : oldC));
            if (LogisticModelWithOffsetOverLogX.D.check(newD)) {
              changed = hasD = true;
              break findD;
            }
            newD = LogisticModelWithOffsetOverLogX._d_x1y1x2y2bc(x0, y0,
                x1, y1, (hasB ? newB : oldB), (hasC ? newC : oldC));
            if (LogisticModelWithOffsetOverLogX.D.check(newD)) {
              changed = hasD = true;
              break findD;
            }
            newD = LogisticModelWithOffsetOverLogX._d_x1y1x2y2x3y3c(x0, y0,
                x1, y1, x2, y2, (hasC ? newC : oldC));
            if (LogisticModelWithOffsetOverLogX.D.check(newD)) {
              changed = hasD = true;
              break findD;
            }
          }
        }

        // try to do whatever
        if (!changed) {
          emergency: {
            if (!hasD) {
              newD = Math.min(Math.min(y0, y1), y2);// Math.min(y2, y3));
              changed = hasD = LogisticModelWithOffsetOverLogX.D
                  .check(newD);
              if (changed) {
                break emergency;
              }
            }

            if (!hasA) {
              findA2: {
                if (Math.abs(x0) <= 0d) {
                  newA = Math.max(Double.MIN_NORMAL,
                      y0 - (hasD ? newD : oldD));
                  changed = hasA = true;
                  break findA2;
                }
                if (Math.abs(x1) <= 0d) {
                  newA = Math.max(Double.MIN_NORMAL,
                      y1 - (hasD ? newD : oldD));
                  changed = hasA = true;
                  break findA2;
                }
                if (Math.abs(x2) <= 0d) {
                  newA = Math.max(Double.MIN_NORMAL,
                      y2 - (hasD ? newD : oldD));
                  changed = hasA = true;
                  break findA2;
                }
                // if (Math.abs(x3) <= 0d) {
                // newA = Math.max(Double.MIN_NORMAL,
                // y3 - (hasD ? newD : oldD));
                // changed = hasA = true;
                // break findA2;
                // }
              }
            }
          }
        }
      }

      destGuess[0] = newA;
      destGuess[1] = newB;
      destGuess[2] = newC;
      destGuess[3] = newD;
    }
  }

  /** the checker for parameter {@code c}. */
  private static final class __CheckerD extends ParameterValueChecker {

    /** create */
    __CheckerD() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final boolean check(final double value) {
      return (MathUtils.isFinite(value) && (Math.abs(value) < 1e30d));
    }
  }
}