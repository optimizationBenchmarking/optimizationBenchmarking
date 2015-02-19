package org.optimizationBenchmarking.utils.graphics.style.font;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLInputTool;

/** the font palette xml input driver */
public final class FontPaletteXMLInput extends
    XMLInputTool<FontPaletteBuilder> {

  /** create */
  FontPaletteXMLInput() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final void configureSAXParserFactory(final SAXParserFactory spf)
      throws Throwable {
    Throwable rec;
    SchemaFactory sf;
    Schema schema;

    rec = null;
    schema = null;
    try {
      sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      schema = sf.newSchema(//
          FontPaletteXML.class.getResource("fontPalette.1.0.xsd")); //$NON-NLS-1$
    } catch (final Throwable a) {
      rec = a;
    } finally {
      sf = null;
    }

    try {
      spf.setNamespaceAware(true);
      if (schema != null) {
        spf.setValidating(false);
        spf.setSchema(schema);
      } else {
        spf.setValidating(false);
      }
    } catch (final Throwable b) {
      rec = ErrorUtils.aggregateError(rec, b);
    }

    if (rec != null) {
      ErrorUtils.throwAsIOException(rec);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final FontPaletteXMLHandler wrapDestination(
      final FontPaletteBuilder dataDestination, final IOJob job) {
    return new FontPaletteXMLHandler(null, dataDestination);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Font Palette XML Input"; //$NON-NLS-1$
  }

  /**
   * get the instance of the {@link FontPaletteXMLInput}
   * 
   * @return the instance of the {@link FontPaletteXMLInput}
   */
  public static final FontPaletteXMLInput getInstance() {
    return __FontPaletteXMLInputLoader.INSTANCE;
  }

  /** the loader */
  private static final class __FontPaletteXMLInputLoader {
    /** the font palette xml */
    static final FontPaletteXMLInput INSTANCE = new FontPaletteXMLInput();
  }
}
