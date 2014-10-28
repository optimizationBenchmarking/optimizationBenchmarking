package test.junit.org.optimizationBenchmarking.utils.math.matrix;

import org.optimizationBenchmarking.utils.math.statistics.aggregate.IAggregate;

/** the test aggregate */
final class _TestAggregateA implements IAggregate {

  /** the sum */
  private double m_sum;

  /** create */
  _TestAggregateA() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final byte v) {
    this.m_sum += v;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final short v) {
    this.m_sum += v;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final int v) {
    this.m_sum += v;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final long v) {
    this.m_sum += v;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final float v) {
    this.m_sum += v;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final double v) {
    this.m_sum += v;
  }

  /** clear */
  final void _clear() {
    this.m_sum = 0d;
  }

  /**
   * assert the same aggregate
   * 
   * @param z
   *          the aggregate
   */
  final void _assert(final _TestAggregateB z) {
    final double a, b, c, d, e;

    a = this.m_sum;
    b = z.m_sumA;
    c = z.m_sumB;
    d = z.m_sumC;
    e = z.m_sumD;

    if (a == b) {
      return;
    }
    if (a == c) {
      return;
    }
    if (a == d) {
      return;
    }
    if (a == e) {
      return;
    }
    if (Double.isNaN(a)//
        && (Double.isNaN(b) || Double.isNaN(c) || //
            Double.isNaN(d) || Double.isNaN(e))) {
      return;
    }

    throw new AssertionError(//
        "(" + //$NON-NLS-1$
            a + " != " + b + ") && (" + //$NON-NLS-1$//$NON-NLS-2$
            a + " != " + c + ") && (" + //$NON-NLS-1$//$NON-NLS-2$
            a + " != " + d + ") && (" + //$NON-NLS-1$//$NON-NLS-2$
            a + " != " + e + ")"); //$NON-NLS-1$//$NON-NLS-2$
  }
}
