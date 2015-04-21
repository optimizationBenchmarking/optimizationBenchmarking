package org.optimizationBenchmarking.experimentation.data.impl.delegates;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractInstanceSet;
import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A subset of an instance set is basically a sub-set of another instance
 * set with a different owner, but delegates inquiries to that instance
 * set, where ever feasible.
 */
public class InstanceSetSubset extends AbstractInstanceSet {

  /** the original instance set */
  private final IInstanceSet m_orig;

  /** the data */
  private final ArrayListView<? extends IInstance> m_data;

  /** can we delegate attribute computation ? */
  private final boolean m_canDelegateAttributes;

  /**
   * create a subset of an instance set
   * 
   * @param orig
   *          the original instance set
   * @param copy
   *          the data to copy
   * @param owner
   *          the owner
   */
  public InstanceSetSubset(final IInstanceSet orig,
      final ArrayListView<? extends IInstance> copy,
      final IExperimentSet owner) {
    super(owner);

    final ArrayListView<? extends IInstance> origData;
    final IInstance[] instances;
    final int origSize, copySize;
    boolean canDelegate;
    int i;

    this.m_orig = InstanceSetSubset.__check(orig);

    origData = orig.getData();
    if (origData == null) {
      throw new IllegalArgumentException(//
          "No instance of IInstanceSet can have null data, but we encountered one such instance.");//$NON-NLS-1$
    }

    origSize = origData.size();
    if (origSize <= 0) {
      throw new IllegalArgumentException(//
          "No instance of IInstanceSet can have empty data, but we encountered one such instance.");//$NON-NLS-1$
    }

    if (copy == null) {
      this.m_data = origData;
      this.m_canDelegateAttributes = true;
    } else {

      copySize = copy.size();
      if (copySize <= 0) {
        throw new IllegalArgumentException(//
            "Cannot create empty instance set.");//$NON-NLS-1$
      }

      if (copySize > origSize) {
        throw new IllegalArgumentException(//
            "Cannot create InstanceSetSubset with more instance runs than the original instance set.");//$NON-NLS-1$
      }

      instances = new IInstance[copySize];
      i = 0;
      canDelegate = true;
      for (final IInstance instance : copy) {

        if (instance.getOwner() != orig) {
          throw new IllegalArgumentException(
              "Can only create InstanceSetSubset of instances which all stem from the same instance set."); //$NON-NLS-1$
        }

        canDelegate &= (instance == origData.get(i));

        instances[i] = instance;
      }

      if (canDelegate) {
        this.m_data = origData;
      } else {
        this.m_data = new ArrayListView<>(instances);
      }
      this.m_canDelegateAttributes = canDelegate;
    }
  }

  /**
   * create a delegate of an instance set
   * 
   * @param orig
   *          the original instance set
   * @param owner
   *          the owner
   */
  public InstanceSetSubset(final IInstanceSet orig,
      final IExperimentSet owner) {
    this(InstanceSetSubset.__check(orig), orig.getData(), owner);
  }

  /**
   * Check an experiment
   * 
   * @param orig
   *          the experiment
   * @return the experiment
   */
  private static final IInstanceSet __check(final IInstanceSet orig) {
    if (orig == null) {
      throw new IllegalArgumentException(//
          "IInstanceSet to delegate to must not be null."); //$NON-NLS-1$
    }
    return orig;
  }

  /** {@inheritDoc} */
  @Override
  public final ArrayListView<? extends IInstance> getData() {
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

  /** {@inheritDoc} */
  @Override
  public IInstance find(final String name) {
    if (this.m_canDelegateAttributes) {
      return this.m_orig.find(name);
    }
    return super.find(name);
  }
}
