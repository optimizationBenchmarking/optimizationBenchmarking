package test.junit.org.optimizationBenchmarking.utils.collections.maps;

import org.optimizationBenchmarking.utils.collections.maps.ChainedMapEntry;
import org.optimizationBenchmarking.utils.collections.maps.ObjectMap;

import test.junit.org.optimizationBenchmarking.utils.collections.MapTest;

/**
 * This is a test designed to verify that our tests are compatible to the
 * behavior of Java's map collection classes.
 */
public class ObjectMapTest
extends
MapTest<Object, Object, ObjectMap<Object, Object, ChainedMapEntry<Object, Object>>> {

  /** create */
  public ObjectMapTest() {
    this(new ObjectMap<>());
  }

  /**
   * create
   *
   * @param map
   *          the map
   */
  protected ObjectMapTest(
      final ObjectMap<Object, Object, ChainedMapEntry<Object, Object>> map) {
    super(null, map, false, true);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isModifiable() {
    return true;
  }
}
