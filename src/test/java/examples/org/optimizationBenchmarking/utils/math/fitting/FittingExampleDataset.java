package examples.org.optimizationBenchmarking.utils.math.fitting;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StableSum;

/** An example data set for the fitting of data. */
public class FittingExampleDataset {

  /** the name of the example data set */
  public final String name;

  /** the data matrix */
  public final IMatrix data;

  /** the model to be fitted */
  public final ParametricUnaryFunction model;

  /** the number of sources this data set is composed from */
  public final int sources;

  /**
   * create the fitting example
   *
   * @param _name
   *          the name of the example data set
   * @param _data
   *          the data matrix
   * @param _model
   *          the model to be fitted
   * @param _sources
   *          the number of sources this data set is composed from
   */
  public FittingExampleDataset(final String _name, final IMatrix _data,
      final ParametricUnaryFunction _model, final int _sources) {
    super();

    this.data = _data;
    this.model = _model;
    this.sources = _sources;
    this.name = _name;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return this.name + ": " + //$NON-NLS-1$
        this.model.getClass().getSimpleName() + " on " + //$NON-NLS-1$
        this.data.m() + " points"; //$NON-NLS-1$
  }

  /**
   * Plot the data and the fitting to the given file
   *
   * @param dest
   *          the destination path
   * @param fitting
   *          the fitting
   * @param quality
   *          the quality of the fitting
   * @throws IOException
   *           if i/o fails
   */
  public final void plot(final Path dest, final double[] fitting,
      final double quality) throws IOException {
    final StableSum sum;
    final int size;
    int index;
    double x, y, calc;

    try (final OutputStream os = PathUtils.openOutputStream(dest)) {
      try (final OutputStreamWriter osw = new OutputStreamWriter(os)) {
        try (final BufferedWriter bw = new BufferedWriter(osw)) {
          sum = new StableSum();
          size = this.data.m();
          for (index = 0; index < size; index++) {
            bw.write(Double.toString(x = this.data.getDouble(index, 0)));
            bw.write('\t');
            bw.write(Double.toString(y = this.data.getDouble(index, 1)));
            bw.write('\t');
            bw.write(Double.toString(calc = this.model.value(x, fitting)));
            calc = Math.abs(calc - y);
            y = Math.abs(y);
            if (y > 0d) {
              calc /= y;
            }
            sum.append(calc);
            bw.newLine();
          }

          bw.write('#');
          bw.newLine();
          bw.write('#');
          bw.newLine();
          bw.write("# fitting:"); //$NON-NLS-1$
          for (final double d : fitting) {
            bw.write('\t');
            bw.write(Double.toString(d));
          }
          bw.newLine();
          bw.write("# quality:\t"); //$NON-NLS-1$
          bw.write(Double.toString(quality));
          bw.newLine();
          bw.write("# error sum:\t"); //$NON-NLS-1$
          bw.write(Double.toString(sum.doubleValue()));
        }
      }
    }
  }

  /**
   * Get the error sum for a particular model parameter setting
   * 
   * @param parameters
   *          the model parameters
   * @return the error sum
   */
  public final double evaluate(final double[] parameters) {
    final StableSum sum;
    int i;

    sum = new StableSum();
    for (i = this.data.m(); (--i) >= 0;) {
      sum.append(
          Math.abs(this.model.value(this.data.getDouble(i, 0), parameters)
              - this.data.getDouble(i, 1)));
    }
    return sum.doubleValue();
  }
}
