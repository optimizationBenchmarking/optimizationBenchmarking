package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import java.util.Random;

import org.optimizationBenchmarking.utils.math.PolynomialFitter;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** A parameter guesser for quadratic polynomials */
final class _PolynomialGuesser extends _BasicInternalGuesser {

  /**
   * create the guesser
   *
   * @param data
   *          the data
   */
  _PolynomialGuesser(final IMatrix data) {
    super(data);
  }

  /** {@inheritDoc} */
  @Override
  final boolean guessBasedOn3Points(final double x0, final double y0,
      final double x1, final double y1, final double x2, final double y2,
      final double[] dest, final Random random) {
    return PolynomialFitter.findCoefficientsDegree2(x0, y0, x1, y1, x2, y2,
        dest);
  }

  /** {@inheritDoc} */
  @Override
  final boolean guessBasedOn2Points(final double x0, final double y0,
      final double x1, final double y1, final double[] dest) {
    return PolynomialFitter.findCoefficientsDegree1(x0, y0, x1, y1, dest);
  }

  /** {@inheritDoc} */
  @Override
  final boolean guessBasedOn1Point(final double x0, final double y0,
      final double[] dest) {
    return PolynomialFitter.findCoefficientsDegree0(x0, y0, dest);
  }
}
