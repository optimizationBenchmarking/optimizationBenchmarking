package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/** A job for the LaTeX compiler. */
public abstract class LaTeXJob extends ToolJob implements Callable<Void> {

  /**
   * Create the LaTeX job
   *
   * @param logger
   *          the logger
   */
  LaTeXJob(final Logger logger) {
    super(logger);
  }

  /**
   * Can this job do some actual compilation work? If {@code true} is
   * returned, this job can compile a LaTeX document to the PDF format. If
   * {@code false} is returned, this job does basically nothing if
   * executed. The reason may be that no suitable tool chain was found
   * which can deal with the specified requirements.
   *
   * @return {@code true} if this job can (probably) compile the supplied
   *         documents to a PDF, {@code false} if it does nothing
   */
  public abstract boolean canCompileToPDF();

  /** {@inheritDoc} */
  @Override
  public abstract Void call() throws IOException;
}
