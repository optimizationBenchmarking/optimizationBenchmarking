package org.optimizationBenchmarking.utils.graphics.graphic.impl.text;

import java.awt.Dimension;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.AbstractGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicBuilder;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.text.transformations.LaTeXCharTransformer;

/**
 * A driver driver for creating graphics which do not perform any actual
 * rendering. Instead, they implements the
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 * interface. This is somewhat of a hack to allow specialized chart drivers
 * to print their contents to a text file instead of actually producing a
 * graphic.
 */
public final class TextGraphicDriver extends AbstractGraphicDriver {

  /** create */
  TextGraphicDriver() {
    super(EGraphicFormat.TEXT);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    try {
      return (LaTeXCharTransformer.getInstance() != null);
    } catch (final Throwable error) {
      return false;
    }
  }

  /**
   * get the instance of the FreeHEP PDF driver
   *
   * @return the instance of the FreeHEP PDF driver
   */
  public static final TextGraphicDriver getInstance() {
    return __TextGraphicDriverLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Text Driver"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  protected final Graphic createGraphic(final GraphicBuilder builder) {
    final Dimension dim;
    final Path path;
    final Logger logger;

    dim = AbstractGraphicDriver.getIntegerSizeInPoints(builder.getSize());

    path = this.makePath(builder.getBasePath(),
        builder.getMainDocumentNameSuggestion());
    logger = builder.getLogger();

    return new _TextGraphic(logger, builder.getFileProducerListener(),
        path, dim.width, dim.height);
  }

  /** the loader */
  static final class __TextGraphicDriverLoader {
    /** the globally shared instance of the text graphic driver */
    static final TextGraphicDriver INSTANCE = new TextGraphicDriver();

  }
}
