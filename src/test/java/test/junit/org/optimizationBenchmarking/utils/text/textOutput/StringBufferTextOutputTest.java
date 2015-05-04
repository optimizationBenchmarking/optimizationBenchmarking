package test.junit.org.optimizationBenchmarking.utils.text.textOutput;

import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A test of an internal wrapper class of
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 */
public class StringBufferTextOutputTest extends
TextOutputTest<StringBuffer> {

  /** create */
  public StringBufferTextOutputTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final StringBuffer createRootObject() {
    return new StringBuffer();
  }

  /** {@inheritDoc} */
  @Override
  protected final String getString(final StringBuffer root) {
    return root.toString();
  }

  /** {@inheritDoc} */
  @Override
  protected final ITextOutput wrap(final StringBuffer root) {
    return AbstractTextOutput.wrap(root);
  }

}
