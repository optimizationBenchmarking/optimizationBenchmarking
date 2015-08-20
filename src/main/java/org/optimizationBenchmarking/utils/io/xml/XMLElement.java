package org.optimizationBenchmarking.utils.io.xml;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.text.transformations.XMLCharTransformer;

/**
 * An xml element output node. This node is used in four steps:
 * <ol>
 * <li>Namespace declarations: Declare all the namespaces it uses via
 * {@link #namespaceSetDefault(URI)} and
 * {@link #namespaceSetPrefix(URI, String)}.</li>
 * <li>Name: Declare the name of the node via {@link #name(URI, String)}.</li>
 * <li>Attributes: Write all attributes via
 * {@link #attributeEncoded(URI, String, String)} and
 * {@link #attributeRaw(URI, String, String)}.</li>
 * <li>Write the elements content, such as
 * <ol>
 * <li>PlainText output via {@link #textRaw()} and {@link #textEncoded()}.</li>
 * <li>Contained elements via {@link #element()}.</li>
 * </ol>
 * </li>
 * </ol>
 */
public final class XMLElement extends XMLBase {

  /** namespaces can be declared */
  private static final int STATE_NAMESPACES = (FSM.STATE_NOTHING + 1);
  /** name can be declared */
  private static final int STATE_NAME = (XMLElement.STATE_NAMESPACES + 1);
  /** attributes can be written */
  private static final int STATE_ATTRIBUTES = (XMLElement.STATE_NAME + 1);
  /** content can be written */
  private static final int STATE_CONTENT = (XMLElement.STATE_ATTRIBUTES + 1);
  /** nothing can be written anymore */
  private static final int STATE_CLOSED = (XMLElement.STATE_CONTENT + 1);

  /** the xml namespace chars */
  private static final char[] XMLNS = { ' ', 'x', 'm', 'l', 'n', 's' };

  /** equals-quote */
  private static final char[] EQQUO = { '=', '"' };

  /** the element name */
  private String m_name;

  /** the namespace uri of this element */
  private URI m_namespaceURI;

  /** the namespaces: this map has copy-on-write semantics */
  private HashMap<Object, _Namespace> m_namespaces;

  /**
   * the default namespace: inherited from owning element, but can be
   * overwritten via {@link #namespaceSetDefault(URI)}
   */
  private _Namespace m_defaultNamespace;

  /** the new namespaces */
  private ArrayList<_Namespace> m_todo;

  /** the raw text output */
  private ITextOutput m_encoded;

  /**
   * create a new xml element
   *
   * @param owner
   *          the owner the name
   */
  XMLElement(final XMLBase owner) {
    super(owner, null);

    final XMLElement o;

    if (owner instanceof XMLElement) {
      o = ((XMLElement) owner);
      this.m_namespaces = o.m_namespaces;
      this.m_defaultNamespace = o.m_defaultNamespace;
    } else {
      this.m_namespaces = new HashMap<>();
    }

    this.open();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected synchronized void onOpen() {
    ITextOutput o;
    HierarchicalFSM owner;
    XMLElement xo;

    super.onOpen();

    o = this.getTextOutput();
    owner = this.getOwner();
    if (owner instanceof XMLElement) {
      xo = ((XMLElement) owner);
      if (xo.getTextOutput() == o) {
        this.m_encoded = xo.m_encoded;
        return;
      }
    }
    this.m_encoded = XMLCharTransformer.getInstance().transform(o);
  }

  /**
   * Set the name of this element
   *
   * @param namespaceURI
   *          the namespace uri
   * @param name
   *          the name
   */
  public synchronized final void name(final URI namespaceURI,
      final String name) {
    this.fsmStateAssertAndSet(EComparison.LESS, XMLElement.STATE_NAME,
        XMLElement.STATE_NAME);
    this.m_name = name;
    this.m_namespaceURI = namespaceURI;
  }

  /** {@inheritDoc} */
  @Override
  protected final void fsmStateAppendName(final int state,
      final MemoryTextOutput append) {
    switch (state) {
      case STATE_NAMESPACES: {
        append.append("namespaceDefinitions"); //$NON-NLS-1$
        return;
      }
      case STATE_NAME: {
        append.append("elementName"); //$NON-NLS-1$
        return;
      }
      case STATE_ATTRIBUTES: {
        append.append("attributeValues"); //$NON-NLS-1$
        return;
      }
      case STATE_CONTENT: {
        append.append("contents"); //$NON-NLS-1$
        return;
      }
      case STATE_CLOSED: {
        append.append("closed"); //$NON-NLS-1$
        return;
      }

      default: {
        super.fsmStateAppendName(state, append);
      }
    }
  }

  /**
   * Move to the given state
   *
   * @param maximumAllowedState
   *          the maximum allowed state
   * @param targetState
   *          the target state
   */
  @SuppressWarnings("fallthrough")
  private final void __ensureState(final int maximumAllowedState,
      final int targetState) {
    final int oldState;
    final MemoryTextOutput sb;
    ITextOutput ap;
    _Namespace ns;

    ap = null;
    ns = null;

    oldState = this.fsmStateAssertAndSet(EComparison.LESS_OR_EQUAL,
        maximumAllowedState, targetState);
    if (oldState < targetState) {
      switch (oldState) {
        case STATE_NOTHING:
        case STATE_NAMESPACES: {
          throw new IllegalStateException(//
              "Cannot begin element before setting its name."); //$NON-NLS-1$
        }
        case STATE_NAME: {
          ap = this.getTextOutput();
          if (this.m_namespaceURI != null) {
            ns = this.__getNameSpacePrefix(this.m_namespaceURI, true);
            if (ns != null) {
              this.m_namespaceURI = ns.m_uri;
            }
          }

          ap.append('<');
          if ((ns != null) && (ns.m_prefixChars != null)) {
            ap.append(ns.m_prefixChars);
            ap.append(':');
          }
          ap.append(this.m_name);

          if (this.m_todo != null) {
            for (final _Namespace tns : this.m_todo) {
              ap.append(XMLElement.XMLNS, 0, 6);
              if (tns.m_prefixChars != null) {
                ap.append(':');
                ap.append(tns.m_prefixChars);
              }
              ap.append(XMLElement.EQQUO);
              ap.append(tns.m_uriChars);
              ap.append('"');
            }
            this.m_todo = null;
          }

          if (targetState <= XMLElement.STATE_ATTRIBUTES) {
            return;
          }
        }

        case STATE_ATTRIBUTES: {
          if (targetState <= XMLElement.STATE_CONTENT) {
            ((ap != null) ? ap : this.getTextOutput()).append('>');
            return;
          }
        }

        case STATE_CONTENT: {
          if (ap == null) {
            ap = this.getTextOutput();
          }

          if (oldState >= XMLElement.STATE_CONTENT) {
            ap.append('<');
            ap.append('/');

            if ((ns == null) && (this.m_namespaceURI != null)) {
              ns = this.__getNameSpacePrefix(this.m_namespaceURI, true);
            }
            if ((ns != null) && (ns.m_prefixChars != null)) {
              ap.append(ns.m_prefixChars);
              ap.append(':');
            }
            ap.append(this.m_name);
          } else {
            ap.append('/');
          }
          ap.append('>');

          if (targetState <= XMLElement.STATE_CLOSED) {
            return;
          }
        }

        default: {
          sb = new MemoryTextOutput();
          sb.append(//
          "We should have never gotten here, old state "); //$NON-NLS-1$
          this.fsmStateAppendName(oldState, sb);
          sb.append(//
          " is unknown or inconsistent and cannot be left for target state "); //$NON-NLS-1$
          this.fsmStateAppendName(targetState, sb);
          sb.append('.');
          throw new IllegalStateException(sb.toString());
        }
      }

    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.__ensureState(XMLElement.STATE_CONTENT, XMLElement.STATE_CLOSED);
    super.onClose();
  }

  /**
   * Obtain the prefix for a given namespace.
   *
   * @param uri
   *          the namespace URI
   * @param isDefaultNamespaceAllowed
   *          is the default namespace allowed?
   * @return the prefix to use
   */
  private final _Namespace __getNameSpacePrefix(final URI uri,
      final boolean isDefaultNamespaceAllowed) {
    final HashMap<Object, _Namespace> nss;
    final _Namespace def;
    final MemoryTextOutput sb;
    URI useURI;
    _Namespace definition;

    if (uri == null) {
      throw new IllegalArgumentException(
          "Namespace URIs must not be null."); //$NON-NLS-1$
    }

    useURI = null;
    definition = null;
    nss = this.m_namespaces;

    // Try to find if a prefix has already been defined for the namespace
    // URI. If so, it takes precedence to the (new) prefix. The only
    // situation
    // where having two prefixes for a namespace is admissible is to
    // declare an
    // URI both the default namespace and to give it a prefix. This is the
    // only
    // way to put attributes into a namespace which is the default
    // namespace.
    def = ((isDefaultNamespaceAllowed) ? (this.m_defaultNamespace) : null);

    if (def != null) {
      if (def.m_uri.equals(uri)) {
        return def;
      }
    }

    // We cannot use a default namespace for this exact uri.
    definition = nss.get(uri);
    if (definition != null) {
      return definition;
    }

    // No namespace was assigned to this exact uri, but maybe it has just
    // not
    // been normalized properly?
    useURI = uri.normalize();
    if (useURI != uri) {
      if (def != null) {
        if (def.m_uri.equals(useURI)) {
          return def;
        }
      }

      // We cannot use a default namespace for this exact uri.
      definition = nss.get(useURI);
      if (definition != null) {
        return definition;
      }
    }

    sb = new MemoryTextOutput();
    sb.append("Namespace '"); //$NON-NLS-1$
    sb.append(uri.toString());
    if (useURI != uri) {
      sb.append("' and its normalized version '"); //$NON-NLS-1$
      sb.append(useURI);
      sb.append("' do not have a prefix"); //$NON-NLS-1$
    } else {
      sb.append("' does not have a prefix"); //$NON-NLS-1$
    }
    if (isDefaultNamespaceAllowed) {
      sb.append(" nor is it the default namespace"); //$NON-NLS-1$
    }
    sb.append(" of "); //$NON-NLS-1$
    sb.append(this);
    sb.append('.');
    throw new IllegalArgumentException(sb.toString());
  }

  /**
   * Obtain a
   * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
   * with which text can directly be written to the underlying stream,
   * without any prior encoding. Only characters must be written that
   * cannot lead to problems with the XML specification. In particular, do
   * not write stuff such as <code>&amp;</code> or <code>&lt;</code>.
   *
   * @return the raw text output destination
   * @see #textEncoded()
   */
  public final ITextOutput textRaw() {
    this.__ensureState(XMLElement.STATE_CONTENT, XMLElement.STATE_CONTENT);
    return this.getTextOutput();
  }

  /**
   * Get a
   * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
   * for writing arbitrary text output to this element.
   *
   * @return the encoded text output destination
   * @see #textRaw()
   */
  public final ITextOutput textEncoded() {
    this.__ensureState(XMLElement.STATE_CONTENT, XMLElement.STATE_CONTENT);
    this.assertNoChildren();// can we write text or is a child open?
    return this.m_encoded;
  }

  /**
   * Write an attribute value, encode if necessary.
   *
   * @param namespaceURI
   *          the namespace of the attribute
   * @param name
   *          the name of the attribute
   * @param value
   *          the attribute's value
   * @param encode
   *          perform encoding?
   */
  private synchronized final void __attribute(final URI namespaceURI,
      final String name, final String value, final boolean encode) {
    final char[] prefix;
    final ITextOutput ap;

    this.__ensureState(XMLElement.STATE_ATTRIBUTES,
        XMLElement.STATE_ATTRIBUTES);
    ap = this.getTextOutput();

    if ((namespaceURI != null) && (namespaceURI != this.m_namespaceURI)
        && (!(namespaceURI.equals(this.m_namespaceURI)))) {
      prefix = this.__getNameSpacePrefix(namespaceURI, false).m_prefixChars;
    } else {
      prefix = null;
    }

    ap.append(' ');
    if (prefix != null) {
      ap.append(prefix);
      ap.append(':');
    }
    ap.append(name);
    ap.append(XMLElement.EQQUO);
    (encode ? this.m_encoded : ap).append(value);
    ap.append('"');
  }

  /**
   * Write an attribute value
   *
   * @param namespaceURI
   *          the namespace of the attribute
   * @param name
   *          the name of the attribute
   * @param value
   *          the attribute's value
   */
  public final void attributeEncoded(final URI namespaceURI,
      final String name, final String value) {
    this.__attribute(namespaceURI, name, value, true);
  }

  /**
   * Write an attribute value to this element without performing
   * XML-encoding. This method is faster than
   * {@link #attributeEncoded(URI, String, String)}, but can only be
   * applied if it is absolutely clear that the contents of {@code value}
   * will not violate the XML specification, in particular, do not contain
   * stuff such as {@code "}.
   *
   * @param namespaceURI
   *          the namespace of the attribute
   * @param name
   *          the name of the attribute
   * @param value
   *          the attribute's value
   */
  public final void attributeRaw(final URI namespaceURI,
      final String name, final String value) {
    this.__attribute(namespaceURI, name, value, false);
  }

  /**
   * Assign a prefix to a namespace uri
   *
   * @param namespaceURI
   *          the namespace uri
   * @param prefix
   *          the prefix
   */
  @SuppressWarnings("unchecked")
  public synchronized final void namespaceSetPrefix(
      final URI namespaceURI, final String prefix) {
    final String pf;
    ArrayList<_Namespace> todo;
    HashMap<Object, _Namespace> nss;
    URI useURI;
    _Namespace definition;

    if (namespaceURI == null) {
      throw new IllegalArgumentException(//
          "Cannot set prefix '" + prefix + //$NON-NLS-1$
              "' for null namespace URI in '" + this + //$NON-NLS-1$
              "'."); //$NON-NLS-1$
    }

    pf = TextUtils.normalize(prefix);
    if (pf == null) {
      throw new IllegalArgumentException(//
          "Namespace prefix must not be empty or null, but prefix for namespace '"//$NON-NLS-1$
              + namespaceURI + "' is '" + prefix + //$NON-NLS-1$
              "' in '" + this + "'."); //$NON-NLS-1$//$NON-NLS-2$
    }

    this.fsmStateAssertAndSet(EComparison.LESS_OR_EQUAL,
        XMLElement.STATE_NAMESPACES, XMLElement.STATE_NAMESPACES);

    nss = this.m_namespaces;
    definition = nss.get(namespaceURI);
    if (definition != null) {
      return;
    }

    useURI = namespaceURI.normalize();
    if (useURI != namespaceURI) {
      definition = nss.get(useURI);
      if (definition != null) {
        if (this.getOwner() instanceof XMLElement) {
          this.m_namespaces = nss = //
          ((HashMap<Object, _Namespace>) (nss.clone()));
        }

        nss.put(namespaceURI, definition);
      }
    }

    definition = nss.get(prefix);
    if (definition != null) {
      if (definition.m_uri.equals(useURI)) {
        return;
      }
      throw new IllegalArgumentException(//
          "Prefix '" + prefix + //$NON-NLS-1$
              "' has already been assigned to namespace URI '"//$NON-NLS-1$
              + definition.m_uri
              + "' so it cannot be assigned to URI '" + namespaceURI + //$NON-NLS-1$
              "' in '" + this + "'."); //$NON-NLS-1$//$NON-NLS-2$
    }

    if (this.getOwner() instanceof XMLElement) {
      this.m_namespaces = nss = ((HashMap<Object, _Namespace>) (nss
          .clone()));
    }
    definition = new _Namespace(useURI, prefix);
    nss.put(prefix, definition);
    nss.put(useURI, definition);
    if (namespaceURI != useURI) {
      nss.put(namespaceURI, definition);
    }

    if ((todo = this.m_todo) == null) {
      this.m_todo = todo = new ArrayList<>();
    }
    todo.add(definition);
  }

  /**
   * Set the namespace uri as default namespace
   *
   * @param namespaceURI
   *          the namespace uri
   */
  public synchronized final void namespaceSetDefault(final URI namespaceURI) {
    final _Namespace ns;
    final URI useURI;
    ArrayList<_Namespace> todo;

    if (namespaceURI == null) {
      throw new IllegalArgumentException(//
          "Cannot set null default namespace."); //$NON-NLS-1$
    }
    this.fsmStateAssertAndSet(EComparison.LESS_OR_EQUAL,
        XMLElement.STATE_NAMESPACES, XMLElement.STATE_NAMESPACES);

    useURI = namespaceURI.normalize();

    if ((todo = this.m_todo) == null) {
      this.m_todo = todo = new ArrayList<>();
    } else {
      for (final _Namespace nso : todo) {
        if (nso.m_prefixChars == null) {
          if (useURI.equals(nso.m_uri)) {
            return;
          }
          throw new IllegalStateException("Cannot set '" + namespaceURI + //$NON-NLS-1$
              "' as default namespace uri, since you already set '" + //$NON-NLS-1$
              nso.m_uri + "' in '" + this + //$NON-NLS-1$
              "'."); //$NON-NLS-1$
        }
      }
    }

    ns = new _Namespace(useURI, null);

    todo.add(ns);
    this.m_defaultNamespace = ns;
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    super.beforeChildOpens(child, hasOtherChildren);
    if (child instanceof XMLElement) {
      this.__ensureState(XMLElement.STATE_CONTENT,
          XMLElement.STATE_CONTENT);
    } else {
      this.throwChildNotAllowed(child);
    }
  }
}
