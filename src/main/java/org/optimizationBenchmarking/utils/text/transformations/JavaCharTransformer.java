package org.optimizationBenchmarking.utils.text.transformations;

import java.text.Normalizer;

import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * <p>
 * A character transformer that transforms unicode characters to Java
 * entities.
 * </p>
 */
public final class JavaCharTransformer extends CharTransformer {

  /** Create */
  JavaCharTransformer() {
    super();
  }

  /**
   * Get the instance of the Java character transformer
   *
   * @return the instance of the Java character transformer
   */
  public static final JavaCharTransformer getInstance() {
    return __JavaCharTransformerLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final AbstractTextOutput transform(final ITextOutput out,
      final Normalizer.Form form) {
    if (form == null) {
      return new _JavaTransformedTextOutput(out);
    }
    return new _NormalizingJavaTransformedTextOutput(out, form);
  }

  /** the internal loader */
  private static final class __JavaCharTransformerLoader {

    /** instantiate */
    static final JavaCharTransformer INSTANCE = new JavaCharTransformer();

  }
}
