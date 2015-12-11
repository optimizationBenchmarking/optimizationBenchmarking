package org.optimizationBenchmarking.utils.ml.fitting.impl.lssimplex;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem.Evaluation;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.QRDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.optimizationBenchmarking.utils.ml.fitting.spec.FittingEvaluation;

/** The internal evaluation. */
final class _InternalEvaluation extends FittingEvaluation
    implements Evaluation {

  /** the jacobian */
  RealMatrix m_jacobian;
  /** the residuals */
  RealVector m_residuals;
  /** the point */
  private final RealVector m_point;

  /**
   * create the internal evaluation
   *
   * @param point
   *          the point
   */
  _InternalEvaluation(final RealVector point) {
    super();
    this.m_point = point;
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
    return this.rmsError;
  }

  /** {@inheritDoc} */
  @Override
  public final double getCost() {
    return this.rsError;
  }
}
