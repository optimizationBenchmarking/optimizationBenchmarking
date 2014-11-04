package org.optimizationBenchmarking.utils.config;

import java.io.IOException;
import java.io.Writer;
import java.util.Properties;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.structured.TextOutputDriver;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.TextOutputWriter;

/** the configuration properties output */
public class ConfigurationPropertiesOutput extends
    TextOutputDriver<Configuration> {

  /** the configuration properties io driver */
  public static final ConfigurationPropertiesOutput INSTANCE = new ConfigurationPropertiesOutput();

  /** create */
  private ConfigurationPropertiesOutput() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final void doStoreText(final Configuration data,
      final ITextOutput dest, final Logger logger) {
    Properties pr;

    pr = new Properties();
    synchronized (data.m_data) {
      pr.putAll(data.m_data);
    }
    try (final Writer writer = TextOutputWriter.wrap(dest)) {
      pr.store(writer, null);
    } catch (final IOException ioe) {
      ErrorUtils.throwAsRuntimeException(ioe);
    }
  }
}
