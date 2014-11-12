package org.optimizationBenchmarking.utils.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.structured.TextInputDriver;

/** the configuration properties input */
public final class ConfigurationPropertiesInput extends
    TextInputDriver<ConfigurationBuilder> {

  /** create */
  ConfigurationPropertiesInput() {
    super();
  }

  /**
   * get the instance of the {@link ConfigurationPropertiesInput}
   * 
   * @return the instance of the {@link ConfigurationPropertiesInput}
   */
  public static final ConfigurationPropertiesInput getInstance() {
    return __ConfigurationPropertiesInputLoader.INSTANCE;
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

  /** the loader */
  private static final class __ConfigurationPropertiesInputLoader {

    /** the configuration properties io driver */
    static final ConfigurationPropertiesInput INSTANCE = new ConfigurationPropertiesInput();

  }
}
