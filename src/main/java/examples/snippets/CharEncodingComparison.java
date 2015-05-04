package examples.snippets;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.text.Normalizer;

/**
 * <p>
 * A small class for making a HTML table comparing the representations of
 * different characters under different encodings.
 * </p>
 */
public final class CharEncodingComparison {

  /** A list of the encodings to test compare. */
  private static final String[] ENCODINGS = { "UTF-8",//$NON-NLS-1$
      "ISO-8859-1",//$NON-NLS-1$
      "Windows-1252",//$NON-NLS-1$
      "US-ASCII",//$NON-NLS-1$
      "CP1047",//$NON-NLS-1$
      "GB2312",//$NON-NLS-1$
      "GBK",//$NON-NLS-1$
      "GB18030",//$NON-NLS-1$
      "Big5",//$NON-NLS-1$
      "UTF-16LE",//$NON-NLS-1$
      "UTF-16BE",//$NON-NLS-1$
      "UTF-32LE",//$NON-NLS-1$
      "UTF-32BE",//$NON-NLS-1$
  };

  /**
   * Links to websites describing the {@link #ENCODINGS encodings} to test.
   */
  private static final String[] ENCODINGS_LINKS = {
      "http://en.wikipedia.org/wiki/UTF-8",//$NON-NLS-1$
      "http://en.wikipedia.org/wiki/ISO/IEC_8859-1",//$NON-NLS-1$
      "http://en.wikipedia.org/wiki/Windows-1252",//$NON-NLS-1$
      "http://en.wikipedia.org/wiki/ASCII",//$NON-NLS-1$
      "http://en.wikipedia.org/wiki/EBCDIC_1047",//$NON-NLS-1$
      "http://en.wikipedia.org/wiki/GB_2312",//$NON-NLS-1$
      "http://en.wikipedia.org/wiki/GBK",//$NON-NLS-1$
      "http://en.wikipedia.org/wiki/GB18030",//$NON-NLS-1$
      "http://en.wikipedia.org/wiki/Big-5",//$NON-NLS-1$
      "http://en.wikipedia.org/wiki/UTF-16",//$NON-NLS-1$
      "http://en.wikipedia.org/wiki/UTF-16",//$NON-NLS-1$
      "http://en.wikipedia.org/wiki/UTF-32",//$NON-NLS-1$
      "http://en.wikipedia.org/wiki/UTF-32",//$NON-NLS-1$
  };

  /** the hex encoding */
  private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6',
      '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

  /**
   * Some standard names for characters with low code points, i.e., the
   * control characters {@code 0} to {@code 31}, plus the space character.
   */
  private static final String[] FIXED_NAMES = { "NUL",//$NON-NLS-1$
      "SOH",//$NON-NLS-1$
      "STX",//$NON-NLS-1$
      "ETX",//$NON-NLS-1$
      "EOT",//$NON-NLS-1$
      "ENQ",//$NON-NLS-1$
      "ACK",//$NON-NLS-1$
      "BEL",//$NON-NLS-1$
      "BS",//$NON-NLS-1$
      "HT",//$NON-NLS-1$
      "LF",//$NON-NLS-1$
      "VT",//$NON-NLS-1$
      "FF",//$NON-NLS-1$
      "CR",//$NON-NLS-1$
      "SO",//$NON-NLS-1$
      "SI",//$NON-NLS-1$
      "DLE",//$NON-NLS-1$
      "DC1",//$NON-NLS-1$
      "DC2",//$NON-NLS-1$
      "DC3",//$NON-NLS-1$
      "DC4",//$NON-NLS-1$
      "NAK",//$NON-NLS-1$
      "SYN",//$NON-NLS-1$
      "ETB",//$NON-NLS-1$
      "CAN",//$NON-NLS-1$
      "EM",//$NON-NLS-1$
      "SUB",//$NON-NLS-1$
      "ESC",//$NON-NLS-1$
      "FS",//$NON-NLS-1$
      "GS",//$NON-NLS-1$
      "RS",//$NON-NLS-1$
      "US",//$NON-NLS-1$
      "SPACE",//$NON-NLS-1$
  };

  /**
   * Text to be printed for characters which do not have a representation
   * under a given encoding. Here we simply use &quot;&empty;&quot;.
   */
  private static final String MISSING = "&empty;";//$NON-NLS-1$

  /** The first code point to be listed: {@value} . */
  private static final int START = 0;

  /** The last code point to be listed: {@value} . */
  private static final int END = 0x3ff;

  /**
   * Encode a character: Translate the character {@code chr} to a byte
   * sequence based on a given {@code encoding}. The byte sequence is then
   * translated to a string in hexadecimal notation, where the
   * least-significant byte is at the left-most position. Depending on the
   * encoding and on the character, the output may be more than one byte,
   * i.e., more than two hex-digits.
   *
   * @param chr
   *          the character
   * @param encoding
   *          the encoding
   * @return the encoding
   */
  private static final String __encode(final int chr, final String encoding) {
    final byte[] data;
    final char[] chx;
    int i, j;
    byte b;

    try {
      try (final ByteArrayOutputStream bs = new ByteArrayOutputStream()) {
        try (final OutputStreamWriter wos = new OutputStreamWriter(bs,
            encoding)) {
          wos.write(chr);
          wos.flush();
        }
        data = bs.toByteArray();
      }

      try (final ByteArrayInputStream bis = new ByteArrayInputStream(data)) {
        try (final InputStreamReader isr = new InputStreamReader(bis,
            encoding)) {
          if (isr.read() != chr) {
            return CharEncodingComparison.MISSING;
          }
        }
      }

      i = data.length;
      chx = new char[i << 1];
      for (j = 0, i = 0; i < data.length; i++) {
        b = data[i];
        chx[j++] = CharEncodingComparison.HEX[(b >>> 4) & 0xf];
        chx[j++] = CharEncodingComparison.HEX[b & 0xf];
      }
      return String.valueOf(chx);
    } catch (final Throwable tt) {
      return CharEncodingComparison.MISSING;
    }
  }

  /**
   * Write a the value of a code point in unicode notation to a given
   * writer.
   *
   * @param ch
   *          the character
   * @param w
   *          the writer
   * @throws IOException
   *           if something fails
   */
  private static final void __codePoint(final int ch, final Writer w)
      throws IOException {
    String s;
    int i;

    w.write('U');
    w.write('+');

    s = Integer.toHexString(ch);
    for (i = s.length(); i < 4; i++) {
      w.write('0');
    }
    w.write(s);
  }

  /**
   * Compute the <a
   * href="http://www.unicode.org/reports/tr15/">NFKC</a>-normalization of
   * the character {@code chr}
   *
   * @param chr
   *          the character
   * @return its normalized form
   */
  private static final String __normalizeNFKC(final int chr) {
    final String s;
    final StringBuilder sb;
    int length, i, a, b, c;
    char x;
    boolean add;

    s = Normalizer.normalize(String.valueOf((char) chr),
        Normalizer.Form.NFKC);
    if (((length = s.length()) <= 0)
        || ((length == 1) && (s.charAt(0) == chr))) {
      return null;
    }

    sb = new StringBuilder(length << 3);
    for (i = 0; i < length; i++) {
      x = s.charAt(i);
      if (x < CharEncodingComparison.FIXED_NAMES.length) {
        sb.append(CharEncodingComparison.FIXED_NAMES[x]);
      } else {
        switch (Character.getType(x)) {
          case Character.NON_SPACING_MARK: {
            add = true;
            break;
          }
          default: {
            add = false;
          }
        }

        if (add) {
          if (i > 0) {
            sb.append('+');
          } else {
            sb.append("&nbsp;"); //$NON-NLS-1$
          }
        }

        sb.append('&');
        sb.append('#');
        sb.append('x');

        a = ((x >>> 4) & 0xf);
        b = ((x >>> 8) & 0xf);
        c = ((x >>> 12) & 0xf);
        if ((a != 0) || (b != 0) || (c != 0)) {
          if ((b != 0) || (c != 0)) {
            if (c != 0) {
              sb.append(CharEncodingComparison.HEX[c]);
            }
            sb.append(CharEncodingComparison.HEX[b]);
          }
          sb.append(CharEncodingComparison.HEX[a]);
        }
        sb.append(CharEncodingComparison.HEX[x & 0xf]);
        sb.append(';');

        if (add) {
          sb.append("&nbsp;"); //$NON-NLS-1$
        }
      }
    }
    return sb.toString();
  }

  /**
   * Compute the <a
   * href="http://en.wikipedia.org/wiki/Query_string#URL_encoding"
   * >URL-encoding</a> of the character {@code chr}, based on UTF-8
   *
   * @param chr
   *          the character
   * @return the url-encoding
   */
  private static final String __urlEncode(final int chr) {
    try {
      return URLEncoder.encode(String.valueOf((char) chr), "UTF-8"); //$NON-NLS-1$
    } catch (final Throwable t) {
      return null;
    }
  }

  /**
   * The main routine, which creates a HTML table and stores it under path
   * {@code args[0]} if at least one command line argument is provided,
   * otherwise it stores it under &quot;<code>/tmp/encodings.html</code>
   * &quot;.
   *
   * @param args
   *          ignored
   */
  public static final void main(final String[] args) {
    int ch, first, count;
    String part, s;

    try (final FileWriter fw = new FileWriter(
        ((args != null) && (args.length > 0)) ? args[0]
            : "/tmp/encodings.html")) { //$NON-NLS-1$
      try (final BufferedWriter bw = new BufferedWriter(fw)) {
        bw.write("<html><head><title>The Unicode Code Points from ");//$NON-NLS-1$
        CharEncodingComparison.__codePoint(CharEncodingComparison.START,
            bw);
        bw.write(" to ");//$NON-NLS-1$
        CharEncodingComparison.__codePoint(CharEncodingComparison.END, bw);
        bw.write(" and their Encodings</title></head><body>");//$NON-NLS-1$

        bw.write("<table border=\"1\" style=\"border-collapse:collapse;text-align:right;font-family:monospace\">"); //$NON-NLS-1$

        for (final boolean toggle : new boolean[] { true, false }) {

          if (toggle) {
            part = "thead";//$NON-NLS-1$
          } else {
            part = "tfoot ";//$NON-NLS-1$
          }

          bw.write('<');
          bw.write(part);
          bw.write(" style=\"text-align:center\">"); //$NON-NLS-1$
          if (toggle) {
            CharEncodingComparison.__head2(bw);
            CharEncodingComparison.__head1(bw);
          } else {
            CharEncodingComparison.__head1(bw);
            CharEncodingComparison.__head2(bw);
          }

          bw.write("</"); //$NON-NLS-1$"
          bw.write(part);
          bw.write('>');
        }

        bw.write("<tbody>"); //$NON-NLS-1$
        count = 0;
        for (ch = CharEncodingComparison.START; ch <= CharEncodingComparison.END; ch++) {
          bw.write("<tr"); //$NON-NLS-1$
          if ((count & 1) != 0) {
            bw.write(" style=\"background-color:lightgray\">");//$NON-NLS-1$
          } else {
            bw.write('>');
          }
          count++;

          first = ch;
          while (!(Character.isDefined(ch) && Character
              .isValidCodePoint(ch))) {
            if ((++ch) > CharEncodingComparison.END) {
              break;
            }
          }
          if (ch <= first) {

            bw.write("<th style=\"text-align:center\">"); //$NON-NLS-1$
            s = Integer.toHexString(ch);
            if (ch < CharEncodingComparison.FIXED_NAMES.length) {
              bw.write(CharEncodingComparison.FIXED_NAMES[ch]);
            } else {
              bw.write("&#x"); //$NON-NLS-1$
              bw.write(s);
              bw.write(';');
            }
            bw.write("</th><td"); //$NON-NLS-1$
            s = CharEncodingComparison.__normalizeNFKC(ch);
            if (s != null) {
              bw.write(" style=\"text-align:center\">");//$NON-NLS-1$
              bw.write(s);
              bw.write("</td>"); //$NON-NLS-1$
            } else {
              bw.write("/>"); //$NON-NLS-1$
            }
            bw.write("<td>"); //$NON-NLS-1$
            CharEncodingComparison.__codePoint(ch, bw);
            bw.write("</td><td>"); //$NON-NLS-1$
            bw.write(Integer.toString(ch));
            bw.write("</td><td"); //$NON-NLS-1$

            s = CharEncodingComparison.__urlEncode(ch);
            if (s != null) {
              bw.write('>');
              bw.write(s);
              bw.write("</td>"); //$NON-NLS-1$
            } else {
              bw.write("/>"); //$NON-NLS-1$
            }

            for (final String enc : CharEncodingComparison.ENCODINGS) {
              bw.write("<td>"); //$NON-NLS-1$
              bw.write(CharEncodingComparison.__encode(((char) ch), enc));
              bw.write("</td>"); //$NON-NLS-1$
            }

          } else {
            --ch;
            bw.write("<td style=\"text-align:center\" colspan=\""); //$NON-NLS-1$
            bw.write(Integer.toString(5 + //
                CharEncodingComparison.ENCODINGS.length));
            bw.write("\">invalid or undefined code point"); //$NON-NLS-1$
            if (ch != first) {
              bw.write("s: "); //$NON-NLS-1$
              CharEncodingComparison.__codePoint(first, bw);
              bw.write(" to  "); //$NON-NLS-1$
              CharEncodingComparison.__codePoint(ch, bw);
            } else {
              bw.write(": "); //$NON-NLS-1$
              CharEncodingComparison.__codePoint(first, bw);
            }
            bw.write("</td>"); //$NON-NLS-1$
          }
          bw.write("</tr>"); //$NON-NLS-1$
        }
        bw.write("</tbody></table>"); //$NON-NLS-1$
        bw.write("</body></html>");//$NON-NLS-1$
      }
    } catch (final Throwable tt) {
      tt.printStackTrace();
    }
  }

  /**
   * Write the first head
   *
   * @param bw
   *          the writer
   * @throws IOException
   *           if something fails
   */
  private static final void __head1(final Writer bw) throws IOException {
    int i;
    String s;

    bw.write("<tr>"); //$NON-NLS-1$
    bw.write("<th>Char</th>"); //$NON-NLS-1$
    bw.write("<th><a href=\"http://www.unicode.org/reports/tr15/\">NFKC</a></th>"); //$NON-NLS-1$
    bw.write("<th><a href=\"http://en.wikipedia.org/wiki/Unicode\">Code Point</a></th>"); //$NON-NLS-1$
    bw.write("<th>Decimal</th>"); //$NON-NLS-1$
    bw.write("<th><a href=\"http://en.wikipedia.org/wiki/Query_string#URL_encoding\">in URL</a></th>"); //$NON-NLS-1$

    for (i = 0; i < CharEncodingComparison.ENCODINGS.length; i++) {
      bw.write("<th>"); //$NON-NLS-1$
      s = CharEncodingComparison.ENCODINGS_LINKS[i];
      if (s != null) {
        bw.write("<a href=\"");//$NON-NLS-1$
        bw.write(s);
        bw.write("\">");//$NON-NLS-1$
      }
      bw.write(CharEncodingComparison.ENCODINGS[i]);
      if (s != null) {
        bw.write("</a>");//$NON-NLS-1$
      }
      bw.write("</th>"); //$NON-NLS-1$
    }
    bw.write("</tr>"); //$NON-NLS-1$
  }

  /**
   * Write the second head
   *
   * @param bw
   *          the writer
   * @throws IOException
   *           if something fails
   */
  private static final void __head2(final Writer bw) throws IOException {
    bw.write("<tr><th colspan=\"5\">Character Information</th>"); //$NON-NLS-1$
    bw.write("<th colspan=\""); //$NON-NLS-1$
    bw.write(Integer.toString(CharEncodingComparison.ENCODINGS.length));
    bw.write("\">Encoding: hexadecimal, bytes in same order as they appear in a file, ");//$NON-NLS-1$
    bw.write(CharEncodingComparison.MISSING);
    bw.write(" &equiv; cannot be represented</th></tr>");//$NON-NLS-1$);
  }
}
