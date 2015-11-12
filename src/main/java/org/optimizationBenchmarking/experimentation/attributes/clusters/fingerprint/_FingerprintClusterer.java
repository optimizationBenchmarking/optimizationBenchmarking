package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import java.io.IOException;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.attributes.OnlySharedInstances;
import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElement;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElementSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.io.StreamLineIterator;
import org.optimizationBenchmarking.utils.math.mathEngine.impl.R.R;
import org.optimizationBenchmarking.utils.math.mathEngine.impl.R.REngine;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/**
 * Cluster experimental data based on performance fingerprints. It can
 * group either experiments by their performance or instances by the
 * performance of the algorithms on them. This clusterer proceeds as
 * follows:
 * <ul>
 * <li>First, we compute the
 * {@link org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint.Fingerprint}
 * s of the elements to be clustered.</li>
 * </ul>
 *
 * @param <CT>
 *          the cluster type
 * @param <CCT>
 *          the clustering type
 */
abstract class _FingerprintClusterer<CT extends _FingerprintCluster<CCT>, CCT extends _FingerprintClustering<CT>>
    extends Attribute<IExperimentSet, CCT> {

  /** create the clusterer */
  _FingerprintClusterer() {
    super(EAttributeType.TEMPORARILY_STORED);
  }

  /**
   * Get the source of the data to be clustered
   *
   * @param data
   *          the data
   * @return the source of the data to be clustered
   */
  abstract INamedElementSet _getClusterSource(final IExperimentSet data);

  /**
   * Create the clustering
   *
   * @param data
   *          the data
   * @param clustering
   *          the clustering decisions
   * @param source
   *          the source set of named elements to pick from
   * @param names
   *          the element names
   * @return the clustering
   */
  abstract CCT _create(final IExperimentSet data, final IMatrix clustering,
      final INamedElementSet source,
      final ArrayListView<? extends INamedElement> names);

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected final CCT compute(final IExperimentSet data,
      final Logger logger) {
    final INamedElementSet names;
    IMatrix matrix;

    names = this._getClusterSource(OnlySharedInstances.INSTANCE.get(//
        data, logger));
    matrix = Fingerprint.INSTANCE.get(names, logger);
    try (final REngine engine = R.getInstance().use().create()) {
      engine.setMatrix("data", matrix);//$NON-NLS-1$
      matrix = null;
      try {
        engine.execute(new StreamLineIterator(_FingerprintClusterer.class,
            "cluster.txt"));//$NON-NLS-1$
      } catch (final Throwable error) {
        throw new IllegalStateException(//
            "Error while communicating REngine. Maybe the data is just too odd, or some required packages are missing and cannot be installed.", //$NON-NLS-1$
            error);
      }
      matrix = engine.getMatrix("clusters"); //$NON-NLS-1$
    } catch (final IOException ioe) {
      throw new IllegalStateException(//
          "Error while starting REngine. Maybe R is not installed properly?", //$NON-NLS-1$
          ioe);
    }

    return this._create(data, matrix, this._getClusterSource(data),
        names.getData());
  }
}
