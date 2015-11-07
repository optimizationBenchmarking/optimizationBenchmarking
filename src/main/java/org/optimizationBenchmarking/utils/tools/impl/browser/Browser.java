package org.optimizationBenchmarking.utils.tools.impl.browser;

import java.awt.Desktop;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.FileNamePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsFilePredicate;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;
import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessExecutor;
import org.optimizationBenchmarking.utils.tools.spec.IConfigurableJobTool;

/**
 * The browser tool. The tool first tries to detect whether there is a
 * browser which we can launch and for which we can reliably wait until it
 * terminates. If yes, this browser will be used. If not, we will look for
 * a set of known browsers, and if we find one, we will use it. Otherwise,
 * we will try to go through the
 * {@link java.awt.Desktop#browse(java.net.URI) Desktop} API to browse, if
 * that is supported.
 */
public final class Browser extends Tool implements IConfigurableJobTool {

  /** create the browser */
  Browser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return (_BrowserPath.DESKTOP != null)
        || ((ExternalProcessExecutor.getInstance().canUse()
            && (_BrowserPath.PATH != null) && (_BrowserPath.DESC != null)));
  }

  /** {@inheritDoc} */
  @Override
  public final void checkCanUse() {
    if (_BrowserPath.DESKTOP == null) {
      ExternalProcessExecutor.getInstance().checkCanUse();
      if (_BrowserPath.PATH == null) {
        throw new IllegalStateException(//
            "Cannot use the browser tool, since no web browser could be detected."); //$NON-NLS-1$
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final BrowserJobBuilder use() {
    this.checkCanUse();
    return new BrowserJobBuilder();
  }

  /**
   * Get the globally shared instance of the browser tool
   *
   * @return the globally shared instance of the browser tool
   */
  public static final Browser getInstance() {
    return __BrowserLoader.INSTANCE;
  }

  /** the browser loader */
  private static final class __BrowserLoader {
    /** instantiat the browser tool */
    static final Browser INSTANCE = new Browser();
  }

  /** load the path to a browser */
  @SuppressWarnings("unused")
  static final class _BrowserPath {

    /** the browser path */
    static final Path PATH;

    /** the browser description */
    static final _BrowserDesc DESC;

    /** the desctop */
    static final Desktop DESKTOP;

    static {
      final Logger logger;
      Desktop desktop;
      String name;
      _BrowserDesc desc;
      Path path;
      int size;

      // Nowadays, browser run several processes and may re-use existing
      // processes. If the browser is open and a new one started, for our
      // tool, it may look as if the new browser was immediately closed.
      // Some browsers (like Internet Explorer) may provide us with command
      // line arguments to remedy this situation, but generally, there is
      // no way against that. However, we still give it our best shot by
      // providing a facility to have such arguments and hopefully, can add
      // more arguments in the future.

      logger = Configuration.getGlobalLogger();
      if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
        logger.config("Looking for browser binary.");//$NON-NLS-1$
      }

      path = null;
      desc = null;
      desktop = null;

      looper: for (final _BrowserMap browsers : new _BrowserDescs()) {
        size = browsers.size();
        if (size <= 0) {
          continue;
        }
        path = PathUtils.findFirstInPath(new AndPredicate<>(
            new FileNamePredicate(true,//
                browsers.keySet().toArray(new String[size])),//
            CanExecutePredicate.INSTANCE),//
            IsFilePredicate.INSTANCE, browsers._getVisitFirst());
        if (path != null) {
          name = PathUtils.getFileNameWithoutExtension(path);
          desc = browsers.get(name);
          if (desc != null) {
            break looper;
          }
        }
        path = null;
      }

      PATH = path;
      DESC = desc;

      if (path == null) {
        try {
          if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            if (desktop != null) {
              if (!(desktop.isSupported(Desktop.Action.BROWSE))) {
                desktop = null;
              }
            }
          }
        } catch (final Throwable ignore) {
          // well, ignore
          desktop = null;
        }
      }

      DESKTOP = desktop;

      if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
        if (_BrowserPath.DESKTOP != null) {
          logger.config(//
              "No known browser executable detected, but browsing is supported via java.awt.Desktop."); //$NON-NLS-1$
        } else {
          if (_BrowserPath.PATH != null) {
            logger.config("Detected browser binary is '" //$NON-NLS-1$
                + _BrowserPath.PATH + '\'');
          } else {
            logger.config(//
                "No browser binary detected and browsing not supported via java.awt.Desktop.");//$NON-NLS-1$
          }
        }
      }
    }
  }
}
