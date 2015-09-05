package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.io.IFileType;

/** the HTML file types */
public enum EWebFileType implements IFileType {

  /** the CSS file type */
  CSS() {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "css";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return "text/css"; //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "Cascading Style Sheets";//$NON-NLS-1$
    }

  };

}
