package examples.org.optimizationBenchmarking.utils.math.fitting;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.fitting.impl.ls.LSFitter;
import org.optimizationBenchmarking.utils.math.fitting.impl.opti.OptiFittingJobBuilder;
import org.optimizationBenchmarking.utils.math.fitting.spec.FittingJobBuilder;
import org.optimizationBenchmarking.utils.math.fitting.spec.FittingResult;
import org.optimizationBenchmarking.utils.math.fitting.spec.FunctionFitter;
import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.QuantileAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StableSum;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MultiplexingTextOutput;

/** The examples for fitting. */
public class FittingExamples {

  /** the fitters */
  public static final FunctionFitter[] FITTERS = { //
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
   * @param results
   *          the list to receive the fitting results
   * @throws IOException
   *           if i/o fails
   */
  private static final void __doFitter(final Path dest,
      final ArrayListView<FittingExampleDataset> data,
      final FunctionFitter fitter, final ITextOutput log,
      final ArrayList<double[]> results) throws IOException {
    final QuantileAggregate med;
    final StableSum sum;
    final Path folder;
    final String prefix;
    QuantileAggregate[] meds;
    FittingExampleDataset example;
    FittingJobBuilder builder;
    long start, total;
    FittingResult res;
    int problem, index, dim;
    double quality;
    double[] resx;

    med = new QuantileAggregate(0.5d);
    prefix = fitter.getClass().getSimpleName();
    folder = PathUtils.createPathInside(dest, prefix);
    Files.createDirectories(folder);

    sum = new StableSum();

    log.append(prefix);
    total = 0L;
    for (problem = 0; problem < data.size(); problem++) {
      med.reset();
      example = data.get(problem);

      meds = new QuantileAggregate[example.model.getParameterCount()];
      for (index = meds.length; (--index) >= 0;) {
        meds[index] = new QuantileAggregate(0.5d);
      }

      for (index = FittingExamples.TIMES; index > 0; index--) {
        builder = fitter.use().setFunctionToFit(example.model)
            .setPoints(example.data);

        if (builder instanceof OptiFittingJobBuilder) {
          ((OptiFittingJobBuilder) builder)
              .setMinCriticalPoints(example.sources * 3);
        }

        start = System.nanoTime();
        res = builder.create().call();
        total = Math.max(0L, (System.nanoTime() - start));

        resx = res.getFittedParameters();
        for (dim = resx.length; (--dim) >= 0;) {
          meds[dim].append(resx[dim]);
        }

        quality = example.evaluate(resx);
        sum.append(quality);
        med.append(quality);
        example.plot(
            PathUtils.createPathInside(folder,
                prefix + '_' + example.name + '_' + index + ".txt"), //$NON-NLS-1$
            res.getFittedParameters(), res.getQuality());
      }

      resx = new double[meds.length];
      for (dim = resx.length; (--dim) >= 0;) {
        resx[dim] = meds[dim].doubleValue();
      }
      results.add(resx);

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
  @SuppressWarnings("unchecked")
  public static final void main(final String[] args) throws IOException {
    final ArrayListView<FittingExampleDataset> list;
    final ArrayList<double[]>[] results;
    final Path dest;
    final ITextOutput out;
    int index;

    list = new FittingExampleDatasets().call();
    results = new ArrayList[FittingExamples.FITTERS.length];
    dest = PathUtils.createPathInside(PathUtils.getTempDir(), "results");//$NON-NLS-1$

    try (final OutputStream os = PathUtils.openOutputStream(//
        PathUtils.createPathInside(dest, "all.txt"))) {//$NON-NLS-1$
      try (final OutputStreamWriter osw = new OutputStreamWriter(os)) {
        try (final BufferedWriter bw = new BufferedWriter(osw)) {

          out = new MultiplexingTextOutput(
              AbstractTextOutput.wrap(System.out),
              AbstractTextOutput.wrap(bw));

          out.append("fitter");//$NON-NLS-1$
          for (final FittingExampleDataset set : list) {
            out.append('\t');
            out.append(set.name);
          }
          out.append("\tsum\ttime"); //$NON-NLS-1$
          out.appendLineBreak();
          out.flush();

          Files.createDirectories(dest);
          index = 0;
          for (final FunctionFitter fitter : FittingExamples.FITTERS) {
            FittingExamples.__doFitter(dest, list, fitter, out, //
                results[index++] = new ArrayList<>());
          }

          FittingExamples.__printResults(list, results, out);
        }
      }
    }
  }

  /**
   * print the results
   *
   * @param list
   *          the list of data sets
   * @param results
   *          the results
   * @param out
   *          the output device
   */
  private static final void __printResults(
      final ArrayListView<FittingExampleDataset> list,
      final ArrayList<double[]>[] results, final ITextOutput out) {
    final HashMap<Class<? extends ParametricUnaryFunction>, Integer> models;
    FittingExampleDataset example;
    Class<? extends ParametricUnaryFunction> clazz;
    int index;
    boolean first;

    models = new HashMap<>();
    for (final FittingExampleDataset example2 : list) {
      models.put(example2.model.getClass(),
          Integer.valueOf(example2.model.getParameterCount()));
    }

    for (final Map.Entry<Class<? extends ParametricUnaryFunction>, Integer> entry : models
        .entrySet()) {

      out.appendLineBreak();
      out.appendLineBreak();
      out.appendLineBreak();

      clazz = entry.getKey();
      out.append("### Fittings based on Model"); //$NON-NLS-1$
      out.append(clazz.getSimpleName());

      first = true;
      out.append("problem"); //$NON-NLS-1$
      for (final FunctionFitter fitter : FittingExamples.FITTERS) {
        if (first) {
          out.append('\t');
          first = false;
        } else {
          for (index = entry.getValue().intValue(); (--index) >= 0;) {
            out.append('\t');
          }
        }
        out.append(fitter.getClass().getSimpleName());
      }

      out.appendLineBreak();
      for (index = 0; index < list.size(); index++) {
        example = list.get(index);
        if (clazz.equals(example.model.getClass())) {
          out.append(example.name);
          for (final ArrayList<double[]> fr : results) {
            for (final double d : fr.get(index)) {
              out.append('\t');
              out.append(d);
            }
          }
          out.appendLineBreak();
        }
      }
    }
  }
}
