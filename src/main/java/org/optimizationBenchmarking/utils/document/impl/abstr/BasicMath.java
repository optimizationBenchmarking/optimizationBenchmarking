package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/** A mathematics output class */
public class BasicMath extends PlainText implements IMath {

  /**
   * Create a mathematics output.
   * 
   * @param owner
   *          the owning FSM
   * @param out
   *          the output destination
   */
  protected BasicMath(final HierarchicalFSM owner, final Appendable out) {
    super(owner, out);
  }

  /**
   * Create a mathematical function
   * 
   * @param operator
   *          the operator
   * @return the function
   */
  protected MathFunction createFunction(final EMathOperators operator) {
    return new MathFunction(this, operator);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction add() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createFunction(EMathOperators.ADD);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction sub() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createFunction(EMathOperators.SUB);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction mul() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createFunction(EMathOperators.MUL);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction div() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createFunction(EMathOperators.DIV);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction mod() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createFunction(EMathOperators.MOD);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction log() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createFunction(EMathOperators.LOG);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction ln() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createFunction(EMathOperators.LN);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction ld() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createFunction(EMathOperators.LD);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction lg() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createFunction(EMathOperators.LG);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction pow() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createFunction(EMathOperators.POW);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction root() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createFunction(EMathOperators.ROOT);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction sqrt() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createFunction(EMathOperators.SQRT);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction compare(final EComparison cmp) {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createFunction(EMathOperators._comp2MathOp(cmp));
  }

  /**
   * Create a mathematical sub-script
   * 
   * @return the mathematical sub-script
   */
  protected MathSubscript createSubscript() {
    return new MathSubscript(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathSubscript subscript() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createSubscript();
  }

  /**
   * Create a mathematical super-script
   * 
   * @return the mathematical super-script
   */
  protected MathSuperscript createSuperscript() {
    return new MathSuperscript(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathSuperscript superscript() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createSuperscript();
  }

  /**
   * Create an in-braces object
   * 
   * @param braces
   *          the braces counter
   * @return the in-braces object
   */
  protected MathInBraces createInBraces(final int braces) {
    return new MathInBraces(this, braces);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public final synchronized MathInBraces inBraces() {
    HierarchicalFSM o;
    int c;

    this.fsmStateAssert(DocumentElement.STATE_ALIFE);

    c = 0;
    o = this;
    for (;;) {

      if (o instanceof DocumentElement) {
        if (o instanceof MathInBraces) {
          c = (((MathInBraces) o).m_braces + 1);
          break;
        }

        o = ((DocumentElement) o)._owner();
        continue;
      }
      break;
    }

    return this.createInBraces(c);
  }

}
