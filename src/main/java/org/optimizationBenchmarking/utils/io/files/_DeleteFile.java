package org.optimizationBenchmarking.utils.io.files;

import java.io.File;

import org.optimizationBenchmarking.utils.collections.visitors.IVisitor;

/** a file processor <@javaAutorVersion/> */
final class _DeleteFile implements IVisitor<File> {

  /** the owner */
  private final DeleteFile m_owner;

  /**
   * create
   * 
   * @param owner
   *          the owner
   */
  _DeleteFile(final DeleteFile owner) {
    super();
    this.m_owner = owner;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean visit(final File object) {
    try {
      object.delete();
    } catch (final Exception tt) {
      if (this.m_owner.m_error == null) {
        this.m_owner.m_error = tt;
      }
      if (this.m_owner.m_failFast) {
        return false;
      }
      try {
        object.deleteOnExit();
      } catch (final Throwable ttt) {
        // ignore
      }
    }

    return true;
  }
}
