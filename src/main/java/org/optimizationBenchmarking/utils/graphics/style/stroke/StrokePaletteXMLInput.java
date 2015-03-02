package org.optimizationBenchmarking.utils.graphics.style.stroke;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLInputTool;

/** the stroke palette xml input driver */
public final class StrokePaletteXMLInput extends
    XMLInputTool<StrokePaletteBuilder> {

  /** create */
  StrokePaletteXMLInput() {
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
      schema = sf.newSchema(//
          StrokePaletteXML.class.getResource("strokePalette.1.0.xsd")); //$NON-NLS-1$
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
      RethrowMode.THROW_AS_IO_EXCEPTION
          .rethrow(//
              "Error during loading of XML Schema for the stroke palette xml.", //$NON-NLS-1$
              true, rec);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final StrokePaletteXMLHandler wrapDestination(
      final StrokePaletteBuilder dataDestination, final IOJob job) {
    return new StrokePaletteXMLHandler(null, dataDestination);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Stroke Palette XML Input"; //$NON-NLS-1$
  }

  /**
   * get the instance of the {@link StrokePaletteXMLInput}
   * 
   * @return the instance of the {@link StrokePaletteXMLInput}
   */
  public static final StrokePaletteXMLInput getInstance() {
    return __StrokePaletteXMLInputLoader.INSTANCE;
  }

  /** the loader */
  private static final class __StrokePaletteXMLInputLoader {
    /** the stroke palette xml */
    static final StrokePaletteXMLInput INSTANCE = new StrokePaletteXMLInput();
  }
}
