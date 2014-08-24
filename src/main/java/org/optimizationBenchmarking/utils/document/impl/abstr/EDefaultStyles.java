package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IStyle;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a set of default styles */
public enum EDefaultStyles implements IStyle, ITextable {

  /** normal text */
  PLAIN {
    /** {@inheritDoc} */
    @Override
    void _append(final ETextCase textCase, final ITextOutput out) {
      if ((textCase == null) || (textCase == ETextCase.IN_SENTENCE)) {
        out.append("normal"); //$NON-NLS-1$
      } else {
        out.append("Normal"); //$NON-NLS-1$
      }
    }

    /** {@inheritDoc} */
    @Override
    public final String toString() {
      return "normal text"; //$NON-NLS-1$
    }

  },

  /** italic text */
  ITALIC {
    /** {@inheritDoc} */
    @Override
    void _append(final ETextCase textCase, final ITextOutput out) {
      if ((textCase == null) || (textCase == ETextCase.IN_SENTENCE)) {
        out.append("italic"); //$NON-NLS-1$
      } else {
        out.append("Italic"); //$NON-NLS-1$
      }
    }

    /** {@inheritDoc} */
    @Override
    public final String toString() {
      return "italic text"; //$NON-NLS-1$
    }

  },

  /** bold text */
  BOLD {
    /** {@inheritDoc} */
    @Override
    void _append(final ETextCase textCase, final ITextOutput out) {
      if ((textCase == null) || (textCase == ETextCase.IN_SENTENCE)) {
        out.append("bold"); //$NON-NLS-1$
      } else {
        out.append("Bold"); //$NON-NLS-1$
      }
    }

    /** {@inheritDoc} */
    @Override
    public final String toString() {
      return "bold text"; //$NON-NLS-1$
    }

  };

  /** {@inheritDoc} */
  @Override
  public final void describe(final ETextCase textCase,
      final ITextOutput out) {
    if (out instanceof IComplexText) {
      try (final IText st = (((IComplexText) out).style(this))) {
        this._append(textCase, st);
      }
    } else {
      this._append(textCase, out);
    }
  }

  /**
   * append the normal text
   * 
   * @param textCase
   *          the text case
   * @param out
   *          the output
   */
  abstract void _append(final ETextCase textCase, final ITextOutput out);

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    textOut.append(this.toString());
  }

}
