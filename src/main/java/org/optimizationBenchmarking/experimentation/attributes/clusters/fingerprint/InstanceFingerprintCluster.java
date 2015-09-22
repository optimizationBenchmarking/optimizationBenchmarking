package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import org.optimizationBenchmarking.experimentation.attributes.clusters.ICluster;
import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A performance fingerprint cluster is a cluster which holds instances
 * belonging to one similarity group according to their performance
 * fingerprint.
 */
public class InstanceFingerprintCluster extends
    _FingerprintCluster<InstanceFingerprintClustering> implements ICluster {

  /**
   * create the property value group
   *
   * @param owner
   *          the owning element set
   * @param name
   *          the name of the cluster
   * @param selection
   *          the data selection
   */
  InstanceFingerprintCluster(final InstanceFingerprintClustering owner,
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
