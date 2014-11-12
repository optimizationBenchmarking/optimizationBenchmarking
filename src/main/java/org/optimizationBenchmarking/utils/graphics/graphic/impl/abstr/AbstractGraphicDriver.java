package org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr;

import java.io.OutputStream;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorPalette;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.graphics.style.stroke.DefaultStrokePalette;
import org.optimizationBenchmarking.utils.graphics.style.stroke.StrokePalette;
import org.optimizationBenchmarking.utils.io.path.FileTypeDriver;
import org.optimizationBenchmarking.utils.io.path.PathUtils;
import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver}
 * interface.
 */
public abstract class AbstractGraphicDriver extends FileTypeDriver
    implements IGraphicDriver {

  /**
   * instantiate
   * 
   * @param suffix
   *          the file suffix
   */
  protected AbstractGraphicDriver(final String suffix) {
    super(suffix);
  }

  /**
   * Create a graphics object with the size {@code size} in the length unit
   * {@code size.getUnit()}. If the resulting object is an object which
   * writes contents to a file, then it will write its contents to a file
   * specified by {@code path}. Once the graphic is
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic#close()
   * closed}, it will notify the provided {@code listener} interface
   * (unless {@code listener==null}).
   * 
   * @param path
   *          the path
   * @param os
   *          the output stream to write to
   * @param size
   *          the size of the graphic
   * @param listener
   *          the listener interface to be notified when the graphic is
   *          closed
   * @return the graphic object
   */
  protected abstract Graphic doCreateGraphic(final Path path,
      final OutputStream os, final PhysicalDimension size,
      final IObjectListener listener);

  /** {@inheritDoc} */
  @Override
  public Graphic createGraphic(final Path folder,
      final String nameSuggestion, final PhysicalDimension size,
      final IObjectListener listener) {
    final double w, h;
    final ELength sizeUnit;
    final Path p;
    double r;

    if ((size == null) || ((h = size.getHeight()) <= 0d)
        || ((w = size.getWidth()) <= 0d)) {
      throw new IllegalArgumentException(//
          "Invalid graphic size: " + size);//$NON-NLS-1$
    }

    sizeUnit = size.getUnit();
    r = sizeUnit.convertTo(w, ELength.M);
    if ((r <= 1e-4d) || (r >= 10d)) {
      throw new IllegalArgumentException(//
          "A graphic width cannot be smaller than 0.1mm or larger than 10m, but "//$NON-NLS-1$
              + w + " specified in " + sizeUnit + //$NON-NLS-1$ 
              " equals " + r + //$NON-NLS-1$
              "m.");//$NON-NLS-1$
    }

    r = sizeUnit.convertTo(h, ELength.M);
    if ((r <= 1e-4) || (r >= 10d)) {
      throw new IllegalArgumentException(//
          "A graphic height cannot be smaller than 0.1mm or larger than 10m, but "//$NON-NLS-1$
              + h + " specified in " + sizeUnit + //$NON-NLS-1$ 
              " equals " + r + //$NON-NLS-1$
              "m.");//$NON-NLS-1$
    }

    p = this.makePath(folder, nameSuggestion);

    return this.doCreateGraphic(p, PathUtils.openOutputStream(p), size,
        listener);
  }

  /** {@inheritDoc} */
  @Override
  public final Graphic createGraphic(final OutputStream os,
      final PhysicalDimension size, final IObjectListener listener) {
    return this.doCreateGraphic(null, os, size, listener);
  }

  /** {@inheritDoc} */
  @Override
  public ColorPalette getColorPalette() {
    return EColorModel.RBGA_32_BIT.getDefaultPalette();
  }

  /** {@inheritDoc} */
  @Override
  public StrokePalette getStrokePalette() {
    return DefaultStrokePalette.getInstance();
  }

}
