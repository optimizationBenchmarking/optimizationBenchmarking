package org.optimizationBenchmarking.utils.graphics.style;

import java.util.Iterator;

import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;

/**
 * A builder for a palette element.
 *
 * @param <ET>
 *          the element type
 */
public abstract class PaletteElementBuilder<ET extends IStyle> extends
    BuilderFSM<ET> {

  /**
   * Create the palette element builder
   *
   * @param owner
   *          the owner
   */
  protected PaletteElementBuilder(
      final PaletteBuilder<ET, ? extends Palette<ET>> owner) {
    super(owner);
  }

  /**
   * load a string
   *
   * @param strings
   *          the strings
   */
  public abstract void fromStrings(final Iterator<String> strings);
}
