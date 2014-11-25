package org.optimizationBenchmarking.utils.reflection;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
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
        array[i] = new DeepClone<>(array[i]).call();
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

  /** the forbidden constructor */
  private ReflectionUtils() {
    ErrorUtils.doNotCall();
  }

}
