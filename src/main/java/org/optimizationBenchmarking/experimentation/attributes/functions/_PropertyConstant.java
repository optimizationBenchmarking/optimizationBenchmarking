package org.optimizationBenchmarking.experimentation.attributes.functions;

import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.math.text.NamedConstant;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A constant which can be updated based on a property, i.e., either an
 * experiment parameter or instance feature.
 */
abstract class _PropertyConstant extends NamedConstant {

  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /** the property */
  final IProperty m_property;

  /** the current property type */
  private int m_state;
  /** the current long value */
  private long m_longVal;
  /** the current double value */
  private double m_doubleVal;

  /**
   * create the property constant
   * 
   * @param property
   *          the property
   */
  _PropertyConstant(final IProperty property) {
    super();

    if (property == null) {
      throw new IllegalArgumentException(//
          "Property cannot be null."); //$NON-NLS-1$
    }
    this.m_property = property;
  }

  /**
   * Update this property constant based on a data element
   * 
   * @param element
   *          the data element
   * @return the value of the property
   */
  abstract Object _getValue(final IDataElement element);

  /**
   * Update this property constant based on a data element
   * 
   * @param element
   *          the data element
   */
  final void _update(final IDataElement element) {
    final Object got;
    final Number number;

    this.m_state = BasicNumber.STATE_EMPTY;

    if (element == null) {
      throw new IllegalArgumentException("Data element cannot be null."); //$NON-NLS-1$
    }

    got = this._getValue(element);
    if (got instanceof Number) {
      number = ((Number) got);

      this.m_longVal = number.longValue();
      this.m_doubleVal = number.doubleValue();

      if (number instanceof BasicNumber) {
        this.m_state = ((BasicNumber) (number)).getState();
      } else {
        if ((NumericalTypes.getTypes(number) & NumericalTypes.IS_LONG) != 0) {
          this.m_state = BasicNumber.STATE_INTEGER;
        } else {
          if (this.m_doubleVal <= Double.NEGATIVE_INFINITY) {
            this.m_state = BasicNumber.STATE_NEGATIVE_INFINITY;
          } else {
            if (this.m_doubleVal >= Double.POSITIVE_INFINITY) {
              this.m_state = BasicNumber.STATE_POSITIVE_INFINITY;
            } else {
              if (this.m_doubleVal != this.m_doubleVal) {
                this.m_state = BasicNumber.STATE_NAN;
              } else {
                this.m_state = BasicNumber.STATE_DOUBLE;

              }
            }
          }
        }
      }

    } else {
      throw new IllegalArgumentException(//
          "Cannot update numerical constant to value "//$NON-NLS-1$ 
              + got);
    }
  }

  /** clear this property constant's numerical value */
  final void _clear() {
    this.m_state = STATE_EMPTY;
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(ITextOutput out, IParameterRenderer renderer) {
    this.m_property.mathRender(out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(IMath out, IParameterRenderer renderer) {
    this.m_property.mathRender(out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final int getState() {
    return this.m_state;
  }

  /** {@inheritDoc} */
  @Override
  public final double doubleValue() {
    if (this.m_state == STATE_EMPTY) {
      throw new IllegalArgumentException(//
          "No value has been set for property " //$NON-NLS-1$
              + this.m_property);
    }
    return this.m_doubleVal;
  }

  /** {@inheritDoc} */
  @Override
  public final long longValue() {
    if (this.m_state == STATE_EMPTY) {
      throw new IllegalArgumentException(//
          "No value has been set for property " //$NON-NLS-1$
              + this.m_property);
    }
    return this.m_longVal;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return (this.m_property.hashCode() ^ 3453479);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o == this) || ((o instanceof _PropertyConstant) && //
    (this.m_property.equals(((_PropertyConstant) o).m_property))));
  }
}
