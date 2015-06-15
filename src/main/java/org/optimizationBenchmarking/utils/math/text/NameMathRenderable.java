package org.optimizationBenchmarking.utils.math.text;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A math renderable representing a name.
 */
public final class NameMathRenderable implements IMathRenderable {
  /** the name */
  private final String m_name;

  /**
   * create
   *
   * @param name
   *          the name of the object
   */
  public NameMathRenderable(final String name) {
    super();
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null."); //$NON-NLS-1$
    }
    this.m_name = name;
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    NameMathRenderable.mathRender(this.m_name, out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    NameMathRenderable.mathRender(this.m_name, out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return this.m_name.hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o == this) || ((o instanceof NameMathRenderable) && //
    (this.m_name.equals(((NameMathRenderable) o).m_name))));
  }

  /**
   * Render a name
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
    out.append(name);
  }

  /**
   * Render a name
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
