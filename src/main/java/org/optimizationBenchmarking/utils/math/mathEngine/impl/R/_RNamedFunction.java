package org.optimizationBenchmarking.utils.math.mathEngine.impl.R;

import java.io.BufferedWriter;
import java.io.IOException;

import org.optimizationBenchmarking.utils.math.mathEngine.spec.IExpression;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IFunction;

/** A class for creating named functions in R */
final class _RNamedFunction extends _RExpressionScope implements IFunction {

  /** the name of the function */
  private final String m_name;
  /** the infixes */
  private final char[] m_infix;

  /** the number of children, to be utilized by sub-classes */
  private int m_childCount;

  /**
   * create the R expression
   * 
   * @param name
   *          the function name
   * @param owner
   *          the owner
   * @param engine
   *          the engine
   * @param infixes
   *          the infixes
   */
  _RNamedFunction(final String name, final char[] infixes,
      final _RScope owner, final _REngine engine) {
    super(owner, engine);
    this.m_name = name;
    this.m_infix = infixes;
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onOpen() {
    super.onOpen();
    try {
      if (this.m_name != null) {
        this.m_engine.m_out.write(this.m_name);
      }
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
  public final IExpression parameter() {
    return this.parameter(null);
  }

  /** {@inheritDoc} */
  @Override
  public final IExpression parameter(final String name) {
    final BufferedWriter out;
    final int cc;

    out = this.m_engine.m_out;
    try {
      cc = (this.m_childCount++);
      if (cc > 0) {
        if (this.m_infix == null) {
          out.write(',');
        } else {
          if (cc > this.m_infix.length) {
            throw new IllegalStateException(//
                "Too many parameters, can only accept " //$NON-NLS-1$
                    + cc);
          }
          out.write(this.m_infix[cc - 1]);
        }
      }
      if ((name != null) && (this.m_infix == null)) {
        out.write(name);
        out.write('=');
      }
    } catch (final Throwable t) {
      this.m_engine._handleError(t);
    }
    return new _RExpression(this, this.m_engine);
  }

}
