package examples.org.optimizationBenchmarking.utils.io.xml.performance.output;

import java.io.IOException;
import java.io.Writer;

/** a writer writing directly to a temp file */
public class TempFileOutputMethod extends OutputMethod {

  /** create */
  public TempFileOutputMethod() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Writer createWriter() throws IOException {
    return new _TempFileWriter();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "tempFile"; //$NON-NLS-1$
  }
}
