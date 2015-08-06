package org.optimizationBenchmarking.utils.io.structured.spec;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * An input job builder.
 *
 * @param <D>
 *          the data element which will be filled
 */
public interface IInputJobBuilder<D> extends IIOJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IInputJobBuilder<D> configure(final Configuration config);

  /** {@inheritDoc} */
  @Override
  public abstract IInputJobBuilder<D> setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IInputJobBuilder<D> setBasePath(final Path path);

  /**
   * Set the destination data object, i.e., the object to be filled with
   * the loaded data
   *
   * @param destination
   *          the destination data object
   * @return this builder
   */
  public abstract IInputJobBuilder<D> setDestination(final D destination);
}
