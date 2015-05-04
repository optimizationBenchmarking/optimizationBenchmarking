package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElement;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IMathName;
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
  public void appendName(final IMath math) {
    try (final IMathName name = math.name()) {
      name.append(this.getName());
    }
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase appendName(final ITextOutput textOut,
      final ETextCase textCase) {
    if (textOut instanceof IComplexText) {
      try (final IMath math = ((IComplexText) textOut).inlineMath()) {
        this.appendName(math);
      }
    } else {
      textOut.append(this.getName());
    }

    return ETextCase.ensure(textCase).nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public String getPathComponentSuggestion() {
    return this.getName();
  }
}
