package org.optimizationBenchmarking.experimentation.io.tspSuite;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.ExperimentContext;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.data.InstanceRunsContext;
import org.optimizationBenchmarking.experimentation.data.RunContext;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.FileIODriver;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** the internal content handler */
final class _TSPSuiteHandler extends FileIODriver<Object, Object> {
  /** the string indicating the begin of a comment: {@value} */
  private static final String COMMENT_START = "//";//$NON-NLS-1$
  /**
   * the identifier of the section in the log files which holds the
   * algorithm information
   */
  private static final String ALGORITHM_DATA_SECTION = "ALGORITHM_DATA_SECTION"; //$NON-NLS-1$
  /**
   * the identifier to begin the section in the log files which holds the
   * logged information: {@value}
   */
  private static final String LOG_DATA_SECTION = "LOG_DATA_SECTION"; //$NON-NLS-1$
  /**
   * the identifier beginning the section in the log files which holds the
   * infos about the deterministic initializer: {@value}
   */
  private static final String DETERMINISTIC_INITIALIZATION_SECTION = "DETERMINISTIC_INITIALIZATION_SECTION"; //$NON-NLS-1$
  /** the string used to end sections: {@value} */
  private static final String SECTION_END = "SECTION_END";//$NON-NLS-1$

  /** the hierarchical fsm stack */
  private final ExperimentSetContext m_esc;

  /** the experiment context */
  private ExperimentContext m_ec;

  /** the hierarchical fsm stack */
  private InstanceRunsContext m_irsc;

  /** the experiment root */
  private Path m_experimentRoot;

  /** the experiment root */
  private Path m_instanceRunsRoot;

  /**
   * create
   * 
   * @param esb
   *          the experiment set builder to use
   */
  _TSPSuiteHandler(final ExperimentSetContext esb) {
    super();
    this.m_esc = esb;
  }

  /**
   * Load the path.
   * 
   * @param path
   *          the path
   * @param logger
   *          the logger for log output
   * @param defaultEncoding
   *          the expected default encoding
   * @throws IOException
   *           if I/O fails
   */
  final void _loadPath(final Path path, final Logger logger,
      final StreamEncoding<?, ?> defaultEncoding) throws IOException {
    this.doLoadPath(null, path, logger, defaultEncoding);
  }

  /** pop the instance runs context */
  private final void __popIRSC() {
    this.m_instanceRunsRoot = null;
    if (this.m_irsc != null) {
      try {
        this.m_irsc.close();
      } finally {
        this.m_irsc = null;
      }
    }
  }

  /** pop the experiment context */
  private final void __popEC() {
    this.m_experimentRoot = null;
    try {
      this.__popIRSC();
    } finally {
      if (this.m_ec != null) {
        try {
          this.m_ec.close();
        } finally {
          this.m_ec = null;
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doLeaveDirectory(final Object loadContext,
      final Path directory, final Logger logger,
      final StreamEncoding<?, ?> defaultEncoding) throws IOException {

    if (this.m_instanceRunsRoot != null) {
      if (Files.isSameFile(this.m_instanceRunsRoot, directory)) {
        this.__popIRSC();
      }
    }

    if (this.m_experimentRoot != null) {
      if (Files.isSameFile(this.m_experimentRoot, directory)) {
        this.__popEC();
      }
    }
  }

  /**
   * prepare a string for processing
   * 
   * @param s
   *          the string
   * @return the result
   */
  private static final String __prepare(final String s) {
    int i;
    String t;

    t = TextUtils.normalize(s);
    if (t == null) {
      return null;
    }
    i = t.indexOf(_TSPSuiteHandler.COMMENT_START);
    if (i == 0) {
      return null;
    }
    if (i < 0) {
      return t;
    }
    return TextUtils.prepare(t.substring(0, i));
  }

  /**
   * begin a run
   * 
   * @param f
   *          the file
   * @return the run context to use
   */
  private final RunContext __beginRun(final Path f) {
    Path experiment, instance;
    String en, in, n;

    experiment = instance = null;
    en = in = null;
    for (Path p = f.getParent(); p != null; p = p.getParent()) {
      n = TextUtils.normalize(p.getFileName().toString());
      if (n == null) {
        continue;
      }
      if (in == null) {
        in = TSPSuiteDriver._instanceName(n);
        if (in != null) {
          instance = p;
        }
        continue;
      }

      if ("results".equalsIgnoreCase(n) || //$NON-NLS-1$
          "symmetric".equalsIgnoreCase(n) || //$NON-NLS-1$
          "asymmetric".equalsIgnoreCase(n)) { //$NON-NLS-1$
        continue;
      }

      en = n;
      experiment = p;
      break;
    }

    if ((experiment == null) || (instance == null)) {
      // TODO: get instance from file, then try again
      throw new IllegalStateException();
    }

    if (!(instance.equals(this.m_instanceRunsRoot))) {
      this.__popIRSC();
      if (!(experiment.equals(this.m_experimentRoot))) {
        this.__popEC();
        this.m_ec = this.m_esc.createExperiment();
        this.m_ec.setName(en);
        this.m_experimentRoot = experiment;
      }
      this.m_irsc = this.m_ec.createInstanceRuns();
      this.m_irsc.setInstance(in);
      this.m_instanceRunsRoot = instance;
    }

    return this.m_irsc.createRun();
  }

  /** {@inheritDoc} */
  @Override
  protected void doLoadReader(final Object loadContext, final Path file,
      final BufferedReader reader, final Logger logger) throws IOException {
    String s;
    RunContext run;
    int state, idx;

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer(("Trying to load file '" //$NON-NLS-1$
          + file) + '\'');
    }

    run = null;
    state = 0;

    while ((s = reader.readLine()) != null) {
      s = _TSPSuiteHandler.__prepare(s);
      if (s == null) {
        continue;
      }

      if (state == 0) {
        if (_TSPSuiteHandler.LOG_DATA_SECTION.equalsIgnoreCase(s)) {
          state = 1;
          if (run == null) {
            run = this.__beginRun(file);
          }
        } else {
          if (_TSPSuiteHandler.ALGORITHM_DATA_SECTION.equalsIgnoreCase(s)
              || //
              _TSPSuiteHandler.DETERMINISTIC_INITIALIZATION_SECTION
                  .equalsIgnoreCase(s)) {
            state = 2;
            if (run == null) {
              run = this.__beginRun(file);
            }
          }
        }

      } else {
        if (_TSPSuiteHandler.SECTION_END.equalsIgnoreCase(s)) {
          state = 0;
        } else {
          if (state == 1) {
            run.addDataPoint(s);
          } else {
            if (state == 2) {
              idx = s.indexOf(':');
              if (idx <= 0) {
                continue;
              }
              run.setParameterValue(
                  TextUtils.prepare(s.substring(0, idx)),
                  TextUtils.prepare(s.substring(idx + 1)));
            }
          }

        }
      }
    }

    if (run != null) {
      run.close();
    }
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isFileInDirectoryLoadable(final Object loadContext,
      final Path file) {
    final String name;
    int len;
    char ch;

    if (file == null) {
      return false;
    }

    name = TextUtils.normalize(file.getFileName().toString());
    if (name != null) {
      len = name.length();
      if (len > 4) {
        ch = name.charAt(--len);
        if ((ch == 't') || (ch == 'T')) {
          ch = name.charAt(--len);
          if ((ch == 'x') || (ch == 'X')) {
            ch = name.charAt(--len);
            if ((ch == 't') || (ch == 'T')) {
              ch = name.charAt(--len);
              if (ch == '.') {
                return true;
              }
            }
          }
        }
      }
    }

    return false;
  }

}
