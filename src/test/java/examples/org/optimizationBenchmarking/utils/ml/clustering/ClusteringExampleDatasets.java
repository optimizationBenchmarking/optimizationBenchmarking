package examples.org.optimizationBenchmarking.utils.ml.clustering;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix1D;

/** The loader for the clustering example data sets. */
public final class ClusteringExampleDatasets
    implements Callable<ArrayListView<ClusteringExampleDataset>> {

  /** the internal data set list */
  private static final String[] DATASETS = { //
      "simple2", //$NON-NLS-1$
      "simple3", //$NON-NLS-1$
      "simple4", //$NON-NLS-1$
      "iris", //$NON-NLS-1$
  };

  /** create */
  public ClusteringExampleDatasets() {
    super();
  }

  /**
   * load an example data set
   *
   * @param name
   *          the data set name
   * @return the data set
   * @throws IOException
   *           if i/o fails
   */
  private static final ClusteringExampleDataset __load(final String name)
      throws IOException {
    final ArrayList<double[]> features;
    final ArrayList<Integer> classes;
    final int n, m;
    String[] featuresStr;
    double[] cur;
    int[] assignments;
    int i;
    String s;

    try (final InputStream is = ClusteringExampleDatasets.class
        .getResourceAsStream(//
            name + ".txt")) {//$NON-NLS-1$
      try (final InputStreamReader isr = new InputStreamReader(is)) {
        try (final BufferedReader br = new BufferedReader(isr)) {
          features = new ArrayList<>();
          classes = new ArrayList<>();

          while ((s = br.readLine()) != null) {
            s = s.trim();
            if (s.length() <= 0) {
              continue;
            }

            featuresStr = s.split(","); //$NON-NLS-1$
            i = featuresStr.length;

            classes.add(//
                Integer.valueOf((featuresStr[--i].charAt(0) - 'A')));
            cur = new double[i];
            for (; (--i) >= 0;) {
              cur[i] = Double.parseDouble(featuresStr[i]);
            }
            features.add(cur);
          }
        }
      }
    }

    m = classes.size();
    assignments = new int[m];
    for (i = m; (--i) >= 0;) {
      assignments[i] = classes.get(i).intValue();
    }

    n = features.get(0).length;
    cur = new double[m * n];
    i = 0;
    for (final double[] vec : features) {
      System.arraycopy(vec, 0, cur, i, n);
      i += n;
    }

    return new ClusteringExampleDataset(name,
        new DoubleMatrix1D(cur, m, n), assignments[m - 1] + 1,
        assignments);
  }

  /** {@inheritDoc} */
  @Override
  public final ArrayListView<ClusteringExampleDataset> call()
      throws IOException {
    final ClusteringExampleDataset[] ds;
    int i;

    i = ClusteringExampleDatasets.DATASETS.length;
    ds = new ClusteringExampleDataset[i];
    for (final String name : ClusteringExampleDatasets.DATASETS) {
      ds[--i] = ClusteringExampleDatasets.__load(name);
    }
    Arrays.sort(ds);
    return new ArrayListView<>(ds);
  }

}
