package org.optimizationBenchmarking.experimentation.evaluation.impl.description.instances;

import java.io.Closeable;
import java.io.IOException;

import org.optimizationBenchmarking.experimentation.attributes.PropertyValueElements;
import org.optimizationBenchmarking.experimentation.attributes.statistics.propertyExtremals.ExtremalPropertyValues;
import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.impl.SemanticComponentSequenceable;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.ISequenceable;
import org.optimizationBenchmarking.utils.text.numbers.InTextNumberAppender;
import org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A sequenceable for numerical features */
final class _NumericalFeatureSequenceable implements ISequenceable {

  /** the feature */
  final IFeature m_feature;

  /**
   * the extremal values of that feature, or {@code null} if not applicable
   */
  final ExtremalPropertyValues<IFeatureValue> m_extremal;

  /**
   * create the numerical feature sequenceable
   *
   * @param feature
   *          the feature
   * @param extremal
   *          the extremal values of that feature, or {@code null} if not
   *          applicable
   */
  _NumericalFeatureSequenceable(final IFeature feature,
      final ExtremalPropertyValues<IFeatureValue> extremal) {
    super();
    this.m_feature = feature;
    this.m_extremal = extremal;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public final void toSequence(final boolean isFirstInSequence,
      final boolean isLastInSequence, final ETextCase textCase,
      final ITextOutput textOut) {
    ETextCase next;
    ArrayListView<? extends IFeatureValue> values;
    ITextOutput use;
    int size;

    next = this.m_feature.printShortName(textOut, textCase);

    textOut.append(' ');

    if (textOut instanceof IPlainText) {
      use = ((IPlainText) textOut).inBraces();
    } else {
      use = textOut;
      use.append('(');
    }
    try {

      values = this.m_feature.getData();

      printValues: {
        size = values.size();
        if ((size <= 1) || (this.m_extremal == null)) {
          next = next.appendWord("only", use);//$NON-NLS-1$
          use.append(' ');
          next = InTextNumberAppender.INSTANCE.appendTo(size, next, use);
          use.append(' ');
          next = next.appendWord("value", use); //$NON-NLS-1$
          use.append(':');
          use.append(' ');
          use.append(values.get(0));
          break printValues;
        }
        next = InTextNumberAppender.INSTANCE.appendTo(size, next, use);
        use.append(' ');
        next = next.appendWords("values, ranging from", use); //$NON-NLS-1$
        use.append(' ');
        next = SimpleNumberAppender.INSTANCE.appendTo(
            this.m_extremal.getMinimumValue(), next, use);
        next = _NumericalFeatureSequenceable._appendInstances(
            PropertyValueElements.FEATURE_VALUE_INSTANCES.get(//
                this.m_extremal.getMinimum()), use, next, 0);
        use.append(' ');
        next = next.appendWord("to", use); //$NON-NLS-1$
        use.append(' ');
        next = SimpleNumberAppender.INSTANCE.appendTo(
            this.m_extremal.getMaximumValue(), next, use);
        next = _NumericalFeatureSequenceable._appendInstances(
            PropertyValueElements.FEATURE_VALUE_INSTANCES.get(//
                this.m_extremal.getMaximum()), use, next, 0);
      }

    } finally {
      if (use != textOut) {
        try {
          ((Closeable) use).close();
        } catch (final IOException ioe) {
          RethrowMode.AS_RUNTIME_EXCEPTION.rethrow(null, false, ioe);
        }
      } else {
        textOut.append(')');
      }
    }
  }

  /**
   * Append the instances
   *
   * @param instances
   *          the instances
   * @param textOut
   *          the destination
   * @param textCase
   *          the text case
   * @param depth
   *          the depth in sequencing
   * @return the next text case
   */
  static final ETextCase _appendInstances(
      final ArrayListView<? extends IInstance> instances,
      final ITextOutput textOut, final ETextCase textCase, final int depth) {
    if (instances.size() >= 3) {
      return textCase;
    }
    textOut.append(' ');
    if (textOut instanceof IPlainText) {
      try (final IPlainText inside = ((IPlainText) textOut).inBraces()) {
        ESequenceMode.AND.appendNestedSequence(textCase, //
            SemanticComponentSequenceable.wrap(instances, true, false),//
            true, depth, inside);
      }
    } else {
      textOut.append('[');
      ESequenceMode.AND.appendNestedSequence(textCase, //
          SemanticComponentSequenceable.wrap(instances, true, false),//
          true, depth, textOut);
      textOut.append(']');
    }
    return textCase.nextCase();
  }
}
