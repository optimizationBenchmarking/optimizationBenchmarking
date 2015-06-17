package org.optimizationBenchmarking.experimentation.attributes.functions.aggregation2D;

import org.optimizationBenchmarking.experimentation.attributes.functions.FunctionAttribute;
import org.optimizationBenchmarking.experimentation.attributes.statistics.parameters.StatisticalParameter;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.impl.FunctionToMathBridge;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.MatrixBuilder;
import org.optimizationBenchmarking.utils.math.matrix.processing.iterator2D.MatrixIterator2D;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * the base class for 2D statistical parameter curve
 *
 * @param <T>
 *          the element set type
 */
abstract class _Aggregation2DBase<T extends IElementSet> extends
    FunctionAttribute<T> {

  /**
   * create the raw statistics parameter curve
   *
   * @param type
   *          the attribute type
   */
  _Aggregation2DBase(final EAttributeType type) {
    super(type);
  }

  /**
   * Get the dimension of the {@code x}-axis of the curves
   *
   * @return the dimension of the {@code x}-axis of the curves
   */
  public abstract IDimension getXDimension();

  /**
   * Get the transformation to be applied to the values of {@code x}-axis
   * of the curves.
   *
   * @return the transformation to be applied to the values of {@code x}
   *         -axis of the curves, or {@code null} if the values are used
   *         as-is
   */
  public abstract UnaryFunction getXTransformation();

  /**
   * Get the dimension of the {@code y}-axis of the curves
   *
   * @return the dimension of the {@code y}-axis of the curves
   */
  public abstract IDimension getYDimension();

  /**
   * Get the transformation to be applied to the values of {@code y}-axis
   * of the curves <em>before</em> the {@link #getStatisticalParameter()
   * statistical parameter} is computed.
   *
   * @return the transformation to be applied to the values of {@code y}
   *         -axis of the curves <em>before</em> the
   *         {@link #getStatisticalParameter() statistical parameter} is
   *         computed, or {@code null} if the values are used as-is
   */
  public abstract UnaryFunction getYTransformation();

  /**
   * Get the statistical parameter to be computed over the
   * {@link #getYTransformation() transformed} values of the
   * {@link #getYDimension() y}-axis.
   *
   * @return the statistical parameter to be computed over the
   *         {@link #getYTransformation() transformed} values of the
   *         {@link #getYDimension() y}-axis.
   */
  public abstract StatisticalParameter getStatisticalParameter();

  /**
   * Get the statistical parameter to be computed over the
   * {@link #getStatisticalParameter() statistics} obtained from an
   * instance run set.
   *
   * @return the statistical parameter to be computed over the
   *         {@link #getYTransformation() transformed} values of the
   *         {@link #getYDimension() y}-axis.
   */
  public abstract StatisticalParameter getSecondaryStatisticalParameter();

  /** {@inheritDoc} */
  @Override
  public String getPathComponentSuggestion() {
    final MemoryTextOutput mto;
    final StatisticalParameter second;
    UnaryFunction func;

    mto = new MemoryTextOutput();

    second = this.getSecondaryStatisticalParameter();
    if (second != null) {
      mto.append(second.getPathComponentSuggestion());
      mto.append('_');
    }
    mto.append(this.getStatisticalParameter().getPathComponentSuggestion());
    mto.append('_');
    func = this.getYTransformation();
    if (func != null) {
      mto.append(func.toString().toLowerCase());
      mto.append('_');
    }
    mto.append(this.getYDimension().getPathComponentSuggestion());
    mto.append('_');
    func = this.getXTransformation();
    if (func != null) {
      mto.append(func.toString().toLowerCase());
      mto.append('_');
    }
    mto.append(this.getXDimension().getPathComponentSuggestion());

    return mto.toString();
  }

  /** {@inheritDoc} */
  @Override
  protected ETextCase appendXAxisTitlePlain(final ITextOutput textOut,
      final ETextCase textCase) {
    final UnaryFunction func;
    ETextCase use;

    use = ETextCase.ensure(textCase);
    func = this.getXTransformation();
    if (func != null) {
      use = use.appendWord(func.toString(), textOut);
      textOut.append(' ');
    }

    return this.getXDimension().appendName(textOut, use);
  }

  /** {@inheritDoc} */
  @Override
  public void appendXAxisTitle(final IMath math) {
    try (final IMath inner = new FunctionToMathBridge(
        this.getXTransformation(), math)) {
      this.getXDimension().appendName(inner);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected ETextCase appendYAxisTitlePlain(final ITextOutput textOut,
      final ETextCase textCase) {
    final UnaryFunction func;
    final StatisticalParameter second;
    ETextCase use;

    use = ETextCase.ensure(textCase);

    second = this.getSecondaryStatisticalParameter();
    if (second != null) {
      use = ETextCase.ensure(second.appendName(textOut, use));
      textOut.append(' ');
    }

    use = ETextCase.ensure(this.getStatisticalParameter().appendName(
        textOut, use));
    textOut.append(' ');
    func = this.getYTransformation();
    if (func != null) {
      use = use.appendWord(func.toString(), textOut);
      textOut.append(' ');
    }

    return this.getYDimension().appendName(textOut, use);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public void appendYAxisTitle(final IMath math) {
    final StatisticalParameter second;
    final IMath use;

    second = this.getSecondaryStatisticalParameter();
    if (second != null) {
      use = second.asFunction(math);
    } else {
      use = math;
    }

    try (final IMath statPar = this.getStatisticalParameter().asFunction(
        use)) {
      try (final IMath inner = new FunctionToMathBridge(
          this.getYTransformation(), statPar)) {
        this.getYDimension().appendName(inner);
      }
    }

    if (use != math) {
      use.close();
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("rawtypes")
  public final boolean equals(final Object o) {
    final _Aggregation2DBase other;

    if (o == this) {
      return true;
    }

    if (o instanceof _Aggregation2DBase) {
      other = ((_Aggregation2DBase) o);

      return ((this.getXDimension().getIndex() == //
          other.getXDimension().getIndex())//
          && (this.getYDimension().getIndex() == //
          other.getYDimension().getIndex()) && //
          EComparison.equals(this.getXTransformation(),
              other.getXTransformation()) && //
          EComparison.equals(this.getYTransformation(),
              other.getYTransformation()) && //
          EComparison.equals(this.getStatisticalParameter(),
              other.getStatisticalParameter()) && //
      EComparison.equals(this.getSecondaryStatisticalParameter(),
          other.getStatisticalParameter()));
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.combineHashes(//
            HashUtils.combineHashes(//
                HashUtils.hashCode(this.getXDimension().getIndex()),//
                HashUtils.hashCode(this.getXTransformation())),//
            HashUtils.combineHashes(//
                HashUtils.hashCode(this.getYDimension().getIndex()),//
                HashUtils.hashCode(this.getYTransformation()))),//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.getStatisticalParameter()),//
            HashUtils.hashCode(this.getSecondaryStatisticalParameter())));
  }

  /**
   * Aggregate the data of a matrix iterator
   *
   * @param iterator
   *          the iterator
   * @param param
   *          the parameter
   * @return the resulting matrix
   */
  @SuppressWarnings("incomplete-switch")
  static final IMatrix _aggregate(final MatrixIterator2D iterator,
      final StatisticalParameter param) {
    final ScalarAggregate aggregate;
    final MatrixBuilder builder;
    int oldYState, currentYState, xState;
    double oldYDouble, currentYDouble, xDouble;
    long oldYLong, currentYLong, xLong;
    boolean lastWasAdded;
    BasicNumber x;

    aggregate = param.createSampleAggregate();
    builder = new MatrixBuilder(EPrimitiveType.LONG);
    builder.setN(2);

    xDouble = currentYDouble = oldYDouble = Double.NaN;
    xState = oldYState = currentYState = Integer.MIN_VALUE;
    xLong = oldYLong = currentYLong = 0L;
    lastWasAdded = false;

    // Iterate over all the values.
    while (iterator.hasNext()) {
      lastWasAdded = false;

      // Get the x-value
      x = iterator.next();
      xState = x.getState();
      switch (xState) {
        case BasicNumber.STATE_INTEGER: {
          xLong = x.longValue();
          break;
        }
        case BasicNumber.STATE_DOUBLE: {
          xDouble = x.doubleValue();
          break;
        }
      }

      // Compute the y-value
      aggregate.reset();
      iterator.aggregateRow(0, aggregate);
      currentYState = aggregate.getState();
      switch (currentYState) {
        case BasicNumber.STATE_INTEGER: {
          currentYLong = aggregate.longValue();
          break;
        }
        case BasicNumber.STATE_DOUBLE: {
          currentYDouble = aggregate.doubleValue();
          break;
        }
      }

      // We want to compare the current y-value with the old y value. We
      // only add a point if they are different. If we compute, for
      // instance, the median or a very high or low percentile, it could be
      // that the values do not change over many points. We want to avoid
      // adding useless points to the matrix.
      checkAdd: {
        if (currentYState == oldYState) {
          switch (currentYState) {
            case BasicNumber.STATE_INTEGER: {
              if (currentYLong == oldYLong) {
                break checkAdd;
              }
              break;
            }
            case BasicNumber.STATE_DOUBLE: {
              if (EComparison.compareDoubles(currentYDouble, oldYDouble) == 0) {
                break checkAdd;
              }
              break;
            }
            default: {
              break checkAdd;
            }
          }
        }

        // If we get here, the current y and the old y value are different.
        // We add a point.
        lastWasAdded = true;

        // First add the x-coordinate.
        switch (xState) {
          case BasicNumber.STATE_INTEGER: {
            builder.append(xLong);
            break;
          }
          case BasicNumber.STATE_DOUBLE: {
            builder.append(xDouble);
            break;
          }
          case BasicNumber.STATE_POSITIVE_OVERFLOW:
          case BasicNumber.STATE_POSITIVE_INFINITY: {
            builder.append(Double.POSITIVE_INFINITY);
            break;
          }
          case BasicNumber.STATE_NEGATIVE_OVERFLOW:
          case BasicNumber.STATE_NEGATIVE_INFINITY: {
            builder.append(Double.NEGATIVE_INFINITY);
            break;
          }
          default: {
            builder.append(Double.NaN);
          }
        }

        // Now add the y-coordinate.
        switch (currentYState) {
          case BasicNumber.STATE_INTEGER: {
            builder.append(currentYLong);
            break;
          }
          case BasicNumber.STATE_DOUBLE: {
            builder.append(currentYDouble);
            break;
          }
          case BasicNumber.STATE_POSITIVE_OVERFLOW:
          case BasicNumber.STATE_POSITIVE_INFINITY: {
            builder.append(Double.POSITIVE_INFINITY);
            break;
          }
          case BasicNumber.STATE_NEGATIVE_OVERFLOW:
          case BasicNumber.STATE_NEGATIVE_INFINITY: {
            builder.append(Double.NEGATIVE_INFINITY);
            break;
          }
          default: {
            builder.append(Double.NaN);
          }
        }

      }// End of check add: We may get here via break or adding...

      oldYState = currentYState;
      oldYDouble = currentYDouble;
      oldYLong = currentYLong;
    } // end of iteration loop

    // If the last point was not added and we are at the end of the
    // iteration, we should add the last point, since it marks the last
    // element on the x-axis.
    if ((!lastWasAdded) && (xState != Integer.MIN_VALUE)) {
      // Ok, so let's add the last point:
      // First add the x-coordinate.
      switch (xState) {
        case BasicNumber.STATE_INTEGER: {
          builder.append(xLong);
          break;
        }
        case BasicNumber.STATE_DOUBLE: {
          builder.append(xDouble);
          break;
        }
        case BasicNumber.STATE_POSITIVE_OVERFLOW:
        case BasicNumber.STATE_POSITIVE_INFINITY: {
          builder.append(Double.POSITIVE_INFINITY);
          break;
        }
        case BasicNumber.STATE_NEGATIVE_OVERFLOW:
        case BasicNumber.STATE_NEGATIVE_INFINITY: {
          builder.append(Double.NEGATIVE_INFINITY);
          break;
        }
        default: {
          builder.append(Double.NaN);
        }
      }

      // Now add the y-coordinate.
      switch (currentYState) {
        case BasicNumber.STATE_INTEGER: {
          builder.append(currentYLong);
          break;
        }
        case BasicNumber.STATE_DOUBLE: {
          builder.append(currentYDouble);
          break;
        }
        case BasicNumber.STATE_POSITIVE_OVERFLOW:
        case BasicNumber.STATE_POSITIVE_INFINITY: {
          builder.append(Double.POSITIVE_INFINITY);
          break;
        }
        case BasicNumber.STATE_NEGATIVE_OVERFLOW:
        case BasicNumber.STATE_NEGATIVE_INFINITY: {
          builder.append(Double.NEGATIVE_INFINITY);
          break;
        }
        default: {
          builder.append(Double.NaN);
        }
      }
    }

    return builder.make();
  }
}
