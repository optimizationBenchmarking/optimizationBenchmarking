package org.optimizationBenchmarking.experimentation.evaluation.attributes.basic;

import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.impl.ref.Instance;
import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * An attribute for obtaining all instances of an experiment set.
 */
public final class AllInstances extends
    Attribute<ExperimentSet, ArraySetView<Instance>> {

  /** the internal singleton instance */
  private static final AllInstances INSTANCE = new AllInstances();

  /** create */
  private AllInstances() {
    super(EAttributeType.NEVER_STORED);
  }

  /** {@inheritDoc} */
  @Override
  protected final ArraySetView<Instance> compute(final ExperimentSet data) {
    return data.getInstances().getData();
  }

  /**
   * Get the globally shared instance of this attribute
   * 
   * @return the globally shared instance of this attribute
   */
  public static final AllInstances getInstance() {
    return AllInstances.INSTANCE;
  }

}
