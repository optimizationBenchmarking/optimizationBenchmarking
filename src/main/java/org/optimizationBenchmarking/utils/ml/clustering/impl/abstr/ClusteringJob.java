package org.optimizationBenchmarking.utils.ml.clustering.impl.abstr;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.combinatorics.CanonicalPermutation;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix1D;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringJob;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringResult;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/** The base class for clustering jobs. */
public abstract class ClusteringJob extends ToolJob
    implements IClusteringJob {

  /** the data matrix */
  protected IMatrix m_matrix;

  /** the number of classes, {@code -1} if unspecified */
  protected final int m_classes;

  /**
   * create the clustering job
   *
   * @param builder
   *          the job builder
   */
  protected ClusteringJob(final ClusteringJobBuilder builder) {
    super(builder);

    ClusteringJobBuilder._checkClusterNumber(//
        this.m_classes = builder.m_classes, true);
    ClusteringJobBuilder._checkMatrix(this.m_matrix = builder.m_matrix);
  }

  /**
   * check if two {@code double} values are sufficiently equal so that they
   * can be ignored.
   *
   * @param a
   *          the first {@code double} value
   * @param b
   *          the second {@code double} value
   * @return {@code true} if both are sufficiently equal
   */
  private static final boolean __equals(final double a, final double b) {
    return (MathUtils.difference(a, b) <= 3);
  }

  /**
   * Perform the clustering and return a solution record. The result of
   * this method will be automatically {@link #normalizeClusters(int[])}
   * normalized.
   *
   * @return the solution
   * @throws Exception
   *           if something goes wrong
   */
  protected abstract ClusteringSolution cluster() throws Exception;

  /** {@inheritDoc} */
  @Override
  public final IClusteringResult call() throws IllegalArgumentException {
    ClusteringSolution solution;
    Throwable error;
    String message;

    error = null;
    try {
      solution = this.cluster();
      if (MathUtils.isFinite(solution.quality)) {
        ClusteringJob.normalizeClusters(solution.assignment);
        return solution;
      }
    } catch (final IllegalArgumentException iae) {
      throw iae;
    } catch (final Throwable cause) {
      error = cause;
    } finally {
      this.m_matrix = null;
    }

    message = (("Could not cluster the data with method ") //$NON-NLS-1$
        + this + '.');
    if (error != null) {
      throw new IllegalArgumentException(message, error);
    }
    throw new IllegalArgumentException(message);
  }

  /**
   * Preprocess a given matrix: delete useless columns, normalize columns
   *
   * @param matrix
   *          the matrix
   * @return a preprocessed version
   */
  public static final DoubleMatrix1D preprocess(final IMatrix matrix) {
    final double[] min, max;
    final int[] columns;
    final int m;
    double[] data, data2;
    int realN, n, i, j, rj, k;
    double d;

    m = matrix.m();
    n = matrix.n();
    min = new double[n];
    Arrays.fill(min, Double.POSITIVE_INFINITY);
    max = new double[n];
    Arrays.fill(max, Double.NEGATIVE_INFINITY);

    // find minima and maxima
    for (i = m; (--i) >= 0;) {
      for (j = n; (--j) >= 0;) {
        d = matrix.getDouble(i, j);
        if (d < min[j]) {
          min[j] = d;
        }
        if (d > max[j]) {
          max[j] = d;
        }
      }
    }

    // allocate the column list
    columns = new int[n];
    for (j = n; (--j) >= 0;) {
      columns[j] = j;
    }
    realN = n;

    // check for columns that can be deleted based on their raw data values
    outer: for (j = n; (--j) >= 0;) {
      if (ClusteringJob.__equals(min[j], max[j])) {
        // the column contains only one single value
        columns[j] = columns[--realN];
        continue outer;
      }

      // check if the column equals another column
      inner: for (i = j; (--i) >= 0;) {
        if (ClusteringJob.__equals(min[j], min[i])
            && ClusteringJob.__equals(max[j], max[i])) {
          // well, there minimum and maximum are the same, so maybe...
          for (k = m; (--k) >= 0;) {
            if (!(ClusteringJob.__equals(matrix.getDouble(k, j),
                matrix.getDouble(k, i)))) {
              continue inner;
            }
          }
          // ok, they are similar
          columns[j] = columns[--realN];
          continue outer;
        }
      }
    }

    // now normalize the data
    for (j = n; (--j) >= 0;) {
      max[j] -= min[j];
    }

    k = (m * realN);
    data = new double[k];
    for (i = m; (--i) >= 0;) {
      for (j = realN; (--j) >= 0;) {
        rj = columns[j];
        data[--k] = Math.min(1d, Math.max(0d, //
            ((matrix.getDouble(i, rj) - min[rj]) / max[rj])));
      }
    }

    // now check again if some columns are redundant
    for (j = realN; (--j) >= 0;) {
      columns[j] = j;
    }

    n = realN;
    outer2: for (j = realN; (--j) > 0;) {

      inner2: for (i = j; (--i) >= 0;) {

        for (k = ((m - 1) * n); k >= 0; k -= n) {
          if (!(ClusteringJob.__equals(data[k + j], data[k + i]))) {
            continue inner2;
          }
        }

        // ok, they are similar
        columns[j] = columns[--realN];
        continue outer2;
      }
    }

    // Did we delete some data now?
    if (realN != n) {
      // Great, let's re-allocate
      k = (realN * m);
      data2 = new double[k];
      for (i = m; (--i) >= 0;) {
        for (j = realN; (--j) >= 0;) {
          data2[(i * realN) + j] = data[(i * n) + columns[j]];
        }
      }
      data = data2;
      n = realN;
    }

    return new DoubleMatrix1D(data, m, n);
  }

  /**
   * Check whether there is a simple, default solution for the clustering
   * problem
   *
   * @param matrix
   *          the data matrix
   * @param numClasses
   *          the number of classes we want
   * @return the default solution, or {@code null} if there is none
   * @throws IllegalArgumentException
   *           if the clustering job cannot be done
   */
  protected static final ClusteringSolution canSolveDefault(
      final IMatrix matrix, final int numClasses) {
    return ClusteringJob._canSolveDefault(matrix, numClasses);
  }

  /**
   * Check whether there is a simple, default solution for the clustering
   * problem
   *
   * @param matrix
   *          the data matrix
   * @param numClasses
   *          the number of classes we want
   * @return the default solution, or {@code null} if there is none
   * @throws IllegalArgumentException
   *           if the clustering job cannot be done
   */
  static final _DirectResult _canSolveDefault(final IMatrix matrix,
      final int numClasses) {
    final int m;

    m = matrix.m();
    if (numClasses > m) {
      throw new IllegalArgumentException(
          m + " data samples cannot be divided into " //$NON-NLS-1$
              + numClasses + //
              " clusters."); //$NON-NLS-1$
    }
    if (m <= numClasses) {
      return new _DirectResult(CanonicalPermutation.createCanonicalZero(m),
          0d);
    }

    if ((m == 1) || ((numClasses >= 0) && (numClasses <= 1))) {
      return new _DirectResult(new int[m], 0d);
    }

    return null;
  }

  /**
   * Normalize the clusters: We sort clusters by their size, bigger
   * clusters come first. Clusters of equal size are sorted according to
   * their smallest member index.
   *
   * @param clusters
   *          the cluster array to normalize
   */
  public static final void normalizeClusters(final int[] clusters) {
    final __TempCluster[] alloc;
    __TempCluster[] sorted;
    __TempCluster cur;
    int min, max, i;

    min = Integer.MAX_VALUE;
    max = Integer.MIN_VALUE;

    for (final int a : clusters) {
      if (a < min) {
        min = a;
      }
      if (a > max) {
        max = a;
      }
    }

    if (min >= max) {
      Arrays.fill(clusters, 0);
      return;
    }

    alloc = new __TempCluster[(max - min) + 1];
    for (i = alloc.length; (--i) >= 0;) {
      alloc[i] = new __TempCluster();
    }
    for (final int a : clusters) {
      cur = alloc[a - min];
      ++cur.m_size;
      if (a < cur.m_minMember) {
        cur.m_minMember = a;
      }
    }

    sorted = alloc.clone();
    Arrays.sort(sorted);
    for (i = sorted.length; (--i) >= 0;) {
      sorted[i].m_newID = i;
    }
    sorted = null;

    i = (-1);
    for (final int a : clusters) {
      clusters[++i] = alloc[a - min].m_newID;
    }
  }

  /** a temporary cluster */
  private static final class __TempCluster
      implements Comparable<__TempCluster> {

    /** the members */
    int m_minMember;

    /** the number of members */
    int m_size;

    /** the new cluster id */
    int m_newID;

    /** create */
    __TempCluster() {
      super();
      this.m_minMember = Integer.MAX_VALUE;
    }

    /** {@inheritDoc} */
    @Override
    public final int compareTo(final __TempCluster o) {
      int res;

      res = Integer.compare(o.m_size, this.m_size);
      if (res != 0) {
        return res;
      }

      return Integer.compare(this.m_minMember, o.m_minMember);
    }
  }
}
