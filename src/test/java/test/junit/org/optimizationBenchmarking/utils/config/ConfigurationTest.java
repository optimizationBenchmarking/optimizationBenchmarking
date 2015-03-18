package test.junit.org.optimizationBenchmarking.utils.config;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.collections.maps.StringMapCI;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.config.ConfigurationBuilder;
import org.optimizationBenchmarking.utils.config.ConfigurationXMLInput;
import org.optimizationBenchmarking.utils.config.ConfigurationXMLOutput;
import org.optimizationBenchmarking.utils.math.random.RandomUtils;
import org.optimizationBenchmarking.utils.parsers.BooleanParser;
import org.optimizationBenchmarking.utils.parsers.ByteParser;
import org.optimizationBenchmarking.utils.parsers.CharParser;
import org.optimizationBenchmarking.utils.parsers.DoubleParser;
import org.optimizationBenchmarking.utils.parsers.FloatParser;
import org.optimizationBenchmarking.utils.parsers.IntParser;
import org.optimizationBenchmarking.utils.parsers.LongParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.parsers.ShortParser;
import org.optimizationBenchmarking.utils.parsers.StringParser;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.charset.QuotationMarks;

import test.junit.TestBase;
import test.junit.org.optimizationBenchmarking.utils.collections.maps.StringMapTest;

/**
 * A test for configuration objects.
 */
public class ConfigurationTest extends TestBase {

  /** characters allowed in keys */
  private static final String KEY_CHARS = "0123456789abcdefghijklmnopqrstuvwxyz_#+!§$%&?*"; //$NON-NLS-1$

  /** characters allowed in values */
  private static final String VALUE_CHARS = StringMapTest.STRING_MAP_ALLOWED_CHARS;

  /** the set of basic parsers */
  private static final IdentityHashMap<Object, Parser<Object>> BASIC_PARSERS = ConfigurationTest
      .__makeHM();

  /**
   * make
   * 
   * @return the hash map
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private static final IdentityHashMap<Object, Parser<Object>> __makeHM() {
    final IdentityHashMap map;

    map = new IdentityHashMap<>();
    map.put(Boolean.class, ((BooleanParser.INSTANCE)));
    map.put(boolean.class, ((BooleanParser.INSTANCE)));
    map.put(Byte.class, ((ByteParser.INSTANCE)));
    map.put(byte.class, ((ByteParser.INSTANCE)));
    map.put(Character.class, ((CharParser.INSTANCE)));
    map.put(char.class, ((CharParser.INSTANCE)));
    map.put(Double.class, ((DoubleParser.INSTANCE)));
    map.put(double.class, ((DoubleParser.INSTANCE)));
    map.put(Float.class, ((FloatParser.INSTANCE)));
    map.put(float.class, ((FloatParser.INSTANCE)));
    map.put(Integer.class, ((IntParser.INSTANCE)));
    map.put(int.class, ((IntParser.INSTANCE)));
    map.put(Long.class, ((LongParser.INSTANCE)));
    map.put(long.class, ((LongParser.INSTANCE)));
    map.put(Short.class, ((ShortParser.INSTANCE)));
    map.put(short.class, ((ShortParser.INSTANCE)));
    map.put(String.class, ((StringParser.INSTANCE)));

    return ((map));
  }

  /** create the test */
  public ConfigurationTest() {
    super();
  }

  /**
   * make a parameter
   * 
   * @param key
   *          the key
   * @param value
   *          the value
   * @param r
   *          the randomizer
   * @return the parameter
   */
  private static final String __makeParam(final String key,
      final String value, final Random r) {
    final StringBuilder sb;

    sb = new StringBuilder();
    while (r.nextBoolean()) {
      if (r.nextBoolean() && (File.separatorChar != '/')) {
        sb.append('/');
      } else {
        sb.append('-');
      }
    }

    sb.append(ConfigurationTest.__makeKey(key, r));

    if (r.nextBoolean()) {
      sb.append(':');
    } else {
      sb.append('=');
    }

    sb.append(value);
    return sb.toString();
  }

  /**
   * make a key
   * 
   * @param key
   *          the key
   * @param random
   *          the randomizer
   * @return the return value
   */
  private static final String __makeKey(final String key,
      final Random random) {
    final StringBuilder sb;
    char ch;
    int i;

    switch (random.nextInt(4)) {
      case 0: {
        return key;
      }
      case 1: {
        return key.toLowerCase();
      }
      case 2: {
        return key.toUpperCase();
      }
      default: {
        sb = new StringBuilder();
        for (i = 0; i < key.length(); i++) {
          ch = key.charAt(i);
          switch (random.nextInt(3)) {
            case 0: {
              sb.append(ch);
              break;
            }
            case 1: {
              sb.append(Character.toUpperCase(ch));
              break;
            }
            default: {
              sb.append(Character.toLowerCase(ch));
              break;
            }
          }
        }
        return sb.toString();
      }
    }
  }

  /** test whether setting the xml serialization line works */
  @Test(timeout = 3600000)
  public void testXMLSerializationRandomStrings() {
    final Configuration inst, b;
    final Random r;
    final StringMapCI<String> data;
    final ConfigurationXMLInput input;
    final ConfigurationXMLOutput output;
    String x, y;

    input = ConfigurationXMLInput.getInstance();
    Assert.assertNotNull(input);
    Assert.assertTrue(input.canUse());

    output = ConfigurationXMLOutput.getInstance();
    Assert.assertNotNull(output);
    Assert.assertTrue(output.canUse());

    r = new Random();

    try (final ConfigurationBuilder cb = new ConfigurationBuilder()) {
      data = new StringMapCI<>();
      do {
        do {
          x = RandomUtils.longToString(ConfigurationTest.KEY_CHARS,
              r.nextLong());
        } while (data.containsKey(x));
        y = RandomUtils.longToString(ConfigurationTest.VALUE_CHARS,
            r.nextLong());
        data.put(x, y);
        cb.put(x, y);
      } while (r.nextInt(40) > 0);
      inst = cb.getResult();
    }

    try {
      try (StringWriter sw = new StringWriter()) {
        output.use().setWriter(sw).setSource(inst).create().call();
        x = sw.toString();
      }

      try (final ConfigurationBuilder fb = new ConfigurationBuilder()) {
        try (StringReader sr = new StringReader(x)) {
          input.use().addReader(sr).setDestination(fb).create().call();
        }
        b = fb.getResult();
      }
    } catch (final Throwable t) {
      throw new RuntimeException(t);
    }

    for (final Map.Entry<String, String> e : data) {
      Assert.assertEquals(inst.getString(e.getKey(), null),
          b.getString(e.getKey(), "\0x")); //$NON-NLS-1$
    }

  }

  /** test whether setting the properties serialization line works */
  @Test(timeout = 3600000)
  public void testPropertiesSerializationRandomStrings() {
    final Configuration inst, b;
    final Random r;
    final StringMapCI<String> data;
    final ConfigurationXMLInput input;
    final ConfigurationXMLOutput output;
    String x, y;

    input = ConfigurationXMLInput.getInstance();
    Assert.assertNotNull(input);
    Assert.assertTrue(input.canUse());

    output = ConfigurationXMLOutput.getInstance();
    Assert.assertNotNull(output);
    Assert.assertTrue(output.canUse());

    r = new Random();

    try (final ConfigurationBuilder cb = new ConfigurationBuilder()) {
      data = new StringMapCI<>();
      do {
        do {
          x = RandomUtils.longToString(ConfigurationTest.KEY_CHARS,
              r.nextLong());
        } while (data.containsKey(x));
        y = RandomUtils.longToString(ConfigurationTest.VALUE_CHARS,
            r.nextLong());
        data.put(x, y);
        cb.put(x, y);
      } while (r.nextInt(40) > 0);
      inst = cb.getResult();
    }

    try {
      try (StringWriter sw = new StringWriter()) {
        output.use().setWriter(sw).setSource(inst).create().call();
        x = sw.toString();
      }

      try (final ConfigurationBuilder fb = new ConfigurationBuilder()) {
        try (StringReader sr = new StringReader(x)) {
          input.use().addReader(sr).setDestination(fb).create().call();
        }
        b = fb.getResult();
      }
    } catch (final Throwable t) {
      throw new RuntimeException(t);
    }

    for (final Map.Entry<String, String> e : data) {
      Assert.assertEquals(inst.getString(e.getKey(), null),
          b.getString(e.getKey(), "\0x")); //$NON-NLS-1$
    }

  }

  /** test whether setting the command line works */
  @Test(timeout = 3600000)
  public void testSetCommandLineAndGetStrings() {
    final HashMap<String, String> values;
    final ArrayList<String> params;
    final Random random;
    Configuration cfg;
    int i;
    long v;
    String key, value, def;

    values = new HashMap<>();
    params = new ArrayList<>();
    random = new Random();

    for (i = 0; i < 10; i++) {

      try (final ConfigurationBuilder cb = new ConfigurationBuilder()) {
        values.clear();
        params.clear();

        v = 0;
        do {
          key = RandomUtils.longToString(ConfigurationTest.KEY_CHARS, v++);
          if (random.nextBoolean()) {
            value = RandomUtils.longToString(
                ConfigurationTest.VALUE_CHARS, v++);
            params.add(ConfigurationTest.__makeParam(key, value, random));
          } else {
            value = null;
          }

          values.put(key, value);
        } while ((random.nextInt(40) > 0) || (params.size() <= 0));

        cb.putCommandLine(//
        params.toArray(new String[params.size()]));
        cfg = cb.getResult();
      }

      for (final Map.Entry<String, String> e : values.entrySet()) {
        key = ConfigurationTest.__makeKey(e.getKey(), random);
        value = e.getValue();

        def = RandomUtils.longToString(ConfigurationTest.VALUE_CHARS, ++v);

        if (value == null) {
          Assert.assertSame(def, cfg.getString(key, def));
        } else {
          Assert.assertEquals(value, cfg.getString(key, def));
        }
      }
    }
  }

  /**
   * Transform an object to a string
   * 
   * @param o
   *          the object
   * @param random
   *          the randomizer
   * @return the string
   */
  private static final String __toString(final Object o,
      final Random random) {
    final String s;
    final QuotationMarks qm;

    s = String.valueOf(o);
    if (random.nextBoolean()) {
      qm = QuotationMarks.QUOTATION_MARKS.get(random
          .nextInt(QuotationMarks.QUOTATION_MARKS.size()));
      return (qm.getBeginChar() + s + qm.getEndChar());
    }

    return s;
  }

  /** test whether setting the command line works */
  @Test(timeout = 3600000)
  public void testSetCommandLineAndGetTyped() {
    final HashMap<String, Object> values;
    final ArrayList<String> params;
    final Random random;
    Configuration cfg;
    int i;
    long v;
    String key;
    Object value, value2, def;
    Parser<Object> p;

    values = new HashMap<>();
    params = new ArrayList<>();
    random = new Random();

    for (i = 0; i < 10; i++) {

      try (final ConfigurationBuilder cb = new ConfigurationBuilder()) {
        values.clear();
        params.clear();

        v = 0;
        do {
          key = RandomUtils.longToString(ConfigurationTest.KEY_CHARS, ++v);
          if (random.nextBoolean()) {
            value = RandomUtils.longToObject(++v, false);
            if (value instanceof Character) {
              value = Character.valueOf(TextUtils.normalize(
                  value.toString()).charAt(0));
            }
            params.add(ConfigurationTest.__makeParam(key,
                ConfigurationTest.__toString(value, random), random));
          } else {
            value = null;
          }
          values.put(key, value);
        } while ((random.nextInt(40) > 0) || (params.size() <= 0));

        cb.putCommandLine(//
        params.toArray(new String[params.size()]));
        cfg = cb.getResult();
      }

      for (final Map.Entry<String, Object> e : values.entrySet()) {
        key = e.getKey();
        value = e.getValue();

        if (value == null) {
          def = RandomUtils.longToObject(random.nextLong(), false);

          p = ConfigurationTest.BASIC_PARSERS.get(def.getClass());
          Assert.assertSame(def,
              cfg.get(ConfigurationTest.__makeKey(key, random), p, def));
          Assert.assertSame(def,
              cfg.get(ConfigurationTest.__makeKey(key, random), p, def));
          Assert.assertSame(def,
              cfg.get(ConfigurationTest.__makeKey(key, random), p, null));

        } else {
          do {
            def = RandomUtils.longToObject(random.nextLong(), false);
          } while ((def.getClass() != value.getClass())
              || (value.equals(def)));

          p = ConfigurationTest.BASIC_PARSERS.get(value.getClass());
          value2 = cfg.get(key, p, def);
          Assert.assertEquals(value, value2);
          Assert.assertSame(value2,
              cfg.get(ConfigurationTest.__makeKey(key, random), p, def));
          Assert.assertSame(value2,
              cfg.get(ConfigurationTest.__makeKey(key, random), p, null));
          Assert.assertSame(value2,
              cfg.get(ConfigurationTest.__makeKey(key, random), p, value));
          Assert.assertSame(value2,//
              cfg.get(ConfigurationTest.__makeKey(key, random), //
                  p, value2));
        }

        try {
          cfg.get(
              ConfigurationTest.__makeKey(key, random),//
              ((p == ((Object) (LongParser.INSTANCE))) ? IntParser.INSTANCE
                  : LongParser.INSTANCE), null);
          Assert.fail("Changing the type of elements is not permitted."); //$NON-NLS-1$
        } catch (final IllegalStateException | ClassCastException tt) {
          //
        }
      }
    }
  }

}
