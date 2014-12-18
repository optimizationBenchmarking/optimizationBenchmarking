package org.optimizationBenchmarking.utils.math.mathEngine.impl.R;

import java.io.IOException;

import org.optimizationBenchmarking.utils.math.mathEngine.impl.abstr.EDataType;

/** A class for creating limited expression-like scopes in R */
abstract class _RExpressionScope extends _RScope {

  /** the data type */
  EDataType m_type;

  /**
   * create the R expression
   * 
   * @param owner
   *          the owner
   * @param engine
   *          the engine
   */
  _RExpressionScope(final _RScope owner, final REngine engine) {
    super(owner, engine);
    this.m_type = null;
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onOpen() {
    super.onOpen();
    try {
      this.m_engine.m_out.write('(');
    } catch (final IOException ioe) {
      this.m_engine._handleError(ioe);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    try {
      this.m_engine.m_out.write(')');
    } catch (final IOException ioe) {
      this.m_engine._handleError(ioe);
    } finally {
      super.onClose();
    }
  }

  /**
   * set the type of the expression
   * 
   * @param type
   *          the type
   */
  final void _setType(final EDataType type) {
    if (type == null) {
      throw new IllegalArgumentException("Type cannot be null.");//$NON-NLS-1$
    }
    if (this.m_type != null) {
      throw new IllegalStateException("Scope already has a type."); //$NON-NLS-1$
    }
    this.m_type = type;
  }

}
