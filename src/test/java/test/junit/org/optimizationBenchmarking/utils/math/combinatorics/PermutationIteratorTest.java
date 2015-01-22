package test.junit.org.optimizationBenchmarking.utils.math.combinatorics;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.MemoryUtils;
import org.optimizationBenchmarking.utils.math.combinatorics.PermutationHashKey;
import org.optimizationBenchmarking.utils.math.combinatorics.PermutationIterator;
import org.optimizationBenchmarking.utils.math.functions.combinatoric.Factorial;

/**
 * A test of the permutation iterator.
 */
public class PermutationIteratorTest {

  /** the constructor */
  public PermutationIteratorTest() {
    super();
  }

  /**
   * test permutation creation of length n
   * 
   * @param n
   *          the length
   */
  private static final void __doTestInner(final int n) {
    HashMap<PermutationHashKey, PermutationHashKey> set;
    PermutationHashKey newKey, oldKey;
    int expected;
    PermutationIterator it;

    expected = Factorial.INSTANCE.computeAsInt(n);
    Assert.assertTrue(expected < Integer.MAX_VALUE);

    set = new HashMap<>(expected);
    it = new PermutationIterator(n);
    while (it.hasNext()) {
      newKey = new PermutationHashKey(it.next().clone());
      oldKey = set.get(newKey);
      if (oldKey != null) {
        throw new AssertionError(("old: " + oldKey + //$NON-NLS-1$
            ", new: " + newKey)); //$NON-NLS-1$
      }
      set.put(newKey, newKey);
    }
    Assert.assertEquals(expected, set.size());
  }

  /**
   * test permutation creation of length n
   * 
   * @param n
   *          the length
   */
  private static final void __doTest(final int n) {
    try {
      PermutationIteratorTest.__doTestInner(n);
    } catch (final OutOfMemoryError oome) {
      if (n < 10) {
        throw oome;// about 300'000 should always be possible
      }
    }
    MemoryUtils.gc();
  }

  /** test the amount of created unique permutations of length 1 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOfLength1() {
    PermutationIteratorTest.__doTest(1);
  }

  /** test the amount of created unique permutations of length 2 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOfLength2() {
    PermutationIteratorTest.__doTest(2);
  }

  /** test the amount of created unique permutations of length 3 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOfLength3() {
    PermutationIteratorTest.__doTest(3);
  }

  /** test the amount of created unique permutations of length 4 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOfLength4() {
    PermutationIteratorTest.__doTest(4);
  }

  /** test the amount of created unique permutations of length 5 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOfLength5() {
    PermutationIteratorTest.__doTest(5);
  }

  /** test the amount of created unique permutations of length 6 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOfLength6() {
    PermutationIteratorTest.__doTest(6);
  }

  /** test the amount of created unique permutations of length 7 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOfLength7() {
    PermutationIteratorTest.__doTest(7);
  }

  /** test the amount of created unique permutations of length 8 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOfLength8() {
    PermutationIteratorTest.__doTest(8);
  }

  /** test the amount of created unique permutations of length 9 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOfLength9() {
    PermutationIteratorTest.__doTest(9);
  }

  /** test the amount of created unique permutations of length 10 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOfLength10() {
    PermutationIteratorTest.__doTest(10);
  }

  /** test the amount of created unique permutations of length 11 */
  @Test(timeout = 11600000)
  public void testCreatedUniqueOfLength11() {
    PermutationIteratorTest.__doTest(11);
  }

  /** test the amount of created unique permutations of length 12 */
  @Test(timeout = 11600000)
  public void testCreatedUniqueOfLength12() {
    PermutationIteratorTest.__doTest(12);
  }

}
