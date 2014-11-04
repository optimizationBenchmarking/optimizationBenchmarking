package org.optimizationBenchmarking.experimentation.data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * An experiment set.
 * </p>
 */
public final class ExperimentSet extends _IDObjectSet<Experiment> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** an atomic id counter */
  private static final AtomicInteger ID_COUNTER = new AtomicInteger();

  /** the dimension set */
  private final DimensionSet m_dimensions;

  /** the parameters */
  private final ParameterSet m_parameters;

  /** the features */
  private final FeatureSet m_features;

  /** the instances */
  private final InstanceSet m_instances;

  /**
   * instantiate
   * 
   * @param dimensions
   *          the dimensions
   * @param instances
   *          the instances
   * @param features
   *          the features
   * @param parameters
   *          the parameters
   * @param data
   *          the data of the set
   * @param clone
   *          should we clone the data?
   * @param sort
   *          should we sort the data?
   * @param own
   *          should mark the elements as owned by this object?
   */
  ExperimentSet(final DimensionSet dimensions,
      final InstanceSet instances, final FeatureSet features,
      final ParameterSet parameters, final Experiment[] data,
      final boolean clone, final boolean sort, final boolean own) {
    super(data, clone, sort, own);

    if ((dimensions == null) || (dimensions.getData().isEmpty())) {
      throw new IllegalArgumentException((//
          "Dimension set must not be null or empty, but is "//$NON-NLS-1$
          + dimensions) + '.');
    }

    if ((instances == null) || (instances.getData().isEmpty())) {
      throw new IllegalArgumentException((//
          "Instance set must not be null or empty, but is "//$NON-NLS-1$
          + instances) + '.');
    }
    if ((features == null) || (features.getData().isEmpty())) {
      throw new IllegalArgumentException((//
          "Feature set must not be null or empty, but is "//$NON-NLS-1$
          + features) + '.');
    }

    if ((parameters == null) || (parameters.getData().isEmpty())) {
      throw new IllegalArgumentException((//
          "Parameter set must not be null or empty, but is "//$NON-NLS-1$
          + parameters) + '.');
    }

    dimensions.m_owner = this;
    this.m_dimensions = dimensions;

    instances.m_owner = this;
    this.m_instances = instances;

    features.m_owner = this;
    this.m_features = features;

    parameters.m_owner = this;
    this.m_parameters = parameters;

    this.m_id = ExperimentSet.ID_COUNTER.getAndIncrement();
  }

  /**
   * check the parameter settings
   * 
   * @param a
   *          the first experiment
   * @param b
   *          the second experiment
   */
  private static final void __checkParamSettings(final Experiment a,
      final Experiment b) {

    if (a.m_parameters == b.m_parameters) {
      throw new IllegalArgumentException((((((//
          "In an experiment set, there must never be two experiments with the same parameter settings, but the experiments '" + //$NON-NLS-1$
          a) + "' and '") + b) + //$NON-NLS-1$
          "' both have exactly the same parameter setting: ") + //$NON-NLS-1$
          a.m_parameters) + '.');
    }

    if (a.m_parameters.equals(b.m_parameters)) {
      throw new IllegalArgumentException((((((((//
          "In an experiment set, there must never be two experiments with the equal parameter settings, but the experiments '" + //$NON-NLS-1$
          a) + "' and '") + b) + //$NON-NLS-1$
          "' both have equal parameter settings, that is ") + //$NON-NLS-1$
          a.m_parameters) + " and ") + //$NON-NLS-1$
          b.m_parameters) + '.');
    }

    if (a.m_parameters.contains(b.m_parameters)) {
      throw new IllegalArgumentException(
          (((((((//
              "In an experiment set, no parameter setting of one experiment may contain the parameter setting of another experiment, but the parameter setting '" + //$NON-NLS-1$
              a.m_parameters) + "' of experiment ") + //$NON-NLS-1$
              a) + "' contains (is a super set of) the parameter setting '") //$NON-NLS-1$
              + b.m_parameters) + "' of experiment '") + b) + //$NON-NLS-1$
              +'.');
    }
  }

  /** {@inheritDoc} */
  @Override
  final void _validateElementPair(final Experiment before,
      final Experiment after) {
    super._validateElementPair(before, after);

    if (before.m_name.equals(after.m_name)) {
      throw new IllegalArgumentException(((((((//
          "No two experiments must have the same name, but experiments '" + //$NON-NLS-1$
          before) + "' and '")//$NON-NLS-1$
          + after) + "' have similar names, that is '")//$NON-NLS-1$
          + before.m_name) + '\'') + '.');
    }

    if (before.m_name.equalsIgnoreCase(after.m_name)) {
      throw new IllegalArgumentException(((((((((//
          "No two experiments must have the same name (even with different case!), but experiments '" + //$NON-NLS-1$
          before) + "' and '")//$NON-NLS-1$
          + after) + "' have similar names, that is '")//$NON-NLS-1$
          + before.m_name) + "' and '") + //$NON-NLS-1$
          after.m_name) + '\'') + '.');
    }

    ExperimentSet.__checkParamSettings(before, after);
    ExperimentSet.__checkParamSettings(after, before);
  }

  /**
   * Get the set of measurement dimensions
   * 
   * @return the set of measurement dimensions
   */
  public final DimensionSet getDimensions() {
    return this.m_dimensions;
  }

  /**
   * Get the set of problem instances
   * 
   * @return the set of problem instances
   */
  public final InstanceSet getInstances() {
    return this.m_instances;
  }

  /**
   * Get the set of instance features
   * 
   * @return the set of instance features
   */
  public final FeatureSet getFeatures() {
    return this.m_features;
  }

  /**
   * Get the set of parameters
   * 
   * @return the set of parameters
   */
  public final ParameterSet getParameters() {
    return this.m_parameters;
  }

  /** {@inheritDoc} */
  @Override
  public ExperimentSet getOwner() {
    return null;
  }
}
