package org.optimizationBenchmarking.utils.io.path;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** some helper routines for path-base IO */
public final class PathUtils {

  /**
   * Check whether a path is {@code null}
   * 
   * @param path
   *          the path to check
   */
  private static final void __pathNotNull(final Path path) {
    if (path == null) {
      throw new IllegalArgumentException("Path must not be null."); //$NON-NLS-1$
    }
  }

  /**
   * Try to find a canonical version of a given path
   * 
   * @param path
   *          the path
   * @return the canonicalized version
   */
  public static final Path normalize(final Path path) {
    Path before, p, q;

    PathUtils.__pathNotNull(path);

    before = path;
    for (;;) {
      p = before;

      try {
        q = p.normalize();
        if ((q != null) && (q != p)) {
          p = q;
        }
      } catch (final Throwable t) {
        // ignore
      }

      try {
        q = p.toAbsolutePath();
        if ((q != null) && (q != p)) {
          p = q;
        }
      } catch (final Throwable t) {
        // ignore
      }

      try {
        q = p.toRealPath();
        if ((q != null) && (q != p)) {
          p = q;
        }
      } catch (final Throwable t) {
        // ignore
      }

      if (p.equals(before)) {
        return before;
      }
      before = p;
    }

  }

  /**
   * Open an output stream to a given path
   * 
   * @param path
   *          the path
   * @return the output stream
   */
  public static final OutputStream openOutputStream(final Path path) {
    Throwable error;
    Path parent;

    PathUtils.__pathNotNull(path);

    error = null;
    try {
      parent = path.getParent();
    } catch (final Throwable t1) {
      error = t1;
      parent = null;
    }

    if (parent != null) {
      try {
        Files.createDirectories(parent);
      } catch (final Throwable t2) {
        error = ErrorUtils.aggregateError(error, t2);
      }
    }

    try {
      return path
          .getFileSystem()
          .provider()
          .newOutputStream(path, StandardOpenOption.CREATE,
              StandardOpenOption.TRUNCATE_EXISTING,
              StandardOpenOption.WRITE);
    } catch (final Throwable t3) {
      ErrorUtils.throwAsRuntimeException(t3, error);
    }
    return null;// this will never be reached
  }

  /**
   * Open an input stream from a given path
   * 
   * @param path
   *          the path
   * @return the input stream
   */
  public static final InputStream openInputStream(final Path path) {
    PathUtils.__pathNotNull(path);

    try {
      return path.getFileSystem().provider()
          .newInputStream(path, StandardOpenOption.READ);
    } catch (final Throwable t3) {
      ErrorUtils.throwAsRuntimeException(t3);
    }
    return null;// this will never be reached
  }

  /**
   * Prepare a file name
   * 
   * @param name
   *          the file name
   * @return the prepared name
   */
  private static final String __prepareName(final String name) {
    final String n;

    n = TextUtils.prepare(name);
    if (n == null) {
      throw new IllegalArgumentException(//
          "Name must not be empty or null, but is '" //$NON-NLS-1$
              + name + '\'');//
    }
    return n;
  }

  /**
   * Add a given name to a path. This corresponds to creating a path
   * referencing sub-directory or file inside a folder.
   * 
   * @param path
   *          the path to a directory
   * @param name
   *          the file name
   * @return the canonicalized version
   */
  public static final Path createPathInside(final Path path,
      final String name) {
    final Path parent, child;
    final String n;

    n = PathUtils.__prepareName(name);
    parent = PathUtils.normalize(path);
    child = PathUtils.normalize(parent.resolve(n));

    if (child.startsWith(parent)) {
      return child;
    }

    throw new IllegalArgumentException(//
        "File '" + child + //$NON-NLS-1$
            "' is not located inside folder '" + parent + //$NON-NLS-1$
            "' and thus, the name '" + name + //$NON-NLS-1$
            "' is invalid.");//$NON-NLS-1$
  }

  /**
   * Make a file name based on a given name suggestion and an extension.
   * 
   * @param nameSuggestion
   *          the file name suggestion
   * @param extension
   *          the extension, i.e., the file name suffix, either with or
   *          without leading {@code '.'}
   * @return the new file name
   */
  public static final String makeFileName(final String nameSuggestion,
      final String extension) {
    final String n, e;
    final int nl, el, ne;
    final boolean z;

    n = PathUtils.__prepareName(nameSuggestion);

    e = TextUtils.prepare(extension);
    if (e == null) {
      return n;
    }

    nl = n.length();
    el = e.length();

    z = (e.charAt(0) == '.');
    if (nl > el) {
      ne = (nl - el);
      if (n.substring(ne).equalsIgnoreCase(e)) {
        if (z) {
          return n;
        }
        if (n.charAt(ne - 1) == '.') {
          return n;
        }
      }
    }

    return ((z ? n : (n + '.')) + e);
  }

  /** the forbidden constructor */
  private PathUtils() {
    ErrorUtils.doNotCall();
  }
}
