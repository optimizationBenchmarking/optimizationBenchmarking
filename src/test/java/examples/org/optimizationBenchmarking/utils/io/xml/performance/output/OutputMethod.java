package examples.org.optimizationBenchmarking.utils.io.xml.performance.output;

import java.io.IOException;
import java.io.Writer;

/**
 * an output method provides writers to test the performance of the xml
 * serialization method
 */
public abstract class OutputMethod {

  /** create */
  protected OutputMethod() {
    super();
  }

  /**
   * Create a writer
   *
   * @return the writer
   * @throws IOException
   *           if io fails
   */
  public abstract Writer createWriter() throws IOException;

}
