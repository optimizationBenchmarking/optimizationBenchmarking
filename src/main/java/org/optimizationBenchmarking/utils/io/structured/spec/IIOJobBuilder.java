package org.optimizationBenchmarking.utils.io.structured.spec;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.tools.spec.IConfigurableToolJobBuilder;

/**
 * A basic I/O job builder.
 */
public interface IIOJobBuilder extends IConfigurableToolJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IIOJobBuilder setLogger(final Logger logger);

  /**
   * Set the path against which all relative paths should be resolved. If
   * you set this path to {@code null} or do not set it at all, then paths
   * will be resolved against the current directory, i.e., the directory in
   * which the Java JVM was started. This is the default behavior. If you
   * set {@code path} to some other/real path, then all none-absolute paths
   * will be resolved against this path.
   *
   * @param path
   *          the path to resolve all relative paths against, or
   *          {@code null} to resolve relative paths against the current
   *          directory
   * @return this builder
   */
  public abstract IIOJobBuilder setBasePath(final Path path);

  /** {@inheritDoc} */
  @Override
  public abstract IIOJob create();

  /** {@inheritDoc} */
  @Override
  public abstract IIOJobBuilder configure(final Configuration config);
}
