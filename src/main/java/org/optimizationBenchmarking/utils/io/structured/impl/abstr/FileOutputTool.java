package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import org.optimizationBenchmarking.utils.io.structured.spec.IFileOutputJobBuilder;
import org.optimizationBenchmarking.utils.io.structured.spec.IFileOutputTool;

/**
 * A tool for generating file output
 * 
 * @param <S>
 *          the source type
 */
public class FileOutputTool<S> extends IOTool<S> implements
    IFileOutputTool<S> {

  /** create */
  protected FileOutputTool() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public IFileOutputJobBuilder<S> use() {
    this.beforeUse();
    return new _FileOutputJobBuilder(this);
  }
}
