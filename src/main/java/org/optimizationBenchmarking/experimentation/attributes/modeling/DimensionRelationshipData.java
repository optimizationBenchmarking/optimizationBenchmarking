package org.optimizationBenchmarking.experimentation.attributes.modeling;

import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingQualityMeasure;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingResult;

/**
 * Data describing a dimension relationship as well as intermediate
 * results.
 */
public final class DimensionRelationshipData {

  /** the fitting result */
  public final IFittingResult fitting;

  /** the fitting quality measure */
  public final IFittingQualityMeasure measure;

  /**
   * Create the dimension relationship data object
   *
   * @param _fitting
   *          the fitting result
   * @param _measure
   *          the fitting quality measure
   */
  DimensionRelationshipData(final IFittingResult _fitting,
      final IFittingQualityMeasure _measure) {
    super();
    this.fitting = _fitting;
    this.measure = _measure;
  }
}
