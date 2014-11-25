package org.optimizationBenchmarking.utils.io.paths;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.parsers.ListParser;
import org.optimizationBenchmarking.utils.parsers.PathParser;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** Some helper routines for {@link java.nio.file.Path}-based operations. */
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
   * Returns the {@code FileSystemProvider} to delegate to.
   * 
   * @param path
   *          the path
   * @return the file system provider
   */
  @SuppressWarnings("resource")
  private static final FileSystemProvider __getProvider(final Path path) {
    final FileSystem fs;
    final FileSystemProvider fsp;

    fs = path.getFileSystem();
    if (fs == null) {
      throw new IllegalArgumentException("Path '" + path + //$NON-NLS-1$
          "' has a null file system."); //$NON-NLS-1$
    }
    fsp = fs.provider();
    if (fsp == null) {
      throw new IllegalArgumentException("Path '" + path + //$NON-NLS-1$
          "' with file system '" + fs//$NON-NLS-1$
          + "' has a null file system provider.");//$NON-NLS-1$
    }
    return fsp;
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
      return PathUtils.__getProvider(path).newOutputStream(path,
          StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING,
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
      return PathUtils.__getProvider(path).newInputStream(path,
          StandardOpenOption.READ);
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
    Path parent, child, t;
    final String n;

    n = PathUtils.__prepareName(name);

    parent = PathUtils.normalize(path);
    if (parent == null) {
      parent = path;
    }

    t = parent.resolve(n);
    child = PathUtils.normalize(t);
    if (child == null) {
      child = t;
    }

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

  /**
   * <p>
   * Delete a given path recursively in a robust way.
   * </p>
   * <p>
   * We walk the file tree recursively starting at {@code path} and try to
   * delete any file and directory we encounter. If a deletion fails (with
   * an {@link java.io.IOException}), the method does not fail immediately
   * but instead enqueues the exception in an internal list and continues
   * deleting. At the end, all the caught exceptions together are thrown as
   * {@link java.lang.Throwable#addSuppressed(java.lang.Throwable)
   * suppressed} exceptions inside one {@link java.io.IOException}. Of
   * course, if a single file or folder deletion fails inside a folder, the
   * whole folder may not be deleted. However, as many files as possible
   * and as many sub-folders as possible will be removed. The exception
   * thrown in the end will provide a collection of information about what
   * went wrong. If nothing went wrong, no exception is thrown (obviously).
   * </p>
   * <p>
   * Notice that different from some solutions out there in the internet,
   * such as <a href="http://stackoverflow.com/questions/779519">http://
   * stackoverflow.com/questions/779519</a>, we do <em>not</em> try
   * platform-native commands such as {@code rm} or {@code rd}. There are
   * several reasons for this:
   * </p>
   * <ol>
   * <li>The most important one may be that the {@link java.nio.file.Path
   * Path API} is not &quot;platform-bound&quot;. It could be implemented
   * for <a href=
   * "https://docs.oracle.com/javase/7/docs/technotes/guides/io/fsp/zipfilesystemprovider.html"
   * >zip-file systems</a> as well or for memory file systems. If you would
   * have such a path and throw it into a platform-native command such as
   * {@code rd}, all hell could break loose (because that path may map to
   * anything, I think).</li>
   * <li>From an OS command, you may not get detailed error information.</li>
   * <li>Calling an OS command or running a process for each deletion may
   * actually be slower (or faster, who knows, but I like deterministic
   * behavior).</li>
   * <li>Apropos, deterministic: using OS-dependent commands may introduce
   * slight differences in behavior on different OSes.</li>
   * <li>Also, you may only be able to implement OS-specific deletion for a
   * small subset of OSes, such as Linux/Unix and Windows. And you will
   * actually somewhat depend on the versions of these OSes. Maybe the
   * parameters of the commands change in an ever-so-subtle in the future
   * or already differ amongst the many versions of Windows, for example
   * &ndash; at least I wouldn't want to check or maintain that.</li>
   * <li>Finally, I think a Java program should stay within the boundaries
   * of the virtual machine for as long as possible. I would only call
   * outside processes if absolutely necessary. This is just a general
   * design idea: Reduce outside dependencies, rely on the security
   * settings, configuration, and implementation of the JVM.</li>
   * </ol>
   * 
   * @param path
   *          the path to delete
   * @throws IOException
   *           if deletion fails
   */
  public static final void delete(final Path path) throws IOException {
    final __DeleteFileTree dft;

    if (path != null) { // if path is null or does not exist, silently exit
      if (Files.exists(path)) { // only now we need to do anything
        dft = new __DeleteFileTree();
        try {
          Files.walkFileTree(path, dft); // recursively delete
        } catch (final IOException ioe) {
          dft._storeException(ioe); // remember any additional exception
        }
        dft._throw(path); // throw an IOException if anything failed
      }
    }
  }

  /**
   * Unzip zip archive represented by an {@link java.io.InputStream} to a
   * folder. This method does not close the input stream.
   * 
   * @param input
   *          the input stream
   * @param dest
   *          the destination folder
   * @throws IOException
   *           if I/O fails
   */
  public static final void unzipToFolder(final InputStream input,
      final Path dest) throws IOException {
    final Path res;
    Path out;
    ZipEntry entry;
    byte[] buffer;
    int bytesRead;

    res = PathUtils.normalize(dest);

    try (final ZipInputStream zis = new ZipInputStream(input)) {
      buffer = null;

      while ((entry = zis.getNextEntry()) != null) {
        try {

          out = PathUtils.createPathInside(res, entry.getName());

          if (entry.isDirectory()) {
            Files.createDirectories(out);
            continue;
          }
          Files.createDirectories(out.getParent());

          try (final OutputStream fos = PathUtils.openOutputStream(out)) {
            if (buffer == null) {
              buffer = new byte[4096];
            }
            bytesRead = 0;

            while ((bytesRead = zis.read(buffer)) > 0) {
              fos.write(buffer, 0, bytesRead);
            }
          }

        } finally {
          zis.closeEntry();
        }
      }
    }
  }

  /**
   * Get a list of paths where it would make sense to search for
   * executables in. This list contains the environment's path variable, as
   * well as the folders containing each element of the <a
   * href="http://en.wikipedia.org/wiki/Classpath_%28Java%29"
   * >classpath</a>, the {@link #getCurrentDir() current directory}, the
   * {@link #getUserHomeDir() user's home directory}, and the
   * {@link #getJavaHomeDir() java home directory}. It also includes some
   * standard candidate folders, such as &quot;c:\Windows&quot; or
   * &quot;/usr/bin&quot; if they exist. All folders in the list exist.
   * 
   * @return the path
   */
  public static final ArrayListView<Path> getPath() {
    return __PathLoader.PATH;
  }

  /**
   * Get the current directory, i.e., the directory in which this program
   * was executed
   * 
   * @return the directory in which this program was executed
   */
  public static final Path getCurrentDir() {
    return __CurrentDirLoader.DIR;
  }

  /**
   * Get the java home directory
   * 
   * @return java home directory
   */
  public static final Path getJavaHomeDir() {
    return __JavaHomeDirLoader.DIR;
  }

  /**
   * Get the user home directory
   * 
   * @return user home directory
   */
  public static final Path getUserHomeDir() {
    return __UserHomeDirLoader.DIR;
  }

  /**
   * Get the temp directory
   * 
   * @return temp directory
   */
  public static final Path getTempDir() {
    return __TempDirLoader.DIR;
  }

  /** the forbidden constructor */
  private PathUtils() {
    ErrorUtils.doNotCall();
  }

  /**
   * load the current directory, i.e., the one in which the program was
   * executed
   */
  private static final class __CurrentDirLoader {

    /** the current directory */
    static final Path DIR;

    static {
      Path p1;

      try {
        p1 = java.nio.file.Paths.get(".");//$NON-NLS-1$
      } catch (final Throwable t) {
        p1 = null;
      }
      try {
        p1 = Configuration.getRoot().get("user.dir", //$NON-NLS-1$
            __DirPathParser.PARSER, p1);
      } catch (final Throwable t) {
        p1 = null;
      }
      DIR = p1;
    }
  }

  /**
   * load the java home directory
   */
  private static final class __JavaHomeDirLoader {

    /** the current directory */
    static final Path DIR;

    static {
      Path p1;

      try {
        p1 = Configuration.getRoot().get("java.home", //$NON-NLS-1$
            __DirPathParser.PARSER, null);
      } catch (final Throwable t) {
        p1 = null;
      }
      if (p1 != null) {
        DIR = p1;
      } else {
        DIR = __UserHomeDirLoader.DIR;
      }
    }
  }

  /**
   * load the user home directory
   */
  private static final class __UserHomeDirLoader {

    /** the current directory */
    static final Path DIR;

    static {
      Path p1;

      try {
        p1 = Configuration.getRoot().get("user.home", //$NON-NLS-1$
            __DirPathParser.PARSER, null);
      } catch (final Throwable t) {
        p1 = null;
      }

      if (p1 != null) {
        DIR = p1;
      } else {
        DIR = __CurrentDirLoader.DIR;
      }
    }
  }

  /**
   * load the paths the paths
   */
  private static final class __PathLoader {

    /** the path */
    static final ArrayListView<Path> PATH;

    static {
      LinkedHashSet<Path> paths;
      ListParser<Path> lister;
      ArrayListView<Path> path;
      Path p;
      Path[] array, array2;
      int i, j, size;

      lister = new ListParser<>(__DirPathParser.PARSER, true, true);

      // get paths from environment
      path = Configuration.getRoot().get(Configuration.PARAM_PATH, lister,
          null);
      paths = new LinkedHashSet<>();
      if (path != null) {
        paths.addAll(path);
        path = null;
      }

      // get paths from class path
      path = Configuration.getRoot().get("java.class.path", //$NON-NLS-1$
          lister, null);
      if (path != null) {
        paths.addAll(path);
        path = null;
      }

      // get paths from class path 2
      path = Configuration.getRoot().get("classpath", //$NON-NLS-1$
          lister, null);
      lister = null;
      if (path != null) {
        paths.addAll(path);
        path = null;
      }

      // add default paths if they exist
      for (final String def : new String[] {
          ("C:" + File.separatorChar + "Program Files"), //$NON-NLS-1$//$NON-NLS-2$
          ("C:" + File.separatorChar + "Program Paths"), //$NON-NLS-1$//$NON-NLS-2$
          ("C:" + File.separatorChar + "Program Paths (x86)"), //$NON-NLS-1$//$NON-NLS-2$
          ("C:" + File.separatorChar + "Windows"), //$NON-NLS-1$//$NON-NLS-2$
          (File.separatorChar + "etc"), //$NON-NLS-1$
          (File.separatorChar + "usr" + File.separatorChar + //$NON-NLS-1$
          "bin"), //$NON-NLS-1$
          (File.separatorChar + "usr" + File.separatorChar + //$NON-NLS-1$
          "sbin"), //$NON-NLS-1$
          (File.separatorChar + "bin"), //$NON-NLS-1$
          (File.separatorChar + "usr" + File.separatorChar + //$NON-NLS-1$
              "local" + File.separatorChar + //$NON-NLS-1$
          "bin"), //$NON-NLS-1$
          (File.separatorChar + "usr" + File.separatorChar + //$NON-NLS-1$
              "local" + File.separatorChar + //$NON-NLS-1$
          "etc"), //$NON-NLS-1$
          (File.separatorChar + "usr" + File.separatorChar + //$NON-NLS-1$
              "local" + File.separatorChar + //$NON-NLS-1$
          "sbin"), //$NON-NLS-1$
      }) {
        try {
          p = __DirPathParser.PARSER.parseString(def);
        } catch (final Throwable t) {
          p = null;
        }
        if (p != null) {
          paths.add(p);
        }
      }

      // add other paths
      p = __JavaHomeDirLoader.DIR;
      if (p != null) {
        paths.add(p);
      }

      p = __UserHomeDirLoader.DIR;
      if (p != null) {
        paths.add(p);
      }

      p = __CurrentDirLoader.DIR;
      if (p != null) {
        paths.add(p);
      }

      paths.remove(null);
      array = paths.toArray(new Path[size = paths.size()]);

      // clean paths
      outer: for (i = size; (--i) > 0;) {
        for (j = i; (--j) >= 0;) {
          if (array[i].startsWith(array[j])) {
            --size;
            System.arraycopy(array, i + 1, array, i, (size - i));
            continue outer;
          }
        }
      }

      if (size < array.length) {
        array2 = new Path[size];
        System.arraycopy(array, 0, array2, 0, size);
        array = array2;
      }

      PATH = new ArrayListView<>(array);
    }
  }

  /**
   * load the temp directory
   */
  private static final class __TempDirLoader {

    /** the current directory */
    static final Path DIR;

    static {
      Path p1, p2;

      p1 = null;
      try {
        p1 = Configuration.getRoot().get("java.io.tmpdir", //$NON-NLS-1$
            __DirPathParser.PARSER, null);
      } catch (final Throwable t) {
        // ignore
      }

      if (p1 != null) {
        DIR = p1;
      } else {
        try {

          p2 = java.nio.file.Files.createTempFile(null, null);
          if (p2 != null) {
            try {
              p1 = PathUtils.normalize(p2.getParent());
            } finally {
              Files.delete(p2);
            }
          }

        } catch (final Throwable t) {
          p1 = null;
        }

        if (p1 != null) {
          DIR = p1;
        } else {
          p1 = __CurrentDirLoader.DIR;
          if (p1 != null) {
            try {
              p1 = PathUtils.normalize(p1.resolve("temp")); //$NON-NLS-1$
            } catch (final Throwable t) {
              //
            }
          }

          DIR = p1;
        }
      }
    }
  }

  /** A path parser which returns directories. */
  private static final class __DirPathParser extends PathParser {

    /** the serial version uid */
    private static final long serialVersionUID = 1L;
    /** the instance */
    static final __DirPathParser PARSER = new __DirPathParser();

    /** the directory parser */
    private __DirPathParser() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public Path parseString(final String string) throws IOException,
        SecurityException {
      BasicFileAttributes bfa;
      Path f;

      try {
        f = java.nio.file.Paths.get(string);
        if (f == null) {
          return null;
        }

        for (;;) {
          f = PathUtils.normalize(f);
          if (f == null) {
            return null;
          }

          bfa = java.nio.file.Files.readAttributes(f,
              BasicFileAttributes.class);
          if (bfa.isDirectory()) {
            return f;
          }
          if (bfa.isRegularFile()) {
            f = f.getParent();
            continue;
          }
          return null;
        }
      } catch (final Throwable t) {//
      }
      return null;
    }

    /** {@inheritDoc} */
    @Override
    public final void validate(final Path instance)
        throws IllegalArgumentException {//
    }
  }

  /**
   * A {@link java.nio.file.FileVisitor} implementation which deletes a
   * file tree recursively, by first deleting the files and sub-directories
   * in a directory, then the directory itself.
   */
  private static final class __DeleteFileTree extends
      SimpleFileVisitor<Path> {

    /** the exceptions */
    private ArrayList<Throwable> m_exceptions;

    /** the file tree deleter's constructor */
    __DeleteFileTree() {
      super();
    }

    /**
     * store an exception (synchronized just in case)
     * 
     * @param t
     *          the exception to store
     */
    synchronized final void _storeException(final Throwable t) {
      if (t != null) {
        if (this.m_exceptions == null) {
          this.m_exceptions = new ArrayList<>();
        }
        this.m_exceptions.add(t);
      }
    }

    /**
     * Throw an {@link java.io.IOException}, if necessary: If we
     * {@link #_storeException(java.lang.Throwable) collected} any
     * exceptions during the file and folder visiting process, we now
     * create and throw an {@link java.io.IOException} which states that
     * errors took place during the deletion of the (entry-point)
     * {@code path} and which has all the collected exceptions as
     * {@link java.lang.Throwable#addSuppressed(java.lang.Throwable)
     * suppressed exceptions}.
     * 
     * @param path
     *          the root path
     * @throws IOException
     *           if necessary
     */
    final void _throw(final Path path) throws IOException {
      IOException ioe;
      if ((this.m_exceptions != null) && (!(this.m_exceptions.isEmpty()))) {
        ioe = new IOException("Error when deleting '" + path + '\''); //$NON-NLS-1$
        for (final Throwable t : this.m_exceptions) {
          ioe.addSuppressed(t);
        }
        throw ioe;
      }
    }

    /** {@iheritDoc} */
    @Override
    public final FileVisitResult visitFile(final Path file,
        final BasicFileAttributes attrs) {
      try {
        Files.delete(file);
      } catch (final Throwable t) {
        this._storeException(t);
      }
      return FileVisitResult.CONTINUE;
    }

    /** {@iheritDoc} */
    @Override
    public final FileVisitResult visitFileFailed(final Path file,
        final IOException exc) {
      if (exc != null) {
        this._storeException(exc);
      }
      try {
        Files.delete(file);
      } catch (final Throwable t) {
        this._storeException(t);
      }
      return FileVisitResult.CONTINUE;
    }

    /** {@iheritDoc} */
    @Override
    public final FileVisitResult postVisitDirectory(final Path dir,
        final IOException exc) throws IOException {
      if (exc != null) {
        this._storeException(exc);
      }
      try {
        Files.delete(dir);
      } catch (final Throwable t) {
        this._storeException(t);
      }
      return FileVisitResult.CONTINUE;
    }
  }
}
