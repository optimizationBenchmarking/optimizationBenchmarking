package org.optimizationBenchmarking.utils.graphics;

/**
 * An interface which is used to notify the owner of a
 * {@link org.optimizationBenchmarking.utils.graphics.Graphic} about what's
 * going on.
 */
public interface IGraphicListener {

  /**
   * Notify the listener that a graphic has been closed. The parameter of
   * this method is the
   * {@link org.optimizationBenchmarking.utils.graphics.GraphicID id}
   * passed in to the
   * {@link org.optimizationBenchmarking.utils.graphics.IGraphicDriver#createGraphic(GraphicID, java.awt.geom.Dimension2D, org.optimizationBenchmarking.utils.math.units.ELength, IGraphicListener)}
   * method of the graphic driver (and which has been created by the
   * {@link org.optimizationBenchmarking.utils.graphics.IGraphicDriver#createGraphicID(java.nio.file.Path)}
   * method of the driver).
   * 
   * @param id
   *          the graphic ID identifying the closed graphic
   */
  public abstract void onGraphicClosed(final GraphicID id);

}
