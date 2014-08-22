package org.optimizationBenchmarking.experimentation.data;

import java.util.HashMap;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** A builder for experiments. */
public final class ExperimentSetContext extends _FSM {

  /** a dimension set context was created */
  private static final int STATE_DIMENSION_SET_CONTEXT_CREATED = (_FSM.STATE_OPEN + 1);
  /** a dimension set context entered the before-open state */
  private static final int STATE_DIMENSION_SET_CONTEXT_BEFORE_OPEN = (ExperimentSetContext.STATE_DIMENSION_SET_CONTEXT_CREATED + 1);
  /** a dimension set context entered the after-open state */
  private static final int STATE_DIMENSION_SET_CONTEXT_AFTER_OPEN = (ExperimentSetContext.STATE_DIMENSION_SET_CONTEXT_BEFORE_OPEN + 1);
  /** a dimension set context entered the closed state */
  private static final int STATE_DIMENSION_SET_CONTEXT_CLOSED = (ExperimentSetContext.STATE_DIMENSION_SET_CONTEXT_AFTER_OPEN + 1);
  /** a dimension set context entered the closed state */
  private static final int STATE_DIMENSION_SET_OBTAINED = (ExperimentSetContext.STATE_DIMENSION_SET_CONTEXT_CLOSED + 1);

  /** a instance set context was created */
  private static final int STATE_INSTANCE_SET_CONTEXT_CREATED = (ExperimentSetContext.STATE_DIMENSION_SET_OBTAINED + 1);
  /** a instance set context entered the before-open state */
  private static final int STATE_INSTANCE_SET_CONTEXT_BEFORE_OPEN = (ExperimentSetContext.STATE_INSTANCE_SET_CONTEXT_CREATED + 1);
  /** a instance set context entered the after-open state */
  private static final int STATE_INSTANCE_SET_CONTEXT_AFTER_OPEN = (ExperimentSetContext.STATE_INSTANCE_SET_CONTEXT_BEFORE_OPEN + 1);
  /** a instance set context entered the closed state */
  private static final int STATE_INSTANCE_SET_CONTEXT_CLOSED = (ExperimentSetContext.STATE_INSTANCE_SET_CONTEXT_AFTER_OPEN + 1);
  /** a feature set context entered the closed state */
  private static final int STATE_FEATURE_SET_CONTEXT_OBTAINED = (ExperimentSetContext.STATE_INSTANCE_SET_CONTEXT_CLOSED + 1);
  /** a instance set context entered the closed state */
  private static final int STATE_INSTANCE_SET_OBTAINED = (ExperimentSetContext.STATE_FEATURE_SET_CONTEXT_OBTAINED + 1);

  /** a experiment set context was created */
  private static final int STATE_EXPERIMENT_SET_CONTEXT_CREATED = (ExperimentSetContext.STATE_INSTANCE_SET_OBTAINED + 1);
  /** a experiment set context entered the before-open state */
  private static final int STATE_EXPERIMENT_SET_CONTEXT_BEFORE_OPEN = (ExperimentSetContext.STATE_EXPERIMENT_SET_CONTEXT_CREATED + 1);
  /** a experiment set context entered the after-open state */
  private static final int STATE_EXPERIMENT_SET_CONTEXT_AFTER_OPEN = (ExperimentSetContext.STATE_EXPERIMENT_SET_CONTEXT_BEFORE_OPEN + 1);
  /** a experiment set context entered the closed state */
  private static final int STATE_EXPERIMENT_SET_CONTEXT_CLOSED = (ExperimentSetContext.STATE_EXPERIMENT_SET_CONTEXT_AFTER_OPEN + 1);
  /** a experiment set context has been obtained */
  private static final int STATE_EXPERIMENT_SET_OBTAINED = (ExperimentSetContext.STATE_EXPERIMENT_SET_CONTEXT_CLOSED + 1);
  /** a experiment set context has been taken */
  private static final int STATE_EXPERIMENT_SET_TAKEN = (ExperimentSetContext.STATE_EXPERIMENT_SET_OBTAINED + 1);

  /** the state names */
  private static final String[] STATE_NAMES;

  static {
    STATE_NAMES = new String[ExperimentSetContext.STATE_EXPERIMENT_SET_TAKEN + 1];

    ExperimentSetContext.STATE_NAMES[ExperimentSetContext.STATE_DIMENSION_SET_CONTEXT_CREATED] = "dimensionSetContextCreated"; //$NON-NLS-1$
    ExperimentSetContext.STATE_NAMES[ExperimentSetContext.STATE_DIMENSION_SET_CONTEXT_BEFORE_OPEN] = "dimensionSetContextBeforeOpen"; //$NON-NLS-1$
    ExperimentSetContext.STATE_NAMES[ExperimentSetContext.STATE_DIMENSION_SET_CONTEXT_AFTER_OPEN] = "dimensionSetContextAfterOpen"; //$NON-NLS-1$
    ExperimentSetContext.STATE_NAMES[ExperimentSetContext.STATE_DIMENSION_SET_CONTEXT_CLOSED] = "dimensionSetContextClosed"; //$NON-NLS-1$
    ExperimentSetContext.STATE_NAMES[ExperimentSetContext.STATE_DIMENSION_SET_OBTAINED] = "dimensionSetContextObtained"; //$NON-NLS-1$

    ExperimentSetContext.STATE_NAMES[ExperimentSetContext.STATE_INSTANCE_SET_CONTEXT_CREATED] = "instanceSetContextCreated"; //$NON-NLS-1$
    ExperimentSetContext.STATE_NAMES[ExperimentSetContext.STATE_INSTANCE_SET_CONTEXT_BEFORE_OPEN] = "instanceSetContextBeforeOpen"; //$NON-NLS-1$
    ExperimentSetContext.STATE_NAMES[ExperimentSetContext.STATE_INSTANCE_SET_CONTEXT_AFTER_OPEN] = "instanceSetContextAfterOpen"; //$NON-NLS-1$
    ExperimentSetContext.STATE_NAMES[ExperimentSetContext.STATE_INSTANCE_SET_CONTEXT_CLOSED] = "instanceContextClosed"; //$NON-NLS-1$
    ExperimentSetContext.STATE_NAMES[ExperimentSetContext.STATE_FEATURE_SET_CONTEXT_OBTAINED] = "featureSetContextObtained"; //$NON-NLS-1$
    ExperimentSetContext.STATE_NAMES[ExperimentSetContext.STATE_INSTANCE_SET_OBTAINED] = "instanceSetContextObtained"; //$NON-NLS-1$

    ExperimentSetContext.STATE_NAMES[ExperimentSetContext.STATE_EXPERIMENT_SET_CONTEXT_CREATED] = "experimentSetContextCreated"; //$NON-NLS-1$
    ExperimentSetContext.STATE_NAMES[ExperimentSetContext.STATE_EXPERIMENT_SET_CONTEXT_BEFORE_OPEN] = "experimentSetContextBeforeOpen"; //$NON-NLS-1$
    ExperimentSetContext.STATE_NAMES[ExperimentSetContext.STATE_EXPERIMENT_SET_CONTEXT_AFTER_OPEN] = "experimentSetContextAfterOpen"; //$NON-NLS-1$
    ExperimentSetContext.STATE_NAMES[ExperimentSetContext.STATE_EXPERIMENT_SET_CONTEXT_CLOSED] = "experimentSetContextClosed"; //$NON-NLS-1$
    ExperimentSetContext.STATE_NAMES[ExperimentSetContext.STATE_EXPERIMENT_SET_OBTAINED] = "experimentSetContextObtained"; //$NON-NLS-1$
    ExperimentSetContext.STATE_NAMES[ExperimentSetContext.STATE_EXPERIMENT_SET_TAKEN] = "experimentSetTaken"; //$NON-NLS-1$
  }

  /** the dimension set */
  private volatile DimensionSet m_dims;

  /** the instances */
  private volatile InstanceSet m_instances;

  /** the problem features */
  private volatile FeatureSet m_features;

  /** the result */
  private volatile ExperimentSet m_result;

  /** the set of objects */
  private volatile HashMap<Object, Object> m_normal;

  /** the dimension set context */
  private volatile _DimensionSetContext m_dsc;

  /** the experiment set context */
  private volatile _ExperimentSetContext m_esc;

  /** the instance set context */
  private volatile _InstanceSetContext m_isc;

  /** create */
  public ExperimentSetContext() {
    super(null);

    this.m_normal = new HashMap<>();

    this.m_normal.put(Parameter.PARAMETER_ALGORITHM,
        Parameter.PARAMETER_ALGORITHM);
    this.m_normal.put(Parameter.PARAMETER_ALGORITHM_NAME,
        Parameter.PARAMETER_ALGORITHM_NAME);
    this.m_normal.put(Parameter.PARAMETER_ALGORITHM_CLASS,
        Parameter.PARAMETER_ALGORITHM_CLASS);
    this.m_normal.put(Parameter.PARAMETER_INITIALIZER,
        Parameter.PARAMETER_INITIALIZER);
    this.m_normal.put(Parameter.PARAMETER_INITIALIZER_NAME,
        Parameter.PARAMETER_INITIALIZER_NAME);
    this.m_normal.put(Parameter.PARAMETER_INITIALIZER_CLASS,
        Parameter.PARAMETER_INITIALIZER_CLASS);
    this.m_normal.put(_PropertyValueGeneralized.NAME,
        _PropertyValueGeneralized.NAME);
    this.m_normal.put(_PropertyValueGeneralized.INSTANCE,
        _PropertyValueGeneralized.INSTANCE);
    this.m_normal.put(_PropertyValueUnspecified.NAME,
        _PropertyValueUnspecified.NAME);
    this.m_normal.put(_PropertyValueUnspecified.INSTANCE,
        _PropertyValueUnspecified.INSTANCE);

    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmStateAppendName(final int state,
      final MemoryTextOutput sb) {
    if ((state > _FSM.STATE_OPEN)
        && (state < ExperimentSetContext.STATE_NAMES.length)) {
      sb.append(ExperimentSetContext.STATE_NAMES[state]);
    } else {
      super.fsmStateAppendName(state, sb);
    }
  }

  /**
   * Create the dimension set.
   * 
   * @return the dimension set context
   */
  private synchronized final _DimensionSetContext __getDimensionSetContext() {
    if (this.m_dsc != null) {
      return this.m_dsc;
    }
    this.fsmStateAssertAndSet(_FSM.STATE_OPEN,
        ExperimentSetContext.STATE_DIMENSION_SET_CONTEXT_CREATED);
    return (this.m_dsc = new _DimensionSetContext(this));
  }

  /**
   * Create the dimension context
   * 
   * @return the dimension context
   */
  public final DimensionContext createDimension() {
    return this.__getDimensionSetContext()._createDimension();
  }

  /** close and finalize the dimension set context */
  private synchronized final void __closeDimensionSetContext() {
    if (this.m_dsc != null) {
      try {
        this.m_dsc.close();
      } finally {
        this.m_dsc = null;
      }
    }
  }

  /**
   * Create the instance set.
   * 
   * @return the instance set context
   */
  private synchronized final _InstanceSetContext __getInstanceSetContext() {
    if (this.m_isc != null) {
      return this.m_isc;
    }
    this.__closeDimensionSetContext();
    this.fsmStateAssertAndSet(
        ExperimentSetContext.STATE_DIMENSION_SET_OBTAINED,
        ExperimentSetContext.STATE_INSTANCE_SET_CONTEXT_CREATED);
    return (this.m_isc = new _InstanceSetContext(this));
  }

  /**
   * Define an instance feature with a given name and description
   * 
   * @param name
   *          the feature name
   * @param desc
   *          the feature's description
   */
  public final void declareFeature(final String name, final String desc) {
    this.__getInstanceSetContext()._declareFeature(name, desc);
  }

  /**
   * Create the instance context
   * 
   * @return the instance context
   */
  public final InstanceContext createInstance() {
    return this.__getInstanceSetContext()._createInstance();
  }

  /** close and finalize the instance set context */
  private synchronized final void __closeInstanceSetContext() {
    if (this.m_isc != null) {
      try {
        this.m_isc.close();
      } finally {
        this.m_isc = null;
      }
    }
  }

  /**
   * Create the experiment set.
   * 
   * @return the experiment set context
   */
  private synchronized final _ExperimentSetContext __getExperimentSetContext() {
    if (this.m_esc != null) {
      return this.m_esc;
    }
    this.__closeInstanceSetContext();
    this.fsmStateAssertAndSet(
        ExperimentSetContext.STATE_INSTANCE_SET_OBTAINED,
        ExperimentSetContext.STATE_EXPERIMENT_SET_CONTEXT_CREATED);
    return (this.m_esc = new _ExperimentSetContext(this));
  }

  /**
   * Define a parameter with a given name and description
   * 
   * @param name
   *          the parameter name
   * @param desc
   *          the parameter's description
   */
  public final void declareParameter(final String name, final String desc) {
    this.__getExperimentSetContext()._declareParameter(name, desc);
  }

  /**
   * Create the experiment context
   * 
   * @return the experiment context
   */
  public final ExperimentContext createExperiment() {
    return this.__getExperimentSetContext()._createExperiment();
  }

  /** close and finalize the experiment set context */
  private synchronized final void __closeExperimentSetContext() {
    if (this.m_esc != null) {
      try {
        this.m_esc.close();
      } finally {
        this.m_esc = null;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (child instanceof _DimensionSetContext) {
      this.fsmStateAssertAndSet(
          ExperimentSetContext.STATE_DIMENSION_SET_CONTEXT_CREATED,
          ExperimentSetContext.STATE_DIMENSION_SET_CONTEXT_BEFORE_OPEN);
    } else {
      if (child instanceof _InstanceSetContext) {
        this.fsmStateAssertAndSet(
            ExperimentSetContext.STATE_INSTANCE_SET_CONTEXT_CREATED,
            ExperimentSetContext.STATE_INSTANCE_SET_CONTEXT_BEFORE_OPEN);
      } else {
        if (child instanceof _ExperimentSetContext) {
          this.fsmStateAssertAndSet(
              ExperimentSetContext.STATE_EXPERIMENT_SET_CONTEXT_CREATED,
              ExperimentSetContext.STATE_EXPERIMENT_SET_CONTEXT_BEFORE_OPEN);
        } else {
          this.throwChildNotAllowed(child);
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (child instanceof _DimensionSetContext) {
      this.fsmStateAssertAndSet(
          ExperimentSetContext.STATE_DIMENSION_SET_CONTEXT_BEFORE_OPEN,
          ExperimentSetContext.STATE_DIMENSION_SET_CONTEXT_AFTER_OPEN);
    } else {
      if (child instanceof _InstanceSetContext) {
        this.fsmStateAssertAndSet(
            ExperimentSetContext.STATE_INSTANCE_SET_CONTEXT_BEFORE_OPEN,
            ExperimentSetContext.STATE_INSTANCE_SET_CONTEXT_AFTER_OPEN);
      } else {
        if (child instanceof _ExperimentSetContext) {
          this.fsmStateAssertAndSet(
              ExperimentSetContext.STATE_EXPERIMENT_SET_CONTEXT_BEFORE_OPEN,
              ExperimentSetContext.STATE_EXPERIMENT_SET_CONTEXT_AFTER_OPEN);
        } else {
          this.throwChildNotAllowed(child);
        }
      }
    }
  }

  /**
   * Obtain the created dimension set. This method is only available after
   * the {@link #createDimension() dimension set creation} has been
   * completed and will throw a {@link java.lang.IllegalStateException}
   * before that.
   * 
   * @return the created dimension set
   */
  public synchronized final DimensionSet getDimensionSet() {
    this.__closeDimensionSetContext();
    this.fsmStateAssert(EComparison.GREATER_OR_EQUAL,
        ExperimentSetContext.STATE_DIMENSION_SET_OBTAINED);
    return this.m_dims;
  }

  /**
   * Obtain the created instance set. This method is only available after
   * the {@link #createInstance() instance set creation} has been completed
   * and will throw a {@link java.lang.IllegalStateException} before that.
   * 
   * @return the created instance set
   */
  public synchronized final InstanceSet getInstanceSet() {
    this.__closeInstanceSetContext();
    this.fsmStateAssert(EComparison.GREATER_OR_EQUAL,
        ExperimentSetContext.STATE_INSTANCE_SET_OBTAINED);
    return this.m_instances;
  }

  /**
   * Obtain the created feature set. This method is only available after
   * the {@link #createInstance() instance set creation} has been completed
   * and will throw a {@link java.lang.IllegalStateException} before that.
   * 
   * @return the created feature set
   */
  public synchronized final FeatureSet getFeatureSet() {
    this.__closeInstanceSetContext();
    this.fsmStateAssert(EComparison.GREATER_OR_EQUAL,
        ExperimentSetContext.STATE_FEATURE_SET_CONTEXT_OBTAINED);
    return this.m_features;
  }

  /**
   * Obtain the created experiment set
   * 
   * @return the created experiment set
   */
  public synchronized final ExperimentSet getResult() {
    final ExperimentSet es;

    this.__closeExperimentSetContext();

    this.fsmStateAssertAndSet(EComparison.GREATER_OR_EQUAL,
        ExperimentSetContext.STATE_EXPERIMENT_SET_OBTAINED,
        ExperimentSetContext.STATE_EXPERIMENT_SET_TAKEN);

    es = this.m_result;
    this.__free();
    if (es == null) {
      throw new IllegalStateException(//
          TextUtils.className(ExperimentSet.class) + //
              " instance cannot be null, but strangely is?!"); //$NON-NLS-1$
    }

    return es;
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {
    final _InstanceSetContext isc;

    super.afterChildClosed(child);

    if (child instanceof _DimensionSetContext) {
      this.fsmStateAssertAndSet(
          ExperimentSetContext.STATE_DIMENSION_SET_CONTEXT_AFTER_OPEN,
          ExperimentSetContext.STATE_DIMENSION_SET_CONTEXT_CLOSED);
      this.m_dims = ((_DimensionSetContext) child)._compile();
      this.fsmStateAssertAndSet(
          ExperimentSetContext.STATE_DIMENSION_SET_CONTEXT_CLOSED,
          ExperimentSetContext.STATE_DIMENSION_SET_OBTAINED);
    } else {
      if (child instanceof _InstanceSetContext) {
        this.fsmStateAssertAndSet(
            ExperimentSetContext.STATE_INSTANCE_SET_CONTEXT_AFTER_OPEN,
            ExperimentSetContext.STATE_INSTANCE_SET_CONTEXT_CLOSED);

        isc = ((_InstanceSetContext) child);

        this.m_features = isc._getFeatureSet();
        if (this.m_features == null) {
          throw new IllegalStateException(//
              "Could not compile instance feature set."); //$NON-NLS-1$
        }

        this.fsmStateAssertAndSet(
            ExperimentSetContext.STATE_INSTANCE_SET_CONTEXT_CLOSED,
            ExperimentSetContext.STATE_FEATURE_SET_CONTEXT_OBTAINED);

        this.m_instances = isc._compile();
        this.fsmStateAssertAndSet(
            ExperimentSetContext.STATE_FEATURE_SET_CONTEXT_OBTAINED,
            ExperimentSetContext.STATE_INSTANCE_SET_OBTAINED);

      } else {
        if (child instanceof _ExperimentSetContext) {
          this.fsmStateAssertAndSet(
              ExperimentSetContext.STATE_EXPERIMENT_SET_CONTEXT_AFTER_OPEN,
              ExperimentSetContext.STATE_EXPERIMENT_SET_CONTEXT_CLOSED);

          this.m_result = ((_ExperimentSetContext) child)._compile();
          this.m_dims = null;
          this.m_features = null;
          this.m_instances = null;
          this.m_normal = null;

          this.fsmStateAssertAndSet(
              ExperimentSetContext.STATE_EXPERIMENT_SET_CONTEXT_CLOSED,
              ExperimentSetContext.STATE_EXPERIMENT_SET_OBTAINED);
        } else {
          this.throwChildNotAllowed(child);
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  protected synchronized final <T> T doNormalizePersistently(final T in) {
    final HashMap<Object, Object> normal;
    final Object b, key;

    key = HashUtils.hashKey(in);

    this.fsmStateAssert(EComparison.LESS_OR_EQUAL,
        ExperimentSetContext.STATE_EXPERIMENT_SET_CONTEXT_CLOSED);

    normal = this.m_normal;
    if (normal == null) {
      throw new IllegalStateException(((//
          "Cannot normalize object '" + in) + '\'') + '.'); //$NON-NLS-1$
    }

    b = normal.get(key);
    if (b != null) {
      return ((T) (b));
    }
    normal.put(key, in);

    return in;
  }

  /** free all allocated data structures */
  private final void __free() {
    this.m_dsc = null;
    this.m_isc = null;
    this.m_esc = null;
    this.m_normal = null;
    this.m_dims = null;
    this.m_features = null;
    this.m_instances = null;
    this.m_result = null;
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final Runtime rt;
    RuntimeException error;
    long oldFm, newFm;
    int times;

    error = null;
    try {
      this.__free();

      this.fsmStateAssert(EComparison.GREATER_OR_EQUAL,
          ExperimentSetContext.STATE_EXPERIMENT_SET_OBTAINED);

      rt = Runtime.getRuntime();

      newFm = rt.freeMemory();
      times = 4;
      do {
        oldFm = newFm;
        Thread.yield();
        rt.gc();
        Thread.yield();
        newFm = rt.freeMemory();
      } while (((--times) >= 0) || (newFm < oldFm));

    } catch (final RuntimeException a) {
      error = a;
    } finally {
      try {
        super.onClose();
      } catch (final RuntimeException b) {
        if (error == null) {
          error = b;
        } else {
          error.addSuppressed(b);
        }
      }
    }
    if (error != null) {
      throw error;
    }
  }
}
