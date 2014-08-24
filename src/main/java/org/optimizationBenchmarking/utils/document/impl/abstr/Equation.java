package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/**
 * An equation has the same structure as a
 * {@link org.optimizationBenchmarking.utils.document.impl.abstr.ComplexObject}
 * , but inherits from
 * {@link org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath}
 * in order to facilitate its functionality.
 */
public class Equation extends BasicMath {

  /** the index in the owning context */
  final int m_index;

  /** the full id of the object, only identifying it in the owning context */
  final String m_globalID;

  /** the short id of the object, globally identifying it */
  final String m_localID;

  /** the label of the object */
  final Label m_label;

  /**
   * Create a complex object
   * 
   * @param owner
   *          the owning section body
   * @param index
   *          the table index in the owning section
   * @param useLabel
   *          the label to use
   */
  Equation(final SectionBody owner, final ILabel useLabel, final int index) {
    super(owner, null);

    final String oid;

    if (index <= 0) {
      throw new IllegalArgumentException(//
          "Object index must be greater than 0, but is " //$NON-NLS-1$
              + index);
    }

    this.m_index = index;
    this.m_localID = this.renderIndex(index);

    oid = ComplexObject._getOwnerID(owner);
    if (oid != null) {
      this.m_globalID = (oid + this.m_localID);
    } else {
      this.m_globalID = this.m_localID;
    }

    if (useLabel != null) {
      this.m_label = this.m_doc.m_manager._getLabel(ELabelType.EQUATION,
          useLabel, this.m_globalID);
    } else {
      this.m_label = null;
    }
  }

  /**
   * Render a string to an index
   * 
   * @param index
   *          the index to render
   * @return a string
   */
  protected String renderIndex(final int index) {
    return (Integer.toString(index) + '.');
  }

  /**
   * Get the label of this equation
   * 
   * @return the label of this equation, or {@code null} if it has none
   */
  public final Label getLabel() {
    return this.m_label;
  }

  /**
   * Get the index of the equation within the owning context (starts at
   * {@code 1})
   * 
   * @return the index of the equation within the owning section
   */
  public final int getIndex() {
    return this.m_index;
  }

  /**
   * Get the global id, which identifies this equation globally unique in
   * the document. This id is composed of the rendered indexes through the
   * complete element hierarchy.
   * 
   * @return the globally unique id of this equation
   */
  public final String getGlobalID() {
    return this.m_globalID;
  }

  /**
   * Get the local id, which identifies this equation within its owning
   * context. It corresponds to a {@link #renderIndex(int) rendered}
   * version of {@link #getIndex()}.
   * 
   * @return the locally unique id of this equation within the owning
   *         section body
   */
  public final String getLocalID() {
    return this.m_localID;
  }
}
