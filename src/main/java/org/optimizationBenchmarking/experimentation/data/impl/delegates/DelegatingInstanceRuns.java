package org.optimizationBenchmarking.experimentation.data.impl.delegates;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * A delegating instance run set is basically a shadow of another instance
 * run set with a different owner, but delegates attribute-based
 * computations to that run set.
 */
public class DelegatingInstanceRuns extends AbstractInstanceRuns {

  /** the original instance run set */
  private final IInstanceRuns m_orig;

  /** the data */
  private final ArraySetView<DelegatingRun> m_data;

  /**
   * create a delegating instance runs
   * 
   * @param orig
   *          the original instance runs
   * @param owner
   *          the owner
   */
  public DelegatingInstanceRuns(final IInstanceRuns orig,
      final IExperiment owner) {
    super(owner);

    final ArraySetView<? extends IRun> origData;
    final DelegatingRun[] runs;
    final int size;
    int i;

    if (orig == null) {
      throw new IllegalArgumentException(//
          "IInstanceRuns to delegate to must not be null."); //$NON-NLS-1$
    }

    origData = orig.getData();
    if (origData == null) {
      throw new IllegalArgumentException(//
          "No instance of IInstanceRuns can have null data, but we encountered one such instance for instance "//$NON-NLS-1$
              + orig.getInstance());
    }

    size = origData.size();
    if (size <= 0) {
      throw new IllegalArgumentException(//
          "No instance of IInstanceRuns can have empty data, but we encountered one such instance for instance "//$NON-NLS-1$
              + orig.getInstance());
    }

    runs = new DelegatingRun[size];
    i = 0;
    for (final IRun run : origData) {
      runs[i] = new DelegatingRun(run, this);
    }

    this.m_data = new ArraySetView<>(runs);
    this.m_orig = orig;
  }

  /** {@inheritDoc} */
  @Override
  public final IInstance getInstance() {
    return this.m_orig.getInstance();
  }

  /** {@inheritDoc} */
  @Override
  public final ArraySetView<DelegatingRun> getData() {
    return this.m_data;
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
