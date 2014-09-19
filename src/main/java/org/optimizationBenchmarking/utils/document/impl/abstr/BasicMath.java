package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.spec.IMath;

/** A mathematics output class */
public class BasicMath extends PlainText implements IMath {

  /**
   * Create a mathematics output.
   * 
   * @param owner
   *          the owning FSM
   */
  BasicMath(final DocumentElement owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction add() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createMathFunction(this, EMathOperators.ADD);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction sub() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createMathFunction(this, EMathOperators.SUB);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction mul() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createMathFunction(this, EMathOperators.MUL);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction div() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createMathFunction(this, EMathOperators.DIV);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction mod() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createMathFunction(this, EMathOperators.MOD);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction log() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createMathFunction(this, EMathOperators.LOG);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction ln() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createMathFunction(this, EMathOperators.LN);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction ld() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createMathFunction(this, EMathOperators.LD);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction lg() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createMathFunction(this, EMathOperators.LG);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction pow() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createMathFunction(this, EMathOperators.POW);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction root() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createMathFunction(this, EMathOperators.ROOT);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction sqrt() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createMathFunction(this, EMathOperators.SQRT);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFunction compare(final EComparison cmp) {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createMathFunction(this,
        EMathOperators._comp2MathOp(cmp));
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathSubscript subscript() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createMathSubscript(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathSuperscript superscript() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createMathSuperscript(this);
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized MathInBraces inBraces() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createMathInBraces(this);
  }

}
