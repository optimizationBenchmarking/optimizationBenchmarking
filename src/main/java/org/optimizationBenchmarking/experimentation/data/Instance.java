package org.optimizationBenchmarking.experimentation.data;

/**
 * A problem instance.
 */
public final class Instance extends _NamedIDObject {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the feature setting of this instance */
  private final FeatureSetting m_features;

  /** the lower boundaries */
  private final Number[] m_lower;

  /** the upper boundaries */
  private final Number[] m_upper;

  /**
   * Create the instance
   * 
   * @param name
   *          the instance name
   * @param desc
   *          the description
   * @param features
   *          the feature setting of this instance
   * @param lower
   *          the lower bounds
   * @param upper
   *          the upper bounds
   */
  Instance(final String name, final String desc,
      final FeatureSetting features, final Number[] lower,
      final Number[] upper) {
    super(name, desc);

    final String n;

    n = this.getName();

    if ((features == null) || (features.size() <= 0)) {
      throw new IllegalArgumentException((((//
          "Feature set of a problem instance must not be null or empty, but feature set of instance '" + //$NON-NLS-1$
          n) + "' is ") + features) + '.'); //$NON-NLS-1$
    }
    if (features.isGeneralized()) {
      throw new IllegalArgumentException((((//
          "Feature set of a problem instance must not be generalized, but feature set of instance '" + //$NON-NLS-1$
          n) + "' is ") + features) + '.'); //$NON-NLS-1$
    }

    if ((upper == null) || (upper.length < 1)) {
      throw new IllegalArgumentException(
          ("There must be at least one upper bound for one dimension for instance '" + //$NON-NLS-1$
          n)
              + "' but there is none."); //$NON-NLS-1$
    }
    if ((lower == null) || (lower.length < 1)) {
      throw new IllegalArgumentException(
          ("There must be at least one lower bound for one dimension for instance '" + //$NON-NLS-1$
          n)
              + "' but there is none."); //$NON-NLS-1$
    }

    if (lower.length != upper.length) {
      throw new IllegalArgumentException(
          ((((("There are " + lower.length) + //$NON-NLS-1$
          " lower bounds but ") + upper.length) + //$NON-NLS-1$
          " upper bounds for instance '") + //$NON-NLS-1$
          n) + '.');
    }

    for (final Number b : upper) {
      InstanceContext._validateBoundary(b, n, true, null);
    }
    for (final Number b : lower) {
      InstanceContext._validateBoundary(b, n, false, null);
    }

    this.m_features = features;
    this.m_lower = lower;
    this.m_upper = upper;
  }

  /**
   * Get the features of this instance
   * 
   * @return the features of this instance
   */
  public final FeatureSetting getFeatureSetting() {
    return this.m_features;
  }

  /**
   * Get the upper boundary of a given dimension for this benchmark
   * instance
   * 
   * @param dim
   *          the dimension
   * @return the upper boundary for dimension {@code dim} for this
   *         benchmark instance
   */
  public final Number getUpperBound(final Dimension dim) {
    return this.m_upper[dim.m_id];
  }

  /**
   * Get the lower boundary of a given dimension for this benchmark
   * instance
   * 
   * @param dim
   *          the dimension
   * @return the lower boundary for dimension {@code dim} for this
   *         benchmark instance
   */
  public final Number getLowerBound(final Dimension dim) {
    return this.m_lower[dim.m_id];
  }

  /**
   * Validate a given data point
   * 
   * @param p
   *          the data point to be validated
   * @throws IllegalArgumentException
   *           if the point {@code p} contains some measurement values that
   *           cannot occur for runs under this benchmark instance
   */
  public final void validateDataPoint(final DataPoint p) {
    Number l, u;
    int i;

    i = p.size();

    if (i != this.m_lower.length) {
      throw new IllegalArgumentException(
          "Invalid dimension of data point " //$NON-NLS-1$
              + p + ": must have " //$NON-NLS-1$
              + this.m_lower.length + " dimensions, but has " + i); //$NON-NLS-1$
    }

    for (; (--i) >= 0;) {
      l = this.m_lower[i];
      u = this.m_upper[i];

      if ((((l instanceof Double) || (l instanceof Float)) ? //
      (l.doubleValue() > p.getDouble(i))//
          : (l.longValue() > p.getLong(i))) || //
          (((u instanceof Double) || (u instanceof Float)) ? //
          (u.doubleValue() < p.getDouble(i))//
              : (u.longValue() < p.getLong(i)))) {
        throw new IllegalArgumentException(((((((((//
            "Dimension " + i) + //$NON-NLS-1$
            " of data point ") + p) + //$NON-NLS-1$
            " is outside of the valid range [") + l) + //$NON-NLS-1$
            ',') + u) + ']') + '.');
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  final int _compareTo(final _IDObject o) {

    if (o instanceof Instance) {
      return this.m_features.compareTo(((Instance) o).m_features);
    }

    return super._compareTo(o);
  }

  /** {@inheritDoc} */
  @Override
  public final InstanceSet getOwner() {
    return ((InstanceSet) (this.m_owner));
  }
}
