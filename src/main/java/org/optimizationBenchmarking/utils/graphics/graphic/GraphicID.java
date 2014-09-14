package org.optimizationBenchmarking.utils.graphics.graphic;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.optimizationBenchmarking.utils.ErrorUtils;

/** A unique identifier for a graphic. */
public class GraphicID {

  /** the driver */
  final IGraphicDriver m_driver;

  /** the path of the graphic */
  final Path m_path;

  /** the id has been used */
  private volatile boolean m_used;

  /**
   * Create an identifier for a graphic object
   * 
   * @param driver
   *          the graphic driver
   * @param path
   *          the path
   */
  public GraphicID(final IGraphicDriver driver, final Path path) {
    super();

    if (path == null) {
      throw new IllegalArgumentException("Path must not be null."); //$NON-NLS-1$
    }
    if (driver == null) {
      throw new IllegalArgumentException("Driver must not be null."); //$NON-NLS-1$
    }
    this.m_path = path;
    this.m_driver = driver;
  }

  /**
   * get the graphic driver which created this id.
   * 
   * @return the graphic driver which created this id
   */
  public final IGraphicDriver getDriver() {
    return this.m_driver;
  }

  /** use this id */
  synchronized final void _use() {
    if (this.m_used) {
      throw new IllegalStateException(//
          "A graphics ID may be used only by one single graphic."); //$NON-NLS-1$
    }
    this.m_used = true;
  }

  /**
   * Get the path of the graphics object
   * 
   * @return the path of the graphics object
   */
  public final Path getPath() {
    return this.m_path;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return super.hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return (this == o);
  }

  /**
   * Get an output stream representing the path identified by graphic id
   * 
   * @return the output stream
   */
  final OutputStream _createOutputStream() {
    final Path path;

    path = this.m_path;

    try {
      Files.createDirectories(path.getParent());

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
}
