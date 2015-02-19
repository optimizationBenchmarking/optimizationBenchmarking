package org.optimizationBenchmarking.utils.graphics.style.stroke;

import java.net.URI;

import org.optimizationBenchmarking.utils.io.IFileType;

/**
 * The internal class for the XML constants of the stroke palette XML
 * format.
 */
public enum StrokePaletteXML implements IFileType {

  /** the stroke palette file type */
  STROKE_PALETTE_XML;

  /** the namespace */
  public static final URI NAMESPACE_URI = URI
      .create(//
          "http://www.optimizationBenchmarking.org/formats/graphics/stroke/strokePalette.1.0.xsd").normalize(); //$NON-NLS-1$
  /** the namespace string */
  public static final String NAMESPACE = StrokePaletteXML.NAMESPACE_URI
      .toString();
  /** the schema name */
  public static final String SCHEMA = StrokePaletteXML.NAMESPACE
      .substring(StrokePaletteXML.NAMESPACE.lastIndexOf('/') + 1);

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
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return "Stroke Palette File"; //$NON-NLS-1$
  }

}