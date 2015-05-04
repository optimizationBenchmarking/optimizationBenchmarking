package org.optimizationBenchmarking.experimentation.evaluation.system.impl.all.ecdf;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.ExperimentSetModule;
import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * The ECDF painter. This module paints the ECDF for different experiments
 * into one diagram.
 */
public final class AllECDF extends ExperimentSetModule {

  /** the ecdf parameter */
  public static final String PARAM_ECDF = "ecdf"; //$NON-NLS-1$

  /** create the instance information tool */
  AllECDF() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final _AllECDFJob createJob(final IExperimentSet data,
      final Configuration config, final Logger logger) {
    return new _AllECDFJob(data, config, logger);
  }

  /**
   * Get the globally shared instance of the instance information module
   *
   * @return the globally shared instance of the instance information
   *         module
   */
  public static final AllECDF getInstance() {
    return __InstanceInformationLoader.INSTANCE;
  }

  /** the instance information loader */
  private static final class __InstanceInformationLoader {
    /** the shared instance */
    static final AllECDF INSTANCE = new AllECDF();
  }
}
