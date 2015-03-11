package org.optimizationBenchmarking.utils.io.xml;

import org.optimizationBenchmarking.utils.io.IFileType;

/** The xml file type */
public enum XMLFileType implements IFileType {

  /** the XML file type instance */
  XML;

  /** {@inheritDoc} */
  @Override
  public final String getDefaultSuffix() {
    return "xml"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final String getMIMEType() {
    return "application/xml"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return "XML File"; //$NON-NLS-1$
  }

}
