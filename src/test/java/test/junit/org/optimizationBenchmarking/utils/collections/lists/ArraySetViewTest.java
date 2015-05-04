package test.junit.org.optimizationBenchmarking.utils.collections.lists;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/** a test for collection classes */
public class ArraySetViewTest extends
ArrayListViewTestBase<Object, ArraySetView<Object>> {

  /** create */
  public ArraySetViewTest() {
    super(null, null, false);
  }

  /** {@inheritDoc} */
  @Override
  public ArraySetView<Object> getInstance() {
    return new ArraySetView<>(//
        ArrayListViewTestBase._randomList());
  }
}
