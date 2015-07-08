package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.tools.impl.latex._PdfTeXAsLaTeX._PdfTeXPathLoader;

/** a tool using PdfTeX as PdfLaTeX */
final class _PdfTeXAsPdfLaTeX extends _LaTeXUsedAsToolBase {

  /** create */
  _PdfTeXAsPdfLaTeX() {
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
    return "pdflatex"; //$NON-NLS-1$
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
    return __PdfTeXAsPdfLaTeXDesc.DESC;
  }

  /** the description */
  private static final class __PdfTeXAsPdfLaTeXDesc extends
      _LaTeXToolChainComponentDesc {

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC = new __PdfTeXAsPdfLaTeXDesc();

    /** create */
    private __PdfTeXAsPdfLaTeXDesc() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    final boolean _supports(final IFileType type) {
      return _LaTeXToolChainComponent._equals(ELaTeXFileType.TEX, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.PDF, type) || //
          _LaTeXToolChainComponent._equals(ELaTeXFileType.PDF, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.PGF, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.PNG, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.JPEG, type);
    }

    /** {@inheritDoc} */
    @Override
    final _LaTeXToolChainComponent _getComponent() {
      return __PdfTeXAsPdfLaTeXLoader.INSTANCE;
    }

    /** the loader */
    private static final class __PdfTeXAsPdfLaTeXLoader {
      /** the instance */
      static final _LaTeXToolChainComponent INSTANCE = new _PdfTeXAsPdfLaTeX();
    }
  }
}
