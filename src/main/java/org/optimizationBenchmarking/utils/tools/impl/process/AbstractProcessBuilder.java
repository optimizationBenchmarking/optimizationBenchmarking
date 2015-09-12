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
public abstract class AbstractProcessBuilder<PT extends AbstractProcess, BT extends AbstractProcessBuilder<PT, BT>>
    extends ToolJobBuilder<PT, BT> {

  /** create the process builder */
  protected AbstractProcessBuilder() {
    super();
  }

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
}
