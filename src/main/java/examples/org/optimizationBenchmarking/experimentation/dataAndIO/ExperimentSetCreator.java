package examples.org.optimizationBenchmarking.experimentation.dataAndIO;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationInput;

/** A class for creating experiment sets */
public abstract class ExperimentSetCreator implements IEvaluationInput,
    Runnable {

  /** the instance */
  private ExperimentSet m_inst;

  /** create */
  protected ExperimentSetCreator() {
    super();
  }

  /**
   * Build an experiment set
   * 
   * @return the constructed experiment set
   * @throws Exception
   *           if something goes wrong
   */
  protected abstract ExperimentSet buildExperimentSet() throws Exception;

  /**
   * Get the experiment set
   * 
   * @return the experiment set
   * @throws Exception
   *           if something goes wrong
   */
  @Override
  public final synchronized ExperimentSet getExperimentSet()
      throws Exception {
    if (this.m_inst == null) {
      this.m_inst = this.buildExperimentSet();
    }
    return this.m_inst;
  }

  /** Load the experiment data and print the infos */
  @Override
  public final void run() {
    final ExperimentSet es;

    try {
      es = this.getExperimentSet();

      System.out.println(es.getData());

    } catch (final Throwable error) {
      error.printStackTrace();
    }
  }
}
