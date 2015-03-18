package test.junit.org.optimizationBenchmarking.utils.hierarchy;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the hierarchy element API.
 */
public class HierarchicalFSMTest extends _HierarchicalTest {

  /** create */
  public HierarchicalFSMTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final void _testMultiThreaded(final int n, final int delayMode,
      final boolean fifo) {
    _HFTTask t;
    _HFTWaitThread wt;
    final long et;
    long ct;
    final Random r;
    ForkJoinPool fjp;

    r = new Random();
    et = (System.currentTimeMillis() + 5000L);

    for (;;) {
      ct = System.currentTimeMillis();
      if (ct >= et) {
        return;
      }

      wt = new _HFTWaitThread(r.nextInt(Math.max(1, ((int) (et - ct)))));
      wt.start();
      fjp = (fifo ? new ForkJoinPool(n,
          ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true)
          : new ForkJoinPool(n));

      try (_HFTDummyHierarchicalFSM d = new _HFTDummyHierarchicalFSM(null)) {
        t = new _HFTTask(wt, d, 0, delayMode);
        fjp.execute(t);
        fjp.shutdown();
        try {
          fjp.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
          wt.join();
        } catch (final Throwable xt) {
          Assert.fail(xt.getMessage());
        }

        wt._checkFailed();
      }
    }
  }

  /** test onClosed errors */
  @SuppressWarnings("resource")
  @Test(timeout = 3600000, expected = IllegalStateException.class)
  public void testExpectedErrorOnClose() {
    _HFTDummyHierarchicalFSM a;

    a = new _HFTDummyHierarchicalFSM(null, false);
    a.close();
  }

  /** test onClosed errors */
  @SuppressWarnings({ "resource", "unused" })
  @Test(timeout = 3600000, expected = IllegalStateException.class)
  public void testExpectedErrorNestA() {
    _HFTDummyHierarchicalFSM a;

    a = new _HFTDummyHierarchicalFSM(null, false);
    new _HFTDummyHierarchicalFSM(a, true);
  }

  /** test onClosed errors */
  @SuppressWarnings({ "resource", "unused" })
  @Test(timeout = 3600000, expected = IllegalStateException.class)
  public void testExpectedErrorNestB() {
    _HFTDummyHierarchicalFSM a;

    a = new _HFTDummyHierarchicalFSM(null, true);
    new _HFTDummyHierarchicalFSM(a, true);
    a.close();
  }

  /** test onClosed errors */
  @SuppressWarnings({ "resource", "unused" })
  @Test(timeout = 3600000, expected = IllegalStateException.class)
  public void testExpectedErrorNestC() {
    _HFTDummyHierarchicalFSM a;

    a = new _HFTDummyHierarchicalFSM(null, true);
    new _HFTDummyHierarchicalFSM(a, true);
    new _HFTDummyHierarchicalFSM(a, true);
    a.close();
  }

  /** test onClosed errors */
  @SuppressWarnings({ "resource", "unused" })
  @Test(timeout = 3600000, expected = IllegalStateException.class)
  public void testExpectedErrorNestD() {
    _HFTDummyHierarchicalFSM a, b;

    a = new _HFTDummyHierarchicalFSM(null, true);
    b = new _HFTDummyHierarchicalFSM(a, true);
    new _HFTDummyHierarchicalFSM(a, true);
    b.close();
    a.close();
  }

  /** test onClosed errors */
  @SuppressWarnings({ "resource", "unused" })
  @Test(timeout = 3600000, expected = IllegalStateException.class)
  public void testExpectedErrorNestE() {
    _HFTDummyHierarchicalFSM a, b;

    a = new _HFTDummyHierarchicalFSM(null, true);
    b = new _HFTDummyHierarchicalFSM(a, true);
    new _HFTDummyHierarchicalFSM(b, true);
    b.close();
  }

  /** test onClosed errors */
  @SuppressWarnings({ "resource", "unused" })
  @Test(timeout = 3600000, expected = IllegalStateException.class)
  public void testExpectedErrorNestF() {
    _HFTDummyHierarchicalFSM a, b;

    a = new _HFTDummyHierarchicalFSM(null, true);
    b = new _HFTDummyHierarchicalFSM(a, true);
    new _HFTDummyHierarchicalFSM(b, true);
    a.close();
  }

}
