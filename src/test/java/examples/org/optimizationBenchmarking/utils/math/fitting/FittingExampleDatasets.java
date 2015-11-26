package examples.org.optimizationBenchmarking.utils.math.fitting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.fitting.models.LogisticModelWithOffsetOverLogX;
import org.optimizationBenchmarking.utils.math.fitting.models.QuadraticModel;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix2D;
import org.optimizationBenchmarking.utils.parallel.Execute;
import org.optimizationBenchmarking.utils.text.TextUtils;

import examples.org.optimizationBenchmarking.experimentation.dataAndIO.BBOBExample;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.ExperimentSetCreator;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.TSPSuiteExample;
import test.junit.TestBase;

/** Some examples for data fitting. */
public final class FittingExampleDatasets extends TestBase
    implements Callable<ArrayListView<FittingExampleDataset>> {

  /** the resources */
  private static final String[] RESOURCES = { //
      "1FlipHC-uf020-01.txt", //$NON-NLS-1$
      "mFlipHC-uf100-01.txt", //$NON-NLS-1$
      "2FlipHCrs-uf250-01.txt", //$NON-NLS-1$
      "2FlipHC-uf250-01.txt", //$NON-NLS-1$
      "2FlipHC-uf250-02.txt", //$NON-NLS-1$
      "2FlipHC-uf250-03.txt", //$NON-NLS-1$
      "2FlipHC-uf250-04.txt", //$NON-NLS-1$
      "2FlipHC-uf250-05.txt", //$NON-NLS-1$
      "2FlipHC-uf250-06.txt", //$NON-NLS-1$
      "2FlipHC-uf250-07.txt", //$NON-NLS-1$
      "2FlipHC-uf250-08.txt", //$NON-NLS-1$
      "2FlipHC-uf250-09.txt", //$NON-NLS-1$
      "2FlipHC-uf250-10.txt", //$NON-NLS-1$
  };

  /** the function modeling the objective value based on time */
  private final LogisticModelWithOffsetOverLogX m_timeObjective;

  /** the function modeling dimensions of the same type */
  private final QuadraticModel m_sameType;

  /** the internal sorter */
  private final __Sorter m_sorter;

  /** the logger */
  final Logger m_logger;

  /**
   * create
   *
   * @param logger
   *          the logger
   */
  public FittingExampleDatasets(final Logger logger) {
    super();

    this.m_timeObjective = new LogisticModelWithOffsetOverLogX();
    this.m_sameType = new QuadraticModel();
    this.m_sorter = new __Sorter();
    this.m_logger = logger;
  }

  /** {@inheritDoc} */
  @Override
  public final ArrayListView<FittingExampleDataset> call()
      throws IOException {
    final ArrayListView<FittingExampleDataset> result;
    final ArrayList<Future<Void>> jobs;
    ArrayList<FittingExampleDataset> list;
    FittingExampleDataset[] sets;

    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.FINE))) {
      this.m_logger.fine("Begin loading example datasets."); //$NON-NLS-1$
    }

    list = new ArrayList<>();
    jobs = new ArrayList<>();

    for (final String resource : FittingExampleDatasets.RESOURCES) {
      jobs.add(Execute.parallel(new __AppendResource(resource, list)));
    }

    jobs.add(Execute.parallel(new __AppendBBOB(list)));
    jobs.add(Execute.parallel(new __AppendTSPSuite(list)));

    Execute.join(jobs);

    sets = list.toArray(new FittingExampleDataset[list.size()]);
    Arrays.sort(sets);
    result = new ArrayListView<>(sets);

    list.clear();
    list = null;

    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.FINE))) {
      this.m_logger.fine("Finished loading example datasets."); //$NON-NLS-1$
    }

    return result;
  }

  /**
   * append a given resource
   *
   * @param resource
   *          the resource
   * @param list
   *          the list
   * @throws IOException
   *           if i/o fails
   */
  final void _appendResource(final String resource,
      final ArrayList<FittingExampleDataset> list) throws IOException {
    final ArrayList<IMatrix> loaded;
    final ArrayList<double[]> current;
    int i, j, size;
    String str;
    double[] data;

    loaded = new ArrayList<>();
    current = new ArrayList<>();
    try (InputStream is = this.getClass().getResourceAsStream(resource)) {
      try (InputStreamReader isr = new InputStreamReader(is)) {
        try (BufferedReader br = new BufferedReader(isr)) {
          while ((str = br.readLine()) != null) {
            if ((str = TextUtils.prepare(str)) == null) {
              size = current.size();
              if (size > 0) {
                loaded.add(new DoubleMatrix2D(
                    current.toArray(new double[size][])));
              }
              current.clear();
              continue;
            }
            data = new double[3];

            i = str.indexOf('\t');
            data[0] = Double.parseDouble(str.substring(0, i));

            i++;
            j = str.indexOf('\t', i);
            data[1] = Double.parseDouble(str.substring(i, j));

            data[2] = Double.parseDouble(str.substring(j + 1));
            current.add(data);
          }
        }
      }
    }

    size = current.size();
    if (size > 0) {
      loaded.add(new DoubleMatrix2D(current.toArray(new double[size][])));
    }

    if (loaded.size() > 0) {
      this.__postprocess(
          ("maxSat_" + //$NON-NLS-1$
              PathUtils.getFileNameWithoutExtension(resource)), //
          loaded, list, new int[] { -1, -1, 1 });
    }
  }

  /**
   * Postprocess the loaded data
   *
   * @param name
   *          the name of the example data set
   * @param loaded
   *          the data
   * @param list
   *          the list to append to
   * @param dimTypes
   *          the description of the dimensions: {@code -1} for time
   *          dimensions, {@code 1} for objective dimensions, {@code 0} for
   *          skip
   */
  private final void __postprocess(final String name,
      final Collection<? extends IMatrix> loaded,
      final ArrayList<FittingExampleDataset> list, final int[] dimTypes) {
    FittingExampleDataset ds;
    int dimA, dimB, useDimA, useDimB, total, index, size, row;
    double[][] rawPoints;
    String useName;

    total = 0;
    for (final IMatrix current : loaded) {
      total += current.m();
    }

    for (dimA = 0; dimA < dimTypes.length; dimA++) {
      if (dimTypes[dimA] == 0) {
        continue;
      }
      for (dimB = (dimA + 1); dimB < dimTypes.length; dimB++) {
        if (dimTypes[dimB] == 0) {
          continue;
        }

        if ((dimTypes[dimA] > 0) && (dimTypes[dimB] < 0)) {
          useDimA = dimB;
          useDimB = dimA;
        } else {
          useDimA = dimA;
          useDimB = dimB;
        }

        rawPoints = new double[total][2];

        index = 0;
        for (final IMatrix run : loaded) {
          size = run.m();
          for (row = 0; row < size; row++) {
            rawPoints[index][0] = run.getDouble(row, useDimA);
            rawPoints[index][1] = run.getDouble(row, useDimB);
            index++;
          }
        }

        Arrays.sort(rawPoints, this.m_sorter);

        useName = name;
        if (dimTypes.length > 2) {
          useName = ((((((useName + '_')
              + ((dimTypes[useDimA] < 0) ? 't' : 'f')) + useDimA)//
              + '_') + ((dimTypes[useDimB] < 0) ? 't' : 'f')) + useDimB);
        }

        ds = new FittingExampleDataset(useName, //
            new DoubleMatrix2D(rawPoints), //
            ((dimTypes[useDimA] == dimTypes[useDimB]) ? this.m_sameType
                : this.m_timeObjective),
            loaded.size());
        synchronized (list) {
          list.add(ds);
        }
      }
    }
  }

  /**
   * append the BBOB data set
   *
   * @param list
   *          the destination list
   * @throws IOException
   *           if i/o fails
   */
  final void _appendBBOB(final ArrayList<FittingExampleDataset> list)
      throws IOException {
    this.__append("bbob", //$NON-NLS-1$
        new BBOBExample(TestBase.getNullLogger()), list, 25);
  }

  /**
   * append the TSPSuite data set
   *
   * @param list
   *          the destination list
   * @throws IOException
   *           if i/o fails
   */
  final void _appendTSPSuite(final ArrayList<FittingExampleDataset> list)
      throws IOException {
    this.__append("tsp", //$NON-NLS-1$
        new TSPSuiteExample(TestBase.getNullLogger()), list, 4, 4);
  }

  /**
   * append the example data set
   *
   * @param baseName
   *          the base name
   * @param source
   *          the source data
   * @param list
   *          the destination list
   * @param maxSetups
   *          the maximum number of setups
   * @param skip
   *          the dimensions to skip
   * @throws IOException
   *           if i/o fails
   */
  private final void __append(final String baseName,
      final ExperimentSetCreator source,
      final ArrayList<FittingExampleDataset> list, final int maxSetups,
      final int... skip) throws IOException {
    final IExperimentSet data;
    final Random rand;
    final HashSet<IInstanceRuns> done;
    final ArrayListView<? extends IExperiment> experiments;
    final int[] dimTypes;
    final ArrayListView<? extends IDimension> dims;
    IExperiment experiment;
    IInstanceRuns runs;
    ArrayListView<? extends IInstanceRuns> instances;
    int setups, trials, size, index;

    try {
      data = source.getExperimentSet();
    } catch (final IOException ioe) {
      throw ioe;
    } catch (final Exception exp) {
      throw new IOException(exp);
    }

    dims = data.getDimensions().getData();
    dimTypes = new int[dims.size()];
    index = 0;
    for (final IDimension dim : dims) {
      dimTypes[index++] = (dim.getDimensionType().isTimeMeasure() ? (-1)
          : 1);
    }
    for (final int i : skip) {
      dimTypes[i] = 0;
    }

    done = new HashSet<>();
    rand = new Random();
    rand.setSeed(234435L);
    experiments = data.getData();

    for (setups = maxSetups, trials = 100000; //
    (setups > 0) && (trials > 0); trials--) {
      size = experiments.size();
      if (size <= 0) {
        continue;
      }
      experiment = experiments.get(rand.nextInt(size));
      instances = experiment.getData();
      size = instances.size();
      if (size <= 0) {
        continue;
      }
      runs = instances.get(rand.nextInt(size));
      if (!(done.add(runs))) {
        continue;
      }
      this.__postprocess(//
          baseName + '_' + experiment.getName() + +'_'
              + runs.getInstance().getName(), //
          runs.getData(), list, dimTypes);
      --setups;
    }
  }

  /**
   * The main method
   *
   * @param args
   *          the arguments
   * @throws IOException
   *           if i/o fails
   */
  public static final void main(final String[] args) throws IOException {
    final ArrayListView<FittingExampleDataset> data;
    final Path root;
    final Random rand;
    Path dest;
    double[] randFitting;
    int index;

    data = new FittingExampleDatasets(null).call();
    root = PathUtils.getTempDir();
    rand = new Random();
    index = 0;

    for (final FittingExampleDataset ds : data) {

      System.out.print(++index);
      System.out.print('\t');
      System.out.println(ds);

      randFitting = new double[ds.model.getParameterCount()];
      ds.model.createParameterGuesser(ds.data)
          .createRandomGuess(randFitting, rand);

      dest = PathUtils.createPathInside(root,
          (Integer.toString(index) + ".txt")); //$NON-NLS-1$
      ds.plot(dest, randFitting, 0d);
    }
  }

  /** The internal sorter class */
  private static final class __Sorter implements Comparator<double[]> {

    /** create */
    __Sorter() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final int compare(final double[] o1, final double[] o2) {
      int idx, cr;
      if (o1 == o2) {
        return 0;
      }

      idx = 0;
      for (final double d : o1) {
        cr = Double.compare(d, o2[idx++]);
        if (cr != 0) {
          return cr;
        }
      }

      return 0;
    }

  }

  /** the internal append resource job */
  private final class __AppendResource implements Callable<Void> {

    /** the resource */
    private final String m_resource;

    /** the list */
    private final ArrayList<FittingExampleDataset> m_list;

    /**
     * create the job
     *
     * @param resource
     *          the resource
     * @param list
     *          the list
     */
    __AppendResource(final String resource,
        final ArrayList<FittingExampleDataset> list) {
      super();
      this.m_list = list;
      this.m_resource = resource;
    }

    /** {@inheritDoc} */
    @Override
    public final Void call() throws Exception {
      if ((FittingExampleDatasets.this.m_logger != null)
          && (FittingExampleDatasets.this.m_logger
              .isLoggable(Level.FINER))) {
        FittingExampleDatasets.this.m_logger
            .finer("Now beginning to load resource " + this.m_resource); //$NON-NLS-1$
      }
      FittingExampleDatasets.this._appendResource(this.m_resource,
          this.m_list);
      if ((FittingExampleDatasets.this.m_logger != null)
          && (FittingExampleDatasets.this.m_logger
              .isLoggable(Level.FINER))) {
        FittingExampleDatasets.this.m_logger
            .finer("Finished loading resource " + this.m_resource); //$NON-NLS-1$
      }
      return null;
    }
  }

  /** the internal append bbob job */
  private final class __AppendBBOB implements Callable<Void> {

    /** the list */
    private final ArrayList<FittingExampleDataset> m_list;

    /**
     * create the job
     *
     * @param list
     *          the list
     */
    __AppendBBOB(final ArrayList<FittingExampleDataset> list) {
      super();
      this.m_list = list;
    }

    /** {@inheritDoc} */
    @Override
    public final Void call() throws Exception {
      if ((FittingExampleDatasets.this.m_logger != null)
          && (FittingExampleDatasets.this.m_logger
              .isLoggable(Level.FINER))) {
        FittingExampleDatasets.this.m_logger
            .finer("Now beginning to load bbob"); //$NON-NLS-1$
      }
      FittingExampleDatasets.this._appendBBOB(this.m_list);
      if ((FittingExampleDatasets.this.m_logger != null)
          && (FittingExampleDatasets.this.m_logger
              .isLoggable(Level.FINER))) {
        FittingExampleDatasets.this.m_logger
            .finer("Finished loading bbob"); //$NON-NLS-1$
      }
      return null;
    }
  }

  /** the internal append tsp suite job */
  private final class __AppendTSPSuite implements Callable<Void> {

    /** the list */
    private final ArrayList<FittingExampleDataset> m_list;

    /**
     * create the job
     *
     * @param list
     *          the list
     */
    __AppendTSPSuite(final ArrayList<FittingExampleDataset> list) {
      super();
      this.m_list = list;
    }

    /** {@inheritDoc} */
    @Override
    public final Void call() throws Exception {
      if ((FittingExampleDatasets.this.m_logger != null)
          && (FittingExampleDatasets.this.m_logger
              .isLoggable(Level.FINER))) {
        FittingExampleDatasets.this.m_logger
            .finer("Now beginning to load tsp suite"); //$NON-NLS-1$
      }
      FittingExampleDatasets.this._appendTSPSuite(this.m_list);
      if ((FittingExampleDatasets.this.m_logger != null)
          && (FittingExampleDatasets.this.m_logger
              .isLoggable(Level.FINER))) {
        FittingExampleDatasets.this.m_logger
            .finer("Finished loading tsp suite"); //$NON-NLS-1$
      }
      return null;
    }
  }
}
