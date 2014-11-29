package org.optimizationBenchmarking.utils.tools.impl.process;

import java.util.logging.Logger;

/** thread for shoveling data to and from an external process */
abstract class _WorkerThread extends Thread {

  /** are we alive? */
  volatile boolean m_alive;

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
}
