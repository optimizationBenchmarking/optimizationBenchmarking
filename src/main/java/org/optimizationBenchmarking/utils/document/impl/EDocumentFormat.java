package org.optimizationBenchmarking.utils.document.impl;

import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10Driver;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;

/** An enumeration of the available document formats */
public enum EDocumentFormat {

  /** The XHTML 1.0 format */
  XHTML_1_0() {
    /** {@inheritDoc} */
    @Override
    public final IDocumentDriver getDefaultDriver() {
      return XHTML10Driver.getDefaultDriver();
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
