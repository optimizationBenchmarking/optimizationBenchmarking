package org.optimizationBenchmarking.utils.parsers;

import java.io.Serializable;

import org.optimizationBenchmarking.utils.tasks.Task;

/**
 * A task which parses a string.
 * 
 * @param <T>
 *          the object type
 */
public final class StringParseTask<T> extends Task<T> implements
    Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the object */
  private final String m_string;

  /** the parser */
  private final Parser<T> m_parser;

  /**
   * Create the string parse task
   * 
   * @param string
   *          the string to parse
   * @param parser
   *          the parser
   */
  public StringParseTask(final String string, final Parser<T> parser) {
    super();
    this.m_string = string;
    this.m_parser = parser;
  }

  /** {@inheritDoc} */
  @Override
  public final T call() throws Exception {
    return this.m_parser.parseString(this.m_string);
  }
}
