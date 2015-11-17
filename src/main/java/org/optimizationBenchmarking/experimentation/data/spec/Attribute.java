package org.optimizationBenchmarking.experimentation.data.spec;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

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
public abstract class Attribute<ST extends IDataElement, RT>
    extends HashObject {

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
   * @param logger
   *          the logger to use, or {@code null} if no logging information
   *          should be created
   * @return the value
   */
  protected abstract RT compute(final ST data, final Logger logger);

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    return ((o != null) && (o.getClass() == this.getClass()));
  }

  /**
   * create the exception to throw
   *
   * @param data
   *          the data element
   * @return the exception to throw
   */
  private final IllegalArgumentException __throw(final ST data) {
    return new IllegalArgumentException(
        "All elements of the experiment API which can have attributes must be instances of " //$NON-NLS-1$
            + TextUtils.className(DataElement.class) + //
            " but you supplied "//$NON-NLS-1$
            + ((data == null) ? "null" //$NON-NLS-1$
                : " an instance of "//$NON-NLS-1$
                    + TextUtils.className(data.getClass()))
            + " to attribute " + //$NON-NLS-1$
            TextUtils.className(this.getClass()) + '.');//
  }

  /**
   * Get the value of the attribute from a given {@code data} element.
   *
   * @param data
   *          the data element
   * @param logger
   *          the logger to use, or {@code null} if no logging information
   *          should be created
   * @return the result type
   */
  public final RT get(final ST data, final Logger logger) {
    if (data instanceof DataElement) {
      return ((DataElement) data).getAttribute(this, logger);
    }
    throw this.__throw(data);
  }

  /**
   * Obtain a job whose {@link java.util.concurrent.Callable#call()} method
   * will return the value of the attribute for the given {@code data}
   * element. This is intended for parallelization.
   *
   * @param data
   *          the data element
   * @param logger
   *          the logger to use, or {@code null} if no logging information
   *          should be created
   * @return the result type
   */
  public final AttributeGetter getter(final ST data, final Logger logger) {
    if (data instanceof DataElement) {
      return new AttributeGetter(((DataElement) data), logger);
    }
    throw this.__throw(data);
  }

  /** a job which can load the value of the current attribute */
  public final class AttributeGetter implements Callable<RT> {
    /** the data to load the attribute from */
    private final DataElement m_data;
    /** the logger to use for getting the data */
    private final Logger m_logger;

    /**
     * Create the getter job
     *
     * @param data
     *          the data to load the attribute value from
     * @param logger
     *          the logger to use
     */
    AttributeGetter(final DataElement data, final Logger logger) {
      super();
      this.m_data = data;
      this.m_logger = logger;
    }

    /** {@inheritDoc} */
    @Override
    public final RT call() {
      return this.m_data.getAttribute(Attribute.this, this.m_logger);
    }
  }
}
