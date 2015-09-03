package org.optimizationBenchmarking.experimentation.data.impl.partial;

import java.util.ArrayList;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractNamedElement;
import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractParameterSet;
import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/** the parameter set */
final class _Parameters extends AbstractParameterSet {

  /** the internal list view with the parameters */
  private ArrayListView<IParameter> m_parameters;
  /** the list of parameters */
  private final ArrayList<_Parameter> m_parameterList;
  /** do we need a new parameter? */
  boolean m_needsNew;

  /**
   * create
   *
   * @param owner
   *          the owner
   */
  _Parameters(final _Experiments owner) {
    super(owner);
    this.m_parameterList = new ArrayList<>();
    this.m_needsNew = true;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final ArrayListView<IParameter> getData() {
    if (this.m_parameters == null) {
      this.m_parameters = ((ArrayListView) (ArrayListView
          .collectionToView(this.m_parameterList)));
    }
    return this.m_parameters;
  }

  /**
   * get the parameter
   *
   * @param forceNew
   *          do we need a new one
   * @return the parameter
   */
  final _Parameter _getParameter(final boolean forceNew) {
    final _Parameter parameter;
    final int size;

    size = this.m_parameterList.size();
    if (forceNew || this.m_needsNew || (size <= 0)) {
      parameter = new _Parameter(this);
      this.m_parameterList.add(parameter);
      this.m_needsNew = false;
      this.m_parameters = null;
      return parameter;
    }
    return this.m_parameterList.get(size - 1);
  }

  /**
   * Get a parameter of the given name
   *
   * @param name
   *          the name
   * @return the parameter
   */
  final _Parameter _getParameterForName(final String name) {
    final String useName;
    _Parameter nparameter;

    useName = AbstractNamedElement.formatName(name);
    for (final _Parameter parameter : this.m_parameterList) {
      if (EComparison.equals(parameter.m_name, useName)) {
        return parameter;
      }
    }

    nparameter = this._getParameter(false);
    if (nparameter.m_name != null) {
      nparameter = this._getParameter(true);
    }
    nparameter.m_name = useName;
    return nparameter;
  }
}
