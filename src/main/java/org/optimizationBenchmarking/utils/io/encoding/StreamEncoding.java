package org.optimizationBenchmarking.utils.io.encoding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;

import org.optimizationBenchmarking.utils.NamedObject;
import org.optimizationBenchmarking.utils.collections.maps.StringMapCI;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * The base class for encodings.
 *
 * @param <IST>
 *          the input stream type
 * @param <OST>
 *          the output stream type
 */
public abstract class StreamEncoding<IST extends Closeable, OST extends Closeable>
    extends NamedObject {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the default string */
  static final String _DEFAULT = "default".intern(); //$NON-NLS-1$

  /** the encodings */
  private static final StringMapCI<StreamEncoding<?, ?>> ENCODINGS = new StringMapCI<>();

  /** the unknown encoding */
  public static final StreamEncoding<Closeable, Closeable> UNKNOWN = //
  new _UnknownEncoding();

  /** the binary encoding */
  public static final StreamEncoding<InputStream, OutputStream> BINARY = //
  new _UnknownBinaryEncoding();

  /** the text encoding */
  public static final StreamEncoding<BufferedReader, BufferedWriter> TEXT = //
  new _UnknownTextEncoding();

  /** the ascii encoding */
  public static final TextEncoding ASCII = new TextEncoding(//
      "ASCII", false); //$NON-NLS-1$

  /** the windows codepage 1252 encoding */
  public static final TextEncoding WINDOWS_1252 = new TextEncoding(//
      "Cp1252", false); //$NON-NLS-1$

  /** the big 5 encoding */
  public static final TextEncoding BIG_5 = new TextEncoding(//
      "Big5", false); //$NON-NLS-1$

  /** the gbk encoding */
  public static final TextEncoding GBK = new TextEncoding(//
      "GBK", true); //$NON-NLS-1$

  /** the GB2312 encoding */
  public static final TextEncoding GB2312 = new TextEncoding(//
      "GB2312", false); //$NON-NLS-1$

  /** the utf-8 encoding */
  public static final TextEncoding UTF_8 = new TextEncoding(//
      "UTF-8", false); //$NON-NLS-1$

  /** the utf-16 big endian encoding */
  public static final TextEncoding UTF_16_BE = new TextEncoding(//
      "UTF-16BE", false); //$NON-NLS-1$

  /** the utf-16 little endian encoding */
  public static final TextEncoding UTF_16_LE = new TextEncoding(//
      "UTF-16LE", false); //$NON-NLS-1$

  /** the ISO-8859-1 encoding */
  public static final TextEncoding ISO_8859_1 = new TextEncoding(//
      "ISO-8859-1", false); //$NON-NLS-1$

  static {
    synchronized (StreamEncoding.ENCODINGS) {

      StreamEncoding.ASCII._register("us-ascii", //$NON-NLS-1$
          "us ascii", //$NON-NLS-1$
          "us_ascii"); //$NON-NLS-1$

      StreamEncoding.BIG_5._register("big-5"); //$NON-NLS-1$

      StreamEncoding.GB2312._register("euc_cn", //$NON-NLS-1$
          "euc cn", //$NON-NLS-1$
          "euccn", //$NON-NLS-1$
          "euc-cn"); //$NON-NLS-1$

      StreamEncoding.UTF_8._register("utf8", //$NON-NLS-1$
          "unicode", //$NON-NLS-1$
          StreamEncoding._DEFAULT, "iso/iec 10646-1:2000", //$NON-NLS-1$
          "iso/iec 10646-1:1993", //$NON-NLS-1$
          "iso/iec 10646-1", //$NON-NLS-1$
          "iso 10646-1"); //$NON-NLS-1$

      StreamEncoding.UTF_16_BE._register("unicodebig"); //$NON-NLS-1$

      StreamEncoding.UTF_16_LE._register("unicodelittle"); //$NON-NLS-1$

      StreamEncoding.ISO_8859_1._register("iso 8859-1", //$NON-NLS-1$
          "8859-1", //$NON-NLS-1$
          "iso_8859-1:1987", //$NON-NLS-1$
          "iso_8859-1", //$NON-NLS-1$
          "iso-tr-100", //$NON-NLS-1$
          "csisolatin1", //$NON-NLS-1$
          "latin1", //$NON-NLS-1$
          "l1", //$NON-NLS-1$
          "ibm819", //$NON-NLS-1$
          "cp819", //$NON-NLS-1$
          "iso/iec 8859-1"); //$NON-NLS-1$

      StreamEncoding.WINDOWS_1252._register("cp1252", //$NON-NLS-1$
          "windows1252", //$NON-NLS-1$
          "windows-1252"); //$NON-NLS-1$
    }
  }

  /**
   * create the encoding
   *
   * @param standardName
   *          the standard name
   * @param autoRegister
   *          should the encoding be automatically registered under its
   *          standard name?
   */
  protected StreamEncoding(final String standardName,
      final boolean autoRegister) {
    super(standardName);
    if (autoRegister) {
      this.register(this.name());
    }
  }

  /**
   * register this encoding under a set of names
   *
   * @param names
   *          the names
   */
  protected final void register(final String... names) {
    synchronized (StreamEncoding.ENCODINGS) {
      for (final String s : names) {
        if (StreamEncoding.ENCODINGS.containsKey(s)) {
          throw new IllegalStateException(//
              "There is already an encoding under name '" + s + //$NON-NLS-1$
                  "'."); //$NON-NLS-1$
        }
      }

      for (final String s : names) {
        StreamEncoding.ENCODINGS.put(s, this);
      }
    }
  }

  /**
   * Wrap an input stream into a stream representing this encoding
   *
   * @param input
   *          the input stream to wrap
   * @return the wrapped stream
   * @throws IOException
   *           if some I/O fails
   */
  public IST wrapInputStream(final InputStream input) throws IOException {
    return this.wrapReader(new InputStreamReader(input));
  }

  /**
   * Wrap an output stream into a stream representing this encoding
   *
   * @param output
   *          the output stream to wrap
   * @return the wrapped stream
   * @throws IOException
   *           if some I/O fails
   */
  public OST wrapOutputStream(final OutputStream output)
      throws IOException {
    return this.wrapWriter(new OutputStreamWriter(output));
  }

  /**
   * Wrap a reader into a stream representing this encoding
   *
   * @param input
   *          the reader to wrap
   * @return the wrapped stream
   * @throws IOException
   *           if some I/O fails
   */
  public abstract IST wrapReader(final Reader input) throws IOException;

  /**
   * Wrap a writer into a stream representing this encoding
   *
   * @param output
   *          the writer to wrap
   * @return the wrapped stream
   * @throws IOException
   *           if some I/O fails
   */
  public abstract OST wrapWriter(final Writer output) throws IOException;

  /**
   * Get the class of the output wrappers
   *
   * @return the class of the output wrappers
   */
  public abstract Class<OST> getOutputClass();

  /**
   * Get the class of the input wrappers
   *
   * @return the class of the input wrappers
   */
  public abstract Class<IST> getInputClass();

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    final StreamEncoding<?, ?> e;
    synchronized (StreamEncoding.ENCODINGS) {
      e = StreamEncoding.ENCODINGS.get(this.name());
    }
    if (e != null) {
      return e;
    }
    return this;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    final StreamEncoding<?, ?> e;
    synchronized (StreamEncoding.ENCODINGS) {
      e = StreamEncoding.ENCODINGS.get(this.name());
      if (e == null) {
        StreamEncoding.ENCODINGS.put(this.name(), this);
      }
    }
    if (e != null) {
      return e;
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof StreamEncoding) {
      return this.name().equalsIgnoreCase(
          ((StreamEncoding<?, ?>) o).name());
    }
    return false;
  }

  /**
   * Try to get the encoding of a given object
   *
   * @param o
   *          the object
   * @return the encoding, or {@link #UNKNOWN} if none could be found
   */
  public static final StreamEncoding<?, ?> getStreamEncoding(final Object o) {
    StreamEncoding<?, ?> te;

    if (o == null) {
      return StreamEncoding.UNKNOWN;
    }
    if (o instanceof IStreamEncoded) {
      return ((IStreamEncoded) o).getStreamEncoding();
    }

    if (o instanceof StreamEncoding) {
      return ((StreamEncoding<?, ?>) o);
    }

    if (o instanceof Writer) {
      te = StreamEncoding.__getWriterEncoding(o);
      if ((te != null) && (te != StreamEncoding.UNKNOWN)) {
        return te;
      }
      return StreamEncoding.TEXT;
    }

    if (o instanceof Reader) {
      te = StreamEncoding.__getReaderEncoding(o);
      if ((te != null) && (te != StreamEncoding.UNKNOWN)) {
        return te;
      }
      return StreamEncoding.TEXT;
    }

    if (o instanceof CharSequence) {
      return StreamEncoding.TEXT;
    }

    if (o instanceof OutputStream) {
      if (o instanceof PrintStream) {
        te = StreamEncoding.__getPrintStreamEncoding(o);
        if ((te != null) && (te != StreamEncoding.UNKNOWN)) {
          return te;
        }
        return StreamEncoding.TEXT;
      }
      return StreamEncoding.BINARY;
    }
    if (o instanceof InputStream) {
      return StreamEncoding.BINARY;
    }

    if ((o instanceof Appendable) || (o instanceof CharBuffer)) {
      return StreamEncoding.TEXT;
    }

    return StreamEncoding.UNKNOWN;
  }

  /**
   * Obtain a text encoding identified by a given string
   *
   * @param s
   *          the string
   * @return the corresponding encoding
   */
  public static final StreamEncoding<?, ?> parseString(final String s) {
    String t;
    Charset c;
    StreamEncoding<?, ?> e;

    t = TextUtils.prepare(s);
    if (t == null) {
      return StreamEncoding.UNKNOWN;
    }

    synchronized (StreamEncoding.ENCODINGS) {
      e = StreamEncoding.ENCODINGS.get(t);
    }

    if (e != null) {
      return e;
    }

    try {
      c = Charset.forName(s);
    } catch (UnsupportedCharsetException | IllegalCharsetNameException exp) {
      c = null;
    }

    if (c != null) {
      t = c.name();
      synchronized (StreamEncoding.ENCODINGS) {
        e = StreamEncoding.ENCODINGS.get(t);
        if (e == null) {
          e = new TextEncoding(c.name(), true);
        }
      }
    }

    if (e != null) {
      return e;
    }
    return StreamEncoding.UNKNOWN;
  }

  /**
   * Extract an encoding from a print stream
   *
   * @param wr
   *          The print stream to get the encoding of.
   * @return The historical name of this encoding, or possibly
   *         <code>null</code> if the stream has been closed
   */
  private static final StreamEncoding<?, ?> __getPrintStreamEncoding(
      final Object wr) {
    Field f;
    Class<?> c;
    StreamEncoding<?, ?> e;
    Object x;

    c = wr.getClass();
    if (c == null) {
      return null;
    }

    try {

      f = c.getField("charOut"); //$NON-NLS-1$
      if (f != null) {
        x = f.get(wr);
        if (x != wr) {
          e = StreamEncoding.getStreamEncoding(x);
          if ((e != null) && (e != StreamEncoding.UNKNOWN)
              && (e != StreamEncoding.TEXT)) {
            return e;
          }
        }
      }
    } catch (final Throwable t) {//
    }

    return null;

  }

  /**
   * Extract an encoding from a writer
   *
   * @param wr
   *          The writer to get the encoding of.
   * @return The historical name of this encoding, or possibly
   *         <code>null</code> if the stream has been closed
   */
  private static final StreamEncoding<?, ?> __getWriterEncoding(
      final Object wr) {
    Field f;
    Class<?> c;
    StreamEncoding<?, ?> e;
    Object x;

    if (wr instanceof OutputStreamWriter) {
      StreamEncoding.parseString(((OutputStreamWriter) wr).getEncoding());
    }

    c = wr.getClass();
    if (c == null) {
      return null;
    }

    try {

      f = c.getField("out"); //$NON-NLS-1$
      if (f != null) {
        x = f.get(wr);
        if (x != wr) {
          e = StreamEncoding.getStreamEncoding(x);
          if ((e != null) && (e != StreamEncoding.UNKNOWN)
              && (e != StreamEncoding.TEXT)) {
            return e;
          }
        }
      }
    } catch (final Throwable t) {//
    }

    try {
      f = c.getField("lock"); //$NON-NLS-1$
      if (f != null) {
        x = f.get(wr);
        if (x != wr) {
          e = StreamEncoding.getStreamEncoding(x);
          if ((e != null) && (e != StreamEncoding.UNKNOWN)
              && (e != StreamEncoding.TEXT)) {
            return e;
          }
        }
      }

    } catch (final Throwable t) {//
    }

    return null;
  }

  /**
   * Extract an encoding from a reader
   *
   * @param r
   *          The reader to get the encoding of.
   * @return The historical name of this encoding, or possibly
   *         <code>null</code> if the stream has been closed
   */
  private static final StreamEncoding<?, ?> __getReaderEncoding(
      final Object r) {
    Field f;
    Class<?> c;
    StreamEncoding<?, ?> e;
    Object x;

    if (r instanceof InputStreamReader) {
      StreamEncoding.parseString(((InputStreamReader) r).getEncoding());
    }

    c = r.getClass();
    if (c == null) {
      return null;
    }

    try {

      f = c.getField("out"); //$NON-NLS-1$
      if (f != null) {
        x = f.get(r);
        if (x != r) {
          e = StreamEncoding.getStreamEncoding(x);
          if ((e != null) && (e != StreamEncoding.UNKNOWN)
              && (e != StreamEncoding.TEXT)) {
            return e;
          }
        }
      }
    } catch (final Throwable t) {//
    }

    try {
      f = c.getField("lock"); //$NON-NLS-1$
      if (f != null) {
        x = f.get(r);
        if (x != r) {
          e = StreamEncoding.getStreamEncoding(x);
          if ((e != null) && (e != StreamEncoding.UNKNOWN)
              && (e != StreamEncoding.TEXT)) {
            return e;
          }
        }
      }

    } catch (final Throwable t) {//
    }
    return null;
  }
}
