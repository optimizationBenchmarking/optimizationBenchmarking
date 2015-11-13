package org.optimizationBenchmarking.utils.config;

import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * The default filter added to loggers and handlers. It seems that the
 * internal implementation of the graphics toolkit for X11, namely
 * {@code sun.awt.X11.XToolkit}, generates a lot of logging entries of
 * level {@link java.util.logging.Level#FINER} talking about some timeout
 * tasks being or being not executed. This happens at such a frequency that
 * it just fills up the logs with rubbish. With this filter here, we try to
 * prevent this behavior. The filter is applied to the
 * {@linkplain java.util.logging.Logger#getGlobal() global logger} and all
 * of its {@links java.util.logging.Logger#getParent() parents} and
 * its/their {@linkplain java.util.logging.Logger#getHandlers() handlers}
 * recursively. It will hopefully remove this useless log entries. The
 * filter is applied in the moment when we invoke
 * {@link org.optimizationBenchmarking.utils.config.Configuration#getGlobalLogger()}
 * .
 */
final class _LoggerFilter implements Filter {

  /** the fixed global logger */
  static final Logger GLOBAL = _LoggerFilter._getFixedGlobal();

  /** the old filter */
  private final Filter m_old;

  /**
   * create the logger filter
   *
   * @param old
   *          the old filter to delegate to
   */
  _LoggerFilter(final Filter old) {
    super();
    this.m_old = old;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isLoggable(final LogRecord record) {
    final String sourceName;
    if (record != null) {
      if (record.getLevel().intValue() <= Level.FINER.intValue()) {
        sourceName = record.getSourceClassName();
        if (sourceName != null) {
          if (sourceName.startsWith("sun.awt.X11.")) { //$NON-NLS-1$
            return false;
          }
        }
      }
      return ((this.m_old == null) || (this.m_old.isLoggable(record)));
    }
    return false;
  }

  /**
   * Get the fixed global logger
   *
   * @return the global logger
   */
  private static final Logger _getFixedGlobal() {
    final Logger logger;

    logger = Logger.getGlobal();
    _LoggerFilter.__fix(logger, null);
    return logger;
  }

  /**
   * Fix a given logger
   *
   * @param logger
   *          the logger
   * @param filter
   *          the filter
   * @return the new filter
   */
  private static final _LoggerFilter __fix(final Logger logger,
      final _LoggerFilter filter) {
    final Handler[] handlers;
    _LoggerFilter useNull, newFilter;
    Filter old;
    Logger parent;

    useNull = filter;
    synchronized (logger) {
      old = logger.getFilter();
      if (old instanceof _LoggerFilter) {
        if (useNull == null) {
          newFilter = ((_LoggerFilter) old);
          if (newFilter.m_old == null) {
            useNull = newFilter;
          }
        }
      } else {
        if (old == null) {
          if (useNull == null) {
            useNull = new _LoggerFilter(null);
          }
          newFilter = useNull;
        } else {
          newFilter = new _LoggerFilter(old);
        }
        logger.setFilter(newFilter);
      }
    }

    handlers = logger.getHandlers();
    if (handlers != null) {
      for (final Handler handler : handlers) {
        old = handler.getFilter();
        if (old instanceof _LoggerFilter) {
          if (useNull == null) {
            newFilter = ((_LoggerFilter) old);
            if (newFilter.m_old == null) {
              useNull = newFilter;
            }
          }
        } else {
          if (old == null) {
            if (useNull == null) {
              useNull = new _LoggerFilter(null);
            }
            newFilter = useNull;
          } else {
            newFilter = new _LoggerFilter(old);
          }
          handler.setFilter(newFilter);
        }
      }
    }

    parent = logger.getParent();
    if ((parent != null) && (parent != logger)) {
      return _LoggerFilter.__fix(parent, useNull);
    }
    return useNull;
  }
}
