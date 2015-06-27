package org.optimizationBenchmarking.utils.document.impl;

import java.util.Collection;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.ISemanticComponent;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.math.text.NamedMathRenderable;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** Utility methods for semantic components */
public final class SemanticComponentUtils {
  /**
   * A default implementation of the method
   * {@link org.optimizationBenchmarking.utils.document.spec.ISemanticComponent#printShortName(ITextOutput, ETextCase)}
   * .
   *
   * @param shortName
   *          the short name
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case to be used
   * @param isCaseFixed
   *          {@code true} if the text case of the short name is fixed and
   *          should not be adapted, regardless of what {@code textCase}
   *          says
   * @return the next text case
   */
  public static final ETextCase printShortName(final String shortName,
      final ITextOutput textOut, final ETextCase textCase,
      final boolean isCaseFixed) {
    final ETextCase useCase;

    useCase = ETextCase.ensure(textCase);
    if (textOut instanceof IComplexText) {
      try (final IText emph = ((IComplexText) textOut).emphasize()) {
        if (isCaseFixed) {
          emph.append(shortName);
          return useCase;
        }
        return useCase.appendWords(shortName, textOut);
      }
    }

    return useCase.appendWords(shortName, textOut);
  }

  /***
   * A default implementation of the method
   * {@link org.optimizationBenchmarking.utils.document.spec.ISemanticComponent#printLongName(ITextOutput, ETextCase)}
   * .
   *
   * @param longName
   *          the long name
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case to be used
   * @return the next text case
   */
  public static final ETextCase printLongName(final String longName,
      final ITextOutput textOut, final ETextCase textCase) {
    return ETextCase.ensure(textCase).appendWords(longName, textOut);
  }

  /**
   * A default implementation of the method
   * {@link org.optimizationBenchmarking.utils.document.spec.ISemanticComponent#printDescription(ITextOutput, ETextCase)}
   * .
   *
   * @param description
   *          the description to print
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case to be used
   * @return the next text case
   */
  public static final ETextCase printDescription(final String description,
      final ITextOutput textOut, final ETextCase textCase) {
    return ETextCase.ensure(textCase).appendWords(description, textOut);
  }

  /**
   * Render a name on a single string. This is a default implementation of
   * the method
   * {@link org.optimizationBenchmarking.utils.math.text.IMathRenderable#mathRender(ITextOutput, IParameterRenderer)}
   * and it delegates to
   * {@link org.optimizationBenchmarking.utils.math.text.NamedMathRenderable#mathRender(ITextOutput, IParameterRenderer)}
   * .
   *
   * @param name
   *          the name to render
   * @param out
   *          the text output device
   * @param renderer
   *          the parameter renderer
   */
  public static final void mathRender(final String name,
      final ITextOutput out, final IParameterRenderer renderer) {
    NamedMathRenderable.mathRender(name, out, renderer);
  }

  /**
   * Render a name based on a single string. This is a default
   * implementation of the method
   * {@link org.optimizationBenchmarking.utils.math.text.IMathRenderable#mathRender(IMath, IParameterRenderer)}
   * and it delegates to
   * {@link org.optimizationBenchmarking.utils.math.text.NamedMathRenderable#mathRender(IMath, IParameterRenderer)}
   * . .
   *
   * @param name
   *          the name to render
   * @param out
   *          the math output device
   * @param renderer
   *          the parameter renderer
   */
  public static final void mathRender(final String name, final IMath out,
      final IParameterRenderer renderer) {
    NamedMathRenderable.mathRender(name, out, renderer);
  }

  /**
   * Render a text in the form of {@code long name (short name)} if the
   * short name of a component is different from the long name, render
   * {@code long name} otherwise.
   *
   * @param component
   *          the component
   * @param output
   *          the output target
   * @param textCase
   *          the text case
   * @return the next case
   */
  public static final ETextCase printLongAndShortNameIfDifferent(
      final ISemanticComponent component, final ITextOutput output,
      final ETextCase textCase) {
    final MemoryTextOutput mto1;
    MemoryTextOutput mto2;
    ETextCase next;

    mto1 = new MemoryTextOutput();
    next = component.printLongName(mto1, textCase);
    mto2 = new MemoryTextOutput(mto1.length());
    next = component.printShortName(mto2, next);

    if (mto1.equalsIgnoreCase(mto2)) {
      mto2 = null;
    }

    if (output instanceof MemoryTextOutput) {
      output.append(mto1);
      if (mto2 != null) {
        output.append(' ');
        output.append('(');
        output.append(mto2);
        output.append(')');
      }
      return next;
    }

    next = component.printLongName(output, textCase);
    if (mto2 != null) {
      output.append(' ');
      output.append('(');
      next = component.printShortName(output, next);
      output.append(')');
    }
    return next;
  }

  /**
   * Print the names of a sequence of semantic components
   *
   * @param mode
   *          the sequence mode
   * @param components
   *          the components
   * @param printShortName
   *          should we print the short name?
   * @param printLongName
   *          should we print the long name?
   * @param textCase
   *          the text case
   * @param dest
   *          the output destination
   */
  public static final void printNames(final ESequenceMode mode,
      final Collection<? extends ISemanticComponent> components,
      final boolean printShortName, final boolean printLongName,
      final ETextCase textCase, final ITextOutput dest) {
    final Object[] list;
    int index;

    if (mode == null) {
      throw new IllegalArgumentException(//
          "Sequence mode cannot be null"); //$NON-NLS-1$
    }
    if (components == null) {
      throw new IllegalArgumentException(//
          "List of components cannot be null.");//$NON-NLS-1$
    }

    SemanticComponentSequenceable._checkPrint(printShortName,
        printLongName);
    list = components.toArray();
    for (index = list.length; (--index) >= 0;) {
      list[index] = new SemanticComponentSequenceable(//
          ((ISemanticComponent) (list[index])),//
          printShortName, printLongName);
    }
    mode.appendSequence(textCase, new ArrayListView<>(list), false, dest);
  }

  /** the forbidden constructor */
  private SemanticComponentUtils() {
    ErrorUtils.doNotCall();
  }
}
