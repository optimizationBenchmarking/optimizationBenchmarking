package org.optimizationBenchmarking.experimentation.data;

import java.util.ArrayList;

/** A context for dimension sets. */
final class _DimensionSetContext extends
    _HierarchicalCollection<Dimension, DimensionContext, DimensionSet> {

  /**
   * create
   * 
   * @param element
   *          the owning element
   */
  _DimensionSetContext(final ExperimentSetContext element) {
    super(element);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final ExperimentSetContext getOwner() {
    return ((ExperimentSetContext) (super.getOwner()));
  }

  /**
   * Create the dimension context
   * 
   * @return the dimension context
   */
  final DimensionContext _createDimension() {
    this.fsmStateAssert(_FSM.STATE_OPEN);
    return new DimensionContext(this);
  }

  /** {@inheritDoc} */
  @Override
  final DimensionSet _doCompile(final ArrayList<Dimension> data) {
    return new DimensionSet(data.toArray(new Dimension[data.size()]));
  }

  /** {@inheritDoc} */
  @Override
  final Class<DimensionContext> _getChildType() {
    return DimensionContext.class;
  }
}
