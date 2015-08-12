package org.optimizationBenchmarking.utils.chart.impl;

import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.chart.impl.export.ExportChartDriver;
import org.optimizationBenchmarking.utils.chart.impl.jfree.JFreeChartDriver;
import org.optimizationBenchmarking.utils.chart.spec.IChartDriver;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.parsers.InstanceParser;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;

/** a chart driver parser */
public final class ChartDriverParser extends InstanceParser<IChartDriver> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the format parser */
  private final InstanceParser<EChartFormat> m_formatParser;

  /** create */
  ChartDriverParser() {
    super(IChartDriver.class, ChartDriverParser.__prefixes());
    this.m_formatParser = new InstanceParser<>(EChartFormat.class, null);
  }

  /**
   * get the prefixes
   *
   * @return the path prefixes
   */
  private static final String[] __prefixes() {
    final LinkedHashSet<String> paths;
    paths = new LinkedHashSet<>();
    ReflectionUtils.addPackageOfClassToPrefixList(ChartDriverParser.class,
        paths);
    ReflectionUtils.addPackageOfClassToPrefixList(ExportChartDriver.class,
        paths);
    ReflectionUtils.addPackageOfClassToPrefixList(JFreeChartDriver.class,
        paths);
    return paths.toArray(new String[paths.size()]);
  }

  /** {@inheritDoc} */
  @Override
  public final IChartDriver parseString(final String string) {
    if ("export".equalsIgnoreCase(string) || //$NON-NLS-1$
        "txt".equalsIgnoreCase(string) || //$NON-NLS-1$
        "text".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return EChartFormat.EXPORT.getDefaultDriver();
    }

    try {
      return this.m_formatParser.parseString(string).getDefaultDriver();
    } catch (final Exception exception) {
      try {
        return super.parseString(string);
      } catch (final Exception exception2) {
        RethrowMode.AS_UNSUPPORTED_OPERATION_EXCEPTION.rethrow(
            ((("Could not find chart driver fitting to string '" //$NON-NLS-1$
            + string) + '\'') + '.'), false,
            ErrorUtils.aggregateError(exception, exception2));
        return null;// never reached
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final IChartDriver parseObject(final Object o) {
    try {
      return this.m_formatParser.parseObject(o).getDefaultDriver();
    } catch (final Exception exception) {
      try {
        return super.parseObject(o);
      } catch (final Exception exception2) {
        RethrowMode.AS_UNSUPPORTED_OPERATION_EXCEPTION.rethrow(
            ((("Could not find chart driver fitting to object '" //$NON-NLS-1$
            + o) + '\'') + '.'), false,
            ErrorUtils.aggregateError(exception, exception2));
        return null;// never reached
      }
    }
  }

  /**
   * Get the singleton instance of this parser
   *
   * @return the chart driver parser
   */
  public static final ChartDriverParser getInstance() {
    return __ChartDriverParserLoader.INSTANCE;
  }

  // default, automatic serialization replacement and resolve routines for
  // singletons
  //
  /**
   * Write replace: the instance this method is invoked on will be replaced
   * with the singleton instance {@link #getInstance()} for serialization,
   * i.e., when the instance is written with
   * {@link java.io.ObjectOutputStream#writeObject(Object)}.
   *
   * @return the replacement instance (always {@link #getInstance()})
   */
  private final Object writeReplace() {
    return ChartDriverParser.getInstance();
  }

  /**
   * Read resolve: The instance this method is invoked on will be replaced
   * with the singleton instance {@link #getInstance()} after
   * serialization, i.e., when the instance is read with
   * {@link java.io.ObjectInputStream#readObject()}.
   *
   * @return the replacement instance (always {@link #getInstance()})
   */
  private final Object readResolve() {
    return ChartDriverParser.getInstance();
  }

  /** the instance loader */
  private static final class __ChartDriverParserLoader {
    /** the instance */
    static final ChartDriverParser INSTANCE = new ChartDriverParser();
  }
}
