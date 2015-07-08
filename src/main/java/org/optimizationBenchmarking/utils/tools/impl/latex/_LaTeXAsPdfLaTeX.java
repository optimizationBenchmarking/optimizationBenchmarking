package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.IFileType;

/** a tool using LaTeX as PdfLaTeX */
final class _LaTeXAsPdfLaTeX extends _LaTeXUsedAsToolBase {

  /** create */
  _LaTeXAsPdfLaTeX() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final Path _getExecutable() {
    return _LaTeX._LaTexPathLoader.PATH;
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
    return __LaTeXAsPdfLaTeXDesc.DESC;
  }

  /** the description */
  private static final class __LaTeXAsPdfLaTeXDesc extends
      _LaTeXToolChainComponentDesc {

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC = new __LaTeXAsPdfLaTeXDesc();

    /** create */
    private __LaTeXAsPdfLaTeXDesc() {
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
      return __LaTeXAsPdfLaTeXLoader.INSTANCE;
    }

    /** the loader */
    private static final class __LaTeXAsPdfLaTeXLoader {
      /** the instance */
      static final _LaTeXToolChainComponent INSTANCE = new _LaTeXAsPdfLaTeX();
    }
  }

}
