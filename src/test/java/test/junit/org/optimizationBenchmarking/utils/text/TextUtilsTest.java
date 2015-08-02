package test.junit.org.optimizationBenchmarking.utils.text;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

import test.junit.TestBase;

/**
 * test the TextUtils class
 */
public class TextUtilsTest extends TestBase {

  /** create */
  public TextUtilsTest() {
    super();
  }

  /** test the simple file size appending */
  @Test(timeout = 3600000)
  public void testAppendFileSizeSimple() {
    final MemoryTextOutput mto;

    mto = new MemoryTextOutput();

    TextUtils.appendFileSize(10, mto);
    Assert.assertEquals("10\u2007B", mto.toString()); //$NON-NLS-1$
    mto.clear();

    TextUtils.appendFileSize(0, mto);
    Assert.assertEquals("0\u2007B", mto.toString()); //$NON-NLS-1$
    mto.clear();

    TextUtils.appendFileSize(1023, mto);
    Assert.assertEquals("1023\u2007B", mto.toString()); //$NON-NLS-1$
    mto.clear();

    TextUtils.appendFileSize(1024, mto);
    Assert.assertEquals("1\u2007KiB", mto.toString()); //$NON-NLS-1$
    mto.clear();

    TextUtils.appendFileSize(1025, mto);
    Assert.assertEquals("1\u2007KiB", mto.toString()); //$NON-NLS-1$
    mto.clear();

    TextUtils.appendFileSize(3500, mto);
    Assert.assertEquals("3\u2007KiB", mto.toString()); //$NON-NLS-1$
    mto.clear();

    TextUtils.appendFileSize(((1024 * 1024) - 1), mto);
    Assert.assertEquals("1023\u2007KiB", mto.toString()); //$NON-NLS-1$
    mto.clear();

    TextUtils.appendFileSize((1024 * 1024), mto);
    Assert.assertEquals("1\u2007MiB", mto.toString()); //$NON-NLS-1$
    mto.clear();

    TextUtils.appendFileSize(((1024L * 1024L * 1024L) - 1), mto);
    Assert.assertEquals("1023\u2007MiB", mto.toString()); //$NON-NLS-1$
    mto.clear();

    TextUtils.appendFileSize((1024L * 1024L * 1024L), mto);
    Assert.assertEquals("1\u2007GiB", mto.toString()); //$NON-NLS-1$
    mto.clear();

    TextUtils.appendFileSize(((1024L * 1024L * 1024L * 1024L) - 1), mto);
    Assert.assertEquals("1023\u2007GiB", mto.toString()); //$NON-NLS-1$
    mto.clear();

    TextUtils.appendFileSize((1024L * 1024L * 1024L * 1024L), mto);
    Assert.assertEquals("1\u2007TiB", mto.toString()); //$NON-NLS-1$
    mto.clear();

    TextUtils.appendFileSize(
        ((1024L * 1024L * 1024L * 1024L * 1024L) - 1), mto);
    Assert.assertEquals("1023\u2007TiB", mto.toString()); //$NON-NLS-1$
    mto.clear();

    TextUtils.appendFileSize((1024L * 1024L * 1024L * 1024L * 1024L), mto);
    Assert.assertEquals("1\u2007PiB", mto.toString()); //$NON-NLS-1$
    mto.clear();

    TextUtils.appendFileSize(
        ((1024L * 1024L * 1024L * 1024L * 1024L * 1024L) - 1), mto);
    Assert.assertEquals("1023\u2007PiB", mto.toString()); //$NON-NLS-1$
    mto.clear();

    TextUtils.appendFileSize(
        (1024L * 1024L * 1024L * 1024L * 1024L * 1024L), mto);
    Assert.assertEquals("1\u2007EiB", mto.toString()); //$NON-NLS-1$
    mto.clear();
  }

}
