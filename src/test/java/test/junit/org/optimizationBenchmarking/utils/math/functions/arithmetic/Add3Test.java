package test.junit.org.optimizationBenchmarking.utils.math.functions.arithmetic;

import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add3;

import test.junit.org.optimizationBenchmarking.utils.math.functions.MathematicalFunctionTest;
import test.junit.org.optimizationBenchmarking.utils.math.functions.TestCase;

/** The test of the add 3 function */
public final class Add3Test extends MathematicalFunctionTest {

  /** the test cases */
  private static final TestCase[] TEST_CASES = { //
      //
      new TestCase(1, 1, 0, 0), //
      //
      new TestCase(6, 1, 2, 3), //
      //
      new TestCase(Long.MAX_VALUE, 1, -1L, Long.MAX_VALUE), //
      //
      new TestCase(Long.MIN_VALUE, 1, -1L, Long.MIN_VALUE), //
      //
      new TestCase(Long.MIN_VALUE, Long.MIN_VALUE, (Long.MIN_VALUE + 1L),
          Long.MAX_VALUE), //
      //
      new TestCase(Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE,
          (-Long.MAX_VALUE)), //
      //
      new TestCase(1, (NumericalTypes.MAX_DOUBLE_LONG + 1L),
          -(NumericalTypes.MAX_DOUBLE_LONG + 1L), 1), //
      //
      new TestCase(123, (NumericalTypes.MAX_DOUBLE_LONG + 23L),
          -(NumericalTypes.MAX_DOUBLE_LONG + 20L), 120), //
      //
      new TestCase(1, -Double.MAX_VALUE, 1, Double.MAX_VALUE),//
  };

  /** create */
  public Add3Test() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final MathematicalFunction getFunction() {
    return Add3.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean isCommutative() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final TestCase[] getTestCases() {
    return Add3Test.TEST_CASES;
  }
}
