package org.optimizationBenchmarking.utils.parsers;

/** A parser for a given type */
public abstract class StringParser extends Parser<String> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create the parser */
  protected StringParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<String> getOutputClass() {
    return String.class;
  }
}
