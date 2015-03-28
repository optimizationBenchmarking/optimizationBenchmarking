package org.optimizationBenchmarking.experimentation.data;

import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/** A parameter. */
public final class Parameter extends Property<ParameterValue> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the string indicating the algorithm: {@value} */
  public static final String PARAMETER_ALGORITHM = "algorithm"; //$NON-NLS-1$

  /** the string indicating that algorithm class follows: {@value} */
  public static final String PARAMETER_ALGORITHM_CLASS = (Parameter.PARAMETER_ALGORITHM + "Class"); //$NON-NLS-1$

  /** the string indicating that the algorithm name follows: {@value} */
  public static final String PARAMETER_ALGORITHM_NAME = (Parameter.PARAMETER_ALGORITHM + "Name"); //$NON-NLS-1$

  /** the initializer prefix */
  public static final String PARAMETER_INITIALIZER = "initializer"; //$NON-NLS-1$

  /**
   * the string indicating that the class of the initialization algorithm
   * follows: {@value}
   */
  public static final String PARAMETER_INITIALIZER_CLASS = (Parameter.PARAMETER_INITIALIZER + "Class"); //$NON-NLS-1$

  /**
   * the string indicating that the name of the initialization algorithm
   * follows: {@value}
   */
  public static final String PARAMETER_INITIALIZER_NAME = (Parameter.PARAMETER_INITIALIZER + "Name"); //$NON-NLS-1$

  /** the unspecified value */
  final ParameterValue m_unspec;

  /**
   * create
   * 
   * @param name
   *          the parameter name
   * @param desc
   *          the description
   * @param primitiveType
   *          the primitive type of the parameter, or {@code null} if the
   *          parameter is a string parameter
   * @param values
   *          the values
   * @param general
   *          the generalized value
   * @param unspecified
   *          the unspecified value
   */
  Parameter(final String name, final String desc,
      final EPrimitiveType primitiveType, final PropertyValue<?>[] values,
      final ParameterValue unspecified, final ParameterValue general) {
    super(name, desc, primitiveType, values, general);

    this.m_unspec = unspecified;
    if (unspecified != null) {
      unspecified.m_owner = this;
    }
  }

  /**
   * Obtain the parameter value record indicating an unspecified value, or
   * {@code null} if all experiments have a specific value for this
   * parameter.
   * 
   * @return the parameter value record indicating an unspecified value, or
   *         {@code null} if all experiments have a specific value for this
   *         parameter.
   */
  @Override
  public final ParameterValue unspecified() {
    return this.m_unspec;
  }

  /** {@inheritDoc} */
  @Override
  public final ParameterSet getOwner() {
    return ((ParameterSet) (this.m_owner));
  }
}
