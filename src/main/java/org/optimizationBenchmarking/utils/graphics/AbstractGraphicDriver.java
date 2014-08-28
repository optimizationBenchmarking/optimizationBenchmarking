package org.optimizationBenchmarking.utils.graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Dimension2D;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.utils.graphics.IGraphicDriver}
 * interface.
 */
public abstract class AbstractGraphicDriver implements IGraphicDriver {

  /** the file suffix */
  private final String m_suffix;

  /**
   * instantiate
   * 
   * @param suffix
   *          the file suffix
   */
  protected AbstractGraphicDriver(final String suffix) {
    super();
    this.m_suffix = ((suffix.charAt(0) == '.') ? suffix : ('.' + suffix));
  }

  /** {@inheritDoc} */
  @Override
  public GraphicID createGraphicID(final Path suggestion) {
    String sfn;
    Path path;
    final int sfnLen, sfxLen;
    boolean checkSuffix;

    path = suggestion.normalize().toAbsolutePath();

    checkSuffix = true;
    if (Files.isDirectory(path)) {
      try {
        path = Files.createTempFile(path, "auto", //$NON-NLS-1$
            this.m_suffix).normalize().toAbsolutePath();
        checkSuffix = false;
      } catch (final Throwable t) {
        ErrorUtils.throwAsRuntimeException(t);
      }
    }

    if (checkSuffix) {
      sfn = path.getFileName().toString();
      sfnLen = sfn.length();
      sfxLen = this.m_suffix.length();

      attach: {
        if (sfnLen > sfxLen) {
          if (this.m_suffix.equalsIgnoreCase(sfn
              .substring(sfnLen - sfxLen))) {
            break attach;
          }
        }

        path = path.getParent().resolve(sfn + this.m_suffix).normalize()
            .toAbsolutePath();
      }
    }

    return new GraphicID(this, path);
  }

  /**
   * Get the file extension (suffix) associated with this graphics driver.
   * The suffix always starts with a dot ({@code .}).
   * 
   * @return the suffix which will be appended to all paths created by
   *         {@link #createGraphicID(Path)}, unless the input path already
   *         has this suffix (case-insensitive)
   */
  public final String getSuffix() {
    return this.m_suffix;
  }

  /**
   * This method does the actual work of creating a graphic. It is called
   * by
   * {@link #createGraphic(GraphicID, Dimension2D, ELength, IGraphicListener)}
   * which checks all arguments.
   * 
   * @param id
   *          the destination id, <em>must</em> be the result of a call to
   *          {@link #createGraphicID(Path)}
   * @param size
   *          the size of the graphic, in unit {@code sizeUnit}
   * @param sizeUnit
   *          the unit in which the values in {@code size} are specified
   * @param listener
   *          the listener interface to be notified when the graphic is
   *          closed
   * @return the graphic object
   * @see #createGraphicID(Path)
   * @see #createGraphic(GraphicID, Dimension2D, ELength, IGraphicListener)
   */
  protected abstract Graphic doCreateGraphic(GraphicID id,
      Dimension2D size, ELength sizeUnit, IGraphicListener listener);

  /** {@inheritDoc} */
  @Override
  public final Graphic createGraphic(final GraphicID id,
      final Dimension2D size, final ELength sizeUnit,
      final IGraphicListener listener) {
    final double w, h;
    double r;

    if (id == null) {
      throw new IllegalArgumentException(//
          "Graphic ID must not be null."); //$NON-NLS-1$
    }
    if ((size == null) || ((h = size.getHeight()) <= 0d)
        || ((w = size.getWidth()) <= 0d)) {
      throw new IllegalArgumentException(//
          "Invalid graphic size: " + size);//$NON-NLS-1$
    }
    if (sizeUnit == null) {
      throw new IllegalArgumentException(//
          "Size unit must not be null."); //$NON-NLS-1$
    }

    r = sizeUnit.convertTo(w, ELength.M);
    if ((r <= 1e-4) || (r >= 10d)) {
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

    if (id.m_driver != this) {
      throw new IllegalArgumentException(//
          "Cannot use graphic id '" + id //$NON-NLS-1$
              + " with driver " + this);//$NON-NLS-1$
    }

    try {
      Files.createDirectories(id.m_path.getParent());
    } catch (final Throwable t) {
      ErrorUtils.throwAsRuntimeException(t);
    }

    return this.doCreateGraphic(id, size, sizeUnit, listener);
  }

  /**
   * Get an output stream representing the path identified by graphic id
   * 
   * @param id
   *          the graphic id
   * @return the output stream
   */
  protected static final OutputStream createOutputStream(final GraphicID id) {
    final Path path;

    path = id.m_path;
    try {
      return path
          .getFileSystem()
          .provider()
          .newOutputStream(path, StandardOpenOption.WRITE,
              StandardOpenOption.CREATE,
              StandardOpenOption.TRUNCATE_EXISTING);
    } catch (final Throwable t) {
      ErrorUtils.throwAsRuntimeException(t);
      return null;// will never be reached
    }
  }

  /**
   * Set the default rendering hints
   * 
   * @param g
   *          the graphic to initialize
   */
  protected static final void setDefaultRenderingHints(final Graphics2D g) {
    g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
        RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
        RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_RENDERING,
        RenderingHints.VALUE_RENDER_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
        RenderingHints.VALUE_STROKE_NORMALIZE);
    g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_DITHERING,
        RenderingHints.VALUE_DITHER_ENABLE);
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
  }
}
