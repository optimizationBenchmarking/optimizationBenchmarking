package org.optimizationBenchmarking.utils.text.predicates;

import java.util.Arrays;
import java.util.Collection;

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
  public StringInListIgnoreCase(final String[] list) {
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

    for (i = list.length; (--i) >= 0;) {
      list[i] = list[i].toLowerCase();
    }
    Arrays.sort(list);

    return list;
  }

  /** {@inheritDoc} */
  @Override
  protected String getString(final T object) {
    return super.getString(object).toLowerCase();
  }
}
