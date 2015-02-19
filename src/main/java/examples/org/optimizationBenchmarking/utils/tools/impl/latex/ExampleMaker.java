package examples.org.optimizationBenchmarking.utils.tools.impl.latex;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.tools.impl.latex.LaTeX;

import examples.org.optimizationBenchmarking.FinishedPrinter;

/**
 * This class makes example PDFs discussing the page sizes and fonts.
 */
public final class ExampleMaker {

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
    final Path dest;
    final Logger logger;

    Configuration.setup(args);
    root = Configuration.getRoot();

    dest = root.getPath("dest", PathUtils.getTempDir()); //$NON-NLS-1$

    logger = Configuration.getGlobalLogger();
    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info(//
          "Now beginning to build static LaTeX examples to" + //$NON-NLS-1$
              dest);
    }

    try {
      Files.createDirectories(dest);
    } catch (final Throwable t) {
      ErrorUtils.logError(logger, "Error creating destination directory.",//$NON-NLS-1$ 
          t, false);
    }

    ExampleMaker.__makeExample(logger, "test-article.tex", dest); //$NON-NLS-1$
    ExampleMaker.__makeExample(logger, "test-report.tex", dest); //$NON-NLS-1$
    ExampleMaker.__makeExample(logger, "test-book.tex", dest); //$NON-NLS-1$
    ExampleMaker.__makeExample(logger, "test-IEEEtran.tex", dest); //$NON-NLS-1$
    ExampleMaker.__makeExample(logger, "test-llncs.tex", dest); //$NON-NLS-1$
    ExampleMaker.__makeExample(logger, "test-sig-alternate.tex", dest); //$NON-NLS-1$

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info(//
          "Building the static LaTeX examples has been completed."); //$NON-NLS-1$
    }
  }

  /**
   * Make the example
   * 
   * @param logger
   *          the logger
   * @param resource
   *          the resource
   * @param folder
   *          the folder
   */
  private static final void __makeExample(final Logger logger,
      final String resource, final Path folder) {
    final Path dest;

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info("Beginning to compile resource '" + resource + //$NON-NLS-1$
          "' to path '" + folder + "'.");//$NON-NLS-1$//$NON-NLS-2$
    }

    try {
      dest = PathUtils.createPathInside(folder, resource);
      try (final InputStream is = ExampleMaker.class
          .getResourceAsStream(resource)) {
        Files.copy(is, dest, StandardCopyOption.REPLACE_EXISTING);
      } catch (final IOException ioe) {
        ErrorUtils.logError(logger, "Error when copying resource.", //$NON-NLS-1$
            ioe, false);
        return;
      }

      try {
        LaTeX.getInstance().use()
            .setFileProducerListener(new FinishedPrinter())
            .setMainFile(dest).setLogger(logger).create().call();
      } catch (final IOException ioe) {
        ErrorUtils.logError(logger, "Error when compiling document.", //$NON-NLS-1$
            ioe, false);
        return;
      }
    } finally {
      if ((logger != null) && (logger.isLoggable(Level.INFO))) {
        logger.info("Finished compiling resource '" + resource + //$NON-NLS-1$
            "' to path '" + folder + "'.");//$NON-NLS-1$//$NON-NLS-2$
      }
    }

  }

  /** create */
  private ExampleMaker() {
    ErrorUtils.doNotCall();
  }

}
