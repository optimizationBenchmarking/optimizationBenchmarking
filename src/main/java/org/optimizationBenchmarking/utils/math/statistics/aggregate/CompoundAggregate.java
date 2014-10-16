package org.optimizationBenchmarking.utils.math.statistics.aggregate;

/** a compound aggregate class */
public final class CompoundAggregate implements IAggregate {
  /** the first aggregate */
  private final IAggregate m_a;

  /** the second aggregate */
  private final IAggregate m_b;

  /**
   * create
   * 
   * @param a
   *          the first child aggregate
   * @param b
   *          the second child aggregate
   */
  private CompoundAggregate(final IAggregate a, final IAggregate b) {
    super();
    this.m_a = a;
    this.m_b = b;
  }

  /**
   * Create a compound aggregate composed of two other aggregates
   * 
   * @param a
   *          the first aggregate
   * @param b
   *          the second aggregate
   * @return the compound aggregate
   */
  public static final IAggregate combine(final IAggregate a,
      final IAggregate b) {
    if (a == null) {
      return b;
    }
    if (b == null) {
      return a;
    }
    return new CompoundAggregate(a, b);
  }

  /**
   * Create a compound aggregate composed of several other aggregates
   * 
   * @param agg
   *          the set of aggregates
   * @return the compound aggregate
   */
  public static final IAggregate combine(final IAggregate... agg) {
    return CompoundAggregate.__combine(agg, 0, agg.length - 1);
  }

  /**
   * Combine aggregates
   * 
   * @param agg
   *          the aggregate list
   * @param start
   *          the start index
   * @param end
   *          the end index
   * @return the combined aggregate
   */
  private static final IAggregate __combine(final IAggregate[] agg,
      final int start, final int end) {
    final int mid;
    final IAggregate a, b;

    if (start > end) {
      return null;
    }

    if (start >= end) {
      return agg[start];
    }
    if ((start + 1) >= end) {
      return CompoundAggregate.combine(agg[start], agg[end]);
    }

    mid = ((start + end) >>> 1);
    a = CompoundAggregate.__combine(agg, start, mid);
    b = CompoundAggregate.__combine(agg, (mid + 1), end);
    return CompoundAggregate.combine(a, b);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final byte v) {
    this.m_a.append(v);
    this.m_b.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final short v) {
    this.m_a.append(v);
    this.m_b.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final int v) {
    this.m_a.append(v);
    this.m_b.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final long v) {
    this.m_a.append(v);
    this.m_b.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final float v) {
    this.m_a.append(v);
    this.m_b.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final double v) {
    this.m_a.append(v);
    this.m_b.append(v);
  }

}
