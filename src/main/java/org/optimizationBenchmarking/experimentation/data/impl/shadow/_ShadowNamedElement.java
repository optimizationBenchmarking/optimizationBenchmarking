package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElement;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A shadow named element is basically a shadow of another named element
 * with a different owner and potentially different attributes. If all
 * associated data of this element is the same, it will delegate
 * attribute-based computations to that named element.
 *
 * @param <OT>
 *          the owner type
 * @param <ST>
 *          the shadow type
 */
class _ShadowNamedElement<OT extends IDataElement, ST extends INamedElement>
    extends _ShadowDataElement<OT, ST> implements INamedElement {

  /**
   * create the shadow named element
   *
   * @param owner
   *          the owning element
   * @param shadow
   *          the named element to shadow
   */
  _ShadowNamedElement(final OT owner, final ST shadow) {
    super(owner, shadow);
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return this.m_shadowUnpacked.getName();
  }

  /** {@inheritDoc} */
  @Override
  public final String getDescription() {
    return this.m_shadowUnpacked.getDescription();
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    this.m_shadowUnpacked.mathRender(out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    this.m_shadowUnpacked.mathRender(out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printShortName(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.m_shadowUnpacked.printShortName(textOut, textCase);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.m_shadowUnpacked.printLongName(textOut, textCase);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.m_shadowUnpacked.printDescription(textOut, textCase);
  }

  /** {@inheritDoc} */
  @Override
  public final String getPathComponentSuggestion() {
    return this.m_shadowUnpacked.getPathComponentSuggestion();
  }
}
