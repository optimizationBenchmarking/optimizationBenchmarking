package org.optimizationBenchmarking.experimentation.io.impl;

import org.optimizationBenchmarking.experimentation.data.Dimension;
import org.optimizationBenchmarking.experimentation.data.DimensionContext;
import org.optimizationBenchmarking.experimentation.data.EDimensionDirection;
import org.optimizationBenchmarking.experimentation.data.EDimensionType;
import org.optimizationBenchmarking.experimentation.data.ExperimentContext;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.data.InstanceContext;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * This class wraps the experiment data building capabilities into a single
 * object maintaining a stack as parser state. In other words, it flattens
 * the whole hierarchical experiment data API.
 */
public class FlatExperimentSetContext {

  /** the hierarchical fsm stack */
  private final HierarchicalFSM[] m_stack;

  /** the current depth in the stack */
  private int m_depth;

  /**
   * Create the experiment data parser
   * 
   * @param context
   *          the wrapped experiment set context
   */
  public FlatExperimentSetContext(final ExperimentSetContext context) {
    super();

    if (context == null) {
      throw new IllegalArgumentException(//
          "ExperimentSetContext cannot be null."); //$NON-NLS-1$
    }

    this.m_stack = new HierarchicalFSM[5];
    this.m_stack[0] = context;
    this.m_depth = 1;
  }

  /**
   * Close all open contexts <em>except</em> the root experiment set
   * context
   */
  @SuppressWarnings("resource")
  public synchronized final void flush() {
    HierarchicalFSM fsm;

    try {
      while (this.m_depth > 1) {
        fsm = this.m_stack[--this.m_depth];
        this.m_stack[this.m_depth] = null;
        fsm.close();
      }
    } catch (Throwable error) {
      throw new IllegalStateException(
          "Error while flushing the context stack.", //$NON-NLS-1$
          error);
    }
  }

  /**
   * Throw an {@link IllegalStateException} with a given error message and
   * including some current state information.
   * 
   * @param error
   *          the error
   * @throws IllegalStateException
   *           always
   */
  private final void __illegalState(final String error)
      throws IllegalStateException {
    throw new IllegalStateException(//
        error + " The current parser depth is " //$NON-NLS-1$
            + this.m_depth + //
            " and current element is an instance of "//$NON-NLS-1$
            + TextUtils.className(//
                this.m_stack[this.m_depth - 1].getClass()));
  }

  /**
   * Begin a (new?) dimension
   * 
   * @param forceNew
   *          {@code true}: close a potentially existing dimension context
   *          and open a new one, {@code false}: keep a potentially open
   *          context
   */
  public synchronized final void dimensionBegin(final boolean forceNew) {
    try {
      this.__dimensionEnsure(forceNew);
    } catch (Throwable error) {
      throw new IllegalStateException(((//
          "Cannot begin a new dimension (with forceNew=" //$NON-NLS-1$
          + forceNew) + ')') + '.');
    }
  }

  /**
   * Ensure that we are in a dimension context
   * 
   * @param forceNew
   *          should we enforce the creation of a new dimension?
   * @return the dimension context
   */
  private final DimensionContext __dimensionEnsure(final boolean forceNew) {
    final HierarchicalFSM fsm;
    final DimensionContext context;

    switch (this.m_depth) {
      case 1: {
        this.m_depth = 2;
        break;
      }
      case 2: {
        fsm = this.m_stack[1];
        if (fsm instanceof DimensionContext) {
          if (forceNew) {
            this.m_stack[1] = null;
            fsm.close();
            break;
          }
          return ((DimensionContext) fsm);
        }
      }
      default: {
        this.__illegalState(//
        "Cannot begin a new dimension here.");//$NON-NLS-1$
        return null;
      }
    }

    context = ((ExperimentSetContext) (this.m_stack[0])).createDimension();
    this.m_stack[1] = context;
    return context;
  }

  /** End the current dimension */
  @SuppressWarnings("resource")
  public synchronized final void dimensionEnd() {
    final HierarchicalFSM fsm;

    if (this.m_depth == 2) {
      fsm = this.m_stack[1];
      if (fsm instanceof DimensionContext) {
        this.m_stack[1] = null;
        try {
          fsm.close();
        } catch (Throwable error) {
          throw new IllegalStateException(//
              "Error while closing dimension context.");//$NON-NLS-1$
        }
      }
    }

    this.__illegalState("Cannot close dimension."); //$NON-NLS-1$
  }

  /**
   * Set the name of the current dimension. If we currently are not in a
   * dimension context, try to create one.
   * 
   * @param name
   *          the name of the current dimension
   */
  public synchronized final void dimensionSetName(final String name) {
    try {
      this.__dimensionEnsure(false).setName(name);
    } catch (Throwable error) {
      throw new IllegalStateException(//
          (((("Error while setting dimension name '") //$NON-NLS-1$
          + name) + '\'') + '.'), error);
    }
  }

  /**
   * Set the description of the current dimension. If we currently are not
   * in a dimension context, try to create one.
   * 
   * @param description
   *          the description of the current dimension
   */
  public synchronized final void dimensionSetDescription(
      final String description) {
    try {
      this.__dimensionEnsure(false).setDescription(description);
    } catch (Throwable error) {
      throw new IllegalStateException(//
          (((("Error while setting dimension description '") //$NON-NLS-1$
          + description) + '\'') + '.'), error);
    }
  }

  /**
   * Add some text to the description of the current dimension. If we
   * currently are not in a dimension context, try to create one.
   * 
   * @param description
   *          the description to be added the current dimension
   */
  public synchronized final void dimensionAddDescription(
      final String description) {
    try {
      this.__dimensionEnsure(false).addDescription(description);
    } catch (Throwable error) {
      throw new IllegalStateException(//
          (((("Error while adding dimension description '") //$NON-NLS-1$
          + description) + '\'') + '.'), error);
    }
  }

  /**
   * Set the direction of the current dimension. If we currently are not in
   * a dimension context, try to create one.
   * 
   * @param direction
   *          the direction of the current dimension
   */
  public synchronized final void dimensionSetDirection(
      final String direction) {
    try {
      this.__dimensionEnsure(false).setDirection(direction);
    } catch (Throwable error) {
      throw new IllegalStateException(//
          (((("Error while setting dimension direction string '") //$NON-NLS-1$
          + direction) + '\'') + '.'), error);
    }
  }

  /**
   * Set the direction of the current dimension. If we currently are not in
   * a dimension context, try to create one.
   * 
   * @param direction
   *          the direction of the current dimension
   */
  public synchronized final void dimensionSetDirection(
      final EDimensionDirection direction) {
    try {
      this.__dimensionEnsure(false).setDirection(direction);
    } catch (Throwable error) {
      throw new IllegalStateException(//
          (((("Error while setting dimension direction '") //$NON-NLS-1$
          + direction) + '\'') + '.'), error);
    }
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
  public synchronized final void dimensionSetParser(
      final Class<? extends NumberParser<?>> parserClass,
      final Number lowerBound, final Number upperBound) {
    try {
      this.__dimensionEnsure(false).setParser(parserClass, lowerBound,
          upperBound);
    } catch (Throwable error) {
      throw new IllegalStateException(//
          ((((((("Error while setting dimension parser class ") //$NON-NLS-1$
          + TextUtils.className(parserClass)) + " and lower bound ") + lowerBound) + //$NON-NLS-1$
          " and upper bound ") + upperBound) + '.'), error);//$NON-NLS-1$
    }
  }

  /**
   * Set the parser of the current dimension. If we currently are not in a
   * dimension context, try to create one.
   * 
   * @param parser
   *          the parser of the current dimension
   */
  public synchronized final void dimensionSetParser(
      final NumberParser<?> parser) {
    try {
      this.__dimensionEnsure(false).setParser(parser);
    } catch (Throwable error) {
      throw new IllegalStateException(//
          (((("Error while setting dimension parser '") //$NON-NLS-1$
          + parser) + '\'') + '.'), error);
    }
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
  public synchronized final void dimensionSetParser(
      final String parserClass, final String lowerBound,
      final String upperBound) {
    try {
      this.__dimensionEnsure(false).setParser(parserClass, lowerBound,
          upperBound);
    } catch (Throwable error) {
      throw new IllegalStateException(//
          ((((((("Error while setting dimension parser strings '") //$NON-NLS-1$
              + parserClass + "' and lower bound '") + lowerBound) + //$NON-NLS-1$
          " and upper bound '") + upperBound) + '\'') + '.'), error);//$NON-NLS-1$
    }
  }

  /**
   * Set the parser of the current dimension. If we currently are not in a
   * dimension context, try to create one.
   * 
   * @param parser
   *          the parser of the current dimension
   */
  public synchronized final void dimensionSetParser(final String parser) {
    try {
      this.__dimensionEnsure(false).setParser(parser);
    } catch (Throwable error) {
      throw new IllegalStateException(//
          (((("Error while setting dimension parser string '") //$NON-NLS-1$
          + parser) + '\'') + '.'), error);
    }
  }

  /**
   * Set the type of the current dimension. If we currently are not in a
   * dimension context, try to create one.
   * 
   * @param type
   *          the type of the current dimension
   */
  public synchronized final void dimensionSetType(final EDimensionType type) {
    try {
      this.__dimensionEnsure(false).setType(type);
    } catch (Throwable error) {
      throw new IllegalStateException(//
          ((("Error while setting dimension type ") //$NON-NLS-1$
          + type) + '.'), error);
    }
  }

  /**
   * Set the type of the current dimension. If we currently are not in a
   * dimension context, try to create one.
   * 
   * @param type
   *          the type of the current dimension
   */
  public synchronized final void dimensionSetType(final String type) {
    try {
      this.__dimensionEnsure(false).setType(type);
    } catch (Throwable error) {
      throw new IllegalStateException(//
          (((("Error while setting dimension type string '") //$NON-NLS-1$
          + type) + '\'') + '.'), error);
    }
  }

  /**
   * Define an instance feature with a given name and description
   * 
   * @param name
   *          the feature name
   * @param desc
   *          the feature's description
   */
  @SuppressWarnings("resource")
  public synchronized final void declareFeature(final String name,
      final String desc) {
    final HierarchicalFSM fsm;

    switch (this.m_depth) {
      case 1: {
        try {
          ((ExperimentSetContext) (this.m_stack[0])).declareFeature(name,
              desc);
        } catch (Throwable error) {
          throw new IllegalStateException(//
              ((((("Error while declaring feature '") //$NON-NLS-1$
              + name) + "' with description '") + desc) + //$NON-NLS-1$
              "' to the experiment set context."), error);//$NON-NLS-1$
        }
        return;
      }
      case 2: {
        fsm = this.m_stack[1];
        if (fsm instanceof InstanceContext) {
          try {
            ((InstanceContext) fsm).declareFeature(name, desc);
          } catch (Throwable error) {
            throw new IllegalStateException(//
                ((((("Error while declaring feature '") //$NON-NLS-1$
                + name) + "' with description '") + desc) + //$NON-NLS-1$
                "' to the instance context."), error);//$NON-NLS-1$
          }
          return;
        }
      }
      default: {
        this.__illegalState((((//
            "Cannot declare feature '" + //$NON-NLS-1$
            name) + "' with description '") + //$NON-NLS-1$
            desc)
            + "' now."); //$NON-NLS-1$
      }
    }
  }

  /**
   * Define a parameter with a given name and description
   * 
   * @param name
   *          the parameter name
   * @param desc
   *          the parameter's description
   */
  @SuppressWarnings("resource")
  public synchronized final void declareParameter(final String name,
      final String desc) {
    final HierarchicalFSM fsm;

    switch (this.m_depth) {
      case 1: {
        try {
          ((ExperimentSetContext) (this.m_stack[0])).declareParameter(
              name, desc);
        } catch (Throwable error) {
          throw new IllegalStateException(//
              ((((("Error while declaring parameter '") //$NON-NLS-1$
              + name) + "' with description '") + desc) + //$NON-NLS-1$
              "' to the experiment set context."), error);//$NON-NLS-1$
        }
        return;
      }
      case 2: {
        fsm = this.m_stack[1];
        if (fsm instanceof ExperimentContext) {
          try {
            ((ExperimentContext) fsm).declareParameter(name, desc);
          } catch (Throwable error) {
            throw new IllegalStateException(//
                ((((("Error while declaring parameter '") //$NON-NLS-1$
                + name) + "' with description '") + desc) + //$NON-NLS-1$
                "' to the experiment context."), error);//$NON-NLS-1$
          }
          return;
        }
      }
      default: {
        this.__illegalState((((//
            "Cannot declare parameter '" + //$NON-NLS-1$
            name) + "' with description '") + //$NON-NLS-1$
            desc)
            + "' now."); //$NON-NLS-1$
      }
    }
  }

  /**
   * Begin a (new?) instance
   * 
   * @param forceNew
   *          {@code true}: close a potentially existing instance context
   *          and open a new one, {@code false}: keep a potentially open
   *          context
   */
  public synchronized final void instanceBegin(final boolean forceNew) {
    this.__instanceEnsure(forceNew);
  }

  /**
   * Ensure that we are in a instance context
   * 
   * @param forceNew
   *          should we enforce the creation of a new instance?
   * @return the instance context
   */
  private final InstanceContext __instanceEnsure(final boolean forceNew) {
    final HierarchicalFSM fsm;
    final InstanceContext context;

    switch (this.m_depth) {
      case 1: {
        this.m_depth = 2;
        break;
      }
      case 2: {
        fsm = this.m_stack[1];

        if (fsm instanceof DimensionContext) {
          this.m_stack[1] = null;
          fsm.close();
          break;
        }

        if (fsm instanceof InstanceContext) {
          if (forceNew) {
            this.m_stack[1] = null;
            fsm.close();
            break;
          }
          return ((InstanceContext) fsm);
        }
      }
      default: {
        this.__illegalState(//
        "Cannot begin a new instance here.");//$NON-NLS-1$
        return null;
      }
    }

    context = ((ExperimentSetContext) (this.m_stack[0])).createInstance();
    this.m_stack[1] = context;
    return context;
  }

  /** End the current instance */
  @SuppressWarnings("resource")
  public synchronized final void instanceEnd() {
    final HierarchicalFSM fsm;

    if (this.m_depth == 2) {
      fsm = this.m_stack[1];
      if (fsm instanceof InstanceContext) {
        this.m_stack[1] = null;
        fsm.close();
      }
    }

    this.__illegalState("Cannot close instance."); //$NON-NLS-1$
  }

  /**
   * Set the name of the current instance. If we currently are not in a
   * instance context, try to create one.
   * 
   * @param name
   *          the name of the current instance
   */
  public synchronized final void instanceSetName(final String name) {
    this.__instanceEnsure(false).setName(name);
  }

  /**
   * Set the description of the current instance. If we currently are not
   * in a instance context, try to create one.
   * 
   * @param description
   *          the description of the current instance
   */
  public synchronized final void instanceSetDescription(
      final String description) {
    this.__instanceEnsure(false).setDescription(description);
  }

  /**
   * Add some text to the description of the current instance. If we
   * currently are not in a instance context, try to create one.
   * 
   * @param description
   *          the description to be added the current instance
   */
  public synchronized final void instanceAddDescription(
      final String description) {
    this.__instanceEnsure(false).addDescription(description);
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
  public final synchronized void instanceSetFeatureValue(
      final String featureName, final Object featureValue) {
    this.__instanceEnsure(false)
        .setFeatureValue(featureName, featureValue);
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
  public final synchronized void instanceSetFeatureValue(
      final String featureName, final Object featureValue,
      final String featureValueDescription) {
    this.__instanceEnsure(false).setFeatureValue(featureName,
        featureValue, featureValueDescription);
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
  public final synchronized void instanceSetFeatureValue(
      final String featureName, final String featureDescription,
      final Object featureValue, final String featureValueDescription) {
    this.__instanceEnsure(false).setFeatureValue(featureName,
        featureDescription, featureValue, featureValueDescription);
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
  public synchronized final void instanceSetLowerBound(
      final Dimension dim, final Number bound) {
    this.__instanceEnsure(false).setLowerBound(dim, bound);
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
  public synchronized final void instanceSetLowerBound(final Object dim,
      final Object bound) {
    this.__instanceEnsure(false).setLowerBound(dim, bound);
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
  public synchronized final void instanceSetUpperBound(
      final Dimension dim, final Number bound) {
    this.__instanceEnsure(false).setUpperBound(dim, bound);
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
  public synchronized final void instanceSetUpperBound(final Object dim,
      final Object bound) {
    this.__instanceEnsure(false).setUpperBound(dim, bound);
  }
}
