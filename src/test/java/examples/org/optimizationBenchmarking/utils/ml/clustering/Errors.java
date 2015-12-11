package examples.org.optimizationBenchmarking.utils.ml.clustering;

import org.optimizationBenchmarking.utils.text.Textable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the error set */
public class Errors extends Textable {

  /** the runtime */
  public final double runtime;

  /** the fraction of incorrectly assigned elements */
  public final double assignmentError;

  /** the deviation of the anticipacted cluster number */
  public final double clusterNumError;

  /**
   * create
   *
   * @param rt
   *          the runtime
   * @param as
   *          the assignment error
   * @param cl
   *          the cluster count error
   */
  Errors(final double rt, final double as, final double cl) {
    super();
    this.runtime = rt;
    this.assignmentError = as;
    this.clusterNumError = cl;
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    int x;

    cn: {
      x = ((int) (Math.round(100d * this.clusterNumError)));
      if (x < 0) {
        textOut.append('-');
        x = -x;
      } else {
        if (x >= 100) {
          textOut.append('1');
          break cn;
        }
      }
      textOut.append('0');
      textOut.append('.');
      if (x < 10) {
        textOut.append('0');
      }
      textOut.append(x);
    }

    textOut.append('/');

    an: {
      x = ((int) (Math.round(100d * this.assignmentError)));

      if (x >= 100) {
        textOut.append('1');
        break an;
      }
      textOut.append('0');
      textOut.append('.');
      if (x < 10) {
        textOut.append('0');
      }
      textOut.append(x);
    }

    textOut.append('/');
    textOut.append(Math.round(this.runtime / 1e8d));
  }
}
