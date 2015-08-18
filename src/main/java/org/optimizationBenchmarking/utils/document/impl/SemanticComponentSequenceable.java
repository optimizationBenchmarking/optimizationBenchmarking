package org.optimizationBenchmarking.utils.document.impl;

import java.util.Collection;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.spec.ISemanticComponent;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.ISequenceable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A sequenceable wrapping a semantic component.
 */
public class SemanticComponentSequenceable implements ISequenceable {

  /** the internal semantic component */
  private final ISemanticComponent m_component;

  /** should we print the short name? */
  private final boolean m_printShortName;
  /** should we print the long name? */
  private final boolean m_printLongName;

  /**
   * Create the semantic component to sequenceable wrapper
   *
   * @param component
   *          the component
   * @param printShortName
   *          should we print the short name?
   * @param printLongName
   *          should we print the long name?
   */
  public SemanticComponentSequenceable(final ISemanticComponent component,
      final boolean printShortName, final boolean printLongName) {
    super();
    if (component == null) {
      throw new IllegalArgumentException(//
          "Semantic component to be wrapped cannot be null."); //$NON-NLS-1$
    }
    SemanticComponentSequenceable._checkPrint(printShortName,
        printLongName);
    this.m_component = component;
    this.m_printShortName = printShortName;
    this.m_printLongName = printLongName;
  }

  /**
   * check the print parameters
   *
   * @param printShortName
   *          should we print the short name?
   * @param printLongName
   *          should we print the long name?
   */
  static final void _checkPrint(final boolean printShortName,
      final boolean printLongName) {
    if (!(printShortName || printLongName)) {
      throw new IllegalArgumentException(//
          "printShortName and printLongName cannot both be false.");//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void toSequence(final boolean isFirstInSequence,
      final boolean isLastInSequence, final ETextCase textCase,
      final ITextOutput textOut) {
    if (this.m_printShortName) {
      if (this.m_printLongName) {
        SemanticComponentUtils.printLongAndShortNameIfDifferent(
            this.m_component, textOut, textCase);
      } else {
        this.m_component.printShortName(textOut, textCase);
      }
    } else {
      this.m_component.printLongName(textOut, textCase);
    }
  }

  /**
   * Wrap a collection of elements to semantic component sequenceables.
   *
   * @param components
   *          the components
   * @param printShortName
   *          should we print the short name?
   * @param printLongName
   *          should we print the long name?
   * @return the wrapped collection
   */
  public static final ArrayListView<ISequenceable> wrap(
      final Collection<? extends ISemanticComponent> components,
      final boolean printShortName, final boolean printLongName) {
    final ISequenceable[] wrapped;
    int index;

    wrapped = new ISequenceable[components.size()];
    index = 0;
    for (final ISemanticComponent isc : components) {
      wrapped[index++] = new SemanticComponentSequenceable(isc,
          printShortName, printLongName);
    }
    return new ArrayListView<>(wrapped);
  }
}
