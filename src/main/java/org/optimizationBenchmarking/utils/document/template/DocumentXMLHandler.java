package org.optimizationBenchmarking.utils.document.template;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;

import org.optimizationBenchmarking.utils.bibliography.io.BibliographyXMLHandler;
import org.optimizationBenchmarking.utils.document.spec.ECitationMode;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IDocumentElement;
import org.optimizationBenchmarking.utils.document.spec.IEquation;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.IList;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IMathName;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;
import org.optimizationBenchmarking.utils.document.spec.IStructuredText;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.io.xml.DelegatingHandler;
import org.optimizationBenchmarking.utils.parsers.LooseByteParser;
import org.optimizationBenchmarking.utils.parsers.LooseDoubleParser;
import org.optimizationBenchmarking.utils.parsers.LooseFloatParser;
import org.optimizationBenchmarking.utils.parsers.LooseIntParser;
import org.optimizationBenchmarking.utils.parsers.LooseLongParser;
import org.optimizationBenchmarking.utils.parsers.LooseShortParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.numbers.NumberAppender;
import org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** a handler for bibliography xml data */
public final class DocumentXMLHandler extends DelegatingHandler {

  /** the number format prefixes */
  private static final String[] NUMBER_FORMAT_PREFIXES;

  static {
    final LinkedHashSet<String> paths;

    paths = new LinkedHashSet<>();
    ReflectionUtils.addPackageOfClassToPrefixList(NumberFormat.class,
        paths);
    ReflectionUtils.addPackageOfClassToPrefixList(DecimalFormat.class,
        paths);
    ReflectionUtils.addPackageOfClassToPrefixList(NumberAppender.class,
        paths);
    ReflectionUtils.addPackageOfClassToPrefixList(
        SimpleNumberAppender.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(
        DecimalFormatSymbols.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(Formatter.class, paths);

    NUMBER_FORMAT_PREFIXES = paths.toArray(new String[paths.size()]);
  }

  /**
   * The stack document elements: the element on the top of the stack can
   * be obtained via {@link #__requireCurrentElement(Class)}. A starting
   * tag in the document element may add a new element to the top of the
   * stack, a closing tag may remove one.
   */
  private final ArrayList<IDocumentElement> m_elementStack;

  /**
   * The properties provided as parameter. Properties can be passed as
   * modifiable parameters to
   * {@code org.optimizationBenchmarking.utils.document.template.IDocumentCallback#callback(IDocumentElement, Map)}
   * and also contain the callback functions.
   */
  private final Map<Object, Object> m_properties;

  /** A multi-purpose cache to speed up accessing and loading of elements. */
  private final HashMap<Object, Object> m_cache;

  // The following two attributes handle text caching. Text must be cached,
  // as we want to a) remove unnecessary white spaces and b) maybe format
  // something as number.
  /**
   * The cached text: filled in by {@link #doCharacters(char[], int, int)}
   * and printed by {@link #__processText()}.
   */
  private final MemoryTextOutput m_textChars;

  /**
   * The last character in the text was a white space, so we can ignore
   * following white spaces.
   */
  private boolean m_textLastWasWhiteSpace;

  // The formatting of the output is handed by these parameters.
  /** the parser for the next text string */
  private ETextCase m_formatTextCase;

  /** the parser for the next text string */
  private Parser<?> m_formatParser;

  /** the formatter to format the next special output, such as numbers */
  private Object m_formatFormatter;

  /** the sequence mode */
  private ESequenceMode m_formatSequenceMode;

  /** the labels */
  private ArrayList<ILabel> m_labels;

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
    this.m_elementStack = new ArrayList<>();
    this.m_elementStack.add(dest);

    this.m_properties = ((properties != null) ? properties
        : new HashMap<>());
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

  /**
   * Obtain the properties of this handler
   *
   * @return the properties of this handler
   */
  public final Map<Object, Object> getProperties() {
    return this.m_properties;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doStartElement(final String uri,
      final String localName, final String qName,
      final Attributes attributes) throws SAXException {

    this.__processText();

    if ((uri == null) || (DocumentXML.NAMESPACE.equalsIgnoreCase(uri))) {

      switch (localName) {

        case DocumentXML.ELEMENT_TEMPLATE: {
          return; // ignore
        }

        case DocumentXML.ELEMENT_DOUBLE: {
          this.__startDouble(attributes);
          return;
        }

        case DocumentXML.ELEMENT_INT: {
          this.__startInt(attributes);
          return;
        }

        case DocumentXML.ELEMENT_LONG: {
          this.__startLong(attributes);
          return;
        }

        case DocumentXML.ELEMENT_FLOAT: {
          this.__startFloat(attributes);
          return;
        }

        case DocumentXML.ELEMENT_SHORT: {
          this.__startShort(attributes);
          return;
        }

        case DocumentXML.ELEMENT_BYTE: {
          this.__startByte(attributes);
          return;
        }

        case DocumentXML.ELEMENT_BR: {
          this.__startBR(attributes);
          return;
        }

        case DocumentXML.ELEMENT_NBSP: {
          this.__startNBSP(attributes);
          return;
        }

        case DocumentXML.ELEMENT_CALL: {
          this.__startCall(attributes);
          return;
        }

        case DocumentXML.ELEMENT_VALUE: {
          this.__startValue(attributes);
          return;
        }

        case DocumentXML.ELEMENT_QUOTE: {
          this.__startQuotes(attributes);
          return;
        }

        case DocumentXML.ELEMENT_BRACE: {
          this.__startBraces(attributes);
          return;
        }

        case DocumentXML.ELEMENT_EMPH: {
          this.__startEmph(attributes);
          return;
        }

        case DocumentXML.ELEMENT_CODE: {
          this.__startInlineCode(attributes);
          return;
        }

        case DocumentXML.ELEMENT_SUP: {
          this.__startSuper(attributes);
          return;
        }

        case DocumentXML.ELEMENT_SUB: {
          this.__startSub(attributes);
          return;
        }

        case DocumentXML.ELEMENT_CITE: {
          this.__startCite(attributes);
          return;
        }

        case DocumentXML.ELEMENT_SECTION: {
          this.__startSection(attributes);
          return;
        }

        case DocumentXML.ELEMENT_SECTION_TITLE: {
          this.__startSectionTitle(attributes);
          return;
        }

        case DocumentXML.ELEMENT_SECTION_BODY: {
          this.__startSectionBody(attributes);
          return;
        }

        case DocumentXML.ELEMENT_REF: {
          this.__startReference(attributes);
          return;
        }

        case DocumentXML.ELEMENT_REF_LABEL: {
          return; // ignore
        }

        case DocumentXML.ELEMENT_OL: {
          this.__startEnumerate(attributes);
          return;
        }

        case DocumentXML.ELEMENT_UL: {
          this.__startItemize(attributes);
          return;
        }

        case DocumentXML.ELEMENT_LI: {
          this.__startItem(attributes);
          return;
        }

        case DocumentXML.ELEMENT_EQUATION: {
          this.__startEquation(attributes);
          return;
        }

        case DocumentXML.ELEMENT_INLINE_MATH: {
          this.__startInlineMath(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_ABS: {
          this.__startMathAbs(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_ADD: {
          this.__startMathAdd(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_COMP: {
          this.__startMathComp(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_COS: {
          this.__startMathCos(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_DIV: {
          this.__startMathDiv(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_DIV_INLINE: {
          this.__startMathDivInline(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_FACTORIAL: {
          this.__startMathFactorial(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_LD: {
          this.__startMathLd(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_LG: {
          this.__startMathLg(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_LN: {
          this.__startMathLn(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_LOG: {
          this.__startMathLog(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_MOD: {
          this.__startMathMod(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_MUL: {
          this.__startMathMul(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_NEGATE: {
          this.__startMathNegate(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_POW: {
          this.__startMathPow(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_ROOT: {
          this.__startMathRoot(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_SIN: {
          this.__startMathSin(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_SQRT: {
          this.__startMathSqrt(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_SUB: {
          this.__startMathSub(attributes);
          return;
        }

        case DocumentXML.ELEMENT_MATH_TAN: {
          this.__startMathTan(attributes);
          return;
        }
        case DocumentXML.ELEMENT_MATH_NUMBER: {
          this.__startMathNumber(attributes);
          return;
        }
        case DocumentXML.ELEMENT_MATH_NAME: {
          this.__startMathName(attributes);
          return;
        }
        case DocumentXML.ELEMENT_MATH_TEXT: {
          this.__startMathText(attributes);
          return;
        }

        default: {
          throw new IllegalArgumentException(//
              "Unknown element with local name '" //$NON-NLS-1$
                  + localName + "' and qualified name '" + //$NON-NLS-1$
                  qName + "' from namespace URI '" + uri + //$NON-NLS-1$
                  "' with attributes " + attributes + //$NON-NLS-1$
                  " detected."); //$NON-NLS-1$
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void doCharacters(final char[] ch, final int start,
      final int length) throws SAXException {
    final MemoryTextOutput chars;
    int oldPos, curPos, end;
    boolean lastWasWhiteSpace;
    char c;

    if ((ch == null) || (length <= 0)) {
      return;
    }

    lastWasWhiteSpace = this.m_textLastWasWhiteSpace;

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

    this.m_textLastWasWhiteSpace = lastWasWhiteSpace;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  protected final void doEndElement(final String uri,
      final String localName, final String qName) throws SAXException {

    this.__processText();

    if ((uri == null) || (DocumentXML.NAMESPACE.equalsIgnoreCase(uri))) {

      switch (localName) {
        case DocumentXML.ELEMENT_QUOTE: {
          this.__endQuotes();
          return;
        }

        case DocumentXML.ELEMENT_BRACE: {
          this.__endBraces();
          return;
        }

        case DocumentXML.ELEMENT_EMPH: {
          this.__endEmph();
          return;
        }

        case DocumentXML.ELEMENT_CODE: {
          this.__endInlineCode();
          return;
        }

        case DocumentXML.ELEMENT_SUP: {
          this.__endSuper();
          return;
        }

        case DocumentXML.ELEMENT_SUB: {
          this.__endSub();
          return;
        }

        case DocumentXML.ELEMENT_SECTION: {
          this.__endSection();
          return;
        }

        case DocumentXML.ELEMENT_SECTION_TITLE: {
          this.__endSectionTitle();
          return;
        }

        case DocumentXML.ELEMENT_SECTION_BODY: {
          this.__endSectionBody();
          return;
        }

        case DocumentXML.ELEMENT_REF: {
          this.__endReference();
          return;
        }

        case DocumentXML.ELEMENT_OL: {
          this.__endEnumerate();
          return;
        }

        case DocumentXML.ELEMENT_UL: {
          this.__endItemize();
          return;
        }

        case DocumentXML.ELEMENT_LI: {
          this.__endItem();
          return;
        }

        case DocumentXML.ELEMENT_EQUATION: {
          this.__endEquation();
          return;
        }

        case DocumentXML.ELEMENT_INLINE_MATH: {
          this.__endInlineMath();
          return;
        }

        case DocumentXML.ELEMENT_MATH_ABS: {
          this.__endMathAbs();
          return;
        }

        case DocumentXML.ELEMENT_MATH_ADD: {
          this.__endMathAdd();
          return;
        }

        case DocumentXML.ELEMENT_MATH_COMP: {
          this.__endMathComp();
          return;
        }

        case DocumentXML.ELEMENT_MATH_COS: {
          this.__endMathCos();
          return;
        }

        case DocumentXML.ELEMENT_MATH_DIV: {
          this.__endMathDiv();
          return;
        }

        case DocumentXML.ELEMENT_MATH_DIV_INLINE: {
          this.__endMathDivInline();
          return;
        }

        case DocumentXML.ELEMENT_MATH_FACTORIAL: {
          this.__endMathFactorial();
          return;
        }

        case DocumentXML.ELEMENT_MATH_LD: {
          this.__endMathLd();
          return;
        }

        case DocumentXML.ELEMENT_MATH_LG: {
          this.__endMathLg();
          return;
        }

        case DocumentXML.ELEMENT_MATH_LN: {
          this.__endMathLn();
          return;
        }

        case DocumentXML.ELEMENT_MATH_LOG: {
          this.__endMathLog();
          return;
        }

        case DocumentXML.ELEMENT_MATH_MOD: {
          this.__endMathMod();
          return;
        }

        case DocumentXML.ELEMENT_MATH_MUL: {
          this.__endMathMul();
          return;
        }

        case DocumentXML.ELEMENT_MATH_NEGATE: {
          this.__endMathNegate();
          return;
        }

        case DocumentXML.ELEMENT_MATH_POW: {
          this.__endMathPow();
          return;
        }

        case DocumentXML.ELEMENT_MATH_ROOT: {
          this.__endMathRoot();
          return;
        }

        case DocumentXML.ELEMENT_MATH_SIN: {
          this.__endMathSin();
          return;
        }

        case DocumentXML.ELEMENT_MATH_SQRT: {
          this.__endMathSqrt();
          return;
        }

        case DocumentXML.ELEMENT_MATH_SUB: {
          this.__endMathSub();
          return;
        }

        case DocumentXML.ELEMENT_MATH_TAN: {
          this.__endMathTan();
          return;
        }
        case DocumentXML.ELEMENT_MATH_NUMBER: {
          this.__endMathNumber();
          return;
        }
        case DocumentXML.ELEMENT_MATH_NAME: {
          this.__endMathName();
          return;
        }
        case DocumentXML.ELEMENT_MATH_TEXT: {
          this.__endMathText();
          return;
        }
      }
    }
  }

  /** process the cached text */
  @SuppressWarnings({ "resource", "null" })
  private final void __processText() {
    final MemoryTextOutput chars;
    final ITextOutput textOut;
    final IDocumentElement element;
    final int len;
    Parser<?> parser;
    Object object;
    String string;

    parser = this.m_formatParser;
    this.m_formatParser = null;
    this.m_textLastWasWhiteSpace = false;

    chars = this.m_textChars;
    if ((len = chars.length()) > 0) {

      // process labels: we are in the ref tag
      if (this.m_labels != null) {
        string = chars.toString();
        chars.clear();
        string = TextUtils.normalize(string);
        if (string == null) {
          throw new IllegalArgumentException(//
              "Label name must not be null or empty."); //$NON-NLS-1$
        }
        object = this.m_properties.get(string);
        if (object == null) {
          throw new IllegalArgumentException("Label '" + //$NON-NLS-1$
              string + "' undefined."); //$NON-NLS-1$
        }
        if (!(object instanceof ILabel)) {
          throw new IllegalArgumentException(//
              "The labels you reference must be instances of ILabel, but property'"//$NON-NLS-1$
                  + string + "' points to an instance of " //$NON-NLS-1$
                  + TextUtils.className(object.getClass()));
        }
        this.m_labels.add((ILabel) object);
        return;
      }

      // process text output
      element = this.__requireCurrentElement(IDocumentElement.class);
      if (element instanceof ITextOutput) {
        textOut = ((ITextOutput) (element));

        if (parser != null) {
          string = chars.toString();
          chars.clear();

          object = null;
          try {
            object = parser.parseString(string);
            string = null;
            parser = null;
          } catch (final Throwable e) {
            throw new IllegalArgumentException(//
                ("Error while parsing String '" + //$NON-NLS-1$
                    string + "' with parser of type " + //$NON-NLS-1$
                TextUtils.className(parser.getClass())), e);
          }

          this.__formatObject(object, textOut);
          return;
        }

        chars.toText(textOut);
        chars.clear();
        return;
      }

      if ((len > 1) || (chars.charAt(0) != ' ')) {
        throw new IllegalStateException("Text '" + chars + //$NON-NLS-1$
            "' was found, but the current element is of type " + //$NON-NLS-1$
            TextUtils.className(element.getClass()) + //
            ", i.e., is not an element to which we can write text.");//$NON-NLS-1$
      }
      chars.clear();
    }

    if (parser != null) {
      throw new IllegalStateException(//
          "Cannot pass an empty string to a parser, but parser of type " //$NON-NLS-1$
              + TextUtils.className(parser.getClass()) + " set.");//$NON-NLS-1$
    }
  }

  /**
   * Start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_DOUBLE}
   * tag. The end does not need to be handled.
   *
   * @param attributes
   *          the attributes
   */
  private final void __startDouble(final Attributes attributes) {
    this.__loadFormatAttrs(attributes);
    this.m_formatParser = LooseDoubleParser.INSTANCE;
    return;
  }

  /**
   * Start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_INT}
   * tag. The end does not need to be handled.
   *
   * @param attributes
   *          the attributes
   */
  private final void __startInt(final Attributes attributes) {
    this.__loadFormatAttrs(attributes);
    this.m_formatParser = LooseIntParser.INSTANCE;
    return;
  }

  /**
   * Start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_LONG}
   * tag. The end does not need to be handled.
   *
   * @param attributes
   *          the attributes
   */
  private final void __startLong(final Attributes attributes) {
    this.__loadFormatAttrs(attributes);
    this.m_formatParser = LooseLongParser.INSTANCE;
    return;
  }

  /**
   * Start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_FLOAT}
   * tag. The end does not need to be handled.
   *
   * @param attributes
   *          the attributes
   */
  private final void __startFloat(final Attributes attributes) {
    this.__loadFormatAttrs(attributes);
    this.m_formatParser = LooseFloatParser.INSTANCE;
    return;
  }

  /**
   * Start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_SHORT}
   * tag. The end does not need to be handled.
   *
   * @param attributes
   *          the attributes
   */
  private final void __startShort(final Attributes attributes) {
    this.__loadFormatAttrs(attributes);
    this.m_formatParser = LooseShortParser.INSTANCE;
    return;
  }

  /**
   * Start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_BYTE}
   * tag. The end does not need to be handled.
   *
   * @param attributes
   *          the attributes
   */
  private final void __startByte(final Attributes attributes) {
    this.__loadFormatAttrs(attributes);
    this.m_formatParser = LooseByteParser.INSTANCE;
    return;
  }

  /**
   * Start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_VALUE}
   * tag. The end does not need to be handled.
   *
   * @param attributes
   *          the attributes
   */
  private final void __startValue(final Attributes attributes) {
    final Object object;
    final String value;

    this.__loadFormatAttrs(attributes);

    value = DelegatingHandler.getAttributeNormalized(attributes,
        DocumentXML.NAMESPACE, DocumentXML.ATTR_VALUE_OF);
    if (value == null) {
      throw new IllegalArgumentException(//
          "Value attribute '" + //$NON-NLS-1$
              DocumentXML.ATTR_VALUE_OF + //
              "' of tag '" + //$NON-NLS-1$
              DocumentXML.ELEMENT_VALUE + //
              "' cannot be null or an empty string.");//$NON-NLS-1$
    }
    object = this.m_properties.get(value);

    this.__formatObject(object,
        this.__requireCurrentElement(ITextOutput.class));
  }

  /**
   * Get the element on the top of the element stack
   *
   * @param clazz
   *          the required class
   * @return the element on the top of the element stack
   * @param <T>
   *          the required class
   */
  @SuppressWarnings("resource")
  private final <T> T __requireCurrentElement(final Class<T> clazz) {
    final int size;
    final IDocumentElement element;

    if (clazz == null) {
      throw new IllegalArgumentException(//
          "Required element class cannot be null."); //$NON-NLS-1$
    }

    size = this.m_elementStack.size();
    if (size <= 0) {
      throw new IllegalStateException(//
          "There must be at least one document element on the stack, i.e., a 'current' element."); //$NON-NLS-1$
    }

    element = this.m_elementStack.get(size - 1);

    if (element == null) {
      throw new IllegalStateException(//
          "Element on top of the document stack was null. This should never happen."); //$NON-NLS-1$
    }

    if (clazz.isInstance(element)) {
      return clazz.cast(element);
    }

    throw new IllegalStateException(//
        "The current element must be an instance of " + //$NON-NLS-1$
            TextUtils.className(clazz) + //
            " but is instead an instance of "//$NON-NLS-1$
            + TextUtils.className(element.getClass()));
  }

  /**
   * Push an element to the element stack
   *
   * @param element
   *          the element to push
   */
  private final void __pushElement(final IDocumentElement element) {
    if (element == null) {
      throw new IllegalArgumentException(
          "Cannot push null element to element stack."); //$NON-NLS-1$
    }

    this.m_elementStack.add(element);
  }

  /**
   * Pop an element on the top of the element stack
   *
   * @param clazz
   *          the required class
   */
  @SuppressWarnings("resource")
  private final void __popElement(
      final Class<? extends IDocumentElement> clazz) {
    final int size;
    final IDocumentElement element;

    if (clazz == null) {
      throw new IllegalArgumentException(//
          "Required element class cannot be null."); //$NON-NLS-1$
    }

    size = this.m_elementStack.size();
    if (size <= 0) {
      throw new IllegalStateException(//
          "There must be at least one document element to pop on the stack, i.e., a 'current' element."); //$NON-NLS-1$
    }

    element = this.m_elementStack.remove(size - 1);

    if (element == null) {
      throw new IllegalStateException(//
          "Element on top of the document stack was null. This should never happen."); //$NON-NLS-1$
    }

    if (clazz.isInstance(element)) {
      element.close();
      return;
    }

    throw new IllegalStateException(//
        "The element to be closed (the current element on top of the stack) must be an instance of " + //$NON-NLS-1$
            TextUtils.className(clazz) + //
            " but is instead an instance of "//$NON-NLS-1$
            + TextUtils.className(element.getClass()));
  }

  /**
   * Start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_BR}
   * tag. The end does not need to be handled.
   *
   * @param attributes
   *          the attributes
   */
  private final void __startBR(final Attributes attributes) {
    this.__requireCurrentElement(ITextOutput.class).appendLineBreak();
  }

  /**
   * Start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_NBSP}
   * tag. The end does not need to be handled.
   *
   * @param attributes
   *          the attributes
   */
  private final void __startNBSP(final Attributes attributes) {
    this.__requireCurrentElement(ITextOutput.class)
        .appendNonBreakingSpace();
  }

  /**
   * Start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_CALL}
   * tag. The end does not need to be handled.
   *
   * @param attributes
   *          the attributes
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private final void __startCall(final Attributes attributes) {
    final IDocumentCallback<IDocumentElement> callback;
    final String callbackKey;

    callbackKey = DelegatingHandler.getAttributeNormalized(attributes,
        DocumentXML.NAMESPACE, DocumentXML.ATTR_CALL_F);
    if (callbackKey == null) {
      throw new IllegalArgumentException(
          (((//
              "Must specify attribute '" + //$NON-NLS-1$
              DocumentXML.ATTR_CALL_F) + "' to make a call to a callback via '") + //$NON-NLS-1$
              DocumentXML.ELEMENT_CALL)
              + "', i.e., the callback function must be defined.");//$NON-NLS-1$
    }

    callback = ((IDocumentCallback) (this.m_properties.get(callbackKey)));

    if (callback == null) {
      throw new IllegalArgumentException(//
          "The callback function '" + callbackKey + //$NON-NLS-1$
              "' is undefined.");//$NON-NLS-1$
    }

    callback.callback(
        this.__requireCurrentElement(callback.getElementClass()),
        this.m_properties);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_QUOTE}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endQuotes()
   */
  private final void __startQuotes(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IPlainText.class).inQuotes());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_QUOTE}
   * tag
   *
   * @see #__startQuotes(Attributes)
   */
  private final void __endQuotes() {
    this.__popElement(IPlainText.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_BRACE}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endBraces()
   */
  @SuppressWarnings("resource")
  private final void __startBraces(final Attributes attributes) {
    final IDocumentElement element;

    element = this.__requireCurrentElement(IDocumentElement.class);
    if (element instanceof IPlainText) {
      this.__pushElement(((IPlainText) element).inBraces());
    } else {
      if (element instanceof IMath) {
        this.__pushElement(((IMath) element).inBraces());
      } else {
        throw new IllegalStateException(//
            "Only instances of IPlainText or IMath can contain braces elements, but the current element is an instance of " //$NON-NLS-1$
                + TextUtils.className(element.getClass()));
      }
    }
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_BRACE}
   * tag
   *
   * @see #__startBraces(Attributes)
   */
  private final void __endBraces() {
    this.__popElement(IDocumentElement.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_EMPH}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endEmph()
   */
  private final void __startEmph(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IComplexText.class).emphasize());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_EMPH}
   * tag
   *
   * @see #__startEmph(Attributes)
   */
  private final void __endEmph() {
    this.__popElement(IPlainText.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_CODE}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endInlineCode()
   */
  private final void __startInlineCode(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IComplexText.class).inlineCode());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_CODE}
   * tag
   *
   * @see #__startInlineCode(Attributes)
   */
  private final void __endInlineCode() {
    this.__popElement(IText.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_SUP}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endSuper()
   */
  @SuppressWarnings("resource")
  private final void __startSuper(final Attributes attributes) {
    final IText element;

    element = this.__requireCurrentElement(IComplexText.class);
    if (element instanceof IComplexText) {
      this.__pushElement(((IComplexText) element).superscript());
    } else {
      if (element instanceof IMathName) {
        this.__pushElement(((IMathName) element).superscript());
      } else {
        throw new IllegalStateException(//
            "Only IMathName or IComplexText can contain superscript elements, but the current element is an instance of " //$NON-NLS-1$
                + TextUtils.className(element.getClass()));
      }
    }
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_SUP}
   * tag
   *
   * @see #__startSuper(Attributes)
   */
  private final void __endSuper() {
    this.__popElement(IText.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_SUP}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endSub()
   */
  @SuppressWarnings("resource")
  private final void __startSub(final Attributes attributes) {
    final IText element;

    element = this.__requireCurrentElement(IComplexText.class);
    if (element instanceof IComplexText) {
      this.__pushElement(((IComplexText) element).subscript());
    } else {
      if (element instanceof IMathName) {
        this.__pushElement(((IMathName) element).subscript());
      } else {
        throw new IllegalStateException(//
            "Only IMathName or IComplexText can contain subscript elements, but the current element is an instance of " //$NON-NLS-1$
                + TextUtils.className(element.getClass()));
      }
    }
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_SUP}
   * tag
   *
   * @see #__startSub(Attributes)
   */
  private final void __endSub() {
    this.__popElement(IText.class);
  }

  /**
   * Start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_CITE}
   * tag. The end does not need to be handled.
   *
   * @param attr
   *          the attributes
   */
  @SuppressWarnings("unused")
  private final void __startCite(final Attributes attr) {
    ESequenceMode sequenceMode;
    ETextCase textCase;
    ECitationMode citationMode;

    sequenceMode = DocumentXML._parseSequenceMode(DelegatingHandler
        .getAttributeNormalized(attr, DocumentXML.NAMESPACE,
            DocumentXML.ATTR_SEQUENCE_MODE), ESequenceMode.COMMA);

    textCase = DocumentXML._parseTextCase(
        //
        DelegatingHandler.getAttributeNormalized(attr,
            DocumentXML.NAMESPACE, DocumentXML.ATTR_TEXT_CASE),
        ETextCase.IN_SENTENCE);

    citationMode = DocumentXML._parseCitationMode(
        //
        DelegatingHandler.getAttributeNormalized(attr,
            DocumentXML.NAMESPACE, DocumentXML.ATTR_CITATION_MODE),
        ECitationMode.ID);

    new BibliographyXMLHandler(this, this.__requireCurrentElement(
        IComplexText.class).cite(citationMode, textCase, sequenceMode));
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_SECTION}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endSection()
   */
  @SuppressWarnings("resource")
  private final void __startSection(final Attributes attributes) {
    ILabel label;
    ISection sec;
    String prop;
    Object obj;

    prop = DelegatingHandler.getAttributeNormalized(attributes,
        DocumentXML.NAMESPACE, DocumentXML.ATTR_LABEL);

    label = null;
    if (prop != null) {

      obj = this.m_properties.get(prop);

      if (obj != null) {
        if (!(obj instanceof ILabel)) {
          throw new IllegalArgumentException(//
              "The label attribute of section must point to a property which is either null or an instance of ILabel, but the value of '" //$NON-NLS-1$
                  + prop + //
                  "' is an instance of "//$NON-NLS-1$
                  + TextUtils.className(prop.getClass()));
        }
        label = ((ILabel) (obj));
      } else {
        label = ELabelType.AUTO;
      }
    }

    sec = this.__requireCurrentElement(ISectionContainer.class).section(
        label);
    this.__pushElement(sec);

    if (prop != null) {
      this.m_properties.put(prop, sec.getLabel());
    }
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_SECTION}
   * tag
   *
   * @see #__startSection(Attributes)
   */
  private final void __endSection() {
    this.__popElement(ISection.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_SECTION_TITLE}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endSectionTitle()
   */
  private final void __startSectionTitle(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(ISection.class).title());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_SECTION_TITLE}
   * tag
   *
   * @see #__startSectionTitle(Attributes)
   */
  private final void __endSectionTitle() {
    this.__popElement(IComplexText.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_SECTION_BODY}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endSectionBody()
   */
  private final void __startSectionBody(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(ISection.class).body());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_SECTION_BODY}
   * tag
   *
   * @see #__startSectionBody(Attributes)
   */
  private final void __endSectionBody() {
    this.__popElement(ISectionBody.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_REF}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endReference()
   */
  private final void __startReference(final Attributes attributes) {
    this.m_labels = new ArrayList<>();

    this.m_formatTextCase = DocumentXML._parseTextCase(DelegatingHandler
        .getAttributeNormalized(attributes, DocumentXML.NAMESPACE,
            DocumentXML.ATTR_TEXT_CASE), ETextCase.IN_SENTENCE);
    this.m_formatSequenceMode = DocumentXML._parseSequenceMode(
        DelegatingHandler.getAttributeNormalized(attributes,
            DocumentXML.NAMESPACE, DocumentXML.ATTR_SEQUENCE_MODE),
        ESequenceMode.AND);
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_REF}
   * tag
   *
   * @see #__startReference(Attributes)
   */
  private final void __endReference() {
    final ILabel[] array;
    final int size;
    final ETextCase textCase;
    final ESequenceMode seqMode;

    textCase = this.m_formatTextCase;
    this.m_formatTextCase = null;
    seqMode = this.m_formatSequenceMode;
    this.m_formatSequenceMode = null;

    if (this.m_labels == null) {
      throw new IllegalStateException(//
          "Labels must not be null at end of reference tag."); //$NON-NLS-1$
    }

    size = this.m_labels.size();
    if (size <= 0) {
      throw new IllegalArgumentException(//
          "At least one label must be defined in reference tag."); //$NON-NLS-1$
    }
    array = this.m_labels.toArray(new ILabel[size]);
    this.m_labels = null;

    this.__requireCurrentElement(IComplexText.class).reference(textCase,
        seqMode, array);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_OL}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endEnumerate()
   */
  private final void __startEnumerate(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IStructuredText.class).enumeration());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_OL}
   * tag
   *
   * @see #__startEnumerate(Attributes)
   */
  private final void __endEnumerate() {
    this.__popElement(IList.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_UL}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endItemize()
   */
  private final void __startItemize(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IStructuredText.class).itemization());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_UL}
   * tag
   *
   * @see #__startItemize(Attributes)
   */
  private final void __endItemize() {
    this.__popElement(IList.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_LI}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endItem()
   */
  private final void __startItem(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IList.class).item());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_LI}
   * tag
   *
   * @see #__startItem(Attributes)
   */
  private final void __endItem() {
    this.__popElement(IStructuredText.class);
  }

  /**
   * Load formatting attributes: This will set the
   * {@link #m_formatTextCase text case} and the {@link #m_formatFormatter
   * formatter}.
   *
   * @param atts
   *          the attributes
   */
  @SuppressWarnings("unused")
  private final void __loadFormatAttrs(final Attributes atts) {
    String s;
    DecimalFormatSymbols x;

    this.m_formatFormatter = null;
    this.m_formatTextCase = ETextCase.IN_SENTENCE;

    s = DelegatingHandler.getAttributeNormalized(atts,
        DocumentXML.NAMESPACE, DocumentXML.ATTR_FORMAT_PROPERTY);
    if (s != null) {

      this.m_formatFormatter = this.m_properties.get(s);

      if (this.m_formatFormatter == null) {
        throw new IllegalArgumentException(//
            "Formatter property '" + //$NON-NLS-1$
                DocumentXML.ATTR_FORMAT_PROPERTY + "' has value '" + s + //$NON-NLS-1$
                "', but no formatter is specified as property under this key."); //$NON-NLS-1$
      }
    } else {
      s = DelegatingHandler.getAttributeNormalized(atts,
          DocumentXML.NAMESPACE, DocumentXML.ATTR_FORMAT_INSTANCE);
      if (s != null) {
        this.m_formatFormatter = this.m_cache.get(s);
        if (this.m_formatFormatter == null) {
          try {
            this.m_formatFormatter = ReflectionUtils
                .getInstanceByName(Object.class, s,
                    DocumentXMLHandler.NUMBER_FORMAT_PREFIXES);
          } catch (final ReflectiveOperationException y) {
            throw new RuntimeException(//
                "Error while trying to obtain formatter '" + s //$NON-NLS-1$
                    + "' specified as attribute '" + //$NON-NLS-1$
                    DocumentXML.ATTR_FORMAT_INSTANCE
                    + "' could not be found.");//$NON-NLS-1$
          }
          if (this.m_formatFormatter != null) {
            this.m_cache.put(s, this.m_formatFormatter);
          } else {
            throw new RuntimeException(//
                "The formatter '" + s //$NON-NLS-1$
                    + "' specified as attribute '" + //$NON-NLS-1$
                    DocumentXML.ATTR_FORMAT_INSTANCE
                    + "' could not be found.");//$NON-NLS-1$
          }
        }
      } else {
        s = DelegatingHandler.getAttributeNormalized(atts,
            DocumentXML.NAMESPACE, DocumentXML.ATTR_FORMAT_PATTERN);
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

    this.m_formatTextCase = DocumentXML._parseTextCase(DelegatingHandler
        .getAttributeNormalized(atts, DocumentXML.NAMESPACE,
            DocumentXML.ATTR_TEXT_CASE), ETextCase.IN_SENTENCE);
  }

  /**
   * Append an object to the given text output destination
   *
   * @param object
   *          the object to be flushed
   * @param dest
   *          the destination
   */
  private final void __formatObject(final Object object,
      final ITextOutput dest) {
    final Object format;
    final ETextCase textCase;
    final double d;
    final long l;
    final int i;
    final short s;
    final byte b;
    final float f;
    String formatted;

    this.m_formatParser = null;
    format = this.m_formatFormatter;
    this.m_formatFormatter = null;
    if (this.m_formatTextCase == null) {
      textCase = ETextCase.IN_SENTENCE;
    } else {
      textCase = this.m_formatTextCase;
      this.m_formatTextCase = null;
    }

    if (object == null) {
      throw new IllegalArgumentException(//
          "Object to format with formatter '" + //$NON-NLS-1$
              format + "' and text case '" + textCase//$NON-NLS-1$
              + "' cannot be null");//$NON-NLS-1$
    }

    if (object instanceof Number) {

      if (object instanceof Integer) {
        i = ((Integer) object).intValue();
        if (format instanceof NumberAppender) {
          ((NumberAppender) (format)).appendTo(i, textCase, dest);
          return;
        }
        if (format instanceof DecimalFormat) {
          synchronized (format) {
            formatted = ((DecimalFormat) (format)).format(i);
          }
          if (formatted == null) {
            throw new IllegalArgumentException(//
                "Formatting int value " + i + //$NON-NLS-1$
                    " with DecimalFormat " + format + //$NON-NLS-1$
                    " yields null?");//$NON-NLS-1$
          }
          dest.append(formatted);
          return;
        }

        if (format == null) {
          dest.append(i);
          return;
        }

        throw new IllegalArgumentException(//
            "A formatter able to deal with int numbers is required, such as a NumberAppender or DecimalFormat. Null would also be ok, in which case we print the number directly. However, the formatter of type " //$NON-NLS-1$
                + TextUtils.className(format.getClass()) + //
                " is not suitable for this purpose.");//$NON-NLS-1$
      }

      if (object instanceof Long) {
        l = ((Long) object).longValue();
        if (format instanceof NumberAppender) {
          ((NumberAppender) (format)).appendTo(l, textCase, dest);
          return;
        }
        if (format instanceof DecimalFormat) {
          synchronized (format) {
            formatted = ((DecimalFormat) (format)).format(l);
          }
          if (formatted == null) {
            throw new IllegalArgumentException(//
                "Formatting long value " + l + //$NON-NLS-1$
                    " with DecimalFormat " + format + //$NON-NLS-1$
                    " yields null?");//$NON-NLS-1$
          }
          dest.append(formatted);
          return;
        }
        if (format == null) {
          dest.append(l);
          return;
        }
        throw new IllegalArgumentException(//
            "A formatter able to deal with long numbers is required, such as a NumberAppender or DecimalFormat. However, the formatter of type " //$NON-NLS-1$
                + TextUtils.className(format.getClass()) + //
                " is not suitable for this purpose.");//$NON-NLS-1$
      }

      if (object instanceof Double) {
        d = ((Double) object).doubleValue();
        if (format instanceof NumberAppender) {
          ((NumberAppender) (format)).appendTo(d, textCase, dest);
          return;
        }
        if (format instanceof DecimalFormat) {
          synchronized (format) {
            formatted = ((DecimalFormat) (format)).format(d);
          }
          if (formatted == null) {
            throw new IllegalArgumentException(//
                "Formatting double value " + d + //$NON-NLS-1$
                    " with DecimalFormat " + format + //$NON-NLS-1$
                    " yields null?");//$NON-NLS-1$
          }
          dest.append(formatted);
          return;
        }
        if (format == null) {
          dest.append(d);
          return;
        }
        throw new IllegalArgumentException(//
            "A formatter able to deal with double numbers is required, such as a NumberAppender or DecimalFormat. However, the formatter of type " //$NON-NLS-1$
                + TextUtils.className(format.getClass()) + //
                " is not suitable for this purpose.");//$NON-NLS-1$
      }

      if (object instanceof Float) {
        f = ((Float) object).floatValue();
        if (format instanceof NumberAppender) {
          ((NumberAppender) (format)).appendTo(f, textCase, dest);
          return;
        }
        if (format instanceof DecimalFormat) {
          synchronized (format) {
            formatted = ((DecimalFormat) (format)).format(f);
          }
          if (formatted == null) {
            throw new IllegalArgumentException(//
                "Formatting float value " + f + //$NON-NLS-1$
                    " with DecimalFormat " + format + //$NON-NLS-1$
                    " yields null?");//$NON-NLS-1$
          }
          dest.append(formatted);
          return;
        }
        if (format == null) {
          dest.append(f);
          return;
        }
        throw new IllegalArgumentException(//
            "A formatter able to deal with float numbers is required, such as a NumberAppender or DecimalFormat. However, the formatter of type " //$NON-NLS-1$
                + TextUtils.className(format.getClass()) + //
                " is not suitable for this purpose.");//$NON-NLS-1$
      }

      if (object instanceof Short) {
        s = ((Short) object).shortValue();
        if (format instanceof NumberAppender) {
          ((NumberAppender) (format)).appendTo(s, textCase, dest);
          return;
        }
        if (format instanceof DecimalFormat) {
          synchronized (format) {
            formatted = ((DecimalFormat) (format)).format(s);
          }
          if (formatted == null) {
            throw new IllegalArgumentException(//
                "Formatting short value " + s + //$NON-NLS-1$
                    " with DecimalFormat " + format + //$NON-NLS-1$
                    " yields null?");//$NON-NLS-1$
          }
          dest.append(formatted);
          return;
        }
        if (format == null) {
          dest.append(s);
          return;
        }
        throw new IllegalArgumentException(//
            "A formatter able to deal with short numbers is required, such as a NumberAppender or DecimalFormat. However, the formatter of type " //$NON-NLS-1$
                + TextUtils.className(format.getClass()) + //
                " is not suitable for this purpose.");//$NON-NLS-1$
      }

      if (object instanceof Byte) {
        b = ((Byte) object).byteValue();
        if (format instanceof NumberAppender) {
          ((NumberAppender) (format)).appendTo(b, textCase, dest);
          return;
        }
        if (format instanceof DecimalFormat) {
          synchronized (format) {
            formatted = ((DecimalFormat) (format)).format(b);
          }
          if (formatted == null) {
            throw new IllegalArgumentException(//
                "Formatting byte value " + b + //$NON-NLS-1$
                    " with DecimalFormat " + format + //$NON-NLS-1$
                    " yields null?");//$NON-NLS-1$
          }
          dest.append(formatted);
          return;
        }
        if (format == null) {
          dest.append(b);
          return;
        }
        throw new IllegalArgumentException(//
            "A formatter able to deal with byte numbers is required, such as a NumberAppender or DecimalFormat. However, the formatter of type " //$NON-NLS-1$
                + TextUtils.className(format.getClass()) + //
                " is not suitable for this purpose.");//$NON-NLS-1$
      }

      // deal with non-standard numbers

      if (format == null) {
        dest.append(object);
        return;
      }

      d = ((Number) object).doubleValue();
      if (format instanceof NumberAppender) {
        ((NumberAppender) (format)).appendTo(d, textCase, dest);
        return;
      }
      if (format instanceof DecimalFormat) {
        synchronized (format) {
          formatted = ((DecimalFormat) (format)).format(d);
        }
        if (formatted == null) {
          throw new IllegalArgumentException(//
              "Formatting double value " + d + //$NON-NLS-1$
                  " with DecimalFormat " + format + //$NON-NLS-1$
                  " yields null?");//$NON-NLS-1$
        }
        dest.append(formatted);
        return;
      }

      throw new IllegalArgumentException(//
          "A formatter able to deal with numbers of type " + //$NON-NLS-1$
              TextUtils.className(object.getClass()) + //
              " is required, such as a NumberAppender or DecimalFormat. Null would also be ok, in which case we print the number directly. However, the formatter of type " //$NON-NLS-1$
              + TextUtils.className(format.getClass()) + //
              " is not suitable for this purpose.");//$NON-NLS-1$
    }

    if (format != null) {
      throw new IllegalArgumentException(//
          "Object of type " + //$NON-NLS-1$
              TextUtils.className(object.getClass())//
              + " cannot be processed by formatter of type " + //$NON-NLS-1$
              TextUtils.className(format.getClass()));
    }

    if (object instanceof ITextable) {
      ((ITextable) object).toText(dest);
      return;
    }

    if (object instanceof String) {
      dest.append((String) object);
      return;
    }
    if (object instanceof char[]) {
      dest.append((char[]) object);
      return;
    }
    if (object instanceof CharSequence) {
      dest.append((CharSequence) object);
      return;
    }

    if (object instanceof Character) {
      dest.append(((Character) object).charValue());
      return;
    }
    if (object instanceof Boolean) {
      dest.append(((Boolean) object).booleanValue());
      return;
    }

    dest.append(object);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_EQUATION}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endEquation()
   */
  @SuppressWarnings("resource")
  private final void __startEquation(final Attributes attributes) {
    ILabel label;
    IEquation sec;
    String prop;
    Object obj;

    prop = DelegatingHandler.getAttributeNormalized(attributes,
        DocumentXML.NAMESPACE, DocumentXML.ATTR_LABEL);

    label = null;
    if (prop != null) {

      obj = this.m_properties.get(prop);

      if (obj != null) {
        if (!(obj instanceof ILabel)) {
          throw new IllegalArgumentException(//
              "The label attribute of equation must point to a property which is either null or an instance of ILabel, but the value of '" //$NON-NLS-1$
                  + prop + //
                  "' is an instance of "//$NON-NLS-1$
                  + TextUtils.className(prop.getClass()));
        }
        label = ((ILabel) (obj));
      } else {
        label = ELabelType.AUTO;
      }
    }

    sec = this.__requireCurrentElement(ISectionBody.class).equation(label);
    this.__pushElement(sec);

    if (prop != null) {
      this.m_properties.put(prop, sec.getLabel());
    }
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_EQUATION}
   * tag
   *
   * @see #__startEquation(Attributes)
   */
  private final void __endEquation() {
    this.__popElement(IEquation.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_INLINE_MATH}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endInlineMath()
   */
  private final void __startInlineMath(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IComplexText.class).inlineMath());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_INLINE_MATH}
   * tag
   *
   * @see #__startInlineMath(Attributes)
   */
  private final void __endInlineMath() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_ABS}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathAbs()
   */
  private final void __startMathAbs(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).abs());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_ABS}
   * tag
   *
   * @see #__startMathAbs(Attributes)
   */
  private final void __endMathAbs() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_ABS}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathAdd()
   */
  private final void __startMathAdd(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).add());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_ABS}
   * tag
   *
   * @see #__startMathAdd(Attributes)
   */
  private final void __endMathAdd() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_COMP}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathComp()
   */
  private final void __startMathComp(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).compare(
        DocumentXML._parseComparisonOperator(DelegatingHandler
            .getAttributeNormalized(attributes, DocumentXML.NAMESPACE,
                DocumentXML.ATTR_MATH_COMP_OP))));
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_COMP}
   * tag
   *
   * @see #__startMathComp(Attributes)
   */
  private final void __endMathComp() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_COS}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathCos()
   */
  private final void __startMathCos(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).cos());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_COS}
   * tag
   *
   * @see #__startMathCos(Attributes)
   */
  private final void __endMathCos() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_DIV}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathDiv()
   */
  private final void __startMathDiv(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).div());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_DIV}
   * tag
   *
   * @see #__startMathDiv(Attributes)
   */
  private final void __endMathDiv() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_DIV_INLINE}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathDivInline()
   */
  private final void __startMathDivInline(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).divInline());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_DIV_INLINE}
   * tag
   *
   * @see #__startMathDivInline(Attributes)
   */
  private final void __endMathDivInline() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_FACTORIAL}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathFactorial()
   */
  private final void __startMathFactorial(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).factorial());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_FACTORIAL}
   * tag
   *
   * @see #__startMathFactorial(Attributes)
   */
  private final void __endMathFactorial() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_LD}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathLd()
   */
  private final void __startMathLd(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).ld());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_LD}
   * tag
   *
   * @see #__startMathLd(Attributes)
   */
  private final void __endMathLd() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_LG}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathLg()
   */
  private final void __startMathLg(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).lg());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_LG}
   * tag
   *
   * @see #__startMathLg(Attributes)
   */
  private final void __endMathLg() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_LN}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathLn()
   */
  private final void __startMathLn(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).ln());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_LN}
   * tag
   *
   * @see #__startMathLn(Attributes)
   */
  private final void __endMathLn() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_LOG}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathLog()
   */
  private final void __startMathLog(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).log());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_LOG}
   * tag
   *
   * @see #__startMathLog(Attributes)
   */
  private final void __endMathLog() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_MOD}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathMod()
   */
  private final void __startMathMod(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).mod());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_MOD}
   * tag
   *
   * @see #__startMathMod(Attributes)
   */
  private final void __endMathMod() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_MUL}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathMul()
   */
  private final void __startMathMul(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).mul());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_MUL}
   * tag
   *
   * @see #__startMathMul(Attributes)
   */
  private final void __endMathMul() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_NEGATE}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathNegate()
   */
  private final void __startMathNegate(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).negate());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_NEGATE}
   * tag
   *
   * @see #__startMathNegate(Attributes)
   */
  private final void __endMathNegate() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_POW}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathPow()
   */
  private final void __startMathPow(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).pow());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_POW}
   * tag
   *
   * @see #__startMathPow(Attributes)
   */
  private final void __endMathPow() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_ROOT}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathRoot()
   */
  private final void __startMathRoot(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).root());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_ROOT}
   * tag
   *
   * @see #__startMathRoot(Attributes)
   */
  private final void __endMathRoot() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_SIN}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathSin()
   */
  private final void __startMathSin(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).sin());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_SIN}
   * tag
   *
   * @see #__startMathSin(Attributes)
   */
  private final void __endMathSin() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_SQRT}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathSqrt()
   */
  private final void __startMathSqrt(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).sqrt());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_SQRT}
   * tag
   *
   * @see #__startMathSqrt(Attributes)
   */
  private final void __endMathSqrt() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_SUB}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathSub()
   */
  private final void __startMathSub(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).sub());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_SUB}
   * tag
   *
   * @see #__startMathSub(Attributes)
   */
  private final void __endMathSub() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_TAN}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathTan()
   */
  private final void __startMathTan(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).tan());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_TAN}
   * tag
   *
   * @see #__startMathTan(Attributes)
   */
  private final void __endMathTan() {
    this.__popElement(IMath.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_NAME}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathName()
   */
  private final void __startMathName(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).name());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_NAME}
   * tag
   *
   * @see #__startMathName(Attributes)
   */
  private final void __endMathName() {
    this.__popElement(IMathName.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_NUMBER}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathNumber()
   */
  private final void __startMathNumber(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).number());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_NUMBER}
   * tag
   *
   * @see #__startMathNumber(Attributes)
   */
  private final void __endMathNumber() {
    this.__popElement(IText.class);
  }

  /**
   * start the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_TEXT}
   * tag
   *
   * @param attributes
   *          the attributes
   * @see #__endMathText()
   */
  private final void __startMathText(final Attributes attributes) {
    this.__pushElement(//
    this.__requireCurrentElement(IMath.class).text());
  }

  /**
   * end the
   * {@value org.optimizationBenchmarking.utils.document.template.DocumentXML#ELEMENT_MATH_TEXT}
   * tag
   *
   * @see #__startMathText(Attributes)
   */
  private final void __endMathText() {
    this.__popElement(IComplexText.class);
  }

}
