package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

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
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.AlphabeticNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * The performance fingerprint clustering contains a division of
 * experiments or instances according to their performance fingerprints.
 *
 * @param <CT>
 *          the cluster type
 */
abstract class _FingerprintClustering<CT extends _FingerprintCluster<?>>
    extends DataElement implements IClustering {

  /** the owning experiment set */
  private final IExperimentSet m_owner;

  /** the data */
  private final ArrayListView<CT> m_data;

  /**
   * create the performance fingerprint clustering
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
  _FingerprintClustering(final IExperimentSet owner,
      final IMatrix clusters, final INamedElementSet source,
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
    for (clusterIndex = 1;; ++clusterIndex) {
      selection = null;
      for (find = clusters.n(); (--find) >= 0;) {
        if (clusters.getLong(0, find) == clusterIndex) {
          if (selection == null) {
            selection = new DataSelection(owner);
          }
          name = names.get(find).getName();
          ne = source.find(name);
          if (ne == null) {
            throw new IllegalStateException(
                "Cannot find element of name '" //$NON-NLS-1$
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
          .toString((clusterIndex - 1), ETextCase.IN_SENTENCE), selection));
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

  /** {@inheritDoc} */
  @Override
  public final ETextCase printShortName(final ITextOutput textOut,
      final ETextCase textCase) {
    return textCase.appendWord("fingerprint", textOut); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    return textCase.appendWord("fingerprint", textOut); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return textCase.appendWord("fingerprint", textOut); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final String getPathComponentSuggestion() {
    return "fingerprint"; //$NON-NLS-1$
  }
}
