package org.optimizationBenchmarking.utils.io.files;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.optimizationBenchmarking.utils.predicates.IPredicate;
import org.optimizationBenchmarking.utils.tasks.Task;

/** Find a given file.<@javaAutorVersion/> */
public final class FindFile extends Task<File> {

  /** the predicate */
  final IPredicate<? super File> m_predicate;

  /** did we find something? */
  private final File[] m_paths;

  /** a predicate checking whether we should descend into a directory */
  private final IPredicate<? super File> m_shouldDescend;

  /** should we visit directories? */
  private final boolean m_visitDirectories;

  /** should we visit files? */
  private final boolean m_visitFiles;

  /** the result */
  File m_result;

  /**
   * Create the find file action
   * 
   * @param paths
   *          the paths to search
   * @param shouldDescend
   *          should we descent
   * @param visitDirectories
   *          should we visit directories
   * @param visitFiles
   *          should we visit files
   * @param predicate
   *          the predicate
   */
  FindFile(final File[] paths,
      final IPredicate<? super File> shouldDescend,
      final boolean visitDirectories, final boolean visitFiles,
      final IPredicate<? super File> predicate) {
    super();
    this.m_predicate = predicate;
    this.m_paths = paths;
    this.m_shouldDescend = shouldDescend;
    this.m_visitDirectories = visitDirectories;
    this.m_visitFiles = visitFiles;
  }

  /**
   * Create the find file action
   * 
   * @param paths
   *          the paths to search
   * @param shouldDescend
   *          should we descent
   * @param visitDirectories
   *          should we visit directories
   * @param visitFiles
   *          should we visit files
   * @param predicate
   *          the predicate
   */
  public FindFile(final IPredicate<? super File> shouldDescend,
      final boolean visitDirectories, final boolean visitFiles,
      final IPredicate<? super File> predicate, final File... paths) {
    this(paths.clone(), shouldDescend, visitDirectories, visitFiles,
        predicate);
  }

  /**
   * Create the find file action
   * 
   * @param paths
   *          the paths to search
   * @param visitDirectories
   *          should we visit directories
   * @param visitFiles
   *          should we visit files
   * @param predicate
   *          the predicate
   */
  public FindFile(final boolean visitDirectories,
      final boolean visitFiles, final IPredicate<? super File> predicate,
      final File... paths) {
    this(null, visitDirectories, visitFiles, predicate, paths);
  }

  /**
   * Create the find file action
   * 
   * @param paths
   *          the paths to search
   * @param shouldDescend
   *          should we descent
   * @param visitDirectories
   *          should we visit directories
   * @param visitFiles
   *          should we visit files
   * @param predicate
   *          the predicate
   */
  public FindFile(final IPredicate<? super File> shouldDescend,
      final boolean visitDirectories, final boolean visitFiles,
      final IPredicate<? super File> predicate,
      final Collection<File> paths) {
    this(paths.toArray(new File[paths.size()]), shouldDescend,
        visitDirectories, visitFiles, predicate);
  }

  /**
   * Create the find file action
   * 
   * @param paths
   *          the paths to search
   * @param visitDirectories
   *          should we visit directories
   * @param visitFiles
   *          should we visit files
   * @param predicate
   *          the predicate
   */
  public FindFile(final boolean visitDirectories,
      final boolean visitFiles, final IPredicate<? super File> predicate,
      final Collection<File> paths) {
    this(null, visitDirectories, visitFiles, predicate, paths);
  }

  /** {@inheritDoc} */
  @Override
  public final File call() throws IOException, SecurityException {
    final _FindFile f;

    this.m_result = null;
    f = new _FindFile(this);
    for (final File path : this.m_paths) {
      if (!(new FileSystemWalker(path, f, this.m_shouldDescend,
          this.m_visitDirectories, this.m_visitFiles).call()
          .booleanValue())) {
        if (this.m_result != null) {
          return this.m_result;
        }
      }
    }

    return null;
  }

}
