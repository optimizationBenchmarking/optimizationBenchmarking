package org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.attributes.clusters.ICluster;
import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.experimentation.data.impl.shadow.ShadowExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.text.ETextCase;
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

  /**
   * Append a number
   *
   * @param number
   *          the number to append
   * @param out
   *          the text output device
   */
  static final void _appendNumber(final Number number,
      final ITextOutput out) {
    if ((NumericalTypes.getTypes(number) & NumericalTypes.IS_LONG) != 0) {
      out.append(number.longValue());
    } else {
      out.append(number.doubleValue());
    }
  }

  /**
   * Append a name
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case
   * @return the next text case
   */
  abstract ETextCase _appendName(final ITextOutput textOut,
      final ETextCase textCase);

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendName(final ITextOutput textOut,
      final ETextCase textCase) {
    final ArrayListView<? extends IInstance> list;
    final int size;
    ETextCase useCase;

    useCase = ETextCase.ensure(textCase);
    list = this.getInstances().getData();
    size = list.size();
    if (size <= 0) {
      useCase = useCase.appendWords("no instance", textOut); //$NON-NLS-1$
    } else {
      if (size <= 1) {
        useCase = useCase.appendWord("instance", textOut); //$NON-NLS-1$
        textOut.append(' ');
        useCase = list.get(0).appendName(textOut, useCase);
      } else {
        textOut.append(size);
        textOut.append(' ');
        useCase = useCase.appendWord("instances", textOut); //$NON-NLS-1$
      }
    }

    textOut.append(' ');
    useCase = ETextCase.ensure(useCase).appendWord("with", textOut); //$NON-NLS-1$
    textOut.append(' ');
    return this._appendName(textOut, useCase);
  }
}
