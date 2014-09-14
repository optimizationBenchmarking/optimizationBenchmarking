package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * the caption of a table
 */
public class TableCaption extends ComplexText {
  /**
   * create the caption
   * 
   * @param owner
   *          the owner
   */
  public TableCaption(final Table owner) {
    super(owner);
  }

  /**
   * Get the owning table
   * 
   * @return the owning table
   */
  @Override
  protected Table getOwner() {
    return ((Table) (super.getOwner()));
  }
}
