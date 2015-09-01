package org.optimizationBenchmarking.experimentation.data.impl.partial;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractExperimentSet;

/** the internal experiment set */
final class _Experiments extends AbstractExperimentSet {

  /** the dimension set */
  private _Dimensions m_dimensions;

  /** the feature set */
  private _Features m_features;

  /** the instance set */
  private _Instances m_instances;

  /** create */
  _Experiments() {
    super();
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
}
