package org.optimizationBenchmarking.experimentation.attributes.clusters;

import org.optimizationBenchmarking.experimentation.attributes.clusters.byInstance.ByInstanceGrouping;
import org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint.InstanceFingerprintClusterer;
import org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups.PropertyValueSelector;
import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * This class allows for load a clustering method.
 */
public final class ClustererLoader {
  /** The the group-by parameter: {@value} */
  public static final String PARAM_GROUPING = "grouping"; //$NON-NLS-1$

  /**
   * Obtain the attribute used to get the clustering
   *
   * @param data
   *          the data
   * @param config
   *          the configuration
   * @return the clustering, or {@code null} if no clustering is provided
   */
  public static final Attribute<? super IExperimentSet, ? extends IClustering> configureClustering(
      final IExperimentSet data, final Configuration config) {
    final String grouping;

    grouping = config.getString(ClustererLoader.PARAM_GROUPING, null);
    if (grouping == null) {
      return null;
    }

    switch (TextUtils.toLowerCase(grouping)) {
      case ByInstanceGrouping.CHOICE_BY_INSTANCE: {
        return ByInstanceGrouping.INSTANCE;
      }
      case PropertyValueSelector.CHOICE_EXPERIMENTS_BY_PARAMETER_VALUE:
      case PropertyValueSelector.CHOICE_INSTANCES_BY_FEATURE_VALUE: {
        return PropertyValueSelector.configure(data, config);
      }
      case InstanceFingerprintClusterer.CHOICE_INSTANCES_BY_PERFORMANCE: {
        return InstanceFingerprintClusterer.INSTANCE;
      }
      default: {
        throw new IllegalArgumentException(((//
            "Unknown grouping scheme: '" + grouping) + '\'') + '.');//$NON-NLS-1$
      }
    }
  }

  /** the forbidden constructor */
  private ClustererLoader() {
    ErrorUtils.doNotCall();
  }
}
