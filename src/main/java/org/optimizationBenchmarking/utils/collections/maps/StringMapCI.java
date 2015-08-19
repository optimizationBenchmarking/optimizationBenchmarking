package org.optimizationBenchmarking.utils.collections.maps;

import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * <p>
 * A case-insensitive hash map that uses {@link java.lang.String}s as keys.
 * All keys will be converted to lower case. Since the case of key strings
 * is ignored, this implementation breaks with some of the contracts of
 * {@link java.util.Map}. For instance, this map may return {@code true}
 * when queried whether it {@link java.util.Map#equals(Object) equals}
 * another map, although it may have a different
 * {@link java.util.Map#hashCode() hash code}.
 * </p>
 * <p>
 * A more elegant method to do this would probably be the one discussed at
 * http://stackoverflow.com/posts/22336599. However, that would break with
 * some functionality depending on this class, such as the works of
 * {@link org.optimizationBenchmarking.utils.config.Configuration}. Thus,
 * in the future, I will need to find a way to do that better. So don't
 * rely on the lower-casing of strings.
 * </p>
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
      return TextUtils.toLowerCase(t);
    }
    return null;
  }
}
