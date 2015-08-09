package org.optimizationBenchmarking.utils.graphics.style.stroke;

import org.optimizationBenchmarking.utils.io.xml.DelegatingHandler;
import org.optimizationBenchmarking.utils.text.tokenizers.WordBasedStringIterator;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** a handler for stroke palette xml data */
public class StrokePaletteXMLHandler extends DelegatingHandler {

  /** the destination stroke palette builder */
  private final StrokePaletteBuilder m_dest;

  /**
   * Create
   *
   * @param owner
   *          the owning handler, or {@code null} if not used
   * @param dest
   *          the stroke palette builder instance to load the data into
   */
  public StrokePaletteXMLHandler(final DelegatingHandler owner,
      final StrokePaletteBuilder dest) {
    super(owner);
    this.m_dest = dest;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doStartElement(final String uri,
      final String localName, final String qName,
      final Attributes attributes) throws SAXException {
    final String dash;
    StrokeStyleBuilder builder;

    if ((uri == null)
        || (StrokePaletteXML.NAMESPACE.equalsIgnoreCase(uri))) {

      builder = null;
      if (StrokePaletteXML.ELEMENT_STROKE.equalsIgnoreCase(localName)) {
        builder = this.m_dest.add();
      } else {
        if (StrokePaletteXML.ELEMENT_DEFAULT_STROKE
            .equalsIgnoreCase(localName)) {
          builder = this.m_dest.setDefaultStroke();
        } else {
          if (StrokePaletteXML.ELEMENT_THIN_STROKE
              .equalsIgnoreCase(localName)) {
            builder = this.m_dest.setThinStroke();
          } else {
            if (StrokePaletteXML.ELEMENT_THICK_STROKE
                .equalsIgnoreCase(localName)) {
              builder = this.m_dest.setThickStroke();
            }
          }
        }
      }

      if (builder == null) {
        return;
      }

      builder.setName(//
          DelegatingHandler.getAttribute(attributes,//
              StrokePaletteXML.NAMESPACE, StrokePaletteXML.ATTRIBUTE_NAME));

      builder.setWidth(Float.parseFloat(//
          DelegatingHandler.getAttribute(
              attributes,//
              StrokePaletteXML.NAMESPACE,
              StrokePaletteXML.ATTRIBUTE_THICKNESS)));

      dash = DelegatingHandler.getAttribute(attributes,//
          StrokePaletteXML.NAMESPACE, StrokePaletteXML.ATTRIBUTE_DASH);

      if (dash != null) {
        for (final String flt : new WordBasedStringIterator(dash)) {
          builder.addDash(Float.parseFloat(flt));
        }
      }

      builder.close();
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void doEndElement(final String uri,
      final String localName, final String qName) throws SAXException {
    if ((uri == null)
        || (StrokePaletteXML.NAMESPACE.equalsIgnoreCase(uri))) {

      if (StrokePaletteXML.ELEMENT_STROKE_PALETTE
          .equalsIgnoreCase(localName)) {
        this.close();
        return;
      }
    }
  }
}
