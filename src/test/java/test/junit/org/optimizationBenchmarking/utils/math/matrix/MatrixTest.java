package test.junit.org.optimizationBenchmarking.utils.math.matrix;

import java.util.Iterator;
import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

import test.junit.InstanceTest;

/**
 * A test for matrix data structures.
 * 
 * @param <MT>
 *          the matrix type
 */
@Ignore
public class MatrixTest<MT extends IMatrix> extends InstanceTest<MT> {

  /** the constructor */
  protected MatrixTest() {
    this(null, null, false);
  }

  /**
   * Create the matrix test
   * 
   * @param owner
   *          the owner
   * @param isSingleton
   *          is this a singleton-based tests?
   * @param instance
   *          the instance, or {@code null} if unspecified
   */
  public MatrixTest(final MatrixTest<MT> owner, final MT instance,
      final boolean isSingleton) {
    super(owner, instance, isSingleton, false);
  }

  /** test the matrix dimensions */
  @Test(timeout = 3600000)
  public void testMatrixDimensionsAndGetters() {
    MT matrix, old;
    int n, m;
    int i, j, z;
    double d;
    long l;
    boolean b;

    old = null;
    for (z = 100; (--z) >= 0;) {
      matrix = this.getInstance();

      Assert.assertNotNull(matrix);
      if (matrix == old) {
        return;
      }
      old = matrix;

      m = matrix.m();
      Assert.assertTrue(m > 0);

      n = matrix.n();
      Assert.assertTrue(n > 0);

      b = matrix.isIntegerMatrix();

      // test all valid indices
      for (i = 0; i < m; i++) {
        for (j = 0; j < n; j++) {

          d = matrix.getDouble(i, j);
          l = matrix.getLong(i, j);
          if (b && ((l & 0xfffff00000000000L) == 0L)) {
            Assert.assertEquals(((long) (d)), l);
            Assert.assertTrue(d == l);
          }
        }
      }

      matrix.getDouble((m - 1), (n - 1));
      matrix.getLong((m - 1), (n - 1));

      // test invalid indices
      try {
        matrix.getDouble(-1, 0);
        Assert.fail("Illegal matrix access: -1,0"); //$NON-NLS-1$
      } catch (final Throwable t) {
        //
      }

      try {
        matrix.getLong(-1, 0);
        Assert.fail("Illegal matrix access: -1,0"); //$NON-NLS-1$
      } catch (final Throwable t) {
        //
      }

      try {
        matrix.getDouble(0, -1);
        Assert.fail("Illegal matrix access: 0,-1"); //$NON-NLS-1$
      } catch (final Throwable t) {
        //
      }

      try {
        matrix.getLong(0, -1);
        Assert.fail("Illegal matrix access: 0,-1"); //$NON-NLS-1$
      } catch (final Throwable t) {
        //
      }

      try {
        matrix.getDouble(-1, -1);
        Assert.fail("Illegal matrix access: -1,-1"); //$NON-NLS-1$
      } catch (final Throwable t) {
        //
      }

      try {
        matrix.getLong(-1, -1);
        Assert.fail("Illegal matrix access: -1,-1"); //$NON-NLS-1$
      } catch (final Throwable t) {
        //
      }

      try {
        matrix.getDouble(m, 0);
        Assert.fail("Illegal matrix access: m,0"); //$NON-NLS-1$
      } catch (final Throwable t) {
        //
      }

      try {
        matrix.getLong(m, 0);
        Assert.fail("Illegal matrix access: m,0"); //$NON-NLS-1$
      } catch (final Throwable t) {
        //
      }

      try {
        matrix.getDouble(0, n);
        Assert.fail("Illegal matrix access: 0,n"); //$NON-NLS-1$
      } catch (final Throwable t) {
        //
      }

      try {
        matrix.getLong(0, n);
        Assert.fail("Illegal matrix access: 0,n"); //$NON-NLS-1$
      } catch (final Throwable t) {
        //
      }

      try {
        matrix.getDouble(m, n);
        Assert.fail("Illegal matrix access: m,n"); //$NON-NLS-1$
      } catch (final Throwable t) {
        //
      }

      try {
        matrix.getLong(m, n);
        Assert.fail("Illegal matrix access: m,n"); //$NON-NLS-1$
      } catch (final Throwable t) {
        //
      }
    }
  }

  /**
   * check if two matrices have the same integer property
   * 
   * @param a
   *          the first matrix
   * @param b
   *          the second matrix
   */
  @SuppressWarnings("rawtypes")
  protected void checkIntegerMatrixSame(final IMatrix a, final IMatrix b) {
    InstanceTest<?> o;

    o = this.getOwner();
    if ((o != null) && (o instanceof MatrixTest)) {
      ((MatrixTest) o).checkIntegerMatrixSame(a, b);
    } else {
      Assert.assertTrue(a.isIntegerMatrix() == b.isIntegerMatrix());
    }
  }

  /** test the transposed matrix */
  @Test(timeout = 3600000)
  public void testMatrixTransposed() {
    IMatrix a, b, old;
    int m, n, i, j, z;

    old = null;
    for (z = 30; (--z) >= 0;) {
      a = this.getInstance();
      Assert.assertNotNull(a);
      if (a == old) {
        return;
      }
      old = a;

      b = a.transpose();
      Assert.assertNotNull(b);
      m = a.m();
      n = a.n();
      if ((m > 1) || (n > 1)) {
        Assert.assertNotSame(a, b);
      } else {
        if (this.isTopLevelTest()) {
          Assert.assertSame(a, b);
        }
      }

      this.checkIntegerMatrixSame(a, b);

      for (i = 0; i < m; i++) {
        for (j = 0; j < n; j++) {

          Assert.assertTrue(a.getDouble(i, j) == b.getDouble(j, i));
          Assert.assertEquals(a.getLong(i, j), b.getLong(j, i));

        }
      }

      this.applyTestsToInstance(b);

      // if (this.isTopLevelTest()) {
      // Assert.assertSame(b.transpose(), a);
      // }
    }
  }

  /** test the copied matrix */
  @Test(timeout = 3600000)
  public void testMatrixCopy() {
    IMatrix old, cur, copy;
    int z, i, j, m, n;
    boolean a, b;

    old = null;
    for (z = 10; (--z) >= 0;) {
      cur = this.getInstance();
      Assert.assertNotNull(cur);
      if (cur == old) {
        return;
      }
      old = cur;

      copy = cur.copy();
      Assert.assertEquals(m = cur.m(), copy.m());
      Assert.assertEquals(n = cur.n(), copy.n());

      if (copy.getClass() == cur.getClass()) {
        Assert.assertEquals(cur, copy);
        Assert.assertEquals(cur.hashCode(), copy.hashCode());
      } else {

        if (copy.isIntegerMatrix() && cur.isIntegerMatrix()) {
          for (i = m; (--i) >= 0;) {
            for (j = n; (--j) >= 0;) {
              Assert.assertEquals(cur.getLong(i, j), copy.getLong(i, j));
            }
          }
          Assert.assertEquals(cur.hashCode(), copy.hashCode());
        } else {
          if (cur.isIntegerMatrix() == copy.isIntegerMatrix()) {
            for (i = m; (--i) >= 0;) {
              for (j = n; (--j) >= 0;) {
                Assert.assertTrue(cur.getDouble(i, j) == copy.getDouble(i,
                    j));
              }
            }
          } else {
            a = b = true;
            for (i = m; (--i) >= 0;) {
              for (j = n; (--j) >= 0;) {
                a &= (cur.getDouble(i, j) == copy.getDouble(i, j));
                b &= (cur.getLong(i, j) == copy.getLong(i, j));
                Assert.assertTrue(a || b);
              }
            }
          }
        }

      }

      this.applyTestsToInstance(copy);
    }

  }

  /**
   * test a selection in the matrix
   * 
   * @param orig
   *          the original matrix
   * @param sel
   *          the selected matrix
   * @param selRows
   *          the row selection
   * @param selCols
   *          the column selection
   * @param depth
   *          the depth
   * @param rand
   *          the randomizer
   */
  private final void __testSelected(final IMatrix orig, final IMatrix sel,
      final int[] selRows, final int[] selCols, final Random rand,
      final int depth) {
    int i, j, k;
    boolean rows;
    int[] selSel, selDo, selOrig;

    for (i = selRows.length; (--i) >= 0;) {
      for (j = selCols.length; (--j) >= 0;) {

        Assert.assertTrue(sel.getDouble(i, j) == //
            orig.getDouble(selRows[i], selCols[j]));

        Assert.assertEquals(sel.getLong(i, j), //
            orig.getLong(selRows[i], selCols[j]));
      }
    }

    if (depth > 0) {
      outer: for (k = 3; (--k) >= 0;) {

        switch (k) {
          case 0: {
            this.__testSelected(orig.transpose(), sel.transpose(),
                selCols, selRows, rand, (depth - 1));
            continue outer;
          }

          case 1: {
            rows = false;
            selOrig = selCols;
            break;
          }

          default: {
            selOrig = selRows;
            rows = true;
            break;
          }
        }

        selSel = new int[1 + rand.nextInt(selOrig.length + 4)];
        selDo = new int[selSel.length];
        for (i = selSel.length; (--i) >= 0;) {
          selSel[i] = rand.nextInt(selOrig.length);
          selDo[i] = selOrig[selSel[i]];
        }

        this.__testSelected(orig,//
            (rows ? sel.selectRows(selSel) : sel.selectColumns(selSel)),//
            (rows ? selDo : selRows),//
            (rows ? selCols : selDo),//
            rand, (depth - 1));
      }
    }

    this.applyTestsToInstance(sel);
  }

  /** test the matrix selection */
  @Test(timeout = 3600000)
  public void testMatrixSelectionAndTranspose() {
    final Random rand;
    MT matrix, old;
    int i, z;
    int[] selRows, selCols;

    rand = new Random();

    old = null;
    for (z = 3; (--z) >= 0;) {
      matrix = this.getInstance();
      Assert.assertNotNull(matrix);

      if (matrix == old) {
        return;
      }
      old = matrix;

      i = matrix.m();
      selRows = new int[i];
      for (; (--i) >= 0;) {
        selRows[i] = i;
      }

      i = matrix.n();
      selCols = new int[i];
      for (; (--i) >= 0;) {
        selCols[i] = i;
      }

      this.__testSelected(matrix,//
          (rand.nextBoolean() ? matrix.selectColumns(selCols)//
              : matrix.selectRows(selRows)),//
          selRows, selCols, rand, //
          (this.isTopLevelTest() ? 6 : 1));
    }
  }

  /** test the iteration methods */
  @Test(timeout = 3600000)
  public void testMatrixIterate() {
    IMatrix cur, old, im;
    Iterator<IMatrix> it;
    int m, n, i, j, z;

    old = null;
    for (z = 30; (--z) >= 0;) {
      cur = this.getInstance();
      Assert.assertNotNull(cur);
      if (cur == old) {
        return;
      }
      old = cur;
      n = cur.n();
      m = cur.m();

      it = cur.iterateColumns();
      for (j = 0; j < n; j++) {

        Assert.assertTrue(it.hasNext());

        im = it.next();
        Assert.assertNotNull(im);

        Assert.assertEquals(1, im.n());
        Assert.assertEquals(m, im.m());

        for (i = 0; i < m; i++) {
          Assert.assertTrue(im.getDouble(i, 0) == cur.getDouble(i, j));
          Assert.assertEquals(im.getLong(i, 0), cur.getLong(i, j));
        }

        this.applyTestsToInstance(im);
      }

      Assert.assertFalse(it.hasNext());

      it = cur.iterateRows();
      for (i = 0; i < m; i++) {

        Assert.assertTrue(it.hasNext());

        im = it.next();
        Assert.assertNotNull(im);

        Assert.assertEquals(n, im.n());
        Assert.assertEquals(1, im.m());

        for (j = 0; j < n; j++) {
          Assert.assertTrue(im.getDouble(0, j) == cur.getDouble(i, j));
          Assert.assertEquals(im.getLong(0, j), cur.getLong(i, j));
        }

        this.applyTestsToInstance(im);
      }

      Assert.assertFalse(it.hasNext());

    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected InstanceTest<?> createTestForInstance(final Object instance,
      final boolean isSingleton, final boolean isModifiable) {

    if (instance instanceof IMatrix) {
      return new MatrixTest(this, ((IMatrix) instance), isSingleton);
    }

    return super
        .createTestForInstance(instance, isSingleton, isModifiable);
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    this.testMatrixDimensionsAndGetters();
    this.testMatrixTransposed();
    this.testMatrixSelectionAndTranspose();
    this.testMatrixIterate();
    this.testMatrixCopy();
  }
}
