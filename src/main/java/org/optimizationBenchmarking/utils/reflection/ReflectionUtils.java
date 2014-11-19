package org.optimizationBenchmarking.utils.reflection;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * Some utilities for reflection access.
 */
public class ReflectionUtils {

  /**
   * Make sure that a class is loaded. This method can be used as some kind
   * of static way to check whether some classes are present in the class
   * path. If it throws a {@link java.lang.ClassNotFoundException}, the
   * class is not present, which could mean that a certain jar or other
   * dependency is missing.
   * 
   * @param clazz
   *          the class to be loaded
   * @throws ClassNotFoundException
   *           if the class could not be loaded
   */
  public static final void ensureClassIsLoaded(final String clazz)
      throws ClassNotFoundException {
    String a, b;

    try {
      Class.forName(clazz);
    } catch (final Throwable t) {
      a = t.getMessage();
      b = ((clazz != null) ? clazz : TextUtils.NULL_STRING);
      if ((!(t instanceof ClassNotFoundException))
          || ((a == null) || (!(a.contains(b))))) {
        throw new ClassNotFoundException(("Could not load " + b), t); //$NON-NLS-1$
      }
      throw ((ClassNotFoundException) t);
    }
  }

  /**
   * Ensure that a set of classes is loaded. This method can be used as
   * some kind of static way to check whether some classes are present in
   * the class path. If it throws a
   * {@link java.lang.ClassNotFoundException}, the class is not present,
   * which could mean that a certain jar or other dependency is missing.
   * 
   * @param classes
   *          the classes
   * @throws ClassNotFoundException
   *           if at least one of the class could not be loaded
   */
  public static final void ensureClassesAreLoaded(final String... classes)
      throws ClassNotFoundException {
    if (classes != null) {
      for (final String clazz : classes) {
        ReflectionUtils.ensureClassIsLoaded(clazz);
      }
    }
  }

  /** the forbidden constructor */
  private ReflectionUtils() {
    ErrorUtils.doNotCall();
  }

}
