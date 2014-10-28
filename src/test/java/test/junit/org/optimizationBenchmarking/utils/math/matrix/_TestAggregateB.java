package test.junit.org.optimizationBenchmarking.utils.math.matrix;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** the test aggregate */
final class _TestAggregateB {

  /** the sum A */
  double m_sumA;
  /** the sum B */
  double m_sumB;
  /** the sum C */
  double m_sumC;
  /** the sum D */
  double m_sumD;

  /** create */
  _TestAggregateB() {
    super();
  }

  /** clear */
  final void _clear() {
    this.m_sumA = 0d;
    this.m_sumB = 0d;
    this.m_sumC = 0d;
    this.m_sumD = 0d;
  }

  /**
   * Append a matrix value
   * 
   * @param matrix
   *          the matrix
   * @param row
   *          the row
   * @param col
   *          the column
   */
  public final void append1(final IMatrix matrix, final int row,
      final int col) {
    this.m_sumA += matrix.getDouble(row, col);
    this.m_sumB += matrix.getLong(row, col);
  }

  /**
   * Append a matrix value
   * 
   * @param matrix
   *          the matrix
   * @param row
   *          the row
   * @param col
   *          the column
   */
  public final void append2(final IMatrix matrix, final int row,
      final int col) {
    this.m_sumC += matrix.getDouble(row, col);
    this.m_sumD += matrix.getLong(row, col);
  }

}
