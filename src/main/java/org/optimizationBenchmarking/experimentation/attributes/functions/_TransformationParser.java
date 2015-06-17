package org.optimizationBenchmarking.experimentation.attributes.functions;

import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.utils.collections.maps.StringMapCI;
import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.compound.FunctionBuilder;
import org.optimizationBenchmarking.utils.math.functions.compound.UnaryFunctionBuilder;
import org.optimizationBenchmarking.utils.math.text.AbstractNameResolver;
import org.optimizationBenchmarking.utils.math.text.CompoundFunctionParser;
import org.optimizationBenchmarking.utils.parsers.Parser;

/**
 * A parser which can translate a string to a data transformation.
 * 
 * @param <TT>
 *          the transformation type
 */
abstract class _TransformationParser<TT extends _Transformation<?>>
    extends Parser<TT> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the internal function parser */
  private final CompoundFunctionParser<UnaryFunction> m_functionParser;

  /** the property resolver */
  private final __PropertyResolver m_resolver;

  /** the experiment set */
  final ExperimentSet m_experimentSet;

  /**
   * the unary function for the parameter: must be reset to {@code null} by
   * {@link #_createTransformation(UnaryFunction, _PropertyConstant[])}
   */
  transient UnaryFunction m_unary;

  /**
   * create the transformation parser
   * 
   * @param experimentSet
   *          the experiment set
   */
  _TransformationParser(final ExperimentSet experimentSet) {
    super();

    if (experimentSet == null) {
      throw new IllegalArgumentException(//
          "Experiment set cannot be null."); //$NON-NLS-1$
    }
    this.m_experimentSet = experimentSet;

    this.m_resolver = new __PropertyResolver();
    this.m_functionParser = new CompoundFunctionParser<>(
        UnaryFunctionBuilder.getInstance(), this.m_resolver);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final TT parseString(final String string) {
    final UnaryFunction function;

    function = this.m_functionParser.parseString(string);
    return this._createTransformation(function,
        this.m_resolver._getConstants());
  }

  /**
   * Create the transformation
   * 
   * @param function
   *          the parsed function
   * @param constants
   *          the property constants it contains
   * @return the created transformation
   */
  abstract TT _createTransformation(final UnaryFunction function,
      final _PropertyConstant[] constants);

  /**
   * Resolve an otherwise unassigned name
   * 
   * @param name
   *          the name
   * @param builder
   *          the function builder
   * @return the function corresponding to the name
   */
  abstract UnaryFunction _resolveUnknownName(final String name,
      final FunctionBuilder<UnaryFunction> builder);

  /** the internal entity resolver */
  private final class __PropertyResolver extends AbstractNameResolver {

    /** the resolved properties */
    private final StringMapCI<__Resolved> m_resolved;

    /** create the internal property resolver */
    __PropertyResolver() {
      super();
      this.m_resolved = new StringMapCI<>();
    }

    /**
     * Obtain the parsed constants, after parsing is done.
     * 
     * @return the constants
     */
    synchronized _PropertyConstant[] _getConstants() {
      final int size;
      final _PropertyConstant[] constants;
      int index;

      size = this.m_resolved.size();
      if (size <= 0) {
        return null;
      }

      constants = new _PropertyConstant[size];
      index = 0;
      for (__Resolved resolved : this.m_resolved.values()) {
        constants[index++] = resolved.m_constant;
      }

      this.m_resolved.clear();
      return constants;
    }

    /** {@inheritDoc} */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public synchronized final MathematicalFunction resolve(String name,
        FunctionBuilder<?> builder) {
      __Resolved resolved;
      _PropertyConstant constant;
      IFeature feature;
      IParameter parameter;
      Number namedConst;
      UnaryFunction unknown;

      resolved = this.m_resolved.get(name);
      if (resolved != null) {
        return resolved.m_func;
      }

      feature = _TransformationParser.this.m_experimentSet.getFeatures()
          .find(name);
      if (feature != null) {
        constant = new _FeatureConstant(feature);
      } else {
        parameter = _TransformationParser.this.m_experimentSet
            .getParameters().find(name);
        if (parameter != null) {
          constant = new _ParameterConstant(parameter);
        } else {
          constant = null;
        }
      }

      if (constant != null) {
        resolved = new __Resolved(constant,
            ((UnaryFunction) (builder.constant(constant))));
        this.m_resolved.put(name, resolved);
        return resolved.m_func;
      }

      namedConst = resolveDefaultConstant(name);
      if (namedConst != null) {
        return builder.constant(namedConst);
      }

      unknown = _TransformationParser.this._resolveUnknownName(name,
          ((FunctionBuilder) builder));
      if (unknown != null) {
        return unknown;
      }

      return super.resolve(name, builder);
    }
  }

  /** a record for resolved properties */
  private static final class __Resolved {
    /** the constant */
    final _PropertyConstant m_constant;
    /** the function */
    final UnaryFunction m_func;

    /**
     * Create the resolved record
     * 
     * @param constant
     *          the constant
     * @param func
     *          the function
     */
    __Resolved(final _PropertyConstant constant, final UnaryFunction func) {
      super();
      this.m_constant = constant;
      this.m_func = func;
    }
  }
}
