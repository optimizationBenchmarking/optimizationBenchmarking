package test.junit.org.optimizationBenchmarking.utils.math.functions;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.combinatorics.PermutationIterator;
import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;

import test.junit.TestBase;

/**
 * A test for mathematical functions.
 */
@Ignore
public abstract class MathematicalFunctionTest extends TestBase {

  /** create the test */
  public MathematicalFunctionTest() {
    super();
  }

  /**
   * Get the mathematical function to test
   *
   * @return the mathematical function to test
   */
  public abstract MathematicalFunction getFunction();

  /**
   * Get the test cases to be used to test the function
   *
   * @return the test cases
   */
  public TestCase[] getTestCases() {
    return TestCase.EMPTY;
  }

  /**
   * Is this operator commutative? Example: {@code a*b = b*a}, i.e.,
   * multiplication is commutative. Division is not, as {@code a/b != b/a}.
   *
   * @return {@code true} if it is, {@code false} if not
   */
  protected boolean isCommutative() {
    return false;
  }

  /**
   * Test the {@code double}-based computation based on the test cases
   */
  @Test(timeout = 3600000)
  public void testTestCasesAllDouble() {
    MathematicalFunction function;
    double[] params;
    int length;
    int[] perm;
    Iterator<int[]> it;

    function = this.getFunction();
    Assert.assertNotNull(function);
    for (final TestCase test : this.getTestCases()) {
      Assert.assertNotNull(test);
      length = test.m_in.length;
      Assert.assertTrue(length >= function.getMinArity());
      Assert.assertTrue(length <= function.getMaxArity());

      if ((test.m_choices & TestCase.ALL_AS_DOUBLE) != 0) {
        params = new double[test.m_in.length];
        it = new PermutationIterator(test.m_in.length);
        do {
          perm = it.next();
          for (length = perm.length; (--length) >= 0;) {
            params[perm[length] - 1] = test.m_in[length].doubleValue();
          }

          Assert.assertEquals(test.m_out.doubleValue(),
              function.computeAsDouble(params), 1e-15d);
        } while (this.isCommutative() && it.hasNext());
      }
    }
  }

  /**
   * Test the {@code float}-based computation based on the test cases
   */
  @Test(timeout = 3600000)
  public void testTestCasesAllFloat() {
    MathematicalFunction function;
    float[] params;
    int length;
    int[] perm;
    Iterator<int[]> it;

    function = this.getFunction();
    Assert.assertNotNull(function);
    for (final TestCase test : this.getTestCases()) {
      Assert.assertNotNull(test);
      length = test.m_in.length;
      Assert.assertTrue(length >= function.getMinArity());
      Assert.assertTrue(length <= function.getMaxArity());

      if ((test.m_choices & TestCase.ALL_AS_FLOAT) != 0) {
        params = new float[test.m_in.length];

        it = new PermutationIterator(test.m_in.length);
        do {
          perm = it.next();
          for (length = perm.length; (--length) >= 0;) {
            params[perm[length] - 1] = test.m_in[length].floatValue();
          }

          Assert.assertEquals(test.m_out.floatValue(),
              function.computeAsFloat(params), 1e-10f);
        } while (this.isCommutative() && it.hasNext());
      }
    }
  }

  /**
   * Test the {@code long}-based computation based on the test cases
   */
  @Test(timeout = 3600000)
  public void testTestCasesAllLong() {
    MathematicalFunction function;
    long[] params;
    int length;
    int[] perm;
    Iterator<int[]> it;

    function = this.getFunction();
    Assert.assertNotNull(function);
    for (final TestCase test : this.getTestCases()) {
      Assert.assertNotNull(test);
      length = test.m_in.length;
      Assert.assertTrue(length >= function.getMinArity());
      Assert.assertTrue(length <= function.getMaxArity());

      if ((test.m_choices & TestCase.ALL_AS_LONG) != 0) {
        params = new long[test.m_in.length];

        it = new PermutationIterator(test.m_in.length);
        do {
          perm = it.next();
          for (length = perm.length; (--length) >= 0;) {
            params[perm[length] - 1] = test.m_in[length].longValue();
          }
          Assert.assertEquals(test.m_out.longValue(),
              function.computeAsLong(params));
        } while (this.isCommutative() && it.hasNext());
      }
    }
  }

  /**
   * Test the {@code int}-based computation based on the test cases
   */
  @Test(timeout = 3600000)
  public void testTestCasesAllInt() {
    MathematicalFunction function;
    int[] params;
    int length;
    int[] perm;
    Iterator<int[]> it;

    function = this.getFunction();
    Assert.assertNotNull(function);
    for (final TestCase test : this.getTestCases()) {
      Assert.assertNotNull(test);
      length = test.m_in.length;
      Assert.assertTrue(length >= function.getMinArity());
      Assert.assertTrue(length <= function.getMaxArity());

      if ((test.m_choices & TestCase.ALL_AS_INT) != 0) {
        params = new int[test.m_in.length];

        it = new PermutationIterator(test.m_in.length);
        do {
          perm = it.next();
          for (length = perm.length; (--length) >= 0;) {
            params[perm[length] - 1] = test.m_in[length].intValue();
          }

          Assert.assertEquals(test.m_out.intValue(),
              function.computeAsInt(params));
        } while (this.isCommutative() && it.hasNext());
      }
    }
  }

  /**
   * Test the {@code short}-based computation based on the test cases
   */
  @Test(timeout = 3600000)
  public void testTestCasesAllShort() {
    MathematicalFunction function;
    short[] params;
    int length;
    int[] perm;
    Iterator<int[]> it;

    function = this.getFunction();
    Assert.assertNotNull(function);
    for (final TestCase test : this.getTestCases()) {
      Assert.assertNotNull(test);
      length = test.m_in.length;
      Assert.assertTrue(length >= function.getMinArity());
      Assert.assertTrue(length <= function.getMaxArity());

      if ((test.m_choices & TestCase.ALL_AS_SHORT) != 0) {
        params = new short[test.m_in.length];
        it = new PermutationIterator(test.m_in.length);
        do {
          perm = it.next();
          for (length = perm.length; (--length) >= 0;) {
            params[perm[length] - 1] = test.m_in[length].shortValue();
          }

          Assert.assertEquals(test.m_out.shortValue(),
              function.computeAsShort(params));
        } while (this.isCommutative() && it.hasNext());
      }
    }
  }

  /**
   * Test the {@code byte}-based computation based on the test cases
   */
  @Test(timeout = 3600000)
  public void testTestCasesAllByte() {
    MathematicalFunction function;
    byte[] params;
    int length;
    int[] perm;
    Iterator<int[]> it;

    function = this.getFunction();
    Assert.assertNotNull(function);
    for (final TestCase test : this.getTestCases()) {
      Assert.assertNotNull(test);
      length = test.m_in.length;
      Assert.assertTrue(length >= function.getMinArity());
      Assert.assertTrue(length <= function.getMaxArity());

      if ((test.m_choices & TestCase.ALL_AS_BYTE) != 0) {
        params = new byte[test.m_in.length];
        it = new PermutationIterator(test.m_in.length);
        do {
          perm = it.next();
          for (length = perm.length; (--length) >= 0;) {
            params[perm[length] - 1] = test.m_in[length].byteValue();
          }

          Assert.assertEquals(test.m_out.byteValue(),
              function.computeAsByte(params));
        } while (this.isCommutative() && it.hasNext());
      }
    }
  }

  /**
   * Test the {@code long}-based computation based on the test cases with
   * {@code double}-based result
   */
  @Test(timeout = 3600000)
  public void testTestCasesInLongOutDouble() {
    MathematicalFunction function;
    long[] params;
    int length;
    int[] perm;
    Iterator<int[]> it;

    function = this.getFunction();
    Assert.assertNotNull(function);
    for (final TestCase test : this.getTestCases()) {
      Assert.assertNotNull(test);
      length = test.m_in.length;
      Assert.assertTrue(length >= function.getMinArity());
      Assert.assertTrue(length <= function.getMaxArity());

      if ((test.m_choices & TestCase.IN_LONG_OUT_DOUBLE) != 0) {
        params = new long[test.m_in.length];
        it = new PermutationIterator(test.m_in.length);
        do {
          perm = it.next();
          for (length = perm.length; (--length) >= 0;) {
            params[perm[length] - 1] = test.m_in[length].longValue();
          }

          Assert.assertEquals(test.m_out.doubleValue(),
              function.computeAsDouble(params), 1e-15d);
        } while (this.isCommutative() && it.hasNext());
      }
    }
  }

  /**
   * Test the {@code int}-based computation based on the test cases with
   * {@code double}-based result
   */
  @Test(timeout = 3600000)
  public void testTestCasesInIntOutDouble() {
    MathematicalFunction function;
    int[] params;
    int length;
    int[] perm;
    Iterator<int[]> it;

    function = this.getFunction();
    Assert.assertNotNull(function);
    for (final TestCase test : this.getTestCases()) {
      Assert.assertNotNull(test);
      length = test.m_in.length;
      Assert.assertTrue(length >= function.getMinArity());
      Assert.assertTrue(length <= function.getMaxArity());

      if ((test.m_choices & TestCase.IN_INT_OUT_DOUBLE) != 0) {
        params = new int[test.m_in.length];
        it = new PermutationIterator(test.m_in.length);
        do {
          perm = it.next();
          for (length = perm.length; (--length) >= 0;) {
            params[perm[length] - 1] = test.m_in[length].intValue();
          }

          Assert.assertEquals(test.m_out.doubleValue(),
              function.computeAsDouble(params), 1e-15d);
        } while (this.isCommutative() && it.hasNext());
      }
    }
  }
}
