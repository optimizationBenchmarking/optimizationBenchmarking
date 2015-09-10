package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.FileNamePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsFilePredicate;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;

/** the <a href="http://en.wikipedia.org/wiki/XeTeX">XeLaTeX</a> tool */
final class _XeLaTeX extends _LaTeXToolBase {

  /** create */
  _XeLaTeX() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final Path _getExecutable() {
    return PathUtils.findFirstInPath(new AndPredicate<>(
        new FileNamePredicate(true, "xelatex" //$NON-NLS-1$
        ), CanExecutePredicate.INSTANCE),//
        IsFilePredicate.INSTANCE, this._getVisitFirst());
  }

  /** {@inheritDoc} */
  @Override
  final Path[] _getVisitFirst() {
    return _LaTeXToolChainComponent._visitBefore(
        new String[] { "/usr/bin/xelatex" }, //$NON-NLS-1$
        super._getVisitFirst());
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
    return __XeLaTeXDesc.DESC;
  }

  /** the description */
  private static final class __XeLaTeXDesc extends
      _LaTeXToolChainComponentDesc {

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC = new __XeLaTeXDesc();

    /** create */
    private __XeLaTeXDesc() {
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
      return __XeLaTeXLoader.INSTANCE;
    }

    /** the loader */
    private static final class __XeLaTeXLoader {
      /** the instance */
      static final _LaTeXToolChainComponent INSTANCE = new _XeLaTeX();
    }
  }

}
