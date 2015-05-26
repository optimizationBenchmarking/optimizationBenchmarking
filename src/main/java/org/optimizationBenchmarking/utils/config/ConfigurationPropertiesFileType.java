package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.text.ETextFileType;

/**
 * The properties-based configuration file type
 */
public enum ConfigurationPropertiesFileType implements IFileType {

  /** the configuration properties file type */
  CONFIG_PROPERTIES;

  /** {@inheritDoc} */
  @Override
  public final String getDefaultSuffix() {
    return ETextFileType.TXT.getDefaultSuffix();
  }

  /** {@inheritDoc} */
  @Override
  public final String getMIMEType() {
    return ETextFileType.TXT.getMIMEType();
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return "Configuration Properties File"; //$NON-NLS-1$
  }
}
