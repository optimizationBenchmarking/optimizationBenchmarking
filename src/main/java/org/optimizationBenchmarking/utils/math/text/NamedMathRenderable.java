package org.optimizationBenchmarking.utils.math.text;

import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A math renderable representing a name.
 */
public final class NamedMathRenderable implements IMathRenderable {
  /** the name */
  private final String m_name;

  /**
   * create
   *
   * @param name
   *          the name of the object
   */
  public NamedMathRenderable(final String name) {
    super();
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null."); //$NON-NLS-1$
    }
    this.m_name = name;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return this.m_name;
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
  public final int hashCode() {
    return this.m_name.hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o == this) || ((o instanceof NamedMathRenderable) && //
        (this.m_name.equals(((NamedMathRenderable) o).m_name))));
  }

  /**
   * Render a name on a single string. This is a default implementation of
   * the method
   * {@link org.optimizationBenchmarking.utils.math.text.IMathRenderable#mathRender(ITextOutput, IParameterRenderer)}
   * .
   *
   * @param name
   *          the name to render
   * @param out
   *          the text output device
   * @param renderer
   *          the parameter renderer
   */
  public static final void mathRender(final String name,
      final ITextOutput out, final IParameterRenderer renderer) {
    if (out instanceof IComplexText) {
      try (final IMath math = ((IComplexText) out).inlineMath()) {
        NamedMathRenderable.mathRender(name, math, renderer);
      }
    } else {
      out.append(name);
    }
  }

  /**
   * Render a name based on a single string. This is a default
   * implementation of the method
   * {@link org.optimizationBenchmarking.utils.math.text.IMathRenderable#mathRender(IMath, IParameterRenderer)}
   * .
   *
   * @param name
   *          the name to render
   * @param out
   *          the math output device
   * @param renderer
   *          the parameter renderer
   */
  public static final void mathRender(final String name, final IMath out,
      final IParameterRenderer renderer) {
    try (final IText text = out.name()) {
      text.append(name);
    }
  }

}
