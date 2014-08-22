package org.optimizationBenchmarking.utils.io.files;

import java.io.File;

/**
 * This file class always represents absolute files.
 */
class _AbsoluteFile extends File {
  /** The serial version uid. */
  private static final long serialVersionUID = 1;

  /**
   * Create a canonical file by specifying a canonical path.
   * 
   * @param filename
   *          The canonical path.
   */
  _AbsoluteFile(final String filename) {
    super(filename);
  }

  /**
   * Create a canonical file by specifying a canonical path.
   * 
   * @param parent
   *          the parent file
   * @param filename
   *          The canonical path.
   */
  _AbsoluteFile(final _AbsoluteFile parent, final String filename) {
    super(parent, filename);
  }

  /** {@inheritDoc} */
  @Override
  public _AbsoluteFile getParentFile() {
    final String s;

    s = this.getParent();
    return (s != null) ? new _AbsoluteFile(s) : null;
  }

  /** {@inheritDoc} */
  @Override
  public final _AbsoluteFile getAbsoluteFile() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final String getAbsolutePath() {
    return this.getPath();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isAbsolute() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return this.getAbsolutePath();
  }
}
