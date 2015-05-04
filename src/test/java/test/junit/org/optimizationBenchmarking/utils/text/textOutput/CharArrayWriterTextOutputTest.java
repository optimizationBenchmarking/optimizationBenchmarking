package test.junit.org.optimizationBenchmarking.utils.text.textOutput;

import java.io.CharArrayWriter;

import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A test of an internal wrapper class of
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 */
public class CharArrayWriterTextOutputTest extends
TextOutputTest<CharArrayWriter> {

  /** create */
  public CharArrayWriterTextOutputTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final CharArrayWriter createRootObject() {
    return new CharArrayWriter();
  }

  /** {@inheritDoc} */
  @Override
  protected final String getString(final CharArrayWriter root) {
    return root.toString();
  }

  /** {@inheritDoc} */
  @Override
  protected final ITextOutput wrap(final CharArrayWriter root) {
    return AbstractTextOutput.wrap(root);
  }

}
