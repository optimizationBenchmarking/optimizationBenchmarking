package org.optimizationBenchmarking.utils.tools.impl.process;

import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/**
 * <p>
 * This tool allows us to start external processes in a deadlock-free way.
 * </p>
 * <p>
 * Java's Process Process API, founded on the classes
 * {@link java.lang.Process} and {@link java.lang.ProcessBuilder} allows
 * you to execute external processes already perfectly fine. However, it
 * has one severe drawback: Reading from and writing to their standard I/O
 * streams ({@code stdout}, {@code stdin}, {@code stderr}) can deadlock
 * very easily! With this tool here we fix that issue.
 * </p>
 * <h2>Deadlocks with Java's process API</h2>
 * <p>
 * Deadlocks with Java's process API may be caused as follows: Let's say
 * the standard I/O streams of a process are accessed via
 * {@link java.lang.ProcessBuilder.Redirect.Type#PIPE pipes}, as it is the
 * default. This usually means that the processes {@code stdout} and
 * {@code stderr} streams basically write their data to a buffer and its
 * {@code stdin} stream reads its data from a buffer. If the {@code stdin}
 * buffer is empty, reading input (say, via {@link java.lang.System#in})
 * will block the newly started process. If one of the buffers for
 * {@code stdout} and {@code stderr} are full, the process will block as
 * well. On our side, we will obviously block when trying to read data from
 * {@code stdout} and {@code stderr} if their buffers are empty or when
 * trying to write to a full {@code stdin} buffer.
 * </p>
 * <p>
 * With this we have the ingredients for mayham: Let's say we are waiting
 * for output from the external process, i.e., trying to read its
 * {@code stdout}. However, the process has written much to its
 * {@code stderr}, which caused that buffer to be full, and it is now
 * blocking until we read that stuff and the buffer has free space again.
 * Deadlock. The same can happen the other way around. Or we want to try to
 * write to the process' {@code stdin} and will write enough to fill the
 * corresponding buffer, i.e., will be blocked until the process is reading
 * from its {@code stdin}. Well, if the process is blocked writing to its
 * {@code stderr} or {@code stdout} (due to reaching the buffer capacity),
 * it can never do that. Deadlock!
 * </p>
 * <p>
 * As a result, we can <em>never</em> access the standard output streams of
 * a process in a single {@link java.lang.Thread}. I mean, we could do
 * round robin and read a byte from each before writing a byte, but that
 * seems to be fragile. An we totally lose as soon as character-based I/O
 * is involed, since {@link java.io.Writer}s and {@code java.io.Reader}s
 * may need to read multiple bytes at once to represent a character.
 * </p>
 * <h2>Solution</h2>
 * <p>
 * Our class counts the number of standard streams to the new process from
 * which you might want to read or to which you might want to write.
 * Streams which are redirected to files, for instance, are not counted,
 * since they cannot cause deadlocks. Anyway, if the number of
 * "interesting" streams is larger than 1, then it creates one
 * {@link org.optimizationBenchmarking.utils.tools.impl.process._WorkerThread
 * thread} per stream. These threads shovel data to and from internal
 * {@link org.optimizationBenchmarking.utils.parallel.ByteProducerConsumerBuffer
 * buffers} which can grow indefinitely. These buffers are then accessed
 * via streams, exactly as if you would write to the standard streams of
 * the new process directly. Reading from and writing to them can still
 * block, since maybe you are waiting for an output the process has not yet
 * written. However, it may not cause a deadlock, since the threads will
 * always continue shoveling in the background so no stalling because of
 * full pipe buffers can occur anymore. On the down side, this system is
 * potentially much more memory consuming.
 * </p>
 */
public final class ExternalProcessExecutor extends Tool {

  /** create */
  ExternalProcessExecutor() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final ExternalProcessBuilder use() {
    this.checkCanUse();
    return new ExternalProcessBuilder();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "External Process Executor"; //$NON-NLS-1$
  }

  /**
   * Get the instance of the external process executor
   *
   * @return the instance of the external process executor
   */
  public static final ExternalProcessExecutor getInstance() {
    return ProcessExecutorLoader.INSTANCE;
  }

  /** the loader of the external process executor */
  private static final class ProcessExecutorLoader {
    /** the globally shared instance of the external process tool */
    static final ExternalProcessExecutor INSTANCE = new ExternalProcessExecutor();
  }
}
