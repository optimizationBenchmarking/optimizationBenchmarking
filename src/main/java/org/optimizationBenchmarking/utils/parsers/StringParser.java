package org.optimizationBenchmarking.utils.parsers;

import java.text.Normalizer;

import org.optimizationBenchmarking.utils.EmptyUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.charset.Char;
import org.optimizationBenchmarking.utils.text.charset.Characters;
import org.optimizationBenchmarking.utils.text.charset.QuotationMark;

/** A parser for a given type */
public class StringParser extends Parser<String> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;
  /** the globally shared instance of the boolean parser */
  public static final StringParser INSTANCE = new StringParser();

  /** create the parser */
  protected StringParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<String> getOutputClass() {
    return String.class;
  }

  /**
   * parse the string, but don't validate yet
   *
   * @param inString
   *          the string
   * @return the result
   */
  private final String __parseString(final String inString) {
    char[] data;
    char ch;
    int end, start, i, code, val, z;
    QuotationMark qm;
    Char chr;
    String string, temp;

    if (inString == null) {
      return EmptyUtils.EMPTY_STRING;
    }

    string = inString;
    z = 2;
    for (;;) {

      data = string.toCharArray();
      for (end = data.length; (end > 0); end--) {// TODO: check (end > 0)
        ch = data[end - 1];
        if ((ch > 32) && (!(Character.isISOControl(ch)))) {
          break;
        }
      }

      for (start = 0; start < end; start++) {
        ch = data[start];
        if ((ch > 32) && (!(Character.isISOControl(ch)))) {
          break;
        }
      }

      if (start >= end) {
        return EmptyUtils.EMPTY_STRING;
      }

      for (i = start; i < end; i++) {

        if (data[i] == '\\') {
          if (i < (end - 1)) {
            ch = data[i + 1];
            if (TextUtils.toLowerCase(ch) == 'u') {
              if (i < (end - 5)) {
                val = code = Character.digit(data[i + 2], 16);
                if (code < 0) {
                  continue;
                }
                val <<= 4;
                code = Character.digit(data[i + 3], 16);
                if (code < 0) {
                  continue;
                }
                val += code;
                val <<= 4;
                code = Character.digit(data[i + 4], 16);
                if (code < 0) {
                  continue;
                }
                val += code;
                val <<= 4;
                code = Character.digit(data[i + 5], 16);
                if (code < 0) {
                  continue;
                }
                val += code;
                data[i] = ((char) (val));
                System.arraycopy(data, (i + 6), data, (i + 1),
                    (end - i - 6));
                end -= 5;
              }
              continue;
            }
            chr = Characters.CHARACTERS.getEscapedChar(ch);
            if (chr == null) {
              continue;
            }
            data[i] = chr.getChar();
            System.arraycopy(data, (i + 2), data, (i + 1), (end - i - 2));
            end--;
            continue;
          }
        }
      }

      for (;; end--) {
        ch = data[end - 1];
        if ((ch > 32) && (!(Character.isISOControl(ch)))) {
          break;
        }
      }

      for (; start < end; start++) {
        ch = data[start];
        if ((ch > 32) && (!(Character.isISOControl(ch)))) {
          break;
        }
      }

      if (start >= end) {
        return EmptyUtils.EMPTY_STRING;
      }

      i = 0;
      if ((end - start) >= 2) {
        chr = Characters.CHARACTERS.getChar(data[start]);
        if (chr instanceof QuotationMark) {
          qm = ((QuotationMark) chr);
          if (qm.isOpening()
              && (qm.canEndWith(Characters.CHARACTERS
                  .getChar(data[end - 1])))) {
            start++;
            end--;
          }
        }
      }

      if (start >= end) {
        return EmptyUtils.EMPTY_STRING;
      }

      if ((start <= 0) && (end >= string.length())) {
        return string;
      }

      string = String.valueOf(data, start, (end - start));
      if ((--z) <= 0) {
        if (string.equals(inString)) {
          return inString;
        }
        return string;
      }

      temp = Normalizer.normalize(string,
          TextUtils.DEFAULT_NORMALIZER_FORM);
      if (temp == null) {
        return EmptyUtils.EMPTY_STRING;
      }
      if (temp.equals(inString)) {
        return inString;
      }
      if (temp.equals(string)) {
        return string;
      }
      string = temp;
      temp = null;
    }
  }

  /** {@inheritDoc} */
  @Override
  public void validate(final String instance)
      throws IllegalArgumentException {
    //
  }

  /** {@inheritDoc} */
  @Override
  public final String parseString(final String string) {
    final String ret;

    ret = this.__parseString(string);
    this.validate(ret);
    return ret;
  }

  /** {@inheritDoc} */
  @Override
  public final String parseObject(final Object o) {
    final String ret;

    ret = ((o == null) ? EmptyUtils.EMPTY_STRING : //
        this.__parseString(String.valueOf(o)));
    this.validate(ret);
    return ret;
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return StringParser.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return StringParser.INSTANCE;
  }
}
