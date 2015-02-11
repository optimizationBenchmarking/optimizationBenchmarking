package examples.snippets;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.FileNamePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsFilePredicate;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;

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
    System.out.println(" === The Paths === "); //$NON-NLS-1$
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

    System.out.println();
    System.out.println(" === Searching for Some Applications === "); //$NON-NLS-1$

    for (final String[] s : new String[][] {//
    { "gs", "gswin64", "gswin64c",//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
        "gswin32", "gswin32c" },//$NON-NLS-1$//$NON-NLS-2$
        { "R", },//$NON-NLS-1$
        { "evince", "AcroRd32", "AcroRd64", //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
            "acroreader", "acrobat" }, //$NON-NLS-1$//$NON-NLS-2$
        { "java", }, //$NON-NLS-1$
        { "latex", "pdftex" },//$NON-NLS-1$//$NON-NLS-2$
        { "xetex", },//$NON-NLS-1$
        { "bibtex", "bibtex.original" }, //$NON-NLS-1$//$NON-NLS-2$
        { "pdftex", "pdflatex" }, //$NON-NLS-1$ //$NON-NLS-2$
        { "gedit", "notepad" }, //$NON-NLS-1$//$NON-NLS-2$
        { "gcc", "g++", "gpp" }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        { "vlc", }, //$NON-NLS-1$
    }) {
      System.out.print(s[0] + ":\t");//$NON-NLS-1$
      System.out.println(//
          PathUtils.findFirstInPath(
              new AndPredicate<>(new FileNamePredicate(true, s),
                  CanExecutePredicate.INSTANCE),//
              IsFilePredicate.INSTANCE,//
              new Path[] { null,//
                  Paths.get("C:\\windows\\") }//$NON-NLS-1$
              ));
    }

    for (final String s : new String[] { "tools.jar", //$NON-NLS-1$
        "rt.jar", //$NON-NLS-1$
    }) {
      System.out.print(s + ":\t");//$NON-NLS-1$
      System.out.println(//
          PathUtils.findFirstInPath(new FileNamePredicate(false,
              new String[] { s }), IsFilePredicate.INSTANCE,//
              new Path[] { null,//
                  Paths.get("C:\\windows\\") }//$NON-NLS-1$
              ));
    }
  }

  /** the forbidden constructor */
  private PathList() {
    ErrorUtils.doNotCall();
  }
}
