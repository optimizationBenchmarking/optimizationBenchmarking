package org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.attributes.clusters.ICluster;
import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.experimentation.data.impl.shadow.ShadowExperimentSet;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A group of experiments or experiment sub-sets selected according to some
 * criterion based on a property.
 * 
 * @param <OT>
 *          the owning groups
 */
public abstract class PropertyValueGroup<OT extends PropertyValueGroups>
    extends ShadowExperimentSet<OT> implements ICluster {

  /**
   * create the property value group
   * 
   * @param owner
   *          the owning element set
   * @param selection
   *          the data selection
   */
  PropertyValueGroup(final OT owner, final DataSelection selection) {
    super(owner, selection);
  }

  /**
   * Append the selection criterion.
   * 
   * @param textOut
   *          the text output to append to
   */
  public abstract void appendCriterion(final ITextOutput textOut);

  /**
   * Get the the selection criterion string
   * 
   * @return the the selection criterion string
   */
  public String getCriterionString() {
    final MemoryTextOutput mto;

    mto = new MemoryTextOutput();
    this.appendCriterion(mto);
    return mto.toString();
  }
}
