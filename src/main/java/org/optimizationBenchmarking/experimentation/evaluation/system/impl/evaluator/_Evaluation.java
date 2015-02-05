package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.Experiment;
import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.data.Feature;
import org.optimizationBenchmarking.experimentation.data.Instance;
import org.optimizationBenchmarking.experimentation.data.InstanceRuns;
import org.optimizationBenchmarking.experimentation.data.Parameter;
import org.optimizationBenchmarking.experimentation.data.Run;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.DocumentDriverOutput;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.StructuredIOInput;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluation;
import org.optimizationBenchmarking.experimentation.io.spec.IExperimentInput;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthors;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibDateBuilder;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBody;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBuilder;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;
import org.optimizationBenchmarking.utils.document.spec.IDocumentHeader;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.io.structured.spec.IInputJobBuilder;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.InTextNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

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
    final MemoryTextOutput mto;

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

    this.__configureModules(config, logger);
    if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
      mto = new MemoryTextOutput();
      mto.append("Finished loading configuration ");//$NON-NLS-1$
      config.toText(mto);
      mto.append(". The list of modules contains ");//$NON-NLS-1$
    } else {
      mto = null;
    }
    _ModulesBuilder._checkModules(this._getModules(), mto);

    if ((mto != null) && (logger != null)
        && (logger.isLoggable(Level.CONFIG))) {
      mto.append('.');
      logger.config(mto.toString());
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
      if (authors != null) {

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
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        logger.log(Level.SEVERE,//
            "Unrecoverable error while parsing the authors parameter.", //$NON-NLS-1$
            re);
      }
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
    final IExperimentInput input;
    final IInputJobBuilder<ExperimentSetContext> builder;

    input = config.getInstance(Evaluator.PARAM_INPUT_DRIVER,
        IExperimentInput.class, null);
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

    output = config.getInstance(Evaluator.PARAM_OUTPUT_DRIVER,
        IDocumentDriver.class, null);
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
  private final void __configureModules(final Configuration config,
      final Logger logger) {
    final IInputJobBuilder<_EvaluationSetup> builder;
    final _EvaluationXMLInput input;

    input = _EvaluationXMLInput.getInstance();
    if (input.areSourcesDefined(config)) {
      if ((logger != null) && (logger.isLoggable(Level.CONFIG))) {
        logger.config(//
            "Configuring evaluation process and module hierarchy from XML.");//$NON-NLS-1$
      }

      builder = input.use();

      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine(//
            "Job builder for XML-based evaluation configuration created and will now be configured."); //$NON-NLS-1$
      }

      builder.configure(config);
      builder.setLogger(logger);
      builder.setDestination(this);

      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine(//
            "Job builder for XML-based evaluation configuration has been configured."); //$NON-NLS-1$
      }

      try {
        builder.create().call();
      } catch (final IOException ioe) {
        throw new IllegalArgumentException(//
            "I/O error during evaluation process configuration.", //$NON-NLS-1$
            ioe);
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
  private final ExperimentSet __loadData(final Logger logger) {
    ExperimentSet data;
    MemoryTextOutput text;

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info("Now loading and analyzing input data."); //$NON-NLS-1$
    }

    try {
      data = this._takeInput().getExperimentSet();
    } catch (final Exception ex) {
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        logger
            .log(
                Level.SEVERE,
                "Unrecoverable error during the process of obtaining the input data.", //$NON-NLS-1$
                ex);
      }
      data = null;
      throw new IllegalStateException("Could not load input data.",//$NON-NLS-1$
          ex);
    }

    if (data == null) {
      throw new IllegalArgumentException("Input data cannot be null."); //$NON-NLS-1$
    }

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      text = new MemoryTextOutput();
      text.append("Input data successfully loaded - "); //$NON-NLS-1$
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
      final ExperimentSet data, final MemoryTextOutput message) {
    final ArraySetView<Experiment> experimentSet;
    ArraySetView<InstanceRuns> instanceRuns;
    ArraySetView<Run> runs;
    ArraySetView<Instance> instanceSet;
    ArraySetView<Feature> featureSet;
    ArraySetView<Parameter> parameterSet;
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
    for (final Parameter param : parameterSet) {
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
    for (final Experiment ex : experimentSet) {
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
      for (final InstanceRuns instance : instanceRuns) {
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
    for (final Instance inst : instanceSet) {
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
    for (final Feature feature : featureSet) {
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
    IDocument doc;

    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine("Now allocating destination document."); //$NON-NLS-1$
    }

    try {
      doc = this._takeOutput().getDocument();
    } catch (final Exception error) {
      doc = null;
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        logger
            .log(
                Level.SEVERE,
                "Unrecoverable error during the process of allocating the output document.", //$NON-NLS-1$
                error);
      }
      throw new IllegalStateException("Could not create output document.",//$NON-NLS-1$
          error);
    }

    if (doc == null) {
      throw new IllegalStateException("Output document is null.");//$NON-NLS-1$
    }

    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine("Destination document successfully allocated."); //$NON-NLS-1$
    }

    return doc;
  }

  /**
   * make the authors
   * 
   * @return the authors
   */
  private final BibAuthors __makeAuthors() {
    final BibAuthors authors;

    authors = this._takeAuthors();
    if ((authors == null) || (authors.isEmpty())) {
      try (final BibAuthorsBuilder builder = new BibAuthorsBuilder()) {
        try (final BibAuthorBuilder author = builder.author()) {
          author.setFamilyName("Anonymous");//$NON-NLS-1$
          author.setPersonalName("Anne"); //$NON-NLS-1$
        }
        return builder.getResult();
      }
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
  private static final void __summary(final _Modules modules,
      final ExperimentSet set, final IPlainText summary,
      final Logger logger) {
    final ArraySetView<Experiment> data;
    final int size;
    final Parameter param;
    Object name;
    HashSet<String> names;

    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine("Writing abstract to output document."); //$NON-NLS-1$
    }

    summary.append(//
        "This document contains an automatically-generated evaluation report on "); //$NON-NLS-1$

    data = set.getData();
    size = data.size();
    param = set.getParameters().find(Parameter.PARAMETER_ALGORITHM_NAME);
    if (size == 1) {
      summary.append(" one algorithm"); //$NON-NLS-1$      
      if (param != null) {
        name = data.get(0).parameters().get(param);
        if ((name != null) && (name instanceof String)) {
          summary.append(": ");//$NON-NLS-1$
          summary.append((String) (name));
        }
      }
    } else {
      InTextNumberAppender.INSTANCE.appendTo(size, ETextCase.IN_SENTENCE,
          summary);
      summary.append(" experiments"); //$NON-NLS-1$
      if (param != null) {
        names = null;
        for (final Experiment experiment : data) {
          name = experiment.parameters().get(param);
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
          } else {
            summary.append(" with the algorithms "); //$NON-NLS-1$
            ESequenceMode.AND.appendSequence(ETextCase.IN_SENTENCE, names,
                true, summary);
          }
        }
      }
    }

    summary.append('.');

    modules._doSummaryJobs(set, summary);
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
  private static final void __header(final _Modules modules,
      final BibAuthors authors, final ExperimentSet set,
      final IDocumentHeader header, final Logger logger) {
    final ArraySetView<Experiment> data;
    final int size;
    final Parameter param;
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
            name = data.get(0).parameters().get(param);
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

    header.authors().addAuthors(authors);

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
    final Logger logger;
    final ExperimentSet data;
    final _Modules root;
    final BibAuthors authors;

    logger = this._getLogger();
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
      root._doInitJobs(data, doc);

      try (final IDocumentHeader header = doc.header()) {
        _Evaluation.__header(root, authors, data, header, logger);
      }

      try (final IDocumentBody body = doc.body()) {
        root._bodyJobs(data, body);
      }

      try (final IDocumentBody footer = doc.footer()) {
        root._footerJobs(data, footer);
      }
    }
  }

}
