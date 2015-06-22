package org.optimizationBenchmarking.experimentation.evaluation.impl.all.aggregation2D;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.attributes.functions.aggregation2D.Aggregation2D;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.impl.all.function.FunctionData;
import org.optimizationBenchmarking.experimentation.evaluation.impl.all.function.FunctionJob;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

/** A job of the Aggregation2D module. */
final class _AllAggregation2DJob extends FunctionJob {

  /**
   * Create the Aggregation2D job
   *
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @param config
   *          the configuration
   */
  _AllAggregation2DJob(final IExperimentSet data,
      final Configuration config, final Logger logger) {
    super(data, Aggregation2D.create(data, config), config, logger);
  }

  /** {@inheritDoc} */
  @Override
  protected void process(final FunctionData data,
      final ISectionContainer sectionContainer, final Logger logger) {

    try (final ISection section = sectionContainer.section(null)) {
      try (final IComplexText title = section.title()) {
        this.makeTitle(title);
      }

      try (final ISectionBody body = section.body()) {
        if (data != null) {
          this.makePlots(data, body, section.getStyles(), logger, null);
        }
      }
    }
  }
}
