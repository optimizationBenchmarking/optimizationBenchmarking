package org.optimizationBenchmarking.experimentation.attributes.functions;

import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSetting;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/**
 * A constant representing an experiment parameter value.
 */
final class _ParameterConstant extends _PropertyConstant {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * create the parameter constant
   *
   * @param parameter
   *          the parameter
   */
  _ParameterConstant(final IParameter parameter) {
    super(parameter);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  final Object _getValue(final IDataElement element) {
    if (element instanceof IParameterSetting) {
      return this.__getValue((IParameterSetting) element);
    }
    if (element instanceof IInstanceRuns) {
      return this.__getValue((IInstanceRuns) element);
    }
    if (element instanceof IRun) {
      return this.__getValue((IRun) element);
    }
    if (element instanceof IExperiment) {
      return this.__getValue((IExperiment) element);
    }
    if (element instanceof IExperimentSet) {
      return this.__getValue((IExperimentSet) element);
    }
    if (element instanceof IElementSet) {
      return this.__getValue((IElementSet) element);
    }
    if (element instanceof Iterable) {
      return this.__getValue((Iterable) element);
    }

    throw new IllegalArgumentException(//
        "Cannot get a parameter value from a " //$NON-NLS-1$
            + element);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return ("Parameter " + this.m_property.getName()); //$NON-NLS-1$
  }

  /**
   * Get the value of a parameter from a parameter setting
   *
   * @param setting
   *          the setting
   * @return the parameter value
   */
  private final Object __getValue(final IParameterSetting setting) {
    return setting.get(this.m_property);
  }

  /**
   * Get the value of a parameter from an instance run set
   *
   * @param instanceRuns
   *          the instance runs
   * @return the parameter value
   */
  private final Object __getValue(final IInstanceRuns instanceRuns) {
    return this.__getValue(instanceRuns.getOwner());
  }

  /**
   * Get the value of a parameter from a run
   *
   * @param run
   *          the run
   * @return the parameter value
   */
  private final Object __getValue(final IRun run) {
    return this.__getValue(run.getOwner());
  }

  /**
   * Get the value from a collection of things
   *
   * @param list
   *          the list
   * @return the result
   */
  private final Object __getValue(final Iterable<? extends Object> list) {
    Object value, newVal;

    value = null;
    looper: for (final Object element : list) {
      if (element instanceof IDataElement) {
        newVal = this._getValue((IDataElement) element);
        if (newVal == null) {
          value = null;
          break looper;
        }
        if (value == null) {
          value = newVal;
        } else {
          if (!(EComparison.equals(value, newVal))) {
            throw new IllegalArgumentException(//
                "Parameter value must be the same over the whole list, otherwise computation is inconsistent, but we discovered the non-equal values "//$NON-NLS-1$
                    + value + " and " + newVal);//$NON-NLS-1$
          }
        }
      } else {
        throw new IllegalArgumentException(//
            "Cannot deal with collections containing "//$NON-NLS-1$
                + element);
      }
    }

    if (value == null) {
      throw new IllegalArgumentException("Parameter value cannot be null."); //$NON-NLS-1$
    }

    return value;
  }

  /**
   * Get the value of a parameter from an experiment
   *
   * @param experiment
   *          the experiment
   * @return the parameter value
   */
  private final Object __getValue(final IExperiment experiment) {
    return this.__getValue(experiment.getParameterSetting());
  }

  /**
   * Get the value of a parameter from an experiment set
   *
   * @param experimentSet
   *          the experimentSet
   * @return the parameter value
   */
  private final Object __getValue(final IExperimentSet experimentSet) {
    return this.__getValue(experimentSet.getData());
  }

  /**
   * Get the value of a parameter from an element set
   *
   * @param elementSet
   *          the elementSet
   * @return the parameter value
   */
  private final Object __getValue(final IElementSet elementSet) {
    return this.__getValue(elementSet.getData());
  }
}
