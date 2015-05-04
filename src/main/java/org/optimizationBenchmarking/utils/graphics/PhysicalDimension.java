package org.optimizationBenchmarking.utils.graphics;

import java.awt.geom.Dimension2D;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A physical dimension is a
 * {@link org.optimizationBenchmarking.utils.graphics.DoubleDimension}
 * associated with a length unit
 */
public class PhysicalDimension extends DoubleDimension {
  /** the length unit */
  final ELength m_unit;

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
  public void toText(final ITextOutput textOut) {
    final String s;

    textOut.append(this.m_width);
    s = this.m_unit.getShortcut();
    textOut.append(s);
    textOut.append('x');
    textOut.append(this.m_height);
    textOut.append(s);
  }

  /**
   * compare this physical dimension to another one
   *
   * @param dim
   *          the dimension
   * @return {@code true} if they are equals, {@code false} otherwise
   */
  final boolean _equals(final PhysicalDimension dim) {
    ELength l1, l2;

    l1 = this.m_unit;
    l2 = dim.m_unit;

    if (l1 == l2) {
      return ((EComparison.EQUAL.compare(this.m_width, dim.m_width))//
      && (EComparison.EQUAL.compare(this.m_height, dim.m_height)));
    }

    return ((EComparison.EQUAL.compare(//
        ((l1 == ELength.PT) ? this.m_width : //
            (l1.convertTo(this.m_width, ELength.PT))),//
        ((l2 == ELength.PT) ? dim.m_width : //
            (l2.convertTo(dim.m_width, ELength.PT)))))//
    && //
    (EComparison.EQUAL.compare(//
        ((l1 == ELength.PT) ? this.m_height : //
            (l1.convertTo(this.m_height, ELength.PT))),//
        ((l2 == ELength.PT) ? dim.m_height : //
            (l2.convertTo(dim.m_height, ELength.PT))))));
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    final PhysicalDimension dim;

    if (o == this) {
      return true;
    }

    if (o == null) {
      return false;
    }

    if (o instanceof PhysicalDimension) {
      dim = ((PhysicalDimension) o);
      return ((EComparison.EQUAL.compare(this.m_width, dim.m_width))//
          && (EComparison.EQUAL.compare(this.m_height, dim.m_height))//
      && EComparison.equals(this.m_unit, dim.m_unit));
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(HashUtils.combineHashes(//
        HashUtils.hashCode(this.m_height),//
        HashUtils.hashCode(this.m_width)),//
        HashUtils.hashCode(this.m_unit));
  }

}
