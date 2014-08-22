package org.optimizationBenchmarking.utils.text.predicates;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.predicates.IPredicate;

/**
 * check whether a string is in a list
 * 
 * @param <T>
 *          the object type
 */
public class StringInList<T> extends HashObject implements IPredicate<T>,
    Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the string list */
  private final String[] m_list;

  /**
   * Create the predicate
   * 
   * @param list
   *          the string list
   */
  public StringInList(final String[] list) {
    super();
    this.m_list = this._getList(list.clone());
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    int hc;

    hc = 0;
    for (final String s : this.m_list) {
      hc = HashUtils.combineHashes(hc, HashUtils.hashCode(s));
    }
    return hc;
  }

  /**
   * Create the predicate
   * 
   * @param list
   *          the string list
   */
  public StringInList(final Collection<String> list) {
    super();
    this.m_list = this._getList(list.toArray(new String[list.size()]));
  }

  /**
   * get the list
   * 
   * @param list
   *          the input list
   * @return the list to use
   */
  String[] _getList(final String[] list) {
    Arrays.sort(list);
    return list;
  }

  /**
   * get the string value of an object
   * 
   * @param object
   *          the object
   * @return the string value
   */
  protected String getString(final T object) {
    return String.valueOf(object);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean check(final T object) {
    return (Arrays.binarySearch(this.m_list, this.getString(object)) >= 0);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof StringInList) {
      return Arrays.equals(this.m_list, ((StringInList<?>) o).m_list);
    }
    return false;
  }
}
