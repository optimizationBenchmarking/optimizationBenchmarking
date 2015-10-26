package org.optimizationBenchmarking.utils.math.fitting.impl.opti;

/** A candidate solution for optimization-based fitting */
public final class OptiIndividual {

  /** the fitting solution */
  public final double[] solution;

  /** the fitting quality */
  public double quality;

  /**
   * Create the opti-individual
   *
   * @param dim
   *          the dimension
   */
  OptiIndividual(final int dim) {
    super();
    this.solution = new double[dim];
  }

  /**
   * Copy the data from another individual
   *
   * @param copy
   *          the other individual to be copied
   */
  public final void assign(final OptiIndividual copy) {
    System.arraycopy(copy.solution, 0, this.solution, 0,
        this.solution.length);
    this.quality = copy.quality;
  }
}
