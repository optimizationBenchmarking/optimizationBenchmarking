package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.attributes.OnlyUsedInstances;
import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.data.impl.ref.Parameter;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.experimentation.evaluation.impl.abstr.DocumentDriverOutput;
import org.optimizationBenchmarking.experimentation.evaluation.impl.abstr.StructuredIOInput;
import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data.EvaluationModules;
import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data.EvaluationModulesBuilder;
import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data.ModuleEntry;
import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.io.EvaluationXMLInput;
import org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluation;
import org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluationOutput;
import org.optimizationBenchmarking.experimentation.io.impl.ExperimentSetInputParser;
import org.optimizationBenchmarking.experimentation.io.spec.IExperimentSetInput;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthors;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibDateBuilder;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.impl.DocumentDriverParser;
import org.optimizationBenchmarking.utils.document.impl.SemanticComponentUtils;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBody;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBuilder;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;
import org.optimizationBenchmarking.utils.document.spec.IDocumentHeader;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.io.structured.spec.IIOJob;
import org.optimizationBenchmarking.utils.io.structured.spec.IInputJobBuilder;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.InTextNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/** The evaluation job */
final class _Evaluation extends _EvaluationSetup implements IEvaluation {

  /**
   * create the evaluation job
   *
   * @param builder
   *          the evaluation builder
   */
  _Evaluation(final _EvaluationBuilder builder) {
    super();
    this._assign(builder);
  }

  /**
   * If a configuration was set via
   * {@link #_setConfiguration(org.optimizationBenchmarking.utils.config.Configuration)}
   * , perform the configuration.
   *
   * @param logger
   *          the logger
   */
  private final void __configure(final Logger logger) {
    final Configuration config;

    config = this._getConfiguration();
    if (config == null) {
      return;
    }

    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      logger.config("Begin loading configuration " + config); //$NON-NLS-1$
    }

    this.__configureAuthors(config, logger);

    this.__configureInput(config, logger);
    _EvaluationSetup._checkEvaluationInput(this._getInput());

    this.__configureOutput(config, logger);
    _EvaluationSetup._checkEvaluationOutput(this._getOutput());

    this.__loadModules(config, logger);

    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      logger.config("Finished loading configuration."); //$NON-NLS-1$
    }
  }

  /**
   * Configure the authors
   *
   * @param config
   *          the configuration
   * @param logger
   *          the logger
   */
  private final void __configureAuthors(final Configuration config,
      final Logger logger) {
    ArrayListView<String> authors;
    Iterator<String> it;

    try {
      authors = config.getStringList(Evaluator.PARAM_AUTHORS, null);
      if ((authors != null) && (!(authors.isEmpty()))) {

        if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
          logger.config("Setting authors to " + authors); //$NON-NLS-1$
        }

        it = authors.iterator();
        if (it.hasNext()) {
          try (final BibAuthorsBuilder builder = new BibAuthorsBuilder()) {
            while (it.hasNext()) {
              try (final BibAuthorBuilder author = builder.author()) {
                author.setFamilyName(it.next());
                if (it.hasNext()) {
                  author.setPersonalName(it.next());
                }
              }
            }
            this._setAuthors(builder.getResult());
          }
        }
      }
    } catch (final RuntimeException re) {
      ErrorUtils.logError(logger,
          "Unrecoverable error while parsing the authors parameter.", //$NON-NLS-1$
          re, false, RethrowMode.AS_RUNTIME_EXCEPTION);
    }
  }

  /**
   * Configure the input source
   *
   * @param config
   *          the configuration
   * @param logger
   *          the logger
   */
  private final void __configureInput(final Configuration config,
      final Logger logger) {
    final IExperimentSetInput input;
    final IInputJobBuilder<ExperimentSetContext> builder;

    input = config.get(Evaluator.PARAM_INPUT_DRIVER,
        ExperimentSetInputParser.getInstance(), null);
    if (input != null) {

      if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
        logger.config("Input driver " + input + //$NON-NLS-1$
            " will now be used."); //$NON-NLS-1$
      }

      builder = input.use();

      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine(//
            "Job builder of input driver created, job will now be configured."); //$NON-NLS-1$
      }

      builder.configure(config);
      builder.setLogger(logger);

      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine("Job builder of input driver has been configured."); //$NON-NLS-1$
      }

      this._setInput(new StructuredIOInput(builder, logger));
    }
  }

  /**
   * Configure the output destination
   *
   * @param config
   *          the configuration
   * @param logger
   *          the logger
   */
  private final void __configureOutput(final Configuration config,
      final Logger logger) {
    final IDocumentDriver output;
    final IDocumentBuilder builder;

    output = config.get(Evaluator.PARAM_OUTPUT_DRIVER,
        DocumentDriverParser.getInstance(), null);
    if (output != null) {

      if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
        logger.config("Output driver " + output + //$NON-NLS-1$
            " will now be used."); //$NON-NLS-1$
      }

      builder = output.use();

      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine(//
            "Job builder of output driver created, job will now be configured."); //$NON-NLS-1$
      }

      builder.configure(config);
      builder.setLogger(logger);

      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine("Job builder of output driver has been configured."); //$NON-NLS-1$
      }
      this._setOutput(new DocumentDriverOutput(builder));
    }
  }

  /**
   * Load the modules based on the configuration.
   *
   * @param config
   *          the configuration
   * @param logger
   *          the logger
   */
  private final void __loadModules(final Configuration config,
      final Logger logger) {
    final IInputJobBuilder<EvaluationModulesBuilder> jobBuilder;
    final EvaluationXMLInput input;
    final IIOJob job;
    final EvaluationModules modules;

    input = EvaluationXMLInput.getInstance();
    if (input.areSourcesDefined(config)) {
      if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
        logger.config(//
            "Configuring evaluation process and module hierarchy from XML.");//$NON-NLS-1$
      }

      try (final EvaluationModulesBuilder builder = new EvaluationModulesBuilder()) {

        if (config != null) {
          builder.setRootConfiguration(config);
        }

        jobBuilder = input.use();
        jobBuilder.configure(config);
        jobBuilder.setLogger(logger);
        jobBuilder.setDestination(builder);

        if ((logger != null) && (logger.isLoggable(Level.FINE))) {
          logger.fine(//
              "Job builder for XML-based evaluation configuration has been configured."); //$NON-NLS-1$
        }

        job = jobBuilder.create();
        if (job == null) {
          throw new IllegalStateException(//
              "XML-based configuration loader created null job.");//$NON-NLS-1$
        }
        try {
          job.call();
        } catch (final IOException ioe) {
          throw new IllegalArgumentException(//
              "I/O error during evaluation process configuration.", //$NON-NLS-1$
              ioe);
        }
        if ((logger != null) && (logger.isLoggable(Level.FINE))) {
          logger.fine(//
              "XML-based evaluation configuration job has completed successfully, now building data structures."); //$NON-NLS-1$
        }

        modules = builder.getResult();
      }

      if (modules == null) {
        throw new IllegalStateException(//
            "The modules data structure loaded from XML is null, although the loading process completed without error?"); //$NON-NLS-1$
      }

      this._setBaseConfiguration(modules.getConfiguration());
      for (final ModuleEntry entry : modules.getEntries()) {
        this._addModule(entry.getModule(), entry.getConfiguration());
      }

      if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
        logger.fine(//
            "Finished loading module data from XML."); //$NON-NLS-1$
      }
    }
  }

  /**
   * Obtain the data for the execution.
   *
   * @param logger
   *          the logger
   * @return the experiment set
   */
  private final IExperimentSet __loadData(final Logger logger) {
    IExperimentSet data;
    MemoryTextOutput text;

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info("Now loading and analyzing input data."); //$NON-NLS-1$
    }

    try {
      data = this._takeInput().getExperimentSet();
    } catch (final Exception ex) {
      data = null;
      ErrorUtils
          .logError(
              logger,
              "Unrecoverable error during the process of obtaining the input data.", //$NON-NLS-1$
              ex, false, RethrowMode.AS_RUNTIME_EXCEPTION);
      return null;// will never be reached
    }

    if (data == null) {
      throw new IllegalArgumentException("Input data cannot be null."); //$NON-NLS-1$
    }

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      text = new MemoryTextOutput();
      text.append("Input data "); //$NON-NLS-1$
      text.append(data);
      text.append(" successfully loaded -"); //$NON-NLS-1$
    } else {
      text = null;
    }
    _Evaluation.__checkExperimentData(data, text);
    if ((text != null) && (logger != null)
        && (logger.isLoggable(Level.INFO))) {
      logger.info(text.toString());
      text = null;
    }

    return data;
  }

  /**
   * Check whether an experiment data set is OK. Throw an exception if not.
   *
   * @param data
   *          the data
   * @param message
   *          store a status message in here (if not {@code null}).
   */
  private static final void __checkExperimentData(
      final IExperimentSet data, final MemoryTextOutput message) {
    final ArrayListView<? extends IExperiment> experimentSet;
    ArrayListView<? extends IInstanceRuns> instanceRuns;
    ArrayListView<? extends IRun> runs;
    ArrayListView<? extends IInstance> instanceSet;
    ArrayListView<? extends IFeature> featureSet;
    ArrayListView<? extends IParameter> parameterSet;
    int i, j, k;

    if (data == null) {
      throw new IllegalArgumentException("Input data cannot be null."); //$NON-NLS-1$
    }

    if (((experimentSet = data.getData()) == null)
        || ((i = experimentSet.size()) <= 0)) {
      throw new IllegalArgumentException(
          "Input data contains no experiment."); //$NON-NLS-1$
    }

    if (message != null) {
      message.append(" Discovered "); //$NON-NLS-1$
      message.append(i);
      message.append(" experiment"); //$NON-NLS-1$
      if (i > 1) {
        message.append('s');
      }
      message.append(" with ");//$NON-NLS-1$
    }

    parameterSet = data.getParameters().getData();
    j = 0;
    if (parameterSet != null) {
      if ((j = parameterSet.size()) <= 0) {
        if (i > 1) {
          throw new IllegalArgumentException(//
              "Parameter set cannot be empty if multiple instances exist.");//$NON-NLS-1$
        }
      }
    } else {
      throw new IllegalArgumentException(//
          "Parameter set cannot be null.");//$NON-NLS-1$
    }

    if (message != null) {
      message.append(j);
      message.append(" parameter"); //$NON-NLS-1$
      if (j > 1) {
        message.append('s');
      }
      if (j > 0) {
        message.append('(');
      }
    }

    j = 0;
    for (final IParameter param : parameterSet) {
      j++;
      if (param == null) {
        throw new IllegalArgumentException("The " + j + //$NON-NLS-1$
            "th parameter is null.");//$NON-NLS-1$
      }
      if (message != null) {
        if (j > 1) {
          message.append(',');
          message.append(' ');
        }
        message.append(param.getName());
      }
    }

    if (message != null) {
      if (j > 0) {
        message.append(')');
      }
      message.append(':');
    }

    i = 0;
    for (final IExperiment ex : experimentSet) {
      i++;
      if (ex == null) {
        throw new IllegalArgumentException(
            "The " + i + "th experiment is null.");//$NON-NLS-1$//$NON-NLS-2$
      }

      if (((instanceRuns = ex.getData()) == null)
          || ((j = instanceRuns.size()) <= 0)) {
        throw new IllegalArgumentException("The " + i + "th experiment (" + //$NON-NLS-1$//$NON-NLS-2$
            ex.getName() + ") contains no run sets.");//$NON-NLS-1$
      }
      if (message != null) {
        message.append(" Experiment '");//$NON-NLS-1$
        message.append(ex.getName());
        message.append(" has ");//$NON-NLS-1$
        message.append(j);
        message.append(" run sets");//$NON-NLS-1$

      }

      j = 0;
      for (final IInstanceRuns instance : instanceRuns) {
        j++;
        if (instance == null) {
          throw new IllegalArgumentException(//
              "The " + j + //$NON-NLS-1$
                  "th instance run set of experiment '" //$NON-NLS-1$
                  + ex.getName() + "' is null.");//$NON-NLS-1$
        }
        if (((runs = instance.getData()) == null)
            || ((k = runs.size()) <= 0)) {
          throw new IllegalArgumentException(//
              "The instance run set for instance " + //$NON-NLS-1$
                  instance.getInstance().getName() + //
                  " of experiment '" //$NON-NLS-1$
                  + ex.getName() + "' is empty.");//$NON-NLS-1$
        }
        if (message != null) {
          message.append(',');
          message.append(' ');
          message.append(k);
          message.append(" runs for instance ");//$NON-NLS-1$
          message.append(instance.getInstance().getName());
        }
      }

      if (message != null) {
        message.append('.');
      }
    }

    instanceSet = data.getInstances().getData();
    if ((instanceSet == null) || ((i = instanceSet.size()) <= 0)) {
      throw new IllegalArgumentException(//
          "Instance set cannot be empty.");//$NON-NLS-1$
    }
    featureSet = data.getFeatures().getData();
    j = 0;
    if (featureSet != null) {
      if ((j = featureSet.size()) <= 0) {
        if (i > 1) {
          throw new IllegalArgumentException(//
              "Feature set cannot be empty if multiple instances exist.");//$NON-NLS-1$
        }
      }
    } else {
      throw new IllegalArgumentException(//
          "Feature set cannot be null.");//$NON-NLS-1$
    }

    if (message != null) {
      if (i > 1) {
        message.append(" There are ");//$NON-NLS-1$
        message.append(i);
        message.append(" benchmark instances with ");//$NON-NLS-1$
      } else {
        message.append("There is one benchmark instance with ");//$NON-NLS-1$
      }
    }

    i = 0;
    for (final IInstance inst : instanceSet) {
      i++;
      if (inst == null) {
        throw new IllegalArgumentException(//
            "The " + i + "th instance is null");//$NON-NLS-1$//$NON-NLS-2$
      }
      if (message != null) {
        if (i > 1) {
          message.append(',');
          message.append(' ');
        }
        message.append(inst.getName());
      }
    }

    if (message != null) {
      message.append(") with ");//$NON-NLS-1$
      message.append(j);
      message.append(" feature");//$NON-NLS-1$
      if (j != 1) {
        message.append('s');
      }
      if (j > 0) {
        message.append(' ');
        message.append('(');
      }
    }

    j = 0;
    for (final IFeature feature : featureSet) {
      j++;
      if (feature == null) {
        throw new IllegalArgumentException(//
            "The " + j + "th feature is null");//$NON-NLS-1$//$NON-NLS-2$
      }
      if (message != null) {
        if (j > 1) {
          message.append(',');
          message.append(' ');
        }
        message.append(feature.getName());
      }
    }

    if (message != null) {
      if (j > 0) {
        message.append(')');
      }
      message.append('.');
    }
  }

  /**
   * Obtain the document for the output.
   *
   * @param logger
   *          the logger
   * @return the destination document
   */
  private final IDocument __createDocument(final Logger logger) {
    final IEvaluationOutput output;
    final IFileProducerListener listener;
    IDocument doc;

    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine("Now allocating destination document."); //$NON-NLS-1$
    }

    try {
      listener = this._takeFileProducerListener();
      output = this._takeOutput();
      if ((listener != null) && (output instanceof DocumentDriverOutput)) {
        ((DocumentDriverOutput) output).setFileProducerListener(listener);
      }
      doc = output.getDocument();
    } catch (final Exception error) {
      doc = null;
      ErrorUtils
          .logError(
              logger,
              "Unrecoverable error during the process of allocating the output document.", //$NON-NLS-1$
              error, false, RethrowMode.AS_RUNTIME_EXCEPTION);
    }

    if (doc == null) {
      throw new IllegalStateException("Output document is null.");//$NON-NLS-1$
    }

    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine("Destination document "//$NON-NLS-1$
          + doc.toString() + //
          " successfully allocated."); //$NON-NLS-1$
    }

    return doc;
  }

  /**
   * make the authors
   *
   * @return the authors
   */
  private final BibAuthors __makeAuthors() {
    final Logger logger;
    BibAuthors authors;

    authors = this._takeAuthors();
    if ((authors == null) || (authors.isEmpty())) {
      try (final BibAuthorsBuilder builder = new BibAuthorsBuilder()) {
        try (final BibAuthorBuilder author = builder.author()) {
          author.setFamilyName("Anonymous");//$NON-NLS-1$
          author.setPersonalName("Anne"); //$NON-NLS-1$
        }
        authors = builder.getResult();
      }
    }

    logger = this._getLogger();
    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info("Author(s) set to " + authors); //$NON-NLS-1$
    }

    return authors;
  }

  /**
   * Write the summary of the document
   *
   * @param modules
   *          the modules
   * @param set
   *          the experiment set
   * @param summary
   *          the summary destination
   * @param logger
   *          the logger
   */
  private static final void __summary(final _MainJob modules,
      final IExperimentSet set, final IPlainText summary,
      final Logger logger) {
    final ArrayListView<? extends IExperiment> experiments;
    final int experimentSize, instanceSize;
    final IParameter param;
    Object name;
    HashSet<String> names;

    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine("Writing abstract to output document."); //$NON-NLS-1$
    }

    summary.append(//
        "This is the evaluation report on "); //$NON-NLS-1$

    experiments = set.getData();
    experimentSize = experiments.size();
    param = set.getParameters().find(Parameter.PARAMETER_ALGORITHM_NAME);
    algoNames: {

      if (experimentSize == 1) {
        summary.append(" one algorithm"); //$NON-NLS-1$
        if (param != null) {
          name = experiments.get(0).getParameterSetting().get(param);
          if ((name != null) && (name instanceof String)) {
            summary.append(": ");//$NON-NLS-1$
            summary.append((String) (name));
            break algoNames;
          }
        } else {
          summary.append(", namely ");//$NON-NLS-1$
          experiments.get(0)
              .printShortName(summary, ETextCase.IN_SENTENCE);
          break algoNames;
        }

      }

      InTextNumberAppender.INSTANCE.appendTo(experimentSize,
          ETextCase.IN_SENTENCE, summary);
      summary.append(" experiments"); //$NON-NLS-1$
      if (param != null) {
        names = null;
        for (final IExperiment experiment : experiments) {
          name = experiment.getParameterSetting().get(param);
          if ((name != null) && (name instanceof String)) {
            if (names == null) {
              names = new HashSet<>();
            }
            names.add((String) (name));
          }
        }

        if (names != null) {
          if (names.size() <= 1) {
            summary.append(" with the algorithm "); //$NON-NLS-1$
            summary.append(names.iterator().next());
            break algoNames;
          }
          summary.append(" with the algorithms "); //$NON-NLS-1$
          ESequenceMode.AND.appendSequence(ETextCase.IN_SENTENCE, names,
              true, summary);
          break algoNames;
        }
      }

      summary.append(", namely "); //$NON-NLS-1$
      SemanticComponentUtils.printNames(ESequenceMode.AND, experiments,
          true, false, ETextCase.IN_SENTENCE, summary);
    }

    summary.append(" on "); //$NON-NLS-1$
    instanceSize = OnlyUsedInstances.INSTANCE.get(set).getInstances()
        .getData().size();
    InTextNumberAppender.INSTANCE.appendTo(instanceSize,
        ETextCase.IN_SENTENCE, summary);
    summary.append(" benchmark instance"); //$NON-NLS-1$
    if (instanceSize > 0) {
      summary.append('s');
    }

    summary.append(//
        ". This report has been generated with the "); //$NON-NLS-1$
    Evaluator.getInstance()._printInfo(summary);
    summary.append('.');

    modules.summary(summary);
  }

  /**
   * create the document header
   *
   * @param modules
   *          the modules
   * @param authors
   *          the authors
   * @param set
   *          the data
   * @param header
   *          the header
   * @param logger
   *          the logger
   */
  private static final void __header(final _MainJob modules,
      final BibAuthors authors, final IExperimentSet set,
      final IDocumentHeader header, final Logger logger) {
    final ArrayListView<? extends IExperiment> data;
    final int size;
    final IParameter param;
    final Object name;

    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine("Beginning to write header of output document."); //$NON-NLS-1$
    }

    try (final IPlainText title = header.title()) {
      title.append("Evaluation Report on "); //$NON-NLS-1$

      data = set.getData();
      size = data.size();
      if (size == 1) {
        single: {
          param = set.getParameters().find(
              Parameter.PARAMETER_ALGORITHM_NAME);
          if (param != null) {
            name = data.get(0).getParameterSetting().get(param);
            if ((name != null) && (name instanceof String)) {
              title.append((String) name);
              break single;
            }
          }
          title.append(" One Algorithm"); //$NON-NLS-1$
        }
      } else {
        InTextNumberAppender.INSTANCE.appendTo(size, ETextCase.IN_TITLE,
            title);
        title.append(" Experiments"); //$NON-NLS-1$
      }
    }

    try (final BibAuthorsBuilder authorsBuilder = header.authors()) {
      authorsBuilder.addAuthors(authors);
    }

    try (final BibDateBuilder bd = header.date()) {
      bd.fromNow();
    }

    try (final IPlainText summary = header.summary()) {
      _Evaluation.__summary(modules, set, summary, logger);
    }

    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine("Finished to write header of output document."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void run() {//
    final IExperimentSet data;
    final _MainJob root;
    final BibAuthors authors;
    Logger logger;
    Configuration config;

    logger = this._getLogger();
    if (logger == null) {
      config = this._getConfiguration();
      if (config != null) {
        logger = config.getLogger(Configuration.PARAM_LOGGER, logger);
        config = null;
      }
    }
    if (logger == null) {
      logger = Configuration.getGlobalLogger();
    }
    this._setLogger(logger);

    this.__configure(logger);

    data = this.__loadData(logger);
    if (data == null) {
      return;
    }

    root = _ModulesBuilder._buildModules(this, data, logger);
    if (root == null) {
      throw new IllegalArgumentException("Root module cannot be null."); //$NON-NLS-1$
    }

    authors = this.__makeAuthors();

    try (final IDocument doc = this.__createDocument(logger)) {
      root.initialize(doc);

      try (final IDocumentHeader header = doc.header()) {
        _Evaluation.__header(root, authors, data, header, logger);
      }

      try (final IDocumentBody body = doc.body()) {
        root._bodyJobs(body);
      }

      try (final IDocumentBody footer = doc.footer()) {
        root._footerJobs(footer);
      }
    }
  }

}
