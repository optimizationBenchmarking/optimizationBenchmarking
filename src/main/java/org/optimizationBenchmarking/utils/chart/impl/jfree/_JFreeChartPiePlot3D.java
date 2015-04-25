package org.optimizationBenchmarking.utils.chart.impl.jfree;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.PieDataset;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * We must subclass {@link org.jfree.chart.plot.PiePlot3D} because it
 * ignores the method
 * {@link org.jfree.chart.labels.PieSectionLabelGenerator#generateAttributedSectionLabel(PieDataset, Comparable)}
 * &hellip;
 */
final class _JFreeChartPiePlot3D extends PiePlot3D {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * create
   * 
   * @param dataset
   *          the data set
   */
  _JFreeChartPiePlot3D(final _JFreeChartPieDataset dataset) {
    super(dataset);
  }

  /**
   * Returns a collection of legend items for the pie chart. Overridden
   * version from {@link org.jfree.chart.plot.PiePlot} because it ignores
   * the method
   * {@link org.jfree.chart.labels.PieSectionLabelGenerator#generateAttributedSectionLabel(PieDataset, Comparable)}
   * 
   * @return The legend items (never <code>null</code>).
   */
  @SuppressWarnings("rawtypes")
  @Override
  public LegendItemCollection getLegendItems() {
    final PieDataset dataset;
    final LegendItemCollection result;
    final List keys;
    final Iterator iterator;
    final PieSectionLabelGenerator legendLabelGenerator;
    final _JFreeChartPieDataset data;
    Comparable key;
    Number n;
    Paint paint;
    Paint outlinePaint;
    Stroke outlineStroke;
    LegendItem item;
    String label;
    Shape shape;
    boolean include;
    MemoryTextOutput mto;
    AttributedString string;
    AttributedCharacterIterator attit;
    Color color;
    double v;
    Font font;
    int index;

    result = new LegendItemCollection();

    dataset = this.getDataset();
    if (dataset == null) {
      return result;
    }

    if (dataset instanceof _JFreeChartPieDataset) {
      data = ((_JFreeChartPieDataset) dataset);
    } else {
      data = null;
    }

    keys = dataset.getKeys();
    shape = this.getLegendItemShape();
    iterator = keys.iterator();
    legendLabelGenerator = this.getLegendLabelGenerator();
    mto = null;

    while (iterator.hasNext()) {
      key = (Comparable) iterator.next();
      n = dataset.getValue(key);
      if (n == null) {
        include = (!(this.getIgnoreNullValues()));
      } else {
        v = n.doubleValue();
        if (v == 0.0d) {
          include = (!(this.getIgnoreZeroValues()));
        } else {
          include = (v > 0.0d);
        }
      }

      if (include) {
        string = legendLabelGenerator.generateAttributedSectionLabel(
            dataset, key);
        if (string == null) {
          font = null;
          label = legendLabelGenerator.generateSectionLabel(dataset, key);
        } else {
          if (mto == null) {
            mto = new MemoryTextOutput();
          }
          attit = string.getIterator();
          font = ((Font) (attit.getAttribute(TextAttribute.FONT)));
          mto.append(attit);
          attit = null;
          label = mto.toString();
          mto.clear();
        }

        if (label != null) {
          paint = this.lookupSectionPaint(key);
          outlinePaint = this.lookupSectionOutlinePaint(key);
          outlineStroke = this.lookupSectionOutlineStroke(key);

          color = null;
          if (data != null) {
            index = data.getIndex(key);
            if (index >= 0) {
              color = data.m_data.get(index).getColor();
            }
          }

          item = new LegendItem(label, label, null, null, true, shape,
              true, paint, true, outlinePaint, outlineStroke,
              false, // line not visible
              _JFreeChartRenderer.EMPTY_SHAPE,
              _JFreeChartRenderer.BASIC_STROKE, Color.BLACK);
          if (string != null) {
            item.setLabelFont(font);
          }
          if (color != null) {
            item.setLabelPaint(color);
          }
          item.setDataset(this.getDataset());
          item.setSeriesIndex(dataset.getIndex(key));
          item.setSeriesKey(key);
          result.add(item);
        }
      }
    }
    return result;
  }

}
