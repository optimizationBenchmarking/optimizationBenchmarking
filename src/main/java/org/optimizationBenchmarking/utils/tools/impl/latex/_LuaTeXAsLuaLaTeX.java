package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.FileNamePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsFilePredicate;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;

/** a tool using LuaTeX as LuaLaTeX */
final class _LuaTeXAsLuaLaTeX extends _LaTeXUsedAsToolBase {

  /** create */
  _LuaTeXAsLuaLaTeX() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final Path _getExecutable() {
    return _LuaTeXPathLoader.PATH;
  }

  /** {@inheritDoc} */
  @Override
  final String _getProcName() {
    return "lualatex"; //$NON-NLS-1$
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
    return (acceptEPS ? __LuaTeXAsLuaLaTeXDesc.DESC_WITH_EPS
        : __LuaTeXAsLuaLaTeXDesc.DESC_WITHOUT_EPS);
  }

  /** the loader of the path to LuaTeX */
  static final class _LuaTeXPathLoader {
    /** the path to the LuaTeX executable */
    static final Path PATH = PathUtils.findFirstInPath(new AndPredicate<>(
        new FileNamePredicate(true, "luatex" //$NON-NLS-1$
            ), CanExecutePredicate.INSTANCE),//
            IsFilePredicate.INSTANCE, null);
  }

  /** the description */
  private static final class __LuaTeXAsLuaLaTeXDesc extends
  _LaTeXToolChainComponentDesc {

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC_WITHOUT_EPS = new __LuaTeXAsLuaLaTeXDesc(
        false);

    /** the description */
    static final _LaTeXToolChainComponentDesc DESC_WITH_EPS = new __LuaTeXAsLuaLaTeXDesc(
        true);

    /** should we accept eps? */
    private final boolean m_acceptEPS;

    /**
     * create
     *
     * @param acceptEPS
     *          should we accept EPS?
     */
    private __LuaTeXAsLuaLaTeXDesc(final boolean acceptEPS) {
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
      return __LuaTeXAsLuaLaTeXLoader.INSTANCE;
    }

    /** the loader */
    private static final class __LuaTeXAsLuaLaTeXLoader {
      /** the instance */
      static final _LaTeXToolChainComponent INSTANCE = new _LuaTeXAsLuaLaTeX();
    }
  }
}
