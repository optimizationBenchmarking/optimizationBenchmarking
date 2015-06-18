package org.optimizationBenchmarking.experimentation.attributes.functions;

import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.text.NamedConstant;

/**
 * A constant based on some data.
 */
abstract class _DataBasedConstant extends NamedConstant {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the current property type */
  private int m_state;
  /** the current long value */
  private long m_longVal;
  /** the current double value */
  private double m_doubleVal;

  /** create the data-based constant */
  _DataBasedConstant() {
    super();
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
    this.m_state = BasicNumber.STATE_EMPTY;
  }

  /** {@inheritDoc} */
  @Override
  public final int getState() {
    return this.m_state;
  }

  /** {@inheritDoc} */
  @Override
  public final double doubleValue() {
    if (this.m_state == BasicNumber.STATE_EMPTY) {
      throw new IllegalArgumentException(//
          "No value has been set for " //$NON-NLS-1$
              + this.toString());
    }
    return this.m_doubleVal;
  }

  /** {@inheritDoc} */
  @Override
  public final long longValue() {
    if (this.m_state == BasicNumber.STATE_EMPTY) {
      throw new IllegalArgumentException(//
          "No value has been set for " //$NON-NLS-1$
              + this.toString());
    }
    return this.m_longVal;
  }
}
