package org.optimizationBenchmarking.experimentation.data.impl.delegates;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A subset of a experiment set is basically a sub-set of another
 * experiment set with a different owner, but delegates inquiries to that
 * experiment set, where ever feasible.
 */
public class ExperimentSetSubset extends AbstractExperimentSet {

  /** the original experiment set */
  private final IExperimentSet m_orig;

  /** the data */
  private final ArrayListView<ExperimentSubset> m_data;

  /** can we delegate attribute computation ? */
  private final boolean m_canDelegateAttributes;

  /**
   * create a subset of an experiment set
   * 
   * @param orig
   *          the original experiment set
   * @param copy
   *          the data to copy
   */
  public ExperimentSetSubset(final IExperimentSet orig,
      final ArrayListView<? extends IExperiment> copy) {
    super();

    final ArrayListView<? extends IExperiment> origData, copyData;
    final ExperimentSubset[] experiments;
    final int origSize, copySize;
    boolean canDelegate;
    int i;

    this.m_orig = ExperimentSetSubset.__check(orig);

    origData = orig.getData();
    if (origData == null) {
      throw new IllegalArgumentException(//
          "No instance of IExperimentSet can have null data, but we encountered one such instance.");//$NON-NLS-1$
    }

    copyData = ((copy == null) ? origData : copy);

    origSize = origData.size();
    if (origSize <= 0) {
      throw new IllegalArgumentException(//
          "No instance of IExperimentSet can have empty data, but we encountered one such instance.");//$NON-NLS-1$
    }

    copySize = copyData.size();
    if (copySize <= 0) {
      throw new IllegalArgumentException(//
          "Cannot create empty experiment set.");//$NON-NLS-1$
    }

    if (copySize > origSize) {
      throw new IllegalArgumentException(//
          "Cannot create ExperimentSetSubset with more experiment than the original experiment set.");//$NON-NLS-1$
    }

    experiments = new ExperimentSubset[copySize];
    i = 0;
    canDelegate = true;
    for (final IExperiment experiment : copyData) {

      if (experiment.getOwner() != orig) {
        throw new IllegalArgumentException(
            "Can only create ExperimentSetSubset of experiments which all stem from the same experiment set."); //$NON-NLS-1$
      }

      canDelegate &= (experiment == origData.get(i));

      experiments[i] = new ExperimentSubset(experiment, this);
    }

    this.m_data = new ArrayListView<>(experiments);
    this.m_canDelegateAttributes = canDelegate;
  }

  /**
   * create a delegate of an experiment set
   * 
   * @param orig
   *          the original experiment set
   */
  public ExperimentSetSubset(final IExperimentSet orig) {
    this(ExperimentSetSubset.__check(orig), orig.getData());
  }

  /**
   * Check an experiment set
   * 
   * @param orig
   *          the experiment set
   * @return the experiment set
   */
  private static final IExperimentSet __check(final IExperimentSet orig) {
    if (orig == null) {
      throw new IllegalArgumentException(//
          "IExperimentSet to delegate to must not be null."); //$NON-NLS-1$
    }
    return orig;
  }

  /** {@inheritDoc} */
  @Override
  public final ArrayListView<ExperimentSubset> getData() {
    return this.m_data;
  }

  /** {@inheritDoc} */
  @Override
  public final IDimensionSet getDimensions() {
    return this.m_orig.getDimensions();
  }

  /** {@inheritDoc} */
  @Override
  public IInstanceSet getInstances() {
    return this.m_orig.getInstances(); // TODO
  }

  /** {@inheritDoc} */
  @Override
  public final IFeatureSet getFeatures() {
    return this.m_orig.getFeatures();
  }

  /** {@inheritDoc} */
  @Override
  public final IParameterSet getParameters() {
    return this.m_orig.getParameters();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  protected final <XDT extends IDataElement, RT> RT getAttribute(
      final Attribute<XDT, RT> attribute) {
    if (this.m_canDelegateAttributes) {
      return DataElement.delegateGetAttribute(((XDT) (this.m_orig)),
          attribute);
    }
    return super.getAttribute(attribute);
  }
}
