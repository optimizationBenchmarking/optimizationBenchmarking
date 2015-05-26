package org.optimizationBenchmarking.utils.chart.impl.export;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.chart.impl.abstr.ChartDriver;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledAxis;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledDataScalar;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledLine2D;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledLineChart2D;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledPieChart;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * The driver for exporting chart data to text.
 */
public final class ExportChartDriver extends ChartDriver {

  /** the character signifying a starting line comment */
  public static final char COMMENT_LINE_BEGIN = '#';

  /** the character separating columns */
  public static final char COLUMN_SEPARATOR = '\t';

  /** the character after a special comment */
  public static final char COMMENT_SPECIAL = ':';

  /** the string indicating the chart title */
  public static final String CHART_TITLE = "chart-title"; //$NON-NLS-1$

  /** the string indicating the chart type */
  public static final String CHART_TYPE = "chart-type"; //$NON-NLS-1$
  /** the line 2d chart type */
  public static final String CHART_TYPE_LINE_2D = "line2D"; //$NON-NLS-1$
  /** the pie chart type */
  public static final String CHART_TYPE_PIE = "pie"; //$NON-NLS-1$

  /**
   * the string indicating total number of lines in the line chart or
   * slices in a pie chart, etc.
   */
  public static final String CHART_SIZE = "chart-size"; //$NON-NLS-1$

  /** the string indicating the title of the x-axis */
  public static final String X_AXIS_TITLE = "x-axis-title"; //$NON-NLS-1$
  /** the string indicating the minimum of the x-axis */
  public static final String X_AXIS_MIN = "x-axis-min"; //$NON-NLS-1$
  /** the string indicating the maximum of the x-axis */
  public static final String X_AXIS_MAX = "x-axis-max"; //$NON-NLS-1$

  /** the string indicating the title of the y-axis */
  public static final String Y_AXIS_TITLE = "y-axis-title"; //$NON-NLS-1$
  /** the string indicating the minimum of the y-axis */
  public static final String Y_AXIS_MIN = "y-axis-min"; //$NON-NLS-1$
  /** the string indicating the maximum of the y-axis */
  public static final String Y_AXIS_MAX = "y-axis-max"; //$NON-NLS-1$

  /** the string indicating index of the current line (starts at 0) */
  public static final String LINE_INDEX = "line-index"; //$NON-NLS-1$
  /** the string indicating the number of points of the current line */
  public static final String LINE_SIZE = "line-size"; //$NON-NLS-1$
  /** the string indicating title of the current line */
  public static final String LINE_TITLE = "line-title"; //$NON-NLS-1$
  /**
   * the string indicating
   * {@link org.optimizationBenchmarking.utils.chart.spec.ELineType type}
   * of the current line
   */
  public static final String LINE_TYPE = "line-type"; //$NON-NLS-1$
  /** the amount of a slice */
  public static final String SLICE_AMOUNT = "slice-amount"; //$NON-NLS-1$
  /** the title of a slice */
  public static final String SLICE_TITLE = "slice-title"; //$NON-NLS-1$

  /** create */
  ExportChartDriver() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return true;
  }

  /**
   * Get the instance of the {@link ExportChartDriver}.
   *
   * @return the instance of the {@link ExportChartDriver}
   */
  public static final ExportChartDriver getInstance() {
    return __ExportDriverLoader.INSTANCE;
  }

  /**
   * a special comment
   *
   * @param parameter
   *          the parameter
   * @param value
   *          the value
   * @param out
   *          the text output to use
   */
  private static final void __special(final String parameter,
      final String value, final ITextOutput out) {
    if ((value != null) && (value.length() > 0)) {
      out.append(ExportChartDriver.COMMENT_LINE_BEGIN);
      out.append(' ');
      out.append(parameter);
      out.append(ExportChartDriver.COMMENT_SPECIAL);
      out.append(' ');
      out.append(value);
      out.appendLineBreak();
    }
  }

  /**
   * transform a double value to a string
   *
   * @param val
   *          the value
   * @return the string
   */
  private static final String __toString(final double val) {
    return SimpleNumberAppender.INSTANCE.toString(val,
        ETextCase.IN_SENTENCE);
  }

  /**
   * convert a Graphic to an instance of ITextOutput, if possible
   *
   * @param graphic
   *          the graphic
   * @return the text output
   */
  private static final ITextOutput __toText(final Graphic graphic) {
    if (graphic instanceof ITextOutput) {
      return ((ITextOutput) graphic);
    }
    throw new IllegalArgumentException(
        (((//
            "The chart driver '" + //$NON-NLS-1$
            TextUtils.className(ExportChartDriver.class)) + "' requires graphics which implement '") + //$NON-NLS-1$
            TextUtils.className(ITextOutput.class))
            + "' in order to work, since it is intended to export data to text rather than actually rendering charts.");//$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  protected final void renderLineChart2D(final CompiledLineChart2D chart,
      final Graphic graphic, final Logger logger) {
    final ITextOutput out;
    final String xAxisTitle, yAxisTitle, titles;
    final ArrayListView<CompiledLine2D> lines;
    CompiledAxis axis;
    IMatrix matrix;
    int index, i, m;
    boolean xIsLong, yIsLong;

    out = ExportChartDriver.__toText(graphic);

    ExportChartDriver.__special(ExportChartDriver.CHART_TYPE,
        ExportChartDriver.CHART_TYPE_LINE_2D, out);
    ExportChartDriver.__special(ExportChartDriver.CHART_TITLE,
        chart.getTitle(), out);

    lines = chart.getLines();
    ExportChartDriver.__special(ExportChartDriver.CHART_SIZE,
        Integer.toString(lines.size()), out);

    axis = chart.getXAxis();
    ExportChartDriver.__special(ExportChartDriver.X_AXIS_TITLE,
        (xAxisTitle = axis.getTitle()), out);
    ExportChartDriver.__special(ExportChartDriver.X_AXIS_MIN,
        ExportChartDriver.__toString(axis.getMinimum()), out);
    ExportChartDriver.__special(ExportChartDriver.X_AXIS_MAX,
        ExportChartDriver.__toString(axis.getMaximum()), out);

    axis = chart.getYAxis();
    ExportChartDriver.__special(ExportChartDriver.Y_AXIS_TITLE,
        (yAxisTitle = axis.getTitle()), out);
    ExportChartDriver.__special(ExportChartDriver.Y_AXIS_MIN,
        ExportChartDriver.__toString(axis.getMinimum()), out);
    ExportChartDriver.__special(ExportChartDriver.Y_AXIS_MAX,
        ExportChartDriver.__toString(axis.getMaximum()), out);

    if ((xAxisTitle != null) && (yAxisTitle != null)) {
      titles = (((ExportChartDriver.COMMENT_LINE_BEGIN + (' ' + xAxisTitle)) + ExportChartDriver.COLUMN_SEPARATOR) + yAxisTitle);
    } else {
      titles = null;
    }

    index = 0;
    for (final CompiledLine2D line : lines) {
      if (index > 0) {
        out.appendLineBreak();
        out.appendLineBreak();
      }
      ExportChartDriver.__special(ExportChartDriver.LINE_INDEX,
          Integer.toString(index++), out);
      ExportChartDriver.__special(ExportChartDriver.LINE_TYPE,
          String.valueOf(line.getType()), out);

      matrix = line.getData();
      m = matrix.m();
      ExportChartDriver.__special(ExportChartDriver.LINE_SIZE,
          String.valueOf(m), out);

      ExportChartDriver.__special(ExportChartDriver.LINE_TITLE,
          line.getTitle(), out);
      if (titles != null) {
        out.append(titles);
        out.appendLineBreak();
      }

      if (matrix.isIntegerMatrix()) {
        xIsLong = yIsLong = true;
      } else {
        xIsLong = matrix.selectColumns(0).isIntegerMatrix();
        yIsLong = matrix.selectColumns(1).isIntegerMatrix();
      }

      for (i = 0; i < m; i++) {
        if (xIsLong) {
          out.append(matrix.getLong(i, 0));
        } else {
          out.append(ExportChartDriver.__toString(matrix.getDouble(i, 0)));
        }
        out.append(ExportChartDriver.COLUMN_SEPARATOR);
        if (yIsLong) {
          out.append(matrix.getLong(i, 1));
        } else {
          out.append(ExportChartDriver.__toString(matrix.getDouble(i, 1)));
        }
        out.appendLineBreak();
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void renderPieChart(final CompiledPieChart chart,
      final Graphic graphic, final Logger logger) {
    final ITextOutput out;
    final ArrayListView<CompiledDataScalar> slices;
    Number number;
    String title;

    out = ExportChartDriver.__toText(graphic);
    ExportChartDriver.__special(ExportChartDriver.CHART_TYPE,
        ExportChartDriver.CHART_TYPE_PIE, out);
    ExportChartDriver.__special(ExportChartDriver.CHART_TITLE,
        chart.getTitle(), out);

    slices = chart.getSlices();
    ExportChartDriver.__special(ExportChartDriver.CHART_SIZE,
        Integer.toString(slices.size()), out);

    out.append(ExportChartDriver.COMMENT_LINE_BEGIN);
    out.append(' ');
    out.append(ExportChartDriver.SLICE_AMOUNT);
    out.append(ExportChartDriver.COLUMN_SEPARATOR);
    out.append(ExportChartDriver.SLICE_TITLE);
    out.appendLineBreak();

    for (final CompiledDataScalar scalar : slices) {
      number = scalar.getData();
      if ((NumericalTypes.getTypes(number) & NumericalTypes.IS_LONG) != 0) {
        out.append(number.longValue());
      } else {
        out.append(ExportChartDriver.__toString(number.doubleValue()));
      }
      title = scalar.getTitle();
      if ((title != null) && (title.length() > 0)) {
        out.append(ExportChartDriver.COLUMN_SEPARATOR);
        out.append(title);
      }
      out.appendLineBreak();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Export Chart Driver"; //$NON-NLS-1$
  }

  /** the loader */
  private static final class __ExportDriverLoader {

    /** the export chart driver */
    static final ExportChartDriver INSTANCE = new ExportChartDriver();
  }
}