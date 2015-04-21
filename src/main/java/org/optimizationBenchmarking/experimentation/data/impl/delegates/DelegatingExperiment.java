package org.optimizationBenchmarking.experimentation.data.impl.delegates;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSetting;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * A delegating experiment is basically a shadow of another experiment with
 * a different owner, but delegates attribute-based computations to that
 * run set.
 */
public class DelegatingExperiment extends AbstractExperiment {

  /** the original experiment */
  private final IExperiment m_orig;

  /** the data */
  private final ArraySetView<DelegatingInstanceRuns> m_data;

  /**
   * create a delegating experiment
   * 
   * @param orig
   *          the original experiment
   * @param owner
   *          the owner
   */
  public DelegatingExperiment(final IExperiment orig,
      final IExperimentSet owner) {
    super(owner);

    final ArraySetView<? extends IInstanceRuns> origData;
    final DelegatingInstanceRuns[] instanceRuns;
    final int size;
    int i;

    if (orig == null) {
      throw new IllegalArgumentException(//
          "IExperiment to delegate to must not be null."); //$NON-NLS-1$
    }

    origData = orig.getData();
    if (origData == null) {
      throw new IllegalArgumentException(//
          "No instance of IExperiment can have null data, but we encountered "//$NON-NLS-1$
              + orig);
    }

    size = origData.size();
    if (size <= 0) {
      throw new IllegalArgumentException(//
          "No instance of IExperiment can have empty data, but we encountered "//$NON-NLS-1$
              + orig);
    }

    instanceRuns = new DelegatingInstanceRuns[size];
    i = 0;
    for (final IInstanceRuns run : origData) {
      instanceRuns[i] = new DelegatingInstanceRuns(run, this);
    }

    this.m_data = new ArraySetView<>(instanceRuns);
    this.m_orig = orig;
  }

  /** {@inheritDoc} */
  @Override
  public final ArraySetView<DelegatingInstanceRuns> getData() {
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
    return DataElement.delegateGetAttribute(((XDT) (this.m_orig)),
        attribute);
  }
}
