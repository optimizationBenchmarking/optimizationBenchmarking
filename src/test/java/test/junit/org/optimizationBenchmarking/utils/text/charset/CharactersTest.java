package test.junit.org.optimizationBenchmarking.utils.text.charset;

import org.junit.Test;
import org.optimizationBenchmarking.utils.text.charset.Char;
import org.optimizationBenchmarking.utils.text.charset.Characters;

import test.junit.org.optimizationBenchmarking.utils.collections.lists.ArraySetViewTestBase;

/**
 * test the characters
 */
public class CharactersTest extends ArraySetViewTestBase<Char, Characters> {

  /** create */
  public CharactersTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isSingleton() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  protected Characters getInstance() {
    return Characters.CHARACTERS;
  }

  /** Test the characters */
  @Test(timeout = 3600000)
  public void testChars() {
    for (final Char ch : this.getInstance()) {
      new CharTest(ch).validateChar();
    }
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    this.testChars();
  }
}
