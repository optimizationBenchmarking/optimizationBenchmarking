package org.optimizationBenchmarking.utils.io.files;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.optimizationBenchmarking.utils.reflection.GetSystemEnvironment;
import org.optimizationBenchmarking.utils.reflection.GetSystemProperty;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * The common files an application should know about.
 */
public final class Paths {

  /** The current directory. */
  private static File s_currentDir = null;

  /** The java home directory. */
  private static File s_JavaHomeDir = null;

  /** The user directory. */
  private static File s_userHomeDir = null;

  /** The temp directory. */
  private static File s_tempDir = null;

  /** the path */
  private static Collection<File> s_path = null;

  /**
   * Get a list of paths where it would make sense to search for
   * executables in. This list contains the environment's path variable, as
   * well as the {@link #getCurrentDir() current directory}, the
   * {@link #getUserHomeDir() user's home directory}, and the
   * {@link #getJavaHomeDir() java home directory}. It also includes some
   * standard candidate folders. All folders in the list exist.
   *
   * @return the path
   */
  public static final Collection<File> getPath() {
    String s;
    String[] x;
    final ArrayList<File> cf;
    File f;

    if (Paths.s_path == null) {

      cf = new ArrayList<>();
      Paths.getCurrentDir();
      Paths.getJavaHomeDir();
      Paths.getUserHomeDir();
      Paths.getTempDir();

      find: {
        s = TextUtils.prepare(new GetSystemEnvironment("path").call()); //$NON-NLS-1$
        if (s == null) {
          s = TextUtils.prepare(new GetSystemEnvironment("PATH").call()); //$NON-NLS-1$
          if (s == null) {
            s = TextUtils
                .prepare(new GetSystemEnvironment("$path").call()); //$NON-NLS-1$
            if (s == null) {
              s = TextUtils
                  .prepare(new GetSystemEnvironment("%PATH%").call()); //$NON-NLS-1$
              if (s == null) {
                s = TextUtils.prepare(//
                    new GetSystemEnvironment("$PATH").call()); //$NON-NLS-1$
                if (s == null) {
                  break find;
                }
              }
            }
          }
        }

        x = s.split(File.pathSeparator);
        if (x != null) {
          for (final String path : x) {
            try {
              f = Paths.__reduce(path);
              if ((f != null) && (!(cf.contains(f)))) {
                cf.add(f);
              }
            } catch (final Throwable t) {
              //
            }
          }
        }
      }

      for (final String def : new String[] {
          ("C:" + File.separatorChar + "Program Files"), //$NON-NLS-1$//$NON-NLS-2$
          ("C:" + File.separatorChar + "Program Files (x86)"), //$NON-NLS-1$//$NON-NLS-2$
          ("C:" + File.separatorChar + "Windows"), //$NON-NLS-1$//$NON-NLS-2$
          ("C:" + File.separatorChar + "Windows" //$NON-NLS-1$//$NON-NLS-2$
              + File.separatorChar + "System32"), //$NON-NLS-1$
          ("C:" + File.separatorChar + "Windows" //$NON-NLS-1$//$NON-NLS-2$
              + File.separatorChar + "System"), //$NON-NLS-1$
          (File.separatorChar + "usr" + File.separatorChar + //$NON-NLS-1$
          "bin"), //$NON-NLS-1$
          (File.separatorChar + "usr" + File.separatorChar + //$NON-NLS-1$
          "sbin"), //$NON-NLS-1$
          (File.separatorChar + "bin"), //$NON-NLS-1$
          (File.separatorChar + "sbin"), //$NON-NLS-1$
          (File.separatorChar + "usr" + File.separatorChar + //$NON-NLS-1$
              "local" + File.separatorChar + //$NON-NLS-1$
          "bin"), //$NON-NLS-1$
          (File.separatorChar + "usr" + File.separatorChar + //$NON-NLS-1$
              "local" + File.separatorChar + //$NON-NLS-1$
          "sbin"), //$NON-NLS-1$
      }) {
        try {
          f = Paths.__reduce(def);
          if ((f != null) && (!(cf.contains(f)))) {
            cf.add(f);
          }
        } catch (final Throwable t) {
          //
        }
      }

      f = Paths.getCurrentDir();
      if ((f != null) && (!(cf.contains(f)))) {
        cf.add(f);
      }

      f = Paths.getUserHomeDir();
      if ((f != null) && (!(cf.contains(f)))) {
        cf.add(f);
      }

      f = Paths.getJavaHomeDir();
      if ((f != null) && (!(cf.contains(f)))) {
        cf.add(f);
      }

      Paths.s_path = Collections.unmodifiableCollection(cf);
    }
    return Paths.s_path;
  }

  /**
   * Get the current directory, i.e., the directory in which this program
   * was executed
   *
   * @return the directory in which this program was executed
   */
  public static final File getCurrentDir() {
    if (Paths.s_currentDir == null) {
      try {
        Paths.s_currentDir = Paths.__reduce(new GetSystemProperty(
            "user.dir").call()); //$NON-NLS-1$;
      } catch (final Throwable t) {
        Paths.s_currentDir = null;
      }
      if (Paths.s_currentDir == null) {
        try {
          Paths.s_currentDir = Paths.__reduce("."); //$NON-NLS-1$
        } catch (final Throwable t) {
          Paths.s_currentDir = null;
        }
      }
      if (Paths.s_currentDir == null) {
        Paths.s_currentDir = Paths.getUserHomeDir();
      }
    }
    return Paths.s_currentDir;
  }

  /**
   * Get the java home directory
   *
   * @return java home directory
   */
  public static final File getJavaHomeDir() {
    if (Paths.s_JavaHomeDir == null) {
      Paths.s_JavaHomeDir = Paths.__reduce(new GetSystemProperty(
          "java.home").call()); //$NON-NLS-1$;
    }
    return Paths.s_JavaHomeDir;
  }

  /**
   * Get the user home directory
   *
   * @return user home directory
   */
  public static final File getUserHomeDir() {
    if (Paths.s_userHomeDir == null) {
      Paths.s_userHomeDir = Paths.__reduce(//
          new GetSystemProperty("user.home").call()); //$NON-NLS-1$;
    }
    return Paths.s_userHomeDir;
  }

  /**
   * Get the temp directory
   *
   * @return temp directory
   */
  public static final File getTempDir() {
    File f2;

    if (Paths.s_tempDir == null) {
      try {
        Paths.s_tempDir = Paths.__reduce(new GetSystemProperty(
            "java.io.tmpdir").call()); //$NON-NLS-1$;
      } catch (final Throwable t) {
        Paths.s_tempDir = null;
      }

      if (Paths.s_tempDir == null) {
        try {
          f2 = File.createTempFile("test", "test"); //$NON-NLS-1$;//$NON-NLS-2$;
          if (f2 != null) {
            try {
              Paths.s_tempDir = Paths.__reduce(f2.getParent());
            } finally {
              f2.delete();
            }
          }
        } catch (final Throwable t) {
          Paths.s_tempDir = null;
        }

        if (Paths.s_tempDir == null) {
          Paths.s_tempDir = Paths.getCurrentDir();
        }
      }

    }

    return Paths.s_tempDir;
  }

  /**
   * reduce the files
   *
   * @param fn
   *          the file
   * @return the reduced one
   */
  private static final File __reduce(final String fn) {
    final File f;

    if (fn != null) {
      try {
        f = new CanonicalizeFile(fn).call();
      } catch (final Throwable t) {
        return null;
      }

      if ((f != null) && (f.exists()) && (f.isDirectory())) {

        if (f.equals(Paths.s_JavaHomeDir)) {
          return Paths.s_JavaHomeDir;
        }
        if (f.equals(Paths.s_currentDir)) {
          return Paths.s_currentDir;
        }
        if (f.equals(Paths.s_userHomeDir)) {
          return Paths.s_userHomeDir;
        }
        if (f.equals(Paths.s_tempDir)) {
          return Paths.s_tempDir;
        }
        return f;
      }
    }
    return null;
  }
}
