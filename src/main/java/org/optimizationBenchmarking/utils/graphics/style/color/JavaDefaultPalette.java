package org.optimizationBenchmarking.utils.graphics.style.color;

import java.awt.Color;

/**
 * The minimum default palette of Java, supporting all the colors defined
 * in {@linkplain java.awt.Color}.
 */
public final class JavaDefaultPalette extends ColorPalette {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the light-gray color */
  private static final ColorStyle LIGHT_GRAY = new ColorStyle(
      "light gray", Color.LIGHT_GRAY.getRGB()); //$NON-NLS-1$

  /** the gray color */
  private static final ColorStyle GRAY = new ColorStyle(
      "gray", Color.GRAY.getRGB()); //$NON-NLS-1$

  /** the dark-gray color */
  private static final ColorStyle DARK_GRAY = new ColorStyle(
      "dark gray", Color.DARK_GRAY.getRGB()); //$NON-NLS-1$

  /** the red color */
  private static final ColorStyle RED = new ColorStyle(
      "red", Color.RED.getRGB()); //$NON-NLS-1$

  /** the pink color */
  private static final ColorStyle PINK = new ColorStyle(
      "pink", Color.PINK.getRGB()); //$NON-NLS-1$

  /** the orange */
  private static final ColorStyle ORANGE = new ColorStyle(
      "orange", Color.ORANGE.getRGB()); //$NON-NLS-1$

  /** the yellow */
  private static final ColorStyle YELLOW = new ColorStyle(
      "yellow", Color.YELLOW.getRGB()); //$NON-NLS-1$

  /** the green */
  private static final ColorStyle GREEN = new ColorStyle(
      "green", Color.GREEN.getRGB()); //$NON-NLS-1$

  /** the magenta */
  private static final ColorStyle MAGENTA = new ColorStyle(
      "magenta", Color.MAGENTA.getRGB()); //$NON-NLS-1$

  /** the cyan */
  private static final ColorStyle CYAN = new ColorStyle(
      "cyan", Color.CYAN.getRGB()); //$NON-NLS-1$

  /** the blue */
  private static final ColorStyle BLUE = new ColorStyle(
      "blue", Color.BLUE.getRGB()); //$NON-NLS-1$

  /** the globally shared instance of the default palette */
  public static final JavaDefaultPalette INSTANCE = new JavaDefaultPalette();

  /** instantiate */
  private JavaDefaultPalette() {
    super(new ColorStyle[] { JavaDefaultPalette.RED,
        JavaDefaultPalette.BLUE, JavaDefaultPalette.MAGENTA,
        JavaDefaultPalette.GREEN, JavaDefaultPalette.PINK,
        JavaDefaultPalette.CYAN, JavaDefaultPalette.GRAY,
        JavaDefaultPalette.ORANGE, JavaDefaultPalette.DARK_GRAY,
        JavaDefaultPalette.YELLOW, JavaDefaultPalette.LIGHT_GRAY, });
  }

  /**
   * read resolve
   * 
   * @return {@link #INSTANCE}
   */
  private final Object readResolve() {
    return JavaDefaultPalette.INSTANCE;
  }

  /**
   * write replace
   * 
   * @return {@link #INSTANCE}
   */
  private final Object writeReplace() {
    return JavaDefaultPalette.INSTANCE;
  }

}
