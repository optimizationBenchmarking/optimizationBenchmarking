package org.optimizationBenchmarking.utils.graphics.style.color;

import java.net.URI;

import org.optimizationBenchmarking.utils.io.IFileType;

/**
 * The internal class for the XML constants of the color palette XML
 * format.
 */
public enum ColorPaletteXML implements IFileType {

  /** the color palette file type */
  COLOR_PALETTE_XML;

  /** the namespace */
  public static final URI NAMESPACE_URI = URI
      .create(//
          "http://www.optimizationBenchmarking.org/formats/graphics/color/colorPalette.1.0.xsd").normalize(); //$NON-NLS-1$
  /** the namespace string */
  public static final String NAMESPACE = ColorPaletteXML.NAMESPACE_URI
      .toString();
  /** the schema name */
  public static final String SCHEMA = ColorPaletteXML.NAMESPACE
      .substring(ColorPaletteXML.NAMESPACE.lastIndexOf('/') + 1);

  /** the color attribute name */
  static final String ATTRIBUTE_NAME = "name"; //$NON-NLS-1$
  /** the rgb attribute name */
  static final String ATTRIBUTE_RGB = "rgb"; //$NON-NLS-1$

  /** the color element */
  static final String ELEMENT_COLOR = "color"; //$NON-NLS-1$
  /** the color palette element */
  static final String ELEMENT_COLOR_PALETTE = "colorPalette"; //$NON-NLS-1$

  /** {@inheritDoc} */
  @Override
  public final String getDefaultSuffix() {
    return "colorPalette"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final String getMIMEType() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return "Color Palette File"; //$NON-NLS-1$
  }

}