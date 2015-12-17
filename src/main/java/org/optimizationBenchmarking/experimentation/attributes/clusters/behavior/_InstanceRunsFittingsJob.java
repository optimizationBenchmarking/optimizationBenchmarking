package org.optimizationBenchmarking.experimentation.attributes.clusters.behavior;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.attributes.modeling.DimensionRelationshipAndData;
import org.optimizationBenchmarking.experimentation.attributes.modeling.DimensionRelationshipData;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.utils.parallel.Execute;

/** Compute all the fittings for a given instance runs set. */
final class _InstanceRunsFittingsJob
    implements Callable<DimensionRelationshipData[]> {

  /** the runs */
  private final IInstanceRuns m_runs;

  /** the attributes to process */
  private DimensionRelationshipAndData[] m_attrs;

  /** the logger */
  private Logger m_logger;

  /**
   * create
   *
   * @param runs
   *          the runs
   * @param attrs
   *          the attributes to process
   * @param logger
   *          the logger
   */
  _InstanceRunsFittingsJob(final IInstanceRuns runs,
      final DimensionRelationshipAndData[] attrs, final Logger logger) {
    super();
    this.m_runs = runs;
    this.m_attrs = attrs;
    this.m_logger = logger;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final DimensionRelationshipData[] call() {
    final Future<DimensionRelationshipData>[] futures;
    final DimensionRelationshipData[] fittings;
    int i;

    futures = new Future[this.m_attrs.length];
    i = (-1);
    for (final DimensionRelationshipAndData attrs : this.m_attrs) {
      futures[++i] = Execute.parallel(//
          attrs.getter(this.m_runs, this.m_logger));
    }
    this.m_attrs = null;
    this.m_logger = null;

    fittings = new DimensionRelationshipData[futures.length];
    Execute.join(futures, fittings, 0, false);

    return fittings;
  }
}
