package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.EDimensionDirection;
import org.optimizationBenchmarking.experimentation.data.spec.EDimensionType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet;
import org.optimizationBenchmarking.utils.parsers.LooseDoubleParser;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IDimension}
 * interface.
 */
public abstract class AbstractDimension extends AbstractNamedElement
    implements IDimension {

  /** the owner */
  IDimensionSet m_owner;

  /**
   * Create the abstract dimension. If {@code owner==null}, you must later
   * set it via
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractDimensionSet#own(AbstractDimension)}
   * .
   *
   * @param owner
   *          the owner
   */
  protected AbstractDimension(final IDimensionSet owner) {
    super();
    this.m_owner = owner;
  }

  /**
   * Validate a dimension's type
   *
   * @param dimensionType
   *          the dimension type
   */
  public static final void validateType(final EDimensionType dimensionType) {
    if (dimensionType == null) {
      throw new IllegalArgumentException(//
          "Dimension type cannot be null."); //$NON-NLS-1$
    }
  }

  /**
   * Validate the direction of a dimension
   *
   * @param dimensionDirection
   *          the direction of a dimension
   */
  public static final void validateDirection(
      final EDimensionDirection dimensionDirection) {
    if (dimensionDirection == null) {
      throw new IllegalArgumentException(//
          "Dimension direction cannot be null.");//$NON-NLS-1$
    }
  }

  /**
   * Validate a dimension's parser
   *
   * @param parser
   *          the parser
   * @return the primitive type corresponding to the parser's output class
   */
  public static final EPrimitiveType validateParser(
      final NumberParser<?> parser) {
    final EPrimitiveType type;
    if (parser == null) {
      throw new IllegalArgumentException(//
          "The parser for a dimension's value cannot be null."); //$NON-NLS-1$
    }

    type = EPrimitiveType.getPrimitiveType(parser.getOutputClass());
    if (type == null) {
      throw new IllegalArgumentException(//
          "The parser for a dimension's value cannot have a null output class, but "//$NON-NLS-1$
              + parser + " has.");//$NON-NLS-1$
    }
    if (!(type.isNumber())) {
      throw new IllegalArgumentException(
          "The basic type must be assignable to a primitive number, but " + //$NON-NLS-1$
              String.valueOf(parser) + " with output class " + //$NON-NLS-1$
              TextUtils.className(parser.getOutputClass()) + " is not."); //$NON-NLS-1$
    }
    return type;
  }

  /**
   * Validate a dimension's index
   *
   * @param index
   *          the index
   */
  public static final void validateIndex(final int index) {
    if (index < 0) {
      throw new IllegalArgumentException(//
          "Dimension index type cannot less than 0, but is " + index); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final IDimensionSet getOwner() {
    return this.m_owner;
  }

  /** {@inheritDoc} */
  @Override
  public EPrimitiveType getDataType() {
    return EPrimitiveType.DOUBLE;
  }

  /** {@inheritDoc} */
  @Override
  public EDimensionType getDimensionType() {
    return EDimensionType.QUALITY_PROBLEM_INDEPENDENT;
  }

  /** {@inheritDoc} */
  @Override
  public EDimensionDirection getDirection() {
    return EDimensionDirection.DECREASING;
  }

  /** {@inheritDoc} */
  @Override
  public int getIndex() {
    return 0;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public NumberParser<Number> getParser() {
    return ((NumberParser) (LooseDoubleParser.INSTANCE));
  }
}
