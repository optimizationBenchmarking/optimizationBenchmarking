package org.optimizationBenchmarking.utils.io.paths;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.spi.FileSystemProvider;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.parsers.ListParser;
import org.optimizationBenchmarking.utils.parsers.PathParser;
import org.optimizationBenchmarking.utils.predicates.IPredicate;
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
  public static final FileSystemProvider getFileSystemProvider(
      final Path path) {
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
   * Canonicalize a file
   * 
   * @param f
   *          the file
   * @return the canonicalized version
   */
  static final File _canonicalize(final File f) {
    File result;

    try {
      result = f.getCanonicalFile();
      if (result != null) {
        return result;
      }
    } catch (final Throwable t) {//
    }

    try {
      result = f.getAbsoluteFile();
      if (result != null) {
        return result;
      }
    } catch (final Throwable t2) {
      //
    }

    return f;
  }

  /**
   * Get a file representing the physical path. This method will never
   * return {@code null}. If no physical file can be derived from the path,
   * an {@link java.lang.IllegalArgumentException} will be thrown.
   * 
   * @param path
   *          the path
   * @return the file representing the physical path
   * @throws IllegalArgumentException
   *           if the path cannot be represented as file
   */
  public static final File getPhysicalFile(final Path path)
      throws IllegalArgumentException {
    Path normPath;
    File file;
    Throwable error;
    String message;

    normPath = PathUtils.normalize(path);
    error = null;
    try {
      file = normPath.toFile();
    } catch (final UnsupportedOperationException use) {
      error = use;
      file = null;
    }

    if (file != null) {
      try {
        return AccessController.doPrivileged(new __Canonicalizer(file));
      } catch (final Throwable t) {
        return PathUtils._canonicalize(file);
      }
    }

    message = ("Cannot translate path '" + path + //$NON-NLS-1$
    "' to a physical file path."); //$NON-NLS-1$;
    if (error != null) {
      throw new IllegalArgumentException(message, error);
    }
    throw new IllegalArgumentException(message);
  }

  /**
   * Get a string representing the physical path. This method will never
   * return {@code null}. If no physical file can be derived from the path,
   * an {@link java.lang.IllegalArgumentException} will be thrown.
   * 
   * @param path
   *          the path
   * @return the string representing the physical path
   */
  public static final String getPhysicalPath(final Path path) {
    final String result;

    result = PathUtils.getPhysicalFile(path).toString();
    if (result != null) {
      return result;
    }
    throw new IllegalArgumentException(//
        "Error when obtaining path string from '" + //$NON-NLS-1$
            path + '\'');
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
      return PathUtils.getFileSystemProvider(path).newOutputStream(path,
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
      return PathUtils.getFileSystemProvider(path).newInputStream(path,
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
   * Get the name of a file or folder
   * 
   * @param fileOrFolder
   *          the file or folder
   * @return the name of the file or folder, or {@code null}
   */
  public static final String getName(final Path fileOrFolder) {
    Path p;

    if (fileOrFolder == null) {
      return null;
    }

    p = PathUtils.normalize(fileOrFolder);
    p = ((p != null) ? p : fileOrFolder).getFileName();
    if (p == null) {
      return null;
    }
    return p.toString();
  }

  /**
   * Get the extension (file name suffix) of a given file, not including
   * the dot and cast to lower case.
   * 
   * @param file
   *          the file
   * @return the extension, or {@code null} if no extension exists
   */
  public static final String getFileExtension(final Path file) {
    final String str;
    final int last;

    str = PathUtils.getName(file);
    if (str == null) {
      return null;
    }

    last = str.lastIndexOf('.');
    if ((last < 0) || (last >= (str.length() - 1))) {
      return null;
    }
    return str.substring(last + 1).toLowerCase();
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
   * <p>
   * Get a list of paths where it would make sense to search for
   * executables or libraries.
   * </p>
   * <p>
   * This list contains the environment's <a
   * href="http://en.wikipedia.org/wiki/PATH_%28variable%29">PATH</a>
   * variable, as well as the folders containing each element of the <a
   * href="http://en.wikipedia.org/wiki/Classpath_%28Java%29"
   * >classpath</a>, the {@link #getCurrentDir() current directory} and the
   * {@link #getJavaHomeDir() java home directory}. It also includes some
   * standard candidate folders, such as &quot;{@code C:\Windows} &quot; or
   * &quot;{@code /usr/bin}&quot; and folders where we may look for
   * libraries (such as &quot;{@code /usr/local/lib}&quot; if they exist.
   * Again: All folders in the list exist.
   * </p>
   * <p>
   * Since the path components are built in a system-specific order, a path
   * like {@code C:\Windows\System32} may appear before {@code C:\Windows}.
   * The reason for this may be priorities defined by the user or OS which
   * we do not want to ignore. This means that when searching the sub-trees
   * for a given binary, we would look at the sub-tree
   * {@code C:\Windows\System32} twice. In order to avoid that, you should
   * use {@link #visitPath(FileVisitor, Path...)} which automatically skips
   * these folders. (Notice that the list returned by {@link #getPath()}
   * will never contain something like {@code C:\Windows} followed by
   * {@code C:\Windows\System32}, though, as we can skip such
   * constellations without modifying any result of a search process.)
   * </p>
   * 
   * @return the path
   * @see #visitPath(FileVisitor, Path...)
   * @see #findFirstInPath(IPredicate, IPredicate, Path[])
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

  /**
   * First, visit the {@link java.nio.file.Path paths} in the array
   * {@code visitVist} (if they exist). If the {@code visitor} does not
   * indicate that it wishes to
   * {@link java.nio.file.FileVisitResult#TERMINATE} terminate, then visit
   * the elements of the {@link #getPath() PATH}
   * <em>that are likely to contain useful libraries or binaries</em>. For
   * instance, although we will visit the files in &quot;{@code C:\Windows}
   * &quot;, we will not visit &quot;{@code C:\Windows\Temp}&quot; or any
   * other known temporary folder. This method provides the basic
   * infrastructure on which
   * {@link #findFirstInPath(IPredicate, IPredicate, Path[])} is built.
   * 
   * @param visitor
   *          the visitor
   * @param visitFirst
   *          {@code null} or a list of paths to visit <em>before</em>
   *          walking through the {@link #getPath() PATH}
   * @return the result
   * @throws IOException
   *           if I/O fails
   * @see #getPath()
   * @see #findFirstInPath(IPredicate, IPredicate, Path[])
   */
  public static final FileVisitResult visitPath(
      final FileVisitor<Path> visitor, final Path[] visitFirst)
      throws IOException {
    final __PathVisitor visitorWrapper;
    Path[] pathSet;
    FileVisitResult r;

    if (visitor == null) {
      throw new IllegalArgumentException("Path visitor cannot be null."); //$NON-NLS-1$
    }

    visitorWrapper = new __PathVisitor(visitor);

    visitorWrapper.m_inUserDefinedTemplates = true;
    pathSet = visitFirst;
    for (;;) {
      if (pathSet != null) {
        for (Path p : pathSet) {
          if (p == null) {
            continue;
          }

          if (visitorWrapper.m_inUserDefinedTemplates) {
            p = PathUtils.normalize(p);
          }

          Files.walkFileTree(p, visitorWrapper);
          if (((r = visitorWrapper.m_lastResult) == null)
              || (r == FileVisitResult.TERMINATE)) {
            break;
          }
        }
      }

      if (visitorWrapper.m_inUserDefinedTemplates) {
        pathSet = __PathLoader.PATH_ARRAY;
        visitorWrapper.m_inUserDefinedTemplates = false;
      } else {
        break;
      }
    }

    return visitorWrapper.m_lastResult;
  }

  /**
   * Find a {@link java.nio.file.Path path} that matches the given
   * {@link org.optimizationBenchmarking.utils.predicates.IPredicate
   * pathPredicate} and whose attributes match the
   * {@link org.optimizationBenchmarking.utils.predicates.IPredicate
   * attsPredicate}. Therefore, first, visit the {@link java.nio.file.Path
   * paths} in the array {@code visitVist}. If no fitting path was found,
   * then visit the elements of the {@link #getPath() PATH}.
   * 
   * @param visitFirst
   *          {@code null} or a list of paths to visit <em>before</em>
   *          walking through the {@link #getPath() PATH}. Elements in this
   *          array may also be {@code null} or non-existing paths.
   * @param pathPredicate
   *          the path predicate to match
   * @param attsPredicate
   *          the attributes predicate
   * @return the result
   * @see #visitPath(FileVisitor, Path[])
   * @see #getPath()
   */
  public static final Path findFirstInPath(
      final IPredicate<Path> pathPredicate,
      final IPredicate<BasicFileAttributes> attsPredicate,
      final Path[] visitFirst) {
    final __FindFirst ff;

    if ((pathPredicate == null) && (attsPredicate == null)) {
      return null;
    }

    ff = new __FindFirst(pathPredicate, attsPredicate);

    try {
      PathUtils.visitPath(ff, visitFirst);
    } catch (final Throwable t) {
      //
    }

    return ff.m_found;
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
      final Configuration config;
      Path p1;

      p1 = null;

      finder: {
        try {
          config = Configuration.getRoot();
          try {
            p1 = config.get("user.home", //$NON-NLS-1$
                __DirPathParser.PARSER, null);
            break finder;
          } catch (final Throwable t) {
            p1 = null;
          }

          for (final String def : new String[] {//
          "HOME", //$NON-NLS-1$
              "HOMEPATH", //$NON-NLS-1$
              "USERPROFILE", //$NON-NLS-1$
          }) {
            p1 = config.get(def, __DirPathParser.PARSER, null);
            if (p1 != null) {
              break finder;
            }
          }
        } catch (final Throwable tt) {
          p1 = null;
        }
      }

      if (p1 != null) {
        DIR = p1;
      } else {
        DIR = __CurrentDirLoader.DIR;
      }
    }
  }

  /** load the paths the paths */
  private static final class __PathLoader {

    /** the path */
    static final ArrayListView<Path> PATH;

    /** the path */
    static final Path[] PATH_ARRAY;

    static {
      final Configuration config;
      LinkedHashSet<Path> paths;
      ListParser<Path> lister;
      ArrayListView<Path> path;
      Path p;
      Path[] array, array2;
      int i, j, size;

      // get paths from environment
      config = Configuration.getRoot();

      paths = new LinkedHashSet<>();
      lister = new ListParser<>(__DirPathParser.PARSER, true, true);

      // get path lists from environment variables or java configuration
      for (final String key : new String[] {//
      Configuration.PARAM_PATH,// PATH
          "java.class.path",// class path //$NON-NLS-1$
          "classpath",//environment class path //$NON-NLS-1$
          "java.library.path",//path to native libraries//$NON-NLS-1$
      }) {
        path = config.get(key, lister, null);
        if (path != null) {
          paths.addAll(path);
          path = null;
        }
      }
      lister = null;

      // add paths from environment variables or java config
      for (final String key : new String[] {//
      "ProgramFiles", //$NON-NLS-1$
          "ProgramFiles(x86)", //$NON-NLS-1$
          "ProgramW6432)", //$NON-NLS-1$
          "CommonProgramFiles", //$NON-NLS-1$
          "CommonProgramFiles(x86)", //$NON-NLS-1$
          "CommonProgramW6432", //$NON-NLS-1$
          "SystemRoot", //$NON-NLS-1$
          "windir", //$NON-NLS-1$
          "SHELL", //$NON-NLS-1$
          "COMPIZ_BIN_PATH", //$NON-NLS-1$
      }) {
        p = config.get(key, __DirPathParser.PARSER, null);
        if (p != null) {
          paths.add(p);
        }
      }

      // add default paths if they exist
      for (final String template : new String[] {//
      "C:/Program Files", //$NON-NLS-1$
          "C:/Program Files (x86)", //$NON-NLS-1$
          "C:/Windows", //$NON-NLS-1$
          "/etc", //$NON-NLS-1$
          "/bin", //$NON-NLS-1$
          "/usr/bin", //$NON-NLS-1$
          "/usr/lib", //$NON-NLS-1$
          "/usr/sbin", //$NON-NLS-1$ 
          "/usr/local/bin", //$NON-NLS-1$
          "/usr/local/etc", //$NON-NLS-1$
          "/usr/local/lib", //$NON-NLS-1$
          "/usr/local/sbin", //$NON-NLS-1$
      }) {
        try {
          p = __DirPathParser.PARSER.parseString(template);
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
      PATH_ARRAY = array;

      PATH = new ArrayListView<>(__PathLoader.PATH_ARRAY);
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
        final IOException exc) {
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

  /**
   * A visitor for the {@link PathUtils#getPath() PATH} which tries to skip
   * unnecessary visits to nested path components. This visitor should be
   * immune to some circular hard links and other shenanigans to some
   * degree at least.
   */
  private static final class __PathVisitor extends SimpleFileVisitor<Path> {

    /** some paths we should always skip when traveling through the path */
    static final Path[] SKIP;

    static {
      final HashSet<Path> skip;
      Path p, win;

      skip = new HashSet<>();
      p = PathUtils.getTempDir();
      if (p != null) {
        skip.add(p);
      }
      for (final String template : new String[] {//
      "/tmp", //$NON-NLS-1$
          "/etc/cups/ssl", //$NON-NLS-1$
          "/etc/ssl/private", //$NON-NLS-1$
          "/etc/polkit-1/localauthority",//$NON-NLS-1$
      }) {
        try {
          p = __DirPathParser.PARSER.parseString(template);
          if (p != null) {
            skip.add(p);
          }
        } catch (final Throwable t) {//
        }
      }

      win = Configuration.getRoot().get(
          "windir", __DirPathParser.PARSER, null);//$NON-NLS-1$
      if (win == null) {
        try {
          win = __DirPathParser.PARSER.parseString("C:\\Windows"); //$NON-NLS-1$
        } catch (final Throwable t) {
          win = null;
        }
      }

      if (win != null) {

        // add default paths if they exist
        for (final String template : new String[] {//
        "addins", //$NON-NLS-1$
            "ADFS", //$NON-NLS-1$
            "AppCompat", //$NON-NLS-1$
            "apppatch", //$NON-NLS-1$
            "AppReadiness", //$NON-NLS-1$
            "assembly", //$NON-NLS-1$
            "BitLockerDiscoveryVolumeContents", //$NON-NLS-1$
            "Boot", //$NON-NLS-1$
            "Branding", //$NON-NLS-1$
            "Camera", //$NON-NLS-1$
            "CbsTemp", //$NON-NLS-1$
            "CSC", //$NON-NLS-1$
            "Cursors", //$NON-NLS-1$
            "de-DE", //$NON-NLS-1$
            "debug", //$NON-NLS-1$
            "DesktopTileResources", //$NON-NLS-1$
            "diagnostics", //$NON-NLS-1$
            "DigitalLocker", //$NON-NLS-1$
            "Downloaded Program Files", //$NON-NLS-1$
            "en-US", //$NON-NLS-1$
            "Fonts", //$NON-NLS-1$
            "Globalization", //$NON-NLS-1$
            "Help", //$NON-NLS-1$
            "History", //$NON-NLS-1$
            "IME", //$NON-NLS-1$
            "ImmersiveControlPanel", //$NON-NLS-1$
            "Inf", //$NON-NLS-1$
            "InputMethod", //$NON-NLS-1$
            "Installer", //$NON-NLS-1$
            "L2Schemas", //$NON-NLS-1$
            "LiveKernelReports", //$NON-NLS-1$
            "Logs", //$NON-NLS-1$
            "Media", //$NON-NLS-1$
            "MediaViewer", //$NON-NLS-1$
            "Minidump", //$NON-NLS-1$
            "ModemLogs", //$NON-NLS-1$
            "Offline Web Pages", //$NON-NLS-1$
            "Panther", //$NON-NLS-1$
            "PCHEALTH", //$NON-NLS-1$
            "Performance", //$NON-NLS-1$
            "PLA", //$NON-NLS-1$
            "PolicyDefinitions", //$NON-NLS-1$
            "Prefetch", //$NON-NLS-1$
            "Registration", //$NON-NLS-1$
            "rescache", //$NON-NLS-1$
            "Resources", //$NON-NLS-1$
            "SchCache", //$NON-NLS-1$
            "schemas", //$NON-NLS-1$
            "security", //$NON-NLS-1$
            "ServiceProfiles", //$NON-NLS-1$
            "servicing", //$NON-NLS-1$
            "Setup", //$NON-NLS-1$
            "ShellNew", //$NON-NLS-1$
            "SKB", //$NON-NLS-1$
            "SoftwareDistribution", //$NON-NLS-1$
            "Speech", //$NON-NLS-1$1$
            "SystemResources", //$NON-NLS-1$
            "TAPI", //$NON-NLS-1$
            "Tasks", //$NON-NLS-1$
            "Temp", //$NON-NLS-1$
            "ToastData", //$NON-NLS-1$
            "tracing", //$NON-NLS-1$
            "twain_32", //$NON-NLS-1$
            "vpnplugins", //$NON-NLS-1$
            "Vss", //$NON-NLS-1$
            "Web", //$NON-NLS-1$
            "WinStore", //$NON-NLS-1$
            "WinSxS", //$NON-NLS-1$
            "zh-CN", //$NON-NLS-1$
        }) {
          try {
            p = PathUtils.createPathInside(win, template);
            if ((p != null) && (Files.isDirectory(p))) {
              skip.add(p);
            }
          } catch (final Throwable t) {
            //
          }
        }
      }

      SKIP = skip.toArray(new Path[skip.size()]);
    }

    /** the visitor to carry around */
    private final FileVisitor<Path> m_visitor;

    /** the paths already done */
    private final HashSet<Path> m_done;

    /** are we in the user-defined template root mode? */
    boolean m_inUserDefinedTemplates;

    /** the last file visit result */
    volatile FileVisitResult m_lastResult;

    /**
     * create the path visitor
     * 
     * @param visitor
     *          the visitor to carry around
     */
    __PathVisitor(final FileVisitor<Path> visitor) {
      super();
      this.m_visitor = visitor;

      this.m_done = new HashSet<>(4096);
      for (final Path skip : __PathVisitor.SKIP) {
        this.m_done.add(skip);
      }

      this.m_lastResult = FileVisitResult.CONTINUE;
    }

    /**
     * Add an element to a hash set in a synchronized way &ndash;
     * synchronization here is "just-in-case". Who knows what these new
     * Java versions will parallelize.
     * 
     * @param p
     *          the path to add
     * @param s
     *          the set
     * @return the return value
     */
    private static final boolean __add(final Path p, final HashSet<Path> s) {
      synchronized (s) {
        return s.add(p);
      }
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult preVisitDirectory(final Path dir,
        final BasicFileAttributes attrs) throws IOException {

      if ((dir == null) || (attrs == null)
          || (!(__PathVisitor.__add(dir, this.m_done)))) {
        return ((this.m_lastResult == FileVisitResult.TERMINATE) ? FileVisitResult.TERMINATE
            : FileVisitResult.SKIP_SUBTREE);
      }

      return (this.m_lastResult = this.m_visitor.preVisitDirectory(dir,
          attrs));
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult visitFile(final Path file,
        final BasicFileAttributes attrs) throws IOException {
      if ((file != null) && (attrs != null)) {
        return (this.m_lastResult = this.m_visitor.visitFile(file, attrs));
      }

      switch (this.m_lastResult) {
        case TERMINATE: {
          return FileVisitResult.TERMINATE;
        }
        case SKIP_SIBLINGS: {
          return FileVisitResult.SKIP_SIBLINGS;
        }
        default: {
          return FileVisitResult.CONTINUE;
        }
      }
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult visitFileFailed(final Path file,
        final IOException exc) throws IOException {
      if ((file != null)
          && ((!(this.m_inUserDefinedTemplates)) || (!(exc instanceof NoSuchFileException)))) {
        return (this.m_lastResult = this.m_visitor.visitFileFailed(file,
            exc));
      }
      return ((this.m_lastResult == FileVisitResult.TERMINATE) ? FileVisitResult.TERMINATE
          : FileVisitResult.CONTINUE);
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult postVisitDirectory(final Path dir,
        final IOException exc) throws IOException {

      if (dir == null) {
        return ((this.m_lastResult == FileVisitResult.TERMINATE) ? FileVisitResult.TERMINATE
            : FileVisitResult.CONTINUE);
      }
      return (this.m_lastResult = this.m_visitor.postVisitDirectory(dir,
          exc));
    }
  }

  /**
   * A visitor for the {@link PathUtils#getPath() PATH} which returns the
   * first file matching to a given criterion.
   */
  private static final class __FindFirst extends SimpleFileVisitor<Path> {

    /** the path predicate */
    private final IPredicate<Path> m_pathPredicate;

    /** the attributes predicate */
    private final IPredicate<BasicFileAttributes> m_attsPredicate;

    /** the result */
    volatile Path m_found;

    /**
     * create the path visitor
     * 
     * @param pathPredicate
     *          the path predicate to match
     * @param attsPredicate
     *          the attributes predicate
     */
    __FindFirst(final IPredicate<Path> pathPredicate,
        final IPredicate<BasicFileAttributes> attsPredicate) {
      super();
      this.m_pathPredicate = pathPredicate;
      this.m_attsPredicate = attsPredicate;
    }

    /**
     * Check whether the predicates match
     * 
     * @param path
     *          the path
     * @param attrs
     *          the attributes
     * @return {@code true} if and only if the predicates match,
     *         {@code false} otherwise
     */
    private final FileVisitResult __check(final Path path,
        final BasicFileAttributes attrs) {
      check: {
        checkAtts: {
          if (this.m_attsPredicate != null) {
            try {
              if (this.m_attsPredicate.check(attrs)) {
                break checkAtts;
              }
            } catch (final Throwable t) {
              //
            }
            break check;
          }
        }

        if (this.m_pathPredicate != null) {
          try {
            if (this.m_pathPredicate.check(path)) {
              this.m_found = path;
              return FileVisitResult.TERMINATE;
            }

          } catch (final Throwable t) {
            //
          }
        }
      }
      return ((this.m_found == null) ? FileVisitResult.CONTINUE
          : FileVisitResult.TERMINATE);
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult preVisitDirectory(final Path dir,
        final BasicFileAttributes attrs) {
      return this.__check(dir, attrs);
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult visitFile(final Path file,
        final BasicFileAttributes attrs) {
      return this.__check(file, attrs);
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult visitFileFailed(final Path file,
        final IOException exc) {
      return ((this.m_found == null) ? FileVisitResult.CONTINUE
          : FileVisitResult.TERMINATE);
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult postVisitDirectory(final Path dir,
        final IOException exc) throws IOException {
      return ((this.m_found == null) ? FileVisitResult.CONTINUE
          : FileVisitResult.TERMINATE);
    }
  }

  /**
   * This small private class helps to canonicalize a file by tunneling
   * this operation through a <code>PrivilegedAction</code>.
   */
  private static final class __Canonicalizer implements
      PrivilegedAction<File> {
    /** The file to canonicalize. */
    private final File m_file;

    /**
     * The constructor of the canonicalizer.
     * 
     * @param file
     *          The file to be canonicalized.
     */
    __Canonicalizer(final File file) {
      super();
      this.m_file = file;
    }

    /** {@inheritDoc} */
    @Override
    public final File run() {
      return PathUtils._canonicalize(this.m_file);
    }

  }

}
