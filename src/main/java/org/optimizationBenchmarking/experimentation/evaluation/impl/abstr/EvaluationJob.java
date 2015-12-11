package org.optimizationBenchmarking.experimentation.evaluation.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluationJob;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.Textable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for evaluation jobs.
 *
 * @param
 *          <DT>
 *          the data type
 */
public abstract class EvaluationJob<DT extends IElementSet>
    extends Textable implements IEvaluationJob {

  /** the initial state */
  private static final int STATE_NOTHING = 0;
  /**
   * we are before calling
   * {@link #doInitialize(IElementSet, IDocument, Logger)}
   */
  private static final int STATE_BEFORE_INIT = (EvaluationJob.STATE_NOTHING
      + 1);
  /**
   * we are after calling
   * {@link #doInitialize(IElementSet, IDocument, Logger)}
   */
  private static final int STATE_AFTER_INIT = (EvaluationJob.STATE_BEFORE_INIT
      + 1);
  /**
   * we are before calling
   * {@link #doSummary(IElementSet, IPlainText, Logger)}
   */
  private static final int STATE_BEFORE_SUMMARY = (EvaluationJob.STATE_AFTER_INIT
      + 1);
  /**
   * we are after calling
   * {@link #doSummary(IElementSet, IPlainText, Logger)}
   */
  private static final int STATE_AFTER_SUMMARY = (EvaluationJob.STATE_BEFORE_SUMMARY
      + 1);
  /**
   * we are before calling
   * {@link #doMain(IElementSet, ISectionContainer, Logger)}
   */
  private static final int STATE_BEFORE_MAIN = (EvaluationJob.STATE_AFTER_SUMMARY
      + 1);
  /**
   * we are after calling
   * {@link #doMain(IElementSet, ISectionContainer, Logger)}
   */
  private static final int STATE_AFTER_MAIN = (EvaluationJob.STATE_BEFORE_MAIN
      + 1);

  /** the state names */
  private static final char[][] STATE_NAMES = {
      { 'a', 'f', 't', 'e', 'r', ' ', 'c', 'o', 'n', 's', 't', 'r', 'u',
          'c', 't', 'i', 'o', 'n', },
      { 'b', 'e', 'f', 'o', 'r', 'e', ' ', 'c', 'a', 'l', 'l', 'i', 'n',
          'g', ' ', 'd', 'o', 'I', 'n', 'i', 't', 'i', 'a', 'l', 'i', 'z',
          'e', },
      { 'a', 'f', 't', 'e', 'r', ' ', 'c', 'a', 'l', 'l', 'i', 'n', 'g',
          ' ', 'd', 'o', 'I', 'n', 'i', 't', 'i', 'a', 'l', 'i', 'z',
          'e', },
      { 'b', 'e', 'f', 'o', 'r', 'e', ' ', 'c', 'a', 'l', 'l', 'i', 'n',
          'g', ' ', 'd', 'o', 'S', 'u', 'm', 'm', 'a', 'r', 'y', },
      { 'a', 'f', 't', 'e', 'r', ' ', 'c', 'a', 'l', 'l', 'i', 'n', 'g',
          ' ', 'd', 'o', 'S', 'u', 'm', 'm', 'a', 'r', 'y', },
      { 'b', 'e', 'f', 'o', 'r', 'e', ' ', 'c', 'a', 'l', 'l', 'i', 'n',
          'g', ' ', 'd', 'o', 'M', 'a', 'i', 'n', },
      { 'a', 'f', 't', 'e', 'r', ' ', 'c', 'a', 'l', 'l', 'i', 'n', 'g',
          ' ', 'd', 'o', 'M', 'a', 'i', 'n', }, };

  /** the data */
  private DT m_data;

  /** the logger */
  private Logger m_logger;

  /** the state */
  private volatile int m_state;

  /**
   * Create the evaluation job
   *
   * @param data
   *          the data
   * @param logger
   *          the logger
   */
  protected EvaluationJob(final DT data, final Logger logger) {
    super();

    EvaluationJob._checkData(this, data);
    this.m_data = data;
    this.m_logger = logger;
    this.m_state = EvaluationJob.STATE_NOTHING;
  }

  /**
   * Check the data and find whether it is OK or not
   *
   * @param caller
   *          the caller
   * @param data
   *          the data
   */
  static final void _checkData(final Object caller,
      final IElementSet data) {
    final ArrayListView<?> list;

    if (data == null) {
      throw new IllegalArgumentException(//
          TextUtils.className(caller.getClass()) + //
              " cannot accept null as input data."); //$NON-NLS-1$
    }

    list = data.getData();
    if (list == null) {
      throw new IllegalArgumentException(//
          "The getData() method of the "//$NON-NLS-1$
              + TextUtils.className(data.getClass()) + //
              " provided as input to the " //$NON-NLS-1$
              + TextUtils.className(caller.getClass()) + //
              " returned null.");//$NON-NLS-1$
    }

    if (list.isEmpty()) {
      throw new IllegalArgumentException(//
          "The getData() method of the "//$NON-NLS-1$
              + TextUtils.className(data.getClass()) + //
              " provided as input to the " //$NON-NLS-1$
              + TextUtils.className(caller.getClass()) + //
              " returned an empty set.");//$NON-NLS-1$
    }
  }

  /**
   * step the state
   *
   * @param expected
   *          the expected state
   * @param next
   *          the next state
   */
  private synchronized final void __step(final int expected,
      final int next) {
    final MemoryTextOutput mto;

    if (this.m_state != expected) {
      mto = new MemoryTextOutput();
      mto.append("Evaluation job ");//$NON-NLS-1$
      this.toText(mto);
      mto.append(" is supposed to be in state '");//$NON-NLS-1$
      mto.append(EvaluationJob.STATE_NAMES[expected]);
      mto.append("' but is in state '"); //$NON-NLS-1$
      mto.append(EvaluationJob.STATE_NAMES[this.m_state]);
      mto.append('\'');
      mto.append('.');
      throw new IllegalStateException(mto.toString());
    }
    this.m_state = next;
  }

  /**
   * Perform the initialization. This may involve, e.g., allocating labels.
   *
   * @param data
   *          the data to process
   * @param document
   *          the document to which we will write the output
   * @param logger
   *          the logger, or {@code null} if no log output should be
   *          generated
   */
  protected void doInitialize(final DT data, final IDocument document,
      final Logger logger) {
    //
  }

  /** {@inheritDoc} */
  @Override
  public final void initialize(final IDocument document) {
    this.__step(EvaluationJob.STATE_NOTHING,
        EvaluationJob.STATE_BEFORE_INIT);
    try {
      this.doInitialize(this.m_data, document, this.m_logger);
    } finally {
      this.__step(EvaluationJob.STATE_BEFORE_INIT,
          EvaluationJob.STATE_AFTER_INIT);
    }
  }

  /**
   * Write the summary text, if any.
   *
   * @param data
   *          the data to process
   * @param summary
   *          the
   *          {@link org.optimizationBenchmarking.utils.document.spec.IPlainText
   *          text output} to which we may write the summary text. Any
   *          summary text should be preceded by a space.
   * @param logger
   *          the logger, or {@code null} if no log output should be
   *          generated
   */
  protected void doSummary(final DT data, final IPlainText summary,
      final Logger logger) {
    //
  }

  /** {@inheritDoc} */
  @Override
  public final void summary(final IPlainText summary) {
    this.__step(EvaluationJob.STATE_AFTER_INIT,
        EvaluationJob.STATE_BEFORE_SUMMARY);
    this.doSummary(this.m_data, summary, this.m_logger);
    this.__step(EvaluationJob.STATE_BEFORE_SUMMARY,
        EvaluationJob.STATE_AFTER_SUMMARY);
  }

  /**
   * Generate the main section.
   *
   * @param data
   *          the data to process
   * @param sectionContainer
   *          the
   *          {@link org.optimizationBenchmarking.utils.document.spec.ISectionContainer
   *          section container} in which the destination section of this
   *          job should be created
   * @param logger
   *          the logger, or {@code null} if no log output should be
   *          generated
   */
  protected void doMain(final DT data,
      final ISectionContainer sectionContainer, final Logger logger) {
    //
  }

  /** {@inheritDoc} */
  @Override
  public final void main(final ISectionContainer sectionContainer) {
    try {
      this.__step(EvaluationJob.STATE_AFTER_SUMMARY,
          EvaluationJob.STATE_BEFORE_MAIN);
      this.doMain(this.m_data, sectionContainer, this.m_logger);
      this.__step(EvaluationJob.STATE_BEFORE_MAIN,
          EvaluationJob.STATE_AFTER_MAIN);
    } finally {
      this.m_data = null;
      this.m_logger = null;
    }
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    textOut.append(TextUtils.className(this.getClass()));
    if (this.m_data != null) {
      textOut.append(" on "); //$NON-NLS-1$
      textOut.append(this.m_data);
    }
  }
}
