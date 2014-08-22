package org.optimizationBenchmarking.utils.io.files.predicates;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import org.optimizationBenchmarking.utils.predicates.IPredicate;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.predicates.StringInListIgnoreCase;

/**
 * This predicate is {@code true} if the file can be executed.
 */
public final class CanExecutePredicate implements IPredicate<File>,
    Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  public static final CanExecutePredicate INSTANCE = new CanExecutePredicate();

  /** extensions for executables */
  private static StringInListIgnoreCase<String> s_executable_extensions = null;

  /**
   * Obtain the executable extensions
   * 
   * @return the executable extensions
   */
  private static final StringInListIgnoreCase<String> __findExecutableExtensions() {
    final ArrayList<String> l;
    String s;
    String[] ext;
    int i;

    if (CanExecutePredicate.s_executable_extensions == null) {

      l = new ArrayList<>();
      l.add(".exe"); //$NON-NLS-1$
      l.add(".bat"); //$NON-NLS-1$
      l.add(".com"); //$NON-NLS-1$
      l.add(".sh"); //$NON-NLS-1$

      s = null;
      try {
        s = TextUtils.prepare(System.getenv("PATHEXT")); //$NON-NLS-1$
        if (s == null) {
          s = TextUtils.prepare(System.getenv("pathext")); //$NON-NLS-1$
        }
      } catch (final SecurityException se) {
        //
      }

      if (s != null) {
        ext = s.split(File.pathSeparator);
        if (ext != null) {
          for (i = (ext.length - 1); i >= 0; i--) {
            s = TextUtils.prepare(ext[i]);
            if (s != null) {
              s = s.toLowerCase();
              if (!(l.contains(s))) {
                l.add(s);
              }
            }
          }
        }
      }

      CanExecutePredicate.s_executable_extensions = new StringInListIgnoreCase<>(
          l);
    }
    return CanExecutePredicate.s_executable_extensions;
  }

  /** create */
  private CanExecutePredicate() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean check(final File file) {
    final int dot;
    final String name;

    if (file == null) {
      return false;
    }

    if (file.exists() && file.isFile()) {

      if (file.canExecute()) {
        if (File.separatorChar != '\\') {
          // hack for windows/dos checking: under windows, many files are
          // executable, so only if we are not under windows, let's return
          // here
          return true;
        }
      }

      name = file.getName();
      dot = name.lastIndexOf('.');
      if (dot < 0) {
        return false;
      }

      return CanExecutePredicate.__findExecutableExtensions().check(
          name.substring(dot));
    }

    return false;
  }

  /**
   * write replace
   * 
   * @return the replacement
   */
  private final Object writeReplace() {
    return CanExecutePredicate.INSTANCE;
  }

  /**
   * read resolve
   * 
   * @return the replacement
   */
  private final Object readResolve() {
    return CanExecutePredicate.INSTANCE;
  }
}
