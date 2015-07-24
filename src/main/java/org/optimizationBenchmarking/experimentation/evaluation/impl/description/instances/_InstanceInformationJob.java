package org.optimizationBenchmarking.experimentation.evaluation.impl.description.instances;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.attributes.OnlySharedInstances;
import org.optimizationBenchmarking.experimentation.attributes.OnlyUsedInstances;
import org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups.PropertyValueGroup;
import org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups.PropertyValueGrouper;
import org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups.PropertyValueGroups;
import org.optimizationBenchmarking.experimentation.attributes.statistics.propertyExtremals.ExtremalPropertyValues;
import org.optimizationBenchmarking.experimentation.attributes.statistics.propertyExtremals.ExtremalPropertyValuesGetter;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;
import org.optimizationBenchmarking.experimentation.evaluation.impl.abstr.DescriptionJob;
import org.optimizationBenchmarking.utils.chart.spec.ELegendMode;
import org.optimizationBenchmarking.utils.chart.spec.IDataScalar;
import org.optimizationBenchmarking.utils.chart.spec.IPieChart;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.impl.FigureSizeParser;
import org.optimizationBenchmarking.utils.document.impl.SemanticComponentUtils;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IFigure;
import org.optimizationBenchmarking.utils.document.spec.IFigureSeries;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.IList;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorStyle;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.ISequenceable;
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
   * Make the feature figure series of pie charts which show how many
   * instances belong to a given feature value.
   *
   * @param data
   *          the data
   * @param styles
   *          the styles
   * @param body
   *          the section body
   * @param mainCaption
   *          the caption to use for the main figure(series)
   * @param pathComponent
   *          the path component
   * @param colors
   *          the colors
   * @return the label to be used for the main figure
   */
  private final ILabel __makeFeatureFigures(final IExperimentSet data,
      final StyleSet styles, final ISectionBody body,
      final String mainCaption, final String pathComponent,
      final ArrayList<ColorStyle> colors) {
    final PropertyValueGroups[] groups;
    final ILabel label;
    String mainPath;
    String featureName;
    int index, groupIndex;

    groups = new PropertyValueGroups[this.m_groupers.length];
    index = 0;
    for (final IFeature feature : data.getFeatures().getData()) {
      groups[index] = this.m_groupers[index].get(feature);
      index++;
    }

    if (groups.length > 1) {
      mainPath = "instanceFeaturePieCharts"; //$NON-NLS-1$
      if (pathComponent != null) {
        mainPath += '/' + pathComponent;
      }
      try (final IFigureSeries series = body.figureSeries(ELabelType.AUTO,
          this.m_figureSize, mainPath)) {
        label = series.getLabel();
        try (final IComplexText caption = series.caption()) {
          caption.append(mainCaption);
        }

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
                  if (groupIndex >= colors.size()) {
                    colors.add(styles.allocateColor());
                  }
                  slice.setColor(colors.get(groupIndex));
                  slice.setTitle(group.getCriterionString());
                  slice.setData(group.getInstances().getData().size());
                  ++groupIndex;
                }
              }
            }
          }
        }
      }

    } else {
      featureName = groups[0].getOwner().getName();
      mainPath = (featureName + "PieChart"); //$NON-NLS-1$
      if (pathComponent != null) {
        mainPath += '/' + pathComponent;
      }
      try (final IFigure figure = body.figure(ELabelType.AUTO,
          this.m_figureSize, mainPath)) {
        label = figure.getLabel();
        try (final IComplexText caption = figure.caption()) {
          caption.append("Feature "); //$NON-NLS-1$
          caption.append(featureName);
        }
        try (final IPieChart pie = figure.pieChart()) {
          groupIndex = 0;
          pie.setLegendMode(ELegendMode.SHOW_COMPLETE_LEGEND);
          for (final PropertyValueGroup<?> group : groups[0].getData()) {
            try (final IDataScalar slice = pie.slice()) {
              if (groupIndex >= colors.size()) {
                colors.add(styles.allocateColor());
              }
              slice.setColor(colors.get(groupIndex));
              slice.setTitle(group.getCriterionString());
              slice.setData(group.getInstances().getData().size());
              ++groupIndex;
            }
          }
        }
      }
    }

    return label;
  }

  /**
   * Discuss the instance features, i.e., how many instances belong to a
   * given feature
   *
   * @param data
   *          the experiment data
   * @param dataForSome
   *          the instances for which at least some experiments have data
   * @param dataForAll
   *          the instances for which all experiments have data
   * @param styles
   *          the styles
   * @param body
   *          the section body to write to
   */
  private final void __discussRelativeFeatureValueAmounts(
      final IExperimentSet data, final IExperimentSet dataForSome,
      final IExperimentSet dataForAll, final StyleSet styles,
      final ISectionBody body) {
    final ILabel labelMain, labelForSome, labelForAll;
    final int features;
    final int allSize, forSomeSize, forAllSize;
    ArrayList<ColorStyle> colors;

    allSize = data.getInstances().getData().size();
    if (allSize <= 1) {
      return;
    }

    forSomeSize = dataForSome.getInstances().getData().size();
    forAllSize = dataForAll.getInstances().getData().size();

    colors = new ArrayList<>();
    if ((forAllSize < allSize) && (forSomeSize > 0)) {
      if (forAllSize > 0) {
        labelMain = this
            .__makeFeatureFigures(
                data,
                styles,
                body,//
                "The fractions of all available instances with specific feature values.", //$NON-NLS-1$
                "existing", colors);//$NON-NLS-1$
      } else {
        labelMain = null;
      }

      if (forSomeSize < allSize) {
        labelForSome = this
            .__makeFeatureFigures(
                dataForSome,
                styles,
                body,//
                "The fractions of instances with specific feature values for which at least some experiments have runs.", //$NON-NLS-1$
                "some", colors);//$NON-NLS-1$
      } else {
        labelForSome = null;
      }

      if (forAllSize < forSomeSize) {
        labelForAll = this
            .__makeFeatureFigures(
                dataForAll,
                styles,
                body,//
                "The fractions of instances with specific feature values for which all experiments have runs.", //$NON-NLS-1$
                "all", colors);//$NON-NLS-1$
      } else {
        labelForAll = null;
      }

    } else {
      labelMain = this.__makeFeatureFigures(data, styles, body,//
          "The fractions of instances with specific feature values.", //$NON-NLS-1$
          null, colors);
      labelForSome = labelForAll = null;
    }
    colors = null;
    body.appendLineBreak();

    body.append("In ");//$NON-NLS-1$
    body.reference(ETextCase.IN_SENTENCE, ESequenceMode.COMMA, labelMain);
    body.append(//
    " we illustrate the relative amount of benchmark instances per feature value over all ");//$NON-NLS-1$
    InTextNumberAppender.INSTANCE.appendTo(allSize, ETextCase.IN_SENTENCE,
        body);
    features = this.m_groupers.length;
    body.append(" benchmark instances. The slices in the pie chart");//$NON-NLS-1$
    if ((features > 1) || (forAllSize < allSize)) {
      body.append('s');
    }
    body.append(//
    " are the bigger, the more benchmark instances have the associated feature value in comparison to the other values. The more similar the pie sizes are, the more evenly are the benchmark instances distributed over the benchmark feature values, which may be a good idea for fair experimentation.");//$NON-NLS-1$

    if ((forAllSize < allSize) && (forSomeSize > 0)) {

      body.append(//
      " Since experimental runs have not been performed for every instance, we draw the same plot");//$NON-NLS-1$
      if (features > 1) {
        body.append('s');
      }
      body.append(" again in ");//$NON-NLS-1$

      drawForAll: {
        if (forSomeSize < allSize) {
          body.reference(ETextCase.IN_SENTENCE, ESequenceMode.COMMA,
              labelForSome);
          body.append(", but only for the ");//$NON-NLS-1$
          InTextNumberAppender.INSTANCE.appendTo(forSomeSize,
              ETextCase.IN_SENTENCE, body);

          body.append((forSomeSize > 1) ? " instances" : //$NON-NLS-1$
              " instance"); //$NON-NLS-1$

          body.append(" for which ");//$NON-NLS-1$
          if (forAllSize < forSomeSize) {
            body.append("runs have been conducted in at least some experiments");//$NON-NLS-1$
          } else {
            body.append("all experiments contain runs.");//$NON-NLS-1$
            break drawForAll;
          }

          if (forAllSize > 0) {
            body.append(", and in ");//$NON-NLS-1$
          } else {
            body.append('.');
            break drawForAll;
          }
        }

        body.reference(ETextCase.IN_SENTENCE, ESequenceMode.COMMA,
            labelForAll);
        body.append(" for the ");//$NON-NLS-1$
        InTextNumberAppender.INSTANCE.appendTo(forAllSize,
            ETextCase.IN_SENTENCE, body);
        body.append((forAllSize > 1) ? " instances" : //$NON-NLS-1$
            " instance"); //$NON-NLS-1$
        body.append(", for which all experiments contain runs.");//$NON-NLS-1$
      }

      body.append(//
      " The difference between the charts can provide information about whether a certain instance feature is under-represented in the experiments.");//$NON-NLS-1$
    }
  }

  /**
   * Discuss the instances, how many there are, for which we have data,
   * etc.
   *
   * @param data
   *          the experiment data
   * @param dataForSome
   *          the instances for which at least some experiments have data
   * @param dataForAll
   *          the instances for which all experiments have data
   * @param styles
   *          the styles
   * @param body
   *          the section body to write to
   */
  private final void __discussInstances(final IExperimentSet data,
      final IExperimentSet dataForSome, final IExperimentSet dataForAll,
      final StyleSet styles, final ISectionBody body) {
    final ArrayListView<? extends IInstance> instances;
    final IInstanceSet instanceSet, allDataInstanceSet, someDataInstanceSet;
    final int featureSize, instanceSize, allDataInstanceSize, someDataInstanceSize;
    ArrayList<IInstance> missing;

    instanceSet = data.getInstances();
    instances = instanceSet.getData();
    instanceSize = instances.size();
    allDataInstanceSet = dataForAll.getInstances();
    allDataInstanceSize = allDataInstanceSet.getData().size();
    someDataInstanceSet = dataForSome.getInstances();
    someDataInstanceSize = someDataInstanceSet.getData().size();

    body.append("Experiments were conducted on "); //$NON-NLS-1$

    if (someDataInstanceSize <= 0) {
      body.append("none!");//$NON-NLS-1$
    } else {
      InTextNumberAppender.INSTANCE.appendTo(someDataInstanceSize,
          ETextCase.IN_SENTENCE, body);
    }

    if (someDataInstanceSize < instanceSize) {
      body.append(" of the "); //$NON-NLS-1$
      InTextNumberAppender.INSTANCE.appendTo(instanceSize,
          ETextCase.IN_SENTENCE, body);
      body.append(" available "); //$NON-NLS-1$
    }

    if (someDataInstanceSize > 1) {
      body.append(" benchmark instances, which can be distinguished by "); //$NON-NLS-1$
      featureSize = data.getFeatures().getData().size();
      InTextNumberAppender.INSTANCE.appendTo(featureSize,
          ETextCase.IN_SENTENCE, body);
      body.append((featureSize > 1) ? " features." : " feature."); //$NON-NLS-1$//$NON-NLS-2$
    } else {
      body.append((instanceSize > 1) ? " benchmark instances."//$NON-NLS-1$
          : " benchmark instance."); //$NON-NLS-1$
    }

    if (someDataInstanceSize <= 0) {
      body.append(//
      " This will be a major problem when evaluating results, many things will fail."); //$NON-NLS-1$
      return;
    }

    missing = null;
    if (someDataInstanceSize < instanceSize) {
      if ((instanceSize - someDataInstanceSize) > someDataInstanceSize) {
        body.append(" Data is only available for instances "); //$NON-NLS-1$
        SemanticComponentUtils.printNames(ESequenceMode.AND,
            someDataInstanceSet.getData(), true, false,
            ETextCase.IN_SENTENCE, body);
        body.append('.');
      } else {
        missing = new ArrayList<>();
        for (final IInstance inst : instanceSet.getData()) {
          if (someDataInstanceSet.find(inst.getName()) == null) {
            missing.add(inst);
          }
        }

        body.append(" For "); //$NON-NLS-1$
        InTextNumberAppender.INSTANCE.appendTo(missing.size(),
            ETextCase.IN_SENTENCE, body);
        body.append(" of them"); //$NON-NLS-1$
        if (missing.size() < 10) {
          body.append(' ');
          try (final IPlainText text = body.inBraces()) {
            SemanticComponentUtils.printNames(ESequenceMode.AND, missing,
                true, false, ETextCase.IN_SENTENCE, text);
          }
        }
        missing.clear();
        body.append(", no data has been collected."); //$NON-NLS-1$
      }
    }

    if (allDataInstanceSize < someDataInstanceSize) {
      if (allDataInstanceSize <= 0) {
        body.append(//
        " There is no single benchmark instance for which all experiments contain runs. This will be a major problem when evaluating results, many things will fail."); //$NON-NLS-1$
        return;
      }

      if (someDataInstanceSize < instanceSize) {
        body.append(" Furthermore, "); //$NON-NLS-1$
      } else {
        body.append(" However, "); //$NON-NLS-1$
      }

      if ((someDataInstanceSize - allDataInstanceSize) > allDataInstanceSize) {
        body.append("data is only available in "); //$NON-NLS-1$
        try (final IText emph = body.emphasize()) {
          emph.append("all"); //$NON-NLS-1$
        }
        body.append(" experiments for "); //$NON-NLS-1$
        InTextNumberAppender.INSTANCE.appendTo(allDataInstanceSize,
            ETextCase.IN_SENTENCE, body);
        body.append(" instances "); //$NON-NLS-1$
        SemanticComponentUtils.printNames(ESequenceMode.AND,
            allDataInstanceSet.getData(), true, false,
            ETextCase.IN_SENTENCE, body);
        body.append(", while at least some experiments have no data for the rest."); //$NON-NLS-1$
      } else {
        if (missing == null) {
          missing = new ArrayList<>();
        }
        for (final IInstance inst : someDataInstanceSet.getData()) {
          if (allDataInstanceSet.find(inst.getName()) == null) {
            missing.add(inst);
          }
        }
        body.append("at least some experiments do not have data for instances"); //$NON-NLS-1$
        if (missing.size() < 10) {
          body.append(' ');
          SemanticComponentUtils.printNames(ESequenceMode.AND, missing,
              true, false, ETextCase.IN_SENTENCE, body);
        }
        missing = null;
        body.append('.');
      }
    }
  }

  /**
   * Discuss the numerical properties
   *
   * @param data
   *          the experiment data
   * @param dataForSome
   *          the instances for which at least some experiments have data
   * @param dataForAll
   *          the instances for which all experiments have data
   * @param styles
   *          the styles
   * @param body
   *          the section body to write to
   */
  private final void __discussFeatureValues(final IExperimentSet data,
      final IExperimentSet dataForSome, final IExperimentSet dataForAll,
      final StyleSet styles, final ISectionBody body) {
    final int count;
    ISequenceable[] current, prev;
    ArrayListView<? extends IFeature> features;
    ArrayList<ISequenceable> print;
    IFeature feature;
    int i, size;
    EPrimitiveType type;
    ExtremalPropertyValues<IFeatureValue> extreme;
    ISequenceable seq;
    boolean further, needsSpace;

    current = null;
    count = data.getFeatures().getData().size();

    body.appendLineBreak();

    print = new ArrayList<>();
    needsSpace = further = false;
    for (final IExperimentSet es : new IExperimentSet[] { data,
        dataForSome, dataForAll }) {
      if (es == null) {
        break;
      }
      prev = current;
      current = new ISequenceable[count];
      features = es.getFeatures().getData();

      if (features.size() > 0) {

        for (i = count; (--i) >= 0;) {
          feature = features.get(i);
          type = feature.getPrimitiveType();
          if ((type != null) && (type.isNumber())) {
            extreme = ExtremalPropertyValuesGetter.EXTREMAL_FEATURE_VALUES
                .get(feature);
            if ((prev == null) || (!(EComparison.equals(//
                ((_NumericalFeatureSequenceable) (prev[i])).m_extremal,//
                extreme)))) {
              seq = new _NumericalFeatureSequenceable(feature, extreme);
              print.add(seq);
            } else {
              seq = prev[i];
            }
          } else {
            if ((prev == null)
                || (((_OrdinalFeatureSequenceable) (prev[i])).m_feature
                    .getData().size() > feature.getData().size())) {
              seq = new _OrdinalFeatureSequenceable(feature);
              print.add(seq);
            } else {
              seq = prev[i];
            }
          }
          current[i] = seq;
        }

        if (print.isEmpty()) {
          continue;
        }

        if (needsSpace) {
          body.append(' ');
        }

        if (es == data) {
          body.append("There benchmark instances are characterized by ");//$NON-NLS-1$
          InTextNumberAppender.INSTANCE.appendTo(count,
              ETextCase.IN_SENTENCE, body);
          body.append((count > 1) ? " features: " : //$NON-NLS-1$
              " feature: ");//$NON-NLS-1$
        } else {
          if (es == dataForSome) {
            body.append(//
            "If we only consider the ");//$NON-NLS-1$
            InTextNumberAppender.INSTANCE.appendTo(es.getInstances()
                .getData().size(), ETextCase.IN_SENTENCE, body);
            body.append(//
            " benchmark instances for which at least some experimental runs have been conducted, the ranges of ");//$NON-NLS-1$

            i = print.size();
            if (i >= count) {
              if (i == 1) {
                body.append("the only"); //$NON-NLS-1$
              } else {
                body.append("all"); //$NON-NLS-1$
              }
            } else {
              InTextNumberAppender.INSTANCE.appendTo(i,
                  ETextCase.IN_SENTENCE, body);
            }
            body.append((i > 1) ? " features change as follows: " : //$NON-NLS-1$
                " feature changes as follows: ");//$NON-NLS-1$
            further = true;
          } else {
            if (further) {
              body.append(//
              "Furthermore, i");//$NON-NLS-1$
            } else {
              body.append('I');
            }
            body.append(//
            "f we only look at the ");//$NON-NLS-1$
            InTextNumberAppender.INSTANCE.appendTo(es.getInstances()
                .getData().size(), ETextCase.IN_SENTENCE, body);
            body.append(//
            " benchmark instances for which runs exist in all experiments, the ranges of "); //$NON-NLS-1$
            i = print.size();
            if (i >= count) {
              if (i == 1) {
                body.append("the only"); //$NON-NLS-1$
              } else {
                body.append("all"); //$NON-NLS-1$
              }
            } else {
              InTextNumberAppender.INSTANCE.appendTo(i,
                  ETextCase.IN_SENTENCE, body);
            }
            body.append((i > 1) ? " features change as follows: " : //$NON-NLS-1$
                " feature changes as follows: ");//$NON-NLS-1$
          }
        }

        size = print.size();
        if (size > 1) {
          try (final IList list = body.itemization()) {
            --size;
            for (i = 0; i <= size; i++) {
              seq = print.get(i);
              try (final IText text = list.item()) {
                seq.toSequence((i <= 0), (i >= size),
                    ETextCase.IN_SENTENCE, text);
              }
            }
          }
          needsSpace = false;
        } else {
          print.get(0).toSequence(true, true, ETextCase.IN_SENTENCE, body);
          body.append('.');
          needsSpace = true;
        }

        print.clear();
      } else {
        // no experiments
        if (needsSpace) {
          body.append(' ');
        }
        if (es == dataForAll) {
          body.append(//
          "Not single instance exists for which runs have been performed in all experiments."); //$NON-NLS-1$
          return;
        }
        body.append(//
        "No experimental runs have been performed at all."); //$NON-NLS-1$
        return;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void doMain(final IExperimentSet data,
      final ISectionContainer sectionContainer, final Logger logger) {
    final StyleSet styles;
    final IExperimentSet dataForAll, dataForSome;

    try (final ISection section = sectionContainer.section(null)) {
      try (final IPlainText title = section.title()) {
        title.append("Instance Information"); //$NON-NLS-1$
      }

      styles = section.getStyles();

      dataForAll = OnlySharedInstances.INSTANCE.get(data);
      dataForSome = OnlyUsedInstances.INSTANCE.get(data);

      try (final ISectionBody body = section.body()) {
        this.__discussInstances(data, dataForSome, dataForAll, styles,
            body);
        this.__discussFeatureValues(data, dataForSome, dataForAll, styles,
            body);
        this.__discussRelativeFeatureValueAmounts(data, dataForSome,
            dataForAll, styles, body);
      }
    }
  }
}
