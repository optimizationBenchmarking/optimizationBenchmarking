package org.optimizationBenchmarking.experimentation.attributes.modeling;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.utils.collections.ImmutableAssociation;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingQualityMeasure;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingResult;

/**
 * A dimension relationship model is a function which relates the values of
 * one dimension to the values of another. This attribute returns such a
 * fitting plus a quality metric which can be used to assess the quality of
 * other fittings on the same data. This combined result is never stored.
 * However, the fitting will be preserved as {@link DimensionRelationship}
 * attribute.
 */
public final class DimensionRelationshipWithMetric extends
    _ModelAttributeBase<ImmutableAssociation<IFittingResult, IFittingQualityMeasure>> {

  /**
   * create the model attribute base.
   *
   * @param dimX
   *          the model input dimension
   * @param dimY
   *          the model output dimension
   */
  public DimensionRelationshipWithMetric(final IDimension dimX,
      final IDimension dimY) {
    super(EAttributeType.NEVER_STORED, dimX, dimY, 332287);
  }

  /** {@inheritDoc} */
  @Override
  protected final ImmutableAssociation<IFittingResult, IFittingQualityMeasure> compute(
      final IInstanceRuns data, final Logger logger) {
    final ImmutableAssociation<IFittingResult, IFittingQualityMeasure> res;
    IFittingResult fitting1, fitting2;
    _DimensionRelationshipViaSideEffect side;

    side = new _DimensionRelationshipViaSideEffect(this);
    try {
      fitting1 = side.get(data, logger);
    } catch (@SuppressWarnings("unused") final Throwable expected) {
      // we expect an error here, since {@code null} is no valid attribute
      // value
      fitting1 = null;
    }

    if (fitting1 != null) {
      // Oh, a fitting has already been computed before. We just need to
      // set up the quality measure.
      return new ImmutableAssociation<>(fitting1, //
          this._getMeasure(this._getDataMatrix(data)));
    }

    // No fitting has been computed yet. Let's do it.

    res = this._compute(data, logger);

    // Since computing the fitting is very expensive, we try to preserve
    // it. Unfortunately, this must be done via some side-effects. To sneak
    // it under DimensionRelationship.
    fitting1 = res.getKey();

    side.m_result = res.getKey();
    fitting2 = side.get(data, logger);
    // WARNING: Comment line below back in if attribute changed to
    // TEMPORARILY_STORED
    // side.m_result = null;
    side = null;

    if (fitting1 != fitting2) {
      // Oh, there was already a fitting ... then use the stored one
      return new ImmutableAssociation<>(fitting2, res.getValue());
    }
    return res;
  }
}
