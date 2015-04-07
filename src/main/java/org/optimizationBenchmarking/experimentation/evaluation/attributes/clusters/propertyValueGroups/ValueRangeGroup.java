package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.DataElement;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A group of data elements belonging to range of values.
 * 
 * @param <VT>
 *          the value type
 * @param <DT>
 *          the data set type
 */
public abstract class ValueRangeGroup<VT extends Object, DT extends DataElement>
    extends PropertyValueGroup<DT> {

  /** the inclusive lower bound */
  private final VT m_lower;
  /** the inclusive or exclusive upper bound */
  private final VT m_upper;
  /** is the upper bound exclusive? */
  private final boolean m_isUpperExclusive;
  /** the actual values */
  private final ArraySetView<VT> m_values;

  /**
   * create the property value group
   * 
   * @param lowerBound
   *          the inclusive lower bound
   * @param upperBound
   *          the exclusive upper bound
   * @param isUpperExclusive
   *          is the upper bound exclusive?
   * @param values
   *          the values
   * @param data
   *          the data
   */
  ValueRangeGroup(final VT lowerBound, final VT upperBound,
      final boolean isUpperExclusive, final VT[] values, final DT[] data) {
    super(data);

    if (lowerBound == null) {
      throw new IllegalArgumentException(//
          "Lower bound of value range group must not be null."); //$NON-NLS-1$
    }
    if (upperBound == null) {
      throw new IllegalArgumentException(//
          "Upper bound of value range group must not be null."); //$NON-NLS-1$
    }
    if (lowerBound.getClass() != upperBound.getClass()) {
      throw new IllegalArgumentException(//
          "The class of the lower and upper bound of a value range group must be the same."); //$NON-NLS-1$
    }
    if (values == null) {
      throw new IllegalArgumentException(//
          "The value list of the value range group must not be null."); //$NON-NLS-1$
    }
    if (values.length <= 0) {
      throw new IllegalArgumentException(//
          "The value list of the value range group must contain at least one value."); //$NON-NLS-1$

    }

    this.m_isUpperExclusive = isUpperExclusive;
    this.m_lower = lowerBound;
    this.m_upper = upperBound;
    this.m_values = new ArraySetView<>(values);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public ValueRangeGroups<DT> getOwner() {
    return ((ValueRangeGroups) (this.m_owner));
  }

  /**
   * Obtain the inclusive lower bound of the value range
   * 
   * @return the inclusive lower bound of the value range
   * @see #getUpperBound()
   * @see #getValues()
   */
  public final VT getLowerBound() {
    return this.m_lower;
  }

  /**
   * Obtain the exclusive or inclusive upper bound of the value range
   * 
   * @return the exclusive or inclusive upper bound of the value range
   * @see #isUpperBoundExclusive()
   * @see #getLowerBound()
   * @see #getValues()
   */
  public final VT getUpperBound() {
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

  /**
   * Get the actual values of the property. For each value {@code v}, it
   * must hold that
   * <code>{@link #getLowerBound()}&le;v&lt;{@link #getUpperBound()}</code>
   * (if {@link #isUpperBoundExclusive()} is {@code true} or
   * <code>{@link #getLowerBound()}&le;v&le;{@link #getUpperBound()}</code>
   * otherwise.
   * 
   * @return the actual values of the property
   * @see #getLowerBound()
   * @see #getUpperBound()
   */
  public final ArraySetView<VT> getValues() {
    return this.m_values;
  }

  /** {@inheritDoc} */
  @Override
  protected int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_lower),//
            HashUtils.combineHashes(//
                HashUtils.hashCode(this.m_upper),//
                HashUtils.hashCode(this.m_isUpperExclusive))),//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_values),//
            super.calcHashCode()));
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    textOut.append('[');

    addLower: {
      if (this.m_lower instanceof Number) {
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
      }
      textOut.append(this.m_lower);
    }

    textOut.append(',');

    addUpper: {
      if (this.m_upper instanceof Number) {
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
      }
      textOut.append(this.m_upper);
    }

    textOut.append(this.m_isUpperExclusive ? ')' : ']');
    super.toText(textOut);
  }
}
