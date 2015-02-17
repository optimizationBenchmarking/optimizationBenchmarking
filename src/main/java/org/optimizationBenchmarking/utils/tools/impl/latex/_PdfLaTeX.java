package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.FileNamePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsFilePredicate;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;
import org.optimizationBenchmarking.utils.tools.impl.process.EProcessStream;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcess;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.ProcessExecutor;

/** the PdfLaTeX tool */
final class _PdfLaTeX extends _LaTeXToolChainComponent {

  /** the executable */
  private final Path m_executable;

  /** create */
  _PdfLaTeX() {
    super();

    this.m_executable = _PdfLaTeXPathLoader.PATH;
  }

  /** {@inheritDoc} */
  @Override
  final boolean _canUse() {
    return (this.m_executable != null);
  }

  /** {@inheritDoc} */
  @Override
  final void _use(final _LaTeXMainJob job) throws IOException {
    final Logger logger;
    final Path exec, tex, aux, pdf;
    final ExternalProcessBuilder builder;
    final int ret;
    boolean ok;

    if ((exec = this.m_executable) == null) {
      throw new UnsupportedOperationException(
          "No PdfLaTeX binary detected."); //$NON-NLS-1$
    }

    logger = job._getLogger();

    if (((tex = job._getFile(ELaTeXFileType.TEX)) == null)
        || (!(Files.exists(tex)))) {
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(//
            "No .tex file was found. This should never happen."); //$NON-NLS-1$
      }
      return;
    }

    if ((aux = job._getFile(ELaTeXFileType.AUX)) == null) {
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(//
            "No .aux file was specified. This should never happen.");//$NON-NLS-1$
      }
      return;
    }
    if ((pdf = job._getFile(ELaTeXFileType.PDF)) == null) {
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(//
            "No .pdf file was specified. This should never happen.");//$NON-NLS-1$
      }
      return;
    }

    builder = ProcessExecutor.getInstance().use();
    builder.setDirectory(job._getDirectory());
    builder.setExecutable(exec);
    builder.addPathArgument(tex);
    builder.setLogger(logger);
    builder.setStdErr(EProcessStream.REDIRECT_TO_LOGGER);
    builder.setStdOut(EProcessStream.REDIRECT_TO_LOGGER);
    builder.setStdIn(EProcessStream.IGNORE);

    try (ExternalProcess proc = builder.create()) {
      if ((ret = proc.waitFor()) != 0) {
        throw new IOException((((((("PdfLaTeX executable '" //$NON-NLS-1$
            + exec) + "' returned value ") + ret) + //$NON-NLS-1$
            ", which indicates an error, for tex file '") + //$NON-NLS-1$
            tex) + '\'') + '.');
      }
    }

    ok = true;
    if (!(Files.exists(aux))) {
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(((("PdfLaTeX executable '" //$NON-NLS-1$
            + exec) + "' applied to tex file '") + //$NON-NLS-1$
            tex)
            + "' should have created an aux file, but did not.");//$NON-NLS-1$
        ok = false;
      }
    }
    if (!(Files.exists(pdf))) {
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(((("PdfLaTeX executable '" //$NON-NLS-1$
            + exec) + "' applied to tex file '") + //$NON-NLS-1$
            tex)
            + "' should have created an pdf file, but did not.");//$NON-NLS-1$
        ok = false;
      }
    }

    if (ok) {
      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine(//
            (("Finished applying PdfLaTeX to '" + tex) + '\'') + '.'); //$NON-NLS-1$
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  final ELaTeXFileType _produces() {
    return ELaTeXFileType.PDF;
  }

  /**
   * get the description
   * 
   * @return the description
   */
  static final _LaTeXToolChainComponentDesc _getDescription() {
    return __PdfLaTeXDesc.DESC;
  }

  /** the description */
  private static final class __PdfLaTeXDesc extends
      _LaTeXToolChainComponentDesc {

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC = new __PdfLaTeXDesc();

    /** create */
    private __PdfLaTeXDesc() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    final boolean _supports(final IFileType type) {
      return _LaTeXToolChainComponent._equals(ELaTeXFileType.TEX, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.PDF, type) || //
          _LaTeXToolChainComponent._equals(ELaTeXFileType.PDF, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.PNG, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.JPEG, type);
    }

    /** {@inheritDoc} */
    @Override
    final _LaTeXToolChainComponent _getComponent() {
      return __PdfLaTeXLoader.INSTANCE;
    }

    /** the loader */
    private static final class __PdfLaTeXLoader {
      /** the instance */
      static final _LaTeXToolChainComponent INSTANCE = new _PdfLaTeX();
    }
  }

  /** the loader of the path to PdfLaTeX */
  static final class _PdfLaTeXPathLoader {
    /** the path to the PdFLaTeX executable */
    static final Path PATH = PathUtils.findFirstInPath(new AndPredicate<>(
        new FileNamePredicate(true, "pdflatex" //$NON-NLS-1$
        ), CanExecutePredicate.INSTANCE),//
        IsFilePredicate.INSTANCE, null);
  }
}
