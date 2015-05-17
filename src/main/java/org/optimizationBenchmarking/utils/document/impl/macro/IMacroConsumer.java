package org.optimizationBenchmarking.utils.document.impl.macro;

import org.optimizationBenchmarking.utils.document.spec.IDocumentElement;

/**
 * The macro consumer.
 *
 * @param <T>
 *          the document element type
 */
public interface IMacroConsumer<T extends IDocumentElement> {

  /**
   * A macro has been recorded
   *
   * @param macro
   *          the recorded macro
   */
  public abstract void consume(final Macro<T> macro);

}
