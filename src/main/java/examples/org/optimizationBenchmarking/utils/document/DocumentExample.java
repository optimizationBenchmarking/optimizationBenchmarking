package examples.org.optimizationBenchmarking.utils.document;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.optimizationBenchmarking.utils.RandomUtils;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibDateBuilder;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10Driver;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBody;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;
import org.optimizationBenchmarking.utils.document.spec.IDocumentHeader;
import org.optimizationBenchmarking.utils.document.spec.IEquation;
import org.optimizationBenchmarking.utils.document.spec.IFigure;
import org.optimizationBenchmarking.utils.document.spec.IFigureSeries;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.ILabeledElement;
import org.optimizationBenchmarking.utils.document.spec.IList;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;
import org.optimizationBenchmarking.utils.document.spec.IStructuredText;
import org.optimizationBenchmarking.utils.document.spec.ITable;
import org.optimizationBenchmarking.utils.document.spec.ITableRow;
import org.optimizationBenchmarking.utils.document.spec.ITableSection;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.document.spec.TableCellDef;
import org.optimizationBenchmarking.utils.graphics.EScreenSize;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorStyle;
import org.optimizationBenchmarking.utils.graphics.style.font.FontStyle;
import org.optimizationBenchmarking.utils.graphics.style.stroke.StrokeStyle;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

import examples.org.optimizationBenchmarking.LoremIpsum;
import examples.org.optimizationBenchmarking.utils.graphics.FinishedPrinter;

/**
 * An example used to illustrate how documents can be created with the
 * document output API in parallel. The creation of each of the (nested)
 * sections is launched as a separate
 * {@link java.util.concurrent.RecursiveAction ForkJoinTask}. Several
 * different document drivers are used in parallel to generate documents.
 * Each document is entirely random and contains examples for all of the
 * available primitives of the API.
 */
public class DocumentExample implements Runnable {

  /** the graphic driver to use */
  private static final IDocumentDriver[] DRIVERS = {
      XHTML10Driver.getDefaultDriver(),
      new XHTML10Driver(EGraphicFormat.JPEG.getDefaultDriver(),
          EScreenSize.WQXGA.getPhysicalSize(120), null) };

  /** the normal text */
  private static final int NORMAL_TEXT = 0;
  /** in braces */
  private static final int IN_BRACES = (DocumentExample.NORMAL_TEXT + 1);
  /** in quotes */
  private static final int IN_QUOTES = (DocumentExample.IN_BRACES + 1);
  /** with font */
  private static final int WITH_FONT = (DocumentExample.IN_QUOTES + 1);
  /** with color */
  private static final int WITH_COLOR = (DocumentExample.WITH_FONT + 1);
  /** section */
  private static final int SECTION = (DocumentExample.WITH_COLOR + 1);
  /** enum */
  private static final int ENUM = (DocumentExample.SECTION + 1);
  /** itemize */
  private static final int ITEMIZE = (DocumentExample.ENUM + 1);
  /** figure */
  private static final int FIGURE = (DocumentExample.ITEMIZE + 1);
  /** figure series */
  private static final int FIGURE_SERIES = (DocumentExample.FIGURE + 1);
  /** table */
  private static final int TABLE = (DocumentExample.FIGURE_SERIES + 1);
  /** equation */
  private static final int EQUATION = (DocumentExample.TABLE + 1);
  /** inline math */
  private static final int INLINE_MATH = (DocumentExample.EQUATION + 1);

  /** all elements */
  private static final int ALL_LENGTH = (DocumentExample.INLINE_MATH + 1);

  /** the table cell defs */
  private static final TableCellDef[] CELLS = { TableCellDef.CENTER,
      TableCellDef.RIGHT, TableCellDef.LEFT,
      TableCellDef.VERTICAL_SEPARATOR };

  /** the comparison */
  private static final EComparison[] COMP = EComparison.values();

  /** the randomizer */
  private final Random m_rand;

  /** the document */
  private final IDocument m_doc;

  /** the fonts */
  private FontStyle[] m_fonts;

  /** the colors */
  private ColorStyle[] m_colors;

  /** the strokes */
  private StrokeStyle[] m_strokes;

  /** the maximum achieved section depth */
  private volatile int m_maxSectionDepth;

  /** the elements which have been done */
  private final boolean[] m_done;

  /** the labels */
  private final ArrayList<ILabel> m_labels;

  /** the figure counter */
  private volatile long m_figureCounter;

  /**
   * run the example: there are problems with the pdf output
   * 
   * @param args
   *          the arguments
   * @throws Throwable
   *           if isomething fails
   */
  public static final void main(final String[] args) throws Throwable {
    final Path dir;
    final ForkJoinPool pool;
    String last, cur;
    int i;

    if ((args != null) && (args.length > 0)) {
      dir = Paths.get(args[0]);
    } else {
      dir = Files.createTempDirectory("document"); //$NON-NLS-1$
    }

    i = 0;
    cur = null;
    pool = new ForkJoinPool();

    for (final IDocumentDriver driver : DocumentExample.DRIVERS) {//
      last = cur;
      cur = driver.getClass().getSimpleName();

      if (!(cur.equals(last))) {
        i = 0;
      }
      i++;

      pool.execute(new DocumentExample(driver.createDocument(
          dir.resolve((cur + '_') + i), "report",//$NON-NLS-1$ 
          new FinishedPrinter())));
    }

    pool.shutdown();
    pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
  }

  /**
   * mark a thing as done
   * 
   * @param index
   *          the index
   */
  private final void __do(final int index) {
    this.m_done[index] = true;
  }

  /**
   * note a label
   * 
   * @param e
   *          the labeled element
   */
  private final void __label(final ILabeledElement e) {
    final ILabel l;
    if (e != null) {
      l = e.getLabel();
      if (l != null) {
        synchronized (this.m_labels) {
          this.m_labels.add(l);
        }
      }
    }
  }

  /**
   * note the maximum section depth
   * 
   * @param i
   *          the value
   */
  private synchronized final void __maxSecDepth(final int i) {
    if (i > this.m_maxSectionDepth) {
      this.m_maxSectionDepth = i;
    }
  }

  /**
   * get the next figure
   * 
   * @return the next figure
   */
  private synchronized final long __nextFigure() {
    return (this.m_figureCounter++);
  }

  /**
   * create the header
   * 
   * @param header
   *          the header
   */
  private final void __createHeader(final IDocumentHeader header) {
    boolean first;

    try (final IPlainText t = header.title()) {
      t.append("Report Created by Driver "); //$NON-NLS-1$
      t.append(this.m_doc.getClass().getSimpleName());
    }

    try (final BibAuthorsBuilder babs = header.authors()) {
      try (final BibAuthorBuilder bab = babs.addAuthor()) {
        bab.setFamilyName("Weise");//$NON-NLS-1$
        bab.setPersonalName("Thomas");//$NON-NLS-1$
      }
      try (final BibAuthorBuilder bab = babs.addAuthor()) {
        bab.setFamilyName("Other");//$NON-NLS-1$
        bab.setPersonalName("Someone");//$NON-NLS-1$
      }
    }

    try (final BibDateBuilder bd = header.date()) {
      bd.fromNow();
    }

    first = true;
    try (final IPlainText t = header.summary()) {
      do {
        if (first) {
          first = false;
        } else {
          t.append(' ');
        }
        LoremIpsum.appendLoremIpsum(t, this.m_rand);
      } while (this.m_rand.nextInt(5) > 0);
    }
  }

  /**
   * check if examples for any possible thing are included
   * 
   * @return the things
   */
  private final boolean __hasAll() {
    for (final boolean b : this.m_done) {
      if (!b) {
        return false;
      }
    }
    return true;
  }

  /**
   * Create a section in a section container
   * 
   * @param sc
   *          the section container
   * @param sectionDepth
   *          the section depth
   */
  final void _createSection(final ISectionContainer sc,
      final int sectionDepth) {
    boolean hasSection, hasText, needsNewLine;
    int cur, last;
    ArrayList<Future<Void>> ra;

    hasText = hasSection = false;

    this.__do(DocumentExample.SECTION);
    needsNewLine = false;
    this.__maxSecDepth(sectionDepth + 1);
    cur = last = (-1);
    ra = null;

    try (final ISection section = sc.section(ELabelType.AUTO)) {
      this.__label(section);

      try (final IPlainText title = section.title()) {
        LoremIpsum.appendLoremIpsum(title, this.m_rand, 8);
      }

      try (final ISectionBody body = section.body()) {
        needsNewLine = false;
        do {
          if (hasSection) {
            cur = 0;
          } else {
            do {
              cur = this.m_rand.nextInt(8);
            } while ((cur == last) || ((cur == 2) && (last == 3))
                || ((cur == 3) && (last == 2)));
            last = cur;
          }

          switch (cur) {
            case 0: {
              if (sectionDepth > 4) {
                break;
              }
              hasSection = true;
              if (ra == null) {
                ra = new ArrayList<>();
              }
              ra.add(new _SectionTask(this, body, (sectionDepth + 1))
                  .fork());
              needsNewLine = true;
              break;
            }
            case 1: {
              this.__createList(body, 0);
              needsNewLine = false;
              break;
            }
            case 2: {
              this.__createFigure(body);
              needsNewLine = true;
              break;
            }
            case 3: {
              this.__createFigureSeries(body);
              needsNewLine = true;
              break;
            }
            case 4: {
              this.__createTable(body);
              needsNewLine = true;
              break;
            }
            case 5: {
              this.__createEquation(body);
              needsNewLine = false;
              break;
            }
            default: {
              this.__text(body, 0, needsNewLine);
              needsNewLine = hasText = true;
              break;
            }
          }
        } while ((!(hasText || hasSection)) || this.m_rand.nextBoolean());

        if (ra != null) {
          for (final Future<Void> f : ra) {
            try {
              f.get();
            } catch (final Throwable tt) {
              tt.printStackTrace();
            }
          }
        }
      }
    }
  }

  /**
   * print some text
   * 
   * @param out
   *          the text output
   * @param depth
   *          the depth
   * @param needsNewLine
   *          needs a new line?
   */
  private final void __text(final ITextOutput out, final int depth,
      final boolean needsNewLine) {
    boolean first, must, needs;
    IStyle s;

    first = true;
    must = needsNewLine;
    needs = false;

    do {
      spacer: {
        if (first) {
          if ((depth <= 0) && must) {
            out.appendLineBreak();
          }
          break spacer;
        }
        if (this.m_rand.nextBoolean()) {
          out.append(' ');
        } else {
          out.appendLineBreak();
        }
      }
      first = false;

      LoremIpsum.appendLoremIpsum(out, this.m_rand);
      this.__do(DocumentExample.NORMAL_TEXT);

      if ((out instanceof IPlainText) && (depth < 10)) {

        switch (this.m_rand.nextInt((out instanceof IComplexText) ? 6 : 3)) {
          case 1: {
            out.append(' ');
            try (final IPlainText t = ((IPlainText) out).inBraces()) {
              this.__text(t, (depth + 1), this.m_rand.nextBoolean());
              this.__do(DocumentExample.IN_BRACES);
            }
            break;
          }
          case 2: {
            out.append(' ');
            try (final IPlainText t = ((IPlainText) out).inQuotes()) {
              this.__text(t, (depth + 1), this.m_rand.nextBoolean());
              this.__do(DocumentExample.IN_QUOTES);
            }
            break;
          }
          case 3: {
            out.append(' ');
            try (final IPlainText t = ((IComplexText) out)
                .style((s = this.m_fonts[this.m_rand
                    .nextInt(this.m_fonts.length)]))) {
              t.append("In ");//$NON-NLS-1$
              s.appendDescription(ETextCase.IN_SENTENCE, t, false);
              t.append(" font: "); //$NON-NLS-1$
              this.__do(DocumentExample.WITH_FONT);
              this.__text(t, (depth + 1), this.m_rand.nextBoolean());
            }
            break;
          }
          case 4: {
            out.append(' ');
            try (final IPlainText t = ((IComplexText) out)
                .style((s = this.m_colors[this.m_rand
                    .nextInt(this.m_colors.length)]))) {
              t.append("In ");//$NON-NLS-1$
              s.appendDescription(ETextCase.IN_SENTENCE, t, false);
              t.append(" color: "); //$NON-NLS-1$
              this.__do(DocumentExample.WITH_COLOR);
              this.__text(t, (depth + 1), this.m_rand.nextBoolean());
            }
            break;
          }
          case 5: {
            out.append(" Equation: ");//$NON-NLS-1$
            this.__createInlineMath(((IComplexText) out));
            break;
          }
          default: {
            // nothing
          }
        }

        needs = true;
      }

      if (needs && (this.m_rand.nextBoolean())) {
        out.append(' ');
        LoremIpsum.appendLoremIpsum(out, this.m_rand);
        this.__do(DocumentExample.NORMAL_TEXT);
      }

      first = false;
    } while (this.m_rand.nextInt(depth + 2) <= 0);

  }

  /**
   * create a list
   * 
   * @param sb
   *          the section body
   * @param listDepth
   *          the list depth
   */
  private final void __createList(final IStructuredText sb,
      final int listDepth) {
    final boolean b;
    int ic;

    b = this.m_rand.nextBoolean();
    if (listDepth <= 0) {
      if (b) {
        this.__do(DocumentExample.ENUM);
      } else {
        this.__do(DocumentExample.ITEMIZE);
      }
    }

    try (final IList list = (b ? sb.enumeration() : sb.itemization())) {
      ic = 0;
      do {
        try (final IStructuredText item = list.item()) {
          if (this.m_rand.nextInt(10) <= 0) {
            this.__text(item, 0, false);
          } else {
            LoremIpsum.appendLoremIpsum(item, this.m_rand);
          }
          if ((listDepth <= 4)
              && (this.m_rand.nextInt(listDepth + 5) <= 0)) {
            this.__createList(item, (listDepth + 1));
          }
        }
      } while (((++ic) <= 2) || (this.m_rand.nextBoolean()));

    }
  }

  /**
   * create the body
   * 
   * @param body
   *          the body
   */
  private final void __createBody(final IDocumentBody body) {
    this.m_maxSectionDepth = 0;
    Arrays.fill(this.m_done, false);
    do {
      this._createSection(body, 0);
    } while ((!this.__hasAll()) || (this.m_maxSectionDepth < 3)
        || (this.m_rand.nextInt(3) > 0));
  }

  /**
   * create the footer
   * 
   * @param footer
   *          the footer
   */
  private final void __createFooter(final IDocumentBody footer) {
    this.m_maxSectionDepth = 0;
    Arrays.fill(this.m_done, false);
    do {
      this._createSection(footer, 0);
    } while (this.m_rand.nextBoolean());
  }

  /**
   * create
   * 
   * @param doc
   *          the document
   */
  private DocumentExample(final IDocument doc) {
    super();
    this.m_rand = new Random();
    this.m_doc = doc;
    this.m_done = new boolean[DocumentExample.ALL_LENGTH];
    this.m_labels = new ArrayList<>();
  }

  /** {@inheritDoc} */
  @Override
  public final void run() {
    try {
      this.m_maxSectionDepth = 0;
      this.m_labels.clear();
      this.m_figureCounter = 0L;

      Arrays.fill(this.m_done, false);

      this.__loadStyles();
      try (final IDocumentHeader header = this.m_doc.header()) {
        this.__createHeader(header);
      }
      try (final IDocumentBody body = this.m_doc.body()) {
        this.__createBody(body);
      }
      try (final IDocumentBody footer = this.m_doc.footer()) {
        this.__createFooter(footer);
      }
    } finally {
      this.m_doc.close();
    }
  }

  /** load the styles */
  private final void __loadStyles() {
    final StyleSet ss;
    final ArrayList<Object> list;

    ss = this.m_doc.getStyles();
    list = new ArrayList<>();

    findFonts: for (;;) {
      final List<FontStyle> s = ss.allocateFonts(1);
      if (s == null) {
        break findFonts;
      }
      list.addAll(s);
    }
    list.add(ss.getCodeFont());
    list.add(ss.getDefaultFont());
    list.add(ss.getEmphFont());
    this.m_fonts = list.toArray(new FontStyle[list.size()]);

    list.clear();
    findColors: for (;;) {
      final List<ColorStyle> s = ss.allocateColors(1);
      if (s == null) {
        break findColors;
      }
      list.addAll(s);
    }
    list.add(ss.getBlack());
    this.m_colors = list.toArray(new ColorStyle[list.size()]);

    list.clear();
    findStrokes: for (;;) {
      final List<StrokeStyle> s = ss.allocateStrokes(1);
      if (s == null) {
        break findStrokes;
      }
      list.addAll(s);
    }
    list.add(ss.getDefaultStroke());
    list.add(ss.getThickStroke());
    list.add(ss.getThinStroke());
    this.m_strokes = list.toArray(new StrokeStyle[list.size()]);
  }

  /**
   * create a table
   * 
   * @param sb
   *          the section body
   */
  private final void __createTable(final ISectionBody sb) {
    final ArrayList<TableCellDef> def, pureDef;
    final TableCellDef[] pureDefs;
    TableCellDef d;
    int min;

    this.__do(DocumentExample.TABLE);

    def = new ArrayList<>();
    pureDef = new ArrayList<>();
    min = (this.m_rand.nextInt(3) + 1);
    do {
      d = DocumentExample.CELLS[this.m_rand
          .nextInt(DocumentExample.CELLS.length)];
      def.add(d);
      if (d != TableCellDef.VERTICAL_SEPARATOR) {
        pureDef.add(d);
      }
    } while ((pureDef.size() < min) || this.m_rand.nextBoolean());
    pureDefs = pureDef.toArray(new TableCellDef[pureDef.size()]);

    try (final ITable tab = sb.table(ELabelType.AUTO,
        this.m_rand.nextBoolean(),
        def.toArray(new TableCellDef[def.size()]))) {
      try (final IPlainText cap = tab.caption()) {
        LoremIpsum.appendLoremIpsum(cap, this.m_rand,
            this.m_rand.nextInt(10) + 10);
      }
      try (final ITableSection s = tab.header()) {
        this.__makeTableSection(s, pureDefs, 1, 3);
      }
      try (final ITableSection s = tab.body()) {
        this.__makeTableSection(s, pureDefs, 2, 1000);
      }
      try (final ITableSection s = tab.footer()) {
        this.__makeTableSection(s, pureDefs, 0, 3);
      }
    }
  }

  /**
   * make a table section
   * 
   * @param sec
   *          the section
   * @param cells
   *          the cells
   * @param minRows
   *          the minimum number of rows
   * @param maxRows
   *          the maximum number of rows
   */
  private final void __makeTableSection(final ITableSection sec,
      final TableCellDef[] cells, final int minRows, final int maxRows) {
    int rows, neededRows, i, maxX, maxY;
    int[] blocked;
    TableCellDef d;

    neededRows = minRows;
    rows = 0;
    blocked = new int[cells.length];
    while ((rows < maxRows)
        && ((rows < neededRows) || (this.m_rand.nextInt(4) > 0))) {
      try (final ITableRow row = sec.row()) {
        rows++;
        for (i = 0; i < cells.length; i++) {

          if (blocked[i] < rows) {
            if (this.m_rand.nextInt(7) <= 0) {
              findMaxX: for (maxX = i; (++maxX) < cells.length;) {
                if (blocked[maxX] >= rows) {
                  break findMaxX;
                }
              }

              maxX = ((i + this.m_rand.nextInt(maxX - i)) + 1);
            } else {
              maxX = (i + 1);
            }

            if (this.m_rand.nextInt(7) <= 0) {
              maxY = (rows + 1 + this.m_rand.nextInt(Math.min(5,
                  ((maxRows - rows) + 1))));
            } else {
              maxY = (rows + 1);
            }

            Arrays.fill(blocked, i, maxX, (maxY - 1));
            neededRows = Math.max(neededRows, (maxY - 1));

            if ((maxX <= (i + 1)) && (maxY <= (rows + 1))) {
              try (final IPlainText cell = row.cell()) {
                cell.append(RandomUtils.longToObject(
                    this.m_rand.nextLong(), true));
              }
            } else {
              do {
                d = DocumentExample.CELLS[this.m_rand
                    .nextInt(DocumentExample.CELLS.length)];
              } while (d == TableCellDef.VERTICAL_SEPARATOR);
              try (final IPlainText cell = row.cell((maxX - i),
                  (maxY - rows), d)) {
                if (this.m_rand.nextBoolean()) {
                  cell.append(d);
                } else {
                  cell.append(RandomUtils.longToObject(
                      this.m_rand.nextLong(), true));
                }
              }
            }
          }
        }
      }
    }
  }

  /**
   * create a figure
   * 
   * @param sb
   *          the section body
   */
  private final void __createFigure(final ISectionBody sb) {
    final EFigureSize[] s;
    final EFigureSize v;

    s = EFigureSize.values();
    this.__do(DocumentExample.FIGURE);
    try (final IFigure fig = sb.figure(ELabelType.AUTO,
        (v = s[this.m_rand.nextInt(s.length)]),
        RandomUtils.longToString(null, this.__nextFigure()))) {
      this.__fillFigure(fig, v);
    }
  }

  /**
   * create a figure series
   * 
   * @param sb
   *          the section body
   */
  private final void __createFigureSeries(final ISectionBody sb) {
    final EFigureSize[] s;
    final EFigureSize v;
    final int c;
    int i;

    s = EFigureSize.values();
    v = s[this.m_rand.nextInt(s.length)];
    c = v.getNX();
    this.__do(DocumentExample.FIGURE_SERIES);
    try (final IFigureSeries fs = sb.figureSeries(ELabelType.AUTO, v,
        RandomUtils.longToString(null, this.__nextFigure()))) {
      this.__label(fs);
      try (final IPlainText caption = fs.caption()) {
        caption.append("A figure series with figures of size "); //$NON-NLS-1$
        caption.append(v.toString());
        caption.append(':');
        caption.append(' ');
        LoremIpsum.appendLoremIpsum(caption, this.m_rand);
      }
      for (i = (1 + this.m_rand.nextInt(10 * c)); (--i) >= 0;) {
        try (final IFigure fig = fs.figure(ELabelType.AUTO,
            RandomUtils.longToString(null, this.__nextFigure()))) {
          this.__fillFigure(fig, null);
        }
      }
    }
  }

  /**
   * get a point
   * 
   * @param r
   *          the rectangle
   * @return the point
   */
  private final Point2D __randomPoint(final Rectangle2D r) {
    return new Point2D.Double(
        //
        (r.getX() + (this.m_rand.nextDouble() * r.getWidth())),
        (r.getY() + (this.m_rand.nextDouble() * r.getHeight())));
  }

  /**
   * get a random polygon
   * 
   * @param r
   *          the rectangle
   * @return the point
   */
  private final double[][] __randomPolygon(final Rectangle2D r) {
    final double[][] data;
    double x, y;
    int dirX, dirY;
    Point2D p;
    int i;

    data = new double[2][this.m_rand.nextInt(100) + 3];
    p = this.__randomPoint(r);
    data[0][0] = p.getX();
    data[1][0] = p.getY();

    dirX = dirY = 0;
    for (i = 1; i < data[0].length; i++) {

      if (this.m_rand.nextInt(10) <= 0) {
        p = this.__randomPoint(r);
        data[0][i] = p.getX();
        data[1][i] = p.getY();
      } else {
        do {
          dirX += (this.m_rand.nextBoolean() ? (1) : (-1));
          dirY += (this.m_rand.nextBoolean() ? (1) : (-1));
        } while ((dirX == 0) && (dirY == 0));

        data[0][i] = data[0][i - 1];

        x = (this.m_rand.nextDouble() * 0.05d * r.getWidth());
        if (dirX > 0) {
          data[0][i] += x;
        } else {
          if (dirX < 0) {
            data[0][i] -= x;
          }
        }

        data[1][i] = data[1][i - 1];
        y = (this.m_rand.nextDouble() * 0.05d * r.getHeight());
        if (dirX > 0) {
          data[1][i] += y;
        } else {
          if (dirX < 0) {
            data[1][i] -= y;
          }
        }
      }

    }

    return data;
  }

  /**
   * fill a figure
   * 
   * @param fig
   *          the figure
   * @param v
   *          the figure size
   */
  private final void __fillFigure(final IFigure fig, final EFigureSize v) {
    final Rectangle2D r;
    final ArrayList<AffineTransform> at;
    int k, e, i;
    Point2D a, b;
    double[][] d;
    MemoryTextOutput mo;
    FontStyle fs;

    this.__label(fig);

    try (final IPlainText cap = fig.caption()) {
      if (v != null) {
        cap.append("A figure of size "); //$NON-NLS-1$
        cap.append(v.toString());
        cap.append(':');
        cap.append(' ');
      }
      LoremIpsum.appendLoremIpsum(cap, this.m_rand, 20);
    }

    at = new ArrayList<>();
    e = 500;
    mo = new MemoryTextOutput();
    fs = null;
    try (final Graphic g = fig.body()) {
      at.add(g.getTransform());

      r = g.getBounds();
      k = 0;
      do {
        switch (this.m_rand.nextInt(15)) {
          case 0: {
            i = this.m_fonts.length;
            if (i > 0) {
              g.setFont((fs = this.m_fonts[this.m_rand.nextInt(i)])
                  .getFont());
              break;
            }
          }
          case 1: {
            i = this.m_colors.length;
            if (i > 0) {
              g.setColor(this.m_colors[this.m_rand.nextInt(i)]);
              break;
            }
          }
          case 2: {
            i = this.m_strokes.length;
            if (i > 0) {
              g.setStroke(this.m_strokes[this.m_rand.nextInt(i)]);
              break;
            }
          }
          case 3: {
            trans: for (;;) {
              try {
                switch (this.m_rand.nextInt(3)) {
                  case 0: {
                    g.shear((-1d + (2d * this.m_rand.nextDouble())),
                        (-1d + (2d * this.m_rand.nextDouble())));
                    break;
                  }
                  case 1: {
                    g.rotate(Math.PI
                        * (-1d + (2d * this.m_rand.nextDouble())));
                    break;
                  }
                  default: {
                    g.scale(//
                        (1d + ((this.m_rand.nextDouble() * 0.1d) - 0.05d)),//
                        (1d + ((this.m_rand.nextDouble() * 0.1d) - 0.05d)));
                  }
                }
                at.add(g.getTransform());
                break trans;
              } catch (final Throwable tt) {
                //
              }
            }
            break;
          }
          case 4: {
            if (at.size() > 1) {
              at.remove(at.size() - 1);
              g.setTransform(at.get(at.size() - 1));
            }
            break;
          }

          case 5: {
            a = this.__randomPoint(r);
            b = this.__randomPoint(r);
            g.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
            k++;
            break;
          }
          case 6: {
            a = this.__randomPoint(r);
            b = this.__randomPoint(r);
            g.drawRect(a.getX(), a.getY(), (b.getX() - a.getX()),
                (b.getY() - a.getY()));
            k++;
            break;
          }
          case 7: {
            a = this.__randomPoint(r);
            b = this.__randomPoint(r);
            g.fillRect(a.getX(), a.getY(), (b.getX() - a.getX()),
                (b.getY() - a.getY()));
            k++;
            break;
          }
          case 8: {
            a = this.__randomPoint(r);
            b = this.__randomPoint(r);
            g.drawArc(a.getX(), a.getY(), (b.getX() - a.getX()),
                (b.getY() - a.getY()),
                (Math.PI * 2d * this.m_rand.nextDouble()),
                (Math.PI * 2d * this.m_rand.nextDouble()));
            k++;
            break;
          }
          case 9: {
            a = this.__randomPoint(r);
            b = this.__randomPoint(r);
            g.fillArc(a.getX(), a.getY(), (b.getX() - a.getX()),
                (b.getY() - a.getY()),
                (Math.PI * 2d * this.m_rand.nextDouble()),
                (Math.PI * 2d * this.m_rand.nextDouble()));
            k++;
            break;
          }
          case 10: {
            d = this.__randomPolygon(r);
            g.drawPolygon(d[0], d[1], d[0].length);
            k++;
            break;
          }
          case 11: {
            d = this.__randomPolygon(r);
            g.fillPolygon(d[0], d[1], d[0].length);
            k++;
            break;
          }
          case 12: {
            d = this.__randomPolygon(r);
            g.drawPolyline(d[0], d[1], d[0].length);
            k++;
            break;
          }

          default: {
            a = this.__randomPoint(r);
            mo.clear();
            if ((fs != null) && (this.m_rand.nextBoolean())) {
              fs.appendDescription(ETextCase.AT_SENTENCE_START, mo, false);
              mo.append(' ');
            }
            LoremIpsum.appendLoremIpsum(mo, this.m_rand,
                this.m_rand.nextInt(10) + 1);
            g.drawString(mo.toString(), a.getX(), a.getY());
            mo.clear();
            k++;
          }
        }
      } while ((k < e) || (this.m_rand.nextInt(10) > 0));
    }
  }

  /**
   * create an equation
   * 
   * @param sb
   *          the section body
   */
  private final void __createEquation(final ISectionBody sb) {
    try (final IEquation equ = sb.equation(ELabelType.AUTO)) {
      this.__do(DocumentExample.EQUATION);
      this.__label(equ);
      this.__fillMath(equ, 1, 1, 5);
    }
  }

  /**
   * create an inline math
   * 
   * @param sb
   *          the text
   */
  private final void __createInlineMath(final IComplexText sb) {
    try (final IMath equ = sb.inlineMath()) {
      this.__do(DocumentExample.INLINE_MATH);
      this.__fillMath(equ, 1, 1, 4);
    }
  }

  /**
   * fill a mathematics context
   * 
   * @param math
   *          the maths context
   * @param minArgs
   *          the minimum arguments
   * @param maxArgs
   *          the maximum arguments
   * @param depth
   *          the depth
   */
  private final void __fillMath(final IMath math, final int minArgs,
      final int maxArgs, final int depth) {
    int args;

    args = 0;
    do {
      switch (this.m_rand.nextInt((depth <= 0) ? 3 : 23)) {

        case 0: {
          try (final IText text = math.name()) {
            text.append("id"); //$NON-NLS-1$
            text.append(this.m_rand.nextInt(10));
          }
          break;
        }

        case 1: {
          try (final IText text = math.number()) {
            text.append(this.m_rand.nextInt(201) - 100);
          }
          break;
        }

        case 2: {
          try (final IText text = math.text()) {
            text.append("text_"); //$NON-NLS-1$
            text.append(this.m_rand.nextInt(10));
          }
          break;
        }

        case 3: {
          try (final IMath nm = math.add()) {
            this.__fillMath(nm, 2, 20, (depth - 1));
          }
          break;
        }

        case 4: {
          try (final IMath nm = math.sub()) {
            this.__fillMath(nm, 2, 20, (depth - 1));
          }
          break;
        }

        case 5: {
          try (final IMath nm = math.mul()) {
            this.__fillMath(nm, 2, 20, (depth - 1));
          }
          break;
        }

        case 6: {
          try (final IMath nm = math.div()) {
            this.__fillMath(nm, 2, 2, (depth - 1));
          }
          break;
        }

        case 7: {
          try (final IMath nm = math.divInline()) {
            this.__fillMath(nm, 2, 2, (depth - 1));
          }
          break;
        }

        case 8: {
          try (final IMath nm = math.mod()) {
            this.__fillMath(nm, 2, 2, (depth - 1));
          }
          break;
        }

        case 9: {
          try (final IMath nm = math.log()) {
            this.__fillMath(nm, 2, 2, (depth - 1));
          }
          break;
        }

        case 10: {
          try (final IMath nm = math.ln()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 11: {
          try (final IMath nm = math.ld()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 12: {
          try (final IMath nm = math.lg()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 13: {
          try (final IMath nm = math.sqrt()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 14: {
          try (final IMath nm = math.root()) {
            this.__fillMath(nm, 2, 2, (depth - 1));
          }
          break;
        }

        case 15: {
          try (final IMath nm = math
              .compare(DocumentExample.COMP[this.m_rand
                  .nextInt(DocumentExample.COMP.length)])) {
            this.__fillMath(nm, 2, 2, (depth - 1));
          }
          break;
        }

        case 16: {
          try (final IMath nm = math.inBraces()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 17: {
          try (final IMath nm = math.negate()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 18: {
          try (final IMath nm = math.abs()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 19: {
          try (final IMath nm = math.factorial()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 20: {
          try (final IMath nm = math.sin()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 21: {
          try (final IMath nm = math.cos()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        case 22: {
          try (final IMath nm = math.tan()) {
            this.__fillMath(nm, 1, 1, (depth - 1));
          }
          break;
        }

        default: {
          throw new IllegalStateException();
        }
      }

      args++;
    } while ((args < minArgs)
        || ((args < maxArgs) && this.m_rand.nextBoolean()));

  }
}
