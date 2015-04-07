package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.DataElement;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A group of values. This class is most likely used to group property
 * values.
 * 
 * @param <DT>
 *          the data set type
 */
public abstract class PropertyValueGroup<DT extends DataElement> extends
    _GroupBase {

  /** the data elements */
  private final ArraySetView<DT> m_data;
  /** the owning value group set */
  PropertyValueGroups<DT> m_owner;

  /**
   * create the property value group
   * 
   * @param data
   *          the data
   */
  PropertyValueGroup(final DT[] data) {
    super();

    if (data == null) {
      throw new IllegalArgumentException(//
          "Data elements belonging to property value group must not be null."); //$NON-NLS-1$
    }

    this.m_data = new ArraySetView<>(data);
  }

  /**
   * Get the owning group set
   * 
   * @return the owning group set
   */
  public PropertyValueGroups<DT> getOwner() {
    return this.m_owner;
  }

  /**
   * Obtain the data elements belonging to this value group
   * 
   * @return the data elements belonging to this value group
   */
  public final ArraySetView<DT> getElements() {
    return this.m_data;
  }

  /** {@inheritDoc} */
  @Override
  protected int calcHashCode() {
    return HashUtils.hashCode(this.m_data);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public final boolean equals(final Object o) {
    final PropertyValueGroup pvg;

    if (o == this) {
      return true;
    }

    if (o instanceof PropertyValueGroup) {
      pvg = ((PropertyValueGroup) o);
      return this.m_data.equals(pvg.m_data);
    }

    return false;
  }

  /**
   * Does the group contain the given value object? Or, at least, would it
   * contain the value potentially? For example, a
   * {@link org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups.LongRangeGroup}
   * would contain any value in its specified range, even if the value does
   * not actually exist.
   * 
   * @param o
   *          the object
   * @return {@code true} if the group contains it, {@code false} otherwise
   */
  public abstract boolean contains(final Object o);

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    final int size;
    textOut.append(" contains "); //$NON-NLS-1$
    textOut.append(size = this.m_data.size());
    textOut.append(" element"); //$NON-NLS-1$
    if (size != 1) {
      textOut.append('s');
    }
  }
}
