package test.junit.org.optimizationBenchmarking.utils.collections.maps;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.RandomUtils;
import org.optimizationBenchmarking.utils.collections.maps.ObjectMap;
import org.optimizationBenchmarking.utils.collections.maps.StringMap;

/**
 * This is a test designed to verify that our tests are compatible to the
 * behavior of Java's map collection classes.
 */
public class StringMapTest extends ObjectMapTest {

  /** characters allowed in keys */
  public static final String STRING_MAP_ALLOWED_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_#+!§$%&?*:;,.-~^°"; //$NON-NLS-1$

  /**
   * create
   * 
   * @param map
   *          the map
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected StringMapTest(final StringMap<Object> map) {
    super((ObjectMap) map);
  }

  /** create */
  public StringMapTest() {
    this(new StringMap<>());
  }

  /** {@inheritDoc} */
  @Override
  protected String createKey(final long sequence) {
    return RandomUtils.longToString(
        StringMapTest.STRING_MAP_ALLOWED_CHARS, sequence);
  }

  /**
   * modify a given key
   * 
   * @param key
   *          the key
   * @param random
   *          the random number generator
   * @return the modified key
   */
  static final String __modify(final String key, final Random random) {
    final int mode;
    final StringBuilder sb;

    mode = (1 + random.nextInt(3));

    sb = new StringBuilder();

    if ((mode & 1) != 0) {
      do {
        sb.append((char) (1 + random.nextInt(' ')));
      } while (random.nextBoolean());
    }

    sb.append(key);

    if ((mode & 2) != 0) {
      do {
        sb.append((char) (1 + random.nextInt(' ')));
      } while (random.nextBoolean());
    }

    return sb.toString();
  }

  /** test add and remove */
  @Test(timeout = 3600000)
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public void testPutRemoveWithSpaces() {
    final StringMap<Object> map;
    int origSize, i;
    final Random r;
    final String[] keys;
    Object oldValue, newValue;
    String modKey1, modKey2, modKey3;
    long seq;

    if (!(this.isModifiable())) {
      return;
    }

    seq = 0;
    map = ((StringMap) (this.getInstance()));
    origSize = map.size();
    r = new Random();

    keys = new String[100];
    for (i = keys.length; (--i) >= 0;) {
      keys[i] = this.createKey(seq++);
    }

    for (final String key : keys) {

      for (i = 100; (--i) >= 0;) {
        modKey1 = StringMapTest.__modify(key, r);
        modKey2 = StringMapTest.__modify(key, r);
        modKey3 = StringMapTest.__modify(key, r);

        Assert.assertFalse(map.containsKey(key));
        Assert.assertFalse(map.containsKey(modKey1));
        Assert.assertFalse(map.containsKey(modKey2));
        Assert.assertFalse(map.containsKey(modKey3));
        Assert.assertEquals(origSize, map.size());

        oldValue = null;
        newValue = this.createValue(seq++);
        Assert.assertSame(oldValue, map.put(key, newValue));
        Assert.assertEquals(origSize + 1, map.size());
        Assert.assertTrue(map.containsKey(key));
        Assert.assertTrue(map.containsKey(modKey1));
        Assert.assertTrue(map.containsKey(modKey2));
        Assert.assertTrue(map.containsKey(modKey3));
        Assert.assertSame(newValue, map.get(key));
        Assert.assertSame(newValue, map.get(modKey1));
        Assert.assertSame(newValue, map.get(modKey2));
        Assert.assertSame(newValue, map.get(modKey3));

        oldValue = newValue;
        newValue = this.createValue(seq++);
        Assert.assertSame(oldValue, map.put(modKey1, newValue));
        Assert.assertEquals(origSize + 1, map.size());
        Assert.assertTrue(map.containsKey(key));
        Assert.assertTrue(map.containsKey(modKey1));
        Assert.assertTrue(map.containsKey(modKey2));
        Assert.assertTrue(map.containsKey(modKey3));
        Assert.assertSame(newValue, map.get(key));
        Assert.assertSame(newValue, map.get(modKey1));
        Assert.assertSame(newValue, map.get(modKey2));
        Assert.assertSame(newValue, map.get(modKey3));

        oldValue = newValue;
        newValue = this.createValue(seq++);
        Assert.assertSame(oldValue, map.put(modKey2, newValue));
        Assert.assertEquals(origSize + 1, map.size());
        Assert.assertTrue(map.containsKey(key));
        Assert.assertTrue(map.containsKey(modKey1));
        Assert.assertTrue(map.containsKey(modKey2));
        Assert.assertTrue(map.containsKey(modKey3));
        Assert.assertSame(newValue, map.get(key));
        Assert.assertSame(newValue, map.get(modKey1));
        Assert.assertSame(newValue, map.get(modKey2));
        Assert.assertSame(newValue, map.get(modKey3));

        oldValue = newValue;
        newValue = this.createValue(seq++);
        Assert.assertSame(oldValue, map.put(modKey3, newValue));
        Assert.assertEquals(origSize + 1, map.size());
        Assert.assertTrue(map.containsKey(key));
        Assert.assertTrue(map.containsKey(modKey1));
        Assert.assertTrue(map.containsKey(modKey2));
        Assert.assertTrue(map.containsKey(modKey3));
        Assert.assertSame(newValue, map.get(key));
        Assert.assertSame(newValue, map.get(modKey1));
        Assert.assertSame(newValue, map.get(modKey2));
        Assert.assertSame(newValue, map.get(modKey3));

        Assert.assertSame(newValue, map.remove(key));
        Assert.assertEquals(origSize, map.size());
        Assert.assertFalse(map.containsKey(key));
      }
    }

    Assert.assertEquals(origSize, map.size());
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    this.testPutRemoveWithSpaces();
  }
}
