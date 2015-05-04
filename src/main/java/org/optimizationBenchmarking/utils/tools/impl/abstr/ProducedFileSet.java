package org.optimizationBenchmarking.utils.tools.impl.abstr;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.optimizationBenchmarking.utils.io.IFileType;

/**
 * This class holds a set of produced files.
 */
public class ProducedFileSet extends _FileSet {

  /** the files */
  private HashMap<Path, IFileType> m_files;

  /** create the set of produced files */
  public ProducedFileSet() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final IFileType _getPathType(final Path path) {
    if (this.m_files != null) {
      return this.m_files.get(path);
    }
    return null;
  }

  /** {@inheritDoc} */
  @Override
  final void _addFile(final Path path, final IFileType type) {
    if (this.m_files == null) {
      this.m_files = new HashMap<>();
    }
    this.m_files.put(path, type);
  }

  /**
   * Get the set of files produced until now.
   *
   * @return the set of files produced until now
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public synchronized final Map<Path, IFileType> getProducedFiles() {
    if (this.m_files == null) {
      return new HashMap<>();
    }
    return ((Map) (this.m_files.clone()));
  }
}
