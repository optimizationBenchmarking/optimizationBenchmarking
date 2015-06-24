package org.optimizationBenchmarking.experimentation.evaluation;

import java.io.PrintStream;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.Evaluator;
import org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluationBuilder;
import org.optimizationBenchmarking.utils.MemoryUtils;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.SynchronizedIO;

/** The main entry point for evaluation. */
public final class Main {

  /** should we execute quietly? */
  public static final String PARAM_QUIET = "quiet"; //$NON-NLS-1$

  /**
   * The main method.
   *
   * @param args
   *          the command line arguments
   */
  public static final void main(final String[] args) {
    final Runnable evaluation;
    final int processors;
    final Logger logger;
    final ForkJoinPool pool;
    final boolean printInfo;
    Configuration config;
    Evaluator evaluator;
    IEvaluationBuilder builder;
    int terminated;

    Configuration.setup(args);
    config = Configuration.getRoot();

    evaluator = Evaluator.getInstance();

    logger = Configuration.getGlobalLogger();

    printInfo = (!(config.getBoolean(Main.PARAM_QUIET, false)));
    if (printInfo) {
      new __Begin(logger, evaluator).run();
    }

    try {

      if (logger != null) {
        if (logger.isLoggable(Level.INFO)) {
          logger.info(//
              "Starting up the evaluator tool and configuring the evaluation job."); //$NON-NLS-1$
          if (logger.isLoggable(Level.CONFIG)) {
            logger.config(//
                "The root configuration provided via the command line is " + //$NON-NLS-1$
                    config.toString());
          }
        }
      }

      builder = evaluator.use();
      evaluator = null;

      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine(//
            "Evaluation job builder has been created and will now be provided with configuration."); //$NON-NLS-1$
      }

      builder.configure(config);

      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine(//
            "Evaluation job builder has been configured, evaluation job will now be created. The configuration object itself may be cached and only evaluated once the job is actually running."); //$NON-NLS-1$
      }

      evaluation = builder.create();
      builder = null;

      if ((logger != null) && (logger.isLoggable(Level.INFO))) {
        logger.info(//
            "Evaluation job has been configured and created."); //$NON-NLS-1$
      }

      processors = config.getInt(Evaluator.PARAM_PARALLEL, 1,//
          Math.max(1, Runtime.getRuntime().availableProcessors()), 1);
      config = null;

      if (processors <= 1) {

        if ((logger != null) && (logger.isLoggable(Level.INFO))) {
          logger.info(//
              "Begin executing the evaluation job in the current thread, '" + //$NON-NLS-1$
                  Thread.currentThread() + //
                  "', i.e., on one processor."); //$NON-NLS-1$
        }

        evaluation.run();

        if ((logger != null) && (logger.isLoggable(Level.INFO))) {
          logger.info(//
              "The evaluation job has finished (seemingly successfully) in thread " //$NON-NLS-1$
                  + Thread.currentThread());
        }
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

        pool.execute(evaluation);
        pool.shutdown();

        if ((logger != null) && (logger.isLoggable(Level.FINE))) {
          logger.fine(//
              "Evaluation task successfully submitted to pool " //$NON-NLS-1$
                  + pool);
        }

        for (;;) {
          try {
            terminated = (pool.awaitTermination(1L, TimeUnit.DAYS) ? 0 : 1);
          } catch (final InterruptedException inter) {
            if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
              logger
                  .log(
                      Level.WARNING,
                      "Caught InterruptedException while waiting for evaluation to finish.", //$NON-NLS-1$
                      inter);
            }
            terminated = 3;
          }

          if (terminated == 0) {
            break;
          }

          MemoryUtils.fullGC();

          if ((terminated == 1) && (logger != null)
              && (logger.isLoggable(Level.INFO))) {
            logger.info(//
                "Timeout of 1 day ellapsed before " //$NON-NLS-1$
                    + pool + //
                    " has terminated. The job takes quite long, it seems.");//$NON-NLS-1$
          }
        }

        if ((logger != null) && (logger.isLoggable(Level.INFO))) {
          logger.info(//
              "The evaluation job has finished and the pool "//$NON-NLS-1$
                  + pool + //
                  " has terminated (seemingly without encountering an unrecoverable error)."); //$NON-NLS-1$
        }
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

    if (printInfo) {
      new __End(logger).run();
    }
  }

  /**
   * Write the hello message
   */
  private static final class __Begin extends SynchronizedIO<PrintStream> {

    /** the evaluator */
    private final Evaluator m_evaluator;

    /**
     * Create the start message writer
     *
     * @param logger
     *          the logger to synchronize on
     * @param evaluator
     *          the evaluator
     */
    __Begin(final Logger logger, final Evaluator evaluator) {
      super(System.out, logger);
      this.m_evaluator = evaluator;
    }

    /** {@inheritDoc} */
    @Override
    protected final void io(final PrintStream io, final Logger logger) {
      io.print("Welcome to the "); //$NON-NLS-1$
      io.println(this.m_evaluator.getProjectName());
      io.print("  version: "); //$NON-NLS-1$
      io.println(this.m_evaluator.getProjectVersion());
      io.print("  url: "); //$NON-NLS-1$
      io.println(this.m_evaluator.getProjectURL());
      io.print("  for JDK version: "); //$NON-NLS-1$
      io.println(this.m_evaluator.getProjectJDK());
      io.print("  contact: "); //$NON-NLS-1$
      io.print(this.m_evaluator.getContactName());
      io.print(' ');
      io.print('(');
      io.print(this.m_evaluator.getContactEmail());
      io.print(',');
      io.print(' ');
      io.print(this.m_evaluator.getContactURL());
      io.println(')');
      io.println();
    }
  }

  /**
   * Write the goodbye message
   */
  private static final class __End extends SynchronizedIO<PrintStream> {

    /**
     * Create the start message writer
     *
     * @param logger
     *          the logger to synchronize on
     */
    __End(final Logger logger) {
      super(System.out, logger);
    }

    /** {@inheritDoc} */
    @Override
    protected final void io(final PrintStream io, final Logger logger) {
      io.println();
      io.println("...goodbye.");//$NON-NLS-1$
    }
  }
}