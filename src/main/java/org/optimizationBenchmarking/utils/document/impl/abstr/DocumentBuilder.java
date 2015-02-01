package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBuilder;
import org.optimizationBenchmarking.utils.graphics.chart.impl.EChartFormat;
import org.optimizationBenchmarking.utils.graphics.chart.spec.IChartDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicConfiguration;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicConfigurationBuilder;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.tools.impl.abstr.DocumentProducerJobBuilder;

/** The class for document builders */
public abstract class DocumentBuilder extends
    DocumentProducerJobBuilder<Document, DocumentBuilder> implements
    IDocumentBuilder {

  /** the chart driver */
  public static final String PARAM_CHART_DRIVER = "chartDriver"; //$NON-NLS-1$

  /** the owning builder */
  private final DocumentDriver m_owner;

  /** the graphic configuration builder */
  private final GraphicConfigurationBuilder m_graphicConfig;

  /** the chart driver */
  private IChartDriver m_chartDriver;

  /**
   * create the tool job builder
   * 
   * @param owner
   *          the owning document builder
   */
  protected DocumentBuilder(final DocumentDriver owner) {
    super();

    if (owner == null) {
      throw new IllegalArgumentException(//
          "Document driver cannot be null."); //$NON-NLS-1$
    }
    this.m_owner = owner;

    this.m_graphicConfig = new GraphicConfigurationBuilder();
    this.setGraphicDriver(owner.getDefaultGraphicDriver());
  }

  /**
   * Create the style set.
   * 
   * @return the style set
   */
  protected abstract StyleSet createStyleSet();

  /**
   * Check a chart driver
   * 
   * @param driver
   *          the chart driver
   */
  static final void _checkChartDriver(final IChartDriver driver) {
    if (driver == null) {
      throw new IllegalArgumentException("Chart driver cannot be null."); //$NON-NLS-1$
    }
    driver.checkCanUse();
  }

  /**
   * Get the owning document driver
   * 
   * @return the owning document driver
   */
  protected final DocumentDriver getOwner() {
    return this.m_owner;
  }

  /**
   * Get the graphics configuration represented by this builder
   * 
   * @return the graphics configuration represented by this builder
   */
  protected final GraphicConfiguration getGraphicConfiguration() {
    return this.m_graphicConfig.immutable();
  }

  /** {@inheritDoc} */
  @Override
  public final DocumentBuilder setDotsPerInch(final int dotsPerInch) {
    this.m_graphicConfig.setDotsPerInch(dotsPerInch);
    return this;
  }

  /**
   * Get the dots per inch
   * 
   * @return the dots per inch
   * @see #setDotsPerInch(int)
   */
  public final int getDotsPerInch() {
    return this.m_graphicConfig.getDotsPerInch();
  }

  /** {@inheritDoc} */
  @Override
  public final DocumentBuilder setColorModel(final EColorModel colorModel) {
    this.m_graphicConfig.setColorModel(colorModel);
    return this;
  }

  /**
   * Get the color model
   * 
   * @return the color model
   * @see #setColorModel(EColorModel)
   */
  public final EColorModel getColorModel() {
    return this.m_graphicConfig.getColorModel();
  }

  /** {@inheritDoc} */
  @Override
  public final DocumentBuilder setQuality(final double quality) {
    this.m_graphicConfig.setQuality(quality);
    return this;
  }

  /**
   * Get the rendering quality
   * 
   * @return the rendering quality
   */
  public final double getQuality() {
    return this.m_graphicConfig.getQuality();
  }

  /** {@inheritDoc} */
  @Override
  public final DocumentBuilder setGraphicDriver(final IGraphicDriver driver) {
    this.m_graphicConfig.setGraphicDriver(driver);
    return this;
  }

  /**
   * Get the graphic driver to be used for creating the graphics in the
   * document.
   * 
   * @return the graphic driver to use
   * @see #setGraphicDriver(IGraphicDriver)
   */
  public final IGraphicDriver getGraphicDriver() {
    return this.m_graphicConfig.getGraphicDriver();
  }

  /** {@inheritDoc} */
  @Override
  public DocumentBuilder configure(final Configuration config) {
    final IChartDriver oldDriver, newDriver;

    super.configure(config);
    this.m_graphicConfig.configure(config);

    oldDriver = this.m_chartDriver;
    newDriver = config.getInstance(DocumentBuilder.PARAM_CHART_DRIVER,
        IChartDriver.class, oldDriver);
    if ((oldDriver != null) || (newDriver != null)) {
      this.setChartDriver(newDriver);
    }

    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final Document create() {
    final Logger logger;

    this.validate();

    if (((logger = this.getLogger()) != null)
        && (logger.isLoggable(Level.FINE))) {
      logger.fine(//
          "Begin creation of document '" + //$NON-NLS-1$
              this.getMainDocumentNameSuggestion()
              + "' in folder '" + this.getBasePath() + //$NON-NLS-1$
              "' with graphic driver " + //$NON-NLS-1$ 
              this.getGraphicDriver());
    }
    return this.doCreateDocument();
  }

  /** {@inheritDoc} */
  @Override
  protected void validate() {
    super.validate();
    DocumentBuilder._checkChartDriver(this.getChartDriver());
  }

  /** {@inheritDoc} */
  @Override
  public final DocumentBuilder setChartDriver(final IChartDriver driver) {
    DocumentBuilder._checkChartDriver(driver);
    this.m_chartDriver = driver;
    return this;
  }

  /**
   * Get the chart driver
   * 
   * @return the chart driver
   */
  public final IChartDriver getChartDriver() {
    if (this.m_chartDriver != null) {
      return this.m_chartDriver;
    }
    return EChartFormat.DEFAULT.getDefaultDriver();
  }

  /**
   * Create the document
   * 
   * @return the document
   */
  protected abstract Document doCreateDocument();

}
