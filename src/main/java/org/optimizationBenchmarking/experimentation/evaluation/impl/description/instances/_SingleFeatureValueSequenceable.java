package org.optimizationBenchmarking.experimentation.evaluation.impl.description.instances;

import org.optimizationBenchmarking.experimentation.attributes.PropertyValueElements;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.ISequenceable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A sequenceable for a single ordinal feature value */
final class _SingleFeatureValueSequenceable implements ISequenceable {

  /** the feature value */
  final IFeatureValue m_featureValue;

  /**
   * create the numerical feature sequenceable
   *
   * @param featureValue
   *          the feature value
   */
  _SingleFeatureValueSequenceable(final IFeatureValue featureValue) {
    super();
    this.m_featureValue = featureValue;
  }

  /** {@inheritDoc} */
  @Override
  public final void toSequence(final boolean isFirstInSequence,
      final boolean isLastInSequence, final ETextCase textCase,
      final ITextOutput textOut) {

    if (textOut instanceof IComplexText) {
      try (final IPlainText text = ((IComplexText) textOut).emphasize()) {
        text.append(this.m_featureValue.getValue());
      }
    } else {
      textOut.append(this.m_featureValue.getValue());
    }

    _NumericalFeatureSequenceable._appendInstances(
        PropertyValueElements.FEATURE_VALUE_INSTANCES
            .get(this.m_featureValue), textOut, textCase.nextCase(), 1);
  }
}
