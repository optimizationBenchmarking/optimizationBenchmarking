package test.junit.org.optimizationBenchmarking.utils.collections.lists;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

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
public class ArraySetViewTestBase<ET, T extends ArraySetView<ET>> extends
    ArrayListViewTestBase<ET, T> {

  /** create */
  public ArraySetViewTestBase() {
    this(null, null, false);
  }

  /**
   * create
   *
   * @param owner
   *          the owner
   * @param isSingleton
   *          is this a singleton-based tests?
   * @param instance
   *          the instance, or {@code null} if unspecified
   */
  public ArraySetViewTestBase(final ArraySetViewTestBase<ET, T> owner,
      final T instance, final boolean isSingleton) {
    super(owner, instance, isSingleton);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected InstanceTest<?> createTestForInstance(final Object instance,
      final boolean isSingleton, final boolean isModifiable) {
    if (instance instanceof ArraySetView) {
      return new ArraySetViewTestBase(this, ((ArraySetView) instance),
          isSingleton);
    }
    return super
        .createTestForInstance(instance, isSingleton, isModifiable);
  }

  /** Test if the elements in the set are truly sorted */
  @SuppressWarnings({ "unchecked", "rawtypes", "null" })
  @Test(timeout = 3600000)
  public void testSorted() {
    Object prev;
    boolean first;

    first = true;
    prev = null;

    for (final Object cur : this.getInstance()) {
      Assert.assertNotNull(cur);
      if (first) {
        first = false;
      } else {
        Assert.assertTrue(((Comparable) prev).compareTo(cur) <= 0);
        Assert.assertTrue(((Comparable) cur).compareTo(prev) >= 0);
      }
      prev = cur;
    }
  }

  /** Test if the elements in the set are truly sorted */
  @Test(timeout = 3600000)
  public void testUnique() {
    final T collection;
    Object[] data;
    int i, j;

    collection = this.getInstance();
    data = collection.toArray();
    Assert.assertNotNull(data);
    Assert.assertEquals(data.length, collection.size());

    for (i = data.length; (--i) > 0;) {
      for (j = i; (--j) >= 0;) {
        Assert.assertFalse(data[i].equals(data[j]));
        Assert.assertFalse(data[j].equals(data[i]));
      }
    }

  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    this.testUnique();
    this.testSorted();
  }
}
