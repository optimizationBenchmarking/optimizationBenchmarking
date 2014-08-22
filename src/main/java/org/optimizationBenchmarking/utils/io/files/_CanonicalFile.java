package org.optimizationBenchmarking.utils.io.files;

/**
 * This file class always represents canonical files.
 */
final class _CanonicalFile extends _AbsoluteFile {
  /** The serial version uid. */
  private static final long serialVersionUID = 1;

  /**
   * Create a canonical file by specifying a canonical path.
   * 
   * @param filename
   *          The canonical path.
   */
  _CanonicalFile(final String filename) {
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
  private _CanonicalFile(final _CanonicalFile parent, final String filename) {
    super(parent, filename);
  }

  /** {@inheritDoc} */
  @Override
  public final _CanonicalFile getParentFile() {
    final String s;

    s = this.getParent();
    return (s != null) ? new _CanonicalFile(s) : null;
  }

  /** {@inheritDoc} */
  @Override
  public final _CanonicalFile getCanonicalFile() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final String getCanonicalPath() {
    return this.getPath();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return this.getCanonicalPath();
  }

}
