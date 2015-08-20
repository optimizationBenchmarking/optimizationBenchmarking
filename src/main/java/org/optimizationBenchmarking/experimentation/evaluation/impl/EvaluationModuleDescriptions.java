package org.optimizationBenchmarking.experimentation.evaluation.impl;

import java.io.IOException;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data.ModuleDescriptions;
import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data.ModuleDescriptionsBuilder;
import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.io.ModuleDescriptionXMLInput;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.io.structured.spec.IXMLInputJobBuilder;

/** A shared list of evaluation module descriptions */
public final class EvaluationModuleDescriptions {

  /** the synchronizer */
  private static final Object SYNCH = new Object();

  /** the globally shared instance of the module descriptions */
  private static ModuleDescriptions DESCRIPTIONS = null;

  /**
   * Get the default module descriptions
   *
   * @return the descriptions
   */
  public static final ModuleDescriptions getDescriptions() {
    return EvaluationModuleDescriptions.getDescriptions(null);
  }

  /**
   * Get the default module descriptions
   *
   * @param logger
   *          a logger to use
   * @return the descriptions
   */
  public static final ModuleDescriptions getDescriptions(
      final Logger logger) {
    final IXMLInputJobBuilder<ModuleDescriptionsBuilder> job;
    final String argh;

    synchronized (EvaluationModuleDescriptions.SYNCH) {
      if (EvaluationModuleDescriptions.DESCRIPTIONS == null) {
        try (final ModuleDescriptionsBuilder builder = new ModuleDescriptionsBuilder()) {

          try {
            job = ModuleDescriptionXMLInput.getInstance().use();
            if (logger != null) {
              job.setLogger(logger);
            }
            job.addResource(EvaluationModuleDescriptions.class,
                "modules.xml").configure(Configuration.getRoot()) //$NON-NLS-1$
                .setDestination(builder).create().call();
          } catch (final IOException ioe) {
            argh = "Could not load the module list.";//$NON-NLS-1$
            ErrorUtils.logError(logger, argh, ioe, false,
                RethrowMode.AS_ILLEGAL_STATE_EXCEPTION);
          }
          EvaluationModuleDescriptions.DESCRIPTIONS = builder.getResult();
        }
      }
      return EvaluationModuleDescriptions.DESCRIPTIONS;
    }
  }
}
