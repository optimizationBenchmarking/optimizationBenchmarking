package org.optimizationBenchmarking.experimentation.evaluation.data;

import java.util.ArrayList;

import org.optimizationBenchmarking.utils.comparison.EComparison;

/** A context for creating experiments. */
final class _ExperimentSetContext extends
    _HierarchicalCollection<Experiment, ExperimentContext, ExperimentSet> {

  /** the parameter manager */
  private volatile _ParametersBuilder m_params;

  /** the parameter set */
  private volatile ParameterSet m_parameterSet;

  /**
   * create the experiment set context
   * 
   * @param builder
   *          the experiment builder
   */
  _ExperimentSetContext(final ExperimentSetContext builder) {
    super(builder);
    this.m_params = new _ParametersBuilder(this);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final ExperimentSetContext getOwner() {
    return ((ExperimentSetContext) (super.getOwner()));
  }

  /**
   * get the dimension set
   * 
   * @return the dimension set
   */
  final DimensionSet _getDimensionSet() {
    return this.getOwner().getDimensionSet();
  }

  /**
   * get the instance set
   * 
   * @return the instance set
   */
  final InstanceSet _getInstanceSet() {
    return this.getOwner().getInstanceSet();
  }

  /**
   * get the feature set
   * 
   * @return the feature set
   */
  final FeatureSet _getFeatureSet() {
    return this.getOwner().getFeatureSet();
  }

  /**
   * get the parameter set
   * 
   * @return the parameter set
   */
  synchronized final ParameterSet _getParameterSet() {
    this.fsmStateAssert(EComparison.GREATER_OR_EQUAL,
        _Context.STATE_CLOSED);
    return this.m_parameterSet;
  }

  /**
   * Create an experiment context
   * 
   * @return the experiment context
   */
  synchronized final ExperimentContext _createExperiment() {
    this.fsmStateAssert(_FSM.STATE_OPEN);
    return new ExperimentContext(this, this.m_params);
  }

  /** {@inheritDoc} */
  @Override
  final ExperimentSet _doCompile(final ArrayList<Experiment> data) {
    final ParameterSet ps;

    ps = this.m_parameterSet;
    this.m_parameterSet = null;

    return new ExperimentSet(this._getDimensionSet(),
        this._getInstanceSet(), this._getFeatureSet(), ps,
        data.toArray(new Experiment[data.size()]), false, true, true);
  }

  /**
   * Define a parameter with a given name and description
   * 
   * @param name
   *          the parameter name
   * @param desc
   *          the parameter's description
   */
  synchronized final void _declareParameter(final String name,
      final String desc) {
    this.fsmStateAssert(_FSM.STATE_OPEN);
    this.m_params._declareProperty(name, desc);
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void onClose() {
    final _ParametersBuilder ps;

    ps = this.m_params;
    this.m_params = null;
    this.m_parameterSet = null;
    super.onClose();
    this.m_parameterSet = ps._compile();
  }

  /** {@inheritDoc} */
  @Override
  final Class<ExperimentContext> _getChildType() {
    return ExperimentContext.class;
  }

}
