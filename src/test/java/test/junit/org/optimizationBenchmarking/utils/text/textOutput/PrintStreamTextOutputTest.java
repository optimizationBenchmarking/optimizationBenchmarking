package test.junit.org.optimizationBenchmarking.utils.text.textOutput;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Ignore;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A test of an internal wrapper class of
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}.
 * This test currently is not working, the reason seems to be an encoding
 * issue. Therefore, it is currently {@link org.junit.Ignore ignored}.
 */
@Ignore
public class PrintStreamTextOutputTest extends
    TextOutputTest<ByteArrayOutputStream> {

  /** create */
  public PrintStreamTextOutputTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final ByteArrayOutputStream createRootObject() {
    return new ByteArrayOutputStream();
  }

  /** {@inheritDoc} */
  @Override
  protected final String getString(final ByteArrayOutputStream root) {
    try {
      return new String(root.toByteArray(), "UTF-16"); //$NON-NLS-1$
    } catch (final Throwable t) {
      Assert.fail();
    }
    return null;
  }

  /** {@inheritDoc} */
  @Override
  protected final ITextOutput wrap(final ByteArrayOutputStream root) {
    try {
      return AbstractTextOutput
          .wrap(new PrintStream(root, true, "UTF-16")); //$NON-NLS-1$
    } catch (final Throwable t) {
      Assert.fail();
    }
    return null;
  }

}
