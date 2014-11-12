package org.optimizationBenchmarking.utils.document.template;

import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.structured.XMLInputDriver;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A driver for loading a document into a handler
 */
public final class DocumentXMLInput extends
    XMLInputDriver<DocumentXMLHandler> {

  /** create */
  DocumentXMLInput() {
    super();
  }

  /**
   * Get the instance of the {@link DocumentXMLInput}
   * 
   * @return the instance of the {@link DocumentXMLInput}
   */
  public static final DocumentXMLInput getInstance() {
    return __DocumentXMLInputLoader.INSTANCE;
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
      sf.setResourceResolver(new _LSResourceResolver(sf
          .getResourceResolver()));
      schema = sf.newSchema(//
          _DocumentXMLConstants.class
              .getResource("documentTemplate.1.0.xsd")); //$NON-NLS-1$
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
      final DocumentXMLHandler loaderContext, final Logger logger) {
    return loaderContext;
  }

  /** the loader for lazy initialization */
  private static final class __DocumentXMLInputLoader {

    /** create */
    static final DocumentXMLInput INSTANCE = new DocumentXMLInput();
  }
}