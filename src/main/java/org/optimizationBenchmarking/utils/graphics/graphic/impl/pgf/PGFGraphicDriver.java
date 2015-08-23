package org.optimizationBenchmarking.utils.graphics.graphic.impl.pgf;

import java.awt.Dimension;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.AbstractGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicBuilder;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.text.transformations.LaTeXCharTransformer;

/**
 * A driver driver for the Portable Graphic Format (
 * {@link org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat#PGF
 * PGF}) <sup><a href="http://en.wikipedia.org/wiki/PGF/TikZ">1</a>, <a
 * href="http://ctan.org/pkg/pgf">2</a></sup>. PGF is a <a
 * href="http://en.wikipedia.org/wiki/LaTeX">LaTeX</a> format, meaning that
 * such figures can readily be included into
 * {@link org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDriver
 * LaTeX} documents via {@code \input}.
 */
public final class PGFGraphicDriver extends AbstractGraphicDriver {

  /** create */
  PGFGraphicDriver() {
    super(EGraphicFormat.PGF);
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
  public static final PGFGraphicDriver getInstance() {
    return __PGFGraphicDriverLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "PGF Driver"; //$NON-NLS-1$
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

    try {
      return new _PGFProxy(new _PGFGraphic(logger,
          builder.getFileProducerListener(), path, dim.width, dim.height),
          logger);
    } catch (final Throwable error) {
      ErrorUtils.logError(logger, "Error while creating PGF graphic.", //$NON-NLS-1$
          error, false, RethrowMode.AS_RUNTIME_EXCEPTION);
    }
    return null;// will never be reached
  }

  /** the loader */
  static final class __PGFGraphicDriverLoader {
    /**
     * the globally shared instance of the <a
     * href="http://en.wikipedia.org/wiki/Portable_Document_Format">PDF</a>
     * graphic driver
     */
    static final PGFGraphicDriver INSTANCE = new PGFGraphicDriver();

  }
}
