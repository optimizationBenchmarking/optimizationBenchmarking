package org.optimizationBenchmarking.utils.config;

import java.util.Map;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A configuration definition.
 */
public final class Dump extends
    ArrayListView<Map.Entry<Parameter<?>, Object>> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;
  
  /** the empty dump */
  @SuppressWarnings("unchecked")
  public static final Dump EMPTY_DUMP = new Dump(new Map.Entry[0]);

  /**
   * Create
   * 
   * @param data
   *          the data
   */
  Dump(final Map.Entry<Parameter<?>, Object>[] data) {
    super(data);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o == this) || ((o instanceof Dump) && //
    (super.equals(o))));
  }
}
