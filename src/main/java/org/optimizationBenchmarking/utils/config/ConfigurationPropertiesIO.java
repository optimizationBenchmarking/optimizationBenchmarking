package org.optimizationBenchmarking.utils.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.structured.TextIODriver;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.TextOutputWriter;

/** the configuration xml i/o */
public class ConfigurationPropertiesIO extends
    TextIODriver<Configuration, ConfigurationBuilder> {

  /** the configuration properties io driver */
  public static final ConfigurationPropertiesIO INSTANCE = new ConfigurationPropertiesIO();

  /** create */
  private ConfigurationPropertiesIO() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final void doLoadReader(
      final ConfigurationBuilder loadContext, final BufferedReader reader,
      final Logger logger) throws IOException {
    Properties pr;

    pr = new Properties();
    pr.load(reader);
    loadContext.putProperties(pr);
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
