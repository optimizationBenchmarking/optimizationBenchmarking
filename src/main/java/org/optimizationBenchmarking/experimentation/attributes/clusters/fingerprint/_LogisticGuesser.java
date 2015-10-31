package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.fitting.spec.IParameterGuesser;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** A parameter guesser for the logistic models */
final class _LogisticGuesser
    implements IParameterGuesser, Comparator<double[]> {

  /** the data matrix */
  private final IMatrix m_data;

  /**
   * create the guesser
   *
   * @param data
   *          the data
   */
  _LogisticGuesser(final IMatrix data) {
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
    double t, a, b, c, x1, y1, x2, y2;
    int i;

    m = this.m_data.m();

    find: {
      if (m > 2) {

        fits = new double[Math.max(1, Math.min((m - 2), ((int) (3d - (2d * //
            Math.log(1d - random.nextDouble()))))))][3];
        size = 0;
        trials = 1000;
        while ((size < fits.length) && ((--trials) >= 0)) {
          a = Double.NEGATIVE_INFINITY;

          for (i = 20; (--i) >= 0;) {
            a = Math.max(a, this.m_data.getDouble(random.nextInt(m), 1));
          }

          i = random.nextInt(m);
          x1 = this.m_data.getDouble(i, 0);
          y1 = this.m_data.getDouble(i, 1);

          i = random.nextInt(m);
          x2 = this.m_data.getDouble(i, 0);
          y2 = this.m_data.getDouble(i, 1);

          b = (((x2 * Math.log((a - y1) / y1))
              - (x1 * Math.log((a - y2) / y2))) / (x2 - x1));
          if (MathUtils.isFinite(b)) {
            c = ((Math.log((a - y1) / y1) - b) / x1);
            if (MathUtils.isFinite(c)) {
              fits[size][0] = a;
              fits[size][1] = b;
              fits[size][2] = c;
              size++;
            }
          }
        }

        if (size > 0) {

          Arrays.sort(fits, 0, size, this);
          System.arraycopy(fits[size >> 1], 0, parameters, 0, 2);
          break find;
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
