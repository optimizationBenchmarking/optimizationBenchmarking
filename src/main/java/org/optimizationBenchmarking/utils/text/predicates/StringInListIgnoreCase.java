package org.optimizationBenchmarking.utils.text.predicates;

import java.util.Arrays;
import java.util.Collection;

import org.optimizationBenchmarking.utils.EmptyUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * check whether a string is in a list
 *
 * @param <T>
 *          the object type
 */
public class StringInListIgnoreCase<T> extends StringInList<T> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Create the predicate
   *
   * @param list
   *          the string list
   */
  public StringInListIgnoreCase(final String... list) {
    super(list);
  }

  /**
   * Create the predicate
   *
   * @param list
   *          the string list
   */
  public StringInListIgnoreCase(final Collection<String> list) {
    super(list);
  }

  /** {@inheritDoc} */
  @Override
  final String[] _getList(final String[] list) {
    int i;

    if ((list == null) || ((i = list.length) <= 0)) {
      return EmptyUtils.EMPTY_STRINGS;
    }

    for (; (--i) >= 0;) {
      list[i] = TextUtils.toLowerCase(list[i]);
    }
    Arrays.sort(list);

    return list;
  }

  /** {@inheritDoc} */
  @Override
  protected String getString(final T object) {
    return TextUtils.toLowerCase(super.getString(object));
  }
}
