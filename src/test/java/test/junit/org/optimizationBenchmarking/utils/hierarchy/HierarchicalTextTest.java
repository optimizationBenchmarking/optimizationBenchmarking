package test.junit.org.optimizationBenchmarking.utils.hierarchy;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalTextOutput;

/**
 * Test the hierarchy element API.
 */
public class HierarchicalTextTest extends _HierarchicalTest {

  /** create */
  public HierarchicalTextTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final void _testMultiThreaded(final int n, final int delayMode) {
    _HTTask t;
    _HTWaitThread wt;
    final StringBuilder sb;
    final long et;
    long ct;
    final Random r;
    ForkJoinPool fjp;

    r = new Random();
    et = (System.currentTimeMillis() + 1200000);

    sb = new StringBuilder();

    for (;;) {
      sb.setLength(0);

      ct = System.currentTimeMillis();
      if (ct >= et) {
        return;
      }

      wt = new _HTWaitThread(r.nextInt(Math.max(1, ((int) (et - ct)))));
      wt.start();
      fjp = new ForkJoinPool(n);

      try (HierarchicalTextOutput d = new HierarchicalTextOutput(sb)) {
        t = new _HTTask(wt, d, 0, delayMode);
        fjp.execute(t);
        fjp.shutdown();
        try {
          fjp.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
          wt.join();
        } catch (final Throwable xt) {
          Assert.fail(xt.getMessage());
        }

        for (final String s : wt._texts()) {
          HierarchicalTextTest.__checkString(s, sb);
        }
      }
    }
  }

  /**
   * check if a string is contained in a given string builder
   * 
   * @param s
   *          the string
   * @param sb
   *          the string builder
   */
  private static final void __checkString(final String s,
      final StringBuilder sb) {
    final int len;
    String t;
    int i, l, e, j;

    if (sb.indexOf(s) >= 0) {
      return;
    }

    len = s.length();
    for (l = len; (--l) > 0;) {
      e = (s.length() - l);
      for (i = 0; i <= e; i++) {
        t = s.substring(i, (i + l));
        j = sb.indexOf(t);
        if (j >= 0) {
          Assert.fail("String '" + s + //$NON-NLS-1$
              "' of length "//$NON-NLS-1$
              + len + " was lost, but its longest subsequence '" + t//$NON-NLS-1$
              + "' of length " + l//$NON-NLS-1$
              + " was found at index "//$NON-NLS-1$
              + j
              + " near '"//$NON-NLS-1$
              + sb.toString().substring(Math.max(0, j - len - 1),
                  Math.min(sb.length(), j + l + len + 1)) + "'."); //$NON-NLS-1$
          return;
        }
      }
    }

    Assert.fail("String '" + s + //$NON-NLS-1$
        "' was lost completely."); //$NON-NLS-1$
  }

}
