package org.optimizationBenchmarking.utils.config;

import java.net.URI;

import org.optimizationBenchmarking.utils.error.ErrorUtils;

/**
 * the internal class for the XML constants of the configuration XML format
 */
public final class ConfigurationXMLConstants {
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
  public static final URI NAMESPACE_URI = URI
      .create(//
          "http://www.optimizationBenchmarking.org/formats/configuration/configuration.1.0.xsd").normalize(); //$NON-NLS-1$
  /** the namespace string */
  public static final String NAMESPACE = ConfigurationXMLConstants.NAMESPACE_URI
      .toString();
  /** the schema name */
  public static final String SCHEMA = ConfigurationXMLConstants.NAMESPACE
      .substring(ConfigurationXMLConstants.NAMESPACE.lastIndexOf('/') + 1);

  /** the forbidden constructor */
  private ConfigurationXMLConstants() {
    ErrorUtils.doNotCall();
  }
}