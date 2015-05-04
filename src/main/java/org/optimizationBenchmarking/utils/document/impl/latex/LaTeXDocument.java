package org.optimizationBenchmarking.utils.document.impl.latex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.ImmutableAssociation;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.impl.abstr.Document;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorStyle;
import org.optimizationBenchmarking.utils.io.FileType;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.tools.impl.abstr.FileCollector;
import org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType;
import org.optimizationBenchmarking.utils.tools.impl.latex.LaTeX;
import org.optimizationBenchmarking.utils.tools.impl.latex.LaTeXJob;
import org.optimizationBenchmarking.utils.tools.impl.latex.LaTeXJobBuilder;

/** the LaTeX document */
public final class LaTeXDocument extends Document {

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

  /** do we have colors? */
  private boolean m_hasColors;

  /** do we have underlined text? */
  private boolean m_hasUnderlined;

  /** do we need the ams-symbols package? */
  private boolean m_needsAMSSymb;

  /**
   * Create a document.
   *
   * @param builder
   *          the document builder
   */
  LaTeXDocument(final LaTeXDocumentBuilder builder) {
    super(LaTeXDriver.getInstance(), builder);

    this.m_compile = builder.shouldCompile();
    LaTeXConfiguration._checkDocumentClass(//
        this.m_class = builder.getDocumentClass());
    this.m_setupPackagePath = PathUtils
        .createPathInside(
            this.getDocumentFolder(),
            ((PathUtils.getFileNameWithoutExtension(this.getDocumentPath()) + "-setup.") + //$NON-NLS-1$
            ELaTeXFileType.STY.getDefaultSuffix()));

    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final PhysicalDimension getFigureSize(final EFigureSize size) {
    return size.approximateSize(this.m_class);
  }

  /** {@inheritDoc} */
  @Override
  protected final int getFiguresPerRow(final EFigureSize size) {
    PhysicalDimension dim;
    int perRowCalc, perRowExpected;

    dim = this.getFigureSize(size);

    perRowCalc = (int) (this.m_class.getUnit().convertTo(
        this.m_class.getWidth(), ELength.POINT) / //
    dim.getUnit().convertTo(dim.getWidth(), ELength.POINT));
    perRowExpected = size.getNX();
    if (size.spansAllColumns()) {
      perRowExpected *= this.m_class.getColumnCount();
    }

    return (Math.max(1,//
        Math.max((perRowExpected - 1),//
            Math.min((perRowExpected + 1), perRowCalc))));

  }

  /**
   * get the internal name for a given color style
   *
   * @param style
   *          the style
   * @return the name
   */
  final String _colorName(final ColorStyle style) {
    this.m_hasColors = true;
    return (style.getID() + "Color"); //$NON-NLS-1$
  }

  /**
   * Require a set of resources
   *
   * @param clazz
   *          the class
   * @param resources
   *          the resources
   * @param license
   *          the license text
   * @return the stored paths: An array of the same length as
   *         {@code resources} with {@code null} for any element not copied
   *         and a path for the copied elements
   */
  @SuppressWarnings("resource")
  final Path[] _requireResources(final Class<?> clazz,
      final String[] resources, final String license) {
    final Logger logger;
    final MemoryTextOutput mto;
    final int initLength;
    final FileCollector collector;
    final Path[] ret;
    IFileType type;
    Path path;
    String ending, resource;
    InputStream is;
    int index;

    if (resources == null) {
      throw new IllegalArgumentException(//
          "Paths supplied to requireResource cannot be null."); //$NON-NLS-1$
    }

    logger = this.getLogger();
    ret = new Path[resources.length];

    mto = new MemoryTextOutput();
    mto.append(//
    "The following license, terms, and conditions apply to "); //$NON-NLS-1$
    initLength = mto.length();
    collector = this.getFileCollector();
    try {
      path = null;
      for (index = 0; index < resources.length; index++) {
        resource = resources[index];
        try {
          path = PathUtils.createPathInside(this.getDocumentFolder(),
              resource);
          if (Files.exists(path)) {
            ret[index] = path;
            if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
              logger.warning(//
                  ((((((("Resource '" //$NON-NLS-1$
                      + resource) + "' from class ") + //$NON-NLS-1$
                      TextUtils.className(clazz)) + //
                      " seems to already exist in the document folder as file '")//$NON-NLS-1$
                      + path) + "' of document") + this) + //$NON-NLS-1$
                      ". This may cause problems when compiling the document.");//$NON-NLS-1$
            }
            continue;
          }
          try {

            is = clazz.getResourceAsStream(resource);
            if (is != null) {
              try {
                Files.copy(is, path);
              } finally {
                is.close();
              }

              if (mto.length() > initLength) {
                mto.append(',');
                mto.append(' ');
              }
              mto.append(resource);
              mto.append(" (stored as file '"); //$NON-NLS-1$
              mto.append(path);
              mto.append('\'');
              mto.append(')');

              ending = PathUtils.getFileExtension(path);
              if (ending != null) {
                findType: {
                  for (final EGraphicFormat format : EGraphicFormat.INSTANCES) {
                    if (ending.equalsIgnoreCase(format.getDefaultSuffix())) {
                      type = format;
                      break findType;
                    }
                  }
                  for (final ELaTeXFileType format : ELaTeXFileType.INSTANCES) {
                    if (ending.equalsIgnoreCase(format.getDefaultSuffix())) {
                      type = format;
                      break findType;
                    }
                  }
                  type = new FileType(ending, null, null);
                }

                collector.addFile(path, type);
              }

              ret[index] = path;

            } else {
              if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
                logger
                    .warning(((((("Resource '" //$NON-NLS-1$
                        + resource) + "' from class ") + //$NON-NLS-1$
                        TextUtils.className(clazz)) + //
                        " is missing and thus could not be copied to document ")//$NON-NLS-1$
                        + this)
                        + ". This may make compiling the document impossible.");//$NON-NLS-1$
              }
            }
          } catch (final Throwable error2) {
            ErrorUtils.logError(logger, Level.WARNING,
                (((((((("An error occured while copying resource '" //$NON-NLS-1$
                + resource) + "' from class ") + //$NON-NLS-1$
                TextUtils.className(clazz)) + " to document ") + this) + //$NON-NLS-1$
                " under path '") + path) + //$NON-NLS-1$
                "'. This may make compiling the document impossible."),//$NON-NLS-1$
                error2, true, RethrowMode.DONT_RETHROW);
          }
        } catch (final Throwable error) {
          ErrorUtils
              .logError(
                  logger,
                  Level.WARNING,
                  (((((("An error occured while creating destination path for resource '" //$NON-NLS-1$
                  + resource) + "' from class ") + //$NON-NLS-1$
                  TextUtils.className(clazz)) + " to document ") + this) + //$NON-NLS-1$
                  ". This may make compiling the document impossible."),//$NON-NLS-1$
                  error, true, RethrowMode.DONT_RETHROW);
        }
      }
    } finally {
      if ((mto.length() > initLength) && (logger != null)
          && (logger.isLoggable(Level.INFO))) {
        mto.append(':');
        mto.append(' ');
        mto.append('\'');
        mto.append(license);
        mto.append('\'');
        mto.append('.');
        logger.info(mto.toString());
      }
    }

    return ret;
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
  static final void _requirePackage(final ITextOutput textOut,
      final String packageName, final String[] options) {
    char prefix;

    textOut.append(LaTeXDocument.REQUIRE_PACKAGE);

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

  /**
   * load the package and require it
   *
   * @param textOut
   *          the text output device
   * @param fullPackageName
   *          the full package name
   * @param options
   *          the options
   * @param license
   *          the license
   */
  private final void __loadPackageAndRequire(final ITextOutput textOut,
      final String fullPackageName, final String[] options,
      final String license) {
    final Path[] paths;
    final Path path;

    paths = this._requireResources(LaTeXDocument.class,
        new String[] { fullPackageName }, license);
    if ((paths != null) && (paths.length >= 1)) {
      path = paths[0];
      if (path != null) {
        LaTeXDocument._requirePackage(textOut,
            this._pathRelativeToDocument(path, true), options);
      }
    }
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

    out.append(LaTeXDocument.DOCUMENT_CLASS);
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
        "All required packages and allocated colors etc. are found in package '" //$NON-NLS-1$
        + PathUtils.getFileNameWithoutExtension(//
            this.m_setupPackagePath)) + '\'') + '.'), out);
    LaTeXDocument._requirePackage(out,
        this._pathRelativeToDocument(this.m_setupPackagePath, true), null);
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
    if (!(LaTeXDocument.REQUIRED_SEPARATOR.equals(sep))) {
      rel = rel.replace(sep, LaTeXDocument.REQUIRED_SEPARATOR);
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

    out.append(LaTeXDocument.INCLUDE_GRAPHICS_1);
    SimpleNumberAppender.INSTANCE.appendTo(width, ETextCase.IN_SENTENCE,
        out);
    out.append(shrt = length.getShortcut());
    out.append(LaTeXDocument.INCLUDE_GRAPHICS_2);
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

  /** register that we need the amssymb package */
  final void _registerNeedsAMSSymb() {
    this.m_needsAMSSymb = true;
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

  /** register that there is an underlined text document */
  final void _registerUnderlined() {
    this.m_hasUnderlined = true;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doOnClose() {
    final ITextOutput out;

    out = this.getTextOutput();

    LaTeXDriver._endLine(out);
    LaTeXDriver._commentLine("That's all, folks.", out); //$NON-NLS-1$
    LaTeXDriver._endLine(out);
    out.append(LaTeXDocument.DOCUMENT_END);
    LaTeXDriver._endLine(out);
    out.append(LaTeXDocument.INPUT_END);
    LaTeXDriver._endLine(out);

    super.doOnClose();
  }

  /** {@inheritDoc} */
  @Override
  protected final void postProcess(final Set<IStyle> usedStyles,
      final ArrayListView<ImmutableAssociation<Path, IFileType>> paths) {

    this.__buildSetupPackage(usedStyles);
    this.__compile(paths);
  }

  /**
   * Require a resource
   *
   * @param resource
   *          the resource
   * @param dest
   *          the destination
   */
  private final void __include(final String resource,
      final ITextOutput dest) {
    String s;

    LaTeXDriver._endLine(dest);
    try (final InputStream is = LaTeXDocument.class
        .getResourceAsStream(resource)) {
      try (final InputStreamReader isr = new InputStreamReader(is)) {
        try (final BufferedReader br = new BufferedReader(isr)) {
          while ((s = br.readLine()) != null) {
            s = TextUtils.prepare(s);
            if (s != null) {
              dest.append(s);
              if (s.charAt(s.length() - 1) == '%') {
                dest.appendLineBreak();
                continue;
              }
            }
            LaTeXDriver._endLine(dest);
          }
        }
      }
    } catch (final Throwable error) {
      ErrorUtils.logError(this.getLogger(), Level.WARNING,//
          "Error when loading resource '" //$NON-NLS-1$
              + resource + //
              "' - this will maybe make compiling LaTeX document " //$NON-NLS-1$
              + this + " impossible.",//$NON-NLS-1$
          error, true, RethrowMode.DONT_RETHROW);
    }
    LaTeXDriver._endLine(dest);
  }

  /**
   * build the setup package
   *
   * @param usedStyles
   *          the used styles
   */
  private final void __buildSetupPackage(final Set<IStyle> usedStyles) {
    final AbstractTextOutput out;
    String html;
    ColorStyle color;
    int i;

    try (final OutputStream os = PathUtils
        .openOutputStream(this.m_setupPackagePath)) {
      try (final OutputStreamWriter osw = new OutputStreamWriter(os)) {
        try (final BufferedWriter bw = new BufferedWriter(osw)) {
          out = AbstractTextOutput.wrap(bw);

          // adding default packages

          LaTeXDriver
              ._commentLine(//
                  "This is the setup package for document " + //$NON-NLS-1$
                      PathUtils.getName(this.getDocumentPath()) + //
                      ", it loads all required packages and provides the definitions needed to compile this document.",//$NON-NLS-1$
                  out);

          this.__include("fonts.def", out);//$NON-NLS-1$

          if (this.m_hasTable || this.m_hasColors || this.m_hasCode) {
            this.__include("colors.def", out);//$NON-NLS-1$
          }

          if (this.m_hasFigure || this.m_hasTable) {
            LaTeXDriver
                ._commentLine(//
                    "We need caption3 to properly render figure or table captions.",//$NON-NLS-1$
                    out);
            LaTeXDocument._requirePackage(out, "caption3", null); //$NON-NLS-1$
          }

          if (this.m_hasTable) {
            this.__include("tables.def", out);//$NON-NLS-1$
            if (this.m_hasMultiColCell) {
              LaTeXDriver
                  ._commentLine(
                      "We need multicol since we have at least one table with at least one cell which spans multiple columns.",//$NON-NLS-1$
                      out);
              LaTeXDocument._requirePackage(out, "multicol", null); //$NON-NLS-1$
            }
            if (this.m_hasMultiRowCell) {
              LaTeXDriver
                  ._commentLine(
                      "We need multicol since we have at least one table with at least one cell which spans multiple rows.",//$NON-NLS-1$
                      out);
              LaTeXDocument._requirePackage(out, "multirow", null); //$NON-NLS-1$
            }
          }

          if (this.m_hasFigure) {
            LaTeXDriver._commentLine(
                "We need graphicx to render the figures.",//$NON-NLS-1$
                out);
            LaTeXDocument._requirePackage(out, "graphicx", null); //$NON-NLS-1$
          }

          if (this.m_needsAMSSymb) {
            LaTeXDriver
                ._commentLine(
                    "We need amssymb to render some of the mathematical symbols.",//$NON-NLS-1$
                    out);
            LaTeXDocument._requirePackage(out, "amssymb", null); //$NON-NLS-1$
          }

          // figure series
          if (this.m_hasFigureSeries) {
            this.__loadPackageAndRequire(
                out,
                "figureSeries.sty", //$NON-NLS-1$
                null,
                "The figureSeries Package is under LaTeX Project Public License, either version 1.3 of this license or (at your option) any later version. It is author-maintained by Thomas Weise. Copyright (c) 2014, 2015 Thomas Weise."); //$NON-NLS-1$
          }

          if (this.m_hasCode) {
            this.__include("listings.def", out);//$NON-NLS-1$
          }

          if (this.m_hasTextSubOrSuperScript) {
            LaTeXDocument._requirePackage(out, "fixltx2e", null); //$NON-NLS-1$
          }

          if (this.m_hasUnderlined) {
            LaTeXDriver
                ._commentLine(
                    "We need the ulem package to deal with long, underlined text. Ulem allows it to break over several lines.",//$NON-NLS-1$
                    out);
            this.__loadPackageAndRequire(out,
                "ulem.sty",//$NON-NLS-1$
                new String[] { "normalem" }, //$NON-NLS-1$
                "Copyright (c) 1989-2011 by Donald Arseneau (Vancouver, Canada; asnd@triumf.ca)\nThis software may be freely transmitted, reproduced, or modified for any purpose provided that this copyright notice is left intact. (Small excerpts may be taken and used without any restriction.)"//$NON-NLS-1$
            );
          }

          LaTeXDriver
              ._commentLine(
                  "We need hyperref to allow for clickable references and links.",//$NON-NLS-1$
                  out);
          LaTeXDocument._requirePackage(out, "hyperref", //$NON-NLS-1$
              new String[] { "hidelinks=true" });//$NON-NLS-1$
          if (this.getGraphicFormat() == EGraphicFormat.EPS) {
            LaTeXDocument._requirePackage(out, "breakurl", null); //$NON-NLS-1$
          }

          LaTeXDriver._endLine(out);
          this._requireResources(
              LaTeXDocument.class,
              new String[] { "alphalph.sty" }, //$NON-NLS-1$
              "Project: alphalph, Version: 2011/05/13 v2.4. Copyright (C) 1999, 2006-2008, 2010, 2011 by Heiko Oberdiek <heiko.oberdiek at googlemail.com> This work may be distributed and/or modified under the conditions of the LaTeX Project Public License, either version 1.3c of this license or (at your option) any later version. This version of this license is in http://www.latex-project.org/lppl/lppl-1-3c.txt and the latest version of this license is in http://www.latex-project.org/lppl.txt and version 1.3 or later is part of all distributions of LaTeX version 2005/12/01 or later. This work has the LPPL maintenance status \"maintained\". This Current Maintainer of this work is Heiko Oberdiek."); //$NON-NLS-1$
          this.__include("alphalph.def", out);//$NON-NLS-1$
          LaTeXDriver._endLine(out);

          // now add the colors
          if (this.m_hasColors) {
            LaTeXDriver._endLine(out);
            LaTeXDriver._commentLine(//
                "Here we define the colors used in the documents.",//$NON-NLS-1$
                out);

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

            LaTeXDriver._commentLine(//
                "End of color definitions.",//$NON-NLS-1$
                out);
            LaTeXDriver._endLine(out);
          }

          LaTeXDriver._endLine(out);
          this.m_class.setup(this, out);

          LaTeXDriver._endLine(out);
          LaTeXDriver._endLine(out);

          out.append(LaTeXDocument.INPUT_END);
          LaTeXDriver._endLine(out);
          out.flush();

          this.getFileCollector().addFile(this.m_setupPackagePath,
              ELaTeXFileType.STY);
        }
      }
    } catch (final IOException ioError) {
      ErrorUtils
          .logError(this.getLogger(), Level.WARNING,
              (((("Error when creating setup package '" + //$NON-NLS-1$
              this.m_setupPackagePath) + " for document ") + this) + //$NON-NLS-1$
              " this is a problem, as compiling the document is now impossible."),//$NON-NLS-1$
              ioError, true, RethrowMode.AS_RUNTIME_EXCEPTION);
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
              , error, true, RethrowMode.DONT_RETHROW);
    }
  }
}
