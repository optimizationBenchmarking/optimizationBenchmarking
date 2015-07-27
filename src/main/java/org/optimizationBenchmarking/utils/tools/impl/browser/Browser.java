package org.optimizationBenchmarking.utils.tools.impl.browser;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.FileNamePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsFilePredicate;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;
import org.optimizationBenchmarking.utils.text.TextUtils;
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

    /** the argument */
    static final String ARGUMENT;

    static {
      final Logger logger;
      final LinkedHashMap<String, String> browsers;
      Path p;

      // Nowadays, browser run several processes and may re-use existing
      // processes. If the browser is open and a new one started, for our
      // tool, it may look as if the new browser was immediately closed.
      // Some browsers (like Internet Explorer) may provide us with command
      // line arguments to remedy this situation, but generally, there is
      // no way against that. However, we still give it our best shot by
      // providing a facility to have such arguments and hopefully, can add
      // more arguments in the future.
      browsers = new LinkedHashMap<>();
      browsers.put("firefox", null); //$NON-NLS-1$
      browsers.put("iexplore", "-noframemerging"); //$NON-NLS-1$ //$NON-NLS-2$
      browsers.put("edge", null); //$NON-NLS-1$
      browsers.put("spartan", null); //$NON-NLS-1$
      browsers.put("iceweasel", null); //$NON-NLS-1$
      browsers.put("opera", null); //$NON-NLS-1$
      browsers.put("chromium", null); //$NON-NLS-1$
      browsers.put("chrome", null); //$NON-NLS-1$
      browsers.put("safari", null); //$NON-NLS-1$
      browsers.put("netscape", null); //$NON-NLS-1$
      browsers.put("seamonkey", null); //$NON-NLS-1$
      browsers.put("konquerer", null); //$NON-NLS-1$
      browsers.put("kmelon", null); //$NON-NLS-1$
      browsers.put("lynx", null); //$NON-NLS-1$
      browsers.put("icecat", null); //$NON-NLS-1$
      browsers.put("galeon", null); //$NON-NLS-1$

      // User- or environment-specified browser.
      p = Configuration.getRoot().getPath("browser", null); //$NON-NLS-1$
      if (!(IsFilePredicate.INSTANCE.check(p) && //
      CanExecutePredicate.INSTANCE.check(p))) {
        logger = Configuration.getGlobalLogger();
        if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
          logger.config("Looking for browser binary.");//$NON-NLS-1$
        }

        // Check for real browsers.
        p = PathUtils.findFirstInPath(new AndPredicate<>(
            new FileNamePredicate(true,//
                browsers.keySet().toArray(new String[browsers.size()])),//
            CanExecutePredicate.INSTANCE),//
            IsFilePredicate.INSTANCE, null);

        if (p == null) {
          // Check for utilities which may indirectly launch a browser
          // process.
          p = PathUtils.findFirstInPath(new AndPredicate<>(
              new FileNamePredicate(true,//
                  "xdg-open",//$NON-NLS-1$
                  "gnome-open",//$NON-NLS-1$
                  "cygstart",//$NON-NLS-1$
                  "explorer",//$NON-NLS-1$
                  "open"), //$NON-NLS-1$
              CanExecutePredicate.INSTANCE),//
              IsFilePredicate.INSTANCE, null);
        }

        if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
          logger.config((_BrowserPath.PATH != null) ? //
          ("Browser binary is '" + _BrowserPath.PATH + '\'') : //$NON-NLS-1$
              "No browser binary detected.");//$NON-NLS-1$
        }
      }

      PATH = p;

      if (p == null) {
        ARGUMENT = null;
      } else {
        ARGUMENT = browsers.get(TextUtils.toLowerCase(//
            PathUtils.getFileNameWithoutExtension(p)));
      }
    }

  }
}
