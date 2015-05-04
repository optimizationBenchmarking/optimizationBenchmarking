package test.junit.org.optimizationBenchmarking.utils.collections.lists;

import java.util.Random;

import org.junit.Ignore;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

import test.junit.InstanceTest;
import test.junit.org.optimizationBenchmarking.utils.collections.BasicListTest;

/**
 * a test for collection classes
 *
 * @param <ET>
 *          the element type
 * @param <T>
 *          the collection type
 */
@Ignore
public class ArrayListViewTestBase<ET, T extends ArrayListView<ET>>
extends BasicListTest<ET, ET[], T> {

  /** create */
  public ArrayListViewTestBase() {
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
  public ArrayListViewTestBase(final ArrayListViewTestBase<ET, T> owner,
      final T instance, final boolean isSingleton) {
    super(owner, instance, isSingleton, false);
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean isModifiable() {
    return false;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected InstanceTest<?> createTestForInstance(final Object list,
      final boolean isSingleton, final boolean isModifiable) {
    if (list instanceof ArrayListView) {
      return new ArrayListViewTestBase(this, ((ArrayListView) list),
          isSingleton);
    }
    return super.createTestForInstance(list, isSingleton, isModifiable);
  }

  /**
   * create a random but sorted list of objects
   *
   * @return the random sorted list of objects
   */
  static final Object[] _randomList() {
    int i;
    Object[] l;
    Random r;
    long z;

    r = new Random();
    z = r.nextInt();
    l = new Object[r.nextInt(30)];
    for (i = l.length; (--i) >= 0;) {
      z -= (1 + r.nextInt(100));
      l[i] = Long.valueOf(z);
    }

    return l;
  }
}
