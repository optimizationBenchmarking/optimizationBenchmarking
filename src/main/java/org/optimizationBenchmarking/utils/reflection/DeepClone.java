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
import org.optimizationBenchmarking.utils.io.ByteArrayIOStream;
import org.optimizationBenchmarking.utils.tasks.Task;

/**
 * A task which tries to perform a deep cloning of a given object.
 * 
 * @param <T>
 *          the object type
 */
public final class DeepClone<T> extends Task<T> {

  /** the object instance to clone */
  private final T m_instance;

  /**
   * Deep clone the given object instance
   * 
   * @param instance
   *          the instance
   */
  public DeepClone(final T instance) {
    this.m_instance = instance;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final T call() throws IllegalArgumentException, OutOfMemoryError {
    final Class<T> clazz;
    final Method m;

    if (this.m_instance == null) {
      return null;
    }

    clazz = (Class<T>) (this.m_instance.getClass());
    if (clazz.isArray()) {
      // First, we check for arrays. Arrays can be cloned efficiently.

      if ((this.m_instance) instanceof int[]) {
        int[] array;
        array = ((int[]) (this.m_instance));
        if (array.length <= 0) {
          array = EmptyUtils.EMPTY_INTS;
        } else {
          array = array.clone();
        }
        return ((T) array);
      }
      if ((this.m_instance) instanceof double[]) {
        double[] array;
        array = ((double[]) (this.m_instance));
        if (array.length <= 0) {
          array = EmptyUtils.EMPTY_DOUBLES;
        } else {
          array = array.clone();
        }
        return ((T) array);
      }
      if ((this.m_instance) instanceof long[]) {
        long[] array;
        array = ((long[]) (this.m_instance));
        if (array.length <= 0) {
          array = EmptyUtils.EMPTY_LONGS;
        } else {
          array = array.clone();
        }
        return ((T) array);
      }
      if ((this.m_instance) instanceof boolean[]) {
        boolean[] array;
        array = ((boolean[]) (this.m_instance));
        if (array.length <= 0) {
          array = EmptyUtils.EMPTY_BOOLEANS;
        } else {
          array = array.clone();
        }
        return ((T) array);
      }
      if ((this.m_instance) instanceof char[]) {
        char[] array;
        array = ((char[]) (this.m_instance));
        if (array.length <= 0) {
          array = EmptyUtils.EMPTY_CHARS;
        } else {
          array = array.clone();
        }
        return ((T) array);
      }
      if ((this.m_instance) instanceof byte[]) {
        byte[] array;
        array = ((byte[]) (this.m_instance));
        if (array.length <= 0) {
          array = EmptyUtils.EMPTY_BYTES;
        } else {
          array = array.clone();
        }
        return ((T) array);
      }
      if ((this.m_instance) instanceof float[]) {
        float[] array;
        array = ((float[]) (this.m_instance));
        if (array.length <= 0) {
          array = EmptyUtils.EMPTY_FLOATS;
        } else {
          array = array.clone();
        }
        return ((T) array);
      }
      if ((this.m_instance) instanceof short[]) {
        short[] array;
        array = ((short[]) (this.m_instance));
        if (array.length <= 0) {
          array = EmptyUtils.EMPTY_SHORTS;
        } else {
          array = array.clone();
        }
        return ((T) array);
      }

      Object[] array;
      array = ((Object[]) (this.m_instance)).clone();
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
        (clazz == Pattern.class)) {
      return (this.m_instance);
    }

    // maybe we can clone the object directly?
    if ((this.m_instance) instanceof Cloneable) {
      try {// let's see if it has a public clone method
        m = clazz.getMethod("clone"); //$NON-NLS-1$
        if (m != null) {
          try {
            return ((T) (m.invoke(this.m_instance)));
          } catch (final OutOfMemoryError ome) {
            throw ome;
          } catch (final Throwable t) {
            throw new IllegalArgumentException(t);
          }
        }
      } catch (final NoSuchMethodException nsme) {
        // this may happend and would be OK
      }
    }

    // if the object is serializable, we can try to serialize and
    // deserialize
    // it
    if ((this.m_instance) instanceof Serializable) {

      try {
        try (final ByteArrayIOStream bos = new ByteArrayIOStream()) {
          try (final ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(this.m_instance);
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
    // bottom
    // of this method.

    throw new IllegalArgumentException("Object cannot be cloned."); //$NON-NLS-1$
  }
}
