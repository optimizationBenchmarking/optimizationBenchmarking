package org.optimizationBenchmarking.utils.math.mathEngine.impl.R;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * A scope within {@code R}
 */
abstract class _RScope extends HierarchicalFSM {

  /** the {@code R} engine */
  final REngine m_engine;

  /**
   * create the {@code R}-scope
   * 
   * @param owner
   *          the owner
   * @param engine
   *          the engine
   */
  _RScope(final _RScope owner, final REngine engine) {
    super(owner);

    if (engine != null) {
      this.m_engine = engine;
    } else {
      if (owner != null) {
        this.m_engine = owner.m_engine;
      } else {
        throw new IllegalArgumentException(
            "REngine and owner cannot both be null."); //$NON-NLS-1$
      }
    }
  }

  /**
   * A given path must be deleted once the end of the owning scope is
   * reached
   * 
   * @param path
   *          the path to delete
   */
  @SuppressWarnings("resource")
  void _markDelete(final Path path) {
    _RScope owner;

    owner = ((_RScope) (this.getOwner()));
    if (owner != null) {
      owner._markDelete(path);
    } else {
      throw new IllegalStateException(
          "Top level R scope with no idea how to delete path '" //$NON-NLS-1$
              + path + '\'');
    }
  }
}
