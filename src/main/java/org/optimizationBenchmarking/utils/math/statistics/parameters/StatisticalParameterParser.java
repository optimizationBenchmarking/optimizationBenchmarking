package org.optimizationBenchmarking.utils.math.statistics.parameters;

import org.optimizationBenchmarking.utils.parsers.DoubleParser;
import org.optimizationBenchmarking.utils.parsers.InstanceParser;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** A parser for static parameters */
public final class StatisticalParameterParser extends
    InstanceParser<StatisticalParameter> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the possible separators */
  private static final char[] SEPARATORS = { ' ', '_', '.', '@' };

  /** create */
  StatisticalParameterParser() {
    super(StatisticalParameter.class, new String[] {//
        ReflectionUtils.getPackagePrefix(StatisticalParameter.class) });

  }

  /** {@inheritDoc} */
  @Override
  public final StatisticalParameter parseString(final String string)
      throws Exception {
    final String prepared;
    String lower, first, last;
    int index;

    prepared = TextUtils.prepare(string);
    if (prepared == null) {
      throw new IllegalArgumentException(((//
          "Statistical parameter definition cannot be null, empty, or just contain white space, but is '" //$NON-NLS-1$
          + string) + '\'') + '.');
    }

    lower = TextUtils.toLowerCase(prepared);
    switch (TextUtils.toLowerCase(string)) {
      case ArithmeticMean.SHORT:
      case ArithmeticMean.LONG:
      case ArithmeticMean.OTHER: {
        return ArithmeticMean.INSTANCE;
      }
      case Maximum.SHORT:
      case Maximum.LONG: {
        return Maximum.INSTANCE;
      }
      case Median.SHORT:
      case Median.LONG: {
        return Median.INSTANCE;
      }
      case Minimum.SHORT:
      case Minimum.LONG: {
        return Minimum.INSTANCE;
      }
      case StandardDeviation.SHORT:
      case StandardDeviation.LONG: {
        return StandardDeviation.INSTANCE;
      }
      case Variance.SHORT:
      case Variance.LONG: {
        return Variance.INSTANCE;
      }

      default: {
        try {

          index = (-1);
          for (final char ch : StatisticalParameterParser.SEPARATORS) {
            index = lower.indexOf(ch);
            if (index > 0) {
              break;
            }
          }

          if (index > 0) {
            first = TextUtils.prepare(lower.substring(0, index));
            last = TextUtils.prepare(lower.substring(index + 1));
            if ((first != null) && (last != null)) {
              if (Quantile.SHORT.equals(first)) {
                return Quantile.getInstance(//
                    DoubleParser.INSTANCE.parseDouble(last));
              }
              if (Quantile.LONG.equals(last)) {
                return Quantile.getInstance(//
                    DoubleParser.INSTANCE.parseDouble(first));
              }
            }
            first = last = null;
          }
          lower = null;

          return super.parseString(prepared);
        } catch (final Throwable t) {
          throw new IllegalArgumentException(((//
              "Invalid statistical parameter definition: '" //$NON-NLS-1$
              + string) + "' (see causing exception)."), t);//$NON-NLS-1$
        }
      }
    }
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
    return StatisticalParameterParser.getInstance();
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
    return StatisticalParameterParser.getInstance();
  }

  /**
   * Get the singleton instance of this parser
   *
   * @return the statistical parameter driver parser
   */
  public static final StatisticalParameterParser getInstance() {
    return __StatisticalParameterParserLoader.INSTANCE;
  }

  /** the instance loader */
  private static final class __StatisticalParameterParserLoader {
    /** the instance */
    static final StatisticalParameterParser INSTANCE = new StatisticalParameterParser();
  }
}
