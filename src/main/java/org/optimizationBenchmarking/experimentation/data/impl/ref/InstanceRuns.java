package org.optimizationBenchmarking.experimentation.data.impl.ref;

import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;

/**
 * <p>
 * A set of runs that all have been obtained with the same algorithm
 * configuration for the same benchmarking instance.
 * </p>
 */
public final class InstanceRuns extends _IDObjectSet<Run> implements
IInstanceRuns {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the instance */
  final Instance m_inst;

  /**
   * instantiate
   *
   * @param inst
   *          the instance
   * @param data
   *          the data of the set
   * @param clone
   *          should we clone the data?
   * @param sort
   *          should we sort the data?
   * @param own
   *          should mark the elements as owned by this object?
   */
  InstanceRuns(final Instance inst, final Run[] data, final boolean clone,
      final boolean sort, final boolean own) {
    super(data, clone, sort, own);

    if (inst == null) {
      throw new IllegalArgumentException("Instance must not be null."); //$NON-NLS-1$
    }
    this.m_inst = inst;
  }

  /** {@inheritDoc} */
  @Override
  public final Instance getInstance() {
    return this.m_inst;
  }

  /** {@inheritDoc} */
  @Override
  final int _compareTo(final _IDObject o) {
    final Instance ins;
    final int i;

    check: {
      if (o instanceof InstanceRuns) {
        ins = ((InstanceRuns) o).m_inst;
      } else {
        if (o instanceof Instance) {
          ins = ((Instance) o);
        } else {
          break check;
        }
      }

      i = this.m_inst.compareTo(ins);
      if (i != 0) {
        return i;
      }
    }
    return super._compareTo(o);
  }

  /** {@inheritDoc} */
  @Override
  public final Experiment getOwner() {
    return ((Experiment) (this.m_owner));
  }
}
