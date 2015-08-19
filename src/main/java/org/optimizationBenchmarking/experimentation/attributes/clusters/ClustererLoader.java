package org.optimizationBenchmarking.experimentation.attributes.clusters;

import org.optimizationBenchmarking.experimentation.attributes.clusters.byInstance.ByInstanceGrouping;
import org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups.PropertyValueSelector;
import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.ErrorUtils;

/**
 * This class allows for load a clustering method.
 */
public final class ClustererLoader {
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

    if (config.getBoolean(ByInstanceGrouping.PARAM_BY_INSTANCE, false)) {
      return ByInstanceGrouping.INSTANCE;
    }

    return PropertyValueSelector.configure(data, config);
  }

  /** the forbidden constructor */
  private ClustererLoader() {
    ErrorUtils.doNotCall();
  }
}
