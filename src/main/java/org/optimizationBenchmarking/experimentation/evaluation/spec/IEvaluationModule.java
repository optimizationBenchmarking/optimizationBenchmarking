package org.optimizationBenchmarking.experimentation.evaluation.spec;

import org.optimizationBenchmarking.utils.tools.spec.ITool;

/** This is the basic interface for evaluator modules. */
public interface IEvaluationModule extends ITool {

  /**
   * Get a list of other modules which must also be executed if this module
   * is executed.
   *
   * @return an {@link java.lang.Iterable} with the required modules (may
   *         be {@code null} or an empty list if none are required)
   */
  public abstract Iterable<Class<? extends IEvaluationModule>> getRequiredModules();

  /**
   * <p>
   * In order to determine the execution sequence of modules, this method
   * allows us to additionally define order suggestions. Different from the
   * {@link java.lang.Comparable#compareTo(Object)} method of the
   * {@link java.lang.Comparable} interface, this relationship does not
   * need to be anti-symmetric, just free of contradictions. It also allows
   * for a containment hierarchy.
   * </p>
   * <p>
   * The evaluation process will try to follow the order suggestions
   * according to a best-effort principle. However, it may be that due to
   * parallelism or general structural constraints, some order suggestions
   * will be violated.
   * </p>
   *
   * @param other
   *          the other module to compare with
   * @return the relationship be between this module and the specified
   *         module.
   */
  public abstract EModuleRelationship getRelationship(
      final IEvaluationModule other);

  /** {@inheritDoc} */
  @Override
  public abstract IEvaluationJobBuilder use();
}
