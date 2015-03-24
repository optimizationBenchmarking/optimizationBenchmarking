package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.util.ArrayList;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
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
        for (final _LaTeXToolChainComponentDesc desc : _AllEngines.ALL_ENGINES) {
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
}
