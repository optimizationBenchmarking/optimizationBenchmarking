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
   * @param zeroBased
   *          do we test zero based ({@code true}) or one-based (
   *          {@code false}) permutations?
   */
  private static final void __doTestInner(final int n,
      final boolean zeroBased) {
    HashMap<PermutationHashKey, PermutationHashKey> set;
    PermutationHashKey newKey, oldKey;
    int expected, min, maxExclusive;
    PermutationIterator it;

    expected = Factorial.INSTANCE.computeAsInt(n);
    Assert.assertTrue(expected < Integer.MAX_VALUE);

    set = new HashMap<>(expected);
    it = new PermutationIterator(n, zeroBased);
    maxExclusive = (zeroBased ? n : (n + 1));
    min = maxExclusive - n;
    while (it.hasNext()) {
      newKey = new PermutationHashKey(it.next().clone());
      for (final int num : newKey.getData()) {
        Assert.assertTrue((num >= min) && (num < maxExclusive));
      }
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
   * @param zeroBased
   *          do we test zero based ({@code true}) or one-based (
   *          {@code false}) permutations?
   */
  private static final void __doTest(final int n,
      final boolean zeroBased) {
    try {
      PermutationIteratorTest.__doTestInner(n, zeroBased);
    } catch (final OutOfMemoryError oome) {
      if (n < 10) {
        throw oome;// about 300'000 should always be possible
      }
    }
    MemoryUtils.fullGC();
  }

  /** test the amount of created unique permutations of length 1 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOneBasedOfLength1() {
    PermutationIteratorTest.__doTest(1, false);
  }

  /** test the amount of created unique permutations of length 2 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOneBasedOfLength2() {
    PermutationIteratorTest.__doTest(2, false);
  }

  /** test the amount of created unique permutations of length 3 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOneBasedOfLength3() {
    PermutationIteratorTest.__doTest(3, false);
  }

  /** test the amount of created unique permutations of length 4 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOneBasedOfLength4() {
    PermutationIteratorTest.__doTest(4, false);
  }

  /** test the amount of created unique permutations of length 5 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOneBasedOfLength5() {
    PermutationIteratorTest.__doTest(5, false);
  }

  /** test the amount of created unique permutations of length 6 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOneBasedOfLength6() {
    PermutationIteratorTest.__doTest(6, false);
  }

  /** test the amount of created unique permutations of length 7 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOneBasedOfLength7() {
    PermutationIteratorTest.__doTest(7, false);
  }

  /** test the amount of created unique permutations of length 8 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOneBasedOfLength8() {
    PermutationIteratorTest.__doTest(8, false);
  }

  /** test the amount of created unique permutations of length 9 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOneBasedOfLength9() {
    PermutationIteratorTest.__doTest(9, false);
  }

  /** test the amount of created unique permutations of length 10 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueOneBasedOfLength10() {
    PermutationIteratorTest.__doTest(10, false);
  }

  /** test the amount of created unique permutations of length 1 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueZeroBasedOfLength1() {
    PermutationIteratorTest.__doTest(1, true);
  }

  /** test the amount of created unique permutations of length 2 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueZeroBasedOfLength2() {
    PermutationIteratorTest.__doTest(2, true);
  }

  /** test the amount of created unique permutations of length 3 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueZeroBasedOfLength3() {
    PermutationIteratorTest.__doTest(3, true);
  }

  /** test the amount of created unique permutations of length 4 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueZeroBasedOfLength4() {
    PermutationIteratorTest.__doTest(4, true);
  }

  /** test the amount of created unique permutations of length 5 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueZeroBasedOfLength5() {
    PermutationIteratorTest.__doTest(5, true);
  }

  /** test the amount of created unique permutations of length 6 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueZeroBasedOfLength6() {
    PermutationIteratorTest.__doTest(6, true);
  }

  /** test the amount of created unique permutations of length 7 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueZeroBasedOfLength7() {
    PermutationIteratorTest.__doTest(7, true);
  }

  /** test the amount of created unique permutations of length 8 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueZeroBasedOfLength8() {
    PermutationIteratorTest.__doTest(8, true);
  }

  /** test the amount of created unique permutations of length 9 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueZeroBasedOfLength9() {
    PermutationIteratorTest.__doTest(9, true);
  }

  /** test the amount of created unique permutations of length 10 */
  @Test(timeout = 3600000)
  public void testCreatedUniqueZeroBasedOfLength10() {
    PermutationIteratorTest.__doTest(10, true);
  }
}
