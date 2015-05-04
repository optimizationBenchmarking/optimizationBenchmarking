package test.junit.org.optimizationBenchmarking.utils.collections.maps;

import java.util.HashMap;

import test.junit.org.optimizationBenchmarking.utils.collections.MapTest;

/**
 * This is a test designed to verify that our tests are compatible to the
 * behavior of Java's map collection classes.
 */
public class HashMapTest extends
    MapTest<Object, Object, HashMap<Object, Object>> {

  /** create */
  public HashMapTest() {
    super(null, new HashMap<>(), false, true);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isModifiable() {
    return true;
  }

}
