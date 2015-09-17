package test.junit.org.optimizationBenchmarking.utils.math.mathEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IMathEngine;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IMathEngineTool;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.MatrixBuilder;
import org.optimizationBenchmarking.utils.math.random.RandomUtils;

import test.junit.TestBase;

/** A test for maths engines */
@Ignore
public abstract class MathEngineTest extends TestBase {

  /** create the test */
  protected MathEngineTest() {
    super();
  }

  /**
   * Get the math engine tool
   *
   * @return the tool
   */
  protected abstract IMathEngineTool getTool();

  /** test whether the math engine tool can be constructed */
  @Test(timeout = 3600000)
  public void testToolNotNull() {
    Assert.assertNotNull(this.getTool());
  }

  /** test whether we can read and write boolean values */
  @Test(timeout = 3600000)
  public void testBooleanIO() {
    final IMathEngineTool tool;
    final Random random;
    final HashMap<String, Boolean> values;
    final ArrayList<String> variables;
    int count;
    String variable;
    Boolean value;

    tool = this.getTool();
    Assert.assertNotNull(tool);

    if (!(tool.canUse())) {
      return;
    }

    values = new HashMap<>();
    variables = new ArrayList<>();
    random = new Random();

    try (final IMathEngine engine = tool.use().create()) {
      Assert.assertNotNull(engine);

      for (count = 20000; (--count) >= 0;) {

        if (random.nextBoolean() && (!(variables.isEmpty()))) {
          variable = variables.get(random.nextInt(variables.size()));

          Assert.assertTrue(//
              values.get(variable).booleanValue() == //
              engine.getBoolean(variable));
        } else {
          variable = RandomUtils.longToString(RandomUtils.DEFAULT_CHARSET,
              random.nextLong());
        }

        value = Boolean.valueOf(random.nextBoolean());
        engine.setBoolean(variable, value.booleanValue());
        if (values.put(variable, value) == null) {
          variables.add(variable);
        }
        Assert.assertTrue(//
            value.booleanValue() == engine.getBoolean(variable));
      }

    } catch (final AssertionError ae) {
      throw ae;
    } catch (final Throwable error) {
      throw new AssertionError(error);
    }
  }

  /**
   * create a random long value
   *
   * @param random
   *          the random number generator
   * @return the value
   */
  private static final long __randomLong(final Random random) {
    final double dbl;

    dbl = random.nextLong();
    return ((long) dbl);
  }

  /** test whether we can read and write long values */
  @Test(timeout = 3600000)
  public void testLongIO() {
    final IMathEngineTool tool;
    final Random random;
    final HashMap<String, Long> values;
    final ArrayList<String> variables;
    int count;
    String variable;
    Long value;

    tool = this.getTool();
    Assert.assertNotNull(tool);

    if (!(tool.canUse())) {
      return;
    }

    values = new HashMap<>();
    variables = new ArrayList<>();
    random = new Random();

    try (final IMathEngine engine = tool.use().create()) {
      Assert.assertNotNull(engine);

      for (count = 20000; (--count) >= 0;) {

        if (random.nextBoolean() && (!(variables.isEmpty()))) {
          variable = variables.get(random.nextInt(variables.size()));

          Assert.assertEquals(//
              values.get(variable).longValue(), //
              engine.getLong(variable));
        } else {
          variable = RandomUtils.longToString(RandomUtils.DEFAULT_CHARSET,
              random.nextLong());
        }

        value = Long.valueOf(random.nextBoolean() //
        ? MathEngineTest.__randomLong(random)
            : random.nextInt());
        engine.setLong(variable, value.longValue());
        if (values.put(variable, value) == null) {
          variables.add(variable);
        }
        Assert.assertEquals(//
            value.longValue(), engine.getLong(variable));
      }

    } catch (final AssertionError ae) {
      throw ae;
    } catch (final Throwable error) {
      throw new AssertionError(error);
    }
  }

  /**
   * Get a random {@code double} value
   *
   * @param random
   *          the random number generator
   * @return the value
   */
  private static final double __randomDouble(final Random random) {

    switch (random.nextInt(4)) {
      case 0: {
        return random.nextDouble();
      }
      case 1: {
        return random.nextLong();
      }
      case 2: {
        return (random.nextBoolean() ? Double.POSITIVE_INFINITY
            : Double.NEGATIVE_INFINITY);
      }
      default: {
        return ((0.5 - random.nextDouble()) * Math
            .exp(random.nextInt(100)));
      }
    }
  }

  /** test whether we can read and write double values */
  @Test(timeout = 3600000)
  public void testDoubleIO() {
    final IMathEngineTool tool;
    final Random random;
    final HashMap<String, Double> values;
    final ArrayList<String> variables;
    int count;
    String variable;
    Double value;

    tool = this.getTool();
    Assert.assertNotNull(tool);

    if (!(tool.canUse())) {
      return;
    }

    values = new HashMap<>();
    variables = new ArrayList<>();
    random = new Random();

    try (final IMathEngine engine = tool.use().create()) {
      Assert.assertNotNull(engine);

      for (count = 20000; (--count) >= 0;) {

        if (random.nextBoolean() && (!(variables.isEmpty()))) {
          variable = variables.get(random.nextInt(variables.size()));

          MathEngineTest._assertDoubleEquals(//
              values.get(variable).doubleValue(), //
              engine.getDouble(variable));
        } else {
          variable = RandomUtils.longToString(RandomUtils.DEFAULT_CHARSET,
              random.nextLong());
        }

        value = Double.valueOf(MathEngineTest.__randomDouble(random));
        engine.setDouble(variable, value.doubleValue());
        if (values.put(variable, value) == null) {
          variables.add(variable);
        }
        MathEngineTest._assertDoubleEquals(//
            value.doubleValue(), engine.getDouble(variable));
      }

    } catch (final AssertionError ae) {
      throw ae;
    } catch (final Throwable error) {
      throw new AssertionError(error);
    }
  }

  /**
   * Compare two matrices
   *
   * @param a
   *          the first matrix
   * @param b
   *          the second matrix
   */
  static final void _compareMatrices(final IMatrix a, final IMatrix b) {
    final int n, m;
    final boolean isInt;
    int i, j;

    if (a == null) {
      if (b == null) {
        return;
      }
      throw new AssertionError("Matrix a is null but not matrix b."); //$NON-NLS-1$
    }
    if (b == null) {
      throw new AssertionError("Matrix b is null but not matrix a."); //$NON-NLS-1$
    }

    n = a.n();
    Assert.assertEquals(n, b.n());

    m = a.m();
    Assert.assertEquals(m, b.m());

    isInt = a.isIntegerMatrix();
    Assert.assertTrue(isInt == b.isIntegerMatrix());

    if (isInt) {
      for (i = m; (--i) >= 0;) {
        for (j = n; (--j) >= 0;) {
          Assert.assertEquals(a.getLong(i, j), b.getLong(i, j));
        }
      }
    } else {
      for (i = m; (--i) >= 0;) {
        for (j = n; (--j) >= 0;) {
          MathEngineTest._assertDoubleEquals(a.getDouble(i, j),
              b.getDouble(i, j));
        }
      }
    }
  }

  /**
   * Assert that two doubles are sufficiently equal.
   *
   * @param a
   *          the first double
   * @param b
   *          the second double
   */
  static final void _assertDoubleEquals(final double a, final double b) {
    if ((Math.nextUp(a) >= Math.nextAfter(b, Double.NEGATIVE_INFINITY)) && //
        (Math.nextUp(b) >= Math.nextAfter(a, Double.NEGATIVE_INFINITY))) {
      return;
    }
    throw new AssertionError("The double values " + a + //$NON-NLS-1$
        " and " + b + //$NON-NLS-1$
        " differ by more than two rounding steps."); //$NON-NLS-1$
  }

  /**
   * create a random matrix of the given dimensions
   *
   * @param m
   *          the m
   * @param n
   *          the n
   * @param random
   *          the random number generator
   * @param type
   *          0 == {@code long} 1 == {@code double}, 2== {@code int}
   * @return the matrix
   */
  private static final IMatrix __randomMatrix(final int m, final int n,
      final int type, final Random random) {
    final MatrixBuilder mb;
    int i, j;

    mb = new MatrixBuilder(m * n);
    mb.setM(m);
    mb.setN(n);

    switch (type) {
      case 0: {
        for (i = m; (--i) >= 0;) {
          for (j = n; (--j) >= 0;) {
            mb.append(MathEngineTest.__randomLong(random));
          }
        }
        break;
      }
      case 1: {
        for (i = m; (--i) >= 0;) {
          for (j = n; (--j) >= 0;) {
            mb.append(MathEngineTest.__randomDouble(random));
          }
        }
        break;
      }
      default: {
        for (i = m; (--i) >= 0;) {
          for (j = n; (--j) >= 0;) {
            mb.append(random.nextInt());
          }
        }
        break;
      }
    }

    return mb.make();
  }

  /** test whether we can read and write {@code double} matrices */
  @Test(timeout = 3600000)
  public final void testDoubleMatrixIO() {
    this.__testMatrixIO(1);
  }

  /** test whether we can read and write {@code long} matrices */
  @Test(timeout = 3600000)
  public final void testLongMatrixIO() {
    this.__testMatrixIO(0);
  }

  /** test whether we can read and write {@code int} matrices */
  @Test(timeout = 3600000)
  public final void testIntegerMatrixIO() {
    this.__testMatrixIO(2);
  }

  /**
   * test whether we can read and write matrices
   *
   * @param type
   *          0 == {@code long} 1 == {@code double}, 2== {@code int}
   */
  private final void __testMatrixIO(final int type) {
    final IMathEngineTool tool;
    final Random random;
    final HashMap<String, IMatrix> values;
    final ArrayList<String> variables;
    int count;
    String variable;
    IMatrix value;

    tool = this.getTool();
    Assert.assertNotNull(tool);

    if (!(tool.canUse())) {
      return;
    }

    values = new HashMap<>();
    variables = new ArrayList<>();
    random = new Random();

    try (final IMathEngine engine = tool.use().create()) {
      Assert.assertNotNull(engine);

      for (count = 100; (--count) >= 0;) {

        if (random.nextBoolean() && (!(variables.isEmpty()))) {
          variable = variables.get(random.nextInt(variables.size()));

          MathEngineTest._compareMatrices(values.get(variable),
              engine.getMatrix(variable));
        } else {
          variable = RandomUtils.longToString(RandomUtils.DEFAULT_CHARSET,
              random.nextLong());
        }

        value = MathEngineTest.__randomMatrix((1 + random.nextInt(100)),
            (1 + random.nextInt(100)), type, random);
        engine.setMatrix(variable, value);
        if (values.put(variable, value) == null) {
          variables.add(variable);
        }
        MathEngineTest._compareMatrices(value, engine.getMatrix(variable));
      }

    } catch (final AssertionError ae) {
      throw ae;
    } catch (final Throwable error) {
      throw new AssertionError(error);
    }
  }

  /** test whether we can read and write {@code long} vectors */
  @Test(timeout = 3600000)
  public final void testLongVectorIO() {
    this.__testVectorIO(0);
  }

  /** test whether we can read and write {@code double} vectors */
  @Test(timeout = 3600000)
  public final void testDoubleVectorIO() {
    this.__testVectorIO(1);
  }

  /** test whether we can read and write {@code int} vectors */
  @Test(timeout = 3600000)
  public final void testIntVectorIO() {
    this.__testVectorIO(2);
  }

  /**
   * test whether we can read and write vectors
   *
   * @param type
   *          0 == {@code long} 1 == {@code double}, 2== {@code int}
   */
  private final void __testVectorIO(final int type) {
    final IMathEngineTool tool;
    final Random random;
    final HashMap<String, IMatrix> values;
    final ArrayList<String> variables;
    int count;
    boolean one;
    String variable;
    IMatrix value;

    tool = this.getTool();
    Assert.assertNotNull(tool);

    if (!(tool.canUse())) {
      return;
    }

    values = new HashMap<>();
    variables = new ArrayList<>();
    random = new Random();

    try (final IMathEngine engine = tool.use().create()) {
      Assert.assertNotNull(engine);

      for (count = 1000; (--count) >= 0;) {

        if (random.nextBoolean() && (!(variables.isEmpty()))) {
          variable = variables.get(random.nextInt(variables.size()));

          MathEngineTest._compareMatrices(values.get(variable),
              engine.getMatrix(variable));
        } else {
          variable = RandomUtils.longToString(RandomUtils.DEFAULT_CHARSET,
              random.nextLong());
        }

        one = random.nextBoolean();
        value = MathEngineTest.__randomMatrix(
            (one ? 1 : (1 + random.nextInt(100))),
            (one ? (1 + random.nextInt(100)) : 1), type, random);
        engine.setMatrix(variable, value);
        if (values.put(variable, value) == null) {
          variables.add(variable);
        }
        MathEngineTest._compareMatrices(value, engine.getMatrix(variable));
      }

    } catch (final AssertionError ae) {
      throw ae;
    } catch (final Throwable error) {
      throw new AssertionError(error);
    }
  }
}
