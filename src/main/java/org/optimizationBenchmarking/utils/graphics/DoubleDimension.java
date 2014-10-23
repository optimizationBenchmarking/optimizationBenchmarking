package org.optimizationBenchmarking.utils.graphics;

import java.awt.geom.Dimension2D;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** A immutable dimension based on {@code double}s */
public class DoubleDimension extends Dimension2D implements ITextable {

  /** the empty dimension */
  public static final PhysicalDimension EMPTY = new PhysicalDimension(0d,
      0d, ELength.POINT);

  /** the width */
  final double m_width;

  /** the height */
  final double m_height;

  /**
   * create a new double dimension
   * 
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public DoubleDimension(final double width, final double height) {
    super();
    if ((width < 0d) || Double.isNaN(width) || Double.isInfinite(width)) {
      throw new IllegalArgumentException("Width " + width + //$NON-NLS-1$
          " is invalid."); //$NON-NLS-1$
    }
    if ((height < 0d) || Double.isNaN(height) || Double.isInfinite(height)) {
      throw new IllegalArgumentException("Height " + height + //$NON-NLS-1$
          " is invalid."); //$NON-NLS-1$
    }
    this.m_width = width;
    this.m_height = height;
  }

  /**
   * create a new double dimension
   * 
   * @param dim
   *          the dimension
   */
  public DoubleDimension(final Dimension2D dim) {
    this(dim.getWidth(), dim.getHeight());
  }

  /** {@inheritDoc} */
  @Override
  public final double getWidth() {
    return this.m_width;
  }

  /** {@inheritDoc} */
  @Override
  public final double getHeight() {
    return this.m_height;
  }

  /** {@inheritDoc} */
  @Override
  public void setSize(final double width, final double height) {
    throw new UnsupportedOperationException();
  }

  /**
   * Obtain a copy of this object - and since this object is immutable, we
   * return it itself.
   * 
   * @return this object itself
   */
  @Override
  public final DoubleDimension clone() {
    return this;
  }

  /**
   * Check if this dimension object contains another dimension object
   * 
   * @param dim
   *          the other dimension
   * @return {@code true} if a rectangle of the size defined by this object
   *         could entirely contain a rectangle of the size defined by
   *         {@code dim}
   */
  public boolean contains(final Dimension2D dim) {
    return ((dim.getWidth() <= this.m_width) && (dim.getHeight() <= this.m_height));
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    final MemoryTextOutput mo;

    mo = new MemoryTextOutput(256);
    this.toText(mo);
    return mo.toString();
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    final Dimension2D dim;

    if (o == this) {
      return true;
    }

    if (o == null) {
      return false;
    }

    if (o instanceof Dimension2D) {
      dim = ((Dimension2D) o);

      return ((EComparison.compareDoubles(this.m_height, dim.getHeight()) == 0) && //
      (EComparison.compareDoubles(this.m_width, dim.getWidth()) == 0));
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(HashUtils.hashCode(this.m_height),//
        HashUtils.hashCode(this.m_width));
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    textOut.append(this.m_width);
    textOut.append('x');
    textOut.append(this.m_height);
  }
}
