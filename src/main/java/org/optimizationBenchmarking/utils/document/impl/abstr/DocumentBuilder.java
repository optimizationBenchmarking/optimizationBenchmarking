package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.chart.spec.IChartDriver;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBuilder;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicConfiguration;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.tools.impl.abstr.DocumentProducerJobBuilder;

/** The class for document builders */
public abstract class DocumentBuilder extends
DocumentProducerJobBuilder<Document, DocumentBuilder> implements
IDocumentBuilder, ITextable {

  /** the owning builder */
  private final DocumentConfigurationBuilder m_builder;

  /**
   * create the tool job builder
   *
   * @param owner
   *          the owning document builder
   */
  protected DocumentBuilder(final DocumentDriver owner) {
    super();

    DocumentConfiguration._checkDocumentDriver(owner);
    this.m_builder = this.createConfigurationBuilder(owner);
  }

  /**
   * create the internal configuration builder object
   *
   * @param owner
   *          the owning document driver
   * @return the internal configuration builder
   */
  protected DocumentConfigurationBuilder createConfigurationBuilder(
      final DocumentDriver owner) {
    DocumentConfiguration._checkDocumentDriver(owner);
    return new DocumentConfigurationBuilder(owner);
  }

  /**
   * Create the style set.
   *
   * @return the style set
   */
  protected abstract StyleSet createStyleSet();

  /**
   * Get the owning document driver
   *
   * @return the owning document driver
   */
  protected final DocumentDriver getOwner() {
    return ((DocumentDriver) (this.m_builder.getDocumentDriver()));
  }

  /**
   * Get the document configuration represented by this builder
   *
   * @return the document configuration represented by this builder
   */
  protected final DocumentConfiguration getDocumentConfiguration() {
    return this.m_builder.immutable();
  }

  /**
   * get the immutable graphic configuration
   *
   * @return the immutable graphic configuration
   */
  final GraphicConfiguration _graphicConfig() {
    return this.m_builder._graphicConfig();
  }

  /**
   * Get the document configuration builder
   *
   * @return the document configuration builder
   */
  protected final DocumentConfigurationBuilder getConfigurationBuilder() {
    return this.m_builder;
  }

  /** {@inheritDoc} */
  @Override
  public final DocumentBuilder setDotsPerInch(final int dotsPerInch) {
    this.m_builder.setDotsPerInch(dotsPerInch);
    return this;
  }

  /**
   * Get the dots per inch
   *
   * @return the dots per inch
   * @see #setDotsPerInch(int)
   */
  public final int getDotsPerInch() {
    return this.m_builder.getDotsPerInch();
  }

  /** {@inheritDoc} */
  @Override
  public final DocumentBuilder setColorModel(final EColorModel colorModel) {
    this.m_builder.setColorModel(colorModel);
    return this;
  }

  /**
   * Get the color model
   *
   * @return the color model
   * @see #setColorModel(EColorModel)
   */
  public final EColorModel getColorModel() {
    return this.m_builder.getColorModel();
  }

  /** {@inheritDoc} */
  @Override
  public final DocumentBuilder setQuality(final double quality) {
    this.m_builder.setQuality(quality);
    return this;
  }

  /**
   * Get the rendering quality
   *
   * @return the rendering quality
   */
  public final double getQuality() {
    return this.m_builder.getQuality();
  }

  /** {@inheritDoc} */
  @Override
  public final DocumentBuilder setGraphicDriver(final IGraphicDriver driver) {
    this.m_builder.setGraphicDriver(driver);
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
    return this.m_builder.getGraphicDriver();
  }

  /** {@inheritDoc} */
  @Override
  public DocumentBuilder configure(final Configuration config) {
    super.configure(config);
    this.m_builder.configureWithoutDriver(config);
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
    final IDocumentDriver documents;

    super.validate();

    DocumentConfiguration._checkDocumentDriver(documents = this.m_builder
        .getDocumentDriver());
    DocumentConfiguration._checkChartDriver(this.m_builder
        .getChartDriver());
    DocumentConfiguration._checkGraphicDriverCompliance(
        this.m_builder.getGraphicDriver(), documents);
  }

  /** {@inheritDoc} */
  @Override
  public final DocumentBuilder setChartDriver(final IChartDriver driver) {
    this.m_builder.setChartDriver(driver);
    return this;
  }

  /**
   * Get the chart driver
   *
   * @return the chart driver
   */
  public final IChartDriver getChartDriver() {
    return this.m_builder.getChartDriver();
  }

  /**
   * Create the document
   *
   * @return the document
   */
  protected abstract Document doCreateDocument();

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return this.m_builder.toString();
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return this.m_builder.hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof DocumentBuilder) {
      return this.m_builder.equals(((DocumentBuilder) o).m_builder);
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    this.m_builder.toText(textOut);
  }
}
