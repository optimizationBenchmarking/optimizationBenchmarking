package org.optimizationBenchmarking.utils.graphics.style;

import java.awt.Graphics2D;

import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A style element is a uniquely identifyable resource within a style
 * palette.
 */
public interface IStyle {

  /**
   * Apply a style to a graphic
   *
   * @param g
   *          the graphic
   * @return an instance of {@link java.io.Closeable}
   */
  public abstract StyleApplication applyTo(final Graphics2D g);

  /**
   * Append the name of this style to a given text output
   *
   * @param textCase
   *          the text case to use
   * @param dest
   *          the text output destination
   * @param omitDefaults
   *          should default features be omitted?
   * @return {@code true} if something was appended to {@code dest},
   *         {@code false} otherwise
   */
  public abstract boolean appendDescription(final ETextCase textCase,
      final ITextOutput dest, final boolean omitDefaults);

  /**
   * Obtain the ID of this style. This id is unique within the owning
   * palette.
   *
   * @return a unique ID for this style
   */
  public abstract String getID();

}
