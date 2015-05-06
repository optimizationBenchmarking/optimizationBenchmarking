package org.optimizationBenchmarking.experimentation.attributes.functions.statisticalParameter2D;

import org.optimizationBenchmarking.experimentation.attributes.statistics.parameters.StatisticalParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.MatrixBuilder;
import org.optimizationBenchmarking.utils.math.matrix.processing.ColumnTransformedMatrix;
import org.optimizationBenchmarking.utils.math.matrix.processing.iterator2D.MatrixIterator2D;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/** the raw version of the 2D statistical parameter curve */
public final class _RawStatisticalParameter2D extends
    _StatisticalParameter2DBase<IInstanceRuns> {

  /** the x-dimension dimension */
  final IDimension m_xDim;
  /** the y-dimension dimension */
  final IDimension m_yDim;
  /** the transformation along the y-axis */
  final UnaryFunction m_yTransformation;
  /**
   * the parameter to be computed after applying the
   * {@link #m_yTransformation} if an {@link #m_yTransformation} is
   * specified, otherwise it is computed directly
   */
  final StatisticalParameter m_param;
  /** the x-dimension index */
  private final int m_xIndex;
  /** the y-dimension index */
  private final int m_yIndex;

  /**
   * create the raw statistics parameter
   *
   * @param xDim
   *          the {@code x}-dimension
   * @param xTransformation
   *          the transformation along the {@code x}-dimension
   * @param yDim
   *          the {@code y}-dimension
   * @param yTransformation
   *          the transformation along the {@code y}-dimension
   * @param param
   *          the parameter to be computed after applying the
   *          {@code yTransformation} (if any {@code yTransformation} is
   *          specified, otherwise it is computed directly)
   */
  public _RawStatisticalParameter2D(final IDimension xDim,
      final UnaryFunction xTransformation, final IDimension yDim,
      final UnaryFunction yTransformation, final StatisticalParameter param) {
    super();

    if (xDim == null) {
      throw new IllegalArgumentException("x-dimension cannot be null."); //$NON-NLS-1$
    }
    if (yDim == null) {
      throw new IllegalArgumentException("y-dimension cannot be null."); //$NON-NLS-1$
    }
    if (param == null) {
      throw new IllegalArgumentException(//
          "Statistical parameter cannot be null."); //$NON-NLS-1$
    }

    this.m_xDim = xDim;
    this.m_xIndex = xDim.getIndex();
    this.m_yDim = yDim;
    this.m_yIndex = yDim.getIndex();
    this.m_yTransformation = yTransformation;
    this.m_param = param;
  }

  /** {@inheritDoc} */
  @Override
  public final IDimension getXDimension() {
    return this.m_xDim;
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction getXTransformation() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final IDimension getYDimension() {
    return this.m_yDim;
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction getYTransformation() {
    return this.m_yTransformation;
  }

  /** {@inheritDoc} */
  @Override
  public final StatisticalParameter getStatisticalParameter() {
    return this.m_param;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  protected final IMatrix compute(final IInstanceRuns data) {
    final MatrixIterator2D iterator;
    final ScalarAggregate aggregate;
    final MatrixBuilder builder;
    final IMatrix[] matrices;
    final ArrayListView<? extends IRun> runs;
    int oldYState, currentYState, xState;
    double oldYDouble, currentYDouble, xDouble;
    long oldYLong, currentYLong, xLong;
    boolean lastWasAdded;
    BasicNumber x;
    int i;

    if (this.m_yTransformation != null) {
      runs = data.getData();
      i = runs.size();
      matrices = new IMatrix[i];
      for (; (--i) >= 0;) {
        matrices[i] = new ColumnTransformedMatrix(//
            runs.get(i).selectColumns(this.m_xIndex, this.m_yIndex),//
            Identity.INSTANCE,//
            this.m_yTransformation);
      }
      iterator = MatrixIterator2D.iterate(0, 1, matrices);
    } else {
      iterator = MatrixIterator2D.iterate(this.m_xIndex, this.m_yIndex,
          data.getData());
    }

    aggregate = this.m_param.createSampleAggregate();
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

  /** {@inheritDoc} */
  @Override
  public final StatisticalParameter getSecondaryStatisticalParameter() {
    return null;
  }
}
