package org.optimizationBenchmarking.experimentation.evaluation.system.impl.description.instances;

import java.util.List;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.Feature;
import org.optimizationBenchmarking.experimentation.data.FeatureSet;
import org.optimizationBenchmarking.experimentation.data.Instance;
import org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups.PropertyValueGroup;
import org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups.PropertyValueGrouper;
import org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups.PropertyValueGroups;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.DescriptionJob;
import org.optimizationBenchmarking.utils.chart.spec.ELegendMode;
import org.optimizationBenchmarking.utils.chart.spec.IDataScalar;
import org.optimizationBenchmarking.utils.chart.spec.IPieChart;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.impl.FigureSizeParser;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IFigure;
import org.optimizationBenchmarking.utils.document.spec.IFigureSeries;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorStyle;

/** A job of the instance information module. */
final class _InstanceInformationJob extends DescriptionJob {

  /** the default figure size */
  private static final EFigureSize DEFAULT_FIGURE_SIZE = EFigureSize.PAGE_3_PER_ROW;

  /** the property value groupers */
  private final PropertyValueGrouper<Feature, Instance>[] m_groupers;

  /** the figure size */
  private final EFigureSize m_figureSize;

  /**
   * Create the instance information job
   * 
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @param config
   *          the configuration
   */
  @SuppressWarnings("unchecked")
  _InstanceInformationJob(final ExperimentSet data,
      final Configuration config, final Logger logger) {
    super(data, logger);

    final FeatureSet featureSet;
    final PropertyValueGrouper<Feature, Instance>[] groupers;
    final ArraySetView<Feature> features;
    final int size;
    int i;

    featureSet = data.getFeatures();
    features = featureSet.getData();
    size = features.size();
    this.m_groupers = groupers = new PropertyValueGrouper[size];
    for (i = size; (--i) >= 0;) {
      groupers[i] = PropertyValueGrouper
          .configure(features.get(i), config);
    }

    this.m_figureSize = config.get(FigureSizeParser.PARAM_FIGURE_SIZE,
        FigureSizeParser.INSTANCE,
        _InstanceInformationJob.DEFAULT_FIGURE_SIZE);
  }

  /**
   * Make the feature figure series
   * 
   * @param data
   *          the data
   * @param section
   *          the section
   * @param body
   *          the section body
   */
  @SuppressWarnings("unchecked")
  private final void __makeFeatureFigures(final ExperimentSet data,
      final ISection section, final ISectionBody body) {
    final PropertyValueGroups<Instance>[] groups;
    final List<ColorStyle> colors;
    String featureName;
    int index, groupIndex, maxColors;

    groups = new PropertyValueGroups[this.m_groupers.length];
    maxColors = 0;
    index = 0;
    for (final Feature feature : data.getFeatures().getData()) {
      groups[index] = this.m_groupers[index].get(feature);
      maxColors = Math.max(maxColors, groups[index].getGroups().size());
      index++;
    }

    colors = section.getStyles().allocateColors(maxColors);

    try (final IFigureSeries series = body.figureSeries(null,
        this.m_figureSize, "instanceFeaturePieCharts")) { //$NON-NLS-1$
      try (final IComplexText caption = series.caption()) {
        caption.append("Instances per feature setting.");//$NON-NLS-1$
      }

      index = 0;
      for (final PropertyValueGroups<Instance> grouper : groups) {
        featureName = grouper.getOwner().getName();
        try (final IFigure figure = series.figure(null, featureName)) {
          try (final IComplexText caption = figure.caption()) {
            caption.append("Feature "); //$NON-NLS-1$
            caption.append(featureName);
          }
          try (final IPieChart pie = figure.pieChart()) {
            groupIndex = 0;
            pie.setLegendMode(ELegendMode.SHOW_COMPLETE_LEGEND);
            for (final PropertyValueGroup<Instance> group : grouper
                .getGroups()) {
              try (final IDataScalar slice = pie.slice()) {
                slice.setColor(colors.get(groupIndex++));
                slice.setTitle(group.getValuesString());
                slice.setData(group.getElements().size());
              }
            }
          }
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void doMain(final ExperimentSet data,
      final ISectionContainer sectionContainer, final Logger logger) {

    try (final ISection section = sectionContainer.section(null)) {
      try (final IPlainText title = section.title()) {
        title.append("Instance Information"); //$NON-NLS-1$
      }

      try (final ISectionBody body = section.body()) {
        this.__makeFeatureFigures(data, section, body);
      }
    }
  }
}
