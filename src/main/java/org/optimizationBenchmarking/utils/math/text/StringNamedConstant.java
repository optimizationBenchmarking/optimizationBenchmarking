package org.optimizationBenchmarking.utils.math.text;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A named constant whose name is a simple string */
public abstract class StringNamedConstant extends NamedConstant {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the name of the constant */
  final String m_name;

  /**
   * create the constant
   *
   * @param name
   *          the name of the constant
   */
  StringNamedConstant(final String name) {
    super();
    if (name == null) {
      throw new IllegalArgumentException(//
          "Name of constant cannot be null."); //$NON-NLS-1$
    }
    this.m_name = name;
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    NamedMathRenderable.mathRender(this.m_name, out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    NamedMathRenderable.mathRender(this.m_name, out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return this.m_name;
  }
}
