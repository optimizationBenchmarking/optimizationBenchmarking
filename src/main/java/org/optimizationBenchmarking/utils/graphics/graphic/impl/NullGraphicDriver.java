package org.optimizationBenchmarking.utils.graphics.graphic.impl;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.AbstractGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorPalette;
import org.optimizationBenchmarking.utils.graphics.style.color.JavaDefaultPalette;
import org.optimizationBenchmarking.utils.graphics.style.stroke.DefaultStrokePalette;
import org.optimizationBenchmarking.utils.graphics.style.stroke.StrokePalette;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/** the null driver */
public final class NullGraphicDriver extends AbstractGraphicDriver {

  /** the null instance */
  NullGraphicDriver() {
    super(EGraphicFormat.NULL);
  }

  /**
   * Get the instance of the null graphics driver
   * 
   * @return the instance of the null graphics driver
   */
  public static final NullGraphicDriver getInstance() {
    return __NullGraphicDriverLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "NULL Graphics Driver"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  protected final Graphic createGraphic(final Logger logger,
      final IFileProducerListener listener, final Path basePath,
      final String mainDocumentNameSuggestion, final PhysicalDimension size) {
    return new _NullGraphic(logger, listener, size);
  }

  /** {@inheritDoc} */
  @Override
  protected final Path makePath(final Path basePath,
      final String nameSuggestion) {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final ColorPalette getColorPalette() {
    return JavaDefaultPalette.getInstance();
  }

  /** {@inheritDoc} */
  @Override
  public final StrokePalette getStrokePalette() {
    return DefaultStrokePalette.getInstance();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    textOut.append(this.toString());
  }

  /** the loader */
  private static final class __NullGraphicDriverLoader {

    /** the globally shared instance */
    static final NullGraphicDriver INSTANCE = new NullGraphicDriver();
  }
}
