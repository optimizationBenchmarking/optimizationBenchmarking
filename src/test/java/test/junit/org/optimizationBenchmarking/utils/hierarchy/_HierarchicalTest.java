package test.junit.org.optimizationBenchmarking.utils.hierarchy;

import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

import test.junit.TestBase;

/**
 * Test the hierarchy element API.
 */
@Ignore
public abstract class _HierarchicalTest extends TestBase {

  /** create */
  public _HierarchicalTest() {
    super();
  }

  /**
   * test the API multi-threaded
   * 
   * @param n
   *          the number of threads
   * @param delayMode
   *          the delay
   * @param fifo
   *          are the tests fifo?
   */
  abstract void _testMultiThreaded(final int n, final int delayMode,
      final boolean fifo);

  /** test the API single-threaded without delay fifo-style */
  @Test(timeout = 3600000)
  public void testSingleThreadDelay_FIFO() {
    this._testMultiThreaded(1, 0, true);
  }

  /** test the API single-threaded without delay fifo-style */
  @Test(timeout = 3600000)
  public void testSingleThreadRandomDelay_FIFO() {
    this._testMultiThreaded(1, 1, true);
  }

  /** test the API single-threaded without delay fifo-style */
  @Test(timeout = 3600000)
  public void testSingleThreadNoDelay_FIFO() {
    this._testMultiThreaded(1, 2, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThread_2_Delay_FIFO() {
    this._testMultiThreaded(2, 0, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThread_2_NoDelay_FIFO() {
    this._testMultiThreaded(2, 2, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThread_2_RandomDelay_FIFO() {
    this._testMultiThreaded(2, 1, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThread_3_NoDelay_FIFO() {
    this._testMultiThreaded(3, 2, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThread_3_Delay_FIFO() {
    this._testMultiThreaded(3, 0, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThread_3_RandomDelay_FIFO() {
    this._testMultiThreaded(3, 1, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThread_4_RandomDelay_FIFO() {
    this._testMultiThreaded(4, 1, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThread_5_RandomDelay_FIFO() {
    this._testMultiThreaded(5, 1, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThread_6_RandomDelay_FIFO() {
    this._testMultiThreaded(6, 1, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThread_7_RandomDelay_FIFO() {
    this._testMultiThreaded(7, 1, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThread_8_RandomDelay_FIFO() {
    this._testMultiThreaded(8, 1, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThread_9_RandomDelay_FIFO() {
    this._testMultiThreaded(9, 1, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThread_10_RandomDelay_FIFO() {
    this._testMultiThreaded(10, 1, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThread_20_RandomDelay_FIFO() {
    this._testMultiThreaded(20, 1, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThread_30_RandomDelay_FIFO() {
    this._testMultiThreaded(30, 1, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThread_40_RandomDelay_FIFO() {
    this._testMultiThreaded(40, 1, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_1_100_RandomDelay_FIFO() {
    this._testMultiThreaded((new Random().nextInt(100) + 1), 1, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_100_200_RandomDelay_FIFO() {
    this._testMultiThreaded((new Random().nextInt(100) + 100), 1, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_1_20_RandomDelay_FIFO() {
    this._testMultiThreaded((new Random().nextInt(20) + 1), 1, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_1_50_RandomDelay_FIFO() {
    this._testMultiThreaded((new Random().nextInt(50) + 1), 1, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_20_40_RandomDelay_FIFO() {
    this._testMultiThreaded((new Random().nextInt(20) + 20), 1, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_50_100_RandomDelay_FIFO() {
    this._testMultiThreaded((new Random().nextInt(50) + 50), 1, true);
  }

  /** test the API multi-threaded fifo-style */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_200_400_RandomDelay_FIFO() {
    this._testMultiThreaded((new Random().nextInt(200) + 200), 1, true);
  }

  /** test the API single-threaded without delay default-style */
  @Test(timeout = 3600000)
  public void testSingleThreadDelay_Default() {
    this._testMultiThreaded(1, 0, false);
  }

  /** test the API single-threaded without delay default-style */
  @Test(timeout = 3600000)
  public void testSingleThreadRandomDelay_Default() {
    this._testMultiThreaded(1, 1, false);
  }

  /** test the API single-threaded without delay default-style */
  @Test(timeout = 3600000)
  public void testSingleThreadNoDelay_Default() {
    this._testMultiThreaded(1, 2, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThread_2_Delay_Default() {
    this._testMultiThreaded(2, 0, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThread_2_NoDelay_Default() {
    this._testMultiThreaded(2, 2, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThread_2_RandomDelay_Default() {
    this._testMultiThreaded(2, 1, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThread_3_NoDelay_Default() {
    this._testMultiThreaded(3, 2, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThread_3_Delay_Default() {
    this._testMultiThreaded(3, 0, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThread_3_RandomDelay_Default() {
    this._testMultiThreaded(3, 1, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThread_4_RandomDelay_Default() {
    this._testMultiThreaded(4, 1, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThread_5_RandomDelay_Default() {
    this._testMultiThreaded(5, 1, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThread_6_RandomDelay_Default() {
    this._testMultiThreaded(6, 1, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThread_7_RandomDelay_Default() {
    this._testMultiThreaded(7, 1, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThread_8_RandomDelay_Default() {
    this._testMultiThreaded(8, 1, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThread_9_RandomDelay_Default() {
    this._testMultiThreaded(9, 1, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThread_10_RandomDelay_Default() {
    this._testMultiThreaded(10, 1, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThread_20_RandomDelay_Default() {
    this._testMultiThreaded(20, 1, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThread_30_RandomDelay_Default() {
    this._testMultiThreaded(30, 1, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThread_40_RandomDelay_Default() {
    this._testMultiThreaded(40, 1, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_1_100_RandomDelay_Default() {
    this._testMultiThreaded((new Random().nextInt(100) + 1), 1, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_100_200_RandomDelay_Default() {
    this._testMultiThreaded((new Random().nextInt(100) + 100), 1, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_1_20_RandomDelay_Default() {
    this._testMultiThreaded((new Random().nextInt(20) + 1), 1, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_1_50_RandomDelay_Default() {
    this._testMultiThreaded((new Random().nextInt(50) + 1), 1, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_20_40_RandomDelay_Default() {
    this._testMultiThreaded((new Random().nextInt(20) + 20), 1, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_50_100_RandomDelay_Default() {
    this._testMultiThreaded((new Random().nextInt(50) + 50), 1, false);
  }

  /** test the API multi-threaded default-style */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_200_400_RandomDelay_Default() {
    this._testMultiThreaded((new Random().nextInt(200) + 200), 1, false);
  }
}
