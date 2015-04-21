package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationJob;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationJobBuilder;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJobBuilder;

/**
 * The internal base class for evaluation job builders
 * 
 * @param <DT>
 *          the data type
 * @param <MT>
 *          the module type
 * @param <JT>
 *          the job type
 * @param <JBT>
 *          the job builder type
 */
class _EvaluationJobBuilder<DT extends IElementSet, MT extends _EvaluationModule<DT>, JT extends IEvaluationJob, JBT extends _EvaluationJobBuilder<DT, MT, JT, JBT>>
    extends ToolJobBuilder<JT, JBT> implements IEvaluationJobBuilder {

  /** the module */
  private final MT m_module;

  /** the data */
  private DT m_data;

  /** the configuration */
  private Configuration m_config;

  /**
   * create the job builder
   * 
   * @param module
   *          the module
   */
  _EvaluationJobBuilder(final MT module) {
    super();
    if (module == null) {
      throw new IllegalArgumentException(//
          "Owning module of an "//$NON-NLS-1$
              + TextUtils.className(this.getClass()) + " cannot be null."); //$NON-NLS-1$
    }
    this.m_module = module;
  }

  /**
   * Set the data
   * 
   * @param data
   *          the data
   * @return this builder
   */
  @SuppressWarnings("unchecked")
  public final synchronized JBT setData(final DT data) {
    _EvaluationJob._checkData(this, data);
    this.m_data = data;
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final synchronized JBT configure(final Configuration config) {
    if (config == null) {
      throw new IllegalArgumentException(//
          "Cannot set configuration to null."); //$NON-NLS-1$
    }
    this.m_config = config;
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  protected final void validate() {
    super.validate();
    if (this.m_config == null) {
      throw new IllegalArgumentException(//
          "Configuration must be set for job."); //$NON-NLS-1$
    }
    if (this.m_data == null) {
      throw new IllegalArgumentException(//
          "Data must be set for job."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final JT create() throws Exception {
    this.validate();
    return ((JT) (this.m_module.createJob(this.m_data, this.m_config,
        this.getLogger())));
  }

}
