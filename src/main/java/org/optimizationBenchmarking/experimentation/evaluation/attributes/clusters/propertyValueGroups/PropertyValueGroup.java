package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A group of values. This class is most likely used to group property
 * values.
 * 
 * @param <DT>
 *          the data set type
 */
public abstract class PropertyValueGroup<DT extends IDataElement> extends
    _GroupBase {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

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
    this.m_hashCode = HashUtils.hashCode(this.m_data);
  }

  /**
   * Get the owning group set
   * 
   * @return the owning group set
   */
  @Override
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
   * {@link org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups.ValueRangeGroup}
   * would contain any value in its specified range, even if the value does
   * not actually exist.
   * 
   * @param o
   *          the object
   * @return {@code true} if the group contains it, {@code false} otherwise
   */
  public abstract boolean contains(final Object o);

  /**
   * Append the value specifier to the given text output
   * 
   * @param textOut
   *          the text output to append to
   */
  public abstract void appendValues(final ITextOutput textOut);

  /**
   * Get the values string
   * 
   * @return the values string
   */
  public String getValuesString() {
    final MemoryTextOutput mto;

    mto = new MemoryTextOutput();
    this.appendValues(mto);
    return mto.toString();
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    final int size;
    this.appendValues(textOut);
    textOut.append(':');
    textOut.append(' ');
    textOut.append(size = this.m_data.size());
    textOut.append(" item"); //$NON-NLS-1$
    if (size != 1) {
      textOut.append('s');
    }
  }
}
