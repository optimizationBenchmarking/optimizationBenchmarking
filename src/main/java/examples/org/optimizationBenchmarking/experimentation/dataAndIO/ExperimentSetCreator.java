package examples.org.optimizationBenchmarking.experimentation.dataAndIO;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.Feature;
import org.optimizationBenchmarking.experimentation.data.Parameter;
import org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups.PropertyValueGrouper;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationInput;
import org.optimizationBenchmarking.utils.MemoryUtils;
import org.optimizationBenchmarking.utils.config.Configuration;

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
      MemoryUtils.quickGC();
    }
    return this.m_inst;
  }

  /** Load the experiment data and print the infos */
  @Override
  public final void run() {
    final ExperimentSet es;

    try {
      es = this.getExperimentSet();

      System.out.print("Dimensions: "); //$NON-NLS-1$
      System.out.println(es.getDimensions().getData());

      System.out.print("Instances: "); //$NON-NLS-1$
      System.out.println(es.getInstances().getData());

      System.out.print("Features: "); //$NON-NLS-1$
      System.out.println(es.getFeatures().getData());

      System.out.print("Parameters: "); //$NON-NLS-1$
      System.out.println(es.getParameters().getData());

      System.out.print("Experiments: "); //$NON-NLS-1$
      System.out.println(es.getData());

      for (final Feature feature : es.getFeatures().getData()) {
        System.out.print("Grouped Feature '");//$NON-NLS-1$
        System.out.print(feature.getName());
        System.out.print("': ");//$NON-NLS-1$
        System.out.println(//
            PropertyValueGrouper.configure(feature,
                Configuration.getRoot()).get(feature));
      }

      for (final Parameter parameter : es.getParameters().getData()) {
        System.out.print("Grouped Parameter '");//$NON-NLS-1$
        System.out.print(parameter.getName());
        System.out.print("': ");//$NON-NLS-1$
        System.out.println(//
            PropertyValueGrouper.configure(parameter,
                Configuration.getRoot()).get(parameter));
      }

    } catch (final Throwable error) {
      error.printStackTrace();
    }
  }
}
