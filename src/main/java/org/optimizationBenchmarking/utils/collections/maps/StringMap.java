package org.optimizationBenchmarking.utils.collections.maps;

import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A hash map that uses {@link java.lang.String}s as keys.
 *
 * @param <ET>
 *          the element type
 */
public class StringMap<ET> extends
    ObjectMap<String, ET, ObjectMapEntry<String, ET>> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create the hash map */
  public StringMap() {
    super();
  }

  /**
   * Prepare a string for usage in the map
   *
   * @param s
   *          the string
   * @return the prepared one
   */
  String _prepare(final String s) {
    return TextUtils.prepare(s);
  }

  /** {@inheritDoc} */
  @Override
  public ObjectMapEntry<String, ET> getEntry(final String key,
      final boolean create) {
    return super.getEntry(this._prepare(key), create);
  }
}
