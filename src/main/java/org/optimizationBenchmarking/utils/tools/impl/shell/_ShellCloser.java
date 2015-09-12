package org.optimizationBenchmarking.utils.tools.impl.shell;

import java.io.BufferedWriter;
import java.io.IOException;

import org.optimizationBenchmarking.utils.tools.impl.process.IProcessCloser;
import org.optimizationBenchmarking.utils.tools.impl.process.TextProcess;

/** the shell closer */
final class _ShellCloser implements IProcessCloser<TextProcess> {

  /** create the shell closer */
  _ShellCloser() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public final void beforeClose(final TextProcess process)
      throws IOException {
    final BufferedWriter bw = process.getStdIn();
    bw.flush();
    bw.newLine();
    bw.flush();
    bw.write("exit"); //$NON-NLS-1$
    bw.newLine();
    bw.flush();
  }
}
