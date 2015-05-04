package org.optimizationBenchmarking.utils.graphics.style.font;

import org.optimizationBenchmarking.utils.graphics.EFontType;
import org.optimizationBenchmarking.utils.io.xml.DelegatingHandler;
import org.optimizationBenchmarking.utils.parsers.IntParser;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** a handler for font palette xml data */
public class FontPaletteXMLHandler extends DelegatingHandler {

  /** the destination font palette builder */
  private final FontPaletteBuilder m_dest;

  /** the face choice */
  private final MemoryTextOutput m_faceChoice;

  /** the builder */
  private FontStyleBuilder m_builder;

  /** the font face */
  private boolean m_inFace;

  /** the resource */
  private String m_resource;

  /** the type */
  private EFontType m_type;

  /**
   * Create
   *
   * @param owner
   *          the owning handler, or {@code null} if not used
   * @param dest
   *          the font palette builder instance to load the data into
   */
  public FontPaletteXMLHandler(final DelegatingHandler owner,
      final FontPaletteBuilder dest) {
    super(owner);
    this.m_dest = dest;
    this.m_faceChoice = new MemoryTextOutput();
  }

  /** {@inheritDoc} */
  @Override
  protected final void doStartElement(final String uri,
      final String localName, final String qName,
      final Attributes attributes) throws SAXException {
    String attr;

    this.m_inFace = false;
    if ((uri == null) || (FontPaletteXML.NAMESPACE.equalsIgnoreCase(uri))) {

      if (FontPaletteXML.ELEMENT_FONT_FACE.equalsIgnoreCase(localName)) {
        this.m_inFace = true;
        this.m_resource = DelegatingHandler.getAttribute(attributes,
            FontPaletteXML.NAMESPACE, FontPaletteXML.ATTRIBUTE_RESOURCE);
        this.m_type = FontPaletteXML._parseFontType(DelegatingHandler
            .getAttribute(attributes, FontPaletteXML.NAMESPACE,
                FontPaletteXML.ATTRIBUTE_TYPE));
        this.m_faceChoice.clear();
        return;
      }

      if (FontPaletteXML.ELEMENT_FONT_PALETTE.equalsIgnoreCase(localName)) {
        return;
      }

      if (FontPaletteXML.ELEMENT_FONT.equalsIgnoreCase(localName)) {
        this.m_builder = this.m_dest.add();
      } else {
        if (FontPaletteXML.ELEMENT_DEFAULT_FONT
            .equalsIgnoreCase(localName)) {
          this.m_builder = this.m_dest.setDefaultFont();
        } else {
          if (FontPaletteXML.ELEMENT_EMPHASIZED_FONT
              .equalsIgnoreCase(localName)) {
            this.m_builder = this.m_dest.setEmphFont();
          } else {
            if (FontPaletteXML.ELEMENT_CODE_FONT
                .equalsIgnoreCase(localName)) {
              this.m_builder = this.m_dest.setCodeFont();
            } else {
              return;
            }
          }
        }
      }

      attr = DelegatingHandler.getAttribute(attributes,
          FontPaletteXML.NAMESPACE, FontPaletteXML.ATTRIBUTE_BOLD);
      if (attr != null) {
        this.m_builder.setBold(Boolean.parseBoolean(attr));
      }

      attr = DelegatingHandler.getAttribute(attributes,
          FontPaletteXML.NAMESPACE, FontPaletteXML.ATTRIBUTE_ITALIC);
      if (attr != null) {
        this.m_builder.setItalic(Boolean.parseBoolean(attr));
      }

      attr = DelegatingHandler.getAttribute(attributes,
          FontPaletteXML.NAMESPACE, FontPaletteXML.ATTRIBUTE_UNDERLINED);
      if (attr != null) {
        this.m_builder.setUnderlined(Boolean.parseBoolean(attr));
      }

      attr = DelegatingHandler.getAttribute(attributes,
          FontPaletteXML.NAMESPACE, FontPaletteXML.ATTRIBUTE_FONT_SIZE);
      if (attr != null) {
        this.m_builder.setSize(IntParser.INSTANCE.parseInt(attr));
      }

      this.m_builder.setFontFamily(FontPaletteXML
          ._parseFontFamily(DelegatingHandler.getAttribute(attributes,
              FontPaletteXML.NAMESPACE,
              FontPaletteXML.ATTRIBUTE_FONT_FAMILY)));
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void doEndElement(final String uri,
      final String localName, final String qName) throws SAXException {
    if ((uri == null) || (FontPaletteXML.NAMESPACE.equalsIgnoreCase(uri))) {
      this.m_inFace = false;

      if (FontPaletteXML.ELEMENT_FONT_PALETTE.equalsIgnoreCase(localName)) {
        this.m_builder = null;
        this.close();
        return;
      }

      if (FontPaletteXML.ELEMENT_FONT_FACE.equalsIgnoreCase(localName)) {
        this.m_builder.addFaceChoice(this.m_faceChoice.toString(),
            this.m_resource, this.m_type);
        this.m_faceChoice.clear();
        return;
      }

      if (FontPaletteXML.ELEMENT_FONT.equalsIgnoreCase(localName)
          || FontPaletteXML.ELEMENT_DEFAULT_FONT
          .equalsIgnoreCase(localName)
          || FontPaletteXML.ELEMENT_EMPHASIZED_FONT
          .equalsIgnoreCase(localName)
          || FontPaletteXML.ELEMENT_CODE_FONT.equalsIgnoreCase(localName)) {
        this.m_builder.close();
        this.m_builder = null;
        return;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void doCharacters(final char[] ch, final int start,
      final int length) throws SAXException {
    if (this.m_inFace) {
      this.m_faceChoice.append(ch, start, (start + length));
    }
  }
}
