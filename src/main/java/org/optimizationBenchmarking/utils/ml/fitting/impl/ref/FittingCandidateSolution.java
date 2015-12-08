package org.optimizationBenchmarking.utils.ml.fitting.impl.ref;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** A candidate solution of the fitting process. */
public class FittingCandidateSolution
    implements ITextable, Comparable<FittingCandidateSolution> {

  /** the fitting solution */
  public final double[] solution;

  /** the solution quality */
  public double quality;

  /**
   * Create the fitting candidate solution
   * 
   * @param parameterCount
   *          the number of parameters
   */
  public FittingCandidateSolution(final int parameterCount) {
    super();
    this.solution = new double[parameterCount];
    this.quality = Double.POSITIVE_INFINITY;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(//
        Arrays.hashCode(this.solution), //
        HashUtils.hashCode(this.quality));
  }

  /** {@inheritDoc} */
  @Override
  public void toText(ITextOutput textOut) {
    char x;

    textOut.append(this.quality);
    textOut.append(':');
    x = '[';
    for (double d : this.solution) {
      textOut.append(x);
      textOut.append(d);
      x = ',';
    }
    textOut.append(']');
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    final FittingCandidateSolution s;
    if (o instanceof FittingCandidateSolution) {
      s = ((FittingCandidateSolution) o);
      return ((EComparison.EQUAL.compare(this.quality, s.quality))
          && (Arrays.equals(this.solution, s.solution)));
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    final MemoryTextOutput mto;

    mto = new MemoryTextOutput();
    this.toText(mto);
    return mto.toString();
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final FittingCandidateSolution o) {
    final int max;
    int res, index;

    if (o == this) {
      return 0;
    }
    if (o == null) {
      return (-1);
    }
    res = EComparison.compareDoubles(this.quality, o.quality);
    if (res != 0) {
      return res;
    }

    max = Math.min(this.solution.length, o.solution.length);
    if (max > 0) {
      index = (-1);
      for (double d : this.solution) {
        res = EComparison.compareDoubles(d, o.solution[++index]);
        if (res != 0) {
          return res;
        }
        if (index >= max) {
          break;
        }
      }
    }

    return Integer.compare(this.solution.length, o.solution.length);
  }

  /**
   * Copy the contents of another candidate solution into this record
   * 
   * @param other
   *          the other solution
   */
  public void copyFrom(final FittingCandidateSolution other) {
    System.arraycopy(other.solution, 0, this.solution, 0,
        this.solution.length);
    this.quality = other.quality;
  }
}
