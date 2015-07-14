package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.FileNamePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsFilePredicate;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;

/** the <a href="http://luatex.org/">LuaLaTeX</a> tool */
final class _LuaLaTeX extends _LaTeXToolBase {

  /** create */
  _LuaLaTeX() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final Path _getExecutable() {
    return PathUtils.findFirstInPath(new AndPredicate<>(
        new FileNamePredicate(true, "lualatex" //$NON-NLS-1$
            ), CanExecutePredicate.INSTANCE),//
            IsFilePredicate.INSTANCE, null);
  }

  /** {@inheritDoc} */
  @Override
  final ELaTeXFileType _produces() {
    return ELaTeXFileType.PDF;
  }

  /**
   * get the description
   *
   * @param acceptEPS
   *          should we accept EPS?
   * @return the description
   */
  static final _LaTeXToolChainComponentDesc _getDescription(
      final boolean acceptEPS) {
    return (acceptEPS ? __LuaLaTeXDesc.DESC_WITH_EPS
        : __LuaLaTeXDesc.DESC_WITHOUT_EPS);
  }

  /** the description */
  private static final class __LuaLaTeXDesc extends
  _LaTeXToolChainComponentDesc {

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC_WITHOUT_EPS = new __LuaLaTeXDesc(
        false);

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC_WITH_EPS = new __LuaLaTeXDesc(
        true);

    /** should we accept eps? */
    private final boolean m_acceptEPS;

    /**
     * create
     *
     * @param acceptEPS
     *          should we accept EPS?
     */
    private __LuaLaTeXDesc(final boolean acceptEPS) {
      super();
      this.m_acceptEPS = acceptEPS;
    }

    /** {@inheritDoc} */
    @Override
    final boolean _supports(final IFileType type) {
      if (_LaTeXToolChainComponent._equals(ELaTeXFileType.TEX, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.PDF, type) || //
          _LaTeXToolChainComponent._equals(ELaTeXFileType.PDF, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.PGF, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.PNG, type) || //
          _LaTeXToolChainComponent._equals(EGraphicFormat.JPEG, type)) {
        return true;
      }
      if (_LaTeXToolChainComponent._equals(EGraphicFormat.EPS, type)) {
        return this.m_acceptEPS;
      }
      return false;
    }

    /** {@inheritDoc} */
    @Override
    final _LaTeXToolChainComponent _getComponent() {
      return __LuaLaTeXLoader.INSTANCE;
    }

    /** the loader */
    private static final class __LuaLaTeXLoader {
      /** the instance */
      static final _LaTeXToolChainComponent INSTANCE = new _LuaLaTeX();
    }
  }

}
