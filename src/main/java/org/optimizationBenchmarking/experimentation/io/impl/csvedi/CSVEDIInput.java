package org.optimizationBenchmarking.experimentation.io.impl.csvedi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.data.impl.ref.Instance;
import org.optimizationBenchmarking.experimentation.io.impl.edi.EDI;
import org.optimizationBenchmarking.experimentation.io.impl.edi.EDIInputToolBase;
import org.optimizationBenchmarking.experimentation.io.spec.IExperimentSetInput;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.xml.XMLFileType;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * <p>
 * A driver for mixing Comma-Separated Values (CSV) with Experiment Data
 * Interchange (EDI) input. EDI is our default, canonical format for
 * storing and exchanging
 * {@link org.optimizationBenchmarking.experimentation.data experiment data
 * structures}.
 * </p>
 * <p>
 * This driver looks for the following files:
 * </p>
 * <ol>
 * <li>{@code dimensions.edi} or {@code dimensions.xml} to load the
 * definition of the measurement dimensions.</li>
 * <li>{@code instances.edi} or {@code instances.xml} to load the
 * definition of the benchmark instance dimensions.</li>
 * <li>{@code experiment.edi} or {@code experiment.xml} to load the
 * parameter values of an experiment.</li>
 * </ol>
 * <p>
 * The overall folder structure this loader can deal with looks as follows:
 * </p>
 * <ol>
 * <li>Root Folder
 * <ol>
 * <li><code>dimensions.edi</code> or <code>dimensions.xml</code> defining
 * the dimensions</li>
 * <li><code>instances.edi</code> or <code>instances.xml</code> defining
 * the instances</li>
 * <li><code>experimentA</code> the folder for the first experiment
 * <ol>
 * <li><code>experiment.edi</code> or <code>experiment.xml</code> to define
 * the parameters of the first experiment</li>
 * <li><code>instanceA</code> the folder with the log files for
 * &quot;experimentA&quot; on benchmark instance name &quot;instanceA&quot;
 * <ol>
 * <li><code>run1.csv</code> the first run log file for
 * &quot;experimentA&quot; on &quot;instanceA&quot;</li>
 * <li><code>run2.txt</code> the second run log file for
 * &quot;experimentA&quot; on &quot;instanceA&quot;</li>
 * <li>&hellip;</li>
 * </ol>
 * </li>
 * <li><code>instanceB</code> the folder with the log files for
 * &quot;experimentA&quot; on benchmark instance name &quot;instanceB&quot;
 * <ol>
 * <li><code>run1.csv</code> the first run log file for
 * &quot;experimentA&quot; on &quot;instanceB&quot;</li>
 * <li><code>run2.txt</code> the second run log file for
 * &quot;experimentA&quot; on &quot;instanceB&quot;</li>
 * <li>&hellip;</li>
 * </ol>
 * </li>
 * <li>&hellip;</li>
 * </ol>
 * </li>
 * <li><code>experimentB</code> the folder for the first experiment
 * <ol>
 * <li><code>experiment.edi</code> or <code>experiment.xml</code> to define
 * the parameters of the second experiment</li>
 * <li><code>instanceA</code> the folder with the log files for
 * &quot;experimentB&quot; on benchmark instance name &quot;instanceA&quot;
 * <ol>
 * <li><code>run1.csv</code> the first run log file for
 * &quot;experimentB&quot; on &quot;instanceA&quot;</li>
 * <li><code>run2.txt</code> the second run log file for
 * &quot;experimentB&quot; on &quot;instanceA&quot;</li>
 * <li>&hellip;</li>
 * </ol>
 * </li>
 * <li><code>instanceB</code> the folder with the log files for
 * &quot;experimentB&quot; on benchmark instance name &quot;instanceB&quot;
 * <ol>
 * <li><code>run1.csv</code> the first run log file for
 * &quot;experimentB&quot; on &quot;instanceB&quot;</li>
 * <li><code>run2.txt</code> the second run log file for
 * &quot;experimentB&quot; on &quot;instanceB&quot;</li>
 * <li>&hellip;</li>
 * </ol>
 * </li>
 * <li>&hellip;</li>
 * </ol>
 * </li>
 * <li>&hellip;</li>
 * </ol>
 * </li>
 * </ol>
 */
public final class CSVEDIInput extends
    EDIInputToolBase<ExperimentSetContext> implements IExperimentSetInput {

  /** the base name for dimensions files */
  private static final String DIMENSIONS_BASE = "dimensions"; //$NON-NLS-1$

  /** the base name for instances files */
  private static final String INSTANCES_BASE = "instances"; //$NON-NLS-1$

  /** the base name for experiment files */
  private static final String EXPERIMENT_BASE = "experiment"; //$NON-NLS-1$

  /** the candidates for the dimensions file */
  private static final String[] DIMENSIONS_FILE_CANDIDATES;
  /** the candidates for the instances file */
  private static final String[] INSTANCES_FILE_CANDIDATES;
  /** the candidates for the experiment file */
  private static final String[] EXPERIMENT_FILE_CANDIDATES;

  static {
    final LinkedHashSet<String> candidates;

    candidates = new LinkedHashSet<>();
    CSVEDIInput.__add(CSVEDIInput.DIMENSIONS_BASE, candidates);
    DIMENSIONS_FILE_CANDIDATES = candidates.toArray(new String[candidates
        .size()]);

    candidates.clear();
    CSVEDIInput.__add(CSVEDIInput.INSTANCES_BASE, candidates);
    INSTANCES_FILE_CANDIDATES = candidates.toArray(new String[candidates
        .size()]);

    candidates.clear();
    CSVEDIInput.__add(CSVEDIInput.EXPERIMENT_BASE, candidates);
    EXPERIMENT_FILE_CANDIDATES = candidates.toArray(new String[candidates
        .size()]);
  }

  /** the comments */
  private static final String[] COMMENTS = { "#", //$NON-NLS-1$
      "//"//$NON-NLS-1$
  };

  /**
   * add a file to the linked hash set
   *
   * @param base
   *          the base file name
   * @param dest
   *          the destination
   */
  private static final void __add(final String base,
      final LinkedHashSet<String> dest) {
    for (final String str1 : new String[] { base,
        TextUtils.toUpperCase(base) }) {
      for (final String str2 : new String[] {
          EDI.EDI_XML.getDefaultSuffix(),//
          "edi", //$NON-NLS-1$
          "xml", //$NON-NLS-1$
          XMLFileType.XML.getDefaultSuffix() }) {
        for (final String str3 : new String[] { str2,
            TextUtils.toUpperCase(str2) }) {
          dest.add(str1 + '.' + str3);
        }
      }
    }
  }

  /** create */
  CSVEDIInput() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected _CSVEDIContext createToken(final IOJob job,
      final ExperimentSetContext data) throws Throwable {
    return new _CSVEDIContext(data);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "CSV+EDI Experiment Data Input"; //$NON-NLS-1$
  }

  /**
   * Check whether a regular file may be CSV file. Files are considered to
   * be CSV files if their suffix is either {@code csv} or {@code txt}.
   *
   * @param job
   *          the IO job
   * @param data
   *          the data
   * @param path
   *          the path
   * @param attributes
   *          the file attributes
   * @return {@code true} if the file is a CSV file
   * @throws Throwable
   *           if something goes wrong
   */
  protected boolean isCSV(final IOJob job,
      final ExperimentSetContext data, final Path path,
      final BasicFileAttributes attributes) throws Throwable {
    final String n;
    final char lm3, lm2, lm1, lm0;
    int len;

    n = path.toString();
    len = n.length();
    if (len <= 4) {
      return false;
    }

    lm0 = (n.charAt(--len));
    lm1 = (n.charAt(--len));
    lm2 = (n.charAt(--len));
    lm3 = (n.charAt(--len));

    return (((lm3 == '.') && (//
    (((lm2 == 'c') || (lm2 == 'C'))//
        && ((lm1 == 's') || (lm1 == 'S'))//
    && ((lm0 == 'v') || (lm0 == 'V'))) || //
    (((lm2 == 't') || (lm2 == 'T'))//
        && ((lm1 == 'x') || (lm1 == 'X'))//
    && ((lm0 == 't') || (lm0 == 'T'))))));
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isFileInDirectoryLoadable(final IOJob job,
      final ExperimentSetContext data, final Path path,
      final BasicFileAttributes attributes) throws Throwable {
    return (super.isFileInDirectoryLoadable(job, data, path, attributes) || //
    this.isCSV(job, data, path, attributes));
  }

  /** {@inheritDoc} */
  @Override
  protected void file(final IOJob job, final ExperimentSetContext data,
      final Path path, final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding) throws Throwable {
    final _CSVEDIContext context;

    context = ((_CSVEDIContext) job.getToken());
    if (context._isNew(path, attributes)) {
      if (this.isEDI(job, data, path, attributes)) {
        super.file(job, data, path, attributes, encoding);
      } else {
        if (this.isCSV(job, data, path, attributes)) {
          this.__csv(context, path, encoding);
        } else {
          throw new IllegalStateException("Path '" + path //$NON-NLS-1$
              + "' is neither a CSV nor an EDI file."); //$NON-NLS-1$
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected boolean enterDirectory(final IOJob job,
      final ExperimentSetContext data, final Path path,
      final BasicFileAttributes attributes) throws Throwable {
    final _CSVEDIContext context;
    final Logger logger;
    Instance inst;
    BasicFileAttributes candidateAttrs;
    Path candidate;

    logger = job.getLogger();

    if (super.enterDirectory(job, data, path, attributes)) {

      context = ((_CSVEDIContext) (job.getToken()));
      if (context._isNew(path, attributes)) {

        // check for a dimension definition file
        if (!(context._hasDimensions())) {
          dims: for (final String name : CSVEDIInput.DIMENSIONS_FILE_CANDIDATES) {
            candidate = PathUtils.createPathInside(path, name);
            if (candidate != null) {
              try {
                candidateAttrs = Files.readAttributes(candidate,
                    BasicFileAttributes.class);
              } catch (final Throwable t) {
                continue dims;
              }

              if ((candidateAttrs != null)
                  && (candidateAttrs.isRegularFile())) {
                if (context._isNew(candidate, candidateAttrs)) {

                  if ((logger != null) && (logger.isLoggable(Level.FINER))) {
                    logger.finer("Now loading dimensions definition " //$NON-NLS-1$
                        + candidate);
                  }

                  context._dimensionsFound();
                  super.file(job, data, candidate, attributes,
                      StreamEncoding.TEXT);

                  if ((logger != null) && (logger.isLoggable(Level.FINER))) {
                    logger.finer("Done loading dimensions definition " //$NON-NLS-1$
                        + candidate);
                  }

                  break dims;
                }
              }
            }
          }
        }

        // check for an instances definition file
        if (!(context._hasInstances())) {
          insts: for (final String name : CSVEDIInput.INSTANCES_FILE_CANDIDATES) {
            candidate = PathUtils.createPathInside(path, name);
            if (candidate != null) {
              try {
                candidateAttrs = Files.readAttributes(candidate,
                    BasicFileAttributes.class);
              } catch (final Throwable t) {
                continue insts;
              }

              if ((candidateAttrs != null)
                  && (candidateAttrs.isRegularFile())) {
                if (context._isNew(candidate, candidateAttrs)) {

                  if ((logger != null) && (logger.isLoggable(Level.FINER))) {
                    logger.finer("Now loading instances definition " //$NON-NLS-1$
                        + candidate);
                  }

                  context._instancesFound();
                  super.file(job, data, candidate, attributes,
                      StreamEncoding.TEXT);

                  if ((logger != null) && (logger.isLoggable(Level.FINER))) {
                    logger.finer("Done loading instances definition " //$NON-NLS-1$
                        + candidate);
                  }

                  break insts;
                }
              }
            }
          }
        }

        // check for an experiment description file
        exp: for (final String name : CSVEDIInput.EXPERIMENT_FILE_CANDIDATES) {
          candidate = PathUtils.createPathInside(path, name);
          if (candidate != null) {
            try {
              candidateAttrs = Files.readAttributes(candidate,
                  BasicFileAttributes.class);
            } catch (final Throwable t) {
              continue exp;
            }

            if ((candidateAttrs != null)
                && (candidateAttrs.isRegularFile())) {
              if (context._isNew(candidate, candidateAttrs)) {

                if ((logger != null) && (logger.isLoggable(Level.FINER))) {
                  logger.finer("Now loading experiment definition " //$NON-NLS-1$
                      + candidate);
                }

                super.file(job, data, candidate, attributes,
                    StreamEncoding.TEXT);

                if ((logger != null) && (logger.isLoggable(Level.FINER))) {
                  logger.finer("Done loading experiment definition " //$NON-NLS-1$
                      + candidate);
                }

                break exp;
              }
            }
          }
        }

        // check if we are in an instance run set
        if (context._hasInstances()) {
          inst = context.getInstanceSet().find(PathUtils.getName(path));
          if (inst != null) {
            context.runsBegin(true);
            context.runsSetInstance(inst);
          }
        }

        return true;
      }
    }

    return false;
  }

  /**
   * Process a file as CSV file
   *
   * @param job
   *          the job
   * @param path
   *          the path
   * @param encoding
   *          the encoding
   * @throws Throwable
   *           if something goes wrong
   */
  private final void __csv(final _CSVEDIContext job, final Path path,
      final StreamEncoding<?, ?> encoding) throws Throwable {
    String line;
    int idx;

    try (final InputStream is = PathUtils.openInputStream(path)) {

      try (final Reader ir = (((encoding != null) && (Reader.class
          .isAssignableFrom(encoding.getInputClass())))//
      ? ((Reader) (encoding.wrapInputStream(is)))//
          : new InputStreamReader(is))) {
        try (final BufferedReader br = ((ir instanceof BufferedReader)//
        ? ((BufferedReader) ir)//
            : new BufferedReader(ir))) {
          job.runBegin(true);

          while ((line = br.readLine()) != null) {
            line = TextUtils.prepare(line);
            if (line == null) {
              continue;
            }

            for (final String comment : CSVEDIInput.COMMENTS) {
              idx = line.indexOf(comment);
              if (idx >= 0) {
                line = line.substring(0, idx);
              }
            }

            line = TextUtils.prepare(line);
            if (line == null) {
              continue;
            }

            job.runAddDataPoint(line);
          }

          job.runEnd();
        }
      }
    }
  }

  /**
   * get the instance of the {@link CSVEDIInput}
   *
   * @return the instance of the {@link CSVEDIInput}
   */
  public static final CSVEDIInput getInstance() {
    return __EDIInputLoader.INSTANCE;
  }

  /** the loader */
  private static final class __EDIInputLoader {
    /** create */
    static final CSVEDIInput INSTANCE = new CSVEDIInput();
  }
}
