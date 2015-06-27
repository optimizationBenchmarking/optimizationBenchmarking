package org.optimizationBenchmarking.experimentation.evaluation.impl.description.instances;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups.PropertyValueGroup;
import org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups.PropertyValueGrouper;
import org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups.PropertyValueGroups;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSet;
import org.optimizationBenchmarking.experimentation.evaluation.impl.abstr.DescriptionJob;
import org.optimizationBenchmarking.utils.chart.spec.ELegendMode;
import org.optimizationBenchmarking.utils.chart.spec.IDataScalar;
import org.optimizationBenchmarking.utils.chart.spec.IPieChart;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.impl.FigureSizeParser;
import org.optimizationBenchmarking.utils.document.impl.SemanticComponentUtils;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IFigure;
import org.optimizationBenchmarking.utils.document.spec.IFigureSeries;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorStyle;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.InTextNumberAppender;

/** A job of the instance information module. */
final class _InstanceInformationJob extends DescriptionJob {

  /** the default figure size */
  private static final EFigureSize DEFAULT_FIGURE_SIZE = EFigureSize.PAGE_3_PER_ROW;

  /** the property value groupers */
  private final PropertyValueGrouper[] m_groupers;

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
  _InstanceInformationJob(final IExperimentSet data,
      final Configuration config, final Logger logger) {
    super(data, logger);

    final IFeatureSet featureSet;
    final PropertyValueGrouper[] groupers;
    final ArrayListView<? extends IFeature> features;
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
   * @param label
   *          the label to be used for the main figure
   */
  private final void __makeFeatureFigures(final IExperimentSet data,
      final ISection section, final ISectionBody body, final ILabel label) {
    final PropertyValueGroups[] groups;
    final ArrayList<ColorStyle> colors;
    final StyleSet styles;
    String featureName;
    int index, groupIndex, needed;

    groups = new PropertyValueGroups[this.m_groupers.length];
    colors = new ArrayList<>();
    index = 0;
    styles = section.getStyles();
    for (final IFeature feature : data.getFeatures().getData()) {
      groups[index] = this.m_groupers[index].get(feature);
      needed = groups[index].getData().size();
      while (colors.size() < needed) {
        colors.add(styles.allocateColor());
      }
      index++;
    }

    if (groups.length > 1) {

      try (final IFigureSeries series = body.figureSeries(label,
          this.m_figureSize, "instanceFeaturePieCharts")) { //$NON-NLS-1$
        try (final IComplexText caption = series.caption()) {
          caption.append("Instances per feature setting.");//$NON-NLS-1$
        }

        index = 0;
        for (final PropertyValueGroups grouper : groups) {
          featureName = grouper.getOwner().getName();
          try (final IFigure figure = series.figure(null, featureName)) {
            try (final IComplexText caption = figure.caption()) {
              caption.append("Feature "); //$NON-NLS-1$
              caption.append(featureName);
            }
            try (final IPieChart pie = figure.pieChart()) {
              groupIndex = 0;
              pie.setLegendMode(ELegendMode.SHOW_COMPLETE_LEGEND);
              for (final PropertyValueGroup<?> group : grouper.getData()) {
                try (final IDataScalar slice = pie.slice()) {
                  slice.setColor(colors.get(groupIndex++));
                  slice.setTitle(group.getCriterionString());
                  slice.setData(group.getInstances().getData().size());
                }
              }
            }
          }
        }
      }

    } else {

      featureName = groups[0].getOwner().getName();
      try (final IFigure figure = body.figure(label, this.m_figureSize, //
          (featureName + "PieChart"))) { //$NON-NLS-1$
        try (final IComplexText caption = figure.caption()) {
          caption.append("Feature "); //$NON-NLS-1$
          caption.append(featureName);
        }
        try (final IPieChart pie = figure.pieChart()) {
          groupIndex = 0;
          pie.setLegendMode(ELegendMode.SHOW_COMPLETE_LEGEND);
          for (final PropertyValueGroup<?> group : groups[0].getData()) {
            try (final IDataScalar slice = pie.slice()) {
              slice.setColor(colors.get(groupIndex++));
              slice.setTitle(group.getCriterionString());
              slice.setData(group.getInstances().getData().size());
            }
          }
        }
      }

    }

  }

  /** {@inheritDoc} */
  @Override
  protected final void doMain(final IExperimentSet data,
      final ISectionContainer sectionContainer, final Logger logger) {
    final ILabel label;
    final int instances, features;

    try (final ISection section = sectionContainer.section(null)) {
      try (final IPlainText title = section.title()) {
        title.append("Instance Information"); //$NON-NLS-1$
      }

      label = sectionContainer.createLabel(ELabelType.FIGURE);

      try (final ISectionBody body = section.body()) {
        body.append("In ");//$NON-NLS-1$
        body.reference(ETextCase.IN_SENTENCE, ESequenceMode.COMMA, label);
        body.append(//
        " we illustrate the relative amount of benchmark runs per instance feature. In total, we have ");//$NON-NLS-1$

        instances = data.getInstances().getData().size();
        InTextNumberAppender.INSTANCE.appendTo(instances,
            ETextCase.IN_SENTENCE, body);
        body.append(" benchmark instance");//$NON-NLS-1$
        if (instances > 1) {
          body.append("s and each of them is characterized by ");//$NON-NLS-1$
        } else {
          body.append(" characterized by ");//$NON-NLS-1$
        }
        features = this.m_groupers.length;
        InTextNumberAppender.INSTANCE.appendTo(features,
            ETextCase.IN_SENTENCE, body);
        if (features > 1) {
          body.append(" features, namely ");//$NON-NLS-1$
          SemanticComponentUtils.printNames(
              ESequenceMode.AND,//
              data.getFeatures().getData(), true, true,
              ETextCase.IN_SENTENCE, body);
        } else {
          body.append(" feature, namely ");//$NON-NLS-1$
          SemanticComponentUtils
              .printLongAndShortNameIfDifferent(data.getFeatures()
                  .getData().get(0), body, ETextCase.IN_SENTENCE);
        }
        body.append(//
        ".  The slices in the pie chart");//$NON-NLS-1$
        if (features > 1) {
          body.append('s');
        }
        body.append(//
        " are the bigger, the more benchmark instances have the associated feature value, in comparison to the other values. If a slice is bigger than other slices, this therefore means that the used benchmark instances focus on investigating that feature while less runs are applied to the other features.");//$NON-NLS-1$

        this.__makeFeatureFigures(data, section, body, label);
      }
    }
  }
}
