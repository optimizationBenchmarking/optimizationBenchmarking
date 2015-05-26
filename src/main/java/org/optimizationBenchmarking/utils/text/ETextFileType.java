package org.optimizationBenchmarking.utils.text;

import org.optimizationBenchmarking.utils.io.IFileType;

/** The text file type */
public enum ETextFileType implements IFileType {

  /** the text file type instance */
  TXT {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "txt"; //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return "text/plain"; //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "Text File"; //$NON-NLS-1$
    }
  },

  /** the csv file type instance */
  CSV {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "csv"; //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return "text/csv"; //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "Comma Separated Values"; //$NON-NLS-1$
    }
  },

}