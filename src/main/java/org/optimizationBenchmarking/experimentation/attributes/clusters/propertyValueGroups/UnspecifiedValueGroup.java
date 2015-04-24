package org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A group of data elements belonging to an unspecified parameter value
 */
public final class UnspecifiedValueGroup extends
    PropertyValueGroup<PropertyValueGroups> {

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
    textOut.append(this.m_value);
  }

  /** {@inheritDoc} */
  @Override
  public final String getCriterionString() {
    return this.m_value.toString();
  }
}
