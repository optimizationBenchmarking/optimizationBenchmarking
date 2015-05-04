package org.optimizationBenchmarking.utils.io.xml;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.encoding.TextEncoding;
import org.w3c.dom.ls.LSInput;
import org.xml.sax.InputSource;

/**
 * A simple implementation of the {@link org.w3c.dom.ls.LSInput}. This
 * class is designed so that it may create an input stream or reader on the
 * fly, maybe pointing to an internal resource.
 */
public class SimpleLSInput extends InputSource implements LSInput {

  /** the base uri */
  private String m_baseURI;

  /** the created reader */
  private Reader m_createdReader;

  /** the originally created input stream */
  private InputStream m_createdIs;

  /** the string data */
  private String m_data;

  /** has the encoding been set? */
  private boolean m_encodingSet;

  /** the certified text */
  private boolean m_cert;

  /**
   * create
   *
   * @param publicId
   *          the public id
   * @param systemId
   *          the system id
   * @param baseURI
   *          the base uri
   */
  public SimpleLSInput(final String publicId, final String systemId,
      final String baseURI) {
    super();
    if (publicId != null) {
      super.setPublicId(publicId);
    }
    if (systemId != null) {
      super.setSystemId(systemId);
    }
    this.m_baseURI = baseURI;
  }

  /**
   * Create the internal input stream
   *
   * @return the input stream
   */
  protected InputStream createInputStream() {
    return null;
  }

  /**
   * Create the internal reader
   *
   * @return the reader
   */
  protected Reader createReader() {
    InputStream is;

    is = this.getByteStream();
    if (is != null) {
      return new InputStreamReader(is);
    }
    return null;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public final Reader getCharacterStream() {
    Reader r;

    r = super.getCharacterStream();
    if (r == null) {
      if (this.m_createdReader == null) {
        r = this.m_createdReader = this.createReader();
        super.setCharacterStream(r);
      }
    }

    return r;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public final void setCharacterStream(final Reader characterStream) {
    Reader r;

    r = super.getCharacterStream();
    if ((r != null) && (r == this.m_createdReader)) {
      try {
        r.close();
      } catch (final Throwable t) {
        RethrowMode.AS_RUNTIME_EXCEPTION
            .rethrow(//
                "Error while closing old character stream of SimpleLSInput in order to set a new one.", //$NON-NLS-1$
                true, t);
      } finally {
        super.setCharacterStream(null);
      }
    }
    super.setCharacterStream(characterStream);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public final InputStream getByteStream() {
    InputStream is;

    is = super.getByteStream();
    if (is == null) {
      if (this.m_createdIs == null) {
        is = this.m_createdIs = this.createInputStream();
        super.setByteStream(is);
      }
    }
    return is;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public final void setByteStream(final InputStream byteStream) {
    InputStream is;

    is = super.getByteStream();
    if ((is != null) && (is == this.m_createdIs)) {
      try {
        is.close();
      } catch (final Throwable t) {
        RethrowMode.AS_RUNTIME_EXCEPTION
            .rethrow(//
                "Error while closing byte stream of SimpleLSInput in order to set a new one.", //$NON-NLS-1$
                true, t);
      } finally {
        super.setByteStream(null);
      }
    }
    super.setByteStream(byteStream);
  }

  /** {@inheritDoc} */
  @Override
  public final String getStringData() {
    return this.m_data;
  }

  /** {@inheritDoc} */
  @Override
  public final void setStringData(final String stringData) {
    this.m_data = stringData;
  }

  /** {@inheritDoc} */
  @Override
  public final String getBaseURI() {
    return this.m_baseURI;
  }

  /** {@inheritDoc} */
  @Override
  public final void setBaseURI(final String baseURI) {
    this.m_baseURI = baseURI;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public final String getEncoding() {
    StreamEncoding<?, ?> enc;
    String e;
    Reader r;

    e = super.getEncoding();
    if ((!(this.m_encodingSet)) && (e == null)) {
      r = super.getCharacterStream();
      if (r != null) {
        enc = StreamEncoding.getStreamEncoding(r);
        if ((enc != null) && (enc != StreamEncoding.TEXT)
            && (enc != StreamEncoding.BINARY)
            && (enc != StreamEncoding.UNKNOWN)
            && (enc instanceof TextEncoding)) {
          e = ((TextEncoding) enc).getJavaName();
          super.setEncoding(e);
        }

      }
    }
    return e;
  }

  /** {@inheritDoc} */
  @Override
  public final void setEncoding(final String encoding) {
    this.m_encodingSet = true;
    super.setEncoding(encoding);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean getCertifiedText() {
    return this.m_cert;
  }

  /** {@inheritDoc} */
  @Override
  public final void setCertifiedText(final boolean certifiedText) {
    this.m_cert = certifiedText;
  }

}
