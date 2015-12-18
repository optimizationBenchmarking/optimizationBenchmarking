package org.optimizationBenchmarking.experimentation.attributes.clusters.behavior;

import java.util.ArrayList;

import org.optimizationBenchmarking.experimentation.attributes.clusters.IClustering;
import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElement;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElementSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.AlphabeticNumberAppender;

/**
 * A behavior-based clustering uses algorithm behavior to divide algorithms
 * or instances into groups.
 *
 * @param <CT>
 *          the cluster type
 */
abstract class _BehaviorClustering<CT extends _BehaviorCluster<?>>
    extends DataElement implements IClustering {

  /** the owning experiment set */
  private final IExperimentSet m_owner;

  /** the data */
  private final ArrayListView<CT> m_data;

  /**
   * create the behavior-based clustering
   *
   * @param owner
   *          the owner
   * @param clusters
   *          the matrix of clusters
   * @param source
   *          the source where to draw the named elements from
   * @param names
   *          the names
   */
  _BehaviorClustering(final IExperimentSet owner, final int[] clusters,
      final INamedElementSet source,
      final ArrayListView<? extends INamedElement> names) {
    super();
    final ArrayList<CT> list;
    DataSelection selection;
    int clusterIndex, find;
    INamedElement ne;
    String name;
    int total;

    this.m_owner = owner;

    list = new ArrayList<>(20);

    total = 0;
    for (clusterIndex = 0;; ++clusterIndex) {
      selection = null;
      for (find = clusters.length; (--find) >= 0;) {
        if (clusters[find] == clusterIndex) {
          if (selection == null) {
            selection = new DataSelection(owner);
          }
          name = names.get(find).getName();
          ne = source.find(name);
          if (ne == null) {
            throw new IllegalStateException("Cannot find element of name '" //$NON-NLS-1$
                + name + "' in " + names); //$NON-NLS-1$
          }
          if (ne instanceof IInstance) {
            selection.addInstance((IInstance) ne);
            total++;
          } else {
            if (ne instanceof IExperiment) {
              selection.addExperiment((IExperiment) ne);
              total++;
            } else {
              throw new IllegalStateException(//
                  "Element of name '" + name + //$NON-NLS-1$
                      "' is neither an experiment nor a benchmark instance.");//$NON-NLS-1$
            }
          }
        }
      }

      if (selection == null) {
        break;
      }
      list.add(this._create(AlphabeticNumberAppender.UPPER_CASE_INSTANCE
          .toString(clusterIndex, ETextCase.IN_SENTENCE), selection));
    }

    if (total != names.size()) {
      throw new IllegalStateException(//
          "There are " + names.size() + //$NON-NLS-1$
              " elements that should be in clusters, but " + total + //$NON-NLS-1$
              " have actually been assigned.");//$NON-NLS-1$
    }

    this.m_data = ArrayListView.collectionToView(list);
  }

  /**
   * create a new cluster
   *
   * @param name
   *          the name
   * @param selection
   *          the selection
   * @return the cluster
   */
  abstract CT _create(final String name, final DataSelection selection);

  /** {@inheritDoc} */
  @Override
  public final IExperimentSet getOwner() {
    return this.m_owner;
  }

  /** {@inheritDoc} */
  @Override
  public final ArrayListView<CT> getData() {
    return this.m_data;
  }
}
