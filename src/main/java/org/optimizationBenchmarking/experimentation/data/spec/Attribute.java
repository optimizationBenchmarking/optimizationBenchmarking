package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * <p>
 * This is the base class for attributes, values which can be used to
 * describe
 * {@link org.optimizationBenchmarking.experimentation.data.spec.DataElement}
 * s. Attributes are values which we assign and store in elements of the
 * experiment API. They usually are computed based on the values of the
 * elements or other attributes.
 * </p>
 * <p>
 * Depending on their {@link #m_type type}, attributes may be cached
 * internally. In this case, the attribute instance also serves as key to a
 * {@link java.util.HashMap hash map}-based cache.
 * </p>
 * 
 * @param <ST>
 *          the source object type which is attributed
 * @param <RT>
 *          the result type of the attribute
 */
public abstract class Attribute<ST extends IDataElement, RT> extends
    HashObject {

  /** the attribute type */
  final EAttributeType m_type;

  /**
   * create the attribute
   * 
   * @param type
   *          the attribute type
   */
  protected Attribute(final EAttributeType type) {
    super();
    if (type == null) {
      throw new IllegalArgumentException(
          "Attribute type must not be null."); //$NON-NLS-1$
    }
    this.m_type = type;
  }

  /**
   * Compute the value of this attribute.
   * 
   * @param data
   *          the data to compute the value from
   * @return the value
   */
  protected abstract RT compute(final ST data);

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    return ((o != null) && (o.getClass() == this.getClass()));
  }

  /**
   * Get the value of the attribute from a given data element
   * 
   * @param data
   *          the data element
   * @return the result type
   */
  public final RT get(final ST data) {
    if (data instanceof DataElement) {
      return ((DataElement) data).getAttribute(this);
    }
    throw new IllegalArgumentException(
        "All elements of the experiment API which can have attributes must be instances of " //$NON-NLS-1$
            + TextUtils.className(DataElement.class) + //
            " but you supplied "//$NON-NLS-1$ 
            + ((data == null) ? "null" : //$NON-NLS-1$
                " an instance of " //$NON-NLS-1$
                    + TextUtils.className(data.getClass())) + '.');//
  }

}
