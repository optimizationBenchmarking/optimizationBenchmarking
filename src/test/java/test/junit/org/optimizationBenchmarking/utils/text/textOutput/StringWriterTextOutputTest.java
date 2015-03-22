package test.junit.org.optimizationBenchmarking.utils.text.textOutput;

import java.io.StringWriter;

import org.junit.experimental.categories.Category;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

import test.junit.CategoryMemoryIntenseTests;

/**
 * A test of an internal wrapper class of
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 */
@Category(CategoryMemoryIntenseTests.class)
public class StringWriterTextOutputTest extends
    TextOutputTest<StringWriter> {

  /** create */
  public StringWriterTextOutputTest() {
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
    return AbstractTextOutput.wrap(root);
  }

}
