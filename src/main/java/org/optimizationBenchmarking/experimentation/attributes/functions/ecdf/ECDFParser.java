package org.optimizationBenchmarking.experimentation.attributes.functions.ecdf;

import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet;
import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.MathematicalFunctionParser;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.parsers.AnyNumberParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A parser for obtaining an ECDF computation. It will parse strings of the
 * form {@code [transformation] timeDimension, goalDimension goalValue}.
 * Where
 * <dl>
 * <dt>{@code transformation}</dt>
 * <dd>{@code transformation} is an optional
 * {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
 * unary} transformation function to be applied to the time dimension. If a
 * name for such a function, say
 * <code>"{@link org.optimizationBenchmarking.utils.math.functions.power.Lg lg}"</code>
 * is provided, it is parsed via the
 * {@link org.optimizationBenchmarking.utils.math.functions.MathematicalFunctionParser}
 * . If no function is provided, no transformation will be applied to the
 * time dimension.</dd>
 * <dt>{@code timeDimension}</dt>
 * <dd>{@code timeDimension} is the identifier (name) of the time
 * dimension, i.e., the x-axis. It must exist within
 * <code>{@link #m_dimensions}.{@link org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet#find(String)}</code>
 * .</dd>
 * <dt>{@code goalDimension}</dt>
 * <dd>{@code goalDimension} is the identifier (name) of the goal
 * dimension. It must exist within
 * <code>{@link #m_dimensions}.{@link org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet#find(String)}</code>
 * .</dd>
 * <dt>{@code goalValue}</dt>
 * <dd>The {@code goalValue} is a value in dimension {@code goalDim} that
 * needs to be reached by a
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IRun run}
 * to be counted as "successful".</dd>
 * </dl>
 */
public final class ECDFParser extends Parser<ECDF> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the dimension set */
  private final IDimensionSet m_dimensions;

  /**
   * Create the parser
   *
   * @param dimensions
   *          the dimension set
   */
  public ECDFParser(final IDimensionSet dimensions) {
    super();
    if (dimensions == null) {
      throw new IllegalArgumentException(//
          "Dimension set cannot be null."); //$NON-NLS-1$
    }
    this.m_dimensions = dimensions;
  }

  /** {@inheritDoc} */
  @Override
  public final Class<ECDF> getOutputClass() {
    return ECDF.class;
  }

  /** {@inheritDoc} */
  @Override
  public final ECDF parseString(final String string) throws Exception {
    final String use, transformString;
    final int commaIndex;
    int spaceIndex;
    MathematicalFunction transform;
    String timeString, goalString;
    IDimension timeDim, goalDim;
    Number goalValue;

    use = TextUtils.prepare(string);
    if (use == null) {
      throw new IllegalArgumentException(
          "ECDF string cannot be null, empty, or just consist of white space, but '" //$NON-NLS-1$
          + string + "' does.");//$NON-NLS-1$
    }

    commaIndex = use.indexOf(',');
    if (commaIndex <= 0) {
      throw new IllegalArgumentException(
          "ECDF string must contain a comma somewhere in the middle, but '"//$NON-NLS-1$
          + string + "' does not.");//$NON-NLS-1$
    }

    timeString = TextUtils.prepare(use.substring(0, commaIndex));
    if (timeString == null) {
      throw new IllegalArgumentException(
          "ECDF time dimension string cannot be null, empty, or just consist of white space, but '" //$NON-NLS-1$
          + string + "' has such a string.");//$NON-NLS-1$
    }

    transform = null;
    timeDim = this.m_dimensions.find(timeString);
    if (timeDim == null) {
      spaceIndex = timeString.indexOf(' ');
      if (spaceIndex <= 0) {
        throw new IllegalArgumentException("ECDF time string '" //$NON-NLS-1$
            + string + "' does not match to any dimension.");//$NON-NLS-1$
      }

      transformString = TextUtils.prepare(timeString.substring(0,
          spaceIndex));
      timeString = TextUtils.prepare(timeString.substring(spaceIndex + 1));
      if (timeString == null) {
        throw new IllegalArgumentException(
            "ECDF time dimension string cannot be null, empty, or just consist of white space, but '" //$NON-NLS-1$
            + string + "' has such a string.");//$NON-NLS-1$
      }

      timeDim = this.m_dimensions.find(timeString);
      if (timeDim == null) {
        throw new IllegalArgumentException("ECDF time string '" //$NON-NLS-1$
            + string + "' does not match to any dimension.");//$NON-NLS-1$
      }
      if (transformString != null) {
        transform = MathematicalFunctionParser.getInstance().parseString(
            transformString);
        if (transform == null) {
          throw new IllegalArgumentException(((((//
              "Invalid transformation string for ECDF: '" //$NON-NLS-1$
              + transformString) + //
              "' in definition '") //$NON-NLS-1$
              + string) + '\'') + '.');
        }
        if (!(transform instanceof UnaryFunction)) {
          throw new IllegalArgumentException((((//
              "Invalid transformation string for ECDF: '" //$NON-NLS-1$
              + transformString) + //
              "' in definition '") //$NON-NLS-1$
              + string)
              + "' is not a unary function.");//$NON-NLS-1$
        }
      }
    }

    spaceIndex = use.lastIndexOf(' ');
    if (spaceIndex <= commaIndex) {
      throw new IllegalArgumentException(//
          "Goal string of ECDF definition must contain a space, but in '"//$NON-NLS-1$
          + string + "' this is not the case.");//$NON-NLS-1$
    }
    goalString = TextUtils.prepare(use.substring((commaIndex + 1),
        spaceIndex));
    if (goalString == null) {
      throw new IllegalArgumentException(//
          "Goal string of ECDF definition must not be null, empty, or just contain white space, but in '"//$NON-NLS-1$
          + string + "' this is the case.");//$NON-NLS-1$
    }
    goalDim = this.m_dimensions.find(goalString);
    if (goalDim == null) {
      throw new IllegalArgumentException((((//
          '\'' + goalString) + //
          "' in '")//$NON-NLS-1$
          + string)
          + //
          "' is not a valid dimension.");//$NON-NLS-1$
    }

    goalString = TextUtils.prepare(use.substring(spaceIndex + 1));
    if (goalString == null) {
      throw new IllegalArgumentException(//
          "Goal value string of ECDF definition must not be null, empty, or just contain white space, but in '"//$NON-NLS-1$
          + string + "' this is the case.");//$NON-NLS-1$
    }

    goalValue = AnyNumberParser.INSTANCE.parseString(goalString);
    if (goalValue == null) {
      throw new IllegalArgumentException((((//
          '\'' + goalString) + //
          "' in '")//$NON-NLS-1$
          + string)
          + //
          "' is not a valid number.");//$NON-NLS-1$
    }

    return new ECDF(timeDim, ((UnaryFunction) transform), goalDim,
        goalValue);
  }
}
