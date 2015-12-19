package org.optimizationBenchmarking.utils.ml.fitting.spec;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.text.DoubleConstantParameters;
import org.optimizationBenchmarking.utils.math.text.IMathRenderable;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.math.text.NamedMathRenderable;
import org.optimizationBenchmarking.utils.ml.fitting.impl.guessers.DefaultParameterGuesser;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A base class for representing a real function that depends on one
 * independent variable plus some extra parameters.
 */
public abstract class ParametricUnaryFunction implements IMathRenderable {

  /** create */
  protected ParametricUnaryFunction() {
    super();
  }

  /**
   * Compute the value of the function.
   *
   * @param x
   *          Point for which the function value should be computed.
   * @param parameters
   *          function parameters.
   * @return the value.
   */
  public abstract double value(double x, double[] parameters);

  /**
   * Compute the gradient of the function with respect to its parameters.
   *
   * @param x
   *          Point for which the function value should be computed.
   * @param parameters
   *          Function parameters.
   * @param gradient
   *          the variable to receive the gradient
   */
  public abstract void gradient(double x, double[] parameters,
      final double[] gradient);

  /**
   * Create a unary function representing a specific configuration of this
   * parametric unary function.
   *
   * @param parameters
   *          the parameter configuration
   * @return the unary function
   */
  public final UnaryFunction toUnaryFunction(final double[] parameters) {
    return new __InternalUnary(parameters);
  }

  /**
   * Get the number of parameters
   *
   * @return the number of parameters
   */
  public abstract int getParameterCount();

  /**
   * Create a parameter guesser
   *
   * @param data
   *          the data matrix
   * @return the parameter guesser
   */
  public IParameterGuesser createParameterGuesser(final IMatrix data) {
    return new DefaultParameterGuesser();
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    this.mathRender(out, renderer, new NamedMathRenderable("x")); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    this.mathRender(out, renderer, new NamedMathRenderable("x")); //$NON-NLS-1$
  }

  /**
   * Render the unary function, but provide a renderable for the parameter
   * {@code x}
   *
   * @param out
   *          the destination
   * @param renderer
   *          the parameter renderer for the {@link #getParameterCount()}
   *          parameters
   * @param x
   *          the parameter renderer for the {@code x} input of the
   *          function
   */
  public abstract void mathRender(ITextOutput out,
      IParameterRenderer renderer, final IMathRenderable x);

  /**
   * Render the unary function, but provide a renderable for the parameter
   * {@code x}
   *
   * @param out
   *          the destination
   * @param renderer
   *          the parameter renderer for the {@link #getParameterCount()}
   *          parameters
   * @param x
   *          the parameter renderer for the {@code x} input of the
   *          function
   */
  public abstract void mathRender(IMath out, IParameterRenderer renderer,
      final IMathRenderable x);

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    return ((o == this) || //
        ((o != null) && (o.getClass() == this.getClass())));
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return ((this.getClass().hashCode() - 1709) * 991);
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }

  /**
   * The internal unary function wrapping a specific setup of a parametric
   * unary function
   */
  private final class __InternalUnary extends UnaryFunction {
    /** the serial version uid */
    private static final long serialVersionUID = 1L;
    /** the parameters */
    private final double[] m_parameters;

    /**
     * create
     *
     * @param parameters
     *          the parameters
     */
    __InternalUnary(final double[] parameters) {
      super();
      this.m_parameters = parameters;
    }

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
      return HashUtils.combineHashes(//
          Arrays.hashCode(this.m_parameters), //
          ParametricUnaryFunction.this.hashCode());
    }

    /**
     * get the owner
     *
     * @return the owner
     */
    private final ParametricUnaryFunction __owner() {
      return ParametricUnaryFunction.this;
    }

    /** {@inheritDoc} */
    @Override
    public final boolean equals(final Object o) {
      final __InternalUnary x;
      if (o == this) {
        return true;
      }
      if (o instanceof __InternalUnary) {
        x = ((__InternalUnary) o);
        if (ParametricUnaryFunction.this.equals(x.__owner())) {
          return Arrays.equals(this.m_parameters, x.m_parameters);
        }
      }
      return false;
    }

    /** {@inheritDoc} */
    @Override
    public final double computeAsDouble(final double x0) {
      return ParametricUnaryFunction.this.value(x0, this.m_parameters);
    }

    /** {@inheritDoc} */
    @Override
    public final void mathRender(final ITextOutput out,
        final IParameterRenderer renderer) {
      ParametricUnaryFunction.this.mathRender(out, //
          new DoubleConstantParameters(this.m_parameters), //
          new __SingleRenderable(renderer));
    }

    /** {@inheritDoc} */
    @Override
    public final void mathRender(final IMath out,
        final IParameterRenderer renderer) {
      ParametricUnaryFunction.this.mathRender(out, //
          new DoubleConstantParameters(this.m_parameters), //
          new __SingleRenderable(renderer));
    }
  }

  /** the single renderable */
  private static final class __SingleRenderable
      implements IMathRenderable {

    /** the renderer */
    private final IParameterRenderer m_render;

    /**
     * create
     *
     * @param render
     *          the parameter renderer
     */
    __SingleRenderable(final IParameterRenderer render) {
      super();
      this.m_render = render;
    }

    /** {@inheritDoc} */
    @Override
    public final void mathRender(final ITextOutput out,
        final IParameterRenderer renderer) {
      this.m_render.renderParameter(0, out);
    }

    /** {@inheritDoc} */
    @Override
    public final void mathRender(final IMath out,
        final IParameterRenderer renderer) {
      this.m_render.renderParameter(0, out);
    }
  }
}
