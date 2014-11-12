package test.junit.org.optimizationBenchmarking.utils.text.transformations;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.transformations.CharTransformer;
import org.optimizationBenchmarking.utils.text.transformations.LaTeXCharTransformer;

/**
 * test the LaTeX char transformer
 */
public class LaTeXCharTransformerTest extends CharTransformerTest {

  /** create */
  public LaTeXCharTransformerTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final CharTransformer getInstance() {
    return LaTeXCharTransformer.getInstance();
  }

  /** test special characters */
  @Test(timeout = 3600000)
  public void testSpecialCharacters() {

    Assert.assertEquals("{\\{}", //$NON-NLS-1$
        LaTeXCharTransformer.getInstance().transform(//
            "{", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$

    Assert.assertEquals("{\\}}", //$NON-NLS-1$
        LaTeXCharTransformer.getInstance().transform(//
            "}", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$

  }

}
