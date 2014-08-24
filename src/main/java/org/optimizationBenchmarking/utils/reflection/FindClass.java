package org.optimizationBenchmarking.utils.reflection;

import org.optimizationBenchmarking.utils.tasks.Task;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * Obtain a class.
 *
 * @param <C>
 *          the base class type
 */
public final class FindClass<C> extends Task<Class<C>> {

  /** the name of the class */
  private final String m_name;

  /** the base class */
  private final Class<C> m_base;

  /**
   * Create the get-class action
   *
   * @param name
   *          the class name
   */
  public FindClass(final String name) {
    this(name, null);
  }

  /**
   * Create the get-class action
   *
   * @param name
   *          the class name
   * @param base
   *          the base class
   */
  public FindClass(final String name, final Class<C> base) {
    super();
    this.m_name = name;
    this.m_base = base;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final Class<C> call() throws LinkageError,
      ExceptionInInitializerError, ClassNotFoundException,
      ClassCastException {
    Class<?> c;
    final String n;

    n = TextUtils.prepare(this.m_name);
    if (n == null) {
      throw new ClassNotFoundException("Cannot get class with null name."); //$NON-NLS-1$
    }

    try {
      c = Class.forName(n);
    } catch (LinkageError |
    /* ExceptionInInitializerError| */ClassNotFoundException
        | ClassCastException except) {
      try {
        c = Class.forName("java.lang." + n); //$NON-NLS-1$
      } catch (final Throwable t) {
        throw except;
      }
    }

    if (c != null) {
      FindClass.validateSubClass(c, this.m_base);
      return ((Class<C>) c);
    }

    throw new ClassNotFoundException(n);
  }

  /**
   * Check whether {@code subClass} is really a sub-class of
   * {@code baseClass} and throw an {@link java.lang.ClassCastException}
   * otherwise
   *
   * @param subClass
   *          the sub class
   * @param baseClass
   *          the base class, if {@code null}, we check against
   *          {@code Object.class}
   * @throws ClassCastException
   *           if {@code subClass} is not a sub class of {@code baseClass}
   * @throws ClassNotFoundException
   *           if {@code subClass} is {@code null}
   */
  public static final void validateSubClass(final Class<?> subClass,
      final Class<?> baseClass) throws ClassCastException,
      ClassNotFoundException {
    final Class<?> c;
    final String a, b;

    if (subClass == null) {
      throw new ClassNotFoundException("Subclass must not be null."); //$NON-NLS-1$
    }

    c = ((baseClass != null) ? baseClass : Object.class);

    if (c.isAssignableFrom(subClass)) {
      return;
    }

    a = TextUtils.className(subClass);
    b = TextUtils.className(c);
    throw new ClassCastException("Cannot assign an instance of " + //$NON-NLS-1$
        a + " to a variable of type " + //$NON-NLS-1$
        b + ", i.e., " + a + //$NON-NLS-1$
        " is not a sub-class of " + b + '.'); //$NON-NLS-1$
  }

}
