package org.optimizationBenchmarking.utils.graphics.style.stroke;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.optimizationBenchmarking.utils.io.xml.IXMLFileType;
import org.optimizationBenchmarking.utils.io.xml.XMLFileType;

/**
 * The internal class for the constants of the stroke palette file format.
 */
public enum StrokePaletteXML implements IXMLFileType {

  /** the stroke palette file type */
  STROKE_PALETTE_XML;

  /** the namespace */
  static final URI NAMESPACE_URI = URI
      .create(//
          "http://www.optimizationBenchmarking.org/formats/graphics/stroke/strokePalette.1.0.xsd").normalize(); //$NON-NLS-1$
  /** the namespace string */
  static final String NAMESPACE = StrokePaletteXML.NAMESPACE_URI
      .toString();

  /** the stroke attribute name */
  static final String ATTRIBUTE_NAME = "name"; //$NON-NLS-1$
  /** the stroke's thickness */
  static final String ATTRIBUTE_THICKNESS = "thickness"; //$NON-NLS-1$
  /** the stroke's dash component */
  static final String ATTRIBUTE_DASH = "dash"; //$NON-NLS-1$

  /** the normal stroke element */
  static final String ELEMENT_DEFAULT_STROKE = "defaultStroke"; //$NON-NLS-1$
  /** the thin stroke element */
  static final String ELEMENT_THIN_STROKE = "thinStroke"; //$NON-NLS-1$
  /** the thick stroke element */
  static final String ELEMENT_THICK_STROKE = "thickStroke"; //$NON-NLS-1$
  /** the stroke element */
  static final String ELEMENT_STROKE = "stroke"; //$NON-NLS-1$
  /** the stroke palette element */
  static final String ELEMENT_STROKE_PALETTE = "strokePalette"; //$NON-NLS-1$

  /** {@inheritDoc} */
  @Override
  public final String getDefaultSuffix() {
    return "strokePalette"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final String getMIMEType() {
    return XMLFileType.XML.getMIMEType();
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return "Stroke Palette File"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final URI getNamespaceURI() {
    return StrokePaletteXML.NAMESPACE_URI;
  }

  /** {@inheritDoc} */
  @Override
  public final String getNamespace() {
    return StrokePaletteXML.NAMESPACE;
  }

  /** {@inheritDoc} */
  @Override
  public final URL getSchemaSource() throws IOException {
    final URL u;
    u = this.getClass().getResource(//
        StrokePaletteXML.NAMESPACE.substring(StrokePaletteXML.NAMESPACE
            .lastIndexOf('/') + 1));
    if (u != null) {
      return u;
    }
    return StrokePaletteXML.NAMESPACE_URI.toURL();
  }
}