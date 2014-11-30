package org.optimizationBenchmarking.utils.tools.impl.R;

import java.io.IOException;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJobBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.ProcessExecutor;

/**
 * The builder for an R engine.
 */
public final class REngineBuilder extends
    ToolJobBuilder<REngine, REngineBuilder> {

  /** create the R engine builder */
  REngineBuilder() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final REngine create() throws IOException {
    final ExternalProcessBuilder builder;
    final Logger log;

    log = this.m_logger;

    builder = ProcessExecutor.INSTANCE.use();
    builder.setExecutable(R.INSTANCE.m_rBinary);
    builder.setDirectory(PathUtils.getTempDir());
    builder.addStringArgument("--vanilla"); //$NON-NLS-1$
    builder.addStringArgument("--slave"); //$NON-NLS-1$
    builder.addStringArgument("--interactive"); //$NON-NLS-1$
    builder.addStringArgument("--no-readline"); //$NON-NLS-1$
    builder.addStringArgument("--encoding=" + //$NON-NLS-1$
        R._encoding().name());

    builder.setLogger(log);

    return new REngine(builder.create(), log);
  }

}
