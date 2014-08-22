package org.optimizationBenchmarking.utils.parsers;

import java.io.Serializable;

import org.optimizationBenchmarking.utils.tasks.Task;

/**
 * A task which parses an object.
 * 
 * @param <T>
 *          the object type
 */
public final class ObjectParseTask<T> extends Task<T> implements
    Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the object */
  private final Object m_object;

  /** the parser */
  private final Parser<T> m_parser;

  /**
   * Create the object parse task
   * 
   * @param object
   *          the object to parse
   * @param parser
   *          the parser
   */
  public ObjectParseTask(final Object object, final Parser<T> parser) {
    super();
    this.m_object = object;
    this.m_parser = parser;
  }

  /** {@inheritDoc} */
  @Override
  public final T call() throws Exception {
    return this.m_parser.parseObject(this.m_object);
  }
}
