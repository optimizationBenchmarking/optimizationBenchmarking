package org.optimizationBenchmarking.utils.config;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.optimizationBenchmarking.utils.io.xml.IXMLFileType;
import org.optimizationBenchmarking.utils.io.xml.XMLFileType;

/**
 * The class for the XMLFileType constants of the configuration XMLFileType
 * format.
 */
public enum ConfigurationXML implements IXMLFileType {

  /** the globally shared instance of the configuration XML file type */
  CONFIG_XML;

  /** the configuration parameter name attribute */
  static final String ATTRIBUTE_CONFIGURATION_PARAMETER_NAME = "name"; //$NON-NLS-1$
  /** the configuration parameter value attribute */
  static final String ATTRIBUTE_CONFIGURATION_PARAMETER_VALUE = "value"; //$NON-NLS-1$
  /** the configuration version attribute */
  static final String ATTRIBUTE_CONFIGURATION_VERSION = "version"; //$NON-NLS-1$
  /** the configuration version attribute value */
  static final String ATTRIBUTE_VALUE_CONFIGURATION_VERSION = "1.0"; //$NON-NLS-1$
  /** the configuration parameter element */
  static final String ELEMENT_CONFIGURATION_PARAMETER = "parameter"; //$NON-NLS-1$
  /** the configuration root element */
  static final String ELEMENT_CONFIGURATION_ROOT = "configuration"; //$NON-NLS-1$
  /** the namespace */
  static final URI NAMESPACE_URI = //
      URI.create(//
          "http://www.optimizationBenchmarking.org/formats/configuration/configuration.1.0.xsd").normalize(); //$NON-NLS-1$
  /** the namespace string */
  static final String NAMESPACE = ConfigurationXML.NAMESPACE_URI
      .toString();

  /** {@inheritDoc} */
  @Override
  public final String getDefaultSuffix() {
    return XMLFileType.XML.getDefaultSuffix();
  }

  /** {@inheritDoc} */
  @Override
  public final String getMIMEType() {
    return XMLFileType.XML.getMIMEType();
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return "Configuration XMLFileType File"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final URI getNamespaceURI() {
    return ConfigurationXML.NAMESPACE_URI;
  }

  /** {@inheritDoc} */
  @Override
  public final String getNamespace() {
    return ConfigurationXML.NAMESPACE;
  }

  /** {@inheritDoc} */
  @Override
  public final URL getSchemaSource() throws IOException {
    final URL u;
    u = this.getClass().getResource(//
        ConfigurationXML.NAMESPACE.substring(ConfigurationXML.NAMESPACE
            .lastIndexOf('/') + 1));
    if (u != null) {
      return u;
    }
    return ConfigurationXML.NAMESPACE_URI.toURL();
  }
}