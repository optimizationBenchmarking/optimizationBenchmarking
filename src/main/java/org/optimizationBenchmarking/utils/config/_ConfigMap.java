package org.optimizationBenchmarking.utils.config;

import java.util.ArrayList;
import java.util.Map;

import org.optimizationBenchmarking.utils.collections.ImmutableAssociation;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.maps.ObjectMapEntry;
import org.optimizationBenchmarking.utils.collections.maps.StringMapCI;

/**
 * A configuration map is a case-insensitive string map which allows
 * setting a type for an entry one time.
 */
final class _ConfigMap extends StringMapCI<Object> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create the configuration map */
  _ConfigMap() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final ObjectMapEntry<String, Object> createMapEntry() {
    return new _ConfigMapEntry();
  }

  /**
   * dump the configuration based on a definition
   *
   * @param definition
   *          the configuration definition
   * @return the dump
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  synchronized final ConfigurationDump _dump(
      final ConfigurationDefinition definition) {
    final StringMapCI<Object> map;
    final ArrayListView<Parameter<?>> params;
    final ImmutableAssociation<Parameter<?>, Object>[] known;
    final ArrayList<ImmutableAssociation<String, Object>> unknown;
    Parameter<?> param;
    int i;

    map = new StringMapCI<>();
    map.putAll(this);

    params = definition.getParameters();
    i = params.size();
    known = new ImmutableAssociation[i];
    unknown = new ArrayList<>(this.size());

    for (; (--i) >= 0;) {
      param = params.get(i);
      known[i] = new ImmutableAssociation(param, map.remove(param.m_name));
    }

    if (definition.allowsMore()) {
      for (final Map.Entry<String, Object> obj : map.entrySet()) {
        unknown.add(new ImmutableAssociation<>(obj.getKey(),//
            obj.getValue()));
      }
    }

    return new ConfigurationDump(new ArrayListView(known),//
        ArrayListView.collectionToView(unknown));
  }
}
