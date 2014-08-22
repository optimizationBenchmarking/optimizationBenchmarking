package org.optimizationBenchmarking.utils.hierarchy;

import java.util.HashMap;

import org.optimizationBenchmarking.utils.EmptyUtils;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A finite state machine capable of normalizing objects. This makes sense
 * if you are creating an API to build a complex data structure composed of
 * many objects which could potentially be equal. Then, a normalization
 * process can ensure that all equal components are replaced by the same
 * instance.
 */
public class NormalizingFSM extends HierarchicalFSM {

  /**
   * Create
   * 
   * @param owner
   *          the owning fsm
   */
  protected NormalizingFSM(final HierarchicalFSM owner) {
    super(owner);
  }

  /**
   * Normalize a given object instance without querying a potential owning
   * {@link NormalizingFSM} for its opinion.
   * 
   * @param input
   *          the input object instance
   * @param flags
   *          the flags
   * @param resolved
   *          the set of already resolved objects
   * @return the normalized instance
   * @param <T>
   *          the instance class
   */
  @SuppressWarnings({ "unchecked" })
  private final <T> T __normalize(final T input, final int flags,
      final HashMap<Object, Object> resolved) {
    final Class<?> clazz;
    Object key;
    HashMap<Object, Object> useResolved;
    char ch;
    short s;
    int i, restartAt;
    long l;
    Object[] objs;
    Object x, y;
    T result;

    if (input == null) {
      return null;
    }

    result = input;
    useResolved = resolved;

    inner: if ((flags & 1) != 0) {
      // normalize the input object without side effects

      if (useResolved != null) {
        key = HashUtils.hashKey(input);
        y = useResolved.get(key);
        if (y != null) {
          result = ((T) y);
          break inner;
        }
      } else {
        key = null;
      }

      // check strings: we normalize all character strings
      if (input instanceof String) {
        result = ((T) (TextUtils.normalize((String) input)));
        break inner;
      }

      if (input instanceof Number) {
        if (input instanceof Byte) {
          result = ((T) (Byte.valueOf(((Byte) input).byteValue())));
          break inner;
        }

        if (input instanceof Short) {
          s = ((Short) input).shortValue();
          result = ((T) (((s >= (-128)) && (s <= 127)) ? Short.valueOf(s)
              : input));
          break inner;
        }
        if (input instanceof Integer) {
          i = ((Integer) input).intValue();
          result = ((T) (((i >= (-128)) && (i <= 127)) ? Integer
              .valueOf(i) : input));
          break inner;
        }
        if (input instanceof Long) {
          l = ((Long) input).longValue();
          result = ((T) (((l >= (-128)) && (l <= 127)) ? Long.valueOf(l)
              : input));
          break inner;
        }

        // check for wrapped primitive types
        if (input instanceof Boolean) {
          result = ((T) (Boolean
              .valueOf((((Boolean) input).booleanValue()))));
          break inner;
        }
        if (input instanceof Character) {
          ch = ((Character) input).charValue();
          result = ((T) ((ch <= 127) ? Character.valueOf(ch) : input));
          break inner;
        }
      } else {

        clazz = input.getClass();
        if (clazz.isArray()) {

          // check for an object[] array, maybe nested arrays
          if (input instanceof Object[]) {
            objs = ((Object[]) input);

            // first let's check for empty arrays
            if ((i = objs.length) <= 0) {
              if (objs.getClass() == Object[].class) {
                result = ((T) (EmptyUtils.EMPTY_OBJECTS));
              } else {
                if (objs.getClass() == String[].class) {
                  result = ((T) (EmptyUtils.EMPTY_STRINGS));
                }
              }
              break inner;
            }

            // the array is not empty, so we need to check for complex,
            // nested arrays
            if (useResolved == null) {
              useResolved = new HashMap<>();
              key = HashUtils.hashKey(input);
            }

            y = null;
            useResolved.put(key, objs);

            // optimistic object resolution
            looper: for (;;) {
              restartAt = (-1);

              for (; (--i) >= 0;) {
                x = objs[i];
                if (x == input) {
                  y = objs;
                  if (y == x) {
                    restartAt = Math.max(restartAt, i);
                  }
                } else {
                  y = this.__normalize(x, flags, useResolved);
                }

                if (y != x) {
                  if (objs == input) {
                    objs = objs.clone();
                    useResolved.put(key, objs);
                  }
                  objs[i] = y;
                  if (restartAt >= 0) {
                    i = (restartAt + 1);
                    continue looper;
                  }
                }
              }
              break looper;
            }

            result = ((T) (objs));
            break inner;
          }

          // no object array, so maybe it is a primitive array
          if (input instanceof byte[]) {
            if (((byte[]) input).length <= 0) {
              result = ((T) (EmptyUtils.EMPTY_BYTES));
            }
            break inner;
          }

          if (input instanceof short[]) {
            if (((short[]) input).length <= 0) {
              result = ((T) (EmptyUtils.EMPTY_SHORTS));
            }
            break inner;
          }

          if (input instanceof int[]) {
            if (((int[]) input).length <= 0) {
              result = ((T) (EmptyUtils.EMPTY_INTS));
            }
            break inner;
          }

          if (input instanceof long[]) {
            if (((long[]) input).length <= 0) {
              result = ((T) (EmptyUtils.EMPTY_LONGS));
            }
            break inner;
          }

          if (input instanceof float[]) {
            if (((float[]) input).length <= 0) {
              result = ((T) (EmptyUtils.EMPTY_FLOATS));
            }
            break inner;
          }

          if (input instanceof double[]) {
            if (((double[]) input).length <= 0) {
              result = ((T) (EmptyUtils.EMPTY_DOUBLES));
            }
            break inner;
          }

          if (input instanceof boolean[]) {
            if (((boolean[]) input).length <= 0) {
              result = ((T) (EmptyUtils.EMPTY_BOOLEANS));
            }
            break inner;
          }

          if (input instanceof char[]) {
            if (((char[]) input).length <= 0) {
              result = ((T) (EmptyUtils.EMPTY_CHARS));
            }
            break inner;
          }
        } else {
          // no array - so we have complex objects
          if (input instanceof CharSequence) {
            // character sequences are turned into strings
            return ((T) (this.__normalize(input.toString(), flags,
                useResolved)));
          }
        }
      }
    }

    // This was the local resolution of objects as far as possible. Now we
    // need to see if we can propagate the normalization process upwards
    // through the document tree.

    if ((flags & 2) == 0) {
      return result;
    }

    if (this.m_owner instanceof NormalizingFSM) {
      return ((NormalizingFSM) (this.m_owner)).__normalize(input, 2,
          useResolved);
    }

    return this.doNormalizePersistently(result);
  }

  /**
   * Normalize a given object instance and propagate the normalization
   * process to the top of the hierarchical FSM tree. This may have
   * side-effects: The root instance of the normalizing FSM chain may
   * maintain, e.g., a {@link java.util.HashMap} to ensure that all
   * {@link java.lang.Object#equals(Object) equal} objects are mapped to
   * the same object instance.
   * 
   * @param input
   *          the input object instance
   * @return the normalized instance
   * @param <T>
   *          the instance class
   */
  protected <T> T normalize(final T input) {
    return this.__normalize(input, (-1), null);
  }

  /**
   * Normalize a given object instance, but do not preserve any
   * normalization information for future calls. This method will result in
   * an object which is as canonical as possible without creating
   * additional object references.
   * 
   * @param input
   *          the input object to be normalized
   * @return the normalization result
   */
  protected <T> T normalizeLocal(final T input) {
    return this.__normalize(input, 1, null);
  }

  /**
   * This method is called when the normalization process has reached this
   * object and this object is the root of the a hierarchy of
   * {@link NormalizingFSM}s. This method may be implemented in a way that
   * uses measures such as, e.g., a hash map to make normalized objects
   * &quot;persistent&quot; and to always return the exactly same object
   * when called with equal input instances. The current implementation
   * here just returns the {@code input} object directly.
   * 
   * @param input
   *          the input object
   * @return the result
   */
  protected <T> T doNormalizePersistently(final T input) {
    return input;
  }
}
