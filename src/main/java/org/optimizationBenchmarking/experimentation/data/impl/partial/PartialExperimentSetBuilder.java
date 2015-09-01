package org.optimizationBenchmarking.experimentation.data.impl.partial;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractDimension;
import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractNamedElement;
import org.optimizationBenchmarking.experimentation.data.impl.flat.AbstractFlatExperimentSetContext;
import org.optimizationBenchmarking.experimentation.data.spec.EDimensionDirection;
import org.optimizationBenchmarking.experimentation.data.spec.EDimensionType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;
import org.optimizationBenchmarking.utils.parsers.NumberParser;

/**
 * A builder which allows for the creation of partial data structures of
 * the experiment data api. You can obtain handles to the data elements at
 * any time during the build process. Of course, they might still change.
 * Or be empty.
 */
public final class PartialExperimentSetBuilder extends
    AbstractFlatExperimentSetContext {

  /** the internal experiment set */
  private final _Experiments m_set;

  /** create */
  public PartialExperimentSetBuilder() {
    super();
    this.m_set = new _Experiments();
  }

  /** {@inheritDoc} */
  @Override
  public final void dimensionBegin(final boolean forceNew) {
    this.m_set.getDimensions()._getDimension(forceNew);
  }

  /** {@inheritDoc} */
  @Override
  public final void dimensionEnd() {
    this.m_set.getDimensions().m_needsNew = true;
  }

  /** {@inheritDoc} */
  @Override
  public final void dimensionSetName(final String name) {
    this.m_set.getDimensions()._getDimension(false).m_name = //
    AbstractNamedElement.formatName(name);
  }

  /** {@inheritDoc} */
  @Override
  public final void dimensionSetDescription(final String description) {
    this.m_set.getDimensions()._getDimension(false).m_description = //
    AbstractNamedElement.formatDescription(description);
  }

  /** {@inheritDoc} */
  @Override
  public final void dimensionAddDescription(final String description) {
    final _Dimension dim;

    dim = this.m_set.getDimensions()._getDimension(false);
    dim.m_description = AbstractNamedElement.mergeDescriptions(
        dim.m_description, description);
  }

  /** {@inheritDoc} */
  @Override
  public final void dimensionSetDirection(
      final EDimensionDirection direction) {
    AbstractDimension.validateDirection(direction);
    this.m_set.getDimensions()._getDimension(false).m_dimensionDirection = direction;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final void dimensionSetParser(final NumberParser<?> parser) {
    final _Dimension dim;

    dim = this.m_set.getDimensions()._getDimension(false);
    dim.m_dataType = AbstractDimension.validateParser(parser);
    dim.m_parser = ((NumberParser) parser);
  }

  /** {@inheritDoc} */
  @Override
  public final void dimensionSetType(final EDimensionType type) {
    AbstractDimension.validateType(type);
    this.m_set.getDimensions()._getDimension(false).m_dimensionType = type;
  }

  /** {@inheritDoc} */
  @Override
  public final IDimensionSet getDimensionSet() {
    return this.m_set.getDimensions();
  }

  /** {@inheritDoc} */
  @Override
  public final IFeatureSet getFeatureSet() {
    return this.m_set.getFeatures();
  }

  /** {@inheritDoc} */
  @Override
  public final IInstanceSet getInstanceSet() {
    return this.m_set.getInstances();
  }

  /** {@inheritDoc} */
  @Override
  public final IExperimentSet getExperimentSet() {
    this.getDimensionSet();
    return this.m_set;
  }
}
