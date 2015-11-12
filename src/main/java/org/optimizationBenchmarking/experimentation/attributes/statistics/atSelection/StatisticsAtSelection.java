package org.optimizationBenchmarking.experimentation.attributes.statistics.atSelection;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterValue;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;
import org.optimizationBenchmarking.utils.math.statistics.parameters.Median;
import org.optimizationBenchmarking.utils.math.statistics.parameters.StatisticalParameter;

/**
 * A statistic parameter is computed at a specified selection of an
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns}
 * set. For other objects, it is aggregated. This attribute works for
 * <ul>
 * <li>
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns}
 * </li>
 * <li>
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IExperiment}
 * </li>
 * <li>
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet}
 * </li>
 * <li>
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstance}
 * </li>
 * <li>
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet}
 * </li>
 * <li>
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue}
 * </li>
 * <li>
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IParameterValue}
 * </li>
 * </ul>
 */
public class StatisticsAtSelection
    extends Attribute<IDataElement, Number> {

  /** the instance-runs based statistics */
  private final InstanceRunsStatisticsAtSelection m_runs;

  /**
   * how to aggregate the parameter, say over
   * {@link org.optimizationBenchmarking.experimentation.data.spec.IExperiment}
   * s or
   * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstance}
   * s.
   */
  private final StatisticalParameter m_aggregate;

  /**
   * Create a statistics computer for a given selection criterion
   *
   * @param selection
   *          the selection criterion
   * @param dimension
   *          the dimension over which to compute the statistics
   * @param parameter
   *          the parameter to compute per
   *          {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns}
   * @param aggregate
   *          how to aggregate the parameter, say over
   *          {@link org.optimizationBenchmarking.experimentation.data.spec.IExperiment}
   *          s or
   *          {@link org.optimizationBenchmarking.experimentation.data.spec.IInstance}
   *          s, or {@code null} for default (
   *          {@linkplain org.optimizationBenchmarking.utils.math.statistics.parameters.Median
   *          median}).
   */
  public StatisticsAtSelection(final SelectionCriterion selection,
      final IDimension dimension, final StatisticalParameter parameter,
      final StatisticalParameter aggregate) {
    super(EAttributeType.TEMPORARILY_STORED);

    this.m_runs = new InstanceRunsStatisticsAtSelection(selection,
        dimension, parameter);
    this.m_aggregate = ((aggregate == null) ? Median.INSTANCE : aggregate);
  }

  /** {@inheritDoc} */
  @Override
  protected final Number compute(final IDataElement data,
      final Logger logger) {
    final ScalarAggregate agg;
    final IInstance instance;
    final IFeatureValue featureValue;
    final IParameterValue parameterValue;

    if (data instanceof IInstanceRuns) {
      return this.m_runs.get(((IInstanceRuns) data), logger);
    }

    finder: {
      if (data instanceof IInstance) {
        agg = this.m_aggregate.createSampleAggregate();
        instance = ((IInstance) data);
        exps: for (final IExperiment experiment : instance.getOwner()
            .getOwner().getData()) {
          for (final IInstanceRuns runs : experiment.getData()) {
            if (EComparison.equals(runs.getInstance(), instance)) {
              agg.append(this.get(runs, logger));
              continue exps;
            }
          }
        }
        return agg.toNumber();
      }

      if (data instanceof IFeatureValue) {
        agg = this.m_aggregate.createSampleAggregate();
        featureValue = ((IFeatureValue) data);
        for (final IInstance finstance : featureValue.getOwner().getOwner()
            .getOwner().getInstances().getData()) {
          if (finstance.getFeatureSetting().contains(featureValue)) {
            agg.append(this.compute(finstance, logger));
          }
        }
        return agg.toNumber();
      }

      if (data instanceof IParameterValue) {
        agg = this.m_aggregate.createSampleAggregate();
        parameterValue = ((IParameterValue) data);
        for (final IExperiment exp : parameterValue.getOwner().getOwner()
            .getOwner().getData()) {
          if (exp.getParameterSetting().contains(parameterValue)) {
            agg.append(this.compute(exp, logger));
          }
        }
        return agg.toNumber();
      }

      if (data instanceof IElementSet) {
        agg = this.m_aggregate.createSampleAggregate();
        for (final Object element : ((IElementSet) data).getData()) {
          if (element instanceof IDataElement) {
            agg.append(this.compute(((IDataElement) element), logger));
          } else {
            break finder;
          }
        }
        return agg.toNumber();
      }
    }

    throw new IllegalArgumentException("Class " + //$NON-NLS-1$
        data.getClass().getSimpleName() + //
        " not supported by attribute " + //$NON-NLS-1$
        this.getClass().getSimpleName());
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.hashCode(this.m_aggregate), //
        HashUtils.hashCode(this.m_runs));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final StatisticsAtSelection r;
    if (o == this) {
      return true;
    }
    if (o instanceof StatisticsAtSelection) {
      r = ((StatisticsAtSelection) o);
      return (EComparison.equals(r.m_aggregate, this.m_aggregate) && //
          EComparison.equals(r.m_runs, this.m_runs));
    }
    return false;
  }
}
