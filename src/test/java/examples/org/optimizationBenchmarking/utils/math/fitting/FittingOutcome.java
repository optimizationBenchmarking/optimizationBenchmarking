package examples.org.optimizationBenchmarking.utils.math.fitting;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.QuantileAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StandardDeviationAggregate;

/** the outcome of multiple fitting applications */
public final class FittingOutcome {

  /** the example data set */
  public final FittingExampleDataset example;

  /** the single fitting outcomes */
  public final ArrayListView<SingleFittingOutcome> outcomes;

  /** the path where the log files are */
  public final Path path;

  /** the minimal value of each parameter */
  public final double[] min;
  /** the median value of each parameter */
  public final double[] median;
  /** the maximal value of each parameter */
  public final double[] max;
  /** the standard deviation of each parameter */
  public final double[] stddev;

  /** the minima of all errors */
  public final Errors minErrors;
  /** the medians of all errors */
  public final Errors medianErrors;
  /** the maxima of all errors */
  public final Errors maxErrors;
  /** the standard deviation of all errors */
  public final Errors stddevErrors;

  /**
   * create the fitting outcome
   *
   * @param _example
   *          the fitting example data set
   * @param results
   *          the results
   * @param _path
   *          the path
   */
  FittingOutcome(final FittingExampleDataset _example,
      final SingleFittingOutcome[] results, final Path _path) {
    super();

    int i;

    final StandardDeviationAggregate[] _stddev;
    final QuantileAggregate[] med;

    this.example = _example;
    this.path = _path;
    this.outcomes = new ArrayListView<>(results);

    i = _example.model.getParameterCount();
    this.min = new double[i];
    this.max = new double[i];
    this.median = new double[i];
    this.stddev = new double[i];

    i = Math.max(_example.model.getParameterCount(), 4);

    _stddev = new StandardDeviationAggregate[i];
    med = new QuantileAggregate[i];
    for (; (--i) >= 0;) {
      _stddev[i] = new StandardDeviationAggregate();
      med[i] = new QuantileAggregate(0.5d);
    }

    for (final SingleFittingOutcome o : results) {
      for (i = o.result.length; (--i) >= 0;) {
        _stddev[i].append(o.result[i]);
        med[i].append(o.result[i]);
      }
    }

    for (i = this.min.length; (--i) >= 0;) {
      this.min[i] = _stddev[i].getMinimum().doubleValue();
      this.max[i] = _stddev[i].getMaximum().doubleValue();
      this.stddev[i] = _stddev[i].doubleValue();
      _stddev[i].reset();
      this.median[i] = med[i].doubleValue();
      med[i].reset();
    }

    for (final SingleFittingOutcome o : results) {
      _stddev[0].append(o.errors.quality);
      med[0].append(o.errors.quality);
      _stddev[1].append(o.errors.rootMeanSquareError);
      med[1].append(o.errors.rootMeanSquareError);
      _stddev[2].append(o.errors.medianError);
      med[2].append(o.errors.medianError);
      _stddev[3].append(o.errors.runtime);
      med[3].append(o.errors.runtime);
    }

    this.minErrors = new Errors(_stddev[0].getMinimum().doubleValue(),
        _stddev[1].getMinimum().doubleValue(),
        _stddev[2].getMinimum().doubleValue(),
        _stddev[3].getMinimum().doubleValue());
    this.maxErrors = new Errors(_stddev[0].getMaximum().doubleValue(),
        _stddev[1].getMaximum().doubleValue(),
        _stddev[2].getMaximum().doubleValue(),
        _stddev[3].getMaximum().doubleValue());
    this.stddevErrors = new Errors(_stddev[0].doubleValue(),
        _stddev[1].doubleValue(), _stddev[2].doubleValue(),
        _stddev[3].doubleValue());
    this.medianErrors = new Errors(med[0].doubleValue(),
        med[1].doubleValue(), med[2].doubleValue(), med[3].doubleValue());
  }
}
