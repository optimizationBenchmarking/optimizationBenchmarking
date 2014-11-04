package org.optimizationBenchmarking.utils.config;

import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.structured.XMLInputDriver;
import org.xml.sax.helpers.DefaultHandler;

/** the configuration xml input */
public class ConfigurationXMLInput extends
    XMLInputDriver<ConfigurationBuilder> {

  /** the configuration xml */
  public static final ConfigurationXMLInput INSTANCE = new ConfigurationXMLInput();

  /** create */
  private ConfigurationXMLInput() {
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
          ConfigurationXMLInput.class.getResource("configuration.1.0.xsd")); //$NON-NLS-1$
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
      final ConfigurationBuilder loaderContext, final Logger logger) {
    return new ConfigurationXMLHandler(null, loaderContext);
  }
}
