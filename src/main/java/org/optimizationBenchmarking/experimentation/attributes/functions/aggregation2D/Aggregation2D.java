package org.optimizationBenchmarking.experimentation.attributes.functions.aggregation2D;

import java.util.ArrayList;

import org.optimizationBenchmarking.experimentation.attributes.functions.DimensionTransformation;
import org.optimizationBenchmarking.experimentation.attributes.functions.DimensionTransformationParser;
import org.optimizationBenchmarking.experimentation.attributes.functions.FunctionAttribute;
import org.optimizationBenchmarking.experimentation.attributes.functions.NamedParameterTransformationParser;
import org.optimizationBenchmarking.experimentation.attributes.functions.Transformation;
import org.optimizationBenchmarking.experimentation.attributes.functions.TransformationFunction;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.MatrixBuilder;
import org.optimizationBenchmarking.utils.math.matrix.processing.ColumnTransformedMatrix;
import org.optimizationBenchmarking.utils.math.matrix.processing.iterator2D.MatrixIterator2D;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;
import org.optimizationBenchmarking.utils.math.statistics.parameters.Median;
import org.optimizationBenchmarking.utils.math.statistics.parameters.StatisticalParameter;
import org.optimizationBenchmarking.utils.math.statistics.parameters.StatisticalParameterParser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/**
 * The 2D statistical parameter curve, which can be computed over instance
 * runs, experiments, and experiment sets
 */
public final class Aggregation2D extends FunctionAttribute<IElementSet> {

  /**
   * The default parameter for the aggregate to be computed over the
   * instance runs.
   */
  public static final String DEFAULT_PRIMARY_AGGREGATE_PARAM = "aggregate"; //$NON-NLS-1$

  /**
   * The default parameter for the aggregate to be used to aggregate the
   * results of the instance runs in an experiment or experiment set.
   */
  public static final String DEFAULT_SECONDARY_AGGREGATE_PARAM = "secondaryAggregate"; //$NON-NLS-1$

  /**
   * the parameter to be computed after applying the
   * {@link #getYAxisInputTransformation()}
   */
  private final StatisticalParameter m_param;
  /** the x-dimension index */
  private final int m_xIndex;
  /** the y-dimension index */
  private final int m_yIndex;

  /** the secondary parameter */
  private final StatisticalParameter m_second;

  /**
   * create the raw statistics parameter
   *
   * @param xAxisTransformation
   *          the transformation to be applied to the {@code x}-axis
   * @param yAxisInputTransformation
   *          the transformation to be applied to the data of the {@code y}
   *          -axis before being fed to the actual computation
   * @param yAxisOutputTransformation
   *          the transformation of the result of the function applied to
   *          the data on the {@code y}-axis.
   * @param param
   *          the parameter to be computed after applying the
   *          {@code yTransformation} (if any {@code yTransformation} is
   *          specified, otherwise it is computed directly)
   * @param secondary
   *          the secondary aggregate, {@code null} for default (median)
   */
  protected Aggregation2D(
      final DimensionTransformation xAxisTransformation,
      final DimensionTransformation yAxisInputTransformation,
      final Transformation yAxisOutputTransformation,
      final StatisticalParameter param,
      final StatisticalParameter secondary) {
    super(EAttributeType.NEVER_STORED, xAxisTransformation,
        yAxisInputTransformation, yAxisOutputTransformation);

    if (param == null) {
      throw new IllegalArgumentException(//
          "Statistical parameter cannot be null."); //$NON-NLS-1$
    }
    if (secondary == null) {
      this.m_second = Median.INSTANCE;
    } else {
      this.m_second = secondary;
    }

    this.m_xIndex = xAxisTransformation.getDimension().getIndex();
    this.m_yIndex = yAxisInputTransformation.getDimension().getIndex();
    this.m_param = param;
  }

  /**
   * Get the statistical parameter to be computed over the
   * {@link #getYAxisInputTransformation() transformed} values of the
   * {@code y}-axis for each instance run.
   *
   * @return the statistical parameter to be computed over the
   *         {@link #getYAxisInputTransformation() transformed} values of
   *         the {@code y}-axis.
   */
  public final StatisticalParameter getStatisticalParameter() {
    return this.m_param;
  }

  /**
   * Get the statistical parameter to be computed over the
   * {@link #getStatisticalParameter() statistics} obtained from an
   * instance run set to joint the values for experiments.
   *
   * @return the statistical parameter to be computed over the
   *         {@link #getStatisticalParameter() statistics} obtained from an
   *         instance run set to joint the values for experiments.
   */
  public final StatisticalParameter getSecondaryStatisticalParameter() {
    return this.m_second;
  }

  /**
   * Compute the aggregate per instance runs
   *
   * @param data
   *          the data
   * @return the aggregated data
   */
  private final IMatrix __computeInstanceRuns(final IInstanceRuns data) {
    final IMatrix[] matrices;
    final ArrayListView<? extends IRun> runs;
    int i;

    try (final TransformationFunction xFunction = this
        .getXAxisTransformation().use(data)) {
      try (final TransformationFunction yInputFunction = this
          .getYAxisInputTransformation().use(data)) {
        try (final TransformationFunction yOutputFunction = this
            .getYAxisOutputTransformation().use(data)) {

          runs = data.getData();
          i = runs.size();
          matrices = new IMatrix[i];

          for (; (--i) >= 0;) {
            matrices[i] = new ColumnTransformedMatrix(//
                runs.get(i).selectColumns(this.m_xIndex, this.m_yIndex),//
                xFunction, yInputFunction);
          }

          return Aggregation2D.__aggregate(matrices, this.m_param,
              yOutputFunction);
        }
      }
    }
  }

  /**
   * Compute the aggregate per experiment
   *
   * @param data
   *          the data
   * @return the aggregated data
   */
  private final IMatrix __computeExperiment(final IExperiment data) {
    final IMatrix[] matrices;
    final ArrayListView<? extends IInstanceRuns> runs;
    int i;

    runs = data.getData();
    i = runs.size();
    matrices = new IMatrix[i];

    for (; (--i) >= 0;) {
      matrices[i] = this.__computeInstanceRuns(runs.get(i));
    }

    return Aggregation2D.__aggregate(matrices, this.m_second,
        Identity.INSTANCE);
  }

  /**
   * Compute the aggregate per experiment set
   *
   * @param data
   *          the data
   * @return the aggregated data
   */
  private final IMatrix __computeExperimentSet(final IExperimentSet data) {
    final ArrayList<IMatrix> matrices;

    matrices = new ArrayList<>();
    for (final IExperiment exp : data.getData()) {
      for (final IInstanceRuns irs : exp.getData()) {
        matrices.add(this.__computeInstanceRuns(irs));
      }
    }

    return Aggregation2D.__aggregate(
        matrices.toArray(new IMatrix[matrices.size()]), this.m_second,
        Identity.INSTANCE);
  }

  /**
   * Aggregate the data of a matrix iterator
   *
   * @param matrices
   *          the matrices
   * @param param
   *          the parameter
   * @param transform
   *          the transformation to apply
   * @return the resulting matrix
   */
  @SuppressWarnings("incomplete-switch")
  private static final IMatrix __aggregate(final IMatrix[] matrices,
      final StatisticalParameter param, final UnaryFunction transform) {
    final ScalarAggregate aggregate;
    final MatrixBuilder builder;
    final MatrixIterator2D iterator;
    final boolean isLongArithmeticAccurate;
    int oldYState, currentYState, xState;
    double oldYDouble, currentYDouble, xDouble;
    long oldYLong, currentYLong, xLong;
    boolean lastWasAdded;
    BasicNumber x;

    iterator = MatrixIterator2D.iterate(0, 1, matrices);

    aggregate = param.createSampleAggregate();
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

  /** {@inheritDoc} */
  @Override
  protected final boolean isEqual(
      final FunctionAttribute<IElementSet> other) {
    final Aggregation2D agg;

    agg = ((Aggregation2D) other);
    return (this.m_param.equals(agg.m_param) && //
    this.m_second.equals(agg.m_second));
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(//
        super.calcHashCode(),//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_param),//
            HashUtils.hashCode(this.m_second)));//
  }

  /** {@inheritDoc} */
  @Override
  protected final IMatrix compute(final IElementSet data) {
    if (data instanceof IInstanceRuns) {
      return this.__computeInstanceRuns((IInstanceRuns) data);
    }
    if (data instanceof IExperiment) {
      return this.__computeExperiment((IExperiment) data);
    }
    if (data instanceof IExperimentSet) {
      return this.__computeExperimentSet((IExperimentSet) data);
    }
    throw new IllegalArgumentException(
        "Cannot computed 2d-aggregate over " //$NON-NLS-1$
            + data);
  }

  /** {@inheritDoc} */
  @Override
  protected final String getShortName() {
    return (this.m_second.getShortName() + ' ' + this.m_param
        .getShortName());
  }

  /** {@inheritDoc} */
  @Override
  protected final String getLongName() {
    return (this.m_second.getLongName() + " of " + //$NON-NLS-1$
    (this.m_param.getLongName() + 's'));
  }

  /**
   * Create an instance of {@link Aggregation2D} based on an experiment set
   * and a configuration
   *
   * @param data
   *          the data (experiment set)
   * @param config
   *          the configuration
   * @return the instance of the aggregate
   */
  public static final Aggregation2D create(final IExperimentSet data,
      final Configuration config) {
    DimensionTransformationParser dimParser;
    final DimensionTransformation x, yIn;
    final Transformation yOut;
    final StatisticalParameter first, second;

    dimParser = new DimensionTransformationParser(data);
    x = config
        .get(FunctionAttribute.DEFAULT_X_AXIS_PARAM, dimParser, null);
    if (x == null) {//
      throw new IllegalArgumentException(
          "Must specify an x-dimension via parameter '" //$NON-NLS-1$
              + FunctionAttribute.DEFAULT_X_AXIS_PARAM + '\'');
    }

    yIn = config.get(FunctionAttribute.DEFAULT_Y_INPUT_AXIS_PARAM,
        dimParser, null);
    if (yIn == null) {//
      throw new IllegalArgumentException(
          "Must specify an input dimension for the y-axis via parameter '" //$NON-NLS-1$
              + FunctionAttribute.DEFAULT_Y_INPUT_AXIS_PARAM + '\'');
    }

    dimParser = null;

    yOut = config
        .get(FunctionAttribute.DEFAULT_Y_AXIS_OUTPUT_PARAM,
            new NamedParameterTransformationParser(data),
            new Transformation());

    first = config.get(Aggregation2D.DEFAULT_PRIMARY_AGGREGATE_PARAM,
        StatisticalParameterParser.getInstance(), null);
    if (first == null) {//
      throw new IllegalArgumentException(
          "A statistical parameter (aggregate) to be computed, via parameter '" //$NON-NLS-1$
              + Aggregation2D.DEFAULT_PRIMARY_AGGREGATE_PARAM + '\'');
    }

    second = config.get(Aggregation2D.DEFAULT_SECONDARY_AGGREGATE_PARAM,
        StatisticalParameterParser.getInstance(), Median.INSTANCE);

    return new Aggregation2D(x, yIn, yOut, first, second);
  }
}
