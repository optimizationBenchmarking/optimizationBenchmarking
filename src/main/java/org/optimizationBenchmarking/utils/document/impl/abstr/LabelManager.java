package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.numbers.AlphabeticNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** a label manager */
public final class LabelManager {

  /** the label counters */
  private final int[] m_counters;

  /** the internal memory text output */
  private final MemoryTextOutput m_mto;

  /** create the label manager */
  public LabelManager() {
    super();
    this.m_counters = new int[ELabelType.INSTANCES.size()];
    this.m_mto = new MemoryTextOutput(16);
  }

  /**
   * Get the label for a given object.
   * 
   * @param type
   *          the label type
   * @param label
   *          the label placeholder, must not be {@code null}
   * @param text
   *          the label text
   * @return the label to use
   */
  final Label _getLabel(final ELabelType type, final ILabel label,
      final String text) {
    final Label r;

    if (text == null) {
      throw new IllegalArgumentException(//
          "The reference text must not be null."); //$NON-NLS-1$
    }

    if (label == ELabelType.AUTO) {
      return this.createLabel(type, text);
    }
    if (label instanceof Label) {
      r = ((Label) (label));
      if (r.m_type != type) {
        throw new IllegalArgumentException(//
            "A label of type '" + r.m_type + //$NON-NLS-1$
                "' cannot be used to label an '" + //$NON-NLS-1$
                type + "'.");//$NON-NLS-1$
      }
      this.setReferenceText(r, text);
      return r;
    }

    throw new IllegalArgumentException(//
        "Invalid label: '" + label + //$NON-NLS-1$
            "', only " + TextUtils.className(ELabelType.class) + //$NON-NLS-1$
            ".AUTO and instances of " + TextUtils.className(Label.class) + //$NON-NLS-1$
            " permitted."); //$NON-NLS-1$
  }

  /**
   * Create a new label to mark a table or figure or section with that is
   * going to be written in the future.
   * 
   * @param type
   *          the label type
   * @param refText
   *          the reference text
   * @return the label to be used in forward references
   */
  public final synchronized Label createLabel(final ELabelType type,
      final String refText) {
    final MemoryTextOutput mto;
    final String s;

    mto = this.m_mto;
    mto.append(type.getLabelPrefixChar());
    mto.append(ELabelType.LABEL_PREFIX_SEPARATOR);
    AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(
        (this.m_counters[type.ordinal()]++), ETextCase.IN_SENTENCE, mto);
    s = mto.toString();
    mto.clear();

    return new Label(this, type, s, refText);
  }

  /**
   * Create a new label to mark a table or figure or section with that is
   * going to be written in the future.
   * 
   * @param type
   *          the label type
   * @return the label to be used in forward references
   */
  public final Label createLabel(final ELabelType type) {
    return this.createLabel(type, null);
  }

  /**
   * set the reference text of a label
   * 
   * @param label
   *          the label
   * @param refText
   *          the reference text
   */
  public final void setReferenceText(final Label label,
      final String refText) {
    if (refText == null) {
      throw new IllegalArgumentException(//
          "Reference text must not be null."); //$NON-NLS-1$
    }
    if (label.m_owner != this) {
      throw new IllegalArgumentException(//
          "Reference text can only be set to owned labels."); //$NON-NLS-1$
    }
    label.m_refText = refText;
  }
}
