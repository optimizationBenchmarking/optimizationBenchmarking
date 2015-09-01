package test.junit.org.optimizationBenchmarking.utils.text.tokenizers;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.text.tokenizers.LineIterator;

/** the line iterator test */
public class LineIteratorTest {

  /** create */
  public LineIteratorTest() {
    super();
  }

  /** test string iteration test 1 */
  @Test(timeout = 3600000)
  public void testCase1() {
    Iterator<String> it;

    it = new LineIterator("a\nb \n cde \n", false, false); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("a", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("b ", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals(" cde ", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("", it.next()); //$NON-NLS-1$

    Assert.assertFalse(it.hasNext());
  }

  /** test string iteration test 2 */
  @Test(timeout = 3600000)
  public void testCase2() {
    Iterator<String> it;

    it = new LineIterator("a\nb \n cde \n", true, false); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("a", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("b", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("cde", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("", it.next()); //$NON-NLS-1$

    Assert.assertFalse(it.hasNext());
  }

  /** test string iteration test 3 */
  @Test(timeout = 3600000)
  public void testCase3() {
    Iterator<String> it;

    it = new LineIterator("a\nb \n cde \n", true, true); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("a", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("b", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("cde", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals(null, it.next());

    Assert.assertFalse(it.hasNext());
  }

  /** test string iteration test 5 */
  @Test(timeout = 3600000)
  public void testCase5() {
    Iterator<String> it;

    it = new LineIterator("a\r\nb \n\r cde \r\n\r", false, false); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("a", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("b ", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals(" cde ", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("", it.next()); //$NON-NLS-1$

    Assert.assertFalse(it.hasNext());
  }

  /** test string iteration test 6 */
  @Test(timeout = 3600000)
  public void testCase6() {
    Iterator<String> it;

    it = new LineIterator("a\r\r\nb \n\r cde \r\n", true, false); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("a", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("b", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("cde", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("", it.next()); //$NON-NLS-1$

    Assert.assertFalse(it.hasNext());
  }

}
