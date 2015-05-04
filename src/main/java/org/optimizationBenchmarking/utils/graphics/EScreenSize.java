package org.optimizationBenchmarking.utils.graphics;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.math.units.ELength;

/** An enumeration of screen sizes */
public enum EScreenSize implements IMedium {

  /** the VGA display size */
  VGA(640, 480),

  /** the Super VGA display size */
  SVGA(800, 600),

  /** the eXtended Graphics Array display size */
  XGA(1024, 768),

  /** the Super eXtended Graphics Array display size */
  SXGA(1280, 1024),

  /** the Super eXtended Graphics Array display size + */
  SXGA_PLUS(1400, 1050),

  /** the Wide Super eXtended Graphics Array display size */
  WSXGA_PLUS(1680, 1050),

  /** the full HD resolution */
  FULL_HD(1920, 1080),

  /** the Wide Ultra eXtended Graphics Array display size */
  WUXGA(1920, 1200),

  /** the Wide Quad eXtended Graphics Array display size */
  WQXGA(2560, 1600), ;

  /** the default screen size */
  public static final EScreenSize DEFAULT = SXGA;

  /** the default pixels per inch of a screen */
  public static final int DEFAULT_SCREEN_DPI = 96;

  /** the set of screen sizes */
  public static final ArraySetView<EScreenSize> INSTANCES = //
      new ArraySetView<>(EScreenSize.values());

  /** the width */
  private final int m_width;
  /** the height */
  private final int m_height;

  /**
   * create the screen size
   *
   * @param width
   *          the width
   * @param height
   *          the height
   */
  EScreenSize(final int width, final int height) {
    this.m_height = height;
    this.m_width = width;
  }

  /**
   * Get the screen width
   *
   * @return the screen width
   */
  public final int getWidth() {
    return this.m_width;
  }

  /**
   * Get the screen height
   *
   * @return the screen height
   */
  public final int getHeight() {
    return this.m_height;
  }

  /**
   * Obtain the physical dimensions of this screen size if the given
   * {@code dpi}s are assumed
   *
   * @param dpi
   *          the dots (pixels) per inch
   * @return the dimension
   */
  public final PhysicalDimension getPageSize(final double dpi) {
    return new PhysicalDimension(//
        (this.m_width / dpi), (this.m_height / dpi), ELength.INCH);
  }

  /** {@inheritDoc} */
  @Override
  public final PhysicalDimension getPageSize() {
    return this.getPageSize(EScreenSize.DEFAULT_SCREEN_DPI);
  }
}
