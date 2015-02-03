package org.optimizationBenchmarking.experimentation.data;

import java.util.ArrayList;

/** A context for instance sets. */
final class _InstanceSetContext extends
    _HierarchicalCollection<Instance, InstanceContext, InstanceSet> {

  /** the feature manager */
  private volatile _FeaturesBuilder m_featureSetBuilder;

  /** the feature set */
  private volatile FeatureSet m_featureSet;

  /**
   * create
   * 
   * @param element
   *          the owning element
   */
  _InstanceSetContext(final ExperimentSetContext element) {
    super(element);
    this.m_featureSetBuilder = new _FeaturesBuilder(this);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  final Class<InstanceContext> _getChildType() {
    return InstanceContext.class;
  }

  /** {@inheritDoc} */
  @Override
  protected final ExperimentSetContext getOwner() {
    return ((ExperimentSetContext) (super.getOwner()));
  }

  /**
   * Define a feature with a given name and description
   * 
   * @param name
   *          the feature name
   * @param desc
   *          the feature's description
   */
  synchronized final void _declareFeature(final String name,
      final String desc) {
    this.m_featureSetBuilder._declareProperty(name, desc);
  }

  /**
   * Create the instance context
   * 
   * @return the instance context
   */
  final synchronized InstanceContext _createInstance() {
    this.fsmStateAssert(_FSM.STATE_OPEN);
    return new InstanceContext(this, this.m_featureSetBuilder);
  }

  /**
   * get the dimension set
   * 
   * @return the dimension set
   */
  final DimensionSet _getDimensionSet() {
    return this.getOwner().getDimensionSet();
  }

  /** {@inheritDoc} */
  @Override
  final InstanceSet _doCompile(final ArrayList<Instance> data) {
    return new InstanceSet(data.toArray(new Instance[data.size()]));
  }

  /**
   * get the feature set
   * 
   * @return the feature set
   */
  final synchronized FeatureSet _getFeatureSet() {
    return this.m_featureSet;
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void onClose() {
    this.m_featureSet = null;
    super.onClose();
    this.m_featureSet = this.m_featureSetBuilder._compile();
  }

}
