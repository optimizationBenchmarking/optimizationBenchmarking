package org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicBuilder;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.tools.impl.abstr.DocumentProducerJobBuilder;

/** The base class for graphics builders */
public final class GraphicBuilder extends
    DocumentProducerJobBuilder<Graphic, GraphicBuilder> implements
    IGraphicBuilder {

  /** the dots per inch parameter */
  public static final String PARAM_DPI = "dpi"; //$NON-NLS-1$
  /** the color model parameter */
  public static final String PARAM_COLOR_MODEL = "colorModel"; //$NON-NLS-1$
  /** the image quality parameter */
  public static final String PARAM_QUALITY = "quality"; //$NON-NLS-1$

  /** the minimum image size in meter */
  private static final double MIN_SIZE_M = 1e-4d;
  /** the minimum image size in meter */
  private static final double MAX_SIZE_M = 10d;

  /** the dimension */
  private PhysicalDimension m_size;

  /** the dots per inch */
  private final GraphicConfigurationBuilder m_config;

  /**
   * create the tool job builder
   * 
   * @param owner
   *          the owning graphic builder
   */
  protected GraphicBuilder(final AbstractGraphicDriver owner) {
    super();

    this.m_config = new GraphicConfigurationBuilder();
    this.m_config.setGraphicDriver(owner);
  }

  /** {@inheritDoc} */
  @Override
  public GraphicBuilder configure(final Configuration config) {
    super.configure(config);
    this.m_config.configure(config);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final GraphicBuilder setSize(final PhysicalDimension size) {
    final double w, h;
    final ELength sizeUnit;
    double r;

    if ((size == null) || ((h = size.getHeight()) <= 0d)
        || ((w = size.getWidth()) <= 0d)) {
      throw new IllegalArgumentException(//
          "Invalid graphic size: " + size);//$NON-NLS-1$
    }

    sizeUnit = size.getUnit();
    r = sizeUnit.convertTo(w, ELength.M);
    if ((r <= GraphicBuilder.MIN_SIZE_M)
        || (r >= GraphicBuilder.MAX_SIZE_M)) {
      throw new IllegalArgumentException(//
          "A graphic width cannot be smaller than 0.1mm or larger than 10m, but "//$NON-NLS-1$
              + w + " specified in " + sizeUnit + //$NON-NLS-1$ 
              " equals " + r + //$NON-NLS-1$
              "m.");//$NON-NLS-1$
    }

    r = sizeUnit.convertTo(h, ELength.M);
    if ((r <= GraphicBuilder.MIN_SIZE_M)
        || (r >= GraphicBuilder.MAX_SIZE_M)) {
      throw new IllegalArgumentException(//
          "A graphic height cannot be smaller than 0.1mm or larger than 10m, but "//$NON-NLS-1$
              + h + " specified in " + sizeUnit + //$NON-NLS-1$ 
              " equals " + r + //$NON-NLS-1$
              "m.");//$NON-NLS-1$
    }

    this.m_size = size;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected void validate() {
    super.validate();
    if (this.m_size == null) {
      throw new IllegalArgumentException(//
          "The size of the graphic must be set."); //$NON-NLS-1$
    }
  }

  /**
   * Get the size of the graphic to be built
   * 
   * @return the size of the graphic to be built
   * @see #setSize(PhysicalDimension)
   */
  public final PhysicalDimension getSize() {
    return this.m_size;
  }

  /** {@inheritDoc} */
  @Override
  public final GraphicBuilder setDotsPerInch(final int dotsPerInch) {
    this.m_config.setDotsPerInch(dotsPerInch);
    return this;
  }

  /**
   * Get the dots per inch
   * 
   * @return the dots per inch
   * @see #setDotsPerInch(int)
   */
  public final int getDotsPerInch() {
    return this.m_config.getDotsPerInch();
  }

  /** {@inheritDoc} */
  @Override
  public final GraphicBuilder setColorModel(final EColorModel colorModel) {
    this.m_config.setColorModel(colorModel);
    return this;
  }

  /**
   * Get the color model
   * 
   * @return the color model
   */
  public final EColorModel getColorModel() {
    return this.m_config.getColorModel();
  }

  /**
   * Get the graphics quality
   * 
   * @return the graphics quality
   */
  public final double getQuality() {
    return this.m_config.getQuality();
  }

  /** {@inheritDoc} */
  @Override
  public final GraphicBuilder setQuality(final double quality) {
    this.m_config.setQuality(quality);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final Graphic create() {
    this.validate();
    return ((AbstractGraphicDriver) (this.m_config.getGraphicDriver()))
        .createGraphic(this);
  }
}
