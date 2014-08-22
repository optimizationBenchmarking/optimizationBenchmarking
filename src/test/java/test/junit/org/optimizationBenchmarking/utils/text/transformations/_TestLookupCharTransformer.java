package test.junit.org.optimizationBenchmarking.utils.text.transformations;

import org.optimizationBenchmarking.utils.text.transformations.LookupCharTransformer;

/**
 * The character transformation class for testing purposes.
 */
public final class _TestLookupCharTransformer extends
    LookupCharTransformer {

  /** the character transformer */
  public static final _TestLookupCharTransformer INSTANCE = new _TestLookupCharTransformer();

  /** instantiate */
  private _TestLookupCharTransformer() {
    super("_TestLookupCharTransformer.transform"); //$NON-NLS-1$
  }
}
