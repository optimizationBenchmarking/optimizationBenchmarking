package org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.config.IConfigurable;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.GraphicDriverParser;
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
public class GraphicConfigurationBuilder extends GraphicConfiguration
    implements IConfigurable {

  /** the graphic driver parameter */
  public static final String PARAM_GRAPHIC_DRIVER = "graphicDriver"; //$NON-NLS-1$

  /** create the graphic configuration builder */
  public GraphicConfigurationBuilder() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public GraphicConfigurationBuilder configure(final Configuration config) {
    final IGraphicDriver oldDriver, newDriver;

    this._innerConfigure(config);

    oldDriver = this.m_driver;
    newDriver = config.get(
        GraphicConfigurationBuilder.PARAM_GRAPHIC_DRIVER,
        GraphicDriverParser.getInstance(), oldDriver);
    if ((oldDriver != null) || (newDriver != null)) {
      this.setGraphicDriver(newDriver);
    }
    return this;
  }

  /**
   * Copy settings from a graphic configuration
   *
   * @param copyFrom
   *          the graphic configuration to copy from
   */
  public void assign(final GraphicConfiguration copyFrom) {
    final EColorModel model;
    final int dpi;
    final IGraphicDriver driver;
    final double quality;

    if (copyFrom != null) {
      if ((model = copyFrom.m_colorModel) != null) {
        this.setColorModel(model);
      }
      if ((dpi = copyFrom.m_dpi) > 0) {
        this.setDotsPerInch(dpi);
      }
      if ((driver = copyFrom.m_driver) != null) {
        this.setGraphicDriver(driver);
      }
      if ((quality = copyFrom.m_quality) >= 0d) {
        this.setQuality(quality);
      }
    }
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
        (-1), GraphicConfiguration.MAX_DPI, oldDPI);
    if ((oldDPI > 0) || (newDPI > 0)) {
      this.setDotsPerInch(newDPI);
    }

    oldQuality = ((this.m_quality >= 0d) ? this.m_quality : (-1d));
    newQuality = config.getDouble(GraphicBuilder.PARAM_QUALITY, (-1d),
        GraphicConfiguration.MAX_QUALITY, oldQuality);
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
    GraphicConfiguration._checkDotsPerInch(dotsPerInch);
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
    GraphicConfiguration._checkColorModel(colorModel);
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
    GraphicConfiguration._checkQuality(quality);
    this.m_quality = quality;
  }

  /**
   * Set the graphic driver.
   *
   * @param driver
   *          the graphic driver
   * @see #getGraphicDriver()
   */
  public void setGraphicDriver(final IGraphicDriver driver) {
    GraphicConfiguration._checkDriver(driver);
    this.m_driver = driver;
  }

}
