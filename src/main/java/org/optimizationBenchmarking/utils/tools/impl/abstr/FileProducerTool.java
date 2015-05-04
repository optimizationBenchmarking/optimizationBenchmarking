package org.optimizationBenchmarking.utils.tools.impl.abstr;

import org.optimizationBenchmarking.utils.tools.spec.IFileProducerTool;

/**
 * A tool which can produce files
 */
public abstract class FileProducerTool extends Tool implements
    IFileProducerTool {

  /**
   * Create the file producer tool.
   */
  protected FileProducerTool() {
    super();
  }

}
