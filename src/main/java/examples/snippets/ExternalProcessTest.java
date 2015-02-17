package examples.snippets;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.FileNamePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsFilePredicate;
import org.optimizationBenchmarking.utils.math.random.LoremIpsum;
import org.optimizationBenchmarking.utils.parsers.LoggerParser;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;
import org.optimizationBenchmarking.utils.tools.impl.process.EProcessStream;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcess;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.ProcessExecutor;

/**
 * <p>
 * Here we test some simple external process execution.
 * </p>
 */
public final class ExternalProcessTest {

  /**
   * The main routine
   * 
   * @param args
   *          ignored
   * @throws IOException
   *           if something goes wrong
   */
  public static final void main(final String[] args) throws IOException {
    final Logger logger;
    Path program;
    ExternalProcessBuilder pb;

    logger = LoggerParser.INSTANCE.parseString("'global';FINER"); //$NON-NLS-1$

    // echo
    program = PathUtils.findFirstInPath(new AndPredicate<>(
        new FileNamePredicate(true, "echo"), //$NON-NLS-1$
        CanExecutePredicate.INSTANCE),//
        IsFilePredicate.INSTANCE, null);//

    System.out.println("echo: " + program); //$NON-NLS-1$
    System.out.flush();
    if (program != null) {
      pb = ProcessExecutor.getInstance().use();
      pb.setLogger(logger);
      pb.setDirectory(PathUtils.getTempDir());
      pb.setStdOut(EProcessStream.REDIRECT_TO_LOGGER);
      pb.setStdErr(EProcessStream.REDIRECT_TO_LOGGER);
      pb.setExecutable(program);
      pb.addStringArgument(LoremIpsum.loremIpsum(new Random(), 100));
      pb.setStdIn(EProcessStream.IGNORE);
      try (ExternalProcess process = pb.create()) {
        process.waitFor();
      }
    }
    System.out.flush();
    System.err.flush();

    // latex
    program = PathUtils.findFirstInPath(new AndPredicate<>(
        new FileNamePredicate(true, "pdftex", "latex"),//$NON-NLS-1$//$NON-NLS-2$
        CanExecutePredicate.INSTANCE),//
        IsFilePredicate.INSTANCE, null);//

    System.out.println("latex: " + program); //$NON-NLS-1$
    System.out.flush();
    if (program != null) {
      pb = ProcessExecutor.getInstance().use();
      pb.setLogger(logger);
      pb.setDirectory(PathUtils.getTempDir());
      pb.setStdOut(EProcessStream.REDIRECT_TO_LOGGER);
      pb.setStdErr(EProcessStream.REDIRECT_TO_LOGGER);
      pb.setExecutable(program);
      pb.addStringArgument("--help"); //$NON-NLS-1$
      pb.setStdIn(EProcessStream.IGNORE);
      try (ExternalProcess process = pb.create()) {
        process.waitFor();
      }
    }
    System.out.flush();
    System.err.flush();

    // java
    program = PathUtils.findFirstInPath(new AndPredicate<>(
        new FileNamePredicate(true, "java"),//$NON-NLS-1$
        CanExecutePredicate.INSTANCE),//
        IsFilePredicate.INSTANCE, null);//

    System.out.println("java: " + program); //$NON-NLS-1$
    System.out.flush();
    if (program != null) {
      pb = ProcessExecutor.getInstance().use();
      pb.setLogger(logger);
      pb.setDirectory(PathUtils.getTempDir());
      pb.setStdOut(EProcessStream.REDIRECT_TO_LOGGER);
      pb.setStdErr(EProcessStream.REDIRECT_TO_LOGGER);
      pb.setExecutable(program);
      pb.setStdIn(EProcessStream.IGNORE);
      try (ExternalProcess process = pb.create()) {
        process.waitFor();
      }
    }
    System.out.flush();
    System.err.flush();
  }

  /** the forbidden constructor */
  private ExternalProcessTest() {
    ErrorUtils.doNotCall();
  }
}
