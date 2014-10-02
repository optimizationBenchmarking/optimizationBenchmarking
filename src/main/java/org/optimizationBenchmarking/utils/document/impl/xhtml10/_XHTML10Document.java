package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.Set;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.impl.abstr.Document;
import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.document.object.PathEntry;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.graphics.style.font.FontStyle;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.path.PathUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the XHTML document */
final class _XHTML10Document extends Document {

  /** the style sheet to be used in all cases, except for printing */
  private static final String CSS_DEFAULT = "default.css";//$NON-NLS-1$

  /** the style sheet to be used for printing */
  private static final String CSS_PRINT = "print.css";//$NON-NLS-1$

  /** the xml header start: charset must follow */
  private static final char[] XML_HEADER_BEGIN = { '<', '?', 'x', 'm',
      'l', ' ', 'v', 'e', 'r', 's', 'i', 'o', 'n', '=', '"', '1', '.',
      '0', '"', ' ', 'e', 'n', 'c', 'o', 'd', 'i', 'n', 'g', '=', '"' };
  /** the xml header end: newline must follow */
  private static final char[] XML_HEADER_END = { '"', '?', '>' };

  /** the doc type */
  private static final char[] DOC_TYPE = { '<', '!', 'D', 'O', 'C', 'T',
      'Y', 'P', 'E', ' ', 'h', 't', 'm', 'l', ' ', 'P', 'U', 'B', 'L',
      'I', 'C', ' ', '"', '-', '/', '/', 'W', '3', 'C', '/', '/', 'D',
      'T', 'D', ' ', 'X', 'H', 'T', 'M', 'L', ' ', '1', '.', '0', ' ',
      'S', 't', 'r', 'i', 'c', 't', '/', '/', 'E', 'N', '"', ' ', '"',
      'h', 't', 't', 'p', ':', '/', '/', 'w', 'w', 'w', '.', 'w', '3',
      '.', 'o', 'r', 'g', '/', 'T', 'R', '/', 'x', 'h', 't', 'm', 'l',
      '1', '/', 'D', 'T', 'D', '/', 'x', 'h', 't', 'm', 'l', '1', '-',
      's', 't', 'r', 'i', 'c', 't', '.', 'd', 't', 'd', '"', '>' };

  /** the doc type, charset must follow */
  private static final char[] HTML_BEGIN = { '<', 'h', 't', 'm', 'l', ' ',
      'x', 'm', 'l', 'n', 's', '=', '"', 'h', 't', 't', 'p', ':', '/',
      '/', 'w', 'w', 'w', '.', 'w', '3', '.', 'o', 'r', 'g', '/', '1',
      '9', '9', '9', '/', 'x', 'h', 't', 'm', 'l', '"', ' ', 'l', 'a',
      'n', 'g', '=', '"', 'e', 'n', '"', ' ', 'x', 'm', 'l', ':', 'l',
      'a', 'n', 'g', '=', '"', 'e', 'n', '"', '>' };
  /** the head begin */
  private static final char[] HEAD_BEGIN = { '<', 'h', 'e', 'a', 'd', '>' };

  /** the head begin */
  private static final char[] META_CHARSET_BEGIN = { '<', 'm', 'e', 't',
      'a', ' ', 'h', 't', 't', 'p', '-', 'e', 'q', 'u', 'i', 'v', '=',
      '"', 'c', 'o', 'n', 't', 'e', 'n', 't', '-', 't', 'y', 'p', 'e',
      '"', ' ', 'c', 'o', 'n', 't', 'e', 'n', 't', '=', '"', 'a', 'p',
      'p', 'l', 'i', 'c', 'a', 't', 'i', 'o', 'n', '/', 'x', 'h', 't',
      'm', 'l', '+', 'x', 'm', 'l', ';', ' ', 'c', 'h', 'a', 'r', 's',
      'e', 't', '=' };

  /** the meta style type */
  private static final char[] META_STYLE_TYPE = { '<', 'm', 'e', 't', 'a',
      ' ', 'h', 't', 't', 'p', '-', 'e', 'q', 'u', 'i', 'v', '=', '"',
      'c', 'o', 'n', 't', 'e', 'n', 't', '-', 's', 't', 'y', 'l', 'e',
      '-', 't', 'y', 'p', 'e', '"', ' ', 'c', 'o', 'n', 't', 'e', 'n',
      't', '=', '"', 't', 'e', 'x', 't', '/', 'c', 's', 's', '"', ' ',
      '/', '>' };

  /** the default css link */
  private static final char[] DEFAULT_CSS_LINK = { '<', 'l', 'i', 'n',
      'k', ' ', 'r', 'e', 'l', '=', '"', 's', 't', 'y', 'l', 'e', 's',
      'h', 'e', 'e', 't', '"', ' ', 'm', 'e', 'd', 'i', 'a', '=', '"',
      'a', 'l', 'l', '"', ' ', 'h', 'r', 'e', 'f', '=', '"' };
  /** the print css link */
  private static final char[] PRINT_CSS_LINK = { '<', 'l', 'i', 'n', 'k',
      ' ', 'r', 'e', 'l', '=', '"', 's', 't', 'y', 'l', 'e', 's', 'h',
      'e', 'e', 't', '"', ' ', 'm', 'e', 'd', 'i', 'a', '=', '"', 'p',
      'r', 'i', 'n', 't', '"', ' ', 'h', 'r', 'e', 'f', '=', '"', };

  /**
   * Create a document.
   * 
   * @param driver
   *          the document driver
   * @param docPath
   *          the path to the document
   * @param listener
   *          the object listener the object listener
   */
  _XHTML10Document(final XHTML10Driver driver, final Path docPath,
      final IObjectListener listener) {
    super(driver, docPath, listener);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;
    StreamEncoding<?, ?> e;
    String s;

    super.onOpen();

    this.addPath(new PathEntry(XHTML10Driver.XHTML_MAIN_FILE, this
        .getDocumentPath()));

    out = this.getTextOutput();
    out.append(_XHTML10Document.XML_HEADER_BEGIN);

    e = StreamEncoding.getStreamEncoding(out);
    if ((e == null) || (e == StreamEncoding.TEXT)
        || (e == StreamEncoding.BINARY)) {
      e = StreamEncoding.UTF_8;
    }
    s = e.name();
    out.append(s);
    out.append(_XHTML10Document.XML_HEADER_END);
    out.appendLineBreak();

    out.append(_XHTML10Document.DOC_TYPE);
    out.appendLineBreak();

    out.append(_XHTML10Document.HTML_BEGIN);
    out.append(_XHTML10Document.HEAD_BEGIN);
    out.append(_XHTML10Document.META_CHARSET_BEGIN);
    out.append(s);
    out.append(XHTML10Driver.EMPTY_ATTRIB_TAG_END);

    out.append(_XHTML10Document.META_STYLE_TYPE);

    out.append(_XHTML10Document.DEFAULT_CSS_LINK);
    out.append(_XHTML10Document.CSS_DEFAULT);
    out.append(XHTML10Driver.EMPTY_ATTRIB_TAG_END);

    out.append(_XHTML10Document.PRINT_CSS_LINK);
    out.append(_XHTML10Document.CSS_PRINT);
    out.append(XHTML10Driver.EMPTY_ATTRIB_TAG_END);
  }

  /** {@inheritDoc} */
  @Override
  protected void postProcess(final Set<IStyle> usedStyles,
      final ArrayListView<PathEntry> paths) {
    Path path;
    String s;

    try {
      for (final String name : new String[] {
          _XHTML10Document.CSS_DEFAULT, _XHTML10Document.CSS_PRINT }) {
        path = PathUtils.normalize(this.getDocumentFolder().resolve(name));
        try (final OutputStream os = PathUtils.openOutputStream(path)) {
          try (final OutputStreamWriter osw = new OutputStreamWriter(os)) {
            try (final BufferedWriter bw = new BufferedWriter(osw)) {
              this.__createStyles(bw, usedStyles);

              try (final InputStream is = _XHTML10Document.class
                  .getResourceAsStream(name)) {
                try (final InputStreamReader isr = new InputStreamReader(
                    is)) {
                  try (final BufferedReader br = new BufferedReader(isr)) {
                    while ((s = br.readLine()) != null) {
                      s = TextUtils.prepare(s);
                      if (s != null) {
                        bw.newLine();
                        bw.write(s);
                      }
                    }
                  }
                }
              }
            }
          }
        } catch (final Throwable t) {
          ErrorUtils.throwAsRuntimeException(t);
        }

        this.addPath(new PathEntry(XHTML10Driver.CSS_STYLE_FILE, path));
      }
    } finally {
      super.postProcess(usedStyles, paths);
    }
  }

  /**
   * write a given style
   * 
   * @param fs
   *          the style
   * @param name
   *          the css name
   * @param bw
   *          the writer to write to
   * @param isDefault
   *          {@code true} if the style is default unset attributes are
   *          normal, not inherit
   * @param useSize
   *          should we use the size?
   * @throws IOException
   *           if i/o fails
   */
  private static final void __writeFont(final FontStyle fs,
      final String name, final BufferedWriter bw, final boolean isDefault,
      final boolean useSize) throws IOException {
    boolean first;

    bw.newLine();
    bw.write(name);
    bw.write(' ');
    bw.write('{');

    if (isDefault && useSize) {
      bw.newLine();
      bw.write("font-size:"); //$NON-NLS-1$
      bw.write(Integer.toString(fs.getSize()));
      bw.write(';');
    }

    bw.newLine();
    bw.write("font-family:"); //$NON-NLS-1$
    first = true;
    for (final String s : fs.getFaceChoices()) {
      if (first) {
        first = false;
      } else {
        bw.write(',');
      }
      bw.write('\'');
      bw.write(s);
      bw.write('\'');
    }
    bw.write(';');

    bw.newLine();
    bw.write("font-variant:normal;");//$NON-NLS-1$

    bw.newLine();
    bw.write("text-transform:none;");//$NON-NLS-1$

    bw.newLine();
    bw.write(fs.isBold() ? "font-weight:bold;" : //$NON-NLS-1$
        (isDefault ? "font-weight:normal;" : //$NON-NLS-1$
            "font-weight:inherit;"));//$NON-NLS-1$

    bw.newLine();
    bw.write(fs.isItalic() ? "font-style:italic;" : //$NON-NLS-1$
        (isDefault ? "font-style:normal;" : //$NON-NLS-1$
            "font-style:inherit;"));//$NON-NLS-1$

    bw.newLine();
    bw.write(fs.isUnderlined() ? "text-decoration:underlined;" : //$NON-NLS-1$
        (isDefault ? "text-decoration:normal;" : //$NON-NLS-1$
            "text-decoration:inherit;"));//$NON-NLS-1$

    bw.newLine();
    bw.write('}');
  }

  /**
   * create the styles
   * 
   * @param bw
   *          the writer
   * @param usedStyles
   *          the styles
   * @throws IOException
   *           if i/o fails
   */
  private final void __createStyles(final BufferedWriter bw,
      final Set<IStyle> usedStyles) throws IOException {
    final FontStyle def;
    FontStyle fs;

    def = this.getStyles().getDefaultFont();
    for (final IStyle s : usedStyles) {
      if (s instanceof FontStyle) {
        fs = ((FontStyle) s);

        if (fs == def) {
          _XHTML10Document.__writeFont(fs, "body", bw, true, true);//$NON-NLS-1$
          _XHTML10Document.__writeFont(fs, ('.' + fs.getID()), bw, true,
              false);
        } else {
          _XHTML10Document.__writeFont(fs, ('.' + fs.getID()), bw, false,
              false);
        }

      }
    }
  }
}
