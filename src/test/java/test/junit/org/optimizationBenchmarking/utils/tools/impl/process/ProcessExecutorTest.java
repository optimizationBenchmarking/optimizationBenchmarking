package test.junit.org.optimizationBenchmarking.utils.tools.impl.process;

import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessExecutor;

import test.junit.org.optimizationBenchmarking.utils.tools.ToolTest;

/**
 * Test the
 * {@link org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessExecutor}
 */
public class ProcessExecutorTest extends ToolTest<ExternalProcessExecutor> {

  /** create */
  public ProcessExecutorTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected ExternalProcessExecutor getInstance() {
    return ExternalProcessExecutor.getInstance();
  }
}
