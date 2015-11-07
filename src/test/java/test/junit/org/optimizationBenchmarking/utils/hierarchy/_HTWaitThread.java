package test.junit.org.optimizationBenchmarking.utils.hierarchy;

import java.util.ArrayList;

import org.junit.Ignore;

/** a class that just waits */
@Ignore
final class _HTWaitThread extends Thread {

  /** are we done? */
  volatile boolean m_done;

  /** the end time */
  private final long m_end;

  /** the texts */
  private final ArrayList<String> m_text;

  /**
   * create
   *
   * @param end
   *          the end time
   */
  _HTWaitThread(final int end) {
    super();
    this.m_end = end;
    this.m_text = new ArrayList<>();
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
   * add a text
   *
   * @param text
   *          the text to add
   */
  synchronized final void _add(final String text) {
    this.m_text.add(text);
  }

  /**
   * Get the text
   *
   * @return the texts
   */
  final Iterable<String> _texts() {
    return this.m_text;
  }
}
