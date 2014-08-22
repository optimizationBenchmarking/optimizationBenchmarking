package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/** a label implementation */
public final class Label implements ILabel {

  /** the owner */
  final LabelManager m_owner;

  /** the label type */
  final ELabelType m_type;

  /** the label mark */
  private final String m_mark;

  /** the reference text */
  String m_refText;

  /**
   * create a label
   * 
   * @param owner
   *          the owner
   * @param type
   *          the label type
   * @param mark
   *          the label mark
   * @param refText
   *          the reference text
   */
  Label(final LabelManager owner, final ELabelType type,
      final String mark, final String refText) {
    super();

    if (owner == null) {
      throw new IllegalArgumentException("Label owner must not be null."); //$NON-NLS-1$
    }
    if (type == null) {
      throw new IllegalArgumentException("Label type must not be null."); //$NON-NLS-1$
    }
    if (mark == null) {
      throw new IllegalArgumentException("Label mark must not be null."); //$NON-NLS-1$
    }

    this.m_owner = owner;
    this.m_type = type;
    this.m_mark = mark;
    this.m_refText = refText;
  }

  /**
   * Get the label type
   * 
   * @return the label type
   */
  public final ELabelType getType() {
    return this.m_type;
  }

  /**
   * Get the label mark
   * 
   * @return the label mark
   */
  public final String getLabelMark() {
    return this.m_mark;
  }

  /**
   * Get the reference text
   * 
   * @return the reference text
   */
  public final String getReferenceText() {
    return this.m_refText;
  }

}
