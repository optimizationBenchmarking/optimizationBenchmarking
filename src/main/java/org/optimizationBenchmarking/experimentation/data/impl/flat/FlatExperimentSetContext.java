package org.optimizationBenchmarking.experimentation.data.impl.flat;

import java.util.Arrays;

import org.optimizationBenchmarking.experimentation.data.impl.ref.DataPoint;
import org.optimizationBenchmarking.experimentation.data.impl.ref.DimensionContext;
import org.optimizationBenchmarking.experimentation.data.impl.ref.DimensionSet;
import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentContext;
import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.data.impl.ref.FeatureSet;
import org.optimizationBenchmarking.experimentation.data.impl.ref.Instance;
import org.optimizationBenchmarking.experimentation.data.impl.ref.InstanceContext;
import org.optimizationBenchmarking.experimentation.data.impl.ref.InstanceRunsContext;
import org.optimizationBenchmarking.experimentation.data.impl.ref.InstanceSet;
import org.optimizationBenchmarking.experimentation.data.impl.ref.RunContext;
import org.optimizationBenchmarking.experimentation.data.spec.EDimensionDirection;
import org.optimizationBenchmarking.experimentation.data.spec.EDimensionType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * This class wraps the experiment data building capabilities into a single
 * object the whole parser state. In other words, it flattens the whole
 * hierarchical experiment data API. It must be used strictly sequentially,
 * as opposed to the parallel hierarchical API.
 */
public class FlatExperimentSetContext extends
    AbstractFlatExperimentSetContext {

  /** we are in the experiment set */
  private static final int MODE_EXPERIMENT_SET = 0;
  /** we are in the dimension */
  private static final int MODE_DIMENSION = (FlatExperimentSetContext.MODE_EXPERIMENT_SET + 1);
  /** we are in the instance */
  private static final int MODE_INSTANCE = (FlatExperimentSetContext.MODE_DIMENSION + 1);
  /** we are in the experiment */
  private static final int MODE_EXPERIMENT = (FlatExperimentSetContext.MODE_INSTANCE + 1);
  /** we are in the runs */
  private static final int MODE_RUNS = (FlatExperimentSetContext.MODE_EXPERIMENT + 1);
  /** we are in a run */
  private static final int MODE_RUN = (FlatExperimentSetContext.MODE_RUNS + 1);

  /** the mode names */
  private static final String[] MODE_NAMES = new String[FlatExperimentSetContext.MODE_RUN + 1];

  static {
    FlatExperimentSetContext.MODE_NAMES[FlatExperimentSetContext.MODE_EXPERIMENT_SET] = "the root experiment set"; //$NON-NLS-1$
    FlatExperimentSetContext.MODE_NAMES[FlatExperimentSetContext.MODE_DIMENSION] = "a dimension"; //$NON-NLS-1$
    FlatExperimentSetContext.MODE_NAMES[FlatExperimentSetContext.MODE_INSTANCE] = "an instance"; //$NON-NLS-1$
    FlatExperimentSetContext.MODE_NAMES[FlatExperimentSetContext.MODE_EXPERIMENT] = "an experiment"; //$NON-NLS-1$
    FlatExperimentSetContext.MODE_NAMES[FlatExperimentSetContext.MODE_RUNS] = "a run set"; //$NON-NLS-1$
    FlatExperimentSetContext.MODE_NAMES[FlatExperimentSetContext.MODE_RUN] = "a run"; //$NON-NLS-1$
  }

  /** the hierarchical fsm stack */
  private ExperimentSetContext m_main;

  /** the current mode */
  private int m_mode;

  /** the dimension context */
  private DimensionContext m_dimension;

  /** the dimension name, if any */
  private String m_dimensionName;

  /** the instance context */
  private InstanceContext m_instance;

  /** the instance name, if any */
  private String m_instanceName;

  /** the experiment context */
  private ExperimentContext m_experiment;

  /** the experiment name, if any */
  private String m_experimentName;

  /** the instance runs context */
  private InstanceRunsContext m_runs;

  /** the runs name */
  private String m_runsName;

  /** the run context */
  private RunContext m_run;

  /** Create the experiment data parser */
  @SuppressWarnings("resource")
  public FlatExperimentSetContext() {
    this(new ExperimentSetContext());
  }

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

    this.m_main = context;
    this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT_SET;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "incomplete-switch", "fallthrough" })
  public synchronized final void flush() {

    switch (this.m_mode) {
      case MODE_EXPERIMENT_SET: {
        break;
      }

      case MODE_DIMENSION: {
        try {
          this.m_dimension.close();
        } catch (final Throwable error) {
          throw new IllegalStateException(this.__errorLocation(
              "Error while closing dimension during flushing.", //$NON-NLS-1$
              true), error);
        } finally {
          this.m_dimension = null;
          this.m_dimensionName = null;
          this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT_SET;
        }
        break;
      }

      case MODE_INSTANCE: {
        try {
          this.m_instance.close();
        } catch (final Throwable error) {
          throw new IllegalStateException(this.__errorLocation(
              "Error while closing instance during flushing.", //$NON-NLS-1$
              true), error);
        } finally {
          this.m_instance = null;
          this.m_instanceName = null;
          this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT_SET;
        }
        break;
      }

      case MODE_RUN: {
        try {
          this.m_run.close();
        } catch (final Throwable error) {
          throw new IllegalStateException(this.__errorLocation(
              "Error while closing run during flushing.", //$NON-NLS-1$
              true), error);
        } finally {
          this.m_run = null;
          this.m_mode = FlatExperimentSetContext.MODE_RUNS;
        }
        // no break: fall through!
      }

      case MODE_RUNS: {
        try {
          this.m_runs.close();
        } catch (final Throwable error) {
          throw new IllegalStateException(this.__errorLocation(
              "Error while closing run set during flushing.", //$NON-NLS-1$
              true), error);
        } finally {
          this.m_runs = null;
          this.m_runsName = null;
          this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT;
        }
        // no break: fall through!
      }

      case MODE_EXPERIMENT: {
        try {
          this.m_experiment.close();
        } catch (final Throwable error) {
          throw new IllegalStateException(this.__errorLocation(
              "Error while closing experiment during flushing.", //$NON-NLS-1$
              true), error);
        } finally {
          this.m_experiment = null;
          this.m_experimentName = null;
          this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT_SET;
        }
        break;
      }
    }
  }

  /**
   * Get a text describing the error location
   *
   * @param error
   *          the error
   * @param hasCause
   *          do we know the cause of the error?
   * @return a text describing the error location
   */
  @SuppressWarnings({ "incomplete-switch", "fallthrough" })
  private final String __errorLocation(final String error,
      final boolean hasCause) {
    final MemoryTextOutput mto;

    if (error != null) {
      mto = new MemoryTextOutput(error.length() + 512);
      mto.append(error);
      mto.append(" This");//$NON-NLS-1$
    } else {
      mto = new MemoryTextOutput(512);
      mto.append('A');
    }
    mto.append(" problem occurred while setting up "); //$NON-NLS-1$
    mto.append(FlatExperimentSetContext.MODE_NAMES[this.m_mode]);

    switch (this.m_mode) {
      case MODE_DIMENSION: {
        if (this.m_dimensionName != null) {
          mto.append(" with name '");//$NON-NLS-1$
          mto.append(this.m_dimensionName);
          mto.append('\'');
        }
        break;
      }

      case MODE_INSTANCE: {
        if (this.m_instanceName != null) {
          mto.append(" with name '");//$NON-NLS-1$
          mto.append(this.m_instanceName);
          mto.append('\'');
        }
        break;
      }

      case MODE_RUN:
      case MODE_RUNS: {
        if (this.m_runsName != null) {
          mto.append(" for instance '");//$NON-NLS-1$
          mto.append(this.m_runsName);
          mto.append('\'');
        }
        // no break: fall through
      }
      case MODE_EXPERIMENT: {
        if (this.m_experimentName != null) {
          if (this.m_mode == FlatExperimentSetContext.MODE_RUNS) {
            mto.append(" for the experiment with name '");//$NON-NLS-1$
          } else {
            mto.append(" with name '");//$NON-NLS-1$
          }
          mto.append(this.m_experimentName);
          mto.append('\'');
        }
        break;
      }
    }

    if (hasCause) {
      mto.append(//
      ". More information of what has led to the error is given in the causing exception.");//$NON-NLS-1$
    } else {
      mto.append('.');
    }

    return mto.toString();
  }

  /**
   * Ensure that we are in a dimension context
   *
   * @param forceNew
   *          should we enforce the creation of a new dimension?
   * @return the dimension context
   */
  private final DimensionContext __dimensionEnsure(final boolean forceNew) {
    switch (this.m_mode) {

      case MODE_EXPERIMENT_SET: {
        break;
      }

      case MODE_DIMENSION: {
        if (forceNew) {
          try {
            this.m_dimension.close();
          } catch (final Throwable error) {
            throw new IllegalStateException(
                this.__errorLocation(//
                    "Error while trying to close a dimension context to begin a new one.",//$NON-NLS-1$
                    true), error);
          } finally {
            this.m_dimension = null;
            this.m_dimensionName = null;
            this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT_SET;
          }
          break;
        }
        return this.m_dimension;
      }

      default: {
        throw new IllegalStateException(this.__errorLocation(//
            "Cannot begin a new dimension here.",//$NON-NLS-1$
            false));
      }
    }

    try {
      this.m_dimension = this.m_main.createDimension();
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          "Error while beginning a new dimension.",//$NON-NLS-1$
          true), error);
    }
    this.m_mode = FlatExperimentSetContext.MODE_DIMENSION;
    return this.m_dimension;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void dimensionBegin(final boolean forceNew) {
    this.__dimensionEnsure(forceNew);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void dimensionEnd() {
    switch (this.m_mode) {
      case MODE_EXPERIMENT_SET: {
        return;
      }
      case MODE_DIMENSION: {
        try {
          this.m_dimension.close();
        } catch (final Throwable error) {
          throw new IllegalStateException(this.__errorLocation(//
              "Error while closing dimension context.",//$NON-NLS-1$
              true), error);
        } finally {
          this.m_dimension = null;
          this.m_dimensionName = null;
          this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT_SET;
        }
        return;
      }

      default: {
        throw new IllegalStateException(
            this.__errorLocation(//
                "Cannot close a dimension context at the current point in time.",//$NON-NLS-1$
                false));
      }
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public synchronized final void dimensionSetName(final String name) {
    final DimensionContext context;

    context = this.__dimensionEnsure(false);
    try {
      context.setName(name);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          (((("Error while setting dimension name '") //$NON-NLS-1$
          + name) + '\'') + '.'), true), error);
    }
    this.m_dimensionName = name;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public synchronized final void dimensionSetDescription(
      final String description) {
    final DimensionContext context;

    context = this.__dimensionEnsure(false);
    try {
      context.setDescription(description);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          (((("Error while setting dimension description '") //$NON-NLS-1$
          + description) + '\'') + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public synchronized final void dimensionAddDescription(
      final String description) {
    final DimensionContext context;

    context = this.__dimensionEnsure(false);
    try {
      context.addDescription(description);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          (((("Error while adding dimension description '") //$NON-NLS-1$
          + description) + '\'') + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public synchronized final void dimensionSetDirection(
      final String direction) {
    final DimensionContext context;

    context = this.__dimensionEnsure(false);
    try {
      context.setDirection(direction);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          (((("Error while setting dimension direction string '") //$NON-NLS-1$
          + direction) + '\'') + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void dimensionSetDirection(
      final EDimensionDirection direction) {
    final DimensionContext context;

    context = this.__dimensionEnsure(false);
    try {
      context.setDirection(direction);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          (((("Error while setting dimension direction '") //$NON-NLS-1$
          + direction) + '\'') + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void dimensionSetParser(
      final Class<? extends NumberParser<?>> parserClass,
      final Number lowerBound, final Number upperBound) {
    final DimensionContext context;

    context = this.__dimensionEnsure(false);
    try {
      context.setParser(parserClass, lowerBound, upperBound);
    } catch (final Throwable error) {
      throw new IllegalStateException(
          this.__errorLocation(//
              ((((((("Error while setting dimension parser class ") //$NON-NLS-1$
              + TextUtils.className(parserClass)) + " and lower bound ") + lowerBound) + //$NON-NLS-1$
              " and upper bound ") + upperBound) + '.'), //$NON-NLS-1$
              true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void dimensionSetParser(
      final NumberParser<?> parser) {
    final DimensionContext context;

    context = this.__dimensionEnsure(false);
    try {
      context.setParser(parser);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          (((("Error while setting dimension parser '") //$NON-NLS-1$
          + parser) + '\'') + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void dimensionSetParser(
      final String parserClass, final String lowerBound,
      final String upperBound) {
    final DimensionContext context;

    context = this.__dimensionEnsure(false);
    try {
      context.setParser(parserClass, lowerBound, upperBound);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          ((((((("Error while setting dimension parser strings '") //$NON-NLS-1$
              + parserClass + "' and lower bound '") + lowerBound) + //$NON-NLS-1$
          " and upper bound '") + upperBound) + '\'') + '.'), //$NON-NLS-1$
          true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void dimensionSetParser(final String parserDesc) {
    final DimensionContext context;

    context = this.__dimensionEnsure(false);
    try {
      context.setParser(parserDesc);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          (((("Error while setting dimension parser string '") //$NON-NLS-1$
          + parserDesc) + '\'') + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void dimensionSetType(final EDimensionType type) {
    final DimensionContext context;

    context = this.__dimensionEnsure(false);
    try {
      context.setType(type);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          ((("Error while setting dimension type ") //$NON-NLS-1$
          + type) + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void dimensionSetType(final String type) {
    final DimensionContext context;

    context = this.__dimensionEnsure(false);
    try {
      context.setType(type);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          (((("Error while setting dimension type string '") //$NON-NLS-1$
          + type) + '\'') + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final DimensionSet getDimensionSet() {
    try {
      return this.m_main.getDimensionSet();
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          "Error while trying to obtain the set of all dimensions.", //$NON-NLS-1$
          true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void featureDeclare(final String name,
      final String desc) {

    switch (this.m_mode) {
      case MODE_EXPERIMENT_SET: {
        try {
          this.m_main.declareFeature(name, desc);
        } catch (final Throwable error) {
          throw new IllegalStateException(this.__errorLocation(//
              ((((("Error while declaring feature '") //$NON-NLS-1$
              + name) + "' with description '") + desc) + //$NON-NLS-1$
              "' to the experiment set context."), //$NON-NLS-1$
              true), error);
        }
        return;
      }
      case MODE_INSTANCE: {

        try {
          this.m_instance.declareFeature(name, desc);
        } catch (final Throwable error) {
          throw new IllegalStateException(this.__errorLocation(//
              ((((("Error while declaring feature '") //$NON-NLS-1$
              + name) + "' with description '") + desc) + //$NON-NLS-1$
              "' to the instance context."), //$NON-NLS-1$
              true), error);
        }
        return;
      }

      default: {
        throw new IllegalStateException(this.__errorLocation(((((//
            "Cannot declare feature '" + //$NON-NLS-1$
            name) + "' with description '") + //$NON-NLS-1$
            desc) + "' now."), false)); //$NON-NLS-1$
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void parameterDeclare(final String name,
      final String desc) {

    switch (this.m_mode) {
      case MODE_EXPERIMENT_SET: {
        try {
          this.m_main.declareParameter(name, desc);
        } catch (final Throwable error) {
          throw new IllegalStateException(this.__errorLocation(//
              ((((("Error while declaring parameter '") //$NON-NLS-1$
              + name) + "' with description '") + desc) + //$NON-NLS-1$
              "' to the experiment set context."), //$NON-NLS-1$
              true), error);
        }
        return;
      }

      case MODE_EXPERIMENT: {
        try {
          this.m_experiment.declareParameter(name, desc);
        } catch (final Throwable error) {
          throw new IllegalStateException(this.__errorLocation(//
              ((((("Error while declaring parameter '") //$NON-NLS-1$
              + name) + "' with description '") + desc) + //$NON-NLS-1$
              "' to the experiment context."), true),//$NON-NLS-1$
              error);
        }
        return;
      }

      case MODE_RUNS: {
        try {
          this.m_runs.declareParameter(name, desc);
        } catch (final Throwable error) {
          throw new IllegalStateException(this.__errorLocation(//
              ((((("Error while declaring parameter '") //$NON-NLS-1$
              + name) + "' with description '") + desc) + //$NON-NLS-1$
              "' to the run set context."), true),//$NON-NLS-1$
              error);
        }
        return;
      }
      case MODE_RUN: {
        try {
          this.m_run.declareParameter(name, desc);
        } catch (final Throwable error) {
          throw new IllegalStateException(this.__errorLocation(//
              ((((("Error while declaring parameter '") //$NON-NLS-1$
              + name) + "' with description '") + desc) + //$NON-NLS-1$
              "' to the run context."), true),//$NON-NLS-1$
              error);
        }
        return;
      }

      default: {
        throw new IllegalStateException(this.__errorLocation(((((//
            "Cannot declare parameter '" + //$NON-NLS-1$
            name) + "' with description '") + //$NON-NLS-1$
            desc) + "' now."), false)); //$NON-NLS-1$
      }
    }
  }

  /**
   * Ensure that we are in a instance context
   *
   * @param forceNew
   *          should we enforce the creation of a new instance?
   * @return the instance context
   */
  private final InstanceContext __instanceEnsure(final boolean forceNew) {

    switch (this.m_mode) {
      case MODE_EXPERIMENT_SET: {
        break;
      }

      case MODE_DIMENSION: {
        try {
          this.m_dimension.close();
        } catch (final Throwable error) {
          throw new IllegalStateException(
              this.__errorLocation(//
                  "Error while trying to close a dimension context to begin a new instance.",//$NON-NLS-1$
                  true), error);
        } finally {
          this.m_dimension = null;
          this.m_dimensionName = null;
          this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT_SET;
        }
        break;
      }

      case MODE_INSTANCE: {
        if (forceNew) {
          try {
            this.m_instance.close();
          } catch (final Throwable error) {
            throw new IllegalStateException(
                this.__errorLocation(//
                    "Error while trying to close an instance context to begin a new one.",//$NON-NLS-1$
                    true), error);
          } finally {
            this.m_instance = null;
            this.m_instanceName = null;
            this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT_SET;
          }
          break;
        }
        return this.m_instance;
      }

      default: {
        throw new IllegalStateException(this.__errorLocation(//
            "Cannot begin a new instance here.",//$NON-NLS-1$
            false));
      }
    }

    try {
      this.m_instance = this.m_main.createInstance();
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          "Error while trying to begin a new instance context.",//$NON-NLS-1$
          true), error);
    }
    this.m_mode = FlatExperimentSetContext.MODE_INSTANCE;
    return this.m_instance;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void instanceBegin(final boolean forceNew) {
    this.__instanceEnsure(forceNew);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void instanceEnd() {
    switch (this.m_mode) {
      case MODE_EXPERIMENT_SET: {
        return;
      }
      case MODE_INSTANCE: {
        try {
          this.m_instance.close();
        } catch (final Throwable error) {
          throw new IllegalStateException(this.__errorLocation(//
              "Error while closing instance context.",//$NON-NLS-1$
              true), error);
        } finally {
          this.m_instance = null;
          this.m_instanceName = null;
          this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT_SET;
        }
        return;
      }
      default: {

        throw new IllegalStateException(this.__errorLocation(//
            "Cannot close instance at the current point in time.", //$NON-NLS-1$
            false));
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void instanceSetName(final String name) {
    final InstanceContext context;

    context = this.__instanceEnsure(false);
    try {
      context.setName(name);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          (((("Error while setting instance name '") //$NON-NLS-1$
          + name) + '\'') + '.'), true), error);
    }
    this.m_instanceName = name;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void instanceSetDescription(
      final String description) {
    final InstanceContext context;

    context = this.__instanceEnsure(false);
    try {
      context.setDescription(description);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          (((("Error while setting instance description '") //$NON-NLS-1$
          + description) + '\'') + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void instanceAddDescription(
      final String description) {
    final InstanceContext context;

    context = this.__instanceEnsure(false);
    try {
      context.addDescription(description);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          (((("Error while adding instance description '") //$NON-NLS-1$
          + description) + '\'') + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public final synchronized void instanceSetFeatureValue(
      final String featureName, final Object featureValue) {
    final InstanceContext context;

    context = this.__instanceEnsure(false);
    try {
      context.setFeatureValue(featureName, featureValue);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation((((((//
          "Error while setting value '" //$NON-NLS-1$
          + featureValue) + //
          "' of the feature with name '") //$NON-NLS-1$
          + featureName) + '\'') + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public final synchronized void instanceSetFeatureValue(
      final String featureName, final Object featureValue,
      final String featureValueDescription) {
    final InstanceContext context;

    context = this.__instanceEnsure(false);
    try {
      context.setFeatureValue(featureName, featureValue,
          featureValueDescription);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation((((((((//
          "Error while setting value '" //$NON-NLS-1$
          + featureValue) + "' with description '") //$NON-NLS-1$
          + featureValueDescription) + //
          "' of the feature with name '") //$NON-NLS-1$
          + featureName) + '\'') + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public final synchronized void instanceSetFeatureValue(
      final String featureName, final String featureDescription,
      final Object featureValue, final String featureValueDescription) {
    final InstanceContext context;

    context = this.__instanceEnsure(false);
    try {
      context.setFeatureValue(featureName, featureDescription,
          featureValue, featureValueDescription);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation((((((((((//
          "Error while setting value '" //$NON-NLS-1$
          + featureValue) + "' with description '") //$NON-NLS-1$
          + featureValueDescription) + //
          "' of the feature with name '") //$NON-NLS-1$
          + featureName) + //
          "' with description '")//$NON-NLS-1$
          + featureDescription) + '\'') + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void instanceSetLowerBound(
      final IDimension dim, final Number bound) {
    final InstanceContext context;

    context = this.__instanceEnsure(false);
    try {
      context.setLowerBound(dim, bound);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation((((((//
          "Error while lower bound for dimension '" //$NON-NLS-1$
          + dim) + //
          "' to number '") //$NON-NLS-1$
          + bound) + '\'') + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void instanceSetLowerBound(final Object dim,
      final Object bound) {
    final InstanceContext context;

    context = this.__instanceEnsure(false);
    try {
      context.setLowerBound(dim, bound);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation((((((//
          "Error while lower bound for dimension object '" //$NON-NLS-1$
          + dim) + //
          "' to object '") //$NON-NLS-1$
          + bound) + '\'') + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void instanceSetUpperBound(
      final IDimension dim, final Number bound) {
    final InstanceContext context;

    context = this.__instanceEnsure(false);
    try {
      context.setUpperBound(dim, bound);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation((((((//
          "Error while upper bound for dimension '" //$NON-NLS-1$
          + dim) + //
          "' to number '") //$NON-NLS-1$
          + bound) + '\'') + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void instanceSetUpperBound(final Object dim,
      final Object bound) {
    final InstanceContext context;

    context = this.__instanceEnsure(false);
    try {
      context.setUpperBound(dim, bound);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation((((((//
          "Error while lower bound for dimension object '" //$NON-NLS-1$
          + dim) + //
          "' to object '") //$NON-NLS-1$
          + bound) + '\'') + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("incomplete-switch")
  public synchronized final InstanceSet getInstanceSet() {

    switch (this.m_mode) {
      case MODE_INSTANCE: {
        try {
          this.m_instance.close();
        } catch (final Throwable error) {
          throw new IllegalStateException(
              this.__errorLocation(//
                  "Error while trying to close an instance context in order to obtain the set of all instances.",//$NON-NLS-1$
                  true), error);
        } finally {
          this.m_instance = null;
          this.m_instanceName = null;
          this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT_SET;
        }
        break;
      }

      case MODE_DIMENSION: {
        throw new IllegalStateException(
            this.__errorLocation(//
                "Cannot obtain set of all instances while still creating dimensions: Instances can only be created after dimension creation has finished, and only after the instances have been created, you can obtain them.", //$NON-NLS-1$
                false));
      }
    }

    try {
      return this.m_main.getInstanceSet();
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          "Error while trying to obtain the set of all instances.", //$NON-NLS-1$
          true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("incomplete-switch")
  public synchronized final FeatureSet getFeatureSet() {

    switch (this.m_mode) {
      case MODE_INSTANCE: {
        try {
          this.m_instance.close();
        } catch (final Throwable error) {
          throw new IllegalStateException(
              this.__errorLocation(//
                  "Error while trying to close an instance context in order to obtain the set of all features.",//$NON-NLS-1$
                  true), error);
        } finally {
          this.m_instance = null;
          this.m_instanceName = null;
          this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT_SET;
        }
        break;
      }

      case MODE_DIMENSION: {
        throw new IllegalStateException(
            this.__errorLocation(//
                "Cannot obtain set of all (instance) features while still creating dimensions: Instances can only be created after dimension creation has finished, and only after the instances have been created, you can obtain their features.", //$NON-NLS-1$
                false));
      }
    }

    try {
      return this.m_main.getFeatureSet();
    } catch (final Throwable error) {
      throw new IllegalStateException(
          this.__errorLocation(//
              "Error while trying to obtain the set of all (instance) features.", //$NON-NLS-1$
              true), error);
    }
  }

  /**
   * Ensure that we are in an experiment context
   *
   * @param forceNew
   *          should we enforce the creation of a new experiment?
   * @return the instance context
   */
  @SuppressWarnings("fallthrough")
  private final ExperimentContext __experimentEnsure(final boolean forceNew) {

    switch (this.m_mode) {

      case MODE_EXPERIMENT_SET: {
        break;
      }

      case MODE_INSTANCE: {
        try {
          this.m_instance.close();
        } catch (final Throwable error) {
          throw new IllegalStateException(
              this.__errorLocation(//
                  "Error while trying to close an instance context to begin a new experiment.",//$NON-NLS-1$
                  true), error);
        } finally {
          this.m_instance = null;
          this.m_instanceName = null;
          this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT_SET;
        }
        break;
      }

      case MODE_RUN: {
        if (forceNew) {
          try {
            this.m_run.close();
          } catch (final Throwable error) {
            throw new IllegalStateException(
                this.__errorLocation(//
                    "Error while trying to close an run to begin a new experiment.",//$NON-NLS-1$
                    true), error);
          } finally {
            this.m_run = null;
            this.m_mode = FlatExperimentSetContext.MODE_RUNS;
          }
          // no break: fall through
        } else {
          throw new IllegalStateException(this.__errorLocation(//
              "Cannot lazily begin new experiment when inside a run.",//$NON-NLS-1$
              false));
        }
      }

      case MODE_RUNS: {
        if (forceNew) {
          try {
            this.m_runs.close();
          } catch (final Throwable error) {
            throw new IllegalStateException(
                this.__errorLocation(//
                    "Error while trying to close an run set to begin a new experiment.",//$NON-NLS-1$
                    true), error);
          } finally {
            this.m_runs = null;
            this.m_runsName = null;
            this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT;
          }
          // no break: fall through
        } else {
          throw new IllegalStateException(this.__errorLocation(//
              "Cannot lazily begin new experiment when inside a run set.",//$NON-NLS-1$
              false));
        }
      }

      case MODE_EXPERIMENT: {
        if (forceNew) {
          try {
            this.m_experiment.close();
          } catch (final Throwable error) {
            throw new IllegalStateException(
                this.__errorLocation(//
                    "Error while trying to close an experiment context to begin a new one.",//$NON-NLS-1$
                    true), error);
          } finally {
            this.m_experiment = null;
            this.m_experimentName = null;
            this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT;
          }
          break;
        }
        return this.m_experiment;
      }

      default: {
        throw new IllegalStateException(this.__errorLocation(//
            "Cannot begin a new experiment here.",//$NON-NLS-1$
            false));
      }
    }

    try {
      this.m_experiment = this.m_main.createExperiment();
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          "Error while trying to begin a new experiment context.",//$NON-NLS-1$
          true), error);
    }
    this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT;
    return this.m_experiment;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void experimentBegin(final boolean forceNew) {
    this.__experimentEnsure(forceNew);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("fallthrough")
  public synchronized final void experimentEnd() {

    switch (this.m_mode) {
      case MODE_EXPERIMENT_SET: {
        return;
      }

      case MODE_RUN: {
        try {
          this.m_run.close();
        } catch (final Throwable error) {
          throw new IllegalStateException(
              this.__errorLocation(//
                  "Error while closing run in order to close experiment context.",//$NON-NLS-1$
                  true), error);
        } finally {
          this.m_run = null;
          this.m_mode = FlatExperimentSetContext.MODE_RUNS;
        }
        // no break: fall through
      }

      case MODE_RUNS: {
        try {
          this.m_runs.close();
        } catch (final Throwable error) {
          throw new IllegalStateException(
              this.__errorLocation(//
                  "Error while closing run set in order to close experiment context.",//$NON-NLS-1$
                  true), error);
        } finally {
          this.m_runs = null;
          this.m_runsName = null;
          this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT;
        }
        // no break: fall through
      }

      case MODE_EXPERIMENT: {
        try {
          this.m_experiment.close();
        } catch (final Throwable error) {
          throw new IllegalStateException(this.__errorLocation(//
              "Error while closing experiment context.",//$NON-NLS-1$
              true), error);
        } finally {
          this.m_experiment = null;
          this.m_experimentName = null;
          this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT_SET;
        }
        return;
      }

      default: {
        throw new IllegalStateException(
            this.__errorLocation(//
                "Cannot close a experiment context at the current point in time.",//$NON-NLS-1$
                false));
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void experimentSetName(final String name) {
    final ExperimentContext context;

    context = this.__experimentEnsure(false);
    try {
      context.setName(name);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          (((("Error while setting experiment name '") //$NON-NLS-1$
          + name) + '\'') + '.'), true), error);
    }
    this.m_experimentName = name;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void experimentSetDescription(
      final String description) {
    final ExperimentContext context;

    context = this.__experimentEnsure(false);
    try {
      context.setDescription(description);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          (((("Error while setting experiment description '") //$NON-NLS-1$
          + description) + '\'') + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void experimentAddDescription(
      final String description) {
    final ExperimentContext context;

    context = this.__experimentEnsure(false);
    try {
      context.addDescription(description);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          (((("Error while adding experiment description '") //$NON-NLS-1$
          + description) + '\'') + '.'), true), error);
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "fallthrough", "resource" })
  public final synchronized void experimentSetParameterValue(
      final String parameterName, final Object parameterValue) {
    final ExperimentContext context;
    Throwable error;

    error = null;
    switch (this.m_mode) {

      case MODE_EXPERIMENT: {
        try {
          this.m_experiment.setParameterValue(parameterName,
              parameterValue);
          return;
        } catch (final Throwable t) {
          error = t;
        }
      }

      case MODE_RUNS: {
        try {
          this.m_runs.setParameterValue(parameterName, parameterValue);
          return;
        } catch (final Throwable t) {
          error = t;
        }
      }

      case MODE_RUN: {
        try {
          this.m_run.setParameterValue(parameterName, parameterValue);
          return;
        } catch (final Throwable t) {
          error = t;
        }
      }

      default: {
        context = this.__experimentEnsure(false);
        try {
          context.setParameterValue(parameterName, parameterValue);
          return;
        } catch (final Throwable t) {
          error = t;
        }
      }
    }

    throw new IllegalStateException(this.__errorLocation((((((//
        "Error while setting value '" //$NON-NLS-1$
        + parameterValue) + //
        "' of the parameter with name '") //$NON-NLS-1$
        + parameterName) + '\'') + '.'), true), error);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "fallthrough", "resource" })
  public final synchronized void experimentSetParameterValue(
      final String parameterName, final Object parameterValue,
      final String parameterValueDescription) {
    final ExperimentContext context;
    Throwable error;

    error = null;
    switch (this.m_mode) {

      case MODE_EXPERIMENT: {
        try {
          this.m_experiment.setParameterValue(parameterName,
              parameterValue, parameterValueDescription);
          return;
        } catch (final Throwable t) {
          error = t;
        }
      }

      case MODE_RUNS: {
        try {
          this.m_runs.setParameterValue(parameterName, parameterValue,
              parameterValueDescription);
          return;
        } catch (final Throwable t) {
          error = t;
        }
      }

      case MODE_RUN: {
        try {
          this.m_run.setParameterValue(parameterName, parameterValue,
              parameterValueDescription);
          return;
        } catch (final Throwable t) {
          error = t;
        }
      }

      default: {
        context = this.__experimentEnsure(false);
        try {
          context.setParameterValue(parameterName, parameterValue,
              parameterValueDescription);
          return;
        } catch (final Throwable t) {
          error = t;
        }
      }
    }

    throw new IllegalStateException(this.__errorLocation((((((((//
        "Error while setting value '" //$NON-NLS-1$
        + parameterValue) + "' with description '") //$NON-NLS-1$
        + parameterValueDescription) + //
        "' of the parameter with name '") //$NON-NLS-1$
        + parameterName) + '\'') + '.'), true), error);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "fallthrough", "resource" })
  public final synchronized void experimentSetParameterValue(
      final String parameterName, final String parameterDescription,
      final Object parameterValue, final String parameterValueDescription) {

    final ExperimentContext context;
    Throwable error;

    error = null;
    switch (this.m_mode) {

      case MODE_EXPERIMENT: {
        try {
          this.m_experiment.setParameterValue(parameterName,
              parameterDescription, parameterValue,
              parameterValueDescription);
          return;
        } catch (final Throwable t) {
          error = t;
        }
      }

      case MODE_RUNS: {
        try {
          this.m_runs.setParameterValue(parameterName,
              parameterDescription, parameterValue,
              parameterValueDescription);
          return;
        } catch (final Throwable t) {
          error = t;
        }
      }

      case MODE_RUN: {
        try {
          this.m_run.setParameterValue(parameterName,
              parameterDescription, parameterValue,
              parameterValueDescription);
          return;
        } catch (final Throwable t) {
          error = t;
        }
      }

      default: {
        context = this.__experimentEnsure(false);
        try {
          context.setParameterValue(parameterName, parameterDescription,
              parameterValue, parameterValueDescription);
          return;
        } catch (final Throwable t) {
          error = t;
        }
      }
    }

    throw new IllegalStateException(this.__errorLocation((((((((((//
        "Error while setting value '" //$NON-NLS-1$
        + parameterValue) + "' with description '") //$NON-NLS-1$
        + parameterValueDescription) + //
        "' of the parameter with name '") //$NON-NLS-1$
        + parameterName) + //
        "' with description '")//$NON-NLS-1$
        + parameterDescription) + '\'') + '.'), true), error);
  }

  /**
   * Ensure that we are in an instance runs context
   *
   * @param forceNew
   *          should we enforce the creation of a new instance runset?
   * @return the instance runs context
   */
  @SuppressWarnings("fallthrough")
  private final InstanceRunsContext __runsEnsure(final boolean forceNew) {

    switch (this.m_mode) {

      case MODE_EXPERIMENT: {
        break;
      }

      case MODE_RUN: {
        if (forceNew) {
          try {
            this.m_run.close();
          } catch (final Throwable error) {
            throw new IllegalStateException(
                this.__errorLocation(//
                    "Error while trying to close an run to begin a new runset.",//$NON-NLS-1$
                    true), error);
          } finally {
            this.m_run = null;
            this.m_mode = FlatExperimentSetContext.MODE_RUNS;
          }
          // no break: fall through
        } else {
          throw new IllegalStateException(this.__errorLocation(//
              "Cannot lazily begin new run set when inside a run.",//$NON-NLS-1$
              false));
        }
      }

      case MODE_RUNS: {
        if (forceNew) {
          try {
            this.m_runs.close();
          } catch (final Throwable error) {
            throw new IllegalStateException(
                this.__errorLocation(//
                    "Error while trying to close an run set to begin a new one.",//$NON-NLS-1$
                    true), error);
          } finally {
            this.m_runs = null;
            this.m_runsName = null;
            this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT;
          }
          break;
        }
        return this.m_runs;
      }

      default: {
        throw new IllegalStateException(this.__errorLocation(//
            "Cannot begin a new run set here.",//$NON-NLS-1$
            false));
      }
    }

    try {
      this.m_runs = this.m_experiment.createInstanceRuns();
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          "Error while trying to begin a new runset context.",//$NON-NLS-1$
          true), error);
    }
    this.m_mode = FlatExperimentSetContext.MODE_RUNS;
    return this.m_runs;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void runsBegin(final boolean forceNew) {
    this.__runsEnsure(forceNew);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("fallthrough")
  public synchronized final void runsEnd() {

    switch (this.m_mode) {
      case MODE_EXPERIMENT_SET:
      case MODE_EXPERIMENT: {
        return;
      }

      case MODE_RUN: {
        try {
          this.m_run.close();
        } catch (final Throwable error) {
          throw new IllegalStateException(
              this.__errorLocation(//
                  "Error while closing run context in order to close run set.",//$NON-NLS-1$
                  true), error);
        } finally {
          this.m_run = null;
          this.m_mode = FlatExperimentSetContext.MODE_RUNS;
        }
        // no break: fall through
      }

      case MODE_RUNS: {
        try {
          this.m_runs.close();
        } catch (final Throwable error) {
          throw new IllegalStateException(this.__errorLocation(//
              "Error while closing run set context.",//$NON-NLS-1$
              true), error);
        } finally {
          this.m_runs = null;
          this.m_runsName = null;
          this.m_mode = FlatExperimentSetContext.MODE_EXPERIMENT;
        }
        return;
      }

      default: {
        throw new IllegalStateException(
            this.__errorLocation(//
                "Cannot close a run set context at the current point in time.",//$NON-NLS-1$
                false));
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void runsSetInstance(final Instance inst) {
    final InstanceRunsContext context;

    context = this.__runsEnsure(false);
    try {
      context.setInstance(inst);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(((//
          "Error while setting instance '" //$NON-NLS-1$
          + ((inst != null) ? inst.getName() : TextUtils.NULL_STRING)) + //
          "' of current run set."), true), //$NON-NLS-1$
          error);
    }
    this.m_runsName = ((inst != null) ? inst.getName()
        : TextUtils.NULL_STRING);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void runsSetInstance(final String inst) {
    final InstanceRunsContext context;

    context = this.__runsEnsure(false);
    try {
      context.setInstance(inst);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(((//
          "Error while setting instance '" //$NON-NLS-1$
          + inst) + //
          "' of current run set."), true), //$NON-NLS-1$
          error);
    }
    this.m_runsName = inst;
  }

  /**
   * Ensure that we are in a run context
   *
   * @param forceNew
   *          should we enforce the creation of a new run?
   * @return the run context
   */
  private final RunContext __runEnsure(final boolean forceNew) {

    switch (this.m_mode) {

      case MODE_RUNS: {
        break;
      }

      case MODE_RUN: {
        if (forceNew) {
          try {
            this.m_run.close();
          } catch (final Throwable error) {
            throw new IllegalStateException(
                this.__errorLocation(//
                    "Error while trying to close an run to begin a new runset.",//$NON-NLS-1$
                    true), error);
          } finally {
            this.m_run = null;
            this.m_mode = FlatExperimentSetContext.MODE_RUNS;
          }
        }
        return this.m_run;
      }

      default: {
        throw new IllegalStateException(this.__errorLocation(//
            "Cannot begin a new run set here.",//$NON-NLS-1$
            false));
      }
    }

    try {
      this.m_run = this.m_runs.createRun();
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          "Error while trying to begin a new run context.",//$NON-NLS-1$
          true), error);
    }
    this.m_mode = FlatExperimentSetContext.MODE_RUN;
    return this.m_run;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void runBegin(final boolean forceNew) {
    this.__runEnsure(forceNew);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void runEnd() {
    switch (this.m_mode) {
      case MODE_EXPERIMENT_SET:
      case MODE_EXPERIMENT:
      case MODE_RUNS: {
        return;
      }
      case MODE_RUN: {
        try {
          this.m_run.close();
        } catch (final Throwable error) {
          throw new IllegalStateException(this.__errorLocation(//
              "Error while closing run context.",//$NON-NLS-1$
              true), error);
        } finally {
          this.m_run = null;
          this.m_mode = FlatExperimentSetContext.MODE_RUNS;
        }
        return;
      }

      default: {
        throw new IllegalStateException(this.__errorLocation(//
            "Cannot close a run context at the current point in time.",//$NON-NLS-1$
            false));
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void runAddDataPoint(final DataPoint point) {
    final RunContext context;

    context = this.__runEnsure(false);
    try {
      context.addDataPoint(point);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          ((("Error while adding data point instance ") //$NON-NLS-1$
          + point) + " to run."), true), error);//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void runAddDataPoint(final Object point) {
    final RunContext context;

    context = this.__runEnsure(false);
    try {
      context.addDataPoint(point);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          ((("Error while adding data point object ") //$NON-NLS-1$
          + point) + " to run."), true), error);//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void runAddDataPoint(final Number... values) {
    final RunContext context;

    context = this.__runEnsure(false);
    try {
      context.addDataPoint(values);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          ((("Error while adding data point numbers ") //$NON-NLS-1$
          + Arrays.toString(values)) + " to run."), true), error);//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public synchronized final void runAddDataPoint(final String point) {
    final RunContext context;

    context = this.__runEnsure(false);
    try {
      context.addDataPoint(point);
    } catch (final Throwable error) {
      throw new IllegalStateException(this.__errorLocation(//
          ((("Error while adding data point string '") //$NON-NLS-1$
          + point) + "' to run."), true), error);//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final ExperimentSet getExperimentSet() {
    final ExperimentSetContext main;
    ExperimentSet res;

    main = this.m_main;
    if (main == null) {
      throw new IllegalStateException("Experiment set already taken."); //$NON-NLS-1$
    }

    try {
      this.flush();
    } finally {
      this.m_main = null;
      res = main.create();
      main.close();
    }
    return res;
  }
}
