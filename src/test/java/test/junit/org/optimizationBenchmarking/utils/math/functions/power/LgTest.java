package test.junit.org.optimizationBenchmarking.utils.math.functions.power;

import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.power.Lg;

import test.junit.org.optimizationBenchmarking.utils.math.functions.MathematicalFunctionTest;
import test.junit.org.optimizationBenchmarking.utils.math.functions.TestCase;

/** The test of the lg function */
public final class LgTest extends MathematicalFunctionTest {

  /** the test cases */
  private static final TestCase[] TEST_CASES = { //
  new TestCase(0, 1),//
      new TestCase(1, 10),//
      new TestCase(2, 100),//
      new TestCase(3, 1_000),//
      new TestCase(4, 10_000),//
      new TestCase(5, 100_000),//
      new TestCase(6, 1_000_000),//
      new TestCase(7, 10_000_000),//
      new TestCase(8, 100_000_000),//
      new TestCase(9, 1_000_000_000),//
      new TestCase(10, 10_000_000_000L),//
      new TestCase(11, 100_000_000_000L),//
      new TestCase(12, 1_000_000_000_000L),//
      new TestCase(13, 10_000_000_000_000L),//
      new TestCase(14, 100_000_000_000_000L),//
      new TestCase(15, 1_000_000_000_000_000L),//
      new TestCase(16, 10_000_000_000_000_000L),//
      new TestCase(17, 100_000_000_000_000_000L),//
      new TestCase(18, 1_000_000_000_000_000_000L),//

      new TestCase(-1, (1d / 10)),//
      new TestCase(-2, (1d / 100)),//
      new TestCase(-3, (1d / 1_000)),//
      new TestCase(-4, (1d / 10_000)),//
      new TestCase(-5, (1d / 100_000)),//
      new TestCase(-6, (1d / 1_000_000)),//
      new TestCase(-7, (1d / 10_000_000)),//
      new TestCase(-8, (1d / 100_000_000)),//
      new TestCase(-9, (1d / 1_000_000_000)),//
      new TestCase(-10, (1d / 10_000_000_000L)),//
      new TestCase(-11, (1d / 100_000_000_000L)),//
      new TestCase(-12, (1d / 1_000_000_000_000L)),//
      new TestCase(-13, (1d / 10_000_000_000_000L)),//
      new TestCase(-14, (1d / 100_000_000_000_000L)),//
      new TestCase(-15, (1d / 1_000_000_000_000_000L)),//
      new TestCase(-16, (1d / 10_000_000_000_000_000L)),//
      new TestCase(-17, (1d / 100_000_000_000_000_000L)),//
      new TestCase(-18, (1d / 1_000_000_000_000_000_000L)),//

      new TestCase(Math.log10(23d), 23d),//
      new TestCase(Math.log10(100001d), 100001d), };

  /** create */
  public LgTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final MathematicalFunction getFunction() {
    return Lg.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean isCommutative() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final TestCase[] getTestCases() {
    return LgTest.TEST_CASES;
  }
}
