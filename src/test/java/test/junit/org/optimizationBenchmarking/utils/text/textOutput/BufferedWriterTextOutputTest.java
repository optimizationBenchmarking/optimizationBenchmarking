package test.junit.org.optimizationBenchmarking.utils.text.textOutput;

import java.io.BufferedWriter;
import java.io.StringWriter;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A test of an internal wrapper class of
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 */
public class BufferedWriterTextOutputTest extends
    TextOutputTest<BufferedWriter> {

  /** create */
  public BufferedWriterTextOutputTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final BufferedWriter createRootObject() {
    return new __BufferedStringWriter(new StringWriter());
  }

  /** {@inheritDoc} */
  @Override
  protected final String getString(final BufferedWriter root) {
    return root.toString();
  }

  /** {@inheritDoc} */
  @Override
  protected final ITextOutput wrap(final BufferedWriter root) {
    return AbstractTextOutput.wrap(root);
  }

  /** the internal class */
  private static final class __BufferedStringWriter extends BufferedWriter {

    /** the string writer */
    private final StringWriter m_sw;

    /**
     * create
     * 
     * @param sw
     *          the string writer
     */
    __BufferedStringWriter(final StringWriter sw) {
      super(sw);
      this.m_sw = sw;
    }

    /** {@inheritDoc} */
    @Override
    public final String toString() {
      try {
        this.flush();
      } catch (final Throwable tt) {
        ErrorUtils.throwRuntimeException(//
            "Error while flushing BufferedWriterTextOutput.", //$NON-NLS-1$
            tt);
      }
      return this.m_sw.toString();
    }

  }

}
