package org.optimizationBenchmarking.utils.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Map.Entry;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLOutputTool;
import org.optimizationBenchmarking.utils.io.xml.XMLBase;
import org.optimizationBenchmarking.utils.io.xml.XMLElement;

/**
 * The configuration xml output can store either
 * {@link org.optimizationBenchmarking.utils.config.Configuration}s or
 * {@link org.optimizationBenchmarking.utils.config.Dump}s.
 */
public final class ConfigurationXMLOutput extends XMLOutputTool<Object> {
  /** The configuration namespace prefix */
  public static final String CONFIGURATION_NAMESPACE_PREFIX = "cfg"; //$NON-NLS-1$

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
  protected final void xml(final IOJob job, final Object data,
      final XMLBase xmlBase) throws Throwable {
    final Configuration config;

    try (XMLElement root = xmlBase.element()) {
      root.namespaceSetPrefix(ConfigurationXML.NAMESPACE_URI,
          ConfigurationXMLOutput.CONFIGURATION_NAMESPACE_PREFIX);
      root.name(ConfigurationXML.NAMESPACE_URI,
          ConfigurationXML.ELEMENT_CONFIGURATION_ROOT);

      if (data instanceof Configuration) {
        config = ((Configuration) data);

        synchronized (config.m_data) {
          for (final Entry<String, Object> e : config.m_data.entries()) {
            try (final XMLElement param = root.element()) {
              param.name(ConfigurationXML.NAMESPACE_URI,
                  ConfigurationXML.ELEMENT_CONFIGURATION_PARAMETER);

              param.attributeEncoded(ConfigurationXML.NAMESPACE_URI,
                  ConfigurationXML.ATTRIBUTE_CONFIGURATION_PARAMETER_NAME,
                  e.getKey());
              param
                  .attributeEncoded(
                      ConfigurationXML.NAMESPACE_URI,
                      ConfigurationXML.ATTRIBUTE_CONFIGURATION_PARAMETER_VALUE,
                      String.valueOf(e.getValue()));
            }
          }
        }
        return;
      }

      if (data instanceof Dump) {

        for (final Map.Entry<Parameter<?>, Object> e : ((Dump) data)) {
          try (final XMLElement param = root.element()) {
            param.name(ConfigurationXML.NAMESPACE_URI,
                ConfigurationXML.ELEMENT_CONFIGURATION_PARAMETER);

            param.attributeEncoded(ConfigurationXML.NAMESPACE_URI,
                ConfigurationXML.ATTRIBUTE_CONFIGURATION_PARAMETER_NAME,
                e.getKey().m_name);
            param.attributeEncoded(ConfigurationXML.NAMESPACE_URI,
                ConfigurationXML.ATTRIBUTE_CONFIGURATION_PARAMETER_VALUE,
                String.valueOf(e.getValue()));
          }
        }
        return;
      }

      throw new IllegalArgumentException(//
          "Can only store configurations or dumps, but received an " //$NON-NLS-1$
              + data + '.');
    }
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Configuration XML Output"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  protected final void file(final IOJob job, final Object data,
      final Path file, final StreamEncoding<?, ?> encoding)
      throws Throwable {
    super.file(job, data, file, encoding);
    if (Files.exists(file)) {
      this.addFile(job, file, ConfigurationXML.CONFIG_XML);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final String getDefaultPlainOutputFileName() {
    return ("config." + //$NON-NLS-1$
    ConfigurationXML.CONFIG_XML.getDefaultSuffix());
  }

  /** the loader */
  private static final class __ConfigurationXMLOutputLoader {
    /** the globally shared configuration xml output writer instance */
    static final ConfigurationXMLOutput INSTANCE = new ConfigurationXMLOutput();
  }
}
