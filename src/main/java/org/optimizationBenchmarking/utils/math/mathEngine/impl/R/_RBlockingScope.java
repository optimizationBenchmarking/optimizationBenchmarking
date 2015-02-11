package org.optimizationBenchmarking.utils.math.mathEngine.impl.R;

import java.nio.file.Path;
import java.util.ArrayList;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;

/**
 * A scope within {@code R} which, upon {@link #close()}, blocks until all
 * nested scopes have finished their computation. Thus, it can be used to
 * prune temporary files created by its sub-scopes after that.
 */
abstract class _RBlockingScope extends _RScope {

  /** a list of paths that must be deleted */
  private ArrayList<Path> m_mustDelete;

  /**
   * create the {@code R}-scope
   * 
   * @param owner
   *          the owner
   * @param engine
   *          the engine
   */
  _RBlockingScope(final _RBlockingScope owner, final REngine engine) {
    super(owner, engine);
  }

  /** {@inheritDoc} */
  @Override
  final void _markDelete(final Path path) {
    if (path != null) {
      if (this.m_mustDelete == null) {
        this.m_mustDelete = new ArrayList<>();
      }
      this.m_mustDelete.add(path);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    Throwable error;

    error = null;
    try {
      if (this.m_mustDelete != null) {
        try {
          for (final Path p : this.m_mustDelete) {
            try {
              PathUtils.delete(p);
            } catch (final Throwable t) {
              error = ErrorUtils.aggregateError(error, t);
            }
          }
        } finally {
          this.m_mustDelete = null;
        }
      }
    } finally {
      try {
        super.onClose();
      } catch (final Throwable t) {
        error = ErrorUtils.aggregateError(error, t);
      }
    }

    if (error != null) {
      this.m_engine._handleError(error);
    }
  }
}
