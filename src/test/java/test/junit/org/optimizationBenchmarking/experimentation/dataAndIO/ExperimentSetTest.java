package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.data.spec.IDataPoint;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterValue;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluationInput;
import org.optimizationBenchmarking.experimentation.io.impl.edi.EDIInput;
import org.optimizationBenchmarking.experimentation.io.impl.edi.EDIOutput;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.SaturatingAdd;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

import test.junit.InstanceTest;
import test.junit.org.optimizationBenchmarking.utils.collections.lists.ArrayListViewTestBase;

/** A class for creating experiment sets */
@Ignore
public class ExperimentSetTest extends InstanceTest<IExperimentSet> {

  /** the instance */
  private final IEvaluationInput m_inst;

  /**
   * create
   *
   * @param creator
   *          the wrapped creator
   */
  protected ExperimentSetTest(final IEvaluationInput creator) {
    super();
    this.m_inst = creator;
  }

  /** {@inheritDoc} */
  @Override
  public final IExperimentSet getInstance() {
    try {
      return this.m_inst.getExperimentSet();
    } catch (final Throwable tt) {
      throw new RuntimeException("Failed to create experiment set.", //$NON-NLS-1$
          tt);
    }
  }

  /**
   * Test the experiment set data
   */
  @Test(timeout = 3600000)
  public final void testExperimentSetData() {
    final ArrayListView<? extends IExperiment> d;

    d = this.getInstance().getData();
    Assert.assertNotNull(d);
    Assert.assertFalse(d.isEmpty());
    Assert.assertTrue(d.size() > 0);
  }

  /**
   * Test the experiment dimension data
   */
  @Test(timeout = 3600000)
  public final void testExperimentSetDimensions() {
    final IDimensionSet ds;

    ds = this.getInstance().getDimensions();
    Assert.assertNotNull(ds);
  }

  /**
   * Test the run matrix features
   */
  @Test(timeout = 24000000)
  public final void testExperimentRunsMatrix() {
    ArrayListView<? extends IInstanceRuns> irSet;
    ArrayListView<? extends IRun> rSet;
    final Random rand;
    final HashSet<IInstanceRuns> irChoice;
    final HashSet<IRun> rChoice;

    rand = new Random();
    irChoice = new HashSet<>();
    rChoice = new HashSet<>();

    for (final IExperiment es : this.getInstance().getData()) {
      irSet = es.getData();
      if (irSet.isEmpty()) {
        continue;
      }
      irChoice.clear();
      irChoice.add(irSet.get(0));
      irChoice.add(irSet.get(irSet.size() - 1));
      while ((irChoice.size() < irSet.size()) && (irChoice.size() < 4)) {
        irChoice.add(irSet.get(rand.nextInt(irSet.size())));
      }

      for (final IInstanceRuns ir : irChoice) {
        rSet = ir.getData();
        if (irSet.isEmpty()) {
          continue;
        }
        rChoice.clear();
        rChoice.add(rSet.get(0));
        rChoice.add(rSet.get(rSet.size() - 1));
        while ((rChoice.size() < rSet.size()) && (rChoice.size() < 4)) {
          rChoice.add(rSet.get(rand.nextInt(rSet.size())));
        }
        for (final IRun r : rChoice) {
          new _MatrixTest<>(null, r, false).validateInstance();
        }
      }
    }
  }

  /**
   * Test the run list features
   */
  @Test(timeout = 134000000)
  public final void testExperimentRunsList() {
    ArrayListView<? extends IInstanceRuns> irSet;
    ArrayListView<? extends IRun> rSet;
    final Random rand;
    final HashSet<IInstanceRuns> irChoice;
    final HashSet<IRun> rChoice;

    rand = new Random();
    irChoice = new HashSet<>();
    rChoice = new HashSet<>();

    for (final IExperiment es : this.getInstance().getData()) {
      irSet = es.getData();
      if (irSet.isEmpty()) {
        continue;
      }
      irChoice.clear();
      irChoice.add(irSet.get(0));
      irChoice.add(irSet.get(irSet.size() - 1));
      while ((irChoice.size() < irSet.size()) && (irChoice.size() < 4)) {
        irChoice.add(irSet.get(rand.nextInt(irSet.size())));
      }

      for (final IInstanceRuns ir : irChoice) {
        rSet = ir.getData();
        if (irSet.isEmpty()) {
          continue;
        }
        rChoice.clear();
        rChoice.add(rSet.get(0));
        rChoice.add(rSet.get(rSet.size() - 1));
        while ((rChoice.size() < rSet.size()) && (rChoice.size() < 4)) {
          rChoice.add(rSet.get(rand.nextInt(rSet.size())));
        }
        for (final IRun r : rChoice) {
          new ArrayListViewTestBase<>(null, r.getData(),//
              false).validateInstance();
        }
      }
    }
  }

  /**
   * make more
   *
   * @param d
   *          the double
   * @return more
   */
  private static final double __more(final double d) {
    double a, b;

    a = Math.nextAfter((d + 1d), Double.POSITIVE_INFINITY);
    if (a >= Double.POSITIVE_INFINITY) {
      return a;
    }
    b = Math.nextAfter((((float) a) + 1f), Double.POSITIVE_INFINITY);
    if (b > a) {
      a = b;
    }

    if ((a >= Long.MIN_VALUE) && (a < Long.MAX_VALUE)) {
      b = (((long) a) + 1L);
    }
    if (b > a) {
      a = b;
    }

    return a;
  }

  /**
   * make less
   *
   * @param d
   *          the double
   * @return less
   */
  private static final double __less(final double d) {
    double a, b;

    a = Math.nextAfter((d - 1d), Double.NEGATIVE_INFINITY);
    if (a <= Double.NEGATIVE_INFINITY) {
      return a;
    }
    b = Math.nextAfter((((float) a) - 1f), Double.NEGATIVE_INFINITY);
    if (b < a) {
      a = b;
    }
    if ((a > Long.MIN_VALUE) && (a <= Long.MAX_VALUE)) {
      b = (((long) a) - 1L);
    }
    if (b < a) {
      a = b;
    }

    return a;
  }

  /**
   * Test the run find function for simple, existing values
   */
  @Test(timeout = 3600000)
  public final void testExperimentRunsFindExistingValue() {
    IExperimentSet es;
    IDimensionSet dims;
    IDataPoint dp, found, cur;
    ArrayListView<? extends IDataPoint> dps;
    int i, j, index;

    es = this.getInstance();
    dims = es.getDimensions();

    for (final IExperiment e : es.getData()) {
      for (final IInstanceRuns ir : e.getData()) {
        for (final IRun run : ir.getData()) {
          // begin run
          dps = run.getData();

          // check whether find really turns up the earliest data point
          // with a given value
          for (final IDataPoint x : dps) {
            for (final IDimension dim : dims.getData()) {
              index = dim.getIndex();

              // do we find the right point?
              if (dim.getDataType().isFloat()) {
                dp = run.find(index, x.getDouble(index));
                if (x.getDouble(index) != dp.getDouble(dim.getIndex())) {
                  Assert.fail(//
                      "Direct search for floating point value " + //$NON-NLS-1$
                          x.getDouble(index) + //
                          " for dimension " + dim + //$NON-NLS-1$
                          " at index " + index + //$NON-NLS-1$
                          " returned data point " + dp + //$NON-NLS-1$
                          " instead of the expected " //$NON-NLS-1$
                          + x);

                }
              } else {
                dp = run.find(index, x.getLong(index));
                if (x.getLong(index) != dp.getLong(index)) {
                  Assert.fail(//
                      "Direct search for long value " + //$NON-NLS-1$
                          x.getLong(index) + //
                          " for dimension " + dim + //$NON-NLS-1$
                          " at index " + index + //$NON-NLS-1$
                          " returned data point " + dp + //$NON-NLS-1$
                          " instead of the expected " //$NON-NLS-1$
                          + x);
                }
              }

              if (dp.compareTo(x) > 0) {
                Assert.fail("dim " + dim + //$NON-NLS-1$
                    " at index "//$NON-NLS-1$
                    + index + ": " + //$NON-NLS-1$
                    dp + " must come before or at " + x); //$NON-NLS-1$
              }
              if (x.compareTo(dp) < 0) {
                Assert.fail("dim " + dim + //$NON-NLS-1$
                    " at index "//$NON-NLS-1$
                    + index + ": " + //$NON-NLS-1$
                    x + " must come after or at " + dp); //$NON-NLS-1$
              }

              // check for the earliest point
              found = null;
              j = dps.size();

              finder: for (i = 0; i < j; i++) {
                cur = dps.get(i);
                if (dim.getDataType().isFloat()) {
                  if (cur.getDouble(index) == dp.getDouble(dim.getIndex())) {
                    found = cur;
                    break finder;
                  }
                } else {
                  if (cur.getLong(index) == dp.getLong(dim.getIndex())) {
                    found = cur;
                    break finder;
                  }
                }
              }

              Assert.assertSame(dp, found);
            }
          }
        }
      }
    }
  }

  /**
   * Test the run find function for values before the start or after the
   * end
   */
  @Test(timeout = 3600000)
  public final void testExperimentRunsFindValuesBeforeStartOrAfterEnd() {
    IExperimentSet es;
    IDimensionSet dims;
    IDataPoint first, last;
    double o, p;
    ArrayListView<? extends IDataPoint> dps;
    int index;

    es = this.getInstance();
    dims = es.getDimensions();

    for (final IExperiment e : es.getData()) {
      for (final IInstanceRuns ir : e.getData()) {
        for (final IRun run : ir.getData()) {
          // begin run
          dps = run.getData();

          // now test values which are not in the list
          first = dps.get(0);
          last = dps.get(dps.size() - 1);

          for (final IDimension dim : dims.getData()) {
            index = dim.getIndex();

            if (dim.getDirection().isIncreasing()) {
              o = ExperimentSetTest.__less(first.getDouble(index));
              p = ExperimentSetTest.__more(last.getDouble(index));
            } else {
              o = ExperimentSetTest.__more(first.getDouble(index));
              p = ExperimentSetTest.__less(last.getDouble(index));
            }

            if (dim.getDimensionType().isSolutionQualityMeasure()) {
              Assert.assertNull(run.find(index, p));
              Assert.assertEquals(first, run.find(index, o));
            } else {
              Assert.assertEquals(last, run.find(index, p));
              Assert.assertNull(run.find(index, o));
            }
          }
        }
      }
    }
  }

  /**
   * Test the run find function
   */
  @Test(timeout = 3600000)
  public final void testExperimentRunsFindValuesBetween() {
    IExperimentSet es;
    IDimensionSet dims;
    IDataPoint first, last, found, compare;
    double o, p, q;
    float u, v, w;
    ArrayListView<? extends IDataPoint> dps;
    int i, index, comp;
    long a, b, c, d;

    es = this.getInstance();
    dims = es.getDimensions();

    for (final IExperiment e : es.getData()) {
      for (final IInstanceRuns ir : e.getData()) {
        for (final IRun run : ir.getData()) {
          // begin run
          dps = run.getData();

          // now let's test values in-between two points
          for (i = (dps.size() - 1); (--i) >= 0;) {
            first = dps.get(i);
            last = dps.get(i + 1);

            for (final IDimension dim : dims.getData()) {

              index = dim.getIndex();

              if (dim.getDataType().isInteger()) {
                a = first.getLong(index);
                b = last.getLong(index);
                if (a == b) {
                  continue;
                }
                c = (a + b);
                d = SaturatingAdd.INSTANCE.computeAsLong(a, b);
                if (c != d) {
                  continue;
                }
                c /= 2L;

                comp = Long.compare(a, c);
                if (!(dim.getDirection().isIncreasing())) {
                  comp = (-comp);
                }
                if (comp >= 0) {
                  continue;
                }
                comp = Long.compare(c, b);
                if (!(dim.getDirection().isIncreasing())) {
                  comp = (-comp);
                }
                if (comp >= 0) {
                  continue;
                }

                if (dim.getDimensionType().isSolutionQualityMeasure()) {
                  Assert.assertSame(last, run.find(index, c));
                } else {
                  Assert.assertSame(first, run.find(index, c));
                }
              } else {

                if (dim.getDataType() == EPrimitiveType.DOUBLE) {

                  p = first.getDouble(index);
                  q = last.getDouble(index);
                  comp = EComparison.compareDoubles(p, q);
                  if (!(dim.getDirection().isIncreasing())) {
                    comp = (-comp);
                  }
                  if ((comp >= 0) || (p >= Double.MAX_VALUE)
                      || (p <= (-Double.MAX_VALUE)) || (p != p)
                      || (q >= Double.MAX_VALUE)
                      || (q <= (-Double.MAX_VALUE)) || (q != q)) {
                    continue;
                  }
                  o = (p + q);
                  if ((o >= Double.MAX_VALUE)
                      || (o <= (-Double.MAX_VALUE)) || (o != o)) {
                    continue;
                  }
                  o *= 0.5d;
                  if (o != o) {
                    continue;
                  }

                  comp = EComparison.compareDoubles(p, o);
                  if (!(dim.getDirection().isIncreasing())) {
                    comp = (-comp);
                  }
                  if (comp >= 0) {
                    continue;
                  }

                  comp = EComparison.compareDoubles(o, q);
                  if (!(dim.getDirection().isIncreasing())) {
                    comp = (-comp);
                  }
                  if (comp >= 0) {
                    continue;
                  }

                  found = run.find(index, o);
                  if (dim.getDimensionType().isSolutionQualityMeasure()) {
                    compare = last;
                  } else {
                    compare = first;
                  }

                  if (found != compare) {
                    throw new AssertionError("The log points " + found + //$NON-NLS-1$
                        " and " + compare + //$NON-NLS-1$
                        " should be the same, but they are not. We looked for value "//$NON-NLS-1$
                        + o + " of dimension " + dim.getIndex() + //$NON-NLS-1$
                        " (" + dim + //$NON-NLS-1$
                        ") between the values " + //$NON-NLS-1$
                        p + " and " + q + //$NON-NLS-1$
                        " of points " + first + //$NON-NLS-1$
                        " and " + last + //$NON-NLS-1$
                        " at indexes " + i + //$NON-NLS-1$
                        " and " + (i + 1) + //$NON-NLS-1$
                        " of " + dps.size());//$NON-NLS-1$
                  }
                } else {
                  u = first.getFloat(index);
                  v = last.getFloat(index);
                  comp = EComparison.compareFloats(u, v);
                  if (!(dim.getDirection().isIncreasing())) {
                    comp = (-comp);
                  }
                  if ((comp >= 0) || (u >= Float.MAX_VALUE)
                      || (u <= (-Float.MAX_VALUE)) || (u != u)
                      || (v >= Float.MAX_VALUE)
                      || (v <= (-Float.MAX_VALUE)) || (v != v)) {
                    continue;
                  }
                  w = (u + v);
                  if ((w >= Float.MAX_VALUE) || (w <= (-Float.MAX_VALUE))
                      || (w != w)) {
                    continue;
                  }
                  w *= 0.5f;
                  if (w != w) {
                    continue;
                  }

                  comp = EComparison.compareFloats(u, w);
                  if (!(dim.getDirection().isIncreasing())) {
                    comp = (-comp);
                  }
                  if (comp >= 0) {
                    continue;
                  }

                  comp = EComparison.compareFloats(w, v);
                  if (!(dim.getDirection().isIncreasing())) {
                    comp = (-comp);
                  }
                  if (comp >= 0) {
                    continue;
                  }

                  found = run.find(index, w);
                  if (dim.getDimensionType().isSolutionQualityMeasure()) {
                    compare = last;
                  } else {
                    compare = first;
                  }

                  if (found != compare) {
                    throw new AssertionError("The log points " + found + //$NON-NLS-1$
                        " and " + compare + //$NON-NLS-1$
                        " should be the same, but they are not. We looked for value "//$NON-NLS-1$
                        + w + " of dimension " + dim.getIndex() + //$NON-NLS-1$
                        " (" + dim + //$NON-NLS-1$
                        ") between the values " + //$NON-NLS-1$
                        u + " and " + v + //$NON-NLS-1$
                        " of points " + first + //$NON-NLS-1$
                        " and " + last + //$NON-NLS-1$
                        " at indexes " + i + //$NON-NLS-1$
                        " and " + (i + 1) + //$NON-NLS-1$
                        " of " + dps.size());//$NON-NLS-1$
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  /**
   * Test whether the experiment set has a canonical representation under
   * the EDI serialization: An experiment set can be stored to EDI,
   * resulting in string {@code s1}. A new experiment set can then be
   * deserialized from {@code s1}. If we then serialize this new experiment
   * set (again to EDI), this should in a string {@code s2} with
   * {@code s1.equals(s2)}.
   */
  @Test(timeout = 3600000)
  public void testEDISerializationCanonical() {
    final IExperimentSet inst, es1, es2;
    final EDIOutput output;
    final EDIInput input;
    String s1, s2;

    output = EDIOutput.getInstance();
    Assert.assertNotNull(output);
    Assert.assertTrue(output.canUse());
    input = EDIInput.getInstance();
    Assert.assertNotNull(input);
    Assert.assertTrue(input.canUse());

    inst = this.getInstance();
    try {
      try (final StringWriter w = new StringWriter()) {
        output.use().setWriter(w).setSource(inst).create().call();
        s1 = w.toString();
      }
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }

    try {
      try (final StringReader r = new StringReader(s1)) {
        try (final ExperimentSetContext esc = new ExperimentSetContext()) {
          input.use().addReader(r).setDestination(esc).create().call();
          es1 = esc.create();
        }
      }
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
    ExperimentSetTest._assertEquals(inst, es1);

    try {
      try (final StringWriter w = new StringWriter()) {
        output.use().setWriter(w).setSource(es1).create().call();
        s2 = w.toString();
      }
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
    Assert.assertEquals(s1, s2);
    s1 = null;

    try {
      try (final StringReader r = new StringReader(s2)) {
        s2 = null;
        try (final ExperimentSetContext esc = new ExperimentSetContext()) {
          input.use().addReader(r).setDestination(esc).create().call();
          es2 = esc.create();
        }
      }
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
    ExperimentSetTest._assertEquals(inst, es2);
    ExperimentSetTest._assertEquals(es1, es2);

  }

  /**
   * assert that two experiment sets are equal
   *
   * @param a
   *          set a
   * @param b
   *          set b
   */
  static final void _assertEquals(final IExperimentSet a,
      final IExperimentSet b) {
    int s;
    ArrayListView<? extends IExperiment> ae, be;
    ArrayListView<? extends IFeature> af, bf;
    ArrayListView<? extends IParameter> ap, bp;
    ArrayListView<? extends IDimension> ad, bd;
    IDimension d1, d2;

    ae = a.getData();
    be = b.getData();

    Assert.assertEquals(s = ae.size(), be.size());
    for (; (--s) >= 0;) {
      ExperimentSetTest._assertEquals(ae.get(s), be.get(s));
    }

    af = a.getFeatures().getData();
    bf = b.getFeatures().getData();
    Assert.assertEquals(s = af.size(), bf.size());
    for (; (--s) >= 0;) {
      ExperimentSetTest._assertEquals(af.get(s), bf.get(s));
    }

    ap = a.getParameters().getData();
    bp = b.getParameters().getData();
    Assert.assertEquals(s = ap.size(), bp.size());
    for (; (--s) >= 0;) {
      ExperimentSetTest._assertEquals(ap.get(s), bp.get(s));
    }

    ad = a.getDimensions().getData();
    bd = b.getDimensions().getData();
    Assert.assertEquals(s = ad.size(), bd.size());
    for (; (--s) >= 0;) {
      d1 = ad.get(s);
      d2 = bd.get(s);
      Assert.assertEquals(d1.getName(), d2.getName());
      Assert.assertEquals(d1.getDescription(), d2.getDescription());
      Assert.assertSame(d1.getDataType(), d2.getDataType());
      Assert.assertSame(d1.getDimensionType(), d2.getDimensionType());
      Assert.assertSame(d1.getDirection(), d2.getDirection());
      Assert.assertSame(d1.getParser().getOutputClass(), d2.getParser()
          .getOutputClass());
    }
  }

  /**
   * assert that parameter values are equal
   *
   * @param a
   *          the parameter value
   * @param b
   *          the parameter value
   */
  private static final void __assertVEquals(final Object a, final Object b) {
    boolean id1, id2;
    long l1, l2;
    double d1, d2;
    String s1, s2;

    if ((a instanceof Number) && (b instanceof Number)) {

      if ((a instanceof Float) || (a instanceof Double)) {
        d1 = ((Number) a).doubleValue();
        l1 = 0L;
        id1 = true;
      } else {
        d1 = Double.NaN;
        l1 = ((Number) a).longValue();
        id1 = false;
      }

      if ((b instanceof Float) || (b instanceof Double)) {
        d2 = ((Number) b).doubleValue();
        l2 = 0L;
        id2 = true;
      } else {
        d2 = Double.NaN;
        l2 = ((Number) b).longValue();
        id2 = false;
      }

      if (id1 == id2) {
        if (id1) {
          Assert.assertEquals(d1, d2, 1e-14);
        } else {
          Assert.assertEquals(l1, l2);
        }
        return;
      }

    } else {
      checkString: {
        if (a instanceof String) {
          s1 = ((String) a);
        } else {
          if (a instanceof Character) {
            s1 = a.toString();
          } else {
            break checkString;
          }
        }

        if (b instanceof String) {
          s2 = ((String) b);
        } else {
          if (b instanceof Character) {
            s2 = b.toString();
          } else {
            break checkString;
          }
        }

        Assert.assertEquals(s1, s2);
        return;
      }
    }

    Assert.assertEquals(a, b);
  }

  /**
   * assert that two experiment sets are equal
   *
   * @param a
   *          set a
   * @param b
   *          set b
   */
  static final void _assertEquals(final IExperiment a, final IExperiment b) {
    Iterator<Map.Entry<IProperty, Object>> x, y;
    Map.Entry<IProperty, Object> xe, ye;
    boolean z;
    ArrayListView<? extends IInstanceRuns> ia, ib;
    IInstanceRuns iae, ibe;
    int si, sr, sp;
    ArrayListView<? extends IRun> ra, rb;
    IRun rae, rbe;
    ArrayListView<? extends IDataPoint> da, db;

    Assert.assertEquals(a.getName(), b.getName());
    Assert.assertEquals(a.getDescription(), b.getDescription());

    x = a.getParameterSetting().entrySet().iterator();
    y = b.getParameterSetting().entrySet().iterator();

    outer: for (;;) {
      z = x.hasNext();
      Assert.assertTrue(z == y.hasNext());
      if (!z) {
        break outer;
      }
      xe = x.next();
      ye = y.next();
      ExperimentSetTest.__assertVEquals(xe.getValue(), ye.getValue());
      ExperimentSetTest._assertEquals(((IParameter) (xe.getKey())),
          ((IParameter) (ye.getKey())));
    }

    ia = a.getData();
    ib = b.getData();
    si = ia.size();
    Assert.assertEquals(si, ib.size());
    for (; (--si) >= 0;) {
      iae = ia.get(si);
      ibe = ib.get(si);
      ExperimentSetTest
          ._assertEquals(iae.getInstance(), ibe.getInstance());
      ra = iae.getData();
      rb = ibe.getData();
      sr = ra.size();
      Assert.assertEquals(sr, rb.size());
      for (; (--sr) >= 0;) {
        rae = ra.get(sr);
        rbe = rb.get(sr);

        da = rae.getData();
        db = rbe.getData();
        sp = da.size();
        Assert.assertEquals(sp, db.size());
        for (; (--sp) >= 0;) {
          Assert.assertEquals(da.get(sp), db.get(sp));
        }
      }
    }

  }

  /**
   * assert that two experiment sets are equal
   *
   * @param a
   *          set a
   * @param b
   *          set b
   */
  static final void _assertEquals(final IParameter a, final IParameter b) {
    ArrayListView<? extends IParameterValue> x, y;
    IParameterValue xe, ye;
    int s;

    Assert.assertEquals(a.getName(), b.getName());
    Assert.assertEquals(a.getDescription(), b.getDescription());

    x = a.getData();
    y = b.getData();
    s = x.size();
    Assert.assertEquals(s, y.size());
    for (; (--s) >= 0;) {
      xe = x.get(s);
      ye = y.get(s);
      Assert.assertEquals(xe.getName(), ye.getName());
      Assert.assertEquals(xe.getDescription(), ye.getDescription());
      ExperimentSetTest.__assertVEquals(xe.getValue(), ye.getValue());
    }
  }

  /**
   * assert that two experiment sets are equal
   *
   * @param a
   *          set a
   * @param b
   *          set b
   */
  static final void _assertEquals(final IFeature a, final IFeature b) {
    ArrayListView<? extends IFeatureValue> x, y;
    IFeatureValue xe, ye;
    int s;

    Assert.assertEquals(a.getName(), b.getName());
    Assert.assertEquals(a.getDescription(), b.getDescription());

    x = a.getData();
    y = b.getData();
    s = x.size();
    Assert.assertEquals(s, y.size());
    for (; (--s) >= 0;) {
      xe = x.get(s);
      ye = y.get(s);
      Assert.assertEquals(xe.getName(), ye.getName());
      Assert.assertEquals(xe.getDescription(), ye.getDescription());
      ExperimentSetTest.__assertVEquals(xe.getValue(), ye.getValue());
    }
  }

  /**
   * assert that two experiment sets are equal
   *
   * @param a
   *          set a
   * @param b
   *          set b
   */
  static final void _assertEquals(final IInstance a, final IInstance b) {
    Iterator<Map.Entry<IProperty, Object>> x, y;
    Map.Entry<IProperty, Object> xe, ye;
    boolean z;

    Assert.assertEquals(a.getName(), b.getName());
    Assert.assertEquals(a.getDescription(), b.getDescription());

    x = a.getFeatureSetting().entrySet().iterator();
    y = b.getFeatureSetting().entrySet().iterator();

    outer: for (;;) {
      z = x.hasNext();
      Assert.assertTrue(z == y.hasNext());
      if (!z) {
        break outer;
      }
      xe = x.next();
      ye = y.next();
      ExperimentSetTest.__assertVEquals(xe.getValue(), ye.getValue());
      ExperimentSetTest._assertEquals(((IFeature) (xe.getKey())),
          ((IFeature) (ye.getKey())));
    }
  }

  /** {@inheritDoc} */
  @Override
  public void testSerializationAndDeserializationEquals() {
    // ignored: runs are not serializable
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    this.testExperimentRunsMatrix();
    this.testExperimentRunsList();
    this.testExperimentRunsFindExistingValue();
    this.testExperimentRunsFindValuesBeforeStartOrAfterEnd();
    this.testExperimentRunsFindValuesBetween();
    this.testEDISerializationCanonical();
  }
}
