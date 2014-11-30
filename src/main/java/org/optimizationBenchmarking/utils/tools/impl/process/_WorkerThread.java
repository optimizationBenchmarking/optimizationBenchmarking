package org.optimizationBenchmarking.utils.tools.impl.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * The base class for worker {@link java.lang.Thread threads} for shoveling
 * data to and from an external process. By default, they will be
 * {@link java.lang.Thread#isDaemon() deamon} threads and run at the
 * {@link java.lang.Thread#MIN_PRIORITY lowest priority}.
 */
abstract class _WorkerThread extends Thread {

  /**
   * are we alive: {@code 0}=alive, {@code 1}=shutting down, data can be
   * ignored, {@code 2}=dead, just quit
   */
  volatile int m_mode;

  /** the logger */
  final Logger m_log;

  /**
   * create
   * 
   * @param name
   *          the thread's name
   * @param log
   *          the logger
   */
  _WorkerThread(final String name, final Logger log) {
    super(name);
    this.m_log = log;

    try {// Worker threads can have a low priority. They are only
         // _required_ to do stuff when the main threads are blocked.
         // Otherwise, they _may_ do stuff.
      this.setPriority(Thread.MIN_PRIORITY);
    } catch (final Throwable t) {
      // if we cannot set the priority, it is also OK
    }
    this.setDaemon(true);
  }

  /**
   * discard all data from a given input stream
   * 
   * @param is
   *          the stream
   * @throws IOException
   *           if i/o fails
   */
  final void _discard(final InputStream is) throws IOException {
    long s;
    try {
      while (this.m_mode < 2) {
        s = is.skip(0x1ffffff0L);
        if (s <= 0L) {
          if (is.read() < 0) {// check for end-of-stream
            break;
          }
        }
      }
    } finally {
      is.close();
    }
  }
}
