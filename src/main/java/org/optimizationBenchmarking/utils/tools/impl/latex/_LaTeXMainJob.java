package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.BasicCollection;
import org.optimizationBenchmarking.utils.collections.ImmutableAssociation;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.io.paths.FileChangeDetector;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/** the main job of the LaTeX tool chain */
final class _LaTeXMainJob extends LaTeXJob {

  /** the directory */
  private final Path m_directory;

  /** the base name */
  private final String m_baseName;

  /** the paths file */
  private final Path[] m_paths;

  /** the aux file change detector */
  private FileChangeDetector[] m_detectors;

  /** the tool chain components which are looped */
  private final _LaTeXToolChainComponent[] m_loopChain;
  /** the tool chain components for finalizing the document */
  private final _LaTeXToolChainComponent[] m_finalChain;

  /** the listener */
  private final IFileProducerListener m_listener;

  /**
   * create the main job
   * 
   * @param mainFile
   * @param loop
   * @param refine
   * @param listener
   * @param logger
   */
  _LaTeXMainJob(final Path mainFile,
      final _LaTeXToolChainComponent[] loop,
      final _LaTeXToolChainComponent[] refine,
      final IFileProducerListener listener, final Logger logger) {
    super(logger);

    final Path[] paths;
    final Path main;

    main = PathUtils.normalize(mainFile);
    if (main == null) {
      throw new IllegalArgumentException(//
          "LaTeX main file cannot be null."); //$NON-NLS-1$
    }

    if ((loop == null) || (loop.length <= 0)) {
      throw new IllegalArgumentException(//
          "Main tool chain cannot be empty or null."); //$NON-NLS-1$
    }

    this.m_paths = paths = new Path[ELaTeXFileType.INSTANCES.size()];

    paths[ELaTeXFileType.TEX.ordinal()] = main;
    this.m_directory = PathUtils.normalize(main.getParent());
    this.m_baseName = PathUtils.getFileNameWithoutExtension(main);

    if (this.m_baseName == null) {
      throw new IllegalArgumentException(("LaTeX main file '" + //$NON-NLS-1$ 
          main)
          + "' has a null file name if extension is removed!?"); //$NON-NLS-1$
    }

    this.m_loopChain = loop;
    this.m_finalChain = refine;
    this.m_listener = listener;
  }

  /**
   * Get the logger
   * 
   * @return the logger
   */
  final Logger _getLogger() {
    return this.getLogger();
  }

  /**
   * get the directory in which the jobs are executed
   * 
   * @return the directory in which the jobs are executed
   */
  final Path _getDirectory() {
    return this.m_directory;
  }

  /**
   * Get the file of the given type
   * 
   * @param type
   *          the requested file type
   * @return the file of type {@code type}
   */
  final Path _getFile(final ELaTeXFileType type) {
    return this.m_paths[type.ordinal()];
  }

  /**
   * get the base name of the document
   * 
   * @return the base name of the document
   */
  final String _getBaseName() {
    return this.m_baseName;
  }

  /** make all the paths */

  private final void __makePaths() {
    int index;
    final Path[] paths;

    paths = this.m_paths;
    for (index = paths.length; (--index) >= 0;) {
      if (paths[index] == null) {
        paths[index] = PathUtils.createPathInside(this.m_directory,
            PathUtils.makeFileName(this.m_baseName,
                ELaTeXFileType.INSTANCES.get(index).getDefaultSuffix()));
      }
    }
  }

  /** create the file change detectors */
  private final void __makeFileChangeDetectors() {
    final ArrayList<FileChangeDetector> detectors;
    FileChangeDetector detector;

    detectors = new ArrayList<>();
    for (final ELaTeXFileType type : ELaTeXFileType.INSTANCES) {
      if (type._shouldTrackChanges()) {
        detector = new FileChangeDetector(this.m_paths[type.ordinal()]);
        detector.hasChanged(true);
        detectors.add(detector);
      }
    }

    this.m_detectors = detectors.toArray(//
        new FileChangeDetector[detectors.size()]);
  }

  /**
   * check whether anything has changed
   * 
   * @return {@code true} if something has changed, {@code false} otherwise
   */
  private final boolean __hasChanged() {
    boolean changed;
    Logger logger;

    changed = false;
    logger = null;
    for (final FileChangeDetector detector : this.m_detectors) {
      if (detector.hasChanged(true)) {
        changed = true;

        if (logger == null) {
          logger = this.getLogger();
        }

        if ((logger != null) && (logger.isLoggable(Level.FINER))) {
          logger.finer(("File '" + detector.getPath()) + //$NON-NLS-1$
              "' has changed - need another round of compilation.");//$NON-NLS-1$
        }
      }
    }

    return changed;
  }

  /** invoke the listener */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private final void __invokeListener() {
    final Path path;

    if (this.m_listener == null) {
      return;
    }

    path = this.m_paths[ELaTeXFileType.PDF.ordinal()];
    if (path != null) {
      if (Files.exists(path)) {
        synchronized (this.m_listener) {
          this.m_listener.onFilesFinalized(new ArraySetView(
              new ImmutableAssociation[] { new ImmutableAssociation(path,
                  ELaTeXFileType.PDF) }));
        }
        return;
      }
    }

    synchronized (this.m_listener) {
      this.m_listener.onFilesFinalized(//
          (Collection) (BasicCollection.EMPTY_COLLECTION));
    }

  }

  /** {@inheritDoc} */
  @Override
  public final Void call() throws IOException {
    final Logger logger;
    final String document;
    int rounds;

    logger = this.getLogger();
    if (logger != null) {
      document = ((" LaTeX document '" + //$NON-NLS-1$
          (this._getFile(ELaTeXFileType.TEX) + "' in folder '") + //$NON-NLS-1$ 
      this.m_directory) + '\'');
    } else {
      document = null;
    }

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info("Now begining to compile" + //$NON-NLS-1$
          document + '.');
    }
    rounds = 0;

    try {
      try {
        this.__makePaths();

        if ((this.m_loopChain != null) && (this.m_loopChain.length > 0)) {
          this.__makeFileChangeDetectors();

          if ((logger != null) && (logger.isLoggable(Level.FINE))) {
            logger.fine((//
                "Entering main compilation loop for" + //$NON-NLS-1$
                document) + '.');
          }

          for (;;) {
            rounds++;

            for (final _LaTeXToolChainComponent component : this.m_loopChain) {
              component._use(this);
            }

            if (!(this.__hasChanged())) {
              break;
            }
            if (rounds > 25) {
              if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
                logger
                    .warning((((//
                        "We have done " + rounds) + //$NON-NLS-1$
                        " of compilation for") + //$NON-NLS-1$
                        document)
                        + ". This looks like an endless loop. Maybe you store volatile information like the time in your aux file? Better we stop now even though there still were changes, i.e., we risk to have some undefined labels or references in the final document rather than looping forever.");//$NON-NLS-1$
              }
              break;
            }
          }

          if ((logger != null) && (logger.isLoggable(Level.FINE))) {
            logger.fine((((//
                "Finished main compilation loop for" + //$NON-NLS-1$
                document) + " after ") + rounds) + //$NON-NLS-1$ 
                " compilation cycles."); //$NON-NLS-1$ 
          }
        }

        this.m_detectors = null;

        // now finalize the output
        if ((this.m_finalChain != null) && (this.m_finalChain.length > 0)) {

          if ((logger != null) && (logger.isLoggable(Level.FINE))) {
            logger.fine("Now finalizing" //$NON-NLS-1$
                + document + '.');
          }

          for (final _LaTeXToolChainComponent component : this.m_finalChain) {
            component._use(this);
          }

          if ((logger != null) && (logger.isLoggable(Level.FINE))) {
            logger.fine("Finished finalizing of" //$NON-NLS-1$
                + document + '.');
          }
        }
      } finally {
        this.__cleanUp();
      }
    } catch (final Throwable error) {
      ErrorUtils.logError(logger, ("Error during compilation of" + //$NON-NLS-1$
          document + '.'), error, true);
    }

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.fine((//
          "Finished compilation of" + //$NON-NLS-1$
          document) + '.');
    }

    this.__invokeListener();

    return null;
  }

  /** perform cleanup */
  private final void __cleanUp() {
    Path path;
    for (final ELaTeXFileType type : ELaTeXFileType.INSTANCES) {
      if (type._deleteAfterCompilation()) {
        path = this.m_paths[type.ordinal()];
        if (path != null) {
          try {
            Files.deleteIfExists(path);
          } catch (final Throwable ignoreable) {
            ErrorUtils.logError(this.getLogger(), Level.WARNING,
                (((("Error when trying to delete file '" + //$NON-NLS-1$ 
                path) + "' of type ") + type) + '.'),//$NON-NLS-1$
                ignoreable, true);
          }
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canCompileToPDF() {
    return true;
  }
}
