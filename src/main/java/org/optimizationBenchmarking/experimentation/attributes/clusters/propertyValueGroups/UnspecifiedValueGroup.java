package org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.utils.document.spec.EMathComparison;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A group of data elements belonging to an unspecified parameter value
 */
public final class UnspecifiedValueGroup extends
    PropertyValueGroup<PropertyValueGroups> {

  /** the unspecified string: {@value} */
  private static final String UNSPECIFIED = "unspecified"; //$NON-NLS-1$

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
    textOut.append(UnspecifiedValueGroup.UNSPECIFIED);
  }

  /** {@inheritDoc} */
  @Override
  public final String getCriterionString() {
    return String.valueOf(UnspecifiedValueGroup.UNSPECIFIED);
  }

  /** {@inheritDoc} */
  @Override
  public final String getPathComponentSuggestion() {
    return UnspecifiedValueGroup.UNSPECIFIED;
  }

  /** {@inheritDoc} */
  @Override
  public final void appendName(final IMath math) {
    try (final IMath compare = math.compare(EMathComparison.EQUAL)) {
      this.getOwner().m_property.appendName(compare);
      try (final IComplexText text = math.text()) {
        text.append(UnspecifiedValueGroup.UNSPECIFIED);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  final ETextCase _appendName(final ITextOutput textOut,
      final ETextCase textCase) {
    ETextCase next;

    next = this.getOwner().m_property.appendName(textOut, textCase);
    if (next == null) {
      next = ETextCase.IN_SENTENCE;
    }
    textOut.append(" is "); //$NON-NLS-1$
    next.appendWord(UnspecifiedValueGroup.UNSPECIFIED, textOut);
    return next.nextCase();
  }

}
