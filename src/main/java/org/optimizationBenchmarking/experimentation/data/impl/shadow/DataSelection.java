package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.ArrayList;
import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSet;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

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
   * Add a complete experiment to the selection
   * 
   * @param experiment
   *          the experiment to add
   */
  public synchronized final void addExperiment(final IExperiment experiment) {
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
    if (this._addSubElement(runs)) {
      this.m_compiled = null;
      this.__addInstance(runs.getInstance());
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
