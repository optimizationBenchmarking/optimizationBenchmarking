package org.optimizationBenchmarking.utils.parsers;

import java.io.Serializable;

/**
 * A parser for a given type
 *
 * @param <T>
 *          the type to be parsed
 */
public abstract class Parser<T> implements Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create the parser */
  protected Parser() {
    super();
  }

  /**
   * Get the output type class
   *
   * @return the output type class
   */
  public abstract Class<T> getOutputClass();

  /**
   * Parse the string
   *
   * @param string
   *          the string
   * @return the return type
   * @throws IllegalArgumentException
   *           if something fails
   */
  @SuppressWarnings("unchecked")
  public T parseString(final String string)
      throws IllegalArgumentException {
    final T ret;

    ret = ((T) string);
    this.validate(ret);
    return ret;
  }

  /**
   * Parse a given object
   *
   * @param o
   *          the object
   * @return the parse result
   * @throws IllegalArgumentException
   *           if something fails
   */
  public T parseObject(final Object o) throws IllegalArgumentException {
    final T ret;
    final Class<T> clazz;

    clazz = this.getOutputClass();
    if (clazz.isInstance(o)) {
      ret = clazz.cast(o);
    } else {
      return this.parseString(String.valueOf(o));
    }

    this.validate(ret);
    return ret;
  }

  /**
   * Validate the given object instance. This method should throw some
   * exception if {@code instance} is not OK for any reason. Any of the
   * parse methods implemented must pipe their results through this method.
   * Alternatively, this method must be overridden as final and delegate to
   * another method &mdash; then all parse methods must pipe their results
   * either through this other method or through this method here.
   *
   * @param instance
   *          the object instance
   * @throws IllegalArgumentException
   *           if something fails
   */
  public void validate(final T instance) throws IllegalArgumentException {
    //
  }
}
