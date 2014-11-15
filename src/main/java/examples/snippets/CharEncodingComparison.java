package examples.snippets;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * <p>
 * A small class comparing the encodings of different characters under
 * different schemes.
 * </p>
 */
public class CharEncodingComparison {

  /** the encodings to test */
  private static final String[] ENCODINGS = {//
  "UTF-8",//$NON-NLS-1$
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

  /** the encodings to test */
  private static final String[] ENCODINGS_LINKS = {//
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

  /** some standard names for characters with low code points */
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

  /** the character signifying missing encodings */
  private static final String MISSING = "&empty;";//$NON-NLS-1$

  /** the start point */
  private static final int START = 0;

  /** the end point */
  private static final int END = 0xffff;

  /**
   * Encode a given string
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
      } catch (final Throwable t) {
        return CharEncodingComparison.MISSING;
      }

      try (final ByteArrayInputStream bis = new ByteArrayInputStream(data)) {
        try (final InputStreamReader isr = new InputStreamReader(bis,
            encoding)) {
          if (isr.read() != chr) {
            return CharEncodingComparison.MISSING;
          }
        }
      } catch (final Throwable t) {
        return CharEncodingComparison.MISSING;
      }

      i = data.length;
      chx = new char[i << 1];
      for (j = 0; (--i) >= 0;) {
        b = data[i];
        chx[j++] = CharEncodingComparison.HEX[(b >>> 4) & 0xf];
        chx[j++] = CharEncodingComparison.HEX[b & 0xf];
      }
      return String.valueOf(chx);
    } catch (final Throwable tt) {
      tt.printStackTrace();
      return CharEncodingComparison.MISSING;
    }
  }

  /**
   * write a code point
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
   * The main routine
   * 
   * @param args
   *          ignored
   * @throws Throwable
   *           ignore
   */
  public static final void main(final String[] args) throws Throwable {
    int ch, first, count;
    String s;
    String[] enc;
    int i;

    enc = new String[CharEncodingComparison.ENCODINGS.length];

    try (final FileWriter fw = new FileWriter("/tmp/encodings.html")) { //$NON-NLS-1$
      try (final BufferedWriter bw = new BufferedWriter(fw)) {

        bw.write("<table border=\"1\" style=\"text-align:right;font-family:monospace\">"); //$NON-NLS-1$

        for (final String part : new String[] { "thead",//$NON-NLS-1$ 
            "tfoot " }) {//$NON-NLS-1$
          bw.write('<');
          bw.write(part);
          bw.write(" style=\"text-align:center\">"); //$NON-NLS-1$
          bw.write("<tr>"); //$NON-NLS-1$
          bw.write("<th>Char</th>"); //$NON-NLS-1$
          bw.write("<th><a href=\"http://en.wikipedia.org/wiki/Unicode\">Code Point</a></th>"); //$NON-NLS-1$
          bw.write("<th>Decimal</th>"); //$NON-NLS-1$
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
            bw.write("</th><td>"); //$NON-NLS-1$
            CharEncodingComparison.__codePoint(ch, bw);
            bw.write("</td><td>"); //$NON-NLS-1$
            bw.write(Integer.toString(ch));
            bw.write("</td>"); //$NON-NLS-1$

            for (i = CharEncodingComparison.ENCODINGS.length; (--i) >= 0;) {
              enc[i] = CharEncodingComparison.__encode(((char) ch),
                  CharEncodingComparison.ENCODINGS[i]);
            }
            for (i = 0; i < enc.length; i++) {
              s = enc[i];

              bw.write("<td>"); //$NON-NLS-1$
              if (s == CharEncodingComparison.MISSING) {
                bw.write(s);
              } else {
                if ((i > 0) && s.equals(enc[0])) {
                  bw.write("<em>");//$NON-NLS-1$
                }
                bw.write(s);
                if ((i > 0) && s.equals(enc[0])) {
                  bw.write("<em>");//$NON-NLS-1$
                }
              }
              bw.write("</td>"); //$NON-NLS-1$
            }

          } else {
            --ch;
            bw.write("<td style=\"text-align:center\" colspan=\""); //$NON-NLS-1$
            bw.write(Integer
                .toString(3 + CharEncodingComparison.ENCODINGS.length));
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
        bw.write("</tbody>"); //$NON-NLS-1$
        bw.write("</table>"); //$NON-NLS-1$
      }
    } catch (final Throwable tt) {
      tt.printStackTrace();
    }
  }

}
