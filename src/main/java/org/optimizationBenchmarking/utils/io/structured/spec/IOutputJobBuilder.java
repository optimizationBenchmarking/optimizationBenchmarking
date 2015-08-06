package org.optimizationBenchmarking.utils.io.structured.spec;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * A file output job builder is a device that may store data.
 *
 * @param <D>
 *          the data type which can be stored
 */
public interface IOutputJobBuilder<D> extends IIOJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IOutputJobBuilder<D> configure(final Configuration config);

  /** {@inheritDoc} */
  @Override
  public abstract IOutputJobBuilder<D> setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IOutputJobBuilder<D> setBasePath(final Path path);

  /**
   * Set the source data object, i.e., the object to be stored
   *
   * @param source
   *          the source data object
   * @return this builder
   */
  public abstract IOutputJobBuilder<D> setSource(final D source);
}
