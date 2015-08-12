package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;

/** A parser for a stream encodings. */
public class StreamEncodingParser extends Parser<StreamEncoding<?, ?>> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the stream encodings parser */
  public static final StreamEncodingParser INSTANCE = new StreamEncodingParser();

  /** create the parser */
  private StreamEncodingParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public final Class<StreamEncoding<?, ?>> getOutputClass() {
    return (Class) (StreamEncoding.class);
  }

  /** {@inheritDoc} */
  @Override
  public final StreamEncoding<?, ?> parseString(final String string) {
    final StreamEncoding<?, ?> f;

    f = StreamEncoding.parseString(string);
    this.validate(f);
    return f;
  }

  /** {@inheritDoc} */
  @Override
  public void validate(final StreamEncoding<?, ?> instance)
      throws IllegalArgumentException {
    if (instance == null) {
      throw new IllegalArgumentException(
          "Stream encoding must not be null."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final StreamEncoding<?, ?> parseObject(final Object o) {
    final StreamEncoding<?, ?> f;

    f = StreamEncoding.getStreamEncoding(o);
    this.validate(f);
    return f;
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return StreamEncodingParser.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return StreamEncodingParser.INSTANCE;
  }
}
