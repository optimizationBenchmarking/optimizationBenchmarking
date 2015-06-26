package org.optimizationBenchmarking.experimentation.evaluation.impl.all.function;

import org.optimizationBenchmarking.experimentation.attributes.clusters.ICluster;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/** The functions for a given experiment set */
public final class ExperimentSetFunctions {

  /** the owning function data */
  FunctionData m_owner;

  /** the experiment set (or ICluster) */
  private final IExperimentSet m_set;
  /**
   * the corresponding cluster, or {@code null} if the experiment set is
   * not a cluster
   */
  private final ICluster m_cluster;
  /** the functions */
  private final ArrayListView<ExperimentFunction> m_functions;

  /** the label */
  private ILabel m_label;

  /**
   * create the experiment set functions
   *
   * @param set
   *          the experiment set
   * @param cluster
   *          the corresponding cluster, or {@code null} if the experiment
   *          set is not a cluster
   * @param functions
   *          the functions
   */
  ExperimentSetFunctions(final IExperimentSet set, final ICluster cluster,
      final ExperimentFunction[] functions) {
    super();
    this.m_set = set;
    this.m_cluster = cluster;
    for (final ExperimentFunction func : functions) {
      func.m_owner = this;
    }
    this.m_functions = new ArrayListView<>(functions);
  }

  /**
   * Get the label associated with this data element
   *
   * @return the label associated with this data element
   */
  public final ILabel getLabel() {
    return this.m_label;
  }

  /**
   * set the label
   *
   * @param label
   *          the label
   */
  final void _setLabel(final ILabel label) {
    if (this.m_label != null) {
      throw new IllegalStateException("Label already set?");//$NON-NLS-1$
    }
    if (label == null) {
      throw new IllegalArgumentException("Cannot set label to null."); //$NON-NLS-1$
    }
    this.m_label = label;
  }

  /**
   * Obtain the experiment set
   *
   * @return the experiment set
   */
  public final IExperimentSet getExperimentSet() {
    return this.m_set;
  }

  /**
   * In case that the {@link #getExperimentSet() experiment set}
   * corresponds to a cluster, obtain the cluster &ndash; otherwise, return
   * {@code null}
   *
   * @return the cluster corresponding to the experiment set, or
   *         {@code null} if it does not correspond to a cluster
   */
  public final ICluster getCluster() {
    return this.m_cluster;
  }

  /**
   * Get the experiment functions. The returned list is guaranteed to
   * contain at least one element. Furthermore, the functions of the
   * elements in the returned list have at least two different {@code x}
   * and two different {@code y} coordinates.
   *
   * @return the experiment functions
   */
  public final ArrayListView<ExperimentFunction> getData() {
    return this.m_functions;
  }

  /**
   * Get the owning function data
   *
   * @return the owning function data
   */
  public final FunctionData getOwner() {
    return this.m_owner;
  }
}
