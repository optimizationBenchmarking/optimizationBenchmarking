package org.optimizationBenchmarking.utils.graphics.style.font;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.optimizationBenchmarking.utils.graphics.EFontFamily;
import org.optimizationBenchmarking.utils.graphics.EFontType;
import org.optimizationBenchmarking.utils.io.xml.IXMLFileType;
import org.optimizationBenchmarking.utils.io.xml.XMLFileType;

/**
 * The internal class for the constants of the font palette file format.
 */
public enum FontPaletteXML implements IXMLFileType {

  /** the font palette file type */
  FONT_PALETTE_XML;

  /** the namespace */
  static final URI NAMESPACE_URI = URI
      .create(//
          "http://www.optimizationBenchmarking.org/formats/graphics/font/fontPalette.1.0.xsd").normalize(); //$NON-NLS-1$
  /** the namespace string */
  static final String NAMESPACE = FontPaletteXML.NAMESPACE_URI.toString();

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

  /** a resource providing the font */
  static final String ATTRIBUTE_RESOURCE = "resource";//$NON-NLS-1$
  /** the font type */
  static final String ATTRIBUTE_TYPE = "type";//$NON-NLS-1$

  /** the font families */
  private static final String[] FONT_FAMILY_NAMES = {//
  "serif", //$NON-NLS-1$
      "sansSerif",//$NON-NLS-1$
      "monospaced"//$NON-NLS-1$
  };

  /** the font family values */
  private static final EFontFamily[] FONT_FAMILY_VALUES = { //
  EFontFamily.SERIF,//
      EFontFamily.SANS_SERIF,//
      EFontFamily.MONOSPACED };

  /** the font types */
  private static final String[] FONT_TYPE_NAMES = {//
  "trueType", //$NON-NLS-1$
      "type1",//$NON-NLS-1$
  };

  /** the font type values */
  private static final EFontType[] FONT_TYPE_VALUES = { //
  EFontType.TRUE_TYPE,//
      EFontType.TYPE_1 };

  /**
   * Parse the font family
   *
   * @param string
   *          the font family string
   * @return the font family
   */
  static final EFontFamily _parseFontFamily(final String string) {
    int i;

    if (string == null) {
      throw new IllegalArgumentException(//
          "Must specify font family."); //$NON-NLS-1$
    }

    for (i = FontPaletteXML.FONT_FAMILY_NAMES.length; (--i) >= 0;) {
      if (FontPaletteXML.FONT_FAMILY_NAMES[i].equalsIgnoreCase(string)) {
        return FontPaletteXML.FONT_FAMILY_VALUES[i];
      }
    }

    throw new IllegalArgumentException(//
        "Unknown font family: " + string); //$NON-NLS-1$
  }

  /**
   * Parse the font type
   *
   * @param string
   *          the font type string
   * @return the font type, or {@code null} if no type was specified
   */
  static final EFontType _parseFontType(final String string) {
    int i;

    if (string == null) {
      return null;
    }

    for (i = FontPaletteXML.FONT_TYPE_NAMES.length; (--i) >= 0;) {
      if (FontPaletteXML.FONT_TYPE_NAMES[i].equalsIgnoreCase(string)) {
        return FontPaletteXML.FONT_TYPE_VALUES[i];
      }
    }

    throw new IllegalArgumentException(//
        "Unknown font type: " + string); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final String getDefaultSuffix() {
    return "fontPalette"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final String getMIMEType() {
    return XMLFileType.XML.getMIMEType();
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return "Font Palette File"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final URI getNamespaceURI() {
    return FontPaletteXML.NAMESPACE_URI;
  }

  /** {@inheritDoc} */
  @Override
  public final String getNamespace() {
    return FontPaletteXML.NAMESPACE;
  }

  /** {@inheritDoc} */
  @Override
  public final URL getSchemaSource() throws IOException {
    final URL u;
    u = this.getClass().getResource(//
        FontPaletteXML.NAMESPACE.substring(FontPaletteXML.NAMESPACE
            .lastIndexOf('/') + 1));
    if (u != null) {
      return u;
    }
    return FontPaletteXML.NAMESPACE_URI.toURL();
  }
}