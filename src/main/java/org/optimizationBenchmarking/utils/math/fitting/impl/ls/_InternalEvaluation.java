package org.optimizationBenchmarking.utils.math.fitting.impl.ls;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem.Evaluation;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.QRDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/** The internal evaluation. */
final class _InternalEvaluation implements Evaluation {

  /** the jacobian */
  private final RealMatrix m_jacobian;
  /** the residuals */
  private final RealVector m_residuals;
  /** the point */
  private final RealVector m_point;
  /** the cost */
  private final double m_cost;
  /** the root-mean-square error */
  private final double m_rms;

  /**
   * create the internal evaluation
   *
   * @param jacobian
   *          the jacobian
   * @param residuals
   *          the residuals
   * @param point
   *          the point
   * @param cost
   *          the cost
   * @param rms
   *          the root-mean-square error
   */
  _InternalEvaluation(final RealMatrix jacobian,
      final RealVector residuals, final RealVector point,
      final double cost, final double rms) {
    super();
    this.m_jacobian = jacobian;
    this.m_point = point;
    this.m_residuals = residuals;
    this.m_cost = cost;
    this.m_rms = rms;
  }

  /** {@inheritDoc} */
  @Override
  public final RealMatrix getJacobian() {
    return this.m_jacobian;
  }

  /** {@inheritDoc} */
  @Override
  public final RealVector getResiduals() {
    return this.m_residuals;
  }

  /** {@inheritDoc} */
  @Override
  public final RealVector getPoint() {
    return this.m_point;
  }

  /** {@inheritDoc} */
  @Override
  public final RealMatrix getCovariances(final double threshold) {
    final RealMatrix j, jTj;

    j = this.getJacobian();
    jTj = j.transpose().multiply(j);

    return new QRDecomposition(jTj, threshold).getSolver().getInverse();
  }

  /** {@inheritDoc} */
  @Override
  public final RealVector getSigma(
      final double covarianceSingularityThreshold) {
    final RealMatrix cov;
    final RealVector sig;
    int i;

    cov = this.getCovariances(covarianceSingularityThreshold);
    i = cov.getColumnDimension();
    sig = new ArrayRealVector(i);
    for (; (--i) >= 0;) {
      sig.setEntry(i, Math.sqrt(cov.getEntry(i, i)));
    }
    return sig;
  }

  /** {@inheritDoc} */
  @Override
  public final double getRMS() {
    return this.m_rms;
  }

  /** {@inheritDoc} */
  @Override
  public final double getCost() {
    return this.m_cost;
  }
}
