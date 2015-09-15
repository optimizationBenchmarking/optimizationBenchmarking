package org.optimizationBenchmarking.utils.math.mathEngine.impl.R;

import java.io.BufferedWriter;
import java.io.IOException;

import org.optimizationBenchmarking.utils.tools.impl.process.IProcessCloser;
import org.optimizationBenchmarking.utils.tools.impl.process.TextProcess;

/** the R closer */
final class _RCloser implements IProcessCloser<TextProcess> {

  /** create the R closer */
  _RCloser() {
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
    bw.write("q();"); //$NON-NLS-1$
    bw.newLine();
    bw.flush();
  }
}
