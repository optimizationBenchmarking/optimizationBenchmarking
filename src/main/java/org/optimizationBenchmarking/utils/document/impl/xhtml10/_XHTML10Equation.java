package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.Equation;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an equation in a XHTML document */
final class _XHTML10Equation extends Equation {

  /** the equation div begin */
  private static final char[] EQU_DIV = { '<', 'd', 'i', 'v', ' ', 'c',
    'l', 'a', 's', 's', '=', '"', 'e', 'q', 'u', 'a', 't', 'i', 'o',
    'n', '"', '>' };
  /** the equation table begin */
  private static final char[] EQU_TAB = { '<', 't', 'a', 'b', 'l', 'e',
    ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'e', 'q', 'u', 'a', 't',
    'i', 'o', 'n', '"', '>' };
  /** the equation tr begin */
  private static final char[] EQU_TR = { '<', 't', 'r', ' ', 'c', 'l',
    'a', 's', 's', '=', '"', 'e', 'q', 'u', 'a', 't', 'i', 'o', 'n',
    '"', '>' };
  /** the equation td begin */
  private static final char[] EQU_BODY_TD = { '<', 't', 'd', ' ', 'c',
    'l', 'a', 's', 's', '=', '"', 'e', 'q', 'u', 'a', 't', 'i', 'o',
    'n', 'B', 'o', 'd', 'y', '"', '>' };
  /** the equation td begin */
  private static final char[] EQU_NR_TD = { '<', 't', 'd', ' ', 'c', 'l',
    'a', 's', 's', '=', '"', 'e', 'q', 'u', 'a', 't', 'i', 'o', 'n',
    'N', 'u', 'm', 'b', 'e', 'r', '"', '>', '(', 'E', 'q', '.', '&',
    'n', 'b', 's', 'p', ';' };

  /**
   * Create a new equation
   *
   * @param owner
   *          the owning section body
   * @param index
   *          the table index in the owning section
   * @param useLabel
   *          the label to use
   */
  _XHTML10Equation(final _XHTML10SectionBody owner, final ILabel useLabel,
      final int index) {
    super(owner, useLabel, index);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;

    super.onOpen();

    out = this.getTextOutput();
    out.append(_XHTML10Equation.EQU_DIV);
    out.append(_XHTML10Equation.EQU_TAB);
    out.append(_XHTML10Equation.EQU_TR);
    out.append(_XHTML10Equation.EQU_BODY_TD);
    out.append(_XHTML10InlineMath.MATH_DIV_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();
    out.append(XHTML10Driver.DIV_END);
    out.append(_XHTML10Table.TD_END);
    out.append(_XHTML10Equation.EQU_NR_TD);
    out.append(this.getGlobalID());
    out.append(')');
    XHTML10Driver._label(this.getLabel(), out);

    out.append(_XHTML10Table.TD_END);
    out.append(_XHTML10Table.TR_END);
    out.append(_XHTML10Table.TABLE_END);
    out.append(XHTML10Driver.DIV_END);

    super.onClose();
  }
}
