package examples.org.optimizationBenchmarking.utils.ml.fitting;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StableSum;
import org.optimizationBenchmarking.utils.ml.fitting.spec.ParametricUnaryFunction;

/** An example data set for the fitting of data. */
public class FittingExampleDataset
    implements Comparable<FittingExampleDataset> {

  /** the name of the example data set */
  public final String name;

  /** the data matrix */
  public final IMatrix data;

  /** the model to be fitted */
  public final ArrayListView<ParametricUnaryFunction> models;

  /** the number of sources this data set is composed from */
  public final int sources;

  /**
   * create the fitting example
   *
   * @param _name
   *          the name of the example data set
   * @param _data
   *          the data matrix
   * @param _models
   *          the models to be fitted
   * @param _sources
   *          the number of sources this data set is composed from
   */
  public FittingExampleDataset(final String _name, final IMatrix _data,
      final ArrayListView<ParametricUnaryFunction> _models,
      final int _sources) {
    super();

    this.data = _data;
    this.models = _models;
    this.sources = _sources;
    this.name = _name;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return this.name + ": " + //$NON-NLS-1$
        this.models.toString() + " on " + //$NON-NLS-1$
        this.data.m() + " points"; //$NON-NLS-1$
  }

  /**
   * Plot the data and the fitting to the given file
   *
   * @param dest
   *          the destination path
   * @param model
   *          the model to plot
   * @param fitting
   *          the fitting
   * @param quality
   *          the quality of the fitting
   * @throws IOException
   *           if i/o fails
   */
  public final void plot(final Path dest,
      final ParametricUnaryFunction model, final double[] fitting,
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
            bw.write(Double.toString(calc = model.value(x, fitting)));
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

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final FittingExampleDataset o) {
    return ((o == this) ? 0 : (this.name.compareTo(o.name)));
  }
}
