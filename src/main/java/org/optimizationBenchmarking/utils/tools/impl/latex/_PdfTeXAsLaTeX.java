package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.FileNamePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsFilePredicate;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;

/** a tool using PdfTeX as LaTeX */
final class _PdfTeXAsLaTeX extends _LaTeXUsedAsToolBase {

  /** create */
  _PdfTeXAsLaTeX() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final Path _getExecutable() {
    return _PdfTeXPathLoader.PATH;
  }

  /** {@inheritDoc} */
  @Override
  final String _getProcName() {
    return "latex"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  final ELaTeXFileType _produces() {
    return ELaTeXFileType.DVI;
  }

  /**
   * get the description
   *
   * @return the description
   */
  static final _LaTeXToolChainComponentDesc _getDescription() {
    return __PdfLaTeXAsLaTeXDesc.DESC;
  }

  /** the description */
  private static final class __PdfLaTeXAsLaTeXDesc extends
      _LaTeXToolChainComponentDesc {

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC = new __PdfLaTeXAsLaTeXDesc();

    /** create */
    private __PdfLaTeXAsLaTeXDesc() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    final boolean _supports(final IFileType type) {
      return _LaTeXToolChainComponent._equals(ELaTeXFileType.TEX, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.PGF, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.EPS, type);
    }

    /** {@inheritDoc} */
    @Override
    final _LaTeXToolChainComponent _getComponent() {
      return __PdfTeXAsLaTeXLoader.INSTANCE;
    }

    /** the loader */
    private static final class __PdfTeXAsLaTeXLoader {
      /** the instance */
      static final _LaTeXToolChainComponent INSTANCE = new _PdfTeXAsLaTeX();
    }
  }

  /** the loader of the path to PdfTeX */
  static final class _PdfTeXPathLoader {
    /** the path to the PdfTeX executable */
    static final Path PATH = PathUtils.findFirstInPath(new AndPredicate<>(
        new FileNamePredicate(true, "pdftex" //$NON-NLS-1$
        ), CanExecutePredicate.INSTANCE),//
        IsFilePredicate.INSTANCE,//
        _LaTeXToolChainComponent._visitBefore(//
            new String[] { "/usr/bin/pdftex" },//$NON-NLS-1$
            _LaTeXToolChainComponent._getDefaultVisitFirst()));
  }
}
