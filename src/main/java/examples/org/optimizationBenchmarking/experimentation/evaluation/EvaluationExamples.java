package examples.org.optimizationBenchmarking.experimentation.evaluation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.DocumentEvaluationOutput;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator.Evaluator;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluation;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationBuilder;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationInput;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.config.ConfigurationBuilder;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentConfiguration;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
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
public final class EvaluationExamples {

  /** the document configurations */
  public static final ArrayListView<DocumentConfiguration> DOCUMENT_CONFIGURATIONS = ExampleDocumentConfigurations.FEW_DIVERSE_CONFIGURATIONS;

  /** the evaluator configurations */
  public static final ArrayListView<String> EVALUATOR_CONFIGURATIONS = new ArrayListView<>(
      new String[] { "simple.xml" //$NON-NLS-1$
      });

  /** the examples to be used */
  public static final ArrayListView<Class<? extends ExperimentSetCreator>> DATA_EXAMPLES = ExperimentSetExamples.EXAMPLES;

  /**
   * Run all the experiments
   * 
   * @param args
   *          the command line arguments
   * @throws Throwable
   *           if something goes wrong
   */
  public static final void main(final String[] args) throws Throwable {
    final Path dir;
    final Configuration root;
    final Logger logger;
    Configuration job;
    IEvaluationBuilder builder;
    IEvaluationInput input;
    IEvaluation eval;
    int i;
    Path outputDir;

    if ((args != null) && (args.length > 0)) {
      dir = PathUtils.normalize(args[0]);
    } else {
      dir = Files.createTempDirectory("evaluation"); //$NON-NLS-1$
    }

    i = 0;
    Configuration.setup(args);
    root = Configuration.getRoot();

    logger = Configuration.getGlobalLogger();

    for (final Class<? extends ExperimentSetCreator> source : EvaluationExamples.DATA_EXAMPLES) {

      logger.info("Now processing example data set " + source); //$NON-NLS-1$
      input = source.newInstance();

      for (final String config : EvaluationExamples.EVALUATOR_CONFIGURATIONS) {
        logger.info("Now processing configuration " + config + //$NON-NLS-1$
            " for example data set " + source);//$NON-NLS-1$

        for (final DocumentConfiguration dest : EvaluationExamples.DOCUMENT_CONFIGURATIONS) {
          logger.info("Now processing document setup " + dest + //$NON-NLS-1$
              " for configuration " + config + //$NON-NLS-1$
              " for example data set " + source);//$NON-NLS-1$

          outputDir = PathUtils.normalize(dir
              .resolve(source.getSimpleName())
              .resolve(config.substring(0, config.lastIndexOf('.')))
              .resolve((dest.toString() + '_') + (++i)));

          builder = Evaluator.getInstance().use();
          builder.setInput(input);
          builder.setLogger(logger);
          try (final IDocument doc = dest.createDocument(outputDir,
              "report", //$NON-NLS-1$
              null, logger)) {
            builder.setOutput(new DocumentEvaluationOutput(doc));

            try (final ConfigurationBuilder cb = new ConfigurationBuilder()) {
              cb.setOwner(root);
              cb.put(
                  Evaluator.PARAM_EVALUATION_SETUP,
                  IOTool.RESOURCE_ELEMENT + '('
                      + TextUtils.className(EvaluationExamples.class)
                      + '#' + config + ')');
              job = cb.getResult();
            }

            builder.configure(job);

            eval = builder.create();
            eval.run();
          }

          logger.info("Finished processing document setup " + dest + //$NON-NLS-1$
              " for configuration " + config + //$NON-NLS-1$
              " for example data set " + source);//$NON-NLS-1$
        }

        logger.info("Finished processing configuration " + config + //$NON-NLS-1$
            " for example data set " + source);//$NON-NLS-1$
      }
      logger.info("Finished processing example data set " + source); //$NON-NLS-1$
    }

  }

}
