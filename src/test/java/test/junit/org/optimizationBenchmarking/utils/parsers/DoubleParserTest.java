package test.junit.org.optimizationBenchmarking.utils.parsers;

import java.util.Collection;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.collections.ImmutableAssociation;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.parsers.DoubleParser;
import org.optimizationBenchmarking.utils.parsers.Parser;

/**
 * the basic test for double parsers
 */
public class DoubleParserTest extends ParserTest<Double> {

  /** create the test */
  public DoubleParserTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected DoubleParser getParser() {
    return DoubleParser.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected Collection<Map.Entry<Object, Double>> getExpectedObjectParsingResults() {
    return ((Collection) (new ArrayListView<>(new Map.Entry[] {//
        new ImmutableAssociation(Double.valueOf(0.3),
            Double.valueOf(0.3d)),//
            new ImmutableAssociation(Integer.valueOf(7),
                Double.valueOf(7d)),//
                new ImmutableAssociation(Float.valueOf(-2.4f),
                    Double.valueOf(Float.valueOf(-2.4f).doubleValue())),//
                    new ImmutableAssociation(Byte.valueOf((byte) 21),
                        Double.valueOf(21d)),//
                        new ImmutableAssociation(Long.valueOf(Integer.MAX_VALUE),
                            Double.valueOf(Integer.MAX_VALUE)),//
                            new ImmutableAssociation(
                                Double.valueOf(Double.POSITIVE_INFINITY),
                                Double.valueOf(Double.POSITIVE_INFINITY)),//
    })));
  }

  /**
   * A set of expected results for string-based parsing
   *
   * @return the set of expected results for string-based parsing
   */
  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected Collection<Map.Entry<String, Double>> getExpectedStringParsingResults() {
    return ((Collection) (new ArrayListView<>(new Map.Entry[] {//
        DoubleParserTest.__entry(0.3d, "0.3"),//$NON-NLS-1$
        DoubleParserTest.__entry(7, "7"),//$NON-NLS-1$
        DoubleParserTest.__entry(-2.4d, "-2.4"),//$NON-NLS-1$
        DoubleParserTest.__entry(21d, "21"),//$NON-NLS-1$
        DoubleParserTest.__entry(Integer.MAX_VALUE,
            "Integer#MAX_VALUE"),//$NON-NLS-1$
            DoubleParserTest.__entry(Double.POSITIVE_INFINITY,
                "Double#POSITIVE_INFINITY"), //$NON-NLS-1$
                DoubleParserTest.__entry((Integer.MAX_VALUE),
                    String.valueOf(Integer.MAX_VALUE)),//
                    DoubleParserTest.__entry(Double.POSITIVE_INFINITY,
                        String.valueOf(Double.POSITIVE_INFINITY)),//

                        DoubleParserTest.__entry(0, "0"),//$NON-NLS-1$
                        DoubleParserTest.__entry(Math.E, "'e'"),//$NON-NLS-1$
                        DoubleParserTest.__entry(Math.E, "-(-E)"),//$NON-NLS-1$
                        DoubleParserTest.__entry(1e-13, "1e-13"),//$NON-NLS-1$
                        DoubleParserTest.__entry(-1.31234234, "-1.31234234"),//$NON-NLS-1$
                        DoubleParserTest.__entry(3, "0x3"),//$NON-NLS-1$
                        DoubleParserTest.__entry(-16, "-0x10"),//$NON-NLS-1$
                        DoubleParserTest.__entry(Double.POSITIVE_INFINITY,//
                            "Double#POSITIVE_INFINITY"),//$NON-NLS-1$
                            DoubleParserTest.__entry(Double.NEGATIVE_INFINITY,//
                                "\"-+- +-'Double#POSITIVE_INFINITY'\""),//$NON-NLS-1$
                                DoubleParserTest.__entry(Math.E, "Math#E"),//$NON-NLS-1$
                                DoubleParserTest.__entry(-Math.PI, "-\u03c0"),//$NON-NLS-1$
                                DoubleParserTest.__entry(-Math.PI, "-\\u03c0"),//$NON-NLS-1$
                                DoubleParserTest.__entry(-Math.PI, "-(\\u03c0)"),//$NON-NLS-1$
                                DoubleParserTest.__entry(-Math.PI, "\"-(\\u03c0)\""),//$NON-NLS-1$
                                DoubleParserTest.__entry(Math.PI, "-\"-(\\u03c0)\""),//$NON-NLS-1$
                                DoubleParserTest.__entry(-Math.PI, "-java.lang.Math#PI"),//$NON-NLS-1$
                                DoubleParserTest.__entry(Double.NaN,
                                    String.valueOf(Double.NaN)),//
                                    DoubleParserTest.__entry(-Math.PI,//
                                        ('-' + Character.toString((char) 0x3c0))),
                                        DoubleParserTest.__entry(Math.E, " Ma th# E "),//$NON-NLS-1$
                                        DoubleParserTest.__entry(Math.E / 100, (" Ma th# E %")),//$NON-NLS-1$
                                        DoubleParserTest.__entry(
                                            -(1 | 2 | 4 | 8 | 256 | 512 | 1024 | 2048),//
                                            ("-0b0000_1111_0000_1111")),//$NON-NLS-1$
                                            DoubleParserTest.__entry(Integer.MAX_VALUE,//
                                                "Integer#MAX_VALUE"),//$NON-NLS-1$
                                                DoubleParserTest.__entry(Short.SIZE, "Short#SIZE"),//$NON-NLS-1$;
                                                DoubleParserTest.__entry(-0.3334d, "-[0.3334]"),//$NON-NLS-1$
                                                DoubleParserTest.__entry(-0.3334d / 100d, "-[0.3334]%"),//$NON-NLS-1$
                                                DoubleParserTest.__entry(-0.3334d / 1e6d, "-[0.3334" + //$NON-NLS-1$
                                                    Character.toString((char) (0xb5)) + ']'),
                                                    DoubleParserTest.__entry(-0.3334d / 1e6d, "-<0.3334\\u00b5>"),//$NON-NLS-1$

    })));
  }

  /**
   * create a test record
   *
   * @param val
   *          the value
   * @param text
   *          the text
   * @return the record
   */
  private static final ImmutableAssociation<String, Double> __entry(
      final double val, final String text) {
    return new ImmutableAssociation<>(text, //
        Double.valueOf(val));
  }

  /** {@inheritDoc} */
  @Override
  protected Collection<Object> getExpectedObjectParsingFailures() {
    return new ArrayListView<>(new Object[] {//
        null, new Object() });
  }

  /** {@inheritDoc} */
  @Override
  protected Collection<String> getExpectedStringParsingFailures() {
    return new ArrayListView<>(new String[] {//
        "sohfsaflhsf",//$NON-NLS-1$
        "xcsf sfdsafd"//$NON-NLS-1$
    });
  }

  /**
   * test a double
   *
   * @param d
   *          the double
   * @param t
   *          the string
   */
  private final void __testDouble(final Double d, final String t) {
    final Parser<Double> p;

    p = this.getParser();
    try {
      Assert.assertEquals(d, p.parseObject(t));
      Assert.assertEquals(d, p.parseString(t));
    } catch (final Throwable tt) {
      throw new AssertionError(tt);
    }
  }

  /**
   * Test whether parsing strings to doubles works well
   */
  @Test(timeout = 3600000)
  public void testParseDouble() {
    final Random r;
    int i;
    double test;
    Double d;
    long testLong, otl;

    r = new Random();

    for (i = 10000; i >= 0; i--) {
      if (r.nextBoolean()) {
        test = r.nextLong();
      } else {
        test = r.nextDouble();
      }

      test = Double.parseDouble(Double.toString(test));
      d = Double.valueOf(test);

      this.__testDouble(d, String.valueOf(test));
      this.__testDouble(d, d.toString());

      this.__testDouble(d, Double.toHexString(test));

      test = (-test);
      test = Double.parseDouble(Double.toString(test));
      d = Double.valueOf(test);

      this.__testDouble(d, Double.toString(test));
      this.__testDouble(d, Double.toHexString(test));

      testLong = Long.MIN_VALUE;
      do {
        otl = testLong;
        test = Math.abs(test);
        testLong = Math.round(test);
        test = Double.parseDouble(Double.toString(testLong));
      } while (otl != testLong);

      d = Double.valueOf(test);

      this.__testDouble(d, ("0x" + Long.toHexString(testLong))); //$NON-NLS-1$
      this.__testDouble(d, ("0b" + Long.toBinaryString(testLong))); //$NON-NLS-1$
      this.__testDouble(d, ("0o" + Long.toOctalString(testLong))); //$NON-NLS-1$
    }
  }

}
