package org.optimizationBenchmarking.experimentation.attributes.clusters.behavior;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A algorithm behavior cluster is a cluster which holds algorithm
 * belonging to one similarity group according to their runtime behavior.
 */
public class AlgorithmBehaviorCluster
    extends _BehaviorCluster<AlgorithmBehaviorClustering> {

  /**
   * create the algorithm behavior cluster
   *
   * @param owner
   *          the owning element set
   * @param name
   *          the name of the cluster
   * @param selection
   *          the data selection
   */
  AlgorithmBehaviorCluster(final AlgorithmBehaviorClustering owner,
      final String name, final DataSelection selection) {
    super(owner, name, selection);
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printShortName(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.printLongName(textOut, textCase);
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    textOut.append(this.getInstances().getData().size());
    textOut.append(' ');
    return super.printLongName(textOut, textCase);
  }
}
