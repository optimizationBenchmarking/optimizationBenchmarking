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
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.processing.ColumnTransformedMatrix;
import org.optimizationBenchmarking.utils.math.statistics.parameters.Median;
import org.optimizationBenchmarking.utils.math.statistics.parameters.StatisticalParameter;
import org.optimizationBenchmarking.utils.math.statistics.parameters.StatisticalParameterParser;
import org.optimizationBenchmarking.utils.math.text.DefaultParameterRenderer;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * The 2D statistical parameter curve, which can be computed over instance
 * runs, experiments, and experiment sets
 */
public final class Aggregation2D extends FunctionAttribute<IElementSet> {

  /**
   * The default parameter for the aggregate to be computed over the
   * instance runs.
   */
  public static final String PRIMARY_AGGREGATE_PARAM = "aggregate"; //$NON-NLS-1$

  /**
   * The default parameter for the aggregate to be used to aggregate the
   * results of the instance runs in an experiment or experiment set.
   */
  public static final String SECONDARY_AGGREGATE_PARAM = "secondaryAggregate"; //$NON-NLS-1$

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
    textOut.append(" represents the "); //$NON-NLS-1$
    this.m_param.printLongName(textOut, use);
    textOut.append(" of the "); //$NON-NLS-1$
    yIn.printShortName(textOut, use);
    textOut.append(' ');
    textOut.append(" for a given ellapsed runtime measured in "); //$NON-NLS-1$
    xIn.getDimension().printShortName(textOut, use);

    if (yOut.isIdentityTransformation()) {
      textOut.append(". The "); //$NON-NLS-1$
      this.m_param.printLongName(textOut, use);
    } else {
      textOut.append(//
          ". We do not use these values directly, but instead compute "); //$NON-NLS-1$
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
        " is always computed over the runs of an experiment for a given benchmark instance. If runs for multiple instances are available, we aggregate these "); //$NON-NLS-1$
    if (yOut.isIdentityTransformation()) {
      this.m_param.printLongName(textOut, use);
    } else {
      textOut.append("result"); //$NON-NLS-1$
    }
    textOut.append("s by computing their "); //$NON-NLS-1$
    this.m_second.printLongName(textOut, use);
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

    return use;
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

          return this.m_param.aggregate2D(matrices, 0, 1, yOutputFunction);
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

    return this.m_second.aggregate2D(matrices, 0, 1, Identity.INSTANCE);
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

    return this.m_second.aggregate2D(matrices, 0, 1, Identity.INSTANCE);
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
    return ((this.m_second.getShortName() + ' ') + //
    this.m_param.getShortName());
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
    x = config.get(FunctionAttribute.X_AXIS_PARAM, dimParser, null);
    if (x == null) {//
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

    first = config.get(Aggregation2D.PRIMARY_AGGREGATE_PARAM,
        StatisticalParameterParser.getInstance(), null);
    if (first == null) {//
      throw new IllegalArgumentException(
          "Must specify a statistical parameter (aggregate) to be computed, via parameter '" //$NON-NLS-1$
              + Aggregation2D.PRIMARY_AGGREGATE_PARAM + '\'');
    }

    second = config.get(Aggregation2D.SECONDARY_AGGREGATE_PARAM,
        StatisticalParameterParser.getInstance(), Median.INSTANCE);

    return new Aggregation2D(x, yIn, yOut, first, second);
  }
}
