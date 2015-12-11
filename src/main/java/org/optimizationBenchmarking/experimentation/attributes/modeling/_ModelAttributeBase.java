package org.optimizationBenchmarking.experimentation.attributes.modeling;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.collections.ImmutableAssociation;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix1D;
import org.optimizationBenchmarking.utils.ml.fitting.impl.DefaultFunctionFitter;
import org.optimizationBenchmarking.utils.ml.fitting.models.LogisticModelOverLogX;
import org.optimizationBenchmarking.utils.ml.fitting.models.LogisticModelWithOffsetOverLogX;
import org.optimizationBenchmarking.utils.ml.fitting.models.QuadraticModel;
import org.optimizationBenchmarking.utils.ml.fitting.multi.MultiFunctionFitter;
import org.optimizationBenchmarking.utils.ml.fitting.quality.WeightedRootMeanSquareError;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingQualityMeasure;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingResult;
import org.optimizationBenchmarking.utils.ml.fitting.spec.ParametricUnaryFunction;

/**
 * The base class for model attributes.
 *
 * @param <R>
 *          the result type
 */
abstract class _ModelAttributeBase<R> extends Attribute<IInstanceRuns, R> {

  /** The models attempted for time-objective relationships */
  private static final ArrayListView<ParametricUnaryFunction> TIME_OBJECTIVE_MODELS = //
  new ArrayListView<>(new ParametricUnaryFunction[] { //
      new LogisticModelOverLogX(), //
      new LogisticModelWithOffsetOverLogX(), //
      new QuadraticModel(),//
  });

  /** The models attempted for relationships of equal-type dimensions */
  private static final ArrayListView<ParametricUnaryFunction> EQUAL_TYPE_MODELS = //
  new ArrayListView<>(new ParametricUnaryFunction[] { //
      new QuadraticModel(),//
  });

  /** the dimension to be used as model input */
  private final int m_dimX;
  /** the dimension to be used as model output */
  private final int m_dimY;
  /**
   * the dimension state: bit 0: x-dim is time? bit 1: y-dim is time, rest:
   * distinguish classes
   */
  private final int m_dimTypesAndClazz;

  /**
   * create the model attribute base.
   *
   * @param type
   *          the attribute type
   * @param dimX
   *          the model input dimension
   * @param dimY
   *          the model output dimension
   * @param dimTypesAndClass
   *          the dimension state
   */
  _ModelAttributeBase(final EAttributeType type, final int dimX,
      final int dimY, final int dimTypesAndClass) {
    super(type);

    if (((dimTypesAndClass & 2) != 0) && ((dimTypesAndClass & 1) == 0)) {
      throw new IllegalArgumentException(//
          "Only time-objective, objective-objective, and time-time relatioships can be modeled, but you specified an objective-time relationship."); //$NON-NLS-1$
    }

    this.m_dimX = dimX;
    this.m_dimY = dimY;
    this.m_dimTypesAndClazz = dimTypesAndClass;
  }

  /**
   * create the model attribute base.
   *
   * @param type
   *          the attribute type
   * @param clazz
   *          the class id
   * @param copy
   *          the attribute to copy
   */
  _ModelAttributeBase(final EAttributeType type, final int clazz,
      final _ModelAttributeBase<?> copy) {
    this(type, copy.m_dimX, copy.m_dimY,
        ((copy.m_dimTypesAndClazz & 3) | (clazz << 2)));
  }

  /**
   * create the model attribute base.
   *
   * @param type
   *          the attribute type
   * @param dimX
   *          the model input dimension
   * @param dimY
   *          the model output dimension
   * @param clazz
   *          the class id
   */
  _ModelAttributeBase(final EAttributeType type, final IDimension dimX,
      final IDimension dimY, final int clazz) {
    this(type, dimX.getIndex(), dimY.getIndex(), //
        ((dimX.getDimensionType().isTimeMeasure() ? 1 : 0) | //
            (dimY.getDimensionType().isTimeMeasure() ? 1 : 0) | //
            (clazz << 2)));
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_dimX), //
            HashUtils.hashCode(this.m_dimY)), //
        HashUtils.hashCode(this.m_dimTypesAndClazz));//
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    _ModelAttributeBase<?> eq;

    if (o == this) {
      return true;
    }
    if (o instanceof _ModelAttributeBase) {
      eq = ((_ModelAttributeBase<?>) o);
      return ((this.m_dimX == eq.m_dimX) && //
          (this.m_dimY == eq.m_dimY) && //
          (this.m_dimTypesAndClazz == eq.m_dimTypesAndClazz));
    }
    return false;
  }

  /**
   * compute the data matrix.
   *
   * @param data
   *          the data
   * @return the matrix
   */
  final DoubleMatrix1D _getDataMatrix(final IInstanceRuns data) {
    final ArrayListView<? extends IRun> rawData;
    final double[] matrixData;

    int m, i, j;

    rawData = data.getData();
    m = 0;
    for (final IRun run : rawData) {
      m += run.m();
    }

    i = (m << 1);
    matrixData = new double[i];
    for (final IRun run : rawData) {
      for (j = run.m(); (--j) >= 0;) {
        matrixData[--i] = run.getDouble(j, this.m_dimY);
        matrixData[--i] = run.getDouble(j, this.m_dimX);
      }
    }

    if (i != 0) {
      throw new IllegalStateException(
          "The lengths of the runs changed???"); //$NON-NLS-1$
    }

    return new DoubleMatrix1D(matrixData, m, 2);
  }

  /**
   * get the fitting quality measure
   *
   * @param matrix
   *          the matrix
   * @return the measure
   */
  final IFittingQualityMeasure _getMeasure(final DoubleMatrix1D matrix) {
    return new WeightedRootMeanSquareError(matrix);
  }

  /**
   * Perform the computation
   *
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @return the result
   */
  final ImmutableAssociation<IFittingResult, IFittingQualityMeasure> _compute(
      final IInstanceRuns data, final Logger logger) {
    final DoubleMatrix1D matrix;
    final IFittingQualityMeasure measure;

    matrix = this._getDataMatrix(data);
    measure = this._getMeasure(matrix);

    return new ImmutableAssociation<>(//
        MultiFunctionFitter.getInstance().use()//
            .setLogger(logger)//
            .setFitters(DefaultFunctionFitter.getAvailableInstance())//
            .setFunctions(((this.m_dimTypesAndClazz & 1) == //
            ((this.m_dimTypesAndClazz & 2) >>> 1))//
                ? _ModelAttributeBase.EQUAL_TYPE_MODELS//
                : _ModelAttributeBase.TIME_OBJECTIVE_MODELS)//
            .setQualityMeasure(measure)//
            .setPoints(matrix).create().call(), //
        measure);
  }
}
