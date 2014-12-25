package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** the output job */
final class _OutputJob extends _IOJob {

  /** the destination */
  private final _Location m_dest;

  /**
   * create the _IOJob
   * 
   * @param logger
   *          the logger
   * @param tool
   *          the owning tool
   * @param data
   *          the source data
   * @param dest
   *          the destination
   */
  _OutputJob(final Logger logger, final FileOutputTool<?> tool,
      final Object data, final _Location dest) {
    super(logger, tool, data);
    if (dest == null) {
      throw new IllegalArgumentException("Destination must not be null."); //$NON-NLS-1$
    }
    this.m_dest = dest;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  final void _doCall() throws Throwable {
    ((FileOutputTool) (this.m_tool))._handle(this, this.m_data,
        this.m_dest);
  }

  /** {@inheritDoc} */
  @Override
  final void _appendID(final MemoryTextOutput textOut) {
    super._appendID(textOut);
    _Location._appendLocation(this.m_dest, textOut);
  }
}
