package test.junit.org.optimizationBenchmarking.utils.math.functions.arithmetic;

import org.junit.Ignore;
import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.SaturatingSub;

import test.junit.org.optimizationBenchmarking.utils.math.functions.MathematicalFunctionTest;
import test.junit.org.optimizationBenchmarking.utils.math.functions.TestCase;

/** The test of the saturating subtract function */
public class SaturatingSubTest extends MathematicalFunctionTest {

  /** the test cases */
  private static final TestCase[] TEST_CASES = { //
  new TestCase(1, 1, 0),//
      new TestCase(-1, 0, 1),//
      new TestCase(0, 2, 2),//
      new TestCase(-1, 2, 3),//
      new TestCase(5, 4, -1),//
      new TestCase(6, 4, -2),//
      new TestCase(-7, -4, 3),//
      //
      new TestCase(Long.MAX_VALUE, Long.MAX_VALUE, 0L),//
      new TestCase(Long.MAX_VALUE, Long.MAX_VALUE, -1L),//
      new TestCase(Long.MAX_VALUE, Long.MAX_VALUE, -2L),//
      new TestCase(Long.MAX_VALUE, Long.MAX_VALUE, -Long.MAX_VALUE),//
      new TestCase(Long.MAX_VALUE, Long.MAX_VALUE, Long.MIN_VALUE),//
      new TestCase(0L, Long.MAX_VALUE, Long.MAX_VALUE),//
      new TestCase(1L, Long.MAX_VALUE, (Long.MAX_VALUE - 1L)),//
      //
      new TestCase(Long.MIN_VALUE, Long.MIN_VALUE, 0L),//
      new TestCase(Long.MIN_VALUE, Long.MIN_VALUE, 1L),//
      new TestCase(Long.MIN_VALUE, Long.MIN_VALUE, 2L),//
      new TestCase(Long.MIN_VALUE, Long.MIN_VALUE, Long.MAX_VALUE),//
      new TestCase(0, Long.MIN_VALUE, Long.MIN_VALUE),//
      new TestCase(-1L, Long.MIN_VALUE, (-Long.MAX_VALUE)),//
  };

  /** create */
  public SaturatingSubTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public MathematicalFunction getFunction() {
    return SaturatingSub.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isCommutative() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public TestCase[] getTestCases() {
    return SaturatingSubTest.TEST_CASES;
  }

  /** {@inheritDoc} */
  @Override
  @Ignore
  public void testTestCasesAllDouble() {
    // ignored
  }

  /** {@inheritDoc} */
  @Override
  @Ignore
  public void testTestCasesAllFloat() {
    // ignored
  }
}
