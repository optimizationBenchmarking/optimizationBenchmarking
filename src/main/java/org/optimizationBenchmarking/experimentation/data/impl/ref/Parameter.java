package org.optimizationBenchmarking.experimentation.data.impl.ref;

import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/** A parameter. */
public final class Parameter extends Property<ParameterValue> implements
IParameter {

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
  public final ParameterValue getUnspecified() {
    return this.m_unspec;
  }

  /** {@inheritDoc} */
  @Override
  public final ParameterSet getOwner() {
    return ((ParameterSet) (this.m_owner));
  }

  /**
   * Get the value of this property for the given
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Experiment}
   *
   * @param experiment
   *          the experiment to get the property value of
   * @return the property value
   */
  public final Object get(final Experiment experiment) {
    if (experiment == null) {
      throw new IllegalArgumentException(//
          "Cannot get value of parameter '"//$NON-NLS-1$
          + this.getName() + //
          "' for null experiment."); //$NON-NLS-1$
    }
    return experiment.getParameterSetting().get(this);
  }

  /**
   * Get the value of this parameter for the given element
   *
   * @param element
   *          the element to get the parameter value of, must be an
   *          experiment, or else {@link IllegalArgumentException}
   * @return the property value
   * @throws IllegalArgumentException
   *           if {@code element} is not an instance of
   *           {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Experiment}
   */
  @Override
  public final Object get(final DataElement element) {
    if (element instanceof Experiment) {
      return this.get((Experiment) element);
    }
    throw new IllegalArgumentException(//
        "Cannot get value of parameter '" //$NON-NLS-1$
        + this.getName() + //
        "' for data element '"//$NON-NLS-1$
        + element + //
        "' - only experiments can have parameter values.");//$NON-NLS-1$
  }

}
