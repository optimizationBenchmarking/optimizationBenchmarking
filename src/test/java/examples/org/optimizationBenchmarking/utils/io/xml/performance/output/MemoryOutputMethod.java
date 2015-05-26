package examples.org.optimizationBenchmarking.utils.io.xml.performance.output;

import java.io.CharArrayWriter;
import java.io.Writer;

/** An output method keeping the created data in memory */
public class MemoryOutputMethod extends OutputMethod {

  /** create */
  public MemoryOutputMethod() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Writer createWriter() {
    return new CharArrayWriter();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "memory"; //$NON-NLS-1$
  }
}
