package test.junit.org.optimizationBenchmarking.utils.math.matrix.processing.iterator2D;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.combinatorics.PermutationIterator;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.LongMatrix1D;
import org.optimizationBenchmarking.utils.math.matrix.processing.iterator2D.MatrixIterator2D;

import test.junit.TestBase;
import test.junit.org.optimizationBenchmarking.utils.math.matrix.MatrixTest;

/**
 * Test the matrix iterator 2D.
 */
public class MatrixIterator2DTest extends TestBase {

  /** the matrix data */
  private static final long[][] DATA_1 = {//
  // the first matrix
      { 1L, 10L,//
          5L, 55L,//
          6L, 60L,//
          6L, 61L,//
          6L, 62L,//
          7L, 70L,//
          15L, 70L,//
          16L, 71L,//
      },//
      // the second matrix
      { 2L, 22L,//
          4L, 40L,//
          5L, 56L,//
          6L, 63L,//
          8L, 80L,//
          8L, 80L,//
          12L, 80L,//
          13L, 80L,//
      },//
      // the third matrix
      { 3L, 36L,//
          4L, 50L,//
          5L, 57L,//
          6L, 64L,//
          9L, 90L,//
          10L, 90L,//
          11L, 92L,//
          20L, 92L,//
      } };

  /** the expected iteration result */
  private static final long[][] EXPECTED_1 = {//
  { 1L, 10L },//
      { 2L, 10L, 22L },//
      { 3L, 10L, 22L, 36L },//
      { 4L, 10L, 40L, 50L },//
      { 5L, 55L, 56L, 57L },//
      { 6L, 62L, 63L, 64L },//
      { 7L, 70L, 63L, 64L },//
      { 8L, 70L, 80L, 64L },//
      { 9L, 70L, 80L, 90L },//
      { 11L, 70L, 80L, 92L },//
      { 16L, 71L, 80L, 92L },//
      { 20L, 71L, 80L, 92L },//
  };

  /** create */
  public MatrixIterator2DTest() {
    super();
  }

  /** test whether the iteration proceeds as expected */
  @Test(timeout = 3600000)
  public void testLongIterationData1() {
    final PermutationIterator it;

    it = new PermutationIterator(MatrixIterator2DTest.DATA_1.length);
    while (it.hasNext()) {
      MatrixIterator2DTest.__testLong(MatrixIterator2DTest.DATA_1,
          it.next(), MatrixIterator2DTest.EXPECTED_1);
    }
  }

  /**
   * test a permutation of a given data array
   * 
   * @param data
   *          the data
   * @param perm
   *          the permutation
   * @param result
   *          the expected result
   */
  private static final void __testLong(final long[][] data,
      final int[] perm, final long[][] result) {
    final IMatrix[] matrices;
    final boolean[] have;
    final MatrixIterator2D iterator;
    long val;
    int i, j;

    i = data.length;
    matrices = new IMatrix[i];
    have = new boolean[i];
    for (; (--i) >= 0;) {
      matrices[perm[i] - 1] = new LongMatrix1D(data[i],
          (data[i].length >>> 1), 2);
    }

    iterator = MatrixIterator2D.iterate(0, 1, matrices);
    for (final long[] expected : result) {
      Assert.assertTrue(iterator.hasNext());
      Assert.assertEquals(iterator.next().longValue(), expected[0]);
      Arrays.fill(have, false);
      i = (expected.length - 1);
      Assert.assertEquals(i, iterator.n());
      Assert.assertEquals(matrices.length, iterator.nMax());

      outer: for (; (--i) >= 0;) {
        val = iterator.getLong(0, i);
        finder: for (j = expected.length; (--j) > 0;) {
          if (expected[j] == val) {
            if (have[j - 1]) {
              continue finder;
            }
            have[j - 1] = true;
            continue outer;
          }
        }
        Assert.fail("Value " + val + //$NON-NLS-1$ 
            " not found."); //$NON-NLS-1$
      }

      new MatrixTest<>(null, iterator, true).validateInstance();
    }

    Assert.assertFalse(iterator.hasNext());
  }

}
