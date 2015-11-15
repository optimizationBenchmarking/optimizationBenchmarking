package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.fitting.spec.IParameterGuesser;
import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add3;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.text.IMathRenderable;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A polynomial of degree 2 to be fitted in order to model the relationship
 * of similarly-typed dimensions (time-time, objective-objective):
 * {@code a+b*x+c*x*x}.
 */
public final class PolynomialModel extends ParametricUnaryFunction {

  /** create */
  public PolynomialModel() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double value(final double x, final double[] parameters) {
    return Add3.INSTANCE.computeAsDouble(parameters[0],
        (parameters[1] * x), //
        (parameters[2] * x * x));
  }

  /** {@inheritDoc} */
  @Override
  public final void gradient(final double x, final double[] parameters,
      final double[] gradient) {
    gradient[0] = 1d;// a
    gradient[1] = x;// b
    gradient[2] = x * x;// c
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
    return new _PolynomialGuesser(data);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer, final IMathRenderable x) {
    renderer.renderParameter(0, out);
    out.append('+');
    renderer.renderParameter(1, out);
    out.append('*');
    x.mathRender(out, renderer);
    out.append('+');
    renderer.renderParameter(2, out);
    out.append('*');
    x.mathRender(out, renderer);
    out.append('\u00b2');
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer, final IMathRenderable x) {
    try (final IMath add = out.add()) {
      renderer.renderParameter(0, out);

      try (final IMath add2 = add.add()) {
        try (final IMath mul = add2.mul()) {
          renderer.renderParameter(1, out);
          x.mathRender(mul, renderer);
        }

        try (final IMath add3 = add2.add()) {
          try (final IMath mul = add3.mul()) {
            renderer.renderParameter(2, out);
            x.mathRender(mul, renderer);
          }
        }
      }
    }
  }
}
