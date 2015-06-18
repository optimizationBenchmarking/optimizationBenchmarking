package org.optimizationBenchmarking.experimentation.attributes.functions;

import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSet;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.compound.FunctionBuilder;
import org.optimizationBenchmarking.utils.math.text.IMathRenderable;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A parser for dimension transformations.
 */
public final class NamedSourceTransformationParser extends
    _TransformationParser<NamedSourceTransformation> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the name of the source */
  private final String m_sourceName;
  /** the renderable of the source */
  private final IMathRenderable m_sourceRenderable;

  /**
   * create the dimension transformation parser
   *
   * @param experimentSet
   *          the experiment set
   * @param sourceName
   *          the name of the source
   * @param sourceRenderable
   *          the source renderable
   */
  public NamedSourceTransformationParser(
      final ExperimentSet experimentSet, final String sourceName,
      final IMathRenderable sourceRenderable) {
    super(experimentSet);

    this.m_sourceName = TextUtils.prepare(sourceName);
    if (this.m_sourceName == null) {
      throw new IllegalArgumentException(//
          "Name of source cannot be null, empty, or just consist of white space, but you provided '" //$NON-NLS-1$
              + sourceName + '\'');
    }

    if (sourceRenderable == null) {
      throw new IllegalArgumentException(//
          "Source renderable corresponding to '" + sourceName + //$NON-NLS-1$
              "' is null, but must not be!"); //$NON-NLS-1$
    }
    this.m_sourceRenderable = sourceRenderable;
  }

  /** {@inheritDoc} */
  @Override
  final NamedSourceTransformation _createTransformation(
      final UnaryFunction function, final _DataBasedConstant[] constants) {
    final UnaryFunction func;

    func = this.m_unary;
    this.m_unary = null;
    if (func == null) {
      throw new IllegalArgumentException(//
          "You must specify and use the source of a named source transformation."); //$NON-NLS-1$
    }
    return new NamedSourceTransformation(function, constants,
        this.m_sourceRenderable);
  }

  /** {@inheritDoc} */
  @Override
  final UnaryFunction _resolveUnknownName(final String name,
      final FunctionBuilder<UnaryFunction> builder) {

    if (this.m_sourceName.equalsIgnoreCase(TextUtils.prepare(name))) {
      if (this.m_unary == null) {
        this.m_unary = builder.parameter(0);
      }
      return this.m_unary;
    }

    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final Class<NamedSourceTransformation> getOutputClass() {
    return NamedSourceTransformation.class;
  }

}
