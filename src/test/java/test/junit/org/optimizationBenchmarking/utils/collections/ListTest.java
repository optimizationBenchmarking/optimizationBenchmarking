package test.junit.org.optimizationBenchmarking.utils.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

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
public class ListTest<ET, T extends List<ET>> extends
CollectionTest<ET, T> {

  /** create */
  public ListTest() {
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
  public ListTest(final ListTest<ET, T> owner, final T instance,
      final boolean isSingleton, final boolean isModifiable) {
    super(owner, instance, isSingleton, isModifiable);
  }

  /**
   * Test whether the {@link java.util.List#listIterator()} and
   * {@link java.util.Collection#toArray()} are compatible
   */
  @Test(timeout = 3600000)
  public void testToArrayAndListIteratorCompatible() {
    final T collection;
    final Object[] data;
    final ListIterator<ET> it;
    final int size;
    final Random r;
    int i, j;

    collection = this.getInstance();

    it = collection.listIterator();
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

    for (i = data.length; (--i) >= 0;) {
      Assert.assertTrue(it.hasPrevious());
      Assert.assertSame(data[i], it.previous());
    }

    i = 0;
    r = new Random();
    for (j = (10 * size); (--j) >= 0;) {
      if (r.nextBoolean()) {
        if (i > 0) {
          Assert.assertTrue(it.hasPrevious());
          Assert.assertSame(data[--i], it.previous());
          continue;
        }
      }

      if (i < size) {
        Assert.assertTrue(it.hasNext());
        Assert.assertSame(data[i++], it.next());
      }
    }
  }

  /**
   * Test whether the {@link java.util.List#listIterator()} and
   * {@link java.util.Collection#isEmpty()} are compatible
   */
  @Test(timeout = 3600000)
  public void testIteratorAndListIteratorCompatible() {
    final T collection;
    final ListIterator<ET> lit;
    final Iterator<ET> it;
    boolean n;

    collection = this.getInstance();

    lit = collection.listIterator();
    Assert.assertNotNull(lit);
    Assert.assertTrue(lit.hasNext() ^ collection.isEmpty());

    it = collection.iterator();
    Assert.assertNotNull(it);
    Assert.assertTrue(it.hasNext() ^ collection.isEmpty());

    for (;;) {
      n = it.hasNext();
      Assert.assertTrue(n == lit.hasNext());
      if (!n) {
        break;
      }
      Assert.assertSame(it.next(), lit.next());
    }
  }

  /**
   * Test whether the {@link java.util.List#equals(Object)} method works
   */
  @Test(timeout = 3600000)
  public void testEqualsToArrayList() {
    final T collection;
    ArrayList<Object> al;
    Object old;
    int i;

    collection = this.getInstance();

    al = new ArrayList<>(collection.size());
    al.addAll(collection);
    Assert.assertTrue(al.equals(collection));
    Assert.assertTrue(collection.equals(al));
    Assert.assertEquals(al.hashCode(), collection.hashCode());

    al.add(null);
    Assert.assertFalse(al.equals(collection));
    Assert.assertFalse(collection.equals(al));
    al.remove(al.size() - 1);

    for (i = al.size(); (--i) >= 0;) {
      old = al.set(i, new Object());
      Assert.assertFalse(al.equals(collection));
      Assert.assertFalse(collection.equals(al));
      al.set(i, old);
    }
  }

  /**
   * Test whether the {@link java.util.Collection#toArray()}
   * {@link java.util.List#get(int)} are compatible
   */
  @Test(timeout = 3600000)
  public void testGetAndToArrayCompatible() {
    final T collection;
    final Object[] data;
    final int size;
    int i;

    collection = this.getInstance();

    size = collection.size();
    data = collection.toArray();
    Assert.assertNotNull(data);
    Assert.assertEquals(size, data.length);

    i = 0;
    for (final Object o : data) {
      Assert.assertSame(o, collection.get(i++));
    }
  }

  /**
   * Test whether the {@link java.util.Collection#toArray()}
   * {@link java.util.List#indexOf(Object)} are compatible
   */
  @Test(timeout = 3600000)
  public void testIndexOfAndToArrayCompatible() {
    final T collection;
    final Object[] data;
    final int size;
    int i, idx;

    collection = this.getInstance();

    size = collection.size();
    data = collection.toArray();
    Assert.assertNotNull(data);
    Assert.assertEquals(size, data.length);

    for (i = size; (--i) >= 0;) {
      idx = collection.indexOf(data[i]);
      Assert.assertTrue(idx >= 0);
      Assert.assertTrue(idx < size);
      Assert.assertTrue(idx <= i);
      Assert.assertEquals(collection.get(idx), data[i]);
    }
  }

  /**
   * Test whether the {@link java.util.List#indexOf(Object)} returns false
   * for non-contained objects
   */
  @Test(timeout = 3600000)
  public void testIndexOfFalse() {
    final T collection;

    collection = this.getInstance();
    Assert.assertEquals(collection.indexOf(new Object()), (-1));
  }

  /**
   * Test whether the {@link java.util.Collection#toArray()}
   * {@link java.util.List#lastIndexOf(Object)} are compatible
   */
  @Test(timeout = 3600000)
  public void testLastIndexOfAndToArrayCompatible() {
    final T collection;
    final Object[] data;
    final int size;
    int i, idx;

    collection = this.getInstance();

    size = collection.size();
    data = collection.toArray();
    Assert.assertNotNull(data);
    Assert.assertEquals(size, data.length);

    for (i = size; (--i) >= 0;) {
      idx = collection.lastIndexOf(data[i]);
      Assert.assertTrue(idx >= 0);
      Assert.assertTrue(idx < size);
      Assert.assertTrue(idx >= i);
      Assert.assertEquals(collection.get(idx), data[i]);
    }
  }

  /**
   * Test whether the {@link java.util.List#lastIndexOf(Object)} returns
   * false for non-contained objects
   */
  @Test(timeout = 3600000)
  public void testLastIndexOfFalse() {
    final T collection;

    collection = this.getInstance();

    Assert.assertEquals(collection.lastIndexOf(new Object()), (-1));
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected InstanceTest<?> createTestForInstance(final Object collection,
      final boolean isSingleton, final boolean isModifiable) {
    if (collection instanceof List) {
      return ((new ListTest(this, ((List) collection), isSingleton,
          isModifiable)));
    }
    return super.createTestForInstance(collection, isSingleton,
        isModifiable);
  }

  /**
   * Test sub lists
   */
  @Test(timeout = 3600000)
  public void testSubList() {
    final T collection;
    final int size;
    final Random r;
    int i, a, b;

    if (!(this.isTopLevelTest())) {
      return;
    }

    collection = this.getInstance();
    size = collection.size();

    this.applyTestsToInstance(collection.subList(0, 0),//
        ((size <= 0) ? this.isSingleton() : false),//
        this.isModifiable());

    if (size > 0) {
      this.applyTestsToInstance(collection.subList(0, size),
          this.isSingleton(), this.isModifiable());
      if (size > 1) {
        r = new Random();

        for (i = (3 * size); (--i) >= 0;) {
          a = r.nextInt(size + 1);
          b = r.nextInt(size + 1);

          this.applyTestsToInstance(collection.subList(//
              Math.min(a, b), Math.max(a, b)), false, this.isModifiable());
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    this.testEqualsToArrayList();
    this.testGetAndToArrayCompatible();
    this.testIteratorAndListIteratorCompatible();
    this.testToArrayAndListIteratorCompatible();
    this.testIndexOfAndToArrayCompatible();
    this.testIndexOfFalse();
    this.testLastIndexOfAndToArrayCompatible();
    this.testLastIndexOfFalse();
    this.testSubList();
  }
}
