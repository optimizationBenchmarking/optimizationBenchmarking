package org.optimizationBenchmarking.utils;

/** Some memory utilities. */
public final class MemoryUtils {

  /**
   * Invoke the garbage collector in a very crude manner. This method is
   * more hard-core than {@link System#gc()} as the goal is to free a
   * maximum amount of memory, regardless how long it takes. This makes
   * sense when doing some memory-intense stuff such as evaluating
   * experimental results. It makes not much sense in performance-critical
   * code.
   */
  public static final void fullGC() {
    final Runtime r;
    long best, cur, i;

    r = Runtime.getRuntime();
    best = cur = r.freeMemory();
    i = 5;
    for (;;) {
      r.gc();
      Thread.yield();
      cur = r.freeMemory();
      if (cur > best) {
        best = cur;
        i = 5;
        continue;
      }
      if ((--i) <= 0) {
        return;
      }
    }
  }

  /**
   * A simple call to the garbage collector. Makes sense after
   * memory-intense operations.
   */
  public static final void quickGC() {
    System.gc();
  }
}
