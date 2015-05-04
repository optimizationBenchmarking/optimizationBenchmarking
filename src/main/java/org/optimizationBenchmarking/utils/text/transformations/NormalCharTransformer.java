package org.optimizationBenchmarking.utils.text.transformations;

/**
 * A class we use to load normalize characters, i.e., to translate all
 * characters to their names. This turns complex unicode character such as
 * hexadecimal code point <code>109</code> to strings such as
 * &quot;LatinSmallLetterCWithCircumflex&quot; and hexadecimal code point
 * <code>d7d4</code> to &quot;HangulJongseongTikeutThieuth&quot;, i.e., to
 * something which can safely appear in file names, urls, and other
 * identifiers.
 */
public final class NormalCharTransformer extends LookupCharTransformer {

  /** instantiate */
  NormalCharTransformer() {
    super("normalCharTransformationMap.transform"); //$NON-NLS-1$
  }

  /**
   * Get the instance of the normalizing character transformation
   *
   * @return instance of the normalizing character transformation
   */
  public static final NormalCharTransformer getInstance() {
    return __NormalCharTransformerLoader.INSTANCE;
  }

  /** the loader */
  private static final class __NormalCharTransformerLoader {

    /** the normalizing character transformer */
    static final NormalCharTransformer INSTANCE = new NormalCharTransformer();
  }

}
