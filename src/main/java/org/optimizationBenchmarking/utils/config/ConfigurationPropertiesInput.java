package org.optimizationBenchmarking.utils.config;

import java.io.BufferedReader;
import java.util.Properties;

import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.TextInputTool;

/** the configuration properties input */
public final class ConfigurationPropertiesInput extends
    TextInputTool<ConfigurationBuilder> {

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
  protected final void reader(final IOJob job,
      final ConfigurationBuilder data, final BufferedReader reader)
      throws Throwable {
    Properties pr;

    pr = new Properties();
    pr.load(reader);
    data.putProperties(pr);
  }

  /** the loader */
  private static final class __ConfigurationPropertiesInputLoader {

    /** the configuration properties io driver */
    static final ConfigurationPropertiesInput INSTANCE = new ConfigurationPropertiesInput();

  }
}
