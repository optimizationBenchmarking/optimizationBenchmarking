package org.optimizationBenchmarking.utils.io.files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.optimizationBenchmarking.utils.collections.visitors.IVisitor;
import org.optimizationBenchmarking.utils.predicates.ConstantPredicate;
import org.optimizationBenchmarking.utils.predicates.IPredicate;
import org.optimizationBenchmarking.utils.tasks.Task;

/**
 * This task drags a
 * {@link org.optimizationBenchmarking.utils.collections.visitors.IVisitor
 * visitor} through all files and directory recursively in post order,
 * starting at a given file or root directory. Its {@code #call()} method
 * returns a boolean corresponding to whether the visitor ever returned
 * {@code false} or not.
 */
public final class FileSystemWalker extends Task<Boolean> {

  /** the file */
  private final File m_file;

  /** the visitor to drag through the files */
  private final IVisitor<? super File> m_visitor;

  /** a predicate checking whether we should descend into a directory */
  private final IPredicate<? super File> m_shouldDescend;

  /** should we visit directories? */
  private final boolean m_visitDirectories;

  /** should we visit files? */
  private final boolean m_visitFiles;

  /**
   * create
   * 
   * @param file
   *          the file to search
   * @param visitor
   *          the visitor that will visit the directories and files
   * @param shouldDescend
   *          should we descent
   * @param visitDirectories
   *          should we visit directories
   * @param visitFiles
   *          should we visit files
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public FileSystemWalker(final File file,
      final IVisitor<? super File> visitor,
      final IPredicate<? super File> shouldDescend,
      final boolean visitDirectories, final boolean visitFiles) {
    super();
    this.m_file = file;
    this.m_visitor = visitor;
    this.m_shouldDescend = ((shouldDescend != null) ? shouldDescend
        : ((IPredicate) (ConstantPredicate.TRUE)));
    this.m_visitDirectories = visitDirectories;
    this.m_visitFiles = visitFiles;
  }

  /**
   * create
   * 
   * @param file
   *          the file to search
   * @param visitor
   *          the visitor that will visit the directories and files
   * @param visitDirectories
   *          should we visit directories
   * @param visitFiles
   *          should we visit files
   */
  public FileSystemWalker(final File file,
      final IVisitor<? super File> visitor,
      final boolean visitDirectories, final boolean visitFiles) {
    this(file, visitor, null, visitDirectories, visitFiles);
  }

  /**
   * create
   * 
   * @param file
   *          the file to search
   * @param visitor
   *          the visitor that will visit the directories and files
   */
  public FileSystemWalker(final File file, final IVisitor<File> visitor) {
    this(file, visitor, true, true);
  }

  /** {@inheritDoc} */
  @Override
  public final Boolean call() throws IOException, SecurityException {
    final File f;
    final ArrayList<String> lst;

    f = new CanonicalizeFile(this.m_file).call();
    if (f == null) {
      return Boolean.TRUE;
    }

    lst = new ArrayList<>();
    lst.add(f.getPath());
    return Boolean.valueOf(this.__walk(f, lst));
  }

  /**
   * Walk the files and directories
   * 
   * @param file
   *          the file
   * @param path
   *          the path
   * @return {@code true} if we can stop walking the directories,
   *         {@code false} otherwise
   * @throws IOException
   *           if io fails
   * @throws SecurityException
   *           if security fails
   */
  private final boolean __walk(final File file,
      final ArrayList<String> path) throws IOException, SecurityException {
    final File[] fs;
    final boolean visit;
    File z;
    String b;

    if (file.exists()) {
      if (file.isDirectory()) {
        if (this.m_shouldDescend.check(file)) {

          fs = file.listFiles();
          if (fs != null) {
            outer: for (final File p : fs) {

              z = new CanonicalizeFile(p).call();
              if (z == null) {
                continue outer;
              }

              // This is primitive recursion prevention method that relies
              // on
              // the file name canonicalization.
              b = z.getPath();
              for (final String v : path) {
                if (v.startsWith(b)) {
                  continue outer;
                }
              }

              path.add(b);
              b = null;
              if (!(this.__walk(z, path))) {
                // we don't need to clean-up path, since it is not used
                // anymore
                return false;
              }
              path.remove(path.size() - 1);
            }
          }
        }
        visit = this.m_visitDirectories;
      } else {
        visit = (file.isFile() && this.m_visitFiles);
      }

      if (visit && (!(this.m_visitor.visit(file)))) {
        return false;
      }

    }

    return true;
  }
}
