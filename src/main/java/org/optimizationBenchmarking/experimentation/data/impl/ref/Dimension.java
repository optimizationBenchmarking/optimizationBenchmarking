package org.optimizationBenchmarking.experimentation.data.impl.ref;

import org.optimizationBenchmarking.experimentation.data.spec.EDimensionDirection;
import org.optimizationBenchmarking.experimentation.data.spec.EDimensionType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A dimension.
 */
public class Dimension extends _NamedIDObject implements IDimension {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the parsers */
  final NumberParser<Number> m_parser;

  /** the primitive type */
  final EPrimitiveType m_primitiveType;

  /** the dimension type */
  final EDimensionType m_dimensionType;

  /** the direction of the dimension */
  final EDimensionDirection m_direction;

  /** the default lower boundary */
  final Number m_defaultLower;

  /** the default upper boundary */
  final Number m_defaultUpper;

  /**
   * Create a new dimension
   *
   * @param name
   *          the name of the dimension
   * @param desc
   *          the description
   * @param parser
   *          the parser
   * @param dimType
   *          the dimension type
   * @param direction
   *          is the dimension direction
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  Dimension(final String name, final String desc,
      final NumberParser<? extends Number> parser,
      final EDimensionType dimType, final EDimensionDirection direction) {
    super(name, desc);

    final EPrimitiveType type;

    type = EPrimitiveType.getPrimitiveType(parser.getOutputClass());
    if ((type != null) && (type.isNumber())) {
      this.m_primitiveType = type;
    } else {
      throw new IllegalArgumentException(
          "The basic type must be assignable to a primitive number, but " + //$NON-NLS-1$
              String.valueOf(parser) + " with output class " + //$NON-NLS-1$
              TextUtils.className(parser.getOutputClass()) + " is not."); //$NON-NLS-1$
    }

    if (type.isInteger() != parser.areBoundsInteger()) {
      throw new IllegalArgumentException(
          "The isInteger feature of the dimension type must fit to the integer feature of the parser's bounds. However, the dimension type is " //$NON-NLS-1$
              + type
              + " with the isInteger feature "//$NON-NLS-1$
              + type.isInteger()
              + " and the parser ("//$NON-NLS-1$
              + TextUtils.className(parser.getClass())
              + " for "//$NON-NLS-1$
              + TextUtils.className(parser.getOutputClass())
              + ") has the areBoundsInteger feature "//$NON-NLS-1$
              + parser.areBoundsInteger() + '.');
    }

    if (dimType == null) {
      throw new IllegalArgumentException(//
          "Dimension must not be null."); //$NON-NLS-1$
    }
    if (direction == null) {
      throw new IllegalArgumentException(//
          "Direction must not be null."); //$NON-NLS-1$
    }

    this.m_dimensionType = dimType;
    this.m_direction = direction;
    this.m_parser = ((NumberParser) parser);

    switch (this.m_primitiveType) {
      case BYTE: {
        this.m_defaultLower = Byte.valueOf((byte) (parser
            .getLowerBoundLong()));
        this.m_defaultUpper = Byte.valueOf((byte) (parser
            .getUpperBoundLong()));
        break;
      }
      case SHORT: {
        this.m_defaultLower = Short.valueOf((short) (parser
            .getLowerBoundLong()));
        this.m_defaultUpper = Short.valueOf((short) (parser
            .getUpperBoundLong()));
        break;
      }
      case INT: {
        this.m_defaultLower = Integer.valueOf((int) (parser
            .getLowerBoundLong()));
        this.m_defaultUpper = Integer.valueOf((int) (parser
            .getUpperBoundLong()));
        break;
      }
      case LONG: {
        this.m_defaultLower = Long.valueOf(parser.getLowerBoundLong());
        this.m_defaultUpper = Long.valueOf(parser.getUpperBoundLong());
        break;
      }
      case FLOAT: {
        this.m_defaultLower = Float.valueOf((float) (parser
            .getLowerBoundDouble()));
        this.m_defaultUpper = Float.valueOf((float) (parser
            .getUpperBoundDouble()));
        break;
      }
      case DOUBLE: {
        this.m_defaultLower = Double.valueOf(parser.getLowerBoundDouble());
        this.m_defaultUpper = Double.valueOf(parser.getUpperBoundDouble());
        break;
      }
      default: {
        throw new IllegalArgumentException(
            (((("Invalid type '" + this.m_primitiveType) + //$NON-NLS-1$
            "' for dimension '") + this.getName()) + '\'') + '.'); //$NON-NLS-1$
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    final EPrimitiveType type;
    final long lowerL, upperL;
    final double lowerD, upperD;
    final boolean useLower, useUpper;

    textOut.append(this.m_name);
    textOut.append('(');
    textOut.append(this.m_dimensionType.toString());
    textOut.append(',');
    textOut.append(this.m_direction.toString());
    textOut.append(',');
    type = this.m_primitiveType;
    textOut.append(type.getPrimitiveTypeName().toString());

    if (type.isInteger()) {
      lowerL = this.m_defaultLower.longValue();
      upperL = this.m_defaultUpper.longValue();
      switch (type) {
        case BYTE: {
          useLower = (lowerL > Byte.MIN_VALUE);
          useUpper = (upperL < Byte.MAX_VALUE);
          break;
        }
        case SHORT: {
          useLower = (lowerL > Short.MIN_VALUE);
          useUpper = (upperL < Short.MAX_VALUE);
          break;
        }
        case INT: {
          useLower = (lowerL > Integer.MIN_VALUE);
          useUpper = (upperL < Integer.MAX_VALUE);
          break;
        }
        default: {
          useLower = (lowerL > Long.MIN_VALUE);
          useUpper = (upperL < Long.MAX_VALUE);
          break;
        }
      }

      if (useLower || useUpper) {
        textOut.append(',');
        textOut.append('[');
        if (useLower) {
          textOut.append(lowerL);
        }
        textOut.append(',');
        if (useUpper) {
          textOut.append(upperL);
        }
        textOut.append(']');
      }
    } else {
      lowerD = this.m_defaultLower.doubleValue();
      upperD = this.m_defaultUpper.doubleValue();
      useLower = (lowerD > Double.NEGATIVE_INFINITY);
      useUpper = (upperD < Double.POSITIVE_INFINITY);
      if (useLower || useUpper) {
        textOut.append(',');
        textOut.append('[');
        if (useLower) {
          textOut.append(lowerD);
        }
        textOut.append(',');
        if (useUpper) {
          textOut.append(upperD);
        }
        textOut.append(']');
      }
    }

    textOut.append(')');
  }

  /** {@inheritDoc} */
  @Override
  public final NumberParser<Number> getParser() {
    return this.m_parser;
  }

  /** {@inheritDoc} */
  @Override
  public final EPrimitiveType getDataType() {
    return this.m_primitiveType;
  }

  /** {@inheritDoc} */
  @Override
  public final EDimensionType getDimensionType() {
    return this.m_dimensionType;
  }

  /** {@inheritDoc} */
  @Override
  public final EDimensionDirection getDirection() {
    return this.m_direction;
  }

  /** {@inheritDoc} */
  @Override
  public final int getIndex() {
    return this.m_id;
  }

  /** {@inheritDoc} */
  @Override
  public final DimensionSet getOwner() {
    return ((DimensionSet) (this.m_owner));
  }
}
