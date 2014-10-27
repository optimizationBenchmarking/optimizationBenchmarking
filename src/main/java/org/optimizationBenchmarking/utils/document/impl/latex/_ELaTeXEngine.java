package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;

/** The set of LaTeX engines which we may be able to use. */
public enum _ELaTeXEngine {

  /** use the plain LaTeX engine */
  LATEX() {
    /** {@inheritDoc} */
    @Override
    final boolean _supportsGraphicFormat(final EGraphicFormat format) {
      return (format == EGraphicFormat.EPS);
    }
  },

  /** use the pdf LaTeX engine */
  PDF_LATEX() {
    /** {@inheritDoc} */
    @Override
    final boolean _supportsGraphicFormat(final EGraphicFormat format) {
      return ((format == EGraphicFormat.JPEG)//
          || (format == EGraphicFormat.PDF)//
      || (format == EGraphicFormat.PNG));
    }
  },
  ;

  /**
   * check whether a given graphic format is supported
   * 
   * @param format
   *          the format
   * @return {@code true} if it is supported, {@code false} otherwise
   */
  abstract boolean _supportsGraphicFormat(final EGraphicFormat format);
}
