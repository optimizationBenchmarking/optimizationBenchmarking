package examples.org.optimizationBenchmarking.utils.ml.fitting;

import java.nio.file.Path;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.ml.fitting.impl.lssimplex.LSSimplexFitter;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFunctionFitter;
import org.optimizationBenchmarking.utils.parallel.Execute;
import org.optimizationBenchmarking.utils.parsers.LoggerParser;

/** The examples for fitting. */
public class FittingExamples {

  /** the fitters */
  public static final ArrayListView<IFunctionFitter> FITTERS = //
  new ArrayListView<>(new IFunctionFitter[] { //
      LSSimplexFitter.getInstance(), });

  /**
   * The main entry point
   *
   * @param args
   *          the arguments: ignored
   * @throws Exception
   *           if something fails
   */
  @SuppressWarnings("rawtypes")
  public static final void main(final String[] args) throws Exception {
    final ArrayListView<FittingExampleDataset> data;
    final Path dest;
    final Future[] wait;
    final Logger logger;
    int i;

    logger = LoggerParser.INSTANCE.parseString("global;ALL");//$NON-NLS-1$

    data = Execute.submitToCommonPool(new FittingExampleDatasets(//
        logger, false, true, true, true)).get();

    dest = PathUtils.createPathInside(PathUtils.getTempDir(), "results"); //$NON-NLS-1$
    i = FittingExamples.FITTERS.size();
    wait = new Future[i];
    for (; (--i) >= 0;) {
      wait[i] = Execute.submitToCommonPool(new _FitterJob(logger,
          FittingExamples.FITTERS.get(i), data, dest));
    }
    Execute.join(wait);
  }

}
