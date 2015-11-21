package examples.org.optimizationBenchmarking.utils.math.fitting;

import java.nio.file.Path;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.fitting.impl.ls.LSFitter;
import org.optimizationBenchmarking.utils.math.fitting.spec.FunctionFitter;

/** The examples for fitting. */
public class FittingExamples {

  /** the fitters */
  public static final ArrayListView<FunctionFitter> FITTERS = //
  new ArrayListView<>(new FunctionFitter[] { //
      LSFitter.getInstance(), });

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
    final ForkJoinPool pool;
    final ArrayListView<FittingExampleDataset> data;
    final Path dest;
    final Future[] wait;
    int i;

    pool = new ForkJoinPool(
        Math.max(1, Runtime.getRuntime().availableProcessors() - 1));
    data = pool.submit(new FittingExampleDatasets()).get();

    dest = PathUtils.createPathInside(PathUtils.getTempDir(), "results"); //$NON-NLS-1$
    i = FittingExamples.FITTERS.size();
    wait = new Future[i];
    for (; (--i) >= 0;) {
      wait[i] = pool.submit(
          new _FitterJob(FittingExamples.FITTERS.get(i), data, dest));
    }
    pool.shutdown();
    for (i = wait.length; (--i) >= 0;) {
      wait[i].get();
    }
  }

}
