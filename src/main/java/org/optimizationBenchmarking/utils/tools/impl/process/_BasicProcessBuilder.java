package org.optimizationBenchmarking.utils.tools.impl.process;

import java.nio.file.Path;

/**
 * A basic builder for processes.
 *
 * @param <PT>
 *          the process type
 * @param <BT>
 *          the builder type
 */
abstract class _BasicProcessBuilder<PT extends _BasicProcess, BT extends _BasicProcessBuilder<PT, BT>>
    extends AbstractProcessBuilder<PT, BT> {

  /** the process closter */
  IProcessCloser<? super PT> m_closer;

  /** create the process builder */
  _BasicProcessBuilder() {
    super();
  }

  /**
   * Add a string command line argument
   *
   * @param s
   *          the string command line argument
   * @return this builder
   */
  public abstract BT addStringArgument(final String s);

  /**
   * Add a path command line argument
   *
   * @param path
   *          the path command line argument
   * @return this builder
   */
  public abstract BT addPathArgument(final Path path);

  /**
   * Set a string environment variable for the sub-process
   *
   * @param key
   *          the key
   * @param value
   *          the value
   * @return this builder
   */
  public abstract BT putEnvironmentString(final String key,
      final String value);

  /**
   * Set a path environment variable for the sub-process
   *
   * @param key
   *          the key
   * @param value
   *          the value
   * @return this builder
   */
  public abstract BT putEnvironmentPath(final String key, final Path value);

  /**
   * Remove an environment variable
   *
   * @param key
   *          the variable to remove
   * @return this builder
   */
  public abstract BT removeEnvironmentVar(final String key);

  /**
   * Clear the environment, i.e., delete all variables
   *
   * @return this builder
   */
  public abstract BT clearEnvironment();

  /**
   * Set the executable
   *
   * @param path
   *          the path to the executable
   * @return this builder
   */
  public abstract BT setExecutable(final Path path);

  /**
   * Set the process closer
   *
   * @param closer
   *          the closer
   * @return this builder
   */
  @SuppressWarnings("unchecked")
  public final BT setProcessCloser(final IProcessCloser<? super PT> closer) {
    this.m_closer = closer;
    return ((BT) this);
  }
}
