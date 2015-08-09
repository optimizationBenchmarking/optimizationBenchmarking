package org.optimizationBenchmarking.utils.tools.impl.browser;

import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.maps.StringMapCI;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.FileNamePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsFilePredicate;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;
import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;
import org.optimizationBenchmarking.utils.tools.impl.process.ProcessExecutor;
import org.optimizationBenchmarking.utils.tools.spec.IConfigurableJobTool;

/**
 * The browser tool.
 */
public final class Browser extends Tool implements IConfigurableJobTool {

  /** create the browser */
  Browser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return (ProcessExecutor.getInstance().canUse() && (_BrowserPath.PATH != null));
  }

  /** {@inheritDoc} */
  @Override
  public final void checkCanUse() {
    ProcessExecutor.getInstance().checkCanUse();
    if (_BrowserPath.PATH == null) {
      throw new IllegalStateException(//
          "Cannot use the browser tool, since no web browser could be detected."); //$NON-NLS-1$
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
  static final class _BrowserPath {

    /** the browser path */
    static final Path PATH;

    /** the browser description */
    static final _BrowserDesc DESC;

    static {
      final Logger logger;
      String name;
      _BrowserDesc desc;
      Path path;

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

      looper: for (final StringMapCI<_BrowserDesc> browsers : new _BrowserDescs()) {
        path = PathUtils.findFirstInPath(new AndPredicate<>(
            new FileNamePredicate(true,//
                browsers.keySet().toArray(new String[browsers.size()])),//
            CanExecutePredicate.INSTANCE),//
            IsFilePredicate.INSTANCE, null);
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

      if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
        if (_BrowserPath.PATH != null) {
          logger.config((_BrowserPath.PATH != null) ? //
          ("Browser binary is '" + _BrowserPath.PATH + '\'') : //$NON-NLS-1$
              "No browser binary detected.");//$NON-NLS-1$
        }
      }
    }
  }
}
