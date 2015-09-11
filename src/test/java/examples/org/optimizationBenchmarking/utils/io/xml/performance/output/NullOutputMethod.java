package examples.org.optimizationBenchmarking.utils.io.xml.performance.output;

import java.io.Writer;

import org.optimizationBenchmarking.utils.io.nul.NullWriter;

/** An output method throwing all data away */
public class NullOutputMethod extends OutputMethod {

  /** create */
  public NullOutputMethod() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Writer createWriter() {
    return NullWriter.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "nul"; //$NON-NLS-1$
  }
}
