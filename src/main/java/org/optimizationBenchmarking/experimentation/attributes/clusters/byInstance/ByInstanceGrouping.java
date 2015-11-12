package org.optimizationBenchmarking.experimentation.attributes.clusters.byInstance;

import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.attributes.clusters.ICluster;
import org.optimizationBenchmarking.experimentation.attributes.clusters.IClustering;
import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;

/**
 * An attribute for splitting and grouping experiments by single instances.
 */
public final class ByInstanceGrouping
    extends Attribute<IExperimentSet, IClustering> {

  /** a parameter that can be used to group information by instance */
  public static final String CHOICE_BY_INSTANCE = "single instances"; //$NON-NLS-1$

  /** the globally shared instance */
  public static final ByInstanceGrouping INSTANCE = new ByInstanceGrouping();

  /** create the attribute */
  private ByInstanceGrouping() {
    super(EAttributeType.TEMPORARILY_STORED);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unused")
  @Override
  protected final IClustering compute(final IExperimentSet data,
      final Logger logger) {
    final int origSize;
    final ICluster[] clusters;
    final _InstanceGroups groups;
    HashSet<IInstance> instances;
    IInstance[] instanceArray;
    DataSelection selection;
    int i;

    origSize = data.getInstances().getData().size();

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.log(Level.FINE, //
          "Now grouping instance run sets by instance, which should result in " //$NON-NLS-1$
              + origSize + " groups, that is, one per instance.");//$NON-NLS-1$
    }

    instances = new HashSet<>(origSize);

    findInstances: for (final IExperiment experiment : data.getData()) {
      for (final IInstanceRuns instanceSet : experiment.getData()) {
        if (instances.add(instanceSet.getInstance())) {
          if (instances.size() >= origSize) {
            break findInstances;
          }
        }
      }
    }

    i = instances.size();
    instanceArray = instances.toArray(new IInstance[i]);
    try {
      Arrays.sort(instanceArray);
    } catch (final Throwable ignore) {
      //
    }
    instances = null;

    clusters = new ICluster[i];

    groups = new _InstanceGroups(data, clusters);
    for (; (--i) >= 0;) {
      selection = new DataSelection(data);
      selection.addInstance(instanceArray[i]);
      clusters[i] = new _InstanceCluster(groups, selection);
      selection = null;
    }

    return groups;
  }

  /** {@inhertitDoc} */
  @Override
  public final String toString() {
    return "by instance"; //$NON-NLS-1$
  }
}
