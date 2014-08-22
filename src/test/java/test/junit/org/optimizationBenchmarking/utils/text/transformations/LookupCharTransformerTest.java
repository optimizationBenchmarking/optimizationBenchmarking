package test.junit.org.optimizationBenchmarking.utils.text.transformations;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.transformations.CharTransformer;
import org.optimizationBenchmarking.utils.text.transformations.LookupCharTransformer;

/**
 * test the test char transformer
 */
public class LookupCharTransformerTest extends CharTransformerTest {

  /** create */
  public LookupCharTransformerTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final CharTransformer getInstance() {
    return _TestLookupCharTransformer.INSTANCE;
  }

  /**
   * test if the transformer can correctly transform single characters
   */
  @Test(timeout = 3600000)
  public void testSingleTransform2() {
    final LookupCharTransformer t;

    t = _TestLookupCharTransformer.INSTANCE;
    Assert.assertNotNull(t);

    Assert.assertEquals("", t.transform(" ", //$NON-NLS-1$//$NON-NLS-2$
        TextUtils.DEFAULT_NORMALIZER_FORM));
    Assert.assertEquals("A", t.transform("A",//$NON-NLS-1$//$NON-NLS-2$ 
        TextUtils.DEFAULT_NORMALIZER_FORM));
    Assert.assertEquals(" ", t.transform("B",//$NON-NLS-1$//$NON-NLS-2$
        TextUtils.DEFAULT_NORMALIZER_FORM));
    Assert.assertEquals(
        "C", t.transform("C", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
    Assert.assertEquals(
        "A", t.transform("D", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
    Assert.assertEquals(
        "", t.transform("E", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
    Assert.assertEquals(
        "F", t.transform("F", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
    Assert.assertEquals(
        " ", t.transform("G", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
    Assert.assertEquals(
        "D", t.transform("H", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
    Assert.assertEquals(
        "I", t.transform("I", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
    Assert.assertEquals(
        "blablabla", t.transform("J", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
    Assert
        .assertEquals(
            "this\\is\\a\\k", t.transform("K", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$

    Assert.assertEquals(
        "a", t.transform("a", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
    Assert.assertEquals(
        " ", t.transform("b", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
    Assert.assertEquals(
        "c", t.transform("c", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
    Assert.assertEquals(
        "a", t.transform("d", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
    Assert.assertEquals(
        "", t.transform("e", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
    Assert.assertEquals(
        "f", t.transform("f", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
    Assert.assertEquals(
        " ", t.transform("g", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
    Assert.assertEquals(
        "d", t.transform("h", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
    Assert.assertEquals(
        "i", t.transform("i", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
    Assert.assertEquals(
        "blablabla", t.transform("j", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
    Assert
        .assertEquals(
            "this\\is\\a\\k", t.transform("k", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$//$NON-NLS-2$
  }

  /**
   * test if the transformer can correctly transform single characters
   */
  @Test(timeout = 3600000)
  public void testMultiTransform2() {
    final LookupCharTransformer t;

    t = _TestLookupCharTransformer.INSTANCE;
    Assert.assertNotNull(t);

    Assert.assertEquals(
        "A CAF DIblablablathis\\is\\a\\ka caf diblablablathis\\is\\a\\k", //$NON-NLS-1$/
        t.transform(
            " ABCDEFGHIJKabcdefghijk", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$/

    Assert
        .assertEquals(
            "A A AAAAA AAAAAAAA CCAAAAACAAA", //$NON-NLS-1$/
            t.transform(
                "ABABAAAAABAAAAAAAABCCAAAAACAAA", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$/

    Assert
        .assertEquals(
            "A A AAAAA AAAAAAAA CCAAAAACAAAC", //$NON-NLS-1$/
            t.transform(
                "ABABAAAAABAAAAAAAABCCAAAAACAAAC", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$

    Assert
        .assertEquals(
            "A A AAAAA AAAAAAAA CCAAAAACAAA ", //$NON-NLS-1$/
            t.transform(
                "ABABAAAAABAAAAAAAABCCAAAAACAAAB", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$/

    Assert
        .assertEquals(
            "a a aaaaa aaaaaaaa ccaaaaacaaa", //$NON-NLS-1$/
            t.transform(
                "abaBaaaaaBaaaaaaaabccaaaaacaaa", TextUtils.DEFAULT_NORMALIZER_FORM)); //$NON-NLS-1$/
  }

  // hyphenation is no deprecated
  // /**
  // * test if the transformer can correctly hyphenate
  // */
  // @Test(timeout = 3600000)
  // public void testHyphenate() {
  // final LookupCharTransformer t;
  //
  // t = _TestLookupCharTransformer.INSTANCE;
  // Assert.assertNotNull(t);
  //
  // Assert
  // .assertEquals(
  //            "a a aaaaa aaaaaaaa ccaaaaacaaac", //$NON-NLS-1$/
  // t.transform(
  //                "ababaaaaabaaaaaaaabccaaaaacaaac", ETransformationMode.HYPHENATED)); //$NON-NLS-1$/
  //
  // Assert
  // .assertEquals(
  //            "a a aa-Aaaa aaaaaaaa ccaaaaacaaac", //$NON-NLS-1$/
  // t.transform(
  //                "ababaaAaaabaaaaaaaabccaaaaacaaac", ETransformationMode.HYPHENATED)); //$NON-NLS-1$/
  //
  // Assert
  // .assertEquals(
  //            "a a aa.-aaa aaaaaaaa ccaaaaacaaac", //$NON-NLS-1$/
  // t.transform(
  //                "ababaa.aaabaaaaaaaabccaaaaacaaac", ETransformationMode.HYPHENATED)); //$NON-NLS-1$/
  //
  // Assert
  // .assertEquals(
  //            "a a aa.-Aaaa aaaaaaaa ccaaaaacaaac", //$NON-NLS-1$/
  // t.transform(
  //                "ababaa.Aaaabaaaaaaaabccaaaaacaaac", ETransformationMode.HYPHENATED)); //$NON-NLS-1$/
  //
  // Assert
  // .assertEquals(
  //            "a a aa.-A-A-Aaaa aaaaaaaa ccaaaaacaaac", //$NON-NLS-1$/
  // t.transform(
  //                "ababaa.AAAaaabaaaaaaaabccaaaaacaaac", ETransformationMode.HYPHENATED)); //$NON-NLS-1$/
  // }

}
