package org.optimizationBenchmarking.utils.parsers;

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.EmptyUtils;

/**
 * A parser takes an object and translates it to a
 * {@link java.util.logging.Logger}. The logger description may contain up
 * to three elements:
 * <ol>
 * <li>The logger name. You may use a pre-defined logger that has been
 * created elsewhere in your application or specify a new name or use
 * well-known names such as &quot;global&quot;. The logger name will be
 * resolved using
 * {@link java.util.logging.Logger#getLogger(java.lang.String)}.</li>
 * <li>The {@link java.util.logging.Level logging level}, either a constant
 * that identifies a level (as specified in {@link java.util.logging.Level}
 * ) or a numerical value, i.e., anything the can be parsed by
 * {@link java.util.logging.Level#parse(String)}.</li>
 * <li>If a logging level is given: A Boolean value indicating whether the
 * logger loading should also override all logging levels of the handlers
 * registered with the logger. {@code true} is assumed if no value for this
 * is specified. If the log level is to be enforced with having this
 * parameter as {@code true}, loading the logger will also work its way up
 * to the parents until some useful handler is found.</li>
 * </ol>
 */
public class LoggerParser extends Parser<Logger> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the logger parser */
  public static final LoggerParser INSTANCE = new LoggerParser();

  /** create the parser */
  private LoggerParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<Logger> getOutputClass() {
    return Logger.class;
  }

  /**
   * create a logger from a string list
   *
   * @param data
   *          the data
   * @return the logger
   */
  private final Logger __createLogger(final Collection<String> data) {

    final Iterator<String> it;
    final Logger ret;
    final Level level;
    Handler[] handlers;
    Logger use;
    Filter filter;
    LogRecord record;
    boolean notFound;

    it = data.iterator();

    if (!(it.hasNext())) {
      throw new IllegalArgumentException(//
          "Logger parameter must contain at least the name of the logger."); //$NON-NLS-1$
    }

    ret = Logger.getLogger(StringParser.INSTANCE.parseString(it.next()));

    // is there a logging level?
    if (it.hasNext()) {

      // yes, let's try to parse it
      level = Level.parse(StringParser.INSTANCE.parseString(it.next()));
      ret.setLevel(level);

      // If a logging level specified, we will also set the log levels for
      // all handlers registered with the loggers, unless a third parameter
      // is given which is set to "false"
      setHandlerLevels: {
        if (it.hasNext()) {
          if (!(BooleanParser.INSTANCE.parseBoolean(it.next()))) {
            break setHandlerLevels;
          }
        }

        // let us work our way through the parent hierarchy until we find a
        // useful handler
        use = ret;
        record = null;
        notFound = true;
        findLogger: for (;;) {

          use.setLevel(level);

          handlers = use.getHandlers();
          if ((handlers != null) && (handlers.length > 0)) {
            for (final Handler handler : handlers) {

              if (level == Level.OFF) {
                use.removeHandler(handler);
                use.setUseParentHandlers(false);
                notFound = false;
              } else {

                if (record == null) {
                  record = new LogRecord(level, EmptyUtils.EMPTY_STRING);
                }

                if (!(handler.isLoggable(record))) {
                  handler.setLevel(level);
                }

                filter = handler.getFilter();
                if (filter != null) {
                  if (!(filter.isLoggable(record))) {
                    handler.setFilter(null);
                  }
                }

                if (handler.isLoggable(record)) {
                  notFound = false;
                }
              }
            }
          } else {
            if (level == Level.OFF) {
              use.setUseParentHandlers(false);
              notFound = false;
            }
          }

          if (notFound) {
            if (use.getUseParentHandlers()) {
              use = use.getParent();
              if (use != null) {
                continue findLogger;
              }
            }
          }
          break findLogger;
        }
      }
    }

    this.validate(ret);
    return ret;
  }

  /** {@inheritDoc} */
  @Override
  public final Logger parseString(final String string)
      throws IllegalArgumentException {
    try {
      return this.__createLogger(ListParser.STRING_LIST_PARSER
          .parseString(string));
    } catch (final IllegalArgumentException iae) {
      throw iae;
    } catch (final Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void validate(final Logger instance)
      throws IllegalArgumentException {
    if (instance == null) {
      throw new IllegalArgumentException("Logger must not be null."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final Logger parseObject(final Object o)
      throws IllegalArgumentException {
    final Logger ret;

    if (o instanceof Logger) {
      ret = ((Logger) o);
    } else {
      if (o instanceof Collection) {
        ret = this.__createLogger((Collection) o);
      } else {
        return this.parseString(String.valueOf(o));
      }
    }

    this.validate(ret);
    return ret;
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return LoggerParser.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return LoggerParser.INSTANCE;
  }
}
