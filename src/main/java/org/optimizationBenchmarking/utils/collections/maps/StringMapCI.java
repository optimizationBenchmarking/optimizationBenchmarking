package org.optimizationBenchmarking.utils.collections.maps;

/**
 * A case-insensitive hash map that uses {@link java.lang.String}s as keys.
 * All keys will be converted to lower case. Since the case of key strings
 * is ignored, this implementation breaks with some of the contracts of
 * {@link java.util.Map}. For instance, this map may return {@code true}
 * when queried whether it {@link java.util.Map#equals(Object) equals}
 * another map, although it may have a different
 * {@link java.util.Map#hashCode() hash code}.
 *
 * @param <ET>
 *          the element type
 */
public class StringMapCI<ET> extends StringMap<ET> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create the hash map */
  public StringMapCI() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final String _prepare(final String s) {
    final String t;
    t = super._prepare(s);
    if (t != null) {
      return t.toLowerCase();
    }
    return null;
  }
}
