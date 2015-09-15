package org.optimizationBenchmarking.utils.math.mathEngine.impl.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.math.mathEngine.impl.abstr.MathEngineBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.TextProcess;
import org.optimizationBenchmarking.utils.tools.impl.process.TextProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.TextProcessExecutor;

/**
 * The builder for an R engine.
 */
public final class REngineBuilder extends
    MathEngineBuilder<REngine, REngineBuilder> {

  /** create the R engine builder */
  REngineBuilder() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public final REngine create() throws IOException {
    final TextProcessBuilder builder;
    final Logger log;
    final TempDir temp;
    final TextProcess tp;
    final BufferedWriter bw;
    final R r;
    String line;

    log = this.getLogger();

    builder = TextProcessExecutor.getInstance().use();
    r = R.getInstance();
    builder.setExecutable(r.m_rBinary);

    temp = new TempDir();

    builder.setDirectory(temp.getPath());
    for (final String s : r.m_params) {
      builder.addStringArgument(s);
    }
    builder.setLogger(log);
    builder.setProcessCloser(new _RCloser());

    tp = builder.create();
    bw = tp.getStdIn();

    try (final InputStream is = REngineBuilder.class
        .getResourceAsStream("init.txt")) { //$NON-NLS-1$
      try (final InputStreamReader isr = new InputStreamReader(is)) {
        try (final BufferedReader br = new BufferedReader(isr)) {
          while ((line = br.readLine()) != null) {
            bw.write(line);
            bw.newLine();
          }
        }
      }
    }

    bw.flush();

    return new REngine(tp, temp, log);
  }
}
