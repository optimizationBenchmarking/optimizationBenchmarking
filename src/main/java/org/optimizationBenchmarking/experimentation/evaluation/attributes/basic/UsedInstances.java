package org.optimizationBenchmarking.experimentation.evaluation.attributes.basic;

import java.util.Arrays;
import java.util.HashSet;

import org.optimizationBenchmarking.experimentation.data.Attribute;
import org.optimizationBenchmarking.experimentation.data.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.Experiment;
import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.Instance;
import org.optimizationBenchmarking.experimentation.data.InstanceRuns;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * An attribute for obtaining the instances which have actually been used,
 * i.e., for which we have runs.
 */
public final class UsedInstances extends
    Attribute<ExperimentSet, ArraySetView<Instance>> {

  /** the internal singleton instance */
  private static final UsedInstances INSTANCE = new UsedInstances();

  /** create */
  private UsedInstances() {
    super(EAttributeType.TEMPORARILY_STORED);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  protected final ArraySetView<Instance> compute(final ExperimentSet data) {
    final HashSet<Instance> instances;
    final Instance[] result;
    final int goalCount;
    int count;

    goalCount = data.getInstances().getData().size();
    if (goalCount > 0) {
      instances = new HashSet<>();
      count = 0;
      loop: for (final Experiment experiment : data.getData()) {
        for (final InstanceRuns runs : experiment.getData()) {
          if (instances.add(runs.getInstance())) {
            if ((++count) >= goalCount) {
              break loop;
            }
          }
        }
      }

      if (count > 0) {
        result = instances.toArray(new Instance[count]);
        Arrays.sort(result);
        return new ArraySetView<>(result);
      }
    }
    return ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
  }

  /**
   * Get the globally shared instance of this attribute
   * 
   * @return the globally shared instance of this attribute
   */
  public static final UsedInstances getInstance() {
    return UsedInstances.INSTANCE;
  }

}
