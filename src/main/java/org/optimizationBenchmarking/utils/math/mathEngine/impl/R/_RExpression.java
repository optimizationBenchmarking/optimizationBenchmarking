package org.optimizationBenchmarking.utils.math.mathEngine.impl.R;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.EmptyUtils;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.mathEngine.impl.abstr.EDataType;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IExpression;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IFunction;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IVariable;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender;

/** A class for creating an expression in R */
final class _RExpression extends _RExpressionScope implements IExpression {

  /** the negative infinity */
  static final String NEGATIVE_INFINITY = "-Inf"; //$NON-NLS-1$  
  /** the positive infinity */
  static final String POSITIVE_INFINITY = "Inf"; //$NON-NLS-1$  
  /** the nan */
  static final String NAN = "NaN"; //$NON-NLS-1$
  /** the {@code true} */
  static final String TRUE = "TRUE"; //$NON-NLS-1$
  /** the {@code false} */
  static final String FALSE = "FALSE"; //$NON-NLS-1$

  /**
   * create the R expression
   * 
   * @param owner
   *          the owner
   * @param engine
   *          the engine
   */
  _RExpression(final _RScope owner, final _REngine engine) {
    super(owner, engine);
    this.open();
    this.m_type = null;
  }

  /**
   * convert a long to a string
   * 
   * @param l
   *          the long
   * @return the string
   */
  private static final String __toString(final long l) {
    return Long.toString(l);
  }

  /**
   * convert a double to a string
   * 
   * @param d
   *          the double
   * @return the string
   */
  private static final String __toString(final double d) {
    if (d != d) {
      return _RExpression.NAN;
    }
    if (d <= Double.NEGATIVE_INFINITY) {
      return _RExpression.NEGATIVE_INFINITY;
    }
    if (d >= Double.POSITIVE_INFINITY) {
      return _RExpression.POSITIVE_INFINITY;
    }
    return SimpleNumberAppender.INSTANCE
        .toString(d, ETextCase.IN_SENTENCE);
  }

  /**
   * write a vector
   * 
   * @param matrix
   *          the matrix
   * @param m
   *          the m
   * @param n
   *          the n
   * @throws IOException
   *           if i/o fails
   */
  private final void __writeVector(final IMatrix matrix, final int m,
      final int n) throws IOException {
    final int size;
    final Path temp;
    final BufferedWriter dest, out;
    final char separator;
    int i, j;
    boolean first;

    size = (m * n);
    out = this.m_engine.m_out;
    if (size > 20) {
      temp = this.m_engine._tempFile();
      dest = new BufferedWriter(new OutputStreamWriter(
          PathUtils.openOutputStream(temp)));
      out.write("scan(\"");//$NON-NLS-1$
      out.write(temp.toString());
      out.write("\",n=");//$NON-NLS-1$
      out.write(Integer.toString(size));
      out.write(",quiet=");//$NON-NLS-1$
      out.write(_RExpression.TRUE);
      separator = ' ';
    } else {
      temp = null;
      dest = out;
      out.write("c(");//$NON-NLS-1$
      separator = ',';
    }
    try {
      try {
        first = true;
        if (matrix.isIntegerMatrix()) {
          for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) {
              if (first) {
                first = false;
              } else {
                dest.write(separator);
              }
              dest.write(_RExpression.__toString(matrix.getLong(i, j)));
            }
          }
        } else {
          for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) {
              if (first) {
                first = false;
              } else {
                dest.write(separator);
              }
              dest.write(_RExpression.__toString(matrix.getDouble(i, j)));
            }
          }
        }
      } finally {
        if (dest != out) {
          dest.close();
        }
      }

      out.write(')');
    } finally {
      if (temp != null) {
        this._markDelete(temp);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void matrixValue(final IMatrix matrix) {
    final BufferedWriter out;
    final int m, n;

    if (matrix == null) {
      throw new IllegalArgumentException("Cannot load null matrix."); //$NON-NLS-1$
    }
    this._setType(EDataType.MATRIX);

    m = matrix.m();
    n = matrix.n();
    out = this.m_engine.m_out;
    try {
      out.write("matrix("); //$NON-NLS-1$
      this.__writeVector(matrix, m, n);
      out.write(",nrow=");//$NON-NLS-1$
      out.write(Integer.toString(m));
      out.write(",ncol=");//$NON-NLS-1$
      out.write(Integer.toString(n));
      out.write(",byrow="); //$NON-NLS-1$
      out.write(_RExpression.TRUE);
      out.write(')');
    } catch (final Throwable ioe) {
      this.m_engine._handleError(ioe);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void vectorValue(final IMatrix vector) {
    final int m, n;

    if (vector == null) {
      throw new IllegalArgumentException("Cannot load null vector."); //$NON-NLS-1$
    }
    this._setType(EDataType.VECTOR);

    m = vector.m();
    n = vector.n();
    if ((m != 1) && (n != 1)) {
      throw new IllegalArgumentException(
          ((("A vector must be a matrix with either only one row or only one column, but you passed in a "//$NON-NLS-1$ 
          + m) + 'x') + n)
              + " matrix.");//$NON-NLS-1$
    }
    try {
      this.__writeVector(vector, m, n);
    } catch (final Throwable ioe) {
      this.m_engine._handleError(ioe);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void variableValue(final IVariable id) {
    this._setType(this.m_engine._variableDataType(id));
    try {
      this.m_engine.m_out.write(this.m_engine._variableName(id));
    } catch (final Throwable ioe) {
      this.m_engine._handleError(ioe);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void doubleValue(final double d) {
    this._setType(EDataType.DOUBLE);
    try {
      this.m_engine.m_out.write(_RExpression.__toString(d));
    } catch (final Throwable ioe) {
      this.m_engine._handleError(ioe);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void longValue(final long l) {
    this._setType(EDataType.LONG);
    try {
      this.m_engine.m_out.write(_RExpression.__toString(l));
    } catch (final Throwable ioe) {
      this.m_engine._handleError(ioe);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void booleanValue(final boolean b) {
    this._setType(EDataType.BOOLEAN);
    try {
      this.m_engine.m_out
          .write(b ? _RExpression.TRUE : _RExpression.FALSE);
    } catch (final Throwable ioe) {
      this.m_engine._handleError(ioe);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final IFunction functionResult(final String name) {
    final String s;
    s = TextUtils.prepare(name);
    if (s == null) {
      throw new IllegalArgumentException(//
          "Function name must not be null or empty, but is: '" //$NON-NLS-1$
              + name + '\'');
    }
    return new _RNamedFunction(name, null, this, this.m_engine);
  }

  /** {@inheritDoc} */
  @Override
  public final IFunction mul() {
    return new _RNamedFunction(null, new String[] { "*" }, this, //$NON-NLS-1$
        this.m_engine);
  }

  /** {@inheritDoc} */
  @Override
  public final IFunction div() {
    return new _RNamedFunction(null, new String[] { "/" }, this,//$NON-NLS-1$
        this.m_engine);
  }

  /** {@inheritDoc} */
  @Override
  public final IFunction add() {
    return new _RNamedFunction(null, new String[] { "+" }, this,//$NON-NLS-1$
        this.m_engine);
  }

  /** {@inheritDoc} */
  @Override
  public final IFunction sub() {
    return new _RNamedFunction(null, new String[] { "-" }, this,//$NON-NLS-1$
        this.m_engine);
  }

  /** {@inheritDoc} */
  @Override
  public final IFunction matrixDeterminant() {
    this._setType(EDataType.DOUBLE);
    return new _RNamedFunction("det", //$NON-NLS-1$
        EmptyUtils.EMPTY_STRINGS, this, this.m_engine);
  }

  /** {@inheritDoc} */
  @Override
  public final IFunction matrixTranspose() {
    this._setType(EDataType.MATRIX);
    return new _RNamedFunction("t", //$NON-NLS-1$
        EmptyUtils.EMPTY_STRINGS, this, this.m_engine);
  }

  /** {@inheritDoc} */
  @Override
  public final IFunction matrixMul() {
    this._setType(EDataType.MATRIX);
    return new _RNamedFunction(null,
        new String[] { "%*%" }, this, this.m_engine);//$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final IFunction matrixInvert() {
    this._setType(EDataType.MATRIX);
    return new _RNamedFunction("solve", //$NON-NLS-1$
        EmptyUtils.EMPTY_STRINGS, this, this.m_engine);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {
    EDataType type;

    if (this.m_type == null) {
      if (child instanceof _RNamedFunction) {
        type = ((_RNamedFunction) child).m_type;
        this._setType((type != null) ? type : EDataType.UNKNOWN);
      }
    }

    super.afterChildClosed(child);
  }
}
