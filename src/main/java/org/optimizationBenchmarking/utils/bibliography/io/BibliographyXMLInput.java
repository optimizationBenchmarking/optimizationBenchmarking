package org.optimizationBenchmarking.utils.bibliography.io;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.utils.bibliography.data.BibliographyBuilder;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLInputTool;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A driver for reading bibliography xml
 */
public final class BibliographyXMLInput extends
XMLInputTool<BibliographyBuilder> {

  /** create */
  BibliographyXMLInput() {
    super();
  }

  /**
   * Get the instance of the {@link BibliographyXMLInput}
   *
   * @return the instance of the {@link BibliographyXMLInput}
   */
  public static final BibliographyXMLInput getInstance() {
    return __BibliographyXMLInputLoader.INSTANCE;
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
      schema = sf.newSchema(BibliographyXML.BIBLIOGRAPHY_XML
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
      RethrowMode.AS_IO_EXCEPTION.rethrow(//
          "Error during loading the XML Schema for BibliographyXML.", //$NON-NLS-1$
          true, rec);//
    }
  }

  /** {@inheritDoc} */
  @Override
  protected DefaultHandler wrapDestination(
      final BibliographyBuilder loaderContext, final IOJob logger) {
    return new BibliographyXMLHandler(null, loaderContext);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Bibliography XMLFileType Input"; //$NON-NLS-1$
  }

  /** the loader */
  private static final class __BibliographyXMLInputLoader {
    /** create */
    static final BibliographyXMLInput INSTANCE = new BibliographyXMLInput();
  }
}
