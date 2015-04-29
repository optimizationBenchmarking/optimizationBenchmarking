package org.optimizationBenchmarking.experimentation.data.impl.ref;

import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertySet;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A property: Properties are the base class for both
 * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Feature
 * instance features} and
 * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Parameter
 * experiment parameters}.
 * 
 * @param <DT>
 *          the data type.
 */
public abstract class Property<DT extends PropertyValue<?>> extends
    _NamedIDObjectSet<DT> implements IProperty {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the type of this parameter */
  private final EPrimitiveType m_type;

  /** the data */
  final DT[] m_values;

  /** the generalized value */
  final DT m_general;

  /**
   * create
   * 
   * @param name
   *          the property name
   * @param desc
   *          the description
   * @param primitiveType
   *          the primitive type of the parameter, or {@code null} if the
   *          parameter is a string parameter
   * @param values
   *          the values
   * @param generalized
   *          the generalized value
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  Property(final String name, final String desc,
      final EPrimitiveType primitiveType, final PropertyValue<?>[] values,
      final DT generalized) {
    super(name, desc, ((DT[]) values), false, true, true);

    this.m_type = primitiveType;
    this.m_values = ((DT[]) values);
    this.m_general = generalized;
    generalized.m_id = _PropertyValueGeneralized.ID;
    ((PropertyValue) generalized).m_owner = this;
  }

  /** {@inheritDoc} */
  @Override
  public IPropertySet getOwner() {
    return ((IPropertySet) (this.m_owner));
  }

  /**
   * get the unspecified value
   * 
   * @return the unspecified property value
   */
  DT getUnspecified() {
    throw new IllegalArgumentException("Property '" + //$NON-NLS-1$
        this.getName() + "' does not support unspecified values."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  final int _compareTo(final _IDObject o) {
    return 0;
  }

  /** {@inheritDoc} */
  @Override
  public final DT getGeneralized() {
    return this.m_general;
  }

  /** {@inheritDoc} */
  @Override
  public final EPrimitiveType getPrimitiveType() {
    return this.m_type;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public final DT findValue(final Object value) {
    final DT[] data;
    final Comparable cv;
    int low, high, mid, cmp;
    DT midVal;
    PropertyValue pt;
    String n1, n2, n;
    Object parsed, val;

    if (value == null) {
      return null;
    }

    if ((value instanceof _PropertyValueGeneralized)
        || (_PropertyValueGeneralized.NAME.equals(value))
        || (value == this.m_general)) {
      return this.m_general;
    }
    if ((value instanceof _PropertyValueUnspecified)//
        || (_PropertyValueUnspecified.NAME.equals(value))) {
      return this.getUnspecified();
    }

    data = this.m_values;

    if (value instanceof PropertyValue) {
      mid = (pt = ((PropertyValue) value)).m_id;
      switch (mid) {
        case _PropertyValueUnspecified.ID: {
          return this.getUnspecified();
        }
        case _PropertyValueGeneralized.ID: {
          return this.m_general;
        }
        default: {
          if ((mid >= 0) && (mid < data.length)) {
            midVal = data[mid];
            if (midVal == value) {
              return midVal;
            }
            if (midVal.m_value.equals(pt.m_value)) {
              return midVal;
            }
          }
        }
      }
    }

    low = 0;
    high = (data.length - 1);

    try {
      cv = ((Comparable) value);
      while (low <= high) {
        mid = ((low + high) >>> 1);
        midVal = data[mid];

        cmp = cv.compareTo(midVal.m_value);

        if (cmp > 0) {
          low = (mid + 1);
        } else {
          if (cmp < 0) {
            high = (mid - 1);
          } else {
            return midVal;
          }
        }
      }
    } catch (final ClassCastException cse) {// ignore
    }

    if (this.m_type != null) {
      try {
        parsed = this.m_type.getStrictParser().parseObject(value);
      } catch (final Throwable t1) {
        try {
          parsed = this.m_type.getLooseParser().parseObject(value);
        } catch (final Throwable t2) {
          parsed = null;
        }
      }
    } else {
      parsed = null;
    }
    n1 = value.toString();
    if (parsed != null) {
      n2 = parsed.toString();
      if (n2.equals(n1)) {
        n2 = null;
      }
    } else {
      n2 = null;
    }

    for (final DT pv : data) {
      if ((pv == value) || //
          ((val = pv.m_value).equals(parsed)) || //
          (val.equals(n1)) || //
          (val.equals(n2)) || //
          (n = pv.getName()).equals(n1) || //
          (n.equals(n2))) {
        return pv;
      }
    }

    return null;
  }

  /**
   * Get the value of this property for the given element
   * 
   * @param element
   *          the element to get the property value of
   * @return the property value
   * @throws IllegalArgumentException
   *           if {@code element} is not the kind of type supported by the
   *           property
   */
  public abstract Object get(final DataElement element);

  /**
   * Append the "name" of this object to the given
   * {@link org.optimizationBenchmarking.utils.document.spec.IMath
   * mathematics context}.
   * 
   * @param math
   *          the mathematics output device
   */
  @Override
  public final void appendName(final IMath math) {
    super.appendName(math);
  }

  /**
   * Append the "name" of this object to the given text output device. This
   * device may actually be an instance of
   * {@link org.optimizationBenchmarking.utils.document.spec.IComplexText}
   * or something similar. In this case, the implementation of this
   * function may make use of all the capabilities of this object, foremost
   * including the ability to
   * {@link org.optimizationBenchmarking.utils.document.spec.IComplexText#inlineMath()
   * display mathematical text}.
   * 
   * @param textOut
   *          the text output device
   */
  @Override
  public final void appendName(final ITextOutput textOut) {
    super.appendName(textOut);
  }

  /**
   * Obtain a suggestion for the path component of figures drawn based on
   * this object.
   * 
   * @return a suggestion for the path component of figures drawn based on
   *         this component.
   */
  @Override
  public final String getPathComponentSuggestion() {
    return super.getPathComponentSuggestion();
  }
}
