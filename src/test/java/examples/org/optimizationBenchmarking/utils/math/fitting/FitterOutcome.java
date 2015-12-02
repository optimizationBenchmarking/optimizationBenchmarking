package examples.org.optimizationBenchmarking.utils.math.fitting;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.fitting.spec.FunctionFitter;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.QuantileAggregate;

/** the outcome of multiple fitting applications */
public final class FitterOutcome {

  /** the function fitter */
  public final FunctionFitter fitter;

  /** the fitting outcomes */
  public final ArrayListView<FittingOutcome> outcomes;

  /** the path where the log files are */
  public final Path path;

  /** the median of the minima of all errors */
  public final Errors minErrors;
  /** the median of the medians of all errors */
  public final Errors medianErrors;
  /** the median of the maxima of all errors */
  public final Errors maxErrors;
  /** the median of the standard deviation of all errors */
  public final Errors stddevErrors;

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

    final QuantileAggregate[] med;
    int i;

    this.fitter = _fitter;
    this.outcomes = new ArrayListView<>(results);
    this.path = _path;

    med = new QuantileAggregate[4];
    for (i = med.length; (--i) >= 0;) {
      med[i] = new QuantileAggregate(0.5d);
    }

    for (final FittingOutcome o : results) {
      med[0].append(o.minErrors.quality);
      med[1].append(o.minErrors.rootMeanSquareError);
      med[2].append(o.minErrors.medianError);
      med[3].append(o.minErrors.runtime);
    }

    this.minErrors = new Errors(med[0].doubleValue(), med[1].doubleValue(),
        med[2].doubleValue(), med[3].doubleValue());

    for (final QuantileAggregate agg : med) {
      agg.reset();
    }

    for (final FittingOutcome o : results) {
      med[0].append(o.maxErrors.quality);
      med[1].append(o.maxErrors.rootMeanSquareError);
      med[2].append(o.maxErrors.medianError);
      med[3].append(o.maxErrors.runtime);
    }

    this.maxErrors = new Errors(med[0].doubleValue(), med[1].doubleValue(),
        med[2].doubleValue(), med[3].doubleValue());

    for (final QuantileAggregate agg : med) {
      agg.reset();
    }

    for (final FittingOutcome o : results) {
      med[0].append(o.medianErrors.quality);
      med[1].append(o.medianErrors.rootMeanSquareError);
      med[2].append(o.medianErrors.medianError);
      med[3].append(o.medianErrors.runtime);
    }

    this.medianErrors = new Errors(med[0].doubleValue(),
        med[1].doubleValue(), med[2].doubleValue(), med[3].doubleValue());

    for (final QuantileAggregate agg : med) {
      agg.reset();
    }

    for (final FittingOutcome o : results) {
      med[0].append(o.stddevErrors.quality);
      med[1].append(o.stddevErrors.rootMeanSquareError);
      med[2].append(o.stddevErrors.medianError);
      med[3].append(o.stddevErrors.runtime);
    }

    this.stddevErrors = new Errors(med[0].doubleValue(),
        med[1].doubleValue(), med[2].doubleValue(), med[3].doubleValue());
  }
}
