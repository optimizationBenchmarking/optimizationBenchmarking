package org.optimizationBenchmarking.utils.config;

import java.net.URI;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.structured.XMLIODriver;
import org.optimizationBenchmarking.utils.io.xml.XMLBase;
import org.optimizationBenchmarking.utils.io.xml.XMLElement;
import org.xml.sax.helpers.DefaultHandler;

/** the configuration xml i/o */
public class ConfigurationXMLIO extends
    XMLIODriver<Configuration, Configuration> {

  /** the namespace */
  public static final URI NAMESPACE_URI = URI
      .create(
          "http://www.optimizationBenchmarking.org/formats/configuration/configuration.1.0.xsd").normalize(); //$NON-NLS-1$

  /** the namespace string */
  public static final String NAMESPACE = ConfigurationXMLIO.NAMESPACE_URI
      .toString();

  /** the configuration root element */
  public static final String ELEMENT_CONFIGURATION_ROOT = "configuration";//$NON-NLS-1$
  /** the configuration version attribute */
  public static final String ATTRIBUTE_CONFIGURATION_VERSION = "version";//$NON-NLS-1$
  /** the configuration version attribute value */
  public static final String ATTRIBUTE_VALUE_CONFIGURATION_VERSION = "1.0";//$NON-NLS-1$
  /** the configuration parameter element */
  public static final String ELEMENT_CONFIGURATION_PARAMETER = "parameter";//$NON-NLS-1$
  /** the configuration parameter name attribute */
  public static final String ATTRIBUTE_CONFIGURATION_PARAMETER_NAME = "name";//$NON-NLS-1$
  /** the configuration parameter value attribute */
  public static final String ATTRIBUTE_CONFIGURATION_PARAMETER_VALUE = "value";//$NON-NLS-1$

  /** the configuration xml */
  public static final ConfigurationXMLIO INSTANCE = new ConfigurationXMLIO();

  /** create */
  private ConfigurationXMLIO() {
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
          ConfigurationXMLIO.class.getResource("configuration.1.0.xsd"));//$NON-NLS-1$
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
      root.namespaceSetPrefix(ConfigurationXMLIO.NAMESPACE_URI, "cfg"); //$NON-NLS-1$
      root.name(ConfigurationXMLIO.NAMESPACE_URI,
          ConfigurationXMLIO.ELEMENT_CONFIGURATION_ROOT);

      synchronized (data.m_data) {

        root.attributeRaw(ConfigurationXMLIO.NAMESPACE_URI,
            ConfigurationXMLIO.ATTRIBUTE_CONFIGURATION_VERSION,
            ConfigurationXMLIO.ATTRIBUTE_VALUE_CONFIGURATION_VERSION);

        for (final Entry<String, Object> e : data.m_data.entries()) {
          try (final XMLElement param = root.element()) {
            param.name(ConfigurationXMLIO.NAMESPACE_URI,
                ConfigurationXMLIO.ELEMENT_CONFIGURATION_PARAMETER);

            param.attributeEncoded(ConfigurationXMLIO.NAMESPACE_URI,
                ConfigurationXMLIO.ATTRIBUTE_CONFIGURATION_PARAMETER_NAME,
                e.getKey());
            param
                .attributeEncoded(
                    ConfigurationXMLIO.NAMESPACE_URI,
                    ConfigurationXMLIO.ATTRIBUTE_CONFIGURATION_PARAMETER_VALUE,
                    String.valueOf(e.getValue()));
          }
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected DefaultHandler wrapLoadContext(
      final Configuration loaderContext, final Logger logger) {
    return new ConfigurationXMLHandler(null, loaderContext);
  }
}
