package org.optimizationBenchmarking.utils.text.charset;

import java.io.Serializable;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * A collection of enclosures
 */
public abstract class Enclosure implements Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** Create */
  Enclosure() {
    super();
  }

  /**
   * Get the enclosure begin
   *
   * @return the enclosure begin
   */
  public abstract EnclosureEnd getBegin();

  /**
   * Get the enclosure end
   *
   * @return the enclosure end
   */
  public abstract EnclosureEnd getEnd();

  /**
   * Get the beginning char
   *
   * @return the beginning char
   */
  public abstract char getBeginChar();

  /**
   * Get the end char
   *
   * @return the end char
   */
  public abstract char getEndChar();

  /**
   * Obtain a set view of all the enclosures of this type.
   *
   * @return the set view of all the enclosures of this type.
   */
  public abstract ArraySetView<? extends Enclosure> getSet();
}
