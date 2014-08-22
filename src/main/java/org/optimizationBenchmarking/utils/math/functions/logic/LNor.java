package org.optimizationBenchmarking.utils.math.functions.logic;

import org.optimizationBenchmarking.utils.math.functions.arithmetic.Absolute;

/**
 * The logical nor.
 */
public final class LNor extends BinaryBooleanFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final LNor INSTANCE = new LNor();

  /** the forbidden constructor */
  private LNor() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean compute(final boolean x1, final boolean x2) {
    return (!(x1 || x2));
  }

  /** {@inheritDoc} */
  @Override
  public final int lazyResultForFirstParam(final boolean x1) {
    return (x1 ? (-1) : 0);
  }

  // default, automatic serialization replacement and resolve routines for
  // singletons
  //
  /**
   * Write replace: the instance this method is invoked on will be replaced
   * with the singleton instance {@link #INSTANCE} for serialization, i.e.,
   * when the instance is written with
   * {@link java.io.ObjectOutputStream#writeObject(Object)}.
   * 
   * @return the replacement instance (always {@link #INSTANCE})
   */
  private final Object writeReplace() {
    return Absolute.INSTANCE;
  }

  /**
   * Read resolve: The instance this method is invoked on will be replaced
   * with the singleton instance {@link #INSTANCE} after serialization,
   * i.e., when the instance is read with
   * {@link java.io.ObjectInputStream#readObject()}.
   * 
   * @return the replacement instance (always {@link #INSTANCE})
   */
  private final Object readResolve() {
    return Absolute.INSTANCE;
  }
}
