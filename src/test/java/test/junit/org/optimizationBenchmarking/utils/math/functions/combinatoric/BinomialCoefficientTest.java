package test.junit.org.optimizationBenchmarking.utils.math.functions.combinatoric;

import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.combinatoric.BinomialCoefficient;

import test.junit.org.optimizationBenchmarking.utils.math.functions.MathematicalFunctionTest;
import test.junit.org.optimizationBenchmarking.utils.math.functions.TestCase;

/**
 * The binomial coefficient test.
 */
public class BinomialCoefficientTest extends MathematicalFunctionTest {

  /** the test cases */
  private static final TestCase[] TEST_CASES = { //
    new TestCase(1, 0, 0),//

    new TestCase(1, 1, 0),//
    new TestCase(1, 1, 1),//

    new TestCase(1, 2, 0),//
    new TestCase(2, 2, 1),//

    new TestCase(1, 3, 0),//
    new TestCase(3, 3, 1),//
    new TestCase(3, 3, 2),//
    new TestCase(1, 3, 3),//

    new TestCase(1, 4, 0),//
    new TestCase(4, 4, 1),//
    new TestCase(6, 4, 2),//
    new TestCase(4, 4, 3),//
    new TestCase(1, 4, 4),//

    new TestCase(1, 5, 0),//
    new TestCase(5, 5, 1),//
    new TestCase(10, 5, 2),//
    new TestCase(10, 5, 3),//
    new TestCase(5, 5, 4),//
    new TestCase(1, 5, 5),//

    new TestCase(1, 6, 0),//
    new TestCase(6, 6, 1),//
    new TestCase(15, 6, 2),//
    new TestCase(20, 6, 3),//
    new TestCase(15, 6, 4),//
    new TestCase(6, 6, 5),//
    new TestCase(1, 6, 6),//

    new TestCase(1, 7, 0),//
    new TestCase(7, 7, 1),//
    new TestCase(21, 7, 2),//
    new TestCase(35, 7, 3),//
    new TestCase(35, 7, 4),//
    new TestCase(21, 7, 5),//
    new TestCase(7, 7, 6),//
    new TestCase(1, 7, 7),//

    new TestCase(1, 8, 0),//
    new TestCase(8, 8, 1),//
    new TestCase(28, 8, 2),//
    new TestCase(56, 8, 3),//
    new TestCase(70, 8, 4),//
    new TestCase(56, 8, 5),//
    new TestCase(28, 8, 6),//
    new TestCase(8, 8, 7),//
    new TestCase(1, 8, 8),//

    new TestCase(1, 9, 0),//
    new TestCase(9, 9, 1),//
    new TestCase(36, 9, 2),//
    new TestCase(84, 9, 3),//
    new TestCase(126, 9, 4),//
    new TestCase(126, 9, 5),//
    new TestCase(84, 9, 6),//
    new TestCase(36, 9, 7),//
    new TestCase(9, 9, 8),//
    new TestCase(1, 9, 9),//

    new TestCase(1, 10, 0),//
    new TestCase(10, 10, 1),//
    new TestCase(45, 10, 2),//
    new TestCase(120, 10, 3),//
    new TestCase(210, 10, 4),//
    new TestCase(252, 10, 5),//
    new TestCase(210, 10, 6),//
    new TestCase(120, 10, 7),//
    new TestCase(45, 10, 8),//
    new TestCase(10, 10, 9),//
    new TestCase(1, 10, 10),//

    new TestCase(1, 11, 0),//
    new TestCase(11, 11, 1),//
    new TestCase(55, 11, 2),//
    new TestCase(165, 11, 3),//
    new TestCase(330, 11, 4),//
    new TestCase(462, 11, 5),//
    new TestCase(462, 11, 6),//
    new TestCase(330, 11, 7),//
    new TestCase(165, 11, 8),//
    new TestCase(55, 11, 9),//
    new TestCase(11, 11, 10),//
    new TestCase(1, 11, 11),//

    new TestCase(1, 12, 0),//
    new TestCase(12, 12, 1),//
    new TestCase(66, 12, 2),//
    new TestCase(220, 12, 3),//
    new TestCase(495, 12, 4),//
    new TestCase(792, 12, 5),//
    new TestCase(924, 12, 6),//
    new TestCase(792, 12, 7),//
    new TestCase(495, 12, 8),//
    new TestCase(220, 12, 9),//
    new TestCase(66, 12, 10),//
    new TestCase(12, 12, 11),//
    new TestCase(1, 12, 12),//

    new TestCase(1, 13, 0),//
    new TestCase(13, 13, 1),//
    new TestCase(78, 13, 2),//
    new TestCase(286, 13, 3),//
    new TestCase(715, 13, 4),//
    new TestCase(1287, 13, 5),//
    new TestCase(1716, 13, 6),//
    new TestCase(1716, 13, 7),//
    new TestCase(1287, 13, 8),//
    new TestCase(715, 13, 9),//
    new TestCase(286, 13, 10),//
    new TestCase(78, 13, 11),//
    new TestCase(13, 13, 12),//
    new TestCase(1, 13, 13),//

    new TestCase(1, 14, 0),//
    new TestCase(14, 14, 1),//
    new TestCase(91, 14, 2),//
    new TestCase(364, 14, 3),//
    new TestCase(1001, 14, 4),//
    new TestCase(2002, 14, 5),//
    new TestCase(3003, 14, 6),//
    new TestCase(3432, 14, 7),//
    new TestCase(3003, 14, 8),//
    new TestCase(2002, 14, 9),//
    new TestCase(1001, 14, 10),//
    new TestCase(364, 14, 11),//
    new TestCase(91, 14, 12),//
    new TestCase(14, 14, 13),//
    new TestCase(1, 14, 14),//
  };

  /** create */
  public BinomialCoefficientTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public MathematicalFunction getFunction() {
    return BinomialCoefficient.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isCommutative() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public TestCase[] getTestCases() {
    return BinomialCoefficientTest.TEST_CASES;
  }

}
