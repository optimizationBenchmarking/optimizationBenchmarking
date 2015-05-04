package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.chart.impl.EChartFormat;
import org.optimizationBenchmarking.utils.chart.spec.IChartDriver;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicConfiguration;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicConfigurationBuilder;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A store for the configuration of a document */
public class DocumentConfigurationBuilder extends
GraphicConfigurationBuilder {

  /** the chart driver */
  public static final String PARAM_CHART_DRIVER = "chartDriver"; //$NON-NLS-1$

  /** the chart driver */
  public static final String PARAM_DOCUMENT_DRIVER = "documentDriver"; //$NON-NLS-1$

  /** the chart driver */
  private IChartDriver m_chartDriver;

  /** the document driver */
  private IDocumentDriver m_documentDriver;

  /** create */
  public DocumentConfigurationBuilder() {
    this(null);
  }

  /**
   * create
   *
   * @param driver
   *          the document driver
   */
  protected DocumentConfigurationBuilder(final DocumentDriver driver) {
    super();
    if (driver != null) {
      DocumentConfiguration._checkDocumentDriver(driver);
      this.setDocumentDriver(driver);
    }
  }

  /**
   * Assign to another configuration or configuration builder
   *
   * @param copyFrom
   *          the configuration to copy from
   */
  @Override
  public void assign(final GraphicConfiguration copyFrom) {
    final DocumentConfigurationBuilder builder;
    final DocumentConfiguration config;
    final IChartDriver chart;
    final IDocumentDriver doc;

    if (copyFrom != null) {
      if (copyFrom instanceof DocumentConfigurationBuilder) {
        builder = ((DocumentConfigurationBuilder) copyFrom);
        if ((chart = builder.m_chartDriver) != null) {
          this.setChartDriver(chart);
        }
        if ((doc = builder.m_documentDriver) != null) {
          this.setDocumentDriver(doc);
        }
      } else {
        if (copyFrom instanceof DocumentConfiguration) {
          config = ((DocumentConfiguration) copyFrom);
          if ((chart = config.getChartDriver()) != null) {
            this.setChartDriver(chart);
          }
          if ((doc = config.getDocumentDriver()) != null) {
            this.setDocumentDriver(doc);
          }
        }
      }

      super.assign(copyFrom);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final DocumentConfigurationBuilder configure(
      final Configuration config) {
    final IDocumentDriver oldDriver, newDriver;

    this.configureWithoutDriver(config);

    oldDriver = this.m_documentDriver;
    newDriver = config.getInstance(
        DocumentConfigurationBuilder.PARAM_DOCUMENT_DRIVER,
        IDocumentDriver.class, oldDriver);
    if ((oldDriver != null) || (newDriver != null)) {
      this.setDocumentDriver(newDriver);
    }

    return this;
  }

  /**
   * Configure the local parameters of the document driver. This method
   * loads the configuration from a
   * {@link org.optimizationBenchmarking.utils.config.Configuration
   * configuration context}, but does not overwrite the document driver
   * setting. Using this method makes sense in sub-classes which require
   * certain document drivers.
   *
   * @param config
   *          the configuration
   */
  protected void configureWithoutDriver(final Configuration config) {
    final IChartDriver oldDriver, newDriver;

    super.configure(config);

    oldDriver = this.m_chartDriver;
    newDriver = config.getInstance(
        DocumentConfigurationBuilder.PARAM_CHART_DRIVER,
        IChartDriver.class, oldDriver);
    if ((oldDriver != null) || (newDriver != null)) {
      this.setChartDriver(newDriver);
    }
  }

  /** {@inheritDoc} */
  @Override
  public DocumentConfiguration immutable() {
    return new DocumentConfiguration(this);
  }

  /**
   * get the immutable graphic configuration
   *
   * @return the immutable graphic configuration
   */
  final GraphicConfiguration _graphicConfig() {
    return super.immutable();
  }

  /**
   * Set the chart driver
   *
   * @param driver
   *          the chart driver
   */
  public final void setChartDriver(final IChartDriver driver) {
    DocumentConfiguration._checkChartDriver(driver);
    this.m_chartDriver = driver;
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
   * Set the document driver
   *
   * @param driver
   *          the document driver
   */
  public void setDocumentDriver(final IDocumentDriver driver) {
    IGraphicDriver graphics;

    DocumentConfiguration._checkDocumentDriver(driver);
    DocumentConfiguration._checkGraphicDriverCompliance(
        (graphics = this.getGraphicDriver()), driver);
    this.m_documentDriver = driver;
    if (graphics == null) {
      if (driver instanceof DocumentDriver) {
        graphics = ((DocumentDriver) driver).getDefaultGraphicDriver();
        if (graphics != null) {
          this.setGraphicDriver(graphics);
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public void setGraphicDriver(final IGraphicDriver driver) {
    DocumentConfiguration._checkGraphicDriverCompliance(driver,
        this.m_documentDriver);
    super.setGraphicDriver(driver);
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
            config.getChartDriver()) && EComparison.equals(
                this.m_documentDriver, config.getDocumentDriver()));
      }
    } else {
      if (o instanceof DocumentConfigurationBuilder) {
        if (super.equals(o)) {
          builder = ((DocumentConfigurationBuilder) o);
          return (EComparison.equals(this.m_chartDriver,
              builder.m_chartDriver) && EComparison.equals(
                  this.m_documentDriver, builder.m_documentDriver));
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
}
