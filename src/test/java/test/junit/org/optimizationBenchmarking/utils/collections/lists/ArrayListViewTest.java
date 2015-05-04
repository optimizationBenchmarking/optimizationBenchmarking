package test.junit.org.optimizationBenchmarking.utils.collections.lists;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/** a test for collection classes */
public class ArrayListViewTest extends
ArrayListViewTestBase<Object, ArrayListView<Object>> {

  /** create */
  public ArrayListViewTest() {
    super(null, null, false);
  }

  /** {@inheritDoc} */
  @Override
  public ArrayListView<Object> getInstance() {
    return new ArrayListView<>(//
        ArrayListViewTestBase._randomList());
  }
}
