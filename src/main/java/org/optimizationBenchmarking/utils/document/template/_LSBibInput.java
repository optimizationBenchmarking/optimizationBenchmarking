package org.optimizationBenchmarking.utils.document.template;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.bibliography.io.BibliographyXMLConstants;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.w3c.dom.ls.LSInput;

/** the local ls input implementation */
final class _LSBibInput implements LSInput {

  /** the public id */
  private String m_publicId;

  /** the system id */
  private String m_systemId;

  /** the base uri */
  private String m_baseURI;

  /** the reader */
  private Reader m_reader;

  /** the input stream */
  private InputStream m_is;
  /** the string data */
  private String m_data;
  /** the encoding */
  private String m_encoding;
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
  _LSBibInput(final String publicId, final String systemId,
      final String baseURI) {
    super();
    this.m_publicId = publicId;
    this.m_systemId = systemId;
    this.m_baseURI = baseURI;
    this.m_encoding = StreamEncoding.UTF_8.getJavaName();
  }

  /**
   * open the input stream
   * 
   * @return the input
   */
  private static final InputStream __is() {
    try {
      return BibliographyXMLConstants.class
          .getResourceAsStream(BibliographyXMLConstants.SCHEMA);
    } catch (final Throwable tt) {
      try {
        return BibliographyXMLConstants.NAMESPACE_URI.toURL().openStream();
      } catch (final Throwable ttt) {
        ErrorUtils.throwAsRuntimeException(ttt);
        return null;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final Reader getCharacterStream() {

    if (this.m_reader == null) {
      this.m_reader = new InputStreamReader(_LSBibInput.__is());
    }

    return this.m_reader;
  }

  /** {@inheritDoc} */
  @Override
  public final void setCharacterStream(final Reader characterStream) {
    if (this.m_reader != null) {
      try {
        this.m_reader.close();
      } catch (final Throwable t) {/* */
      }
    }
    this.m_reader = characterStream;
  }

  /** {@inheritDoc} */
  @Override
  public final InputStream getByteStream() {
    if (this.m_is == null) {
      this.m_is = _LSBibInput.__is();
    }
    return this.m_is;
  }

  /** {@inheritDoc} */
  @Override
  public final void setByteStream(final InputStream byteStream) {
    if (this.m_is != null) {
      try {
        this.m_is.close();
      } catch (final Throwable t) {/* */
      }
    }
    this.m_is = byteStream;
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
  public final String getSystemId() {
    return this.m_systemId;
  }

  /** {@inheritDoc} */
  @Override
  public final void setSystemId(final String systemId) {
    this.m_systemId = systemId;
  }

  /** {@inheritDoc} */
  @Override
  public final String getPublicId() {
    return this.m_publicId;
  }

  /** {@inheritDoc} */
  @Override
  public final void setPublicId(final String publicId) {
    this.m_publicId = publicId;
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
  @Override
  public final String getEncoding() {
    return this.m_encoding;
  }

  /** {@inheritDoc} */
  @Override
  public final void setEncoding(final String encoding) {
    this.m_encoding = encoding;
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
