package org.optimizationBenchmarking.utils.graphics.style.color;

import org.optimizationBenchmarking.utils.io.xml.DelegatingHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** a handler for color palette xml data */
public class ColorPaletteXMLHandler extends DelegatingHandler {

  /** the destination color palette builder */
  private final ColorPaletteBuilder m_dest;

  /**
   * Create
   * 
   * @param owner
   *          the owning handler, or {@code null} if not used
   * @param dest
   *          the color palette builder instance to load the data into
   */
  public ColorPaletteXMLHandler(final DelegatingHandler owner,
      final ColorPaletteBuilder dest) {
    super(owner);
    this.m_dest = dest;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doStartElement(final String uri,
      final String localName, final String qName,
      final Attributes attributes) throws SAXException {

    if ((uri == null) || (ColorPaletteXML.NAMESPACE.equalsIgnoreCase(uri))) {

      if (ColorPaletteXML.ELEMENT_COLOR.equalsIgnoreCase(localName)) {
        try (final ColorStyleBuilder builder = this.m_dest.add()) {

          builder.setName(//
              DelegatingHandler.getAttribute(
                  attributes,//
                  ColorPaletteXML.NAMESPACE,
                  ColorPaletteXML.ATTRIBUTE_NAME));

          builder.setRGB(Integer.parseInt(//
              DelegatingHandler
                  .getAttribute(
                      attributes,//
                      ColorPaletteXML.NAMESPACE,
                      ColorPaletteXML.ATTRIBUTE_RGB), 16));

        }
        return;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void doEndElement(final String uri,
      final String localName, final String qName) throws SAXException {
    if ((uri == null) || (ColorPaletteXML.NAMESPACE.equalsIgnoreCase(uri))) {

      if (ColorPaletteXML.ELEMENT_COLOR_PALETTE
          .equalsIgnoreCase(localName)) {
        this.close();
        return;
      }
    }
  }
}
