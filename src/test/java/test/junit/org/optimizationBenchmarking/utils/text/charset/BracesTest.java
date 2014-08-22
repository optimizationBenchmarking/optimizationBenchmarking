package test.junit.org.optimizationBenchmarking.utils.text.charset;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.text.charset.Braces;

/**
 * test the characters
 */
public class BracesTest extends EnclosureTest<Braces> {

  /** create */
  public BracesTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected ArraySetView<Braces> getInstance() {
    return Braces.BRACES;
  }

}
