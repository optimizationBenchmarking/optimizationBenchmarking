package org.optimizationBenchmarking.utils.chart.impl.jfree;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator.Attribute;
import java.text.AttributedString;
import java.util.Collections;
import java.util.Map;

import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.PieDataset;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledDataElement;

/** the pie title label generator */
class _JFreeChartPieLabelGenerator implements PieSectionLabelGenerator {

  /** the plot */
  private final PiePlot m_plot;

  /**
   * create
   *
   * @param plot
   *          the plot
   */
  _JFreeChartPieLabelGenerator(final PiePlot plot) {
    super();
    this.m_plot = plot;
  }

  /**
   * {@inheritDoc} Class {@link org.jfree.chart.plot.PiePlot3D} ignores the
   * method
   * {@link org.jfree.chart.labels.PieSectionLabelGenerator#generateAttributedSectionLabel(PieDataset, Comparable)}
   * , so we need to set the label font explicitly.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final String generateSectionLabel(final PieDataset dataset,
      final Comparable key) {
    _JFreeChartDataset<CompiledDataElement> data;
    CompiledDataElement element;
    Font font;
    Color color;
    String title;
    int idx;

    if (dataset instanceof _JFreeChartDataset) {
      data = ((_JFreeChartDataset) dataset);
      idx = data.indexOf(key);
      if (idx >= 0) {
        element = data.m_data.get(idx);
        if (element != null) {
          title = this._getTitle(element);
          if (title != null) {
            font = element.getTitleFont();
            if (font != null) {
              this.m_plot.setLabelFont(font);
            }
            color = element.getColor();
            if (color != null) {
              this.m_plot.setLabelPaint(color);
            }
          }
          return title;
        }
      }
    }

    return null;
  }

  /**
   * Get the title of an element
   *
   * @param element
   *          the element
   * @return the title
   */
  String _getTitle(final CompiledDataElement element) {
    return element.getTitle();
  }

  /**
   * Get the attributes of a compiled element
   *
   * @param element
   *          the element
   * @return the attributes
   */
  Map<? extends Attribute, ?> _getAttributes(
      final CompiledDataElement element) {
    return Collections.singletonMap(TextAttribute.FONT,
        element.getTitleFont());
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public final AttributedString generateAttributedSectionLabel(
      final PieDataset dataset, final Comparable key) {
    _JFreeChartDataset<CompiledDataElement> data;
    CompiledDataElement element;
    String title;
    int idx;

    if (dataset instanceof _JFreeChartDataset) {
      data = ((_JFreeChartDataset) dataset);
      idx = data.indexOf(key);
      if (idx >= 0) {
        element = data.m_data.get(idx);
        if (element != null) {
          title = this._getTitle(element);
          if (title != null) {
            return new AttributedString(title,
                this._getAttributes(element));
          }
        }
      }
    }

    return null;
  }

}
