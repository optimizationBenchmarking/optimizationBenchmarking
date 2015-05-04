package org.optimizationBenchmarking.utils.math.mathEngine.impl.R;

import java.io.BufferedWriter;
import java.io.IOException;

import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IAssignment;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IVariable;

/**
 * Assign the value of a variable, or allocate a new variable and assign it
 */
public final class RAssignment extends _RBlockingScope implements
    IAssignment {

  /** the variable to use */
  private final IVariable m_var;

  /** has the assignment been done? */
  private boolean m_isAssigned;

  /**
   * create the assignment
   *
   * @param var
   *          the variable
   * @param engine
   *          the engine
   */
  RAssignment(final IVariable var, final REngine engine) {
    super(null, engine);
    this.m_var = var;
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onOpen() {
    final BufferedWriter out;

    super.onOpen();

    out = this.m_engine.m_out;
    try {
      out.write(this.m_engine._variableName(this.m_var));
      out.write('<');
      out.write('-');
    } catch (final IOException ioe) {
      this.m_engine._handleError(ioe);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    final BufferedWriter out;

    if (!this.m_isAssigned) {
      throw new IllegalStateException(//
          "Variable " + this.m_var + //$NON-NLS-1$
              " must be assigned.");//$NON-NLS-1$
    }

    out = this.m_engine.m_out;
    try {
      out.write(';');
      out.newLine();
      out.flush();

      // block until assignment is completed
      out.write("exists(\""); //$NON-NLS-1$
      out.write(this.m_engine._variableName(this.m_var));
      out.write("\");"); //$NON-NLS-1$
      out.newLine();
      out.flush();
      this.m_engine.m_in.readLine();
    } catch (final IOException ioe) {
      this.m_engine._handleError(ioe);
    } finally {// after blocking, we can delete potential temp files
      super.onClose();
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void afterChildClosed(final HierarchicalFSM child) {
    this.m_engine._variableSetDataType(this.m_var,
        ((RExpression) child).m_type);
    super.afterChildClosed(child);
  }

  /** {@inheritDoc} */
  @Override
  public final IVariable getVariable() {
    return this.m_var;
  }

  /** {@inheritDoc} */
  @Override
  public final RExpression value() {
    if (this.m_isAssigned) {
      throw new IllegalStateException("Assignment already done."); //$NON-NLS-1$
    }
    this.m_isAssigned = true;
    return new RExpression(this, this.m_engine);
  }
}
