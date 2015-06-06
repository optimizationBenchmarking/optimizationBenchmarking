package org.optimizationBenchmarking.utils.io;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * A class allowing us to write to {@link java.lang.System#out},
 * {@link java.lang.System#err}, or a given
 * {@link java.util.logging.Logger} in a fully-synchronized mode, i.e.,
 * preventing that anything else may split or otherwise interact with the
 * output. This class can also be used for similar input from
 * {@link java.lang.System#in}.
 *
 * @param <IOT>
 *          the type of the object we want to write to or read from
 */
public abstract class SynchronizedIO<IOT> implements Runnable {

  /** the i/o object to write to or read from */
  private final IOT m_io;

  /** a logger to synchronize on, may be {@code null} */
  private final Logger m_logger;

  /** the synchronization queue */
  private ArrayList<Object> m_synch;

  /** the index */
  private int m_index;

  /**
   * Create the synchronized i/o
   *
   * @param io
   *          the i/o object to write to or read from
   * @param logger
   *          a logger to synchronize on, may be {@code null}
   */
  protected SynchronizedIO(final IOT io, final Logger logger) {
    super();
    if (io == null) {
      throw new IllegalArgumentException("IO object cannot be null."); //$NON-NLS-1$
    }
    this.m_io = io;
    this.m_logger = logger;
  }

  /**
   * This method is invoked in a fully {@code synchronized} method with
   * respect to {@link java.lang.System#in}, {@link java.lang.System#out},
   * {@link java.lang.System#err}, and {@code logger} as well as any parent
   * loggers and handlers of {@code logger} (if {@code logger!=null}).
   *
   * @param io
   *          the i/o object to write to or read from
   * @param logger
   *          a logger to synchronize on, may be {@code null}
   */
  protected abstract void io(final IOT io, final Logger logger);

  /** {@inheritDoc} */
  @Override
  public synchronized final void run() {
    Logger logger;
    Handler[] handlers;
    synchronized (System.in) {
      synchronized (System.out) {
        System.out.flush();
        try {
          synchronized (System.err) {
            System.err.flush();
            try {

              logger = this.m_logger;
              if (logger == null) {
                this.io(this.m_io, null);
                return;
              }

              if (this.m_synch == null) {
                this.m_synch = new ArrayList<>();
              }
              try {

                // enqueue all loggers and handlers
                while (logger != null) {
                  if (!(this.m_synch.contains(logger))) {
                    this.m_synch.add(logger);
                  }
                  handlers = logger.getHandlers();
                  if (handlers != null) {
                    for (final Handler handler : handlers) {
                      if (handler != null) {
                        if (!(this.m_synch.contains(handler))) {
                          this.m_synch.add(handler);
                        }
                      }
                    }
                  }
                  logger = logger.getParent();
                }

                this.__invoke();// invoke the i/o

              } finally {
                this.m_synch.clear();
              }
            } finally {
              System.err.flush();
            }
          }
        } finally {
          System.out.flush();
        }
      }
    }
  }

  /** invoke the synchronized io */
  private final void __invoke() {
    Object obj;

    if (this.m_index >= this.m_synch.size()) {
      this.io(this.m_io, this.m_logger);
      return;
    }

    obj = this.m_synch.get(this.m_index++);
    synchronized (obj) {
      if (obj instanceof Handler) {
        ((Handler) obj).flush();
      }
      this.__invoke();
    }
  }
}
