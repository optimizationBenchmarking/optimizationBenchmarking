package org.optimizationBenchmarking.utils.reflection;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.optimizationBenchmarking.utils.EmptyUtils;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.io.ByteArrayIOStream;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * Some utilities for reflection access.
 */
public final class ReflectionUtils {

  /** the default name of the singleton constant: {@value} */
  private static final String DEFAULT_SINGLETON_CONSTANT_NAME = "INSTANCE"; //$NON-NLS-1$

  /** the default name of the singleton instance getter method: {@value} */
  private static final String DEFAULT_SINGLETON_GETTER_NAME = "getInstance"; //$NON-NLS-1$

  /**
   * the modifiers required to access a field or method from reflection: *
   * * {@value}
   */
  private static final int REQUIRED_MODIFIERS_FOR_ACCESS = (Modifier.PUBLIC
      | Modifier.FINAL | Modifier.STATIC);

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
        throw new ClassNotFoundException(("Could not load class '"//$NON-NLS-1$ 
            + b + '\''), t);
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
    final String clazzName;
    Class<?> found;

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
   * Throw an {@link java.lang.IllegalArgumentException}
   * 
   * @param base
   *          the base class
   * @param container
   *          the container class
   * @param name
   *          the provided name string
   * @param reasonString
   *          the reason string
   * @param reasonException
   *          the causing exception
   * @return the exception
   */
  private static final ReflectiveOperationException __makeFindInstanceError(
      final Class<?> base, final Class<?> container, final String name,
      final String reasonString, final Throwable reasonException) {
    final String string;
    MemoryTextOutput text;

    text = new MemoryTextOutput();
    text.append("Cannot find an instance of the base class "); //$NON-NLS-1$
    if (base != null) {
      text.append(TextUtils.className(base));
    } else {
      text.append(TextUtils.NULL_STRING);
    }
    text.append(" within the container class "); //$NON-NLS-1$
    if (container != null) {
      text.append(TextUtils.className(container));
    } else {
      text.append(TextUtils.NULL_STRING);
    }
    if (name != null) {
      text.append(" under name '");//$NON-NLS-1$
      text.append(name);
      text.append('\'');
    }

    if (reasonString != null) {
      text.append(" because "); //$NON-NLS-1$
      text.append(reasonString);
    }
    text.append('.');

    string = text.toString();
    text = null;
    if (reasonException != null) {
      return new ReflectiveOperationException(string, reasonException);
    }
    return new ReflectiveOperationException(string);
  }

  /**
   * Check whether a class {@code base} is assignable by the class
   * {@code result}.
   * 
   * @param base
   *          the class to assign to
   * @param result
   *          the class to assign from
   * @return {@code true} if variables of type {@code base} are assignable
   *         from {@code result}
   */
  private static final boolean __isAssignable(final Class<?> base,
      final Class<?> result) {
    final EPrimitiveType primitive;
    if (base.isAssignableFrom(result)) {
      return true;
    }
    if (result.isPrimitive()) {
      primitive = EPrimitiveType.getPrimitiveType(result);
      if (primitive != null) {
        return base.isAssignableFrom(primitive.getWrapperClass());
      }
    }
    return false;
  }

  /**
   * return a value from a field
   * 
   * @param base
   *          the base class
   * @param container
   *          the container class
   * @param name
   *          the field name
   * @param field
   *          the field
   * @return the value
   * @throws IllegalArgumentException
   *           the the argument is illegal
   * @throws ReflectiveOperationException
   *           if the operation fails
   */
  private static final <T> T __field(final Class<T> base,
      final Class<?> container, final String name, final Field field)
      throws IllegalArgumentException, ReflectiveOperationException {
    final int mod;
    final Class<?> retClass;

    mod = field.getModifiers();
    if ((mod & ReflectionUtils.REQUIRED_MODIFIERS_FOR_ACCESS) != ReflectionUtils.REQUIRED_MODIFIERS_FOR_ACCESS) {
      throw ReflectionUtils.__makeFindInstanceError(base,
          container,
          name,
          ("field " + field + " is not " + //$NON-NLS-1$//$NON-NLS-2$                    
          Modifier.toString(ReflectionUtils.REQUIRED_MODIFIERS_FOR_ACCESS
              & (~mod))), null);
    }

    retClass = field.getType();
    if (!(ReflectionUtils.__isAssignable(base, retClass))) {
      throw ReflectionUtils.__makeFindInstanceError(base, container, name,
          ("field " + field + //$NON-NLS-1$
              " of type " + TextUtils.className(retClass) + //$NON-NLS-1$
          " is incompatible to base class"), null);//$NON-NLS-1$
    }

    return base.cast(field.get(null));
  }

  /**
   * return a value from a getter method
   * 
   * @param base
   *          the base class
   * @param container
   *          the container class
   * @param name
   *          the getter method name
   * @param method
   *          the method
   * @return the value
   * @throws IllegalArgumentException
   *           the the argument is illegal
   * @throws ReflectiveOperationException
   *           if the operation fails
   */
  private static final <T> T __method(final Class<T> base,
      final Class<?> container, final String name, final Method method)
      throws IllegalArgumentException, ReflectiveOperationException {
    final int mod;
    final Class<?> retClass;
    final Class<?>[] params;

    mod = method.getModifiers();
    if ((mod & ReflectionUtils.REQUIRED_MODIFIERS_FOR_ACCESS) != ReflectionUtils.REQUIRED_MODIFIERS_FOR_ACCESS) {
      throw ReflectionUtils.__makeFindInstanceError(base,
          container,
          name,
          ("method " + method + " is not " + //$NON-NLS-1$//$NON-NLS-2$                    
          Modifier.toString(ReflectionUtils.REQUIRED_MODIFIERS_FOR_ACCESS
              & (~mod))), null);
    }

    retClass = method.getReturnType();
    if (!(ReflectionUtils.__isAssignable(base, retClass))) {
      throw ReflectionUtils.__makeFindInstanceError(base, container, name,
          ("return type " + TextUtils.className(retClass) + //$NON-NLS-1$
              " of method " + method + //$NON-NLS-1$           
          " is incompatible to base class"), null);//$NON-NLS-1$
    }

    params = method.getParameterTypes();
    if ((params != null) && (params.length != 0)) {
      throw ReflectionUtils.__makeFindInstanceError(base, container, name,
          ("method " + method + //$NON-NLS-1$           
              " has more than 0 parameters: " + //$NON-NLS-1$           
          Arrays.toString(params)), null);
    }

    return base.cast(method.invoke(null));
  }

  /**
   * return a value from a constructor
   * 
   * @param base
   *          the base class
   * @param container
   *          the container class
   * @param name
   *          the getter method name
   * @param constructor
   *          the constructor
   * @return the value
   * @throws IllegalArgumentException
   *           the the argument is illegal
   * @throws ReflectiveOperationException
   *           if the operation fails
   */
  private static final <T> T __constructor(final Class<T> base,
      final Class<?> container, final String name,
      final Constructor<?> constructor) throws IllegalArgumentException,
      ReflectiveOperationException {
    final int mod;
    final Class<?> retClass;
    final Class<?>[] params;

    mod = constructor.getModifiers();
    if ((mod & Modifier.PUBLIC) != Modifier.PUBLIC) {
      throw ReflectionUtils.__makeFindInstanceError(base, container, name,
          ("constructor " + constructor + " is not " + //$NON-NLS-1$//$NON-NLS-2$                    
          Modifier.toString(Modifier.PUBLIC)), null);
    }

    retClass = constructor.getDeclaringClass();
    if (!(base.isAssignableFrom(retClass))) {
      throw ReflectionUtils.__makeFindInstanceError(base, container, name,
          ("return type " + TextUtils.className(retClass) + //$NON-NLS-1$
              " of constructor " + constructor + //$NON-NLS-1$           
          " is incompatible to base class"), null);//$NON-NLS-1$
    }

    params = constructor.getParameterTypes();
    if ((params != null) && (params.length != 0)) {
      throw ReflectionUtils.__makeFindInstanceError(base, container, name,
          ("constructor " + constructor + //$NON-NLS-1$           
              " has more than 0 parameters: " + //$NON-NLS-1$           
          Arrays.toString(params)), null);
    }

    return base.cast(constructor.newInstance());
  }

  /**
   * <p>
   * Obtain an instance of a given {@code base} class hosted within a
   * {@code container} class in a best effort approach. Optionally, a
   * {@code name} for a {@code public static final} constant field or
   * getter method may be provided. This method proceeds as follows:
   * </p>
   * <ol>
   * <li>If {@code name} is neither {@code null} nor an empty string nor
   * only consists just of white space, we store a
   * {@link java.lang.String#trim() trimmed} version of this name in the
   * local variable {@code useName}. for
   * <ol>
   * <li>A field with name {@code useName} is looked for. If the field is
   * not {@code public static final} or has a wrong type, we fail with an
   * exception. We access the field and return its value.</li>
   * <li>A method with name {@code useName} is looked for. If the method is
   * not parameterless and {@code public static final} or has a wrong type,
   * we fail with an exception. We invoke the method and return its value.</li>
   * <li>If {@code base} is an {@link java.lang.Enum} and
   * {@code container==base}, then we try to find if there is any instance
   * of {@code base} whose {@link java.lang.Enum#name() name} or
   * {@link java.lang.Enum#toString()} equals to {@code useName} while
   * {@link java.lang.String#equalsIgnoreCase(String) ignoring the case}.
   * If so, that instance is returned.</li>
   * <li>If {@code useName} is the same as the class name of
   * {@code container} and {@code base} is assignable from
   * {@code container}, then we check if {@code container} has a
   * parameterless public constructor. If so, we invoke it and return its
   * result.</li>
   * <li>If neither exists, we fail with an exception.</li>
   * </ol>
   * <li>We check if a {@code public static final} field with the name
   * {@value #DEFAULT_SINGLETON_CONSTANT_NAME} and an appropriate return
   * type exists. If so, we access the field and return its value.</li>
   * <li>We check if a parameterless {@code public static final} method
   * with the name {@value #DEFAULT_SINGLETON_GETTER_NAME} and an
   * appropriate type exists. If so, we invoke the method and return its
   * return value.</li>
   * <li>We check if {@code base} is assignable from {@code container} and
   * {@code container} has a parameterless {@code public} constructor. If
   * so, we invoke the constructor and return the new instance.</li>
   * <li>If {@code base} is assignable from {@code container}, then we
   * check if {@code container} has a parameterless public constructor. If
   * so, we invoke it and return its result.</li>
   * <li>We fail with an error.</li>
   * </ol>
   * 
   * @param base
   *          the base class
   * @param container
   *          the container
   * @param name
   *          the name
   * @return the instance
   * @param <T>
   *          the expected return type
   * @throws ReflectiveOperationException
   *           if we fail to obtain the instance
   */
  @SuppressWarnings("rawtypes")
  public static final <T> T getInstance(final Class<T> base,
      final Class<?> container, final String name)
      throws ReflectiveOperationException {
    final Class<?> useContainer;
    String useName;
    Throwable errorA, errorB, errorC;
    Field field;
    Method method;
    Constructor<?> constructor;
    ReflectiveOperationException bottom;
    T[] constants;

    if (base == null) {
      throw ReflectionUtils.__makeFindInstanceError(base, container, name,
          "base class cannot be null", null); //$NON-NLS-1$
    }

    if (container == null) {
      if (base.isEnum()) {
        useContainer = base;
      } else {
        throw ReflectionUtils.__makeFindInstanceError(base, container,
            name, "container class cannot be null", null); //$NON-NLS-1$
      }
    } else {
      useContainer = container;
    }

    if (useContainer.isPrimitive()) {
      throw ReflectionUtils.__makeFindInstanceError(base, useContainer,
          name, "container class cannot be a primitive type", null); //$NON-NLS-1$
    }
    if (useContainer.isArray()) {
      throw ReflectionUtils.__makeFindInstanceError(base, useContainer,
          name, "base class cannot be an array type", null); //$NON-NLS-1$
    }
    if (base.isAnnotation()) {
      throw ReflectionUtils.__makeFindInstanceError(base, useContainer,
          name, "base class cannot be an annotation type", null); //$NON-NLS-1$
    }
    if (useContainer.isAnnotation()) {
      throw ReflectionUtils.__makeFindInstanceError(base, useContainer,
          name, "container class cannot be an annotation type", null); //$NON-NLS-1$
    }

    errorA = errorB = errorC = null;
    useName = null;

    checkNamed: {
      if (name != null) {
        if (name.indexOf('.') >= 0) {
          throw ReflectionUtils.__makeFindInstanceError(base,
              useContainer, name, "name must not contain a '.'", null); //$NON-NLS-1$
        }
        useName = TextUtils.prepare(name);
        if (useName != null) {
          try {
            field = container.getField(useName);
          } catch (final Throwable t) {
            field = null;
            errorA = t;
          }

          if (field != null) {
            return ReflectionUtils.__field(base, useContainer, useName,
                field);
          }

          try {
            method = container.getMethod(useName);
          } catch (final Throwable t) {
            method = null;
            errorB = t;
          }

          if (method != null) {
            return ReflectionUtils.__method(base, useContainer, useName,
                method);
          }

          if (base.isEnum()
              && ((base == container) || base.isAssignableFrom(container))) {
            constants = base.getEnumConstants();
            if (constants != null) {
              for (final T constx : constants) {
                if (useName.equalsIgnoreCase(((Enum) constx).name())) {
                  return constx;
                }
              }
              for (final T constx : constants) {
                if (useName.equalsIgnoreCase(constx.toString())) {
                  return constx;
                }
              }
            }
          }

          break checkNamed;
        }
      }

      try {
        field = container
            .getField(ReflectionUtils.DEFAULT_SINGLETON_CONSTANT_NAME);
      } catch (final Throwable t) {
        field = null;
        errorA = t;
      }

      if (field != null) {
        return ReflectionUtils.__field(base, useContainer, useName, field);
      }

      try {
        method = container
            .getMethod(ReflectionUtils.DEFAULT_SINGLETON_GETTER_NAME);
      } catch (final Throwable t) {
        method = null;
        errorB = t;
      }

      if (method != null) {
        return ReflectionUtils.__method(base, useContainer, useName,
            method);
      }
    }

    if (!(base.isEnum())) {
      if (base.isAssignableFrom(useContainer)) {
        if ((useName == null)
            || (useContainer.getSimpleName().equals(useName))) {
          try {
            constructor = container.getConstructor();
          } catch (final Throwable t) {
            constructor = null;
            errorC = t;
          }

          if (constructor != null) {
            return ReflectionUtils.__constructor(base, useContainer,
                useName, constructor);
          }
        }
      }
    }

    bottom = ReflectionUtils
        .__makeFindInstanceError(
            base,
            useContainer,
            useName,
            "no appropriate public static final field, parameterless public static final method, or public constructor found to get an instance of base class"//$NON-NLS-1$
            , null);
    if (errorA != null) {
      bottom.addSuppressed(errorA);
    }
    if (errorB != null) {
      bottom.addSuppressed(errorB);
    }
    if (errorC != null) {
      bottom.addSuppressed(errorC);
    }
    throw bottom;
  }

  /**
   * Get an instance by a identifier string
   * 
   * @param identifier
   *          the identifier, of form
   *          {@code packageA.packageB.className#constantName}
   * @param base
   *          the base class, i.e., the return type
   * @return the value of the constant
   * @param <T>
   *          the return type
   * @throws ReflectiveOperationException
   *           if the reflective operation fails
   */
  public static final <T> T getInstanceByName(final Class<T> base,
      final String identifier) throws ReflectiveOperationException {
    final String idString, className, fieldName;
    Class<? extends Object> container;
    Throwable cause;
    final int index;

    idString = TextUtils.prepare(identifier);
    if (idString == null) {
      throw new IllegalArgumentException(//
          "Class+constant identifier must not be null or empty, but is '" + //$NON-NLS-1$
              identifier + '\'');
    }

    index = idString.lastIndexOf('#');
    if ((index <= 0) || (index >= (idString.length() - 1))) {
      className = idString;
      fieldName = null;
    } else {
      className = idString.substring(0, index);
      fieldName = idString.substring(index + 1);
    }

    cause = null;
    try {
      container = ReflectionUtils.findClass(className, Object.class);
    } catch (final Throwable t) {
      cause = t;
      container = null;
    }
    if (container == null) {
      throw ReflectionUtils.__makeFindInstanceError(base, null, fieldName,
          ("could not discover class '" + className//$NON-NLS-1$
              + "' based on string '" + idString + '\''),//$NON-NLS-1$
          cause);
    }

    return ReflectionUtils.getInstance(base, container, fieldName);
  }

  /** the forbidden constructor */
  private ReflectionUtils() {
    ErrorUtils.doNotCall();
  }

}
