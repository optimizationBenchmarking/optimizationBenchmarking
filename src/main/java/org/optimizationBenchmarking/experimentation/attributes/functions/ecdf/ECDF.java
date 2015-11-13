package org.optimizationBenchmarking.experimentation.attributes.functions.ecdf;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthor;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibDateBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibInProceedingsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibOrganizationBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibProceedingsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibTechReportBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.bibliography.data.BibliographyBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.EBibMonth;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.comparison.ComparisonParser;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.spec.ECitationMode;
import org.optimizationBenchmarking.utils.document.spec.EMathComparison;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.parameters.ArithmeticMean;
import org.optimizationBenchmarking.utils.math.statistics.parameters.StatisticalParameter;
import org.optimizationBenchmarking.utils.math.statistics.parameters.StatisticalParameterParser;
import org.optimizationBenchmarking.utils.math.text.DefaultParameterRenderer;
import org.optimizationBenchmarking.utils.parsers.AnyNumberParser;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

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

  /** the references to be used for the ecdf */
  public static final Bibliography REFERENCES = ECDF.__buildReferences();

  /** the goal value as {@code double} */
  private final double m_goalValueDouble;

  /** the goal value as {@code long} */
  private final long m_goalValueLong;

  /**
   * should we use {@link #m_goalValueDouble} as goal ({@code true}) or
   * {@link #m_goalValueLong} ({@code false}?
   */
  private final boolean m_useLongGoal;

  /**
   * the comparison used to determine whether the goal criterion was met
   */
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

        if (yAxisInputTransformation.isLongArithmeticAccurate()) {
          this.m_useLongGoal = true;
          if ((NumericalTypes.getTypes(goalValue)
              & NumericalTypes.IS_LONG) != 0) {
            this.m_goalValueLong = goalValue.longValue();
          } else {
            this.m_goalValueLong = ECDF
                .__doubleToLong(goalValue.doubleValue(), criterion);
          }

          this.m_goalValueDouble = this.m_goalValueLong;
          break;
        }
        // fall through
      }
        //$FALL-THROUGH$
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

  /** {@inheritDoc} */
  @Override
  protected void yAxisRenderYAxisSourceAsParameter(final IMath out) {
    try (final IMath math = out
        .compare(EMathComparison.fromEComparison(this.m_criterion))) {
      super.yAxisRenderYAxisSourceAsParameter(math);
      try (final IText number = math.number()) {
        if (this.isGoalValueLong()
            || (this.m_goalValueLong == this.m_goalValueDouble)) {
          number.append(this.m_goalValueLong);
        } else {
          number.append(this.m_goalValueDouble);
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void yAxisRenderYAxisSourceAsParameter(final ITextOutput out) {
    super.yAxisRenderYAxisSourceAsParameter(out);
    out.append(EMathComparison.fromEComparison(this.m_criterion)
        .getOperatorChar());
    if (this.isGoalValueLong()
        || (this.m_goalValueLong == this.m_goalValueDouble)) {
      out.append(this.m_goalValueLong);
    } else {
      out.append(this.m_goalValueDouble);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected ETextCase printNameInDescription(final ITextOutput textOut,
      final ETextCase textCase, final boolean fromYAxisSemanticComponent) {
    ETextCase next;

    next = super.printNameInDescription(textOut, textCase,
        fromYAxisSemanticComponent);

    if (!fromYAxisSemanticComponent) {
      if (textOut instanceof IComplexText) {
        try (final BibliographyBuilder builder = ((IComplexText) textOut)
            .cite(ECitationMode.ID, next, ESequenceMode.COMMA)) {
          builder.addAll(ECDF.REFERENCES);
        }
      }
    }

    return next.nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    final DimensionTransformation xIn, yIn;
    final Transformation yOut;
    ETextCase use;

    use = super.printDescription(textOut, textCase).nextCase();

    yIn = this.getYAxisInputTransformation();
    xIn = this.getXAxisTransformation();
    yOut = this.getYAxisOutputTransformation();

    textOut.append(" The "); //$NON-NLS-1$
    if (textOut instanceof IComplexText) {
      try (final IMath math = ((IComplexText) textOut).inlineMath()) {
        this.yAxisMathRender(math, DefaultParameterRenderer.INSTANCE);
      }
    } else {
      this.yAxisMathRender(textOut, DefaultParameterRenderer.INSTANCE);
    }
    textOut.append(//
        " represents the fraction of runs which reach a value of "); //$NON-NLS-1$
    yIn.printShortName(textOut, use);
    textOut.append(' ');
    textOut.append(this.m_criterion.toString());
    textOut.append(' ');
    if (this.m_useLongGoal) {
      textOut.append(this.m_goalValueLong);
    } else {
      textOut.append(this.m_goalValueDouble);
    }
    textOut.append(" for a given ellapsed runtime measured in "); //$NON-NLS-1$
    xIn.getDimension().printShortName(textOut, use);

    if (yOut.isIdentityTransformation()) {
      textOut.append(". The "); //$NON-NLS-1$
      this.printShortName(textOut, use);
    } else {
      textOut.append(//
          ". We do not use these fractions directly, but instead compute "); //$NON-NLS-1$
      if (textOut instanceof IComplexText) {
        try (final IMath math = ((IComplexText) textOut).inlineMath()) {
          this.yAxisMathRender(math, DefaultParameterRenderer.INSTANCE);
        }
      } else {
        this.yAxisMathRender(textOut, DefaultParameterRenderer.INSTANCE);
      }
      textOut.append(". The result of this formula"); //$NON-NLS-1$
    }

    textOut.append(//
        " is always computed over the runs of an experiment for a given benchmark instance. If runs for multiple instances are available, we aggregate the results by computing their "); //$NON-NLS-1$
    this.m_aggregate.printLongName(textOut, use);
    textOut.append('.');

    if (!(xIn.isIdentityTransformation())) {
      textOut.append(" The x-axis does not represent the values of "); //$NON-NLS-1$
      xIn.getDimension().printShortName(textOut, use);
      textOut.append(" directly, but instead "); //$NON-NLS-1$
      if (textOut instanceof IComplexText) {
        try (final IMath math = ((IComplexText) textOut).inlineMath()) {
          xIn.mathRender(math, DefaultParameterRenderer.INSTANCE);
        }
      } else {
        xIn.mathRender(textOut, DefaultParameterRenderer.INSTANCE);
      }
      textOut.append('.');
    }

    if (yOut.isIdentityTransformation()) {
      textOut.append(" The "); //$NON-NLS-1$
      this.printShortName(textOut, use);
      textOut.append(" is always between "); //$NON-NLS-1$
      textOut.append(0);
      textOut.append(" and "); //$NON-NLS-1$
      textOut.append(1);
      textOut.append(" \u2012 and the higher it is, the better."); //$NON-NLS-1$
    }
    return use;
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
            super.calcHashCode(), //
            (this.m_useLongGoal ? //
                HashUtils.hashCode(this.m_goalValueLong)
                : HashUtils.hashCode(this.m_goalValueDouble))//
    ), HashUtils.combineHashes(//
        HashUtils.hashCode(this.m_criterion), //
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
                ecdf.m_goalValueDouble)))
        && //
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
   * @param logger
   *          the logger
   * @return the raw matrix
   */
  private final IMatrix __computeInstanceRuns(final IInstanceRuns data,
      final Logger logger) {
    final _List list;
    final DimensionTransformation xIn, yIn;
    final Transformation yOut;
    final IDimension timeDim, goalDim;
    final IMatrix result;
    String name;

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      name = this.getNameForLogging(data);
      logger.finer("Now beginning to compute the " + name + '.'); //$NON-NLS-1$
    } else {
      name = null;
    }

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

          result = list._toMatrix(xInFunc, yOutFunc);

          if ((logger != null) && (logger.isLoggable(Level.FINER))) {
            if (name == null) {
              name = this.getNameForLogging(data);
            }
            logger.finer("Finished computing the " + name + //$NON-NLS-1$
                ", resulting in a " + result.m() + '*' + result.n() + //$NON-NLS-1$
                " matrix.");//$NON-NLS-1$
          }

          return result;
        }
      }
    }
  }

  /**
   * Compute the aggregate per experiment
   *
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @return the aggregated data
   */
  private final IMatrix __computeExperiment(final IExperiment data,
      final Logger logger) {
    final IMatrix[] matrices;
    final ArrayListView<? extends IInstanceRuns> runs;
    final IMatrix result;
    String name;
    int i;

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      name = this.getNameForLogging(data);
      logger.finer("Now beginning to compute " + name + '.'); //$NON-NLS-1$
    } else {
      name = null;
    }

    runs = data.getData();
    i = runs.size();
    matrices = new IMatrix[i];

    for (; (--i) >= 0;) {
      matrices[i] = this.__computeInstanceRuns(runs.get(i), logger);
    }

    result = this.m_aggregate.aggregate2D(matrices, 0, 1,
        Identity.INSTANCE);

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      if (name == null) {
        name = this.getNameForLogging(data);
      }
      logger.finer("Finished computing the " + name + //$NON-NLS-1$
          " by computing the " + this.m_aggregate.getShortName() + //$NON-NLS-1$
          " ECDF over each of the " + //$NON-NLS-1$
          runs.size() + " instance runs, resulting in a "//$NON-NLS-1$
          + result.m() + '*' + result.n() + //
          " matrix.");//$NON-NLS-1$
    }

    return result;
  }

  /**
   * Compute the aggregate per experiment set
   *
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @return the aggregated data
   */
  private final IMatrix __computeExperimentSet(final IExperimentSet data,
      final Logger logger) {
    final ArrayList<IMatrix> matrices;
    final IMatrix result;
    String name;

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      name = this.getNameForLogging();
      logger.finer("Now beginning to compute " + name + '.'); //$NON-NLS-1$
    } else {
      name = null;
    }

    matrices = new ArrayList<>();
    for (final IExperiment exp : data.getData()) {
      for (final IInstanceRuns irs : exp.getData()) {
        matrices.add(this.__computeInstanceRuns(irs, logger));
      }
    }

    result = this.m_aggregate.aggregate2D(matrices, 0, 1,
        Identity.INSTANCE);

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      if (name == null) {
        name = this.getNameForLogging();
      }
      logger.finer("Finished computing the " + name + //$NON-NLS-1$
          " by computing the " + this.m_aggregate.getShortName() + //$NON-NLS-1$
          " ECDF over all instance runs, resulting in a "//$NON-NLS-1$
          + result.m() + '*' + result.n() + //
          " matrix.");//$NON-NLS-1$
    }

    return result;
  }

  /** {@inheritDoc} */
  @Override
  protected final IMatrix compute(final IElementSet data,
      final Logger logger) {

    if (data instanceof IInstanceRuns) {
      return this.__computeInstanceRuns(((IInstanceRuns) data), logger);
    }
    if (data instanceof IExperiment) {
      return this.__computeExperiment(((IExperiment) data), logger);
    }
    if (data instanceof IExperimentSet) {
      return this.__computeExperimentSet(((IExperimentSet) data), logger);
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

    yIn = config.get(FunctionAttribute.Y_INPUT_AXIS_PARAM, dimParser,
        null);
    if (yIn == null) {//
      throw new IllegalArgumentException(
          "Must specify an input dimension for the y-axis via parameter '" //$NON-NLS-1$
              + FunctionAttribute.Y_INPUT_AXIS_PARAM + '\'');
    }

    dimParser = null;

    yOut = config.get(FunctionAttribute.Y_AXIS_OUTPUT_PARAM,
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

  /**
   * build the references to be used when plotting an ECDF
   *
   * @return the bibliography of references
   */
  private static final Bibliography __buildReferences() {
    final BibAuthor hoos;

    hoos = new BibAuthor(//
        "Holger H.", //$NON-NLS-1$
        "Hoos");//$NON-NLS-1$

    try (
        final BibliographyBuilder bibBuilder = new BibliographyBuilder()) {

      try (final BibInProceedingsBuilder inProc = //
      bibBuilder.inProceedings()) {
        try (final BibAuthorsBuilder authors = inProc.setAuthors()) {
          authors.addAuthor(hoos);
          try (final BibAuthorBuilder author = authors.author()) {
            author.setPersonalName("Thomas");//$NON-NLS-1$
            author.setFamilyName("St\u00fctzle");//$NON-NLS-1$
          }
        }
        inProc.setTitle(//
            "Evaluating Las Vegas Algorithms \u2012 Pitfalls and Remedies");//$NON-NLS-1$
        try (final BibProceedingsBuilder proc = inProc.proceedings()) {
          proc.setTitle(//
              "Proceedings of the 14th Conference on Uncertainty in Artificial Intelligence (UAI'98)");//$NON-NLS-1$
          try (final BibDateBuilder date = proc.startDate()) {
            date.setYear(1998);
            date.setMonth(EBibMonth.JULY);
            date.setDay(24);
          }
          try (final BibDateBuilder date = proc.endDate()) {
            date.setYear(1998);
            date.setMonth(EBibMonth.JULY);
            date.setDay(26);
          }
          try (final BibAuthorsBuilder editors = proc.setEditors()) {
            try (final BibAuthorBuilder editor = editors.author()) {
              editor.setPersonalName("Gregory F.");//$NON-NLS-1$
              editor.setFamilyName("Cooper");//$NON-NLS-1$
            }
            try (final BibAuthorBuilder editor = editors.author()) {
              editor.setPersonalName("Serafin");//$NON-NLS-1$
              editor.setFamilyName("Moral");//$NON-NLS-1$
            }
          }
          try (final BibOrganizationBuilder loc = proc.location()) {
            loc.setAddress("Madison, WI, USA");//$NON-NLS-1$
          }
          try (final BibOrganizationBuilder pub = proc.publisher()) {
            pub.setAddress("San Francisco, CA, USA");//$NON-NLS-1$
            pub.setName("Morgan Kaufmann Publishers Inc.");//$NON-NLS-1$
          }
        }
        inProc.setStartPage("238");//$NON-NLS-1$
        inProc.setEndPage("245");//$NON-NLS-1$
        inProc.setURL(//
            "http://www.intellektik.informatik.tu-darmstadt.de/TR/1998/98-02.ps.Z");//$NON-NLS-1$
      }

      try (final BibInProceedingsBuilder inProc = //
      bibBuilder.inProceedings()) {
        try (final BibAuthorsBuilder authors = inProc.setAuthors()) {
          try (final BibAuthorBuilder author = authors.author()) {
            author.setPersonalName("Dave Andrew Douglas");//$NON-NLS-1$
            author.setFamilyName("Tompkins");//$NON-NLS-1$
          }
          authors.addAuthor(hoos);
        }
        inProc.setTitle(//
            "UBCSAT: An Implementation and Experimentation Environment for SLS Algorithms for SAT and MAX-SAT");//$NON-NLS-1$
        try (final BibProceedingsBuilder proc = inProc.proceedings()) {
          proc.setTitle(//
              "Revised Selected Papers from the Seventh International Conference on Theory and Applications of Satisfiability Testing (SAT'04)");//$NON-NLS-1$
          try (final BibDateBuilder date = proc.startDate()) {
            date.setYear(2004);
            date.setMonth(EBibMonth.MAY);
            date.setDay(10);
          }
          try (final BibDateBuilder date = proc.endDate()) {
            date.setYear(2004);
            date.setMonth(EBibMonth.MAY);
            date.setDay(13);
          }
          try (final BibAuthorsBuilder editors = proc.setEditors()) {
            editors.addAuthor(hoos);
            try (final BibAuthorBuilder editor = editors.author()) {
              editor.setPersonalName("David G.");//$NON-NLS-1$
              editor.setFamilyName("Mitchell");//$NON-NLS-1$
            }
          }
          try (final BibOrganizationBuilder loc = proc.location()) {
            loc.setAddress("Vancouver, BC, Canada");//$NON-NLS-1$
          }
          try (final BibOrganizationBuilder pub = proc.publisher()) {
            pub.setAddress("Berlin, Germany");//$NON-NLS-1$
            pub.setName("Springer-Verlag GmbH");//$NON-NLS-1$
          }
          proc.setSeries("Lecture Notes in Computer Science (LNCS)");//$NON-NLS-1$
          proc.setVolume("3542");//$NON-NLS-1$
        }
        inProc.setDOI("10.1007/11527695");//$NON-NLS-1$
        inProc.setStartPage("306");//$NON-NLS-1$
        inProc.setEndPage("320");//$NON-NLS-1$
        inProc.setURL(//
            "http://ubcsat.dtompkins.com/downloads/sat04proc-ubcsat.pdf?attredirects=0");//$NON-NLS-1$
      }

      try (BibTechReportBuilder report = bibBuilder.techReport()) {
        try (final BibAuthorsBuilder authors = report.setAuthors()) {
          try (final BibAuthorBuilder author = authors.author()) {
            author.setPersonalName("Nikolaus");//$NON-NLS-1$
            author.setFamilyName("Hansen");//$NON-NLS-1$
          }
          try (final BibAuthorBuilder author = authors.author()) {
            author.setPersonalName("Anne");//$NON-NLS-1$
            author.setFamilyName("Auger");//$NON-NLS-1$
          }
          try (final BibAuthorBuilder author = authors.author()) {
            author.setPersonalName("Steffen");//$NON-NLS-1$
            author.setFamilyName("Finck");//$NON-NLS-1$
          }
          try (final BibAuthorBuilder author = authors.author()) {
            author.setPersonalName("Raymond");//$NON-NLS-1$
            author.setFamilyName("Ros");//$NON-NLS-1$
          }
        }
        report.setTitle(//
            "Real-Parameter Black-Box Optimization Benchmarking: Experimental Setup");//$NON-NLS-1$
        try (final BibOrganizationBuilder pub = report.publisher()) {
          pub.setAddress("Orsay, France");//$NON-NLS-1$
          pub.setName(//
              "Universit\u00e9 Paris Sud, Institut National de Recherche en Informatique et en Automatique (INRIA) Futurs, \u00c9quipe TAO");//$NON-NLS-1$
        }
        try (final BibDateBuilder date = report.date()) {
          date.setYear(2012);
          date.setMonth(EBibMonth.MARCH);
          date.setDay(24);
        }
        report.setURL(//
            "http://coco.lri.fr/BBOB-downloads/download11.05/bbobdocexperiment.pdf");//$NON-NLS-1$
      }

      return bibBuilder.getResult();
    }
  }
}
