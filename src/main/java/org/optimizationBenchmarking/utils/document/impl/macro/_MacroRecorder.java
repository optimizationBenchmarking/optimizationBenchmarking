package org.optimizationBenchmarking.utils.document.impl.macro;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

import org.optimizationBenchmarking.utils.document.spec.IDocumentElement;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A macro recorder.
 *
 * @param <T>
 *          the document element type
 */
final class _MacroRecorder<T extends IDocumentElement> implements
InvocationHandler {

  /** the list of generated objects */
  private ArrayList<Object> m_objects;

  /** the invocations */
  private ArrayList<_Invocation> m_invocations;

  /** the macro consumer */
  private IMacroConsumer<T> m_consumer;

  /**
   * create the recorder
   *
   * @param clazz
   *          the class to record for
   * @param consumer
   *          the macro consumer
   */
  _MacroRecorder(final Class<T> clazz, final IMacroConsumer<T> consumer) {
    super();
    if (clazz == null) {
      throw new IllegalArgumentException(//
          "Class to record macro for cannot be null.");//$NON-NLS-1$
    }
    if (consumer == null) {
      throw new IllegalArgumentException(//
          "Cannot create macro with null consumer for class "//$NON-NLS-1$
          + clazz);
    }
    this.m_consumer = consumer;
    this.m_invocations = new ArrayList<>();
    this.m_objects = new ArrayList<>();
    this.__proxy(clazz);
  }

  /**
   * Proxy a given interface
   *
   * @param clazz
   *          the class
   * @return the proxy
   * @param <E>
   *          the proxy type
   */
  @SuppressWarnings("unchecked")
  private synchronized final <E> E __proxy(final Class<E> clazz) {
    final E proxy;

    proxy = ((E) (Proxy.newProxyInstance(clazz.getClassLoader(),
        new Class[] { clazz }, this)));
    if (proxy == null) {
      throw new IllegalStateException(//
          "Generated proxy must not be null, but is for class "//$NON-NLS-1$
          + clazz);
    }
    this.m_objects.add(proxy);

    return proxy;
  }

  /**
   * The macro recording has finished, let's consume the macro.
   */
  private final void __consume() {
    final int objectCount;
    ArrayList<_Invocation> list;
    IMacroConsumer<T> consumer;

    list = this.m_invocations;
    consumer = this.m_consumer;
    objectCount = this.m_objects.size();
    this.m_invocations = null;
    this.m_consumer = null;
    this.m_objects = null;

    consumer.consume(new Macro<T>(
        list.toArray(new _Invocation[list.size()]), objectCount));
  }

  /**
   * Get the proxy object
   *
   * @return the proxy object
   */
  @SuppressWarnings("unchecked")
  synchronized final T _getProxy() {
    return ((T) (this.m_objects.get(0)));
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Object invoke(final Object proxy,
      final Method method, final Object[] args) throws Throwable {
    final Object retVal;
    final Class<?> retClazz;
    int index, retIndex;

    if (this.m_objects == null) {
      throw new IllegalStateException(//
          "Macro recording has already finished since 'close' was invoked on the root object. You now cannot call methods anymore, especially not "//$NON-NLS-1$
          + method);
    }

    index = this.m_objects.indexOf(proxy);
    if (index < 0) {
      throw new IllegalStateException(//
          "Cannot invoke method " + method + //$NON-NLS-1$
          " on an unknown proxy object.");//$NON-NLS-1$
    }

    retClazz = method.getReturnType();
    retIndex = (-1);
    if ((retClazz == null) || (retClazz == Void.class)) {
      retVal = null;

      if ((index == 0) && ("close".equals(method.getName()))) { //$NON-NLS-1$
        this.__consume();
        return null;
      }

    } else {
      if (IDocumentElement.class.isAssignableFrom(retClazz)) {
        retVal = this.__proxy(retClazz);
        retIndex = this.m_objects.indexOf(retVal);
        if (retIndex < 0) {
          throw new IllegalStateException(//
              "New proxy object lost?");//$NON-NLS-1$
        }
      } else {
        if (ITextOutput.class.isAssignableFrom(retClazz)) {
          if (ITextOutput.class.isInstance(proxy)) {
            retVal = proxy;
            retIndex = index;
          } else {
            retVal = this.__proxy(retClazz);
          }
        } else {
          if (ILabel.class.isAssignableFrom(retClazz)) {
            retVal = null;
          } else {
            // TODO: BibliographyBuilder
            // TODO: Graphic

            throw new UnsupportedOperationException(//
                "Cannot create proxy for class " + retClazz); //$NON-NLS-1$

          }
        }
      }
    }

    this.m_invocations.add(new _Invocation(index, method,//
        ReflectionUtils.deepClone(args), retIndex));
    return retVal;
  }
}
