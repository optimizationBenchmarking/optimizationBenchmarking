package org.optimizationBenchmarking.utils.config;

import java.io.IOException;
import java.io.Writer;
import java.util.Properties;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.TextOutputTool;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.TextOutputWriter;

/** the configuration properties output */
public final class ConfigurationPropertiesOutput extends
    TextOutputTool<Configuration> {

  /** create */
  ConfigurationPropertiesOutput() {
    super();
  }

  /**
   * get the instance of the {@link ConfigurationPropertiesOutput}
   * 
   * @return the instance of the {@link ConfigurationPropertiesOutput}
   */
  public static final ConfigurationPropertiesOutput getInstance() {
    return __ConfigurationPropertiesOutputLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected final void text(final IOJob job, final Configuration data,
      final ITextOutput textOut) throws Throwable {
    Properties pr;

    pr = new Properties();
    synchronized (data.m_data) {
      pr.putAll(data.m_data);
    }
    try (final Writer writer = TextOutputWriter.wrap(textOut)) {
      pr.store(writer, null);
    } catch (final IOException ioe) {
      ErrorUtils.throwAsRuntimeException(ioe);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Configuration Properties Output"; //$NON-NLS-1$
  }

  /** the loader */
  private static final class __ConfigurationPropertiesOutputLoader {
    /** the configuration properties io driver */
    static final ConfigurationPropertiesOutput INSTANCE = new ConfigurationPropertiesOutput();
  }
}
