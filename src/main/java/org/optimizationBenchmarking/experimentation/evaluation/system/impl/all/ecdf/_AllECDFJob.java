package org.optimizationBenchmarking.experimentation.evaluation.system.impl.all.ecdf;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.attributes.functions.FunctionAttribute;
import org.optimizationBenchmarking.experimentation.attributes.functions.ecdf.ECDFParser;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.all.FunctionJob;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

/** A job of the ECDF module. */
final class _AllECDFJob extends FunctionJob {

  /**
   * Create the ECDF job
   * 
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @param config
   *          the configuration
   */
  _AllECDFJob(final IExperimentSet data, final Configuration config,
      final Logger logger) {
    super(data, config, logger);

  }

  @Override
  protected final FunctionAttribute<? super IExperiment> configureFunction(
      final IExperimentSet data, final Configuration config) {
    return config.get(AllECDF.PARAM_ECDF,
        new ECDFParser(data.getDimensions()), null);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doMain(final IExperimentSet data,
      final ISectionContainer sectionContainer, final Logger logger) {

    try (final ISection section = sectionContainer.section(null)) {
      try (final IPlainText title = section.title()) {
        title.append("Estimated Cumulative Distribution Function"); //$NON-NLS-1$
      }

      try (final ISectionBody body = section.body()) {
        this.makePlots(data, body, section.getStyles(), logger, null);
      }
    }
  }
}
