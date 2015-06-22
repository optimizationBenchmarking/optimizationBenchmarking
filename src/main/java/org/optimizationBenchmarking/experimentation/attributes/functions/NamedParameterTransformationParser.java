package org.optimizationBenchmarking.experimentation.attributes.functions;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.compound.FunctionBuilder;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A parser for a transformations with a named parameter.
 */
public final class NamedParameterTransformationParser extends
    _TransformationParser<Transformation> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the default name for parameters */
  public static final String DEFAULT_PARAMETER_NAME = "result"; //$NON-NLS-1$

  /** the name of the parameter */
  private final String m_parameterName;

  /**
   * create the named parameter transformation parser
   *
   * @param experimentSet
   *          the experiment set
   * @param parameterName
   *          the name of the
   */
  public NamedParameterTransformationParser(
      final IExperimentSet experimentSet, final String parameterName) {
    super(experimentSet);
    this.m_parameterName = TextUtils.prepare(parameterName);
    if (this.m_parameterName == null) {
      throw new IllegalArgumentException(//
          "Name of parameter cannot be null, empty, or just consist of white space, but you provided '" //$NON-NLS-1$
              + parameterName + '\'');
    }
  }

  /**
   * create the named parameter transformation parser
   *
   * @param experimentSet
   *          the experiment set
   */
  public NamedParameterTransformationParser(
      final IExperimentSet experimentSet) {
    this(experimentSet,
        NamedParameterTransformationParser.DEFAULT_PARAMETER_NAME);
  }

  /** {@inheritDoc} */
  @Override
  final Transformation _createTransformation(final UnaryFunction function,
      final _DataBasedConstant[] constants) {
    final UnaryFunction func;

    func = this.m_unary;
    this.m_unary = null;
    if (func == null) {
      throw new IllegalArgumentException(//
          "You must specify and use the source of a named source transformation."); //$NON-NLS-1$
    }
    return new Transformation(function, constants);
  }

  /** {@inheritDoc} */
  @Override
  final UnaryFunction _resolveUnknownName(final String name,
      final FunctionBuilder<UnaryFunction> builder) {

    if (this.m_parameterName.equalsIgnoreCase(TextUtils.prepare(name))) {
      if (this.m_unary == null) {
        this.m_unary = builder.parameter(0);
      }
      return this.m_unary;
    }

    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final Class<Transformation> getOutputClass() {
    return Transformation.class;
  }
}
