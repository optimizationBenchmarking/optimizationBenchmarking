package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.impl.SemanticComponentUtils;
import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElement;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.INamedElement}
 * interface.
 */
public class AbstractNamedElement extends DataElement implements
    INamedElement {

  /** Create the abstract named element. */
  protected AbstractNamedElement() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }

  /** {@inheritDoc} */
  @Override
  public String getDescription() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public IDataElement getOwner() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public void mathRender(ITextOutput out, IParameterRenderer renderer) {
    SemanticComponentUtils.mathRender(this.getName(), out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public void mathRender(IMath out, IParameterRenderer renderer) {
    SemanticComponentUtils.mathRender(this.getName(), out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase appendName(final ITextOutput textOut,
      final ETextCase textCase) {
    return SemanticComponentUtils.appendName(this.getName(), textOut,
        textCase, false);
  }

  /** {@inheritDoc} */
  @Override
  public String getPathComponentSuggestion() {
    return this.getName();
  }
}
