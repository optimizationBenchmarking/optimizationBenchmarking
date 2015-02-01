package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

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

}
