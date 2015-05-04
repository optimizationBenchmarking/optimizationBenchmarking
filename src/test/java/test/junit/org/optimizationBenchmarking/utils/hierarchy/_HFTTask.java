package test.junit.org.optimizationBenchmarking.utils.hierarchy;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Ignore;

/** a thread for testing the hierarchy */
@Ignore
final class _HFTTask extends RecursiveAction {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the root */
  private final _HFTDummyHierarchicalFSM m_root;

  /** the delay mode */
  private final int m_delay;

  /** the owner */
  _HFTWaitThread m_owner;

  /** the depth */
  int m_depth;

  /**
   * create
   *
   * @param root
   *          the root object
   * @param owner
   *          the owner
   * @param depth
   *          the depth
   * @param delay
   *          the delay mode
   */
  _HFTTask(final _HFTWaitThread owner,
      final _HFTDummyHierarchicalFSM root, final int depth, final int delay) {
    super();
    this.m_root = root;
    this.m_owner = owner;
    this.m_depth = depth;
    this.m_delay = delay;
  }

  /** {@inheritDoc} */
  @Override
  public final void compute() {
    final Random r;
    ArrayList<Future<Void>> l;
    _HFTTask x;
    final int delay;
    int nextDelay;

    if (this.m_owner.m_done) {
      return;
    }

    try {
      l = null;
      r = ThreadLocalRandom.current();
      delay = this.m_delay;

      try (final _HFTDummyHierarchicalFSM d = new _HFTDummyHierarchicalFSM(
          this.m_root)) {

        mainLoop: for (;;) {

          if (this.m_owner.m_done) {
            break mainLoop;
          }

          if (delay <= 1) {
            try {
              Thread.sleep(r.nextInt(20));
            } catch (final InterruptedException ie) {
              //
            }
          }

          if (this.m_owner.m_done) {
            break mainLoop;
          }

          if (this.m_depth < 100) {
            if (l == null) {
              l = new ArrayList<>();
            }

            if ((delay == 1) && (r.nextInt(10) <= 0)) {
              nextDelay = r.nextInt(3);
            } else {
              nextDelay = delay;
            }

            x = new _HFTTask(this.m_owner, d, (this.m_depth + 1),
                nextDelay);
            l.add(x.fork());
          }

          if (this.m_owner.m_done) {
            break mainLoop;
          }

          if (delay <= 1) {
            try {
              Thread.sleep(r.nextInt(20));
            } catch (final InterruptedException ie) {
              //
            }
          }

          if (this.m_owner.m_done) {
            break mainLoop;
          }

          if ((this.m_depth > 0) && (r.nextInt(5) <= 0)) {
            break mainLoop;
          }
        }

        if (l != null) {
          for (final Future<Void> y : l) {
            y.get();
          }
        }

      }
    } catch (final Throwable e) {
      this.m_owner._fail(e);
    }
  }

}
