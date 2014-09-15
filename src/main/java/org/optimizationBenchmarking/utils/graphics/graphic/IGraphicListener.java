package org.optimizationBenchmarking.utils.graphics.graphic;

/**
 * An interface which is used to notify the owner of a
 * {@link org.optimizationBenchmarking.utils.graphics.graphic.Graphic}
 * about what's going on.
 */
public interface IGraphicListener {

  /**
   * Notify the listener that a graphic has been closed. The parameter of
   * this method is the
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.GraphicID
   * id} passed in to the
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.IGraphicDriver#createGraphic(GraphicID, org.optimizationBenchmarking.utils.graphics.PhysicalDimension, IGraphicListener)}
   * method of the graphic driver (and which has been created by the
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.IGraphicDriver#createGraphicID(java.nio.file.Path)}
   * method of the driver).
   * 
   * @param id
   *          the graphic ID identifying the closed graphic
   */
  public abstract void onGraphicClosed(final GraphicID id);

}
