package org.optimizationBenchmarking.experimentation.attributes.clusters.byInstance;

import org.optimizationBenchmarking.experimentation.attributes.clusters.ICluster;
import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.experimentation.data.impl.shadow.ShadowExperimentSet;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an instance cluster */
final class _InstanceCluster extends ShadowExperimentSet<_InstanceGroups>
implements ICluster {

  /**
   * Create an instance cluster
   *
   * @param owner
   *          the owner
   * @param selection
   *          the selection
   */
  _InstanceCluster(final _InstanceGroups owner,
      final DataSelection selection) {
    super(owner, selection);
  }

  /** {@inheritDoc} */
  @Override
  public final void appendName(final IMath math) {
    this.getInstances().getData().get(0).appendName(math);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendName(final ITextOutput textOut,
      final ETextCase textCase) {
    final ETextCase use;

    use = ETextCase.ensure(textCase).appendWord("instance",//$NON-NLS-1$
        textOut);
    textOut.append(' ');
    return this.getInstances().getData().get(0).appendName(textOut, use);
  }

  /** {@inheritDoc} */
  @Override
  public final String getPathComponentSuggestion() {
    return this.getInstances().getData().get(0)
        .getPathComponentSuggestion();
  }

}
