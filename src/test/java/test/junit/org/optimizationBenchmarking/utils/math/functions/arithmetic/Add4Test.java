package test.junit.org.optimizationBenchmarking.utils.math.functions.arithmetic;

import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add4;

import test.junit.org.optimizationBenchmarking.utils.math.functions.MathematicalFunctionTest;
import test.junit.org.optimizationBenchmarking.utils.math.functions.TestCase;

/** The test of the add 4 function */
public final class Add4Test extends MathematicalFunctionTest {

  /** the test cases */
  private static final TestCase[] TEST_CASES = { //
      new TestCase(1, 1, 0, 0, 0), //
      new TestCase(10, 1, 2, 3, 4), //
      new TestCase(Long.MAX_VALUE, 1, -1L, Long.MAX_VALUE, 0L), //
      new TestCase(Long.MIN_VALUE, 1, -1L, Long.MIN_VALUE, 0L), //
      new TestCase(Long.MIN_VALUE, Long.MIN_VALUE, (Long.MIN_VALUE + 1L),
          Long.MAX_VALUE, 0L), //
      new TestCase(Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE,
          (-Long.MAX_VALUE), 0L),//
  };

  /** create */
  public Add4Test() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final MathematicalFunction getFunction() {
    return Add4.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean isCommutative() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final TestCase[] getTestCases() {
    return Add4Test.TEST_CASES;
  }
}
