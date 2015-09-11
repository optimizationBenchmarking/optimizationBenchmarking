package org.optimizationBenchmarking.utils.tools.impl.process;

import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/**
 * This tool allows us to start external processes in a deadlock-free way.
 * Different from the processes started by
 * {@link org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessExecutor}
 * , the standard streams to communicate with the processes are character
 * streams, i.e., {@link java.io.Reader}s and {@link java.io.Writer}s,
 */
public final class TextProcessExecutor extends Tool {

  /** create */
  TextProcessExecutor() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return ExternalProcessExecutor.getInstance().canUse();
  }

  /** {@inheritDoc} */
  @Override
  public final void checkCanUse() {
    super.checkCanUse();
    ExternalProcessExecutor.getInstance().checkCanUse();
  }

  /** {@inheritDoc} */
  @Override
  public final TextProcessBuilder use() {
    this.checkCanUse();
    return new TextProcessBuilder();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Text Process Executor"; //$NON-NLS-1$
  }

  /**
   * Get the instance of the text process executor
   *
   * @return the instance of the text process executor
   */
  public static final TextProcessExecutor getInstance() {
    return ProcessExecutorLoader.INSTANCE;
  }

  /** the loader of the text process executor */
  private static final class ProcessExecutorLoader {
    /** the globally shared instance of the text process tool */
    static final TextProcessExecutor INSTANCE = new TextProcessExecutor();
  }
}
