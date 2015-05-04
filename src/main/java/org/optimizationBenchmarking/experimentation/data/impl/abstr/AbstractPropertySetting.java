package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertySetting;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertyValue;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IPropertySetting}
 * interface.
 */
public class AbstractPropertySetting extends
AbstractMap<IProperty, Object> implements IPropertySetting {

  /**
   * Create the abstract property setting
   */
  protected AbstractPropertySetting() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public int size() {
    return this.entrySet().size();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isEmpty() {
    return (this.size() <= 0);
  }

  /** {@inheritDoc} */
  @Override
  public boolean containsKey(final Object key) {
    return (this.get(key) != null);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public boolean containsValue(final Object value) {
    final Collection vals;

    vals = this.values();
    return ((vals != null) && (vals.contains(value)));
  }

  /** {@inheritDoc} */
  @Override
  public final Object put(final IProperty key, final Object value) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public final Object remove(final Object key) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public final void putAll(
      final Map<? extends IProperty, ? extends Object> m) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public final void clear() {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public Set<java.util.Map.Entry<IProperty, Object>> entrySet() {
    return Collections.EMPTY_SET;
  }

  /** {@inheritDoc} */
  @Override
  public int compareTo(final IPropertySetting o) {
    int res;

    if (o == this) {
      return 0;
    }
    if (o == null) {
      return (-1);
    }

    for (final java.util.Map.Entry<IProperty, Object> entry : //
      this.entrySet()) {
      res = EComparison.compareObjects(entry.getValue(),
          o.get(entry.getKey()));
      if (res != 0) {
        return res;
      }
    }

    return Integer.compare(this.size(), o.size());
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public Iterator<? extends IPropertyValue> iterator() {
    return ((Iterator) (this.entrySet().iterator()));
  }

  /** {@inheritDoc} */
  @Override
  public boolean subsumes(final IPropertySetting setting) {
    IProperty key;
    Object value1, value2;

    if (setting == this) {
      return true;
    }
    if (setting == null) {
      return false;
    }

    for (final java.util.Map.Entry<IProperty, Object> entry : //
      this.entrySet()) {
      key = entry.getKey();

      value1 = entry.getValue();
      value2 = setting.get(key);

      if (EComparison.equals(value1, value2)) {
        continue;
      }

      if (EComparison.equals(value1, key.getGeneralized())) {
        continue;
      }

      return false;
    }
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isGeneralized() {
    for (final java.util.Map.Entry<IProperty, Object> entry : //
      this.entrySet()) {
      if (EComparison.equals(//
          entry.getKey().getGeneralized().getValue(), entry.getValue())) {
        return false;
      }
    }
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public boolean specifies(final IProperty property) {
    Object value;

    value = this.get(property);
    if (!(EComparison.equals(value, property.getGeneralized()))) {
      if (property instanceof IParameter) {
        return (!(EComparison.equals(value,
            ((IParameter) property).getUnspecified())));
      }
      return true;
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  public boolean contains(final IPropertyValue value) {
    return ((value != null) && (EComparison.equals(
        this.get(value.getOwner()), value.getValue())));
  }
}