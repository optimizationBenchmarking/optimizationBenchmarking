package org.optimizationBenchmarking.utils.io.encoding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;

import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A text encoding based on some fixed character set.
 */
public final class TextEncoding extends
    StreamEncoding<BufferedReader, BufferedWriter> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the name of the encoding in the java environment */
  private transient final String m_javaName;

  /**
   * create
   *
   * @param names
   *          the encodings names
   * @param autoRegister
   *          should the encoding be automatically registered?
   */
  private TextEncoding(final String[] names, final boolean autoRegister) {
    super(names[0], false);
    this.m_javaName = ((names.length > 1) ? names[1] : names[0]);
    if (autoRegister) {
      this._register();
    }
  }

  /**
   * register with more names
   *
   * @param moreNames
   *          the names
   */
  @SuppressWarnings("unused")
  final void _register(final String... moreNames) {
    final ArrayList<String> names;
    final String sn, snlc, jn, jnlc;
    final Charset cs;
    String lc;

    names = new ArrayList<>();

    sn = this.name();
    snlc = TextUtils.toLowerCase(sn);

    names.add(snlc);
    jn = this.getJavaName();
    if (jn != null) {
      if ((jn != sn) && (!(sn.equals(jn)))) {
        jnlc = TextUtils.toLowerCase(jn);
        if (!(snlc.equals(jnlc))) {
          names.add(jnlc);
        }
      }

      try {
        cs = Charset.forName(jn);

        if (cs != null) {
          lc = cs.displayName();
          if (lc != null) {
            lc = TextUtils.toLowerCase(lc);

            if (!(names.contains(lc) || lc
                .equalsIgnoreCase(StreamEncoding._DEFAULT))) {
              names.add(lc);
            }
          }

          lc = cs.displayName(Locale.US);
          if (lc != null) {
            lc = TextUtils.toLowerCase(lc);

            if (!(names.contains(lc) || lc
                .equalsIgnoreCase(StreamEncoding._DEFAULT))) {
              names.add(lc);
            }
          }

          for (String s : cs.aliases()) {
            if (s != null) {
              s = TextUtils.toLowerCase(s);
              if (!(names.contains(s) || s
                  .equalsIgnoreCase(StreamEncoding._DEFAULT))) {
                names.add(s);
              }
            }
          }
        }

      } catch (final Throwable tt) {
        //
      }
    }

    for (final String s : moreNames) {
      lc = TextUtils.prepare(s);
      if (lc != null) {
        lc = TextUtils.toLowerCase(lc);
        if (!(names.contains(lc))) {
          names.add(lc);
        }
      }
    }

    this.register(names.toArray(new String[names.size()]));
  }

  /**
   * create
   *
   * @param name
   *          the encodings name
   * @param autoRegister
   *          should the encoding be automatically registered?
   */
  TextEncoding(final String name, final boolean autoRegister) {
    this(TextEncoding.resolveName(name), autoRegister);
  }

  /**
   * Get the name under which this encoding is known in the Java world
   *
   * @return the name under which this encoding is known in the Java world
   */
  public final String getJavaName() {
    return this.m_javaName;
  }

  /**
   * Try to resolve the name of an encoding
   *
   * @param name
   *          the name
   * @return the resolved name
   */
  @SuppressWarnings("unused")
  private static final String[] resolveName(final String name) {
    String cs, n;

    n = TextUtils.prepare(name);
    try {
      cs = TextUtils.prepare(Charset.forName(n).name());
    } catch (final Throwable t) {
      cs = null;
    }

    if ((cs != null) && (!(cs.equals(name)))) {
      return new String[] { n, cs };
    }
    return new String[] { n };
  }

  /** {@inheritDoc} */
  @Override
  public final BufferedReader wrapInputStream(final InputStream input)
      throws IOException {
    return new _EncodedBufferedReader(new InputStreamReader(input,
        this.m_javaName), this);
  }

  /** {@inheritDoc} */
  @Override
  public final BufferedWriter wrapOutputStream(final OutputStream output)
      throws IOException {
    return new _EncodedBufferedWriter(new OutputStreamWriter(output,
        this.m_javaName), this);
  }

  /** {@inheritDoc} */
  @Override
  public final BufferedReader wrapReader(final Reader input)
      throws IOException {
    return _UnknownTextEncoding._wrapReader(input);
  }

  /** {@inheritDoc} */
  @Override
  public final BufferedWriter wrapWriter(final Writer output)
      throws IOException {
    return _UnknownTextEncoding._wrapWriter(output);
  }

  /** {@inheritDoc} */
  @Override
  public final Class<BufferedWriter> getOutputClass() {
    return BufferedWriter.class;
  }

  /** {@inheritDoc} */
  @Override
  public final Class<BufferedReader> getInputClass() {
    return BufferedReader.class;
  }

}
