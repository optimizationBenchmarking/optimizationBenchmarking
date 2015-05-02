package org.optimizationBenchmarking.utils.math.matrix.processing;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.matrix.AbstractMatrix;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/**
 * A matrix which is based on applying a unary function to the columns of
 * another matrix.
 */
public class ColumnTransformedMatrix extends AbstractMatrix {

  /** the functions used for transformation */
  private final UnaryFunction[] m_functions;

  /** whether a given column is an integer */
  private final int[] m_intCol;

  /** the source matrix */
  private final IMatrix m_source;

  /**
   * Create the column-transformed matrix
   * 
   * @param source
   *          the source matrix
   * @param transformation
   *          the transformations to be applied to the columns
   */
  public ColumnTransformedMatrix(final IMatrix source,
      final UnaryFunction... transformation) {
    final int n;
    int i;

    if (source == null) {
      throw new IllegalArgumentException(//
          "Source matrix must not be null."); //$NON-NLS-1$
    }
    if (transformation == null) {
      throw new IllegalArgumentException(//
          "Transformation must not be null."); //$NON-NLS-1$
    }
    n = source.n();
    if (n != transformation.length) {
      throw new IllegalArgumentException(//
          "Number of transformation functions (" //$NON-NLS-1$
              + transformation.length + //
              ") must equal number of columns ("//$NON-NLS-1$
              + n + //
              ") but does not.");//$NON-NLS-1$
    }

    this.m_functions = transformation.clone();

    this.m_intCol = new int[n];
    for (i = n; (--i) >= 0;) {
      if (this.m_functions[i] == null) {
        throw new IllegalArgumentException(//
            "Transformation funtion is null at index " + i); //$NON-NLS-1$
      }

      if (source.selectColumns(i).isIntegerMatrix()) {
        if (this.m_functions[i].isLongArithmeticAccurate()) {
          this.m_intCol[i] = 2;
        } else {
          this.m_intCol[i] = 1;
        }
      }
    }

    this.m_source = source;
  }

  /** {@inheritDoc} */
  @Override
  public final int m() {
    return this.m_source.m();
  }

  /** {@inheritDoc} */
  @Override
  public int n() {
    return this.m_source.n();
  }

  /** {@inheritDoc} */
  @Override
  public final double getDouble(final int row, final int column) {
    if ((column >= 0) && (column < this.m_functions.length)) {
      switch (this.m_intCol[column]) {
        case 0: {
          return this.m_functions[column].computeAsDouble(//
              this.m_source.getDouble(row, column));
        }
        case 1: {
          return this.m_functions[column].computeAsDouble(//
              this.m_source.getLong(row, column));
        }
        default: {
          return this.m_functions[column].computeAsLong(//
              this.m_source.getLong(row, column));
        }
      }
    }
    return super.getDouble(row, column);
  }

  /** {@inheritDoc} */
  @Override
  public final long getLong(final int row, final int column) {
    if ((column >= 0) && (column < this.m_functions.length)) {
      if (this.m_intCol[column] == 0) {
        return ((long) (this.m_functions[column].computeAsDouble(//
            this.m_source.getDouble(row, column))));
      }
      return this.m_functions[column].computeAsLong(//
          this.m_source.getLong(row, column));
    }
    return super.getLong(row, column);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isIntegerMatrix() {
    for (final int i : this.m_intCol) {
      if (i != 2) {
        return false;
      }
    }
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix selectColumns(final int... cols) {
    final UnaryFunction[] func;
    final IMatrix sel;
    int i;

    sel = this.m_source.selectColumns(cols);

    i = cols.length;
    func = new UnaryFunction[i];
    for (; (--i) >= 0;) {
      func[i] = this.m_functions[cols[i]];
    }
    return new ColumnTransformedMatrix(sel, func);
  }
}
