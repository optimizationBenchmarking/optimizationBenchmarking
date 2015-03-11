package org.optimizationBenchmarking.utils.graphics.style.color;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.optimizationBenchmarking.utils.io.xml.IXMLFileType;
import org.optimizationBenchmarking.utils.io.xml.XMLFileType;

/**
 * The internal class for the constants of the color palette file type
 * format.
 */
public enum ColorPaletteXML implements IXMLFileType {

  /** the color palette file type */
  COLOR_PALETTE_XML;

  /** the namespace */
  static final URI NAMESPACE_URI = URI
      .create(//
          "http://www.optimizationBenchmarking.org/formats/graphics/color/colorPalette.1.0.xsd").normalize(); //$NON-NLS-1$
  /** the namespace string */
  static final String NAMESPACE = ColorPaletteXML.NAMESPACE_URI.toString();

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
    return XMLFileType.XML.getMIMEType();
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return "Color Palette File"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final URI getNamespaceURI() {
    return ColorPaletteXML.NAMESPACE_URI;
  }

  /** {@inheritDoc} */
  @Override
  public final String getNamespace() {
    return ColorPaletteXML.NAMESPACE;
  }

  /** {@inheritDoc} */
  @Override
  public final URL getSchemaSource() throws IOException {
    final URL u;
    u = this.getClass().getResource(//
        ColorPaletteXML.NAMESPACE.substring(ColorPaletteXML.NAMESPACE
            .lastIndexOf('/') + 1));
    if (u != null) {
      return u;
    }
    return ColorPaletteXML.NAMESPACE_URI.toURL();
  }
}