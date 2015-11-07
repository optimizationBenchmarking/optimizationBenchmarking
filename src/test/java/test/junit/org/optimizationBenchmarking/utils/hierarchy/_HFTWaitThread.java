package test.junit.org.optimizationBenchmarking.utils.hierarchy;

import org.junit.Ignore;

/** a class that just waits */
@Ignore
final class _HFTWaitThread extends Thread {

  /** are we done? */
  volatile boolean m_done;

  /** the end time */
  private final long m_end;

  /** the caught error */
  private volatile Throwable m_error;

  /**
   * create
   *
   * @param end
   *          the end time
   */
  _HFTWaitThread(final int end) {
    super();
    this.m_end = end;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unused")
  @Override
  public final void run() {
    long t;

    while (!(this.m_done)) {
      t = System.currentTimeMillis();
      if (t >= this.m_end) {
        this.m_done = true;
        return;
      }
      try {
        Thread.sleep(Math.max(1, Math.min(100, (this.m_end - t))));
      } catch (final Throwable xt) {
        //
      }
    }
  }

  /**
   * fail
   *
   * @param t
   *          the error
   */
  final void _fail(final Throwable t) {
    this.m_error = t;
    this.m_done = true;
  }

  /** check failed */
  final void _checkFailed() {
    if (this.m_error != null) {
      throw new RuntimeException(this.m_error);
    }
  }
}
