package org.optimizationBenchmarking.experimentation.data.impl.ref;

import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * the hierarchical context root
 *
 * @param <RT>
 *          the result type
 */
class _Context<RT> extends _FSM {

  /** the context is fixated, i.e., can no longer be modified */
  static final int STATE_CLOSED = (_FSM.STATE_OPEN + 1);
  /**
   * the context is compiled, i.e., can no longer be modified and its
   * result can be obtained
   */
  private static final int STATE_COMPILING = (_Context.STATE_CLOSED + 1);

  /** all of the data of this context has been disposed */
  private static final int STATE_COMPILED = (_Context.STATE_COMPILING + 1);

  /** the state names */
  private static final String[] STATE_NAMES = { null,
      _FSM.STATE_OPEN_NAME,//
      "closed",//$NON-NLS-1$
      "compiling",//$NON-NLS-1$
      "compiled"//$NON-NLS-1$
  };

  /** the compiled result */
  private volatile RT m_compiled;

  /**
   * create
   *
   * @param owner
   *          the owner
   */
  _Context(final _FSM owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    this.fsmStateAssertAndSet(_FSM.STATE_OPEN, _Context.STATE_CLOSED);
    super.onClose();
  }

  /**
   * compile and return the compilation result
   *
   * @return the compilation result
   */
  RT _doCompile() {
    throw new UnsupportedOperationException();
  }

  /**
   * compile and return the compilation result
   *
   * @return the compilation result
   */
  synchronized final RT _compile() {
    final RT result;

    this.m_compiled = null;
    this.fsmStateAssertAndSet(_Context.STATE_CLOSED,
        _Context.STATE_COMPILING);
    this.m_compiled = result = this._doCompile();
    if (result == null) {
      throw new IllegalArgumentException(//
          "Compilation result must not be null."); //$NON-NLS-1$
    }
    this.fsmStateAssertAndSet(_Context.STATE_COMPILING,
        _Context.STATE_COMPILED);
    return result;
  }

  /**
   * Get the result of the compilation procedure
   *
   * @return the result of the compilation procedure
   */
  synchronized final RT _getCompiled() {
    this.fsmStateAssert(_Context.STATE_COMPILED);
    return this.m_compiled;
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmStateAppendName(final int state,
      final MemoryTextOutput append) {
    if ((state > 0) && (state < _Context.STATE_NAMES.length)) {
      append.append(_Context.STATE_NAMES[state]);
    } else {
      super.fsmStateAppendName(state, append);
    }
  }

}
