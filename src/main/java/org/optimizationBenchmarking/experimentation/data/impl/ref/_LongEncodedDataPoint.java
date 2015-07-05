package org.optimizationBenchmarking.experimentation.data.impl.ref;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/** a datapoint whose fields are encoded as long values */
final class _LongEncodedDataPoint extends _AbstractDataPoint {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the data */
  private final long[] m_data;

  /**
   * create the long-encoded data point
   *
   * @param dims
   *          the dimensions
   * @param data
   *          the data
   */
  _LongEncodedDataPoint(final ArraySetView<Dimension> dims,
      final long[] data) {
    super(dims);
    this.m_data = data;
  }

  /** {@inheritDoc} */
  @Override
  public final double getDouble(final int index) {
    switch (this.m_dims.get(index).m_primitiveType) {
      case FLOAT: {
        return Float.intBitsToFloat((int) (this.m_data[index]));
      }
      case DOUBLE: {
        return Double.longBitsToDouble(this.m_data[index]);
      }
      default: {
        return this.m_data[index];
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final float getFloat(final int index) {
    switch (this.m_dims.get(index).m_primitiveType) {
      case FLOAT: {
        return Float.intBitsToFloat((int) (this.m_data[index]));
      }
      case DOUBLE: {
        return ((float) (Double.longBitsToDouble(this.m_data[index])));
      }
      default: {
        return this.m_data[index];
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final byte getByte(final int index) {
    switch (this.m_dims.get(index).m_primitiveType) {
      case FLOAT: {
        return ((byte) (Float.intBitsToFloat((int) (this.m_data[index]))));
      }
      case DOUBLE: {
        return ((byte) (Double.longBitsToDouble(this.m_data[index])));
      }
      default: {
        return ((byte) (this.m_data[index]));
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final short getShort(final int index) {
    switch (this.m_dims.get(index).m_primitiveType) {
      case FLOAT: {
        return ((short) (Float.intBitsToFloat((int) (this.m_data[index]))));
      }
      case DOUBLE: {
        return ((short) (Double.longBitsToDouble(this.m_data[index])));
      }
      default: {
        return ((short) (this.m_data[index]));
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int getInt(final int index) {
    switch (this.m_dims.get(index).m_primitiveType) {
      case FLOAT: {
        return ((int) (Float.intBitsToFloat((int) (this.m_data[index]))));
      }
      case DOUBLE: {
        return ((int) (Double.longBitsToDouble(this.m_data[index])));
      }
      default: {
        return ((int) (this.m_data[index]));
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final long getLong(final int index) {
    switch (this.m_dims.get(index).m_primitiveType) {
      case FLOAT: {
        return ((long) (Float.intBitsToFloat((int) (this.m_data[index]))));
      }
      case DOUBLE: {
        return ((long) (Double.longBitsToDouble(this.m_data[index])));
      }
      default: {
        return this.m_data[index];
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int size() {
    return this.m_data.length;
  }

  /** {@inheritDoc} */
  @Override
  public final int n() {
    return this.m_data.length;
  }
}
