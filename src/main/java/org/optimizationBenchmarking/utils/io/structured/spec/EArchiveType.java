package org.optimizationBenchmarking.utils.io.structured.spec;

import org.optimizationBenchmarking.utils.io.IFileType;

/**
 * The archive types supported by the structured I/O API. In the future,
 * the Boolean "isZipCompressed" parameters of many of the output and input
 * job builders will be redesigned to take an argument of this type here
 * instead.
 */
public enum EArchiveType implements IFileType {

  /** the ZIP archive type */
  ZIP() {
    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "zip"; //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return "application/zip";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "ZIP Archive"; //$NON-NLS-1$
    }
  };

}
