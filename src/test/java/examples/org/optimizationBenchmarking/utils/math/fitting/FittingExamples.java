package examples.org.optimizationBenchmarking.utils.math.fitting;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.fitting.impl.ls.LSFitter;
import org.optimizationBenchmarking.utils.math.fitting.impl.opti.OptiFittingJobBuilder;
import org.optimizationBenchmarking.utils.math.fitting.spec.FittingJobBuilder;
import org.optimizationBenchmarking.utils.math.fitting.spec.FittingResult;
import org.optimizationBenchmarking.utils.math.fitting.spec.FunctionFitter;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.QuantileAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StableSum;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MultiplexingTextOutput;

/** The examples for fitting. */
public class FittingExamples {

  /** the fitters */
  public static final FunctionFitter[] FITTERS = {//
  LSFitter.getInstance(), };

  /** fit each data set this often */
  private static final int TIMES = 5;

  /**
   * print the examples of a fitter
   *
   * @param data
   *          the data
   * @param dest
   *          the destination path
   * @param fitter
   *          the fitter
   * @param log
   *          the text output destination for the logging
   * @throws IOException
   *           if i/o fails
   */
  private static final void __doFitter(final Path dest,
      final ArrayListView<FittingExampleDataset> data,
      final FunctionFitter fitter, final ITextOutput log)
      throws IOException {
    final QuantileAggregate med;
    final StableSum sum;
    final Path folder;
    final String prefix;
    FittingJobBuilder builder;
    long start, total;
    FittingResult res;
    int problem, index;

    med = new QuantileAggregate(0.5d);
    prefix = fitter.getClass().getSimpleName();
    folder = PathUtils.createPathInside(dest, prefix);
    Files.createDirectories(folder);

    sum = new StableSum();

    log.append(prefix);
    problem = 0;
    total = 0L;
    for (final FittingExampleDataset example : data) {
      med.reset();
      problem++;

      for (index = FittingExamples.TIMES; index > 0; index--) {
        start = System.nanoTime();

        builder = fitter.use().setFunctionToFit(example.model)
            .setPoints(example.data);

        if (builder instanceof OptiFittingJobBuilder) {
          ((OptiFittingJobBuilder) builder)
              .setMinCriticalPoints(example.sources * 3);
        }

        res = builder.create().call();
        total = Math.max(0L, (System.nanoTime() - start));
        sum.append(res.getQuality());
        med.append(res.getQuality());
        example.plot(
            PathUtils.createPathInside(folder, prefix + '_' + problem
                + '_' + index + ".txt"), //$NON-NLS-1$
            res.getFittedParameters(), res.getQuality());
      }

      log.append('\t');
      log.append(med.doubleValue());
      log.flush();
    }

    log.append('\t');
    log.append(sum.doubleValue());
    log.append('\t');
    log.append(total);
    log.appendLineBreak();
    log.flush();
  }

  /**
   * The main entry point
   *
   * @param args
   *          the arguments: ignored
   * @throws IOException
   *           if i/o fails
   */
  public static final void main(final String[] args) throws IOException {
    final ArrayListView<FittingExampleDataset> list;
    final Path dest;
    final ITextOutput out;
    int idx;

    list = new FittingExampleDatasets().call();

    dest = PathUtils.createPathInside(PathUtils.getTempDir(), "results");//$NON-NLS-1$

    try (final OutputStream os = PathUtils.openOutputStream(//
        PathUtils.createPathInside(dest, "all.txt"))) {//$NON-NLS-1$
      try (final OutputStreamWriter osw = new OutputStreamWriter(os)) {
        try (final BufferedWriter bw = new BufferedWriter(osw)) {

          out = new MultiplexingTextOutput(
              AbstractTextOutput.wrap(System.out),
              AbstractTextOutput.wrap(bw));

          out.append("fitter");//$NON-NLS-1$
          idx = 0;
          for (final FittingExampleDataset set : list) {
            out.append('\t');
            out.append(++idx);
            out.append('_');
            out.append(set.model.getClass().getSimpleName());
          }
          out.append("\tsum\ttime"); //$NON-NLS-1$
          out.appendLineBreak();
          out.flush();

          Files.createDirectories(dest);
          for (final FunctionFitter fitter : FittingExamples.FITTERS) {
            FittingExamples.__doFitter(dest, list, fitter, out);
          }
        }
      }
    }
  }

}
