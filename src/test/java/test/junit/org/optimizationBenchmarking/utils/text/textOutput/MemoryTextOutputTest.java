package test.junit.org.optimizationBenchmarking.utils.text.textOutput;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.random.LoremIpsum;
import org.optimizationBenchmarking.utils.math.random.RandomUtils;
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

    for (i = (-100000); i <= 100000; i++) {
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

    for (i = (-100000L); i <= 100000L; i++) {
      AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(i,
          ETextCase.IN_SENTENCE, mto);
      Assert.assertTrue(set.add(mto.toString()));
      mto.clear();
    }
  }

  /**
   * test if longs are encoded correctly
   *
   * @throws IOException
   *           if i/o fails: but should not
   */
  @Test(timeout = 3600000)
  public void testAppendAll() throws IOException {
    final Random rand;
    MemoryTextOutput mto;
    StringBuilder sb;
    String str;
    int i, j;
    char ch;

    rand = new Random();
    for (j = 4; (--j) >= 0;) {
      mto = this.createRootObject();
      i = 16384;
      sb = new StringBuilder(i * 4096);
      for (; (--i) >= 0;) {
        switch (rand.nextInt(3)) {
          case 0: {
            sb.append(String.valueOf(RandomUtils.longToObject(
                rand.nextLong(), rand.nextBoolean())));
            break;
          }
          case 1: {
            sb.append(LoremIpsum.loremIpsum(rand, rand.nextInt(12)));
            break;
          }
          default: {
            do {
              ch = ((char) (rand.nextInt(65536)));
            } while (!(Character.isValidCodePoint(ch)));
            sb.append(ch);
          }
        }

        str = sb.toString();
        mto.clear();
        mto.appendAll(new StringReader(str));
        Assert.assertEquals(str, mto.toString());
        str = null;
      }
    }
  }
}
