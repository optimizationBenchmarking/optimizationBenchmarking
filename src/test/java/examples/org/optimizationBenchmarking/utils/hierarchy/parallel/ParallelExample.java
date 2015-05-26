package examples.org.optimizationBenchmarking.utils.hierarchy.parallel;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalTextOutput;

/** An example for parallel text output */
public final class ParallelExample {

  /**
   * the main method
   *
   * @param args
   *          command line arguments are ignored
   * @throws Throwable
   *           if something goes wrong
   */
  public static void main(final String[] args) throws Throwable {
    ForkJoinPool pool;

    pool = new ForkJoinPool(10);

    try (final HierarchicalTextOutput root = new HierarchicalTextOutput(
        System.out)) {

      pool.execute(new ParallelSectionTask(root.newText(), "1")); //$NON-NLS-1$

      pool.execute(new ParallelSectionTask(root.newText(), "2")); //$NON-NLS-1$

      pool.execute(new ParallelSectionTask(root.newText(), "3")); //$NON-NLS-1$

      pool.shutdown();
      pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }
  }

  /** the forbidden constructor */
  private ParallelExample() {
    ErrorUtils.doNotCall();
  }
}
