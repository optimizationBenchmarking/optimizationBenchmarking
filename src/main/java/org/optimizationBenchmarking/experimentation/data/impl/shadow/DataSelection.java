package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.ArrayList;
import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;
import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSet;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterValue;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertyValue;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/**
 * An object which can help to select data.
 */
public class DataSelection extends
    _MappedSelection<IExperimentSet, IExperiment, IInstanceRuns> {

  /** the selected features */
  private final _FeatureSelection m_features;

  /** the selected instances */
  private final _InstanceSelection m_instances;

  /** the selected parameters */
  private final _ParameterSelection m_parameters;

  /** the compiled selection */
  private _CompiledSelection m_compiled;

  /**
   * Create the selection object
   * 
   * @param experimentSet
   *          the experiment set to select from
   */
  public DataSelection(final IExperimentSet experimentSet) {
    super(experimentSet);

    this.m_features = new _FeatureSelection(//
        experimentSet.getFeatures());

    this.m_instances = new _InstanceSelection(//
        experimentSet.getInstances());

    this.m_parameters = new _ParameterSelection(//
        experimentSet.getParameters());
  }

  /** {@inheritDoc} */
  @Override
  final _Selection<IExperiment, IInstanceRuns> _createSelection(
      final IExperiment element) {
    return new _InstanceRunsSelection(element);
  }

  /** {@inheritDoc} */
  @Override
  final IExperimentSet _shadow(final IExperimentSet original,
      final Collection<IExperiment> elements) {
    throw new UnsupportedOperationException();
  }

  /**
   * The internal method to add and instance and its features
   * 
   * @param instance
   *          the instance to add
   */
  private final void __addInstance(final IInstance instance) {
    if (this.m_instances._add(instance)) {
      this.m_features._addSubElements(instance.getFeatureSetting());
    }
  }

  /**
   * Add a list of complete experiments to the selection, i.e., do
   * {@link #addExperiment(IExperiment)} on all of them
   * 
   * @param experiments
   *          the experiments to add
   */
  public final void addExperiments(
      final Iterable<? extends IExperiment> experiments) {
    if (experiments == null) {
      throw new IllegalArgumentException(//
          "Iterable of experiments cannot be null."); //$NON-NLS-1$
    }
    for (final IExperiment experiment : experiments) {
      this.addExperiment(experiment);
    }
  }

  /**
   * Add a complete experiment to the selection
   * 
   * @param experiment
   *          the experiment to add
   */
  public synchronized final void addExperiment(final IExperiment experiment) {

    if (experiment == null) {
      throw new IllegalArgumentException(//
          "Cannot add a null experiment."); //$NON-NLS-1$
    }

    if (this._add(experiment)) {
      this.m_compiled = null;
      this.m_parameters._addSubElements(experiment.getParameterSetting());
      for (final IInstanceRuns iruns : experiment.getData()) {
        this.__addInstance(iruns.getInstance());
      }
    }
  }

  /**
   * Add a given instance run set
   * 
   * @param runs
   *          the instance run set
   */
  public synchronized final void addInstanceRuns(final IInstanceRuns runs) {

    if (runs == null) {
      throw new IllegalArgumentException(
          "Cannot add a null instance runs."); //$NON-NLS-1$
    }

    if (this._addSubElement(runs)) {
      this.m_compiled = null;
      this.__addInstance(runs.getInstance());
    }
  }

  /**
   * For each of the instances in {@code instances}, check if there are any
   * instance run sets which fit to it. If so, add them to the selection
   * (and add the instance as well). If no data exists for the instance,
   * ignore it.
   * 
   * @param instances
   *          the instances
   */
  public final void addInstances(
      final Iterable<? extends IInstance> instances) {

    if (instances == null) {
      throw new IllegalArgumentException(//
          "Iterable of instances cannot be null."); //$NON-NLS-1$
    }

    for (final IInstance instance : instances) {
      this.addInstance(instance);
    }
  }

  /**
   * If there are any instance run sets which fit to the given instance,
   * add them to the selection (and add the instance as well). If no data
   * exists for the instance, ignore it.
   * 
   * @param instance
   *          the instance
   */
  public synchronized final void addInstance(final IInstance instance) {
    boolean changed;

    if (instance == null) {
      throw new IllegalArgumentException("Cannot add a null instance."); //$NON-NLS-1$
    }

    changed = false;
    outer: for (final IExperiment experiment : this.m_set.getData()) {
      for (final IInstanceRuns runs : experiment.getData()) {
        if (EComparison.equals(instance, runs.getInstance())) {
          if (this._addSubElement(runs)) {
            changed = true;
          }
          continue outer;
        }
      }
    }

    if (changed) {
      this.m_compiled = null;
      this.__addInstance(instance);
    }
  }

  /**
   * Add a given property value. This entails checking all elements which
   * specify the value and to add them.
   * 
   * @param value
   *          the property value
   */
  public final void addPropertyValue(final IPropertyValue value) {
    if (value instanceof IFeatureValue) {
      this.addFeatureValue((IFeatureValue) value);
    } else {
      if (value instanceof IParameterValue) {
        this.addParameterValue((IParameterValue) value);
      } else {
        throw new IllegalArgumentException(//
            "Property value must either be a feature or a parameter value, but is" //$NON-NLS-1$
                + value);
      }
    }
  }

  /**
   * Add a given property value. This entails checking all elements which
   * specify the value and to add them.
   * 
   * @param property
   *          the property
   * @param value
   *          the property value
   */
  public final void addPropertyValue(final IProperty property,
      final Object value) {
    if (property instanceof IFeature) {
      this.addFeatureValue(((IFeature) property), value);
    } else {
      if (property instanceof IParameter) {
        this.addParameterValue(((IParameter) property), value);
      } else {
        throw new IllegalArgumentException(//
            "Property must eithe be a feature or a parameter, but is " //$NON-NLS-1$
                + property);
      }
    }
  }

  /**
   * Add a given feature value. This entails checking all instances which
   * specify the value and to perform {@link #addInstance(IInstance)} with
   * them.
   * 
   * @param value
   *          the feature value
   */
  public final void addFeatureValue(final IFeatureValue value) {
    if (value == null) {
      throw new IllegalArgumentException(//
          "Cannot add a null feature value."); //$NON-NLS-1$
    }

    if (value.isGeneralized()) {
      throw new IllegalArgumentException(//
          "Cannot add generalized feature value."); //$NON-NLS-1$
    }

    this.addFeatureValue(value.getOwner(), value.getValue());
  }

  /**
   * Add a given feature value. This entails checking all instances which
   * specify the value and to perform {@link #addInstance(IInstance)} with
   * them.
   * 
   * @param feature
   *          the feature
   * @param value
   *          the feature value
   */
  public final void addFeatureValue(final IFeature feature,
      final Object value) {

    if (value == null) {
      throw new IllegalArgumentException(//
          "Cannot add a null feature value."); //$NON-NLS-1$
    }
    if (feature == null) {
      throw new IllegalArgumentException(//
          "Owner of feature value cannot be null."); //$NON-NLS-1$
    }
    if (EComparison.equals(feature.getGeneralized().getValue(), value)) {
      throw new IllegalArgumentException(//
          "Cannot add generalized feature value."); //$NON-NLS-1$
    }

    for (final IInstance instance : this.m_set.getInstances().getData()) {
      if (EComparison.equals(instance.getFeatureSetting().get(feature),
          value)) {
        this.addInstance(instance);
      }
    }
  }

  /**
   * Add a given parameter value. This entails checking all experiment
   * which specify the value and to perform
   * {@link #addExperiment(IExperiment)} with them.
   * 
   * @param value
   *          the parameter value
   */
  public final void addParameterValue(final IParameterValue value) {
    if (value == null) {
      throw new IllegalArgumentException(//
          "Cannot add a null parameter value."); //$NON-NLS-1$
    }

    if (value.isGeneralized()) {
      throw new IllegalArgumentException(//
          "Cannot add generalized parameter value."); //$NON-NLS-1$
    }

    this.addParameterValue(value.getOwner(), value.getValue());
  }

  /**
   * Add a given parameter value. This entails checking all experiments
   * which specify the value and to perform
   * {@link #addExperiment(IExperiment)} with them.
   * 
   * @param parameter
   *          the parameter
   * @param value
   *          the parameter value
   */
  public final void addParameterValue(final IParameter parameter,
      final Object value) {

    if (value == null) {
      throw new IllegalArgumentException(//
          "Cannot add a null parameter value."); //$NON-NLS-1$
    }

    if (parameter == null) {
      throw new IllegalArgumentException(//
          "Owner of parameter value cannot be null."); //$NON-NLS-1$
    }

    if (EComparison.equals(parameter.getGeneralized().getValue(), value)) {
      throw new IllegalArgumentException(//
          "Cannot add generalized parameter value."); //$NON-NLS-1$
    }

    for (final IExperiment experiment : this.m_set.getData()) {
      if (EComparison.equals(
          experiment.getParameterSetting().get(parameter), value)) {
        this.addExperiment(experiment);
      }
    }
  }

  /**
   * get the compiled selection
   * 
   * @return the compiled selection
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  synchronized final _CompiledSelection _getSelection() {
    final ShadowFeatureSet features;
    final IFeatureSet ifeatures;
    final ShadowParameterSet parameters;
    final IParameterSet iparameters;
    final ShadowInstanceSet instances;
    final IInstanceSet iinstances;
    Collection<IExperiment> list;
    _Selection<IExperiment, IInstanceRuns> selection;
    boolean oneDifferent;
    int size;
    IExperiment comp;

    if (this.m_compiled == null) {

      // compile the other elements
      ifeatures = this.m_features._compile();
      if (ifeatures == null) {
        features = new ShadowFeatureSet(null, this.m_features.m_set,
            ((Collection) (ArraySetView.EMPTY_SET_VIEW)));
      } else {
        if (ifeatures == this.m_features.m_set) {
          features = null;
        } else {
          features = ((ShadowFeatureSet) ifeatures);
        }
      }

      iparameters = this.m_parameters._compile();
      if (iparameters == null) {
        parameters = new ShadowParameterSet(null, this.m_parameters.m_set,
            ((Collection) (ArraySetView.EMPTY_SET_VIEW)));
      } else {
        if (iparameters == this.m_parameters.m_set) {
          parameters = null;
        } else {
          parameters = ((ShadowParameterSet) iparameters);
        }
      }

      iinstances = this.m_instances._compile();
      if (iinstances == null) {
        instances = new ShadowInstanceSet(null, this.m_instances.m_set,
            ((Collection) (ArraySetView.EMPTY_SET_VIEW)));
      } else {
        if (iinstances == this.m_instances.m_set) {
          instances = null;
        } else {
          instances = ((ShadowInstanceSet) iinstances);
        }
      }

      // compile the list of experiments

      if ((this.m_selection == null) || //
          ((size = this.m_selection.size()) <= 0)) {
        list = ((Collection) (ArraySetView.EMPTY_SET_VIEW));
      } else {

        list = new ArrayList<>(size);
        oneDifferent = false;
        for (final Object selected : this.m_selection.values()) {
          if (selected == null) {
            continue;
          }
          if (selected instanceof IExperiment) {
            list.add((IExperiment) selected);
            continue;
          }
          selection = ((_Selection) selected);
          comp = selection._compile();
          if (comp != null) {
            list.add(comp);
            oneDifferent |= (comp != selection.m_set);
          }
        }

        size = list.size();
        if (size <= 0) {
          list = ((Collection) (ArraySetView.EMPTY_SET_VIEW));
        } else {
          if (!(oneDifferent || (size < this.m_set.getData().size()))) {
            list = null;
          }
        }
      }

      this.m_compiled = new _CompiledSelection(this.m_set, features,
          instances, parameters, list);

    }
    return this.m_compiled;
  }

  /** a compiled selection */
  static final class _CompiledSelection {

    /** the original experiment set */
    final IExperimentSet m_original;

    /**
     * the feature set, or {@code null} if all features can be used
     * identically
     */
    final ShadowFeatureSet m_features;

    /**
     * the instance set, or {@code null} if all instances can be used
     * identically
     */
    final ShadowInstanceSet m_instances;

    /**
     * the parameter set, or {@code null} if all parameters can be used
     * identically
     */
    final ShadowParameterSet m_parameters;

    /**
     * the experiments set, or {@code null} if all experiments can be used
     * identically
     */
    final Collection<? extends IExperiment> m_experiments;

    /**
     * the compiled selection
     * 
     * @param experimentSet
     *          the experiment set
     * @param features
     *          the feature set
     * @param instances
     *          the instance set
     * @param parameters
     *          the parameters
     * @param experiments
     *          the experiments
     */
    _CompiledSelection(final IExperimentSet experimentSet,
        final ShadowFeatureSet features,
        final ShadowInstanceSet instances,
        final ShadowParameterSet parameters,
        final Collection<? extends IExperiment> experiments) {
      this.m_original = experimentSet;
      this.m_features = features;
      this.m_instances = instances;
      this.m_parameters = parameters;
      this.m_experiments = experiments;
    }
  }
}
