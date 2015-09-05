package org.optimizationBenchmarking.utils.io;

import java.io.Serializable;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** class to host a file type */
public class FileType extends HashObject implements IFileType,
    Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the suffix */
  private final String m_suffix;

  /** the mime type */
  private final String m_mime;

  /** the name */
  private final String m_name;

  /**
   * Create
   *
   * @param suffix
   *          the file name suffix
   * @param mime
   *          the mime type
   * @param name
   *          the file's name
   */
  public FileType(final String suffix, final String mime, final String name) {
    super();

    String s;

    s = TextUtils.prepare(suffix);
    this.m_suffix = ((s != null) ? TextUtils.toLowerCase(s) : s);

    this.m_mime = TextUtils.prepare(mime);

    s = TextUtils.prepare(name);
    if (s == null) {
      s = "Unknown File Type"; //$NON-NLS-1$
      if (this.m_suffix != null) {
        s += " with Extension '" + this.m_suffix + '\'';//$NON-NLS-1$
      }
    }
    this.m_name = s;

    FileTypeRegistry.getInstance().register(this);
  }

  /** {@inheritDoc} */
  @Override
  public final String getDefaultSuffix() {
    return this.m_suffix;
  }

  /** {@inheritDoc} */
  @Override
  public final String getMIMEType() {
    return this.m_mime;
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return this.m_name;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(
        HashUtils.combineHashes(HashUtils.hashCode(this.m_name),
            HashUtils.hashCode(this.m_mime)),
        HashUtils.hashCode(this.m_suffix));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object other) {
    final IFileType ft;

    if (other == this) {
      return true;
    }
    if (other instanceof IFileType) {
      ft = ((IFileType) other);
      return (EComparison.equals(this.m_suffix, ft.getDefaultSuffix())
          && EComparison.equals(this.m_name, ft.getName()) && //
      EComparison.equals(this.m_mime, ft.getMIMEType()));
    }
    return false;
  }
}