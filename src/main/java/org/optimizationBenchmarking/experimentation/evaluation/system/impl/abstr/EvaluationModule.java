package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import org.optimizationBenchmarking.experimentation.evaluation.system.spec.EModuleRelationship;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationModule;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/**
 * The abstract base-class for evaluation modules.
 */
public abstract class EvaluationModule extends Tool implements
    IEvaluationModule {

  /** create the module */
  protected EvaluationModule() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public Iterable<Class<? extends IEvaluationModule>> getRequiredModules() {
    return ((Iterable) (ArraySetView.EMPTY_SET_VIEW));
  }

  /** {@inheritDoc} */
  @Override
  public EModuleRelationship getRelationship(final IEvaluationModule other) {
    return EModuleRelationship.NONE;
  }

  /** {@inheritDoc} */
  @Override
  public boolean canUse() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
