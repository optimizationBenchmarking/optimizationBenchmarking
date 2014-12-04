package org.optimizationBenchmarking.utils.math.mathEngine.impl.R;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.encoding.TextEncoding;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.FileNamePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsFilePredicate;
import org.optimizationBenchmarking.utils.math.mathEngine.impl.abstr.MathEngineTool;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IMathEngineBuilder;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;

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
public final class R extends MathEngineTool<IMathEngineBuilder> {

  /** the parameter denoting the path of the {@code R} binary */
  public static final String PARAM_R_BINARY = "pathOfRBinary"; //$NON-NLS-1$

  /** the shared instance */
  static final R INSTANCE = new R();

  /** the path to the {@code R} executable */
  final Path m_rBinary;

  /** create */
  R() {
    super();
    Path r;

    try {
      r = PathUtils.findFirstInPath(
          new AndPredicate<>(new FileNamePredicate(true,
              new String[] { "R" }), new AndPredicate<>( //$NON-NLS-1$
              CanExecutePredicate.INSTANCE, new _RCriterion())),
          IsFilePredicate.INSTANCE, new Path[] { Configuration.getRoot()
              .getPath(R.PARAM_R_BINARY, null) });
    } catch (final Throwable t) {
      r = null;
    }
    this.m_rBinary = r;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return (this.m_rBinary != null);
  }

  /** {@inheritDoc} */
  @Override
  protected final _REngineBuilder createBuilder() {
    return new _REngineBuilder();
  }

  /**
   * Get the globally shared instance of {@code R}
   * 
   * @return the globally shared instance of {@code R}
   */
  public static final R getInstance() {
    return R.INSTANCE;
  }

  /**
   * Get the name of the encoding to use
   * 
   * @return the name of the encoding to use
   */
  static final TextEncoding _encoding() {
    return StreamEncoding.getUTF8();
  }
}
