package org.optimizationBenchmarking.utils.graphics.graphic.impl.pgf;

import java.awt.Dimension;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.AbstractGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicBuilder;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.text.transformations.LaTeXCharTransformer;

/** A driver which creates a PGF graphic. */
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
    final double wd, hd;
    final Dimension dim;
    final ELength sizeUnit;
    final Path path;
    final PhysicalDimension size;
    final Logger logger;

    size = builder.getSize();
    sizeUnit = size.getUnit();
    wd = sizeUnit.convertTo(size.getWidth(), ELength.POINT);
    hd = sizeUnit.convertTo(size.getHeight(), ELength.POINT);
    dim = new Dimension();
    if ((wd <= 0d) || (wd >= Integer.MAX_VALUE) || (hd <= 0d)
        || (hd >= Integer.MAX_VALUE)
        || ((dim.width = ((int) (0.5d + wd))) <= 0)
        || ((dim.height = ((int) (0.5d + hd))) <= 0)) {
      throw new IllegalArgumentException("Invalid size " + size + //$NON-NLS-1$
          " translated to " + dim);//$NON-NLS-1$
    }

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
