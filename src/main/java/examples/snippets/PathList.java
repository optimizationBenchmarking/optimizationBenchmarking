package examples.snippets;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;

/**
 * <p>
 * The class {@link org.optimizationBenchmarking.utils.io.paths.PathUtils}
 * provides unified access to some special paths, such as the user home,
 * the java home, but also to an (extended) version of the <a
 * href="http://en.wikipedia.org/wiki/PATH_%28variable%29">PATH</a>
 * variable of the operating system, i.e., the list of folders where to
 * look for binaries. All is based on the {@link java.nio.file.Path Path
 * API}.
 * </p>
 */
public final class PathList {

  /**
   * The main routine
   * 
   * @param args
   *          ignored
   */
  public static final void main(final String[] args) {
    System.out.println("User home:\t" + PathUtils.getUserHomeDir()); //$NON-NLS-1$
    System.out.println("Java home:\t" + PathUtils.getJavaHomeDir()); //$NON-NLS-1$
    System.out.println("Current dir:\t" + PathUtils.getCurrentDir()); //$NON-NLS-1$
    System.out.println("Temp dir:\t" + PathUtils.getTempDir()); //$NON-NLS-1$
    System.out.println();
    System.out.println("Path elements:"); //$NON-NLS-1$
    for (final Path p : PathUtils.getPath()) {
      System.out.print('\t');
      System.out.println(p);
    }
  }

  /** the forbidden constructor */
  private PathList() {
    ErrorUtils.doNotCall();
  }
}
