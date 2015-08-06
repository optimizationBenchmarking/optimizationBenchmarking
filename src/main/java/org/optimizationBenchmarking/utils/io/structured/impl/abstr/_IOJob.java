package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.structured.spec.IIOJob;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for I/O jobs
 */
abstract class _IOJob extends IOJob implements IIOJob {

  /** the data */
  final Object m_data;

  /**
   * create the _IOJob
   *
   * @param logger
   *          the logger
   * @param tool
   *          the owning tool
   * @param basePath
   *          the base path used for path and file resolution
   * @param data
   *          the source/dest data
   */
  _IOJob(final Logger logger, final IOTool<?> tool, final Path basePath,
      final Object data) {
    super(logger, tool, basePath);
    if (data == null) {
      throw new IllegalArgumentException("Data must not be null."); //$NON-NLS-1$
    }
    this.m_data = data;
  }

  /** {@inheritDoc} */
  @Override
  void _appendID(final MemoryTextOutput textOut) {
    super._appendID(textOut);
    textOut.append(this.m_data.getClass().getSimpleName());
    textOut.append('#');
    textOut.append(System.identityHashCode(this.m_data));
  }

  /**
   * do the actual job
   *
   * @throws Throwable
   *           if it must
   */
  abstract void _doCall() throws Throwable;

  /** this method is called at the end */
  void _last() {
    //
  }

  /** {@inheritDoc} */
  @Override
  public final Void call() throws IOException {
    final Logger logger;

    try {
      logger = this.getLogger();
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            (("Begin of " + //$NON-NLS-1$
            this.m_tool.toString()) + " I/O.")); //$NON-NLS-1$
      }

      this.m_token = this.m_tool.createToken(this, this.m_data);
      this.m_tool.before(this, this.m_data);
      this._doCall();
      this.m_tool.after(this, this.m_data);
      this.m_token = null;
      this._last();

      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            (("End of " + this.m_tool.toString()) + //$NON-NLS-1$
            " I/O")); //$NON-NLS-1$
      }
    } catch (final Throwable error) {
      this.handleError(error, null);
    }
    return null;
  }

}
