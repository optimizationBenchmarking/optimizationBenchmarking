package org.optimizationBenchmarking.utils.document.template;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.bibliography.io.BibliographyXMLHandler;
import org.optimizationBenchmarking.utils.document.spec.ECitationMode;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IDocumentElement;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.io.xml.DelegatingHandler;
import org.optimizationBenchmarking.utils.parsers.ByteParser;
import org.optimizationBenchmarking.utils.parsers.DoubleParser;
import org.optimizationBenchmarking.utils.parsers.FloatParser;
import org.optimizationBenchmarking.utils.parsers.IntParser;
import org.optimizationBenchmarking.utils.parsers.LongParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.parsers.ShortParser;
import org.optimizationBenchmarking.utils.reflection.GetStaticConstantByName;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.numbers.NumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** a handler for bibliography xml data */
public final class DocumentXMLHandler extends DelegatingHandler {

  /** the document elements */
  private final ArrayList<IDocumentElement> m_elements;

  /** the properties provided as parameter: these values can be printed */
  private final Map<Object, Object> m_properties;

  /** the parser for the next text string */
  private Parser<?> m_formatParser;
  /** the formatter to format the next special output, such as numbers */
  private Object m_formatFormatter;
  /** the text case to be passed to the formatter, if one is used */
  private ETextCase m_formatTextCase;

  /** the memory text output buffer */
  private final MemoryTextOutput m_textChars;

  /** was the last character a white space? */
  private boolean m_textLastWasSpace;

  /** a multi-purpose cache */
  private final HashMap<Object, Object> m_cache;

  /**
   * Create
   * 
   * @param owner
   *          the owning handler, or {@code null} if not used
   * @param dest
   *          the document element to load the data into
   * @param properties
   *          a map with properties which can be accessed
   */
  public DocumentXMLHandler(final DelegatingHandler owner,
      final IDocumentElement dest, final Map<Object, Object> properties) {
    super(owner);
    this.m_elements = new ArrayList<>();
    this.m_elements.add(dest);

    this.m_properties = properties;
    this.m_textChars = new MemoryTextOutput(4096);
    this.m_cache = new HashMap<>();
  }

  /**
   * Create
   * 
   * @param dest
   *          the document element to load the data into
   * @param properties
   *          a map with properties which can be accessed
   */
  public DocumentXMLHandler(final IDocumentElement dest,
      final Map<Object, Object> properties) {
    this(null, dest, properties);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected final void doStartElement(final String uri,
      final String localName, final String qName,
      final Attributes attributes) throws SAXException {
    final IDocumentElement e;

    this.__flushText();

    if ((uri == null)
        || (_DocumentXMLConstants.NAMESPACE.equalsIgnoreCase(uri))) {

      if (_DocumentXMLConstants.ELEMENT_DOUBLE.equalsIgnoreCase(localName)) {
        this.__loadFormatAttrs(attributes);
        this.m_formatParser = DoubleParser.INSTANCE;
        return;
      }

      if (_DocumentXMLConstants.ELEMENT_INT.equalsIgnoreCase(localName)) {
        this.__loadFormatAttrs(attributes);
        this.m_formatParser = IntParser.INSTANCE;
        return;
      }

      if (_DocumentXMLConstants.ELEMENT_LONG.equalsIgnoreCase(localName)) {
        this.__loadFormatAttrs(attributes);
        this.m_formatParser = LongParser.INSTANCE;
        return;
      }

      if (_DocumentXMLConstants.ELEMENT_FLOAT.equalsIgnoreCase(localName)) {
        this.__loadFormatAttrs(attributes);
        this.m_formatParser = FloatParser.INSTANCE;
        return;
      }
      if (_DocumentXMLConstants.ELEMENT_SHORT.equalsIgnoreCase(localName)) {
        this.__loadFormatAttrs(attributes);
        this.m_formatParser = ShortParser.INSTANCE;
        return;
      }

      if (_DocumentXMLConstants.ELEMENT_BYTE.equalsIgnoreCase(localName)) {
        this.__loadFormatAttrs(attributes);
        this.m_formatParser = ByteParser.INSTANCE;
        return;
      }

      e = this.m_elements.get(this.m_elements.size() - 1);

      if (_DocumentXMLConstants.ELEMENT_BR.equalsIgnoreCase(localName)) {
        ((ITextOutput) e).appendLineBreak();
        return;
      }

      if (_DocumentXMLConstants.ELEMENT_NBSP.equalsIgnoreCase(localName)) {
        ((ITextOutput) e).appendNonBreakingSpace();
        return;
      }

      if (_DocumentXMLConstants.ELEMENT_VALUE.equalsIgnoreCase(localName)) {
        this.__loadFormatAttrs(attributes);
        this.__flushObject(this.m_properties.get(DelegatingHandler
            .getAttributeNormalized(attributes,
                _DocumentXMLConstants.NAMESPACE,
                _DocumentXMLConstants.ATTR_VALUE_OF)), ((ITextOutput) e));
        return;
      }

      if (_DocumentXMLConstants.ELEMENT_QUOTE.equalsIgnoreCase(localName)) {
        this.m_elements.add(((IPlainText) e).inQuotes());
        return;
      }

      if (_DocumentXMLConstants.ELEMENT_BRACE.equalsIgnoreCase(localName)) {
        this.m_elements.add(((IPlainText) e).inBraces());
        return;
      }

      if (_DocumentXMLConstants.ELEMENT_EMPH.equalsIgnoreCase(localName)) {
        this.m_elements.add(((IComplexText) e).emphasize());
        return;
      }

      if (_DocumentXMLConstants.ELEMENT_CODE.equalsIgnoreCase(localName)) {
        this.m_elements.add(((IComplexText) e).inlineCode());
        return;
      }

      if (_DocumentXMLConstants.ELEMENT_SUB.equalsIgnoreCase(localName)) {
        this.m_elements.add(((IComplexText) e).subscript());
        return;
      }

      if (_DocumentXMLConstants.ELEMENT_SUP.equalsIgnoreCase(localName)) {
        this.m_elements.add(((IComplexText) e).superscript());
        return;
      }

      if (_DocumentXMLConstants.ELEMENT_CITE.equalsIgnoreCase(localName)) {
        this.__cite(attributes, ((IComplexText) e));
        return;
      }

      return;
    }

  }

  /**
   * begin a citation
   * 
   * @param attr
   *          the attributes
   * @param dest
   *          the destination text
   */
  @SuppressWarnings("unused")
  private final void __cite(final Attributes attr, final IComplexText dest) {
    ESequenceMode seq;
    ETextCase cas;
    ECitationMode mod;
    int i;
    String s;

    seq = _DocumentXMLConstants._parseSequenceMode(DelegatingHandler
        .getAttributeNormalized(attr, _DocumentXMLConstants.NAMESPACE,
            _DocumentXMLConstants.ATTR_SEQUENCE_MODE));
    if (seq == null) {
      seq = ESequenceMode.COMMA;
    }

    cas = _DocumentXMLConstants._parseTextCase(DelegatingHandler
        .getAttributeNormalized(attr, _DocumentXMLConstants.NAMESPACE,
            _DocumentXMLConstants.ATTR_TEXT_CASE));

    s = DelegatingHandler.getAttributeNormalized(attr,
        _DocumentXMLConstants.NAMESPACE,
        _DocumentXMLConstants.ATTR_CITATION_MODE);
    mod = null;
    if (s != null) {
      for (i = _DocumentXMLConstants.VAL_CITATION_MODE.length; (--i) >= 0;) {
        if (_DocumentXMLConstants.VAL_CITATION_MODE[i].equalsIgnoreCase(s)) {
          mod = ECitationMode.INSTANCES.get(i);
          break;
        }
      }
    }
    if (mod == null) {
      mod = ECitationMode.ID;
    }

    new BibliographyXMLHandler(this, dest.cite(mod, cas, seq));
  }

  /** {@inheritDoc} */
  @Override
  protected void doCharacters(final char ch[], final int start,
      final int length) throws SAXException {
    final MemoryTextOutput chars;
    int oldPos, curPos, end;
    boolean lastWasWhiteSpace;
    char c;

    if (length <= 0) {
      return;
    }

    lastWasWhiteSpace = this.m_textLastWasSpace;

    curPos = oldPos = start;
    end = (curPos + length);
    chars = this.m_textChars;

    while (curPos < end) {
      c = ch[curPos];
      if ((c <= ' ') || Character.isWhitespace(c)) {
        if (!lastWasWhiteSpace) {
          lastWasWhiteSpace = true;
          if (curPos > oldPos) {
            chars.append(ch, oldPos, curPos);
          }
          chars.append(' ');
        }
      } else {
        if (lastWasWhiteSpace) {
          oldPos = curPos;
          lastWasWhiteSpace = false;
        }
      }
      curPos++;
    }

    if ((!lastWasWhiteSpace) && (curPos > oldPos)) {
      chars.append(ch, oldPos, curPos);
    }

    this.m_textLastWasSpace = lastWasWhiteSpace;
  }

  /** flush text */
  private final void __flushText() {
    final MemoryTextOutput chars;
    final ITextOutput t;
    Parser<?> p;
    Object o;
    String s;

    p = this.m_formatParser;
    this.m_formatParser = null;

    chars = this.m_textChars;
    if (chars.length() > 0) {
      t = ((ITextOutput) (this.m_elements.get(this.m_elements.size() - 1)));

      if (p != null) {
        s = chars.toString();
        chars.clear();

        o = null;
        try {
          o = p.parseString(s);
          s = null;
          p = null;
        } catch (final Exception e) {
          s = null;
          p = null;
          ErrorUtils.throwAsRuntimeException(e);
        }

        this.__flushObject(o, t);
      } else {
        chars.toText(t);
        chars.clear();
      }
    } else {
      if (p != null) {
        throw new IllegalStateException("Cannot parse empty string with " //$NON-NLS-1$ 
            + p);
      }
    }

    this.m_textLastWasSpace = false;
  }

  /**
   * load format attributes
   * 
   * @param atts
   *          the attributes
   */
  private final void __loadFormatAttrs(final Attributes atts) {
    String s;
    DecimalFormatSymbols x;

    this.m_formatFormatter = null;
    this.m_formatTextCase = ETextCase.IN_SENTENCE;

    s = DelegatingHandler.getAttributeNormalized(atts,
        _DocumentXMLConstants.NAMESPACE,
        _DocumentXMLConstants.ATTR_FORMAT_PROPERTY);
    if (s != null) {
      this.m_formatFormatter = this.m_properties.get(s);
    } else {
      s = DelegatingHandler.getAttributeNormalized(atts,
          _DocumentXMLConstants.NAMESPACE,
          _DocumentXMLConstants.ATTR_FORMAT_INSTANCE);
      if (s != null) {
        this.m_formatFormatter = this.m_cache.get(s);
        if (this.m_formatFormatter == null) {
          try {
            this.m_formatFormatter = new GetStaticConstantByName<>(s,
                Object.class).call();
          } catch (NoSuchFieldException | IllegalArgumentException
              | IllegalAccessException | LinkageError
              | ClassNotFoundException | ClassCastException y) {
            ErrorUtils.throwAsRuntimeException(y);
          }
          if (this.m_formatFormatter != null) {
            this.m_cache.put(s, this.m_formatFormatter);
          }
        }
      } else {
        s = DelegatingHandler.getAttributeNormalized(atts,
            _DocumentXMLConstants.NAMESPACE,
            _DocumentXMLConstants.ATTR_FORMAT_PATTERN);
        if (s != null) {

          this.m_formatFormatter = this.m_cache.get(s);
          if (this.m_formatFormatter == null) {
            x = ((DecimalFormatSymbols) (this.m_cache
                .get(DecimalFormatSymbols.class)));
            if (x == null) {
              x = new DecimalFormatSymbols(Locale.US);
              this.m_cache.put(DecimalFormatSymbols.class, x);
            }
            this.m_formatFormatter = new DecimalFormat(s, x);
          }
        }
      }
    }

    s = DelegatingHandler.getAttributeNormalized(atts,
        _DocumentXMLConstants.NAMESPACE,
        _DocumentXMLConstants.ATTR_TEXT_CASE);
    if (s != null) {
      this.m_formatTextCase = _DocumentXMLConstants._parseTextCase(s);
    }
  }

  /**
   * Append an object to the given text output destination
   * 
   * @param o
   *          the object to be flushed
   * @param dest
   *          the destination
   */
  private final void __flushObject(final Object o, final ITextOutput dest) {
    final Object format;
    final ETextCase tc;
    final double d;
    final long l;
    final int i;
    final short s;
    final byte b;
    final float f;

    this.m_formatParser = null;
    format = this.m_formatFormatter;
    this.m_formatFormatter = null;
    if (this.m_formatTextCase == null) {
      tc = ETextCase.IN_SENTENCE;
    } else {
      tc = this.m_formatTextCase;
      this.m_formatTextCase = null;
    }

    if (o instanceof ITextable) {
      ((ITextable) o).toText(dest);
      return;
    }

    if (o instanceof Number) {

      if (o instanceof Integer) {
        i = ((Integer) o).intValue();
        if (format instanceof NumberAppender) {
          ((NumberAppender) (format)).appendTo(i, tc, dest);
        } else {
          if (format instanceof DecimalFormat) {
            dest.append(((DecimalFormat) (format)).format(i));
          } else {
            dest.append(i);
          }
        }
        return;
      }

      if (o instanceof Long) {
        l = ((Long) o).longValue();
        if (format instanceof NumberAppender) {
          ((NumberAppender) (format)).appendTo(l, tc, dest);
        } else {
          if (format instanceof DecimalFormat) {
            dest.append(((DecimalFormat) (format)).format(l));
          } else {
            dest.append(l);
          }
        }
        return;
      }

      if (o instanceof Float) {
        f = ((Float) o).floatValue();
        if (format instanceof NumberAppender) {
          ((NumberAppender) (format)).appendTo(f, tc, dest);
        } else {
          if (format instanceof DecimalFormat) {
            dest.append(((DecimalFormat) (format)).format(f));
          } else {
            dest.append(f);
          }
        }
        return;
      }

      if (o instanceof Short) {
        s = ((Short) o).shortValue();
        if (format instanceof NumberAppender) {
          ((NumberAppender) (format)).appendTo(s, tc, dest);
        } else {
          if (format instanceof DecimalFormat) {
            dest.append(((DecimalFormat) (format)).format(s));
          } else {
            dest.append(s);
          }
        }
        return;
      }

      if (o instanceof Byte) {
        b = ((Byte) o).byteValue();
        if (format instanceof NumberAppender) {
          ((NumberAppender) (format)).appendTo(b, tc, dest);
        } else {
          if (format instanceof DecimalFormat) {
            dest.append(((DecimalFormat) (format)).format(b));
          } else {
            dest.append(b);
          }
        }
        return;
      }

      d = ((Number) o).doubleValue();
      if (format instanceof NumberAppender) {
        ((NumberAppender) (format)).appendTo(d, tc, dest);
      } else {
        if (format instanceof DecimalFormat) {
          dest.append(((DecimalFormat) (format)).format(d));
        } else {
          dest.append(d);
        }
      }
      return;
    }

    if (o instanceof String) {
      dest.append((String) o);
      return;
    }
    if (o instanceof char[]) {
      dest.append((char[]) o);
      return;
    }
    if (o instanceof CharSequence) {
      dest.append((CharSequence) o);
      return;
    }

    if (o instanceof Character) {
      dest.append(((Character) o).charValue());
      return;
    }
    if (o instanceof Boolean) {
      dest.append(((Boolean) o).booleanValue());
      return;
    }

    dest.append(o);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doEndElement(final String uri,
      final String localName, final String qName) throws SAXException {

    this.__flushText();

    if ((uri == null)
        || (_DocumentXMLConstants.NAMESPACE.equalsIgnoreCase(uri))) {

      if (_DocumentXMLConstants.ELEMENT_QUOTE.equalsIgnoreCase(localName)
          || //
          _DocumentXMLConstants.ELEMENT_BRACE.equalsIgnoreCase(localName)
          || //
          _DocumentXMLConstants.ELEMENT_EMPH.equalsIgnoreCase(localName) || //
          _DocumentXMLConstants.ELEMENT_CODE.equalsIgnoreCase(localName) || //
          _DocumentXMLConstants.ELEMENT_SUB.equalsIgnoreCase(localName) || //
          _DocumentXMLConstants.ELEMENT_SUP.equalsIgnoreCase(localName)) {
        this.m_elements.remove(this.m_elements.size() - 1).close();
        if (this.m_elements.isEmpty()) {
          this.close();
        }
      }

    }
  }
}
