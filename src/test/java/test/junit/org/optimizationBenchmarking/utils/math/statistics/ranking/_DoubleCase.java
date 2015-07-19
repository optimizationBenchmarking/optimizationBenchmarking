package test.junit.org.optimizationBenchmarking.utils.math.statistics.ranking;

import java.util.Random;

import org.junit.Assert;
import org.optimizationBenchmarking.utils.collections.ArrayUtils;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.statistics.ranking.RankingStrategy;

/** A ranking use case based on {@code double} values */
final class _DoubleCase {

  /** the strategy */
  final RankingStrategy m_strategy;

  /** the input */
  final double[] m_input;

  /** the expected output */
  final double[] m_output;

  /**
   * the input data as {@code long}s, or {@code null} if it could not be
   * converted
   */
  final long[] m_inputLong;

  /**
   * Create a case where we rank {@code double}s.
   *
   * @param strategy
   *          the strategy
   * @param in
   *          the input data
   * @param out
   *          the expected output data
   */
  _DoubleCase(final RankingStrategy strategy, final double[] in,
      final double[] out) {
    super();

    Assert.assertNotNull(strategy);
    Assert.assertNotNull(in);
    Assert.assertNotNull(out);
    Assert.assertEquals(in.length, out.length);

    this.m_strategy = strategy;
    this.m_input = in;
    this.m_output = out;

    asLongs: {
      for (final double d : in) {
        if (!(NumericalTypes.isLong(d))) {
          break asLongs;
        }
      }
      this.m_inputLong = ArrayUtils.doublesToLongs(in);
      return;
    }
    this.m_inputLong = null;
  }

  /**
   * Assert that the {@code double}s are ranked correctly
   *
   * @param rand
   *          a random number generator
   */
  final void _assertDoubles(final Random rand) {
    final double[] out, input, expected;
    int i, j, k;
    double t;

    out = new double[this.m_input.length];
    input = this.m_input.clone();
    expected = this.m_output.clone();

    for (i = 0; i < (100 * input.length); i++) {
      this.m_strategy.rank(input, out);
      Assert.assertArrayEquals(expected, out, Double.MIN_VALUE);

      if (input.length <= 1) {
        break;
      }

      j = rand.nextInt(input.length);
      do {
        k = rand.nextInt(input.length);
      } while (k == j);

      t = input[j];
      input[j] = input[k];
      input[k] = t;

      t = expected[j];
      expected[j] = expected[k];
      expected[k] = t;
    }
  }

  /**
   * Assert that the {@code longs}s are ranked correctly
   *
   * @param rand
   *          a random number generator
   */
  final void _assertLongs(final Random rand) {
    final double[] out, expected;
    final long[] input;
    int i, j, k;
    double t;
    long tl;

    if (this.m_inputLong == null) {
      return;
    }

    out = new double[this.m_input.length];
    input = this.m_inputLong.clone();
    expected = this.m_output.clone();

    for (i = 0; i < (100 * input.length); i++) {
      this.m_strategy.rank(input, out);
      Assert.assertArrayEquals(expected, out, Double.MIN_VALUE);

      if (input.length <= 1) {
        break;
      }

      j = rand.nextInt(input.length);
      do {
        k = rand.nextInt(input.length);
      } while (k == j);

      tl = input[j];
      input[j] = input[k];
      input[k] = tl;

      t = expected[j];
      expected[j] = expected[k];
      expected[k] = t;
    }
  }
}
