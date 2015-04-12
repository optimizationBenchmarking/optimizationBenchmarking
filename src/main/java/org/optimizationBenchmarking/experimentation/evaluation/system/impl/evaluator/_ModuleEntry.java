package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.Experiment;
import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IAppendixJobBuilder;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IAppendixModule;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IDescriptionJobBuilder;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IDescriptionModule;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationModule;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentJobBuilder;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentModule;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentSetJobBuilder;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentSetModule;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.config.Configuration;

/** an entry for modules */
final class _ModuleEntry {

  /** the module */
  final IEvaluationModule m_module;

  /** the configuration */
  final Configuration m_config;

  /** the index (and also ID) of the module entry */
  final int m_id;

  /** the child module entries */
  ArrayList<_ModuleEntry> m_children;

  /**
   * create the module entry
   * 
   * @param module
   *          the module
   * @param config
   *          the configuration
   * @param id
   *          the id uniquely identifying this entry in the set of entries
   *          of the same type
   */
  _ModuleEntry(final IEvaluationModule module, final Configuration config,
      final int id) {
    super();

    _EvaluationSetup._checkModule(module);
    _EvaluationSetup._checkConfiguration(config);
    this.m_module = module;
    this.m_config = config;
    this.m_id = id;
  }

  /**
   * Get the number of elements in the hierarchy
   * 
   * @param entries
   *          the entries
   * @return the number of elements
   */
  static final int _hierarchySize(final ArrayList<_ModuleEntry> entries) {
    int count;

    count = 0;
    if (entries != null) {
      for (final _ModuleEntry e : entries) {
        if (e == null) {
          throw new IllegalArgumentException("Module entry is null."); //$NON-NLS-1$
        }
        count++;
        count += _ModuleEntry._hierarchySize(e.m_children);
      }
    }

    return count;
  }

  /**
   * Make the description job
   * 
   * @param entries
   *          the entries
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @return the job or {@code null} if no descriptions were found
   * @throws Exception
   *           if something goes wrong
   */
  static final _DescriptionJobs _makeDescriptionJobs(
      final ArrayList<_ModuleEntry> entries, final ExperimentSet data,
      final Logger logger) throws Exception {
    final _PseudoJob[] jobs;

    jobs = _ModuleEntry.__makeDescriptionJobs(entries, data, logger);
    if (jobs != null) {
      return new _DescriptionJobs(data, logger, jobs);
    }
    return null;
  }

  /**
   * Make the pseudo jobs which compose a description job.
   * 
   * @param entries
   *          the entries
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @return the jobs, or {@code null} if none were found
   * @throws Exception
   *           if something goes wrong
   */
  private static final _PseudoJob[] __makeDescriptionJobs(
      final ArrayList<_ModuleEntry> entries, final ExperimentSet data,
      final Logger logger) throws Exception {
    final int size;
    final _PseudoJob[] jobs;
    IDescriptionJobBuilder builder;
    _ModuleEntry entry;
    _PseudoJob[] sub;
    int i;

    if ((entries == null) || ((size = entries.size()) <= 0)) {
      return null;
    }

    jobs = new _PseudoJob[size];

    for (i = size; (--i) >= 0;) {
      entry = entries.get(i);
      builder = ((IDescriptionModule) (entry.m_module)).use();
      builder.setLogger(logger);
      builder.setData(data);
      builder.configure(entry.m_config);
      sub = _ModuleEntry.__makeDescriptionJobs(entry.m_children, data,
          logger);
      jobs[i] = new _JobWrapper(logger, sub, builder.create());
    }

    return jobs;
  }

  /**
   * Make the experiment job.
   * 
   * @param entries
   *          the entries
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @return the job, or {@code null} if no experiment jobs were found
   * @throws Exception
   *           if something goes wrong
   */
  static final _ExperimentJobs _makeExperimentJobs(
      final ArrayList<_ModuleEntry> entries, final ExperimentSet data,
      final Logger logger) throws Exception {
    final _PseudoJob[] jobs;

    jobs = _ModuleEntry.__makeExperimentJobs(entries, data, logger);
    if (jobs != null) {
      return new _ExperimentJobs(data, logger, jobs);
    }
    return null;
  }

  /**
   * Make the experiment jobs
   * 
   * @param entries
   *          the entries
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @return the jobs, or {@code null} if none were found
   * @throws Exception
   *           if something goes wrong
   */
  private static final _PseudoJob[] __makeExperimentJobs(
      final ArrayList<_ModuleEntry> entries, final ExperimentSet data,
      final Logger logger) throws Exception {
    final int size;
    final ArraySetView<Experiment> list;
    final _PseudoJob[] singleExperiments;
    Experiment experiment;
    int i;

    if ((entries == null) || (entries.size() <= 0)) {
      return null;
    }

    if ((data == null) || ((list = data.getData()) == null)
        || ((size = list.size()) <= 0)) {
      return null;
    }

    singleExperiments = new _PseudoJob[size];
    for (i = size; (--i) >= 0;) {
      experiment = list.get(i);
      singleExperiments[i] = new _ExperimentJob(experiment, data, logger,
          _ModuleEntry.__makeExperimentJobs(entries, experiment, logger));
    }

    return singleExperiments;
  }

  /**
   * Make the experiment jobs
   * 
   * @param entries
   *          the entries
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @return the jobs, or {@code null} if none were found
   * @throws Exception
   *           if something goes wrong
   */
  private static final _PseudoJob[] __makeExperimentJobs(
      final ArrayList<_ModuleEntry> entries, final Experiment data,
      final Logger logger) throws Exception {
    final int size;
    final _PseudoJob[] jobs;
    IExperimentJobBuilder builder;
    _ModuleEntry entry;
    _PseudoJob[] sub;
    int i;

    if ((entries == null) || ((size = entries.size()) <= 0)) {
      return null;
    }

    jobs = new _PseudoJob[size];

    for (i = size; (--i) >= 0;) {
      entry = entries.get(i);
      builder = ((IExperimentModule) (entry.m_module)).use();
      builder.setLogger(logger);
      builder.setData(data);
      builder.configure(entry.m_config);
      sub = _ModuleEntry.__makeExperimentJobs(entry.m_children, data,
          logger);
      jobs[i] = new _JobWrapper(logger, sub, builder.create());
    }

    return jobs;
  }

  /**
   * Make the experiment set job.
   * 
   * @param entries
   *          the entries
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @return the job, or {@code null} if no experiment set jobs were found
   * @throws Exception
   *           if something goes wrong
   */
  static final _ExperimentSetJobs _makeExperimentSetJobs(
      final ArrayList<_ModuleEntry> entries, final ExperimentSet data,
      final Logger logger) throws Exception {
    final _PseudoJob[] jobs;

    jobs = _ModuleEntry.__makeExperimentSetJobs(entries, data, logger);
    if (jobs != null) {
      return new _ExperimentSetJobs(data, logger, jobs);
    }
    return null;
  }

  /**
   * Make the experiment set jobs
   * 
   * @param entries
   *          the entries
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @return the jobs, or {@code null} if none were found
   * @throws Exception
   *           if something goes wrong
   */
  private static final _PseudoJob[] __makeExperimentSetJobs(
      final ArrayList<_ModuleEntry> entries, final ExperimentSet data,
      final Logger logger) throws Exception {
    final int size;
    final _PseudoJob[] jobs;
    IExperimentSetJobBuilder builder;
    _ModuleEntry entry;
    _PseudoJob[] sub;
    int i;

    if ((entries == null) || ((size = entries.size()) <= 0)) {
      return null;
    }

    jobs = new _PseudoJob[size];

    for (i = size; (--i) >= 0;) {
      entry = entries.get(i);
      builder = ((IExperimentSetModule) (entry.m_module)).use();
      builder.setLogger(logger);
      builder.setData(data);
      builder.configure(entry.m_config);
      sub = _ModuleEntry.__makeExperimentSetJobs(entry.m_children, data,
          logger);
      jobs[i] = new _JobWrapper(logger, sub, builder.create());
    }

    return jobs;
  }

  /**
   * Make the appendix job.
   * 
   * @param entries
   *          the entries
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @return the job, or {@code null} if no appendix jobs were found
   * @throws Exception
   *           if something goes wrong
   */
  static final _AppendixJobs _makeAppendixJobs(
      final ArrayList<_ModuleEntry> entries, final ExperimentSet data,
      final Logger logger) throws Exception {
    final _PseudoJob[] jobs;

    jobs = _ModuleEntry.__makeAppendixJobs(entries, data, logger);
    if (jobs != null) {
      return new _AppendixJobs(data, logger, jobs);
    }
    return null;
  }

  /**
   * Make the appendix jobs
   * 
   * @param entries
   *          the entries
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @return the jobs, or {@code null} if none were found
   * @throws Exception
   *           if something goes wrong
   */
  private static final _PseudoJob[] __makeAppendixJobs(
      final ArrayList<_ModuleEntry> entries, final ExperimentSet data,
      final Logger logger) throws Exception {
    final int size;
    final _PseudoJob[] jobs;
    IAppendixJobBuilder builder;
    _ModuleEntry entry;
    _PseudoJob[] sub;
    int i;

    if ((entries == null) || ((size = entries.size()) <= 0)) {
      return null;
    }

    jobs = new _PseudoJob[size];

    for (i = size; (--i) >= 0;) {
      entry = entries.get(i);
      builder = ((IAppendixModule) (entry.m_module)).use();
      builder.setLogger(logger);
      builder.setData(data);
      builder.configure(entry.m_config);
      sub = _ModuleEntry
          .__makeAppendixJobs(entry.m_children, data, logger);
      jobs[i] = new _JobWrapper(logger, sub, builder.create());
    }

    return jobs;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return (this.m_module.toString() + ':' + this.m_config.toString());
  }
}
