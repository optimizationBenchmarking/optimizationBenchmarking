package org.optimizationBenchmarking.experimentation.attributes.functions;

import java.io.Closeable;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A function context allowing the use of an initialized data
 * transformation.
 */
public class TransformationFunction extends UnaryFunction implements
    Closeable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the data transformation */
  private _Transformation<?> m_trafo;

  /**
   * create the function context
   *
   * @param trafo
   *          the data transformation
   */
  TransformationFunction(final _Transformation<?> trafo) {
    super();
    if (trafo == null) {
      throw new IllegalArgumentException(
          "Data transformation cannot be null."); //$NON-NLS-1$
    }
    this.m_trafo = trafo;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return (this.m_trafo.hashCode() ^ 3455269);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof TransformationFunction) {
      return this.m_trafo.equals(((TransformationFunction) o).m_trafo);
    }
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0) {
    return this.m_trafo.m_func.computeAsByte(x0);
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0) {
    return this.m_trafo.m_func.computeAsShort(x0);
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0) {
    return this.m_trafo.m_func.computeAsInt(x0);
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0) {
    return this.m_trafo.m_func.computeAsLong(x0);
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x0) {
    return this.m_trafo.m_func.computeAsFloat(x0);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0) {
    return this.m_trafo.m_func.computeAsDouble(x0);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0) {
    return this.m_trafo.m_func.computeAsDouble(x0);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final int x0) {
    return this.m_trafo.m_func.computeAsDouble(x0);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isLongArithmeticAccurate() {
    return this.m_trafo.m_func.isLongArithmeticAccurate();
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    this.m_trafo.mathRender(out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    this.m_trafo.mathRender(out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final void close() {
    final _Transformation<?> trafo;

    trafo = this.m_trafo;
    this.m_trafo = null;
    if (trafo != null) {
      trafo._endUse();
    }
  }
}
