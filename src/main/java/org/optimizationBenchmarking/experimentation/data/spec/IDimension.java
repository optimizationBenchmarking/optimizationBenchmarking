package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/**
 * The interface describing a measurement dimension.
 */
public interface IDimension extends INamedElement {

  /**
   * Get the data class
   * 
   * @return the data class
   */
  public abstract EPrimitiveType getDataType();

  /**
   * Get the dimension type
   * 
   * @return the dimension type
   */
  public abstract EDimensionType getDimensionType();

  /**
   * The direction in which the values of this dimension change over time.
   * 
   * @return the direction in which the values of this dimension change
   *         over time
   */
  public abstract EDimensionDirection getDirection();

  /**
   * Obtain the index of this dimension within the {@link #getOwner()
   * owning dimension set}.
   * 
   * @return the index of this dimension within the {@link #getOwner()
   *         owning dimension set}.
   */
  public abstract int getIndex();

  /**
   * Get the owning dimension set
   * 
   * @return the owning dimension set
   */
  @Override
  public abstract IDimensionSet getOwner();
}
