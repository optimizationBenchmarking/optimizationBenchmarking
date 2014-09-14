package test.junit.org.optimizationBenchmarking.utils.text.tokenizers;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.text.tokenizers.SeparatorBasedStringIterator;

/** the csv iterator test */
public class SeparatorBasedStringIteratorTest {

  /** create */
  public SeparatorBasedStringIteratorTest() {
    super();
  }

  /** test string iteration test 1 */
  @Test(timeout = 3600000)
  public void testCase1() {
    Iterator<String> it;

    it = new SeparatorBasedStringIterator(//
        "abc,def,ghi ,jkl, mno , pqr\t\n,stu\n\r,\r\nvwx    ,  ,yz  , ", //$NON-NLS-1$
        ',', true);

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("abc", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("def", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("ghi", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("jkl", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("mno", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("pqr", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("stu", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("vwx", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals(null, it.next());

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("yz", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals(null, it.next());

    Assert.assertFalse(it.hasNext());
  }

  /** test string iteration test 2 */
  @Test(timeout = 3600000)
  public void testCase2() {
    Iterator<String> it;

    it = new SeparatorBasedStringIterator(//
        "abc,def,ghi ,jkl, mno , pqr\t\n,stu\n\r,\r\nvwx    ,  ,yz  , ", //$NON-NLS-1$
        ',', false);

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("abc", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("def", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("ghi", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("jkl", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("mno", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("pqr", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("stu", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("vwx", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("", it.next());//$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("yz", it.next()); //$NON-NLS-1$

    Assert.assertTrue(it.hasNext());
    Assert.assertEquals("", it.next());//$NON-NLS-1$

    Assert.assertFalse(it.hasNext());
  }

  /** test string iteration test 3 */
  @Test(timeout = 3600000)
  public void testCase3() {
    Iterator<String> it;

    it = new SeparatorBasedStringIterator(//
        "abc", //$NON-NLS-1$
        ',', true);
    Assert.assertTrue(it.hasNext());
    Assert.assertEquals(it.next(), "abc");//$NON-NLS-1$
    Assert.assertFalse(it.hasNext());

    it = new SeparatorBasedStringIterator(//
        "\tabc", //$NON-NLS-1$
        ',', true);
    Assert.assertTrue(it.hasNext());
    Assert.assertEquals(it.next(), "abc");//$NON-NLS-1$
    Assert.assertFalse(it.hasNext());

    it = new SeparatorBasedStringIterator(//
        "abc\n ", //$NON-NLS-1$
        ',', true);
    Assert.assertTrue(it.hasNext());
    Assert.assertEquals(it.next(), "abc");//$NON-NLS-1$
    Assert.assertFalse(it.hasNext());

    it = new SeparatorBasedStringIterator(//
        "  abc   ", //$NON-NLS-1$
        ',', true);
    Assert.assertTrue(it.hasNext());
    Assert.assertEquals(it.next(), "abc");//$NON-NLS-1$
    Assert.assertFalse(it.hasNext());
  }

  /** test string iteration test 4 */
  @Test(timeout = 3600000)
  public void testCase4() {
    Iterator<String> it;

    it = new SeparatorBasedStringIterator(//
        "", //$NON-NLS-1$
        ',', true);
    Assert.assertFalse(it.hasNext());

    it = new SeparatorBasedStringIterator(//
        " ", //$NON-NLS-1$
        ',', true);
    Assert.assertFalse(it.hasNext());

    it = new SeparatorBasedStringIterator(//
        "\n", //$NON-NLS-1$
        ',', true);
    Assert.assertFalse(it.hasNext());

    it = new SeparatorBasedStringIterator(//
        "\t\n\r ", //$NON-NLS-1$
        ',', true);
    Assert.assertFalse(it.hasNext());
  }

  /** test string iteration test 5 */
  @Test(timeout = 3600000)
  public void testCase5() {
    Iterator<String> it;

    it = new SeparatorBasedStringIterator(//
        "x", //$NON-NLS-1$
        ',', true);
    Assert.assertTrue(it.hasNext());
    Assert.assertEquals(it.next(), "x");//$NON-NLS-1$
    Assert.assertFalse(it.hasNext());

    it = new SeparatorBasedStringIterator(//
        "\ty", //$NON-NLS-1$
        ',', true);
    Assert.assertTrue(it.hasNext());
    Assert.assertEquals(it.next(), "y");//$NON-NLS-1$
    Assert.assertFalse(it.hasNext());

    it = new SeparatorBasedStringIterator(//
        "z\n ", //$NON-NLS-1$
        ',', true);
    Assert.assertTrue(it.hasNext());
    Assert.assertEquals(it.next(), "z");//$NON-NLS-1$
    Assert.assertFalse(it.hasNext());

    it = new SeparatorBasedStringIterator(//
        "  v   ", //$NON-NLS-1$
        ',', true);
    Assert.assertTrue(it.hasNext());
    Assert.assertEquals(it.next(), "v");//$NON-NLS-1$
    Assert.assertFalse(it.hasNext());
  }
}
