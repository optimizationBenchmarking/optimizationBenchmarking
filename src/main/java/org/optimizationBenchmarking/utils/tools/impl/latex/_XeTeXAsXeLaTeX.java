package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.FileNamePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsFilePredicate;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;

/** a tool using XeTeX as XeLaTeX */
final class _XeTeXAsXeLaTeX extends _LaTeXUsedAsToolBase {

  /** create */
  _XeTeXAsXeLaTeX() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final Path _getExecutable() {
    return _XeTeXPathLoader.PATH;
  }

  /** {@inheritDoc} */
  @Override
  final String _getProcName() {
    return "xelatex"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  final boolean _needsOutputFormat() {
    return false;
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
    return __XeTeXAsXeLaTeXDesc.DESC;
  }

  /** the loader of the path to XeTeX */
  static final class _XeTeXPathLoader {
    /** the path to the XeTeX executable */
    static final Path PATH = PathUtils.findFirstInPath(new AndPredicate<>(
        new FileNamePredicate(true, "xetex" //$NON-NLS-1$
        ), CanExecutePredicate.INSTANCE),//
        IsFilePredicate.INSTANCE,//
        _LaTeXToolChainComponent._visitBefore(//
            new String[] { "/usr/bin/xetex" },//$NON-NLS-1$
            _LaTeXToolChainComponent._getDefaultVisitFirst()));
  }

  /** the description */
  private static final class __XeTeXAsXeLaTeXDesc extends
      _LaTeXToolChainComponentDesc {

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC = new __XeTeXAsXeLaTeXDesc();

    /** create */
    private __XeTeXAsXeLaTeXDesc() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    final boolean _supports(final IFileType type) {
      return _LaTeXToolChainComponent._equals(ELaTeXFileType.TEX, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.PDF, type) || //
          _LaTeXToolChainComponent._equals(ELaTeXFileType.PDF, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.EPS, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.PGF, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.PNG, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.JPEG, type);//
      // XeLaTeX also supports bmp, seemingly, but may sometimes fail with
      // "! Dimension too large." error. Thus, we leave the following line
      // _LaTeXToolChainComponent._equals(EGraphicFormat.BMP, type);
      // away.
    }

    /** {@inheritDoc} */
    @Override
    final _LaTeXToolChainComponent _getComponent() {
      return __XeTeXAsXeLaTeXLoader.INSTANCE;
    }

    /** the loader */
    private static final class __XeTeXAsXeLaTeXLoader {
      /** the instance */
      static final _LaTeXToolChainComponent INSTANCE = new _XeTeXAsXeLaTeX();
    }
  }
}
