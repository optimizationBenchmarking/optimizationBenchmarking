package org.optimizationBenchmarking.utils.config;

import java.util.Map.Entry;

import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLOutputTool;
import org.optimizationBenchmarking.utils.io.xml.XMLBase;
import org.optimizationBenchmarking.utils.io.xml.XMLElement;

/** the configuration xml output */
public final class ConfigurationXMLOutput extends
    XMLOutputTool<Configuration> {

  /** create */
  ConfigurationXMLOutput() {
    super();
  }

  /**
   * get the instance of the {@link ConfigurationXMLOutput}
   * 
   * @return the instance of the {@link ConfigurationXMLOutput}
   */
  public static final ConfigurationXMLOutput getInstance() {
    return __ConfigurationXMLOutputLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected void xml(final IOJob job, final Configuration data,
      final XMLBase xmlBase) throws Throwable {
    try (XMLElement root = xmlBase.element()) {
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

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Configuration XML Output"; //$NON-NLS-1$
  }

  /** the loader */
  private static final class __ConfigurationXMLOutputLoader {
    /** the configuration xml */
    static final ConfigurationXMLOutput INSTANCE = new ConfigurationXMLOutput();
  }
}
