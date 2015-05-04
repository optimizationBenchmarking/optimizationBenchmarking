package org.optimizationBenchmarking.utils.tools.impl.abstr;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;

/**
 * This class holds a set of produced files.
 */
abstract class _FileSet extends FileCollector {

  /** create the set of produced files */
  _FileSet() {
    super();
  }

  /**
   * Check whether a given path has already been added
   *
   * @param path
   *          the path to be added
   * @return the type of the path, or {@code null} if none exists
   */
  abstract IFileType _getPathType(final Path path);

  /**
   * Do the work of adding a file
   *
   * @param path
   *          the path
   * @param type
   *          the type
   */
  abstract void _addFile(Path path, IFileType type);

  /** {@inheritDoc} */
  @Override
  public final void addFile(final Path path, final IFileType type) {
    final Path normalized;
    final IFileType oldType;

    if (path == null) {
      throw new IllegalArgumentException("Path of type '" //$NON-NLS-1$
          + type + //
          "' cannot be null."); //$NON-NLS-1$
    }

    if (type == null) {
      throw new IllegalArgumentException("Type of path '"//$NON-NLS-1$
          + type + //
          "' cannot be null."); //$NON-NLS-1$
    }

    normalized = PathUtils.normalize(path);
    if (normalized == null) {
      throw new IllegalArgumentException(//
          "Path '" + path + //$NON-NLS-1$
              "' of type " + type + //$NON-NLS-1$
              " normalizes to null."); //$NON-NLS-1$
    }

    if (!(Files.isRegularFile(normalized))) {
      if (Files.exists(normalized)) {
        throw new IllegalArgumentException("Path '" //$NON-NLS-1$
            + normalized + //
            "' is not a regular file.");//$NON-NLS-1$
      }
      throw new IllegalArgumentException("Path '" //$NON-NLS-1$
          + normalized + //
          "' does not exist.");//$NON-NLS-1$
    }

    synchronized (this) {
      oldType = this._getPathType(normalized);
      if (oldType == null) {
        this._addFile(normalized, type);
        return;
      }
    }

    throw new IllegalStateException(((((("Path '" + normalized) + //$NON-NLS-1$
        "' is already associated with type ") + oldType) //$NON-NLS-1$
        + " and cannot be associated a second time (to type ") + //$NON-NLS-1$
        type + '\'') + '.');
  }

  /** {@inheritDoc} */
  @Override
  public final void addFile(final Entry<Path, IFileType> p) {
    if (p == null) {
      throw new IllegalArgumentException(//
          "Cannot add null path entry."); //$NON-NLS-1$
    }
    this.addFile(p.getKey(), p.getValue());
  }

  /** {@inheritDoc} */
  @Override
  public final void addFiles(final Iterable<Map.Entry<Path, IFileType>> ps) {
    if (ps == null) {
      throw new IllegalArgumentException(//
          "Cannot add the elements of a null iterable.");//$NON-NLS-1$
    }
    synchronized (this) {
      for (final Map.Entry<Path, IFileType> p : ps) {
        this.addFile(p);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void onFilesFinalized(
      final Collection<Entry<Path, IFileType>> result) {
    this.addFiles(result);
  }

}
