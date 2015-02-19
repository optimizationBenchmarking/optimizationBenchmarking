package org.optimizationBenchmarking.utils.graphics.style.font;

import java.net.URI;

import org.optimizationBenchmarking.utils.graphics.style.EFontFamily;
import org.optimizationBenchmarking.utils.io.IFileType;

/**
 * The internal class for the XML constants of the font palette XML format.
 */
public enum FontPaletteXML implements IFileType {

  /** the font palette file type */
  FONT_PALETTE_XML;

  /** the namespace */
  public static final URI NAMESPACE_URI = URI
      .create(//
          "http://www.optimizationBenchmarking.org/formats/graphics/font/fontPalette.1.0.xsd").normalize(); //$NON-NLS-1$
  /** the namespace string */
  public static final String NAMESPACE = FontPaletteXML.NAMESPACE_URI
      .toString();
  /** the schema name */
  public static final String SCHEMA = FontPaletteXML.NAMESPACE
      .substring(FontPaletteXML.NAMESPACE.lastIndexOf('/') + 1);

  /** the font family name attribute */
  static final String ATTRIBUTE_FONT_FAMILY = "family"; //$NON-NLS-1$
  /** the font family size attribute */
  static final String ATTRIBUTE_FONT_SIZE = "size"; //$NON-NLS-1$
  /** the font bold attribute */
  static final String ATTRIBUTE_BOLD = "bold"; //$NON-NLS-1$
  /** the font italic attribute */
  static final String ATTRIBUTE_ITALIC = "italic"; //$NON-NLS-1$
  /** the font underlined attribute */
  static final String ATTRIBUTE_UNDERLINED = "underlined"; //$NON-NLS-1$

  /** the normal font element */
  static final String ELEMENT_DEFAULT_FONT = "defaultFont"; //$NON-NLS-1$
  /** the emphasized font element */
  static final String ELEMENT_EMPHASIZED_FONT = "emphasizedFont"; //$NON-NLS-1$
  /** the code font element */
  static final String ELEMENT_CODE_FONT = "codeFont"; //$NON-NLS-1$
  /** the font element */
  static final String ELEMENT_FONT = "font"; //$NON-NLS-1$
  /** the font face choice element */
  static final String ELEMENT_FONT_FACE = "face"; //$NON-NLS-1$
  /** the font palette root element */
  static final String ELEMENT_FONT_PALETTE = "fontPalette"; //$NON-NLS-1$

  /** the font families */
  static final String[] FONT_FAMILY_NAMES = {//
  "serif", //$NON-NLS-1$
      "sansSerif",//$NON-NLS-1$
      "monospaced"//$NON-NLS-1$
  };
  /** the font family values */
  static final EFontFamily[] FONT_FAMILY_VALUES = { //
  EFontFamily.SERIF,//
      EFontFamily.SANS_SERIF,//
      EFontFamily.MONOSPACED };

  /** {@inheritDoc} */
  @Override
  public final String getDefaultSuffix() {
    return "fontPalette"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final String getMIMEType() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return "Font Palette File"; //$NON-NLS-1$
  }

}