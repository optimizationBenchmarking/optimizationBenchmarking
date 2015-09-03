package org.optimizationBenchmarking.experimentation.data.impl.partial;

import java.util.LinkedHashMap;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/**
 * An internal, modifiable implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstance}
 * interface.
 */
final class _Instance extends AbstractInstance {

  /** the name */
  String m_name;
  /** the description */
  String m_description;

  /** the feature setting */
  private _FeatureSetting m_setting;

  /** the lower bounds */
  private LinkedHashMap<String, Number> m_lower;
  /** the upper bounds */
  private LinkedHashMap<String, Number> m_upper;

  /**
   * Create the abstract instance.
   *
   * @param owner
   *          the owner
   */
  _Instance(final _Instances owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return this.m_name;
  }

  /** {@inheritDoc} */
  @Override
  public final String getDescription() {
    return this.m_description;
  }

  /** {@inheritDoc} */
  @Override
  public final _FeatureSetting getFeatureSetting() {
    if (this.m_setting == null) {
      this.m_setting = new _FeatureSetting();
    }
    return this.m_setting;
  }

  /**
   * Set the lower bound for a given dimension
   *
   * @param dim
   *          the dimension
   * @param value
   *          the bound
   */
  final void _setLower(final String dim, final Number value) {
    Number old;

    if (this.m_lower == null) {
      old = null;
      this.m_lower = new LinkedHashMap<>();
    } else {
      old = this.m_lower.get(dim);
    }
    if (old != null) {
      if (EComparison.equals(old, value)) {
        return;
      }
      throw new IllegalStateException(//
          "Lower bound dimension '" //$NON-NLS-1$
              + dim + //
              "' has already been set to '" + old + //$NON-NLS-1$
              "' and cannot be set to '" + value + //$NON-NLS-1$
              "' in instance '" + this.m_name + //$NON-NLS-1$
              '\'' + '.');

    }
    this.m_lower.put(dim, value);
  }

  /**
   * Set the upper bound for a given dimension
   *
   * @param dim
   *          the dimension
   * @param value
   *          the bound
   */
  final void _setUpper(final String dim, final Number value) {
    Number old;

    if (this.m_upper == null) {
      old = null;
      this.m_upper = new LinkedHashMap<>();
    } else {
      old = this.m_upper.get(dim);
    }
    if (old != null) {
      if (EComparison.equals(old, value)) {
        return;
      }
      throw new IllegalStateException(//
          "Upper bound dimension '" //$NON-NLS-1$
              + dim + //
              "' has already been set to '" + old + //$NON-NLS-1$
              "' and cannot be set to '" + value + //$NON-NLS-1$
              "' in instance '" + this.m_name + //$NON-NLS-1$
              '\'' + '.');

    }
    this.m_upper.put(dim, value);
  }

  /** {@inheritDoc} */
  @Override
  public final Number getUpperBound(final IDimension dim) {
    final Number num;
    if (this.m_upper != null) {
      num = this.m_upper.get(dim.getName());
      if (num != null) {
        return num;
      }
    }
    return super.getUpperBound(dim);
  }

  /** {@inheritDoc} */
  @Override
  public final Number getLowerBound(final IDimension dim) {
    final Number num;
    if (this.m_lower != null) {
      num = this.m_lower.get(dim.getName());
      if (num != null) {
        return num;
      }
    }
    return super.getLowerBound(dim);
  }
}
