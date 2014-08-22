package test.junit.org.optimizationBenchmarking.utils.text.charset;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.text.charset.QuotationMarks;

/**
 * test the characters
 */
public class QuotationMarksTest extends EnclosureTest<QuotationMarks> {

  /** create */
  public QuotationMarksTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected ArraySetView<QuotationMarks> getInstance() {
    return QuotationMarks.QUOTATION_MARKS;
  }

}
