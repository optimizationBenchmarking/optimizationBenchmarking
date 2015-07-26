package test.junit.org.optimizationBenchmarking.utils.collections.maps;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.collections.maps.StringMap;
import org.optimizationBenchmarking.utils.collections.maps.StringMapCI;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * Test the case-insensitive string map.
 */
public class StringMapCITest extends StringMapTest {

  /**
   * create
   *
   * @param map
   *          the map
   */
  protected StringMapCITest(final StringMapCI<Object> map) {
    super(map);
  }

  /** create */
  public StringMapCITest() {
    this(new StringMapCI<>());
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
  static final String __modify2(final String key, final Random random) {
    final int mode;
    final StringBuilder sb;
    char ch;
    int i;

    mode = random.nextInt(4);

    sb = new StringBuilder();

    if ((mode & 1) != 0) {
      do {
        sb.append((char) (1 + random.nextInt(' ')));
      } while (random.nextBoolean());
    }

    for (i = 0; i < key.length(); i++) {
      ch = key.charAt(i);
      switch (random.nextInt(4)) {
        case 1: {
          ch = TextUtils.toLowerCase(ch);
          break;
        }
        case 2: {
          ch = TextUtils.toUpperCase(ch);
          break;
        }
        case 3: {
          ch = TextUtils.toTitleCase(ch);
          break;
        }
        default: {
          break;
        }
      }

      sb.append(ch);
    }

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
  public void testPutRemoveWithSpacesAndCaseChange() {
    final StringMap<Object> map;
    int origSize, i;
    final Random r;
    final String[] keys;
    long seq;
    Object oldValue, newValue;
    String modKey1, modKey2, modKey3;

    if (!(this.isModifiable())) {
      return;
    }

    map = ((StringMap) (this.getInstance()));
    origSize = map.size();
    r = new Random();
    seq = 0;
    keys = new String[100];
    for (i = keys.length; (--i) >= 0;) {
      keys[i] = this.createKey(seq++);
    }

    for (final String key : keys) {

      for (i = 100; (--i) >= 0;) {
        modKey1 = StringMapCITest.__modify2(key, r);
        modKey2 = StringMapCITest.__modify2(key, r);
        modKey3 = StringMapCITest.__modify2(key, r);

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
    this.testPutRemoveWithSpacesAndCaseChange();
  }

  /** {@inheritDoc} */
  @Override
  protected boolean canCompareForeignMapEntries() {
    return false;
  }
}
