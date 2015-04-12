package examples.org.optimizationBenchmarking.experimentation.evaluation;

import java.nio.file.Files;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;

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

  /** the evaluator configurations */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static final ArrayListView<Class<? extends EvaluationExample>> EXAMPLES = new ArrayListView(
      new Class[] { ExperimentInfoExample.class });

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

    if ((args != null) && (args.length > 0)) {
      dir = PathUtils.normalize(args[0]);
    } else {
      dir = Files.createTempDirectory("evaluation"); //$NON-NLS-1$
    }

    for (final Class<? extends EvaluationExample> clazz : EvaluationExamples.EXAMPLES) {
      clazz.newInstance().process(dir);
    }

  }

}
