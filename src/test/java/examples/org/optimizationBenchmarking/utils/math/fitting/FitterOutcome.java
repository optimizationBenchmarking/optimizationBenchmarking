package examples.org.optimizationBenchmarking.utils.math.fitting;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.fitting.spec.FunctionFitter;

/** the outcome of multiple fitting applications */
public final class FitterOutcome {

  /** the function fitter */
  public final FunctionFitter fitter;

  /** the fitting outcomes */
  public final ArrayListView<FittingOutcome> outcomes;

  /** the path where the log files are */
  public final Path path;

  /**
   * create the fitter outcome
   *
   * @param _fitter
   *          the fitting example data set
   * @param results
   *          the results
   * @param _path
   *          the path
   */
  FitterOutcome(final FunctionFitter _fitter,
      final FittingOutcome[] results, final Path _path) {
    super();
    this.fitter = _fitter;
    this.outcomes = new ArrayListView<>(results);
    this.path = _path;
  }
}
