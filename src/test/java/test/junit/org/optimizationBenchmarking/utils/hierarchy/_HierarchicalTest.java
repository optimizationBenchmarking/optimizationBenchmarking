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

  /** test the API single-threaded without delay */
  @Test(timeout = 3600000)
  public void testSingleThreadDelay() {
    this._testMultiThreaded(1, 0);
  }

  /** test the API single-threaded without delay */
  @Test(timeout = 3600000)
  public void testSingleThreadRandomDelay() {
    this._testMultiThreaded(1, 1);
  }

  /** test the API single-threaded without delay */
  @Test(timeout = 3600000)
  public void testSingleThreadNoDelay() {
    this._testMultiThreaded(1, 2);
  }

  /**
   * test the API multi-threaded
   * 
   * @param n
   *          the number of threads
   * @param delayMode
   *          the delay
   */
  abstract void _testMultiThreaded(final int n, final int delayMode);

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThread_2_Delay() {
    this._testMultiThreaded(2, 0);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThread_2_NoDelay() {
    this._testMultiThreaded(2, 2);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThread_2_RandomDelay() {
    this._testMultiThreaded(2, 1);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThread_3_NoDelay() {
    this._testMultiThreaded(3, 2);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThread_3_Delay() {
    this._testMultiThreaded(3, 0);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThread_3_RandomDelay() {
    this._testMultiThreaded(3, 1);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThread_4_RandomDelay() {
    this._testMultiThreaded(4, 1);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThread_5_RandomDelay() {
    this._testMultiThreaded(5, 1);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThread_6_RandomDelay() {
    this._testMultiThreaded(6, 1);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThread_7_RandomDelay() {
    this._testMultiThreaded(7, 1);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThread_8_RandomDelay() {
    this._testMultiThreaded(8, 1);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThread_9_RandomDelay() {
    this._testMultiThreaded(9, 1);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThread_10_RandomDelay() {
    this._testMultiThreaded(10, 1);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThread_20_RandomDelay() {
    this._testMultiThreaded(20, 1);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThread_30_RandomDelay() {
    this._testMultiThreaded(30, 1);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThread_40_RandomDelay() {
    this._testMultiThreaded(40, 1);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_1_100_RandomDelay() {
    this._testMultiThreaded((new Random().nextInt(100) + 1), 1);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_100_200_RandomDelay() {
    this._testMultiThreaded((new Random().nextInt(100) + 100), 1);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_1_20_RandomDelay() {
    this._testMultiThreaded((new Random().nextInt(20) + 1), 1);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_1_50_RandomDelay() {
    this._testMultiThreaded((new Random().nextInt(50) + 1), 1);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_20_40_RandomDelay() {
    this._testMultiThreaded((new Random().nextInt(20) + 20), 1);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_50_100_RandomDelay() {
    this._testMultiThreaded((new Random().nextInt(50) + 50), 1);
  }

  /** test the API multi-threaded */
  @Test(timeout = 3600000)
  public void testMultiThreadRandom_200_400_RandomDelay() {
    this._testMultiThreaded((new Random().nextInt(200) + 200), 1);
  }

}
