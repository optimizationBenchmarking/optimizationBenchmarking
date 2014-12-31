package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.IOException;
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
   * @param data
   *          the source/dest data
   */
  _IOJob(final Logger logger, final IOTool<?> tool, final Object data) {
    super(logger, tool);
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

  /** {@inheritDoc} */
  @Override
  public final Void call() throws IOException {
    try {
      if (this.canLog()) {
        this.log("Begin of I/O"); //$NON-NLS-1$
      }

      this.m_token = this.m_tool.createToken(this, this.m_data);
      this.m_tool.before(this, this.m_data);
      this._doCall();
      this.m_tool.after(this, this.m_data);
      this.m_token = null;

      if (this.canLog()) {
        this.log("End of I/O"); //$NON-NLS-1$
      }
    } catch (final Throwable error) {
      this.handleError(error, null);
    }
    return null;
  }

}
