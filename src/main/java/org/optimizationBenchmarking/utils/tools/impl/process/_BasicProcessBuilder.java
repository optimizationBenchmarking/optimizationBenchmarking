package org.optimizationBenchmarking.utils.tools.impl.process;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJobBuilder;

/**
 * A basic builder for processes.
 *
 * @param <PT>
 *          the process type
 * @param <BT>
 *          the builder type
 */
abstract class _BasicProcessBuilder<PT extends BasicProcess, BT extends _BasicProcessBuilder<PT, BT>>
    extends ToolJobBuilder<PT, BT> {

  /** the process closter */
  IProcessCloser<? super PT> m_closer;

  /** create the process builder */
  _BasicProcessBuilder() {
    super();
  }

  /**
   * Set the executable
   *
   * @param path
   *          the path to the executable
   * @return this builder
   */
  public abstract BT setExecutable(final Path path);

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
   * Set the directory in which the process should be executed
   *
   * @param dir
   *          the directory
   * @return this builder
   */
  public abstract BT setDirectory(final Path dir);

  /**
   * Set the stdin stream definition
   *
   * @param def
   *          the stream definition
   * @return this builder
   */
  public abstract BT setStdIn(final EProcessStream def);

  /**
   * Read the stdin of this process from the given path
   *
   * @param source
   *          the source
   * @return this builder
   */
  public abstract BT readStdInFrom(final Path source);

  /**
   * Set the stdout stream definition
   *
   * @param def
   *          the stream definition
   * @return this builder
   */
  public abstract BT setStdOut(final EProcessStream def);

  /**
   * Store the stdout of this process to the given path
   *
   * @param dest
   *          the destination
   * @param append
   *          should we append to the file identified by {@code dest} if it
   *          exists ({@code true}) or overwrite it ({@code false})?
   * @return this builder
   */
  public abstract BT writeStdOutTo(final Path dest, final boolean append);

  /**
   * Set the stderr stream definition
   *
   * @param def
   *          the stream definition
   * @return this builder
   */
  public abstract BT setStdErr(final EProcessStream def);

  /**
   * Store the stderr of this process to the given path
   *
   * @param dest
   *          the destination
   * @param append
   *          should we append to the file identified by {@code dest} if it
   *          exists ({@code true}) or overwrite it ({@code false})?
   * @return this builder
   */
  public abstract BT writeStdErrTo(final Path dest, final boolean append);

  /**
   * Should stdout and stderr be merged?
   *
   * @param merge
   *          {@code true} if stdout and stderr should be merged,
   *          {@code false} if they are separate streams
   * @return this builder
   */
  public abstract BT setMergeStdOutAndStdErr(final boolean merge);

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
