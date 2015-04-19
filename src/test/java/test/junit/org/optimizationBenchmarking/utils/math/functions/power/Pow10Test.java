package test.junit.org.optimizationBenchmarking.utils.math.functions.power;

import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.power.Pow10;

import test.junit.org.optimizationBenchmarking.utils.math.functions.MathematicalFunctionTest;
import test.junit.org.optimizationBenchmarking.utils.math.functions.TestCase;

/** The test of the pow10 function */
public final class Pow10Test extends MathematicalFunctionTest {

  /** the test cases */
  private static final TestCase[] TEST_CASES = { //
  new TestCase(1, 0),//
      new TestCase(10, 1),//
      new TestCase(100, 2),//
      new TestCase(1_000, 3),//
      new TestCase(10_000, 4),//
      new TestCase(100_000, 5),//
      new TestCase(1_000_000, 6),//
      new TestCase(10_000_000, 7),//
      new TestCase(100_000_000, 8),//
      new TestCase(1_000_000_000, 9),//
      new TestCase(10_000_000_000L, 10L),//
      new TestCase(100_000_000_000L, 11L),//
      new TestCase(1_000_000_000_000L, 12L),//
      new TestCase(10_000_000_000_000L, 13L),//
      new TestCase(100_000_000_000_000L, 14L),//
      new TestCase(1_000_000_000_000_000L, 15L),//
      new TestCase(10_000_000_000_000_000L, 16L),//
      new TestCase(100_000_000_000_000_000L, 17L),//
      new TestCase(1_000_000_000_000_000_000L, 18L),//

      new TestCase((1d / 10), -1),//
      new TestCase((1d / 100), -2),//
      new TestCase((1d / 1_000), -3),//
      new TestCase((1d / 10_000), -4),//
      new TestCase((1d / 100_000), -5),//
      new TestCase((1d / 1_000_000), -6),//
      new TestCase((1d / 10_000_000), -7),//
      new TestCase((1d / 100_000_000), -8),//
      new TestCase((1d / 1_000_000_000), -9),//
      new TestCase((1d / 10_000_000_000L), -10),//
      new TestCase((1d / 100_000_000_000L), -11),//
      new TestCase((1d / 1_000_000_000_000L), -12),//
      new TestCase((1d / 10_000_000_000_000L), -13),//
      new TestCase((1d / 100_000_000_000_000L), -14),//
      new TestCase((1d / 1_000_000_000_000_000L), -15),//
      new TestCase((1d / 10_000_000_000_000_000L), -16),//
      new TestCase((1d / 100_000_000_000_000_000L), -17),//
      new TestCase((1d / 1_000_000_000_000_000_000L), -18),//

      new TestCase(Math.pow(10, Math.PI), Math.PI),//
  };

  /** create */
  public Pow10Test() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final MathematicalFunction getFunction() {
    return Pow10.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean isCommutative() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final TestCase[] getTestCases() {
    return Pow10Test.TEST_CASES;
  }
}
