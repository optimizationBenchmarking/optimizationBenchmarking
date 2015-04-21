package org.optimizationBenchmarking.experimentation.data.impl.delegates;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSetting;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A subset of a experiment is basically a sub-set of another experiment
 * with a different owner, but delegates inquiries to that experiment,
 * where ever feasible.
 */
public class ExperimentSubset extends AbstractExperiment {

  /** the original experiment */
  private final IExperiment m_orig;

  /** the data */
  private final ArrayListView<InstanceRunsSubset> m_data;

  /** can we delegate attribute computation ? */
  private final boolean m_canDelegateAttributes;

  /**
   * create a subset of an experiment
   * 
   * @param orig
   *          the original experiment
   * @param copy
   *          the data to copy
   * @param owner
   *          the owner
   */
  public ExperimentSubset(final IExperiment orig,
      final ArrayListView<? extends IInstanceRuns> copy,
      final IExperimentSet owner) {
    super(owner);

    final ArrayListView<? extends IInstanceRuns> origData, copyData;
    final InstanceRunsSubset[] instanceRunss;
    final int origSize, copySize;
    boolean canDelegate;
    int i;

    this.m_orig = ExperimentSubset.__check(orig);

    origData = orig.getData();
    if (origData == null) {
      throw new IllegalArgumentException(//
          "No instance of IExperiment can have null data, but we encountered one such instance.");//$NON-NLS-1$
    }

    copyData = ((copy == null) ? origData : copy);

    origSize = origData.size();
    if (origSize <= 0) {
      throw new IllegalArgumentException(//
          "No instance of IExperiment can have empty data, but we encountered one such instance.");//$NON-NLS-1$
    }

    copySize = copyData.size();
    if (copySize <= 0) {
      throw new IllegalArgumentException(//
          "Cannot create empty experiment.");//$NON-NLS-1$
    }

    if (copySize > origSize) {
      throw new IllegalArgumentException(//
          "Cannot create ExperimentSubset with more instance runs than the original experiment.");//$NON-NLS-1$
    }

    instanceRunss = new InstanceRunsSubset[copySize];
    i = 0;
    canDelegate = true;
    for (final IInstanceRuns instanceRuns : copyData) {

      if (instanceRuns.getOwner() != orig) {
        throw new IllegalArgumentException(
            "Can only create ExperimentSubset of instance runs which all stem from the same experiment."); //$NON-NLS-1$
      }

      canDelegate &= (instanceRuns == origData.get(i));

      instanceRunss[i] = new InstanceRunsSubset(instanceRuns, this);
    }

    this.m_data = new ArrayListView<>(instanceRunss);
    this.m_canDelegateAttributes = canDelegate;
  }

  /**
   * create a delegate of an experiment
   * 
   * @param orig
   *          the original experiment
   * @param owner
   *          the owner
   */
  public ExperimentSubset(final IExperiment orig,
      final IExperimentSet owner) {
    this(ExperimentSubset.__check(orig), orig.getData(), owner);
  }

  /**
   * Check an experiment
   * 
   * @param orig
   *          the experiment
   * @return the experiment
   */
  private static final IExperiment __check(final IExperiment orig) {
    if (orig == null) {
      throw new IllegalArgumentException(//
          "IExperiment to delegate to must not be null."); //$NON-NLS-1$
    }
    return orig;
  }

  /** {@inheritDoc} */
  @Override
  public final ArrayListView<InstanceRunsSubset> getData() {
    return this.m_data;
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return this.m_orig.getName();
  }

  /** {@inheritDoc} */
  @Override
  public final String getDescription() {
    return this.m_orig.getDescription();
  }

  /** {@inheritDoc} */
  @Override
  public final IParameterSetting getParameterSetting() {
    return this.m_orig.getParameterSetting();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  protected final <XDT extends IDataElement, RT> RT getAttribute(
      final Attribute<XDT, RT> attribute) {
    if (this.m_canDelegateAttributes) {
      return DataElement.delegateGetAttribute(((XDT) (this.m_orig)),
          attribute);
    }
    return super.getAttribute(attribute);
  }
}
