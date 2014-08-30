package org.optimizationBenchmarking.utils.graphics;

import org.optimizationBenchmarking.utils.graphics.drivers.freeHEP.FreeHEPEPSGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.drivers.freeHEP.FreeHEPPDFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.drivers.freeHEP.FreeHEPSVGGraphicDriver;

/**
 * An enumeration with graphics formats.
 */
public enum EGraphicFormat {

  /** the eps format */
  EPS {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultGraphicDriver() {
      return FreeHEPEPSGraphicDriver.INSTANCE;
    }
  },

  /** the pdf format */
  PDF {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultGraphicDriver() {
      return FreeHEPPDFGraphicDriver.INSTANCE;
    }
  },

  /** the svg format */
  SVG {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultGraphicDriver() {
      return FreeHEPSVGGraphicDriver.INSTANCE;
    }
  },

  /** the jpg format */
  JPG,

  /** the png format */
  PNG,

  /** the gif format */
  GIF;

  /**
   * Get the default driver of this format
   * 
   * @return the default driver of this format
   */
  public IGraphicDriver getDefaultGraphicDriver() {
    throw new UnsupportedOperationException();
  }

}
