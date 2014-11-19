package org.optimizationBenchmarking.utils.document.impl;

import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10Driver;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;
import org.optimizationBenchmarking.utils.io.IFileType;

/** An enumeration of the available document formats */
public enum EDocumentFormat implements IFileType {

  /** The XHTML 1.0 format */
  XHTML_1_0() {
    /** {@inheritDoc} */
    @Override
    public final IDocumentDriver getDefaultDriver() {
      return XHTML10Driver.getDefaultDriver();
    }

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "xhtml";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return "application/xhtml+xml"; //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "Extensible Hypertext Markup Language 1.0";//$NON-NLS-1$
    }

  };

  /** the default document format */
  public static final EDocumentFormat DEFAULT = XHTML_1_0;

  /**
   * Obtain the document driver
   * 
   * @return the document driver
   */
  public abstract IDocumentDriver getDefaultDriver();

}
