package org.optimizationBenchmarking.utils.config;

import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.structured.XMLOutputDriver;
import org.optimizationBenchmarking.utils.io.xml.XMLBase;
import org.optimizationBenchmarking.utils.io.xml.XMLElement;

/** the configuration xml output */
public class ConfigurationXMLOutput extends XMLOutputDriver<Configuration> {

  /** the configuration xml */
  public static final ConfigurationXMLOutput INSTANCE = new ConfigurationXMLOutput();

  /** create */
  private ConfigurationXMLOutput() {
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
  protected void doStoreXML(final Configuration data, final XMLBase dest,
      final Logger logger) {
    try (XMLElement root = dest.element()) {
      root.namespaceSetPrefix(_ConfigXMLConstants.NAMESPACE_URI, "cfg"); //$NON-NLS-1$
      root.name(_ConfigXMLConstants.NAMESPACE_URI,
          _ConfigXMLConstants.ELEMENT_CONFIGURATION_ROOT);

      synchronized (data.m_data) {

        root.attributeRaw(_ConfigXMLConstants.NAMESPACE_URI,
            _ConfigXMLConstants.ATTRIBUTE_CONFIGURATION_VERSION,
            _ConfigXMLConstants.ATTRIBUTE_VALUE_CONFIGURATION_VERSION);

        for (final Entry<String, Object> e : data.m_data.entries()) {
          try (final XMLElement param = root.element()) {
            param.name(_ConfigXMLConstants.NAMESPACE_URI,
                _ConfigXMLConstants.ELEMENT_CONFIGURATION_PARAMETER);

            param
                .attributeEncoded(
                    _ConfigXMLConstants.NAMESPACE_URI,
                    _ConfigXMLConstants.ATTRIBUTE_CONFIGURATION_PARAMETER_NAME,
                    e.getKey());
            param
                .attributeEncoded(
                    _ConfigXMLConstants.NAMESPACE_URI,
                    _ConfigXMLConstants.ATTRIBUTE_CONFIGURATION_PARAMETER_VALUE,
                    String.valueOf(e.getValue()));
          }
        }
      }
    }
  }
}
