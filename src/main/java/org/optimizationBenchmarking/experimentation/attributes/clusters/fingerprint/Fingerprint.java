package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import org.optimizationBenchmarking.utils.parallel.Execute;

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
 * , i.e., a row matrix containing the parameters of a fitted model.</li>
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
   * @param logger
   *          the logger
   * @return the fingerprints
   */
  @SuppressWarnings("unchecked")
  static final IMatrix[] _instanceFingerprints(final IInstance inst,
      final Logger logger) {
    final ArrayListView<? extends IExperiment> experiments;
    final int size;
    final Future<IMatrix>[] tasks;
    IMatrix[] res;
    int i, count;

    experiments = inst.getOwner().getOwner().getData();

    size = experiments.size();
    tasks = new Future[size];
    count = 0;
    loop: for (i = 0; i < size; i++) {
      for (final IInstanceRuns runs : experiments.get(i).getData()) {
        if (runs.getInstance() == inst) {
          tasks[count++] = Execute.parallel(
              _InstanceRunsFingerprint.INSTANCE.getter(runs, logger));
          continue loop;
        }
      }
    }

    res = new IMatrix[count];
    Execute.join(tasks, res, 0, true);
    return res;
  }

  /**
   * create the experiment fingerprints.
   *
   * @param experiment
   *          the experiment
   * @param logger
   *          the logger
   * @return the fingerprints
   */
  @SuppressWarnings("unchecked")
  static final IMatrix[] _experimentFingerprints(
      final IExperiment experiment, final Logger logger) {
    final ArrayListView<? extends IInstanceRuns> runs;
    final Future<IMatrix>[] tasks;
    IMatrix[] res;
    int i;

    runs = experiment.getData();
    i = runs.size();
    tasks = new Future[i];
    for (; (--i) >= 0;) {
      tasks[i] = Execute.parallel(
          _InstanceRunsFingerprint.INSTANCE.getter(runs.get(i), logger));
    }

    res = new IMatrix[tasks.length];
    Execute.join(tasks, res, 0, true);
    return res;
  }

  /**
   * Check if two columns are the same in a {@code long} matrix.
   *
   * @param matrix
   *          the matrix
   * @param m
   *          the number of rows
   * @param colA
   *          the first column
   * @param colB
   *          the second column
   * @return {@code true} if they are the same, {@code false} otherwise
   */
  private static final boolean __areColsSameLong(final IMatrix matrix,
      final int m, final int colA, final int colB) {
    int i;

    for (i = m; (--i) >= 0;) {
      if (matrix.getLong(i, colA) != matrix.getLong(i, colB)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check if a column has zero variance in a {@code long} matrix, i.e., if
   * all values in it are the same.
   *
   * @param matrix
   *          the matrix
   * @param m
   *          the number of rows
   * @param col
   *          the column
   * @return {@code true} if the column has zero variance
   */
  private static final boolean __hasColZeroVarianceLong(
      final IMatrix matrix, final int m, final int col) {
    final long longVal;
    int i;

    longVal = matrix.getLong(0, col);
    for (i = m; (--i) > 0;) {
      if (matrix.getLong(i, col) != longVal) {
        return false;
      }
    }

    return true;
  }

  /**
   * Check if two columns are the same in a {@code double} matrix.
   *
   * @param matrix
   *          the matrix
   * @param m
   *          the number of rows
   * @param colA
   *          the first column
   * @param colB
   *          the second column
   * @return {@code true} if they are the same, {@code false} otherwise
   */
  private static final boolean __areColsSameDouble(final IMatrix matrix,
      final int m, final int colA, final int colB) {
    int i;

    for (i = m; (--i) >= 0;) {
      if (EComparison.NOT_EQUAL.compare(matrix.getDouble(i, colA),
          matrix.getDouble(i, colB))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check if a column has zero variance in a {@code double} matrix, i.e.,
   * if all values in it are the same (or contain infinite/NaN).
   *
   * @param matrix
   *          the matrix
   * @param m
   *          the number of rows
   * @param col
   *          the column
   * @return {@code true} if the column has zero variance
   */
  private static final boolean __hasColZeroVarianceDouble(
      final IMatrix matrix, final int m, final int col) {
    final double doubleVal;
    double cur;
    boolean result;
    int i;

    doubleVal = matrix.getDouble(0, col);
    if ((doubleVal <= Double.NEGATIVE_INFINITY)
        || (doubleVal >= Double.POSITIVE_INFINITY)
        || (doubleVal != doubleVal)) {
      return true;
    }

    result = true;
    for (i = m; (--i) > 0;) {
      cur = matrix.getDouble(i, col);
      if ((cur <= Double.NEGATIVE_INFINITY)
          || (cur >= Double.POSITIVE_INFINITY) || (cur != cur)) {
        return true;
      }
      result = (result && EComparison.EQUAL.compare(cur, doubleVal));
    }

    return result;
  }

  /**
   * Let's remove all columns with zero variance, as they do not contribute
   * to the overall information, as well as columns which contain the same
   * values as others.
   *
   * @param data
   *          the data
   * @return the cleansed data
   */
  private static final IMatrix __removeUselessCols(final IMatrix data) {
    final int m, n;
    final int[] selected, chosen;
    int column, done, selCount;

    m = data.m();
    if (m <= 1) {
      return data;
    }

    n = data.n();
    selected = new int[n];
    selCount = 0;

    if (data.isIntegerMatrix()) {
      longOuter: for (column = 0; column < n; column++) {
        if (Fingerprint.__hasColZeroVarianceLong(data, m, column)) {
          continue longOuter;
        }
        for (done = selCount; (--done) >= 0;) {
          if (Fingerprint.__areColsSameLong(data, m, column,
              selected[done])) {
            continue longOuter;
          }
        }
        selected[selCount++] = column;
      }
    } else {
      doubleOuter: for (column = 0; column < n; column++) {
        if (Fingerprint.__hasColZeroVarianceDouble(data, m, column)) {
          continue doubleOuter;
        }
        for (done = selCount; (--done) >= 0;) {
          if (Fingerprint.__areColsSameDouble(data, m, column,
              selected[done])) {
            continue doubleOuter;
          }
        }
        selected[selCount++] = column;
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
  @SuppressWarnings("unchecked")
  @Override
  protected final IMatrix compute(final IDataElement data,
      final Logger logger) {
    final IMatrix[][] matrices;
    final IMatrix result;
    final IInstance instance;
    final IExperiment experiment;
    final Future<IMatrix[]>[] tasks;
    ArrayListView<? extends IInstance> instances;
    ArrayListView<? extends IExperiment> experiments;
    int i;

    if (data instanceof IInstanceRuns) {
      return _InstanceRunsFingerprint.INSTANCE.get(((IInstanceRuns) data),
          logger);
    }

    if (data instanceof IInstance) {
      instance = ((IInstance) data);
      result = new ConcatenatedMatrix(new IMatrix[][] { //
          Fingerprint._instanceFingerprints(instance, logger) });
      if ((logger != null) && (logger.isLoggable(Level.FINER))) {
        logger.finer(((((("The fingerprint for benchmark instance '" + //$NON-NLS-1$
            instance.getName())
            + "' is the column-wise concatenation of all fingerprint matrices of the corresponding instance runs, resulting in an ")//$NON-NLS-1$
            + result.m()) + '*') + result.n()) + " matrix.");//$NON-NLS-1$
      }
      return result;
    }

    if (data instanceof IInstanceSet) {
      instances = ((IInstanceSet) data).getData();
      i = instances.size();
      tasks = new Future[i];
      for (; (--i) >= 0;) {
        tasks[i] = Execute.parallel(//
            new __InstanceFingerprints(instances.get(i), logger));
      }

      matrices = new IMatrix[tasks.length][];
      Execute.join(tasks, matrices, 0, true);
      result = Fingerprint.__removeUselessCols(//
          new ConcatenatedMatrix(matrices));

      if ((logger != null) && (logger.isLoggable(Level.FINER))) {
        logger.finer(((((//
        "The fingerprint of the set of all benchmark instances is the row-wise concatenation of the fingerprint matrices of the single instances, pruned from columns that include only the same single value in each row, resulting in an ")//$NON-NLS-1$
            + result.m()) + '*') + result.n()) + " matrix.");//$NON-NLS-1$
      }

      return result;
    }

    if (data instanceof IExperiment) {
      experiment = ((IExperiment) data);
      result = new ConcatenatedMatrix(new IMatrix[][] { //
          Fingerprint._experimentFingerprints(experiment, logger) });

      if ((logger != null) && (logger.isLoggable(Level.FINER))) {
        logger.finer(((((//
        "The fingerprint of the experiment '" + //$NON-NLS-1$
            experiment.getName() + //
            "' is the column-wise concatenation of all fingerprint matrices of the single instance runs belonging to that experiment, resulting in an ")//$NON-NLS-1$
            + result.m()) + '*') + result.n()) + " matrix.");//$NON-NLS-1$
      }

      return result;
    }

    if (data instanceof IExperimentSet) {
      experiments = ((IExperimentSet) data).getData();
      i = experiments.size();
      tasks = new Future[i];
      for (; (--i) >= 0;) {
        tasks[i] = Execute.parallel(new __ExperimentFingerprints(//
            experiments.get(i), logger));
      }
      matrices = new IMatrix[tasks.length][];
      Execute.join(tasks, matrices, 0, true);

      result = Fingerprint.__removeUselessCols(//
          new ConcatenatedMatrix(matrices));

      if ((logger != null) && (logger.isLoggable(Level.FINER))) {
        logger.finer(((((//
        "The fingerprint of the set of all experiments is the row-wise concatenation of the fingerprint matrices of the single experiments, pruned from columns that include only the same single value in each row, resulting in an ")//$NON-NLS-1$
            + result.m()) + '*') + result.n()) + " matrix.");//$NON-NLS-1$
      }

      return result;
    }

    throw new IllegalArgumentException(
        "Sorry, cannot compute fingerprint of " //$NON-NLS-1$
            + data);
  }

  /** the instance fingerprints getter */
  private static final class __InstanceFingerprints
      implements Callable<IMatrix[]> {
    /** the instance */
    private final IInstance m_inst;
    /** the logger */
    private final Logger m_logger;

    /**
     * create the instance fingerprint getter
     *
     * @param inst
     *          the instance
     * @param logger
     *          the logger
     */
    __InstanceFingerprints(final IInstance inst, final Logger logger) {
      super();
      this.m_inst = inst;
      this.m_logger = logger;
    }

    /** {@inheritDoc} */
    @Override
    public final IMatrix[] call() {
      return Fingerprint._instanceFingerprints(this.m_inst, this.m_logger);
    }
  }

  /** the experiment fingerprints getter */
  private static final class __ExperimentFingerprints
      implements Callable<IMatrix[]> {
    /** the experiment */
    private final IExperiment m_inst;
    /** the logger */
    private final Logger m_logger;

    /**
     * create the experiment fingerprint getter
     *
     * @param inst
     *          the experiment
     * @param logger
     *          the logger
     */
    __ExperimentFingerprints(final IExperiment inst, final Logger logger) {
      super();
      this.m_inst = inst;
      this.m_logger = logger;
    }

    /** {@inheritDoc} */
    @Override
    public final IMatrix[] call() {
      return Fingerprint._experimentFingerprints(this.m_inst,
          this.m_logger);
    }
  }
}
