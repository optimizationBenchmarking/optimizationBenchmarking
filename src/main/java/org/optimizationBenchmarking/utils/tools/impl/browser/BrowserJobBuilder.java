package org.optimizationBenchmarking.utils.tools.impl.browser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.io.EOSFamily;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJobBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.EProcessStream;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessExecutor;
import org.optimizationBenchmarking.utils.tools.spec.IConfigurableToolJobBuilder;

/**
 * The browser job builder allows you to run the browser, visit a specified
 * {@link java.net.URL}, and wait for the user to close the browser.
 */
public final class BrowserJobBuilder extends
    ToolJobBuilder<BrowserJob, BrowserJobBuilder> implements
    IConfigurableToolJobBuilder {

  /** the url to visit */
  public static final String PARAM_URL = "url"; //$NON-NLS-1$

  /** the url */
  private URL m_url;

  /** create */
  BrowserJobBuilder() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public final BrowserJob create() throws IOException {
    final Path executable, parent, batch;
    final ExternalProcessBuilder epb;
    final String url;
    final TempDir temp;
    final Logger logger;
    final String[] args;
    final _BrowserDesc desc;
    boolean reliable;

    if (this.m_url == null) {
      throw new IllegalStateException(//
          "URL to visit has not been set."); //$NON-NLS-1$
    }

    logger = this.getLogger();

    if (Browser._BrowserPath.DESKTOP != null) {
      try {
        Browser._BrowserPath.DESKTOP.browse(this.m_url.toURI());
        return new BrowserJob(logger, null, null, false);
      } catch (final Throwable error) {
        throw new IOException(("Failed to launch browser for URL '" //$NON-NLS-1$
            + this.m_url + '\'' + '.'), error);
      }
    }

    url = this.m_url.toExternalForm();

    executable = Browser._BrowserPath.PATH;
    epb = ExternalProcessExecutor.getInstance().use();

    epb.setLogger(logger);
    epb.setStdErr(EProcessStream.IGNORE);
    epb.setStdIn(EProcessStream.IGNORE);
    epb.setStdOut(EProcessStream.IGNORE);

    desc = Browser._BrowserPath.DESC;
    reliable = desc.m_reliable;
    make: {
      if (desc.m_needsWinBatchWrap) { //
        if (EOSFamily.DETECTED == EOSFamily.Windows) {
          // For windows, we need a special work-around since the IE or
          // chrome may launch but we cannot wait for it to finish
          // directly. The same problem appears with opera.
          // See http://stackoverflow.com/questions/3349922

          temp = new TempDir();
          parent = temp.getPath();
          batch = PathUtils.createPathInside(parent, "batch.bat"); //$NON-NLS-1$

          try (final OutputStream os = PathUtils.openOutputStream(batch)) {
            try (final OutputStreamWriter fow = new OutputStreamWriter(os)) {
              try (final BufferedWriter bw = new BufferedWriter(fow)) {
                bw.write("start \"\" /wait \""); //$NON-NLS-1$
                bw.write(PathUtils.getPhysicalPath(executable, false));
                bw.write('"');
                bw.write(' ');

                args = desc.m_parameters;
                if (args != null) {
                  for (final String arg : args) {
                    bw.write(arg);
                    bw.write(' ');
                  }
                }

                bw.write('"');
                bw.write(url);
                bw.write('"');
                bw.newLine();
              }
            }
          }

          if ((logger != null) && logger.isLoggable(Level.FINE)) {
            logger.fine(//
                "Launching browser via batch '" + batch.toString() + //$NON-NLS-1$
                    "' as work-around for browser processes detaching themselves.");//$NON-NLS-1$
          }

          epb.setExecutable(batch);
          epb.setDirectory(parent);
          break make;
        }
        // if we are not under windows and run directly, we cannot rely on
        // the browser
        reliable = false;
      }

      epb.setExecutable(executable);
      parent = executable.getParent();
      if (parent != null) {
        epb.setDirectory(parent);
      }

      args = desc.m_parameters;
      if (args != null) {
        for (final String arg : args) {
          epb.addStringArgument(arg);
        }
      }
      epb.addStringArgument(url);
      temp = null;
    }

    return new BrowserJob(this.getLogger(), epb.create(), temp, reliable);
  }

  /**
   * Set the URL to visit
   *
   * @param url
   *          the url to visit
   * @return this builder
   */
  public final BrowserJobBuilder setURL(final String url) {
    final URL use;

    if (url == null) {
      throw new IllegalArgumentException(//
          "The string of the URL to visit cannot be null."); //$NON-NLS-1$
    }

    try {
      use = new URL(url);
    } catch (final Throwable error) {
      RethrowMode.AS_ILLEGAL_ARGUMENT_EXCEPTION.rethrow(//
          (("String '" + url) + //$NON-NLS-1$
          "' cannot be converted to an URL."), //$NON-NLS-1$
          false, error);
      return this;
    }

    return this.setURL(use);
  }

  /**
   * Set the URL to visit
   *
   * @param url
   *          the url to visit
   * @return this builder
   */
  public final BrowserJobBuilder setURL(final URL url) {
    final URL use;

    if (url == null) {
      throw new IllegalArgumentException(
          "The URL to visit cannot be null."); //$NON-NLS-1$
    }
    try {
      use = url.toURI().normalize().toURL();
    } catch (final Throwable error) {
      RethrowMode.AS_ILLEGAL_ARGUMENT_EXCEPTION.rethrow(//
          (("URL '" + url) + //$NON-NLS-1$
          "' cannot be normalized and is thus an invalid URL."), //$NON-NLS-1$
          false, error);
      return this;
    }

    if (use == null) {
      throw new IllegalArgumentException(//
          "The URL to visit cannot normalize to null."); //$NON-NLS-1$
    }

    this.m_url = url;

    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final BrowserJobBuilder configure(final Configuration config) {
    final String url;
    final Logger oldLogger, newLogger;

    url = config.getString(BrowserJobBuilder.PARAM_URL, null);
    if (url != null) {
      this.setURL(url);
    }

    oldLogger = this.getLogger();
    newLogger = config.getLogger(Configuration.PARAM_LOGGER, oldLogger);
    if ((oldLogger != null) || (newLogger != null)) {
      this.setLogger(newLogger);
    }

    return this;
  }
}
