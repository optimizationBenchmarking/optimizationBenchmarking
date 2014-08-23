package org.optimizationBenchmarking.utils.document.spec;

import java.nio.file.Path;

/**
 * An interface which is used to notify the owner of a
 * {@link org.optimizationBenchmarking.utils.document.spec.Graphic} about
 * what's going on.
 */
public interface IGraphicListener {

  /**
   * The graphic has been closed
   * 
   * @param graphic
   *          the graphic which has been closed
   * @param path
   *          the path under which the graphic was stored, or {@code null}
   *          if no explicit file storage was created/necessary
   */
  public abstract void onClosed(final Graphic graphic, final Path path);

}
