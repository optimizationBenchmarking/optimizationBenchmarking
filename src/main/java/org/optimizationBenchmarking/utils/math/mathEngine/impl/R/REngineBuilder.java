package org.optimizationBenchmarking.utils.math.mathEngine.impl.R;

import java.io.IOException;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.math.mathEngine.impl.abstr.MathEngineBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.ProcessExecutor;

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
    final ExternalProcessBuilder builder;
    final Logger log;
    final TempDir temp;
    final R r;

    log = this.getLogger();

    builder = ProcessExecutor.getInstance().use();
    r = R.getInstance();
    builder.setExecutable(r.m_rBinary);

    temp = new TempDir();

    builder.setDirectory(temp.getPath());
    for (final String s : r.m_params) {
      builder.addStringArgument(s);
    }
    builder.setLogger(log);

    return new REngine(builder.create(), temp, log);
  }

}
