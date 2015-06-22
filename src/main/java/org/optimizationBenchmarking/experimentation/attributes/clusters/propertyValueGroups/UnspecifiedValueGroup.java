package org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.utils.document.spec.EMathComparison;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A group of data elements belonging to an unspecified parameter value
 */
public final class UnspecifiedValueGroup extends
    PropertyValueGroup<PropertyValueGroups> {

  /** the unspecified char: {@value} */
  private static final char UNSPECIFIED_CHAR = ((char) (0x2205));

  /** the unspecified string: {@value} */
  private static final String UNSPECIFIED_STRING = "unspecified"; //$NON-NLS-1$

  /** the singular value */
  private final Object m_value;

  /**
   * create the distinct value group
   *
   * @param owner
   *          the owning element set
   * @param selection
   *          the data selection
   * @param value
   *          the value
   */
  UnspecifiedValueGroup(final PropertyValueGroups owner,
      final DataSelection selection, final Object value) {
    super(owner, selection);

    if (value == null) {
      throw new IllegalArgumentException(//
          "The unspecified property value cannot be null."); //$NON-NLS-1$
    }

    this.m_value = value;
  }

  /**
   * Obtain the property value common to all data elements in this group
   *
   * @return the property value common to all data elements in this group
   */
  public final Object getValue() {
    return this.m_value;
  }

  /** {@inheritDoc} */
  @Override
  public final void appendCriterion(final ITextOutput textOut) {
    textOut.append(UnspecifiedValueGroup.UNSPECIFIED_CHAR);
  }

  /** {@inheritDoc} */
  @Override
  public final String getCriterionString() {
    return String.valueOf(UnspecifiedValueGroup.UNSPECIFIED_CHAR);
  }

  /** {@inheritDoc} */
  @Override
  public final String getPathComponentSuggestion() {
    return UnspecifiedValueGroup.UNSPECIFIED_STRING;
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    try (final IMath compare = out.compare(EMathComparison.EQUAL)) {
      this.getOwner().m_property.mathRender(compare, renderer);
      try (final IComplexText text = compare.text()) {
        text.append(UnspecifiedValueGroup.UNSPECIFIED_CHAR);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    this.getOwner().m_property.mathRender(out, renderer);
    out.append('=');
    out.append(UnspecifiedValueGroup.UNSPECIFIED_CHAR);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printShortName(final ITextOutput textOut,
      final ETextCase textCase) {
    final ETextCase use;
    use = this.getOwner().m_property.printShortName(textOut, textCase);
    textOut.append(' ');
    return ETextCase.ensure(use).appendWords("is unspecified", textOut); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    final ETextCase use;
    use = this.getOwner().m_property.printLongName(textOut, textCase);
    textOut.append(' ');
    return ETextCase.ensure(use).appendWords("is unspecified", textOut); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    ETextCase use;
    use = ETextCase.ensure(textCase).appendWords(//
        "the set of experiments whose parameter", //$NON-NLS-1$
        textOut);
    textOut.append(' ');
    use = this.getOwner().m_property.printLongName(textOut, use);
    textOut.append(' ');
    return ETextCase.ensure(use).appendWords("is unspecified", textOut); //$NON-NLS-1$
  }

}
