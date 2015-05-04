package org.optimizationBenchmarking.utils.io.structured.spec;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.optimizationBenchmarking.utils.tools.spec.IToolJob;

/** A job of an I/O tool. */
public interface IIOJob extends IToolJob, Callable<Void> {

  /**
   * Perform the I/O operation.
   *
   * @return always {@code null}
   * @throws IOException
   *           if the I/O fails
   */
  @Override
  public abstract Void call() throws IOException;

}
