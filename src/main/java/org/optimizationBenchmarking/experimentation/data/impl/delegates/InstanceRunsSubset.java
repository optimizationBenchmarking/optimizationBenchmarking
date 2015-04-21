package org.optimizationBenchmarking.experimentation.data.impl.delegates;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A subset of a instance run set is basically a sub-set of another
 * instance run set with a different owner, but delegates inquiries to that
 * instance run set, where ever feasible.
 */
public class InstanceRunsSubset extends AbstractInstanceRuns {

  /** the original instance run set */
  private final IInstanceRuns m_orig;

  /** the data */
  private final ArrayListView<DelegatingRun> m_data;

  /** can we delegate attribute computation ? */
  private final boolean m_canDelegateAttributes;

  /**
   * create a subset of an instance run set
   * 
   * @param orig
   *          the original instance runs
   * @param copy
   *          the data to copy
   * @param owner
   *          the owner
   */
  public InstanceRunsSubset(final IInstanceRuns orig,
      final ArrayListView<? extends IRun> copy, final IExperiment owner) {
    super(owner);

    final ArrayListView<? extends IRun> origData, copyData;
    final DelegatingRun[] runs;
    final int origSize, copySize;
    boolean canDelegate;
    int i;

    this.m_orig = InstanceRunsSubset.__check(orig);

    origData = orig.getData();
    if (origData == null) {
      throw new IllegalArgumentException(//
          "No instance of IInstanceRuns can have null data, but we encountered one such instance for instance "//$NON-NLS-1$
              + orig.getInstance());
    }

    copyData = ((copy == null) ? origData : copy);

    origSize = origData.size();
    if (origSize <= 0) {
      throw new IllegalArgumentException(//
          "No instance of IInstanceRuns can have empty data, but we encountered one such instance for instance "//$NON-NLS-1$
              + orig.getInstance());
    }

    copySize = copyData.size();
    if (copySize <= 0) {
      throw new IllegalArgumentException(//
          "Cannot create empty instance run set for instance "//$NON-NLS-1$
              + orig.getInstance());
    }

    if (copySize > origSize) {
      throw new IllegalArgumentException(//
          "Cannot create InstanceRunsSubset with more runs than the original set for instance "//$NON-NLS-1$
              + orig.getInstance());
    }

    runs = new DelegatingRun[copySize];
    i = 0;
    canDelegate = true;
    for (final IRun run : copyData) {

      if (run.getOwner() != orig) {
        throw new IllegalArgumentException(
            "Can only create InstanceRunsSubset of runs which all stem from the same instance run set for instance" //$NON-NLS-1$
                + orig.getInstance());
      }

      canDelegate &= (run == origData.get(i));

      runs[i] = new DelegatingRun(run, this);
    }

    this.m_data = new ArrayListView<>(runs);
    this.m_canDelegateAttributes = canDelegate;
  }

  /**
   * create a delegate of an instance run set
   * 
   * @param orig
   *          the original instance runs
   * @param owner
   *          the owner
   */
  public InstanceRunsSubset(final IInstanceRuns orig,
      final IExperiment owner) {
    this(InstanceRunsSubset.__check(orig), orig.getData(), owner);
  }

  /**
   * Check an instance run set
   * 
   * @param orig
   *          the instance run set
   * @return the instance run set
   */
  private static final IInstanceRuns __check(final IInstanceRuns orig) {
    if (orig == null) {
      throw new IllegalArgumentException(//
          "IInstanceRuns to delegate to must not be null."); //$NON-NLS-1$
    }
    return orig;
  }

  /** {@inheritDoc} */
  @Override
  public final IInstance getInstance() {
    return this.m_orig.getInstance();
  }

  /** {@inheritDoc} */
  @Override
  public final ArrayListView<DelegatingRun> getData() {
    return this.m_data;
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
