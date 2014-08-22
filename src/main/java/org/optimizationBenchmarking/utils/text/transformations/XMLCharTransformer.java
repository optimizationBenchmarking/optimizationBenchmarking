package org.optimizationBenchmarking.utils.text.transformations;

import java.text.Normalizer;

import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * <p>
 * A character transformer that transforms unicode characters to XML
 * entities.
 * </p>
 */
public final class XMLCharTransformer extends CharTransformer {

  /** instantiate */
  public static final XMLCharTransformer INSTANCE = new XMLCharTransformer();

  /** Create */
  private XMLCharTransformer() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final AbstractTextOutput transform(final ITextOutput out,
      final Normalizer.Form form) {
    if (form == null) {
      return new _XMLTransformedTextOutput(out);
    }
    return new _NormalizingXMLTransformedTextOutput(out, form);
  }
}
