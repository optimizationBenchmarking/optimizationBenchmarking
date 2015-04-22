package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSet;

/**
 * A shadow experiment set is basically a shadow of another experiment set
 * with a different owner and potentially different attributes. If all
 * associated data of this element is the same, it will delegate
 * attribute-based computations to that experiment set.
 * 
 * @param <OT>
 *          the owner type
 */
public class ShadowExperimentSet<OT extends IDataElement> extends //
    _ShadowNamedElementSet<OT, IExperimentSet, IExperiment> implements
    IExperimentSet {

  /** the dimensions */
  private IDimensionSet m_dimensions;

  /** the features */
  private IFeatureSet m_features;

  /** the instances */
  private IInstanceSet m_instances;

  /** the parameters */
  private IParameterSet m_parameters;

  /**
   * create the shadow experiment set
   * 
   * @param owner
   *          the owning element set
   * @param shadow
   *          the experiment set to shadow
   * @param experimentSelection
   *          the selection of experiments
   */
  public ShadowExperimentSet(final OT owner, final IExperimentSet shadow,
      final Collection<? extends IExperiment> experimentSelection) {
    super(owner, shadow, experimentSelection);
  }

  /** {@inheritDoc} */
  @Override
  final void _checkDiscardOrig() {
    if ((this.m_data != null) && //
        (this.m_dimensions != null) && //
        (this.m_features != null) && //
        (this.m_instances != null) && //
        (this.m_parameters != null)) {
      this.m_orig = null;
    }
  }

  /** {@inheritDoc} */
  @Override
  final IExperiment _shadow(final IExperiment element) {
    return new ShadowExperiment(this, element, null);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IDimensionSet getDimensions() {
    if (this.m_dimensions == null) {
      this.m_dimensions = new ShadowDimensionSet(this,
          this.m_orig.getDimensions(), null);
      this._checkDiscardOrig();
    }
    return this.m_dimensions;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IInstanceSet getInstances() {
    if (this.m_instances == null) {
      this.m_instances = new ShadowInstanceSet(this,
          this.m_orig.getInstances(), null);
      this._checkDiscardOrig();
    }
    return this.m_instances;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IFeatureSet getFeatures() {
    if (this.m_features == null) {
      this.m_features = new ShadowFeatureSet(this,
          this.m_orig.getFeatures(), null);
      this._checkDiscardOrig();
    }
    return this.m_features;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IParameterSet getParameters() {
    if (this.m_parameters == null) {
      this.m_parameters = new ShadowParameterSet(this,
          this.m_orig.getParameters(), null);
      this._checkDiscardOrig();
    }
    return this.m_parameters;
  }
}
