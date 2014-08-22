package test.junit.org.optimizationBenchmarking.utils.text.textOutput;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.AlphabeticNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A test of the class
 * {@link org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput}
 */
public class MemoryTextOutputTest extends TextOutputTest<MemoryTextOutput> {

  /** create */
  public MemoryTextOutputTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final MemoryTextOutput createRootObject() {
    return new MemoryTextOutput();
  }

  /** {@inheritDoc} */
  @Override
  protected final String getString(final MemoryTextOutput root) {
    return root.toString();
  }

  /** {@inheritDoc} */
  @Override
  protected final ITextOutput wrap(final MemoryTextOutput root) {
    return root;
  }

  /** test if ints are encoded correctly */
  @Test(timeout = 3600000)
  public void testEncodedInt() {
    final MemoryTextOutput mto;
    final HashSet<String> set;
    int i;

    set = new HashSet<>();
    mto = new MemoryTextOutput();

    i = Integer.MIN_VALUE;
    AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(i,
        ETextCase.IN_SENTENCE, mto);
    Assert.assertTrue(set.add(mto.toString()));
    mto.clear();

    i = Integer.MAX_VALUE;
    AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(i,
        ETextCase.IN_SENTENCE, mto);
    Assert.assertTrue(set.add(mto.toString()));
    mto.clear();

    AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(0,
        ETextCase.IN_SENTENCE, mto);
    mto.clear();
    AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(1,
        ETextCase.IN_SENTENCE, mto);
    mto.clear();
    AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(2,
        ETextCase.IN_SENTENCE, mto);
    mto.clear();
    AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(3,
        ETextCase.IN_SENTENCE, mto);
    mto.clear();
    AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(25,
        ETextCase.IN_SENTENCE, mto);
    mto.clear();
    AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(26,
        ETextCase.IN_SENTENCE, mto);
    mto.clear();
    AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(27,
        ETextCase.IN_SENTENCE, mto);
    mto.clear();

    for (i = (-10000000); i <= 10000000; i++) {
      AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(i,
          ETextCase.IN_SENTENCE, mto);
      Assert.assertTrue(set.add(mto.toString()));
      mto.clear();
    }
  }

  /** test if longs are encoded correctly */
  @Test(timeout = 3600000)
  public void testEncodedLong() {
    final MemoryTextOutput mto;
    final HashSet<String> set;
    long i;

    set = new HashSet<>();
    mto = new MemoryTextOutput();

    i = Integer.MIN_VALUE;
    AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(i,
        ETextCase.IN_SENTENCE, mto);
    Assert.assertTrue(set.add(mto.toString()));
    mto.clear();

    i = Integer.MAX_VALUE;
    AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(i,
        ETextCase.IN_SENTENCE, mto);
    Assert.assertTrue(set.add(mto.toString()));
    mto.clear();

    i = Long.MIN_VALUE;
    AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(i,
        ETextCase.IN_SENTENCE, mto);
    Assert.assertTrue(set.add(mto.toString()));
    mto.clear();

    i = Long.MAX_VALUE;
    AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(i,
        ETextCase.IN_SENTENCE, mto);
    Assert.assertTrue(set.add(mto.toString()));
    mto.clear();

    for (i = (-10000000l); i <= 10000000l; i++) {
      AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(i,
          ETextCase.IN_SENTENCE, mto);
      Assert.assertTrue(set.add(mto.toString()));
      mto.clear();
    }
  }
}
