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
final class _REngineBuilder extends
    MathEngineBuilder<_REngine, _REngineBuilder> {

  /** create the R engine builder */
  _REngineBuilder() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public final _REngine create() throws IOException {
    final ExternalProcessBuilder builder;
    final Logger log;
    final TempDir temp;

    log = this.m_logger;

    builder = ProcessExecutor.INSTANCE.use();
    builder.setExecutable(R.INSTANCE.m_rBinary);

    temp = new TempDir();

    builder.setDirectory(temp.getPath());
    builder.addStringArgument("--vanilla"); //$NON-NLS-1$
    builder.addStringArgument("--slave"); //$NON-NLS-1$
    builder.addStringArgument("--no-readline"); //$NON-NLS-1$
    builder.addStringArgument("--encoding=" + //$NON-NLS-1$
        R._encoding().name());

    builder.setLogger(log);

    return new _REngine(builder.create(), temp, log);
  }

}
