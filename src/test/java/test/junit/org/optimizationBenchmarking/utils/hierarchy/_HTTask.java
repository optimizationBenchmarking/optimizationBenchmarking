package test.junit.org.optimizationBenchmarking.utils.hierarchy;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Ignore;
import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.RandomUtils;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalTextOutput;

/** a thread for testing the hierarchy */
@Ignore
final class _HTTask extends RecursiveAction {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the delay mode */
  private final int m_delay;

  /** the root */
  private final HierarchicalTextOutput m_root;

  /** the owner */
  _HTWaitThread m_owner;

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
  _HTTask(final _HTWaitThread owner, final HierarchicalTextOutput root,
      final int depth, final int delay) {
    super();
    this.m_root = root;
    this.m_owner = owner;
    this.m_depth = depth;
    this.m_delay = delay;
  }

  /**
   * write a random string
   * 
   * @param h
   *          the appendable
   * @param r
   *          the random
   */
  private final void __writeString(final HierarchicalTextOutput h,
      final Random r) {
    final StringBuilder sb;
    String s;
    char ch;

    sb = new StringBuilder();
    do {
      if (r.nextBoolean()) {
        s = RandomUtils.longToString(null, r.nextLong());
        sb.append(s);
        h.append(s);
      } else {
        ch = ((char) (((r.nextBoolean() ? 'a' : 'A') + r.nextInt(26))));
        sb.append(ch);
        h.append(ch);
      }

    } while ((sb.length() < 20) || (r.nextInt(6) > 0));

    h.flush();
    this.m_owner._add(sb.toString());
  }

  /** {@inheritDoc} */
  @Override
  public final void compute() {
    final Random r;
    ArrayList<Future<Void>> l;
    _HTTask x;
    final int delay;
    int nextDelay;
    try {
      l = null;
      r = ThreadLocalRandom.current();
      delay = this.m_delay;

      try (final HierarchicalTextOutput d = this.m_root.newText()) {

        this.__writeString(d, r);

        mainLoop: for (;;) {

          if (this.m_owner.m_done) {
            break mainLoop;
          }

          if (delay <= 1) {
            try {
              Thread.sleep(r.nextInt(100));
            } catch (final InterruptedException ie) {
              //
            }
          }

          if (this.m_owner.m_done) {
            break mainLoop;
          }

          if (this.m_depth < 1000) {
            if (l == null) {
              l = new ArrayList<>();
            }

            if ((delay == 1) && (r.nextInt(10) <= 0)) {
              nextDelay = r.nextInt(3);
            } else {
              nextDelay = delay;
            }

            x = new _HTTask(this.m_owner, d, (this.m_depth + 1), nextDelay);
            l.add(x.fork());
          }

          if (this.m_owner.m_done) {
            break mainLoop;
          }

          if (delay <= 1) {
            try {
              Thread.sleep(r.nextInt(100));
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
          try {
            for (final Future<Void> y : l) {
              y.get();
            }
          } catch (final Throwable t) {
            ErrorUtils.throwAsRuntimeException(t);
          }
        }

        this.__writeString(d, r);
      }
    } catch (final Throwable t) {
      throw new AssertionError(t);
    }
  }

}
