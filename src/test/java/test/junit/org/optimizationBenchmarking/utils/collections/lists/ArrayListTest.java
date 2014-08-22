package test.junit.org.optimizationBenchmarking.utils.collections.lists;

import java.util.ArrayList;

import test.junit.org.optimizationBenchmarking.utils.collections.ListTest;

/**
 * This is a test designed to verify that our tests are compatible to the
 * behavior of Java's list collection classes.
 */
public class ArrayListTest extends ListTest<Object, ArrayList<Object>> {

  /** create */
  public ArrayListTest() {
    super(null, new ArrayList<>(), false, true);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isModifiable() {
    return true;
  }
}
