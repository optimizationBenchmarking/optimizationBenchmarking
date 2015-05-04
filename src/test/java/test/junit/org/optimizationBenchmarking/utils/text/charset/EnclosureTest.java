package test.junit.org.optimizationBenchmarking.utils.text.charset;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.text.charset.Enclosure;

import test.junit.org.optimizationBenchmarking.utils.collections.lists.ArraySetViewTestBase;

/**
 * test the enclosures
 *
 * @param <T>
 *          the enclosure test
 */
@Ignore
public class EnclosureTest<T extends Enclosure> extends
ArraySetViewTestBase<T, ArraySetView<T>> {

  /** create */
  public EnclosureTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isSingleton() {
    return true;
  }

  /** Test the Enclosure */
  @Test(timeout = 3600000)
  public void testEnclosure() {
    final ArraySetView<T> collection;

    collection = this.getInstance();
    for (final Enclosure m : collection) {
      Assert.assertTrue(m.getBegin().isOpening());
      Assert.assertTrue(m.getEnd().isClosing());

      Assert.assertTrue(m.getEnd().canStartWith(m.getBegin()));
      Assert.assertTrue(m.getBegin().canEndWith(m.getEnd()));

      Assert.assertEquals(m.getBeginChar(), m.getBegin().getChar());
      Assert.assertEquals(m.getEndChar(), m.getEnd().getChar());

      new CharTest(m.getBegin()).validateChar();
      new CharTest(m.getEnd()).validateChar();

      Assert.assertSame(m.getSet(), collection);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    this.testEnclosure();
  }
}
