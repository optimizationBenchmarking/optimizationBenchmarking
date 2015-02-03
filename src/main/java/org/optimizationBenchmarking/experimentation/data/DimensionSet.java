package org.optimizationBenchmarking.experimentation.data;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a set of dimensions */
public final class DimensionSet extends _IDObjectSet<Dimension> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** an atomic id counter */
  private static final AtomicInteger ID_COUNTER = new AtomicInteger();

  /** the parsers */
  private final DataFactory m_parser;

  /**
   * The dimensions
   * 
   * @param data
   *          the data of the set
   * @param logger
   *          the logger
   */
  DimensionSet(final Dimension[] data, final Logger logger) {
    super(data, false, false, true);
    this.m_parser = new _ClassBuilder(this, logger).call();
    this.m_id = DimensionSet.ID_COUNTER.getAndIncrement();
  }

  /**
   * Get the parser that can read a string and parse to a data point
   * 
   * @return a parser that can read a string and parse to a data point
   */
  public final DataFactory getDataFactory() {
    return this.m_parser;
  }

  /**
   * Find the given dimension.
   * 
   * @param name
   *          the name
   * @return the dimension, or {@code null} if it could not be found
   */
  @Override
  public final Dimension find(final String name) {
    return super.find(name);
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    this.m_data.toText(textOut);
  }

  /** {@inheritDoc} */
  @Override
  public final ExperimentSet getOwner() {
    return ((ExperimentSet) (this.m_owner));
  }
}
