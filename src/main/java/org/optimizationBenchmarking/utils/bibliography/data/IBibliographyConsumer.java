package org.optimizationBenchmarking.utils.bibliography.data;

/** An interface for an activity which can consume a bibliography. */
public interface IBibliographyConsumer {

  /**
   * Consume the given, created bibliography
   *
   * @param bib
   *          the created bibliography
   */
  public abstract void consumeBibliography(final Bibliography bib);

}
