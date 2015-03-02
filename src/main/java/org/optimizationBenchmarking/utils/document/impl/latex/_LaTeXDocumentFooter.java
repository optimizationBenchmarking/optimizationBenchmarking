package org.optimizationBenchmarking.utils.document.impl.latex;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.bibliography.io.BibTeXOutput;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentFooter;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType;

/** the LaTeX document footer */
final class _LaTeXDocumentFooter extends DocumentFooter {
  /** begin the bibliography style */
  private static final char[] BIBLIOGRAPHY_STYLE_BEGIN = { '\\', 'b', 'i',
      'b', 'l', 'i', 'o', 'g', 'r', 'a', 'p', 'h', 'y', 's', 't', 'y',
      'l', 'e', '{' };
  /** begin the bibliography */
  private static final char[] BIBLIOGRAPHY_BEGIN = { '\\', 'b', 'i', 'b',
      'l', 'i', 'o', 'g', 'r', 'a', 'p', 'h', 'y', '{' };

  /**
   * Create a document footer
   * 
   * @param owner
   *          the owning document
   */
  _LaTeXDocumentFooter(final LaTeXDocument owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected final void processCitations(final Bibliography bib) {
    final LaTeXDocument doc;
    final Logger logger;
    final Path path;
    final BibTeXOutput driver;
    final ITextOutput out;
    final int size;
    final String name, style;

    if ((bib == null) || ((size = bib.size()) <= 0)) {
      return;
    }

    doc = ((LaTeXDocument) (this.getDocument()));
    path = PathUtils.createPathInside(doc.getDocumentFolder(),//
        ((PathUtils.getFileNameWithoutExtension(doc.getDocumentPath()) + //
        "-bibliography.") + ELaTeXFileType.BIB.getDefaultSuffix())); //$NON-NLS-1$

    logger = this.getLogger();
    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine((((("Storing " + size) + //$NON-NLS-1$
          " bibliography entries to '") + path) //$NON-NLS-1$
          + '\'') + '.');
    }

    driver = BibTeXOutput.getInstance();
    if ((driver == null) || (!(driver.canUse()))) {
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(//
            "BibTeX output driver cannot be used, so no bibliography can be stored. This will lead to undefined references in the LaTeX document."); //$NON-NLS-1$
      }
      return;
    }

    try {
      driver.use().setPath(path).setLogger(logger).setSource(bib)
          .setFileProducerListener(this.getFileCollector()).create()
          .call();
    } catch (final Throwable error) {
      ErrorUtils
          .logError(logger,
              Level.WARNING,//
              "Error during BibTeX output of citations to '"//$NON-NLS-1$
                  + path + //
                  "'. This will lead to undefined references in the final document.",//$NON-NLS-1$
              error, true, RethrowMode.DONT_RETHROW);
      return;
    }

    if (!(Files.exists(path))) {
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(//
            "BibTeX output driver was told to produce '" + path //$NON-NLS-1$
                + "' but this file has not been produced although no error was generated. This is odd. No bibliography can be stored. This will lead to undefined references in the LaTeX document."); //$NON-NLS-1$
      }
      return;
    }

    out = this.getTextOutput();
    LaTeXDriver._endLine(out);
    name = doc._pathRelativeToDocument(path, true);
    LaTeXDriver._commentLine(("Loading " + //$NON-NLS-1$
        size + " literature references from BibTeX file " + name), out); //$NON-NLS-1$

    style = doc.m_class.getBibliographyStyle();
    if (style != null) {
      out.append(_LaTeXDocumentFooter.BIBLIOGRAPHY_STYLE_BEGIN);
      out.append(style);
      LaTeXDriver._endCommandLine(out);
    }

    out.append(_LaTeXDocumentFooter.BIBLIOGRAPHY_BEGIN);
    out.append(name);
    LaTeXDriver._endCommandLine(out);
  }

}
