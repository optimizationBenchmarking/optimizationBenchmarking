package test.junit.org.optimizationBenchmarking.utils.math.functions.arithmetic;

import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.SaturatingAdd;

import test.junit.org.optimizationBenchmarking.utils.math.functions.MathematicalFunctionTest;
import test.junit.org.optimizationBenchmarking.utils.math.functions.TestCase;

/** The test of the saturating add function */
public final class SaturatingAddTest extends MathematicalFunctionTest {

  /** the test cases */
  private static final TestCase[] TEST_CASES = { //
  new TestCase(1, 1, 0),//
      new TestCase(1, 0, 1),//
      new TestCase(4, 2, 2),//
      new TestCase(5, 2, 3),//
      new TestCase(3, 4, -1),//
      new TestCase(2, 4, -2),//
      new TestCase(-1, -4, 3),//
      //
      new TestCase(Long.MAX_VALUE, Long.MAX_VALUE, 0L),//
      new TestCase(Long.MAX_VALUE, Long.MAX_VALUE, 1L),//
      new TestCase(Long.MAX_VALUE, Long.MAX_VALUE, 2L),//
      new TestCase(Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE),//
      new TestCase(Long.MAX_VALUE, Long.MAX_VALUE, (Long.MAX_VALUE - 1L)),//
      //
      new TestCase(Long.MIN_VALUE, Long.MIN_VALUE, 0L),//
      new TestCase(Long.MIN_VALUE, Long.MIN_VALUE, -1L),//
      new TestCase(Long.MIN_VALUE, Long.MIN_VALUE, -2L),//
      new TestCase(Long.MIN_VALUE, Long.MIN_VALUE, Long.MIN_VALUE),//
      new TestCase(Long.MIN_VALUE, Long.MIN_VALUE, (Long.MIN_VALUE + 1L)),//
      //
      new TestCase((Long.MAX_VALUE - 1L), Long.MAX_VALUE, -1L),//
      new TestCase((Long.MAX_VALUE - 2L), Long.MAX_VALUE, -2L),//
      new TestCase((Long.MIN_VALUE + 1L), Long.MIN_VALUE, 1L),//
      new TestCase((Long.MIN_VALUE + 2L), Long.MIN_VALUE, 2L),//
      //
      new TestCase(-1L, Long.MAX_VALUE, Long.MIN_VALUE),//
      new TestCase(0L, Long.MAX_VALUE, (Long.MIN_VALUE + 1L)),//
      new TestCase(1L, Long.MAX_VALUE, (Long.MIN_VALUE + 2L)),//
      new TestCase(-2L, (Long.MAX_VALUE - 1L), Long.MIN_VALUE),//
      new TestCase(-3L, (Long.MAX_VALUE - 2L), Long.MIN_VALUE),//
  };

  /** create */
  public SaturatingAddTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final MathematicalFunction getFunction() {
    return SaturatingAdd.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean isCommutative() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final TestCase[] getTestCases() {
    return SaturatingAddTest.TEST_CASES;
  }
}
