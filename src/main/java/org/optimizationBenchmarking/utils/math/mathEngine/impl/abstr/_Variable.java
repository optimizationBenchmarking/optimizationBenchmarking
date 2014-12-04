package org.optimizationBenchmarking.utils.math.mathEngine.impl.abstr;

import org.optimizationBenchmarking.utils.math.mathEngine.spec.IVariable;

/** A class for variables */
final class _Variable implements IVariable {

  /** the id of the variable */
  final String m_id;

  /** the owning math engine */
  final MathEngine m_engine;

  /** the data type */
  EDataType m_type;

  /** the variable has been deleted */
  boolean m_deleted;

  /**
   * create a new variable
   * 
   * @param name
   *          the name of the variable
   * @param engine
   *          the owning math engine
   */
  _Variable(final String name, final MathEngine engine) {
    super();
    if (name == null) {
      throw new IllegalArgumentException(//
          "_Variable name must not be null."); //$NON-NLS-1$
    }
    if (engine == null) {
      throw new IllegalArgumentException(//
          "Owning math engine cannot be null.");//$NON-NLS-1$
    }
    this.m_id = name;
    this.m_type = EDataType.UNKNOWN;
    this.m_engine = engine;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    if (this.m_deleted) {
      return ("deleted:" + this.m_id);}//$NON-NLS-1$
    return (this.m_type + (':' + this.m_id));
  }
}
