package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationBuilder;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluator;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/**
 * The evaluator main component.
 */
public final class Evaluator extends Tool implements IEvaluator {

  /** the parameter for parallel execution */
  @SuppressWarnings("nls")
  public static final String PARAM_PARALLEL = "parallel";

  /** create */
  Evaluator() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Evaluator"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final IEvaluationBuilder use() {
    return new _EvaluationBuilder();
  }

  /**
   * Execute the evaluator
   * 
   * @param args
   *          the command line arguments
   */
  public static final void main(final String[] args) {
    final Runnable task;
    final Configuration config;
    final int processors;
    final Logger logger;
    final ForkJoinPool pool;

    Configuration.setup(args);
    config = Configuration.getRoot();

    logger = config.getLogger(Configuration.PARAM_LOGGER,
        Logger.getGlobal());
    try {
      if ((logger != null) && (logger.isLoggable(Level.INFO))) {
        logger.info(//
            "Starting up the evaluator tool and configuring the evaluation job."); //$NON-NLS-1$
      }

      task = Evaluator.getInstance().use().configure(config).create();

      if ((logger != null) && (logger.isLoggable(Level.INFO))) {
        logger.info(//
            "Evaluation job has been configured and created."); //$NON-NLS-1$
      }

      processors = config.getInt(Evaluator.PARAM_PARALLEL, 1,//
          Runtime.getRuntime().availableProcessors(), 1);

      if (processors == 1) {

        if ((logger != null) && (logger.isLoggable(Level.INFO))) {
          logger.info(//
              "Begin executing the evaluation job in the current thread, i.e., on one processor."); //$NON-NLS-1$
        }
        task.run();
      } else {

        if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
          logger.config("Begin executing the evaluation job on " + //$NON-NLS-1$
              processors + " processors."); //$NON-NLS-1$
        }

        pool = new ForkJoinPool(processors);

        if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
          logger.config("We will use ForkJoinPool " + //$NON-NLS-1$
              pool + " for the job execution."); //$NON-NLS-1$
        }

        pool.execute(task);
        pool.shutdown();
        for (;;) {
          if (pool.awaitTermination(1, TimeUnit.DAYS)) {
            break;
          }
        }
      }

      if ((logger != null) && (logger.isLoggable(Level.INFO))) {
        logger.info(//
            "The evaluation job has finished (seemingly without encountering an unrecoverable error)."); //$NON-NLS-1$
      }

    } catch (final Throwable error) {
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        logger.log(Level.SEVERE,
            "An unrecoverable error has been detected.",//$NON-NLS-1$
            error);
      } else {
        error.printStackTrace();
      }
    }
  }

  /**
   * Get the shared instance of the evaluator tool.
   * 
   * @return the shared instance
   */
  public static final Evaluator getInstance() {
    return __EvaluatorLoader.INSTANCE;
  }

  /** the loader */
  private static final class __EvaluatorLoader {
    /** the shared instance */
    static final Evaluator INSTANCE = new Evaluator();
  }
}
