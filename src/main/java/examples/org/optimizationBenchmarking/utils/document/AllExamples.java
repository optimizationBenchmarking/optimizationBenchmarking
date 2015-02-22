package examples.org.optimizationBenchmarking.utils.document;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.optimizationBenchmarking.utils.io.paths.PathUtils;

/**
 * A quick wrapper to invoke all graphics examples.
 */
public class AllExamples {

  /**
   * The main method
   * 
   * @param args
   *          the command line arguments
   * @throws Throwable
   *           if something goes wrong
   */
  public static void main(final String[] args) throws Throwable {
    final Path dir;
    final String[] indi;

    if ((args != null) && (args.length > 0)) {
      dir = Paths.get(args[0]);
      indi = args.clone();
    } else {
      dir = Files.createTempDirectory("document"); //$NON-NLS-1$
      indi = new String[1];
    }

    indi[0] = PathUtils.getPhysicalPath(//
        dir.resolve("template"), false);//$NON-NLS-1$
    TemplateDocumentExample.main(indi);

    indi[0] = PathUtils.getPhysicalPath(//
        dir.resolve("random"), false);//$NON-NLS-1$
    RandomDocumentExample.main(indi);
  }

}
