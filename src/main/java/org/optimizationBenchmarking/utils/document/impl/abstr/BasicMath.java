package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.EMathComparison;
import org.optimizationBenchmarking.utils.document.spec.IMath;

/** A mathematics output class */
public abstract class BasicMath extends DocumentPart implements IMath {

  /** the current number of arguments */
  private int m_size;

  /**
   * Create a mathematics output.
   * 
   * @param owner
   *          the owning FSM
   */
  BasicMath(final DocumentElement owner) {
    super(owner);
  }

  /** allocate the next index */
  private final void __nextIndex() {
    final int max;

    this.fsmStateAssert(DocumentElement.STATE_ALIFE);

    if ((++this.m_size) > (max = this.maxArgs())) {
      throw new IllegalStateException(//
          "An instance of " + this.getClass().getSimpleName() + //$NON-NLS-1$
              " can have at most " + max + //$NON-NLS-1$
              " arguments, but you tried to create the " + //$NON-NLS-1$ 
              this.m_size + "th one."); //$NON-NLS-1$          
    }
  }

  /**
   * Get the minimum number of arguments of this math context.
   * 
   * @return the minimum number of arguments
   */
  abstract int minArgs();

  /**
   * Get the maximum number of arguments of this math context.
   * 
   * @return the maximum number of arguments
   */
  abstract int maxArgs();

  /**
   * Obtain the current number of nested elements (arguments) of this math
   * context.
   * 
   * @return the current number of nested elements (arguments) of this math
   *         context
   */
  protected final int getCurrentSize() {
    return this.m_size;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathAdd add() {
    this.__nextIndex();
    return this.m_driver.createMathAdd(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathSub sub() {
    this.__nextIndex();
    return this.m_driver.createMathSub(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathMul mul() {
    this.__nextIndex();
    return this.m_driver.createMathMul(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathDiv div() {
    this.__nextIndex();
    return this.m_driver.createMathDiv(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathDivInline divInline() {
    this.__nextIndex();
    return this.m_driver.createMathDivInline(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathMod mod() {
    this.__nextIndex();
    return this.m_driver.createMathMod(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathLog log() {
    this.__nextIndex();
    return this.m_driver.createMathLog(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathLn ln() {
    this.__nextIndex();
    return this.m_driver.createMathLn(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathLd ld() {
    this.__nextIndex();
    return this.m_driver.createMathLd(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathLg lg() {
    this.__nextIndex();
    return this.m_driver.createMathLg(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathPow pow() {
    this.__nextIndex();
    return this.m_driver.createMathPow(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathRoot root() {
    this.__nextIndex();
    return this.m_driver.createMathRoot(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathSqrt sqrt() {
    this.__nextIndex();
    return this.m_driver.createMathSqrt(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathCompare compare(final EMathComparison cmp) {
    this.__nextIndex();
    return this.m_driver.createMathCompare(this, cmp);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathNegate negate() {
    this.__nextIndex();
    return this.m_driver.createMathNegate(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathAbs abs() {
    this.__nextIndex();
    return this.m_driver.createMathAbsolute(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathFactorial factorial() {
    this.__nextIndex();
    return this.m_driver.createMathFactorial(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathSin sin() {
    this.__nextIndex();
    return this.m_driver.createMathSin(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathCos cos() {
    this.__nextIndex();
    return this.m_driver.createMathCos(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathTan tan() {
    this.__nextIndex();
    return this.m_driver.createMathTan(this);
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized MathInBraces inBraces() {
    this.__nextIndex();
    return this.m_driver.createMathInBraces(this);
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized MathNumber number() {
    this.__nextIndex();
    return this.m_driver.createMathNumber(this);
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized MathName name() {
    this.__nextIndex();
    return this.m_driver.createMathName(this);
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized MathText text() {
    this.__nextIndex();
    return this.m_driver.createMathText(this);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    final int min;

    min = this.minArgs();

    if (this.m_size < min) {
      throw new IllegalStateException(//
          "An instance of " + this.getClass().getSimpleName() + //$NON-NLS-1$
              " must have at least " + min + //$NON-NLS-1$
              " arguments, but has only " + this.m_size); //$NON-NLS-1$
    }

    super.onClose();
  }
}
