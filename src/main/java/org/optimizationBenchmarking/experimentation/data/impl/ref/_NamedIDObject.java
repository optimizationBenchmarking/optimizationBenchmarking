package org.optimizationBenchmarking.experimentation.data.impl.ref;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.ISemanticComponent;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * An internal set of id objects.
 */
abstract class _NamedIDObject extends _IDObject implements
    ISemanticComponent {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** this object's name */
  final String m_name;
  /** the description of this property */
  final String m_description;

  /**
   * instantiate
   *
   * @param name
   *          the name of the object
   * @param desc
   *          the description
   */
  _NamedIDObject(final String name, final String desc) {
    super();

    if ((this.m_name = TextUtils.normalize(name)) == null) {
      throw new IllegalArgumentException(//
          "Instances of " + TextUtils.className(this.getClass()) + //$NON-NLS-1$
              " must have a non-empty name, but name '" + //$NON-NLS-1$
              name + "' was provided."); //$NON-NLS-1$

    }
    this.m_description = TextUtils.normalize(desc);
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    textOut.append(this.getClass().getSimpleName());
    textOut.append("(name='"); //$NON-NLS-1$
    textOut.append(this.m_name);
    textOut.append("',id="); //$NON-NLS-1$
    textOut.append(this.m_id);
    textOut.append(')');
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return this.m_name;
  }

  /** {@inheritDoc} */
  @Override
  public final String getDescription() {
    return this.m_description;
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    super.mathRender(out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    super.mathRender(out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printShortName(final ITextOutput textOut,
      final ETextCase textCase) {
    return super.printShortName(textOut, textCase);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    return super.printLongName(textOut, textCase);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return super.printDescription(textOut, textCase);
  }

  /**
   * Obtain a suggestion for the path component of figures drawn based on
   * this object.
   *
   * @return a suggestion for the path component of figures drawn based on
   *         this component.
   */
  @Override
  public final String getPathComponentSuggestion() {
    return super.getPathComponentSuggestion();
  }
}
