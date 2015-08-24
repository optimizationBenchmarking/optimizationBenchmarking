package org.optimizationBenchmarking.experimentation.data.impl.flat;

import org.optimizationBenchmarking.experimentation.data.impl.ref.DataPoint;
import org.optimizationBenchmarking.experimentation.data.impl.ref.Dimension;
import org.optimizationBenchmarking.experimentation.data.impl.ref.Instance;
import org.optimizationBenchmarking.experimentation.data.spec.EDimensionDirection;
import org.optimizationBenchmarking.experimentation.data.spec.EDimensionType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.parsers.NumberParserParser;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * An abstract base class for building elements of the experiment set API
 * in a flat way. An implementation of this class may choose to support all
 * or only a subset of the provided methods. For normal data setting
 * routines, un-implemented methods should just do nothing. Unsupported
 * getters ( {@link #getDimensionSet()}, {@link #getExperimentSet()},
 * {@linkplain #getFeatureSet()} , and {@link #getInstanceSet()}) should
 * throw an {@link java.lang.UnsupportedOperationException}.
 */
public abstract class AbstractFlatExperimentSetContext {

  /** Create the experiment data parser */
  protected AbstractFlatExperimentSetContext() {
    super();
  }

  /**
   * If this flat context is, e.g., in the phase of constructing an
   * experiment, this process is finished. If it wraps around a
   * {@linkplain org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSetContext
   * hierarchical experiment set context}, all child contexts from this one
   * are closed, except that root context itself.
   */
  public void flush() {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Begin a (new?) dimension
   *
   * @param forceNew
   *          {@code true}: close a potentially existing dimension context
   *          and open a new one, {@code false}: keep a potentially open
   *          context
   */
  public void dimensionBegin(final boolean forceNew) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /** End the current dimension */
  public void dimensionEnd() {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set the name of the current dimension. If we currently are not in a
   * dimension context, try to create one.
   *
   * @param name
   *          the name of the current dimension
   */
  public void dimensionSetName(final String name) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set the description of the current dimension. If we currently are not
   * in a dimension context, try to create one.
   *
   * @param description
   *          the description of the current dimension
   */
  public void dimensionSetDescription(final String description) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Add some text to the description of the current dimension. If we
   * currently are not in a dimension context, try to create one.
   *
   * @param description
   *          the description to be added the current dimension
   */
  public void dimensionAddDescription(final String description) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set the direction of the current dimension. If we currently are not in
   * a dimension context, try to create one.
   *
   * @param direction
   *          the direction of the current dimension
   */
  public void dimensionSetDirection(final String direction) {
    this.dimensionSetDirection(EDimensionDirection.valueOf(direction));
  }

  /**
   * Set the direction of the current dimension. If we currently are not in
   * a dimension context, try to create one.
   *
   * @param direction
   *          the direction of the current dimension
   */
  public void dimensionSetDirection(final EDimensionDirection direction) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set the parser of the current dimension. If we currently are not in a
   * dimension context, try to create one.
   *
   * @param parserClass
   *          the parser class: must take two numbers as parameter
   * @param lowerBound
   *          the lower boundary
   * @param upperBound
   *          the upper boundary
   */
  public void dimensionSetParser(
      final Class<? extends NumberParser<?>> parserClass,
      final Number lowerBound, final Number upperBound) {
    final NumberParser<?> parser;

    try {
      parser = parserClass.getConstructor(Number.class, Number.class)
          .newInstance(lowerBound, upperBound);
    } catch (final Throwable t) {
      throw new RuntimeException(((((((//
          "Illegal parser class and configuration: " + //$NON-NLS-1$
          TextUtils.className(parserClass)) + ", '") + //$NON-NLS-1$
          lowerBound) + "', '") + upperBound) + '.'), t); //$NON-NLS-1$
    }

    this.dimensionSetParser(parser);
  }

  /**
   * Set the parser of the current dimension. If we currently are not in a
   * dimension context, try to create one.
   *
   * @param parser
   *          the parser of the current dimension
   */
  public void dimensionSetParser(final NumberParser<?> parser) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set the parser of the current dimension. If we currently are not in a
   * dimension context, try to create one.
   *
   * @param parserClass
   *          the parser class: must take two numbers as parameter
   * @param lowerBound
   *          the lower boundary
   * @param upperBound
   *          the upper boundary
   */
  public void dimensionSetParser(final String parserClass,
      final String lowerBound, final String upperBound) {
    final NumberParser<Number> parser;
    try {
      parser = NumberParserParser.getInstance().parse(parserClass,
          lowerBound, upperBound);
      if (parser == null) {
        throw new IllegalArgumentException(//
            "Parser description parses to null parser.");//$NON-NLS-1$
      }
    } catch (final Throwable error) {
      throw new IllegalArgumentException((((((//
          "Invalid parser description '" + //$NON-NLS-1$
          parserClass) + ':') + lowerBound) + ':') + upperBound), error);
    }
    this.dimensionSetParser(parser);
  }

  /**
   * Set the parser of the current dimension. If we currently are not in a
   * dimension context, try to create one.
   *
   * @param parserDesc
   *          the parser description of the current dimension
   */
  public void dimensionSetParser(final String parserDesc) {
    final NumberParser<Number> parser;
    try {
      parser = NumberParserParser.getInstance().parseString(parserDesc);
      if (parser == null) {
        throw new IllegalArgumentException(//
            "Parser description parses to null parser.");//$NON-NLS-1$
      }
    } catch (final Throwable error) {
      throw new IllegalArgumentException(((//
          "Invalid parser description '" + //$NON-NLS-1$
          parserDesc) + '\''), error);
    }
    this.dimensionSetParser(parser);
  }

  /**
   * Set the type of the current dimension. If we currently are not in a
   * dimension context, try to create one.
   *
   * @param type
   *          the type of the current dimension
   */
  public void dimensionSetType(final EDimensionType type) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set the type of the current dimension. If we currently are not in a
   * dimension context, try to create one.
   *
   * @param type
   *          the type of the current dimension
   */
  public void dimensionSetType(final String type) {
    this.dimensionSetType(EDimensionType.valueOf(type));
  }

  /**
   * Obtain the set of all dimensions
   *
   * @return the set of all dimensions
   */
  public IDimensionSet getDimensionSet() {
    throw new UnsupportedOperationException(//
        "This implementation of the flat experiment set builder does not provide dimension sets."); //$NON-NLS-1$
  }

  /**
   * Define an instance feature with a given name and description
   *
   * @param name
   *          the feature name
   * @param desc
   *          the feature's description
   */
  public void featureDeclare(final String name, final String desc) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Define a parameter with a given name and description
   *
   * @param name
   *          the parameter name
   * @param desc
   *          the parameter's description
   */
  public void parameterDeclare(final String name, final String desc) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Begin a (new?) instance
   *
   * @param forceNew
   *          {@code true}: close a potentially existing instance context
   *          and open a new one, {@code false}: keep a potentially open
   *          context
   */
  public void instanceBegin(final boolean forceNew) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /** End the current instance */
  public void instanceEnd() {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set the name of the current instance. If we currently are not in a
   * instance context, try to create one.
   *
   * @param name
   *          the name of the current instance
   */
  public void instanceSetName(final String name) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set the description of the current instance. If we currently are not
   * in a instance context, try to create one.
   *
   * @param description
   *          the description of the current instance
   */
  public void instanceSetDescription(final String description) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Add some text to the description of the current instance. If we
   * currently are not in a instance context, try to create one.
   *
   * @param description
   *          the description to be added the current instance
   */
  public void instanceAddDescription(final String description) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set a feature value of the current instance. If we currently are not
   * in a instance context, try to create one.
   *
   * @param featureName
   *          the feature name
   * @param featureValue
   *          the feature value
   */
  public void instanceSetFeatureValue(final String featureName,
      final Object featureValue) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set a feature value of the current instance. If we currently are not
   * in a instance context, try to create one.
   *
   * @param featureName
   *          the feature name
   * @param featureValue
   *          the feature value
   * @param featureValueDescription
   *          the feature value description
   */
  public void instanceSetFeatureValue(final String featureName,
      final Object featureValue, final String featureValueDescription) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set a feature value of the current instance. If we currently are not
   * in a instance context, try to create one.
   *
   * @param featureName
   *          the feature name
   * @param featureDescription
   *          the feature description
   * @param featureValue
   *          the feature value
   * @param featureValueDescription
   *          the feature value description
   */
  public void instanceSetFeatureValue(final String featureName,
      final String featureDescription, final Object featureValue,
      final String featureValueDescription) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set the lower boundary for the given dimension of the current
   * instance. If we currently are not in a instance context, try to create
   * one.
   *
   * @param dim
   *          the dimension
   * @param bound
   *          the lower bound
   */
  public void instanceSetLowerBound(final Dimension dim, final Number bound) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set the lower boundary for the given dimension of the current
   * instance. If we currently are not in a instance context, try to create
   * one.
   *
   * @param dim
   *          the dimension
   * @param bound
   *          the lower bound
   */
  public void instanceSetLowerBound(final Object dim, final Object bound) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set the upper boundary for the given dimension of the current
   * instance. If we currently are not in a instance context, try to create
   * one.
   *
   * @param dim
   *          the dimension
   * @param bound
   *          the upper bound
   */
  public void instanceSetUpperBound(final Dimension dim, final Number bound) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set the upper boundary for the given dimension of the current
   * instance. If we currently are not in a instance context, try to create
   * one.
   *
   * @param dim
   *          the dimension
   * @param bound
   *          the upper bound
   */
  public void instanceSetUpperBound(final Object dim, final Object bound) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Obtain the set of all instances
   *
   * @return the set of all instances
   */
  public IInstanceSet getInstanceSet() {
    throw new UnsupportedOperationException(//
        "This implementation of the flat experiment set builder does not provide instance sets."); //$NON-NLS-1$
  }

  /**
   * Obtain the set of all features
   *
   * @return the set of all features
   */
  public IFeatureSet getFeatureSet() {
    throw new UnsupportedOperationException(//
        "This implementation of the flat experiment set builder does not provide feature sets."); //$NON-NLS-1$
  }

  /**
   * Begin a (new?) experiment
   *
   * @param forceNew
   *          {@code true}: close a potentially existing experiment context
   *          and open a new one, {@code false}: keep a potentially open
   *          context
   */
  public void experimentBegin(final boolean forceNew) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /** End the current experiment */
  public void experimentEnd() {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set the name of the current experiment. If we currently are not in a
   * experiment context, try to create one.
   *
   * @param name
   *          the name of the current experiment
   */
  public void experimentSetName(final String name) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set the description of the current experiment. If we currently are not
   * in a experiment context, try to create one.
   *
   * @param description
   *          the description of the current experiment
   */
  public void experimentSetDescription(final String description) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Add some text to the description of the current experiment. If we
   * currently are not in a experiment context, try to create one.
   *
   * @param description
   *          the description to be added the current experiment
   */
  public void experimentAddDescription(final String description) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set a parameter value of the current experiment. If we currently are
   * not in a experiment context, try to create one.
   *
   * @param parameterName
   *          the parameter name
   * @param parameterValue
   *          the parameter value
   */
  public void parameterSetValue(final String parameterName,
      final Object parameterValue) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set a parameter value of the current experiment. If we currently are
   * not in a experiment context, try to create one.
   *
   * @param parameterName
   *          the parameter name
   * @param parameterValue
   *          the parameter value
   * @param parameterValueDescription
   *          the parameter value description
   */
  public void parameterSetValue(final String parameterName,
      final Object parameterValue, final String parameterValueDescription) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set a parameter value of the current experiment. If we currently are
   * not in a experiment context, try to create one.
   *
   * @param parameterName
   *          the parameter name
   * @param parameterDescription
   *          the parameter description
   * @param parameterValue
   *          the parameter value
   * @param parameterValueDescription
   *          the parameter value description
   */
  public void parameterSetValue(final String parameterName,
      final String parameterDescription, final Object parameterValue,
      final String parameterValueDescription) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Begin a (new?) run set
   *
   * @param forceNew
   *          {@code true}: close a potentially existing run set context
   *          and open a new one, {@code false}: keep a potentially open
   *          context
   */
  public void runsBegin(final boolean forceNew) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /** End the current run set */
  public void runsEnd() {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set the instance of this instance run context. If we currently are not
   * in a run set context, try to create one.
   *
   * @param inst
   *          the instance of this instance run context
   */
  public void runsSetInstance(final Instance inst) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Set the instance of this instance run context. If we currently are not
   * in a run set context, try to create one.
   *
   * @param inst
   *          the instance of this instance run context
   */
  public void runsSetInstance(final String inst) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Begin a (new?) run
   *
   * @param forceNew
   *          {@code true}: close a potentially existing run context and
   *          open a new one, {@code false}: keep a potentially open
   *          context
   */
  public void runBegin(final boolean forceNew) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /** End the current run */
  public void runEnd() {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Add a data point to the current run. If we currently are not in a run
   * context, try to create one.
   *
   * @param point
   *          the data point to be added
   */
  public void runAddDataPoint(final DataPoint point) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Add a data point to the current run. If we currently are not in a run
   * context, try to create one.
   *
   * @param point
   *          the data point to be added
   */
  public void runAddDataPoint(final Object point) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Add a data point to the current run. If we currently are not in a run
   * context, try to create one.
   *
   * @param values
   *          the data point values to be added
   */
  public void runAddDataPoint(final Number... values) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Add a data point to the current run. If we currently are not in a run
   * context, try to create one.
   *
   * @param point
   *          the data point to be added
   */
  public void runAddDataPoint(final String point) {
    // this method does nothing, yet - implement it in a sub-class
  }

  /**
   * Get the experiment set created with this builder.
   *
   * @return the experiment set created with this builder.
   */
  public IExperimentSet getExperimentSet() {
    throw new UnsupportedOperationException(//
        "This implementation of the flat experiment set builder does not experiment sets."); //$NON-NLS-1$
  }
}
