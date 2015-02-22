package org.optimizationBenchmarking.utils.document.impl.latex;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.ImmutableAssociation;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.impl.abstr.Document;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorStyle;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.text.transformations.NormalCharTransformer;
import org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType;
import org.optimizationBenchmarking.utils.tools.impl.latex.LaTeX;
import org.optimizationBenchmarking.utils.tools.impl.latex.LaTeXJob;
import org.optimizationBenchmarking.utils.tools.impl.latex.LaTeXJobBuilder;

/** the LaTeX document */
final class _LaTeXDocument extends Document {

  /** the figure seris package */
  private static final String FIGURE_SERIES_PACKAGE = "figureSeries.sty"; //$NON-NLS-1$

  /** the default document class */
  private static final char[] DOCUMENT_CLASS = { '\\', 'd', 'o', 'c', 'u',
      'm', 'e', 'n', 't', 'c', 'l', 'a', 's', 's', };

  /** the default document class */
  private static final char[] REQUIRE_PACKAGE = { '\\', 'R', 'e', 'q',
      'u', 'i', 'r', 'e', 'P', 'a', 'c', 'k', 'a', 'g', 'e', };

  /** end the document */
  private static final char[] DOCUMENT_END = { '\\', 'e', 'n', 'd', '{',
      'd', 'o', 'c', 'u', 'm', 'e', 'n', 't', '}' };
  /** end the input */
  private static final char[] INPUT_END = { '\\', 'e', 'n', 'd', 'i', 'n',
      'p', 'u', 't' };
  /** the first part of the include graphics call */
  private static final char[] INCLUDE_GRAPHICS_1 = { '\\', 'i', 'n', 'c',
      'l', 'u', 'd', 'e', 'g', 'r', 'a', 'p', 'h', 'i', 'c', 's', '[',
      'k', 'e', 'e', 'p', 'a', 's', 'p', 'e', 'c', 't', 'r', 'a', 't',
      'i', 'o', ',', 'w', 'i', 'd', 't', 'h', '=' };
  /** the second part of the include graphics call */
  private static final char[] INCLUDE_GRAPHICS_2 = { ',', 'h', 'e', 'i',
      'g', 'h', 't', '=' };

  /** the required separator */
  private static final String REQUIRED_SEPARATOR = "/"; //$NON-NLS-1$

  /**
   * should we try to compile the document to pdf after it as has been
   * created ?
   */
  private final boolean m_compile;
  /** the document class */
  final LaTeXDocumentClass m_class;
  /** the path to the setup package */
  private final Path m_setupPackagePath;

  /**
   * {@code true} if we have at least one figure in the document,
   * {@code false} otherwise
   */
  private boolean m_hasFigure;
  /**
   * {@code true} if we have at least one figure series in the document
   * (and {@link #m_hasFigure} is also {@code true}), {@code false}
   * otherwise
   */
  private boolean m_hasFigureSeries;
  /**
   * {@code true} if we have at least one table in the document,
   * {@code false} otherwise
   */
  private boolean m_hasTable;
  /**
   * {@code true} if we have at least one cell spanning multiple rows in a
   * table in the document (and {@link #m_hasTable} is also {@code true}),
   * {@code false} otherwise
   */
  private boolean m_hasMultiRowCell;
  /**
   * {@code true} if we have at least one cell spanning multiple columns in
   * a table in the document (and {@link #m_hasTable} is also {@code true}
   * ), {@code false} otherwise
   */
  private boolean m_hasMultiColCell;

  /**
   * {@code true} if we have at least one code block in the document,
   * {@code false} otherwise
   */
  private boolean m_hasCode;

  /**
   * {@code true} if we have at least one text super- or sub-script in the
   * document, {@code false} otherwise
   */
  private boolean m_hasTextSubOrSuperScript;

  /** the figure series package path */
  private Path m_figureSeriesPackagePath;

  /** the color names */
  private final HashMap<Object, String> m_colorNames;

  /**
   * Create a document.
   * 
   * @param builder
   *          the document builder
   */
  _LaTeXDocument(final LaTeXDocumentBuilder builder) {
    super(LaTeXDriver.getInstance(), builder);

    this.m_compile = builder.shouldCompile();
    LaTeXConfiguration._checkDocumentClass(//
        this.m_class = builder.getDocumentClass());
    this.m_setupPackagePath = PathUtils
        .createPathInside(
            this.getDocumentFolder(),
            ((PathUtils.getFileNameWithoutExtension(this.getDocumentPath()) + "-setup.") + //$NON-NLS-1$
            ELaTeXFileType.STY.getDefaultSuffix()));

    this.m_colorNames = new HashMap<>();

    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final PhysicalDimension getSize(final EFigureSize size) {
    return size.approximateSize(this.m_class);
  }

  /**
   * get the internal name for a given color style
   * 
   * @param style
   *          the style
   * @return the name
   */
  @SuppressWarnings("incomplete-switch")
  final String _colorName(final ColorStyle style) {
    String ret, ret2;
    MemoryTextOutput mto;
    int i, j, length;
    boolean needsSwitch;

    synchronized (this.m_colorNames) {
      ret = this.m_colorNames.get(style);
      if (ret != null) {
        return ret;
      }

      mto = new MemoryTextOutput();
      ret = style.getID();
      length = ret.length();

      // first turn things like "Navy blue" into "NavyBlue"
      needsSwitch = false;
      for (i = j = 0; j < length; j++) {
        switch (ret.charAt(j)) {
          case '-':
          case ' ': {
            needsSwitch = true;
            if (i < j) {
              if (mto.length() > 0) {
                mto.append(Character.toUpperCase(ret.charAt(i)));
                mto.append(ret, (i + 1), j);
                i = (j + 1);
              }
            }
          }
        }
      }

      if (needsSwitch) {
        if (i < j) {
          if (mto.length() > 0) {
            mto.append(Character.toUpperCase(ret.charAt(i)));
            mto.append(ret, (i + 1), j);
            i = (j + 1);
          }
        }
        ret = mto.toString();
      }
      mto = null;

      // now deal with special characters and make result unique
      ret = NormalCharTransformer.getInstance().transform(ret,
          TextUtils.DEFAULT_NORMALIZER_FORM);
      ret += "Color"; //$NON-NLS-1$

      for (i = 0;; i++) {
        if (i > 0) {
          ret2 = (ret + i);
        } else {
          ret2 = ret;
        }
        if (this.m_colorNames.get(ret2) == null) {
          this.m_colorNames.put(ret2, ret2);
          this.m_colorNames.put(style, ret2);
          return ret2;
        }
      }
    }
  }

  /**
   * require a package
   * 
   * @param textOut
   *          the text output to write to
   * @param packageName
   *          the package name
   * @param options
   *          the package options
   */
  private static final void __requirePackage(final ITextOutput textOut,
      final String packageName, final String... options) {
    char prefix;

    textOut.append(_LaTeXDocument.REQUIRE_PACKAGE);

    if ((options != null) && (options.length > 0)) {
      prefix = '[';
      for (final String option : options) {
        textOut.append(prefix);
        textOut.append(option);
        prefix = ',';
      }
      textOut.append(']');
    }

    textOut.append('{');
    textOut.append(packageName);
    LaTeXDriver._endCommandLine(textOut);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;
    final ArrayListView<String> parameters;
    char ch;

    super.onOpen();

    this.getFileCollector().addFile(this.getDocumentPath(),
        ELaTeXFileType.TEX);

    out = this.getTextOutput();

    out.append(_LaTeXDocument.DOCUMENT_CLASS);
    parameters = this.m_class.getDocumentClassParameters();
    if ((parameters != null) && (!(parameters.isEmpty()))) {
      ch = '[';
      for (final String parameter : parameters) {
        out.append(ch);
        ch = ',';
        out.append(parameter);
      }
      out.append(']');
    }
    out.append('{');
    out.append(this.m_class.getDocumentClass());
    LaTeXDriver._endCommandLine(out);
    LaTeXDriver._endLine(out);

    LaTeXDriver._commentLine((((//
        "All required packages and allocates colors etc. are found in package '" //$NON-NLS-1$
        + PathUtils.getFileNameWithoutExtension(//
            this.m_setupPackagePath)) + '\'') + '.'), out);
    _LaTeXDocument.__requirePackage(out,
        this._pathRelativeToDocument(this.m_setupPackagePath, true));
    LaTeXDriver._endLine(out);
    LaTeXDriver._endLine(out);
  }

  /**
   * get a relative path to a document component
   * 
   * @param path
   *          the path of the component
   * @param omitExtension
   *          should a possible file extension be removed?
   * @return the relative path
   */
  final String _pathRelativeToDocument(final Path path,
      final boolean omitExtension) {
    Path folder, relFile;
    final int lastDot;
    String rel, sep;

    folder = this.getDocumentFolder();
    relFile = folder.relativize(path);
    if (relFile == null) {
      throw new IllegalArgumentException(((((//
          "Could not relativize '" + path) + //$NON-NLS-1$
          "' against document folder '") + folder)//$NON-NLS-1$
          + '\'') + '.');
    }

    rel = relFile.toString();
    if ((rel == null) || (rel.length() <= 0)) {
      throw new IllegalArgumentException((((//
          "Relativizing '" + path) + //$NON-NLS-1$
          "' against document folder '") + folder)//$NON-NLS-1$
          + "' leads to an empty string.");//$NON-NLS-1$
    }

    sep = folder.getFileSystem().getSeparator();
    if (!(_LaTeXDocument.REQUIRED_SEPARATOR.equals(sep))) {
      rel = rel.replace(sep, _LaTeXDocument.REQUIRED_SEPARATOR);
    }

    if (omitExtension) {
      lastDot = rel.lastIndexOf('.');
      if (lastDot >= 0) {
        rel = rel.substring(0, lastDot);
      }
    }

    if ((rel == null) || (rel.length() <= 0)) {
      throw new IllegalArgumentException(
          ((("Relativizing '" + path) + //$NON-NLS-1$
              "' against document folder '") + folder)//$NON-NLS-1$
              + "' leads to an empty string after adjusting separator and extension.");//$NON-NLS-1$
    }

    return rel;
  }

  /**
   * Write an include graphics command to a text output
   * 
   * @param size
   *          the size of the graphic
   * @param files
   *          the graphic files (there should be exactly 1)
   * @param out
   *          the destination to write to
   */
  final void _includeGraphics(final PhysicalDimension size,
      final ArrayListView<Map.Entry<Path, EGraphicFormat>> files,
      final ITextOutput out) {
    final String shrt;
    double width, height;
    ELength length;

    if (files.isEmpty()) {
      LaTeXDriver
          ._commentLine(//
              "No graphic created. This is odd (maybe see the log for details).", //$NON-NLS-1$
              out);
      return;
    }

    width = size.getWidth();
    height = size.getHeight();
    length = size.getUnit();
    switch (length) {
      case POINT:
      case INCH:
      case MILLIMETER:
      case CENTIMETER:
      case PICA:
      case DIDOT:
      case CICERO:
      case SCALED_POINT:
      case BIG_POINT: {
        break;
      }
      default: {
        width = length.convertTo(width, ELength.POINT);
        height = length.convertTo(height, ELength.POINT);
        length = ELength.POINT;
      }
    }

    out.append(_LaTeXDocument.INCLUDE_GRAPHICS_1);
    SimpleNumberAppender.INSTANCE.appendTo(width, ETextCase.IN_SENTENCE,
        out);
    out.append(shrt = length.getShortcut());
    out.append(_LaTeXDocument.INCLUDE_GRAPHICS_2);
    SimpleNumberAppender.INSTANCE.appendTo(height, ETextCase.IN_SENTENCE,
        out);
    out.append(shrt);
    out.append(']');
    out.append('{');
    out.append(this._pathRelativeToDocument(files.get(0).getKey(), false));
    LaTeXDriver._endCommandLine(out);
  }

  /** register that there is a figure series in the document */
  final void _registerFigureSeries() {
    this.m_hasFigureSeries = true;
    this.m_hasFigure = true;
  }

  /** register that there is a figure in the document */
  final void _registerFigure() {
    this.m_hasFigure = true;
  }

  /** register that there is a table in the document */
  final void _registerTable() {
    this.m_hasTable = true;
  }

  /**
   * register that there is a cell which spans multiple rows in a table in
   * the document
   * 
   * @param mode
   *          the table mode
   */
  final void _registerCell(final int mode) {
    this.m_hasTable = true;
    if ((mode & _LaTeXTable.MODE_MULTI_COL) != 0) {
      this.m_hasMultiColCell = true;
    }
    if ((mode & _LaTeXTable.MODE_MULTI_ROW) != 0) {
      this.m_hasMultiRowCell = true;
    }
  }

  /** register that there is a text sub- or superscript in the document */
  final void _registerTextSubOrSuperScript() {
    this.m_hasTextSubOrSuperScript = true;
  }

  /** register that there is a code block in the document */
  final void _registerCode() {
    this.m_hasCode = true;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doOnClose() {
    final ITextOutput out;

    out = this.getTextOutput();

    LaTeXDriver._endLine(out);
    LaTeXDriver._commentLine("That's all, folks.", out); //$NON-NLS-1$
    LaTeXDriver._endLine(out);
    out.append(_LaTeXDocument.DOCUMENT_END);
    LaTeXDriver._endLine(out);
    out.append(_LaTeXDocument.INPUT_END);
    LaTeXDriver._endLine(out);

    super.doOnClose();
  }

  /** {@inheritDoc} */
  @Override
  protected final void postProcess(final Set<IStyle> usedStyles,
      final ArrayListView<ImmutableAssociation<Path, IFileType>> paths) {

    this.__finalizeFigureSeries();
    this.__buildSetupPackage(usedStyles);
    this.__compile(paths);
  }

  /**
   * build the setup package
   * 
   * @param usedStyles
   *          the used styles
   */
  private final void __buildSetupPackage(final Set<IStyle> usedStyles) {
    final AbstractTextOutput out;
    final boolean hasColors;
    String html;
    ColorStyle color;
    int i;

    try (final OutputStream os = PathUtils
        .openOutputStream(this.m_setupPackagePath)) {
      try (final OutputStreamWriter osw = new OutputStreamWriter(os)) {
        try (final BufferedWriter bw = new BufferedWriter(osw)) {
          out = AbstractTextOutput.wrap(bw);

          // adding default packages
          _LaTeXDocument.__requirePackage(out, "fontenc",//$NON-NLS-1$ 
              "T1"); //$NON-NLS-1$

          hasColors = (!(this.m_colorNames.isEmpty()));
          if (this.m_hasTable || hasColors) {
            _LaTeXDocument.__requirePackage(out, "color"); //$NON-NLS-1$
            _LaTeXDocument.__requirePackage(out, "xcolor"); //$NON-NLS-1$
          }

          _LaTeXDocument.__requirePackage(out, "caption3"); //$NON-NLS-1$

          if (this.m_hasTable) {
            if (this.m_hasMultiColCell) {
              _LaTeXDocument.__requirePackage(out, "multicol"); //$NON-NLS-1$
            }
            if (this.m_hasMultiRowCell) {
              _LaTeXDocument.__requirePackage(out, "multirow"); //$NON-NLS-1$
            }
          }

          if (this.m_hasFigure) {
            _LaTeXDocument.__requirePackage(out, "graphicx"); //$NON-NLS-1$
          }

          // figure series
          if (this.m_hasFigureSeries
              && (this.m_figureSeriesPackagePath != null)) {
            _LaTeXDocument.__requirePackage(out,
                this._pathRelativeToDocument(//
                    this.m_figureSeriesPackagePath, true));
            LaTeXDriver._endLine(out);
          }

          if (this.m_hasCode) {
            _LaTeXDocument.__requirePackage(out, "listings"); //$NON-NLS-1$
          }

          if (this.m_hasTextSubOrSuperScript) {
            _LaTeXDocument.__requirePackage(out, "fixltx2e"); //$NON-NLS-1$
          }

          _LaTeXDocument.__requirePackage(out, "hyperref"); //$NON-NLS-1$
          _LaTeXDocument.__requirePackage(out, "breakurl"); //$NON-NLS-1$

          // now add the colors
          if (hasColors) {
            for (final IStyle style : usedStyles) {
              if ((style != null) && (style instanceof ColorStyle)) {
                color = ((ColorStyle) style);
                out.append("\\definecolor{");//$NON-NLS-1$
                out.append(this._colorName(color));
                out.append("}{HTML}{"); //$NON-NLS-1$
                html = Integer.toHexString(color.getRGB() & 0xffffff)
                    .toUpperCase();
                for (i = html.length(); i < 6; i++) {
                  out.append('0');
                }
                out.append(html);
                LaTeXDriver._endCommandLine(out);
              }
            }
          }

          LaTeXDriver._endLine(out);
          LaTeXDriver._endLine(out);

          out.append(_LaTeXDocument.INPUT_END);
          LaTeXDriver._endLine(out);
          out.flush();
        }
      }
    } catch (final IOException ioError) {
      ErrorUtils
          .logError(this.getLogger(),
              (((("Error when creating setup package '" + //$NON-NLS-1$
              this.m_figureSeriesPackagePath) + " for document ") + this) + //$NON-NLS-1$
              " this is a problem, as compiling the document is now impossible.")//$NON-NLS-1$
              , ioError, true);
      ErrorUtils.throwAsRuntimeException(ioError);
    }
  }

  /** finalize the figure series */
  private final void __finalizeFigureSeries() {
    final Path path;
    boolean success;

    this.m_figureSeriesPackagePath = null;

    if (this.m_hasFigureSeries) {
      success = false;

      path = PathUtils.createPathInside(this.getDocumentFolder(),
          _LaTeXDocument.FIGURE_SERIES_PACKAGE);

      try (final InputStream is = _LaTeXDocument.class
          .getResourceAsStream(_LaTeXDocument.FIGURE_SERIES_PACKAGE)) {
        Files.copy(is, path);
        success = true;
      } catch (final IOException ioError) {
        ErrorUtils
            .logError(
                this.getLogger(),
                Level.WARNING,
                ((((("Error when copying package '" + _LaTeXDocument.FIGURE_SERIES_PACKAGE) + //$NON-NLS-1$
                "' to file '") + path) + //$NON-NLS-1$
                "'. Maybe the file already exists? Either way, there could be problems when compiling the document ") //$NON-NLS-1$
                + this) + '.', ioError, true);
      }

      if (success || Files.exists(path)) {
        this.m_figureSeriesPackagePath = path;
        this.getFileCollector().addFile(path, ELaTeXFileType.STY);
      }
    }
  }

  /**
   * compile the document
   * 
   * @param paths
   *          the paths
   */
  private final void __compile(
      final ArrayListView<ImmutableAssociation<Path, IFileType>> paths) {
    final Logger logger;
    final LaTeX toolChain;
    final LaTeXJobBuilder builder;
    final LaTeXJob job;
    IFileType type;

    logger = this.getLogger();

    if (!(this.m_compile)) {
      if ((logger != null) && (logger.isLoggable(Level.FINER))) {
        logger.finer("Compilation for document " + this + //$NON-NLS-1$
            " not requested.");//$NON-NLS-1$
      }
    }

    try {
      toolChain = LaTeX.getInstance();
      if ((toolChain == null) || (!(toolChain.canUse()))) {
        if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
          logger.warning(//
              "Compilation is requested for LaTeX document " //$NON-NLS-1$
                  + this.toString() + //
                  " but the LaTeX compiler tool is not available, so we don't compile.");//$NON-NLS-1$
        }
        return;
      }

      builder = toolChain.use();
      builder.setLogger(logger);
      builder.setFileProducerListener(this.getFileCollector());
      builder.setMainFile(this.getDocumentPath());

      // register the required resources
      for (final ImmutableAssociation<Path, IFileType> file : paths) {
        type = file.getValue();
        if ((type != null)
            && ((type instanceof EGraphicFormat) || (type instanceof ELaTeXFileType))) {
          builder.requireFileType(type);
        }
      }

      // compile the pdf
      job = builder.create();
      if (job.canCompileToPDF()) {
        job.call();
        if ((logger != null) && (logger.isLoggable(Level.INFO))) {
          logger.warning(//
              "Compilation of LaTeX document " //$NON-NLS-1$
                  + this.toString() + //
                  " has been completed without error (seemingly).");//$NON-NLS-1$
        }
      } else {
        if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
          logger.warning(//
              "Compilation is requested for LaTeX document '" //$NON-NLS-1$
                  + this.toString() + //
                  " but no suitable tool chain was found.");//$NON-NLS-1$
        }
      }

    } catch (final Throwable error) {
      ErrorUtils
          .logError(logger,//
              (("An error occured when trying to compile document '"//$NON-NLS-1$
              + this.toString()) + //
              " but we will ignore this error, as it just means that we did not get a PDF.")//$NON-NLS-1$
              , error, true);
    }
  }
}
