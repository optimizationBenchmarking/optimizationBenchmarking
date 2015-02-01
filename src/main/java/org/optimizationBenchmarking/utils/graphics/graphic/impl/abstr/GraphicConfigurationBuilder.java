package org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.config.IConfigurable;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;

/**
 * A class which can be used to store and re-use configurations for
 * creating graphics, including the
 * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver
 * graphic driver} and the quality parameters to be passed to a
 * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicBuilder
 * graphic builder}.
 */
public final class GraphicConfigurationBuilder extends
    GraphicConfiguration implements IConfigurable {

  /** the graphic driver parameter */
  public static final String PARAM_GRAPHIC_DRIVER = "graphicDriver"; //$NON-NLS-1$

  /** the minimum allowed dpi */
  private static final int MIN_DPI = 26;
  /** the maximum allowed dpi */
  private static final int MAX_DPI = 10000;
  /** the minimum allowed quality value */
  private static final double MIN_QUALITY = 0d;
  /** the maximum allowed quality value */
  private static final double MAX_QUALITY = 1d;

  /** create the graphic configuration builder */
  public GraphicConfigurationBuilder() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final GraphicConfigurationBuilder configure(
      final Configuration config) {
    final IGraphicDriver oldDriver, newDriver;

    this._innerConfigure(config);

    oldDriver = this.m_driver;
    newDriver = config.getInstance(
        GraphicConfigurationBuilder.PARAM_GRAPHIC_DRIVER,
        IGraphicDriver.class, oldDriver);
    if ((oldDriver != null) || (newDriver != null)) {
      this.setGraphicDriver(newDriver);
    }
    return this;
  }

  /**
   * Configure the graphic-driver local parameters
   * 
   * @param config
   *          the configuration
   */
  final void _innerConfigure(final Configuration config) {
    final EColorModel oldColors, newColors;
    final int oldDPI, newDPI;
    final double oldQuality, newQuality;

    oldColors = this.m_colorModel;
    newColors = config.getInstance(GraphicBuilder.PARAM_COLOR_MODEL,
        EColorModel.class, oldColors);

    if ((oldColors != null) || (newColors != null)) {
      this.setColorModel(newColors);
    }

    oldDPI = ((this.m_dpi > 0) ? this.m_dpi : (-1));
    newDPI = config.getInt(GraphicBuilder.PARAM_DPI,//
        (-1), GraphicConfigurationBuilder.MAX_DPI, oldDPI);
    if ((oldDPI > 0) || (newDPI > 0)) {
      this.setDotsPerInch(newDPI);
    }

    oldQuality = ((this.m_quality >= 0d) ? this.m_quality : (-1d));
    newQuality = config.getDouble(GraphicBuilder.PARAM_QUALITY,
        GraphicConfigurationBuilder.MIN_QUALITY,
        GraphicConfigurationBuilder.MAX_QUALITY, oldQuality);
    if ((oldQuality >= 0) || (newQuality >= 0)) {
      this.setQuality(newQuality);
    }
  }

  /**
   * Set the dots per inch
   * 
   * @param dotsPerInch
   *          the dots per inch
   * @see org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicBuilder#setDotsPerInch(int)
   * @see #getDotsPerInch()
   */
  public final void setDotsPerInch(final int dotsPerInch) {
    if ((dotsPerInch < GraphicConfigurationBuilder.MIN_DPI)
        || (dotsPerInch > GraphicConfigurationBuilder.MAX_DPI)) {
      throw new IllegalArgumentException(//
          "Cannot create images with less than 1 pixel per MM (26 dot per inch) or more than 100000 dots per inch, since such images would be nonsense. You specified "//$NON-NLS-1$ 
              + dotsPerInch + "dpi, which is outside the sane range."); //$NON-NLS-1$
    }
    this.m_dpi = dotsPerInch;
  }

  /**
   * Set the color model
   * 
   * @param colorModel
   *          the color model
   * @see org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicBuilder#setColorModel(EColorModel)
   * @see #getColorModel()
   */
  public final void setColorModel(final EColorModel colorModel) {
    if (colorModel == null) {
      throw new IllegalArgumentException("Color model cannot be null."); //$NON-NLS-1$
    }
    this.m_colorModel = colorModel;
  }

  /**
   * Set the quality
   * 
   * @param quality
   *          the quality
   * @see org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicBuilder#setQuality(double)
   * @see #getQuality()
   */
  public final void setQuality(final double quality) {
    if ((quality < GraphicConfigurationBuilder.MIN_QUALITY)
        || (quality > GraphicConfigurationBuilder.MAX_QUALITY)
        || (quality != quality)) {
      throw new IllegalArgumentException(//
          "Graphic quality must be in [0,1], but you specified " //$NON-NLS-1$
              + quality);
    }
    this.m_quality = quality;
  }

  /**
   * Set the graphic driver.
   * 
   * @param driver
   *          the graphic driver
   * @see #getGraphicDriver()
   */
  public final void setGraphicDriver(final IGraphicDriver driver) {
    if (driver == null) {
      throw new IllegalArgumentException("Cannot set null driver."); //$NON-NLS-1$
    }
    driver.checkCanUse();
    this.m_driver = driver;
  }

}
