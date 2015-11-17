package org.optimizationBenchmarking.utils.math.matrix.impl;

import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.matrix.AbstractMatrix;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.IAggregate;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/**
 * A matrix builder allows us to build a matrix row by row. It tries to fit
 * the data into a primitive array of minimal type.
 */
public final class MatrixBuilder implements IAggregate {

  /** the m */
  private int m_m;

  /** the n */
  private int m_n;

  /** the size */
  private int m_size;

  /** the maximum allowed size */
  private int m_maxSize;

  /** the internal array */
  private _Array m_array;

  /**
   * create the matrix builder
   *
   * @param expectedType
   *          the expected numerical type
   * @param expectedSize
   *          the expected size
   */
  public MatrixBuilder(final EPrimitiveType expectedType,
      final int expectedSize) {
    super();

    final int size;

    this.m_m = this.m_n = (-1);
    this.m_maxSize = Integer.MAX_VALUE;

    size = ((expectedSize <= 0) ? 32 : expectedSize);

    switch (expectedType) {
      case BYTE: {
        this.m_array = new _ByteArray(new byte[size]);
        return;
      }
      case SHORT: {
        this.m_array = new _ShortArray(new short[size]);
        return;
      }
      case INT: {
        this.m_array = new _IntArray(new int[size]);
        return;
      }
      case LONG: {
        this.m_array = new _LongArray(new long[size]);
        return;
      }
      case FLOAT: {
        this.m_array = new _FloatArray(new float[size]);
        return;
      }
      case DOUBLE: {
        this.m_array = new _DoubleArray(new double[size]);
        return;
      }
      default: {
        throw new IllegalArgumentException(//
            "Unsupported matrix element type: " + //$NON-NLS-1$
                expectedType);
      }
    }
  }

  /**
   * create the matrix builder
   *
   * @param expectedSize
   *          the expected size
   */
  public MatrixBuilder(final int expectedSize) {
    this(EPrimitiveType.INT, expectedSize);
  }

  /** create the matrix builder */
  public MatrixBuilder() {
    this(-1);
  }

  /**
   * create the matrix builder
   *
   * @param expectedType
   *          the expected numerical type
   */
  public MatrixBuilder(final EPrimitiveType expectedType) {
    this(expectedType, (-1));
  }

  /**
   * set m
   *
   * @param m
   *          the m
   */
  public final void setM(final int m) {
    final int maxSize;

    if (m <= 0) {
      throw new IllegalArgumentException("M cannot be less than 1."); //$NON-NLS-1$
    }
    if (m == this.m_m) {
      return;
    }

    if (this.m_m > 0) {
      throw new IllegalStateException("M has already been set."); //$NON-NLS-1$
    }

    if (this.m_n > 0) {
      if (this.m_size > (maxSize = (m * this.m_n))) {
        throw new IllegalArgumentException(//
            "Cannot set m to " + m + //$NON-NLS-1$
                " since n is " + this.m_n + //$NON-NLS-1$
                ", i.e., maxSize=" + maxSize + //$NON-NLS-1$
                ", which would be less than the number " + this.m_size + //$NON-NLS-1$
                " of already stored elements."); //$NON-NLS-1$
      }
      this.m_maxSize = maxSize;
      this.m_array._setMaxSize(this.m_size, maxSize);
    }
    this.m_m = m;
  }

  /**
   * set n
   *
   * @param n
   *          the n
   */
  public final void setN(final int n) {
    final int maxSize;

    if (n <= 0) {
      throw new IllegalArgumentException("N cannot be less than 1."); //$NON-NLS-1$
    }
    if (n == this.m_n) {
      return;
    }

    if (this.m_n > 0) {
      throw new IllegalStateException("N has already been set."); //$NON-NLS-1$
    }

    if (this.m_m > 0) {
      if (this.m_size > (maxSize = (n * this.m_m))) {
        throw new IllegalArgumentException(//
            "Cannot set n to " + n + //$NON-NLS-1$
                " since m is " + this.m_m + //$NON-NLS-1$
                ", i.e., maxSize=" + maxSize + //$NON-NLS-1$
                ", which would be less than the number " + this.m_size + //$NON-NLS-1$
                " of already stored elements."); //$NON-NLS-1$
      }
      this.m_maxSize = maxSize;
      this.m_array._setMaxSize(this.m_size, maxSize);
    }
    this.m_n = n;
  }

  /**
   * Increase the size by {@code 1}
   *
   * @return the size
   */
  private final int __incSize() {
    final int s;
    if ((s = this.m_size) >= this.m_maxSize) {
      throw new IllegalStateException("Already reached maximum size " + s); //$NON-NLS-1$
    }
    this.m_size = (s + 1);
    return s;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final byte v) {
    this.m_array = this.m_array._append(v, this.__incSize());
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final short v) {
    this.m_array = this.m_array._append(v, this.__incSize());
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final int v) {
    this.m_array = this.m_array._append(v, this.__incSize());
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final long v) {
    this.m_array = this.m_array._append(v, this.__incSize());
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final float v) {
    this.m_array = this.m_array._append(v, this.__incSize());
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final double v) {
    this.m_array = this.m_array._append(v, this.__incSize());
  }

  /**
   * Append a given string
   *
   * @param str
   *          the string
   */
  @SuppressWarnings("unused")
  public final void append(final String str) {
    try {
      this.append(Long.parseLong(str));
    } catch (final NumberFormatException nfe) {
      this.append(Double.parseDouble(str));
    }
  }

  /**
   * Append a basic number
   *
   * @param number
   *          the number
   */
  public final void append(final BasicNumber number) {
    if (number == null) {
      throw new IllegalArgumentException(//
          "Cannot append a null number."); //$NON-NLS-1$
    }
    switch (number.getState()) {
      case BasicNumber.STATE_EMPTY: {
        throw new IllegalArgumentException(//
            "Cannot append an empty number."); //$NON-NLS-1$
      }
      case BasicNumber.STATE_INTEGER: {
        this.append(number.longValue());
        return;
      }
      default: {
        this.append(number.doubleValue());
      }
    }
  }

  /**
   * Make the matrix
   *
   * @return the matrix
   */
  public final AbstractMatrix make() {
    int m, n, s;

    m = this.m_m;
    n = this.m_n;
    s = this.m_size;

    if (m > 0) {
      if (n > 0) {
        if (s != this.m_maxSize) {
          throw new IllegalStateException(((((((//
          s + " elements in matrix, but ") + //$NON-NLS-1$
              m) + 'x') + n) + '=') + this.m_maxSize) + " required.");//$NON-NLS-1$
        }
      } else {
        n = (s / m);
        if ((n * m) != s) {
          throw new IllegalStateException((((//
          s + " elements in matrix and m=") + m) //$NON-NLS-1$
              + " which would result in n=") + //$NON-NLS-1$
              ((s / ((double) m))));
        }
      }
    } else {
      if (n > 0) {
        m = (s / n);
        if ((n * m) != s) {
          throw new IllegalStateException((((//
          s + " elements in matrix and n=") + m) //$NON-NLS-1$
              + " which would result in m=") + //$NON-NLS-1$
              ((s / ((double) n))));
        }
      } else {
        if (s < 1) {
          throw new IllegalStateException(//
              "Matrix must have at least 1 element.");//$NON-NLS-1$
        }
        m = s;
        n = 1;
      }
    }

    return this.m_array._make(m, n);
  }

  /**
   * Append a series of {@code double}s
   *
   * @param data
   *          the series of {@code double}s
   */
  public final void append(final double[] data) {
    for (final double x : data) {
      this.append(x);
    }
  }

  /**
   * Append a series of {@code long}s
   *
   * @param data
   *          the series of {@code long}s
   */
  public final void append(final long[] data) {
    for (final long x : data) {
      this.append(x);
    }
  }

  /**
   * Append a matrix row-by-row
   *
   * @param matrix
   *          the matrix to be appended
   */
  public final void appendRowByRow(final IMatrix matrix) {
    final int m, n;
    int i, j;

    if (matrix == null) {
      throw new IllegalArgumentException(
          "Matrix to append row-wise cannot be null."); //$NON-NLS-1$
    }

    m = matrix.m();
    n = matrix.n();
    if (matrix.isIntegerMatrix()) {
      for (i = 0; i < m; i++) {
        for (j = 0; j < n; j++) {
          this.append(matrix.getLong(i, j));
        }
      }
    } else {
      for (i = 0; i < m; i++) {
        for (j = 0; j < n; j++) {
          this.append(matrix.getDouble(i, j));
        }
      }
    }
  }

  /**
   * Copy a matrix.
   *
   * @param matrix
   *          the matrix to copy
   * @param tryInteger
   *          should we try using an integer-based storage, even if we need
   *          to ignore
   *          {@link org.optimizationBenchmarking.utils.math.matrix.IMatrix#isIntegerMatrix()}
   *          ? (Even in this case, if the values are not integer, we will
   *          switch to floating point numbers, but this may waste
   *          runtime.)
   * @return the copied matrix
   */
  public static final AbstractMatrix copy(final IMatrix matrix,
      final boolean tryInteger) {
    final int m, n;
    final MatrixBuilder builder;

    m = matrix.m();
    n = matrix.n();

    builder = new MatrixBuilder(
        ((tryInteger || matrix.isIntegerMatrix())//
            ? EPrimitiveType.INT //
            : EPrimitiveType.FLOAT), //
        m * n);
    builder.setM(m);
    builder.setN(n);
    builder.appendRowByRow(matrix);
    return builder.make();
  }

  /** the internal array class */
  abstract static class _Array {

    /**
     * add a byte
     *
     * @param v
     *          the byte
     * @param size
     *          the size
     * @return an array
     */
    abstract _Array _append(byte v, final int size);

    /**
     * add a short
     *
     * @param v
     *          the short
     * @param size
     *          the size
     * @return an array
     */
    abstract _Array _append(short v, final int size);

    /**
     * add a int
     *
     * @param v
     *          the int
     * @param size
     *          the size
     * @return an array
     */
    abstract _Array _append(int v, final int size);

    /**
     * add a long
     *
     * @param v
     *          the long
     * @param size
     *          the size
     * @return an array
     */
    abstract _Array _append(long v, final int size);

    /**
     * add a float
     *
     * @param v
     *          the float
     * @param size
     *          the size
     * @return an array
     */
    abstract _Array _append(float v, final int size);

    /**
     * add a double
     *
     * @param v
     *          the double
     * @param size
     *          the size
     * @return an array
     */
    abstract _Array _append(double v, final int size);

    /**
     * make the matrix
     *
     * @param m
     *          the m
     * @param n
     *          the n
     * @return the matrix
     */
    abstract AbstractMatrix _make(final int m, final int n);

    /**
     * set the maximum size
     *
     * @param curSize
     *          the current size
     * @param maxSize
     *          the maximum size
     */
    abstract void _setMaxSize(final int curSize, final int maxSize);
  }

  /** the internal growable double array */
  private static final class _DoubleArray extends _Array {

    /** the double data */
    private double[] m_data;

    /**
     * create the array
     *
     * @param data
     *          the data
     */
    _DoubleArray(final double[] data) {
      this.m_data = data;
    }

    /** {@inheritDoc} */
    @Override
    final void _setMaxSize(final int curSize, final int maxSize) {
      double[] data;
      if (this.m_data.length != maxSize) {
        data = new double[maxSize];
        System.arraycopy(this.m_data, 0, data, 0, curSize);
        this.m_data = data;
      }
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final byte v, final int size) {
      return this._append(((double) v), size);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final short v, final int size) {
      return this._append(((double) v), size);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final int v, final int size) {
      return this._append(((double) v), size);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final long v, final int size) {
      return this._append(((double) v), size);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final float v, final int size) {
      return this._append(((double) v), size);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final double v, final int size) {
      double[] data;

      data = this.m_data;
      if (data.length <= size) {
        data = new double[(size + 16) << 1];
        System.arraycopy(this.m_data, 0, data, 0, size);
        this.m_data = data;
      }
      data[size] = v;
      return this;
    }

    /** {@inheritDoc} */
    @Override
    final AbstractMatrix _make(final int m, final int n) {
      final int size;
      double[] data;

      size = (m * n);
      data = this.m_data;
      if (data.length != size) {
        data = new double[size];
        System.arraycopy(this.m_data, 0, data, 0, size);
      }
      return new DoubleMatrix1D(data, m, n);
    }
  }

  /** the internal growable long array */
  private static final class _LongArray extends _Array {

    /** the long data */
    private long[] m_data;

    /**
     * create the array
     *
     * @param data
     *          the data
     */
    _LongArray(final long[] data) {
      this.m_data = data;
    }

    /** {@inheritDoc} */
    @Override
    final void _setMaxSize(final int curSize, final int maxSize) {
      long[] data;
      if (this.m_data.length != maxSize) {
        data = new long[maxSize];
        System.arraycopy(this.m_data, 0, data, 0, curSize);
        this.m_data = data;
      }
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final byte v, final int size) {
      return this._append(((long) v), size);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final short v, final int size) {
      return this._append(((long) v), size);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final int v, final int size) {
      return this._append(((long) v), size);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final long v, final int size) {
      long[] data;

      data = this.m_data;
      if (data.length <= size) {
        data = new long[(size + 16) << 1];
        System.arraycopy(this.m_data, 0, data, 0, size);
        this.m_data = data;
      }
      data[size] = v;
      return this;
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final float v, final int size) {
      final long[] longData;
      final double[] doubleData;
      float[] floatData;
      long l;
      int i;

      if ((NumericalTypes.getTypes(v) & NumericalTypes.IS_LONG) != 0) {
        return this._append(((long) v), size);
      }

      longData = this.m_data;
      floatData = new float[(size < longData.length) ? longData.length
          : ((size + 16) << 1)];
      floatData[size] = v;
      tryFloats: {
        for (i = size; (--i) >= 0;) {
          l = longData[i];
          if ((NumericalTypes.getBestFloatingPointRepresentation(l) & //
              NumericalTypes.IS_FLOAT) == 0) {
            break tryFloats;
          }
          floatData[i] = l;
        }
        return new _FloatArray(floatData);
      }

      doubleData = new double[floatData.length];
      floatData = null;
      doubleData[size] = v;
      for (i = size; (--i) >= 0;) {
        doubleData[i] = longData[i];
      }
      return new _DoubleArray(doubleData);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final double v, final int size) {
      final long[] longData;
      final double[] doubleData;
      final int types;
      int i;

      types = NumericalTypes.getTypes(v);
      if ((types & NumericalTypes.IS_LONG) != 0) {
        return this._append(((long) v), size);
      }

      if (v != v) {
        return this._append(Float.NaN, size);
      }
      if (v >= Double.POSITIVE_INFINITY) {
        return this._append(Float.POSITIVE_INFINITY, size);
      }
      if (v <= Double.NEGATIVE_INFINITY) {
        return this._append(Float.NEGATIVE_INFINITY, size);
      }

      if ((types & NumericalTypes.IS_FLOAT) != 0) {
        return this._append(((float) v), size);
      }

      longData = this.m_data;
      doubleData = new double[(size < longData.length) ? longData.length
          : ((size + 16) << 1)];
      doubleData[size] = v;
      for (i = size; (--i) >= 0;) {
        doubleData[i] = longData[i];
      }
      return new _DoubleArray(doubleData);
    }

    /** {@inheritDoc} */
    @Override
    final AbstractMatrix _make(final int m, final int n) {
      final int size;
      long[] data;

      size = (m * n);
      data = this.m_data;
      if (data.length != size) {
        data = new long[size];
        System.arraycopy(this.m_data, 0, data, 0, size);
      }
      return new LongMatrix1D(data, m, n);
    }
  }

  /** the internal growable long array */
  private static final class _FloatArray extends _Array {

    /** the float data */
    private float[] m_data;

    /**
     * create the array
     *
     * @param data
     *          the data
     */
    _FloatArray(final float[] data) {
      this.m_data = data;
    }

    /** {@inheritDoc} */
    @Override
    final void _setMaxSize(final int curSize, final int maxSize) {
      float[] data;
      if (this.m_data.length != maxSize) {
        data = new float[maxSize];
        System.arraycopy(this.m_data, 0, data, 0, curSize);
        this.m_data = data;
      }
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final byte v, final int size) {
      return this._append(((long) v), size);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final short v, final int size) {
      return this._append(((long) v), size);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final int v, final int size) {
      return this._append(((long) v), size);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final long v, final int size) {
      if ((NumericalTypes.getTypes(v) & //
          NumericalTypes.IS_FLOAT) == 0) {
        return this._append(((double) v), size);
      }
      return this._append(((float) v), size);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final float v, final int size) {
      float[] data;

      data = this.m_data;
      if (data.length <= size) {
        data = new float[(size + 16) << 1];
        System.arraycopy(this.m_data, 0, data, 0, size);
        this.m_data = data;
      }
      data[size] = v;
      return this;
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final double v, final int size) {
      final float[] floatData;
      final double[] doubleData;
      int i;

      if (v != v) {
        return this._append(Float.NaN, size);
      }
      if (v >= Double.POSITIVE_INFINITY) {
        return this._append(Float.POSITIVE_INFINITY, size);
      }
      if (v <= Double.NEGATIVE_INFINITY) {
        return this._append(Float.NEGATIVE_INFINITY, size);
      }

      if ((NumericalTypes.getTypes(v) & //
          NumericalTypes.IS_FLOAT) != 0) {
        return this._append(((float) v), size);
      }

      floatData = this.m_data;
      doubleData = new double[(size < floatData.length) ? floatData.length
          : ((size + 16) << 1)];
      doubleData[size] = v;
      for (i = size; (--i) >= 0;) {
        doubleData[i] = floatData[i];
      }
      return new _DoubleArray(doubleData);
    }

    /** {@inheritDoc} */
    @Override
    final AbstractMatrix _make(final int m, final int n) {
      final int size;
      float[] data;

      size = (m * n);
      data = this.m_data;
      if (data.length != size) {
        data = new float[size];
        System.arraycopy(this.m_data, 0, data, 0, size);
      }
      return new FloatMatrix1D(data, m, n);
    }
  }

  /** the internal growable int array */
  private static final class _IntArray extends _Array {

    /** the int data */
    private int[] m_data;

    /**
     * create the array
     *
     * @param data
     *          the data
     */
    _IntArray(final int[] data) {
      this.m_data = data;
    }

    /** {@inheritDoc} */
    @Override
    final void _setMaxSize(final int curSize, final int maxSize) {
      int[] data;
      if (this.m_data.length != maxSize) {
        data = new int[maxSize];
        System.arraycopy(this.m_data, 0, data, 0, curSize);
        this.m_data = data;
      }
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final byte v, final int size) {
      return this._append(((int) v), size);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final short v, final int size) {
      return this._append(((int) v), size);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final int v, final int size) {
      int[] data;

      data = this.m_data;
      if (data.length <= size) {
        data = new int[(size + 16) << 1];
        System.arraycopy(this.m_data, 0, data, 0, size);
        this.m_data = data;
      }
      data[size] = v;
      return this;
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final long v, final int size) {
      long[] longData;
      int[] intData;
      int i;

      if ((v >= Integer.MIN_VALUE) && (v <= Integer.MAX_VALUE)) {
        return this._append(((int) v), size);
      }

      intData = this.m_data;
      longData = new long[(size < intData.length) ? intData.length
          : ((size + 16) << 1)];
      longData[size] = v;
      for (i = size; (--i) >= 0;) {
        longData[i] = intData[i];
      }
      return new _LongArray(longData);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final float v, final int size) {
      final int[] intData;
      final double[] doubleData;
      final int types;
      float[] floatData;
      int i, val;

      types = NumericalTypes.getTypes(v);
      if ((types & NumericalTypes.IS_INT) != 0) {
        return this._append(((int) v), size);
      }

      if ((types & NumericalTypes.IS_LONG) != 0) {
        return this._append(((long) v), size);
      }

      intData = this.m_data;
      floatData = new float[(size < intData.length) ? intData.length
          : ((size + 16) << 1)];
      floatData[size] = v;
      tryFloats: {
        for (i = size; (--i) >= 0;) {
          val = intData[i];
          if ((NumericalTypes.getBestFloatingPointRepresentation(val) //
              & NumericalTypes.IS_FLOAT) == 0) {
            break tryFloats;
          }
          floatData[i] = val;
        }
        return new _FloatArray(floatData);
      }

      doubleData = new double[floatData.length];
      floatData = null;
      doubleData[size] = v;
      for (i = size; (--i) >= 0;) {
        doubleData[i] = intData[i];
      }
      return new _DoubleArray(doubleData);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final double v, final int size) {
      final int[] intData;
      final double[] doubleData;
      final int types;
      int i;

      types = NumericalTypes.getTypes(v);
      if ((types & NumericalTypes.IS_INT) != 0) {
        return this._append(((int) v), size);
      }

      if ((types & NumericalTypes.IS_LONG) != 0) {
        return this._append(((long) v), size);
      }

      if (v != v) {
        return this._append(Float.NaN, size);
      }
      if (v >= Double.POSITIVE_INFINITY) {
        return this._append(Float.POSITIVE_INFINITY, size);
      }
      if (v <= Double.NEGATIVE_INFINITY) {
        return this._append(Float.NEGATIVE_INFINITY, size);
      }

      if ((types & NumericalTypes.IS_FLOAT) != 0) {
        return this._append(((float) v), size);
      }

      intData = this.m_data;
      doubleData = new double[(size < intData.length) ? intData.length
          : ((size + 16) << 1)];
      doubleData[size] = v;
      for (i = size; (--i) >= 0;) {
        doubleData[i] = intData[i];
      }
      return new _DoubleArray(doubleData);
    }

    /** {@inheritDoc} */
    @Override
    final AbstractMatrix _make(final int m, final int n) {
      final int size;
      int[] data;

      size = (m * n);
      data = this.m_data;
      if (data.length != size) {
        data = new int[size];
        System.arraycopy(this.m_data, 0, data, 0, size);
      }
      return new IntMatrix1D(data, m, n);
    }
  }

  /** the internal growable short array */
  private static final class _ShortArray extends _Array {

    /** the short data */
    private short[] m_data;

    /**
     * create the array
     *
     * @param data
     *          the data
     */
    _ShortArray(final short[] data) {
      this.m_data = data;
    }

    /** {@inheritDoc} */
    @Override
    final void _setMaxSize(final int curSize, final int maxSize) {
      short[] data;
      if (this.m_data.length != maxSize) {
        data = new short[maxSize];
        System.arraycopy(this.m_data, 0, data, 0, curSize);
        this.m_data = data;
      }
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final byte v, final int size) {
      return this._append(((short) v), size);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final short v, final int size) {
      short[] data;

      data = this.m_data;
      if (data.length <= size) {
        data = new short[(size + 16) << 1];
        System.arraycopy(this.m_data, 0, data, 0, size);
        this.m_data = data;
      }
      data[size] = v;
      return this;
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final int v, final int size) {
      int[] intData;
      short[] shortData;
      int i;

      if ((v >= Short.MIN_VALUE) && (v <= Short.MAX_VALUE)) {
        return this._append(((short) v), size);
      }

      shortData = this.m_data;
      intData = new int[(size < shortData.length) ? shortData.length
          : ((size + 16) << 1)];
      intData[size] = v;
      for (i = size; (--i) >= 0;) {
        intData[i] = shortData[i];
      }
      return new _IntArray(intData);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final long v, final int size) {
      long[] longData;
      short[] shortData;
      int i;

      if ((v >= Short.MIN_VALUE) && (v <= Short.MAX_VALUE)) {
        return this._append(((short) v), size);
      }
      if ((v >= Integer.MIN_VALUE) && (v <= Integer.MAX_VALUE)) {
        return this._append(((int) v), size);
      }

      shortData = this.m_data;
      longData = new long[(size < shortData.length) ? shortData.length
          : ((size + 16) << 1)];
      longData[size] = v;
      for (i = size; (--i) >= 0;) {
        longData[i] = shortData[i];
      }
      return new _LongArray(longData);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final float v, final int size) {
      final short[] shortData;
      float[] floatData;
      final int types;
      int i;

      types = NumericalTypes.getTypes(v);
      if ((types & NumericalTypes.IS_SHORT) != 0) {
        return this._append(((short) v), size);
      }

      if ((types & NumericalTypes.IS_INT) != 0) {
        return this._append(((int) v), size);
      }

      if ((types & NumericalTypes.IS_LONG) != 0) {
        return this._append(((long) v), size);
      }

      shortData = this.m_data;
      floatData = new float[(size < shortData.length) ? shortData.length
          : ((size + 16) << 1)];
      floatData[size] = v;
      for (i = size; (--i) >= 0;) {
        floatData[i] = shortData[i];
      }
      return new _FloatArray(floatData);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final double v, final int size) {
      final short[] shortData;
      double[] doubleData;
      final int types;
      int i;

      types = NumericalTypes.getTypes(v);
      if ((types & NumericalTypes.IS_SHORT) != 0) {
        return this._append(((short) v), size);
      }

      if ((types & NumericalTypes.IS_INT) != 0) {
        return this._append(((int) v), size);
      }

      if ((types & NumericalTypes.IS_LONG) != 0) {
        return this._append(((long) v), size);
      }

      if (v != v) {
        return this._append(Float.NaN, size);
      }
      if (v >= Double.POSITIVE_INFINITY) {
        return this._append(Float.POSITIVE_INFINITY, size);
      }
      if (v <= Double.NEGATIVE_INFINITY) {
        return this._append(Float.NEGATIVE_INFINITY, size);
      }

      if ((types & NumericalTypes.IS_FLOAT) != 0) {
        return this._append(((float) v), size);
      }

      shortData = this.m_data;
      doubleData = new double[(size < shortData.length) ? shortData.length
          : ((size + 16) << 1)];
      doubleData[size] = v;
      for (i = size; (--i) >= 0;) {
        doubleData[i] = shortData[i];
      }
      return new _DoubleArray(doubleData);
    }

    /** {@inheritDoc} */
    @Override
    final AbstractMatrix _make(final int m, final int n) {
      final int size;
      short[] data;

      size = (m * n);
      data = this.m_data;
      if (data.length != size) {
        data = new short[size];
        System.arraycopy(this.m_data, 0, data, 0, size);
      }
      return new ShortMatrix1D(data, m, n);
    }
  }

  /** the internal growable byte array */
  private static final class _ByteArray extends _Array {

    /** the byte data */
    private byte[] m_data;

    /**
     * create the array
     *
     * @param data
     *          the data
     */
    _ByteArray(final byte[] data) {
      this.m_data = data;
    }

    /** {@inheritDoc} */
    @Override
    final void _setMaxSize(final int curSize, final int maxSize) {
      byte[] data;
      if (this.m_data.length != maxSize) {
        data = new byte[maxSize];
        System.arraycopy(this.m_data, 0, data, 0, curSize);
        this.m_data = data;
      }
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final byte v, final int size) {
      byte[] data;

      data = this.m_data;
      if (data.length <= size) {
        data = new byte[(size + 16) << 1];
        System.arraycopy(this.m_data, 0, data, 0, size);
        this.m_data = data;
      }
      data[size] = v;
      return this;
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final short v, final int size) {
      short[] shortData;
      byte[] byteData;
      int i;

      if ((v >= Byte.MIN_VALUE) && (v <= Byte.MAX_VALUE)) {
        return this._append(((byte) v), size);
      }

      byteData = this.m_data;
      shortData = new short[(size < byteData.length) ? byteData.length
          : ((size + 16) << 1)];
      shortData[size] = v;
      for (i = size; (--i) >= 0;) {
        shortData[i] = byteData[i];
      }
      return new _ShortArray(shortData);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final int v, final int size) {
      int[] intData;
      byte[] byteData;
      int i;

      if ((v >= Byte.MIN_VALUE) && (v <= Byte.MAX_VALUE)) {
        return this._append(((byte) v), size);
      }
      if ((v >= Short.MIN_VALUE) && (v <= Short.MAX_VALUE)) {
        return this._append(((short) v), size);
      }

      byteData = this.m_data;
      intData = new int[(size < byteData.length) ? byteData.length
          : ((size + 16) << 1)];
      intData[size] = v;
      for (i = size; (--i) >= 0;) {
        intData[i] = byteData[i];
      }
      return new _IntArray(intData);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final long v, final int size) {
      long[] longData;
      byte[] byteData;
      int i;

      if ((v >= Byte.MIN_VALUE) && (v <= Byte.MAX_VALUE)) {
        return this._append(((byte) v), size);
      }
      if ((v >= Short.MIN_VALUE) && (v <= Short.MAX_VALUE)) {
        return this._append(((short) v), size);
      }
      if ((v >= Integer.MIN_VALUE) && (v <= Integer.MAX_VALUE)) {
        return this._append(((int) v), size);
      }

      byteData = this.m_data;
      longData = new long[(size < byteData.length) ? byteData.length
          : ((size + 16) << 1)];
      longData[size] = v;
      for (i = size; (--i) >= 0;) {
        longData[i] = byteData[i];
      }
      return new _LongArray(longData);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final float v, final int size) {
      final byte[] byteData;
      float[] floatData;
      final int types;
      int i;

      types = NumericalTypes.getTypes(v);
      if ((types & NumericalTypes.IS_BYTE) != 0) {
        return this._append(((byte) v), size);
      }

      if ((types & NumericalTypes.IS_SHORT) != 0) {
        return this._append(((short) v), size);
      }

      if ((types & NumericalTypes.IS_INT) != 0) {
        return this._append(((int) v), size);
      }

      if ((types & NumericalTypes.IS_LONG) != 0) {
        return this._append(((long) v), size);
      }

      byteData = this.m_data;
      floatData = new float[(size < byteData.length) ? byteData.length
          : ((size + 16) << 1)];
      floatData[size] = v;
      for (i = size; (--i) >= 0;) {
        floatData[i] = byteData[i];
      }
      return new _FloatArray(floatData);
    }

    /** {@inheritDoc} */
    @Override
    final _Array _append(final double v, final int size) {
      final byte[] byteData;
      final int types;
      double[] doubleData;
      int i;

      types = NumericalTypes.getTypes(v);
      if ((types & NumericalTypes.IS_BYTE) != 0) {
        return this._append(((byte) v), size);
      }

      if ((types & NumericalTypes.IS_SHORT) != 0) {
        return this._append(((short) v), size);
      }

      if ((types & NumericalTypes.IS_INT) != 0) {
        return this._append(((int) v), size);
      }

      if ((types & NumericalTypes.IS_LONG) != 0) {
        return this._append(((long) v), size);
      }

      if (v != v) {
        return this._append(Float.NaN, size);
      }
      if (v >= Double.POSITIVE_INFINITY) {
        return this._append(Float.POSITIVE_INFINITY, size);
      }
      if (v <= Double.NEGATIVE_INFINITY) {
        return this._append(Float.NEGATIVE_INFINITY, size);
      }

      if ((types & NumericalTypes.IS_FLOAT) != 0) {
        return this._append(((float) v), size);
      }

      byteData = this.m_data;
      doubleData = new double[(size < byteData.length) ? byteData.length
          : ((size + 16) << 1)];
      doubleData[size] = v;
      for (i = size; (--i) >= 0;) {
        doubleData[i] = byteData[i];
      }
      return new _DoubleArray(doubleData);
    }

    /** {@inheritDoc} */
    @Override
    final AbstractMatrix _make(final int m, final int n) {
      final int size;
      byte[] data;

      size = (m * n);
      data = this.m_data;
      if (data.length != size) {
        data = new byte[size];
        System.arraycopy(this.m_data, 0, data, 0, size);
      }
      return new ByteMatrix1D(data, m, n);
    }
  }
}
