package org.optimizationBenchmarking.experimentation.io.impl.bbob;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;

import org.optimizationBenchmarking.experimentation.data.ExperimentContext;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.data.InstanceRunsContext;
import org.optimizationBenchmarking.experimentation.data.Parameter;
import org.optimizationBenchmarking.experimentation.data.RunContext;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.parsers.DoubleParser;
import org.optimizationBenchmarking.utils.parsers.IntParser;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** the internal content handler */
final class _BBOBHandler implements Comparator<Number[]> {
  /** the string indicating the begin of a comment: {@value} */
  private static final char COMMENT_START = '%';

  /** the data folder start */
  private static final String DATA_FOLDER_START = "data_f"; //$NON-NLS-1$

  /** the hierarchical fsm stack */
  private final ExperimentSetContext m_esc;

  /** the logger */
  private final IOJob m_logger;

  /**
   * create
   * 
   * @param logger
   *          the logger
   * @param esb
   *          the experiment set builder to use
   */
  _BBOBHandler(final IOJob logger, final ExperimentSetContext esb) {
    super();
    this.m_esc = esb;
    this.m_logger = logger;
  }

  /**
   * load a stream
   * 
   * @param f
   *          the file
   * @param list
   *          the list
   * @param canGrow
   *          can the list grow?
   * @throws Throwable
   *           if i/o fails
   */
  private final void __loadStream(final Path f,
      final ArrayList<ArrayList<Number[]>> list, final boolean canGrow)
      throws Throwable {
    ArrayList<Number[]> current;
    int index, i, end1, start2, end2, len;
    double d;
    char ch;
    String s;
    try {
      try (final InputStream is = f.getFileSystem().provider()
          .newInputStream(f)) {
        try (final InputStreamReader fr = new InputStreamReader(is)) {
          try (final BufferedReader br = new BufferedReader(fr)) {

            current = null;
            index = (-1);
            while ((s = br.readLine()) != null) {
              s = TextUtils.normalize(s);
              if (s == null) {
                continue;
              }

              // new run?
              i = s.indexOf(_BBOBHandler.COMMENT_START);
              if ((i == 0) || (s.indexOf('|') >= 0)) {
                // new run?
                index++;
                if (canGrow) {
                  while (index >= list.size()) {
                    list.add(new ArrayList<Number[]>());
                  }
                }
                if (index >= list.size()) {
                  throw new IllegalStateException(//
                      "Incorrect number of runs in file " + f); //$NON-NLS-1$
                }
                current = list.get(index);
                continue;
              }

              // data point
              if (i > 0) {
                s = TextUtils.prepare(s.substring(0, i));
                if (s == null) {
                  continue;
                }
              }

              len = s.length();

              innerA: for (end1 = 0; end1 < len; end1++) {
                ch = s.charAt(end1);
                if (ch <= ' ') {
                  break innerA;
                }
              }
              innerB: for (start2 = end1; start2 < len; start2++) {
                ch = s.charAt(start2);
                if (ch > ' ') {
                  break innerB;
                }
              }
              innerC: for (end2 = start2; end2 < len; end2++) {
                ch = s.charAt(end2);
                if (ch <= ' ') {
                  break innerC;
                }
              }
              innerD: for (start2 = end2; start2 < len; start2++) {
                ch = s.charAt(start2);
                if (ch > ' ') {
                  break innerD;
                }
              }
              innerE: for (end2 = start2; end2 < len; end2++) {
                ch = s.charAt(end2);
                if (ch <= ' ') {
                  break innerE;
                }
              }

              if ((end1 > 0) && (start2 > end1) && (end2 > start2)) {
                if (current == null) {
                  throw new IllegalStateException(//
                      "Found job point, but not run begin in " + f); //$NON-NLS-1$
                }

                d = DoubleParser.INSTANCE.parseDouble(s.substring(start2,
                    end2));
                if (d < -1e-12d) {
                  throw new IllegalArgumentException(
                      "value " + d + //$NON-NLS-1$
                          " for f-f_opt is too negative for assuming numerical imprecision as reason and performing correction to zero."); //$NON-NLS-1$
                }
                if (d <= 0d) {
                  if (d < 0d) {
                    if ((this.m_logger.canLog(Level.WARNING))) {
                      this.m_logger.log(Level.WARNING,//
                          "Correction of negative objective value " + d + //$NON-NLS-1$
                              " to 0 in file " + f); //$NON-NLS-1$
                    }
                  }
                  d = 0d;
                }
                current.add(new Number[] {
                    IntParser.INSTANCE.parseString(s.substring(0, end1)),//
                    Double.valueOf(d) });
              } else {
                throw new IllegalArgumentException("Found string '" + //$NON-NLS-1$
                    s + "' in file " + f); //$NON-NLS-1$
              }
            }
          }
        }
      }
    } catch (final Throwable t) {
      this.m_logger.handleError(t, ("Error in file: " + f)); //$NON-NLS-1$
    }
  }

  /**
   * handle a data folder
   * 
   * @param dat
   *          the dat file
   * @param tdat
   *          the tdat file
   * @param ic
   *          the instance context
   * @throws Throwable
   *           on error
   */
  private final void __handleInstance(final Path tdat, final Path dat,
      final InstanceRunsContext ic) throws Throwable {
    final ArrayList<ArrayList<Number[]>> list;
    ArrayList<Number[]> run;
    Number[][] nums;
    int i, s;
    Number[] lastN;
    Integer lastI, curI;
    Double lastD, curD;

    if (this.m_logger.canLog(IOJob.FINER_LOG_LEVEL)) {
      this.m_logger.log(IOJob.FINER_LOG_LEVEL,
          "Starting to load data from file combo " + //$NON-NLS-1$l
              tdat + " / " + dat); //$NON-NLS-1$
    }

    list = new ArrayList<>();
    if (tdat != null) {
      this.__loadStream(tdat, list, true);
    }
    if (dat != null) {
      this.__loadStream(dat, list, (tdat == null));
    }
    try {
      for (i = list.size(); (--i) >= 0;) {
        run = list.remove(i);
        if (run == null) {
          continue;
        }
        if ((s = run.size()) > 0) {
          nums = run.toArray(new Number[s][]);
          Arrays.sort(nums, this);
          lastD = null;
          lastI = null;
          lastN = null;
          try (final RunContext rc = ic.createRun()) {

            for (final Number[] point : nums) {
              curI = ((Integer) (point[0]));
              curD = ((Double) (point[1]));
              lastN = null;
              if (((lastI == null) || (curI.compareTo(lastI) > 0))) {
                if (((lastD == null) || (curD.compareTo(lastD) < 0))) {
                  rc.addDataPoint(point);
                } else {
                  lastN = point;
                }
              }
              lastD = curD;
              lastI = curI;
            }

            if (lastN != null) {
              rc.addDataPoint(lastN);
            }
          }
        }
      }
    } catch (final Throwable t) {
      throw new IOException(((("Error in combo " + tdat) //$NON-NLS-1$
          + " / ") + dat), t); //$NON-NLS-1$
    }

    if (this.m_logger.canLog(IOJob.FINER_LOG_LEVEL)) {
      this.m_logger.log(IOJob.FINER_LOG_LEVEL,
          "Finished loading data from file combo " + //$NON-NLS-1$
              tdat + " / " + dat); //$NON-NLS-1$
    }
  }

  /**
   * handle a data folder
   * 
   * @param fid
   *          the function index
   * @param dir
   *          the folder
   * @param ec
   *          the experiment context
   * @throws Throwable
   *           on error
   */
  private final void __handleDataFolder(final int fid, final Path dir,
      final ExperimentContext ec) throws Throwable {

    int count, k, len, i;
    byte b;
    Path[][] data;
    BasicFileAttributes attr;
    char ch;
    boolean td;
    String name;
    Object error;

    if (this.m_logger.canLog(IOJob.FINER_LOG_LEVEL)) {
      this.m_logger.log(IOJob.FINER_LOG_LEVEL,
          "Begin handling data folder " + dir + //$NON-NLS-1$
              " for function id " + fid); //$NON-NLS-1$
    }

    error = null;

    try (DirectoryStream<Path> files = Files.newDirectoryStream(dir)) {

      data = new Path[BBOBInput.DIMENSIONS.length][2];
      count = 0;

      outer: for (final Path f : files) {
        attr = Files.readAttributes(f, BasicFileAttributes.class);

        if (attr.isRegularFile()) {
          name = f.getFileName().toString();
          if (name != null) {
            name = TextUtils.normalize(name);
            if (name != null) {
              len = name.length();
              if (len > 4) {
                ch = name.charAt(--len);
                if ((ch == 'T') || (ch == 't')) {
                  ch = name.charAt(--len);
                  if ((ch == 'A') || (ch == 'a')) {
                    ch = name.charAt(--len);
                    if ((ch == 'D') || (ch == 'd')) {

                      ch = name.charAt(--len);
                      if (ch == '.') {
                        td = false;
                      } else {
                        td = true;
                        if ((ch == 'T') || (ch == 't')) {
                          ch = name.charAt(--len);
                          if (ch != '.') {
                            continue outer;
                          }
                        } else {
                          continue outer;
                        }
                      }

                      inner: for (k = len; (--k) >= 0;) {
                        ch = name.charAt(k);
                        if ((ch < '0') || (ch > '9')) {
                          break inner;
                        }
                      }

                      try {
                        b = Byte.parseByte(name.substring((k + 1), len));

                        i = Arrays.binarySearch(BBOBInput.DIMENSIONS, b);
                        if (i >= 0) {
                          data[i][td ? 0 : 1] = f;
                          count++;
                          continue outer;
                        }
                        throw new IllegalStateException("Dimension " + b + //$NON-NLS-1$
                            " is invalid in folder " + f); //$NON-NLS-1$
                      } catch (final Throwable ttt) {
                        if (this.m_logger.canLog(Level.WARNING)) {
                          this.m_logger.log(Level.WARNING,
                              "Problem in folder " + dir,//$NON-NLS-1$
                              ttt);
                        }
                        error = ErrorUtils.aggregateError(ttt, error);
                      }
                    }
                  }
                }

              }
            }
          }

        }
      }

      try {
        if (count > 0) {
          for (count = data.length; (--count) >= 0;) {
            if ((data[count][0] != null) || (data[count][1] != null)) {
              try (final InstanceRunsContext ic = ec.createInstanceRuns()) {
                ic.setInstance(BBOBInput._makeFunctionName(fid,
                    BBOBInput.DIMENSIONS[count]));
                this.__handleInstance(data[count][0], data[count][1], ic);
              }
            }
          }
        }
      } catch (final Throwable a) {
        error = ErrorUtils.aggregateError(a, error);
      }
    }

    if (error != null) {
      try {
        ErrorUtils.throwIOException(//
            ("Error while processing folder " + dir), //$NON-NLS-1$
            error);
      } catch (final Throwable t) {
        this.m_logger.handleError(t,
            "Unrecoverable error in folder " + dir);//$NON-NLS-1$
      }
    }

    if (this.m_logger.canLog(IOJob.FINER_LOG_LEVEL)) {
      this.m_logger.log(IOJob.FINER_LOG_LEVEL,
          "Finished handling data folder " + dir + //$NON-NLS-1$
              " for function id " + fid); //$NON-NLS-1$
    }
  }

  /**
   * Handle a directory
   * 
   * @param dir
   *          the directory
   * @throws Throwable
   *           if I/O fails
   */
  private final void __handleDirectory(final Path dir) throws Throwable {
    final Path[] dataFolders;
    final ArrayList<Path> revisit;
    BasicFileAttributes attr;
    int dataFolderCount, dim;
    String name;
    Object error;
    int i;

    error = null;
    if (this.m_logger.canLog(IOJob.FINER_LOG_LEVEL)) {
      this.m_logger.log(IOJob.FINER_LOG_LEVEL, ("Now entering folder '" //$NON-NLS-1$
          + dir) + '\'');
    }

    revisit = new ArrayList<>();
    try (DirectoryStream<Path> files = Files.newDirectoryStream(dir)) {

      dataFolders = new Path[BBOBInput.MAX_FUNCTION + 1];
      dataFolderCount = 0;

      outer: for (final Path f : files) {
        attr = Files.readAttributes(f, BasicFileAttributes.class);
        if (attr.isDirectory()) {
          revisit.add(f);
          name = f.getFileName().toString();
          if (name != null) {
            name = TextUtils.normalize(name);
            if (name != null) {
              name = name.toLowerCase();
              if (name.startsWith(_BBOBHandler.DATA_FOLDER_START)) {
                try {
                  dim = Integer.parseInt(name
                      .substring(_BBOBHandler.DATA_FOLDER_START.length()));
                  if ((dim > 0) && (dim < dataFolders.length)) {
                    if (dataFolders[dim] != null) {
                      throw new IllegalArgumentException(//
                          "Two data folders for function id " + dim + //$NON-NLS-1$
                              ": folder " + f + //$NON-NLS-1$
                              " and " + dataFolders[dim]); //$NON-NLS-1$
                    }
                    dataFolders[dim] = f;
                    dataFolderCount++;
                    continue outer;
                  }
                  throw new IllegalArgumentException(//
                      "Illegal function id " + dim + //$NON-NLS-1$
                          " of folder " + f); //$NON-NLS-1$
                } catch (final Throwable a) {

                  if (this.m_logger.canLog(Level.WARNING)) {
                    this.m_logger.log(Level.WARNING,
                        "Problem with folder " + f); //$NON-NLS-1$l
                  }

                  error = ErrorUtils.aggregateError(a, error);
                }
              }
            }
          }
        }
      }
    }

    try {
      if (dataFolderCount > 0) {
        try (final ExperimentContext ec = this.m_esc.createExperiment()) {
          name = dir.getFileName().toString();
          ec.setName(name);
          ec.setParameterValue(Parameter.PARAMETER_ALGORITHM, name);
          for (i = dataFolders.length; (--i) > 0;) {
            if (dataFolders[i] != null) {
              this.__handleDataFolder(i, dataFolders[i], ec);
            }
          }
        }
      } else {
        for (final Path f2 : revisit) {
          this.__handleDirectory(f2);
        }
      }
    } catch (final Throwable a) {
      error = ErrorUtils.aggregateError(a, error);
    }

    if (this.m_logger.canLog(IOJob.FINER_LOG_LEVEL)) {
      this.m_logger.log(IOJob.FINER_LOG_LEVEL, ("Now leaving folder '" //$NON-NLS-1$
          + dir) + '\'');
    }

    if (error != null) {
      ErrorUtils.throwIOException(//
          ("Error in directory " + dir),//$NON-NLS-1$
          error);
    }
  }

  /**
   * Load a given file into the experiment set context
   * 
   * @param f
   *          the file
   * @throws Throwable
   *           if I/O fails
   */
  final void _handle(final Path f) throws Throwable {
    final BasicFileAttributes attr;

    attr = Files.readAttributes(f, BasicFileAttributes.class);

    if (attr.isDirectory()) {
      this.__handleDirectory(f);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int compare(final Number[] o1, final Number[] o2) {
    int r;

    if (o1 == o2) {
      return 0;
    }

    r = Integer.compare(o1[0].intValue(), o2[0].intValue());
    if (r != 0) {
      return r;
    }

    return Double.compare(o1[1].doubleValue(), o2[1].doubleValue());
  }

}
