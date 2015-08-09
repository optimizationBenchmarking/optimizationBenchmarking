package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.util.ArrayList;
import java.util.HashSet;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.tools.impl.abstr.FileProducerTool;
import org.optimizationBenchmarking.utils.tools.impl.process.ProcessExecutor;
import org.optimizationBenchmarking.utils.tools.spec.IConfigurableJobTool;

/**
 * <p>
 * The <a href="http://en.wikipedia.org/wiki/LaTeX">LaTeX</a> tool: a tool
 * which tries to auto-detect a LaTeX installation and then uses it to
 * compile
 * {@link org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType#TEX
 * tex} documents to
 * {@link org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType#PDF
 * pdf}.This tool chain basically only needs a main tex file which it is
 * supposed to compile and a list of file types used. For instance, you
 * could specify that you want to compile the document
 * {@code /tmp/foo/bar.tex} and that this document involves
 * {@link org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat#JPEG
 * jpg} and
 * {@link org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat#PDF
 * pdf} figures as well as a
 * {@link org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType#BIB
 * bib}-TeX file. The tool will then search your system for binaries (such
 * as {@code pdflatex}) which could be used to compile the document to
 * {@link org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType#PDF
 * pdf}. Based on the file types you specify, the tool chain will be
 * compiled differently. If
 * {@link org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType#BIB
 * bib} files are specified, {@code BibTeX} will be used. If you have
 * {@link org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat#EPS
 * eps} figures, then the document will be compiled with {@code latex}
 * instead of {@code pdflatex} .
 * </p>
 * <p>
 * If no tool chain could be built which satisfies the file type
 * requirements and whose required binaries could be detected on the local
 * system, the tool does nothing. If a tool chain could be built, it will
 * be executed automatically. Some parts of the tool chain may need to be
 * iterated several times (e.g., to build the references). The tool tries
 * to clean up all temporary files after execution and leaves only the
 * original files and the produced
 * {@link org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType#PDF
 * pdf}.
 * </p>
 * <p>
 * Logging is supported extensively. In the highest logging level
 * {@link java.util.logging.Level#ALL}, all the output of the executed
 * tools is logged. On lower levels, information is provided which tool is
 * to be executed and whether the execution succeeded.
 * </p>
 * <p>
 * Currently, all binaries are auto-detected via the
 * {@link org.optimizationBenchmarking.utils.io.paths.PathUtils#findFirstInPath(org.optimizationBenchmarking.utils.predicates.IPredicate, org.optimizationBenchmarking.utils.predicates.IPredicate, java.nio.file.Path[])}
 * facility. This is somewhat slow. This is why we cache paths once they
 * have been found. In the future, I will maybe add command line parameters
 * to specify binaries.
 * </p>
 * <p>
 * This tool chain has been tested with <a
 * href="http://tug.org/texlive/">TeXLive</a> under Ubuntu and <a
 * href="http://miktex.org/">MikTeX</a> under Windows.
 * </p>
 */
public class LaTeX extends FileProducerTool implements
    IConfigurableJobTool {

  /** create */
  LaTeX() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return ProcessExecutor.getInstance().canUse();
  }

  /** {@inheritDoc} */
  @Override
  public final void checkCanUse() {
    try {
      ProcessExecutor.getInstance().checkCanUse();
    } catch (final Throwable t) {
      throw new UnsupportedOperationException(
          "Cannot use LaTeX tool since we cannot execute external processes.",//$NON-NLS-1$
          t);
    }
    super.checkCanUse();
  }

  /** {@inheritDoc} */
  @Override
  public final LaTeXJobBuilder use() {
    this.checkCanUse();
    return new LaTeXJobBuilder();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "LaTeX Tool Chain"; //$NON-NLS-1$
  }

  /**
   * Sanitize a file type for checking whether it is acceptable.
   *
   * @param type
   *          the type
   * @return the type which can be added
   */
  static final IFileType _sanitizeFileType(final IFileType type) {

    if (type == null) {
      throw new IllegalArgumentException(
          "A file type for which you require LaTeX tool chain support cannot be null."); //$NON-NLS-1$
    }

    if (type instanceof EGraphicFormat) {
      return type;
    }

    for (final ELaTeXFileType types : ELaTeXFileType.INSTANCES) {
      if (_LaTeXToolChainComponent._equals(types, type)) {
        if (types._canRequire()) {
          return types;
        }
        if (types == ELaTeXFileType.PDF) {
          return EGraphicFormat.PDF;
        }

        return null;
      }
    }

    throw new IllegalArgumentException(
        (("No LaTeX tool chain can be required to understand type '" //$NON-NLS-1$
        + type) + '\'') + '.');
  }

  /**
   * Is there a tool chain for the given types?
   *
   * @param types
   *          the types
   * @return {@code true} if there is a tool chain, {@code false} otherwise
   */
  public final boolean hasToolChainFor(final IFileType... types) {
    final HashSet<IFileType> set;
    IFileType put;

    if (types == null) {
      return false;
    }

    if (!(this.canUse())) {
      return false;
    }

    set = new HashSet<>();
    for (final IFileType type : types) {
      if (type != null) {
        put = LaTeX._sanitizeFileType(type);
        if (put != null) {
          set.add(put);
        }
      }
    }

    return (LaTeX._findToolChain(set) != null);
  }

  /**
   * try to find a tool chain for the given required types
   *
   * @param types
   *          the types
   * @return the tool chain (1st element: loop, 2nd element: refine)
   */
  static final _LaTeXToolChainComponent[][] _findToolChain(
      final HashSet<IFileType> types) {
    final _LaTeXToolChainComponent bibtex, main;
    final ArrayList<_LaTeXToolChainComponent> loop;
    final _LaTeXToolChainComponent[] refine;
    IFileType[] required;

    loop = new ArrayList<>();

    // bibtex is the only ELaTeXFileType we care about
    if (types.remove(ELaTeXFileType.BIB)) {
      bibtex = LaTeX.__bibtex();
      if (bibtex == null) {
        return null;
      }
      loop.add(bibtex);
    }

    // to others we don't care
    types.removeAll(ELaTeXFileType.INSTANCES);
    required = types.toArray(new IFileType[types.size()]);
    main = LaTeX.__tex(required);

    if (main == null) {
      return null;
    }

    required = null;
    switch (main._produces()) {
      case PDF: {
        refine = null;
        break;
      }

      case DVI: {
        refine = LaTeX.__dvi2pdf();
        if (refine == null) {
          return null;
        }
        break;
      }

      case PS: {
        refine = LaTeX.__ps2pdf();
        if (refine == null) {
          return null;
        }
        break;
      }

      default: {
        return null;
      }
    }

    loop.add(0, main);

    return new _LaTeXToolChainComponent[][] {
        loop.toArray(new _LaTeXToolChainComponent[loop.size()]), refine };
  }

  /**
   * Get a tool chain able to convert dvi files to pdf, or {@code null} if
   * none is found
   *
   * @return the tool chain, or {@code null} if none is found
   */
  private static final _LaTeXToolChainComponent[] __dvi2pdf() {
    return __DVI_2_PDF.CHAIN;
  }

  /**
   * Get the tool to be used for bibtex, or {@code null} if none is found
   *
   * @return the tool to be used for bibtex, or {@code null} if none is
   *         found
   */
  private static final _LaTeXToolChainComponent __bibtex() {
    return __BIBTEX.BIBTEX;
  }

  /**
   * Get the tool to be used for ps to pdf conversion, or {@code null} if
   * none is
   *
   * @return the tool to be used for ps to pdf conversion , or {@code null}
   *         if none is found
   */
  private static final _LaTeXToolChainComponent[] __ps2pdf() {
    return __PS_2_PDF.CHAIN;
  }

  /**
   * Get the globally shared instance of the LaTeX tool
   *
   * @return the globally shared instance of the LaTeX tool
   */
  public static final LaTeX getInstance() {
    return __LaTeXLoader.INSTANCE;
  }

  /**
   * Obtain all the supported graphic formats. This method returns a list
   * of all graphic formats which may potentially be supported by a tool
   * chain.
   *
   * @return all the supported graphic formats
   */
  public static final ArraySetView<EGraphicFormat> getAllSupportedGraphicFormats() {
    return __AllSupportedGraphicFormatsLoader.ALL;
  }

  /** the loader */
  private static final class __LaTeXLoader {
    /** the instance */
    static final LaTeX INSTANCE = new LaTeX();
  }

  /** find all the supported graphics formats */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private static final class __AllSupportedGraphicFormatsLoader {

    /** all the supported graphic formats */
    static final ArraySetView<EGraphicFormat> ALL;

    static {
      final ArrayList<EGraphicFormat> list;
      final int size;

      list = new ArrayList<>(EGraphicFormat.INSTANCES.size());
      main: for (final EGraphicFormat format : EGraphicFormat.INSTANCES) {
        for (final _LaTeXToolChainComponentDesc desc : new _AllEngines()) {
          if (desc._supports(format)) {
            list.add(format);
            continue main;
          }
        }
      }

      size = list.size();
      if (size > 0) {
        ALL = new ArraySetView<>(list.toArray(new EGraphicFormat[size]));
      } else {
        ALL = ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
      }
    }
  }

  /**
   * Try to find a LaTeX tool chain component for the given file types
   *
   * @param required
   *          the required file types
   * @return the tool chain component
   */
  private static final _LaTeXToolChainComponent __tex(
      final IFileType[] required) {
    _LaTeXToolChainComponent comp;

    mainLoop: for (final _LaTeXToolChainComponentDesc desc : new _AllEngines()) {
      if (desc == null) {
        continue mainLoop;
      }

      if (required != null) {
        for (final IFileType type : required) {
          if (!(desc._supports(type))) {
            continue mainLoop;
          }
        }
      }

      comp = desc._getComponent();
      if (comp == null) {
        continue mainLoop;
      }
      if (!(comp._canUse())) {
        continue mainLoop;
      }

      return comp;
    }

    return null;
  }

  /** the BibTeX to use */
  private static final class __BIBTEX {
    /** the tool chain */
    static final _LaTeXToolChainComponent BIBTEX;

    static {
      _LaTeXToolChainComponentDesc desc;
      _LaTeXToolChainComponent bibtex;

      bibtex = null;
      desc = _BibTeX._getDescription();
      if (desc != null) {
        bibtex = desc._getComponent();
        if ((bibtex != null) && (!(bibtex._canUse()))) {
          bibtex = null;
        }
      }

      if (bibtex == null) {
        desc = _BibTeX8._getDescription();
        if (desc != null) {
          bibtex = desc._getComponent();
          if ((bibtex != null) && (!(bibtex._canUse()))) {
            bibtex = null;
          }
        }
      }

      BIBTEX = bibtex;
    }
  }

  /** the dvi to pdf chain */
  private static final class __DVI_2_PDF {

    /** the tool chain */
    static final _LaTeXToolChainComponent[] CHAIN;

    static {
      _LaTeXToolChainComponentDesc desc;
      _LaTeXToolChainComponent dvi2ps, ps2pdf;

      dvi2ps = null;
      desc = _Dvi2Ps._getDescription();
      if (desc != null) {
        dvi2ps = desc._getComponent();
        if ((dvi2ps != null) && (!(dvi2ps._canUse()))) {
          dvi2ps = null;
        }
      }

      ps2pdf = null;
      if (dvi2ps != null) {
        ps2pdf = __PS_2_PDF.CHAIN[0];
      }

      if ((dvi2ps != null) && (ps2pdf != null)) {
        CHAIN = new _LaTeXToolChainComponent[] { dvi2ps, ps2pdf };
      } else {
        CHAIN = null;
      }

    }
  }

  /** the ps to pdf chain */
  private static final class __PS_2_PDF {

    /** the tool */
    static final _LaTeXToolChainComponent[] CHAIN;

    static {
      _LaTeXToolChainComponentDesc desc;
      _LaTeXToolChainComponent ps2pdf;

      ps2pdf = null;
      desc = _GhostScript._getDescription();
      if (desc != null) {
        ps2pdf = desc._getComponent();
        if ((ps2pdf != null) && (!(ps2pdf._canUse()))) {
          ps2pdf = null;
        }
      }

      if (ps2pdf == null) {
        desc = _Ps2Pdf._getDescription();
        if (desc != null) {
          ps2pdf = desc._getComponent();
          if ((ps2pdf != null) && (!(ps2pdf._canUse()))) {
            ps2pdf = null;
          }
        }
      }
      CHAIN = new _LaTeXToolChainComponent[] { ps2pdf };
    }
  }

}
