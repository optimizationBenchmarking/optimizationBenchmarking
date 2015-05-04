package test.junit.org.optimizationBenchmarking.utils.hash;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/** the hash utils test */
public class HashUtilsTest {

  /** test hash codes of ints */
  @Test(timeout = 3600000)
  public void testIntHashCode() {
    final Random r;
    int i, j;

    j = 0;
    Assert.assertEquals(Integer.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = 1;
    Assert.assertEquals(Integer.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = (-1);
    Assert.assertEquals(Integer.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = Integer.MAX_VALUE;
    Assert.assertEquals(Integer.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = Integer.MIN_VALUE;
    Assert.assertEquals(Integer.valueOf(j).hashCode(),
        HashUtils.hashCode(j));

    r = new Random();
    for (i = 1000; (--i) >= 0;) {
      j = r.nextInt();
      Assert.assertEquals(//
          Integer.valueOf(j).hashCode(), HashUtils.hashCode(j));
    }
  }

  /** test hash codes of longs */
  @Test(timeout = 3600000)
  public void testLongHashCode() {
    final Random r;
    int i;
    long j;

    j = 0;
    Assert.assertEquals(Long.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = 1;
    Assert.assertEquals(Long.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = (-1);
    Assert.assertEquals(Long.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = Long.MAX_VALUE;
    Assert.assertEquals(Long.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = Long.MIN_VALUE;
    Assert.assertEquals(Long.valueOf(j).hashCode(), HashUtils.hashCode(j));

    r = new Random();
    for (i = 1000; (--i) >= 0;) {
      j = r.nextLong();
      Assert.assertEquals(//
          Long.valueOf(j).hashCode(), HashUtils.hashCode(j));
    }
  }

  /** test hash codes of shorts */
  @Test(timeout = 3600000)
  public void testShortHashCode() {
    final Random r;
    int i;
    short j;

    j = 0;
    Assert
    .assertEquals(Short.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = 1;
    Assert
    .assertEquals(Short.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = (-1);
    Assert
    .assertEquals(Short.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = Short.MAX_VALUE;
    Assert
    .assertEquals(Short.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = Short.MIN_VALUE;
    Assert
    .assertEquals(Short.valueOf(j).hashCode(), HashUtils.hashCode(j));

    r = new Random();
    for (i = 1000; (--i) >= 0;) {
      j = ((short) (r.nextInt() & 0xffff));
      Assert.assertEquals(//
          Short.valueOf(j).hashCode(), HashUtils.hashCode(j));
    }
  }

  /** test hash codes of bytes */
  @Test(timeout = 3600000)
  public void testByteHashCode() {
    final Random r;
    int i;
    byte j;

    j = 0;
    Assert.assertEquals(Byte.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = 1;
    Assert.assertEquals(Byte.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = (-1);
    Assert.assertEquals(Byte.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = Byte.MAX_VALUE;
    Assert.assertEquals(Byte.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = Byte.MIN_VALUE;
    Assert.assertEquals(Byte.valueOf(j).hashCode(), HashUtils.hashCode(j));

    r = new Random();
    for (i = 1000; (--i) >= 0;) {
      j = ((byte) (r.nextInt() & 0xff));
      Assert.assertEquals(//
          Byte.valueOf(j).hashCode(), HashUtils.hashCode(j));
    }
  }

  /** test hash codes of floats */
  @Test(timeout = 3600000)
  public void testFloatHashCode() {
    final Random r;
    int i;
    float j;

    j = 0;
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = 1;
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = (-1);
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = Float.MAX_VALUE;
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = Float.MIN_VALUE;
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = Float.MIN_NORMAL;
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = (-Float.MAX_VALUE);
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = (-Float.MIN_NORMAL);
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = (-Float.MIN_NORMAL);
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = Float.POSITIVE_INFINITY;
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = Float.NEGATIVE_INFINITY;
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = Float.NaN;
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = (-Float.NaN);
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = (1f / 0f);
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = (-1f / 0f);
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = (0f / 0f);
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = (-0f / 0f);
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = (0f);
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    j = (-0f);
    Assert
    .assertEquals(Float.valueOf(j).hashCode(), HashUtils.hashCode(j));

    r = new Random();
    for (i = 1000; (--i) >= 0;) {
      j = Float.intBitsToFloat(r.nextInt());
      Assert.assertEquals(//
          Float.valueOf(j).hashCode(), HashUtils.hashCode(j));
    }
  }

  /** test hash codes of doubles */
  @Test(timeout = 3600000)
  public void testDoubleHashCode() {
    final Random r;
    int i;
    double j;

    j = 0;
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = 1;
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = (-1);
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = Double.MAX_VALUE;
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = Double.MIN_VALUE;
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = Double.MIN_NORMAL;
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = (-Double.MAX_VALUE);
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = (-Double.MIN_NORMAL);
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = (-Double.MIN_NORMAL);
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = Double.POSITIVE_INFINITY;
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = Double.NEGATIVE_INFINITY;
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = Double.NaN;
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = (-Double.NaN);
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = (1d / 0d);
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = (-1d / 0d);
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = (0d / 0d);
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = (-0d / 0d);
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = (0d);
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = (-0d);
    Assert.assertEquals(Double.valueOf(j).hashCode(),
        HashUtils.hashCode(j));

    r = new Random();
    for (i = 1000; (--i) >= 0;) {
      j = Double.longBitsToDouble(r.nextLong());
      Assert.assertEquals(//
          Double.valueOf(j).hashCode(), HashUtils.hashCode(j));
    }
  }

  /** test hash codes of chars */
  @Test(timeout = 3600000)
  public void testCharacterHashCode() {
    final Random r;
    int i;
    char j;

    j = 0;
    Assert.assertEquals(Character.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = 1;
    Assert.assertEquals(Character.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = Character.MAX_VALUE;
    Assert.assertEquals(Character.valueOf(j).hashCode(),
        HashUtils.hashCode(j));
    j = Character.MIN_VALUE;
    Assert.assertEquals(Character.valueOf(j).hashCode(),
        HashUtils.hashCode(j));

    r = new Random();
    for (i = 1000; (--i) >= 0;) {
      j = ((char) (r.nextInt() & 0xffff));
      Assert.assertEquals(//
          Character.valueOf(j).hashCode(), HashUtils.hashCode(j));
    }
  }

  /** test hash codes of object */
  @Test(timeout = 3600000)
  public void testObjectHashCode() {
    Object o;

    Assert.assertEquals(0, HashUtils.hashCode(null));

    o = new Object();
    Assert.assertEquals(o.hashCode(), HashUtils.hashCode(o));
  }

  /** test hash codes of booleans */
  @Test(timeout = 3600000)
  public void testBooleanHashCode() {
    Assert.assertEquals(Boolean.TRUE.hashCode(), HashUtils.hashCode(true));
    Assert.assertEquals(Boolean.FALSE.hashCode(),
        HashUtils.hashCode(false));
  }
}
