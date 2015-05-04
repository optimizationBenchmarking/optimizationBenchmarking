package org.optimizationBenchmarking.utils.graphics.style;

/**
 * An interface for entities which can provide
 * {@link org.optimizationBenchmarking.utils.graphics.style.StyleSet style
 * sets}.
 */
public interface IStyleProvider {

  /**
   * Access the style set
   *
   * @return the style set
   */
  public abstract StyleSet getStyles();
}
