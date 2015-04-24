package org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A group of data elements belonging to a numerical range of values.
 */
public class ValueRangeGroup extends PropertyValueGroup<ValueRangeGroups> {

  /** the inclusive lower bound */
  private final Number m_lower;
  /** the inclusive or exclusive upper bound */
  private final Number m_upper;
  /** is the upper bound exclusive? */
  private final boolean m_isUpperExclusive;

  /**
   * create the property value group
   * 
   * @param owner
   *          the owning element set
   * @param selection
   *          the data selection
   * @param lowerBound
   *          the inclusive lower bound
   * @param upperBound
   *          the inclusive or exclusive upper bound
   * @param isUpperExclusive
   *          is the upper bound exclusive?
   */
  ValueRangeGroup(final ValueRangeGroups owner,
      final DataSelection selection, final Number lowerBound,
      final Number upperBound, final boolean isUpperExclusive) {
    super(owner, selection);

    if (lowerBound == null) {
      throw new IllegalArgumentException(//
          "Lower bound of value range group must not be null."); //$NON-NLS-1$
    }
    if (upperBound == null) {
      throw new IllegalArgumentException(//
          "Upper bound of value range group must not be null."); //$NON-NLS-1$
    }

    this.m_isUpperExclusive = isUpperExclusive;
    this.m_lower = lowerBound;
    this.m_upper = upperBound;
  }

  /**
   * Obtain the inclusive lower bound of the value range
   * 
   * @return the inclusive lower bound of the value range
   * @see #getUpperBound()
   */
  public final Number getLowerBound() {
    return this.m_lower;
  }

  /**
   * Obtain the exclusive or inclusive upper bound of the value range
   * 
   * @return the exclusive or inclusive upper bound of the value range
   * @see #isUpperBoundExclusive()
   * @see #getLowerBound()
   */
  public final Number getUpperBound() {
    return this.m_upper;
  }

  /**
   * Is the upper bound exclusive ({@code true} is returned) or inclusive (
   * {@code false} is returned). Upper bounds should normally be exclusive,
   * since lower bounds are inclusive. This is often the default in Java
   * and allows us to chain intervals nicely. However, there are special
   * cases: Let's say we have an integer interval and one parameter value
   * is {@link java.lang.Long#MAX_VALUE}. In this case, the interval cannot
   * have an exclusive upper bound, as we simply cannot store a
   * {@code long} larger than {@link java.lang.Long#MAX_VALUE}. Or, let's
   * say, a parameter has actually the value
   * {@link java.lang.Double#POSITIVE_INFINITY}.
   * 
   * @return {@code true} if the upper bound is exclusive, {@code false}
   *         otherwise
   * @see #getUpperBound()
   */
  public final boolean isUpperBoundExclusive() {
    return this.m_isUpperExclusive;
  }

  /** {@inheritDoc} */
  @Override
  public final void appendCriterion(final ITextOutput textOut) {
    textOut.append('[');

    addLower: {
      if (this.m_lower instanceof Long) {
        textOut.append(((Long) this.m_lower).longValue());
        break addLower;
      }
      if (this.m_lower instanceof Double) {
        textOut.append(((Double) this.m_lower).doubleValue());
        break addLower;
      }
      if (this.m_lower instanceof Integer) {
        textOut.append(((Integer) this.m_lower).intValue());
        break addLower;
      }
      if (this.m_lower instanceof Short) {
        textOut.append(((Short) this.m_lower).shortValue());
        break addLower;
      }
      if (this.m_lower instanceof Byte) {
        textOut.append(((Byte) this.m_lower).byteValue());
        break addLower;
      }
      if (this.m_lower instanceof Float) {
        textOut.append(((Float) this.m_lower).floatValue());
        break addLower;
      }
      textOut.append(this.m_lower);
    }

    textOut.append(',');

    addUpper: {
      if (this.m_upper instanceof Long) {
        textOut.append(((Long) this.m_upper).longValue());
        break addUpper;
      }
      if (this.m_upper instanceof Double) {
        textOut.append(((Double) this.m_upper).doubleValue());
        break addUpper;
      }
      if (this.m_upper instanceof Integer) {
        textOut.append(((Integer) this.m_upper).intValue());
        break addUpper;
      }
      if (this.m_upper instanceof Short) {
        textOut.append(((Short) this.m_upper).shortValue());
        break addUpper;
      }
      if (this.m_upper instanceof Byte) {
        textOut.append(((Byte) this.m_upper).byteValue());
        break addUpper;
      }
      if (this.m_upper instanceof Float) {
        textOut.append(((Float) this.m_upper).floatValue());
        break addUpper;
      }
      textOut.append(this.m_upper);
    }

    textOut.append(this.m_isUpperExclusive ? ')' : ']');
  }

}
