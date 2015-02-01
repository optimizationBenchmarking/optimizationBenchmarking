package org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicBuilder;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * An class which can be used to store and re-use configurations for
 * creating graphics.
 */
public class GraphicConfiguration implements ITextable {

  /** the driver */
  IGraphicDriver m_driver;

  /** the color model */
  EColorModel m_colorModel;

  /** the dpi */
  int m_dpi;

  /** the quality */
  double m_quality;

  /** create the example */
  GraphicConfiguration() {
    super();
    this.m_dpi = (-1);
    this.m_quality = (-1d);
  }

  /**
   * Get the immutable version of this configuration
   * 
   * @return the immutable version of this configuration
   */
  public final GraphicConfiguration immutable() {
    final GraphicConfiguration res;

    this.__checkDriver();

    if (this.getClass() == GraphicConfiguration.class) {
      return this;
    }

    res = new GraphicConfiguration();
    res.m_driver = this.m_driver;
    res.m_colorModel = this.m_colorModel;
    res.m_dpi = this.m_dpi;
    res.m_quality = this.m_quality;

    return res;
  }

  /** check whether the graphic driver has been set */
  private final void __checkDriver() {
    if (this.m_driver == null) {
      throw new IllegalStateException(//
          "Graphic driver cannot be null."); //$NON-NLS-1$
    }
    this.m_driver.checkCanUse();
  }

  /**
   * Get the dots per inch
   * 
   * @return the dots per inch
   */
  public final int getDotsPerInch() {
    return ((this.m_dpi > 0) ? this.m_dpi : EGraphicFormat.DEFAULT_DPI);
  }

  /**
   * Get the graphics quality
   * 
   * @return the graphics quality
   */
  public final double getQuality() {
    return ((this.m_quality >= 0d) ? this.m_quality
        : EGraphicFormat.DEFAULT_QUALITY);
  }

  /**
   * Get the color model
   * 
   * @return the color model
   */
  public final EColorModel getColorModel() {
    return ((this.m_colorModel != null) ? this.m_colorModel
        : EGraphicFormat.DEFAULT_COLOR_MODEL);
  }

  /**
   * Get the graphic driver
   * 
   * @return the graphic driver
   */
  public final IGraphicDriver getGraphicDriver() {
    return this.m_driver;
  }

  /**
   * Create a graphic of the given size. This is a kitchen-sink method
   * where we throw in all the parameters normally to be passed to
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicBuilder}
   * and not already defined in this configuration object. This kitchen
   * sink approach is not nice and maybe will be amended later. But for now
   * it will do.
   * 
   * @param basePath
   *          the base path, i.e., the folder in which the graphic should
   *          be created
   * @param name
   *          the name of the graphics file (without extension)
   * @param size
   *          the size of the graphic
   * @param listener
   *          the listener to be notified when painting the graphic has
   *          been completed
   * @param logger
   *          the logger to use
   * @return the graphic
   */
  public final Graphic createGraphic(final Path basePath,
      final String name, final PhysicalDimension size,
      final IFileProducerListener listener, final Logger logger) {
    final IGraphicBuilder builder;

    this.__checkDriver();

    builder = this.m_driver.use();
    builder.setBasePath(basePath);
    builder.setMainDocumentNameSuggestion(name);
    builder.setSize(size);
    if (listener != null) {
      builder.setFileProducerListener(listener);
    }

    if (this.m_colorModel != null) {
      builder.setColorModel(this.m_colorModel);
    }

    if (this.m_dpi > 0) {
      builder.setDotsPerInch(this.m_dpi);
    }

    if (this.m_quality >= 0d) {
      builder.setQuality(this.m_quality);
    }
    return builder.create();
  }

  /** to string */
  @Override
  public final String toString() {
    final MemoryTextOutput mto;

    mto = new MemoryTextOutput();
    this.toText(mto);
    return mto.toString();
  }

  /** to string */
  @Override
  public final void toText(final ITextOutput textOut) {

    if (this.m_driver != null) {
      textOut.append(this.m_driver.getClass().getSimpleName());
    }

    if (this.m_colorModel != null) {
      textOut.append('@');
      textOut.append(this.m_colorModel.name());
    }

    if (this.m_dpi > 0) {
      textOut.append('@');
      textOut.append(this.m_dpi);
      textOut.append("dpi"); //$NON-NLS-1$
    }

    if (this.m_quality >= 0d) {
      textOut.append('@');
      textOut.append(this.m_quality);
      textOut.append("quality"); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    int hc;

    hc = HashUtils.hashCode(this.m_driver);
    if (this.m_colorModel != null) {
      hc = HashUtils.combineHashes(hc,
          HashUtils.hashCode(this.m_colorModel));
    }
    if (this.m_dpi > 0) {
      hc = HashUtils.combineHashes(hc, HashUtils.hashCode(this.m_dpi));
    }
    if (this.m_quality >= 0) {
      hc = HashUtils.combineHashes(hc, HashUtils.hashCode(this.m_quality));
    }

    return hc;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final GraphicConfiguration example;

    if (o == this) {
      return true;
    }
    if (o == null) {
      return false;
    }

    if (o instanceof GraphicConfiguration) {
      example = ((GraphicConfiguration) o);
      if (EComparison.equals(this.m_driver, example.m_driver)) {
        if (EComparison.equals(this.m_colorModel, example.m_colorModel)) {

          if (this.m_dpi > 0) {
            if (this.m_dpi != example.m_dpi) {
              return false;
            }
          } else {
            if (example.m_dpi > 0) {
              return false;
            }
          }

          if (this.m_quality >= 0d) {
            if (this.m_quality != example.m_quality) {
              return false;
            }
          } else {
            if (example.m_quality >= 0d) {
              return false;
            }
          }

          return true;
        }
      }
    }
    return false;
  }
}
