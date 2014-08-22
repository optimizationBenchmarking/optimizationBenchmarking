package org.optimizationBenchmarking.experimentation.data;

import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * <p>
 * An experiment.
 * </p>
 */
public final class Experiment extends DataSet<ExperimentSet, InstanceRuns> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the parameter value map */
  final ParameterSetting m_parameters;

  /** this object's name */
  final String m_name;
  /** the description of this property */
  final String m_description;

  /**
   * instantiate
   * 
   * @param name
   *          the name
   * @param desc
   *          the description
   * @param params
   *          the parameter values
   * @param data
   *          the data of the set
   * @param clone
   *          should we clone the data?
   * @param sort
   *          should we sort the data?
   * @param own
   *          should mark the elements as owned by this object?
   */
  Experiment(final String name, final String desc,
      final ParameterSetting params, final InstanceRuns[] data,
      final boolean clone, final boolean sort, final boolean own) {
    super(data, clone, sort, own);
    String n;

    if (params == null) {
      throw new IllegalArgumentException(//
          "Parameter setting of experiment must not be null."); //$NON-NLS-1$
    }
    this.m_parameters = params;

    n = TextUtils.prepare(name);
    if (n == null) {
      n = TextUtils.prepare(params.toName());
    }

    if (n == null) {
      throw new IllegalArgumentException(//
          "Experiments must have a non-empty name, but name '" + //$NON-NLS-1$
              name + "' was provided."); //$NON-NLS-1$

    }
    this.m_name = n;
    this.m_description = TextUtils.prepare(desc);

  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return this.m_name;
  }

  /** {@inheritDoc} */
  @Override
  public final String getDescription() {
    return this.m_description;
  }

  /** {@inheritDoc} */
  @Override
  final void _validateElementPair(final InstanceRuns before,
      final InstanceRuns after) {
    super._validateElementPair(before, after);
    if (before.m_inst.equals(after.m_inst)) {
      throw new IllegalArgumentException(
          (((((("An experiment cannot contain more than one run set for a given instance, but run set '" //$NON-NLS-1$
          + before) + "' and '") + //$NON-NLS-1$
          after) + "' are both for instance '") + //$NON-NLS-1$
          before.m_inst) + '\'') + '.');
    }
  }

  /**
   * Get the parameter map.
   * 
   * @return the parameter map.
   */
  public final ParameterSetting parameters() {
    return this.m_parameters;
  }

  /**
   * Get the name of the experiment
   * 
   * @return the name of the experiment
   */
  public final String name() {
    return this.m_name;
  }

  /** {@inheritDoc} */
  @Override
  final int _compareTo(final _IDObject<?> o) {

    if (o instanceof Experiment) {
      return this.m_parameters.compareTo(((Experiment) o).m_parameters);
    }

    return super._compareTo(o);
  }
}
