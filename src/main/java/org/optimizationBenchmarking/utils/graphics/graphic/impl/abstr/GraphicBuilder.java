package org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicBuilder;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.tools.impl.abstr.FileProducerBuilder;

/** The base class for graphics builders */
public final class GraphicBuilder extends
    FileProducerBuilder<Graphic, GraphicBuilder> implements
    IGraphicBuilder {

  /** the dimension */
  private PhysicalDimension m_size;

  /** the owning builder */
  private final AbstractGraphicDriver m_owner;

  /**
   * create the tool job builder
   * 
   * @param owner
   *          the owning graphic builder
   */
  protected GraphicBuilder(final AbstractGraphicDriver owner) {
    super();
    this.m_owner = owner;
  }

  /** {@inheritDoc} */
  @Override
  public final GraphicBuilder setSize(final PhysicalDimension size) {
    final double w, h;
    final ELength sizeUnit;
    double r;

    if ((size == null) || ((h = size.getHeight()) <= 0d)
        || ((w = size.getWidth()) <= 0d)) {
      throw new IllegalArgumentException(//
          "Invalid graphic size: " + size);//$NON-NLS-1$
    }

    sizeUnit = size.getUnit();
    r = sizeUnit.convertTo(w, ELength.M);
    if ((r <= 1e-4d) || (r >= 10d)) {
      throw new IllegalArgumentException(//
          "A graphic width cannot be smaller than 0.1mm or larger than 10m, but "//$NON-NLS-1$
              + w + " specified in " + sizeUnit + //$NON-NLS-1$ 
              " equals " + r + //$NON-NLS-1$
              "m.");//$NON-NLS-1$
    }

    r = sizeUnit.convertTo(h, ELength.M);
    if ((r <= 1e-4) || (r >= 10d)) {
      throw new IllegalArgumentException(//
          "A graphic height cannot be smaller than 0.1mm or larger than 10m, but "//$NON-NLS-1$
              + h + " specified in " + sizeUnit + //$NON-NLS-1$ 
              " equals " + r + //$NON-NLS-1$
              "m.");//$NON-NLS-1$
    }

    this.m_size = size;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected void validate() {
    super.validate();
    if (this.m_size == null) {
      throw new IllegalArgumentException(//
          "The size of the graphic must be set."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final Graphic create() {
    this.validate();
    return this.m_owner.createGraphic(this.m_logger, this.m_listener,
        this.m_basePath, this.m_mainDocumentNameSuggestion, this.m_size);
  }
}
