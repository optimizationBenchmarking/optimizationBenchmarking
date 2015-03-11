package org.optimizationBenchmarking.utils.graphics.style.color;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLInputTool;

/** the color palette xml input driver */
public final class ColorPaletteXMLInput extends
    XMLInputTool<ColorPaletteBuilder> {

  /** create */
  ColorPaletteXMLInput() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final void configureSAXParserFactory(final SAXParserFactory spf)
      throws Throwable {
    Object rec;
    SchemaFactory sf;
    Schema schema;

    rec = null;
    schema = null;
    try {
      sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      schema = sf.newSchema(ColorPaletteXML.COLOR_PALETTE_XML
          .getSchemaSource());
    } catch (final Throwable a) {
      rec = ErrorUtils.aggregateError(a, rec);
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
      rec = ErrorUtils.aggregateError(b, rec);
    }

    if (rec != null) {
      RethrowMode.AS_IO_EXCEPTION
          .rethrow(//
              "Error during loading of the XMLFileType Schema for the color style palette XMLFileType.", //$NON-NLS-1$
              true, rec);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final ColorPaletteXMLHandler wrapDestination(
      final ColorPaletteBuilder dataDestination, final IOJob job) {
    return new ColorPaletteXMLHandler(null, dataDestination);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Color Palette XMLFileType Input"; //$NON-NLS-1$
  }

  /**
   * get the instance of the {@link ColorPaletteXMLInput}
   * 
   * @return the instance of the {@link ColorPaletteXMLInput}
   */
  public static final ColorPaletteXMLInput getInstance() {
    return __ColorPaletteXMLInputLoader.INSTANCE;
  }

  /** the loader */
  private static final class __ColorPaletteXMLInputLoader {
    /** the color palette xml */
    static final ColorPaletteXMLInput INSTANCE = new ColorPaletteXMLInput();
  }
}
