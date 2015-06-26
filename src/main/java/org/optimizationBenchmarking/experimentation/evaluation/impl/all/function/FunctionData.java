package org.optimizationBenchmarking.experimentation.evaluation.impl.all.function;

import org.optimizationBenchmarking.experimentation.attributes.clusters.IClustering;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/** The overall data of the function job */
public final class FunctionData {
  /** the clustering, or {@code null} if no clustering is defined */
  private final IClustering m_clustering;
  /** the functions */
  private final ArrayListView<ExperimentSetFunctions> m_functions;

  /** the label */
  private final ILabel m_label;

  /** the label for the legend figure */
  private final ILabel m_legendLabel;

  /**
   * Create the data
   *
   * @param clustering
   *          the clustering
   * @param functions
   *          the functions
   * @param label
   *          the main figure label
   * @param legendLabel
   *          the legend label
   */
  FunctionData(final IClustering clustering,
      final ExperimentSetFunctions[] functions, final ILabel label,
      final ILabel legendLabel) {
    super();
    this.m_clustering = clustering;
    this.m_functions = new ArrayListView<>(functions);
    for (final ExperimentSetFunctions esf : functions) {
      esf.m_owner = this;
    }
    this.m_label = label;
    this.m_legendLabel = legendLabel;
  }

  /**
   * Obtain the label for the legend figure
   *
   * @return the label for the legend figure
   */
  public final ILabel getLegendLabel() {
    return this.m_legendLabel;
  }

  /**
   * Obtain the clustering, if any was defined, or {@code null} otherwise
   *
   * @return the clustering, if any was defined, or {@code null} otherwise
   */
  public final IClustering getClustering() {
    return this.m_clustering;
  }

  /**
   * Obtain the experiment set functions. The returned list is guaranteed
   * to contain at least one element.
   *
   * @return the data
   */
  public final ArrayListView<ExperimentSetFunctions> getData() {
    return this.m_functions;
  }

  /**
   * Get the label associated with this data element
   *
   * @return the label associated with this data element
   */
  public final ILabel getLabel() {
    return this.m_label;
  }

}
