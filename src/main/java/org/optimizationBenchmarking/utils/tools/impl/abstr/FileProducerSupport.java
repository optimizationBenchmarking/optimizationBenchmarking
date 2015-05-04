package org.optimizationBenchmarking.utils.tools.impl.abstr;

import java.io.Closeable;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.optimizationBenchmarking.utils.collections.ImmutableAssociation;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * <p>
 * A support for remembering generated files in
 * {@link org.optimizationBenchmarking.utils.tools.spec.IDocumentProducerTool
 * file producing tools}. This class provides a light-weight method to
 * remember generated files and to fire the
 * {@link org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener#onFilesFinalized(Collection)
 * onFilesFinalized} event of a
 * {@link org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener
 * file producer listener}. It can be used
 * </p>
 * <ul>
 * <li>in cases where the tool only generates a single file (in which case
 * this single {@link java.nio.file.Path file}/
 * {@link org.optimizationBenchmarking.utils.io.IFileType file type}
 * -association is stored internally as a single
 * {@link org.optimizationBenchmarking.utils.collections.ImmutableAssociation
 * immutable map entry}) and</li>
 * <li>in cases where many files are generated (in which case they are
 * stored in an {@link java.util.LinkedHashMap ordered hash map}).</li>
 * </ul>
 * <p>
 * It is not possible to {@link #addFile(Path, IFileType) store} the same
 * {@link java.nio.file.Path path} more than once and {@code null}
 * {@link org.optimizationBenchmarking.utils.io.IFileType file types} are
 * not permitted either.
 * </p>
 * <p>
 * Instances of {@link FileProducerSupport} support multi-threading.
 * </p>
 * <p>
 * The
 * {@link org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener#onFilesFinalized(Collection)
 * onFilesFinalized} event of a
 * {@link org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener
 * file producer listener} is fired exactly once when the file producer
 * support is {@link #close() closed} if and only if a
 * {@link org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener}
 * was passed in the constructor. The event method is called outside of a
 * synchronized block, i.e., not synchronized. This should make deadlocks
 * unlikely.
 * </p>
 * <p>
 * All file collections obtained via {@link #getProducedFiles()} or passed
 * to
 * {@link org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener#onFilesFinalized(Collection)}
 * are immutable, which also holds for their elements. They are also not
 * affected by any concurrent or later change of the internally managed
 * file list. The returned collections can safely be stored.
 * </p>
 * <p>
 * This class also implements
 * {@link org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener}
 * in an efficient way, so you can even stack listeners on top of each
 * other efficiently. The use case is that sometimes, several file
 * producing tools are combined to create an output document.
 * </p>
 */
public final class FileProducerSupport extends _FileSet implements
    Closeable {

  /**
   * the map of files if more than one was produced (otherwise,
   * {@link #m_single} is used
   */
  private volatile LinkedHashMap<Path, IFileType> m_files;

  /**
   * the immutable array list view wrapped around {@link #m_files} or
   * {@link #m_single}
   */
  private volatile ArrayListView<ImmutableAssociation<Path, IFileType>> m_output;

  /** the listener, or {@code null} if none was specified */
  private volatile IFileProducerListener m_listener;

  /**
   * an association used instead of {@link #m_files} if only single files
   * were produced
   */
  private volatile ImmutableAssociation<Path, IFileType> m_single;

  /** are we closed? */
  private volatile boolean m_closed;

  /**
   * create
   *
   * @param listener
   *          the listener, or {@code null} if none was specified
   */
  public FileProducerSupport(final IFileProducerListener listener) {
    super();
    this.m_listener = listener;
  }

  /** {@inheritDoc} */
  @Override
  final IFileType _getPathType(final Path path) {
    if (this.m_files == null) {
      if (this.m_single == null) {
        return null;
      }
      if (this.m_single.getKey().equals(path)) {
        return this.m_single.getValue();
      }
      return null;
    }
    return this.m_files.get(path);
  }

  /** {@inheritDoc} */
  @Override
  final void _addFile(final Path path, final IFileType type) {
    this.m_output = null;

    if (this.m_files == null) {
      if (this.m_single == null) {
        this.m_single = new ImmutableAssociation<>(path, type);
        return;
      }

      this.m_files = new LinkedHashMap<>();
      this.m_files.put(this.m_single.getKey(), this.m_single.getValue());
      this.m_single = null;
    }

    this.m_files.put(path, type);
  }

  /**
   * Get the (current) collection of created files.
   *
   * @return A collection with all the created files.
   * @throws IllegalStateException
   *           if the file producer support has already been
   *           {@link #close() closed}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public synchronized final ArrayListView<ImmutableAssociation<Path, IFileType>> getProducedFiles() {
    if (this.m_closed) {
      throw new IllegalStateException(//
          "File producer support already closed."); //$NON-NLS-1$
    }

    if (this.m_output != null) {
      return this.m_output;
    }

    if (this.m_single != null) {
      return (this.m_output = new ArraySetView(
          new ImmutableAssociation[] { this.m_single }));
    }

    if (this.m_files != null) {
      return (this.m_output = FileProducerSupport.__make(this.m_files));
    }

    return ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
  }

  /**
   * make the list view for a map
   *
   * @param map
   *          the map
   * @return the list view
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private static final ArrayListView<ImmutableAssociation<Path, IFileType>> __make(
      final LinkedHashMap<Path, IFileType> map) {
    final Map.Entry[] data;
    Map.Entry entry;
    int i;

    data = map.entrySet().toArray(new Map.Entry[map.size()]);
    for (i = data.length; (--i) >= 0;) {
      entry = data[i];
      data[i] = new ImmutableAssociation(entry.getKey(), entry.getValue());
    }
    return new ArrayListView(data);
  }

  /**
   * Close this file producer support and call the
   * {@link org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener#onFilesFinalized(Collection)
   * onFilesFinalized} method of the specified listener, if such a listener
   * was passed in the constructor.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final void close() {
    final FileCollector support;
    final IFileProducerListener listener;
    final LinkedHashMap<Path, IFileType> files;
    final ArrayListView<ImmutableAssociation<Path, IFileType>> output;
    final ImmutableAssociation<Path, IFileType> single;

    synchronized (this) {

      if (this.m_closed) {
        return;
      }
      this.m_closed = true;

      listener = this.m_listener;
      this.m_listener = null;
      files = this.m_files;
      this.m_files = null;
      output = this.m_output;
      this.m_output = null;
      single = this.m_single;
      this.m_single = null;
    }

    if (listener == null) {
      return;
    }

    if (listener instanceof FileCollector) {
      support = ((FileCollector) listener);
      if (single != null) {
        support.addFile(single);
        return;
      }
      if (output != null) {
        support.addFiles((Iterable) output);
        return;
      }
      if (files != null) {
        support.addFiles(files.entrySet());
        return;
      }
      return;
    }

    if (output != null) {
      listener.onFilesFinalized((Collection) output);
      return;
    }

    if (single != null) {
      listener.onFilesFinalized(new ArraySetView(
          new ImmutableAssociation[] { single }));
      return;
    }

    if (files != null) {
      listener.onFilesFinalized((Collection) (//
          FileProducerSupport.__make(files)));
      return;
    }

    listener.onFilesFinalized(//
        (ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
  }
}
