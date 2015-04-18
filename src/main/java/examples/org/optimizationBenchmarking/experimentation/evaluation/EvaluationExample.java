package examples.org.optimizationBenchmarking.experimentation.evaluation;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.DocumentEvaluationOutput;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator.Evaluator;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluation;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationBuilder;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.config.ConfigurationBuilder;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentConfiguration;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOTool;
import org.optimizationBenchmarking.utils.text.TextUtils;

import examples.org.optimizationBenchmarking.experimentation.dataAndIO.ExperimentSetCreator;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.ExperimentSetExamples;
import examples.org.optimizationBenchmarking.utils.document.ExampleDocumentConfigurations;

/**
 * This class provides examples for the capabilities of the experiment
 * evaluation abilities. It applies different configurations of the
 * evaluator with different
 * {@link org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationModule
 * modules} to different example data sets, both
 * {@link examples.org.optimizationBenchmarking.experimentation.dataAndIO.RandomExample
 * randomly created} as well as extracted from
 * {@link examples.org.optimizationBenchmarking.experimentation.dataAndIO.TSPSuiteExample
 * actual experiments}. For the output, different
 * {@link org.optimizationBenchmarking.utils.document.spec.IDocumentDriver
 * document drivers} are used, in combination with different
 * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver
 * graphic drivers}.
 */
public abstract class EvaluationExample {

  /** create */
  private static final AtomicLong COUNTER = new AtomicLong();

  /** create */
  protected EvaluationExample() {
    super();
  }

  /**
   * get the resource class
   * 
   * @return the class to load the resource from
   */
  protected Class<?> getResourceClass() {
    return this.getClass();
  }

  /**
   * Get the resource name
   * 
   * @return the resource name
   */
  protected abstract String getResourceName();

  /**
   * Process a given experiment source and destination
   * 
   * @param input
   *          the experiment set source
   * @param dest
   *          the destination
   * @param baseDir
   *          the base output directory
   * @param root
   *          the root configuration
   * @param logger
   *          the logger
   */
  public final void process(final ExperimentSetCreator input,
      final DocumentConfiguration dest, final Path baseDir,
      final Configuration root, final Logger logger) {
    final String inputName, destName, configName;
    Configuration job;
    IEvaluationBuilder builder;
    IEvaluation eval;
    Path outputDir;

    inputName = input.getClass().getSimpleName();
    destName = dest.toString();
    configName = this.getClass().getSimpleName();

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info("Now applying " //$NON-NLS-1$
          + configName + //
          " to example data set " //$NON-NLS-1$
          + inputName + //
          " with output format " //$NON-NLS-1$
          + destName);
    }

    outputDir = PathUtils
        .normalize(baseDir
            .resolve(inputName)
            .resolve(configName)
            .resolve(
                (destName + '_')
                    + EvaluationExample.COUNTER.incrementAndGet()));

    builder = Evaluator.getInstance().use();
    builder.setInput(input);
    builder.setLogger(logger);
    try (final IDocument doc = dest.createDocument(outputDir, "report", //$NON-NLS-1$
        null, logger)) {
      builder.setOutput(new DocumentEvaluationOutput(doc));

      try (final ConfigurationBuilder cb = new ConfigurationBuilder()) {
        cb.setOwner(root);
        cb.put(Evaluator.PARAM_EVALUATION_SETUP, IOTool.RESOURCE_ELEMENT
            + '(' + TextUtils.className(this.getResourceClass()) + '#'
            + this.getResourceName() + ')');
        job = cb.getResult();
      }

      builder.configure(job);

      eval = builder.create();
      eval.run();
    }

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info("Finished applying " //$NON-NLS-1$
          + configName + //
          " to example data set " //$NON-NLS-1$
          + inputName + //
          " with output format " //$NON-NLS-1$
          + destName);
    }
  }

  /**
   * Process: Write all the examples in a given base dir
   * 
   * @param baseDir
   *          the base dir
   */
  public final void process(final Path baseDir) {
    final Configuration root;
    final Logger logger;

    root = Configuration.getRoot();
    logger = Configuration.getGlobalLogger();
    try {
      for (final Class<? extends ExperimentSetCreator> source : ExperimentSetExamples.EXAMPLES) {
        for (final DocumentConfiguration dest : ExampleDocumentConfigurations.FEW_DIVERSE_CONFIGURATIONS) {
          this.process(
              source.getConstructor(Logger.class).newInstance(logger),
              dest, baseDir, root, logger);
        }
      }
    } catch (final Throwable tt) {
      ErrorUtils.logError(
          logger, //
          ("Severe error during experiment example " //$NON-NLS-1$
          + TextUtils.className(this.getClass())), tt, true,
          RethrowMode.AS_RUNTIME_EXCEPTION);
    }
  }

}
