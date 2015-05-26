package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.chart.spec.IChartDriver;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBuilder;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicConfiguration;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/** A store for the configuration of a document */
public class DocumentConfiguration extends GraphicConfiguration {

  /** the chart driver */
  private final IChartDriver m_chartDriver;

  /** the document driver */
  private final IDocumentDriver m_documentDriver;

  /**
   * create
   *
   * @param builder
   *          the builder
   */
  protected DocumentConfiguration(
      final DocumentConfigurationBuilder builder) {
    super(builder);
    DocumentConfiguration
        ._checkDocumentDriver(this.m_documentDriver = builder
            .getDocumentDriver());
    DocumentConfiguration._checkChartDriver(this.m_chartDriver = builder
        .getChartDriver());
    DocumentConfiguration._checkGraphicDriverCompliance(
        this.getGraphicDriver(), this.m_documentDriver);
    DocumentConfiguration._checkChartDriverCompliance(this.m_chartDriver,
        this.m_documentDriver);
  }

  /** {@inheritDoc} */
  @Override
  public DocumentConfiguration immutable() {

    if (this.getClass() == DocumentConfiguration.class) {
      return this;
    }

    throw new UnsupportedOperationException(//
        "Cannot make instance of " + //$NON-NLS-1$
            TextUtils.className(this.getClass()) + " immutable."); //$NON-NLS-1$
  }

  /**
   * check the chart driver
   *
   * @param driver
   *          the chart driver which must not be null
   */
  static final void _checkChartDriver(final IChartDriver driver) {
    if (driver == null) {
      throw new IllegalArgumentException("Chart driver must not be null."); //$NON-NLS-1$
    }
    driver.checkCanUse();
  }

  /**
   * check the document driver
   *
   * @param driver
   *          the document driver which must not be null
   */
  static final void _checkDocumentDriver(final IDocumentDriver driver) {
    if (driver == null) {
      throw new IllegalArgumentException(
          "Document driver must not be null."); //$NON-NLS-1$
    }
    driver.checkCanUse();
  }

  /**
   * check whether a graphic driver complies with a document driver
   *
   * @param graphics
   *          the graphics driver
   * @param document
   *          the document driver
   */
  static final void _checkGraphicDriverCompliance(
      final IGraphicDriver graphics, final IDocumentDriver document) {
    if (graphics != null) {
      if (document != null) {
        if (document instanceof DocumentDriver) {
          ((DocumentDriver) document).checkGraphicDriver(graphics);
        }
      }
    }
  }

  /**
   * check whether a chart driver complies with a document driver
   *
   * @param charts
   *          the charts driver
   * @param document
   *          the document driver
   */
  static final void _checkChartDriverCompliance(final IChartDriver charts,
      final IDocumentDriver document) {
    if (charts != null) {
      if (document != null) {
        if (document instanceof DocumentDriver) {
          ((DocumentDriver) document).checkChartDriver(charts);
        }
      }
    }
  }

  /**
   * Get the chart driver
   *
   * @return the chart driver
   */
  public final IChartDriver getChartDriver() {
    return this.m_chartDriver;
  }

  /**
   * Get the document driver
   *
   * @return the document driver
   */
  public final IDocumentDriver getDocumentDriver() {
    return this.m_documentDriver;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    final DocumentConfiguration config;
    final DocumentConfigurationBuilder builder;

    if (o == this) {
      return true;
    }
    if (o instanceof DocumentConfiguration) {
      if (super.equals(o)) {
        config = ((DocumentConfiguration) o);
        return (EComparison.equals(this.m_chartDriver,
            config.m_chartDriver) && EComparison.equals(
            this.m_documentDriver, config.m_documentDriver));
      }
    } else {
      if (o instanceof DocumentConfigurationBuilder) {
        if (super.equals(o)) {
          builder = ((DocumentConfigurationBuilder) o);
          return (EComparison.equals(this.m_chartDriver,
              builder.getChartDriver()) && EComparison.equals(
              this.m_documentDriver, builder.getDocumentDriver()));
        }
      }
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(super.hashCode(), HashUtils
        .combineHashes(HashUtils.hashCode(this.m_chartDriver),
            HashUtils.hashCode(this.m_documentDriver)));
  }

  /** to string */
  @Override
  public void toText(final ITextOutput textOut) {
    if (this.m_documentDriver != null) {
      textOut.append(this.m_documentDriver.getClass().getSimpleName());
      textOut.append('@');
    }
    super.toText(textOut);
    if (this.m_chartDriver != null) {
      textOut.append('@');
      textOut.append(this.m_chartDriver.getClass().getSimpleName());
    }
  }

  /**
   * Create a document builder with this object's settings
   *
   * @return the document builder
   */
  public IDocumentBuilder createDocumentBuilder() {
    final IDocumentBuilder builder;
    final IChartDriver chartDriver;

    DocumentConfiguration._checkDocumentDriver(this.m_documentDriver);

    builder = this.m_documentDriver.use();

    if (this.hasColorModel()) {
      builder.setColorModel(this.getColorModel());
    }

    if (this.hasDotsPerInch()) {
      builder.setDotsPerInch(this.getDotsPerInch());
    }

    if (this.hasQuality()) {
      builder.setQuality(this.getQuality());
    }

    if ((chartDriver = this.getChartDriver()) != null) {
      builder.setChartDriver(chartDriver);
    }

    builder.setGraphicDriver(this.getGraphicDriver());

    return builder;
  }

  /**
   * Create a document. This is a kitchen-sink method where we throw in all
   * the parameters normally to be passed to
   * {@link org.optimizationBenchmarking.utils.document.spec.IDocumentBuilder}
   * and not already defined in this configuration object. This kitchen
   * sink approach is not nice and maybe will be amended later. But for now
   * it will do.
   *
   * @param basePath
   *          the base path, i.e., the folder in which the document should
   *          be created
   * @param name
   *          the name of the graphics file (without extension)
   * @param listener
   *          the listener to be notified when painting the graphic has
   *          been completed
   * @param logger
   *          the logger to use
   * @return the graphic
   */
  public final IDocument createDocument(final Path basePath,
      final String name, final IFileProducerListener listener,
      final Logger logger) {
    final IDocumentBuilder builder;

    builder = this.createDocumentBuilder();
    builder.setBasePath(basePath);
    builder.setMainDocumentNameSuggestion(name);
    if (listener != null) {
      builder.setFileProducerListener(listener);
    }
    if (logger != null) {
      builder.setLogger(logger);
    }

    return builder.create();
  }
}