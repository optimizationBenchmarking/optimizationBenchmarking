package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.collections.ImmutableAssociation;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A configuration definition.
 */
public final class Definition extends ArrayListView<Parameter<?>> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Create
   *
   * @param data
   *          the data
   */
  Definition(final Parameter<?>[] data) {
    super(data);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o == this) || ((o instanceof Definition) && //
    (super.equals(o))));
  }

  /**
   * Create the configuration dump
   *
   * @param config
   *          the configuration, or {@code null} if none is provided
   * @return the dump
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public final Dump dump(final Configuration config) {
    final ImmutableAssociation<Parameter<?>, Object>[] known;
    final _ConfigMap map;
    Parameter<?> param;
    int i;

    i = this.m_data.length;

    if (i <= 0) {
      return Dump.EMPTY_DUMP;
    }

    known = new ImmutableAssociation[i];
    if (config != null) {
      map = config.m_data;
      for (; (--i) >= 0;) {
        param = this.m_data[i];
        known[i] = new ImmutableAssociation(param,//
            param._formatForDump(map.get(param.m_name)));
      }
    } else {
      for (; (--i) >= 0;) {
        known[i] = new ImmutableAssociation(this.m_data[i], null);
      }
    }

    return new Dump(known);
  }
}
