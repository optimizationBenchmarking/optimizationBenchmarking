package test.junit.org.optimizationBenchmarking.utils.text.charset;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.text.charset.Char;
import org.optimizationBenchmarking.utils.text.charset.Characters;
import org.optimizationBenchmarking.utils.text.charset.EnclosureEnd;
import org.optimizationBenchmarking.utils.text.charset.QuotationMark;

import test.junit.TestBase;

/** Test a char. */
@Ignore
public class CharTest extends TestBase {

  /** the char */
  private final Char m_ch;

  /**
   * create
   * 
   * @param ch
   *          the char
   */
  protected CharTest(final Char ch) {
    super();
    this.m_ch = ch;
  }

  /**
   * Get the char
   * 
   * @return the char
   */
  protected Char getChar() {
    return this.m_ch;
  }

  /** Test whether the collection serializes and deserializes properly */
  @Test(timeout = 3600000)
  public void testSerialization() {
    final Char ch;

    ch = this.getChar();
    Assert.assertSame(ch, TestBase.serializeDeserialize(ch));
  }

  /** Test whether the character is correctly defined */
  @Test(timeout = 3600000)
  public void testDefined() {
    final Char ch;

    ch = this.getChar();
    Character.isDefined(ch.getChar());
    Character.isValidCodePoint(ch.getChar());
  }

  /** Test whether the character is consistent with the character set */
  @Test(timeout = 3600000)
  public void testConsistency() {
    final Char ch;
    String escape;

    ch = this.getChar();
    Assert.assertSame(ch, Characters.CHARACTERS.getChar(ch.getChar()));
    Assert.assertSame(ch,
        Characters.CHARACTERS.get(Characters.CHARACTERS.indexOf(ch)));

    escape = ch.getEscapeSequence();
    Assert.assertTrue((escape == null) || (escape.length() == 2));
  }

  /**
   * Test whether the character is an instance of EnclosureEnd and, if so,
   * if it is a correct instance
   */
  @Test(timeout = 3600000)
  public void testEnclosureEnd() {
    final Char ch;
    final EnclosureEnd e;

    ch = this.getChar();
    if (ch instanceof EnclosureEnd) {
      e = ((EnclosureEnd) ch);

      Assert.assertNotNull(e.getOtherEnd());
      Assert.assertNotNull(e.getOwner());
      Assert.assertTrue(e.isOpening() || e.isClosing());

      if (e.isOpening()) {
        Assert.assertTrue(e.getOtherEnd().isClosing());
        Assert.assertTrue(e.canEndWith(e.getOtherEnd()));
        Assert.assertTrue(e.getOtherEnd().canStartWith(e));
      }

      if (e.isClosing()) {
        Assert.assertTrue(e.getOtherEnd().isOpening());
        Assert.assertTrue(e.canStartWith(e.getOtherEnd()));
        Assert.assertTrue(e.getOtherEnd().canEndWith(e));
      }
    }
  }

  /**
   * Test whether the character is an instance of QuotationMark and, if so,
   * if it is a correct instance
   */
  @Test(timeout = 3600000)
  public void testQuotationMark() {
    final Char ch;
    final QuotationMark e;

    ch = this.getChar();
    if (ch instanceof QuotationMark) {
      e = ((QuotationMark) ch);

      Assert.assertTrue(e.getDashCount() > 0);
      Assert
          .assertEquals(e.getDashCount(), e.getOtherEnd().getDashCount());
    }
  }

  /** run all tests */
  protected void validateChar() {
    this.testSerialization();
    this.testDefined();
    this.testConsistency();
    this.testEnclosureEnd();
    this.testQuotationMark();
  }
}
