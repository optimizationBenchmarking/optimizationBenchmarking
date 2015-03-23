package test.junit;

import java.io.Externalizable;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * the base class for tests of instance objects
 * 
 * @param <T>
 *          the instance type
 */
@Ignore
public class InstanceTest<T> extends TestBase {

  /** the visited key */
  private static final Object VISITED = new Object();

  /** the owner */
  private final InstanceTest<T> m_owner;

  /** will the {@link #getInstance()} return singletons? */
  private final boolean m_isSingleton;

  /** are the instances modifiable? */
  private final boolean m_isModifiable;

  /** an instance */
  private final T m_instance;

  /** the constructor */
  protected InstanceTest() {
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
  protected InstanceTest(final InstanceTest<T> owner, final T instance,
      final boolean isSingleton, final boolean isModifiable) {
    super();
    this.m_owner = owner;
    this.m_instance = instance;
    this.m_isSingleton = isSingleton;
    this.m_isModifiable = isModifiable;
  }

  /**
   * get the instance to test
   * 
   * @return the instance to test
   */
  protected T getInstance() {
    return this.m_instance;
  }

  /**
   * Is the returned instance a singleton? If so, this has strong
   * implication on its serialization behavior, amongst others.
   * 
   * @return {@code true} if and only if the instance is a singleton
   */
  protected boolean isSingleton() {
    return this.m_isSingleton;
  }

  /**
   * Top-level tests may apply recursive testing behaviors. Tests which are
   * not top-level should not do so to avoid infinite recursion.
   * 
   * @return {@code true} if and only if this is a top-level test,
   *         {@code false} otherwise.
   */
  protected boolean isTopLevelTest() {
    return (this.m_owner == null);
  }

  /**
   * Are the instances we deal with modifiable?
   * 
   * @return {@code true} if {@link #getInstance()} returns an object that
   *         can, in some way, be modified. {@code false} if the objects
   *         are constants
   */
  protected boolean isModifiable() {
    return this.m_isModifiable;
  }

  /**
   * Get the owning test instance
   * 
   * @return the owning test instance, or {@code null} if this is a
   *         top-level test.
   */
  protected InstanceTest<T> getOwner() {
    return this.m_owner;
  }

  /** test whether the instance is {@code null} - it should not be. */
  @Test(timeout = 3600000)
  public void testInstanceNotNull() {
    Assert.assertNotNull(this.getInstance());
  }

  /** test whether the instance equals itself */
  @Test(timeout = 3600000)
  public void testInstanceEqualsItself() {
    final T inst;

    inst = this.getInstance();
    Assert.assertTrue(inst.equals(inst));
    Assert.assertEquals(inst, inst);
  }

  /** test whether the instance does not equal {@code null} */
  @Test(timeout = 3600000)
  public void testInstanceNotEqualsNull() {
    final T inst;

    inst = this.getInstance();
    Assert.assertFalse(inst.equals(null));
  }

  /** test whether the instance does not equal dummy data */
  @Test(timeout = 3600000)
  public void testInstanceNotEqualsDummy() {
    final T inst;

    inst = this.getInstance();
    Assert.assertFalse(inst.equals(new Object()));
    Assert.assertFalse(inst.equals(new DummyCloneableAndSerializable()));
    Assert.assertFalse(inst.equals("")); //$NON-NLS-1$
    Assert.assertFalse(inst.equals("blblblb")); //$NON-NLS-1$
    Assert.assertFalse(inst.equals(Integer.valueOf(-1)));
    Assert.assertFalse(inst.equals(Integer.valueOf(0)));
    Assert.assertFalse(inst.equals(Boolean.TRUE));
  }

  /**
   * test whether we can invoke {@link java.lang.Object#toString()} and
   * that it does not return {@code null}
   */
  @Test(timeout = 3600000)
  public void testCanInvokeToStringAndNotGetNull() {
    Assert.assertNotNull(this.getInstance().toString());
  }

  /**
   * test whether we can invoke {@link java.lang.Object#toString()} several
   * times and always get the same result
   */
  @Test(timeout = 3600000)
  public void testCanInvokeToStringAndGetSameResult() {
    final T inst;

    inst = this.getInstance();
    Assert.assertEquals(inst.toString(), inst.toString());
  }

  /**
   * test whether we can invoke {@link java.lang.Object#hashCode()} several
   * times and get the same result
   */
  @Test(timeout = 3600000)
  public void testCanInvokeHashCodeAndGetSameResult() {
    final T inst;

    inst = this.getInstance();
    Assert.assertEquals(inst.hashCode(), inst.hashCode());
  }

  /**
   * check whether a given object can be serialized
   * 
   * @param o
   *          the object
   * @return {@code true} if the object can be serialized, {@code false}
   *         otherwise
   */
  protected boolean canSerialize(final Object o) {
    return this.__canSerialize(o, new IdentityHashMap<>());
  }

  /**
   * check whether a given object can be serialized
   * 
   * @param o
   *          the object
   * @param visited
   *          the visited objects
   * @return {@code true} if the object can be serialized, {@code false}
   *         otherwise
   */
  @SuppressWarnings("rawtypes")
  private final boolean __canSerialize(final Object o,
      final IdentityHashMap<Object, Object> visited) {
    Iterable it;
    Map.Entry e;

    if (o == null) {
      return true;
    }

    if (visited.put(o, InstanceTest.VISITED) == InstanceTest.VISITED) {
      return true;
    }

    if ((o instanceof Serializable) || (o instanceof Externalizable)) {
      if (o instanceof Iterable) {
        it = ((Iterable) o);
      } else {
        if (o instanceof Map) {
          it = ((Map) o).entrySet();
        } else {
          if (o instanceof Map.Entry) {
            e = ((Map.Entry) o);
            return ((this.__canSerialize(e.getKey(), visited)) && //
            (this.__canSerialize(e.getValue(), visited)));
          }
          return true;
        }
      }

      for (final Object x : it) {
        if (!(this.__canSerialize(x, visited))) {
          return false;
        }
      }
    }

    return false;
  }

  /**
   * If the object can be serialized, then we serialize the object in
   * memory. We then de-serialize the object. We then test whether the
   * deserialized instance is the same (if {@link #isSingleton()} returns
   * {@code true}) or equal (otherwise) to the original instance. We do
   * this a couple of times, just to see whether everything is OK.
   */
  @Test(timeout = 3600000)
  public void testSerializationAndDeserializationEquals() {
    final T orig;
    final Random r;
    Object result, use;
    int i;

    orig = this.getInstance();
    if (this.canSerialize(orig)) {
      r = new Random();
      use = orig;

      for (i = 0; i < 10; i++) {
        result = TestBase.serializeDeserialize(use);
        Assert.assertNotNull(result);
        Assert.assertSame(orig.getClass(), result.getClass());

        if (this.isSingleton()) {
          Assert.assertSame(orig, result);
        } else {
          Assert.assertTrue(orig != result);
          this.compareTwoCopies(orig, result);
        }

        this.applyTestsToInstance(result);

        if (this.isSingleton()) {
          Assert.assertSame(orig, result);
        } else {
          Assert.assertTrue(orig != result);
          this.compareTwoCopies(orig, result);
        }

        switch (i) {
          case 0: {
            use = result;
            break;
          }
          case 1: {
            break;
          }
          default: {
            if (r.nextBoolean()) {
              use = result;
            }
          }
        }
      }
    }
  }

  /**
   * check whether a given object can be cloned
   * 
   * @param o
   *          the object
   * @return {@code true} if the object can be cloned, {@code false}
   *         otherwise
   */
  protected boolean canClone(final Object o) {
    if (o instanceof Cloneable) {
      try {
        return ((o.getClass().getMethod("clone")) != null); //$NON-NLS-1$
      } catch (final NoSuchMethodException x) {
        return false;
      } catch (final Throwable t) {
        throw new AssertionError(t);
      }
    }

    return false;
  }

  /**
   * clone an instance via reflection
   * 
   * @param instance
   *          the instance
   * @return the cloned copy
   */
  protected T cloneInstance(final T instance) {
    final Method clone;
    try {
      clone = instance.getClass().getMethod("clone"); //$NON-NLS-1$
      return ((T) (clone.invoke(instance)));
    } catch (final Throwable v) {
      throw new AssertionError(v);
    }
  }

  /**
   * If the object can be cloned, then we clone. We then test whether the
   * cloned instance is the same (if {@link #isSingleton()} returns
   * {@code true}) or equal (otherwise) to the original instance. We do
   * this a couple of times, just to see whether everything is OK.
   */
  @Test(timeout = 3600000)
  public void testCloneEquals() {
    final T orig;
    final Random r;
    T result, use;
    int i;

    orig = this.getInstance();
    if (this.canClone(orig)) {
      r = new Random();
      use = orig;

      for (i = 0; i < 10; i++) {
        result = this.cloneInstance(use);
        Assert.assertNotNull(result);
        Assert.assertSame(orig.getClass(), result.getClass());

        if (this.isSingleton()) {
          Assert.assertSame(orig, result);
        } else {
          Assert.assertTrue(orig != result);
          this.compareTwoCopies(orig, result);
        }

        this.applyTestsToInstance(result);

        if (this.isSingleton()) {
          Assert.assertSame(orig, result);
        } else {
          Assert.assertTrue(orig != result);
          this.compareTwoCopies(orig, result);
        }

        switch (i) {
          case 0: {
            use = result;
            break;
          }
          case 1: {
            break;
          }
          default: {
            if (r.nextBoolean()) {
              use = result;
            }
          }
        }
      }
    }
  }

  /**
   * compare two copies of an object
   * 
   * @param a
   *          the first object
   * @param b
   *          the second object
   */
  protected void compareTwoCopies(final Object a, final Object b) {
    Assert.assertEquals(a, b);
    Assert.assertEquals(a.hashCode(), b.hashCode());
    Assert.assertEquals(a.toString(), b.toString());
    Assert.assertTrue(a.equals(b));
    Assert.assertTrue(b.equals(a));
  }

  /**
   * This method should apply the tests to the given instance if this is a
   * top-level test. The general contract is that the instance must be in
   * the same state after it has been put into this method than before.
   * This method can perform any additional tests to check whether the
   * instance is ok.
   * 
   * @param instance
   *          the instance to test
   * @param isSingleton
   *          is {@code instance} a singleton instance?
   * @param isModifiable
   *          is {@code instance} a modifiable instance?
   */
  protected void applyTestsToInstance(final Object instance,
      final boolean isSingleton, final boolean isModifiable) {
    final InstanceTest<?> test;

    if (this.isTopLevelTest()) {
      test = this.createTestForInstance(instance, isSingleton,
          isModifiable);
      if (test != null) {
        test.validateInstance();
      }
    }
  }

  /**
   * This method should apply the tests to the given instance if this is a
   * top-level test. The general contract is that the instance must be in
   * the same state after it has been put into this method than before.
   * This method can perform any additional tests to check whether the
   * instance is ok.
   * 
   * @param instance
   *          the instance to test
   */
  protected void applyTestsToInstance(final Object instance) {
    final InstanceTest<?> test;

    if (this.isTopLevelTest()) {
      test = this.createTestForInstance(instance, this.isSingleton(),
          this.isModifiable());
      if (test != null) {
        test.validateInstance();
      }
    }
  }

  /**
   * Create a test for the given instance
   * 
   * @param instance
   *          the instance to test
   * @param isSingleton
   *          is {@code instance} a singleton instance?
   * @param isModifiable
   *          is {@code instance} a modifiable instance?
   * @return the test, or {@code null} if none could be created
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected InstanceTest<?> createTestForInstance(final Object instance,
      final boolean isSingleton, final boolean isModifiable) {
    return new InstanceTest<>(((InstanceTest) this), instance,
        isSingleton, isModifiable);
  }

  /**
   * Run all tests.
   */
  public void validateInstance() {
    this.testInstanceNotNull();

    this.testInstanceEqualsItself();
    this.testInstanceNotEqualsNull();
    this.testInstanceNotEqualsDummy();

    this.testCanInvokeHashCodeAndGetSameResult();
    this.testCanInvokeToStringAndNotGetNull();
    this.testCanInvokeToStringAndGetSameResult();

    this.testSerializationAndDeserializationEquals();
    this.testCloneEquals();
  }
}
