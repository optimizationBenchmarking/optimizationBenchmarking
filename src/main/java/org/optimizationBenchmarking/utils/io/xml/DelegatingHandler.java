package org.optimizationBenchmarking.utils.io.xml;

import java.io.IOException;

import org.optimizationBenchmarking.utils.text.TextUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A content handler which delegates its calls.
 */
public class DelegatingHandler extends DefaultHandler {

  /** the finite state machine */
  final _HandlerFSM m_fsm;

  /** the delegating handler */
  DelegatingHandler m_delegate;

  /**
   * create
   *
   * @param owner
   *          the owner
   */
  protected DelegatingHandler(final DelegatingHandler owner) {
    super();

    this.m_fsm = new _HandlerFSM(((owner != null) ? owner.m_fsm : null),
        this);
    this.m_fsm._open();
  }

  /**
   * A new delegate has begun its work
   *
   * @param delegate
   *          the delegate
   */
  protected void onStartDelegate(final DelegatingHandler delegate) {//
  }

  /**
   * A delegate has finished its work
   *
   * @param delegate
   *          the delegate
   */
  protected void onEndDelegate(final DelegatingHandler delegate) {//
  }

  /**
   * Close this delegating handler. It will not be possible to attach any
   * more child handlers.
   */
  protected void close() {
    this.m_fsm._close();
  }

  /**
   * delegate resolve entity
   *
   * @param publicId
   *          see {@link #resolveEntity(String, String)}
   * @param systemId
   *          see {@link #resolveEntity(String, String)}
   * @return see {@link #resolveEntity(String, String)}
   * @throws IOException
   *           see {@link #resolveEntity(String, String)}
   * @throws SAXException
   *           see {@link #resolveEntity(String, String)}
   */
  protected InputSource delegateResolveEntity(final String publicId,
      final String systemId) throws IOException, SAXException {
    final DelegatingHandler del;
    if ((del = this.m_delegate) != null) {
      return del.resolveEntity(publicId, systemId);
    }
    return super.resolveEntity(publicId, systemId);
  }

  /**
   * do resolve entity
   *
   * @param publicId
   *          see {@link #resolveEntity(String, String)}
   * @param systemId
   *          see {@link #resolveEntity(String, String)}
   * @return see {@link #resolveEntity(String, String)}
   * @throws IOException
   *           see {@link #resolveEntity(String, String)}
   * @throws SAXException
   *           see {@link #resolveEntity(String, String)}
   */
  protected InputSource doResolveEntity(final String publicId,
      final String systemId) throws IOException, SAXException {
    return super.resolveEntity(publicId, systemId);
  }

  /** {@inheritDoc} */
  @Override
  public final InputSource resolveEntity(final String publicId,
      final String systemId) throws IOException, SAXException {
    if (this.m_delegate != null) {
      return this.delegateResolveEntity(publicId, systemId);
    }
    return this.doResolveEntity(publicId, systemId);
  }

  /**
   * delegate notation declaration
   *
   * @param name
   *          see {@link #notationDecl(String, String, String)}
   * @param publicId
   *          see {@link #notationDecl(String, String, String)}
   * @param systemId
   *          see {@link #notationDecl(String, String, String)}
   * @throws SAXException
   *           see {@link #notationDecl(String, String, String)}
   */
  protected void delegateNotationDecl(final String name,
      final String publicId, final String systemId) throws SAXException {
    final DelegatingHandler del;
    if ((del = this.m_delegate) != null) {
      del.notationDecl(name, publicId, systemId);
    }
  }

  /**
   * do notation declaration
   *
   * @param name
   *          see {@link #notationDecl(String, String, String)}
   * @param publicId
   *          see {@link #notationDecl(String, String, String)}
   * @param systemId
   *          see {@link #notationDecl(String, String, String)}
   * @throws SAXException
   *           see {@link #notationDecl(String, String, String)}
   */
  protected void doNotationDecl(final String name, final String publicId,
      final String systemId) throws SAXException {
    //
  }

  /** {@inheritDoc} */
  @Override
  public final void notationDecl(final String name, final String publicId,
      final String systemId) throws SAXException {
    if (this.m_delegate != null) {
      this.delegateNotationDecl(name, publicId, systemId);
    } else {
      this.doNotationDecl(name, publicId, systemId);
    }
  }

  /**
   * delegate an unparsed entity declaration
   *
   * @param name
   *          see
   *          {@link #unparsedEntityDecl(String, String, String, String)}
   * @param publicId
   *          see
   *          {@link #unparsedEntityDecl(String, String, String, String)}
   * @param systemId
   *          see
   *          {@link #unparsedEntityDecl(String, String, String, String)}
   * @param notationName
   *          see
   *          {@link #unparsedEntityDecl(String, String, String, String)}
   * @throws SAXException
   *           see
   *           {@link #unparsedEntityDecl(String, String, String, String)}
   */
  protected void delegateUnparsedEntityDecl(final String name,
      final String publicId, final String systemId,
      final String notationName) throws SAXException {
    final DelegatingHandler del;
    if ((del = this.m_delegate) != null) {
      del.unparsedEntityDecl(name, publicId, systemId, notationName);
    }
  }

  /**
   * do an unparsed entity declaration
   *
   * @param name
   *          see
   *          {@link #unparsedEntityDecl(String, String, String, String)}
   * @param publicId
   *          see
   *          {@link #unparsedEntityDecl(String, String, String, String)}
   * @param systemId
   *          see
   *          {@link #unparsedEntityDecl(String, String, String, String)}
   * @param notationName
   *          see
   *          {@link #unparsedEntityDecl(String, String, String, String)}
   * @throws SAXException
   *           see
   *           {@link #unparsedEntityDecl(String, String, String, String)}
   */
  protected void doUnparsedEntityDecl(final String name,
      final String publicId, final String systemId,
      final String notationName) throws SAXException {
    super.unparsedEntityDecl(name, publicId, systemId, notationName);
  }

  /** {@inheritDoc} */
  @Override
  public final void unparsedEntityDecl(final String name,
      final String publicId, final String systemId,
      final String notationName) throws SAXException {
    if (this.m_delegate != null) {
      this.delegateUnparsedEntityDecl(name, publicId, systemId,
          notationName);
    } else {
      this.doUnparsedEntityDecl(name, publicId, systemId, notationName);
    }
  }

  /**
   * delegate set document locator
   *
   * @param locator
   *          see {@link #setDocumentLocator(Locator)}
   */
  protected void delegateSetDocumentLocator(final Locator locator) {
    final DelegatingHandler del;
    if ((del = this.m_delegate) != null) {
      del.setDocumentLocator(locator);
    }
  }

  /**
   * do set document locator
   *
   * @param locator
   *          see {@link #setDocumentLocator(Locator)}
   */
  protected void doSetDocumentLocator(final Locator locator) {
    super.setDocumentLocator(locator);
  }

  /** {@inheritDoc} */
  @Override
  public final void setDocumentLocator(final Locator locator) {
    if (this.m_delegate != null) {
      this.delegateSetDocumentLocator(locator);
    } else {
      this.doSetDocumentLocator(locator);
    }
  }

  /**
   * delegate start document
   *
   * @throws SAXException
   *           see {@link #startDocument()}
   */
  protected void delegateStartDocument() throws SAXException {
    final DelegatingHandler del;

    if ((del = this.m_delegate) != null) {
      del.startDocument();
    }
  }

  /**
   * do start document
   *
   * @throws SAXException
   *           see {@link #startDocument()}
   */
  protected void doStartDocument() throws SAXException {
    super.startDocument();
  }

  /** {@inheritDoc} */
  @Override
  public final void startDocument() throws SAXException {
    if (this.m_delegate != null) {
      this.delegateStartDocument();
    } else {
      this.doStartDocument();
    }
  }

  /**
   * delegate end document
   *
   * @throws SAXException
   *           see {@link #endDocument()}
   */
  protected void delegateEndDocument() throws SAXException {
    final DelegatingHandler del;

    if ((del = this.m_delegate) != null) {
      del.endDocument();
    }
  }

  /**
   * do end document
   *
   * @throws SAXException
   *           see {@link #endDocument()}
   */
  protected void doEndDocument() throws SAXException {
    super.endDocument();
  }

  /** {@inheritDoc} */
  @Override
  public final void endDocument() throws SAXException {
    if (this.m_delegate != null) {
      this.delegateEndDocument();
    } else {
      this.doEndDocument();
    }
  }

  /**
   * delegate start prefix mapping
   *
   * @param prefix
   *          see {@link #startPrefixMapping(String, String)}
   * @param uri
   *          see {@link #startPrefixMapping(String, String)}
   * @throws SAXException
   *           see {@link #startPrefixMapping(String, String)}
   */
  protected void delegateStartPrefixMapping(final String prefix,
      final String uri) throws SAXException {
    final DelegatingHandler del;
    if ((del = this.m_delegate) != null) {
      del.startPrefixMapping(prefix, uri);
    }
  }

  /**
   * do start prefix mapping
   *
   * @param prefix
   *          see {@link #startPrefixMapping(String, String)}
   * @param uri
   *          see {@link #startPrefixMapping(String, String)}
   * @throws SAXException
   *           see {@link #startPrefixMapping(String, String)}
   */
  protected void doStartPrefixMapping(final String prefix, final String uri)
      throws SAXException {
    super.startPrefixMapping(prefix, uri);
  }

  /** {@inheritDoc} */
  @Override
  public final void startPrefixMapping(final String prefix,
      final String uri) throws SAXException {
    if (this.m_delegate != null) {
      this.delegateStartPrefixMapping(prefix, uri);
    } else {
      this.doStartPrefixMapping(prefix, uri);
    }
  }

  /**
   * delegate end prefix mapping
   *
   * @param prefix
   *          see {@link #endPrefixMapping(String)}
   * @throws SAXException
   *           see {@link #endPrefixMapping(String)}
   */
  protected void delegateEndPrefixMapping(final String prefix)
      throws SAXException {
    final DelegatingHandler del;
    if ((del = this.m_delegate) != null) {
      del.endPrefixMapping(prefix);
    }
  }

  /**
   * do end prefix mapping
   *
   * @param prefix
   *          see {@link #endPrefixMapping(String)}
   * @throws SAXException
   *           see {@link #endPrefixMapping(String)}
   */
  protected void doEndPrefixMapping(final String prefix)
      throws SAXException {
    super.endPrefixMapping(prefix);
  }

  /** {@inheritDoc} */
  @Override
  public final void endPrefixMapping(final String prefix)
      throws SAXException {
    if (this.m_delegate != null) {
      this.delegateEndPrefixMapping(prefix);
    } else {
      this.doEndPrefixMapping(prefix);
    }
  }

  /**
   * delegate start element
   *
   * @param uri
   *          see {@link #startElement(String, String, String, Attributes)}
   * @param localName
   *          see {@link #startElement(String, String, String, Attributes)}
   * @param qName
   *          see {@link #startElement(String, String, String, Attributes)}
   * @param attributes
   *          see {@link #startElement(String, String, String, Attributes)}
   * @throws SAXException
   *           see
   *           {@link #startElement(String, String, String, Attributes)}
   */
  protected void delegateStartElement(final String uri,
      final String localName, final String qName,
      final Attributes attributes) throws SAXException {
    final DelegatingHandler del;
    if ((del = this.m_delegate) != null) {
      del.startElement(uri, localName, qName, attributes);
    }
  }

  /**
   * do start element
   *
   * @param uri
   *          see {@link #startElement(String, String, String, Attributes)}
   * @param localName
   *          see {@link #startElement(String, String, String, Attributes)}
   * @param qName
   *          see {@link #startElement(String, String, String, Attributes)}
   * @param attributes
   *          see {@link #startElement(String, String, String, Attributes)}
   * @throws SAXException
   *           see
   *           {@link #startElement(String, String, String, Attributes)}
   */
  protected void doStartElement(final String uri, final String localName,
      final String qName, final Attributes attributes) throws SAXException {
    super.startElement(uri, localName, qName, attributes);
  }

  /** {@inheritDoc} */
  @Override
  public final void startElement(final String uri, final String localName,
      final String qName, final Attributes attributes) throws SAXException {
    if (this.m_delegate != null) {
      this.delegateStartElement(uri, localName, qName, attributes);
    } else {
      this.doStartElement(uri, localName, qName, attributes);
    }
  }

  /**
   * delegate end element
   *
   * @param uri
   *          see {@link #endElement(String, String, String)}
   * @param localName
   *          see {@link #endElement(String, String, String)}
   * @param qName
   *          see {@link #endElement(String, String, String)}
   * @throws SAXException
   *           see {@link #endElement(String, String, String)}
   */
  protected void delegateEndElement(final String uri,
      final String localName, final String qName) throws SAXException {
    final DelegatingHandler del;
    if ((del = this.m_delegate) != null) {
      del.endElement(uri, localName, qName);
    }
  }

  /**
   * do end element
   *
   * @param uri
   *          see {@link #endElement(String, String, String)}
   * @param localName
   *          see {@link #endElement(String, String, String)}
   * @param qName
   *          see {@link #endElement(String, String, String)}
   * @throws SAXException
   *           see {@link #endElement(String, String, String)}
   */
  protected void doEndElement(final String uri, final String localName,
      final String qName) throws SAXException {
    super.endElement(uri, localName, qName);
  }

  /** {@inheritDoc} */
  @Override
  public final void endElement(final String uri, final String localName,
      final String qName) throws SAXException {
    if (this.m_delegate != null) {
      this.delegateEndElement(uri, localName, qName);
    } else {
      this.doEndElement(uri, localName, qName);
    }
  }

  /**
   * delegate characters
   *
   * @param ch
   *          see {@link #characters(char[], int, int)}
   * @param start
   *          see {@link #characters(char[], int, int)}
   * @param length
   *          see {@link #characters(char[], int, int)}
   * @throws SAXException
   *           see {@link #characters(char[], int, int)}
   */
  protected void delegateCharacters(final char[] ch, final int start,
      final int length) throws SAXException {
    final DelegatingHandler del;

    if ((del = this.m_delegate) != null) {
      del.characters(ch, start, length);
    }
  }

  /**
   * do characters
   *
   * @param ch
   *          see {@link #characters(char[], int, int)}
   * @param start
   *          see {@link #characters(char[], int, int)}
   * @param length
   *          see {@link #characters(char[], int, int)}
   * @throws SAXException
   *           see {@link #characters(char[], int, int)}
   */
  protected void doCharacters(final char[] ch, final int start,
      final int length) throws SAXException {
    super.characters(ch, start, length);
  }

  /** {@inheritDoc} */
  @Override
  public void characters(final char[] ch, final int start, final int length)
      throws SAXException {
    if (this.m_delegate != null) {
      this.delegateCharacters(ch, start, length);
    } else {
      this.doCharacters(ch, start, length);
    }
  }

  /**
   * delegate ignorableWhitespace
   *
   * @param ch
   *          see {@link #ignorableWhitespace(char[], int, int)}
   * @param start
   *          see {@link #ignorableWhitespace(char[], int, int)}
   * @param length
   *          see {@link #ignorableWhitespace(char[], int, int)}
   * @throws SAXException
   *           see {@link #ignorableWhitespace(char[], int, int)}
   */
  protected void delegateIgnorableWhitespace(final char[] ch,
      final int start, final int length) throws SAXException {
    final DelegatingHandler del;

    if ((del = this.m_delegate) != null) {
      del.ignorableWhitespace(ch, start, length);
    }
  }

  /**
   * do ignorableWhitespace
   *
   * @param ch
   *          see {@link #ignorableWhitespace(char[], int, int)}
   * @param start
   *          see {@link #ignorableWhitespace(char[], int, int)}
   * @param length
   *          see {@link #ignorableWhitespace(char[], int, int)}
   * @throws SAXException
   *           see {@link #ignorableWhitespace(char[], int, int)}
   */
  protected void doIgnorableWhitespace(final char[] ch, final int start,
      final int length) throws SAXException {
    super.ignorableWhitespace(ch, start, length);
  }

  /** {@inheritDoc} */
  @Override
  public void ignorableWhitespace(final char[] ch, final int start,
      final int length) throws SAXException {
    if (this.m_delegate != null) {
      this.delegateIgnorableWhitespace(ch, start, length);
    } else {
      this.doIgnorableWhitespace(ch, start, length);
    }
  }

  /**
   * delegate processing instructions
   *
   * @param target
   *          see {@link #processingInstruction(String, String)}
   * @param data
   *          see {@link #processingInstruction(String, String)}
   * @throws SAXException
   *           see {@link #processingInstruction(String, String)}
   */
  protected void delegateProcessingInstruction(final String target,
      final String data) throws SAXException {
    final DelegatingHandler del;
    if ((del = this.m_delegate) != null) {
      del.processingInstruction(target, data);
    }
  }

  /**
   * do processing instructions
   *
   * @param target
   *          see {@link #processingInstruction(String, String)}
   * @param data
   *          see {@link #processingInstruction(String, String)}
   * @throws SAXException
   *           see {@link #processingInstruction(String, String)}
   */
  protected void doProcessingInstruction(final String target,
      final String data) throws SAXException {
    super.processingInstruction(target, data);
  }

  /** {@inheritDoc} */
  @Override
  public final void processingInstruction(final String target,
      final String data) throws SAXException {
    if (this.m_delegate != null) {
      this.delegateProcessingInstruction(target, data);
    } else {
      this.doProcessingInstruction(target, data);
    }
  }

  /**
   * delegate skipped entities
   *
   * @param name
   *          see {@link #skippedEntity(String)}
   * @throws SAXException
   *           see {@link #skippedEntity(String)}
   */
  protected void delegateSkippedEntity(final String name)
      throws SAXException {
    final DelegatingHandler del;
    if ((del = this.m_delegate) != null) {
      del.skippedEntity(name);
    }
  }

  /**
   * do skipped entities
   *
   * @param name
   *          see {@link #skippedEntity(String)}
   * @throws SAXException
   *           see {@link #skippedEntity(String)}
   */
  protected void doSkippedEntity(final String name) throws SAXException {
    super.skippedEntity(name);
  }

  /** {@inheritDoc} */
  @Override
  public final void skippedEntity(final String name) throws SAXException {
    if (this.m_delegate != null) {
      this.delegateSkippedEntity(name);
    } else {
      this.doSkippedEntity(name);
    }
  }

  /**
   * delegate a warning
   *
   * @param e
   *          see {@link #warning(SAXParseException)}
   * @throws SAXException
   *           {@link #warning(SAXParseException)}
   */
  protected void delegateWarning(final SAXParseException e)
      throws SAXException {
    final DelegatingHandler del;
    if ((del = this.m_delegate) != null) {
      del.warning(e);
    }
  }

  /**
   * do a warning
   *
   * @param e
   *          see {@link #warning(SAXParseException)}
   * @throws SAXException
   *           {@link #warning(SAXParseException)}
   */
  protected void doWarning(final SAXParseException e) throws SAXException {
    super.warning(e);
  }

  /** {@inheritDoc} */
  @Override
  public final void warning(final SAXParseException e) throws SAXException {
    if (this.m_delegate != null) {
      this.delegateWarning(e);
    } else {
      this.doWarning(e);
    }
  }

  /**
   * delegate a error
   *
   * @param e
   *          see {@link #error(SAXParseException)}
   * @throws SAXException
   *           {@link #error(SAXParseException)}
   */
  protected void delegateError(final SAXParseException e)
      throws SAXException {
    final DelegatingHandler del;
    if ((del = this.m_delegate) != null) {
      del.error(e);
    }
  }

  /**
   * do a error
   *
   * @param e
   *          see {@link #error(SAXParseException)}
   * @throws SAXException
   *           {@link #error(SAXParseException)}
   */
  protected void doError(final SAXParseException e) throws SAXException {
    super.error(e);
  }

  /** {@inheritDoc} */
  @Override
  public final void error(final SAXParseException e) throws SAXException {
    if (this.m_delegate != null) {
      this.delegateError(e);
    } else {
      this.doError(e);
    }
  }

  /**
   * delegate a fatalError
   *
   * @param e
   *          see {@link #fatalError(SAXParseException)}
   * @throws SAXException
   *           {@link #fatalError(SAXParseException)}
   */
  protected void delegateFatalError(final SAXParseException e)
      throws SAXException {
    final DelegatingHandler del;
    if ((del = this.m_delegate) != null) {
      del.fatalError(e);
    }
  }

  /**
   * do a fatalError
   *
   * @param e
   *          see {@link #fatalError(SAXParseException)}
   * @throws SAXException
   *           {@link #fatalError(SAXParseException)}
   */
  protected void doFatalError(final SAXParseException e)
      throws SAXException {
    super.fatalError(e);
  }

  /** {@inheritDoc} */
  @Override
  public final void fatalError(final SAXParseException e)
      throws SAXException {
    if (this.m_delegate != null) {
      this.delegateFatalError(e);
    } else {
      this.doFatalError(e);
    }
  }

  /**
   * Get the value of a given attribute. This method first tries to find
   * the attribute by looking for the corresponding
   * {@code (namespaceURI, name)} pair. if that fails, it looks for an
   * attribute with an empty ({@code ""}) namespace and the right
   * {@code name}. If that fails as well, it looks just for an attribute
   * where the qualified name is the same as {@code name}.
   *
   * @param attributes
   *          the attributes
   * @param namespaceURI
   *          the namespace uri
   * @param name
   *          the attribute name
   * @return the value, or {@code null} if the value has not been specified
   * @see #getAttributeNormalized(Attributes, String, String)
   */
  protected static final String getAttribute(final Attributes attributes,
      final String namespaceURI, final String name) {
    String a;

    a = attributes.getValue(namespaceURI, name);
    if (a == null) {
      a = attributes.getValue("", name); //$NON-NLS-1$
      if (a == null) {
        a = attributes.getValue(name);
      }
    }
    return a;
  }

  /**
   * Get the value of a given attribute via
   * {@link #getAttribute(Attributes, String, String)} and use
   * {@link org.optimizationBenchmarking.utils.text.TextUtils#normalize(String)}
   * to normalize the result. This will convert all empty string attribute
   * values to {@code null}.
   *
   * @param attributes
   *          the attributes
   * @param namespaceURI
   *          the namespace uri
   * @param name
   *          the attribute name
   * @return the value, or {@code null} if the value has not been specified
   *         or was empty
   * @see #getAttribute(Attributes, String, String)
   */
  protected static final String getAttributeNormalized(
      final Attributes attributes, final String namespaceURI,
      final String name) {
    final String a;

    a = DelegatingHandler.getAttribute(attributes, namespaceURI, name);
    if (a != null) {
      return TextUtils.normalize(a);
    }
    return null;
  }
}
