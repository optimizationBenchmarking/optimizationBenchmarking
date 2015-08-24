package org.optimizationBenchmarking.experimentation.io.impl.csvedi;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;

import org.optimizationBenchmarking.experimentation.data.impl.flat.FlatExperimentSetContext;
import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSetContext;

/** The internal flat experiment set context for the CSV-EDI input driver */
final class _CSVEDIContext extends FlatExperimentSetContext {

  /** the files already done */
  private final HashSet<Object> m_done;

  /** did we already process the dimensions? */
  private volatile boolean m_hasDimensions;
  /** did we already process the instances? */
  private volatile boolean m_hasInstances;

  /**
   * create
   *
   * @param context
   *          the hierarchical context to wrap
   */
  _CSVEDIContext(final ExperimentSetContext context) {
    super(context);
    this.m_done = new HashSet<>();
  }

  /**
   * did we already process dimensions?
   *
   * @return {@code true} if we did, {@code false} otherwise
   */
  final boolean _hasDimensions() {
    return this.m_hasDimensions;
  }

  /** notify that we processed dimensions */
  final void _dimensionsFound() {
    this.m_hasDimensions = true;
  }

  /**
   * did we already process instances?
   *
   * @return {@code true} if we did, {@code false} otherwise
   */
  final boolean _hasInstances() {
    return this.m_hasInstances;
  }

  /** notify that we processed instances */
  final void _instancesFound() {
    this.m_hasInstances = true;
  }

  /**
   * Check if a path is new
   *
   * @param path
   *          the path
   * @param attrs
   *          the file attributes of the path
   * @return {@code true} if the path is new and worth exploring,
   *         {@code false} if it already has been explored
   */
  final boolean _isNew(final Path path, final BasicFileAttributes attrs) {
    final Object key;

    if (path == null) {
      throw new IllegalArgumentException("Path cannot be null."); //$NON-NLS-1$
    }
    if (attrs == null) {
      throw new IllegalArgumentException("File attributes cannot be null."); //$NON-NLS-1$
    }

    synchronized (this.m_done) {
      if (this.m_done.add(path)) {
        key = attrs.fileKey();
        if (key == null) {
          return true;
        }
        if (!(path.equals(key) || key.equals(path))) {
          return this.m_done.add(key);
        }
      }
    }

    return false;
  }
}
