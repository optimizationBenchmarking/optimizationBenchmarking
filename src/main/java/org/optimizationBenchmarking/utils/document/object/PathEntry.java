package org.optimizationBenchmarking.utils.document.object;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.collections.ImmutableAssociation;
import org.optimizationBenchmarking.utils.io.path.PathUtils;

/**
 * An entry associating a path with a path type key.
 */
public class PathEntry extends ImmutableAssociation<Object, Path> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Create the new path entry
   * 
   * @param key
   *          the path entry key
   * @param path
   *          the path entry
   */
  public PathEntry(final Object key, final Path path) {
    super(key, PathUtils.normalize(path));
  }

}
