package test.junit.org.optimizationBenchmarking.utils.text.textOutput;

import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A test of an internal wrapper class of
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 */
public class StringBuilderTextOutputTest extends
    TextOutputTest<StringBuilder> {

  /** create */
  public StringBuilderTextOutputTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final StringBuilder createRootObject() {
    return new StringBuilder();
  }

  /** {@inheritDoc} */
  @Override
  protected final String getString(final StringBuilder root) {
    return root.toString();
  }

  /** {@inheritDoc} */
  @Override
  protected final ITextOutput wrap(final StringBuilder root) {
    return AbstractTextOutput.wrap(root);
  }

}
