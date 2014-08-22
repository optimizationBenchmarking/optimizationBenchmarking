package test.junit.org.optimizationBenchmarking.utils.collections;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.collections.lists.BasicList;
import org.optimizationBenchmarking.utils.collections.visitors.CollectingVisitor;

import test.junit.InstanceTest;

/**
 * a test for collection classes
 * 
 * @param <ET>
 *          the element type
 * @param <AT>
 *          the array type
 * @param <T>
 *          the collection type
 */
@Ignore
public class BasicListTest<ET, AT, T extends BasicList<ET>> extends
    ListTest<ET, T> {

  /** create */
  public BasicListTest() {
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
  public BasicListTest(final BasicListTest<ET, AT, T> owner,
      final T instance, final boolean isSingleton,
      final boolean isModifiable) {
    super(owner, instance, isSingleton, isModifiable);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected InstanceTest<?> createTestForInstance(final Object collection,
      final boolean isSingleton, final boolean isModifiable) {
    if (collection instanceof BasicList) {
      return ((new BasicListTest(this, ((BasicList) collection),
          isSingleton, isModifiable)));
    }
    return super.createTestForInstance(collection, isSingleton,
        isModifiable);
  }

  /** visit all the elements */
  @Test(timeout = 3600000)
  public void testVisitAll() {
    final T collection;
    final CollectingVisitor<ET> vis;

    collection = this.getInstance();
    vis = new CollectingVisitor<>();
    Assert.assertTrue(collection.visit(vis));
    Assert.assertTrue(vis.equals(collection));
    Assert.assertTrue(collection.equals(vis));
  }

  /** perform random selections */
  @Test(timeout = 3600000)
  public void testRandomSelection() {
    final T collection;
    final int size;
    final _SelectionPredicate sel;
    final Random r;
    List<ET> selected;
    int i, j, k;

    collection = this.getInstance();
    size = collection.size();
    sel = new _SelectionPredicate(size);
    r = new Random();

    for (i = (5 * (size + 1)); (--i) >= 0;) {

      switch (i) {
        case 0: {
          Arrays.fill(sel.m_sel, true);
          break;
        }
        case 1: {
          Arrays.fill(sel.m_sel, false);
          break;
        }
        default: {
          for (j = size; (--j) >= 0;) {
            sel.m_sel[j] = r.nextBoolean();
          }
        }
      }
      sel.m_idx = 0;
      sel.m_true = 0;

      selected = collection.select(sel);
      Assert.assertNotNull(selected);
      Assert.assertEquals(sel.m_true, selected.size());

      k = 0;
      for (j = 0; j < size; j++) {
        if (sel.m_sel[j]) {
          Assert.assertSame(selected.get(k++), collection.get(j));
        }
      }

      this.applyTestsToInstance(selected,//
          (this.isSingleton() && (selected.size() == collection.size())),//
          (selected.size() == collection.size()) ? this.isModifiable()
              : false);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    this.testVisitAll();
    this.testRandomSelection();
  }
}
