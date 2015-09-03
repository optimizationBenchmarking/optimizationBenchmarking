package org.optimizationBenchmarking.experimentation.data.impl.partial;

import java.util.LinkedHashMap;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterValue;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/** the internal parameter class */
final class _Parameter extends AbstractParameter {

  /** the parameter name */
  String m_name;

  /** the description */
  String m_description;

  /** the parameter values */
  private final LinkedHashMap<Object, _ParameterValue> m_values;

  /** the parameter values */
  private ArrayListView<_ParameterValue> m_parameterValues;

  /**
   * create the parameter
   *
   * @param owner
   *          the owner
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  _Parameter(final _Parameters owner) {
    super(owner);
    this.m_parameterValues = ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
    this.m_values = new LinkedHashMap<>();
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

  /**
   * create a value based on the provided object
   *
   * @param value
   *          the value object
   * @return the the created value
   */
  final _ParameterValue _getValue(final Object value) {
    _ParameterValue f;

    f = this.m_values.get(value);
    if (f != null) {
      return f;
    }

    f = new _ParameterValue(this, value);
    this.m_values.put(value, f);
    return f;
  }

  /** {@inheritDoc} */
  @Override
  public final ArrayListView<? extends IParameterValue> getData() {
    if (this.m_parameterValues == null) {
      this.m_parameterValues = ArrayListView.collectionToView(//
          this.m_values.values());
    }
    return this.m_parameterValues;
  }
}
