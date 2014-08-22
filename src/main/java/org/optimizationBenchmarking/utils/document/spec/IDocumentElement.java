package org.optimizationBenchmarking.utils.document.spec;

import java.io.Closeable;

/**
 * The basic interface for the parallel document output API.
 */
public interface IDocumentElement extends Closeable {

  /**
   * Close the API element.
   */
  @Override
  public abstract void close();

}
