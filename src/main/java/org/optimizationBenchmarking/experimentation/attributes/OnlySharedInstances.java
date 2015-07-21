package org.optimizationBenchmarking.experimentation.attributes;

import java.util.ArrayList;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.experimentation.data.impl.shadow.ShadowExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/**
 * This checks all experiments in an experiment set and chooses only
 * instance runs for which data from all experiments exists. Assume that we
 * have four instances, {@code A}, {@code B}, {@code C}, and {@code D} as
 * well as three experiments {@code I}, {@code II}, and {@code III}.
 * Experiment {@code I} has runs for instance {@code A}, {@code B},
 * {@code C}, but not {@code D}. Experiment {@code II} has runs for all
 * instances. Experiment {@code III} has runs only for instance {@code A}
 * and {@code C}. Then a shadow copy of the original experiment set will be
 * created with shadow copies of the experiments only containing the runs
 * for instances {@code A} and {@code C}. If all experiments have data for
 * all instances, this attribute returns the original experiment set.
 */
public final class OnlySharedInstances extends
    Attribute<IExperimentSet, IExperimentSet> {

  /**
   * The globally shared instance of the {@link OnlySharedInstances}
   * attribute
   */
  public static final OnlySharedInstances INSTANCE = new OnlySharedInstances();

  /** create the instance of this attribute */
  private OnlySharedInstances() {
    super(EAttributeType.TEMPORARILY_STORED);
  }

  /** {@inheritDoc} */
  @Override
  protected final IExperimentSet compute(final IExperimentSet data) {
    final ArrayListView<? extends IInstance> origInstances;
    final ArrayList<IInstance> instances;
    final int origSize;
    final DataSelection selection;
    String wantName;
    IInstance want, have;
    int i;

    origInstances = data.getInstances().getData();
    origSize = origInstances.size();

    if (origSize <= 0) {
      return data;
    }

    instances = new ArrayList<>(origInstances);

    for (final IExperiment experiment : data.getData()) {
      outer: for (i = instances.size(); (--i) >= 0;) {
        want = instances.get(i);
        wantName = want.getName();
        for (final IInstanceRuns instanceRuns : experiment.getData()) {
          have = instanceRuns.getInstance();
          if (want == have) {
            continue outer;
          }
          if (EComparison.equals(have.getName(), wantName)) {
            continue outer;
          }
        }
        instances.remove(i);
      }
    }

    if (instances.size() >= origSize) {
      return data;
    }

    selection = new DataSelection(data);
    selection.addInstances(instances);

    return new ShadowExperimentSet<>(data, selection);
  }
}
