package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.io.IFileType;

/**
 * the internal class for the XML constants of the configuration XML format
 */
public enum EConfigurationFileType implements IFileType {

  /** the configuration xml file type */
  CONFIG_XML {
    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "xml"; //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return null;
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "Configuration XML File"; //$NON-NLS-1$
    }
  },
  /** the configuration xml file type */
  CONFIG_PROPERTIES {
    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "txt"; //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return null;
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "Configuration Properties File"; //$NON-NLS-1$
    }
  };
}
