package org.optimizationBenchmarking.experimentation.data.impl.partial;

import java.util.ArrayList;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractExperimentSet;
import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractNamedElement;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/** the internal experiment set */
final class _Experiments extends AbstractExperimentSet {

  /** the dimension set */
  private _Dimensions m_dimensions;

  /** the feature set */
  private _Features m_features;

  /** the instance set */
  private _Instances m_instances;

  /** the parameters set */
  private _Parameters m_parameters;

  /** the internal list view with the experiments */
  private ArrayListView<IExperiment> m_experiments;
  /** the list of experiments */
  private final ArrayList<_Experiment> m_experimentList;
  /** do we need a new experiment? */
  boolean m_needsNew;

  /** create */
  _Experiments() {
    super();
    this.m_experimentList = new ArrayList<>();
    this.m_needsNew = true;
  }

  /** flush all contexts */
  final void _flush() {
    if (this.m_dimensions != null) {
      this.m_dimensions.m_needsNew = true;
    }
    if (this.m_features != null) {
      this.m_features.m_needsNew = true;
    }
    if (this.m_instances != null) {
      this.m_instances.m_needsNew = true;
    }
    if (this.m_parameters != null) {
      this.m_parameters.m_needsNew = true;
    }
    this.m_needsNew = true;
  }

  /** {@inheritDoc} */
  @Override
  public final _Dimensions getDimensions() {
    if (this.m_dimensions == null) {
      this.m_dimensions = new _Dimensions(this);
    }
    return this.m_dimensions;
  }

  /** {@inheritDoc} */
  @Override
  public final _Features getFeatures() {
    if (this.m_features == null) {
      this.m_features = new _Features(this);
    }
    return this.m_features;
  }

  /** {@inheritDoc} */
  @Override
  public final _Instances getInstances() {
    if (this.m_instances == null) {
      this.m_instances = new _Instances(this);
    }
    return this.m_instances;
  }

  /** {@inheritDoc} */
  @Override
  public final _Parameters getParameters() {
    if (this.m_parameters == null) {
      this.m_parameters = new _Parameters(this);
    }
    return this.m_parameters;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final ArrayListView<IExperiment> getData() {
    if (this.m_experiments == null) {
      this.m_experiments = ((ArrayListView) (ArrayListView
          .collectionToView(this.m_experimentList)));
    }
    return this.m_experiments;
  }

  /**
   * get the experiment
   *
   * @param forceNew
   *          do we need a new one
   * @return the experiment
   */
  final _Experiment _getExperiment(final boolean forceNew) {
    final _Experiment experiment;
    final int size;

    size = this.m_experimentList.size();
    if (forceNew || this.m_needsNew || (size <= 0)) {
      experiment = new _Experiment(this);
      this.m_experimentList.add(experiment);
      this.m_needsNew = false;
      this.m_experiments = null;
      return experiment;
    }
    return this.m_experimentList.get(size - 1);
  }

  /**
   * Get a experiment of the given name
   *
   * @param name
   *          the name
   * @return the experiment
   */
  final _Experiment _getExperimentForName(final String name) {
    final String useName;
    _Experiment nexperiment;

    useName = AbstractNamedElement.formatName(name);
    for (final _Experiment experiment : this.m_experimentList) {
      if (EComparison.equals(experiment.m_name, useName)) {
        return experiment;
      }
    }

    nexperiment = this._getExperiment(false);
    if (nexperiment.m_name != null) {
      nexperiment = this._getExperiment(true);
    }
    nexperiment.m_name = useName;
    return nexperiment;
  }
}
