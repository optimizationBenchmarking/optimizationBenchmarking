package org.optimizationBenchmarking.experimentation.data.impl.ref;

import org.optimizationBenchmarking.experimentation.data.spec.IDataPoint;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.IAggregate;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * an internal base class for non-automatically generated implementations
 * of {@link DataPoint}
 */
abstract class _AbstractDataPoint extends DataPoint {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the reference to the dimension set */
  final ArraySetView<Dimension> m_dims;

  /**
   * create the data point
   *
   * @param dims
   *          the dimension set
   */
  _AbstractDataPoint(final ArraySetView<Dimension> dims) {
    super();
    this.m_dims = dims;
  }

  /** {@inheritDoc} */
  @Override
  public final void aggregateColumn(final int column,
      final IAggregate aggregate) {
    switch (this.m_dims.get(column).m_primitiveType) {
      case BYTE: {
        aggregate.append(this.getByte(column));
        return;
      }
      case SHORT: {
        aggregate.append(this.getShort(column));
        return;
      }
      case INT: {
        aggregate.append(this.getInt(column));
        return;
      }
      case LONG: {
        aggregate.append(this.getLong(column));
        return;
      }
      case FLOAT: {
        aggregate.append(this.getFloat(column));
        return;
      }
      default: {
        aggregate.append(this.getDouble(column));
        return;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void aggregateRow(final int row, final IAggregate aggregate) {
    int column;
    if (row == 0) {
      loop: for (column = this.size(); (--column) >= 0;) {
        switch (this.m_dims.get(column).m_primitiveType) {
          case BYTE: {
            aggregate.append(this.getByte(column));
            continue loop;
          }
          case SHORT: {
            aggregate.append(this.getShort(column));
            continue loop;
          }
          case INT: {
            aggregate.append(this.getInt(column));
            continue loop;
          }
          case LONG: {
            aggregate.append(this.getLong(column));
            continue loop;
          }
          case FLOAT: {
            aggregate.append(this.getFloat(column));
            continue loop;
          }
          default: {
            aggregate.append(this.getDouble(column));
            continue loop;
          }
        }
      }
    } else {
      throw new IllegalArgumentException(//
          "A data point has one row, but you specified zero-based index "//$NON-NLS-1$
              + row);
    }
  }

  /** {@inheritDoc} */
  @Override
  public int size() {
    return this.m_dims.size();
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final IDataPoint o) {
    final int size;
    int column, current;
    Dimension dim;

    if (o == this) {
      return 0;
    }

    size = this.m_dims.size();
    for (column = 0; column < size; column++) {
      dim = this.m_dims.get(column);

      switch (dim.m_primitiveType) {
        case BYTE: {
          current = Byte.compare(this.getByte(column), o.getByte(column));
          break;
        }
        case SHORT: {
          current = Short.compare(this.getShort(column),
              o.getShort(column));
          break;
        }
        case INT: {
          current = Integer.compare(this.getInt(column), o.getInt(column));
          break;
        }
        case LONG: {
          current = Long.compare(this.getLong(column), o.getLong(column));
          break;
        }
        case FLOAT: {
          current = EComparison.compareFloats(this.getFloat(column),
              o.getFloat(column));
          break;
        }
        default: {
          current = EComparison.compareDoubles(this.getDouble(column),
              o.getDouble(column));
          break;
        }
      }
      if (current != 0) {
        return (dim.m_direction.isIncreasing() ? current : (-current));
      }
    }

    return 0;
  }

  /** {@inheritDoc} */
  @Override
  public final void validateAfter(final DataPoint before) {

    int column, current;
    boolean after;
    Dimension dim;

    after = false;
    for (column = this.m_dims.size(); (--column) >= 0;) {
      dim = this.m_dims.get(column);

      switch (dim.m_primitiveType) {
        case BYTE: {
          current = Byte.compare(this.getByte(column),
              before.getByte(column));
          break;
        }
        case SHORT: {
          current = Short.compare(this.getShort(column),
              before.getShort(column));
          break;
        }
        case INT: {
          current = Integer.compare(this.getInt(column),
              before.getInt(column));
          break;
        }
        case LONG: {
          current = Long.compare(this.getLong(column),
              before.getLong(column));
          break;
        }
        case FLOAT: {
          current = EComparison.compareFloats(this.getFloat(column),
              before.getFloat(column));
          break;
        }
        default: {
          current = EComparison.compareDoubles(this.getDouble(column),
              before.getDouble(column));
          break;
        }
      }
      if (!(dim.m_direction.isIncreasing())) {
        current = (-current);
      }

      if (current < (dim.m_direction.isStrict() ? 1 : 0)) {
        throw new IllegalArgumentException("Data point " //$NON-NLS-1$
            + this + " cannot follow data point " + //$NON-NLS-1$
            before + " due to its value in dimension "//$NON-NLS-1$
            + dim);
      }
      after |= (current != 0);
    }

    if (after) {
      return;
    }
    throw new IllegalArgumentException("Data point " //$NON-NLS-1$
        + this + " cannot follow data point " + //$NON-NLS-1$
        before + ", since they are identical.");//$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    int column;
    IDataPoint other;

    if (o == this) {
      return true;
    }

    if (o instanceof IDataPoint) {
      other = ((IDataPoint) o);

      column = this.size();
      if (column == other.size()) {

        loop: for (; (--column) >= 0;) {

          switch (this.m_dims.get(column).m_primitiveType) {
            case BYTE: {
              if (this.getByte(column) != other.getByte(column)) {
                return false;
              }
              continue loop;
            }
            case SHORT: {
              if (this.getShort(column) != other.getShort(column)) {
                return false;
              }
              continue loop;
            }
            case INT: {
              if (this.getInt(column) != other.getInt(column)) {
                return false;
              }
              continue loop;
            }
            case LONG: {
              if (this.getLong(column) != other.getLong(column)) {
                return false;
              }
              continue loop;
            }
            case FLOAT: {
              if (EComparison.EQUAL.compare(this.getFloat(column),
                  other.getFloat(column))) {
                continue loop;
              }
              return false;
            }
            default: {
              if (EComparison.EQUAL.compare(this.getDouble(column),
                  other.getDouble(column))) {
                continue loop;
              }
              return false;
            }
          }
        }
        return true;
      }
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    final int size;
    int column, hashCode;

    size = this.size();
    hashCode = HashUtils.hashCode(size);

    loop: for (column = 0; column < size; column++) {
      switch (this.m_dims.get(column).m_primitiveType) {
        case BYTE: {
          hashCode = HashUtils.combineHashes(hashCode,//
              HashUtils.hashCode(this.getByte(column)));
          continue loop;
        }
        case SHORT: {
          hashCode = HashUtils.combineHashes(hashCode,//
              HashUtils.hashCode(this.getShort(column)));
          continue loop;
        }
        case INT: {
          hashCode = HashUtils.combineHashes(hashCode,//
              HashUtils.hashCode(this.getInt(column)));
          continue loop;
        }
        case LONG: {
          hashCode = HashUtils.combineHashes(hashCode,//
              HashUtils.hashCode(this.getLong(column)));
          continue loop;
        }
        case FLOAT: {
          hashCode = HashUtils.combineHashes(hashCode,//
              HashUtils.hashCode(this.getFloat(column)));
          continue loop;
        }
        default: {
          hashCode = HashUtils.combineHashes(hashCode,//
              HashUtils.hashCode(this.getDouble(column)));
          continue loop;
        }
      }
    }
    return hashCode;
  }

  /** {@inherintDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    final int size;
    int column;
    char prefix;

    prefix = '[';

    size = this.size();

    loop: for (column = 0; column < size; column++) {
      textOut.append(prefix);
      prefix = ',';
      switch (this.m_dims.get(column).m_primitiveType) {
        case BYTE: {
          textOut.append(this.getByte(column));
          continue loop;
        }
        case SHORT: {
          textOut.append(this.getShort(column));
          continue loop;
        }
        case INT: {
          textOut.append(this.getInt(column));
          continue loop;
        }
        case LONG: {
          textOut.append(this.getLong(column));
          continue loop;
        }
        case FLOAT: {
          textOut.append(this.getFloat(column));
          continue loop;
        }
        default: {
          textOut.append(this.getDouble(column));
          continue loop;
        }
      }
    }
    textOut.append(']');
  }
}
