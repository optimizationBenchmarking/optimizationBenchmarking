package examples.org.optimizationBenchmarking.utils.ml.clustering;

import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.ml.clustering.impl.Rbased.RBasedDataClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.impl.Rbased.RBasedDistanceClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusterer;
import org.optimizationBenchmarking.utils.parallel.Execute;
import org.optimizationBenchmarking.utils.parsers.LoggerParser;

/** The clustering examples. */
public final class ClusteringExamples {

  /** The list of clusterers */
  public static final ArrayListView<IClusterer> CLUSTERERS = new ArrayListView<>(
      new IClusterer[] { //
          RBasedDataClusterer.getInstance(), //
          RBasedDistanceClusterer.getInstance(),//
  });

  /**
   * run all the clustering jobs
   *
   * @param datasets
   *          the datasets
   * @param logger
   *          the logger
   * @return the result
   * @throws Exception
   *           if it must
   */
  @SuppressWarnings("unchecked")
  public static final ArrayListView<ClustererOutcome> runAll(
      final ArrayListView<ClusteringExampleDataset> datasets,
      final Logger logger) throws Exception {
    final Future<ClustererOutcome>[] jobs;
    final ClustererOutcome[] results;
    int index;

    index = ClusteringExamples.CLUSTERERS.size();
    jobs = new Future[index];
    for (; (--index) >= 0;) {
      jobs[index] = Execute.submitToCommonPool(new _ClustererJob(logger,
          datasets, ClusteringExamples.CLUSTERERS.get(index)));
    }
    results = new ClustererOutcome[jobs.length];
    Execute.join(jobs, results, 0, true);

    return new ArrayListView<>(results);
  }

  /**
   * The main routine
   *
   * @param args
   *          the command line arguments
   * @throws Throwable
   *           if it must
   */
  public static final void main(final String[] args) throws Throwable {
    final ArrayListView<ClusteringExampleDataset> datasets;
    final ArrayListView<ClustererOutcome> outcomes;
    final Logger logger;

    logger = LoggerParser.INSTANCE.parseString("global;ALL");//$NON-NLS-1$

    datasets = Execute.submitToCommonPool(new ClusteringExampleDatasets())
        .get();

    outcomes = ClusteringExamples.runAll(datasets, logger);

    System.out.println();
    System.out.println();
    System.out.println("================== MEAN ==================="); //$NON-NLS-1$
    System.out.println();
    System.out.print("clusterer");//$NON-NLS-1$
    for (final ClusteringExampleDataset ds : datasets) {
      System.out.print('\t');
      System.out.print(ds.name);
    }
    System.out.println();

    for (final ClustererOutcome oc : outcomes) {
      System.out.print(oc.clusterer.toString());
      for (final MultiClusteringOutcome mco : oc.outcomes) {
        System.out.print('\t');
        System.out.print(mco.mean);
      }
      System.out.println();
    }

    System.out.println();
    System.out.println();
    System.out.println("================== STDDEV ==================="); //$NON-NLS-1$
    System.out.println();
    System.out.print("clusterer");//$NON-NLS-1$
    for (final ClusteringExampleDataset ds : datasets) {
      System.out.print('\t');
      System.out.print(ds.name);
    }
    System.out.println();

    for (final ClustererOutcome oc : outcomes) {
      System.out.print(oc.clusterer.toString());
      for (final MultiClusteringOutcome mco : oc.outcomes) {
        System.out.print('\t');
        System.out.print(mco.stddev);
      }
      System.out.println();
    }
  }
}
