package test.junit.org.optimizationBenchmarking.utils.parsers;

import java.util.Collection;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.parsers.Parser;

import test.junit.TestBase;

/**
 * the basic test for parsers
 *
 * @param <T>
 *          the parsed type
 */
@Ignore
public abstract class ParserTest<T> extends TestBase {

  /** create the test */
  protected ParserTest() {
    super();
  }

  /**
   * Get the parser
   *
   * @return the parser
   */
  protected abstract Parser<T> getParser();

  /**
   * A set of expected results for object-based parsing
   *
   * @return the set of expected results for object-based parsing
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected Collection<Map.Entry<Object, T>> getExpectedObjectParsingResults() {
    return ((Collection) (ArraySetView.EMPTY_SET_VIEW));
  }

  /**
   * A set of expected results for string-based parsing
   *
   * @return the set of expected results for string-based parsing
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected Collection<Map.Entry<String, T>> getExpectedStringParsingResults() {
    return ((Collection) (ArraySetView.EMPTY_SET_VIEW));
  }

  /**
   * A set of inputs where object-based parsing must fail
   *
   * @return the set of inputs where object-based parsing must fail
   */
  protected Collection<Object> getExpectedObjectParsingFailures() {
    return ArraySetView.EMPTY_SET_VIEW;
  }

  /**
   * A set of inputs where string-based parsing must fail
   *
   * @return the set of inputs where string-based parsing must fail
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected Collection<String> getExpectedStringParsingFailures() {
    return ((Collection) (ArraySetView.EMPTY_SET_VIEW));
  }

  /** test whether the parser is null */
  @Test(timeout = 3600000)
  public void testParserNotNull() {
    Assert.assertNotNull(this.getParser());
  }

  /** test whether the parser class is valid */
  @Test(timeout = 3600000)
  public void testParserClassValid() {
    Class<T> clazz;
    clazz = this.getParser().getOutputClass();
    Assert.assertNotNull(clazz);
    Assert.assertTrue(Object.class.isAssignableFrom(clazz));
  }

  /** test whether the string parses the object inputs correctly */
  @Test(timeout = 3600000)
  public void testExpectedStringParseResults() {
    final Parser<T> parser;

    parser = this.getParser();
    for (final Map.Entry<String, T> expected : this
        .getExpectedStringParsingResults()) {
      try {
        Assert.assertEquals(expected.getValue(),
            parser.parseString(expected.getKey()));
      } catch (final Throwable tt) {
        throw new AssertionError(tt);
      }
    }
  }

  /** test whether the parser parses the object inputs correctly */
  @SuppressWarnings("unchecked")
  @Test(timeout = 3600000)
  public void testExpectedObjectParseResults() {
    final Parser<T> parser;
    final Collection<Map.Entry<Object, T>>[] arr;

    arr = new Collection[] { this.getExpectedObjectParsingResults(),
        this.getExpectedStringParsingResults() };

    parser = this.getParser();
    for (final Collection<Map.Entry<Object, T>> list : arr) {
      for (final Map.Entry<Object, T> expected : list) {
        try {
          Assert.assertEquals(expected.getValue(),
              parser.parseObject(expected.getKey()));
        } catch (final Throwable tt) {
          throw new AssertionError(tt);
        }
      }
    }
  }

  /** test whether the string parses the object inputs correctly */
  @Test(timeout = 3600000)
  public void testExpectedStringParseFailures() {
    final Parser<T> parser;

    parser = this.getParser();
    for (final String expected : this.getExpectedStringParsingFailures()) {
      try {
        parser.parseString(expected);
        Assert.fail("Damn, this should have thrown an exception."); //$NON-NLS-1$
      } catch (final Throwable tt) {// ok
      }
    }
  }

  /** test whether the parser parses the object inputs correctly */
  @SuppressWarnings("unchecked")
  @Test(timeout = 3600000)
  public void testExpectedObjectParseFailures() {
    final Parser<T> parser;
    final Collection<Object>[] arr;

    arr = new Collection[] { this.getExpectedObjectParsingFailures(),
        this.getExpectedStringParsingFailures() };

    parser = this.getParser();
    for (final Collection<Object> list : arr) {
      for (final Object expected : list) {
        try {
          parser.parseObject(expected);
          Assert.fail("Damn, this should have thrown an exception."); //$NON-NLS-1$
        } catch (final Throwable tt) {// ok
        }
      }
    }
  }

}
