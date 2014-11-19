package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.io.IFileType;

/** the HTML file types */
public enum ELaTeXFileTypes implements IFileType {

  /** the CSS file type */
  LATEX() {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "tex";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return "application/x-latex"; //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "LaTeX";//$NON-NLS-1$
    }

  };

}
