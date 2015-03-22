package test.junit.org.optimizationBenchmarking.utils.text.textOutput;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A test of an internal wrapper class of
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 */
public class PrintWriterTextOutputTest extends
    TextOutputTest<StringWriter> {

  /** create */
  public PrintWriterTextOutputTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final StringWriter createRootObject() {
    return new StringWriter();
  }

  /** {@inheritDoc} */
  @Override
  protected final String getString(final StringWriter root) {
    return root.toString();
  }

  /** {@inheritDoc} */
  @Override
  protected final ITextOutput wrap(final StringWriter root) {
    return AbstractTextOutput.wrap(new PrintWriter(root));
  }

}
