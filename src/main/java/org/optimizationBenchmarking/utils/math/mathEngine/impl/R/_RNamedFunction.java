package org.optimizationBenchmarking.utils.math.mathEngine.impl.R;

import java.io.BufferedWriter;
import java.io.IOException;

import org.optimizationBenchmarking.utils.math.mathEngine.spec.IExpression;

/** A class for creating named functions in R */
final class _RNamedFunction extends _RFunctionBase {

  /** the name of the function */
  private final String m_name;

  /**
   * create the R expression
   * 
   * @param name
   *          the function name
   * @param owner
   *          the owner
   * @param engine
   *          the engine
   */
  _RNamedFunction(final String name, final _RScope owner,
      final _REngine engine) {
    super(owner, engine);
    this.m_name = name;
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onOpen() {
    super.onOpen();
    try {
      this.m_engine.m_out.write(this.m_name);
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

  /** {@inheritDoc} */
  @Override
  public final IExpression parameter(final String name) {
    final BufferedWriter out;

    out = this.m_engine.m_out;
    try {
      if ((this.m_childCount++) > 0) {
        out.write(',');
      }
      if (name != null) {
        out.write(name);
        out.write('=');
      }
    } catch (final Throwable t) {
      this.m_engine._handleError(t);
    }
    return new _RExpression(this, this.m_engine);
  }

}
