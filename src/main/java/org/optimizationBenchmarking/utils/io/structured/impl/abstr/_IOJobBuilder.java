package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import org.optimizationBenchmarking.utils.io.structured.spec.IIOJob;
import org.optimizationBenchmarking.utils.io.structured.spec.IIOJobBuilder;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJobBuilder;

/**
 * The base class for building IO jobs
 * 
 * @param <JBT>
 *          the job builder type
 */
abstract class _IOJobBuilder<JBT extends _IOJobBuilder<JBT>> extends
    ToolJobBuilder<IIOJob, JBT> implements IIOJobBuilder {

  /** the tool */
  final IOTool<?> m_tool;

  /**
   * create the IIOJobBuilder
   * 
   * @param tool
   *          the owning tool
   */
  _IOJobBuilder(final IOTool<?> tool) {
    super();
    _IOJobBuilder._validateTool(tool);
    this.m_tool = tool;
  }

  /**
   * validate the tool
   * 
   * @param tool
   *          the tool
   */
  static final void _validateTool(final IOTool<?> tool) {
    if (tool == null) {
      throw new IllegalArgumentException("Tool must not be null."); //$NON-NLS-1$
    }
    if (!(tool.canUse())) {
      throw new IllegalArgumentException("Tool '" + //$NON-NLS-1$
          tool.toString() + "' cannot be used."); //$NON-NLS-1$
    }
  }

  /**
   * create the job
   * 
   * @return the job
   */
  abstract IIOJob _doCreate();

  /** {@inheritDoc} */
  @Override
  public final IIOJob create() {
    this.validate();
    return this._doCreate();
  }
}
