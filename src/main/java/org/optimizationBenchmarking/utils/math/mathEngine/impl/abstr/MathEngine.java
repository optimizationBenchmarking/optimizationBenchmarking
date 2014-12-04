package org.optimizationBenchmarking.utils.math.mathEngine.impl.abstr;

import java.io.IOException;

import org.optimizationBenchmarking.utils.math.mathEngine.spec.IMathEngine;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IVariable;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.AlphabeticNumberAppender;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/** The base class for math engines */
public abstract class MathEngine extends ToolJob implements IMathEngine {

  /** the internal variable counter */
  private int m_varCounter;

  /** create */
  protected MathEngine() {
    super();
  }

  /**
   * do close
   * 
   * @throws IOException
   *           if it must
   */
  @SuppressWarnings("unused")
  protected void doClose() throws IOException {
    //
  }

  /**
   * Close the engine
   * 
   * @throws IOException
   *           if it must
   */
  @Override
  public final synchronized void close() throws IOException {
    if (this.m_varCounter < 0) {
      return;
    }
    this.m_varCounter = (-1);
    this.doClose();
  }

  /** check the state */
  protected final void checkState() {
    if (this.m_varCounter < 0) {
      throw new IllegalStateException("Math engine already closed."); //$NON-NLS-1$
    }
  }

  /**
   * Create a variable
   * 
   * @return the variable
   */
  protected final IVariable variableCreate() {
    final String name;
    synchronized (this) {
      this.checkState();
      name = AlphabeticNumberAppender.LOWER_CASE_INSTANCE.toString(
          (this.m_varCounter++), ETextCase.IN_SENTENCE);
    }
    return new _Variable(name, this);
  }

  /**
   * validate a variable
   * 
   * @param variable
   *          the variable
   * @return the variable
   */
  private final _Variable __validateVar(final IVariable variable) {
    final _Variable var;

    if (variable == null) {
      throw new IllegalArgumentException(//
          "_Variable cannot be null."); //$NON-NLS-1$
    }

    if (variable instanceof _Variable) {
      var = ((_Variable) variable);
    } else {
      throw new IllegalArgumentException("_Variable " + variable//$NON-NLS-1$
          + " is of a class which cannot be processed.");//$NON-NLS-1$
    }

    if (var.m_deleted) {
      throw new IllegalArgumentException(//
          "_Variable " + //$NON-NLS-1$
              var.m_id + " has already been deleted");//$NON-NLS-1$
    }
    if (var.m_engine != this) {
      throw new IllegalArgumentException(//
          "_Variable " + //$NON-NLS-1$
              var.m_id + " is owned by another engine."); //$NON-NLS-1$
    }
    this.checkState();
    return var;
  }

  /**
   * Use the variable: get its name
   * 
   * @param variable
   *          the variable
   * @return the variable's name
   */
  protected final String variableName(final IVariable variable) {
    return this.__validateVar(variable).m_id;
  }

  /**
   * Use the variable: get its type
   * 
   * @param variable
   *          the variable
   * @return the variable's type
   */
  protected final EDataType variableDataType(final IVariable variable) {
    return this.__validateVar(variable).m_type;
  }

  /**
   * Set the type of a variable
   * 
   * @param variable
   *          the variable
   * @param type
   *          the type
   */
  protected final void variableSetDataType(final IVariable variable,
      final EDataType type) {
    this.__validateVar(variable).m_type = type;
  }

  /**
   * Delete a variable
   * 
   * @param variable
   *          the variable
   */
  protected final void variableDelete(final IVariable variable) {
    final _Variable var;
    var = this.__validateVar(variable);
    var.m_deleted = true;
    var.m_type = EDataType.UNKNOWN;

  }
}
