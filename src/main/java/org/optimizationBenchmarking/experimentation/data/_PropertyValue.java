package org.optimizationBenchmarking.experimentation.data;

import java.util.Map;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A parameter value.
 * 
 * @param <OT>
 *          the owner type
 */
class _PropertyValue<OT extends _Property<?, ?>> extends
    _NamedIDObject<OT> implements Map.Entry<OT, Object> {
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
  _PropertyValue(final String name, final String desc, final Object value) {
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
          return this.m_owner.unspecified();
        }
        case _PropertyValueGeneralized.ID: {
          return this.m_owner.m_general;
        }
      }
    }
    return super.readResolve();
  }

  /** {@inheritDoc} */
  @Override
  public final OT getKey() {
    return this.m_owner;
  }

  /** {@inheritDoc} */
  @Override
  public final Object getValue() {
    return this.m_value;
  }

  /** {@inheritDoc} */
  @Override
  public final Object setValue(final Object value) {
    throw new UnsupportedOperationException(//
        "Cannot change the value of a property."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return (this.m_owner.hashCode() ^ this.m_value.hashCode());
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return (this.m_owner.getName() + '=' + this.m_value);
  }

  /**
   * Is this parameter value generalized?
   * 
   * @return {@code true} if and only if
   *         <code>this.{@link #getValue() getValue()}=={@link org.optimizationBenchmarking.experimentation.data._PropertyValueGeneralized#INSTANCE}</code>
   *         , {@code false} otherwise
   */
  public final boolean isGeneralized() {
    return (this.m_value == _PropertyValueGeneralized.INSTANCE);
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    textOut.append(this.m_owner.getName());
    textOut.append('=');
    textOut.append(this.m_value);
  }
}
