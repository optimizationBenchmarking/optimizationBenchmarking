package org.optimizationBenchmarking.experimentation.data;

/**
 * <p>
 * A collection of data experimental data.
 * </p>
 * 
 * @param <OT>
 *          the owner type
 * @param <DT>
 *          the type
 */
public class DataSet<OT, DT extends _IDObject<? extends _IDObjectSet<?, ?>>>
    extends _IDObjectSet<OT, DT> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * instantiate
   * 
   * @param data
   *          the data of the set
   * @param clone
   *          should we clone the data?
   * @param sort
   *          should we sort the data?
   * @param own
   *          should mark the elements as owned by this object?
   */
  DataSet(final DT[] data, final boolean clone, final boolean sort,
      final boolean own) {
    super(data, clone, sort, own);
  }
}
