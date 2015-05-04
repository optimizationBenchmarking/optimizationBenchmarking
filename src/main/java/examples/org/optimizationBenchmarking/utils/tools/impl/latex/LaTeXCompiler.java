package examples.org.optimizationBenchmarking.utils.tools.impl.latex;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.tools.impl.latex.LaTeX;
import org.optimizationBenchmarking.utils.tools.impl.latex.LaTeXJobBuilder;

import examples.org.optimizationBenchmarking.FinishedPrinter;

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
 * <p>
 * Invoke a LaTeX compiler tool chain from the command line, use with the
 * following command line arguments:
 * </p>
 * <ul>
 * <li>{@code latexSource=xxx}: path to the latex source file to be
 * compiled, required.</li>
 * <li>{@code latexUsedFormats=aaa;bbb;cc}: list of the formats that need
 * to be processed additionally to {@code tex}. E.g. {@code bib},
 * {@code JPEG}, {@code eps}, etc.</li>
 * <li>{@code logger="global";ALL} log all output (or {@code FINE} etc.,
 * optional</li>
 * </ul>
 */
public final class LaTeXCompiler {

  /**
   * The main routine
   *
   * @param args
   *          the command line arguments
   * @throws Exception
   *           if something fails
   */
  public final static void main(final String[] args) throws Exception {
    final Configuration root;
    final LaTeXJobBuilder builder;
    final Callable<Void> job;
    final Logger logger;

    Configuration.setup(args);
    root = Configuration.getRoot();

    logger = Configuration.getGlobalLogger();
    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info(//
          "Now beginning to build LaTeX job. This entails searching for executables and may take a while."); //$NON-NLS-1$
      logger.info(//
          "Btw, LaTeX supports the following graphic formats: " //$NON-NLS-1$
          + LaTeX.getAllSupportedGraphicFormats());
    }

    builder = LaTeX.getInstance().use();
    builder.configure(root);
    builder.setFileProducerListener(new FinishedPrinter());
    job = builder.create();

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info(//
          "LaTeX job has been configured and created and will now be executed."); //$NON-NLS-1$
    }

    job.call();

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info(//
          "LaTeX job has been completed."); //$NON-NLS-1$
    }
  }

  /** create */
  private LaTeXCompiler() {
    ErrorUtils.doNotCall();
  }

}
