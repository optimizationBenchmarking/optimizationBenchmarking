package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/** The type of the dimension. */
public enum EDimensionType {

  /**
   * The dimension represents a time measurement based on CPU clock time.
   * Such measurements are only comparable for measurements taken on the
   * same computer, since they are {@link #isMachineDependent() machine
   * dependent}. The reason is that one machine {@code M1} may be faster
   * than another one (say, {@code M2}), it may perform many more
   * {@link #ITERATION_FE algorithm steps} within the same amount of
   * seconds than the other. Thus, if an algorithm {@code A1} executed
   * {@code M1} gets better results than algorithm {@code A2} executed on
   * {@code M2} in the same time, we cannot make any assumption about which
   * of them is better.
   */
  RUNTIME_CPU,

  /**
   * In order to mitigate the {@link #isMachineDependent() machine
   * dependency} of {@link #RUNTIME_CPU CPU time} measures, we can
   * normalize them with a machine-dependent performance indicator. Such a
   * performance indicator should be proportional (or inverse-proportional)
   * to how fast a machine can execute algorithms (of a given family). Such
   * a normalization procedure makes runtime time measurements based on
   * real time comparable, even if they stem from different machines.
   * However, we can never make real time measurements fully unbiased, as
   * there may always be stuff such as scheduling anomalies, other
   * processes running on the same computers, interferences by the Java
   * virtual machines, influences of swapping and paging, hard drive access
   * issues, etc., that may {@link #isMachineBiased() bias} them.
   */
  RUNTIME_NORMALIZED,

  /**
   * A machine-independent time measurement in algorithm steps. Such
   * measurements are only comparable amongst equivalent algorithm
   * configurations. For instance, in a Genetic Algorithm, an algorithm
   * step may be one generation. Generation counts are only comparable for
   * the same population sizes and operators. Instead of counting
   * generations, one would therefore rather measure runtime in terms of
   * <code>{@link #ITERATION_FE FE}</code>s,
   */
  ITERATION_ALGORITHM_STEP,

  /**
   * A machine-independent time measure in terms of objective function
   * evaluations ({@code FE}s). An objective function evaluation
   * corresponds to a fully created solution which was passed to the
   * objective function. One {@code FE} may have different real time costs
   * for different algorithms. In the Traveling Salesman Problem, for
   * instance, some local search steps can be performed in <em>O(1)</em>,
   * crossovers in an Evolutionary Algorithm may take <em>O(n)</em>, and a
   * solution creation in an Ant Colony Optimization algorithm may take
   * <em>O(n<sup>2</sup>)</em> steps, where <em>n</em> is the problem
   * scale. Sometimes there may exist a way to measure runtime in an
   * algorithm-independent fashion which is more precise, i.e., a
   * {@link #ITERATION_SUB_FE sub-FE} measure.
   */
  ITERATION_FE,

  /**
   * A machine-independent time measure with a granularity below that of
   * <code>{@link #ITERATION_FE FE}</code>s. Such a time measure should be
   * the fairest machine-independent measure to compare different
   * algorithms.
   */
  ITERATION_SUB_FE,

  /**
   * A problem-dependent quality measure, such as objective values. The
   * values in this dimension can only be compared for the same problem.
   */
  QUALITY_PROBLEM_DEPENDENT,

  /**
   * A problem-independent quality measure, such as normalized objective
   * values.
   */
  QUALITY_PROBLEM_INDEPENDENT, ;

  /** the value set view */
  public static final ArraySetView<EDimensionType> INSTANCES = new ArraySetView<>(
      EDimensionType.values());

  /**
   * Are the measurements given in this dimension machine dependent? If you
   * have two {@link #RUNTIME_CPU CPU time} measurements from two different
   * machines, then you cannot compare them. The reason is that one machine
   * may have a much faster processor than the other. In this case, results
   * obtained by the first machine after the same amount of
   * {@link #RUNTIME_CPU CPU time} may seem much better, even for the same
   * algorithm.
   * 
   * @return {@code true} if two measurements in this dimension taken on
   *         different computers cannot be compared, {@code false} if they
   *         can.
   * @see #isMachineBiased()
   */
  public final boolean isMachineDependent() {
    return (this == RUNTIME_CPU);
  }

  /**
   * May the measurements given in this dimension be influenced by other
   * stuff going on on the machine they are taken? With
   * {@link #RUNTIME_NORMALIZED normalized runtime}, we can mitigate the
   * machine dependency. However, it is hardly possible to remove the
   * impact of scheduling, latency of hard drive access, virtual machine
   * stuff, etc. this way. Thus, {@link #RUNTIME_NORMALIZED normalized
   * runtime} measures (and {@link #RUNTIME_CPU CPU time} as well) may be
   * biased by machine-internal effects.
   * 
   * @return {@code true} if the measurements taken in this dimension may
   *         be biased by other stuff going on the same machine,
   *         {@code false} otherwise.
   */
  public final boolean isMachineBiased() {
    return ((this == RUNTIME_CPU) || (this == RUNTIME_NORMALIZED));
  }

  /**
   * Does the dimension represent an quality measure, such as
   * {@link #QUALITY_PROBLEM_DEPENDENT objective values} or
   * {@link #QUALITY_PROBLEM_INDEPENDENT normalized objective values}?
   * 
   * @return {@code true} if the dimension represent an quality measure,
   *         such as {@link #QUALITY_PROBLEM_DEPENDENT objective values} or
   *         {@link #QUALITY_PROBLEM_INDEPENDENT normalized objective
   *         values}, {@code false} otherwise.
   */
  public final boolean isSolutionQualityMeasure() {
    return ((this == QUALITY_PROBLEM_DEPENDENT) || (this == QUALITY_PROBLEM_INDEPENDENT));
  }

  /**
   * Is this dimension a machine-independent time measure based on
   * algorithm steps?
   * 
   * @return {@code true} if this dimension represents a
   *         machine-independent time measure based on algorithm steps,
   *         {@code false} if not.
   */
  public final boolean isIterationCount() {
    return ((this == ITERATION_ALGORITHM_STEP) || (this == ITERATION_FE) || (this == ITERATION_SUB_FE));
  }

  /**
   * Is this dimension a time measure?
   * 
   * @return {@code true} if this dimension is a machine-dependent,
   *         machine-independent, or machine-biased time measure,
   *         {@code false} if this dimension does not represent time.
   */
  public final boolean isTimeMeasure() {
    return ((this != QUALITY_PROBLEM_DEPENDENT) && (this != QUALITY_PROBLEM_INDEPENDENT));
  }

}
