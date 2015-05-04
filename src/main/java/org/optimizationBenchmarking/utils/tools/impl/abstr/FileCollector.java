package org.optimizationBenchmarking.utils.tools.impl.abstr;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * A collector for files. This is a base class providing (but not
 * implementing) a couple of useful methods as well as the
 * {@link org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener}
 * interface. It is the base class for
 * {@link org.optimizationBenchmarking.utils.tools.impl.abstr.FileProducerSupport}
 * but different from this class, it does not implement
 * {@link java.io.Closeable}, so instances of it can be handed around
 * freely.
 */
public abstract class FileCollector implements IFileProducerListener {

  /** Create the file collector */
  protected FileCollector() {
    super();
  }

  /**
   * Add a file to this file collection. The file is defined as a
   * {@link java.nio.file.Path path} and an associated
   * {@link org.optimizationBenchmarking.utils.io.IFileType file type},
   * neither of which can be null. Adding two equal paths will result in an
   * {@link IllegalStateException}.
   *
   * @param path
   *          the path to the file
   * @param type
   *          the file type
   * @throws IllegalArgumentException
   *           if either {@code path} or {@code type} are null or otherwise
   *           invalid
   */
  public abstract void addFile(final Path path, final IFileType type);

  /**
   * Add a {@link java.nio.file.Path file}/
   * {@link org.optimizationBenchmarking.utils.io.IFileType file type}
   * -association to this file collection.
   *
   * @param p
   *          the path to add
   */
  public abstract void addFile(final Map.Entry<Path, IFileType> p);

  /**
   * Add a set of paths into the file collection.
   *
   * @param ps
   *          the paths to add
   */
  public abstract void addFiles(
      final Iterable<Map.Entry<Path, IFileType>> ps);

  /** {@inheritDoc} */
  @Override
  public abstract void onFilesFinalized(
      final Collection<Entry<Path, IFileType>> result);
}
