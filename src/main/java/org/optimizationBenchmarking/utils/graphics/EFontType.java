package org.optimizationBenchmarking.utils.graphics;

import java.awt.Font;

import org.optimizationBenchmarking.utils.io.IFileType;

/** The available font types */
public enum EFontType implements IFileType {

  /** true type fonts */
  TRUE_TYPE(Font.TRUETYPE_FONT,//
      "True Type Font", //$NON-NLS-1$
      "ttf",//$NON-NLS-1$
      "application/x-font-ttf"), //$NON-NLS-1$

  /** type 1 fonts */
  TYPE_1(Font.TYPE1_FONT,//
      "Type 1 Font", //$NON-NLS-1$
      "pfb",//$NON-NLS-1$
      null);

  /** the type ID */
  private final int m_typeID;

  /** the type name */
  private final String m_name;

  /** the file suffix */
  private final String m_suffix;

  /** the mime type */
  private final String m_mime;

  /**
   * create the font type
   *
   * @param typeID
   *          the type id
   * @param name
   *          the name
   * @param suffix
   *          the suffix
   * @param mime
   *          the mime type
   */
  EFontType(final int typeID, final String name, final String suffix,
      final String mime) {
    this.m_typeID = typeID;
    this.m_name = name;
    this.m_suffix = suffix;
    this.m_mime = mime;
  }

  /**
   * Get the font type understood by
   * {@link java.awt.Font#createFont(int, java.io.InputStream)}
   *
   * @return the font type understood by
   *         {@link java.awt.Font#createFont(int, java.io.InputStream)}
   */
  public final int getJavaFontType() {
    return this.m_typeID;
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
}
