package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.DataElement;
import org.optimizationBenchmarking.experimentation.data.ParameterValue;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A group of data elements belonging to a distinct, singular value.
 * 
 * @param <DT>
 *          the data set type
 */
public final class DistinctValueGroup<DT extends DataElement> extends
    PropertyValueGroup<DT> {

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
  }

  /**
   * Obtain the property value common to all data elements in this group
   * 
   * @return the property value common to all data elements in this group
   */
  public final Object getValue() {
    return this.m_value;
  }

  /**
   * Does this group represent unspecified parameter values?
   * 
   * @return {@code true} if the group stands for all elements which do not
   *         have the given parameter specified
   */
  public final boolean isUnspecified() {
    return ParameterValue.isUnspecified(this.m_value);
  }

  /** {@inheritDoc} */
  @Override
  protected int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.hashCode(this.m_value),//
        super.calcHashCode());
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
}
