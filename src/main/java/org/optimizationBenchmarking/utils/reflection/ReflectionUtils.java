package org.optimizationBenchmarking.utils.reflection;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.security.AccessController;
import java.util.regex.Pattern;

import org.optimizationBenchmarking.utils.EmptyUtils;
import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.io.ByteArrayIOStream;
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

  /**
   * Deep clone an object
   * 
   * @param instance
   *          the object
   * @return the deep-cloned object
   * @throws IllegalArgumentException
   *           if the object cannot be cloned
   * @throws OutOfMemoryError
   *           the we are out of memory
   */
  @SuppressWarnings("unchecked")
  public static final <T> T deepClone(final T instance)
      throws IllegalArgumentException, OutOfMemoryError {
    final Class<T> clazz;
    final Method m;

    if (instance == null) {
      return null;
    }

    clazz = (Class<T>) (instance.getClass());
    if (clazz.isArray()) {
      // First, we check for arrays. Arrays can be cloned efficiently.

      if ((instance) instanceof int[]) {
        int[] array;
        array = ((int[]) (instance));
        if (array.length <= 0) {
          array = EmptyUtils.EMPTY_INTS;
        } else {
          array = array.clone();
        }
        return ((T) array);
      }
      if ((instance) instanceof double[]) {
        double[] array;
        array = ((double[]) (instance));
        if (array.length <= 0) {
          array = EmptyUtils.EMPTY_DOUBLES;
        } else {
          array = array.clone();
        }
        return ((T) array);
      }
      if ((instance) instanceof long[]) {
        long[] array;
        array = ((long[]) (instance));
        if (array.length <= 0) {
          array = EmptyUtils.EMPTY_LONGS;
        } else {
          array = array.clone();
        }
        return ((T) array);
      }
      if ((instance) instanceof boolean[]) {
        boolean[] array;
        array = ((boolean[]) (instance));
        if (array.length <= 0) {
          array = EmptyUtils.EMPTY_BOOLEANS;
        } else {
          array = array.clone();
        }
        return ((T) array);
      }
      if ((instance) instanceof char[]) {
        char[] array;
        array = ((char[]) (instance));
        if (array.length <= 0) {
          array = EmptyUtils.EMPTY_CHARS;
        } else {
          array = array.clone();
        }
        return ((T) array);
      }
      if ((instance) instanceof byte[]) {
        byte[] array;
        array = ((byte[]) (instance));
        if (array.length <= 0) {
          array = EmptyUtils.EMPTY_BYTES;
        } else {
          array = array.clone();
        }
        return ((T) array);
      }
      if ((instance) instanceof float[]) {
        float[] array;
        array = ((float[]) (instance));
        if (array.length <= 0) {
          array = EmptyUtils.EMPTY_FLOATS;
        } else {
          array = array.clone();
        }
        return ((T) array);
      }
      if ((instance) instanceof short[]) {
        short[] array;
        array = ((short[]) (instance));
        if (array.length <= 0) {
          array = EmptyUtils.EMPTY_SHORTS;
        } else {
          array = array.clone();
        }
        return ((T) array);
      }

      Object[] array;
      array = ((Object[]) (instance)).clone();
      for (int i = array.length; (--i) >= 0;) {
        array[i] = ReflectionUtils.deepClone(array[i]);
      }
      return ((T) array);
    }

    // ok, we do not have an array - check for objects that are immutable
    if ((clazz == String.class) || //
        (clazz == URI.class) || //
        (clazz == URL.class) || //
        (clazz == Integer.class) || //
        (clazz == Double.class) || //
        (clazz == Long.class) || //
        (clazz == Boolean.class) || //
        (clazz == Character.class) || //
        (clazz == Byte.class) || //
        (clazz == Float.class) || //
        (clazz == Short.class) || //
        (clazz == Void.class) || //
        (clazz == BigDecimal.class) || //
        (clazz == BigInteger.class) || //
        (clazz == Pattern.class) || //
        (instance instanceof ArrayListView)) {
      return (instance);
    }

    // maybe we can clone the object directly?
    if ((instance) instanceof Cloneable) {
      try {// let's see if it has a public clone method
        m = clazz.getMethod("clone"); //$NON-NLS-1$
        if (m != null) {
          try {
            return ((T) (m.invoke(instance)));
          } catch (final OutOfMemoryError ome) {
            throw ome;
          } catch (final Throwable t) {
            throw new IllegalArgumentException(t);
          }
        }
      } catch (final NoSuchMethodException nsme) {
        // this may happen and would be OK
      }
    }

    // if the object is serializable, we can try to serialize and
    // deserialize it
    if ((instance) instanceof Serializable) {

      try {
        try (final ByteArrayIOStream bos = new ByteArrayIOStream()) {
          try (final ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(instance);
          }
          try (final ByteArrayInputStream ibs = bos.asInput()) {
            try (final ObjectInputStream ois = new ObjectInputStream(ibs)) {
              return ((T) (ois.readObject()));
            }
          }
        }
      } catch (final OutOfMemoryError ome) {
        throw ome;
      } catch (final Throwable t) {
        throw new IllegalArgumentException(t);
      }
    }

    // OK, if we get here, its finito: The object is not a primitive array
    // nor is it immutable. It is also not public cloneable and cannot be
    // serialized. This means we are out of "clean" and "official" ways to
    // copy the code. Thus, let's simply fail with an exception at the
    // bottom of this method.
    throw new IllegalArgumentException("Object cannot be cloned."); //$NON-NLS-1$
  }

  /**
   * Get a system property value: Try to obtain the value of property
   * {@code name} via {@link java.lang.System#getProperty(String)}. If that
   * fails, try to use a
   * {@link java.security.AccessController#doPrivileged(java.security.PrivilegedAction)
   * privileged action} to get the property.
   * 
   * @param name
   *          the property name
   * @return the property value
   */
  public static final String getSystemProperty(final String name) {
    if (name == null) {
      throw new IllegalArgumentException(//
          "System property name cannot be null."); //$NON-NLS-1$
    }
    try {
      return System.getProperty(name);
    } catch (final Throwable t) {
      return AccessController.doPrivileged(new _PropertyGetter(name));
    }
  }

  /**
   * Get an environment variable value: Try to obtain the value of
   * environment variable {@code name} via
   * {@link java.lang.System#getenv(String)}. If that fails, try to use a
   * {@link java.security.AccessController#doPrivileged(java.security.PrivilegedAction)
   * privileged action} to get the value.
   * 
   * @param name
   *          the property name
   * @return the property value
   */
  public static final String getSystemEnvironment(final String name) {
    if (name == null) {
      throw new IllegalArgumentException(//
          "Environment variable name cannot be null."); //$NON-NLS-1$
    }

    try {
      return System.getenv(name);
    } catch (final Throwable t) {
      return AccessController.doPrivileged(//
          new _EnvironmentGetter(name));
    }
  }

  /**
   * Check whether {@code subClass} is really a sub-class of
   * {@code baseClass} and throw an {@link java.lang.ClassCastException}
   * otherwise.
   * 
   * @param subClass
   *          the sub class
   * @param baseClass
   *          the base class
   * @throws ClassCastException
   *           if {@code subClass} is not a sub class of {@code baseClass}
   * @throws IllegalArgumentException
   *           if {@code subClass} or {@code baseClass} is {@code null}
   */
  public static final void validateSubClass(final Class<?> subClass,
      final Class<?> baseClass) throws ClassCastException {
    final String a, b;

    if (subClass == null) {
      throw new IllegalArgumentException("Subclass must not be null."); //$NON-NLS-1$
    }
    if (baseClass == null) {
      throw new IllegalArgumentException(//
          "Must provide valid base class, but provided null.");//$NON-NLS-1$
    }

    if (baseClass.isAssignableFrom(subClass)) {
      return;
    }

    a = TextUtils.className(subClass);
    b = TextUtils.className(baseClass);
    throw new ClassCastException((((((((//
        "Cannot assign an instance of " + //$NON-NLS-1$
        a) + " to a variable of type ") + //$NON-NLS-1$
        b) + ", i.e., ") + a) + //$NON-NLS-1$
        " is not a sub-class of ") + b) + '.'); //$NON-NLS-1$
  }

  /**
   * Find a class of the given {@code name} which must be a sub-class of
   * {@code base}. We first consider {@code name} as fully-qualified class
   * name. If no class of that name exists, we search in package
   * {@code java.lang}.
   * 
   * @param name
   *          the class name
   * @param base
   *          the base class
   * @param <C>
   *          the class type
   * @return the discovered class
   * @throws IllegalArgumentException
   *           if the class name {@code clazz} or {@code base} are invalid
   * @throws LinkageError
   *           if linkage fails
   * @throws ExceptionInInitializerError
   *           if class initialization fails
   * @throws ClassNotFoundException
   *           if the class was not found
   * @throws ClassCastException
   *           if the class is not of type {@code C}
   */
  @SuppressWarnings("unchecked")
  public static final <C> Class<? extends C> findClass(final String name,
      final Class<C> base) throws LinkageError,
      ExceptionInInitializerError, ClassNotFoundException,
      ClassCastException {
    Class<?> found;
    final String clazzName;

    if (base == null) {
      throw new IllegalArgumentException(//
          "Must provide proper base class for a class to discover, but provided null."); //$NON-NLS-1$
    }

    clazzName = TextUtils.prepare(name);
    if (clazzName == null) {
      throw new ClassNotFoundException(((//
          "Cannot get class with null or empty name '" + //$NON-NLS-1$
          name) + '\'') + '.');
    }

    try {
      found = Class.forName(clazzName);
    } catch (LinkageError | ClassCastException except) {
      throw except;
    } catch (final ClassNotFoundException except) {
      try {
        found = Class.forName("java.lang." + clazzName); //$NON-NLS-1$
      } catch (final Throwable t) {
        throw except;
      }
    }

    if (found == null) {
      throw new ClassNotFoundException(//
          ("Could not find class '" + clazzName)//$NON-NLS-1$
          + '\'');
    }

    ReflectionUtils.validateSubClass(found, base);
    return ((Class<C>) found);
  }

  /**
   * Get a the value of a static constant.
   * 
   * @param clazz
   *          the class which hosts the constant
   * @param name
   *          the constant's name
   * @param base
   *          the type of the constant
   * @return the static constant field's value
   * @param <T>
   *          the type of the constant
   * @throws NoSuchFieldException
   *           if the field was not found
   * @throws SecurityException
   *           if security does not allow checking, finding, or accessing
   *           the field
   * @throws IllegalArgumentException
   *           if the {@code clazz}, {@code base} or {@code name} are
   *           invalid
   * @throws IllegalAccessException
   *           if accessing the field was not permitted
   */
  public static final <T> T getStaticFieldValue(final Class<?> clazz,
      final String name, final Class<T> base) throws NoSuchFieldException,
      SecurityException, IllegalArgumentException, IllegalAccessException {
    final Object value;
    final String fieldName;
    final Field field;

    if (clazz == null) {
      throw new IllegalArgumentException(//
          "Class hosting a static constant cannot be null."); //$NON-NLS-1$
    }

    if (base == null) {
      throw new IllegalArgumentException(//
          "Must provide base class for getting a static constant, but provided null."); //$NON-NLS-1$
    }

    fieldName = TextUtils.prepare(name);
    if (fieldName == null) {
      throw new IllegalArgumentException(((//
          "Static constant name cannot be null or empty, but is '"//$NON-NLS-1$
          + name) + '\'') + '.');
    }

    if (fieldName.lastIndexOf('.') >= 0) {
      throw new IllegalArgumentException(//
          "Field name must not contain '.', but '" + //$NON-NLS-1$
              name + "' does.");//$NON-NLS-1$
    }

    field = clazz.getField(fieldName);
    if (field == null) {
      throw new NoSuchFieldException(//
          "getField returned null for '" + //$NON-NLS-1$
              name + "'!?");//$NON-NLS-1$
    }

    value = field.get(null);
    if (value == null) {
      return null;
    }

    ReflectionUtils.validateSubClass(value.getClass(), base);
    return base.cast(value);
  }

  /**
   * Get the value of a static constant by name
   * 
   * @param identifier
   *          the identifier, of form
   *          {@code packageA.packageB.className#constantName} or
   *          {@code packageA.packageB.className.constantName}
   * @param base
   *          the base class, i.e., the return type
   * @return the value of the constant
   * @param <T>
   *          the return type
   * @throws NoSuchFieldException
   *           if the field does not exist
   * @throws SecurityException
   *           if security does not allow to access the class or field
   * @throws IllegalArgumentException
   *           the the identifier or base class are invalid
   * @throws IllegalAccessException
   *           the security does not permit doing this
   * @throws LinkageError
   *           if there is a linkage error when accessing the class
   * @throws ExceptionInInitializerError
   *           if there is an error when loading the class
   * @throws ClassNotFoundException
   *           if the class does not exist
   * @throws ClassCastException
   *           if the class hierarchy or return type do not match
   */
  public static final <T> T getStaticFieldValueByName(
      final String identifier, final Class<T> base)
      throws NoSuchFieldException, SecurityException,
      IllegalArgumentException, IllegalAccessException, LinkageError,
      ExceptionInInitializerError, ClassNotFoundException,
      ClassCastException {
    Class<? extends Object> host;
    String idString;
    int index;

    idString = TextUtils.prepare(identifier);
    if (idString == null) {
      throw new IllegalArgumentException(//
          "Class+constant identifier must not be null or empty, but is '" + //$NON-NLS-1$
              identifier + '\'');
    }

    index = idString.lastIndexOf('#');
    if (index < 0) {
      index = idString.lastIndexOf('.');
    }
    if ((index <= 0) || (index >= (idString.length() - 1))) {
      throw new IllegalArgumentException(
          "Class+constant identifier '" + idString //$NON-NLS-1$
              + "' is invalid");//$NON-NLS-1$
    }

    host = ReflectionUtils.findClass(idString.substring(0, index),
        Object.class);
    if (host == null) {
      throw new ClassNotFoundException(//
          "findClass returned null for '" + //$NON-NLS-1$
              identifier + "'!?");//$NON-NLS-1$
    }
    return ReflectionUtils.getStaticFieldValue(host,
        idString.substring(index + 1), base);
  }

  /** the forbidden constructor */
  private ReflectionUtils() {
    ErrorUtils.doNotCall();
  }

}
