package org.optimizationBenchmarking.utils.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.structured.TextInputDriver;

/** the configuration properties input */
public class ConfigurationPropertiesInput extends
    TextInputDriver<ConfigurationBuilder> {

  /** the configuration properties io driver */
  public static final ConfigurationPropertiesInput INSTANCE = new ConfigurationPropertiesInput();

  /** create */
  private ConfigurationPropertiesInput() {
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
}
