package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import java.util.ArrayList;

import org.optimizationBenchmarking.experimentation.attributes.clusters.ICluster;
import org.optimizationBenchmarking.experimentation.attributes.clusters.IClustering;
import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.AlphabeticNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * The performance fingerprint clustering contains a division of
 * experiments or instances according to their performance fingerprints.
 */
public class FingerprintClustering extends DataElement implements
    IClustering {

  /** the data */
  private final ArrayListView<FingerprintCluster> m_data;

  /** the owning experiment set */
  private final IExperimentSet m_owner;

  /**
   * create the performance fingerprint clustering
   *
   * @param owner
   *          the owner
   * @param clusters
   *          the matrix of clusters
   */
  FingerprintClustering(final IExperimentSet owner, final IMatrix clusters) {
    super();
    final ArrayList<FingerprintCluster> list;
    final ArrayListView<? extends IInstance> instances;
    DataSelection selection;
    int clusterIndex, find;

    this.m_owner = owner;

    list = new ArrayList<>(20);
    instances = owner.getInstances().getData();

    for (clusterIndex = 1;; ++clusterIndex) {
      selection = null;

      for (find = clusters.n(); (--find) >= 0;) {
        if (clusters.getLong(0, find) == clusterIndex) {
          if (selection == null) {
            selection = new DataSelection(owner);
          }
          selection.addInstance(instances.get(find));
        }
      }

      if (selection == null) {
        break;
      }
      list.add(new FingerprintCluster(this,
          AlphabeticNumberAppender.UPPER_CASE_INSTANCE.toString(
              (clusterIndex - 1), ETextCase.IN_SENTENCE), selection));
    }

    this.m_data = ArrayListView.collectionToView(list);
  }

  /** {@inheritDoc} */
  @Override
  public final IDataElement getOwner() {
    return this.m_owner;
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

  /** {@inheritDoc} */
  @Override
  public final ArrayListView<? extends ICluster> getData() {
    return this.m_data;
  }
}
