package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import java.util.Map;
import java.util.Map.Entry;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertySet;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertySetting;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertyValue;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IPropertySet}
 * interface.
 *
 * @param <ST>
 *          the setting type
 */
abstract class _AbstractPropertySet<ST extends IPropertySetting> extends
    AbstractNamedElementSet implements IPropertySet {

  /** the owning experiment set */
  IExperimentSet m_owner;

  /**
   * Create the abstract property set. If {@code owner==null}, you must
   * later set it.
   *
   * @param owner
   *          the owner
   */
  _AbstractPropertySet(final IExperimentSet owner) {
    super();
    this.m_owner = owner;
  }

  /**
   * Create an empty setting
   *
   * @return the empty setting
   */
  abstract ST _createEmpty();

  /** {@inheritDoc} */
  @Override
  public final IExperimentSet getOwner() {
    return this.m_owner;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public ArrayListView<? extends IProperty> getData() {
    return ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
  }

  /** {@inheritDoc} */
  @Override
  public IProperty find(final String name) {
    return ((IProperty) (super.find(name)));
  }

  /** {@inheritDoc} */
  @Override
  public ST createSettingFromValues(
      final Iterable<? extends IPropertyValue> values) {
    if ((this.getData().isEmpty()) || (values == null)
        || (!(values.iterator().hasNext()))) {
      return this._createEmpty();
    }
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public ST createSettingFromMapping(final Map<String, Object> values) {
    if ((this.getData().isEmpty()) || (values == null)
        || (values.isEmpty())) {
      return this._createEmpty();
    }
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public ST createSettingFromMapping(final Entry<String, Object>[] values) {
    if ((this.getData().isEmpty()) || (values == null)
        || (values.length <= 0)) {
      return this._createEmpty();
    }
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public ST createSettingFromMapping(
      final Iterable<Entry<String, Object>> values) {
    if ((this.getData().isEmpty()) || (values == null)
        || (!(values.iterator().hasNext()))) {
      return this._createEmpty();
    }
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public ST createSettingFromValues(final IPropertyValue... values) {
    if ((this.getData().isEmpty()) || (values == null)
        || (values.length <= 0)) {
      return this._createEmpty();
    }
    throw new UnsupportedOperationException();
  }

}
