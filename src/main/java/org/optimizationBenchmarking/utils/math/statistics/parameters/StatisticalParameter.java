package org.optimizationBenchmarking.utils.math.statistics.parameters;

import java.util.Collection;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.ISemanticMathComponent;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.MatrixBuilder;
import org.optimizationBenchmarking.utils.math.matrix.processing.iterator2D.MatrixIterator2D;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * <p>
 * This class represents a univariate statistic parameter, that is a
 * property which can be {@link #createSampleAggregate() computed} based on
 * a stream of numerical data. Examples are the
 * </p>
 * <ol>
 * <li>
 * {@link org.optimizationBenchmarking.utils.math.statistics.parameters.Median
 * median},</li>
 * <li>
 * {@link org.optimizationBenchmarking.utils.math.statistics.parameters.ArithmeticMean
 * mean},</li>
 * <li>
 * {@link org.optimizationBenchmarking.utils.math.statistics.parameters.Quantile
 * quantiles},</li>
 * <li>
 * {@link org.optimizationBenchmarking.utils.math.statistics.parameters.Minimum
 * minimum},</li>
 * <li>
 * {@link org.optimizationBenchmarking.utils.math.statistics.parameters.Maximum
 * maximum}</li>
 * <li>
 * {@link org.optimizationBenchmarking.utils.math.statistics.parameters.Variance
 * variance}, or the</li>
 * <li>
 * {@link org.optimizationBenchmarking.utils.math.statistics.parameters.StandardDeviation
 * standard deviation}</li>
 * </ol>
 */
public abstract class StatisticalParameter implements
    ISemanticMathComponent {

  /** the short name */
  private final String m_shortName;
  /** the long name */
  private final String m_longName;

  /**
   * create
   *
   * @param shortName
   *          the short name
   * @param longName
   *          the long name
   */
  StatisticalParameter(final String shortName, final String longName) {
    super();

    this.m_shortName = TextUtils.prepare(shortName);
    this.m_longName = TextUtils.prepare(longName);
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printShortName(final ITextOutput textOut,
      final ETextCase textCase) {
    return textCase.appendWord(this.m_shortName, textOut);
  }

  /** {@inheritDoc} */
  @Override
  public void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    out.append(this.m_shortName);
    out.append('(');
    renderer.renderParameter(0, out);
    out.append(')');
  }

  /** {@inheritDoc} */
  @Override
  public void mathRender(final IMath out, final IParameterRenderer renderer) {
    try (final IMath function = out.nAryFunction(this.m_shortName, 1, 1)) {
      renderer.renderParameter(0, out);
    }
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    return textCase.appendWords(this.m_longName, textOut);
  }

  /** {@inheritDoc} */
  @Override
  public final String getPathComponentSuggestion() {
    return this.getShortName();
  }

  /**
   * Get the short name of this statistic parameter
   *
   * @return the short name of this statistic parameter
   */
  public final String getShortName() {
    return this.m_shortName;
  }

  /**
   * Get the long name of this statistic parameter
   *
   * @return the long name of this statistic parameter
   */
  public final String getLongName() {
    return this.m_longName;
  }

  /**
   * Create a new
   * {@linkplain org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate
   * scalar aggregate} which can be used to compute this parameter from a
   * data sample
   *
   * @return the scalar aggregate used to compute the parameter from a data
   *         sample
   */
  public abstract ScalarAggregate createSampleAggregate();

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    return ((o == this) || ((o != null) && //
    (this.getClass() == o.getClass())));
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.hashCode(this.getClass());
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return this.m_shortName;
  }

  /**
   * Aggregate the data of over a set of matrices. The matrices must have
   * (at least) two dimensions: We iterate over each unique value of
   * dimensions {@code timeDim} in any of the provided matrices and
   * aggregate the corresponding values in dimension {@code aggregateDim}.
   * The term "corresponding" is based on the behavior of the
   * {@org.optimizationBenchmarking.utils.math.matrix.processing.iterator2D.MatrixIterator2D
   * 
   * matrix iterator}.
   *
   * @param matrices
   *          the matrices
   * @param timeDim
   *          the dimension to be used as time: We will aggregate the
   *          values of {@code aggregateDim} for each unique value in
   *          {@code timeDim}
   * @param aggregateDim
   *          the values of this dimension are aggregated
   * @param transform
   *          the transformation to apply to the aggregate result value
   * @return the resulting matrix
   */
  public final IMatrix aggregate2D(final IMatrix[] matrices,
      final int timeDim, final int aggregateDim,
      final UnaryFunction transform) {
    return this.__aggregate2D(
        MatrixIterator2D.iterate(timeDim, aggregateDim, matrices, false),
        transform);
  }

  /**
   * Aggregate the data of over a set of matrices. The matrices must have
   * (at least) two dimensions: We iterate over each unique value of
   * dimensions {@code timeDim} in any of the provided matrices and
   * aggregate the corresponding values in dimension {@code aggregateDim}.
   * The term "corresponding" is based on the behavior of the
   * {@org.optimizationBenchmarking.utils.math.matrix.processing.iterator2D.MatrixIterator2D
   * 
   * matrix iterator}.
   *
   * @param matrices
   *          the matrices
   * @param timeDim
   *          the dimension to be used as time: We will aggregate the
   *          values of {@code aggregateDim} for each unique value in
   *          {@code timeDim}
   * @param aggregateDim
   *          the values of this dimension are aggregated
   * @param transform
   *          the transformation to apply to the aggregate result value
   * @return the resulting matrix
   * @see #aggregate2D(IMatrix[], int, int, UnaryFunction)
   */
  public final IMatrix aggregate2D(final Collection<IMatrix> matrices,
      final int timeDim, final int aggregateDim,
      final UnaryFunction transform) {
    return this.__aggregate2D(
        MatrixIterator2D.iterate(timeDim, aggregateDim, matrices, false),
        transform);
  }

  /**
   * Aggregate the data via a matrix iterator: Do the work of
   * {@link #aggregate2D(Collection, int, int, UnaryFunction)} and
   * {@link #aggregate2D(IMatrix[], int, int, UnaryFunction)}
   *
   * @param iterator
   *          the iterator
   * @param transform
   *          the transformation to apply to the aggregate result value
   * @return the resulting matrix
   * @see #aggregate2D(Collection, int, int, UnaryFunction)
   * @see #aggregate2D(IMatrix[], int, int, UnaryFunction)
   */
  @SuppressWarnings("incomplete-switch")
  private final IMatrix __aggregate2D(final MatrixIterator2D iterator,
      final UnaryFunction transform) {
    final ScalarAggregate aggregate;
    final MatrixBuilder builder;
    final boolean isLongArithmeticAccurate;
    int oldYState, currentYState, xState;
    double oldYDouble, currentYDouble, xDouble;
    long oldYLong, currentYLong, xLong;
    boolean lastWasAdded;
    BasicNumber x;

    aggregate = this.createSampleAggregate();
    builder = new MatrixBuilder(EPrimitiveType.LONG);
    builder.setN(2);

    xDouble = currentYDouble = oldYDouble = Double.NaN;
    xState = oldYState = currentYState = Integer.MIN_VALUE;
    xLong = oldYLong = currentYLong = 0L;
    lastWasAdded = false;
    isLongArithmeticAccurate = transform.isLongArithmeticAccurate();

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
              if (EComparison.EQUAL.compare(currentYDouble, oldYDouble)) {
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
            if (isLongArithmeticAccurate) {
              builder.append(transform.computeAsLong(currentYLong));
            } else {
              builder.append(transform.computeAsDouble(currentYLong));
            }
            break;
          }
          case BasicNumber.STATE_DOUBLE: {
            builder.append(transform.computeAsDouble(currentYDouble));
            break;
          }
          case BasicNumber.STATE_POSITIVE_OVERFLOW:
          case BasicNumber.STATE_POSITIVE_INFINITY: {
            builder.append(transform
                .computeAsDouble(Double.POSITIVE_INFINITY));
            break;
          }
          case BasicNumber.STATE_NEGATIVE_OVERFLOW:
          case BasicNumber.STATE_NEGATIVE_INFINITY: {
            builder.append(transform
                .computeAsDouble(Double.NEGATIVE_INFINITY));
            break;
          }
          default: {
            builder.append(transform.computeAsDouble(Double.NaN));
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
          if (isLongArithmeticAccurate) {
            builder.append(transform.computeAsLong(currentYLong));
          } else {
            builder.append(transform.computeAsDouble(currentYLong));
          }
          break;
        }
        case BasicNumber.STATE_DOUBLE: {
          builder.append(transform.computeAsDouble(currentYDouble));
          break;
        }
        case BasicNumber.STATE_POSITIVE_OVERFLOW:
        case BasicNumber.STATE_POSITIVE_INFINITY: {
          builder.append(transform
              .computeAsDouble(Double.POSITIVE_INFINITY));
          break;
        }
        case BasicNumber.STATE_NEGATIVE_OVERFLOW:
        case BasicNumber.STATE_NEGATIVE_INFINITY: {
          builder.append(transform
              .computeAsDouble(Double.NEGATIVE_INFINITY));
          break;
        }
        default: {
          builder.append(transform.computeAsDouble(Double.NaN));
        }
      }
    }

    return builder.make();
  }
}
