package org.optimizationBenchmarking.utils.graphics;

import java.awt.geom.Dimension2D;

import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * A physical dimension is a
 * {@link org.optimizationBenchmarking.utils.graphics.DoubleDimension}
 * associated with a length unit
 */
public class PhysicalDimension extends DoubleDimension {
  /** the length unit */
  private final ELength m_unit;

  /**
   * create a new double dimension
   * 
   * @param width
   *          the width
   * @param height
   *          the height
   * @param unit
   *          the length unit
   */
  public PhysicalDimension(final double width, final double height,
      final ELength unit) {
    super(width, height);
    PhysicalDimension.__checkUnit(unit);
    this.m_unit = unit;
  }

  /**
   * Check the unit
   * 
   * @param unit
   *          the unit
   */
  private static final void __checkUnit(final ELength unit) {
    if (unit == null) {
      throw new IllegalArgumentException("Length unit must not be null."); //$NON-NLS-1$
    }
  }

  /**
   * create a new double dimension
   * 
   * @param dim
   *          the dimension
   * @param unit
   *          the unit
   */
  public PhysicalDimension(final Dimension2D dim, final ELength unit) {
    this(dim.getWidth(), dim.getHeight(), unit);
  }

  /**
   * Get the unit
   * 
   * @return the unit
   */
  public final ELength getUnit() {
    return this.m_unit;
  }

  /**
   * Convert this object to a given unit
   * 
   * @param unit
   *          the unit
   * @return the new object
   */
  public PhysicalDimension convertTo(final ELength unit) {
    if (unit == this.m_unit) {
      return this;
    }
    PhysicalDimension.__checkUnit(unit);
    return new PhysicalDimension(//
        this.m_unit.convertTo(this.m_width, unit),//
        this.m_unit.convertTo(this.m_height, unit),//
        unit);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean contains(final Dimension2D dim) {
    return super.contains(//
        (dim instanceof PhysicalDimension) ? //
        ((PhysicalDimension) dim).convertTo(this.m_unit)
            : dim);
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return Double.toString(this.m_width) + 'x'
        + Double.toString(this.m_height) + this.m_unit.getShortcut();
  }
}
