package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.HashSet;

import org.optimizationBenchmarking.experimentation.data.Experiment;
import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.Parameter;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluation;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthors;
import org.optimizationBenchmarking.utils.bibliography.data.BibDateBuilder;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBody;
import org.optimizationBenchmarking.utils.document.spec.IDocumentHeader;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.InTextNumberAppender;

/** The evaluation job */
final class _Evaluation implements IEvaluation {

  /** the document */
  private final IDocument m_doc;
  /** the data */
  private final ExperimentSet m_data;
  /** the modules */
  private final _Modules m_modules;

  /** the authors */
  private final BibAuthors m_authors;

  /**
   * create the evaluation job
   * 
   * @param doc
   *          the document
   * @param data
   *          the data
   * @param modules
   *          the modules
   * @param authors
   *          the authors
   */
  _Evaluation(final IDocument doc, final ExperimentSet data,
      final _Modules modules, final BibAuthors authors) {
    super();

    _EvaluationBuilder._checkData(data);
    _EvaluationBuilder._checkDocument(doc);
    _EvaluationBuilder._checkAuthors(authors);

    if (modules == null) {
      _EvaluationBuilder._noModuleError();
    }

    this.m_doc = doc;
    this.m_data = data;
    this.m_modules = modules;
    this.m_authors = authors;
  }

  /**
   * Write the summary of the document
   * 
   * @param summary
   */
  private final void __summary(final IPlainText summary) {
    final ExperimentSet set;
    final ArraySetView<Experiment> data;
    final int size;
    final Parameter param;
    Object name;
    HashSet<String> names;

    set = this.m_data;

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

    this.m_modules._doSummaryJobs(this.m_data, summary);
  }

  /**
   * create the document header
   * 
   * @param header
   *          the header
   */
  private final void __header(final IDocumentHeader header) {
    final ExperimentSet set;
    final ArraySetView<Experiment> data;
    final int size;
    final Parameter param;
    final Object name;

    set = this.m_data;
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

    header.authors().addAuthors(this.m_authors);

    try (final BibDateBuilder bd = header.date()) {
      bd.fromNow();
    }

    try (final IPlainText summary = header.summary()) {
      this.__summary(summary);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void run() {
    try (final IDocument doc = this.m_doc) {

      this.m_modules._doInitJobs(this.m_data, doc);

      try (final IDocumentHeader header = doc.header()) {
        this.__header(header);
      }

      try (final IDocumentBody body = doc.body()) {
        this.m_modules._bodyJobs(this.m_data, body);
      }

      try (final IDocumentBody footer = doc.footer()) {
        this.m_modules._footerJobs(this.m_data, footer);
      }
    }
  }

}
