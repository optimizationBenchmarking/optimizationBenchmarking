package org.optimizationBenchmarking.utils.document.template;

import java.util.Map;

import org.optimizationBenchmarking.utils.document.spec.IDocumentElement;

/**
 * A callback function which can be passed to the properties map of a
 * document handler and then be invoked from inside the template.
 *
 * @param <T>
 *          the element type
 */
public interface IDocumentCallback<T extends IDocumentElement> {

  /**
   * Process the given element
   *
   * @param element
   *          the element
   * @param properties
   *          a modifiable map of properties
   */
  public abstract void callback(final T element,
      final Map<Object, Object> properties);

  /**
   * Get the required element type to be passed to the callback routine
   *
   * @return the required element type to be passed to the callback routine
   */
  public abstract Class<T> getElementClass();

}
