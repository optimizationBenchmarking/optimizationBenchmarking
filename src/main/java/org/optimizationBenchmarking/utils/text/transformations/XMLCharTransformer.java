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

  /** Create */
  XMLCharTransformer() {
    super();
  }

  /**
   * Get the instance of the XML character transformer
   *
   * @return the instance of the XML character transformer
   */
  public static final XMLCharTransformer getInstance() {
    return __XMLCharTransformerLoader.INSTANCE;
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

  /** the internal loader */
  private static final class __XMLCharTransformerLoader {

    /** instantiate */
    static final XMLCharTransformer INSTANCE = new XMLCharTransformer();

  }
}
