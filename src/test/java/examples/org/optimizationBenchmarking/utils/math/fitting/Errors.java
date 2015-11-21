package examples.org.optimizationBenchmarking.utils.math.fitting;

/** measured errors */
public final class Errors {

  /** the fitting quality */
  public final double quality;
  /** the mean square error */
  public final double rootMeanSquareError;
  /** the median error */
  public final double medianError;
  /** the weighted mean square error */
  public final double weightedRootMeanSquareError;
  /** the weighted median error */
  public final double weightedMedianError;

  /**
   * create the errors
   *
   * @param _quality
   *          the fitting quality
   * @param _rootMeanSquareError
   *          the mean square error
   * @param _medianError
   *          the median error
   * @param _weightedRootMeanSquareError
   *          the weighted mean square error
   * @param _weightedMedianError
   *          the weighted median error
   */
  Errors(final double _quality, final double _rootMeanSquareError,
      final double _medianError, final double _weightedRootMeanSquareError,
      final double _weightedMedianError) {
    super();
    this.quality = _quality;
    this.rootMeanSquareError = _rootMeanSquareError;
    this.medianError = _medianError;
    this.weightedRootMeanSquareError = _weightedRootMeanSquareError;
    this.weightedMedianError = _weightedMedianError;
  }
}
