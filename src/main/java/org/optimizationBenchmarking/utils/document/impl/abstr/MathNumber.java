package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * A text class for mathematical numbers. Its contents will be rendered in
 * a number-like style.
 */
public class MathNumber extends Text {

  /**
   * Create a text.
   * 
   * @param owner
   *          the owning FSM
   */
  protected MathNumber(final BasicMath owner) {
    super(owner);
  }
  //
  // /** only numbers are allowed */
  // private static final void __onlyNumbers() {
  // throw new UnsupportedOperationException(//
  //        "Can only add numbers to " + //$NON-NLS-1$
  // MathNumber.class.getSimpleName());
  // }
  //
  // /** {@inheritDoc} */
  // @Override
  // public final synchronized ITextOutput append(final CharSequence csq) {
  // MathNumber.__onlyNumbers();
  // return this;
  // }
  //
  // /** {@inheritDoc} */
  // @Override
  // public final synchronized ITextOutput append(final CharSequence csq,
  // final int start, final int end) {
  // MathNumber.__onlyNumbers();
  // return this;
  // }
  //
  // /** {@inheritDoc} */
  // @Override
  // public final synchronized ITextOutput append(final char c) {
  // MathNumber.__onlyNumbers();
  // return this;
  // }
  //
  // /** {@inheritDoc} */
  // @Override
  // public final synchronized void append(final String s) {
  // MathNumber.__onlyNumbers();
  // }
  //
  // /** {@inheritDoc} */
  // @Override
  // public final synchronized void append(final String s, final int start,
  // final int end) {
  // MathNumber.__onlyNumbers();
  // }
  //
  // /** {@inheritDoc} */
  // @Override
  // public final synchronized void append(final char[] chars) {
  // MathNumber.__onlyNumbers();
  // }
  //
  // /** {@inheritDoc} */
  // @Override
  // public final synchronized void append(final char[] chars,
  // final int start, final int end) {
  // MathNumber.__onlyNumbers();
  // }
  //
  // /** {@inheritDoc} */
  // @Override
  // public final synchronized void append(final boolean v) {
  // MathNumber.__onlyNumbers();
  // }
  //
  // /** {@inheritDoc} */
  // @Override
  // public final synchronized void append(final Object o) {
  // MathNumber.__onlyNumbers();
  // }
  //
  // /** {@inheritDoc} */
  // @Override
  // public final synchronized void appendLineBreak() {
  // MathNumber.__onlyNumbers();
  // }
  //
  // /** {@inheritDoc} */
  // @Override
  // public final synchronized void appendNonBreakingSpace() {
  // MathNumber.__onlyNumbers();
  // }
}
