package org.optimizationBenchmarking.utils.graphics.style;

import java.awt.Graphics2D;
import java.util.Set;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A palette.
 *
 * @param <T>
 *          the palette element type
 */
public class Palette<T extends IStyle> extends ArrayListView<T> implements
    Set<T> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * create the palette
   *
   * @param data
   *          the palette data
   */
  protected Palette(final T[] data) {
    super(data);
    if (data.length < 1) {
      throw new IllegalArgumentException(
          "A palette must have at least one element, but only " //$NON-NLS-1$
              + data.length + " are specified."); //$NON-NLS-1$
    }
    if (data.length > 10000) {
      throw new IllegalArgumentException(//
          "A palette must not have more than 10000 elements, but "//$NON-NLS-1$
              + data.length + " are specified.");//$NON-NLS-1$
    }
  }

  /**
   * Initialize a graphics context with the defaults of this palette
   *
   * @param graphics
   *          the graphics context
   */
  public void initialize(final Graphics2D graphics) {
    //
  }
}
