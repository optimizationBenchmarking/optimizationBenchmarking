package org.optimizationBenchmarking.utils.tools.impl.browser;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJobBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.EProcessStream;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.ProcessExecutor;
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

  /** the external process builder */
  private final ExternalProcessBuilder m_builder;

  /** the url */
  private boolean m_hasURL;

  /**
   * create
   *
   * @param browser
   *          the path to the browser
   */
  BrowserJobBuilder(final Path browser) {
    super();
    this.m_builder = ProcessExecutor.getInstance().use();

    Path parent;

    this.m_builder.setExecutable(browser);
    parent = browser.getParent();
    if (parent != null) {
      this.m_builder.setDirectory(parent);
    }

    this.m_builder.setStdIn(EProcessStream.IGNORE);
    this.m_builder.setStdErr(EProcessStream.IGNORE);
    this.m_builder.setStdOut(EProcessStream.IGNORE);
  }

  /** {@inheritDoc} */
  @Override
  public final BrowserJob create() throws IOException {
    if (!(this.m_hasURL)) {
      throw new IllegalStateException(//
          "URL to visit has not been set."); //$NON-NLS-1$
    }
    this.m_builder.setLogger(this.getLogger());
    return new BrowserJob(this.getLogger(), this.m_builder.create());
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
    if (this.m_hasURL) {
      throw new IllegalStateException("URL has already been set."); //$NON-NLS-1$
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

    this.m_hasURL = true;
    this.m_builder.addStringArgument(url.toExternalForm());

    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final BrowserJobBuilder configure(final Configuration config) {
    final String url;

    url = config.getString(BrowserJobBuilder.PARAM_URL, null);
    if (url != null) {
      this.setURL(url);
    }

    return this;
  }
}
