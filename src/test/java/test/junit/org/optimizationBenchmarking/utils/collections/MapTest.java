package test.junit.org.optimizationBenchmarking.utils.collections;

import java.io.Externalizable;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.random.RandomUtils;

import test.junit.InstanceTest;
import test.junit.TestBase;

/**
 * A test for maps.
 * 
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 * @param <MT>
 */
@Ignore
public class MapTest<K, V, MT extends Map<K, V>> extends InstanceTest<MT> {

  /** the constructor */
  protected MapTest() {
    this(null, null, false, false);
  }

  /**
   * Create the instance test
   * 
   * @param owner
   *          the owner
   * @param isSingleton
   *          is this a singleton-based tests?
   * @param isModifiable
   *          are the instances modifiable?
   * @param instance
   *          the instance, or {@code null} if unspecified
   */
  public MapTest(final MapTest<K, V, MT> owner, final MT instance,
      final boolean isSingleton, final boolean isModifiable) {
    super(owner, instance, isSingleton, isModifiable);
  }

  /**
   * create an instance of the key type
   * 
   * @param sequence
   *          the sequence id
   * @return an instance of the key type
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected K createKey(final long sequence) {
    final InstanceTest t;
    t = this.getOwner();
    if (t instanceof MapTest) {
      return ((K) (((MapTest) t).createKey(sequence)));
    }
    return ((K) (RandomUtils.longToObject(sequence, true)));
  }

  /**
   * create an instance of the value type
   * 
   * @param sequence
   *          the sequence id
   * @return an instance of the value type
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected V createValue(final long sequence) {
    final InstanceTest t;
    t = this.getOwner();
    if (t instanceof MapTest) {
      return ((V) (((MapTest) t).createKey(sequence)));
    }
    return ((V) (RandomUtils.longToObject(sequence, false)));
  }

  /**
   * Can entries of this map be compared to entries of other maps?
   * 
   * @return {@code true} if so, {@code false} otherwise
   */
  @SuppressWarnings("rawtypes")
  protected boolean canCompareForeignMapEntries() {
    InstanceTest it;
    it = this.getOwner();
    if (it instanceof MapTest) {
      return ((MapTest) it).canCompareForeignMapEntries();
    }
    return true;
  }

  /**
   * Test whether the {@link java.lang.Object#toString()} method works
   */
  @Test(timeout = 3600000)
  public void testToString() {
    final MT map;
    final String s;

    map = this.getInstance();

    s = map.toString();
    Assert.assertNotNull(s);
    Assert.assertTrue(map.isEmpty() || (s.length() > 0));
  }

  /**
   * Test whether the {@link java.util.Collection#hashCode()} method works
   */
  @Test(timeout = 3600000)
  public void testHashCode() {
    this.getInstance().hashCode();
  }

  /**
   * Test whether the {@link java.util.Collection#equals(Object)} method
   * works
   */
  @Test(timeout = 3600000)
  public void testEqualsDummy() {
    final MT map;

    map = this.getInstance();
    Assert.assertFalse(map.equals(null));
    Assert.assertTrue(map.equals(map));
    Assert.assertFalse(map.equals(new Object()));
  }

  /**
   * Test whether the {@link java.util.Collection#equals(Object)} method
   * works
   */
  @Test(timeout = 3600000)
  public void testEqualsAndHashCodeSameMap() {
    final MT m;
    final Map<K, V> map;

    m = this.getInstance();
    map = new HashMap<>();
    map.putAll(m);

    map.equals(m);
    m.equals(map);

    Assert.assertEquals(map.hashCode(), m.hashCode());
  }

  /**
   * Test whether the collection serializes and deserializes properly
   */
  @Test(timeout = 3600000)
  @SuppressWarnings("unchecked")
  public void testSerialization() {
    final MT map, map3;
    final Map<K, V> data;
    Object map2;
    K key;
    V value;

    map = this.getInstance();
    if (map instanceof Serializable) {
      data = new HashMap<>();

      for (final Map.Entry<K, V> entry : map.entrySet()) {
        if ((entry != null)//
            && (!((entry instanceof Serializable)//
                || (entry instanceof Externalizable) || //
                (((key = entry.getKey()) != null) && //
                (!((key instanceof Serializable) || //
                (key instanceof Externalizable)))) || //
            (((value = entry.getValue()) != null) && //
            (!((value instanceof Serializable) || //
            (value instanceof Externalizable))))))) {
          return;
        }
      }
      data.putAll(map);

      map2 = TestBase.serializeDeserialize(map);

      Assert.assertTrue(map.getClass().isInstance(map2));
      map3 = ((MT) (map2));

      if (this.isSingleton()) {
        Assert.assertSame(map3, map);
      } else {
        Assert.assertTrue(map3.equals(map));
        Assert.assertTrue(map.equals(map3));
      }

      this.__testCollectionFaces(map3);

      data.equals(map3);
    }
  }

  /** run all tests for the collection faces */
  @Test(timeout = 3600000)
  public void testCollectionFaces() {
    this.__testCollectionFaces(this.getInstance());
  }

  /**
   * test add and remove
   * 
   * @param depth
   *          the depth
   * @param random
   *          the randomizer
   * @param id
   *          the key counter
   * @return the new key counter
   */
  private long __testPutRemove(final int depth, final long id,
      final Random random) {
    final MT map;
    final HashMap<K, V> put;
    int i, j, count, origSize;
    long z;
    K key;
    V value;

    map = this.getInstance();
    put = new HashMap<>();
    origSize = map.size();
    z = (id);
    for (i = 0; i < 5; i++) {
      count = 1 + random.nextInt(100);
      put.clear();

      for (j = 0; j < count; j++) {
        key = this.createKey(++z);
        value = this.createValue(random.nextLong());
        Assert.assertNull(put.put(key, value));
        Assert.assertNull(map.put(key, value));
      }

      Assert.assertEquals(put.size(), count);
      Assert.assertEquals(map.size(), (origSize + count));

      if (depth > 0) {
        z = this.__testPutRemove(depth - 1, ++z, random);
      } else {
        this.applyTestsToInstance(map, this.isSingleton(), false);
      }

      for (final Map.Entry<K, V> entry : put.entrySet()) {
        key = entry.getKey();
        value = entry.getValue();
        Assert.assertTrue(map.containsKey(key));
        Assert.assertTrue(map.containsValue(value));
        Assert.assertTrue(map.keySet().contains(key));
        if (this.canCompareForeignMapEntries()) {
          Assert.assertTrue(map.entrySet().contains(entry));
        }
        Assert.assertTrue(map.values().contains(value));
        Assert.assertEquals(value, map.remove(key));
      }

      Assert.assertEquals(map.size(), origSize);
    }

    return (z + 1);
  }

  /** test add and remove */
  @Test(timeout = 3600000)
  public void testPutRemove() {
    if (!(this.isModifiable())) {
      return;
    }
    this.__testPutRemove(4, 0, new Random());
  }

  /** test add and remove */
  @Test(timeout = 3600000)
  @SuppressWarnings("unchecked")
  public void testPutRemoveWithNull() {
    final MT map;
    int origSize, i;
    final Random r;
    final K[] keys;
    V oldValue, newValue;
    long sequence;

    if (!(this.isModifiable())) {
      return;
    }

    map = this.getInstance();
    origSize = map.size();
    r = new Random();
    sequence = r.nextLong();

    keys = ((K[]) (new Object[100]));
    for (i = keys.length; (--i) > 0;) {
      keys[i] = this.createKey(sequence++);
    }
    keys[0] = null;

    for (final K key : keys) {

      for (i = 100; (--i) >= 0;) {
        Assert.assertFalse(map.containsKey(key));
        Assert.assertEquals(origSize, map.size());

        oldValue = null;
        newValue = this.createValue(r.nextLong());
        Assert.assertSame(oldValue, map.put(key, newValue));
        Assert.assertEquals(origSize + 1, map.size());
        Assert.assertTrue(map.containsKey(key));
        Assert.assertSame(newValue, map.get(key));

        oldValue = newValue;
        newValue = this.createValue(r.nextLong());
        Assert.assertSame(oldValue, map.put(key, newValue));
        Assert.assertEquals(origSize + 1, map.size());
        Assert.assertTrue(map.containsKey(key));
        Assert.assertSame(newValue, map.get(key));

        Assert.assertTrue(map.containsKey(key));
        Assert.assertEquals(origSize + 1, map.size());
        Assert.assertSame(newValue, map.remove(key));
        Assert.assertEquals(origSize, map.size());
        Assert.assertFalse(map.containsKey(key));

        oldValue = null;
        newValue = this.createValue(r.nextLong());
        Assert.assertSame(oldValue, map.put(key, newValue));
        Assert.assertTrue(map.containsKey(key));
        Assert.assertSame(newValue, map.get(key));

        oldValue = newValue;
        newValue = this.createValue(r.nextLong());
        Assert.assertSame(oldValue, map.put(key, newValue));
        Assert.assertEquals(origSize + 1, map.size());
        Assert.assertTrue(map.containsKey(key));
        Assert.assertSame(newValue, map.get(key));

        oldValue = newValue;
        newValue = null;
        Assert.assertSame(oldValue, map.put(key, newValue));
        Assert.assertEquals(origSize + 1, map.size());
        Assert.assertTrue(map.containsKey(key));
        Assert.assertSame(newValue, map.get(key));

        oldValue = newValue;
        newValue = this.createValue(r.nextLong());
        Assert.assertSame(oldValue, map.put(key, newValue));
        Assert.assertEquals(origSize + 1, map.size());
        Assert.assertTrue(map.containsKey(key));
        Assert.assertSame(newValue, map.get(key));

        Assert.assertTrue(map.containsKey(key));
        Assert.assertEquals(origSize + 1, map.size());
        Assert.assertSame(newValue, map.remove(key));
        Assert.assertEquals(origSize, map.size());
        Assert.assertFalse(map.containsKey(key));
      }
    }

    Assert.assertEquals(origSize, map.size());
  }

  /**
   * run all tests for the collection faces
   * 
   * @param map
   *          the map
   */
  private final void __testCollectionFaces(final MT map) {
    new CollectionTest<>(null, map.keySet(), false, false)
        .validateInstance();
    new CollectionTest<>(null, map.values(), false, false)
        .validateInstance();
    new CollectionTest<>(null, map.entrySet(), false, false)
        .validateInstance();
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    this.testToString();
    this.testHashCode();
    this.testEqualsDummy();
    this.testEqualsAndHashCodeSameMap();
    this.testSerialization();
    this.testPutRemove();
    this.testPutRemoveWithNull();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected InstanceTest<?> createTestForInstance(final Object collection,
      final boolean isSingleton, final boolean isModifiable) {
    if (collection instanceof Map) {
      return ((new MapTest(this, ((Map) collection), isSingleton,
          isModifiable)));
    }
    return super.createTestForInstance(collection, isSingleton,
        isModifiable);
  }

  /** {@inheritDoc} */
  @Override
  protected void compareTwoCopies(final Object a, final Object b) {
    Assert.assertEquals(a, b);
    Assert.assertEquals(a.hashCode(), b.hashCode());
    Assert.assertTrue(a.equals(b));
    Assert.assertTrue(b.equals(a));
  }
}
