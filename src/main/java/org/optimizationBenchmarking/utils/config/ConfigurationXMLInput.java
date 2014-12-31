package org.optimizationBenchmarking.utils.config;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLInputTool;
import org.xml.sax.helpers.DefaultHandler;

/** the configuration xml input */
public final class ConfigurationXMLInput extends
    XMLInputTool<ConfigurationBuilder> {

  /** create */
  ConfigurationXMLInput() {
    super();
  }

  /**
   * get the instance of the {@link ConfigurationXMLInput}
   * 
   * @return the instance of the {@link ConfigurationXMLInput}
   */
  public static final ConfigurationXMLInput getInstance() {
    return __ConfigurationXMLInputLoader.INSTANCE;
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
          _ConfigXMLConstants.class.getResource("configuration.1.0.xsd")); //$NON-NLS-1$
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
      final ConfigurationBuilder dataDestination, final IOJob job) {
    return new ConfigurationXMLHandler(null, dataDestination);
  }

  /** the loader */
  private static final class __ConfigurationXMLInputLoader {
    /** the configuration xml */
    static final ConfigurationXMLInput INSTANCE = new ConfigurationXMLInput();
  }
}
