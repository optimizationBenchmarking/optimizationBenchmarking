package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A group of data elements belonging to a distinct, singular value.
 * 
 * @param <DT>
 *          the data set type
 */
public final class DistinctValueGroup<DT extends IDataElement> extends
    PropertyValueGroup<DT> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the singular value */
  private final Object m_value;

  /**
   * create the property value group
   * 
   * @param value
   *          the value
   * @param data
   *          the data
   */
  DistinctValueGroup(final Object value, final DT[] data) {
    super(data);

    if (value == null) {
      throw new IllegalArgumentException(//
          "The singular property value cannot be null."); //$NON-NLS-1$
    }
    this.m_value = value;

    this.m_hashCode = HashUtils.combineHashes(//
        super.m_hashCode,//
        HashUtils.hashCode(this.m_value));
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
  public final boolean contains(final Object o) {
    return EComparison.equals(o, this.m_value);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final DistinctValueGroups<DT> getOwner() {
    return ((DistinctValueGroups) (this.m_owner));
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    textOut.append(this.m_value);
    super.toText(textOut);
  }

  /** {@inheritDoc} */
  @Override
  public final void appendValues(final ITextOutput textOut) {
    textOut.append(this.m_value);
  }

  /** {@inheritDoc} */
  @Override
  public final String getValuesString() {
    return this.m_value.toString();
  }
}
