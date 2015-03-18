package test.junit.org.optimizationBenchmarking.utils.tools.impl.process;

import org.optimizationBenchmarking.utils.tools.impl.process.ProcessExecutor;

import test.junit.org.optimizationBenchmarking.utils.tools.ToolTest;

/**
 * Test the
 * {@link org.optimizationBenchmarking.utils.tools.impl.process.ProcessExecutor}
 */
public class ProcessExecutorTest extends ToolTest<ProcessExecutor> {

  /** create */
  public ProcessExecutorTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected ProcessExecutor getInstance() {
    return ProcessExecutor.getInstance();
  }
}
