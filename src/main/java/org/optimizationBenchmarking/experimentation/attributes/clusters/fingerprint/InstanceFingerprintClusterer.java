package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElementSet;

/**
 * A clusterer for clustering via instance fingerprints.
 */
public final class InstanceFingerprintClusterer extends
    _FingerprintClusterer {

  /**
   * indicate that clustering should be performed based on instance
   * performance
   */
  public static final String CHOICE_INSTANCES_BY_PERFORMANCE = "instances by experimental results"; //$NON-NLS-1$

  /** create the instance finger print */
  public static final InstanceFingerprintClusterer INSTANCE = new InstanceFingerprintClusterer();

  /** create the instance fingerprint clusterer */
  private InstanceFingerprintClusterer() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final INamedElementSet _getClusterSource(final IExperimentSet data) {
    return data.getInstances();
  }
}
