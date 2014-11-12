package org.optimizationBenchmarking.utils.bibliography.io;

import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.bibliography.data.BibliographyBuilder;
import org.optimizationBenchmarking.utils.io.structured.XMLInputDriver;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A driver for reading bibliography xml
 */
public final class BibliographyXMLInput extends
    XMLInputDriver<BibliographyBuilder> {

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
    Throwable rec;
    SchemaFactory sf;
    Schema schema;

    rec = null;
    schema = null;
    try {
      sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      schema = sf.newSchema(//
          BibliographyXMLConstants.class
              .getResource(BibliographyXMLConstants.SCHEMA));
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
  protected DefaultHandler wrapLoadContext(
      final BibliographyBuilder loaderContext, final Logger logger) {
    return new BibliographyXMLHandler(null, loaderContext);
  }

  /** the loader */
  private static final class __BibliographyXMLInputLoader {
    /** create */
    static final BibliographyXMLInput INSTANCE = new BibliographyXMLInput();
  }
}
