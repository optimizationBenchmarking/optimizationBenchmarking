package examples.snippets;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.math.mathEngine.impl.R.R;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IAssignment;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IExpression;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IFunction;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IMathEngine;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IVariable;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix2D;
import org.optimizationBenchmarking.utils.math.matrix.impl.LongMatrix2D;

/**
 * <p>
 * This is a small test of {@code R}.
 * </p>
 */
public final class RunR {

  /**
   * The main routine
   * 
   * @param args
   *          ignored
   */
  public static final void main(final String[] args) {
    IMatrix input, output;
    IVariable var;

    Configuration.setup(args);

    try (final IMathEngine r = R
        .getInstance()
        .use()
        .setLogger(
            Configuration.getRoot().getLogger(Configuration.PARAM_LOGGER,
                null)).create()) {

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
      System.out.println(r.getAsDouble(var));
      output = r.getAsMatrix(var);
      System.out.println(output.getClass().getSimpleName() + ' ' + output);

      input = new DoubleMatrix2D(new double[][] {
          { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
          { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
          { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 } });

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
      System.out.println(output.getClass().getSimpleName() + ' ' + output);

      try (IAssignment ass = r.assign()) {
        var = ass.getVariable();
        try (final IExpression expr = ass.value()) {
          try (final IFunction fct = expr.determinant()) {
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
      System.out.println(output.getClass().getSimpleName() + ' ' + output);

    } catch (final Throwable t) {
      t.printStackTrace();
    }
  }

  /** the forbidden constructor */
  private RunR() {
    ErrorUtils.doNotCall();
  }
}
