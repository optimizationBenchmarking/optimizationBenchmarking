package examples.snippets;

import java.nio.file.Files;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.io.paths.TempDir;

/** Here we test the temporary folder API. */
public final class TempDirExample {

  /**
   * The main routine
   *
   * @param args
   *          ignored
   */
  public static final void main(final String[] args) {
    Path tempPath, file;

    try {
      tempPath = null;
      try (final TempDir temp = new TempDir()) {
        tempPath = temp.getPath();

        System.out.println("Inside try-with-resources scope"); //$NON-NLS-1$

        System.out.println("Temp path is: " + tempPath); //$NON-NLS-1$
        System.out.println(tempPath + " exists: " + //$NON-NLS-1$
            Files.exists(tempPath));

        file = tempPath.resolve("test.txt");//$NON-NLS-1$
        Files.createFile(file);

        System.out.println(file + " exists: " + Files.exists(file));//$NON-NLS-1$
      }

      System.out.println("After try-with-resources scope"); //$NON-NLS-1$
      System.out.println(tempPath + " exists: " + //$NON-NLS-1$
          Files.exists(tempPath));
      System.out.println(file + " exists: " + Files.exists(file));//$NON-NLS-1$

    } catch (final Throwable ttt) {
      ttt.printStackTrace(); // this should not happen
    }
  }

  /** the forbidden constructor */
  private TempDirExample() {
    ErrorUtils.doNotCall();
  }
}
