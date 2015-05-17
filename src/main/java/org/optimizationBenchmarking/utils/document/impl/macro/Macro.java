package org.optimizationBenchmarking.utils.document.impl.macro;

import java.lang.reflect.InvocationTargetException;

import org.optimizationBenchmarking.utils.document.spec.IDocumentElement;

/**
 * A macro is a recorded set of method invocations.
 *
 * @param <T>
 *          the document element type
 */
public final class Macro<T extends IDocumentElement> {

  /** the recorded method invocations */
  private final _Invocation[] m_invocations;

  /** the maximum number of objects involved */
  private final int m_objectCount;

  /**
   * create
   *
   * @param invocations
   *          the recorded method invocations
   * @param objectCount
   *          the maximum number of objects involved
   */
  Macro(final _Invocation[] invocations, final int objectCount) {
    super();
    if (invocations == null) {
      throw new IllegalArgumentException(//
          "Method invocations cannot be null.");//$NON-NLS-1$
    }
    if (objectCount <= 0) {
      throw new IllegalArgumentException(//
          "Object count must be at least one, but is "//$NON-NLS-1$
          + objectCount);
    }
    this.m_invocations = invocations;
    this.m_objectCount = objectCount;
  }

  /**
   * Play the macro onto a given document element
   *
   * @param dest
   *          the document element
   */
  public final void play(final T dest) {
    final Object[] objects;
    Object ret;

    objects = new Object[this.m_objectCount];
    for (final _Invocation invoke : this.m_invocations) {
      try {
        ret = invoke.m_method.invoke(objects[invoke.m_object],
            invoke.m_params);
      } catch (IllegalAccessException | IllegalArgumentException
          | InvocationTargetException error) {
        throw new IllegalStateException(invoke.m_method + //
            " created an error when invoked reflectively inside a macro.",//$NON-NLS-1$
            error);
      }
      if (invoke.m_retObject >= 0) {
        if (ret == null) {
          throw new IllegalStateException(invoke.m_method + //
              " returned null, but should not have.");//$NON-NLS-1$
        }
        objects[invoke.m_retObject] = ret;
      }
    }
  }

  /**
   * Record a macro for a given element type
   *
   * @param clazz
   *          the class to record for
   * @param consumer
   *          the macro consumer
   * @return the instance of the document element whose interactions with
   *         are recorded
   * @param <E>
   *          the element type
   */
  public static final <E extends IDocumentElement> E record(
      final Class<E> clazz, final IMacroConsumer<E> consumer) {
    return new _MacroRecorder<>(clazz, consumer)._getProxy();
  }

}
