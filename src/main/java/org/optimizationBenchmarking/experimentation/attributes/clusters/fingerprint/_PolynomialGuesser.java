package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import org.optimizationBenchmarking.utils.math.PolynomialFitter;
import org.optimizationBenchmarking.utils.math.fitting.spec.IParameterGuesser;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** A parameter guesser for quadratic polynomials */
final class _PolynomialGuesser
    implements IParameterGuesser, Comparator<double[]> {

  /** the data matrix */
  private final IMatrix m_data;

  /**
   * create the guesser
   *
   * @param data
   *          the data
   */
  _PolynomialGuesser(final IMatrix data) {
    super();
    this.m_data = data;
  }

  /** {@inheritDoc} */
  @Override
  public final void createRandomGuess(final double[] parameters,
      final Random random) {
    final int m;
    final double[][] fits;
    int trials, size;
    double t;

    m = this.m_data.m();

    find: {
      if (m == 1) {
        if (PolynomialFitter.findCoefficientsDegree0(//
            this.m_data.getDouble(0, 0), //
            this.m_data.getDouble(0, 1), //
            parameters)) {
          parameters[1] = parameters[2] = 0d;
          break find;
        }
      } else {
        if (m == 2) {
          if (PolynomialFitter.findCoefficientsDegree1(//
              this.m_data.getDouble(0, 0), //
              this.m_data.getDouble(0, 1), //
              this.m_data.getDouble(1, 0), //
              this.m_data.getDouble(1, 1), //
              parameters)) {
            parameters[2] = 0d;
            break find;
          }
        } else {

          fits = new double[Math.max(1,
              Math.min((m - 2), ((int) (3d - (2d * //
                  Math.log(1d - random.nextDouble()))))))][3];
          size = 0;
          trials = 1000;
          while ((size < fits.length) && ((--trials) >= 0)) {

            if (PolynomialFitter.findCoefficientsDegree2(//
                this.m_data.getDouble(0, 0), //
                this.m_data.getDouble(0, 1), //
                this.m_data.getDouble(1, 0), //
                this.m_data.getDouble(1, 1), //
                this.m_data.getDouble(2, 0), //
                this.m_data.getDouble(2, 1), //
                fits[size])) {
              size++;
            }
          }

          if (size > 0) {

            Arrays.sort(fits, 0, size, this);
            System.arraycopy(fits[size >> 1], 0, parameters, 0, 2);
            break find;
          }

        }
      }

      parameters[0] = random.nextGaussian();
      parameters[1] = random.nextGaussian();
      parameters[2] = random.nextGaussian();
      return;
    }

    for (size = parameters.length; (--size) >= 0;) {
      t = Math.abs(parameters[size]);
      if (t <= 0d) {
        parameters[size] = (0.1d * random.nextGaussian());
      } else {
        parameters[size] += (0.05d * t * random.nextGaussian());
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int compare(final double[] o1, final double[] o2) {
    int res;

    res = Double.compare(o1[2], o2[2]);
    if (res != 0) {
      return res;
    }

    res = Double.compare(o1[1], o2[1]);
    if (res != 0) {
      return res;
    }

    return Double.compare(o1[0], o2[0]);
  }
}
