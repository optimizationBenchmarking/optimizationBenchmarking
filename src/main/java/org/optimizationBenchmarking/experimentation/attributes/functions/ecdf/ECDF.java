package org.optimizationBenchmarking.experimentation.attributes.functions.ecdf;

import java.util.ArrayList;

import org.optimizationBenchmarking.experimentation.attributes.functions.DimensionTransformation;
import org.optimizationBenchmarking.experimentation.attributes.functions.DimensionTransformationParser;
import org.optimizationBenchmarking.experimentation.attributes.functions.FunctionAttribute;
import org.optimizationBenchmarking.experimentation.attributes.functions.NamedParameterTransformationParser;
import org.optimizationBenchmarking.experimentation.attributes.functions.Transformation;
import org.optimizationBenchmarking.experimentation.attributes.functions.TransformationFunction;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.comparison.ComparisonParser;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.parameters.ArithmeticMean;
import org.optimizationBenchmarking.utils.math.statistics.parameters.StatisticalParameter;
import org.optimizationBenchmarking.utils.math.statistics.parameters.StatisticalParameterParser;
import org.optimizationBenchmarking.utils.parsers.AnyNumberParser;

/**
 * The Estimated Cumulative Distribution Function returns, for an
 * experiment or instance runs set, the fraction of runs which have reached
 * a specified goal.
 */
public final class ECDF extends FunctionAttribute<IElementSet> {

  /** the criterion parameter */
  public static final String CRITERION_PARAM = "criterion";//$NON-NLS-1$
  /** the goal parameter */
  public static final String GOAL_PARAM = "goal";//$NON-NLS-1$
  /** the aggregate parameter */
  public static final String AGGREGATE_PARAM = "aggregate";//$NON-NLS-1$

  /** the goal value as {@code double} */
  private final double m_goalValueDouble;

  /** the goal value as {@code long} */
  private final long m_goalValueLong;

  /**
   * should we use {@link #m_goalValueDouble} as goal ({@code true}) or
   * {@link #m_goalValueLong} ({@code false}?
   */
  private final boolean m_useLongGoal;

  /** the comparison used to determine whether the goal criterion was met */
  private final EComparison m_criterion;

  /** the way to aggregate the different ECDFs */
  private final StatisticalParameter m_aggregate;

  /**
   * Create the ECDF attribute
   *
   * @param xAxisTransformation
   *          the transformation to be applied to the {@code x}-axis
   * @param yAxisInputTransformation
   *          the transformation to be applied to the data of the {@code y}
   *          -axis before being fed to the actual computation
   * @param yAxisOutputTransformation
   *          the transformation of the result of the function applied to
   *          the data on the {@code y}-axis.
   * @param goalValue
   *          the goal value
   * @param criterion
   *          the goal comparison criterion
   * @param aggregate
   *          the method to aggregate the ECDFs
   */
  public ECDF(final DimensionTransformation xAxisTransformation,
      final DimensionTransformation yAxisInputTransformation,
      final Transformation yAxisOutputTransformation,
      final Number goalValue, final EComparison criterion,
      final StatisticalParameter aggregate) {
    super(EAttributeType.NEVER_STORED, xAxisTransformation,
        yAxisInputTransformation, yAxisOutputTransformation);

    final IDimension goalDim;

    if (goalValue == null) {
      throw new IllegalArgumentException(//
          "Goal value of ECDF cannot be null.");//$NON-NLS-1$
    }
    if (criterion == null) {
      throw new IllegalArgumentException(//
          "Comparison criterion of ECDF cannot be null.");//$NON-NLS-1$
    }
    if (aggregate == null) {
      throw new IllegalArgumentException(//
          "Aggregate to join ECDFs of different instance sets cannot be null.");//$NON-NLS-1$
    }

    goalDim = yAxisInputTransformation.getDimension();

    switch (goalDim.getDataType()) {
      case BYTE:
      case SHORT:
      case INT:
      case LONG: {
        this.m_useLongGoal = yAxisInputTransformation
            .isLongArithmeticAccurate();

        if ((NumericalTypes.getTypes(goalValue) & NumericalTypes.IS_LONG) != 0) {
          this.m_goalValueLong = goalValue.longValue();
        } else {
          this.m_goalValueLong = ECDF.__doubleToLong(
              goalValue.doubleValue(), criterion);
        }

        this.m_goalValueDouble = this.m_goalValueLong;
        break;
      }
      default: {
        this.m_goalValueDouble = goalValue.doubleValue();
        this.m_useLongGoal = false;
        this.m_goalValueLong = ECDF.__doubleToLong(this.m_goalValueDouble,
            criterion);
      }
    }

    this.m_criterion = criterion;
    this.m_aggregate = aggregate;
  }

  /**
   * convert a {@code double} goal value to a {@code long}
   *
   * @param d
   *          the goal value
   * @param criterion
   *          the goal criterion
   * @return the converted value
   */
  private static final long __doubleToLong(final double d,
      final EComparison criterion) {

    if (d <= Long.MIN_VALUE) {
      return Long.MIN_VALUE;
    }

    if (d >= Long.MAX_VALUE) {
      return Long.MAX_VALUE;
    }

    switch (criterion) {
      case LESS:
      case LESS_OR_EQUAL: {
        if (d != d) {
          return Long.MIN_VALUE;
        }
        return ((long) (0.5d + Math.floor(d)));
      }

      case EQUAL:
      case NOT_EQUAL: {

        if (d != d) {
          throw new IllegalArgumentException(//
              "Cannot transform " + d + //$NON-NLS-1$
                  " to a long under " + criterion);//$NON-NLS-1$
        }
        return ((long) d);
      }

      case GREATER:
      case GREATER_OR_EQUAL: {
        if (d != d) {
          return Long.MAX_VALUE;
        }
        return ((long) (0.5d + Math.ceil(d)));
      }

      default: {
        throw new IllegalArgumentException(//
            "Illegal comparison operator: " //$NON-NLS-1$
                + criterion);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.combineHashes(//
            super.calcHashCode(),//
            (this.m_useLongGoal ? //
            HashUtils.hashCode(this.m_goalValueLong)
                : HashUtils.hashCode(this.m_goalValueDouble))//
            ), HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_criterion),//
            HashUtils.hashCode(this.m_aggregate)//
            ));
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean isEqual(
      final FunctionAttribute<IElementSet> other) {
    final ECDF ecdf;

    ecdf = ((ECDF) other);

    return ((this.m_useLongGoal == ecdf.m_useLongGoal)//
        && //
        (this.m_useLongGoal ? //
        (this.m_goalValueLong == ecdf.m_goalValueLong)
            : //
            (EComparison.EQUAL.compare(this.m_goalValueDouble,
                ecdf.m_goalValueDouble))) && //
        this.m_criterion.equals(ecdf.m_criterion) && //
    this.m_aggregate.equals(ecdf.m_aggregate));
  }

  /**
   * Is the goal value a {@code long} value?
   *
   * @return {@code true} if the goal value is a {@code long} value,
   *         {@code false} if it is a {@code double}
   * @see #getGoalValueDouble()
   * @see #getGoalValueLong()
   */
  public final boolean isGoalValueLong() {
    return this.m_useLongGoal;
  }

  /**
   * Get the goal value as a {@code long}. The result of this method will
   * only be accurate if {@link #isGoalValueLong()} returned {@code true}.
   * Otherwise, {@link #getGoalValueDouble()} should be used.
   *
   * @return the goal value as a {@code long}
   * @see #isGoalValueLong()
   * @see #getGoalValueDouble()
   */
  public final long getGoalValueLong() {
    return this.m_goalValueLong;
  }

  /**
   * Get the goal value as a {@code double}. The result of this method will
   * only be accurate if {@link #isGoalValueLong()} returned {@code false}.
   * Otherwise, {@link #getGoalValueLong()} should be used.
   *
   * @return the goal value as a {@code double}
   * @see #isGoalValueLong()
   * @see #getGoalValueLong()
   */
  public final double getGoalValueDouble() {
    return this.m_goalValueDouble;
  }

  /** {@inheritDoc} */
  @Override
  public final String getShortName() {
    return "ECDF"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final String getLongName() {
    return "estimated cumulative distribution function"; //$NON-NLS-1$
  }

  /**
   * Compute the raw matrix for an instance run set
   *
   * @param data
   *          the data the instance runs
   * @return the raw matrix
   */
  private final IMatrix __computeInstanceRuns(final IInstanceRuns data) {
    final _List list;
    final DimensionTransformation xIn, yIn;
    final Transformation yOut;
    final IDimension timeDim, goalDim;

    xIn = this.getXAxisTransformation();
    try (final TransformationFunction xInFunc = xIn.use(data)) {
      yIn = this.getYAxisInputTransformation();
      try (final TransformationFunction yInFunc = yIn.use(data)) {
        yOut = this.getYAxisOutputTransformation();
        try (final TransformationFunction yOutFunc = yOut.use(data)) {

          timeDim = xIn.getDimension();
          goalDim = yIn.getDimension();

          if (goalDim.getDataType().isInteger()
              && yInFunc.isLongArithmeticAccurate()) {
            if (timeDim.getDataType().isInteger()) {
              list = new _LongTimeLongGoal(timeDim, goalDim,
                  this.m_criterion, yInFunc, this.m_goalValueLong);
            } else {
              list = new _DoubleTimeLongGoal(timeDim, goalDim,
                  this.m_criterion, yInFunc, this.m_goalValueLong);
            }
          } else {
            if (timeDim.getDataType().isInteger()) {
              list = new _LongTimeDoubleGoal(timeDim, goalDim,
                  this.m_criterion, yInFunc, this.m_goalValueDouble);
            } else {
              list = new _DoubleTimeDoubleGoal(timeDim, goalDim,
                  this.m_criterion, yInFunc, this.m_goalValueDouble);
            }
          }

          for (final IRun run : data.getData()) {
            list._addRun(run);
          }

          return list._toMatrix(xInFunc, yOutFunc);
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

    return this.m_aggregate.aggregate2D(matrices, 0, 1, Identity.INSTANCE);
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

    return this.m_aggregate.aggregate2D(matrices, 0, 1, Identity.INSTANCE);
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

    throw new IllegalArgumentException("Cannot compute ECDF over "//$NON-NLS-1$
        + data);
  }

  /**
   * Create an instance of {@link ECDF} based on an experiment set and a
   * configuration
   *
   * @param data
   *          the data (experiment set)
   * @param config
   *          the configuration
   * @return the instance of the aggregate
   */
  public static final ECDF create(final IExperimentSet data,
      final Configuration config) {
    DimensionTransformationParser dimParser;
    final DimensionTransformation xIn, yIn;
    final Transformation yOut;
    final StatisticalParameter aggregate;
    final IDimension goalDim;
    EComparison compare;
    Number goal;

    dimParser = new DimensionTransformationParser(data);
    xIn = config.get(//
        FunctionAttribute.X_AXIS_PARAM, dimParser, null);
    if (xIn == null) {//
      throw new IllegalArgumentException(
          "Must specify an x-dimension via parameter '" //$NON-NLS-1$
              + FunctionAttribute.X_AXIS_PARAM + '\'');
    }

    yIn = config
        .get(FunctionAttribute.Y_INPUT_AXIS_PARAM, dimParser, null);
    if (yIn == null) {//
      throw new IllegalArgumentException(
          "Must specify an input dimension for the y-axis via parameter '" //$NON-NLS-1$
              + FunctionAttribute.Y_INPUT_AXIS_PARAM + '\'');
    }

    dimParser = null;

    yOut = config
        .get(FunctionAttribute.Y_AXIS_OUTPUT_PARAM,
            new NamedParameterTransformationParser(data),
            new Transformation());

    aggregate = config.get(ECDF.AGGREGATE_PARAM,
        StatisticalParameterParser.getInstance(), ArithmeticMean.INSTANCE);

    goalDim = yIn.getDimension();

    if (goalDim.getDirection().isIncreasing()) {
      compare = EComparison.GREATER_OR_EQUAL;
      if (goalDim.getDataType().isInteger()) {
        goal = Long.valueOf(goalDim.getParser().getUpperBoundLong());
      } else {
        goal = Double.valueOf(goalDim.getParser().getUpperBoundDouble());
      }
    } else {
      compare = EComparison.LESS_OR_EQUAL;
      if (goalDim.getDataType().isInteger()) {
        goal = Long.valueOf(goalDim.getParser().getLowerBoundLong());
      } else {
        goal = Double.valueOf(goalDim.getParser().getLowerBoundDouble());
      }
    }

    goal = config.get(ECDF.GOAL_PARAM, AnyNumberParser.INSTANCE, goal);
    compare = config.get(ECDF.CRITERION_PARAM,
        ComparisonParser.getInstance(), compare);

    return new ECDF(xIn, yIn, yOut, goal, compare, aggregate);
  }

}
