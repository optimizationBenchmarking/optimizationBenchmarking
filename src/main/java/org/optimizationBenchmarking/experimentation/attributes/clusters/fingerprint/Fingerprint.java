package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.processing.ConcatenatedMatrix;

/**
 * <p>
 * A fingerprint contains data which, hopefully, describes the performance
 * curve of an algorithm on a specific benchmark instance sufficiently well
 * to distinguish it from the curves of other algorithms on that instance
 * or from that algorithm on other instances. It has the following
 * structure:
 * </p>
 * <ul>
 * <li>For an instance of
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns}
 * , it is the corresponding
 * {@link org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint._InstanceRunsFingerprint}
 * , i.e., a row matrix containing basic, raw statistics.</li>
 * <li>For an instance of
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstance}
 * , it is a row matrix with all single
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns}
 * {@linkplain org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint._InstanceRunsFingerprint
 * fingerprints} mentioned above (one for each experiment which contains
 * corresponding runs) concatenated to each other.</li>
 * <li>For an instance of
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet}
 * , it is a matrix with one row for each instance, and the row contains
 * the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstance}
 * fingerprint mentioned above.</li>
 * <li>For an instance of
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IExperiment}
 * , it is a row matrix with one
 * {@linkplain org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint._InstanceRunsFingerprint
 * fingerprint} for each of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns}
 * the experiment contains.</li>
 * <li>For an instance of
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet}
 * , it is a matrix with one row for each experiment, and the row contains
 * the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IExperiment}
 * fingerprint mentioned above.</li>
 * <li>No other type is supported.</li>
 * </ul>
 */
public final class Fingerprint extends Attribute<IDataElement, IMatrix> {

  /** create the fingerprint */
  public static final Fingerprint INSTANCE = new Fingerprint();

  /** create the attribute */
  private Fingerprint() {
    super(EAttributeType.NEVER_STORED);
  }

  /**
   * create the instance fingerprints.
   *
   * @param inst
   *          the instance
   * @return the fingerprints
   */
  private static final IMatrix[] __instanceFingerprints(
      final IInstance inst) {
    final ArrayListView<? extends IExperiment> experiments;
    final int size;
    IMatrix[] res, resize;
    int i, count;

    experiments = inst.getOwner().getOwner().getData();

    size = experiments.size();
    res = new IMatrix[size];
    count = 0;
    loop: for (i = 0; i < size; i++) {
      for (final IInstanceRuns runs : experiments.get(i).getData()) {
        if (runs.getInstance() == inst) {
          res[count++] = _InstanceRunsFingerprint.INSTANCE.get(runs);
          continue loop;
        }
      }
    }

    if (res.length == count) {
      return res;
    }
    resize = new IMatrix[count];
    System.arraycopy(res, 0, resize, 0, count);
    return resize;
  }

  /**
   * create the experiment fingerprints.
   *
   * @param experiment
   *          the experiment
   * @return the fingerprints
   */
  private static final IMatrix[] __experimentFingerprints(
      final IExperiment experiment) {
    final ArrayListView<? extends IInstanceRuns> runs;
    IMatrix[] res;
    int i;

    runs = experiment.getData();
    i = runs.size();
    res = new IMatrix[i];
    for (; (--i) >= 0;) {
      res[i] = _InstanceRunsFingerprint.INSTANCE.get(runs.get(i));
    }
    return res;
  }

  /**
   * Let's remove all columns with zero variance, as they do not contribute
   * to the overall information.
   *
   * @param data
   *          the data
   * @return the cleansed data
   */
  private static final IMatrix __removeZeroVarianceCols(final IMatrix data) {
    final int m, n;
    final int[] selected, chosen;
    long longVal;
    double doubleVal;
    int i, j, selCount;

    m = data.m();
    if (m <= 1) {
      return data;
    }

    n = data.n();
    selected = new int[n];
    selCount = 0;

    if (data.isIntegerMatrix()) {
      longOuter: for (j = 0; j < n; j++) {
        longVal = data.getLong(0, j);
        for (i = m; (--i) > 0;) {
          if (data.getLong(i, j) != longVal) {
            selected[selCount++] = j;
            continue longOuter;
          }
        }
      }
    } else {
      doubleOuter: for (j = 0; j < n; j++) {
        doubleVal = data.getDouble(0, j);
        for (i = m; (--i) > 0;) {
          if (EComparison.NOT_EQUAL.compare(data.getDouble(i, j),
              doubleVal)) {
            selected[selCount++] = j;
            continue doubleOuter;
          }
        }
      }
    }
    if (selCount >= n) {
      return data;
    }
    chosen = new int[selCount];
    System.arraycopy(selected, 0, chosen, 0, selCount);
    return data.selectColumns(chosen);
  }

  /** {@inheritDoc} */
  @Override
  protected final IMatrix compute(final IDataElement data) {
    final IMatrix[][] matrices;
    ArrayListView<? extends IInstance> instances;
    ArrayListView<? extends IExperiment> experiments;
    int i;

    if (data instanceof IInstanceRuns) {
      return _InstanceRunsFingerprint.INSTANCE.get((IInstanceRuns) data);
    }

    if (data instanceof IInstance) {
      return new ConcatenatedMatrix(new IMatrix[][] {//
          Fingerprint.__instanceFingerprints((IInstance) data) });
    }

    if (data instanceof IInstanceSet) {
      instances = ((IInstanceSet) data).getData();
      i = instances.size();

      matrices = new IMatrix[i][];
      for (; (--i) >= 0;) {
        matrices[i] = Fingerprint.__instanceFingerprints(instances.get(i));
      }
      return Fingerprint.__removeZeroVarianceCols(new ConcatenatedMatrix(
          matrices));
    }

    if (data instanceof IExperiment) {
      return new ConcatenatedMatrix(new IMatrix[][] {//
          Fingerprint.__experimentFingerprints((IExperiment) data) });
    }

    if (data instanceof IExperimentSet) {
      experiments = ((IExperimentSet) data).getData();
      i = experiments.size();
      matrices = new IMatrix[i][];
      for (; (--i) >= 0;) {
        matrices[i] = Fingerprint.__experimentFingerprints(experiments
            .get(i));
      }
      return Fingerprint.__removeZeroVarianceCols(new ConcatenatedMatrix(
          matrices));
    }

    throw new IllegalArgumentException(
        "Sorry, cannot compute fingerprint of " //$NON-NLS-1$
            + data);
  }
}
