package org.optimizationBenchmarking.utils.document.template;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLInputTool;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A driver for loading a document into a handler
 */
public final class DocumentXMLInput extends
    XMLInputTool<DocumentXMLHandler> {

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
  protected DefaultHandler wrapDestination(
      final DocumentXMLHandler dataDestination, final IOJob job) {
    return dataDestination;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Document Template XML Input"; //$NON-NLS-1$
  }

  /** the loader for lazy initialization */
  private static final class __DocumentXMLInputLoader {

    /** create */
    static final DocumentXMLInput INSTANCE = new DocumentXMLInput();
  }
}