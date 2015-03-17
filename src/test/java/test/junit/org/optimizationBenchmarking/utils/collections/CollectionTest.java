package test.junit.org.optimizationBenchmarking.utils.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.random.RandomUtils;

import test.junit.InstanceTest;

/**
 * a test for collection classes
 * 
 * @param <ET>
 *          the element type
 * @param <T>
 *          the collection type
 */
@Ignore
public class CollectionTest<ET, T extends Collection<ET>> extends
    InstanceTest<T> {

  /** create */
  public CollectionTest() {
    this(null, null, false, true);
  }

  /**
   * create
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
  public CollectionTest(final CollectionTest<ET, T> owner,
      final T instance, final boolean isSingleton,
      final boolean isModifiable) {
    super(owner, instance, isSingleton, isModifiable);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected InstanceTest<?> createTestForInstance(final Object collection,
      final boolean isSingleton, final boolean isModifiable) {
    if (collection instanceof Collection) {
      return ((new CollectionTest(this, ((Collection) collection),
          isSingleton, isModifiable)));
    }
    return super.createTestForInstance(collection, isSingleton,
        isModifiable);
  }

  /**
   * create an instance of the element type
   * 
   * @param sequence
   *          the sequence value
   * @return an instance of the element type
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected ET createElement(final long sequence) {
    final InstanceTest<T> owner;

    owner = this.getOwner();
    if (owner instanceof CollectionTest) {
      return ((ET) (((CollectionTest) (owner)).createElement(sequence)));
    }

    return ((ET) (RandomUtils.longToObject(sequence, true)));
  }

  /**
   * Test whether the {@link java.util.Collection#size()} and
   * {@link java.util.Collection#isEmpty()} are compatible
   */
  @Test(timeout = 3600000)
  public void testSizeAndEmptyCompatible() {
    final T collection;

    collection = this.getInstance();
    Assert.assertTrue((collection.size() > 0) ^ collection.isEmpty());
  }

  /**
   * Test whether the {@link java.util.Collection#iterator()} and
   * {@link java.util.Collection#toArray()} are compatible
   */
  @Test(timeout = 3600000)
  public void testToArrayAndIteratorCompatible() {
    final T collection;
    final Object[] data;
    final Iterator<ET> it;
    final int size;

    collection = this.getInstance();

    it = collection.iterator();
    Assert.assertNotNull(it);
    Assert.assertTrue(it.hasNext() ^ collection.isEmpty());

    size = collection.size();
    data = collection.toArray();
    Assert.assertNotNull(data);
    Assert.assertEquals(size, data.length);

    for (final Object o : data) {
      Assert.assertTrue(it.hasNext());
      Assert.assertSame(o, it.next());
    }
  }

  /**
   * Test whether the {@link java.util.Collection#toArray()}
   * {@link java.util.Collection#toArray(Object[])} are compatible
   */
  @Test(timeout = 3600000)
  public void testToArrayAndToArrayCompatible() {
    final T collection;
    final Object[] data, data2, data3, data5;
    Object[] data4;
    final int size;
    int i;

    collection = this.getInstance();

    size = collection.size();
    data = collection.toArray();
    Assert.assertNotNull(data);
    Assert.assertEquals(size, data.length);

    data2 = new Object[size];
    Assert.assertSame(data2, collection.toArray(data2));

    data3 = new Object[size + 1];
    Assert.assertSame(data3, collection.toArray(data3));

    if (size > 0) {
      data4 = new Object[size - 1];
      Assert.assertNotSame(data4, (data5 = collection.toArray(data4)));
      Assert.assertNotNull(data5);
    } else {
      data4 = data5 = null;
    }

    for (i = size; (--i) >= 0;) {
      Assert.assertSame(data[i], data2[i]);
      Assert.assertSame(data2[i], data3[i]);
      if (data5 != null) {
        Assert.assertSame(data3[i], data5[i]);
      }
    }

    Assert.assertNull(data3[size]);
  }

  /**
   * Test whether the {@link java.util.Collection#toArray()}
   * {@link java.util.Collection#contains(Object)} are compatible
   */
  @Test(timeout = 3600000)
  public void testContainsAndToArrayCompatible() {
    final T collection;
    final Object[] data;
    final int size;

    collection = this.getInstance();

    size = collection.size();
    data = collection.toArray();
    Assert.assertNotNull(data);
    Assert.assertEquals(size, data.length);

    for (final Object o : data) {
      Assert.assertTrue(collection.contains(o));
    }
  }

  /**
   * Test whether the {@link java.util.Collection#toArray()}
   * {@link java.util.Collection#containsAll(Collection)} are compatible
   */
  @Test(timeout = 3600000)
  public void testContainsAllAndToArrayCompatible() {
    final T collection;
    final Object[] data;
    final int size;
    final ArrayList<Object> al;

    collection = this.getInstance();

    size = collection.size();
    data = collection.toArray();
    Assert.assertNotNull(data);
    Assert.assertEquals(size, data.length);

    Assert.assertTrue(collection.containsAll(Arrays.asList(data)));

    al = new ArrayList<>(size);
    al.addAll(collection);
    Assert.assertTrue(collection.containsAll(al));
  }

  /**
   * Test whether the {@link java.util.Collection#iterator()}
   * {@link java.util.Collection#contains(Object)} are compatible
   */
  @Test(timeout = 3600000)
  public void testIteratorAndContainsCompatible() {
    final T collection;

    collection = this.getInstance();
    for (final ET e : collection) {
      Assert.assertTrue(collection.contains(e));
    }
  }

  /**
   * Test whether the {@link java.util.Collection#contains(Object)} returns
   * false for objects which are not contained
   */
  @Test(timeout = 3600000)
  public void testContainsFalse() {
    final T collection;

    collection = this.getInstance();
    Assert.assertFalse(collection.contains(new Object()));
  }

  /**
   * Test whether the {@link java.lang.Object#toString()} method works
   */
  @Test(timeout = 3600000)
  public void testToStringNotEmpty() {
    final T collection;
    final String s;

    collection = this.getInstance();

    s = collection.toString();
    Assert.assertNotNull(s);
    Assert.assertTrue(collection.isEmpty() || (s.length() > 0));
  }

  /**
   * do the recursive add and iterator remove
   * 
   * @param random
   *          the randomizer
   * @param depth
   *          the depth
   */
  private final void __testAddAndIteratorRemove(final int depth,
      final Random random) {
    final T collection;
    final int origSize;
    final Object[] v;
    long sequence;
    Iterator<ET> it;
    ET et;
    int i, j, total, count, added;

    if (!(this.isModifiable())) {
      return;
    }

    collection = this.getInstance();
    origSize = collection.size();
    v = new Object[50];
    sequence = random.nextLong();

    for (i = 0; i < 7; i++) {
      count = (1 + random.nextInt(v.length));
      added = 0;

      for (j = 0; j < count; j++) {
        et = this.createElement(sequence++);
        if (collection.add(et)) {
          v[added++] = et;
        }
      }

      if (depth > 0) {
        if (random.nextBoolean()) {
          this.__testAddAndIteratorRemove(depth - 1, random);
        } else {
          this.__testAddAllAndItertorRemove(depth - 1, random);
        }
      } else {
        if (i == 0) {
          this.applyTestsToInstance(collection);
        }
      }

      total = (origSize + added);
      Assert.assertEquals(total, collection.size());
      it = collection.iterator();
      for (j = 0; j < total; j++) {
        Assert.assertTrue(it.hasNext());
        et = it.next();
        if (j >= origSize) {
          Assert.assertSame(et, v[j - origSize]);
          it.remove();
        }
      }
    }
  }

  /** test the add method */
  @Test(timeout = 3600000)
  public void testAddAndItertorRemove() {
    if (!(this.isModifiable())) {
      return;
    }
    this.__testAddAndIteratorRemove(2, new Random());
  }

  /**
   * do the recursive add and iterator remove
   * 
   * @param random
   *          the randomizer
   * @param depth
   *          the depth
   */
  private final void __testAddAllAndItertorRemove(final int depth,
      final Random random) {
    final T collection;
    final int origSize;
    final ArrayList<ET> v;
    long sequence;
    boolean b;
    Iterator<ET> it;
    ET et;
    int i, j, k, total, count;

    if (!(this.isModifiable())) {
      return;
    }

    collection = this.getInstance();
    origSize = collection.size();
    v = new ArrayList<>();
    sequence = random.nextLong();

    for (i = 0; i < 7; i++) {
      count = (1 + random.nextInt(50));
      v.clear();

      for (j = 0; j < count; j++) {
        v.add(this.createElement(sequence++));
      }

      b = collection.addAll(v);

      total = collection.size();
      if (b) {
        Assert.assertTrue(total > origSize);
        Assert.assertTrue((total - origSize) <= count);
      } else {
        Assert.assertEquals(total, origSize);
      }

      if (depth > 0) {
        if (random.nextBoolean()) {
          this.__testAddAndIteratorRemove(depth - 1, random);
        } else {
          this.__testAddAllAndItertorRemove(depth - 1, random);
        }
      } else {
        if (i == 0) {
          this.applyTestsToInstance(collection);
        }
      }

      it = collection.iterator();
      k = 0;
      for (j = 0; j < total; j++) {
        Assert.assertTrue(it.hasNext());
        et = it.next();
        if (j >= origSize) {
          while (et != v.get(k)) {
            k++;
          }
          Assert.assertSame(et, v.get(k++));
          it.remove();
        }
      }
    }
  }

  /** test the add method */
  @Test(timeout = 3600000)
  public void testAddAllAndItertorRemove() {
    if (!(this.isModifiable())) {
      return;
    }

    this.__testAddAllAndItertorRemove(2, new Random());
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();

    this.testSizeAndEmptyCompatible();
    this.testIteratorAndContainsCompatible();
    this.testToArrayAndIteratorCompatible();
    this.testToArrayAndToArrayCompatible();
    this.testContainsAllAndToArrayCompatible();
    this.testContainsAndToArrayCompatible();
    this.testContainsFalse();
    this.testToStringNotEmpty();

    this.testAddAndItertorRemove();
    this.testAddAllAndItertorRemove();
  }

}
