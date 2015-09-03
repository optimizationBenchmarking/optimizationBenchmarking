package org.optimizationBenchmarking.experimentation.data.impl.partial;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractExperiment;

/**
 * An internal, modifiable implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IExperiment}
 * interface.
 */
final class _Experiment extends AbstractExperiment {

  /** the name */
  String m_name;
  /** the description */
  String m_description;

  /** the parameter setting */
  private _ParameterSetting m_setting;

  /**
   * Create the abstract experiment.
   *
   * @param owner
   *          the owner
   */
  _Experiment(final _Experiments owner) {
    super(owner);
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

  /** {@inheritDoc} */
  @Override
  public final _ParameterSetting getParameterSetting() {
    if (this.m_setting == null) {
      this.m_setting = new _ParameterSetting();
    }
    return this.m_setting;
  }
}
