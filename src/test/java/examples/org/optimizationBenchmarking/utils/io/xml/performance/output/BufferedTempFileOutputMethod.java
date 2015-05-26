package examples.org.optimizationBenchmarking.utils.io.xml.performance.output;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

/** a writer writing directly to a temp file */
public class BufferedTempFileOutputMethod extends OutputMethod {

  /** create */
  public BufferedTempFileOutputMethod() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Writer createWriter() throws IOException {
    return new BufferedWriter(new _TempFileWriter());
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "bufferedTempFile"; //$NON-NLS-1$
  }
}
