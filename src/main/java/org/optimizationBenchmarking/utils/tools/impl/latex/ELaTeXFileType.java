package org.optimizationBenchmarking.utils.tools.impl.latex;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.io.IFileType;

/**
 * File types related to <a
 * href="http://en.wikipedia.org/wiki/LaTeX">LaTeX</a> and used by the
 * automated
 * {@link org.optimizationBenchmarking.utils.tools.impl.latex.LaTeX LaTeX
 * Tool Chain}.
 */
public enum ELaTeXFileType implements IFileType {

  /**
   * the <a href="http://en.wikipedia.org/wiki/LaTeX">LaTeX</a> main file
   * type
   */
  TEX(false, false, true) {

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
      return "LaTeX Document";//$NON-NLS-1$
    }
  },

  /**
   * the <a href="http://en.wikipedia.org/wiki/BibTeX">BibTeX</a> file type
   */
  BIB(false, false, true) {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "bib";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return "application/x-bibtex"; //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "BibTeX Literature Database";//$NON-NLS-1$
    }
  },

  /** the bst file type */
  BST(false, false, true) {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "bst";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return null;
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "BibTeX Bibliography Style";//$NON-NLS-1$
    }
  },

  /** the LaTeX document class file type */
  CLS(false, false, true) {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "cls";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return null;
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "LaTeX Document Class";//$NON-NLS-1$
    }
  },

  /** the LaTeX style/package file type */
  STY(false, false, true) {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "sty";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return null;
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "LaTeX Package";//$NON-NLS-1$
    }
  },

  /** the LaTeX style/package file type */
  DEF(false, false, true) {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "def";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return null;
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "LaTeX Definition File";//$NON-NLS-1$
    }
  },

  /** the aux file type */
  AUX(true, true, false) {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "aux";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return null;
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "LaTeX AUX";//$NON-NLS-1$
    }
  },

  /** the bbl file type */
  BBL(true, true, false) {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "bbl";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return null;
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "BibTeX Reference List";//$NON-NLS-1$
    }
  },

  /** the dvi file type */
  DVI(true, false, false) {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "dvi";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return "application/x-dvi";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "Device Independent File Format";//$NON-NLS-1$
    }
  },

  /** the post script file type */
  PS(true, false, false) {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "ps";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return "application/postscript";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "PostScript";//$NON-NLS-1$
    }
  },

  /** the outline post script file type */
  OUT_PS(true, false, false) {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "out.ps";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return "application/postscript";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "PostScript Outline";//$NON-NLS-1$
    }
  },

  /** the PDF file type */
  PDF(false, false, false) {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "pdf";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return "application/pdf";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "Portable Document Format";//$NON-NLS-1$
    }
  },

  /** the log file type */
  LOG(true, false, false) {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "log";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return null;
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "LaTeX Log";//$NON-NLS-1$
    }
  },

  /** the blg file type */
  BLG(true, false, false) {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "blg";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return null;
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "BibTeX Log";//$NON-NLS-1$
    }
  },

  /** the out file type */
  OUT(true, true, false) {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "out";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return null;
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "LaTeX Output File";//$NON-NLS-1$
    }
  },

  /** the thumbnal file type */
  THM(true, false, false) {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "thm";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return null;
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "Thumbnail File";//$NON-NLS-1$
    }
  },

  /** the index file type */
  IDX(true, true, false) {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "idx";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return null;
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "LaTeX Index File";//$NON-NLS-1$
    }
  },
  ;

  /** A set view of all the instances */
  public static final ArraySetView<ELaTeXFileType> INSTANCES = //
  new ArraySetView<>(ELaTeXFileType.values());

  /** should we delete the file afterwards? */
  private final boolean m_deleteAfter;

  /** should we track the changes? */
  private final boolean m_trackChanges;

  /** can this be a required input type? */
  private final boolean m_canRequire;

  /**
   * Create the new LaTeX file type
   *
   * @param deleteAfter
   *          should we delete the file afterwards?
   * @param trackChanges
   *          should we track changes?
   * @param canRequire
   *          can this be a required input type?
   */
  ELaTeXFileType(final boolean deleteAfter, final boolean trackChanges,
      final boolean canRequire) {
    this.m_deleteAfter = deleteAfter;
    this.m_trackChanges = trackChanges;
    this.m_canRequire = canRequire;
  }

  /**
   * Should we delete the file after the compilation has been completed?
   *
   * @return {@code true} if we need to delete the file, {@code false} if
   *         not
   */
  final boolean _deleteAfterCompilation() {
    return this.m_deleteAfter;
  }

  /**
   * Should we track changes to this file type?
   *
   * @return {@code true} if we need to track changes, {@code false}
   *         otherwise
   */
  final boolean _shouldTrackChanges() {
    return this.m_trackChanges;
  }

  /**
   * Can this be a required input type for a LaTeX tool chain?
   *
   * @return can this be a required input type for a LaTeX tool chain?
   */
  final boolean _canRequire() {
    return this.m_canRequire;
  }
}
