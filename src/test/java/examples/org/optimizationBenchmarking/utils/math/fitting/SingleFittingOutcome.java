package examples.org.optimizationBenchmarking.utils.math.fitting;

import java.nio.file.Path;

/** The result of a fitting process. */
public final class SingleFittingOutcome {

  /** the result of the fitting process */
  public final double[] result;
  /** the associated file */
  public final Path file;
  /** the fitting quality */
  public final Errors errors;

  /**
   * create the fitting outcome
   *
   * @param _result
   *          the result of the fitting process
   * @param _file
   *          the associated file
   * @param _errors
   *          the errors the weighted median error
   */
  SingleFittingOutcome(final double[] _result, final Path _file,
      final Errors _errors) {
    super();
    this.result = _result;
    this.file = _file;
    this.errors = _errors;
  }
}
