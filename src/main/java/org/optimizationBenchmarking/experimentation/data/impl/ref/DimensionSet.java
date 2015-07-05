package org.optimizationBenchmarking.experimentation.data.impl.ref;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet;
import org.optimizationBenchmarking.utils.compiler.JavaCompilerTool;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a set of dimensions */
public final class DimensionSet extends _IDObjectSet<Dimension> implements
    IDimensionSet {
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
    this.m_id = DimensionSet.ID_COUNTER.getAndIncrement();

    if (JavaCompilerTool.getInstance().canUse()) {
      this.m_parser = new _ClassBuilder(this, logger).call();
    } else {
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(//
            "You are not using this software with a JDK (Java Development Kit).\nThis means we have to use fallback data structures, which are _much_ slower and require _much_ more memory.\nIf you use a JDK, we use the compiler to automatically build data structures fitting perfectly to the dimensions you have specified.\nYou are probably using a JRE (Java Runtime Environment) to execute this program, so we cannot do that.\nYour Java version is: " //$NON-NLS-1$
                + System.getProperty("java.version") + ' ' + //$NON-NLS-1$
                System.getProperty("java.vm.version") + ' ' + //$NON-NLS-1$
                System.getProperty("java.vm.name") + //$NON-NLS-1$
                " installed in " + //$NON-NLS-1$
                System.getProperty("java.home") + '.');//$NON-NLS-1$
      }

      this.m_parser = new _LongEncodedDataFactory(this.m_data);
    }
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
