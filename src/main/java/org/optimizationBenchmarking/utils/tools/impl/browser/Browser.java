package org.optimizationBenchmarking.utils.tools.impl.browser;

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
    return (__BrowserPath.PATH != null);
  }

  /** {@inheritDoc} */
  @Override
  public final void checkCanUse() {
    if (__BrowserPath.PATH == null) {
      throw new IllegalStateException(//
          "Cannot use the browser tool, since no web browser could be detected."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final BrowserJobBuilder use() {
    this.checkCanUse();
    return new BrowserJobBuilder(__BrowserPath.PATH);
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
  private static final class __BrowserPath {

    /** the browser path */
    static final Path PATH;

    static {
      final Path p;
      final Logger logger;

      p = Configuration.getRoot().getPath("browser", null); //$NON-NLS-1$
      if (IsFilePredicate.INSTANCE.check(p)
          && CanExecutePredicate.INSTANCE.check(p)) {
        PATH = p;
      } else {

        logger = Configuration.getGlobalLogger();
        if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
          logger.config("Looking for browser binary.");//$NON-NLS-1$
        }

        PATH = PathUtils.findFirstInPath(new AndPredicate<>(
            new FileNamePredicate(true,//
                "xdg-open", //$NON-NLS-1$
                "gnome-open", //$NON-NLS-1$
                "firefox", //$NON-NLS-1$
                "opera", //$NON-NLS-1$
                "iexplore", //$NON-NLS-1$
                "edge", //$NON-NLS-1$
                "iceweasel", //$NON-NLS-1$
                "chrome", //$NON-NLS-1$
                "safari", //$NON-NLS-1$
                "netscape", //$NON-NLS-1$
                "camino", //$NON-NLS-1$
                "seamonkey", //$NON-NLS-1$
                "konquerer", //$NON-NLS-1$
                "galeon", //$NON-NLS-1$
                "kmelon", //$NON-NLS-1$
                "lynx", //$NON-NLS-1$
                "icecat" //$NON-NLS-1$
            ), CanExecutePredicate.INSTANCE),//
            IsFilePredicate.INSTANCE, null);

        if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
          logger
              .config((__BrowserPath.PATH != null) ? ("Browser binary is '" + __BrowserPath.PATH + '\'') : //$NON-NLS-1$
                  "No browser binary detected.");//$NON-NLS-1$
        }
      }
    }

  }
}
