package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.EDimensionDirection;
import org.optimizationBenchmarking.experimentation.data.spec.EDimensionType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/**
 * A basic implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IDimension}
 * interface.
 */
public class BasicDimension extends AbstractDimension {

  /** the name */
  private final String m_name;
  /** the description */
  private final String m_description;
  /** the data type */
  private final EPrimitiveType m_dataType;
  /** the dimension type */
  private final EDimensionType m_dimensionType;
  /** the dimension direction */
  private final EDimensionDirection m_dimensionDirection;
  /** the parser */
  private final NumberParser<Number> m_parser;
  /** the index */
  private final int m_index;

  /**
   * Create the abstract instance. If {@code owner==null}, you must later
   * set it via
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractDimensionSet#own(AbstractDimension)}
   *
   * @param owner
   *          the owner
   * @param name
   *          the name
   * @param description
   *          the description
   * @param dataType
   *          the data type
   * @param dimensionType
   *          the dimension type
   * @param dimensionDirection
   *          the direction
   * @param parser
   *          the parser
   * @param index
   *          the index
   */
  public BasicDimension(final IDimensionSet owner, final String name,
      final String description, final EPrimitiveType dataType,
      final EDimensionType dimensionType,
      final EDimensionDirection dimensionDirection,
      final NumberParser<Number> parser, final int index) {
    super(owner);

    this.m_name = AbstractNamedElement.formatName(name);
    this.m_description = AbstractNamedElement
        .formatDescription(description);

    if (dataType == null) {
      throw new IllegalArgumentException(//
          "Dimension data type cannot be null."); //$NON-NLS-1$
    }
    if (dimensionType == null) {
      throw new IllegalArgumentException(//
          "Dimension type cannot be null."); //$NON-NLS-1$
    }
    if (dimensionDirection == null) {
      throw new IllegalArgumentException(//
          "Dimension direction cannot be null."); //$NON-NLS-1$
    }
    if (parser == null) {
      throw new IllegalArgumentException(//
          "Dimension parser type cannot be null."); //$NON-NLS-1$
    }
    if (index < 0) {
      throw new IllegalArgumentException(//
          "Dimension index type cannot less than 0, but is " + index); //$NON-NLS-1$
    }

    if (!(dataType.getWrapperClass().isAssignableFrom(parser
        .getOutputClass()))) {
      throw new IllegalArgumentException(//
          "Dimension data type '" + dataType + //$NON-NLS-1$
              " does not fit to parser " + parser + //$NON-NLS-1$
              " with output class " + //$NON-NLS-1$
              parser.getOutputClass());
    }

    this.m_dataType = dataType;
    this.m_dimensionType = dimensionType;
    this.m_dimensionDirection = dimensionDirection;
    this.m_parser = parser;
    this.m_index = index;
  }

  /** {@inheritDoc} */
  @Override
  public final EPrimitiveType getDataType() {
    return this.m_dataType;
  }

  /** {@inheritDoc} */
  @Override
  public final EDimensionType getDimensionType() {
    return this.m_dimensionType;
  }

  /** {@inheritDoc} */
  @Override
  public final EDimensionDirection getDirection() {
    return this.m_dimensionDirection;
  }

  /** {@inheritDoc} */
  @Override
  public final int getIndex() {
    return this.m_index;
  }

  /** {@inheritDoc} */
  @Override
  public final NumberParser<Number> getParser() {
    return this.m_parser;
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return this.m_name;
  }

  /** {@inheritDoc} */
  @Override
  public final String getDescription() {
    return this.m_description;
  }
}
