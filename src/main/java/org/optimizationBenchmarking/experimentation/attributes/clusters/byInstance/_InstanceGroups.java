package org.optimizationBenchmarking.experimentation.attributes.clusters.byInstance;

import org.optimizationBenchmarking.experimentation.attributes.clusters.ICluster;
import org.optimizationBenchmarking.experimentation.attributes.clusters.IClustering;
import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A set of instance-based clusters.
 */
final class _InstanceGroups extends DataElement implements IClustering {

  /** the owner */
  private final IExperimentSet m_owner;

  /** the data */
  private final ArrayListView<ICluster> m_data;

  /**
   * create the instance groups
   *
   * @param owner
   *          the owner
   * @param data
   *          the data
   */
  _InstanceGroups(final IExperimentSet owner, final ICluster[] data) {
    super();

    this.m_owner = owner;
    this.m_data = new ArrayListView<>(data);
  }

  /** {@inheritDoc} */
  @Override
  public final IExperimentSet getOwner() {
    return this.m_owner;
  }

  /** {@inheritDoc} */
  @Override
  public final void appendName(final IMath math) {
    try (IComplexText text = math.text()) {
      this.appendName(text, ETextCase.IN_SENTENCE);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendName(final ITextOutput textOut,
      final ETextCase textCase) {
    return ETextCase.ensure(textCase).appendWords(//
        "grouped by instance", textOut); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final String getPathComponentSuggestion() {
    return "instances"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final ArrayListView<ICluster> getData() {
    return this.m_data;
  }
}
