package org.optimizationBenchmarking.utils.document.impl.latex;

import java.nio.file.Path;
import java.util.Map;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.graphics.EPaperSize;
import org.optimizationBenchmarking.utils.graphics.PageDimension;
import org.optimizationBenchmarking.utils.graphics.style.PaletteInputDriver;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteBuilder;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A document class for LaTeX documents */
public class LaTeXDocumentClass extends PageDimension {

  /** the class to use */
  private final String m_class;

  /** the class parameters */
  private final ArrayListView<String> m_classParams;

  /** the bibliography style to use */
  private final String m_bibStyle;

  /** the page size */
  private final EPaperSize m_paperSize;

  /** the font palette */
  final FontPalette m_fonts;

  /**
   * create a new document class
   * 
   * @param clazz
   *          the class
   * @param params
   *          the parameters of this class
   * @param paperSize
   *          the paper size
   * @param bibStyle
   *          the bibliography style to use
   * @param width
   *          the width
   * @param height
   *          the height
   * @param columnCount
   *          the number of columns
   * @param columnWidth
   *          the width of a column
   * @param unit
   *          the length unit
   * @param fonts
   *          the font palette of this document class
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public LaTeXDocumentClass(final String clazz,
      final ArrayListView<String> params, final EPaperSize paperSize,
      final String bibStyle, final double width, final double height,
      final int columnCount, final double columnWidth, final ELength unit,
      final FontPalette fonts) {
    super(width, height, columnCount, columnWidth, unit);

    if ((this.m_class = TextUtils.normalize(clazz)) == null) {
      throw new IllegalArgumentException(//
          "Document class name cannot be empty or null, but is '" //$NON-NLS-1$
              + clazz + '\'');
    }
    if ((this.m_bibStyle = TextUtils.normalize(bibStyle)) == null) {
      throw new IllegalArgumentException(//
          "Bibliography style cannot be empty or null, but is '" //$NON-NLS-1$
              + bibStyle + '\'');
    }
    if (paperSize == null) {
      throw new IllegalArgumentException(//
          "Paper size cannot be null."); //$NON-NLS-1$
    }
    this.m_paperSize = paperSize;
    if ((params == null) || (params.isEmpty())) {
      this.m_classParams = ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
    } else {
      this.m_classParams = params;
    }
    this.m_fonts = ((fonts == null) ? LaTeXDocumentClass
        .getDefaultFontPalette() : fonts);
  }

  /**
   * Get the document class name
   * 
   * @return the document class name
   */
  public final String getDocumentClass() {
    return this.m_class;
  }

  /**
   * Get the document class parameters
   * 
   * @return the document class parameters
   */
  public final ArrayListView<String> getDocumentClassParameters() {
    return this.m_classParams;
  }

  /**
   * Get the bibliography style name
   * 
   * @return the bibliography style name
   */
  public final String getBibliographyStyle() {
    return this.m_bibStyle;
  }

  /**
   * Get the paper size
   * 
   * @return the paper size
   */
  public final EPaperSize getPaperSize() {
    return this.m_paperSize;
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    textOut.append(this.m_class);
    if (!(this.m_classParams.isEmpty())) {
      this.m_classParams.toText(textOut);
    }
    textOut.append('{');
    super.toText(textOut);
    textOut.append(", with bibliography style "); //$NON-NLS-1$
    textOut.append(this.m_bibStyle);
    textOut.append('}');
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(//
        HashUtils.combineHashes(super.hashCode(),//
            HashUtils.combineHashes(//
                HashUtils.hashCode(this.m_class),//
                HashUtils.hashCode(this.m_classParams))),//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_bibStyle),//
            HashUtils.hashCode(this.m_fonts)));
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    final LaTeXDocumentClass c;

    if (o == this) {
      return true;
    }

    if (o instanceof LaTeXDocumentClass) {
      c = ((LaTeXDocumentClass) o);

      return (EComparison.equals(this.m_class, c.m_class) && //
          EComparison.equals(this.m_classParams, c.m_classParams) && //
          EComparison.equals(this.m_bibStyle, c.m_bibStyle) && //
          EComparison.equals(this.m_fonts, c.m_fonts) && //
      super.equals(o));
    }
    return false;
  }

  /**
   * Initialize: copy all the necessary resources into a given folder
   * 
   * @param folder
   *          the folder to copy
   * @return a list of path entries describing the elements stored in the
   *         folder, or {@code null} if no additional files were created
   */
  public Map.Entry<Path, IFileType>[] initialize(final Path folder) {
    return null;
  }

  /**
   * obtain the default font palette
   * 
   * @return the default font palette
   */
  public static final FontPalette getDefaultFontPalette() {
    return __LaTeXDefaultFontPaletteLoader.INSTANCE;
  }

  /** the default font palette */
  private static final class __LaTeXDefaultFontPaletteLoader {

    /** the internal font palette */
    static final FontPalette INSTANCE;

    static {
      FontPalette p;

      p = null;
      try (final FontPaletteBuilder tb = new FontPaletteBuilder()) {
        PaletteInputDriver
            .getInstance()
            .use()
            .setDestination(tb)
            .addResource(LaTeXDocumentClass.class, "latex.fontPalette").create().call(); //$NON-NLS-1$
        p = tb.getResult();
      } catch (final Throwable tt) {
        ErrorUtils.throwAsRuntimeException(tt);
      }

      INSTANCE = p;
    }

  }
}
