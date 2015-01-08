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
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
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
 * was passed in the constructor.
 * </p>
 * <p>
 * All file collections obtained via {@link #getProducedFiles()} or passed
 * to
 * {@link org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener#onFilesFinalized(Collection)}
 * are immutable, which also holds for their elements. They are also not
 * affected by any concurrent or later change of the internally managed
 * file list. The returned collections can safely be stored.
 * </p>
 */
public final class FileProducerSupport implements Closeable {

  /** the created files */
  private volatile LinkedHashMap<Path, IFileType> m_files;

  /** the immutable wrapped around {@link #m_files} */
  private volatile ArrayListView<ImmutableAssociation<Path, IFileType>> m_output;

  /** the listener, or {@code null} if none was specified */
  private volatile IFileProducerListener m_listener;

  /** an assocation used for single files */
  private volatile ImmutableAssociation<Path, IFileType> m_single;

  /** are we open? */
  private volatile boolean m_open;

  /**
   * create
   * 
   * @param listener
   *          the listener, or {@code null} if none was specified
   */
  public FileProducerSupport(final IFileProducerListener listener) {
    super();
    this.m_open = true;
  }

  /**
   * Add a file to this listener support's internal list. The file is
   * defined as a {@link java.nio.file.Path path} and an associated
   * {@link org.optimizationBenchmarking.utils.io.IFileType file type},
   * neither of which can be null. Adding two equal paths will result in an
   * {@link IllegalStateException}.
   * 
   * @param path
   *          the path to the file
   * @param type
   *          the file type
   * @throws IllegalStateException
   *           if the {@code path} is already associated to a {@code type}
   *           or if the file producer support has already been
   *           {@link #close() closed}
   * @throws IllegalArgumentException
   *           if either {@code path} or {@code type} are null or otherwise
   *           invalid
   */
  public final void addFile(final Path path, final IFileType type) {
    final Path normalized;
    final IFileType old;

    if (path == null) {
      throw new IllegalArgumentException(//
          "Cannot add null path of type " + type); //$NON-NLS-1$
    }

    if (type == null) {
      throw new IllegalArgumentException(//
          "Path '" + path + //$NON-NLS-1$
              "' cannot have null type."); //$NON-NLS-1$
    }

    normalized = PathUtils.normalize(path);
    if (normalized == null) {
      throw new IllegalArgumentException(//
          "Path '" + path + //$NON-NLS-1$
              "' of type " + type + //$NON-NLS-1$
              " normalizes to null."); //$NON-NLS-1$
    }

    synchronized (this) {
      if (!this.m_open) {
        throw new IllegalStateException("Cannot add path '" + //$NON-NLS-1$
            normalized + " of type " + type + //$NON-NLS-1$
            ", because file producer support already closed.");//$NON-NLS-1$
      }

      if (this.m_files == null) {
        if (this.m_single == null) {
          this.m_single = new ImmutableAssociation<>(normalized, type);
          return;
        }

        this.m_files = new LinkedHashMap<>();
        this.m_files.put(this.m_single.getKey(), this.m_single.getValue());
        this.m_single = null;
      }
      this.m_output = null;

      old = this.m_files.get(normalized);
      if (old == null) {
        this.m_files.put(normalized, type);
        return;
      }
    }

    throw new IllegalStateException(((((("Path '" + normalized) + //$NON-NLS-1$
        "' is already associated with type ") + old) //$NON-NLS-1$
        + " and cannot be associated a second time (to type ") + //$NON-NLS-1$ 
        type + '\'') + '.');
  }

  /**
   * Add a {@link java.nio.file.Path file}/
   * {@link org.optimizationBenchmarking.utils.io.IFileType file type}
   * -association to this listener support.
   * 
   * @param p
   *          the path to add
   */
  public final void addFile(final Map.Entry<Path, IFileType> p) {
    if (p == null) {
      throw new IllegalArgumentException(//
          "Cannot add null path entry."); //$NON-NLS-1$
    }
    this.addFile(p.getKey(), p.getValue());
  }

  /**
   * Add a set of paths into the internal path list.
   * 
   * @param ps
   *          the paths to add
   */
  public final void addFiles(final Iterable<Map.Entry<Path, IFileType>> ps) {
    if (ps == null) {
      throw new IllegalArgumentException(//
          "Cannot add the elements of a null iterable.");//$NON-NLS-1$
    }
    synchronized (this) {
      for (final Map.Entry<Path, IFileType> p : ps) {
        this.addFile(p);
      }
    }
  }

  /**
   * Get the (current) collection of created files.
   * 
   * @return A collection with all the created files.
   * @throws IllegalStateException
   *           if the file producer support has already been
   *           {@link #close() closed}.
   */
  public synchronized final ArrayListView<ImmutableAssociation<Path, IFileType>> getProducedFiles() {
    if (!(this.m_open)) {
      throw new IllegalStateException(//
          "File producer support already closed."); //$NON-NLS-1$
    }
    return this.__get();
  }

  /**
   * Get the current collection of created files.
   * 
   * @return A collection with all the created files.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private final ArrayListView<ImmutableAssociation<Path, IFileType>> __get() {
    final Map.Entry[] data;
    Map.Entry entry;
    int i;

    if (this.m_output != null) {
      return this.m_output;
    }

    if (this.m_single != null) {
      return (this.m_output = new ArraySetView(
          new ImmutableAssociation[] { this.m_single }));
    }

    if (this.m_files != null) {
      data = this.m_files.entrySet().toArray(
          new Map.Entry[this.m_files.size()]);
      for (i = data.length; (--i) >= 0;) {
        entry = data[i];
        data[i] = new ImmutableAssociation(entry.getKey(),
            entry.getValue());
      }
      return (this.m_output = new ArrayListView(data));
    }

    return ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
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
    final ArrayListView<ImmutableAssociation<Path, IFileType>> pass;
    final IFileProducerListener listener;

    synchronized (this) {

      if (this.m_open) {
        this.m_open = false;
      } else {
        return;
      }

      listener = this.m_listener;
      this.m_listener = null;

      if (listener != null) {
        pass = this.__get();
      } else {
        pass = null;
      }

      this.m_output = null;
      this.m_files = null;
      this.m_single = null;
    }

    if (listener != null) {
      listener.onFilesFinalized((Collection) pass);
    }
  }
}
