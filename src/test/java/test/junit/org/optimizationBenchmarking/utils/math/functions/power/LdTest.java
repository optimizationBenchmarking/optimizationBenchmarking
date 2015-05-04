package test.junit.org.optimizationBenchmarking.utils.math.functions.power;

import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.power.Ld;

import test.junit.org.optimizationBenchmarking.utils.math.functions.MathematicalFunctionTest;
import test.junit.org.optimizationBenchmarking.utils.math.functions.TestCase;

/** The test of the ld function */
public final class LdTest extends MathematicalFunctionTest {

  /** the test cases */
  private static final TestCase[] TEST_CASES = { //
    new TestCase(0, 1),//
    new TestCase(1, 2),//
    new TestCase(2, 4),//
    new TestCase(3, 8),//
    new TestCase(4, 16),//
    new TestCase(5, 32),//
    new TestCase(6, 64),//
    new TestCase(7, 128),//
    new TestCase(8, 256),//
    new TestCase(9, 512),//
    new TestCase(10, 1024),//
    new TestCase(11, 2048),//
    new TestCase(12, 4096),//
    new TestCase(13, 8192),//
    new TestCase(14, 16384),//
    new TestCase(15, (1 << 15)),//
    new TestCase(16, (1 << 16)),//
    new TestCase(17, (1 << 17)),//
    new TestCase(18, (1 << 18)),//
    new TestCase(19, (1 << 19)),//
    new TestCase(20, (1 << 20)),//
    new TestCase(21, (1 << 21)),//
    new TestCase(22, (1 << 22)),//
    new TestCase(23, (1 << 23)),//
    new TestCase(24, (1 << 24)),//
    new TestCase(25, (1 << 25)),//
    new TestCase(26, (1 << 26)),//
    new TestCase(27, (1 << 27)),//
    new TestCase(28, (1 << 28)),//
    new TestCase(29, (1 << 29)),//
    new TestCase(30, (1 << 30)),//
    new TestCase(31, (1L << 31)),//
    new TestCase(32, (1L << 32)),//
    new TestCase(33, (1L << 33)),//
    new TestCase(34, (1L << 34)),//
    new TestCase(35, (1L << 35)),//
    new TestCase(36, (1L << 36)),//
    new TestCase(37, (1L << 37)),//
    new TestCase(38, (1L << 38)),//
    new TestCase(39, (1L << 39)),//
    new TestCase(40, (1L << 40)),//
    new TestCase(59, (1L << 59)),//
    new TestCase(60, (1L << 60)),//
    new TestCase(61, (1L << 61)),//
    new TestCase(62, (1L << 62)),//

    new TestCase(-1, (1d / 2)),//
    new TestCase(-2, (1d / 4)),//
    new TestCase(-3, (1d / 8)),//
    new TestCase(-4, (1d / 16)),//
    new TestCase(-5, (1d / 32)),//
    new TestCase(-29, (1d / (1 << 29))),//
    new TestCase(-30, (1d / (1 << 30))),//
    new TestCase(-31, (1d / (1L << 31))),//
    new TestCase(-32, (1d / (1L << 32))),//
    new TestCase(-33, (1d / (1L << 33))),//
  };

  /** create */
  public LdTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final MathematicalFunction getFunction() {
    return Ld.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean isCommutative() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final TestCase[] getTestCases() {
    return LdTest.TEST_CASES;
  }
}
