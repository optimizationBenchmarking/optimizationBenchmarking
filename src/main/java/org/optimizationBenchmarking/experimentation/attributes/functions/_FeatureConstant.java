package org.optimizationBenchmarking.experimentation.attributes.functions;

import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSetting;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/**
 * A constant representing an instance feature value.
 */
final class _FeatureConstant extends _PropertyConstant {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * create the feature constant
   * 
   * @param feature
   *          the feature
   */
  _FeatureConstant(final IProperty feature) {
    super(feature);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  final Object _getValue(IDataElement element) {
    if (element instanceof IInstance) {
      return __getValue((IInstance) element);
    }
    if (element instanceof IFeatureSetting) {
      return __getValue((IFeatureSetting) element);
    }
    if (element instanceof IInstanceRuns) {
      return __getValue((IInstanceRuns) element);
    }
    if (element instanceof IRun) {
      return __getValue((IRun) element);
    }
    if (element instanceof IExperiment) {
      return __getValue((IExperiment) element);
    }
    if (element instanceof IExperimentSet) {
      return __getValue((IExperimentSet) element);
    }
    if (element instanceof IInstanceSet) {
      return __getValue((IInstanceSet) element);
    }
    if (element instanceof IElementSet) {
      return __getValue((IElementSet) element);
    }
    if (element instanceof Iterable) {
      return __getValue((Iterable) element);
    }

    throw new IllegalArgumentException(//
        "Cannot get a feature value from a " //$NON-NLS-1$
            + element);
  }

  /**
   * Get the value of a feature from a feature setting
   * 
   * @param setting
   *          the setting
   * @return the feature value
   */
  private final Object __getValue(IFeatureSetting setting) {
    return setting.get(this.m_property);
  }

  /**
   * Get the value of a feature from an instance
   * 
   * @param instance
   *          the instance
   * @return the feature value
   */
  private final Object __getValue(IInstance instance) {
    return this.__getValue(instance.getFeatureSetting());
  }

  /**
   * Get the value of a feature from an instance run set
   * 
   * @param instanceRuns
   *          the instance runs
   * @return the feature value
   */
  private final Object __getValue(IInstanceRuns instanceRuns) {
    return this.__getValue(instanceRuns.getInstance());
  }

  /**
   * Get the value of a feature from a run
   * 
   * @param run
   *          the run
   * @return the feature value
   */
  private final Object __getValue(IRun run) {
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
    looper: for (Object element : list) {
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
                "Feature value must be the same over the whole experiment, otherwise computation is inconsistent, but we discovered the non-equal values "//$NON-NLS-1$ 
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
      throw new IllegalArgumentException("Feature value cannot be null."); //$NON-NLS-1$
    }

    return value;
  }

  /**
   * Get the value of a feature from an experiment
   * 
   * @param experiment
   *          the experiment
   * @return the feature value
   */
  private final Object __getValue(IExperiment experiment) {
    return this.__getValue(experiment.getData());
  }

  /**
   * Get the value of a feature from an experiment set
   * 
   * @param experimentSet
   *          the experimentSet
   * @return the feature value
   */
  private final Object __getValue(IExperimentSet experimentSet) {
    return this.__getValue(experimentSet.getData());
  }

  /**
   * Get the value of a feature from an element set
   * 
   * @param elementSet
   *          the elementSet
   * @return the feature value
   */
  private final Object __getValue(IElementSet elementSet) {
    return this.__getValue(elementSet.getData());
  }

  /**
   * Get the value of a feature from an instance set
   * 
   * @param instanceSet
   *          the instanceSet
   * @return the feature value
   */
  private final Object __getValue(IInstanceSet instanceSet) {
    return this.__getValue(instanceSet.getData());
  }

}
