package org.optimizationBenchmarking.utils.math.fitting.impl.opti;

/** A candidate solution for optimization-based fitting */
public final class OptiIndividual {

  /** the fitting solution */
  public final double[] solution;

  /** the fitting quality */
  public double quality;

  /** the set of critical data points */
  public final DataPoint[] critical;

  /** the comparison score */
  int m_score;

  /** the hit score */
  long m_hitScore;

  /**
   * Create the opti-individual
   *
   * @param dim
   *          the dimension
   * @param pointsUsed
   *          the points used
   */
  OptiIndividual(final int dim, final int pointsUsed) {
    super();
    this.solution = new double[dim];
    this.critical = new DataPoint[pointsUsed];
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
    System.arraycopy(copy.critical, 0, this.critical, 0,
        this.critical.length);
  }
}
