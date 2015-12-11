package org.optimizationBenchmarking.utils.ml.fitting.multi;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.ml.fitting.impl.abstr.FittingJobBuilder;
import org.optimizationBenchmarking.utils.ml.fitting.quality.FittingQualityMeasure;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingQualityMeasure;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingResult;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFunctionFitter;
import org.optimizationBenchmarking.utils.ml.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.parallel.Execute;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/**
 * A fitting job which tries to fit multiple models to a given data set and
 * picks the best result.
 */
public final class MultiFittingJob extends ToolJob
    implements Callable<IFittingResult> {

  /** the fitters */
  private final Iterable<IFunctionFitter> m_fitters;

  /** the {@code x-y}-coordinate pairs */
  private final IMatrix m_points;

  /** a functions to fit */
  private final Iterable<ParametricUnaryFunction> m_functions;

  /** the fitting quality measure */
  private final IFittingQualityMeasure m_measure;

  /**
   * create
   *
   * @param builder
   *          the owning builder
   */
  MultiFittingJob(final MultiFittingJobBuilder builder) {
    super(builder);

    FittingQualityMeasure.validateData(//
        this.m_points = builder.m_points);
    MultiFittingJobBuilder._validateFunctions(//
        this.m_functions = builder.m_functions);
    MultiFittingJobBuilder._validateFitters(//
        this.m_fitters = builder.m_fitters);
    FittingJobBuilder.validateMeasure(//
        this.m_measure = builder.m_measure);
  }

  /** {@inheritDoc} */
  @Override
  public final IFittingResult call() {
    final ArrayList<Future<IFittingResult>> futures;
    final Logger logger;
    final int size;
    final IFittingResult[] results;
    IFittingResult best;
    double bestQuality, curQuality;
    int bestLength, curLength;

    futures = new ArrayList<>();
    logger = this.getLogger();

    for (final IFunctionFitter fitter : this.m_fitters) {
      if (fitter == null) {
        throw new IllegalArgumentException(
            "Function fitter cannot be null."); //$NON-NLS-1$
      }
      for (final ParametricUnaryFunction function : this.m_functions) {
        FittingJobBuilder.validateFunction(function);
        futures.add(Execute.parallel(fitter.use().setLogger(logger)//
            .setFunctionToFit(function)//
            .setQualityMeasure(this.m_measure)//
            .setPoints(this.m_points)//
            .create()));
      }
    }

    size = futures.size();
    if (size <= 0) {
      throw new IllegalArgumentException(//
          "There must be at least one function fitter and at least one function to fit."); //$NON-NLS-1$
    }

    results = new IFittingResult[size];
    Execute.join(futures, results, 0, false);

    best = null;
    bestQuality = Double.POSITIVE_INFINITY;
    bestLength = Integer.MAX_VALUE;

    for (final IFittingResult cur : results) {
      if (cur == null) {
        continue;
      }
      curQuality = cur.getQuality();
      curLength = cur.getFittedParametersRef().length;
      if ((best == null) || //
          (MathUtils.isFinite(curQuality) && (//
          (curQuality < bestQuality) || //
              ((curQuality == bestQuality)
                  && (curLength < bestLength))))) {
        best = cur;
        bestQuality = curQuality;
        bestLength = curLength;
      }
    }

    if ((best != null) && (MathUtils.isFinite(bestQuality))) {
      return best;
    }
    throw new IllegalArgumentException(//
        "No function could successfully be fitted to the data."); //$NON-NLS-1$
  }
}
