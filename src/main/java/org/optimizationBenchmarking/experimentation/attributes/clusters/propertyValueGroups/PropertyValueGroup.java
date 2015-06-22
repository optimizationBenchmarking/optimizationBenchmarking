package org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.attributes.clusters.ICluster;
import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.experimentation.data.impl.shadow.ShadowExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.impl.SemanticComponentUtils;
import org.optimizationBenchmarking.utils.document.spec.ISemanticComponent;
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
   * Print the number of selected elements or the title of the selected
   * element, if only one was selected.
   *
   * @param textOut
   *          the number of selected elements
   * @param textCase
   *          the number of selected elements
   * @param nameMode
   *          the name printing mode
   * @return the next text case
   */
  final ETextCase _printSelected(final ITextOutput textOut,
      final ETextCase textCase, final int nameMode) {
    final IProperty prop;
    final ArrayListView<? extends IInstance> instanceList;
    final ArrayListView<? extends IExperiment> experimentList;
    final int size;
    final ISemanticComponent component;
    ETextCase useCase;

    useCase = ETextCase.ensure(textCase);
    prop = this.getOwner().m_property;

    if (prop instanceof IFeature) {
      instanceList = this.getInstances().getData();
      size = instanceList.size();
      if (size <= 0) {
        useCase = useCase.appendWords("no instance", textOut); //$NON-NLS-1$
      } else {
        if (size <= 1) {
          useCase = useCase.appendWord("instance", textOut); //$NON-NLS-1$
          textOut.append(' ');
          component = instanceList.get(0);

          switch (nameMode) {
            case 0: {
              useCase = component.printShortName(textOut, useCase);
              break;
            }
            case 1: {
              useCase = component.printLongName(textOut, useCase);
              break;
            }
            default: {
              useCase = SemanticComponentUtils
                  .printLongAndShortNameIfDifferent(component, textOut,
                      textCase);
            }
          }

        } else {
          textOut.append(size);
          textOut.append(' ');
          useCase = useCase.appendWord("instances", textOut); //$NON-NLS-1$
        }
      }
    } else {
      if (prop instanceof IParameter) {
        experimentList = this.getData();
        size = experimentList.size();
        if (size <= 0) {
          useCase = useCase.appendWords("no experiment", textOut); //$NON-NLS-1$
        } else {
          if (size <= 1) {
            useCase = useCase.appendWord("experiment", textOut); //$NON-NLS-1$
            textOut.append(' ');
            component = experimentList.get(0);
            switch (nameMode) {
              case 0: {
                useCase = component.printShortName(textOut, useCase);
                break;
              }
              case 1: {
                useCase = component.printLongName(textOut, useCase);
                break;
              }
              default: {
                useCase = SemanticComponentUtils
                    .printLongAndShortNameIfDifferent(component, textOut,
                        textCase);
              }
            }
          } else {
            textOut.append(size);
            textOut.append(' ');
            useCase = useCase.appendWord("experiments", textOut); //$NON-NLS-1$
          }
        }
      } else {
        useCase = useCase.appendWords("data with", textOut); //$NON-NLS-1$
      }
    }

    textOut.append(' ');
    useCase = ETextCase.ensure(useCase).appendWord("with", textOut); //$NON-NLS-1$
    textOut.append(' ');
    return useCase;
  }
}
