package test.junit.org.optimizationBenchmarking.utils.text.textOutput;

import org.junit.experimental.categories.Category;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

import test.junit.CategoryMemoryIntenseTests;

/**
 * A test of an internal wrapper class of
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 */
@Category(CategoryMemoryIntenseTests.class)
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
