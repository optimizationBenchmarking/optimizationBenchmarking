package test.junit.org.optimizationBenchmarking.utils.math.statistics.ranking;

import java.util.Random;

import org.junit.Test;
import org.optimizationBenchmarking.utils.math.statistics.ranking.ENaNStrategy;
import org.optimizationBenchmarking.utils.math.statistics.ranking.ETieStrategy;
import org.optimizationBenchmarking.utils.math.statistics.ranking.RankingStrategy;

/**
 * Some basic tests for ranking.
 */
public class RankingTests {

  /** the double cases */
  public static final _DoubleCase[] CASES = {//
  //
      new _DoubleCase(//
          new RankingStrategy(ENaNStrategy.ERROR, ETieStrategy.MINIMUM),//
          new double[] { 1, 2, 3, 4, 4, 5, 6, 6, 6, 7 },//
          new double[] { 1, 2, 3, 4, 4, 6, 7, 7, 7, 10 }), //
      //
      new _DoubleCase(//
          new RankingStrategy(ENaNStrategy.ERROR,
              ETieStrategy.MINIMUM_TIGHT),//
          new double[] { 1, 2, 3, 4, 4, 5, 6, 6, 6, 7 },//
          new double[] { 1, 2, 3, 4, 4, 5, 6, 6, 6, 7 }), //
      //
      new _DoubleCase(//
          new RankingStrategy(ENaNStrategy.ERROR, ETieStrategy.MAXIMUM),//
          new double[] { 1, 2, 3, 4, 4, 5, 6, 6, 6, 7 },//
          new double[] { 1, 2, 3, 5, 5, 6, 9, 9, 9, 10 }), //
      //
      new _DoubleCase(//
          new RankingStrategy(ENaNStrategy.ERROR, ETieStrategy.AVERAGE),//
          new double[] { 1, 2, 3, 4, 4, 5, 6, 6, 6, 7 },//
          new double[] { 1, 2, 3, 4.5, 4.5, 6, 8, 8, 8, 10 }), //
      //
      new _DoubleCase(//
          new RankingStrategy(ENaNStrategy.MAXIMAL, ETieStrategy.MINIMUM),//
          new double[] { 1, 2, 3, 4, 4, 5, 6, Double.NaN, 6, 6, 7 },//
          new double[] { 1, 2, 3, 4, 4, 6, 7, 11, 7, 7, 10 }), //
      //
      new _DoubleCase(//
          new RankingStrategy(ENaNStrategy.MAXIMAL,
              ETieStrategy.MINIMUM_TIGHT),//
          new double[] { 1, 2, 3, 4, 4, 5, 6, Double.NaN, 6, 6, 7 },//
          new double[] { 1, 2, 3, 4, 4, 5, 6, 8, 6, 6, 7 }), //
      //
      new _DoubleCase(//
          new RankingStrategy(ENaNStrategy.MAXIMAL, ETieStrategy.MAXIMUM),//
          new double[] { 1, 2, 3, 4, 4, 5, 6, Double.NaN, 6, 6, 7 },//
          new double[] { 1, 2, 3, 5, 5, 6, 9, 11, 9, 9, 10 }), //
      //
      new _DoubleCase(//
          new RankingStrategy(ENaNStrategy.MAXIMAL, ETieStrategy.AVERAGE),//
          new double[] { 1, 2, 3, 4, 4, 5, 6, Double.NaN, 6, 6, 7 },//
          new double[] { 1, 2, 3, 4.5, 4.5, 6, 8, 11, 8, 8, 10 }), //
      //
      new _DoubleCase(//
          new RankingStrategy(ENaNStrategy.MINIMAL, ETieStrategy.MINIMUM),//
          new double[] { 1, 2, 3, 4, 4, 5, 6, Double.NaN, 6, 6, 7 },//
          new double[] { 2, 3, 4, 5, 5, 7, 8, 1, 8, 8, 11 }), //
      //
      new _DoubleCase(//
          new RankingStrategy(ENaNStrategy.MINIMAL,
              ETieStrategy.MINIMUM_TIGHT),//
          new double[] { 1, 2, 3, 4, 4, 5, 6, Double.NaN, 6, 6, 7 },//
          new double[] { 2, 3, 4, 5, 5, 6, 7, 1, 7, 7, 8 }), //
      //
      new _DoubleCase(//
          new RankingStrategy(ENaNStrategy.MINIMAL, ETieStrategy.MAXIMUM),//
          new double[] { 1, 2, 3, 4, 4, 5, 6, Double.NaN, 6, 6, 7 },//
          new double[] { 2, 3, 4, 6, 6, 7, 10, 1, 10, 10, 11 }), //
      //
      new _DoubleCase(//
          new RankingStrategy(ENaNStrategy.MINIMAL, ETieStrategy.AVERAGE),//
          new double[] { 1, 2, 3, 4, 4, 5, 6, Double.NaN, 6, 6, 7 },//
          new double[] { 2, 3, 4, 5.5, 5.5, 7, 9, 1, 9, 9, 11 }), //
      //
      new _DoubleCase(//
          new RankingStrategy(ENaNStrategy.NAN, ETieStrategy.MINIMUM),//
          new double[] { 1, 2, 3, 4, 4, 5, 6, Double.NaN, 6, 6, 7 },//
          new double[] { 1, 2, 3, 4, 4, 6, 7, Double.NaN, 7, 7, 10 }), //
      //
      new _DoubleCase(//
          new RankingStrategy(ENaNStrategy.NAN, ETieStrategy.MINIMUM_TIGHT),//
          new double[] { 1, 2, 3, 4, 4, 5, 6, Double.NaN, 6, 6, 7 },//
          new double[] { 1, 2, 3, 4, 4, 5, 6, Double.NaN, 6, 6, 7 }), //
      //
      new _DoubleCase(//
          new RankingStrategy(ENaNStrategy.NAN, ETieStrategy.MAXIMUM),//
          new double[] { 1, 2, 3, 4, 4, 5, 6, Double.NaN, 6, 6, 7 },//
          new double[] { 1, 2, 3, 5, 5, 6, 9, Double.NaN, 9, 9, 10 }), //
      //
      new _DoubleCase(//
          new RankingStrategy(ENaNStrategy.NAN, ETieStrategy.AVERAGE),//
          new double[] { 1, 2, 3, 4, 4, 5, 6, Double.NaN, 6, 6, 7 },//
          new double[] { 1, 2, 3, 4.5, 4.5, 6, 8, Double.NaN, 8, 8, 10 }), //
  //

  };

  /** Test the {@code double} cases */
  @Test(timeout = 3600000)
  public void testDoubleCases() {
    final Random rand;
    rand = new Random();
    for (final _DoubleCase cas : RankingTests.CASES) {
      cas._assertDoubles(rand);
    }
  }

  /** Test the {@code long} cases */
  @Test(timeout = 3600000)
  public void testLongCases() {
    final Random rand;
    rand = new Random();
    for (final _DoubleCase cas : RankingTests.CASES) {
      cas._assertLongs(rand);
    }
  }

}
