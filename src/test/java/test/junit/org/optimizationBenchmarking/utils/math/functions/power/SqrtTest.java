package test.junit.org.optimizationBenchmarking.utils.math.functions.power;

import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.power.Sqrt;

import test.junit.org.optimizationBenchmarking.utils.math.functions.MathematicalFunctionTest;
import test.junit.org.optimizationBenchmarking.utils.math.functions.TestCase;

/** The test of the sqrt function */
public final class SqrtTest extends MathematicalFunctionTest {

  /** the test cases */
  private static final TestCase[] TEST_CASES = { //
    new TestCase(0, 0),//
    new TestCase(1, 1),//
    new TestCase(2, 4),//
    new TestCase(Math.sqrt(5), 5),//
    new TestCase(Math.sqrt(7), 7),//
    new TestCase(3, 9),//
    new TestCase(4, 16),//
    new TestCase(5, 25),//
    new TestCase(6, 36),//
    new TestCase(7, 49),//
    new TestCase(8, 64),//
    new TestCase(9, 81),//
    new TestCase(10, 100),//
    new TestCase(Math.sqrt(1000), 1000),
    new TestCase(3037000497L, (3037000497L * 3037000497L)),//
    new TestCase(3037000498L, (3037000498L * 3037000498L)),//
    new TestCase(3037000499L, (3037000499L * 3037000499L)), //

    // This test case does not work because the conversion from long to
    // double has a loss of precision
    // new TestCase(Math.sqrt((3037000499L * 3037000499L) - 1L),//
    // ((3037000499L * 3037000499L) - 1L)) //

    // Thus, we now use test cases which ensures that conversion without
    // loss of precision is possible.
    new TestCase(((long) (Math.sqrt(9007199254740992L))),
        (((long) (Math.sqrt(9007199254740992L))) * //
            ((long) (Math.sqrt(9007199254740992L))))),//
            new TestCase((((long) (Math.sqrt(9007199254740992L))) - 1L),
                ((((long) (Math.sqrt(9007199254740992L))) - 1L) * //
                    (((long) (Math.sqrt(9007199254740992L))) - 1L))),//
                    new TestCase((((long) (Math.sqrt(9007199254740992L))) - 2L),
                        ((((long) (Math.sqrt(9007199254740992L))) - 2L) * //
                            (((long) (Math.sqrt(9007199254740992L))) - 2L))),//
                            new TestCase((((long) (Math.sqrt(9007199254740992L))) - 3L),
                                ((((long) (Math.sqrt(9007199254740992L))) - 3L) * //
                                    (((long) (Math.sqrt(9007199254740992L))) - 3L))),//
  };

  /** create */
  public SqrtTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final MathematicalFunction getFunction() {
    return Sqrt.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean isCommutative() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final TestCase[] getTestCases() {
    return SqrtTest.TEST_CASES;
  }
}
