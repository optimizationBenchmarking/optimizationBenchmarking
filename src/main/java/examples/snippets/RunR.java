package examples.snippets;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.math.mathEngine.impl.R.R;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IAssignment;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IExpression;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IFunction;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IMathEngine;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IVariable;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.ByteMatrix1D;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix2D;
import org.optimizationBenchmarking.utils.math.matrix.impl.LongMatrix2D;

/**
 * <p>
 * This is a small test of {@code R}.
 * </p>
 */
public final class RunR implements Runnable {

  /**
   * The main routine
   * 
   * @param args
   *          passed to configuration
   * @throws Throwable
   *           if something goes wrong
   */
  public static final void main(final String[] args) throws Throwable {
    Configuration.setup(args);
    ExecutorService es;
    int i;

    es = Executors.newFixedThreadPool(10);
    for (i = 1; i <= 20; i++) {
      es.submit(new RunR());
    }

    es.shutdown();
    es.awaitTermination(Long.MAX_VALUE, TimeUnit.HOURS);

  }

  /** the forbidden constructor */
  private RunR() {
    super();
  }

  @Override
  public void run() {
    IMatrix input, output;
    IVariable var;
    Random random;
    byte[] randomMatrix;
    String id;

    if (!R.getInstance().canUse()) {
      System.out.println(//
          "Cannot use R (likely it is not installed). Exitting."); //$NON-NLS-1$
      return;
    }

    random = new Random();

    try (final IMathEngine r = R
        .getInstance()
        .use()
        .setLogger(
            Configuration.getRoot().getLogger(Configuration.PARAM_LOGGER,
                null)).create()) {
      id = (r.toString() + ' ');

      input = new DoubleMatrix2D(new double[][] { { 1, 2, 3 },
          { 4, 5, 6 }, { 7, 8, 9 } });
      try (IAssignment ass = r.assign()) {
        var = ass.getVariable();
        try (final IExpression expr = ass.value()) {
          try (final IFunction fct = expr.functionResult("det")) { //$NON-NLS-1$
            try (final IExpression expr2 = fct.parameter()) {
              expr2.matrixValue(input);
            }
          }
        }
      }
      System.out.println(id + r.getAsDouble(var));
      output = r.getAsMatrix(var);
      System.out.println(id + output.getClass().getSimpleName() + ' '
          + output);

      randomMatrix = new byte[4 * 5];
      random.nextBytes(randomMatrix);
      input = new ByteMatrix1D(randomMatrix, 4, 5);

      try (IAssignment ass = r.assign()) {
        var = ass.getVariable();
        try (final IExpression expr = ass.value()) {
          try (final IFunction fct = expr.functionResult("103.5*")) { //$NON-NLS-1$
            try (final IExpression expr2 = fct.parameter()) {
              expr2.matrixValue(input);
            }
          }
        }
      }
      output = r.getAsMatrix(var);
      System.out.println(id + output.getClass().getSimpleName() + ' '
          + output);

      try (IAssignment ass = r.assign()) {
        var = ass.getVariable();
        try (final IExpression expr = ass.value()) {
          try (final IFunction fct = expr.matrixDeterminant()) {
            try (final IExpression expr2 = fct.parameter()) {

              try (final IFunction fct2 = expr2.mul()) {
                try (final IExpression expr3 = fct2.parameter()) {
                  expr3.matrixValue(new DoubleMatrix2D(new double[][] {
                      { 1.1, 2.2, 3.3 }, { 4.4, 5.5, 6.6 },
                      { 7.7, 8.8, 9.9 } }));
                }
                try (final IExpression expr3 = fct2.parameter()) {
                  expr3.matrixValue(new LongMatrix2D(new long[][] {
                      { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } }));
                }
              }
            }
          }
        }
      }
      output = r.getAsMatrix(var);
      System.out.println(id + output.getClass().getSimpleName() + ' '
          + output);

    } catch (final Throwable t) {
      t.printStackTrace();
    }
  }
}
