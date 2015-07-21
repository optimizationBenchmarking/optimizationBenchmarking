package org.optimizationBenchmarking.experimentation.attributes.statistics.selection;

import org.optimizationBenchmarking.experimentation.data.spec.IDataPoint;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/** The end-of-run data point selection criterion. */
public final class EndOfRun extends SelectionCriterion {

  /**
   * The globally shared instance of the end-of-run data point selection
   * criterion.
   */
  public static final EndOfRun INSTANCE = new EndOfRun();

  /** create */
  private EndOfRun() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final IDataPoint get(final IRun run) {
    final ArrayListView<? extends IDataPoint> data;
    data = run.getData();
    return data.get(data.size() - 1);
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return EndOfRun.class.hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o == this) || (o instanceof EndOfRun));
  }
}
