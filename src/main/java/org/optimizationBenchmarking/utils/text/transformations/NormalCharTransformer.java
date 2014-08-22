package org.optimizationBenchmarking.utils.text.transformations;

/**
 * the internal class we use to load the text normalization data
 */
public final class NormalCharTransformer extends LookupCharTransformer {

  /** the normalizing character transformer */
  public static final NormalCharTransformer INSTANCE = new NormalCharTransformer();

  /** instantiate */
  private NormalCharTransformer() {
    super("normalCharTransformationMap.transform"); //$NON-NLS-1$
  }

}
