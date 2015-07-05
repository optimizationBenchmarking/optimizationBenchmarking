package org.optimizationBenchmarking.experimentation.data.impl.ref;

import java.util.Collection;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/** a factory for long-encoded data points */
final class _LongEncodedDataFactory extends DataFactory {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the dimension set */
  private final ArraySetView<Dimension> m_dims;

  /**
   * create the data factory
   *
   * @param dims
   *          the dimensions
   */
  _LongEncodedDataFactory(final ArraySetView<Dimension> dims) {
    super();
    this.m_dims = dims;
  }

  /** parse a string */
  @Override
  public final DataPoint parseString(final String s) {
    final int length, size;
    final long[] data;
    int i, j, index;
    char ch;
    Number current;
    Dimension dim;

    size = this.m_dims.size();

    data = new long[size];

    j = -1;
    length = s.length();
    ch = 0;
    try {
      loop: for (index = 0; index < size; index++) {
        i = (j + 1);
        while (s.charAt(i) <= ' ') {
          i++;
        }
        if (index <= 0) {
          if (s.charAt(i) == '[') {
            i++;
          }
        }
        j = i;
        inner: {
          for (j = i; j < length; j++) {
            ch = s.charAt(j);
            if ((ch <= ' ') || (ch == ',')) {
              break inner;
            }
          }
          if (ch == ']') {
            j--;
          }
        }

        dim = this.m_dims.get(index);
        current = dim.m_parser.parseString(s.substring(i, j));

        switch (dim.m_primitiveType) {
          case BYTE: {
            data[index] = current.byteValue();
            continue loop;
          }
          case SHORT: {
            data[index] = current.shortValue();
            continue loop;
          }
          case INT: {
            data[index] = current.intValue();
            continue loop;
          }
          case LONG: {
            data[index] = current.longValue();
            continue loop;
          }
          case FLOAT: {
            data[index] = Float.floatToIntBits(current.floatValue());
            continue loop;
          }
          default: {
            data[index] = Double.doubleToLongBits(current.doubleValue());
            continue loop;
          }
        }
      }

      return new _LongEncodedDataPoint(this.m_dims, data);

    } catch (final Throwable t) {
      throw new IllegalArgumentException("The string '" + s //$NON-NLS-1$
          + "' does not represent a valid data point.", t);//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final DataPoint parseObject(final Object o) {
    if (o instanceof _LongEncodedDataPoint) {
      return ((_LongEncodedDataPoint) o);
    }
    return this.parseString(String.valueOf(o));
  }

  /** {@inheritDoc} */
  @Override
  public final DataPoint parseNumbers(final Number... numbers) {
    final long[] data;
    int index;
    Dimension dim;
    Number current;

    data = new long[this.m_dims.size()];
    loop: for (index = 0; index < data.length; index++) {
      dim = this.m_dims.get(index);
      current = numbers[index];
      switch (dim.m_primitiveType) {
        case BYTE: {
          data[index] = current.byteValue();
          continue loop;
        }
        case SHORT: {
          data[index] = current.shortValue();
          continue loop;
        }
        case INT: {
          data[index] = current.intValue();
          continue loop;
        }
        case LONG: {
          data[index] = current.longValue();
          continue loop;
        }
        case FLOAT: {
          data[index] = Float.floatToIntBits(current.floatValue());
          continue loop;
        }
        default: {
          data[index] = Double.doubleToLongBits(current.doubleValue());
          continue loop;
        }
      }
    }

    return new _LongEncodedDataPoint(this.m_dims, data);
  }

  /** {@inheritDoc} */
  @Override
  public final Run createRun(final Instance instance,
      final Collection<DataPoint> points) {
    return new _BasicRun(points.toArray(new DataPoint[points.size()]),
        this.m_dims);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final Class<DataPoint> getOutputClass() {
    return ((Class) (_LongEncodedDataPoint.class));
  }

}
