package org.optimizationBenchmarking.experimentation.attributes.clusters.behavior;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A instance behavior cluster is a cluster which holds instances belonging
 * to one similarity group according to the runtime behavior of algorithms
 * on them.
 */
public class InstanceBehaviorCluster
    extends _BehaviorCluster<InstanceBehaviorClustering> {

  /**
   * create the instance behavior cluster
   *
   * @param owner
   *          the owning element set
   * @param name
   *          the name of the cluster
   * @param selection
   *          the data selection
   */
  InstanceBehaviorCluster(final InstanceBehaviorClustering owner,
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
