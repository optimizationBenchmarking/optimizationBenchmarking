package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.text.TextFileTypes;

/**
 * The properties-based configuration file type
 */
public enum ConfigurationPropertiesFileType implements IFileType {

  /** the configuration properties file type */
  CONFIG_PROPERTIES;

  /** {@inheritDoc} */
  @Override
  public final String getDefaultSuffix() {
    return TextFileTypes.TXT.getDefaultSuffix();
  }

  /** {@inheritDoc} */
  @Override
  public final String getMIMEType() {
    return TextFileTypes.TXT.getMIMEType();
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return "Configuration Properties File"; //$NON-NLS-1$
  }
}
