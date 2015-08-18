package org.optimizationBenchmarking.experimentation.evaluation.impl;

import java.io.IOException;

import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data.ModuleDescriptions;
import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data.ModuleDescriptionsBuilder;
import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.io.ModuleDescriptionXMLInput;
import org.optimizationBenchmarking.utils.config.Configuration;

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
    synchronized (SYNCH) {
      if (DESCRIPTIONS == null) {
        try (final ModuleDescriptionsBuilder builder = new ModuleDescriptionsBuilder()) {

          try {
            ModuleDescriptionXMLInput
                .getInstance()
                .use()
                .addResource(EvaluationModuleDescriptions.class,
                    "modules.xml").configure(Configuration.getRoot()) //$NON-NLS-1$
                .setDestination(builder).create().call();
          } catch (IOException ioe) {
            throw new IllegalStateException(
                "Could not load the module list.",//$NON-NLS-1$
                ioe);
          }
          DESCRIPTIONS = builder.getResult();
        }
      }
      return DESCRIPTIONS;
    }
  }
}
