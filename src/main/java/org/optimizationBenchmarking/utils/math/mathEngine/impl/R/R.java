package org.optimizationBenchmarking.utils.math.mathEngine.impl.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;

import org.optimizationBenchmarking.utils.EmptyUtils;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.encoding.TextEncoding;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.FileNamePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsFilePredicate;
import org.optimizationBenchmarking.utils.math.mathEngine.impl.abstr.MathEngineTool;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcess;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.ProcessExecutor;

/**
 * <p>
 * The entry point for using {@code R}.
 * </p>
 * <p>
 * Originally, this was intended to be a wrapper for <a
 * href="http://www.rforge.net/JRI/">JRI</a>, the open source and
 * (seemingly) default Java/R Interface. However, implementing such a
 * wrapper would have led to several complex problems: Not only do we need
 * to detect several different binaries or libraries, which itself would
 * have been OK, we also need to set environment variables. That was the
 * killer argument against it, since the goal of the Tool API is a to be
 * close-to "zero-configuration" and hide everything under the hood.
 * Another problem is that JRI is single-threaded.
 * </p>
 * <p>
 * Instead, we therefore run {@code R} as external process.
 * </p>
 */
public final class R extends MathEngineTool<REngineBuilder> {

  /** the parameter denoting the path of the {@code R} binary */
  public static final String PARAM_R_BINARY = "pathOfRBinary"; //$NON-NLS-1$

  /** the path to the {@code R} executable */
  final Path m_rBinary;

  /** the parameters to use for running {@code R} */
  final String[] m_params;

  /** create */
  R() {
    super();

    Path r;
    ArrayList<Path> list;
    HashSet<String> params;
    int size;
    ProcessExecutor exec;
    ExternalProcessBuilder builder;
    final String[] wantedParams;
    final String enc;
    String s;

    r = null;
    params = null;

    exec = ProcessExecutor.getInstance();

    if ((exec != null) && (exec.canUse())) {

      list = new ArrayList<>();
      try {
        r = Configuration.getRoot().getPath(R.PARAM_R_BINARY, null);
        if (r != null) {
          list.add(r);
        }
      } catch (final Throwable t) {
        //
      }

      try {
        r = PathUtils.normalize("/usr/bin/R"); //$NON-NLS-1$
        if (r != null) {
          list.add(r);
        }
      } catch (final Throwable t) {
        //
      }

      try {
        r = PathUtils.normalize("C:\\Program Files\\R\\"); //$NON-NLS-1$
        if (r != null) {
          list.add(r);
        }
      } catch (final Throwable t) {
        //
      }

      r = null;
      try {
        r = PathUtils.findFirstInPath(//
            new AndPredicate<>(//
                new FileNamePredicate(true, new String[] { "R",//$NON-NLS-1$ 
                    "Rterm" }),//$NON-NLS-1$
                new AndPredicate<>(
                    //
                    CanExecutePredicate.INSTANCE,
                    new _RAtLeastVersion3Criterion())),//
            IsFilePredicate.INSTANCE, //
            list.toArray(new Path[list.size()]));

        if (r != null) {
          builder = exec.use();
          builder.setExecutable(r);
          builder.setMergeStdOutAndStdErr(true);

          builder.setDirectory(PathUtils.getTempDir());
          builder.addStringArgument("--help"); //$NON-NLS-1$

          try (final ExternalProcess ep = builder.create()) {
            try (final InputStreamReader isr = new InputStreamReader(
                ep.getStdOut())) {
              try (final BufferedReader br = new BufferedReader(isr)) {
                params = new HashSet<>();
                enc = "--encoding";//$NON-NLS-1$

                wantedParams = new String[] {//
                "--vanilla", //$NON-NLS-1$
                    "--slave", //$NON-NLS-1$
                    "--no-readline", //$NON-NLS-1$
                    "--no-save", //$NON-NLS-1$
                    "--no-environ", //$NON-NLS-1$
                    "--no-site-file", //$NON-NLS-1$
                    "--no-init-file", //$NON-NLS-1$
                    "--no-restore-data", //$NON-NLS-1$
                    "--no-restore-history", //$NON-NLS-1$
                    "--no-restore", //$NON-NLS-1$                      
                    enc, };

                findParams: while ((s = br.readLine()) != null) {
                  s = s.trim();
                  for (final String t : wantedParams) {
                    if (s.startsWith(t)) {
                      if (params.add(t)) {
                        if (params.size() >= wantedParams.length) {
                          break findParams;
                        }
                      }
                    }
                  }
                }

                if (params.contains(enc)) {
                  params.remove(enc);
                  params.add(enc + '=' + R._encoding().name());
                }
              }
            }
            if (ep.waitFor() != 0) {
              r = null;
              params = null;
            }
          }

        }

      } catch (final Throwable t) {
        r = null;
      }
    }

    this.m_rBinary = r;
    this.m_params = (((params != null) && ((size = params.size()) > 0))//
    ? params.toArray(new String[size])
        : EmptyUtils.EMPTY_STRINGS);

  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return (this.m_rBinary != null);
  }

  /** {@inheritDoc} */
  @Override
  protected final REngineBuilder createBuilder() {
    return new REngineBuilder();
  }

  /**
   * Get the globally shared instance of {@code R}
   * 
   * @return the globally shared instance of {@code R}
   */
  public static final R getInstance() {
    return RLoader.INSTANCE;
  }

  /**
   * Get the name of the encoding to use
   * 
   * @return the name of the encoding to use
   */
  static final TextEncoding _encoding() {
    return StreamEncoding.getUTF8();
  }

  /** create the R engine */
  private static final class RLoader {
    /** the shared instance */
    static final R INSTANCE = new R();
  }
}
