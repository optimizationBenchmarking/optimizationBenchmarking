package org.optimizationBenchmarking.experimentation.data.impl.ref;

import java.util.Map;

import org.optimizationBenchmarking.experimentation.data.spec.IPropertyValue;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A property value: Property values are the base class for both
 * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.FeatureValue
 * instance feature values} and
 * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.ParameterValue
 * experiment parameter values}.
 * 
 * @param <OT>
 *          the owner type
 */
public abstract class PropertyValue<OT extends Property<?>> extends
    _NamedIDObject implements IPropertyValue, Map.Entry<OT, Object> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the value */
  final Object m_value;

  /**
   * Create the parameter value
   * 
   * @param name
   *          the string representation of this value
   * @param desc
   *          the description
   * @param value
   *          the value
   */
  PropertyValue(final String name, final String desc, final Object value) {
    super(name, desc);
    if (value == null) {
      throw new IllegalArgumentException(//
          "Property value must not be null, but '" + name + //$NON-NLS-1$
              "' is evaluated to null."); //$NON-NLS-1$
    }
    this.m_value = value;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  protected final Object readResolve() {
    if (this.m_owner != null) {
      switch (this.m_id) {
        case _PropertyValueUnspecified.ID: {
          return ((OT) (this.m_owner)).getUnspecified();
        }
        case _PropertyValueGeneralized.ID: {
          return ((OT) (this.m_owner)).m_general;
        }
      }
    }
    return super.readResolve();
  }

  /** {@inheritDoc} */
  @Override
  public final OT getKey() {
    return ((OT) (this.m_owner));
  }

  /** {@inheritDoc} */
  @Override
  public final OT getOwner() {
    return ((OT) (this.m_owner));
  }

  /** {@inheritDoc} */
  @Override
  public final Object getValue() {
    return this.m_value;
  }

  /** {@inheritDoc} */
  @Override
  public final Object setValue(final Object value) {
    final OT owner;
    owner = this.getOwner();
    throw new UnsupportedOperationException(((((((((//
        "Cannot change the value of the " + //$NON-NLS-1$
        this.getClass().getSimpleName()) + //
        " with name '") + this.m_name) + //$NON-NLS-1$
        "' belonging to ") + //$NON-NLS-1$
        owner.getClass().getSimpleName()) + //
        " with name '") + //$NON-NLS-1$
        this.getOwner().getName()) + '\'') + '.');
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return (this.m_owner.hashCode() ^ this.m_value.hashCode());
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return (((OT) (this.m_owner)).getName() + '=' + this.m_value);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isGeneralized() {
    return PropertyValue.isGeneralized(this.m_value);
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    textOut.append(((OT) (this.m_owner)).getName());
    textOut.append('=');
    textOut.append(this.m_value);
  }

  /**
   * Check whether a property value is generalized
   * 
   * @param value
   *          the value
   * @return {@code true} if the value is generalized, {@code false} if it
   *         is not
   * @see #isGeneralized()
   */
  public static final boolean isGeneralized(final Object value) {
    return _PropertyValueGeneralized.INSTANCE.equals(value);
  }
}
